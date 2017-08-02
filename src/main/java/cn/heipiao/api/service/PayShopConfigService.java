/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.PayShopConfig;

/**
 * @author wzw
 * @date 2016年10月15日
 * @version 1.0
 */
public interface PayShopConfigService {


	PayShopConfig getById(Integer serviceId);

	
	void addPojo(PayShopConfig pojo);
	
	
	void modifyPojo(PayShopConfig pojo);


	/**
	 * @param uid
	 * @param orderMoney 
	 * @param orderId 
	 * @return 
	 */
	int reward(Long uid, String orderId, Double orderMoney);
	
	
}
