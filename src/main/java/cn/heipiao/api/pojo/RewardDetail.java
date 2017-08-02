package cn.heipiao.api.pojo;

import java.util.Date;

public class RewardDetail {
	/**
	 * 平台奖励明细id
	 */
	private Long id;
	/**
	 * 平台奖励类型
	 */
	private int type;
	/**
	 * 平台奖励明细说明
	 */
	private String description;
	/**
	 * 平台奖励金额
	 */
	private Double amount;
	/**
	 * 平台奖励明细日期
	 */
	private Date time;
	/**
	 * 平台奖励明细商家id
	 */
	private Long bid;
	/**
	 * 平台奖励商家类  0表示渔具店 1表示钓场
	 */
	private int btype;
	public Long getId() {
		return id;
	}
	public int getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public Double getAmount() {
		return amount;
	}
	public Date getTime() {
		return time;
	}
	public Long getBid() {
		return bid;
	}
	public int getBtype() {
		return btype;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public void setBtype(int btype) {
		this.btype = btype;
	}
	
}
