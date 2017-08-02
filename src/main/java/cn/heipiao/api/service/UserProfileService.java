package cn.heipiao.api.service;

import cn.heipiao.api.pojo.UserProfile;

/**
 * @author wzw
 * @date 2017年3月31日
 */
public interface UserProfileService {

	void addPojo(UserProfile pojo);
	
	void updateMsgSum(Long uid,Integer msgSum);

	UserProfile getByUid(Long uid);
	
}
