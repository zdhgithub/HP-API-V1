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
public class PartnerDevlopOrders {

	/**
	 * 订单号  yyMMddHHmmss + 合伙人uid
	 */
	private String orderId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 合伙人id
	 */
	private Integer partnerId;
	
	/**
	 * 商家id
	 */
	private Long bid;
	
	/**
	 * 商家类型0表示渔具店1表示钓场
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

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
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

	public Long getBid() {
		return bid;
	}

	public Integer getType() {
		return type;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
