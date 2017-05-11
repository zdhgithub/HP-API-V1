package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.TabRelavance;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserOpLog;

/**
 * @author zf
 * @version 1.0
 * @description 分出来的userservice
 * @date 2016年6月3日
 */
public interface UserOpService {

	/**
	 * 分页查询用户列表--支持排序
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<User> queryUsers(Map<String, Object> params) throws Exception;

	/**
	 * 根据username查询user
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User queryUserById(Long uid) throws Exception;

	/**
	 * 通过手机号或是username查询用户
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<User> queryUserByPhoneOrName(Map<String, Object> param)
			throws Exception;

	/**
	 * 查询user标签
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<TabRelavance> queryUserLabel(String userId, String type)
			throws Exception;

	/**
	 * 修改用户信息,备注/贴标签/去标签/拉黑
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void modifyUser(Map<String, Object> params) throws Exception;

	/**
	 * 用户注册 保存用户并返回带有id的User对象 脱敏处理:密码,最后一次登录时间等
	 * 
	 * @param user
	 * @throws Exception
	 */
	User save(User user) throws Exception;

	/**
	 * 查询用户总数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer queryUsersNum() throws Exception;

	/**
	 * 
	 * 作用: 密码登录 - 传统登录
	 * 
	 * @param param
	 * @param password
	 * @return
	 */
	User passwordLogin(String param, String password);

	/**
	 * 作用: 获取登陆用户
	 * 
	 * @param param
	 * @return
	 */
	User loginUser(String param);

	/**
	 * @param openId
	 * @param source
	 * @return
	 */
	public User queryUserByOpenId(String openId, String source);
	
	/**
	 * 
	 * @param openId
	 * @return
	 */
	public User queryUserByOpenId(String openId);

	/**
	 * @param u
	 */
	public void authUser(User u);

	/**
	 * @说明 赠送注册用户票券
	 * @param userId
	 * @return
	 */
	boolean presentRegisterTicket(Long userId);

	/**
	 * @param phone
	 * @return
	 */
	public User queryUserByPhone(String phone);

	/**
	 * @param us
	 */
	public void saveImplicit(User us);

	/**
	 * @param user
	 * @throws Exception 
	 */
	public void notInitiativeRegister(User user) throws Exception;

	/**
	 * 修改用户密码
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void findPwd(User user) throws Exception;

	/**
	 * 添加日志
	 * 
	 * @param user
	 * @throws Exception
	 */
	public Integer addLog(UserOpLog log) throws Exception;
	/**
	 * 查询日志
	 * 
	 * @param user
	 * @throws Exception
	 */
	public List<UserOpLog> getLogs(Map<String,Object> map) throws Exception;

	/**
	 * @param mobile
	 * @return
	 */
	public User getLogin(String mobile);

	/**
	 * @param id
	 * @return
	 */
	public Map<String, Object> getEmp(Long id);
	
	public Integer modifyUser(User user) throws Exception;

	/**
	 * 判断当前用户是否是合伙人，钓场主或者店铺主中的一种
	 * @param id
	 * @return
	 */
	public boolean isPartner(Long id);

	/**
	 * 依据原手机号码修改新手机号码（手机号码是唯一索引）
	 * @param map
	 * 		@param mobileOld
	 * 		@param mobileNew
	 * @return
	 */
	public int changeNewMobileByOldMobile(Map<String, String> map);
	/**
	 * 保存APP基本信息
	 * @param json
	 * @return
	 */
	public Integer saveAppRecord(JSONObject json);
	/**
	 * 获取登陆失效时间
	 * @param uid
	 * @param app
	 * @return
	 */
	public Integer getTokenStatus(Integer uid,String app);
	/**
	 * 更新APP失效时间
	 * @param uid
	 * @param app
	 */
	public void updateAppLoginTime(Integer uid,String app);

	public String wxUserinfo(String userInfo, String code) throws Exception;
	/**
	 * 查询不是合伙人 的用户
	 */
	public List<User> selectNotPartnerList(Map<String,Object> map);
	
	/**
	 * cp添加虚拟用户
	 * @param url 虚拟用户头像url
	 * @param nickname 虚拟用户昵称
	 * @param cityId 虚拟用户区域id
	 * @param remark    虚拟 用户备注
	 */
	public Integer saveIdealUser(User user);
	/**
	 * cp查询所有的虚拟用户
	 */
	public List<User> queryIdealUser();
	/**
	 * cp修改虚拟用户信息
	 */
	public int updateIdealUser(JSONObject json);

	public String wxAuth2(String code) throws Exception;

	/**
	 * 微信app获取个人资料
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	String wxGetUserInfo(String accessToken, String openId) throws Exception;

	public void removeOpenid(User u);

	public void removePhone(User u);
}
