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
public class Coupons {

	/**
	 * 优惠券id
	 */
	private Long cid;

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 优惠券名称
	 */
	private String couponName;
	
	/**
	 * 优惠券金额:元
	 */
	private Integer couponFee;

	/**
	 * 使用规则比如满指定的金额可以使用：元
	 */
	private Integer useRule;
	
	/**
	 * 使用日期
	 */
	private Timestamp useTime;
	
	/**
	 * 截止日期
	 */
	private Timestamp deadline;
	
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 优惠券获取时间
	 */
	private Timestamp getTime;

	/**
	 * 开始使用时间
	 */
	private Timestamp startTime;

	/**
	 * 优惠券类别 0:平台, 1:合伙人钓场券, 2:钓场, 3:店铺 ，4:合伙人店铺券 ，5:平台钓场券,6:平台渔具券
	 */
	private Integer category;

	/**
	 * 优惠券状态  0:未使用,1:已使用,2:已过期
	 */
	private Integer status;

	/**
	 * 优惠券收费费用(元)
	 */
	private Integer fee;
	/**
	 * 新旧标识1标识新，0标识旧/已读
	 */
	private boolean isRead; 
	
	
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the cid
	 */
	public Long getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(Long cid) {
		this.cid = cid;
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
	 * @return the couponName
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * @param couponName the couponName to set
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	/**
	 * @return the couponFee
	 */
	public Integer getCouponFee() {
		return couponFee;
	}

	/**
	 * @param couponFee the couponFee to set
	 */
	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	/**
	 * @return the useRule
	 */
	public Integer getUseRule() {
		return useRule;
	}

	/**
	 * @param useRule the useRule to set
	 */
	public void setUseRule(Integer useRule) {
		this.useRule = useRule;
	}

	/**
	 * @return the useTime
	 */
	public Timestamp getUseTime() {
		return useTime;
	}

	/**
	 * @param useTime the useTime to set
	 */
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	/**
	 * @return the deadline
	 */
	public Timestamp getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the getTime
	 */
	public Timestamp getGetTime() {
		return getTime;
	}

	/**
	 * @param getTime the getTime to set
	 */
	public void setGetTime(Timestamp getTime) {
		this.getTime = getTime;
	}

	/**
	 * @return the category
	 */
	public Integer getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Integer category) {
		this.category = category;
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

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

}
