/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerEarningOrders;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface PartnerEarningOrdersMapper {

	PartnerEarningOrders selectByOrderId(String orderId);
	
	
	void insertPojo(PartnerEarningOrders pojo);
	
}
