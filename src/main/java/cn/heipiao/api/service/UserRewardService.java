/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.UserReward;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public interface UserRewardService {

	/**
	 * 奖励
	 * 核票奖励和票支付奖励处理方法
	 * 需要根据城市的配置去查看是否有奖励
	 * @param uid 用户id
	 * @param serviceId 业务号 1：钓场，2：店铺
	 * @param amount 消费金额(漂币)
	 * @param orderId 钓场对应的是tid，店铺对应的是orderId
	 * @param cityId 城市id
	 * @param otherAmount 其他漂币
	 * @return
	 */
	int reward(Long uid ,Integer serviceId,Integer amount,String orderId,Integer cityId);
	
	
	void addPojo(Long uid,Integer pAmount,Integer bAmount);


	/**
	 * @param uid
	 * @return
	 */
	UserReward getUserReward(Long uid);
}
