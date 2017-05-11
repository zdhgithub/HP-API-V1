/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PayShopConfig;

/**
 * @author wzw
 * @date 2016年10月15日
 * @version 1.0
 */
public interface PayShopConfigMapper {

	PayShopConfig selectById(Integer serviceId);

	
	void insertPojo(PayShopConfig pojo);
	
	
	void updatePojo(PayShopConfig pojo);
	
}
