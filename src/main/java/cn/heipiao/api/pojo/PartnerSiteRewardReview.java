package cn.heipiao.api.pojo;

import java.sql.Timestamp;


public class PartnerSiteRewardReview {
	/**
	 * 钓场id
	 */
	private Integer siteId;
	/**
	 * 上限奖金shelvesAmount
	 */
	private Integer shelvesAmount;
	/**
	 * 上限奖金审核状态shelvesStatus
	 */
	private int shelvesStatus;
	/**
	 * 交易奖金
	 */
	private Integer tradingAmount;
	/**
	 * 交易审核状态
	 */
	private int tradingStatus;
	/**
	 * 上架奖金审核时间
	 */
	private Timestamp shelvesTime;
	/**
	 *交易奖金审核时间
	 */
	private Timestamp tradingTime;
	public Integer getsiteId() {
		return siteId;
	}
	public Integer getShelvesAmount() {
		return shelvesAmount;
	}
	public int getShelvesStatus() {
		return shelvesStatus;
	}
	public Integer getTradingAmount() {
		return tradingAmount;
	}
	public int getTradingStatus() {
		return tradingStatus;
	}
	public Timestamp getShelvesTime() {
		return shelvesTime;
	}
	public Timestamp getTradingTime() {
		return tradingTime;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public void setShelvesAmount(Integer shelvesAmount) {
		this.shelvesAmount = shelvesAmount;
	}
	public void setShelvesStatus(int shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}
	public void setTradingAmount(Integer tradingAmount) {
		this.tradingAmount = tradingAmount;
	}
	public void setTradingStatus(int tradingStatus) {
		this.tradingStatus = tradingStatus;
	}
	public void setShelvesTime(Timestamp shelvesTime) {
		this.shelvesTime = shelvesTime;
	}
	public void setTradingTime(Timestamp tradingTime) {
		this.tradingTime = tradingTime;
	}
	
	
}
