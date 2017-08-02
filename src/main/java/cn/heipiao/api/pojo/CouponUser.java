package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 用户券关联
 * @date 2016年7月19日
 */
public class CouponUser implements Serializable {
	private static final long serialVersionUID = 6383627206994149455L;
	
	/** ID */
	private Long id;
	/** 用户ID */
	private Integer userId;
	/** 券ID */
	private Integer couponId;
	/** 有效期至.. */
	private Date indateTime;
	/** 获取时间 */
	private Date getTime;
	/** 使用时间 */
	private Date usedTime;
	/** 优惠券的使用状态0:未使用,1:已使用,2:已过期 */
	private Integer status;
	/** 券 */
	private Coupon coupon;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public Date getIndateTime() {
		return indateTime;
	}

	public Date getGetTime() {
		return getTime;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public void setIndateTime(Date indateTime) {
		this.indateTime = indateTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}
