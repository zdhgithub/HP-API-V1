package cn.heipiao.api.service.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishShopSignMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.PartnerCityRateConfigMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PartnerRewardConfigMapper;
import cn.heipiao.api.mapper.PartnerShopRateConfigMapper;
import cn.heipiao.api.mapper.PartnerSiteRateConfigMapper;
import cn.heipiao.api.mapper.RegionMapper;
import cn.heipiao.api.mapper.SysRegionMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopSign;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerCityRateConfig;
import cn.heipiao.api.pojo.PartnerEarningRecord;
import cn.heipiao.api.pojo.PartnerRewardConfig;
import cn.heipiao.api.pojo.PartnerShopRateConfig;
import cn.heipiao.api.pojo.PartnerSiteRateConfig;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.PartnerEarningOrdersService;
import cn.heipiao.api.service.PartnerEarningRecordService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.PartnerShopEarningOrdersService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月3日
 */
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

	@Resource
	private PartnerMapper partnerMapper;

	@Resource
	private FishSiteMapper fishSiteMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private SysRegionMapper sysRegionMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private PartnerEarningOrdersService partnerEarningOrdersService;
	
	@Resource
	private PartnerShopEarningOrdersService partnerShopEarningOrdersService;
	
	@Resource
	private PartnerEarningRecordService partnerEarningRecordService;

	@Resource
	private FishShopSignMapper<FishShopSign> fishShopSignMapper;
	
	@Resource
	private PartnerCityRateConfigMapper partnerCityRateConfigMapper;

	@Resource
	private PartnerShopRateConfigMapper partnerShopRateConfigMapper;
	
	@Resource
	private PartnerSiteRateConfigMapper partnerSiteRateConfigMapper;

	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private JPushService jPushService;
	@Resource
	private PartnerRewardConfigMapper partnerRewardConfigMapper;
	@Resource
	private RegionMapper regionMapper;
	
	/*
	 * @Override public RespMsg<?> queryPartnerById(String username) throws
	 * Exception { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public RespMsg<?> queryPartnerByPhoneOrName(Map<String,Object>
	 * map) throws Exception { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public List<Partner> queryPartnerByRegion(Map<String, Object> map)
			throws Exception {
		return partnerMapper.selectListByOneCondition(map);
	}

	/*
	 * @Override public RespMsg<?> queryPartners(Map<String, Object> params)
	 * throws Exception { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public RespMsg<?> queryPartnerSite(Map<String, Object> params)
	 * throws Exception { // TODO Auto-generated method stub return null; }
	 */

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int savePartner(Partner partner) throws Exception {
		Partner pt = partnerMapper.getByPartnerId(partner.getId().intValue());
		if (pt != null) {
			partnerMapper.updateIsExists(pt.getpId().intValue(), true);
		}else{
			partnerMapper.savePartner(partner);
		}
		return pt == null ? partner.getpId().intValue() : pt.getpId().intValue();
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int deletePartner(Integer partnerId) throws Exception {
		Partner p = partnerMapper.selectByPartnerId(partnerId);
		if(p == null){
			return Status.partner_not_exists;
		}
		fishShopMapper.updatePartnerIdIsNull(partnerId);
		fishSiteMapper.updatePartnerIdIsNull(partnerId);
		fishShopSignMapper.deletePartnerId(partnerId);
		
		partnerMapper.updateIsExists(partnerId,false);
		return Status.success;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void distributePartnerSite(FishSite pojo) throws Exception {
		pojo.setSignStatus(ApiConstant.SignStatus.SIGN);
		pojo.setSignTime(ExDateUtils.getDate());
		fishSiteMapper.updatePojo(pojo);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void modifyPartner(Partner partner) throws Exception {
		partnerMapper.updatePartner(partner);

		if (this.hasValue(partner, User.class, "id", "serialVersionUID"))
			userMapper.updateById(partner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.PartnerService#queryPartnerByPhoneOrNickname(java
	 * .lang.String)
	 */
	@Override
	public List<Partner> queryPartnerByPhoneOrNickname(Map<String, Object> map) {
		return partnerMapper.selectListByOneCondition(map);
	}

	@Override
	public Partner queryPartnerById(Integer userId) throws Exception {
		Partner partner = partnerMapper.getPartnerById(userId);
		return partner;
	}

	/**
	 * 判断对象是否含有值
	 * 
	 * @param clazz
	 *            目标类型
	 * @param filterFields
	 *            过滤属性
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private <U> boolean hasValue(U obj, Class<U> clazz, String... _filterFields)
			throws IllegalArgumentException, IllegalAccessException {
		boolean isEmpty = _filterFields == null || _filterFields.length == 0;
		for (Field field : clazz.getDeclaredFields()) {
			if (!isEmpty
					&& Arrays.binarySearch(_filterFields, field.getName()) > -1) { // 过滤的属性值
				continue;
			}
			field.setAccessible(true);
			if (field.get(obj) != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Region> getPaertnerRegion() {
		return this.partnerMapper.getPartnerRegion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.PartnerService#selectByUid(java.lang.Long)
	 */
	@Override
	public Partner selectByUid(Long uid) {
		return partnerMapper.selectByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.PartnerService#updateCouponFeeAndSum(cn.heipiao
	 * .api.pojo.Partner)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updateCouponFeeAndSum(Partner p) {
		partnerMapper.updateCouponFeeAndSum(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.PartnerService#selectFishSiteByPartner(java.util
	 * .Map)
	 */
	@Override
	public List<FishSite> selectFishSiteByPartner(Map<String, Object> map) {
		return fishSiteMapper.selectFishSiteByPartner(map);
	}

	@Override
	public List<Region> getOpenRegion() {
		return sysRegionMapper.selectOpenList();
	}

	@Override
	@Transactional(readOnly = false)
	public Integer signShop(Long shopId, Long uid) throws Exception {
		Partner partner = this.partnerMapper.selectByUid(uid);
		if(partner==null) {
			return Status.partner_not_exists;
		}
//		if (this.fishShopSignMapper.selectMax(partner.getpId().intValue()) >= 3) {
//			return Status.FISH_SHOP_SIGN_BEYOND_LIMITS;
//		}
		if (this.fishShopSignMapper.selectOne(shopId) == null) {
			FishShopSign fs = new FishShopSign();
			fs.setPartnerId(partner.getpId().intValue());
			fs.setShopId(shopId);
			fs.setSignTime(ExDateUtils.getDate());
			fs.setSignStatus(0);
			fishShopSignMapper.insert(fs);
		}
		return Status.success;
	}
	@Transactional(readOnly = false)
	@Override	
	public Integer ticketSignShop(Long shopId, Long uid) throws Exception {
		Partner partner = this.partnerMapper.selectByUid(uid);
		if(partner==null) {
			return Status.partner_not_exists;
		}
//		if (this.fishShopSignMapper.selectMax(partner.getpId().intValue()) >= 3) {
//			return Status.FISH_SHOP_SIGN_BEYOND_LIMITS;
//		}
		Map<String,Object> map = new HashMap<String, Object>();
			map.put("partnerId",partner.getpId().intValue());
			map.put("shopId", shopId);
			map.put("signTime", ExDateUtils.getDate());
			map.put("signStatus", 2);
			fishShopSignMapper.update(map);
		return Status.success;
	}
	@Override
	public List<Region> getOpenRegionSp() {
		return this.sysRegionMapper.selectOpenListForShop();
	}

	/**
	 * 2.3去掉奖励 
	 * @param shopId
	 * @param fee
	 * @throws Exception 
	 */
	@Override
	public void shopEarn(ShopTradeOrders sto,int fee) throws Exception {
		FishShop fs = fishShopMapper.selectFishShopByShopId(sto.getShopId());
		if(fs == null || fs.getSignUid() == null || fs.getSignUid() <= 0){
			return ;
		}
		Partner p = partnerMapper.selectByPartnerId(fs.getSignUid());
		if(p == null){
			return;
		}
		PartnerShopRateConfig psa = partnerShopRateConfigMapper.selectById(p.getpId().intValue(), fs.getId());
		double rate = 0;
		if(psa == null){
			PartnerCityRateConfig pcr = partnerCityRateConfigMapper.selectById(fs.getCityId());
			if(pcr != null){
				rate = pcr.getShopRate();
			}
		}else {
			rate = psa.getShopRate();
		}
		
		if(rate <= 0){
			return;
		}
		
		int addGoldCoin = (int) (fee * 0.05 * rate);
		if(addGoldCoin <= 0){
			return;
		}
		//向合伙人添加漂币
		accountService.addGoldCoin(p.getId(), addGoldCoin);
		
		//创建订单 
		partnerShopEarningOrdersService.addPojo(p.getId(), sto.getOrderId(), p.getpId().intValue(), fs.getId(), addGoldCoin);
		
		
		//消息
		systemMsgService.saveMsg("店铺收益", null, "店铺核票收益获得" + addGoldCoin + "漂币", p.getId().intValue(), null);
		//推送消息
		jPushService.sendPush(p.getId().intValue(), "C", "店铺收益", "店铺核票收益获得" + addGoldCoin + "漂币", null);
		
	}

	/**
	 * 2.3去掉奖励 
	 * @param siteId
	 * @param fee
	 * @throws Exception 
	 */
	@Override
	public void siteEarn(Partner p,FishSite fs,UserTickets ut, Integer fee) throws Exception {
		//收益为漂币
		PartnerSiteRateConfig psa = partnerSiteRateConfigMapper.selectById(p.getpId().intValue(), fs.getFishSiteId());
		double rate = 0;
		if(psa == null){
			PartnerCityRateConfig pcr = partnerCityRateConfigMapper.selectById(fs.getCityId());
			if(pcr != null){
				rate = pcr.getSiteRate();
			}
		}else {
			rate = psa.getSiteRate();
		}
		
		if(rate <= 0){
			return;
		}
		
		int addGoldCoin = (int) (fee * 0.05 * rate);
		if(addGoldCoin <= 0){
			return;
		}
		//向合伙人添加漂币
		accountService.addGoldCoin(p.getId(), addGoldCoin);
		
		//创建订单 
		partnerEarningOrdersService.addPojo(p.getId(), ut.getTid(), p.getpId().intValue(), fs.getFishSiteId(), addGoldCoin);
		
		//添加收益记录
		PartnerEarningRecord per = new PartnerEarningRecord();
		per.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		per.setDesc("钓场核票收入");
		per.setFishSiteId(fs.getFishSiteId());
		per.setGoldFee(addGoldCoin);
		per.setUid(p.getId());
		partnerEarningRecordService.addPojo(per);
		
		//消息
		systemMsgService.saveMsg("钓场收益", null, "钓场核票收益获得" + addGoldCoin + "漂币", p.getId().intValue(), null);
		//推送消息
		jPushService.sendPush(p.getId().intValue(), "C", "钓场收益", "钓场核票收益获得" + addGoldCoin + "漂币", null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerService#distributePartnerShop(java.lang.Integer, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int removePartnerShop(Integer partnerId, Long shopId) {
		Partner p = partnerMapper.selectByPartnerId(partnerId);
		if(p == null){
			return Status.partner_not_exists;
		}
		FishShop fs = fishShopMapper.selectFishShopByShopId(shopId);
		if(fs == null){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if(fs.getSignUid() == null || fs.getSignUid().intValue() != partnerId.intValue()){
			return Status.value_is_null_or_error;
		}
		fishShopMapper.updatePartnerIdFishShopById(fs.getId());
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerService#removePartnerSite(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int removePartnerSite(Integer partnerId, Integer siteId) {
		Partner p = partnerMapper.selectByPartnerId(partnerId);
		if(p == null){
			return Status.partner_not_exists;
		}
		FishSite fs = fishSiteMapper.selectById(siteId);
		if(fs == null){
			return Status.fish_site_not_exists;
		}
		if(fs.getPartnerId() == null || fs.getPartnerId().intValue() != partnerId.intValue()){
			return Status.value_is_null_or_error;
		}
		fishSiteMapper.updatePartnerIdBySiteId(siteId);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerService#selectPartnerByUid(java.lang.Long)
	 */
	@Override
	public Partner selectPartnerByUid(Long uid) {
		return partnerMapper.selectPartnerByUid(uid);
	}

	@Override
	public int insertConfig(PartnerRewardConfig partnerRewardConfig) {
		partnerRewardConfigMapper.insert(partnerRewardConfig);
		return Status.success;
	}

	@Override
	public int deleteConfig(Integer id) {
		partnerRewardConfigMapper.deleteById(id);
		return Status.success;
	}

	@Override
	public int updateConfig(PartnerRewardConfig partnerRewardConfig) {
		partnerRewardConfigMapper.updateById(partnerRewardConfig);
		return Status.success;
	}

	@Override
	public List<PartnerRewardConfig> selectConfig(Map<String, Object> map) {
		
		return partnerRewardConfigMapper.selectList(map);
	}

	@Override
	public PartnerRewardConfig selectOne(Map<String, Object> map) {
		return partnerRewardConfigMapper.selectByRegion(map);
	}

	@Override
	public List<Region> getAllRegion() {
		
		return regionMapper.allRegion();
	}	
}
