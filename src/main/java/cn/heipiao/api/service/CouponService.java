package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Coupon;

/**
 * @author zf
 * @version 1.0
 * @description 券service
 * @date 2016年7月20日
 */
public interface CouponService {
	/**
	 * 新建券
	 * 
	 * @param coupon
	 * @return
	 * @throws Exception
	 */
	public int saveCoupon(Coupon coupon) throws Exception;

	/**
	 * 查询券列表
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCouponList(Map<String, Object> param)
			throws Exception;
	/**
	 * 搜索
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCouponListSearch(Map<String, Object> param)
			throws Exception;

	/**
	 * 暂停/启用发放券
	 * 
	 * @param couponId
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public int updateCouponFlag(Integer couponId, Integer flag)
			throws Exception;
}
