package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.RewardDetail;

/**
 * 
 * @ClassName: RewardDetailService
 * @Description: TODO
 * @author duzh
 * @date 2017年1月17日
 */
public interface RewardDetailService {
	/**
	 * 获取商家平台奖励明细
	 */
	List<RewardDetail> selectByBid(Long bid,int type,int start,int size);
	/**
	 * 添加商家平台奖励明细
	 */
	int insertRewardDetail(RewardDetail rewardDetail);
}
