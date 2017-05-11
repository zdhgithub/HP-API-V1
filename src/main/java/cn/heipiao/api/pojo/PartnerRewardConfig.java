package cn.heipiao.api.pojo;

public class PartnerRewardConfig {
	/**
	 * 唯一表示
	 */
	private int id;
	/**
	 * 配置类型0-表示渔具店 1-表示钓场
	 */
	private int type;
	/**
	 * 区域id
	 */
	private int regionId;
	/**
	 * 字典类型0-大 ，1-中，2-小
	 */
	private int scale;
	/**
	 * 商家奖励金额
	 */
	private Integer groundingSum;
	/**
	 * 交易金额
	 */
	private Integer transactionSum;
	/**
	 * 要求说明
	 */
	private String interpellation;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public int getRegionId() {
		return regionId;
	}
	public int getScale() {
		return scale;
	}
	public Integer getGroundingSum() {
		return groundingSum;
	}
	public Integer getTransactionSum() {
		return transactionSum;
	}
	public String getInterpellation() {
		return interpellation;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public void setGroundingSum(Integer groundingSum) {
		this.groundingSum = groundingSum;
	}
	public void setTransactionSum(Integer transactionSum) {
		this.transactionSum = transactionSum;
	}
	public void setInterpellation(String interpellation) {
		this.interpellation = interpellation;
	}
	
}
