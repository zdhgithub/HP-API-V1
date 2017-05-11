/**
 * 
 */
package cn.heipiao.api.service;

/**
 * @author wzw
 * @date 2016年10月19日
 * @version 1.0
 */
public interface PartnerShopEarningOrdersService {

	void addPojo(Long uid ,String shopTradeId,Integer partnerId, Long shopId, Integer amount);
}
