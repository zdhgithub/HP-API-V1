package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.CouponUser;
import cn.heipiao.api.pojo.Coupons;

/**
 * @author zf
 * @version 1.0
 * @description 用户获取的券的service
 * @date 2016年7月20日
 */
public interface CouponUserService {

	/** 未使用 **/
	int NO_USE = 0;

	/** 已使用 **/
	int USED = 1;

	/** 超时券 **/
	int OUTTIME_COUPON = 2;

	/**
	 * 查询用户不同状态的券
	 * 
	 * @param userId
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<CouponUser> getListByUser(Integer userId, Integer status,
			Integer start, Integer size) throws Exception;

	/**
	 * 查询用户可以用来支付的券
	 * 
	 * @param userId
	 * @param smoney
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
//	public List<CouponUser> getUsableCouponsByUser(Integer userId,
//			Double smoney,Integer siteId) throws Exception;

	/**
	 * 查询某个券的明细
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
//	public List<CouponUser> getListByCoupon(Map<String, Object> param)
//			throws Exception;

	/**
	 * 统计钓场主共发券数量
	 * 
	 * @return
	 */
//	public int countSendedCoupons() throws Exception;

	/**
	 * 统计钓场主发券的总金额
	 * 
	 * @return
	 */
//	public double countSendedCouponMoney() throws Exception;

	/**
	 * 统计可以使用的券的数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int countUnusedCoupons(Integer userId, Integer status)
			throws Exception;

	/**
	 * @说明 赠送用户票券
	 * @param userId
	 *            用户Id
	 * @param type
	 *            赠送方式
	 * @return
	 */
//	boolean presentUser(int userId);
	
	/**
	 * @说明 赠送用户票券
	 * @param userId
	 *            用户Id
	 * @param type
	 *            赠送方式
	 * @return
	 */
	void presentUser(Long userId);

	/**
	 * @param m
	 * @return
	 */
	public List<CouponUser> getUsableCouponsByUser(Map<String, Object> m);

	/**获取未用的店铺券
	 * @param uid
	 * @param shopId
	 * @return
	 */
	public List<Coupons> getUnusedShopCoupons(Long uid, Long shopId);

	/**根据用户查询新券
	 * @param userId
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	List<Coupons> getNewListByUser(Integer userId, Integer status, Integer start, Integer size) throws Exception;

}
