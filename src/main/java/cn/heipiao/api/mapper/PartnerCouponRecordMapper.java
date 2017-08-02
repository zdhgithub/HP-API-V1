/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.PartnerCouponRecord;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public interface PartnerCouponRecordMapper {

	List<PartnerCouponRecord> selectByPartnerId(Integer partnerId);
	
	PartnerCouponRecord selectByCouponIdAsLock(Long coupon);
	
	PartnerCouponRecord selectByCouponId(Long coupon);
	
	void insertPojo(PartnerCouponRecord pojo);

	/**
	 * @param partnerId
	 * @return
	 */
	Integer selectCount(Integer partnerId);

	/**
	 * @param partnerId
	 * @return
	 */
	Integer selectCountByUsed(Integer partnerId);
	
	
}
