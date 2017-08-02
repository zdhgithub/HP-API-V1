package cn.heipiao.api.resources;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import cn.heipiao.api.constant.ApiConstant.UserConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.TabRelavance;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserOpLog;
import cn.heipiao.api.redis.RedisConfig;
import cn.heipiao.api.service.CacheService;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDigestUtils;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zf
 * @version 1.0
 * @description user资源类
 * @date 2016年6月1日
 */
//@Path("users")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@Component
@Api(tags = "用户模块")
@RestController
@RequestMapping(value = "users",produces="application/json")
public class UserResource {

	/**
	 * 用户登录(钓友)
	 */
	public static final String USER_LOGIN = "user";

	/**
	 * 员工登陆(钓场管理 )
	 */
	public static final String EMP_LOGIN = "emp";

	@Resource
	private UserOpService userOpService;

	@Resource
	private EmpService empService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserResource.class);

	@Resource
	private CacheService cache;
	
	@Resource
	private CampaignService campaignService;

	/**
	 * 查询用户总数
	 * 
	 * @return
	 * @since 1.0
	 */
//	@Path("number")
//	@GET
	@RequestMapping(value = "number",method = RequestMethod.GET)
	public String countUsers() {
		Integer counts = 0;
		try {
			counts = userOpService.queryUsersNum();
			return JSONObject.toJSONString(new RespMsg<String>(counts + ""));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@POST
//	@Path("findpwd")
	@RequestMapping(value = "findpwd",method = RequestMethod.POST,consumes = "application/json")
	public String findPwd(@RequestBody JSONObject json) {
		if (json == null) {
			return JSONObject.toJSONString(new RespMsg<Integer>(
					Status.value_is_null_or_error));
		}
		try {
			logger.debug("json:{}", json.toJSONString());
			String mobile = json.getString("mobile");
			String verify = json.getString("verification_code");
			String ver = (String) cache
					.get(StringUtils.join("findPwd", mobile),RedisConfig.smsDB);
			if (!verify.equals(ver)) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.veriy_code_error));
			}
			User user = userOpService.queryUserByPhone(mobile);
			if (user == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.no_such_user));
			}

			String newPassword = json.getString("newPassword");
			String repeatNewPassword = json.getString("repeatNewPassword");
			if (!passwordVerify(newPassword)
					|| !passwordVerify(repeatNewPassword)) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.pwd_format_error));
			}
			if (!newPassword.equals(repeatNewPassword)) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.pwd_not_agreed));
			}
			User newUser = new User();
			newUser.setId(user.getId());
			newUser.setPassword(ExDigestUtils.sha1Hex(newPassword));
			userOpService.findPwd(newUser);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
	}

	/**
	 * 通过userId查询user信息
	 * 
	 * @param userId
	 * @return
	 * @since 1.0
	 */
//	@Path("user/{userId}")
//	@GET
	@RequestMapping(value = "user/{userId}",method = RequestMethod.GET)
	public String queryUser(@PathVariable("userId") final Long userId) {
		logger.debug("userId:{}", userId);
		User user = null;
		if (userId == null) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		try {
			user = userOpService.queryUserById(userId);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return user == null ? JSONObject.toJSONString( JSONObject
				.toJSONString(new RespMsg<User>(Status.no_such_user,
						RespMessage.getRespMsg(Status.no_such_user))))
				: JSONObject.toJSONString(new RespMsg<User>(user));
	}

	/**
	 * 根据用户名或手机号匹配查询用户
	 * 
	 * @param conditions
	 * @return
	 * @since 1.0
	 */
//	@Path("{conditions}/{start}/{size}")
//	@GET
	@RequestMapping(value = "{conditions}/{start}/{size:\\d+}",method = RequestMethod.GET)
	public String queryUsersByPhoneOrName(
			@PathVariable("conditions") final String conditions,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		if (StringUtils.isEmpty(conditions)) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error));
		}
		List<User> userList = null;
		List<User> usersExt = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("condition", conditions);
			userList = userOpService.queryUserByPhoneOrName(param);

			param.put("start", (start - 1) * size);
			param.put("size", size);
			usersExt = userOpService.queryUserByPhoneOrName(param);
			result.put("data", usersExt);
			result.put("total", userList.size());
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error));
		}
		return JSONObject
				.toJSONString(new RespMsg<Map<String, Object>>(result));
	}

	/**
	 * 查询用户的标签
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @since 1.0
	 */
//	@Path("{userId}/label/{type}")
//	@GET
	@RequestMapping(value = "{userId}/label/{type}",method = RequestMethod.GET)
	public String queryUserLabels(@PathVariable("userId") final String userId,
			@PathVariable("type") final String type) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(type)) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		List<TabRelavance> tabs = null;
		try {
			tabs = userOpService.queryUserLabel(userId, type);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<List<TabRelavance>>(tabs));
	}

	/**
	 * 分页查询会员信息列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 * @since 1.0
	 */
	@RequestMapping(value = "{start}/{size}/{orderRule:[a-zA-Z]*}",method = RequestMethod.GET)
	public String queryUsers(@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size,
			@PathVariable("orderRule") final String orderRule) {
		List<User> userList = null;
		int userNum = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", (start - 1) * size);
		params.put("size", size);
		params.put("orderRule", orderRule);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			userList = userOpService.queryUsers(params);
			userNum = userOpService.queryUsersNum();
			result.put("data", userList);
			result.put("total", userNum);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject
				.toJSONString(new RespMsg<Map<String, Object>>(result));
	}

	/**
	 * 用户注册
	 * 
	 * @param userBean
	 * @return
	 * @since 1.0
	 */
//	@Path("register")
//	@POST
	@RequestMapping(value = "register",method = RequestMethod.POST,consumes = "application/json")
	public String userRegist(@RequestBody JSONObject params) {

		try {
			logger.debug("params:{}", params);
			String mobile = params.getString("phoneNum"); // 电话号码
			String param = params.getString("phoneNum"); // 用户名
			String password = params.getString("password"); // 密码
			String clientVerify = params.getString("verification_code"); // 验证码

			if (!UserResource.passwordVerify(password)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, "密码格式错误"));
			}

			if (StringUtils.isBlank(mobile) || StringUtils.isBlank(param)
					|| StringUtils.isBlank(password)
					|| StringUtils.isBlank(clientVerify)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, "请求参数不能为空"));
			}

			String serviceVerifyNum = (String) cache.get(StringUtils.join(
					"register", mobile),RedisConfig.smsDB);
			if (!clientVerify.equals(serviceVerifyNum)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.veriy_code_error, RespMessage
								.getRespMsg(Status.veriy_code_error)));
			}

			User user = this.userOpService.loginUser(param);

			if (user != null) {
				if (user.getStatus().equals("3")) {
					// 非主动注册处理
					userOpService.notInitiativeRegister(user);
				} else {
					return JSONObject.toJSONString(new RespMsg<>(
							Status.USER_IS_EXIST, RespMessage
									.getRespMsg(Status.USER_IS_EXIST)));
				}
			}

			if (user == null) {
				user = new User();
				user.setPhone(mobile);
				user.setUsername(param);
				user.setPassword(password);
				user.setNickname(param);
				user = this.userOpService.save(user);
			}

//			cache.put(ExDigestUtils.sha1Hex(String.valueOf(user.getId())), user);
			return JSONObject.toJSONString(new RespMsg<>(user));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * @说明 用户名和昵称校验
	 * @param userName
	 * @return
	 */
	public static boolean userNameVerify(String userName) {
		return StringUtils.isNoneBlank(userName) && userName.length() > 0
				&& userName.length() < 12;
	}

	/**
	 * @说明 密码校验
	 * @param passwors
	 * @return
	 */
	public static boolean passwordVerify(String password) {
		return StringUtils.isNoneBlank(password)
				&& !StringUtils.isNumeric(password) && password.length() > 6
				&& password.length() < 20;
	}

	/**
	 * 作用: 检查用户是否已经注册
	 * 
	 * @param userName
	 * @return
	 */
//	@Path("isExits/{userName}")
//	@GET
	@RequestMapping(value = "isExits/{userName}",method = RequestMethod.GET)
	public String isExits(@PathVariable("userName") String userName) {
		try {
			logger.debug("userName:{}", userName);
			User user = this.userOpService.loginUser(userName);
			// 判断为空或者status为3属于用户未注册
			return user == null || user.getStatus().equals("3") ? JSONObject
					.toJSONString(new RespMsg<>()) : JSONObject
					.toJSONString(new RespMsg<>(Status.USER_IS_EXIST,
							RespMessage.getRespMsg(Status.USER_IS_EXIST)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 作用: 用户登录,目前只是实现了短信和,用户名密码登录
	 * 
	 * @param params
	 * @param type
	 *            type=user:钓友端登陆 ; type=emp:钓厂管理端登陆 ;
	 * @return
	 * @since 1.0
	 */
//	@Path("verify/{type}")
//	@POST
	@RequestMapping(value = "verify/{type}",method = RequestMethod.POST,consumes = "application/json")
	public String userLogin(@RequestBody JSONObject params, @PathVariable("type") String type) {
		logger.debug("params:{}", params);
		try {
			String mobile = params.getString("mobile");
			String clientVerify = params.getString("verify"); // 客户端验证码

			String param = params.getString("params");
			String password = params.getString("password");

			String key = StringUtils.join("login", mobile);

			if (StringUtils.isNoneBlank(mobile)
					&& StringUtils.isNoneBlank(clientVerify)) { // 短信登陆
				String serviceVerifyNum = (String) cache.get(key,RedisConfig.smsDB); // 服务端验证码

				if (!clientVerify.equals(serviceVerifyNum)) {
					// 验证码错误
					return JSONObject.toJSONString(new RespMsg<User>(
							Status.veriy_code_error, RespMessage
									.getRespMsg(Status.veriy_code_error)));
				}

				if (USER_LOGIN.equals(type)) { // 验证码正确 - 钓友端登陆
					cache.remove(key,RedisConfig.smsDB);
					User user = userOpService.loginUser(mobile); // 查看用户

					if (user == null) { // 用户尚未在平台注册
						user = new User();
						user.setPhone(mobile);
						user = this.userOpService.save(user); // 注册用户
					} else if (user.getStatus().equals("3")) {
						// 非主动注册处理
						userOpService.notInitiativeRegister(user);
					}
//					cache.put(ExDigestUtils.sha1Hex(String.valueOf(user.getId())),user);
					//查看是否可以提现收益
					boolean b = userOpService.isPartner(user.getId());
					user.setIsWithdraw(b);
//					userOpService.updateAppLoginTime(user.getId().intValue(), "C");
					return JSONObject.toJSONString(new RespMsg<User>(user));
				}

				if (EMP_LOGIN.equals(type)) { // 验证码正确 - 钓场管理端登陆
					cache.remove(key,RedisConfig.smsDB);
					Emp emp = empService
							.findEmpByPhoneOrEmaileOrUserName(mobile);
					if (emp == null) {
						return JSONObject.toJSONString(new RespMsg<>(
								Status.USER_NO_REGISTER, "该员工没有注册"));
					}
//					userOpService.updateAppLoginTime(emp.getUserId(), "B");
					return JSONObject.toJSONString(new RespMsg<Emp>(emp));
				}

			}

			if ((StringUtils.isBlank(param) || StringUtils.isBlank(password))) { // 校验字段
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}

			if (USER_LOGIN.equals(type)) {
				User user = this.userOpService.passwordLogin(param,
						ExDigestUtils.sha1Hex(password));// 密码加密

				if (user == null) {
					return JSONObject.toJSONString(new RespMsg<User>(
							Status.login_error, RespMessage
									.getRespMsg(Status.login_error)));
				}
//				userOpService.updateAppLoginTime(user.getId().intValue(), "C");
				if (user.getStatus().equals(UserConstant.USER_STATUS_ABLE)) { // 校验用户状态
					user.setPassword(null);
					user.setLastLoginTime(null);
					user.setStatus(null);
					user.setRegisTime(null);
					//查看是否可以提现收益
//					boolean b = userOpService.isPartner(user.getId());
					user.setIsWithdraw(false);
//					cache.put(String.valueOf(user.getId()), user); // 将用户存放缓存
					return JSONObject.toJSONString(new RespMsg<User>(user));
				}

				return JSONObject.toJSONString(new RespMsg<User>(
						Status.USER_STATUS_DISABLE, RespMessage
								.getRespMsg(Status.USER_STATUS_DISABLE)));
			}

			if (EMP_LOGIN.equals(type)) {

				Emp emp = empService.passowrdlogin(param, password);
				if (emp == null) {
					return JSONObject.toJSONString(new RespMsg<>(
							Status.USER_NO_REGISTER, "该员工没有注册"));
				}
				String status = emp.getEmpStatus();
				if (!String.valueOf(EmpService.VALID).equals(status)
						&& !String.valueOf(EmpService.ROOT_USER).equals(status)) {
					// (员工状态不可用)
					return JSONObject.toJSONString(new RespMsg<>(
							Status.EMP_IN_VALID, RespMessage
									.getRespMsg(Status.EMP_IN_VALID)));
				}
//				userOpService.updateAppLoginTime(emp.getUserId().intValue(), "B");
				if (ExDigestUtils.sha1Hex(password).equals(emp.getPassword())) {
					emp.setPassword(null);
					return JSONObject.toJSONString(new RespMsg<>(emp));
				}

			}

			return JSONObject.toJSONString(new RespMsg<User>(
					Status.login_error, RespMessage
							.getRespMsg(Status.login_error)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 作用  ：将登陆后的钓场信息重新返回
	 * 
	 * @param params  }	
	 * 
	 */
//	@Path("role/{uid}")
//	@GET
	@RequestMapping(value = "role/{uid}",method = RequestMethod.GET)
	public String getRole(@PathVariable("uid")Long uid){
		User user =null;
		try {
			user = userOpService.queryUserById(uid);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		Map<String, Object>	map = userOpService.getEmp(uid);
			if (map.size() > 0) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("identity", map);
				m.put("user", user);
				return JSONObject.toJSONString(new RespMsg<>(m));
			}
		
		return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists,
					RespMessage.getRespMsg(Status.fish_site_not_exists)));	
	}
	
	/**
	 * 2.0 b端登录
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("b/login")
//	@POST
	@RequestMapping(value = "b/login",method = RequestMethod.POST,consumes = "application/json")
	public String login(@RequestBody JSONObject json) {
		String mobile = json == null ? null : json.getString("mobile");
		String verifyNum = json == null ? null : json.getString("verifyNum"); // 客户端验证码
		String password = json == null ? null : json.getString("password");
		if (mobile == null || (verifyNum == null && password == null)) {
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		User user = null;
		if (verifyNum != null) {
			String key = StringUtils.join("login", mobile);// 需要修改验证码问题（同时登陆c，b端）
			String verifyNum0 = (String) cache.get(key,RedisConfig.smsDB);
			if (verifyNum0 == null || !verifyNum0.equals(verifyNum)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.veriy_code_error, RespMessage
								.getRespMsg(Status.veriy_code_error)));
			}
			cache.remove(key,RedisConfig.smsDB);
		}
		user = userOpService.getLogin(mobile);
		if (user != null) {
			if (password != null
					&& (user.getPassword() == null || !ExDigestUtils.sha1Hex(
							password).equals(user.getPassword()))) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.login_error, RespMessage
								.getRespMsg(Status.login_error)));
			}
			Map<String, Object> map = null;
			if (!user.getStatus().equals("0")) {
				map = userOpService.getEmp(user.getId());
				if (map.size() > 0) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("identity", map);
					m.put("user", user);
					return JSONObject.toJSONString(new RespMsg<>(m));
				}
			}

//			userOpService.updateAppLoginTime(user.getId().intValue(), "B");
		}
		return JSONObject.toJSONString(new RespMsg<>(Status.EMP_NOT_EXISTS,
				RespMessage.getRespMsg(Status.EMP_NOT_EXISTS)));
	}

	/**
	 * {"code":"code","phone":"158********","verifyNum":"1234","userInfo":"","activityCid":1} 
	 * 微信活动报名
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("login/wxActivity")
	@ApiOperation(value = "微信活动报名",notes="参数 \n\r {\"code\":微信code,\"phone\":用户手机号,"
			+ "\"verifyNum\":短信验证码,\"userInfo\":微信userInfo,\"activityCid\":活动id}")
	@RequestMapping(value = "login/wxActivity",method = RequestMethod.POST,consumes = "application/json")
	public String wxActivityLogin(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}" , json);
			Integer activityCid = json == null ? null : json.getInteger("activityCid");
			if(activityCid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String rs = userWxMiniLogin(json);
			JSONObject rsj = JSONObject.parseObject(rs);
			if(rsj.getIntValue("status") != 0){
				return rs;
			}
			User u = JSONObject.toJavaObject(rsj.getJSONObject("body"), User.class); 
			campaignService.enter(u.getId(), activityCid, u.getUnionId(), 0);
//			if(JSONObject.parseObject(rsc).getIntValue("status") != 0){
//				return rsc;
//			}
			return rs;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 微信验证是否报名
	 * @param json
	 * @return
	 */
//	@Path("login/wxActivity/check")
//	@POST
	@ApiOperation(value = "微信验证是否报名",notes = "{\"code\":\"\",\"userInfo\":\"userInfo\",\"activityCid\":12}")
	@RequestMapping(value = "login/wxActivity/check" , method = RequestMethod.POST,consumes = "application/json")
	public String wxActivityCheck(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String code = json == null ? null : json.getString("code");
			String userInfo = json == null ? null : json.getString("userInfo");
			Integer activityCid = json == null ? null : json.getInteger("activityCid");
			if (code == null || userInfo == null || activityCid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String result = userOpService.wxUserinfo(userInfo,code);
			JSONObject resultJson = JSONObject.parseObject(result);
			
			String unionId = resultJson.getString("unionId"); 
			User u = userOpService.queryUserByOpenId(unionId);
			if(u != null){
				campaignService.enter(u.getId(), activityCid, u.getUnionId(), 0);
				//去除敏感内容 
				u.setPassword(null);
				u.setIdcard(null);
				u.setOpenId(resultJson.getString("openId"));
				u.setUnionId(unionId);
			}else{
				return JSONObject.toJSONString(new RespMsg<>());
			}
			return JSONObject.toJSONString(new RespMsg<>(u));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 微信小程序登录
	 * 
	 * {"code":"code","phone":"158********","verifyNum":"1234","userInfo":""} 
	 * @param json
	 * @return
	 * http://192.168.1.89:8080/heipiao_api/users/login/wxMini
	 */
//	@ApiOperation(value = "微信小程序验证",notes="{\"code\":\"code\",\"phone\":\"158********\",\"verifyNum\":\"1234\",\"userInfo\":\"\"} ")
	@ApiOperation(value = "微信小程序登录",notes="{\"code\":\"code\",\"userInfo\":\"\"} ")
	@RequestMapping(value = "login/wxMini",method = RequestMethod.POST,consumes = "application/json")
	public String userWxMiniLogin(@RequestBody JSONObject json) {
		try {
			logger.info("json:{}",json);
			String code = json == null ? null : json.getString("code");
//			String phone = json == null ? null : json.getString("phone");
//			String verifyNum = json == null ? null : json.getString("verifyNum");
			String userInfo = json == null ? null : json.getString("userInfo");
			if (code == null || userInfo == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
//			String vNum = cache.get(StringUtils.join(PortalResource.wxMini,phone), RedisConfig.smsDB);
//			if(vNum == null || !vNum.equals(verifyNum)){
//				return JSONObject.toJSONString(new RespMsg<>(Status.veriy_code_error,RespMessage.getRespMsg(Status.veriy_code_error)));
//			}
			//验证手机号是否注册以及绑定
//			User pu = userOpService.queryUserByPhone(phone);
//			if(pu != null && pu.getOpenId() != null){
				//返回已绑定错误
//				return JSONObject.toJSONString(new RespMsg<>(Status.user_wx_bound,RespMessage.getRespMsg(Status.user_wx_bound)));
//			}
			
			String result = userOpService.wxUserinfo(userInfo,code);
			JSONObject resultJson = JSONObject.parseObject(result);
			
			//验证微信unionId是否存在并且已绑定
			String unionId = resultJson.getString("unionId"); 
			User wu = userOpService.queryUserByOpenId(unionId);
//			if(wu != null){
//				//返回其他账户已绑定错误
//				return JSONObject.toJSONString(new RespMsg<>(Status.user_wx_be_bound,RespMessage.getRespMsg(Status.user_wx_be_bound)));
//			}
			
			if(wu == null){
				//新用户注册
				wu = new User();
				wu.setUnionId(unionId);
				wu.setSex(resultJson.getString("gender"));
				wu.setNickname(resultJson.getString("nickName"));
				wu.setPortriat(resultJson.getString("avatarUrl"));
				userOpService.save(wu);
			}
//			if(pu == null){
//				//新用户注册
//				pu = new User();
//				pu.setOpenId(unionId);
//				pu.setSex(resultJson.getString("gender"));
//				pu.setNickname(resultJson.getString("nickName"));
//				userOpService.save(pu);
//			}else{
//				//老用户绑定
//				pu.setOpenId(unionId);
//				//只更新一个字段
//				User uu = new User();
//				uu.setId(pu.getId());
//				uu.setOpenId(unionId);
//				userOpService.modifyUser(uu);
//			}
			//去除敏感内容 
			wu.setPassword(null);
			wu.setIdcard(null);
			wu.setOpenId(resultJson.getString("openId"));
			wu.setUnionId(unionId);
			//最后清除验证码
//			cache.remove(StringUtils.join(PortalResource.wxMini,phone), RedisConfig.smsDB);
			return JSONObject.toJSONString(new RespMsg<>(wu));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 验证是否绑定微信
	 * @param json
	 * @return
	 */
//	@Path("login/wxMini/check")
//	@POST
	@ApiOperation(value = "验证是否绑定微信-微信小程序")
	@RequestMapping(value = "login/wxMini/check",method = RequestMethod.POST,consumes = "application/json")
	public String userWxMiniCheck(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String code = json == null ? null : json.getString("code");
			String userInfo = json == null ? null : json.getString("userInfo");
			if (code == null || userInfo == null ) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String result = userOpService.wxUserinfo(userInfo,code);
			JSONObject resultJson = JSONObject.parseObject(result);
			
			String unionId = resultJson.getString("unionId"); 
			User wu = userOpService.queryUserByOpenId(unionId);
			if(wu != null){
				wu.setPassword(null);
				wu.setIdcard(null);
				wu.setOpenId(resultJson.getString("openId"));
				wu.setUnionId(unionId);
			}
			return JSONObject.toJSONString(new RespMsg<>(wu));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "微信登录-app",notes="请求参数说明：\n\r code 微信code "
			+ "\n\r返回参数说明：isNew 是否是新用户 true：是，false:不是，\n\r user:用户对象")
	@RequestMapping(value = "login/wxapp",method = RequestMethod.POST,consumes = "application/json")
	public String wxLogin(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String code = json == null ? null : json.getString("code");
			if(code == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String rs = userOpService.wxAuth2(code);
			if(rs == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"微信授权失败"));
			}
			
			JSONObject rsJson = JSONObject.parseObject(rs);
			
			String accessToken = rsJson.getString("access_token");
			String unionid = rsJson.getString("unionid");
			String openid = rsJson.getString("openid");
			User u = userOpService.queryUserByOpenId(unionid);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("isNew", u == null);
			if(u == null){
				//新用户注册流程
				String userinfo = userOpService.wxGetUserInfo(accessToken, openid);
				if(userinfo == null){
					return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"获取微信用户资料失败"));
				}
				JSONObject uiJson = JSONObject.parseObject(userinfo);
				u = new User();
				u.setNickname(uiJson.getString("nickname"));
				u.setSex(uiJson.getString("sex"));
				u.setUnionId(unionid);
				u.setOpenId(openid);
				u.setPortriat(uiJson.getString("headimgurl"));
				userOpService.save(u);
			}else{
				//老用户
				u.setPassword(null);
				u.setOpenId(openid);
			}
			map.put("user", u);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation(value = "绑定微信-app",notes="请求参数说明：\n\r code 微信code \n\r uid  用户id ")
	@RequestMapping(value = "bindingwxapp",method = RequestMethod.PUT,consumes = "application/json")
	public String bindingWx(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String code = json == null ? null : json.getString("code");
			Long uid = json == null ? null : json.getLong("uid");
			if(code == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			String rs = userOpService.wxAuth2(code);
			if(rs == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"微信授权失败"));
			}
			
			JSONObject rsJson = JSONObject.parseObject(rs);
			
//			String accessToken = rsJson.getString("access_token");
			String unionid = rsJson.getString("unionid");
			User u = userOpService.queryUserByOpenId(unionid);
			if(u == null){
				//新用户注册流程
				u = new User();
				u.setId(uid);
				u.setUnionId(unionid);
				userOpService.modifyUser(u);
			}else{
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "该微信已经被绑定"));
			}
			return JSONObject.toJSONString(new RespMsg<>(u));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "绑定手机号",notes="{\"uid\":2342,\"phone\":\"158****2342\",\"verifyNum\":\"1234\"}")
	@RequestMapping(value = "bindingPhone",method = RequestMethod.PUT,consumes = "application/json")
	public String bindingPhone(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String phone = json == null ? null : json.getString("phone");
			String verifyNum = json == null ?  null : json.getString("verifyNum");
			Long uid = json == null ?  null : json.getLong("uid");
			if(phone == null || verifyNum == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			User u = userOpService.queryUserByPhone(phone);
			if(u != null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"该号码已经被绑定"));
			}
			String _verifyNum = cache.get(PortalResource.binding + phone, RedisConfig.smsDB);
			if(_verifyNum == null || !_verifyNum.equals(verifyNum)){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "验证码错误"));
			}
			cache.remove(PortalResource.binding + phone, RedisConfig.smsDB);
			u = new User();
			u.setPhone(phone);
			u.setId(uid);
			userOpService.modifyUser(u);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
//	@ApiOperation(value = "解绑定手机号",notes="{\"uid\":2342,\"phone\":\"158****2342\"}")
//	@RequestMapping(value = "unBindingPhone",method = RequestMethod.PUT,consumes = "application/json")
//	public String unBindingPhone(@RequestBody JSONObject json){
//		try {
//			logger.info("json:{}",json);
//			String phone = json == null ? null : json.getString("phone");
//			Long uid = json == null ?  null : json.getLong("uid");
//			if(phone == null || uid == null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
//						RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			User u = userOpService.queryUserById(uid);
//			if(u == null || !u.getPhone().equals(phone) || u.getUnionId() == null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"解绑错误"));
//			}
//			userOpService.removePhone(u);
//			return JSONObject.toJSONString(new RespMsg<>());
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
//		}
//	}
	
//	@ApiOperation(value = "解绑定微信",notes="{\"uid\":2342,\"unionid\":\"o6_bmasdasdsad6_2sgVt7hMZOPfL\"}")
//	@RequestMapping(value = "unBindingWx",method = RequestMethod.PUT,consumes = "application/json")
	public String unBindingWx(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			String unionid = json == null ? null : json.getString("unionid");
			Long uid = json == null ?  null : json.getLong("uid");
			if(unionid == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			User u = userOpService.queryUserById(uid);
			if(u == null || !u.getUnionId().equals(unionid) || u.getPhone() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"解绑错误"));
			}
			userOpService.removeOpenid(u);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 修改用户信息（头像、昵称、等）
	 * 
	 * @param jsonObject
	 * @return
	 * @since 1.0
	 */
//	@Path("modify/info")
//	@PUT
	@RequestMapping(value = "modify/info",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyUserInfo(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			if (jsonObject == null) {
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			
			Long birthday = jsonObject.getLong("birthday");
			if(birthday != null){
				jsonObject.put("birthday", new Date(birthday));
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			User user = JSONObject.toJavaObject(jsonObject, User.class);
			if (user.getId() == null) {
				return JSONObject.toJSONString(new RespMsg<String>(
						Status.value_is_null_or_error));
			}

			if (user.getNickname() != null && !UserResource.userNameVerify(user.getNickname())) {
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.value_is_null_or_error, "昵称不符合格式"));
			}

			params.put("op", "4");
			params.put("user", user);
			userOpService.modifyUser(params);
			return JSONObject.toJSONString(new RespMsg<User>(null));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 添加日志
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@POST
//	@Path("addlog")
	@RequestMapping(value = "addlog",method = RequestMethod.POST,consumes = "application/json")
	public String addLog(@RequestBody JSONObject json) {
		try {
			logger.debug("log:{}", json);
			UserOpLog log = JSONObject.toJavaObject(json, UserOpLog.class);
			userOpService.addLog(log);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 查询日志
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@GET
//	@Path("logs/{start}/{size}")
	@RequestMapping(value = "logs/{start}/{size}",method = RequestMethod.GET)
	public String queryLog(@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (start - 1) * size);
			map.put("size", size);
			List<UserOpLog> logs = userOpService.getLogs(map);
			return JSONObject.toJSONString(new RespMsg<List<UserOpLog>>(logs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 修改用户头像和昵称
	 * 
	 * @param json
	 * @return
	 * @since 1.2
	 */
//	@Path("info")
//	@PUT
	@RequestMapping(value = "info",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyUser(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			User user = JSONObject.toJavaObject(json, User.class);
			return ResultUtils.out(this.userOpService.modifyUser(user));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 用于验证手机号码的有效性
	 * @param params
	 * @return
	 */
//	@Path("chkmobile")
//	@POST
	@RequestMapping(value = "chkmobile",method = RequestMethod.POST,consumes = "application/json")
	public String checkVerify(@RequestBody JSONObject params){
		logger.debug("params:{}", params);
		try{
			String mobile = params.getString("mobile");
			String clientVerify = params.getString("verify");
			if(checkMobileVerify(mobile, clientVerify)){
				return JSONObject.toJSONString(new RespMsg<>());
			}else{
				// 验证码错误
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.veriy_code_error, RespMessage
								.getRespMsg(Status.veriy_code_error)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 通过 chkmobile发验证码后的服务器验证
	 * @param mobile
	 * @param clientVerify
	 * @return
	 */
	private boolean checkMobileVerify(String mobile, String clientVerify){
		if (StringUtils.isNoneBlank(mobile) && StringUtils.isNoneBlank(clientVerify)) {
			String key = StringUtils.join(PortalResource.chkmobile, mobile);
			String serviceVerifyNum = (String) cache.get(key,RedisConfig.smsDB);
			cache.remove(key,RedisConfig.smsDB);
			if (!clientVerify.equals(serviceVerifyNum)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 作用: 用户变更手机号码
	 * @param params
	 *            mobileOld:原手机号码 
	 *            mobileNew:新手机号码
	 *            verify：      客户端验证码
	 * @return
	 */
//	@Path("change")
//	@PUT
	@ApiOperation(value="用户变更手机号码",notes="请求参数 \n\r mobileOld:原手机号码 \n\r mobileNew:新手机号码 \n\r verify:客户端验证码")
	@RequestMapping(value = "change",method = RequestMethod.PUT,consumes = "application/json")
	public String userChange(@RequestBody JSONObject params) {
		logger.debug("params:{}", params);
		try {
			String mobileOld = params.getString("mobileOld");
			String mobileNew = params.getString("mobileNew");			
			String clientVerify = params.getString("verify"); // 客户端验证码
			String checkMoble = null;
			if (mobileOld == null || clientVerify == null) {
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			if(mobileNew == null || (mobileNew != null && mobileNew.trim().length() == 0)){
				checkMoble = mobileOld;
			}else{
				User mobileNewUser = userOpService.queryUserByPhone(mobileNew);
				if(mobileNewUser != null){
					return JSONObject.toJSONString(new RespMsg<User>(
							Status.USER_IS_EXIST, "新号码已存在"));
				}
				checkMoble = mobileNew;
			}
			if(!checkMobileVerify(checkMoble, clientVerify)){
				// 验证码错误
				return JSONObject.toJSONString(new RespMsg<User>(
						Status.veriy_code_error, RespMessage
								.getRespMsg(Status.veriy_code_error)));
			}
			if (mobileOld != null && mobileNew != null && mobileNew.trim().length() > 0) {
				Map<String, String> map = new HashMap<>();
				map.put("mobileOld", mobileOld);
				map.put("mobileNew", mobileNew);
				userOpService.changeNewMobileByOldMobile(map);
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	

}
