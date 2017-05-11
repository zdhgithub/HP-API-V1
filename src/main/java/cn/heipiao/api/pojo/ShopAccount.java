/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */

public class ShopAccount {

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 用户id
	 */
	private Long shopId;
	
	/**
	 * 用户余额
	 */
	private Double balance;

	/**
	 * 最近一次提现时间
	 */
	private Timestamp withdrawTime;

	/**
	 * 提现规则id
	 */
	private Integer withdrawRuleId;
	
	/**
	 * 付款码 md5(uid+shopId)
	 */
	private String payCode;
	
	/**
	 * 渔具店名称
	 */
	private String shopName;

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * @return the withdrawTime
	 */
	public Timestamp getWithdrawTime() {
		return withdrawTime;
	}

	/**
	 * @param withdrawTime
	 *            the withdrawTime to set
	 */
	public void setWithdrawTime(Timestamp withdrawTime) {
		this.withdrawTime = withdrawTime;
	}


	public Integer getWithdrawRuleId() {
		return withdrawRuleId;
	}

	public void setWithdrawRuleId(Integer withdrawRuleId) {
		this.withdrawRuleId = withdrawRuleId;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
