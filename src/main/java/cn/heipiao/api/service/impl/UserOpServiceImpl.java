package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.constant.ApiConstant.UserConstant;
import cn.heipiao.api.constant.MyBatisConstant;
import cn.heipiao.api.mapper.AppRecordMapper;
import cn.heipiao.api.mapper.ArticleNewMapper;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.DictMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.TabRelavanceMapper;
import cn.heipiao.api.mapper.UserGoldCoinMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.UserOpLogMapper;
import cn.heipiao.api.pay.HttpUtils;
import cn.heipiao.api.pay.PayConfig;
import cn.heipiao.api.pojo.AppRecord;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.pojo.TabRelavance;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.pojo.UserOpLog;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.CouponUserService;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.ModuleService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.service.WebService;
import cn.heipiao.api.utils.ExAES128Utils;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExDigestUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zf
 * @version 1.0
 * @description 分出来的userservice
 * @date 2016年6月3日
 */
@Service
public class UserOpServiceImpl implements UserOpService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserOpServiceImpl.class);

	@Resource
	private HttpUtils http;
	
	@Resource
	private PayConfig payConfig;
	
	@Resource
	private UserMapper userMapper;
	@Resource
	private DictMapper dictmapper;
	@Resource
	private TabRelavanceMapper tabRelavanceMapper;
	
	@Resource
	private UserOpLogMapper userOpLogMapper;
	
	@Resource
	private CouponUserService couponUserService;
	
	@Resource
	private UserGoldCoinMapper userGoldCoinMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private WebService webService;
	
	@Resource
	private EmpService empService;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	@Resource
	private ArticleNewMapper articleNewMapper;
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private AppRecordMapper appRecordMapper;
	
	public static final int days = 30;
	
	@Override
	public List<User> queryUsers(Map<String, Object> params) throws Exception {
		String orderRule = (String) params.get("orderRule");
		if (!StringUtils.isEmpty(orderRule)) {
			if (orderRule.endsWith(MyBatisConstant.ORDER_ASC)) {
				params.put("asc", MyBatisConstant.ORDER_ASC);
			} else if (orderRule.endsWith(MyBatisConstant.ORDER_DESC)) {
				params.put("desc", MyBatisConstant.ORDER_DESC);
			}
			if (orderRule.contains("nickname")) {
				params.put("order", "f_user_nickname");
			} else if (orderRule.contains("registime")) {
				params.put("order", "f_user_register_time");
			}
		}
		List<User> userList = userMapper.selectList(params);
		return userList;
	}
	@Override
	public List<User> selectNotPartnerList(Map<String,Object> params){
		String orderRule = (String) params.get("orderRule");
		if (!StringUtils.isEmpty(orderRule)) {
			if (orderRule.endsWith(MyBatisConstant.ORDER_ASC)) {
				params.put("asc", MyBatisConstant.ORDER_ASC);
			} else if (orderRule.endsWith(MyBatisConstant.ORDER_DESC)) {
				params.put("desc", MyBatisConstant.ORDER_DESC);
			}
			if (orderRule.contains("nickname")) {
				params.put("order", "f_user_nickname");
			} else if (orderRule.contains("registime")) {
				params.put("order", "f_user_register_time");
			}
		}
		List<User> userList = userMapper.selectNotPartnerList(params);
		return userList;
	}
	@Override
	public User queryUserById(Long userId) throws Exception {
		return userMapper.selectById(userId);
	}

	@Override
	public List<User> queryUserByPhoneOrName(Map<String, Object> param)
			throws Exception {
		List<User> list = userMapper.getUsersByPhoneOrName(param);
		return list;
	}

	@Override
	public List<TabRelavance> queryUserLabel(String userId, String type)
			throws Exception {
		List<TabRelavance> tabs = tabRelavanceMapper.getUserTab(
				Integer.parseInt(userId), type);
		return tabs;
	}

	@Override
	public void modifyUser(Map<String, Object> params) throws Exception {
		this.modifyRemark(params);
		this.modifyTips(params);
		this.modifyBlack(params);
		this.modifyUserInfo(params);
	}

	/**
	 * 修改备注
	 * 
	 * @param params
	 */
	@Transactional(readOnly = false)
	public void modifyRemark(Map<String, Object> params) {
		if (!"1".equals(params.get("op"))) {
			return;
		}
		User user = new User();
		user.setId((Long)params.get("userId"));
		user.setPlatformRemarks((String) params.get("platformRemarks"));
		userMapper.updateById(user);
	}

	/**
	 * 贴标签
	 * 
	 * @param params
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void modifyTips(Map<String, Object> params) throws Exception {
		if (!"2".equals(params.get("op"))) {
			return;
		}
		String labels = (String) params.get("labels");
		String userId = (String) params.get("userId");
		String[] strs = labels.trim().split(
				ApiConstant.SysConstant.DIVIDE_STR_0);
		tabRelavanceMapper.deleteUserTab(Integer.parseInt(userId),
				ApiConstant.UserConstant.NORMAL_LABEL);
		for (String s : strs) {
			DictConfig dict = dictmapper.getDictByCode(s);
			TabRelavance tab = new TabRelavance();
			tab.setUserId(Long.parseLong((String) params.get("userId")));
			tab.setTabCode(s);
			tab.setTabName(dict.getValue());
			tab.setTime(ExDateUtils.getDate());
			tabRelavanceMapper.saveUserTab(tab);
		}
	}

	/**
	 * 拉黑操作
	 * 
	 * @param params
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@Transactional(readOnly = false)
	public void modifyBlack(Map<String, Object> params)
			throws NumberFormatException, Exception {
		if (!"3".equals(params.get("op"))) {
			return;
		}
		String blackLabels = (String) params.get("blackLabels");
		String userId = (String) params.get("userId");
		String[] strs = blackLabels.trim().split(
				ApiConstant.SysConstant.DIVIDE_STR_0);
		tabRelavanceMapper.deleteUserTab(Integer.parseInt(userId),
				ApiConstant.UserConstant.BLACK_LABEL);
		for (String s : strs) {
			DictConfig dict = dictmapper.getDictByCode(s);
			TabRelavance tab = new TabRelavance();
			tab.setUserId(Long.parseLong((String) params.get("userId")));
			tab.setTabCode(s);
			tab.setTabName(dict.getValue());
			tab.setTime(ExDateUtils.getDate());
			tabRelavanceMapper.saveUserTab(tab);
		}

	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @param op
	 * @return
	 */
	public int modifyUserInfo(Map<String, Object> params) {
		String op = (String) params.get("op");
		if (!"4".equals(op)) {
			return 0;
		}
		User user = (User) params.get("user");
		userMapper.updateById(user);
		return user.getId().intValue();
	}

	@Override
	@Transactional(readOnly = false)
	public User save(User user) throws Exception {
		// 用户命生成规则,
		// String uname = StringUtils.join( "hp_" ,
		// RandomNumberUtils.getVerifyNum(8));
		// if( StringUtils.isBlank(user.getUsername()) ){
//		if (!StringUtils.isBlank(user.getPhone())) {
//			
//		}
		// }

		user.setStatus(UserConstant.USER_STATUS_ABLE); // 设置用户有效状态

		String pwd = user.getPassword();
		if (!StringUtils.isBlank(pwd))
			user.setPassword(ExDigestUtils.sha1Hex(pwd));
		// user.setPortriat(Config.getMapProp(DEFAULT_USER_PORTRIAT_KEY));
		
		//初始化
		initUser(user);
		
//		this.moduleService.initData(user.getId().intValue());
		
		//送券
//		this.presentRegisterTicket(user.getId());
		
		user.setPassword(null);
		user.setLastLoginTime(null);
		user.setRegisTime(null);
		return user;
	}

	/**
	 * 初始化用户的基本信息
	 * @param user
	 */
	private void initUser(User user){
		if(StringUtils.isBlank(user.getNickname())){
			//默认将电话号码模糊处理为昵称
			user.setNickname(user.getPhone().replace(user.getPhone().substring(3, 7), "****"));
		}
		user.setUsername(user.getPhone());
		user.setRegisTime(ExDateUtils.getDate());
		user.setLastLoginTime(ExDateUtils.getDate());
		userMapper.insert(user);
		
		//创建用户漂币记录
		UserGoldCoin ugc = new UserGoldCoin();
		ugc.setUid(user.getId());
		ugc.setGoldCoin(0);
		ugc.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		userGoldCoinMapper.insertPojo(ugc);
	}
	
	@Override
	public Integer queryUsersNum() throws Exception {
		int usersNum = userMapper.getUsersNum();
		return usersNum;
	}

	@Override
	public User passwordLogin(String param, String password) {
		User user = this.userMapper.findByLoginParam(param); // 根据登录参数查询用户

		if (user == null || !password.equals(user.getPassword())) { // 校验密码
			return null;
		}
		return user;
	}

	@Override
	public User loginUser(String param) {
		return this.userMapper.findByLoginParam(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.UserOpService#queryUserByOpenId(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public User queryUserByOpenId(String openId, String source) {
		return userMapper.queryUserByOpenId(openId, source);
	}
	
	@Override
	public User queryUserByOpenId(String openId) {
		return userMapper.queryUserByOpenId(openId, null);
	}

	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#authUser(cn.heipiao.api.pojo.User)
	 */
	@Override
	public void authUser(User u) {
		userMapper.updateRealnameOrIdcardByUid(u);
	}

	@Override
	public boolean presentRegisterTicket(Long userId) {
		this.couponUserService.presentUser(userId);
		return true;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#queryUserByPhone(java.lang.String)
	 */
	@Override
	public User queryUserByPhone(String phone) {
		return userMapper.queryUserByPhone(phone);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#saveImplicit(cn.heipiao.api.pojo.User)
	 */
	@Override
	public void saveImplicit(User us) {
		initUser(us);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#notInitiatidRegister(cn.heipiao.api.pojo.User)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public void notInitiativeRegister(User user) throws Exception {
		//更新优惠券，只更新优惠券过期时间
		long t = ExDateUtils.getCalendar().getTimeInMillis();
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("uid", user.getId());
//		map.put("deadline", new Timestamp(t + 30L * 24 * 60 * 60 * 1000));
		Coupons c = new Coupons();
		c.setUid(user.getId());
		c.setDeadline(new Timestamp(t + 30L * 24 * 60 * 60 * 1000));
		couponsMapper.updateCouponByUid(c);
		//向推荐人添加奖励
		if(user.getStat() != null && user.getStat().intValue() == 1){
			//奖励漂币数量
			int amount = 200;
			//奖励推荐人200漂币
			webService.addRewardToRuid(user.getId(), amount,"推荐好友首次登陆奖励",1);
			user.setStat(2);
		}
		//更新用户状态
		user.setStatus(ApiConstant.UserConstant.USER_STATUS_ABLE);
		userMapper.updateById(user);
	}

	@Override
	@Transactional
	public void findPwd(User user) throws Exception {
		userMapper.updateById(user);
		
	}
	
	@Override
	@Transactional
	public Integer addLog(UserOpLog log) throws Exception {
		log.setOpTime(ExDateUtils.getDate());
		userOpLogMapper.insert(log);
		return null;
	}

	@Override
	public List<UserOpLog> getLogs(Map<String,Object> map) throws Exception {
		return userOpLogMapper.selectList(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#getLogin(java.lang.String)
	 */
	@Override
	public User getLogin(String mobile) {
		return userMapper.queryUserByPhone(mobile);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#getEmp(java.lang.Long)
	 */
	@Override
	public Map<String, Object> getEmp(Long uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Emp> siteEmp = empService.selectSiteEmpByUid(uid);
		List<ShopEmp> shopEmp = empService.selectShopEmpByUid(uid);
		if(siteEmp.size() > 0)
			map.put("site", siteEmp);
		if(shopEmp.size() > 0)
			map.put("shop", shopEmp);
		return map;
	}

	@Override
	@Transactional
	public Integer modifyUser(User user) throws Exception {
		this.userMapper.updateById(user);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserOpService#isWithdraw(java.lang.Long)
	 */
	@Override
	public boolean isPartner(Long uid) {
		boolean b = false;
		b = fishSiteMapper.isExistsNormal(uid);
		b = !b ? fishShopMapper.isExistsNormal(uid) : b;
		b = !b ? partnerMapper.selectByUid(uid) != null : b;
		return b;
	}

	@Override
	public int changeNewMobileByOldMobile(Map<String, String> map) {
		return userMapper.updateNewMobileByOldMobile(map);
	}

	@Override
	@Transactional
	public Integer saveAppRecord(JSONObject json) {
		AppRecord pojo = JSONObject.toJavaObject(json, AppRecord.class);
		pojo.setCreateTime(ExDateUtils.getDate());
		appRecordMapper.insert(pojo);
		return Status.success;
	}

	@Override
	@Transactional
	public Integer getTokenStatus(Integer uid,String app) {
		Date now = ExDateUtils.getDate();
		Date user_lose_time = this.appRecordMapper.selectOneLoseTime(uid,app);
		if(user_lose_time==null) {//新增失效时间记录
			Date loseTime = DateUtils.addDays(now, days);
			this.appRecordMapper.insertOne(uid, loseTime,app);
			return Status.success;
		}else {
			if(now.getTime()>user_lose_time.getTime()) {//如果登陆时间失效，返回失效表示，并更新失效时间
				this.appRecordMapper.updateOne(uid, DateUtils.addDays(now, days), app);
				return Status.login_token_status;
			}else {
				return Status.success;
			}
		}
	}

	@Override
	@Transactional
	public void updateAppLoginTime(Integer uid,String app) {
		Date now = ExDateUtils.getDate();
		this.appRecordMapper.updateOne(uid, DateUtils.addDays(now, days), app);
	}

	@Override
	public String wxUserinfo(String userInfo,String code) throws Exception {
		String rl =	http.execute("https://api.weixin.qq.com/sns/jscode2session?"
				+ "appid=" + payConfig.wx_mini_appid + "&secret=" + payConfig.wx_mini_secret + "&js_code=" + code + "&grant_type=authorization_code",
				"get", null);
		JSONObject rlJson = JSONObject.parseObject(rl);
		JSONObject userInfoJson = JSONObject.parseObject(userInfo);
		logger.info(rlJson.toString());
		return ExAES128Utils.decrypt(userInfoJson.getString("encryptedData"), rlJson.getString("session_key"), 
				ExAES128Utils.generateIV(userInfoJson.getString("iv")));
	}
	
	@Override
	@Transactional
	public Integer saveIdealUser(User user) {
		userMapper.insert(user);
		return Status.success;
	}
	@Override
	public List<User> queryIdealUser(){
		return userMapper.selectIdealUser();
	}
	@Override
	public int updateIdealUser(JSONObject json) {
		User user = new User();
		String url = json.getString("url");
		String nickname = json.getString("nickname");
		Integer cityid = json.getInteger("cityid");
		String remark = json.getString("remark");
		Long uid = json.getLong("uid");
		user.setPortriat(url);
		user.setNickname(nickname);
		user.setRegionId(cityid);
		user.setRemark(remark);
		user.setId(uid);
		return userMapper.updateById(user);
	}
	
	/**
	 * https://api.weixin.qq.com/sns/oauth2/access_token?
	 * appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 * 
	 * { 
			"access_token":"ACCESS_TOKEN", 
			"expires_in":7200, 
			"refresh_token":"REFRESH_TOKEN",
			"openid":"OPENID", 
			"scope":"SCOPE",
			"unionid":"o6_bmasdasdsad6_2sgVt7hMZOPfL"
		}
	 * 
	 * @throws Exception 
	 */
	@Override
	public String wxAuth2(String code) throws Exception {
		String resp = http.execute("https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=" + payConfig.pay_wx_appid_c
				+ "&secret=" + payConfig.pay_wx_appSecret_c
				+ "&code=" + code + "&grant_type=authorization_code", "get", null);
		logger.info("wx-auth:" + resp);
		return resp.startsWith("{\"errcode\"") ? null : resp;
	}
	
	/**
	 * { 
		"openid":"OPENID",
		"nickname":"NICKNAME",
		"sex":1,
		"province":"PROVINCE",
		"city":"CITY",
		"country":"COUNTRY",
		"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
		"privilege":[
		"PRIVILEGE1", 
		"PRIVILEGE2"
		],
		"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		
		}
	 */
	@Override
	public String wxGetUserInfo(String accessToken,String openId) throws Exception{
		String resp = http.execute("https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token=ACCESS_TOKEN&openid=OPENID".replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId)
				,"get", null);
		logger.info("wx-userinfo:" + resp);
		return resp.startsWith("{\"errcode\"") ? null : resp;
	}
	
	
	@Override
	public void removeOpenid(User u) {
		userMapper.removeOpenid(u);
	}
	
	@Override
	public void removePhone(User u) {
		userMapper.removePhone(u);
	}
	
}
