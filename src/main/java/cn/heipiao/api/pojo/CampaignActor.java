package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * 活动参与者
 * 
 * @author Chris
 * @version 3.0
 * @date 2017-03-03
 *
 */
public class CampaignActor {

	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 用户id
	 */
	private Integer uid;

	/**
	 * 用户名称
	 */
	private String name;

	/**
	 * 用户头像
	 */
	private String portriat;

	/**
	 * 活动id
	 */
	private Integer cid;

	/**
	 * 报名时间
	 */
	private Date entryTime;

	/**
	 * 支持类型，0线下支付，1线上支付
	 */
	private Integer payType;

	/**
	 * 支付金额，线下支付时为0
	 */
	private Float payAmount;

	/**
	 * 抽签号码
	 */
	private Integer luckyNumber;

	/**
	 * 备注
	 */
	private String Remark;

	/**
	 * 支付状态 0:待支付，1:已支付,2:线下付款, 3:已取消
	 */
	private Integer payStatus;

	/**
	 * 退款状态，0未退款，1已退款
	 */
	private Integer refundStatus;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

}
