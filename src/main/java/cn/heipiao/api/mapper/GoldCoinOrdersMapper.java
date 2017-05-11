/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.GoldCoinOrders;

/**
 * @author wzw
 * @date 2016年8月19日
 * @version 1.0
 */
public interface GoldCoinOrdersMapper {

	
	GoldCoinOrders selectByOrderIdAsLock(String orderId);
	
	
	GoldCoinOrders selectByOrderId(String orderId);
	
	
	void insertPojo(GoldCoinOrders pojo);
	
	
	void updatePojo(GoldCoinOrders pojo);


	/**
	 * @param orderId
	 */
	void updateTimeoutOrders(String orderId);


	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<String> selectTimeoutOrders(Map<String, Object> map);
	
}
