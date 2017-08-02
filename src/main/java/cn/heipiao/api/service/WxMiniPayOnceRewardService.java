package cn.heipiao.api.service;
/**
 * @author wzw
 * @date 2017年2月20日
 */
public interface WxMiniPayOnceRewardService {

	int reward(Long uid,String orderId, Integer category);
	
}
