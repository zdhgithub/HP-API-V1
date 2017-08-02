/**
 * 
 */
package cn.heipiao.api.pay;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
@Component("PayService")
public class PayService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayService.class);
	
	@Resource
	private HttpUtils httpUtils;
	
	@Resource
	private PayParams payParams;
	
	@Resource
	private PayConfig payConfig;
	
	
	private static volatile String ip = null;
	/**
	 *
	 * 微信
	 * 微信统一下单并返回app端调起支付的参数列表
	 * @param appid
	 * @param body
	 * @param out_trade_no
	 * @param spbill_create_ip
	 * @param total_fee
	 * @param hp_service 
	 * @return 不为空正常，为null则失败
	 * @throws Exception
	 */
	public  String wechatUnifiedorder(String appid,String body,String out_trade_no , String spbill_create_ip ,Integer total_fee,
			String hp_service,String openid,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxUnifiedorder(appid, body, out_trade_no, spbill_create_ip, total_fee,hp_service,openid,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatUnifiedorder-xmlStr:{}" ,xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/pay/unifiedorder", HttpMethod.POST.toString(), new StringEntity(xmlStr,"UTF-8"));
		logger.debug("wechatUnifiedorder-respStr:{}", respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		if(m.get("return_code").toString().equalsIgnoreCase(PayParams.success) && m.get("result_code").toString().equalsIgnoreCase(PayParams.success)){
			String prepayid = (String) m.get("prepay_id");
			if(openid != null && openid.length() > 0){
				return wechatMiniPayParam(prepayid);
			}else{
				return wechatPayParam(prepayid);
			}
		}
		logger.error("wechatUnifiedorder-respStr:{}" ,respStr);
		return null;
	}
	
	/**
	 * 微信
	 * app端调起支付的参数生成
	 * @param prepayid
	 * @return
	 */
	private  String wechatPayParam(String prepayid) {
		Map<String, Object> map = payParams.wxPayParams(prepayid);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		logger.info("wechatPay-map:{}", map);
		return JSONObject.toJSONString(map);
	}
	
	/**
	 * 微信
	 * 小程序调起支付的参数生成
	 * @param prepayid
	 * @return
	 */
	private  String wechatMiniPayParam(String prepayid) {
		Map<String, Object> map = payParams.wxMiniPayParams(prepayid);
		map.put(PayParams.paySign, Sign.md5Sign(PayParams.signStr(map, PayParams.paySign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		logger.info("wechatMiniPayParam-map:{}", map);
		return JSONObject.toJSONString(map);
	}
	
	
	/**
	 * 微信
	 * 关闭订单
	 * @param out_trade_no
	 * @throws Exception 
	 * @throws  
	 */
	public boolean wechatCloseTrade(String out_trade_no,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxCloseTradeParams(out_trade_no,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatCloseTrade-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/pay/closeorder", RequestMethod.POST.toString(), new StringEntity(xmlStr,"UTF-8"));
		logger.debug("wechatCloseTrade - respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		return m != null && m.get("return_code").equalsIgnoreCase(PayParams.success) && m.get("result_code").equalsIgnoreCase(PayParams.success);
	}
	
	/**
	 * 微信
	 * 查询订单
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 */
	public  Map<String, String> wechatQueryTrade(String out_trade_no,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxQueryTradeParams(out_trade_no,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatQueryTrade-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/pay/orderquery", RequestMethod.POST.toString(), new StringEntity(xmlStr,"UTF-8"));
		logger.debug("wechatQueryTrade - respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		return m;
	}

	/**
	 * 退款
	 * @return true 退款申请成功，false：退款失败
	 * @throws Exception
	 * @param out_trade_no
	 *            商户侧传给微信的订单号
	 * @param out_refund_no
	 *            商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param total_fee
	 *            订单总金额，单位为分，只能为整数
	 * @param refund_fee
	 *            退款总金额，单位为分 ,只能为整数
	 * 相同的退款单号重复退款返回结果一样
	 * 
	 */
	public  boolean wechatRefund(String out_trade_no, String out_refund_no, Integer total_fee,Integer refund_fee,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxRefundPayParams(out_trade_no, out_refund_no, total_fee, refund_fee,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatRefund-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/secapi/pay/refund",HttpMethod.POST.toString(), 
				new StringEntity(xmlStr,PayConfig.utf_8),payConfig.pay_wx_mch_id_c,payConfig.pay_wx_https_certPath_c);
		logger.debug("wechatRefund-respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		if(m.get("return_code").toString().equalsIgnoreCase(PayParams.success) && m.get("result_code").toString().equalsIgnoreCase(PayParams.success)){
			return true;
		}
//		logger.error("wechatRefund-respStr:{}",respStr);
		return false;
	}

	/**
	 * 微信
	 * 查询退款进度
	 * @param out_refund_no 退款单号
	 * @return
	 * @throws Exception
	 */
	public  Map<String, String> wechatQueryRefundByOutRefundNo(String out_refund_no,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxQueryRefundParams(out_refund_no,1,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatQueryRefund-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/pay/refundquery",HttpMethod.POST.toString(), new StringEntity(xmlStr,PayConfig.utf_8));
		logger.debug("wechatQueryRefund-respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		return m;
	}
	
	/**
	 * 微信
	 * 查询退款进度
	 * @param out_trade_no 订单号
	 * @param out_refund_no 退款单号
	 * @return
	 * @throws Exception
	 */
	public  boolean wechatQueryRefundByOutTradeNo(String out_trade_no,String out_refund_no,Integer whereIsApp) throws Exception {
		Map<String, Object> map = payParams.wxQueryRefundParams(out_trade_no,2,whereIsApp);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + payConfig.pay_wx_key_c).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatQueryRefund-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/pay/refundquery",HttpMethod.POST.toString(), new StringEntity(xmlStr,PayConfig.utf_8));
		logger.debug("wechatQueryRefund-respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		if(m.get("return_code").equalsIgnoreCase(PayParams.success) && 
				m.get("result_code").equalsIgnoreCase(PayParams.success)){
//			int refund_count = Integer.parseInt(m.get("refund_count"));
			int refund_fee = Integer.parseInt(m.get("refund_fee"));
			int cash_fee = Integer.parseInt(m.get("cash_fee"));
			if( cash_fee <= refund_fee){
				return true;
			}
			/*for (int i = 0; i < refund_count; i++) {
				if(m.get("out_refund_no_" + i).equals(out_refund_no)){
					return m.get("refund_status_").equals(PayParams.success) ;
				}
			}*/
		}
		
		return false;
	}
	
	/**
	 * 微信
	 * 企业向用户支付
	 * @param partner_trade_no 订单号
	 * @param openid 第三方openid
	 * @param re_user_name 用户真实姓名
	 * @param amount 付款金额(单位为：分)
	 * @param desc 企业操作说明
	 * @param spbill_create_ip 调用接口的机器ip
	 * @return
	 */
	public Map<String, String> wechatEnterprisePay(String partner_trade_no,String openid,String re_user_name
			,Integer amount,String desc,Integer bc) throws Exception  {
//		Map<String, Object> map = payParams.wxEnterprisePay(partner_trade_no, openid, re_user_name, amount, desc, spbill_create_ip,appid,mch_id);
//		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + key).toUpperCase());
//		String xmlStr = XMLParser.converterMapToXml(map);
//		logger.debug("wechatEnterprisePay-xmlStr:{}",xmlStr);
//		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", HttpMethod.POST.toString(), new StringEntity(xmlStr,PayConfig.utf_8),
//				mch_id, https_certPath);
//		logger.debug("wechatEnterprisePay-respStr:{}",respStr);
//		Map<String, String> m = XMLParser.getMapFromXML(respStr);
//		return m;
		if(ip == null){
			InetAddress ia = InetAddress.getLocalHost();
			ip = ia.getHostAddress();
		}
		Map<String, String> map = null;
		switch (bc) {
		case 1:
			 map = wechatEnterprisePay0(partner_trade_no, openid, re_user_name, amount, desc, ip, payConfig.pay_wx_appid_b,payConfig.pay_wx_mch_id_b,payConfig.pay_wx_https_certPath_b,payConfig.pay_wx_key_b);
			break;
		case 2:
			map = wechatEnterprisePay0(partner_trade_no, openid, re_user_name, amount, desc, ip, payConfig.pay_wx_appid_c,payConfig.pay_wx_mch_id_c,payConfig.pay_wx_https_certPath_c,payConfig.pay_wx_key_c);
			break;
		}
		return map;
	}
	
	private Map<String,String> wechatEnterprisePay0(String partner_trade_no,String openid,String re_user_name
			,Integer amount,String desc,String spbill_create_ip,String appid ,String mch_id,String https_certPath,String key) throws Exception{
		Map<String, Object> map = payParams.wxEnterprisePay(partner_trade_no, openid, re_user_name, amount, desc, spbill_create_ip,appid,mch_id);
		map.put(PayParams.sign, Sign.md5Sign(PayParams.signStr(map, PayParams.sign), PayParams.keyStr + key).toUpperCase());
		String xmlStr = XMLParser.converterMapToXml(map);
		logger.debug("wechatEnterprisePay-xmlStr:{}",xmlStr);
		String respStr = httpUtils.execute("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", HttpMethod.POST.toString(), new StringEntity(xmlStr,PayConfig.utf_8),
				mch_id, https_certPath);
		logger.debug("wechatEnterprisePay-respStr:{}",respStr);
		Map<String, String> m = XMLParser.getMapFromXML(respStr);
		return m;
	}
	
	
	//=================================================================支付宝=============================================================
	
	/**
	 * 支付宝
	 * app端调起支付的参数生成
	 * @param out_trade_no
	 *            订单号
	 * @param subject
	 *            商品名称
	 * @param total_fee
	 *            总金额(单位：元)
	 * @param body
	 *            商品详情(最大长度512)
	 * @return
	 * @throws Exception 
	 */
	public String aliPayParam(String out_trade_no, String subject, String total_fee,String hp_service ) throws Exception{
		Map<String, Object> map = payParams.aliPayParams(out_trade_no, subject, total_fee,hp_service);
		map.put(PayParams.sign, URLEncoder.encode(Sign.rsaSign(PayParams.signStr0(map, PayParams.sign,PayParams.sign_type), payConfig.pay_ali_privateKey, PayConfig.utf_8),PayConfig.utf_8));
		logger.debug("aliPayParam-map:{}" , map);
		String signStr = PayParams.signStr0(map); 
		logger.info("aliPayParam-return:{}",signStr);
		return signStr;
	}
	
	/**
	 * 支付宝
	 * 无密退款
	 * @param out_request_no 退款号
	 * @param out_trade_no 订单号
	 * @param refund_fee 退款金额 单位：元
	 * @return
	 * @throws Exception
	 */
	public  boolean aliRefund(String out_request_no,String out_trade_no,String refund_fee) throws Exception{
		Map<String, Object> map = payParams.aliRefundNoPwd(out_request_no, out_trade_no, refund_fee);
		map.put(PayParams.sign, Sign.rsaSign(PayParams.signStr(map, PayParams.sign), payConfig.pay_ali_privateKey,  PayConfig.utf_8));
		logger.debug("aliRefund-map:{}",map);
		List<NameValuePair> m = new ArrayList<NameValuePair>();
		for (Entry<String, Object> ent : map.entrySet()) {
			m.add(new BasicNameValuePair(ent.getKey(), (String) ent.getValue()) );
		}
		String respStr = httpUtils.execute("https://openapi.alipay.com/gateway.do", RequestMethod.POST.toString(), new UrlEncodedFormEntity(m, PayConfig.utf_8));
		logger.debug(respStr);
		JSONObject json = JSONObject.parseObject(respStr).getJSONObject("alipay_trade_refund_response");
		return json.getString("code").equals("10000");
	}
	
	
	/**
	 * @param out_request_no 退款号
	 * @param out_trade_no 订单号
	 * @throws Exception 
	 * 
	 * 异常说明
	 * 
	 * 退款号存在的情况下
	 * {
		    "alipay_trade_fastpay_refund_query_response": {
		        "code": "10000",
		        "msg": "Success",
		        "out_request_no": "201611060851440000006563",
		        "out_trade_no": "161106019513818",
		        "refund_amount": "190.00",
		        "total_amount": "190.00",
		        "trade_no": "2016110621001004360242723547"
		    },
		    "sign": "ES2bmmqTNG/80zErJBNuywnic7jGRElSlmCHMHpP1BUtHhJggaB1Ps6KNY4xTgyRqoOJTaM7XpE7Zr7g4RTVMBgisXGQzpddyUNmUWchD9yhJvg+w8koWRFJiqPcsol7K6aGtK1DSzsSHV0w2AZXvDvaj4gNkYZrnvnduNcEJ3c="
		}
	 *
	 * 退款号不存在的情况下
	 * 
	 * {
		    "alipay_trade_fastpay_refund_query_response": {
		        "code": "10000",
		        "msg": "Success"
		    },
		    "sign": "PK9mGX8uy3xfwRKygPxPCuf62YQU5JrdY4yDSn5dRhRcjtcTlzm8Gzv1yhnNXOhyITDzY6O8+rwPpSUqrQXT3VvS6dkMvEnoXsT5uKefRKX9PuvwbttN5Vc7Me57aPX9dReiBji4HgBVMI22gcugZ8mBTuNIecYNAAxiVcZjUFQ="
		}
	 * 
	 */
	public boolean aliRefundQuery(String out_trade_no ,String out_request_no) throws Exception {
		Map<String, Object> map = payParams.aliRefundQuery(out_trade_no,out_request_no);
		map.put(PayParams.sign, Sign.rsaSign(PayParams.signStr(map, PayParams.sign), payConfig.pay_ali_privateKey,  PayConfig.utf_8));
		logger.debug("aliRefundQuery-map:{}",map);
		List<NameValuePair> m = new ArrayList<NameValuePair>();
		for (Entry<String, Object> ent : map.entrySet()) {
			m.add(new BasicNameValuePair(ent.getKey(), (String) ent.getValue()) );
		}
		String respStr = httpUtils.execute("https://openapi.alipay.com/gateway.do", RequestMethod.POST.toString(),new UrlEncodedFormEntity(m, PayConfig.utf_8));
		logger.debug(respStr);
		JSONObject json = JSONObject.parseObject(respStr).getJSONObject("alipay_trade_fastpay_refund_query_response");
		return json.getString("code").equals("10000") && json.getString("out_request_no") != null; 
	}
	
	/**
	 * 支付宝
	 * 订单查询
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 * 
	 * eg：
	 * 成功
	 * {"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"186****0365",
	 * 						"buyer_pay_amount":"0.00","buyer_user_id":"2088802641118377","invoice_amount":"0.00"
	 * 							,"open_id":"20881083869477745278895580514037","out_trade_no":"201608156528000030569951"
	 * 						,"point_amount":"0.00","receipt_amount":"0.00","send_pay_date":"2016-08-15 20:44:09","total_amount":"0.02"
	 * 						,"trade_no":"2016081521001004370200646413","trade_status":"TRADE_SUCCESS"}
	 * ,"sign":"UHDWGONbbUJBZS0yq7VzaBCEJjN8wbo9U9a4QQKXv6FTt8tRrSOWE2ijPi9rft+E1u0Du3ItKzoAt34aDzS8H2uarGrz22P6MA8hEhneFe09n7i0SfbLQ2F31h1oIyWsZohmlNzK6NlmQusAMUH2KHqo01T80VUAdoi6nYEbp1s="}
	 * 
	 * 失败
	 * {"alipay_trade_query_response":{"code":"40004","msg":"Business Failed","sub_code":"ACQ.TRADE_NOT_EXIST","sub_msg":"交易不存在","buyer_pay_amount":"0.00","invoice_amount":"0.00","out_trade_no":"2016081565280000305699511","point_amount":"0.00","receipt_amount":"0.00"},"sign":"Pn5ZREWKymLEukuSqzTt/3puvQgn+ZwkIfTl8KucKUz1dUk1OJmCXutr30BlT88Balw1yEwDPYm3g9ceCfTZd7BJ4x4EU3Wls8tNN1Ow9WVrHCsjbXd9h2ecxqPT/lZnqhHVhVCwgWPkcG6VZx+wFwWpOGmlMf1ldO+R0uecOyU="}
	 * 
	 */
	public  String aliQueryTrade(String out_trade_no) throws Exception {
		Map<String, Object> map = payParams.aliQueryTrade(out_trade_no);
		map.put(PayParams.sign, Sign.rsaSign(PayParams.signStr(map, PayParams.sign), payConfig.pay_ali_privateKey,  PayConfig.utf_8));
		logger.debug("aliQueryTrade-map:{}",map);
		List<NameValuePair> m = new ArrayList<NameValuePair>();
		for (Entry<String, Object> ent : map.entrySet()) {
			m.add(new BasicNameValuePair(ent.getKey(), (String) ent.getValue()) );
		}
		String respStr = httpUtils.execute("https://openapi.alipay.com/gateway.do", RequestMethod.POST.toString(), new UrlEncodedFormEntity(m, PayConfig.utf_8));
		logger.debug(respStr);
		return respStr;
	}
	
	/**
	 * 支付宝
	 * 关闭订单
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 * 
	 * eg：
	 * 成功
	 * {"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"186****0365",
	 * 						"buyer_pay_amount":"0.00","buyer_user_id":"2088802641118377","invoice_amount":"0.00"
	 * 							,"open_id":"20881083869477745278895580514037","out_trade_no":"201608156528000030569951"
	 * 						,"point_amount":"0.00","receipt_amount":"0.00","send_pay_date":"2016-08-15 20:44:09","total_amount":"0.02"
	 * 						,"trade_no":"2016081521001004370200646413","trade_status":"TRADE_SUCCESS"}
	 * ,"sign":"UHDWGONbbUJBZS0yq7VzaBCEJjN8wbo9U9a4QQKXv6FTt8tRrSOWE2ijPi9rft+E1u0Du3ItKzoAt34aDzS8H2uarGrz22P6MA8hEhneFe09n7i0SfbLQ2F31h1oIyWsZohmlNzK6NlmQusAMUH2KHqo01T80VUAdoi6nYEbp1s="}
	 * 
	 * 失败
	 * {"alipay_trade_query_response":{"code":"40004","msg":"Business Failed","sub_code":"ACQ.TRADE_NOT_EXIST","sub_msg":"交易不存在","buyer_pay_amount":"0.00","invoice_amount":"0.00","out_trade_no":"2016081565280000305699511","point_amount":"0.00","receipt_amount":"0.00"},"sign":"Pn5ZREWKymLEukuSqzTt/3puvQgn+ZwkIfTl8KucKUz1dUk1OJmCXutr30BlT88Balw1yEwDPYm3g9ceCfTZd7BJ4x4EU3Wls8tNN1Ow9WVrHCsjbXd9h2ecxqPT/lZnqhHVhVCwgWPkcG6VZx+wFwWpOGmlMf1ldO+R0uecOyU="}
	 * 
	 */
	public  boolean aliCloseTrade(String out_trade_no) throws Exception {
		Map<String, Object> map = payParams.aliCloseTrade(out_trade_no);
		map.put(PayParams.sign, Sign.rsaSign(PayParams.signStr(map, PayParams.sign), payConfig.pay_ali_privateKey,  PayConfig.utf_8));
		logger.debug("aliQueryTrade-map:{}",map);
		List<NameValuePair> m = new ArrayList<NameValuePair>();
		for (Entry<String, Object> ent : map.entrySet()) {
			m.add(new BasicNameValuePair(ent.getKey(), (String) ent.getValue()) );
		}
		String respStr = httpUtils.execute("https://openapi.alipay.com/gateway.do", RequestMethod.POST.toString(), new UrlEncodedFormEntity(m, PayConfig.utf_8));
		logger.debug(respStr);
		return respStr != null 
				&& JSONObject.parseObject(respStr).getJSONObject("alipay_trade_close_response") != null
				&& JSONObject.parseObject(respStr).getJSONObject("alipay_trade_close_response").getString("code").equals("10000") ;
	}
	
	/**
	 * 支付宝
	 * notify_id校验
	 * @param notify_id
	 * @return true 正确，反之则错误
	 * @throws Exception
	 */
	public  String notifyVerify(String notify_id) throws Exception{
		return httpUtils.execute("https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088421306777403&notify_id=", RequestMethod.GET.toString(), null);
	}

	
	
	
	
	
	
	
	
//	=====================================test======================================
	
	
	
	public static void main(String[] args) throws Exception {
//		 pay = new PayService();			  2016072021001004370255587643
		//退款
//		 System.out.println(PayService.aliRefund("2", "12", "0.01"));
		 //查询
//		 PayService.aliQueryTrade("11");
//		System.out.println(notifyVerify("61d295d47c85ccf55bdb57fcfc365f1iuu"));
		
		 //统一下单
		//微信退款
//		System.out.println(PayService.wechatRefund("201601147", "5", 5, 1));
		//微信订单查询
//		System.out.println(PayService.wechatQueryTrade("201601147"));
		//微信退款查询
//		System.out.println(PayService.wechatQueryRefund("201601147"));
		
	}

	/**
	 * @param orderId
	 * @throws Exception 
	 */
	public boolean closeTrade(String orderId,int tradePlatform,Integer whereIsApp) throws Exception {
		if(tradePlatform == 1){
			return wechatCloseTrade(orderId,whereIsApp);
		}else if(tradePlatform == 2){
			return aliCloseTrade(orderId);
		}
		return false;
	}

}
