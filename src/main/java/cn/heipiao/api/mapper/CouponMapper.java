package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.Coupon;

/**
 *	@author z
 *	@version 1.0
 *	@description 
 *	@date 2016年7月19日
 */
public interface CouponMapper extends BaseMapper<Coupon>{
	
	/**
	 * @说明 - 根据券的发放类型查询 - 返回有效类型且正在发放的券
	 * @param type
	 * @return
	 */
	List<Coupon> selectByType(int type);
	
	/**
	 * @说明 优惠券数量减一
	 * @param coupons
	 * @return
	 */
	int subtractOnes(List<Coupon> coupons);
	
}
