/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Orders;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface OrdersMapper {
	
	Integer countOrders(Integer uid);
	/**
	 * @param pojo
	 */
	void insertPojo(Orders pojo);

	/**
	 * @param orderId
	 * @return
	 */
	Orders selectById(@Param("orderId") String orderId);
	
	/**
	 * @param orderId
	 * @param status
	 */
	void updateStatus(@Param("orderId")String orderId, @Param("status")Integer status);
	
	/**
	 * 更新退款余额
	 * @param orderId
	 */
	void updateRefundFee(@Param("orderId") String orderId,@Param("refundFee")Double refundFee);

	/**
	 * 更新payTime，status，tradeNo,tradePlatform字段
	 * @param pojo
	 */
	void updatePart(Orders pojo);
	
	/**
	 * @param map
	 * @return
	 */
	List<Orders> selectOrdersBySite(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<Orders> selectOrdersByUid(Map<String, Object> map);

	/**
	 * 只获取订单记录加锁
	 * 不获取订单详情
	 * @param out_trade_no
	 * @return
	 */
	Orders selectAsLockById(String orderId);
	
	/**
	 * @说明 清除超时订单
	 * @param params
	 * @return
	 */
//	int clearOutTimeOrder(Map<String , Object> params);

	/**
	 * 线程调用
	 * @param map
	 * @return
	 */
	List<String> selectTimeoutOrders(Map<String, Object> map);

}
