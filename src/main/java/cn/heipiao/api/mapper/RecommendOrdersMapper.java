/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.RecommendOrders;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface RecommendOrdersMapper {

	RecommendOrders selectByOrderId(String orderId);
	
	
	void insertPojo(RecommendOrders pojo);
	
}
