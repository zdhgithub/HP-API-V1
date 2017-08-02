/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserRecommend;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public interface UserRecommendMapper {
	
	
	UserRecommend selectByUidAsLock(Long uid);

	
	UserRecommend selectByUid(Long uid);

	
	void insertPojo(UserRecommend pojo);


	/**
	 * @param uid
	 * @return
	 */
	List<UserRecommend> selectListByUid(Long uid);


	/**
	 * @param uid
	 * @return
	 */
	User getUser(Long uid);


	/**
	 * @param uid
	 * @return
	 */
	Integer selectUserCount(Long uid);
	
	
}
