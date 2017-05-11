/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.WithdrawOrders;

/**
 * @author wzw
 * @date 2016年8月6日
 * @version 1.0
 */
public interface WithdrawOrdersService {

	/**
	 * 提现手续费费率 5%
	 */
	double withdraw_poundage_rate = 0.05;
	
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
	void addPojo(WithdrawOrders pojo);

	/**
	 * 
	 * @param pojo
	 */
	void modifyPojo(WithdrawOrders pojo);

}
