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

public class Account {

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 用户余额
	 */
	private Double balance;

	/**
	 * 最近一次提现时间
	 */
	private Timestamp withdrawTime;

	/**
	 * 支付密码
	 */
	private String payPwd;
	
	/**
	 * 提现规则id
	 */
	private Integer withdrawRuleId;

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

	/**
	 * @return the payPwd
	 */
	public String getPayPwd() {
		return payPwd;
	}

	/**
	 * @param payPwd
	 *            the payPwd to set
	 */
	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public Integer getWithdrawRuleId() {
		return withdrawRuleId;
	}

	public void setWithdrawRuleId(Integer withdrawRuleId) {
		this.withdrawRuleId = withdrawRuleId;
	}

}
