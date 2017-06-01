package cn.heipiao.api.service.impl;

import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.heipiao.api.pay.HttpUtils;
import cn.heipiao.api.service.SmsService;

/**
 * @说明 港澳资讯短信平台
 * @author chenwenye
 * @version heipiao1.0 2016年8月8日
 */
@Service
public class SmsServiceImpl3 implements SmsService {

	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl3.class);

	/**
	 * 账号：102569 密码：hpdyHPDY0812
	 */

	/** 短信发送请求地址 **/
	private static final String SMS_URL = "http://59.50.95.232:8050/SendSms2014.aspx";

	/** 用户代码 **/
	private static final String USER_SPID = "102569";

	/** 密码 **/
	private static final String PASSWORD = "HPDYHPDY0812";
	
	@Resource
	private HttpUtils httpUtils;

	@Override
	public boolean send(Map<String, String> _params) throws Exception {
		/*
		 * Map<String, Object> params = new LinkedMap<String, Object>();
		 * params.put("mobiles", _params.get("mobile")); params.put("sms",
		 * encode(_params.get("content"))); params.put("spid", USER_SPID);
		 * params.put("pwd", PASSWORD);
		 */
		/*
		 * String resp = httpUtils.execute(SMS_URL, "POST", new
		 * UrlEncodedFormEntity(HttpUtils.getNameValuePair(params), "GBK"));
		 */
		String param = "sms=" + URLEncoder.encode(Base64.encodeBase64String(_params.get("content").getBytes("GBK")), "UTF-8")
		+ "&mobiles=" + _params.get("mobile") + "&spid=" + USER_SPID + "&pwd=" + PASSWORD ;

//		String statusCode = connectURL(param, SMS_URL);
		
		String statusCode = httpUtils.execute(SMS_URL + "?" + param, "post", null);

		if (statusCode != null) {
			if (statusCode.indexOf("<Result>0</Result>") > -1) {
				log.info(param + "服务器接收成功!");
			} else {
				if (statusCode.indexOf(" <Result>1</Result>") > -1) {
					log.error("流水号seq重复!");
				} else if (statusCode.indexOf("<Result>-1</Result>") > -1) {
					log.error("spid、pwd、mobiles、sms不能为空!");
				} else if (statusCode.indexOf("<Result>-2</Result>>") > -1) {
					log.error("账户被禁用!");
				} else if (statusCode.indexOf("<Result>-3</Result>") > -1) {
					log.error("短信余额不足!");
				} else if (statusCode.indexOf("<Result>-4</Result>") > -1) {
					log.error("帐号或密码非法!");
				} else if (statusCode.indexOf("<Result>-12</Result>") > -1) {
					log.error("其他异常!");
				}
			}
		}

		return true;
	}

}
