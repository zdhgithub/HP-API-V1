package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RewardDetail;

/**
 * 
 * @ClassName: RewardDetailMapper
 * @Description: 平台奖励明细
 * @author duzh
 * @date 2017年1月17日
 */
public interface RewardDetailMapper {
	/**
	 * 根据bid type 获取奖励明细
	 */
	List<RewardDetail> selectByBid(Map<String,Object> params);
	/**
	 * 添加平台奖励明细记录
	 */
	void insertRewardDetail(RewardDetail rewardDetail);
	
}
