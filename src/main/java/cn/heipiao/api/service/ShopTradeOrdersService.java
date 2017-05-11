/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.ShopTradeOrders;

/**
 * @author wzw
 * @date 2016年10月14日
 * @version 1.0
 */
public interface ShopTradeOrdersService {

	ShopTradeOrders selectByOrderId(String orderId);

	
	int addShopTradeOrders(ShopTradeOrders pojo) throws Exception;
	
	
	int cancelShopTradeOrders(String orderId) throws Exception;


	void verifyOrders(String orderId) throws Exception;


	/**
	 * @param shopId
	 * @return
	 */
	List<ShopTradeOrders> getRecentOrders(Long shopId);


	/**
	 * @param shopId
	 * @return
	 */
	Map<String, Object> countBoard(Long shopId);


	/**
	 * @param map
	 * @return
	 */
	List<ShopTradeOrders> getOrdersByShopId(Map<String, Object> map);


	/**
	 * @param pojo
	 * @throws Exception 
	 */
	void shopDeal(ShopTradeOrders pojo) throws Exception;

	/**
	 *  @param pojo
	 * @throws Exception
	 */
	void shopDetial(ShopTradeOrders pojo) throws Exception;
	Map<String, Object> getTradeRecord(Map<String, Object> map);


}
