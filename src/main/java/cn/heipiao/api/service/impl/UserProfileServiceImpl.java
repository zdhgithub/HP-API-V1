package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.UserProfileMapper;
import cn.heipiao.api.pojo.UserProfile;
import cn.heipiao.api.service.UserProfileService;

/**
 * @author wzw
 * @date 2017年3月31日
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Resource
	private UserProfileMapper userProfileMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addPojo(UserProfile pojo) {
		UserProfile up = userProfileMapper.selectByUid(pojo.getUid());
		if(up == null){
			pojo.setMsgSum(pojo.getMsgSum() == null ? 0 : pojo.getMsgSum());
			userProfileMapper.insertPojo(pojo);
		}else{
			userProfileMapper.updatePojo(pojo);
		}
	}

	@Override
	public UserProfile getByUid(Long uid) {
		return userProfileMapper.selectByUid(uid);
	}

	@Override
	public void updateMsgSum(Long uid,Integer msgSum) {
		UserProfile pojo = new UserProfile();
		pojo.setUid(uid);
		pojo.setMsgSum(msgSum);
		UserProfile up = userProfileMapper.selectByUid(pojo.getUid());
		if(up == null){
			pojo.setMsgSum(pojo.getMsgSum() == null ? 0 : pojo.getMsgSum());
			userProfileMapper.insertPojo(pojo);
		}else{
			up.setMsgSum(pojo.getMsgSum() > 0 ? up.getMsgSum() + pojo.getMsgSum() : 0);
			userProfileMapper.updateMsgSum(up);
		}
	}
	
	
	

}
