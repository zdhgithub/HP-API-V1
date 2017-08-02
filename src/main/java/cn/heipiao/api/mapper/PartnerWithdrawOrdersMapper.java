/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerWithdrawOrders;

/**
 * @author wzw
 * @date 2016年8月6日
 * @version 1.0
 */
public interface PartnerWithdrawOrdersMapper {

	/**
	 * 加锁
	 * @param tradeNo
	 * @return
	 */
	PartnerWithdrawOrders selectByTradeNoAsLock(String tradeNo);

	/**
	 * 
	 * @param tradeNo
	 * @return
	 */
	PartnerWithdrawOrders selectByTradeNo(String tradeNo);
	
	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(PartnerWithdrawOrders pojo);

	/**
	 * 
	 * @param pojo
	 */
	void updatePojo(PartnerWithdrawOrders pojo);

	/**
	 * @param map
	 * @return
	 */
	List<PartnerWithdrawOrders> selectListByLimit(Map<String, Object> map);

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<PartnerWithdrawOrders> selectByStatus(Map<String, Object> map);
}
