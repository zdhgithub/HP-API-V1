/**
 * 
 */
package cn.heipiao.api.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 支付参数配置类
 * @author wzw
 * @date 2016年7月26日
 * @version 1.0
 */
@Component
public class PayConfig {
	
	public static final String utf_8 = "UTF-8";

	@Value("${pay.ali.partner}")
	public String pay_ali_partner;

	@Value("${pay.ali.privateKey}")
	public String pay_ali_privateKey;
	
	@Value("${pay.ali.alipayPublicKey}")
	public String pay_ali_alipayPublicKey;
	
	@Value("${pay.ali.notify_url}")
	public String pay_ali_notify_url;
	
	@Value("${pay.ali.sign_type}")
	public String pay_ali_sign_type;
	
	@Value("${pay.ali.app_id_c}")
	public String pay_ali_app_id_c;
	
	@Value("${pay.ali.app_id_b}")
	public String pay_ali_app_id_b;
	
	
	
	
	@Value("${pay.wx.appid_c}")
	public String pay_wx_appid_c;
	
	@Value("${pay.wx.appSecret_c}")
	public String pay_wx_appSecret_c;
	
	@Value("${pay.wx.mch_id_c}")
	public String pay_wx_mch_id_c;
	
	@Value("${pay.wx.https.certPath_c}")
	public String pay_wx_https_certPath_c;
	
	@Value("${pay.wx.key_c}")
	public String pay_wx_key_c;
	
	@Value("${pay.wx.appid_b}")
	public String pay_wx_appid_b;
	
	@Value("${pay.wx.mch_id_b}")
	public String pay_wx_mch_id_b;
	
	@Value("${pay.wx.https.certPath_b}")
	public String pay_wx_https_certPath_b;
	
	@Value("${pay.wx.key_b}")
	public String pay_wx_key_b;
	
	@Value("${pay.wx.notify_url}")
	public String pay_wx_notify_url;
	
	
//	============微信小程序参数============================
	@Value("${wx.mini.appid}")
	public String wx_mini_appid ;
	
	@Value("${wx.mini.secret}")
	public String wx_mini_secret;
	
	

}
