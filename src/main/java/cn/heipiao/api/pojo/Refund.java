/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年7月19日
 * @version 1.0
 */
public class Refund {
	/**
	 * 退款号
	 */
	private String refundNo;
	
	/**
	 * 票id
	 */
	private Long tid;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 钓场id
	 */
	private Integer fishSiteId;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 平台交易号
	 */
	private String tradeNo;
	
	/**
	 * 订单总金额
	 */
	private Double totalFee;
	
	/**
	 * 退款金额
	 */
	private Double refundFee;
	
	/**
	 * 退使用的存鱼
	 */
	private Double depositFee;
	
	/**
	 * 单个票使用的漂币金额
	 */
	private Integer goldCoinFee;
	
	/**
	 * 单个票使用的收益漂币金额
	 */
	private Integer earningsGoldCoinFee;
	
	/**
	 * 状态 0:申请退款，1：申请退款中，2：退款完成，3：退款失败 
	 */
	private Integer status;
	
	/**
	 * 交易平台  1：微信，2：支付宝
	 */
	private Integer tradePlatform;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 确认退款时间
	 */
	private Timestamp confirmRefundTime;
	
	/**
	 * 退票原因
	 */
	private String reason;

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
	 * @return the fishSiteId
	 */
	public Integer getFishSiteId() {
		return fishSiteId;
	}

	/**
	 * @param fishSiteId the fishSiteId to set
	 */
	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	/**
	 * @return the refundNo
	 */
	public String getRefundNo() {
		return refundNo;
	}

	/**
	 * @param refundNo the refundNo to set
	 */
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

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
	 * @return the totalFee
	 */
	public Double getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the refundFee
	 */
	public Double getRefundFee() {
		return refundFee;
	}

	/**
	 * @param refundFee the refundFee to set
	 */
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * @return the depositFee
	 */
	public Double getDepositFee() {
		return depositFee;
	}

	/**
	 * @param depositFee the depositFee to set
	 */
	public void setDepositFee(Double depositFee) {
		this.depositFee = depositFee;
	}

	/**
	 * @return the goldCoinFee
	 */
	public Integer getGoldCoinFee() {
		return goldCoinFee;
	}

	/**
	 * @param goldCoinFee the goldCoinFee to set
	 */
	public void setGoldCoinFee(Integer goldCoinFee) {
		this.goldCoinFee = goldCoinFee;
	}

	public Integer getEarningsGoldCoinFee() {
		return earningsGoldCoinFee;
	}

	public void setEarningsGoldCoinFee(Integer earningsGoldCoinFee) {
		this.earningsGoldCoinFee = earningsGoldCoinFee;
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
	 * @return the confirmRefundTime
	 */
	public Timestamp getConfirmRefundTime() {
		return confirmRefundTime;
	}

	/**
	 * @param confirmRefundTime the confirmRefundTime to set
	 */
	public void setConfirmRefundTime(Timestamp confirmRefundTime) {
		this.confirmRefundTime = confirmRefundTime;
	}

	/**
	 * @return the tid
	 */
	public Long getTid() {
		return tid;
	}

	/**
	 * @param tid the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

}
