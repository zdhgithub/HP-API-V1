package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.User;

/**
 * @author z
 * @version 1.0
 * @description userMapper
 * @date 2016年6月1日
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 通过多个条件模糊查询
	 * 
	 * @param user
	 * @return
	 */
	List<User> getUsersByConditions(Map<String, String> param);
	/**
	 * 查询用户ID
	 * 
	 * @param
	 * @return
	 */
	List<Integer> getUserIDs();

	/**
	 * 根据昵称或是手机号查用户 支持模糊查询
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<User> getUsersByPhoneOrName(Map<String,Object> param);

	/**
	 * 获取user总数量
	 * 
	 * @return
	 */
	public Integer getUsersNum();

	/**
	 * 作用: 根据登录参数查找用户
	 * @param param
	 * @return
	 */
	User findByLoginParam(String param);

	/**
	 * @param openId
	 * @param source
	 * @return
	 */
	User queryUserByOpenId(@Param("unionId") String unionId, @Param("source")String source);

	/**
	 * @param u
	 */
	void updateRealnameOrIdcardByUid(User u);

	/**
	 * @param phone
	 * @return
	 */
	User queryUserByPhone(String phone);
	
	/**
	 * 依据原手机号码修改新手机号码（手机号码是唯一索引）
	 * @param map
	 * 		@param mobileOld
	 * 		@param mobileNew
	 * @return
	 */
	int updateNewMobileByOldMobile(Map<String, String> map);
	/**
	 * 根据 uid查找用户
	 */
	User selectById(Integer id);
	/**获取不是合伙人的用户*/
	List<User> selectNotPartnerList(Map<String,Object> param);
	
	/**
	 * cp查询所有的虚拟用户
	 */
	List<User> selectIdealUser();
	
	int removeOpenid(User u);
	
	
	int removePhone(User u);
	
}
