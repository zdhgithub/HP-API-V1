/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.OrdersDetails;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface OrdersDetailsMapper {

	/**
	 * @param ordersDetails
	 */
	void insertBatch(List<OrdersDetails> list);

	
	List<OrdersDetails> selectByOrderId(String orderId);
	
}
