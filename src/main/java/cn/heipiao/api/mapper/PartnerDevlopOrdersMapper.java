/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.PartnerDevlopOrders;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public interface PartnerDevlopOrdersMapper {

	PartnerDevlopOrders selectByOrderId(String orderId);
	
	
	void insertPojo(PartnerDevlopOrders pojo);
	
}
