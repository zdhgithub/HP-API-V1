package cn.heipiao.api.pojo;

import java.util.Date;

public class RewardOfMonth {
	/**
	 * 平台奖励月账单id
	 */
	private Long id;
	/**
	 * 平台奖励商家id
	 */
	private Long bid;
	/**
	 * 平台奖励商家类型
	 */
	private int type;
	/**
	 * 平台奖励月账单时间
	 */
	private Date time;
	/**
	 * 月累计返利金额
	 */
	private Double total;
	/**
	 * 月累计核票次数
	 */
	private int ticketNum;
	/**
	 * 月累计支付次数
	 */
	private int payNum;
	/**
	 * 月累计提点金额
	 */
	private double rebateTotal;
	public Long getId() {
		return id;
	}
	public Long getBid() {
		return bid;
	}
	public int getType() {
		return type;
	}
	public Date getTime() {
		return time;
	}
	public Double getTotal() {
		return total;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	public int getPayNum() {
		return payNum;
	}
	public double getRebateTotal() {
		return rebateTotal;
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
	public void setTime(Date time) {
		this.time = time;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}
	public void setRebateTotal(double rebateTotal) {
		this.rebateTotal = rebateTotal;
	}
	
}