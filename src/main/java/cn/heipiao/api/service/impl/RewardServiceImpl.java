package cn.heipiao.api.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.mapper.RewardPolicyMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.RewardPolicy;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.RewardService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 
 * @ClassName: RewardServiceImpl
 * @Description: TODO
 * @author duzh
 * @date 2017年1月14日
 */
@Service
public class RewardServiceImpl implements RewardService{
	@Resource
	private RewardPolicyMapper<RewardPolicy> rewardPolicyMapper;
	/**
	 * cp端获取奖励信息
	 */
	@Override
	public List<RewardPolicy> selectRewardPolicyList(int type,int start,int size,long bid,String title) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("start", start);
		param.put("size", size);
		param.put("type", type);
		param.put("bid", bid);
		param.put("title", title);
		List<RewardPolicy> list=rewardPolicyMapper.selectList(param);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}
	/**
	 * 商家获取奖励政策信息
	 */
	@Override
	public RewardPolicy selectRewardPloicyByBid(Long bid,int type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bid", bid);
		params.put("type", type);
		RewardPolicy rewardPolicy = rewardPolicyMapper.selectByBid(params);
		if(rewardPolicy!=null){
			return rewardPolicy;
		}
		return null;
	}
	
	/**
	 * cp端添加奖励政策信息
	 */
	@Override
	public Integer saveRewardPolicy(JSONObject json) throws Exception {
		RewardPolicy policy = JSONObject.toJavaObject(json, RewardPolicy.class);
		policy.setTime(ExDateUtils.getDate());
		this.rewardPolicyMapper.insertPolicy(policy);
		return Status.success;
	}
	/**
	 * cp 端删除商家奖励政策
	 */
	@Override
	public Integer delectRewardPolicy(Long id) {
		rewardPolicyMapper.deletePolicy(id);
		return Status.success;
	}
	/**
	 * cp 端修改商家奖励政策信息
	 */
	@Override
	public Integer updateRewardPolicy(JSONObject json) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		RewardPolicy policy = JSONObject.toJavaObject(json, RewardPolicy.class);
		param.put("time",ExDateUtils.getDate());
		param.put("title",policy.getTitle());
		param.put("content", policy.getContent());
		param.put("id", policy.getId());
		this.rewardPolicyMapper.updatePolicy(param);
		return Status.success;
		
	}
	
	
}
