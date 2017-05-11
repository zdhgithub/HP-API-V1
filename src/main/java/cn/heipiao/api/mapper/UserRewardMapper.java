/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.UserReward;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public interface UserRewardMapper {

	UserReward selectAsLockByUid(Long uid);
	
	UserReward selectByUid(Long uid);
	
	void insertPojo(UserReward pojo);
	
	void updatePojo(UserReward pojo);
	
}
