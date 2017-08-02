/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerShopEarningOrders;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface PartnerShopEarningOrdersMapper {

	PartnerShopEarningOrders selectByOrderId(String orderId);
	
	
	void insertPojo(PartnerShopEarningOrders pojo);
	
}
