package cn.heipiao.api.service;

/**
 * @author zf
 * @version 1.0
 * @description 用户账户操作
 * @date 2016年7月11日
 */
public interface UserBalanceOpService {
	/**
	 * 增加用户存鱼账户余额
	 * 
	 * @param userId
	 *            用户ID
	 * @param siteId
	 *            钓场ID
	 * @param amount
	 *            增加额
	 */
	public void inUserDepositFishBalance(Long userId, Integer siteId,
			Double amount);

	/**
	 * 减少用户存鱼账户余额
	 * 
	 * @param userId
	 *            用户ID
	 * @param siteId
	 *            钓场ID
	 * @param amount
	 *            减少额
	 */
	public void deUserDepositFishBalance(Long userId, Integer siteId,
			Double amount);

	/**
	 * 增加用户账户余额
	 * 
	 * @param userId
	 *            用户ID
	 * @param amount
	 *            增加额
	 */
	public void inUserBalance(Long userId, Double amount);

	/**
	 * 减少用户账户余额
	 * 
	 * @param userId
	 *            用户ID
	 * @param amount
	 *            减少额
	 */
	public void deUserBalance(Long userId, Double amount);

}
