package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.WxMiniPayOnceReward;

/**
 * @author wzw
 * @date 2017年2月20日
 */
public interface WxMiniPayOnceRewardMapper {

	
	WxMiniPayOnceReward selectByUid(Long uid);
	
	void insertPojo(WxMiniPayOnceReward wxr);
	
	
}
