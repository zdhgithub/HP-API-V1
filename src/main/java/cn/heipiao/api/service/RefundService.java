/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.Refund;

/**
 * @author wzw
 * @date 2016年7月20日
 * @version 1.0
 */
public interface RefundService {

	/**
	 * @param refundNo
	 * @return 
	 * @throws Exception 
	 */
	boolean confirmRefund(String refundNo) throws Exception;
	
	/**
	 * 退款业务
	 * @param r
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public boolean refundTicket(Refund r, Orders order) throws Exception ;

	/**
	 * @param r
	 */
	void refundGoldAndDeposit(Refund r);

	/**
	 * 更新微信退款状态
	 * @param refundNo
	 */
	public void updateWxRefundStatus(String refundNo);
	
}
