/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.PartnerSiteRateConfig;

/**
 * @author wzw
 * @date 2016年11月9日
 * @version 1.0
 */
public interface PartnerSiteRateConfigMapper {

	PartnerSiteRateConfig selectById(@Param("siteId") Integer siteId,@Param("partnerId") Integer partnerId);
	
	void insertPojo(PartnerSiteRateConfig pojo);
	
	void updatePojo(PartnerSiteRateConfig pojo);
}
