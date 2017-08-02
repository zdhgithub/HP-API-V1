/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Orders;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface OrdersService {

	/**
	 * @param pojo
	 * @throws Exception 
	 */
	int  addOrder(Orders pojo) throws Exception;

	/**
	 * @param orderId
	 * @param orderId 
	 * @throws Exception 
	 */
	int cancelOrder(String orderId) throws Exception;

	/**
	 * 验证平台并查询订单结果信息处理
	 * 
	 * @param tradePlatform
	 * @param orderId
	 * @return 0:不做处理,反之直接返回
	 * @throws Exception
	 */
//	int verifyOrders(Integer tradePlatform, String orderId) throws Exception;
	
	void verifyOrders(String orderId) throws Exception;
	
	
	int verifyCancelOrders(String orderId) throws Exception;
	
	/**
	 * @param map
	 * @return
	 */
	List<Orders> getOrdersListBySite(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Orders> getOrdersListByUid(Map<String, Object> map);

	/**
	 * @param orderId
	 * @return
	 */
	Orders getOrdersById(String orderId);

	/**
	 * @说明
	 * 		清除超时订单
	 * @return
	 * @throws Exception 
	 */
	void clearOutimeOrder() throws Exception;

	/**
	 * @param orderId
	 * @return
	 */
	Orders selectByOrderId(String orderId);

	/**
	 * @param pojo
	 * @throws Exception 
	 */
	void siteDeal(Orders pojo) throws Exception;

}
