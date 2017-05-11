package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.mapper.RewardDetailMapper;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.RewardDetail;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.RewardDetailService;
import cn.heipiao.api.utils.ResultUtils;
/**
 * 
 * @ClassName: RewardDetailServiceImpl
 * @Description: TODO
 * @author duzh
 * @date 2017年1月17日
 */
@Service
public class RewardDetailServiceImpl implements RewardDetailService{
	
	@Resource
	private RewardDetailMapper rewardDetailMapper;
	/**
	 * 获取商家平台奖励明细
	 */
	@Override
	public List<RewardDetail> selectByBid(Long bid, int type,int start,int size) {
		Map<String,Object> params = new HashedMap<String, Object>();
		params.put("bid", bid);
		params.put("btype", type);
		params.put("size", size);
		params.put("start", start);
		List<RewardDetail> list = rewardDetailMapper.selectByBid(params);
		return list;
	}
	/**
	 * 添加商家平台奖励明细
	 */
	@Override
	public int insertRewardDetail(RewardDetail rewardDetail) {
		rewardDetailMapper.insertRewardDetail(rewardDetail);
		return Status.success;
	}

}
