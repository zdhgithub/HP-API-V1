/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.PartnerGivingOutCouponCountByMonth;

/**
 * @author wzw
 * @date 2016年9月19日
 * @version 1.0
 */
public interface PartnerGivingOutCouponCountByMonthMapper {

	
	PartnerGivingOutCouponCountByMonth selectByUidAndPartnerIdAsLock(@Param("uid") Long uid,@Param("partnerId") Integer partnerId);

	
	PartnerGivingOutCouponCountByMonth selectByUidAndPartnerId(@Param("uid") Long uid,@Param("partnerId") Integer partnerId);
	
	
	void insertPojo(PartnerGivingOutCouponCountByMonth pojo);
	
	
	void updatePojo(PartnerGivingOutCouponCountByMonth pojo);
}
