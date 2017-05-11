/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wzw
 * @date 2016年6月28日
 * @version 1.0
 */
public class Orders {

	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 用户uid
	 */
	private Long uid;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 用户头像
	 */
	private String portriat;

	/**
	 * 钓场id
	 */
	private Integer fishSiteId;

	/**
	 * 订单金额(订单总价)
	 */
	private Double ordersMoney;

	/**
	 * 支付金额(实付款)
	 */
	private Double payMoney;

	/**
	 * 使用的存鱼总金额
	 */
	private Double depositMoney;

	/**
	 *  平台优惠券金额
	 */
	private Double couponsMoney;
	
	/**
	 * 其他优惠券金额 (不计入商家收入中)
	 */
	private Integer otherCouponsMoney;
	
	/**
	 * 使用漂币
	 */
	private Integer goldCoinMoney;
	
	/**
	 * 使用收益漂币
	 */
	private Integer earningsGoldCoinMoney;

	/**
	 * 支付时间
	 */
	private Timestamp payTime;

	/**
	 * 订单创建时间
	 */
	private Timestamp createTime;

	/**
	 * 订单状态0:待支付,1:支付已完成,2:订单支付超时,3:订单取消,4:订单完成
	 */
	private Integer status;

	/**
	 * 优惠券id
	 */
	private Long couponId;

	/**
	 * 退款金额
	 */
	private Double refundFee;
	
	/**
	 * 交易平台  0:商戶平台,1:微信,2:支付宝
	 */
	private Integer tradePlatform;

	/**
	 * 平台交易号
	 */
	private String tradeNo;
	
	/**
	 * 优惠券收费费用(漂币)
	 */
	private Integer couponsFees;
	
	
	/**
	 * 购买产品列表
	 */
	private List<OrdersDetails> ordersDetails;
	/**
	 * 经度
	 */
	private Float lng;
	
	/**
	 * 纬度
	 */
	private Float lat;
	/**
	 * 距离
	 */
	private Double distance;
	
	/**
	 * 订单来自那个app（1:平台的,2:小程序的）
	 */
	private Integer  whereIsApp;

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Float getLng() {
		return lng;
	}

	public Float getLat() {
		return lat;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}


	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
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
	 * @param uid
	 *            the uid to set
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
	 * @param fishSiteId
	 *            the fishSiteId to set
	 */
	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	/**
	 * @return the ordersMoney
	 */
	public Double getOrdersMoney() {
		return ordersMoney;
	}

	/**
	 * @param ordersMoney
	 *            the ordersMoney to set
	 */
	public void setOrdersMoney(Double ordersMoney) {
		this.ordersMoney = ordersMoney;
	}

	/**
	 * @return the payMoney
	 */
	public Double getPayMoney() {
		return payMoney;
	}

	/**
	 * @param payMoney
	 *            the payMoney to set
	 */
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	/**
	 * @return the depositMoney
	 */
	public Double getDepositMoney() {
		return depositMoney;
	}

	/**
	 * @param depositMoney
	 *            the depositMoney to set
	 */
	public void setDepositMoney(Double depositMoney) {
		this.depositMoney = depositMoney;
	}

	/**
	 * @return the couponsMoney
	 */
	public Double getCouponsMoney() {
		return couponsMoney;
	}

	/**
	 * @param couponsMoney
	 *            the couponsMoney to set
	 */
	public void setCouponsMoney(Double couponsMoney) {
		this.couponsMoney = couponsMoney;
	}

	public Integer getOtherCouponsMoney() {
		return otherCouponsMoney;
	}

	public void setOtherCouponsMoney(Integer otherCouponsMoney) {
		this.otherCouponsMoney = otherCouponsMoney;
	}

	/**
	 * @return the goldCoinMoney
	 */
	public Integer getGoldCoinMoney() {
		return goldCoinMoney;
	}

	/**
	 * @param goldCoinMoney the goldCoinMoney to set
	 */
	public void setGoldCoinMoney(Integer goldCoinMoney) {
		this.goldCoinMoney = goldCoinMoney;
	}

	public Integer getEarningsGoldCoinMoney() {
		return earningsGoldCoinMoney;
	}

	public void setEarningsGoldCoinMoney(Integer earningsGoldCoinMoney) {
		this.earningsGoldCoinMoney = earningsGoldCoinMoney;
	}

	/**
	 * @return the payTime
	 */
	public Timestamp getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime
	 *            the payTime to set
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
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the couponId
	 */
	public Long getCouponId() {
		return couponId;
	}

	/**
	 * @param couponId
	 *            the couponId to set
	 */
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	/**
	 * @return the refundFee
	 */
	public Double getRefundFee() {
		return refundFee;
	}

	/**
	 * @param refundFee
	 *            the refundFee to set
	 */
	public void setRefundFee(Double refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * @return the tradeNo
	 */
	public String getTradeNo() {
		return tradeNo;
	}

	/**
	 * @param tradeNo
	 *            the tradeNo to set
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public Integer getCouponsFees() {
		return couponsFees;
	}

	public void setCouponsFees(Integer couponsFees) {
		this.couponsFees = couponsFees;
	}

	/**
	 * @return the ordersDetails
	 */
	public List<OrdersDetails> getOrdersDetails() {
		return ordersDetails;
	}

	/**
	 * @param ordersDetails
	 *            the ordersDetails to set
	 */
	public void setOrdersDetails(List<OrdersDetails> ordersDetails) {
		this.ordersDetails = ordersDetails;
	}

	public Integer getWhereIsApp() {
		return whereIsApp;
	}

	public void setWhereIsApp(Integer whereIsApp) {
		this.whereIsApp = whereIsApp;
	}

}
