/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public class AccountRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3718830358035467406L;

	/**
	 * 用户uid
	 */
	private Integer uid;

	/**
	 * 增减类型
	 */
	private Integer direction;

	/**
	 * 交易类型
	 */
	private Integer tradeType;

	/**
	 * 交易金额
	 */
	private Double tradeMoney;

	/**
	 * 交易时间
	 */
	private Timestamp tradeTime;

	/**
	 * 交易描述
	 */
	private String description;

	/**
	 * 订单号/交易号
	 */
	private String orderId;

	/**
	 * 对方/资金入口方
	 */
	private String otherSide;

	/**
	 * 状态1成功，0失败
	 */
	private Integer status;
	/**
	 * 钓场ID，如果在该钓场有消费，则记录，否则，为null
	 */
	private Integer siteId;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getUid() {
		return uid;
	}

	public Integer getDirection() {
		return direction;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public Timestamp getTradeTime() {
		return tradeTime;
	}

	public String getDescription() {
		return description;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getOtherSide() {
		return otherSide;
	}

	public Integer getStatus() {
		return status;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOtherSide(String otherSide) {
		this.otherSide = otherSide;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
