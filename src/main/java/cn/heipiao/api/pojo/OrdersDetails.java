/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public class OrdersDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6038319865646342277L;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 图片base64
	 */
	private String img;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 原单价
	 */
	private Double price;
	
	/**
	 * 优惠价
	 */
	private Double discountPrice;

	/**
	 * 现价（实付单价）
	 */
	private Double payPrice;

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
	 * 购买数量
	 */
	private Integer amount;

	/**
	 * 时长
	 */
	private Float hourLong;

	/**
	 * 商品id
	 */
	private Integer goodId;
	
	/**
	 * 优惠券收费费用(漂币)
	 */
	private Integer couponFee;

	/**
	 * 线下消费产品id
	 */
	private Integer pid;

	/**
	 * @return the ordersId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param ordersId
	 *            the ordersId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img
	 *            the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName
	 *            the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the amount
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * @return the goodId
	 */
	public Integer getGoodId() {
		return goodId;
	}

	/**
	 * @param goodId
	 *            the goodId to set
	 */
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	/**
	 * @return the payPrice
	 */
	public Double getPayPrice() {
		return payPrice;
	}

	/**
	 * @param payPrice
	 *            the payPrice to set
	 */
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	/**
	 * @return the depositFee
	 */
	public Double getDepositFee() {
		return depositFee;
	}

	/**
	 * @param depositFee
	 *            the depositFee to set
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

	/**
	 * @return the hourLong
	 */
	public Float getHourLong() {
		return hourLong;
	}

	/**
	 * @param hourLong
	 *            the hourLong to set
	 */
	public void setHourLong(Float hourLong) {
		this.hourLong = hourLong;
	}

	/**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * @return the discountPrice
	 */
	public Double getDiscountPrice() {
		return discountPrice;
	}

	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

}
