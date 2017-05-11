package cn.heipiao.api.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.RewardPlatform;

/**
 * 
 * @ClassName: RewardConfigPlatformMapping
 * @Description: 平台奖励配置
 * @author zf
 * @date 2017年1月16日
 */
public interface RewardConfigPlatformMapper {
	/**
	 * cp通过商家id商家类型查询
	 */
	RewardPlatform selectRewardPlatformByBid(Map<String, Object> params);
	/**
	 * cp添加商家平台奖励配置
	 */
	void insertRewardPlatform(RewardPlatform rewardPlatform);
	/**
	 * cp修改商家平台奖励配置
	 */
	void updateRewardPlatform(Map<String, Object> params);
	/**
	 * 删除商家平台奖励配置
	 */
	void update(@Param("bid")Long bid,@Param("status")Integer status,@Param("type")Integer type);
}
