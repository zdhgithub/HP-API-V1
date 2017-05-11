/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月28日
 * @version 1.0
 */
public class ShopTradeOrders {

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
	private Long shopId;

	/**
	 * 订单金额(订单总价)
	 */
	private Double ordersMoney;

	/**
	 * 支付金额(实付款)
	 */
	private Double payMoney;

	/**
	 * 优惠券金额
	 */
	private Integer couponsMoney;

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
	 * 交易平台 0:商戶平台,1:微信,2:支付宝
	 */
	private Integer tradePlatform;

	/**
	 * 平台交易号
	 */
	private String tradeNo;

	/**
	 * 优惠券收费费用(漂币)
	 */
	private Integer couponsFee;

	// 距离
	private Long distance;

	// 经度
	private Double lng;

	// 纬度
	private Double lat;

	/**
	 * 订单来自那个app（1:平台的,2:小程序的）
	 */
	private Integer whereIsApp;

	private String phoneNum;

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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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
	 * @return the couponsMoney
	 */
	public Integer getCouponsMoney() {
		return couponsMoney;
	}

	/**
	 * @param couponsMoney
	 *            the couponsMoney to set
	 */
	public void setCouponsMoney(Integer couponsMoney) {
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
	 * @param goldCoinMoney
	 *            the goldCoinMoney to set
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
	 * @param tradePlatform
	 *            the tradePlatform to set
	 */
	public void setTradePlatform(Integer tradePlatform) {
		this.tradePlatform = tradePlatform;
	}

	public Integer getCouponsFee() {
		return couponsFee;
	}

	public void setCouponsFee(Integer couponsFee) {
		this.couponsFee = couponsFee;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getWhereIsApp() {
		return whereIsApp;
	}

	public void setWhereIsApp(Integer whereIsApp) {
		this.whereIsApp = whereIsApp;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
