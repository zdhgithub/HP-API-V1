/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.PartnerShopRateConfig;

/**
 * @author wzw
 * @date 2016年11月9日
 * @version 1.0
 */
public interface PartnerShopRateConfigMapper {

	PartnerShopRateConfig selectById(@Param("partnerId")Integer partnerId,@Param("shopId")Long shopId);

	
	void insertPojo(PartnerShopRateConfig pojo);
	
	void updatePojo(PartnerShopRateConfig pojo);
	
}
