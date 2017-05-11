/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.UserGoldCoin;

/**
 * @author wzw
 * @date 2016年8月16日
 * @version 1.0
 */
public interface UserGoldCoinMapper {

	UserGoldCoin selectByUid(Long uid);
	
	
	UserGoldCoin selectByUidAsLock(Long uid);
	
	
	void insertPojo(UserGoldCoin pojo);
	
	/**
	 * 只更新充值漂币和收益漂币
	 * @param pojo
	 */
	void updatePojo(UserGoldCoin pojo);
	
	/**
	 * 更新收益漂币和提现日期
	 * @param pojo
	 */
	void updateEarningsAndWithdrawDate(UserGoldCoin pojo);
}
