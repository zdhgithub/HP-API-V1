/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public class PartnerCouponRecord {

	/**
	 * 合伙人id
	 */
	private Integer partnerId;
	
	/**
	 * 领券人的uid
	 */
	private Long uid;
	
	/**
	 * 手机号
	 */
	private String phone; 
	
	/**
	 * 券id
	 */
	private Long couponId;
	
	/**
	 * 对应优惠券状态一样
	 */
	private Integer status;
	
	/**
	 * 发放时间
	 */
	private Timestamp createTime;

	/**
	 * @return the partnerId
	 */
	public Integer getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	
}
