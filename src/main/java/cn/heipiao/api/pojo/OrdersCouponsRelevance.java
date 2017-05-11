/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public class OrdersCouponsRelevance {

	/**
	 * 订单id
	 */
	private String orderId;
	
	/**
	 * 优惠券id
	 */
	private Integer couponId;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

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
	 * @return the couponId
	 */
	public Integer getCouponId() {
		return couponId;
	}

	/**
	 * @param couponId the couponId to set
	 */
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
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

	
}
