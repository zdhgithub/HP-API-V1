package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.UserProfile;

/**
 * @author wzw
 * @date 2017年3月31日
 */
public interface UserProfileMapper {

	UserProfile selectByUid(Long uid);
	
	int insertPojo(UserProfile pojo);
	
	int updatePojo(UserProfile pojo);
	
	int updateMsgSum(UserProfile pojo);
	
}
