package cn.heipiao.api.service;


import cn.heipiao.api.pojo.RewardPlatform;
	/**
	 * 
	 * @ClassName: RewardPlatformService
	 * @Description: 平台奖励政策
	 * @author duzh
	 * @date 2017年1月16日
	 */
public interface RewardPlatformService {
	/**
	 * cp通过商家id商家类型查询
	 */
	RewardPlatform findRewardPlatformByBid(Long bid,int type);
	RewardPlatform findRewardPlatform(Long bid,int type,int status);
	/**
	 * cp添加商家平台奖励配置
	 */
	int insertRewardPlatform(RewardPlatform rewardPlatform);
	/**
	 * cp删除商家平台奖励配置
	 */
	int update(Long id,Integer status,Integer type) throws Exception;
}
