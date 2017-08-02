package cn.heipiao.api.pojo;

import java.sql.Timestamp;

public class PartnerOver {
	/**
	 * 唯一表示
	 */
	private int id;
	/**
	 * 配置类型0-表示渔具店 1-表示钓场
	 */
	private int type;
	/**
	 * 商家id 
	 */
	private Long bid;
	/**
	 * 合伙人id
	 */
	private Long partnerId;
	/**
	 * 失效时间
	 */
	private Timestamp OverTime;
	public int getId() {
		return id;
	}
	public int getType() {
		return type;
	}
	public Long getBid() {
		return bid;
	}
	public Long getPartnerId() {
		return partnerId;
	}
	public Timestamp getOverTime() {
		return OverTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public void setOverTime(Timestamp overTime) {
		OverTime = overTime;
	}
}
