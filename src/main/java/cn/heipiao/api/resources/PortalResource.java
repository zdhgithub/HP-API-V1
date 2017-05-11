package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.redis.RedisConfig;
import cn.heipiao.api.service.CacheService;
import cn.heipiao.api.service.SmsService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.RandomNumberUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 说明 : 短信接口Resource 功能 : a. 提供短信验证码发送功能
 * 
 * @author chenwenye
 * @since 2016-6-14 heipiao1.0
 */
@Api(tags = "短信发送模块")
@RestController
@RequestMapping(value = "portal",produces="application/json")
public final class PortalResource {

	
	public static final String wxMini = "wxMini";
	
	public static final String login = "login";

	public static final String register = "register";
	
	public static final String findPwd = "findPwd";
	
	public static final String chkmobile = "chkmobile";
	
	public static final String binding = "binding";
	
	@Resource
	private UserOpService userOpService;
	
	@Resource
	private CacheService ehCache;

	@Resource
	private SmsService smsService;
	/** 日志 **/
	public static final Logger log = LoggerFactory.getLogger(PortalResource.class);

	/**
	 * 
	 * 功能 :
	 * 
	 * @param sign
	 *            安全签名
	 * @param text
	 *            短信内容
	 * @param outTradeNo
	 *            短信流水号-18位
	 * @param partner
	 *            商户号
	 * @param mobile
	 *            手机号码数组-支持多个-可通常情况只会有一个
	 * @return
	 */
//	@Path("sms/{type}")
//	@POST
	@ApiOperation(value = "发送验证码",notes = "参数说明：type的值以及对应的body体参数"
			+ "\n\r type  body体"
			+ "\n\r 绑定手机号  binding   {\"mobile\":\"158****1111\"}"
			+ "\n\r 微信小程序 wxMini {\"userInfo\":\"\",\"code\":\"\",\"mobile\":\"158****1111\"}"
			+ "\n\r 验证手机号码  chkmobile  {\"mobile\":\"158****1111\"}"
			+ "\n\r 找回密码  findPwd  {\"mobile\":\"158****1111\"}"
			+ "\n\r 注册  register {\"mobile\":\"158****1111\"}"
			+ "\n\r 登陆   login {\"mobile\":\"158****1111\"} ")
	@RequestMapping(value = "sms/{type}",method = RequestMethod.POST,consumes = "application/json")
	public String send(@RequestBody JSONObject param, @PathVariable("type") String type) {
		try {
			log.info("param:{}",param);
			String mobile = param.getString("mobile");

			if (StringUtils.isBlank(mobile)) { // 参数异常
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("mobile", mobile);
			String val = StringUtils.EMPTY;
			switch (type) {
			case login://登陆
				val = getContent(login,mobile);
				break;

			case register://注册
				val = getContent(register,mobile);
				break;
			case findPwd://找回密码
				val = getContent(findPwd,mobile);
				break;
			case chkmobile://验证手机号码
				val = getContent(chkmobile,mobile);
				break;
			case wxMini://微信小程序验证手机号码
//				String code = param.getString("code");
//				String userInfo = param.getString("userInfo");
//				if(code == null || userInfo == null){
//					return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
//							RespMessage.getRespMsg(Status.value_is_null_or_error)));
//				}
//				int rs = verifyWxUserinfo(code,userInfo,mobile);
//				if(rs != Status.success){
//					return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs)));
//				}
//				val = getContent(wxMini,mobile);
//				break;
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"接口错误"));
			case binding://绑定手机号
				User u = userOpService.queryUserByPhone(mobile);
				if(u != null){
					return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"该号码已经被绑定"));
				}
				val = getContent(binding,mobile);
				break;
			default:
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			// break;
			}
			log.info(val);
			params.put("content", val);

			if (!smsService.send(params)) {
				return JSONObject.toJSONString(new RespMsg<>(Status.sms_fail, RespMessage.getRespMsg(Status.sms_fail)));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error, "短信推送服务出错"));
		}
		return JSONObject.toJSONString(new RespMsg<>(Status.success, RespMessage.getRespMsg(Status.success)));
	}

	/**
	 * 微信活动报名
	 * @param param
	 * @return
	 */
//	@Path("sms/wxActivity")
//	@POST
	@RequestMapping(value = "sms/wxActivity",method = RequestMethod.POST,consumes = "application/json")
	public String send(@RequestBody JSONObject param) {
		try {
			log.info("param:{}",param);
			String mobile = param.getString("mobile");

			if (StringUtils.isBlank(mobile)) { 
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}

			Map<String, String> params = new HashMap<String, String>();
			params.put("mobile", mobile);
			
			String code = param.getString("code");
			String userInfo = param.getString("userInfo");
			if(code != null && userInfo != null){
				int rs = verifyWxUserinfo(code,userInfo,mobile);
				if(rs != Status.success){
					return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs)));
				}
				
			}
			
			String val = getContent(wxMini,mobile);
			
			log.info(val);
			params.put("content", val);

			if (!smsService.send(params)) {
				return JSONObject.toJSONString(new RespMsg<>(Status.sms_fail, RespMessage.getRespMsg(Status.sms_fail)));
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error, "短信推送服务出错"));
		}
		return JSONObject.toJSONString(new RespMsg<>(Status.success, RespMessage.getRespMsg(Status.success)));
	}
	
	private int verifyWxUserinfo(String code, String userInfo,String phone) throws Exception {
		//验证手机号是否注册以及绑定
		User pu = userOpService.queryUserByPhone(phone);
		if(pu != null && pu.getUnionId() != null){
			//返回已绑定错误
			return Status.user_wx_bound;
		}
		String result = userOpService.wxUserinfo(userInfo, code);
		JSONObject resultJson = JSONObject.parseObject(result);
		
		//验证微信unionId是否存在并且已绑定
		String unionId = resultJson.getString("unionId"); 
		User wu = userOpService.queryUserByOpenId(unionId);
		if(wu != null){
			//返回其他账户已绑定错误
			return Status.user_wx_be_bound;
		}
		return Status.success;
	}



	private String  getContent(String type,String phone){
		String ver = RandomNumberUtils.getVerifyNum(4);
		ehCache.put(StringUtils.join(type, phone), ver,RedisConfig.smsDB); // 电话号码为key,验证码为value
		return StringUtils.join("验证码：" + ver + "。此验证码10分钟内有效，请尽快返回完成验证。如非本人操作，请忽略本短信。 ");
	}
	
}
