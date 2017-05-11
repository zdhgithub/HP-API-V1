package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardPlatformWithdrawOrder;

/**
 * 
 * @ClassName: RewardAmountService
 * @Description: 商家平台奖励账户
 * @author duzh
 * @date 2017年1月17日
 */
public interface RewardAmountService {
	/**
	 * 商家获取平台奖励账户信息
	 */
	RewardAmount findRewardAmount(Long bid,int type);
	/**
	 * 修改商家平台奖励账户信息
	 */
	int updateRewardAmount(RewardAmount rewardAmount);
	/**
	 * 开通商家平台奖账户
	 */
	int insertRewardAmount(RewardAmount rewardAmount);
	/**
	 * 渔具店奖励平台提现申请
	 * @return
	 */
	int bussinessAccountWithdrawApply(RewardPlatformWithdrawOrder wo)throws Exception;
	/**
	 *奖励平台提现
	 */
	int bussinessAccountWithdraw(RewardPlatformWithdrawOrder wo,boolean ...bs) throws Exception;
	/**
	 * 钓场奖励平台提现申请
	 * @return
	 */
	int fishSiteBussinessAccountWithdrawApply(RewardPlatformWithdrawOrder wo)throws Exception;
	/**
	 * 提现订单
	 */
	List<RewardPlatformWithdrawOrder> selectListByLimit(Map<String, Object> map);
}
