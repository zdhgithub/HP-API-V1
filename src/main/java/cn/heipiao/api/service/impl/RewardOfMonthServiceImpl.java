package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.RewardOfMonthMapper;
import cn.heipiao.api.pojo.RewardOfMonth;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.RewardOfMonthService;

/**
 * 
 * @ClassName: RewardOfMonthServiceImpl
 * @Description: TODO
 * @author duzh
 * @date 2017年1月17日
 */
@Service
public class RewardOfMonthServiceImpl implements RewardOfMonthService{
	@Resource
	private RewardOfMonthMapper rewardOfMonthMapper;
	/**
	 * 商家获取平台奖励月账单
	 */
	@Override
	public List<RewardOfMonth> selectRewardOfMonth(Long bid, int type,int start,int size) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bid", bid);
		params.put("type", type);
		params.put("size", size);
		params.put("start", start);
		List<RewardOfMonth> list = rewardOfMonthMapper.selectByBid(params);
		return list;
	}
	/**
	 * 添加商家平台奖励月账单
	 */
	@Override
	public int insertRewardOfMonth(RewardOfMonth rewardOfMonth) {
		rewardOfMonthMapper.insert(rewardOfMonth);
		return Status.success;
	}

}
