package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.CouponMapper;
import cn.heipiao.api.mapper.CouponUser10Mapper;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.DictMapper;
import cn.heipiao.api.pojo.CouponUser;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.service.CouponUserService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description user 持有的券的业务实现
 * @date 2016年7月20日
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {
//	@Resource
//	private CouponUserMapper couponUserMapper;
	
	@Resource
	private CouponUser10Mapper couponUser10Mapper;

	@Resource
	private CouponMapper couponMapper;
	
	@Resource
	private CouponsMapper couponsMapper;

	@Resource
	private DictMapper dictMapper;
	
//	private static final Logger logger = LoggerFactory
//			.getLogger(CouponUserServiceImpl.class);

	@Override
	public List<CouponUser> getListByUser(Integer userId, Integer status,
			Integer start, Integer size) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", userId);
		param.put("status", status);
		param.put("start", start);
		param.put("size", size);
//		List<CouponUser> list = couponUserMapper.selectList(param);
		List<CouponUser> list = couponUser10Mapper.selectByUid(param);
		return list;
	}
	
	@Override
	public List<Coupons> getNewListByUser(Integer userId, Integer status,
			Integer start, Integer size) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uid", userId);
		param.put("status", status);
		param.put("start", start);
		param.put("size", size);
//		List<CouponUser> list = couponUserMapper.selectList(param);
//		List<CouponUser> list = couponUser10Mapper.selectByUid(param);
		List<Coupons> list = couponsMapper.selectByUid(param);
		//将所有未读的优惠券改为已读
		couponsMapper.updateIsRead();
		return list;
	}

//	@Override
//	public List<CouponUser> getListByCoupon(Map<String, Object> param)
//			throws Exception {
//		List<CouponUser> list = couponUserMapper.selectList(param);
//		return list;
//	}

//	@Override
//	public int countSendedCoupons() throws Exception {
//		int sum = couponUserMapper.countSendedCoupons();
//		return sum;
//	}

//	@Override
//	public double countSendedCouponMoney() throws Exception {
//		double sum = couponUserMapper.countSendedCouponMoney();
//		return sum;
//	}

	@Override
	public int countUnusedCoupons(Integer userId, Integer status)
			throws Exception {
//		int num = couponUserMapper.countUnusedCoupons(userId, status);
		int num = couponUser10Mapper.countUnusedCoupons(userId, status);
		return num;
	}

//	@Override
//	@Transactional
//	@Deprecated
//	public boolean presentUser(int userId) {
//		Coupon coupon = couponMapper.selectById(11);
//
//		if (coupon == null) {
//			return true;
//		}
//
//			CouponUser couponUser = new CouponUser();
//			couponUser.setUserId(userId);
//			couponUser.setGetTime(ExDateUtils.getDate());
//			couponUser.setStatus(NO_USE);
//			couponUser.setCouponId(coupon.getId());
//			if (coupon.getIndate() != null) {
//				// 天数转毫秒
//				Long addTime = TimeUnit.DAYS.toMillis(coupon.getIndate());
//				// 设置到期时间
//				couponUser.setIndateTime(new Date(ExDateUtils.getDate()
//						.getTime() + addTime));
//			} else {
//				couponUser.setIndateTime(coupon.getIndateTime());
//			}
//		// 将某类型的优惠券打进用户帐号
//		this.couponUserMapper.insert(couponUser);
//		// 将优惠券库存数减一
//		List<Coupon> coupons = new ArrayList<Coupon>();
//		coupons.add(coupon);
//		this.couponMapper.subtractOnes(coupons);
//		return true;
//	}

	/**
	 * 新用户注册送券一张10元钓场券，一张10元店铺券
	 */
	public void presentUser(Long userId) {
		Timestamp t = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		Coupons c = new Coupons();
		c.setUid(userId);
		c.setCategory(5);
		c.setCouponFee(10);
		c.setUseRule(50);
		c.setCouponName("注册券");
		//过期时间 30天
		c.setDeadline(new Timestamp(t.getTime() + 30L * 24 * 60 * 60 * 1000));
		c.setDesc("平台店铺送券");
		c.setGetTime(t);
		c.setStatus(0);
		couponsMapper.insertPojo(c);
		Coupons c1 = new Coupons();
		c1.setUid(userId);
		c1.setCategory(0);
		c1.setCouponFee(10);
		c1.setUseRule(50);
		c1.setCouponName("注册券");
		//过期时间 30天
		c1.setDeadline(new Timestamp(t.getTime() + 30L * 24 * 60 * 60 * 1000));
		c1.setDesc("平台钓场送券");
		c1.setGetTime(t);
		c1.setStatus(0);
		couponsMapper.insertPojo(c1);
	}
	
	
//	@Override
//	public List<CouponUser> getUsableCouponsByUser(Integer userId,
//			Double smoney, Integer siteId) throws Exception {
//		Map<String, Object> param = new HashMap<String, Object>();
//		List<CouponUser> newList = new ArrayList<CouponUser>();
//		param.put("userId", userId);
//		param.put("status", 0);
//		List<CouponUser> list = couponUserMapper.selectList(param);
//		if (list.size() > 0) {
//			for (CouponUser c : list) {
//				// 从配置中查使用条件对应的使用规则
//				DictConfig dict = dictMapper.selectById(c.getCoupon()
//						.getUseRule());
//				logger.debug("dictvalue:{},smoney:{},fishsiteId:{}",dict.getValue(),smoney,c.getCoupon().getFishSiteId());
//				if (smoney >= Integer.valueOf(dict.getValue())
//						&& c.getCoupon().getFishSiteId() != null
//						&& c.getCoupon().getFishSiteId().intValue() == siteId) {
//					newList.add(c);
//					continue;
//				} 
//				if (smoney >= Integer.valueOf(dict.getValue())
//						&& c.getCoupon().getFishSiteId() == null) {
//					newList.add(c);
//					continue;
//				}
//			}
//		}
//		return newList;
//	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CouponUserService#getUsableCouponsByUser(java.util.Map)
	 */
	@Override
	public List<CouponUser> getUsableCouponsByUser(Map<String, Object> m) {
		return couponUser10Mapper.getUsableCouponsByUser(m);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.CouponUserService#getUnusedShopCoupons(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Coupons> getUnusedShopCoupons(Long uid, Long shopId) {
		return couponsMapper.selectUnusedShopCoupons(uid,shopId);
	}
	
	/**
	 * 判断优惠券是否过期
	 * @param deadline
	 * @return
	 */
	public static boolean isTimeoutByCoupons(Date deadline){
		return Integer.parseInt(ExDateUtils.getDateFormat(deadline, ExDateUtils.pattern))
				< Integer.parseInt(ExDateUtils.getCurrentDayFormat());
	}
	
}
