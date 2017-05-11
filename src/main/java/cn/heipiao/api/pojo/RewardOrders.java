/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public class RewardOrders {

	/**
	 * 交易号 yyMMddHHmmss + uid'
	 */
	private String tradeId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 类型 1：钓场 ，2：店铺
	 */
	private Integer serviceId;
	
	/**
	 * 钓场对应的是tid，店铺对应的是orderId
	 */
	private String orderId;
	
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

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
