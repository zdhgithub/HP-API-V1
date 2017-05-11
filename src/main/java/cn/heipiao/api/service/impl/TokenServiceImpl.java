/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.oss.SignGetToken;
import cn.heipiao.api.oss.StsGetToken;
import cn.heipiao.api.pay.HttpUtils;
import cn.heipiao.api.pay.PayConfig;
import cn.heipiao.api.pojo.Token;
import cn.heipiao.api.redis.RedisConfig;
import cn.heipiao.api.redis.RedisPool;
import cn.heipiao.api.service.CacheService;
import cn.heipiao.api.service.TokenService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年7月11日
 * @version 1.0
 */
@Service
public class TokenServiceImpl implements TokenService {

	private final static Logger logger  = LoggerFactory.getLogger(TokenServiceImpl.class);
	
	private static final String tokenKey = "oss_sts";
	
	private static final String wxMiniAccessToken = "wx_mini_access_token";
	
	@Resource
	private StsGetToken stsGetToken;
	
	@Resource
	private CacheService cacheService;
	
	@Resource
	private SignGetToken signGetToken;
	
	@Resource
	private HttpUtils http;
	
	@Resource
	private PayConfig payConfig;
	
	@Resource
	private RedisPool redisPool;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.TokenService#selectByName(java.lang.String)
	 */
	@Override
//	@Cacheable(value = { "dataCache" },key = "#p0")
	public Token selectByName(String name,String roleSessionName) throws Exception {
		Token token = null;
		String tokenStr = cacheService.get(tokenKey, RedisConfig.regoinDB);
		if(tokenStr == null){
			token = update(name, roleSessionName);
		}else{
			token = JSONObject.parseObject(tokenStr, Token.class);
			//控制50分钟更新一次
			if(ExDateUtils.getDate().getTime() / 1000 > token.getExpiration() - 10 * 60){
				synchronized (this) {
					String tStr = cacheService.get(tokenKey, RedisConfig.regoinDB);
					token = JSONObject.parseObject(tStr, Token.class);
					if(ExDateUtils.getDate().getTime() / 1000 > token.getExpiration() - 10 * 60){
						token = update(StsGetToken.oss_sts, roleSessionName);
					}
				}
			}
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.TokenService#insert(cn.heipiao.api.pojo.Token)
	 */
//	@Override
////	@CachePut(value = { "dataCache" },key = "#p0")
//	public synchronized Token insert(String name,String roleSessionName) throws Exception {
//		Token token = stsGetToken.getStsToken(roleSessionName);
//		cacheService.put(tokenKey, JSONObject.toJSONString(token), RedisConfig.regoinDB);
//		return token;
//	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.TokenService#update(cn.heipiao.api.pojo.Token)
	 */
	@Override
//	@CachePut(value = { "dataCache" },key = "#p0")
	public synchronized Token update(String name,String roleSessionName) throws Exception {
		Token token = stsGetToken.getStsToken(roleSessionName);
		cacheService.put(tokenKey, JSONObject.toJSONString(token), RedisConfig.regoinDB);
		return token;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.TokenService#getTokenBySign()
	 */
	@Override
	public Map<String, String> getTokenBySign(String bucket, String dir) throws Exception {
		return signGetToken.getToken(bucket, dir);
	}

	@Override
	public String getWxaQrcode(String path) throws Exception {
		String result = getQrcodeImage(path);
		//判断token无效
		if(result.contains("\"errcode\"")){
			put(wxMiniAccessToken, RedisConfig.regoinDB);
			result = getQrcodeImage(path);
		}
		return result;
	}

	
	private String getQrcodeImage(String path) throws Exception{
		String ac = getAccessToken(wxMiniAccessToken,RedisConfig.regoinDB);
		logger.info(ac);
		String result = http.execute("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + ac, "post", 
				new StringEntity("{\"path\":\"" + path + "\",\"width\":430}", PayConfig.utf_8));
		return result;
	}
	
	private String getAccessToken(String key,int DB) throws Exception {
		String ac = null;
		String val = cacheService.get(key,DB);
		if(val != null){
			JSONObject json = JSONObject.parseObject(val);
			int ex = json.getInteger("expires_in");
			//判断是否过期
			if(ExDateUtils.getCalendar().getTimeInMillis() / 1000 - ex > 3600){
				ac = put(key, DB);
			}else{
				ac = json.getString("access_token");
			}
		}else{
			ac = put(key ,DB);
		}
		return ac;
	}
	
	private String put(String key,int DB) throws Exception{
		String resp = getAccessToken();
		JSONObject rJson = JSONObject.parseObject(resp);
		rJson.put("expires_in", ExDateUtils.getCalendar().getTimeInMillis() / 1000);
		cacheService.put(key, rJson.toJSONString(), DB);
		return rJson.getString("access_token");
	}
	
	private String getAccessToken() throws Exception {
		String result = http.execute("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + 
				payConfig.wx_mini_appid + "&secret=" + payConfig.wx_mini_secret, "get", null);
		logger.info(result);
		return result;
	}

}
