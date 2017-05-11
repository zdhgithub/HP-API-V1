/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
public class GiveCoupons {

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 优惠券名称
	 */
	private String couponName;

	/**
	 * 优惠券金额(元)
	 */
	private Integer couponFee;

	/**
	 * 券的开始使用时间
	 */
	private Timestamp startTime;

	/**
	 * 券的过期时间
	 */
	private Timestamp endTime;

	/**
	 * 使用规则
	 */
	private Integer useRule;

	/**
	 * 优惠券总数量
	 */
	private Integer amount;

	/**
	 * 已发放数量
	 */
	private Integer count;

	/**
	 * 已使用数量
	 */
	private Integer useCount;

	/**
	 * 每人限制领券数量
	 */
	private Integer limit;

	/**
	 * 0:发行中,1:暂停,2:已停止,3:已完成
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * 1.大众券; 2:定向常规券，3:定向特定券
	 */
	private Integer category;

	/**
	 * 收费金额(元)
	 */
	private Integer fee;

	// 接收参数
	private Set<Long> uids;
	// 种类 1:在商家消费过的用户, 2:关注商家的用户,3:关注商家个人品牌的用户
	private Set<Integer> flag;

	//是否已领券
	private Boolean isGet;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getUseRule() {
		return useRule;
	}

	public void setUseRule(Integer useRule) {
		this.useRule = useRule;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Set<Long> getUids() {
		return uids;
	}

	public void setUids(Set<Long> uids) {
		this.uids = uids;
	}

	public Set<Integer> getFlag() {
		return flag;
	}

	public void setFlag(Set<Integer> flag) {
		this.flag = flag;
	}

	public Boolean getIsGet() {
		return isGet;
	}

	public void setIsGet(Boolean isGet) {
		this.isGet = isGet;
	}

}
