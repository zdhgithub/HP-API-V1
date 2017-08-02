package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishShopSignMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.FishSiteSignMapper;
import cn.heipiao.api.mapper.PartnerDevlopOrdersMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PartnerOverMapper;
import cn.heipiao.api.mapper.PartnerRewardConfigMapper;
import cn.heipiao.api.mapper.PartnerShopRewardReviewMapper;
import cn.heipiao.api.mapper.PartnerSiteRewardReviewMapper;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopSign;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteSign;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerDevlopOrders;
import cn.heipiao.api.pojo.PartnerEarningOrders;
import cn.heipiao.api.pojo.PartnerOver;
import cn.heipiao.api.pojo.PartnerRewardConfig;
import cn.heipiao.api.pojo.PartnerShopRewardReview;
import cn.heipiao.api.pojo.PartnerSiteRewardReview;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.PartnerClaimService;
import cn.heipiao.api.service.PartnerShopEarningOrdersService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

@Service
public class PartnerClaimServiceImpl implements PartnerClaimService{
	@Resource
	private PartnerSiteRewardReviewMapper partnerSiteRewardReviewMapper;
	@Resource
	private PartnerRewardConfigMapper partnerRewardConfigMapper;
	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private PartnerOverMapper partnerOverMapper;
	@Resource
	private PartnerShopRewardReviewMapper partnerShopRewardReviewMapper;
	@Resource
	private FishShopSignMapper<FishShopSign> fishShopSignMapper;
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private AccountBillService accountBillService;
	@Resource
	private PartnerMapper partnerMapper;
	@Resource
	private SystemMsgService systemMsgService;
	@Resource
	private JPushService jPushService;
	@Resource
	private PartnerShopEarningOrdersService partnerShopEarningOrdersService;
	@Resource
	private PartnerDevlopOrdersMapper partnerDevlopOrdersMapper;
	@Resource
	private FishSiteService fishSiteService;
	@Resource
	private AccountService accountService;
	@Resource
	private FishSiteSignMapper fishSiteSignMapper;
	/**
	 * 合伙人钓场认领奖金发放
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public int updateSiteId(Integer siteId, Integer shelvesAmount,Integer status) throws Exception {
		FishSite site = fishSiteService.selectById(siteId);
		FishSiteSign siteSign = fishSiteSignMapper.selectOne(siteId);
		if( site.getUid() == null ){
			return Status.fish_site_not_auth;
		}
		if(siteSign!=null && siteSign.getPartnerId() == null ){
			return Status.accept_partner_not_exists;
		}
//		if(site.getCategory() == null){
//			return Status.NOT_CATEGORY;
//		}
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerSiteRewardReview partnerSiteRewardReview = partnerSiteRewardReviewMapper
				.selectBysiteId(siteId);
		if (partnerSiteRewardReview == null || partnerSiteRewardReview.getShelvesStatus()==1) {
			return Status.CONFIG_NOTEXCIT;
			
		} else {
			partnerSiteRewardReview.setSiteId(siteId);
			partnerSiteRewardReview.setShelvesStatus(status);
			partnerSiteRewardReview.setShelvesAmount(shelvesAmount);
			partnerSiteRewardReview.setShelvesTime(t);
			partnerSiteRewardReviewMapper
					.updateBysiteId(partnerSiteRewardReview);
		}
		//认领审核通过后，变为售票已认领    合伙人第一次认领通过之后，默认进入售票认领状态，等待审核
		if(status==1 && siteSign !=null &&siteSign.getPartnerId()!=null){
			User u= partnerMapper.selectByPartnerIdAsLock(siteSign.getPartnerId());
			if(u==null || u.getId()==null){
				return Status.accept_partner_not_exists;
			}
			//添加订单
			PartnerDevlopOrders pdo = new PartnerDevlopOrders();
			pdo.setAmount(shelvesAmount);
			pdo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			pdo.setDesc("合伙人钓场认领开发奖金");
			pdo.setBid(siteId.longValue());
			pdo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, u.getId().toString()));
			pdo.setPartnerId(siteSign.getPartnerId());
			pdo.setType(1);
			pdo.setUid(u.getId());
			partnerDevlopOrdersMapper.insertPojo(pdo);
			//添加漂币
			
			accountService.addGoldCoin(u.getId(), shelvesAmount);
			//添加账单
			
			accountBillService.addPojo(u.getId(), pdo.getOrderId(), 1, 18, 2,ArithUtil.div(shelvesAmount, 100, 2) , site.getFishSiteName()+"上架奖励");
			Map<String,String> extras = new HashMap<String, String>();
			extras.put("siteName", site.getFishSiteName());
			extras.put("reward", shelvesAmount + "");
			//合伙人消息保存
			systemMsgService.saveMsg("开发奖", null, site.getFishSiteName() + "认领审核通过",u.getId().intValue() , null);
			//合伙人消息推送
			jPushService.sendPush(u.getId().intValue(), "C", "开发奖认领审核通过", site.getFishSiteName() + "审核通过", extras);
		}
		return Status.success;
	}
	/**
	 * 合伙人钓场售票奖金审核
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public int updateTradingSiteId(Integer siteId,Integer tradingAmount,Integer status) throws Exception {
		FishSite site = fishSiteService.selectById(siteId);
		FishSiteSign siteSign = fishSiteSignMapper.selectOne(siteId);
		if( site.getUid() == null ){
			return Status.fish_site_not_auth;
		}
//		if(site.getPartnerId() == null){
//			return Status.partner_not_exists;
//		}
		if(site.getCategory() == null){
			return Status.NOT_CATEGORY;
		}
		if(siteSign ==null){
			return Status.NOT_PASS;
		}
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerSiteRewardReview partnerSiteRewardReview = partnerSiteRewardReviewMapper
				.selectBysiteId(siteId);
		if (partnerSiteRewardReview == null || partnerSiteRewardReview.getTradingStatus()==1) {
			return Status.CONFIG_NOTEXCIT;
		}
		partnerSiteRewardReview.setSiteId(siteId);
		partnerSiteRewardReview.setTradingStatus(status);
		partnerSiteRewardReview.setTradingAmount(tradingAmount);
		partnerSiteRewardReview.setTradingTime(t);
		partnerSiteRewardReviewMapper
				.updateBysiteId(partnerSiteRewardReview);
		//认领审核通过后，变为已签约
		if(status==1){
			User u = partnerMapper.selectByPartnerIdAsLock(siteSign.getPartnerId());
			//添加订单
			PartnerDevlopOrders pdo = new PartnerDevlopOrders();
			pdo.setAmount(tradingAmount);
			pdo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			pdo.setDesc("合伙人钓场售票认领开发奖金");
			pdo.setBid(siteId.longValue());
			pdo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, u.getId().toString()));
			pdo.setPartnerId(site.getPartnerId());
			pdo.setType(1);
			pdo.setUid(u.getId());
			partnerDevlopOrdersMapper.insertPojo(pdo);
			//添加漂币
			accountService.addGoldCoin(u.getId(), tradingAmount);
			//添加账单
			accountBillService.addPojo(u.getId(), pdo.getOrderId(), 1, 18, 2,ArithUtil.div(tradingAmount, 100, 2), site.getFishSiteName()+"正常交易奖励");
			Map<String,String> extras = new HashMap<String, String>();
			extras.put("siteName", site.getFishSiteName());
			extras.put("reward", tradingAmount + "");
			//合伙人消息保存
			systemMsgService.saveMsg("开发奖", null, site.getFishSiteName() + "售票认领审核通过",u.getId().intValue() , null);
			//合伙人消息推送
			jPushService.sendPush(u.getId().intValue(), "C", "开发奖售票认领审核通过", site.getFishSiteName() + "审核通过", extras);
		}
		return Status.success;
	}
	/**
	 * 合伙人渔具店认领奖金审核
	 */
	@Override
	@Transactional
	public int updateShopId(Long shopId, Integer shelvesAmount,Integer status) throws Exception{
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerShopRewardReview partnerShopRewardReview = partnerShopRewardReviewMapper.selectByShopId(shopId);
				
		if (partnerShopRewardReview == null || partnerShopRewardReview.getShelvesStatus()==1) {
			return Status.CONFIG_NOTEXCIT;
		}
			partnerShopRewardReview.setShopId(shopId);
			partnerShopRewardReview.setShelvesStatus(status);
			partnerShopRewardReview.setShelvesAmount(shelvesAmount);
			partnerShopRewardReview.setShelvesTime(t);
			partnerShopRewardReviewMapper
					.updateByShopId(partnerShopRewardReview);
		//认领审核通过后，变为已认领
			FishShop fishShop = fishShopMapper.selectFishShopByShopId(shopId);
			FishShopSign fishShopSign = fishShopSignMapper.selectOne(shopId);
		if(fishShopSign!=null && status==1){
			User u = partnerMapper.selectByPartnerIdAsLock(fishShopSign.getPartnerId());
			//添加订单
			PartnerDevlopOrders pdo = new PartnerDevlopOrders();
			pdo.setAmount(shelvesAmount);
			pdo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			pdo.setDesc("合伙人渔具店售票认领开发奖金");
			pdo.setBid(shopId);
			pdo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, u.getId().toString()));
			pdo.setPartnerId(fishShopSign.getPartnerId());
			pdo.setType(0);
			pdo.setUid(u.getId());
			partnerDevlopOrdersMapper.insertPojo(pdo);
			//添加漂币
			accountService.addGoldCoin(u.getId(), shelvesAmount);
			//添加账单
			accountBillService.addPojo(u.getId(), pdo.getOrderId(), 1, 18, 2,ArithUtil.div(shelvesAmount, 100, 2) , fishShop.getName()+"上架奖励");
			Map<String,String> extras = new HashMap<String, String>();
			extras.put("siteName", fishShop.getName());
			extras.put("reward", shelvesAmount + "");
			//合伙人消息保存
			systemMsgService.saveMsg("开发奖", null, fishShop.getName() + "认领审核通过",u.getId().intValue() , null);
			//合伙人消息推送
			jPushService.sendPush(u.getId().intValue(), "C", "开发奖认领审核通过", fishShop.getName() + "审核通过", extras);
		}
		return Status.success;
	}
	/**
	 * 合伙人渔具店售票奖金审核
	 */
	@Override
	@Transactional
	public int updateTradingShopId(Long shopId,Integer tradingAmount,Integer status) throws Exception{
		
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerShopRewardReview partnerShopRewardReview = partnerShopRewardReviewMapper
				.selectByShopId(shopId);
		if (partnerShopRewardReview == null || partnerShopRewardReview.getTradingStatus()==1) {
			return Status.CONFIG_NOTEXCIT;
		} 	
			partnerShopRewardReview.setShopId(shopId);
			partnerShopRewardReview.setTradingStatus(status);
			partnerShopRewardReview.setTradingAmount(tradingAmount);
			partnerShopRewardReview.setTradingTime(t);
			partnerShopRewardReviewMapper
					.updateByShopId(partnerShopRewardReview);
		//认领审核通过后，变为已签约
		FishShopSign signShop = fishShopSignMapper.selectOne(shopId);
		if(signShop == null || (signShop !=null && signShop.getPartnerId()==null)){
			return Status.not_trading;
		}
		if(signShop != null && signShop.getPartnerId()!=null && status==1){
			FishShop fishShop = fishShopMapper.selectFishShopAsLockByShopId(shopId);
			User u = partnerMapper.selectByPartnerIdAsLock(signShop.getPartnerId());
			//添加订单
			PartnerDevlopOrders pdo = new PartnerDevlopOrders();
			pdo.setAmount(tradingAmount);
			pdo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			pdo.setDesc("合伙人渔具店售票认领开发奖金");
			pdo.setBid(shopId);
			pdo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, u.getId().toString()));
			pdo.setPartnerId(signShop.getPartnerId());
			pdo.setType(0);
			pdo.setUid(u.getId());
			partnerDevlopOrdersMapper.insertPojo(pdo);
			//添加漂币
			accountService.addGoldCoin(u.getId(), tradingAmount);
			//添加账单
			accountBillService.addPojo(u.getId(), pdo.getOrderId(), 1, 18, 2,ArithUtil.div(tradingAmount, 100, 2) ,fishShop.getName()+"正常交易奖励");
			Map<String,String> extras = new HashMap<String, String>();
			extras.put("siteName", fishShop.getName());
			extras.put("reward", tradingAmount + "");
			//合伙人消息保存
			systemMsgService.saveMsg("开发奖", null, fishShop.getName() + "售票认领审核通过",u.getId().intValue() , null);
			//合伙人消息推送
			jPushService.sendPush(u.getId().intValue(), "C", "开发奖售票认领审核通过", fishShop.getName() + "审核通过", extras);
		}
		return Status.success;
	}
	/**
	 * cp拉去渔具店认领奖励配置
	 */
	@Override
	@Transactional
	public PartnerShopRewardReview findShopRewardConfig(Long shopId) {
		PartnerShopRewardReview partnerShopRewardReview= partnerShopRewardReviewMapper.selectByShopId(shopId.longValue());
		return partnerShopRewardReview;
	}
	/**
	 * cp拉去钓场认领奖励配置
	 */
	@Override
	public PartnerSiteRewardReview findSiteRewardConfig(Integer siteId) {
		return partnerSiteRewardReviewMapper.selectBysiteId(siteId);
	}
	/**
	 * cp添加/修改合伙人奖励配置
	 */
	@Override
	@Transactional
	public int insertPartnerShopReward(Long bid,Integer amount,Integer style){
			PartnerShopRewardReview partnerShopRewardReview = partnerShopRewardReviewMapper.selectByShopId(bid);
			if(partnerShopRewardReview==null){
				PartnerShopRewardReview pojos = new PartnerShopRewardReview();
				if(style==0){
					pojos.setShelvesAmount(amount);	
					pojos.setShelvesStatus(0);
				}else{
					pojos.setTradingAmount(amount);
					pojos.setTradingStatus(0);
				}
				pojos.setShopId(bid);
				partnerShopRewardReviewMapper.insert(pojos);
			}else{
				if(style==0){
					partnerShopRewardReview.setShelvesAmount(amount);
					partnerShopRewardReview.setShelvesStatus(0);
				}else {
					partnerShopRewardReview.setTradingAmount(amount);
					partnerShopRewardReview.setTradingStatus(0);
				}
				partnerShopRewardReview.setShopId(bid);
				partnerShopRewardReviewMapper.updateByShopId(partnerShopRewardReview);
			}
			return Status.success;
		}
/**
 * cp添加/修改合伙人奖励配置
 */
	@Override
	@Transactional
	public int insertPartnerSiteReward(Integer bid,Integer amount,Integer style){
		PartnerSiteRewardReview partnerSiteRewardReview = partnerSiteRewardReviewMapper.selectBysiteId(bid.intValue());
		if(partnerSiteRewardReview==null){
			PartnerSiteRewardReview pojo = new PartnerSiteRewardReview();				
			if(style==0){
				pojo.setShelvesAmount(amount);
				pojo.setShelvesStatus(0);
			}else{
				pojo.setTradingAmount(amount);
				pojo.setTradingStatus(0);
			}
			pojo.setSiteId(bid);
			partnerSiteRewardReviewMapper.insert(pojo);
		}else{
			if(style==0){
				partnerSiteRewardReview.setShelvesAmount(amount);
				partnerSiteRewardReview.setShelvesStatus(0);
			}else{
				partnerSiteRewardReview.setTradingAmount(amount);
				partnerSiteRewardReview.setTradingStatus(0);
			}
			partnerSiteRewardReview.setSiteId(bid.intValue());
			partnerSiteRewardReviewMapper.updateBysiteId(partnerSiteRewardReview);
		}
		return Status.success;
	}
	@Override
	public int updateSiteIdSign(Integer siteId,Integer status) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		FishSite site = fishSiteService.selectById(siteId);
		FishSiteSign siteSign = fishSiteSignMapper.selectOne(siteId);
		if( site.getUid() == null ){
			return Status.fish_site_not_auth;
		}
		if(siteSign.getPartnerId() == null){
			return Status.accept_partner_not_exists;
		}
//		if(site.getCategory() == null){
//			return Status.NOT_CATEGORY;
//		}
		if(status==1){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("signStatus", ApiConstant.SignStatus.TICK_CLAIMED);
			map.put("signTime", t);
			fishSiteSignMapper.update(map);
			
		}else{
			fishSiteSignMapper.delete(siteId);
		}
		return Status.success;
	}
	
	@Override
	public int updateSiteIdSignTrad(Integer siteId,Integer status) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		FishSite site = fishSiteService.selectById(siteId);
		FishSiteSign siteSign = fishSiteSignMapper.selectOne(siteId); 
		if( site.getUid() == null ){
			return Status.fish_site_not_auth;
		}
		if( siteSign!=null && siteSign.getPartnerId() == null){
			return Status.accept_partner_not_exists;
		}
		if(site.getCategory() == null){
			return Status.NOT_CATEGORY;
		}
		if(status==1){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("siteId", siteId);
//			map.put("signStatus", ApiConstant.SignStatus.SIGN);
//			map.put("signTime", t);
//			map.put("partnerId", siteSign.getPartnerId());
//			fishSiteMapper.updateByPartnerIdAndSiteId(map);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("signStatus", ApiConstant.SignStatus.SIGN);
			map.put("signTime", t);
			fishSiteSignMapper.update(map);
			
		}else{
			PartnerOver partnerOver = new PartnerOver();
			partnerOver.setBid(siteId.longValue());
			partnerOver.setOverTime(t);
			partnerOver.setPartnerId(siteSign.getPartnerId().longValue());
			partnerOver.setType(1);
			partnerOverMapper.insert(partnerOver);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("signStatus", ApiConstant.SignStatus.TICK_CLAIM);
			map.put("signTime", t);
			fishSiteSignMapper.update(map);
		}
		return Status.success;
	}
	@Override
	public int updateShopIdSign(Long shopId, Integer status) {
		FishShop fishShop = fishShopMapper.selectFishShopByShopId(shopId);
		FishShopSign fishShopSign = fishShopSignMapper.selectOne(shopId);
//		if(fishShop.getUid() == null){
//			return Status.FISH_SHOP_NOT_NAME_PASS;
//		}
		if(fishShopSign !=null && fishShopSign.getPartnerId() == null){
			return Status.FISH_SHOP_STATUS_NOT_EXISTS;
		}
//		if(fishShop.getAuthStatus() ==2){
//			 return Status.FISH_SHOP_SIGN_ALREADY;
//		}
		if(fishShop !=null && fishShop.getScale()==null){
			return Status.FISH_SHOP_TYPE_NOT_EXISTS;
		}
		if(status == 1){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("signTime", ExDateUtils.getDate());
			map.put("signStatus", 2);
			fishShopSignMapper.update(map);
		}else{
			fishShopSignMapper.delete(shopId);
		}
		return Status.success;
	}
	
	@Override
	public int updateShopIdSignTrand(Long shopId, Integer status) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		FishShop fishShop = fishShopMapper.selectFishShopByShopId(shopId);
		FishShopSign fishShopSign = fishShopSignMapper.selectOne(shopId);
//		if(fishShop!=null && fishShop.getUid() == null){
//			return Status.FISH_SHOP_NOT_NAME_PASS;
//		}
		if(fishShopSign !=null && fishShopSign.getPartnerId() == null){
			return Status.FISH_SHOP_STATUS_NOT_EXISTS;
		}
//		if(fishShop.getAuthStatus() ==2 || fishShop.getSignUid() !=null){
//			 return Status.FISH_SHOP_SIGN_ALREADY;
//		}
		if(fishShop!=null && fishShop.getScale()==null){
			return Status.FISH_SHOP_TYPE_NOT_EXISTS;
		}
		if(status == 1){
//			fishShop.setSignUid(fishShopSign.getPartnerId());
//			fishShop.setAuthStatus(2);
//			fishShop.setAuthTimeSign(t.toString());
//			fishShopMapper.updateFishShopUidAuthStatus(fishShop);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("signTime", ExDateUtils.getDate());
			map.put("signStatus", 3);
			fishShopSignMapper.update(map);
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("signTime", ExDateUtils.getDate());
			map.put("signStatus", 1);
			fishShopSignMapper.updatePartner(map);
			PartnerOver partnerOver = new PartnerOver();
			partnerOver.setBid(shopId);
			partnerOver.setOverTime(t);
			partnerOver.setPartnerId(fishShopSign.getPartnerId().longValue());
			partnerOver.setType(0);
			partnerOverMapper.insert(partnerOver);
		}
		
		return Status.success;
	}
	
}

