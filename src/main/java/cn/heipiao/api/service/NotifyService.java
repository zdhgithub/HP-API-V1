package cn.heipiao.api.service;

import cn.heipiao.api.pojo.AliPayNotify;
import cn.heipiao.api.pojo.WxPayNotify;

/**
 * @author zf
 * @version 1.0
 * @description 处理回调通知
 * @date 2016年7月4日
 */
public interface NotifyService {

	/**
	 * @param map
	 */
//	public String wxPayNotify(WxPayNotify wxPayNotify);

	/**
	 * @param map
	 * @return
	 * @throws Exception 
	 */
//	public String aliPayNotify(AliPayNotify aliPayNotify) throws Exception;
	
	/**
	 * 微信
	 * 通过订单号查询通知
	 * @param orderId
	 * @return
	 */
	public WxPayNotify getWxPayNotify(String orderId);
	
	/**
	 * 支付宝
	 * 通过订单号查询通知
	 * @param orderId
	 * @return
	 */
	public AliPayNotify getAliPayNotify(String orderId);

	/**
	 * ali 订单关闭
	 * @param aliPayNotify
	 */
	public void updateAliPayNotify(AliPayNotify aliPayNotify);

	/**
	 * @param orderId
	 * @param m 
	 * 
	 *  0:表示订单未支付
	 * 1：表示订单已支付
	 * -1：表示订单取消或者其它
	 * 
	 */
	public int wxTradeQueryResult(String orderId,WxPayNotify wx,Integer whereIsApp) throws Exception;

	/**
	 * @param orderId
	 * @throws Exception 
	 * 
	 * 0:表示订单未支付
	 * 1：表示订单已支付
	 * -1：表示订单取消或者其它 
	 * 
	 */
	public int aliTradeQueryResult(String orderId ,AliPayNotify ali) throws Exception;

	/**
	 * @param wxPayNotify
	 */
//	public String wxPayGoldCoinNofify(WxPayNotify wxPayNotify);

	/**
	 * @param aliPayNotify
	 * @return
	 * @throws Exception 
	 */
//	public String aliPayGoldCoinNofify(AliPayNotify aliPayNotify) throws Exception;

	/**
	 * @param aliPayNotify
	 * @return
	 * @throws Exception 
	 */
	String aliNotify(AliPayNotify aliPayNotify) throws Exception;

	/**
	 * @param wxPayNotify
	 * @return
	 * @throws Exception 
	 */
	String wxNotify(WxPayNotify wxPayNotify) throws Exception;

	/**
	 * @param aliPayNotify
	 */
	public void updateAliPayGoldCoin(AliPayNotify aliPayNotify);

	/**
	 * @param wx
	 * @return
	 * @throws Exception 
	 */
	int wxOrdersSuccess(WxPayNotify wx) throws Exception;

	/**
	 * 验证平台并查询订单结果信息处理
	 * 
	 * @param tradePlatform
	 * @param orderId
	 */
	int verifyOrders(int tradePlatform, String orderId,String attach,Integer whereIsApp) throws Exception;

	/**
	 * @param ali
	 * @return
	 * @throws Exception
	 */
	int aliOrdersSuccess(AliPayNotify ali) throws Exception;

	/**
	 * @param orderId
	 * @param attach
	 * @return
	 * @throws Exception
	 */
	int orderFail(String orderId, String attach) throws Exception;

	/**
	 * 票支付订单关闭
	 * @param aliPayNotify
	 */
	public void updateAliPayShop(AliPayNotify aliPayNotify);

//	/**
//	 * wx 订单关闭
//	 * @param wxPayNotify
//	 */
//	void updateWxPayNotify(WxPayNotify wxPayNotify);
	
}
