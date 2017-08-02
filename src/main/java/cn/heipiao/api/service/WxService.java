package cn.heipiao.api.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-11
 */
public interface WxService {

	String getAppId();
	
	String getAppSecret();
	
	String getAccessToken() throws Exception;

	String getJsApiTicket() throws Exception;

	String CreateNoncestr();

	String createPaySign(HashMap<String, String> params) throws UnsupportedEncodingException ;

}
