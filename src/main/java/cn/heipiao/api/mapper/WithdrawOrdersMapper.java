/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.WithdrawOrders;

/**
 * @author wzw
 * @date 2016年8月6日
 * @version 1.0
 */
public interface WithdrawOrdersMapper {

	/**
	 * 加锁
	 * @param tradeNo
	 * @return
	 */
	WithdrawOrders selectByTradeNoAsLock(String tradeNo);

	/**
	 * 
	 * @param tradeNo
	 * @return
	 */
	WithdrawOrders selectByTradeNo(String tradeNo);
	
	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(WithdrawOrders pojo);

	/**
	 * 
	 * @param pojo
	 */
	void updatePojo(WithdrawOrders pojo);

	/**
	 * @param map
	 * @return
	 */
	List<WithdrawOrders> selectListByLimit(Map<String, Object> map);

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<WithdrawOrders> selectByStatus(Map<String, Object> map);
}
