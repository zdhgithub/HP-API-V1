package cn.heipiao.api.pojo;

import java.sql.Date;
import java.sql.Timestamp;


public class PartnerShopRewardReview {
	/**
	 * 店铺id
	 */
	private Long shopId;
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
	public Long getShopId() {
		return shopId;
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
	public void setShopId(Long shopId) {
		this.shopId = shopId;
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
