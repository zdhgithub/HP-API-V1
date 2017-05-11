/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.UserShareReward;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
public interface UserShareRewardMapper {

	/**
	 * 加锁
	 * @param uid
	 * @return
	 */
	UserShareReward selectAsLockByUid(Long uid);

	
	UserShareReward selectByUid(Long uid);
	
	
	void  updatePojo(UserShareReward pojo);
	
	
	void  insertPojo(UserShareReward pojo);
}
