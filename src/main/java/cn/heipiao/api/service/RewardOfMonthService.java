package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.RewardOfMonth;

/**
 * 
 * @ClassName: RewardOfMonthService
 * @Description: 商家平台奖励月账单
 * @author duzh
 * @date 2017年1月17日
 */
public interface RewardOfMonthService {
	/**
	 * 商家获取平台奖励月账单
	 */
	List<RewardOfMonth> selectRewardOfMonth(Long bid,int type,int start,int size);
	/**
	 * 添加商家平台奖励月账单
	 */
	int insertRewardOfMonth(RewardOfMonth rewardOfMonth);
}
