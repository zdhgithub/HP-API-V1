package cn.heipiao.api.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.WxService;

/**
 * 提供内容文章的所有功能
 * Provide all functions of content and article
 * 
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-01
 */
//@Path("/wx")
//@Produces({ MediaType.APPLICATION_JSON})
//@Consumes({MediaType.APPLICATION_JSON})
//@Component
@RestController
@RequestMapping(value = "wx",produces="application/json")
public class WxResource {
	private static final Logger logger = LoggerFactory.getLogger(WxResource.class);
	
	@Resource
	private WxService wxService;
	
	/**
	 * 获取微信分享调用JS_API配置参数
	 * @return 
	 */
//	@POST
//	@Path("jsapi/params")
	@RequestMapping(value = "jsapi/params",method = RequestMethod.POST,consumes = "application/json")
	public String getArticles(@RequestBody JSONObject json) {
		try {
			Map<?, ?> pojo = json == null ? null : JSONObject.toJavaObject(json, Map.class);
			String url = (String)pojo.get("url");
			url = checkURL(url);
			if(StringUtils.isBlank(url)){
				getErrorRet(Status.value_is_null_or_error);
			}
			String jsapiTicket = wxService.getJsApiTicket();
			String appid = wxService.getAppId();
			
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("noncestr", wxService.CreateNoncestr());
			params.put("timestamp", String.valueOf(new Date().getTime() / 1000));
			params.put("jsapi_ticket", jsapiTicket);
			params.put("url", url);
			logger.debug("生成JSAPITICKET的签名参数:{}", params);
			String sign = wxService.createPaySign(params);
			
			Map<String, Object> result = new HashMap<>();
			result.put("appId", appid);
			result.put("timestamp", params.get("timestamp"));
			result.put("noncestr", params.get("noncestr"));
			result.put("signature", sign);
			return getRightRet(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return getErrorRet(Status.error);
		}
	}


	
	/**
	 * 检查传递的URL的有效性
	 * @param url
	 * @return
	 */
	private String checkURL(String url) {
		if (url == null) {
			return null;
		} else {
			try {
				url = URLDecoder.decode(url, "utf-8");
				return url;
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	}

	// 返回对象
	private String getRightRet(Object o){
		return JSONObject.toJSONString(new RespMsg<>(o));
	}
	
	// 返回错误
	private String getErrorRet(int ERROR_CODE){
		return JSONObject.toJSONString(new RespMsg<>(ERROR_CODE, RespMessage.getRespMsg(ERROR_CODE)));
	}
}
