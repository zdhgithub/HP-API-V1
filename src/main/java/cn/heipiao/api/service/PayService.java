/**
 * 
 */
package cn.heipiao.api.service;

/**
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
public interface PayService {
	
	/**
	 * 异步通知业务类型 api
	 * 如需修改调度线程同步修改
	 */
	//禁止修改  购票
	public  static final String buyGoodOrders = "1";
	//禁止修改   购漂币
	public static final String payGoldCoin = "2";
	
	//禁止修改   付款到店铺
	public static final String payShop = "3";
	
	//禁止修改   活动支付
	public static final String activity = "4";

	/**
	 * @param uid
	 * @param platform
	 * @param orderId
	 * @param hpService 
	 * @param openid 
	 * @return
	 * @throws Exception 
	 */
	String generatePayParam(Long uid, int platform, String orderId,String appIp,String body, Integer hpService, String openid) throws Exception;

	/**
	 * @param orderId
	 */
//	int payOrderConfirm(String orderId);

}
