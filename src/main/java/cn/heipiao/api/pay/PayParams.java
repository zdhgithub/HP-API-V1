/**
 * 
 */
package cn.heipiao.api.pay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * 支付参数请求参数组装类
 * @author wzw
 * @date 2016年7月13日
 * @version 1.0
 */
@Component
public class PayParams {

	public static String sign = "sign";
	
	//小程序签名参数名
	public static String paySign = "paySign";
	

	public static String keyStr = "&key=";

	public static String sign_type = "sign_type";
	
	public static final String success = "SUCCESS"; 

	public static final String fail = "FAIL"; 

	@Resource
	private PayConfig payConfig;
	
	/*
	 * appid=wxedaf15837c0ef53d& body=黑漂钓鱼-测试& mch_id=1365397002&
	 * nonce_str=u5zh2os7qmaftvvi&
	 * notify_url=http://120.24.253.129:8880/callback/notify_url.do&
	 * out_trade_no=201601143& spbill_create_ip=192.168.0.152& total_fee=5&
	 * trade_type=APP
	 * 
	 */
	/**
	 * 微信 获取统一下单参数
	 * 
	 * @param appid
	 *            应用id
	 * @param body
	 *            商品名称
	 * @param out_trade_no
	 *            订单号
	 * @param spbill_create_ip
	 *            终端ip
	 * @param total_fee
	 *            支付金额 单位为:分
	 * @param hp_service 
	 * @param hp_service 
	 * @return
	 */
	public Map<String, Object> wxUnifiedorder(String appid, String body, String out_trade_no,
			String spbill_create_ip, Integer total_fee, String hp_service,String openid,Integer whereIsApp) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", appid);
		map.put("body", body);
		map.put("mch_id", payConfig.pay_wx_mch_id_c);
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		map.put("notify_url", payConfig.pay_wx_notify_url);
		map.put("out_trade_no", out_trade_no);
		map.put("spbill_create_ip", spbill_create_ip);
		map.put("total_fee", total_fee);
		if(whereIsApp != null && whereIsApp == 2 ){
			map.put("trade_type", "JSAPI");
			map.put("openid", openid);
		}else{
			map.put("trade_type", "APP");
		}
		//商家自定义参数
		map.put("attach", hp_service);
		return map;
	}

	/**
	 * 微信 获取app端调起支付的参数列表
	 * 
	 * @param prepayid
	 * @return
	 */
	public  Map<String, Object> wxPayParams(String prepayid) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", payConfig.pay_wx_appid_c);
		map.put("partnerid", payConfig.pay_wx_mch_id_c);
		map.put("prepayid", prepayid);
		map.put("package", "Sign=WXPay");
		map.put("noncestr", RandomNumberUtils.getRandomStringByLength(16));
		map.put("timestamp", "" + System.currentTimeMillis() / 1000);
		return map;
	}
	
	/**
	 * 微信小程序 
	 * 获取小程序调起支付的参数列表
	 * 
	 * @param prepayid
	 * @return
	 */
	public  Map<String, Object> wxMiniPayParams(String prepayid) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appId", payConfig.wx_mini_appid);
		map.put("signType", "MD5");
		map.put("package", "prepay_id=" + prepayid);
		map.put("nonceStr", RandomNumberUtils.getRandomStringByLength(16));
		map.put("timeStamp", "" + System.currentTimeMillis() / 1000);
		return map;
	}
	
	/**
	 * 微信
	 * 查询订单
	 * @param out_trade_no 订单号
	 * @return
	 */
	public  Map<String, Object> wxQueryTradeParams(String out_trade_no,Integer whereIsApp) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", whereIsApp != null && whereIsApp == 2 ? payConfig.wx_mini_appid : payConfig.pay_wx_appid_c);
		map.put("mch_id", payConfig.pay_wx_mch_id_c);
		map.put("out_trade_no", out_trade_no);
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		return map;
	}
	
	/**
	 * 微信
	 * 关闭订单
	 * @param out_trade_no 订单号
	 * @return
	 */
	public  Map<String, Object> wxCloseTradeParams(String out_trade_no,Integer whereIsApp) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", whereIsApp != null && whereIsApp == 2 ? payConfig.wx_mini_appid : payConfig.pay_wx_appid_c);
		map.put("mch_id", payConfig.pay_wx_mch_id_c);
		map.put("out_trade_no", out_trade_no);
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		return map;
	}
	

	/**
	 * 微信 
	 * 获取退款参数
	 * 
	 * @param out_trade_no
	 *            商户侧传给微信的订单号
	 * @param out_refund_no
	 *            商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param total_fee
	 *            订单总金额，单位为分，只能为整数
	 * @param refund_fee
	 *            退款总金额，单位为分 ,只能为整数
	 * @return
	 * 
	 * refundNo 退款号 (唯一)
	 * orderId  订单号
	 * tradeNo  平台交易号
	 * totalFee 订单总金额
	 * refundFee 退款总金额
	 * createTime 创建时间
	 * status 状态  0:申请退款，1：申请退款中，2：退款完成，3：退款失败 
	 * 
	 */
	public  Map<String, Object> wxRefundPayParams(String out_trade_no, String out_refund_no, Integer total_fee,
			Integer refund_fee,Integer whereIsApp) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", whereIsApp != null && whereIsApp == 2 ? payConfig.wx_mini_appid : payConfig.pay_wx_appid_c);
		map.put("mch_id", payConfig.pay_wx_mch_id_c);
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		map.put("out_trade_no", out_trade_no);
		map.put("out_refund_no", out_refund_no);
		map.put("total_fee", total_fee);
		map.put("refund_fee", refund_fee);
		map.put("op_user_id", payConfig.pay_wx_mch_id_c);
		return map;
	}

	/**
	 * 微信
	 * 查询退款
	 * @param i 1:退款单号，2:订单号
	 * @param out_no 退款单号
	 * @param out_no 订单号
	 * @return
	 */
	public  Map<String, Object> wxQueryRefundParams(String out_no,int i,Integer whereIsApp) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("appid", whereIsApp != null && whereIsApp == 2 ? payConfig.wx_mini_appid : payConfig.pay_wx_appid_c);
		map.put("mch_id", payConfig.pay_wx_mch_id_c);
		if(i == 1){
			//商户退款单号
			map.put("out_refund_no", out_no);
		}else if(i == 2){
			//商户订款单号
			map.put("out_trade_no", out_no);
		}
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		return map;
	}
	
	/**
	 * 获取企业向用户支付参数
	 * @param partner_trade_no 订单号
	 * @param openid 第三方openid
	 * @param re_user_name 用户真实姓名
	 * @param amount 付款金额(单位为：分)
	 * @param desc 企业操作说明
	 * @param spbill_create_ip 调用接口的机器ip
	 * @return
	 */
	public  Map<String,Object> wxEnterprisePay(String partner_trade_no,String openid,String re_user_name
			,Integer amount,String desc,String spbill_create_ip,String appid ,String mch_id){
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("mch_appid", appid);
		map.put("mchid", mch_id);
		map.put("nonce_str", RandomNumberUtils.getRandomStringByLength(16));
		map.put("partner_trade_no", partner_trade_no);
		map.put("openid", openid);
		map.put("check_name", "FORCE_CHECK");
		map.put("re_user_name", re_user_name);
		map.put("amount", amount);
		map.put("desc", desc);
		map.put("spbill_create_ip", spbill_create_ip);
		return map;
	}
	
	/**
	 * 微信返回参数
	 * @param respParams
	 * @return
	 */
	 public static String responseXml(String respParams){
		 return String.format("<xml>" +
							  "<return_code><![CDATA[%s]]></return_code>" +
							  "<return_msg><![CDATA[%s]]></return_msg>" +
							"</xml>", respParams,respParams);
	 }
	 
	 
//	 ====================================================支付宝================================================

	/*
	 * service="mobile.securitypay.pay"& partner="2088421306777403"&
	 * _input_charset="utf-8"& sign_type="RSA"& sign=
	 * "yDhkZPEpjxHAzu4xs%2Fgbrodtcu66eFA%2BhZ1Gi3zXpjFv18q8ZShHD84HguN2ACc7fRJ%2FQD9CrIoQWWSWaPpHu1Q%2FSwuGG4HfmmKh%2BRRIiIR7fcd%2BozcehTrOJJUvbWwmSglhaT%2FHA8QAj4uWsrMfwvxOhSGxyznvpmX%2B7a0K8HY%3D"&
	 * notify_url="http://120.24.253.129:8880/callback/notify_url.do"&
	 * out_trade_no="7"& subject="购票"& payment_type="1"&
	 * seller_id="2088421306777403"& total_fee="0.05"& body="测试"&
	 * app_id="2016071301612106"
	 */
	/**
	 * 支付宝 获取支付请求参数
	 * 
	 * @param out_trade_no
	 *            订单号
	 * @param subject
	 *            商品名称
	 * @param total_fee
	 *            总金额(单位：元)
	 * @return
	 */
	public  Map<String, Object> aliPayParams(String out_trade_no, String subject, String total_fee,String hp_service ) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("service", "mobile.securitypay.pay");
		map.put("partner", payConfig.pay_ali_partner);
		map.put("_input_charset", PayConfig.utf_8);
		map.put("sign_type", payConfig.pay_ali_sign_type);
		map.put("notify_url", payConfig.pay_ali_notify_url);
		map.put("out_trade_no", out_trade_no);
		map.put("subject", subject);
		map.put("payment_type", "1");
		map.put("seller_id", payConfig.pay_ali_partner);
		map.put("total_fee", total_fee);
		//商家自定义参数
		map.put("attach", hp_service);
		return map;
	}
	
	
	

	/**
	 * 支付宝
	 * 单条无密退款参数
	 * @param out_request_no 退款号
	 * @param out_trade_no 订单号
	 * @param refund_fee 退款金额 单位：元
	 * @return
	 */
	public  Map<String ,Object> aliRefundNoPwd(String out_request_no,String out_trade_no,String refund_fee){
//		旧版本
//		Map<String, Object> map = new TreeMap<String, Object>();
//		map.put("service", "refund_fastpay_by_platform_nopwd");
//		map.put("partner", AlipayConfig.partner);
//		map.put("_input_charset", AlipayConfig.input_charset);
//		map.put("sign_type", AlipayConfig.sign_type);
//		map.put("sign", "");
//		map.put("notify_url", AlipayConfig.notify_url);
//		map.put("refund_date", AlipayConfig.refund_date);
//		map.put("batch_no", batch_no);
//		map.put("batch_num", "1");
//		map.put("detail_data", trade_no + "^" + refund_fee + "^" + refund_desc);
//		map.put("detail_data", "2016071921001004370245493017^0.01^ceshi");
		
//		新版本接口
		Map<String, Object> sParaTemp = new TreeMap<String, Object>();
		sParaTemp.put("method", "alipay.trade.refund");
		sParaTemp.put("app_id", payConfig.pay_ali_app_id_c);
		sParaTemp.put("charset", PayConfig.utf_8);
		sParaTemp.put("format", "JSON");
		sParaTemp.put("sign_type", "RSA");
		sParaTemp.put("timestamp",  ExDateUtils.getDateFormat());
		sParaTemp.put("version", "1.0");
		sParaTemp.put("biz_content", "{\"out_trade_no\":\"" + out_trade_no + "\","
				+ "\"out_request_no\":\"" + out_request_no + "\","
						+ "\"refund_amount\":\"" + refund_fee +"\"}");
		return sParaTemp;
	}
	
	/**
	 * 支付宝
	 * 查询订单
	 * @param out_trade_no 订单号
	 * @return
	 */
	public  Map<String,Object> aliQueryTrade(String out_trade_no){
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("app_id", payConfig.pay_ali_app_id_c);
		map.put("method", "alipay.trade.query");
		map.put("charset", PayConfig.utf_8);
		map.put("sign_type", payConfig.pay_ali_sign_type);
		map.put("timestamp", ExDateUtils.getDateFormat());
		map.put("version", "1.0");
		map.put("biz_content", "{\"out_trade_no\":\"" + out_trade_no + "\"}");
		return map;
	}
	
	/**
	 * 支付宝
	 * 关闭订单
	 * @param out_trade_no
	 * @return
	 */
	public Map<String, Object> aliCloseTrade(String out_trade_no) {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("app_id", payConfig.pay_ali_app_id_c);
		map.put("method", "alipay.trade.query");
		map.put("charset", PayConfig.utf_8);
		map.put("sign_type", payConfig.pay_ali_sign_type);
		map.put("timestamp", ExDateUtils.getDateFormat());
		map.put("version", "1.0");
		map.put("biz_content", "{\"out_trade_no\":\"" + out_trade_no + "\"}");
		return map;
	}
	
	/**
	 * 支付宝
	 * 企业支付参数
	 * 
	 * @return
	 */
	public  Map<String,Object> aliEnterprisePay(){
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("service", "batch_trans_notify");
		map.put("partner", payConfig.pay_ali_partner);
		map.put("_input_charset", PayConfig.utf_8);
		map.put("sign_type", payConfig.pay_ali_sign_type);
		map.put("notify_url", "");
		map.put("account_name", "");
		map.put("detail_data", "");
		map.put("batch_no", "");
		map.put("batch_num", "");
		map.put("batch_fee", "");
		map.put("email", "");
		map.put("pay_date", ExDateUtils.getCurrentDayFormat());
		return map;
	}
	
	
	/**
	 * 支付宝
	 * 退款查询
	 * @param out_trade_no
	 * @param out_request_no
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> aliRefundQuery(String out_trade_no, String out_request_no) throws Exception {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("app_id", payConfig.pay_ali_app_id_c);
		map.put("method", "alipay.trade.fastpay.refund.query");
		map.put("charset", PayConfig.utf_8);
		map.put("sign_type", payConfig.pay_ali_sign_type);
		map.put("timestamp", ExDateUtils.getCurrentDayFormat("yyyy-MM-dd HH:mm:ss"));
		map.put("version", "1.0");
		map.put("biz_content", "{\"out_trade_no\":\"" + out_trade_no  +"\","
				+ "\"out_request_no\":\"" + out_request_no + "\"}");
		return map;
	}
	
	
	
//	=====================================签名方式=====================================================
	
	
	/**
	 * 生成待签名的字符串 appid=wxd930ea5d5a258f4f&body=test
	 * 
	 * @param map
	 * @param ignore
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String signStr(Map<String, ? extends Object> map, String... ignore) {
		List<String> keys = ignore == null ? Collections.EMPTY_LIST : Arrays.asList(ignore);
		StringBuffer sb = new StringBuffer();
		for (Entry<String, ? extends Object> ent : map.entrySet()) {
			if (keys.contains(ent.getKey()) || ent.getValue() == null || ent.getValue().toString().equals(""))
				continue;
			sb.append(ent.getKey() + "=" + ent.getValue() + "&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 生成值带双引号的待签名的字符串
	 * service="mobile.securitypay.pay"&partner="2088101568338364"
	 * 
	 * @param m
	 * @param ignore
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String signStr0(Map<String,  ? extends Object> map, String... ignore) {
		List<String> keys = ignore == null ? Collections.EMPTY_LIST : Arrays.asList(ignore);
		StringBuffer sb = new StringBuffer();
		for (Entry<String, ? extends Object> ent : map.entrySet()) {
			if (keys.contains(ent.getKey()) || ent.getValue() == null || ent.getValue().equals(""))
				continue;
			sb.append(ent.getKey() + "=\"" + ent.getValue() + "\"&");
		}
		return sb.substring(0, sb.length() - 1);
	}


}
