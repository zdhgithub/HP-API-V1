package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.RewardPlatformWithdrawOrder;

/**
 * 
 * @ClassName: RewardPlarformWithdrawOrderMapper
 * @Description: 平台奖励提现订单
 * @author duzh
 * @date 2017年1月19日
 */
public interface RewardPlatformWithdrawOrderMapper {
	/**
	 * 加锁
	 * 
	 * @param tradeNo
	 * @return
	 */
	RewardPlatformWithdrawOrder selectByTradeNoAsLock(String tradeNo);

	/**
	 * 
	 * @param tradeNo
	 * @return
	 */
	RewardPlatformWithdrawOrder selectByTradeNo(String tradeNo);

	/**
	 * 
	 * @param pojo
	 */
	void insert(RewardPlatformWithdrawOrder pojo);

	/**
	 * 
	 * @param pojo
	 */
	void update(RewardPlatformWithdrawOrder pojo);

	/**
	 * @param map
	 * @return
	 */
	List<RewardPlatformWithdrawOrder> selectListByLimit(Map<String, Object> map);

	/**
	 * 线程调用
	 * 
	 * @param map
	 * @return
	 */
	List<RewardPlatformWithdrawOrder> selectByStatus(Map<String, Object> map);

}
