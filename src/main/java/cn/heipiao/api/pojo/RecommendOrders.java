/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public class RecommendOrders {

	/**
	 * 订单号 yyMMddHHmmss + 被推荐人uid
	 */
	private String orderId;
	
	/**
	 * 被推荐人id
	 */
	private Long uid;
	
	/**
	 * 推荐人id
	 */
	private Long ruid;
	
	/**
	 * 类型 1：登录奖励 ，2：消费奖励
	 */
	private Integer type;
	
	/**
	 * 描述信息
	 */
	private String desc;
	
	/**
	 * 奖励漂币数量
	 */
	private Integer amount;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRuid() {
		return ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
