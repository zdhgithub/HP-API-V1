package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RewardPolicy;

public interface RewardPolicyMapper<T>{
	/**
	 * 商家添加奖励政策
	 * @param <T>
	 */
    public void insertPolicy(T pojo);
	/**
	 * 通过商家id查询奖励政策
	 * @param params 
	 */
	RewardPolicy selectByBid(Map<String, Object> params);
	/**
	 * 更新商家政策
	 */
	void updatePolicy(Map<String, Object> param);
	/**
	 * 删除商家政策
	 */
	void deletePolicy(Long bid);
	/**
	 * 查询商家政策列表
	 */
	List<RewardPolicy> selectList(Map<String, Object> param);
}
