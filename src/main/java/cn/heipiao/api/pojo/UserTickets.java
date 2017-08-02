/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年7月1日
 * @version 1.0
 */
public class UserTickets {

	/**
	 * 票id
	 */
	private Long tid;

	/**
	 * 票名称
	 */
	private String ticketName;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 钓场id
	 */
	private Integer fishSiteId;

	/**
	 * 线下消费产品id
	 */
	private Long pid;
	
	/**
	 * 商品id
	 */
	private Integer goodId;

	/**
	 * 原单价
	 */
	private Double primeCost;

	/**
	 * 实付款
	 */
	private Double outOfPocket;

	/**
	 * 使用的存鱼金额
	 */
	private Double depositFee;
	
	/**
	 * 使用漂币
	 */
	private Integer goldCoinFee;
	
	/**
	 * 使用收益漂币
	 */
	private Integer earningsGoldCoinFee;
	
	/**
	 * 使用的优惠券金额
	 */
	private Double couponsFee;
	
	/**
	 * 使用的优惠券Id
	 */
	private Long couponId;

	/**
	 * 钓鱼时长
	 */
	private Float duration;

	/**
	 * 购买时间
	 */
	private Timestamp buyTime;

	/**
	 * 有效期(暂时保留)
	 */
	private Timestamp expirationDate;

	/**
	 * 使用时间
	 */
	private Timestamp useTime;

	/**
	 * 状态 0:未使用,1:已使用,2:已退票,3:过期
	 */
	private Integer status;

	/**
	 * 钓场名称
	 */
	private String fishSiteName;
	/**
	 * 主图
	 */
	private String mainImg;
	/**
	 * 详细地址
	 */
	private String address;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 用户头像
	 */
	private String portriat;
	/**
	 * 票编码
	 */
	private Integer ticketCode;
	
	//实付总价
	private Double payMoney;
	
	
	/**
	 * 优惠券收费费用(漂币)
	 */
	private Integer couponFee;
	
	public Integer getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(Integer ticketCode) {
		this.ticketCode = ticketCode;
	}

	public Long getTid() {
		return tid;
	}

	public String getOrderId() {
		return orderId;
	}

	public Long getUid() {
		return uid;
	}

	public Integer getFishSiteId() {
		return fishSiteId;
	}

	public Long getPid() {
		return pid;
	}

	/**
	 * @return the goodId
	 */
	public Integer getGoodId() {
		return goodId;
	}

	/**
	 * @param goodId the goodId to set
	 */
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public Double getPrimeCost() {
		return primeCost;
	}

	public Double getOutOfPocket() {
		return outOfPocket;
	}

	public Double getDepositFee() {
		return depositFee;
	}

	public Float getDuration() {
		return duration;
	}

	public Timestamp getBuyTime() {
		return buyTime;
	}

	public Timestamp getExpirationDate() {
		return expirationDate;
	}

	public Timestamp getUseTime() {
		return useTime;
	}

	public Integer getStatus() {
		return status;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getMainImg() {
		return mainImg;
	}

	public String getAddress() {
		return address;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	/**
	 * @return the ticketName
	 */
	public String getTicketName() {
		return ticketName;
	}

	/**
	 * @param ticketName
	 *            the ticketName to set
	 */
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public void setPrimeCost(Double primeCost) {
		this.primeCost = primeCost;
	}

	public void setOutOfPocket(Double outOfPocket) {
		this.outOfPocket = outOfPocket;
	}

	public void setDepositFee(Double depositFee) {
		this.depositFee = depositFee;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public void setBuyTime(Timestamp buyTime) {
		this.buyTime = buyTime;
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	/**
	 * @return the couponId
	 */
	public Long getCouponId() {
		return couponId;
	}

	/**
	 * @param couponId the couponId to set
	 */
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the portriat
	 */
	public String getPortriat() {
		return portriat;
	}

	/**
	 * @param portriat
	 *            the portriat to set
	 */
	public void setPortriat(String portriat) {
		this.portriat = portriat;
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
	 * @return the couponsFee
	 */
	public Double getCouponsFee() {
		return couponsFee;
	}

	/**
	 * @param couponsFee the couponsFee to set
	 */
	public void setCouponsFee(Double couponsFee) {
		this.couponsFee = couponsFee;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

}
