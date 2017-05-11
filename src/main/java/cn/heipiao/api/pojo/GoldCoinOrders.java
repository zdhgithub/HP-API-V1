/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月19日
 * @version 1.0
 */
public class GoldCoinOrders {

	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 用户id
	 */
	private Long uid; 
	
	/**
	 * 支付金额   单位:元
	 */
	private Integer ordersMoney;
	
	/**
	 * 赠送漂币
	 */
	private Integer givingGoldCoin;
	
	/**
	 * 交易平台 1:微信,2:支付宝
	 */
	private Integer tradePlatform;

	/**
	 * 平台交易号
	 */
	private String tradeNo;
	
	/**
	 * 订单状态0:待支付,1:支付已完成,2:订单支付超时,3:订单取消,4:订单完成
	 */
	private Integer status;
	
	/**
	 * 支付时间
	 */
	private Timestamp payTime;

	/**
	 * 订单创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 关闭订单时间
	 */
	private Timestamp closeTime;

	
	/**
	 * 订单来自那个app（1:平台的,2:小程序的）
	 */
	private Integer  whereIsApp;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the ordersMoney
	 */
	public Integer getOrdersMoney() {
		return ordersMoney;
	}

	/**
	 * @param ordersMoney the ordersMoney to set
	 */
	public void setOrdersMoney(Integer ordersMoney) {
		this.ordersMoney = ordersMoney;
	}

	/**
	 * @return the givingGoldCoin
	 */
	public Integer getGivingGoldCoin() {
		return givingGoldCoin;
	}

	/**
	 * @param givingGoldCoin the givingGoldCoin to set
	 */
	public void setGivingGoldCoin(Integer givingGoldCoin) {
		this.givingGoldCoin = givingGoldCoin;
	}

	/**
	 * @return the tradePlatform
	 */
	public Integer getTradePlatform() {
		return tradePlatform;
	}

	/**
	 * @param tradePlatform the tradePlatform to set
	 */
	public void setTradePlatform(Integer tradePlatform) {
		this.tradePlatform = tradePlatform;
	}

	/**
	 * @return the tradeNo
	 */
	public String getTradeNo() {
		return tradeNo;
	}

	/**
	 * @param tradeNo the tradeNo to set
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the payTime
	 */
	public Timestamp getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the closeTime
	 */
	public Timestamp getCloseTime() {
		return closeTime;
	}

	/**
	 * @param closeTime the closeTime to set
	 */
	public void setCloseTime(Timestamp closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getWhereIsApp() {
		return whereIsApp;
	}

	public void setWhereIsApp(Integer whereIsApp) {
		this.whereIsApp = whereIsApp;
	}
	
}
