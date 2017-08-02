package cn.heipiao.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pay.HttpUtils;
import cn.heipiao.api.service.WxService;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-11
 */
@Service
public class WxServiceImpl implements WxService {
	
	private static final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

	private Object[] lock = {};
	private static String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token";
	private static String JSAPI_TICKET_API = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	private Date accessTokenExpireTime;

	@Resource
	private HttpUtils httpUtils;
	
	//访问页面domain
	@Value("${fodder.content.AppID}")
	private String appId;
	
	//访问页面路径
	@Value("${fodder.content.AppSecret}")
	private String appSecret;
	
	private String accessToken;
	
	private String jsapiTicket;
	

	@Override
	public String getAppId() {
		return appId;
	}

	@Override
	public String getAppSecret() {
		return appSecret;
	}
	
	@Override
	public String getJsApiTicket() throws Exception {
		if (jsapiTicket == null || isExpire()) {
			obtainAccessToken();
		}
		return jsapiTicket;
	}
	
	@Override
	public String getAccessToken() throws Exception {
		if (accessToken == null || isExpire()) {
			obtainAccessToken();
		}
		return accessToken;
	}

	private boolean isExpire() {
		synchronized (lock) {
			return accessTokenExpireTime == null ||
					System.currentTimeMillis() - accessTokenExpireTime.getTime() > 10000;
		}
	}
	
	/**
	 * 重新获取 AccessToken
	 * @throws Exception 
	 */
	public void obtainAccessToken() throws Exception {
		synchronized (lock) {
			if (isExpire()) {
	    		long startTime = System.currentTimeMillis();
	    		HashMap<String, String> paras = new HashMap<>(4);
	    		paras.put("grant_type", "client_credential");
	    		paras.put("appid", appId);
	    		paras.put("secret", appSecret);
	    		String accessTokenApi = assembleGetUrl(ACCESS_TOKEN_API, paras);
	    		
	    		String apiResult = httpUtils.execute(accessTokenApi, RequestMethod.GET.toString(), null);
	    		Map<String, Object> jo = transformToJsonAndcheck(apiResult);
	    		this.accessToken = toChars(jo.get("access_token"));
	    		this.jsapiTicket = getJsapiTicket(this.accessToken);
	    		int expiresIn = ((Number)jo.get("expires_in")).intValue();
	    		this.accessTokenExpireTime = new Date(startTime + (expiresIn * 1000));
			}
		}
	}

	/**
	 * 组装get访问链接
	 * @param url
	 * @return
	 */
	private String assembleGetUrl(String url, HashMap<String, String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(url).append("?");
		for(Map.Entry<String, String> entry: params.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return sb.toString();
	}

	/**
	 * 获取JsapiTicket
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	private String getJsapiTicket(String accessToken) throws Exception {
		HashMap<String, String> paras = new HashMap<>(4);
		paras.put("access_token", accessToken);
		paras.put("type", "jsapi");
		String jsApiTicketApi = assembleGetUrl(JSAPI_TICKET_API, paras);
		
		String apiResult = httpUtils.execute(jsApiTicketApi, RequestMethod.GET.toString(), null);
		Map<String, Object> jo = transformToJsonAndcheck(apiResult);
		return toChars(jo.get("ticket"));
	}

	/**
	 * 对象转换为String
	 * @param object
	 * @return
	 */
	private String toChars(Object object) {
		if (object == null) return null;
		else {
			if (object instanceof Calendar) return toChars(((Calendar)object).getTime());
			else if (object instanceof Date) return toChars((Date)object);
			else return object.toString();
		}
	}

	@Override
	public String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 签名生成方法
	 */
	@Override
	public String createPaySign(HashMap<String, String> bizObj) throws UnsupportedEncodingException {
		HashMap<String, String> bizParameters = new HashMap<String, String>();

		List<Map.Entry<String, String>> infoIds = 
				new ArrayList<Map.Entry<String, String>>(bizObj.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			if (item.getKey() != "") {
				bizParameters.put(item.getKey().toLowerCase(), item.getValue());
			}
		}
		String bizString = FormatBizQueryParaMap(bizParameters, false);
		// System.out.println(bizString);

		return DigestUtils.sha1Hex(bizString);
	}


	public static String FormatBizQueryParaMap(HashMap<String, String> paraMap,
			boolean urlencode) throws UnsupportedEncodingException {

		String buff = "";
		List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
				paraMap.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, String> item = infoIds.get(i);
			// System.out.println(item.getKey());
			if (item.getKey() != "") {
				String key = item.getKey();
				String val = item.getValue();
				if (urlencode) {
					val = URLEncoder.encode(val, "utf-8");
				}
				// buff += key.toLowerCase() + "=" + val + "&";
				buff += key + "=" + val + "&";
			}
		}
		if (buff.isEmpty() == false) {
			buff = buff.substring(0, buff.length() - 1);
		}
		logger.debug("排序后等待签名的字符串:{}", buff);
		return buff;
	}
	
	/**
	 * 检查并转换接口返回的数据，供具体的接口处理方法解释
	 * 
	 * @param wcResult 微信公众平台返回的数据
	 * @return　转换后的JSON格式对象
	 * @throws WechatApiException 如果微信公众平台服务器返回了异常消息
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> transformToJsonAndcheck(String wcResult) {
		logger.debug("微信接口返回: {}", wcResult);
		Map<String, Object> rsMap = null;
		rsMap = (Map<String, Object>)parseString2Map(wcResult);
		return rsMap;
	}
	
	/**
	 * 解析json字串到Map
	 * @param sJson
	 * @return
	 */
	private Map<?, ?> parseString2Map(String sJson){
		return JSONObject.toJavaObject(JSONObject.parseObject(sJson), Map.class);
	}
	
}
