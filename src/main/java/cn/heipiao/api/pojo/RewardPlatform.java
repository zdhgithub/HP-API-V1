package cn.heipiao.api.pojo;

public class RewardPlatform {
	/**
	 * 平台奖励配置id
	 */
	private Long id;
	/**
	 * 平台奖励商家id
	 */
	private Long bid;
	/**
	 * 返利抽点比例
	 */
	private float percent;
	/**
	 * 奖励金额（商家 单位：元）
	 */
	private double bonus;
	/**
	 * 合伙人奖励金额（单位：分）
	 */
	private Integer partnerBonus;
	/**
	 * 平台奖励状态  0表示开启  1表示关闭
	 */
	private int status;
	/**
	 * 平台奖励政策的商家类型 0表示渔具店 1表示钓场
	 */
	private int type;
	public Long getId() {
		return id;
	}
	public Long getBid() {
		return bid;
	}
	public float getPercent() {
		return percent;
	}
	public double getBonus() {
		return bonus;
	}
	public int getStatus() {
		return status;
	}
	public int getType() {
		return type;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getPartnerBonus() {
		return partnerBonus;
	}
	public void setPartnerBonus(Integer partnerBonus) {
		this.partnerBonus = partnerBonus;
	}
	
	
}
