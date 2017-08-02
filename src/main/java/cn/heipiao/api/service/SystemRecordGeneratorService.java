package cn.heipiao.api.service;

/**
 * @author zf
 * @version 1.0
 * @description 生成交易记录
 * @date 2016年7月11日
 */
public interface SystemRecordGeneratorService {
	/**
	 * 出账交易记录
	 * 
	 * @param userId
	 *            用户id
	 * @param orderId
	 *            交易号/订单号
	 * @param tradeType
	 *            交易类型（交易类型0表示消费（购票）1表示充值，2表示提现）
	 * @param tradeMoney
	 *            交易金额
	 * @param description
	 *            交易描述,可为空
	 * @param otherSide
	 *            对方/描述资金的输入输出
	 * @param status
	 *            交易记录状态(0表示失败，1表示成功)
	 */
	public void generateAccountDeRecord(Integer userId, String orderId,
			int tradeType, double tradeMoney, String description,
			String otherSide, Integer status,Integer fishSiteId);

	/**
	 * 入账交易记录
	 * 
	 * @param userId
	 *            用户id
	 * @param orderId
	 *            交易号/订单号
	 * @param tradeType
	 *            交易类型（交易类型0表示消费（购票）1表示充值，2表示提现）
	 * @param tradeMoney
	 *            交易金额
	 * @param description
	 *            交易描述,可为空
	 * @param otherSide
	 *            对方/描述资金的输入输出
	 * @param status
	 *            交易记录状态(0表示失败，1表示成功)
	 */
	public void generateAccountInRecord(Integer userId, String orderId,
			int tradeType, double tradeMoney, String description,
			String otherSide, Integer status,Integer fishSiteId);

	/**
	 * 消费存鱼记录
	 * 
	 * @param userId
	 *            用户ID
	 * @param siteId
	 *            钓场ID
	 * @param tradeMoney
	 *            交易金额
	 */
	public void generateDeDepositFishRecord(Long userId, Integer siteId,
			Double tradeMoney);

	/**
	 * 增加存鱼记录
	 * 
	 * @param userId
	 *            用户ID
	 * @param siteId
	 *            钓场ID
	 * @param tradeMoney
	 *            交易金额
	 */
	public void generateInDepositFishRecord(Long userId, Integer siteId,
			Double tradeMoney);
}
