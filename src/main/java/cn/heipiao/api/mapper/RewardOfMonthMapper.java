package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RewardOfMonth;

/**
 * 
 * @ClassName: RewardOfMonth
 * @Description: 商家平台奖励月账单
 * @author duzh
 * @date 2017年1月17日
 * @version 
 */
public interface RewardOfMonthMapper {
	/**
	 * 根据商家bid 查询平台奖励月账单信息
	 */
	List<RewardOfMonth> selectByBid(Map<String,Object> params);
	/**
	 * 添加平台奖励月账单信息
	 */
	void insert(RewardOfMonth rewardOfMonth);
}
