/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.CouponFeesConfig;
import cn.heipiao.api.pojo.GiveCoupons;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
public interface CouponFeesConfigService {

	/**
	 * 设置优惠券收费金额
	 * @param serviceId
	 * @param pojo
	 */
	void setFees(Integer serviceId ,GiveCoupons pojo);

	/**
	 * @param categoryId 
	 * @param category
	 * @return
	 */
	List<CouponFeesConfig> getCouponFeeRule(Integer serviceId, Integer categoryId);
	
}
