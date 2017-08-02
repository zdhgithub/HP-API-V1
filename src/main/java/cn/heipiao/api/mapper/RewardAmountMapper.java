package cn.heipiao.api.mapper;

import java.util.Map;

import cn.heipiao.api.pojo.RewardAmount;

/**
 * 
 * @ClassName: RewardAmountMapper
 * @Description: 商家平台奖励账户
 * @author duzh
 * @date 2017年1月17日
 */
public interface RewardAmountMapper {
	/**
	 * 根据商家bid查询账户信息
	 */
	RewardAmount selectByBid(Map<String,Object> params);
	/**
	 * 根据商家bid查询账户信息
	 */
	RewardAmount selectByBidAsLock(Map<String,Object> params);
	/**
	 * 修改商家平台奖励账户信息
	 */
	void updateByBid(Map<String,Object> params);
	/**
	 * 添加商家平台奖励账户信息
	 */
	void insertRewardPlatformAmount(RewardAmount rewardAmount);
	/**
	 * 更新商家平台奖励账户信息
	 */
	void updatePojo(RewardAmount rewardAmount);
}
