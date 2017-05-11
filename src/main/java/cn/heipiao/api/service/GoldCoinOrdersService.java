/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.GoldCoinOrders;

/**
 * @author wzw
 * @date 2016年8月19日
 * @version 1.0
 */
public interface GoldCoinOrdersService {
	
	
	GoldCoinOrders getGoldCoinOrdersByOrderId(String orderId);

	
	int addGoldCoinOrders(GoldCoinOrders pojo);
	
	
	int cancelGoldCoinOrders(String orderId) throws Exception;


	/**
	 * @param platform
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
//	int verifyOrders(int platform, String orderId) throws Exception;


	void verifyOrders(String orderId) throws Exception;
}
