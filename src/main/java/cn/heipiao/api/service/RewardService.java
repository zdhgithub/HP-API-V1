package cn.heipiao.api.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.RewardPolicy;

/**
 * 
 * @ClassName: RewardService
 * @Description: TODO
 * @author duzh
 * @date 2017年1月14日
 */
public interface RewardService {
	/**
	 * CP端 获取奖励政策列表
	 */
	List<RewardPolicy> selectRewardPolicyList(int type,int start,int size,long bid,String title);
	/**
	 * c端 商家获取奖励政策信息
	 */
	RewardPolicy selectRewardPloicyByBid(Long bid,int type);
	/**
	 * cp 端添加奖励政策信息
	 * @return 
	 */
	public Integer saveRewardPolicy(JSONObject json) throws Exception;
	/**
	 * cp 端删除商家奖励政策 
	 * @param bid 
	 */
	public Integer delectRewardPolicy(Long id);
	/**
	 * cp 端修改商家奖励政策信息
	 */
	public Integer updateRewardPolicy(JSONObject json) throws Exception;
}
