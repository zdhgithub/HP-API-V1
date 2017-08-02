/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.ShopWithdrawOrders;

/**
 * @author wzw
 * @date 2016年10月12日
 * @version 1.0
 */
public interface ShopWithdrawOrdersMapper {

	/**
	 * 加锁
	 * @param tradeNo
	 * @return
	 */
	ShopWithdrawOrders selectByTradeNoAsLock(String tradeNo);

	/**
	 * 
	 * @param tradeNo
	 * @return
	 */
	ShopWithdrawOrders selectByTradeNo(String tradeNo);
	
	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(ShopWithdrawOrders pojo);

	/**
	 * 
	 * @param pojo
	 */
	void updatePojo(ShopWithdrawOrders pojo);

	/**
	 * @param map
	 * @return
	 */
	List<ShopWithdrawOrders> selectListByLimit(Map<String, Object> map);

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<ShopWithdrawOrders> selectByStatus(Map<String, Object> map);
	
}
