package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2017年2月20日
 */
public class WxMiniPayOnceReward {
	/**
	 * 交易号
	 */
	private String tradeId;

	/**
	 * 用户uid
	 */
	private Long uid;

	/**
	 * 对应的支付订单号
	 */
	private String orderId;

	/**
	 * 奖励的漂币数量
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
