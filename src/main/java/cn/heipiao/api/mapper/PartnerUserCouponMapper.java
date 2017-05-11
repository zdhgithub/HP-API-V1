/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerUserCoupon;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public interface PartnerUserCouponMapper {

	
	PartnerUserCoupon selectByUidAsLock(Long uid);

	
	PartnerUserCoupon selectByUid(Long uid);
	
	
	void insertPojo(PartnerUserCoupon pojo);
	
	
	void updateAmount(PartnerUserCoupon pojo);
	
}
