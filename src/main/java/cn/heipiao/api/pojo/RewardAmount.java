package cn.heipiao.api.pojo;

import java.sql.Timestamp;



public class RewardAmount {
	/**
	 * 平台奖励商家账户id
	 */
	private Long id;
	/**
	 * 平台奖励商家id
	 */
	private Long bid;
	/**
	 * 平台奖励商家类型 0表示渔具店 1表示钓场
	 */
	private int type;
	/**
	 * 平台奖励商家账户余额
	 */
	private Double balance;
	/**
	 * 平台奖励商家累计奖励金（是实时的数据）
	 */
	private  Double total;
	/**
	 * 平台奖励商家未体现金额
	 */
	private Double remain;
	/**
	 * 商家最近提现时间
	 * @return
	 */
	private Timestamp withdrawTime;

	
	public Long getId() {
		return id;
	}
	public Long getBid() {
		return bid;
	}
	public int getType() {
		return type;
	}
	public Double getBalance() {
		return balance;
	}
	public Double getTotal() {
		return total;
	}
	public Double getRemain() {
		return remain;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public void setRemain(Double remain) {
		this.remain = remain;
	}
	public Timestamp getWithdrawTime() {
		return withdrawTime;
	}
	public void setWithdrawTime(Timestamp withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	
}
