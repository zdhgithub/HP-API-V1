/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.PartnerCouponRecordMapper;
import cn.heipiao.api.mapper.PartnerGivingOutCouponCountByMonthMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PartnerUserCouponMapper;
import cn.heipiao.api.mapper.RecommendOrdersMapper;
import cn.heipiao.api.mapper.UserRecommendMapper;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerCouponRecord;
import cn.heipiao.api.pojo.PartnerGivingOutCouponCountByMonth;
import cn.heipiao.api.pojo.PartnerUserCoupon;
import cn.heipiao.api.pojo.RecommendOrders;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserRecommend;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.CouponUserService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.ShopCouponsService;
import cn.heipiao.api.service.SiteCouponsService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.service.WebService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class WebServiceImpl implements WebService {

	@Resource
	private PartnerCouponRecordMapper partnerCouponRecordMapper;
	
	@Resource
	private PartnerUserCouponMapper partnerUserCouponMapper;

	@Resource
	private UserRecommendMapper  userRecommendMapper;
	
	@Resource
	private RecommendOrdersMapper recommendOrdersMapper;
	
	@Resource
	private UserOpService userOpService;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private CouponUserService couponUserService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private PartnerGivingOutCouponCountByMonthMapper partnerGivingOutCouponCountByMonthMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private UserRewardService userRewardService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private JPushService jPushService;

	@Resource
	private SiteCouponsService siteCouponsService;

	@Resource
	private ShopCouponsService shopCouponsService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#giveOutCoupon(cn.heipiao.api.pojo.PartnerCouponRecord)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	@Deprecated
	public int giveOutCoupon(Long uid, String phone) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerCouponRecord pcr = new PartnerCouponRecord();
		pcr.setPhone(phone);
		//判断合伙人
		Partner partner = partnerMapper.selectByUidAsLock(uid);
		if(partner == null){
			return Status.partner_not_exists;
		}
		if(partner.getGivingOutCouponSum() == null || partner.getGivingOutCouponSum().intValue() <= 0){
			return Status.partner_giving_out_not_enough;
		}
		//设置为合伙人id
		pcr.setPartnerId(partner.getpId().intValue());
		User us = userOpService.queryUserByPhone(pcr.getPhone());
		PartnerUserCoupon puc = null;
		//判断用户
		if(us == null){
			us = new User();
			us.setPhone(pcr.getPhone());
			us.setStatus(ApiConstant.UserConstant.USER_STATUS_IMPLICIT);
			us.setStat(4);
			userOpService.saveImplicit(us);
			//发注册券
			couponUserService.presentUser(us.getId());
		} 
		
		//统计本月领券人的次数
		PartnerGivingOutCouponCountByMonth pgo= partnerGivingOutCouponCountByMonthMapper.selectByUidAndPartnerIdAsLock(us.getId(), partner.getpId().intValue());
		int month = Integer.parseInt(ExDateUtils.getDateFormat(t,"yyyyMM"));
		if(pgo == null){
			pgo = new PartnerGivingOutCouponCountByMonth();
			pgo.setCount(1);
			pgo.setCreateTime(t);
			pgo.setMonth(month);
			pgo.setPartnerId(partner.getpId().intValue());
			pgo.setUid(us.getId());
			partnerGivingOutCouponCountByMonthMapper.insertPojo(pgo);
		}else{
			if(pgo.getMonth() ==  month){
				if(pgo.getCount() >= 5){
					return Status.partner_giving_coupon_uid_limited;
				}
				pgo.setCount(pgo.getCount() + 1);
			} else {
				pgo.setMonth(month);
				pgo.setCount(1);
			}
			partnerGivingOutCouponCountByMonthMapper.updatePojo(pgo);
		}
		
		
		
		
		puc = partnerUserCouponMapper.selectByUidAsLock(us.getId());
		if(puc == null){
			puc = new PartnerUserCoupon();
			puc.setAmount(1);
			puc.setUid(us.getId());
			puc.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			partnerUserCouponMapper.insertPojo(puc);
		}else{
			puc.setAmount(puc.getAmount() + 1);
			partnerUserCouponMapper.updateAmount(puc);
		}
		
//		Coupon c = null;
//		if(puc.getAmount() == 1){
//			c = couponMapper.selectById(8);
//		}else if(puc.getAmount() == 2){
//			c = couponMapper.selectById(9);
//		}else{
//			c = couponMapper.selectById(10);
//		}
//		//优惠券创建
//		CouponUser cu = createCoupon(c,us);
		
		Coupons c = null;
		if(puc.getAmount() == 1){
			c = createCoupon(us.getId(),10,1);
		}else if(puc.getAmount() == 2){
			c = createCoupon(us.getId(),5,1);
		}else{
			c = createCoupon(us.getId(),partner.getGivingOutCouponFee(),1);
		}
		
		
		
		pcr.setCouponId(c.getCid());
		pcr.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		pcr.setUid(us.getId());
		partnerCouponRecordMapper.insertPojo(pcr);
		
		//更新数量
		partner.setGivingOutCouponCount(partner.getGivingOutCouponCount() + 1);
		partner.setGivingOutCouponSum(partner.getGivingOutCouponSum() - 1 < 0 ? 0 : partner.getGivingOutCouponSum() - 1);
		partnerMapper.updateCouponCount(partner);
		
		return Status.success;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int giveOutCouponNew(Long uid, String phone,int category) throws Exception {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		PartnerCouponRecord pcr = new PartnerCouponRecord();
		pcr.setPhone(phone);
		//判断合伙人
		Partner partner = partnerMapper.selectByUidAsLock(uid);
		if(partner == null){
			return Status.partner_not_exists;
		}
		if(partner.getGivingOutCouponSum() == null || partner.getGivingOutCouponSum().intValue() <= 0){
			return Status.partner_giving_out_not_enough;
		}
		//设置为合伙人id
		pcr.setPartnerId(partner.getpId().intValue());
		User us = userOpService.queryUserByPhone(pcr.getPhone());
		PartnerUserCoupon puc = null;
		//判断用户
		boolean isNewUser = false;
		if(us == null){
			isNewUser = true;
			us = new User();
			us.setPhone(pcr.getPhone());
			us.setStatus(ApiConstant.UserConstant.USER_STATUS_IMPLICIT);
			us.setStat(4);
			userOpService.saveImplicit(us);
			//发注册券
			couponUserService.presentUser(us.getId());
			
		} 
		
		//统计本月领券人的次数
		PartnerGivingOutCouponCountByMonth pgo= partnerGivingOutCouponCountByMonthMapper.selectByUidAndPartnerIdAsLock(us.getId(), partner.getpId().intValue());
//		int month = Integer.parseInt(ExDateUtils.getDateFormat(t,"yyyyMM"));
		if(pgo == null){
			pgo = new PartnerGivingOutCouponCountByMonth();
			pgo.setCount(1);
			pgo.setCreateTime(t);
			pgo.setMonth(0);
			pgo.setPartnerId(partner.getpId().intValue());
			pgo.setUid(us.getId());
			partnerGivingOutCouponCountByMonthMapper.insertPojo(pgo);
		}else{
//			if(pgo.getMonth() ==  month){
//				if(pgo.getCount() >= 5){
//					return Status.partner_giving_coupon_uid_limited;
//				}
//				pgo.setCount(pgo.getCount() + 1);
//			} else {
//				pgo.setMonth(month);
//				pgo.setCount(1);
//			}
			if(pgo.getCount() >= 2){
				return Status.partner_giving_coupon_uid_limited;
			}
			
			pgo.setCount(pgo.getCount() + 1);
			partnerGivingOutCouponCountByMonthMapper.updatePojo(pgo);
		}
		
		
		
		
		puc = partnerUserCouponMapper.selectByUidAsLock(us.getId());
		if(puc == null){
			puc = new PartnerUserCoupon();
			puc.setAmount(1);
			puc.setUid(us.getId());
			puc.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			partnerUserCouponMapper.insertPojo(puc);
		}else{
			puc.setAmount(puc.getAmount() + 1);
			partnerUserCouponMapper.updateAmount(puc);
		}
		
		//老用户5元
		int couponFee = 5;
		//判断是否为新用户
		if(isNewUser){
			//新用户10元
			couponFee = 10;
		}
			
		Coupons c = createCoupon(us.getId(),couponFee,category);
		
		
		pcr.setCouponId(c.getCid());
		pcr.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		pcr.setUid(us.getId());
		partnerCouponRecordMapper.insertPojo(pcr);
		
		//更新数量
		partner.setGivingOutCouponCount(partner.getGivingOutCouponCount() + 1);
		partner.setGivingOutCouponSum(partner.getGivingOutCouponSum() - 1 < 0 ? 0 : partner.getGivingOutCouponSum() - 1);
		partnerMapper.updateCouponCount(partner);
		
		//消息
		systemMsgService.saveMsg(category == 1 ? "钓场券" : "店铺券", null, category == 1 ? "合伙人奖励的钓场券":"合伙人奖励的店铺券", us.getId().intValue(), null);
		
		return Status.success;
	}
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#userRecommend(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int userRecommend(Long uid, String phone) throws Exception {
		User u = userOpService.queryUserById(uid);
		if(u == null || !u.getStatus().equals("1")){
			return Status.USER_STATUS_DISABLE;
		}
		User us = userOpService.queryUserByPhone(phone);
		if(us == null){
			us = new User();
			us.setPhone(phone);
			us.setStatus(ApiConstant.UserConstant.USER_STATUS_IMPLICIT);
			us.setStat(1);
			userOpService.saveImplicit(us);
		
			 //优惠券创建 
			//推荐登录获得20元券
//			createCoupon(us.getId(),20);
			
			couponUserService.presentUser(us.getId());
			
			UserRecommend pojo = new UserRecommend();
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			pojo.setRuid(u.getId());
			pojo.setUid(us.getId());
			userRecommendMapper.insertPojo(pojo);
			
			return Status.success;
		}
		return Status.USER_IS_EXIST;
	}


	/**
	 * 商家优惠券分享后，页面获取商家优惠券功能
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int couponsShareGet(String phone, Long business, Long coupons, boolean isSiteId) {
		User us = userOpService.queryUserByPhone(phone);
		int result = -1;
		if(us == null){
			us = new User();
			us.setPhone(phone);
			us.setStatus(ApiConstant.UserConstant.USER_STATUS_IMPLICIT);
			us.setStat(5);
			userOpService.saveImplicit(us);
		}
		us = userOpService.queryUserByPhone(phone);
		try{
			if(us != null && us.getId() != null){
				if(isSiteId){
					result = siteCouponsService.getSiteCoupon(us.getId(), business.intValue(), coupons);
				}else{
					result = shopCouponsService.getShopCoupon(us.getId(), business, coupons);
				}
			}
		}catch(Exception e){
			return Status.COUPONS_SHARE_GET_FAIL;
		}
		if(result == Status.success){
			return Status.COUPONS_SHARE_GET_SUCCESS;
		}else{
			return Status.COUPONS_SHARE_GET_FAIL;
		}
	}
	
	/**
	 * 
	 * @param uid 用户id
	 * @param couponFee 领券金额:元
	 * @param category 1:钓场,2:店铺
	 * @return
	 */
	private Coupons createCoupon(Long uid,Integer couponFee, int category){
//		CouponUser cu = new CouponUser();
//		cu.setCouponId(c.getId());
//		cu.setGetTime(ExDateUtils.getDate());
//		cu.setIndateTime(new Date(ExDateUtils.getCalendar().getTimeInMillis() + 30L * 24 * 60 * 60 * 1000));
//		cu.setStatus(0);
//		cu.setUserId(u.getId().intValue());
//		couponUserMapper.insert(cu);
		
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		Coupons c = new Coupons();
		c.setUid(uid);
		c.setCategory(category == 1 ? 1 : 4);
		c.setCouponFee(couponFee);
		//目前硬编码,以后需要配置
		c.setUseRule(50);
		c.setCouponName("合伙人奖励券");
		//过期时间 30天
		c.setDeadline(new Timestamp(t.getTime() + 30L * 24 * 60 * 60 * 1000));
		c.setDesc(category == 1 ? "合伙人钓场券" : "合伙人店铺券");
		c.setGetTime(t);
		c.setStatus(0);
		couponsMapper.insertPojo(c);
		return c;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectByPartnerId(java.lang.Integer)
	 */
	@Override
	public List<PartnerCouponRecord> selectByPartnerId(Long uid) throws Exception {
		Partner partner = partnerMapper.getPartnerById(uid.intValue());
		return partner == null ? new ArrayList<PartnerCouponRecord>() : partnerCouponRecordMapper.selectByPartnerId(partner.getpId().intValue());
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectByUid(java.lang.Long)
	 */
	@Override
	public List<UserRecommend> selectListByUid(Long uid) {
		return userRecommendMapper.selectListByUid(uid);
	}

	@Override
	public void addRewardToRuid(Long uid,Integer amount,String desc,Integer type) throws Exception{
		UserRecommend ur = userRecommendMapper.selectByUid(uid);
		accountService.addGoldCoin(ur.getRuid(), amount);
		//创建订单 
		RecommendOrders ro = new RecommendOrders();
		ro.setAmount(amount);
		ro.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		ro.setDesc(desc);
		ro.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10,uid.toString()));
		ro.setRuid(ur.getRuid());
		ro.setType(type);
		ro.setUid(uid);
		recommendOrdersMapper.insertPojo(ro);
		
		//添加账单
		accountBillService.addPojo(ro.getRuid(), ro.getOrderId(), 1, 8, 2, ArithUtil.div((double)ro.getAmount(),100,2), type == 1 ? "奖励-好友登录":"奖励-好友消费");
		
		//消息
		systemMsgService.saveMsg( "好开心！我有漂币啦", null, type == 1 ? "您邀请的好友已注册，您获得了" + amount + "漂币，记得下次使用哦":"您邀请的好友已购票，您获得了" + amount + "漂币，记得下次使用哦 " + ro.getAmount() + "漂币", ur.getRuid().intValue(), null);
		
		//推送消息
		jPushService.sendPush(ur.getRuid().intValue(), "C", type == 1 ? "登录奖励" : "消费奖励", "推荐用户app奖励 " + ro.getAmount() + "漂币", null);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectUserByUid(java.lang.Long)
	 */
	@Override
	public User selectUserByUid(Long uid) {
		return userRecommendMapper.getUser(uid);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectUserCount(java.lang.Long)
	 */
	@Override
	public Integer selectUserCount(Long uid) {
		return userRecommendMapper.selectUserCount(uid);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectPartnerCount(java.lang.Long)
	 */
	@Override
	public Map<String, Object> selectPartnerCount(Long uid) throws Exception {
		Partner partner = partnerMapper.getPartnerById(uid.intValue());
		Map<String,Object> map = new HashMap<String, Object>();
		Integer pc = null;
		Integer used = null;
		if(partner != null){
			pc = partnerCouponRecordMapper.selectCount(partner.getpId().intValue());
			used = partnerCouponRecordMapper.selectCountByUsed(partner.getpId().intValue()); 
		}
		map.put("count", pc == null ? 0 : pc);
		map.put("used", used == null ? 0 : used);
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WebService#selectPartnerPrerogativeCount(java.lang.Long)
	 */
	@Override
	public Map<String, Object> selectPartnerPrerogativeCount(Long uid) throws Exception {
		Partner parnter = partnerMapper.getPartnerById(uid.intValue());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("count", parnter == null ? 0 : parnter.getGivingOutCouponSum());
		return map;
	}
	
}
