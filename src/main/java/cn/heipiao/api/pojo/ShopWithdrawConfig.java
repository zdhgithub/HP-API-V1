package cn.heipiao.api.pojo;

public class ShopWithdrawConfig {

	/**
	 * 商家id(钓鱼场,渔具店,农家乐)
	 */
	private Long shopId;
	
	/**
	 * 1:钓鱼场, 2:渔具店, 3:农家乐
	 */
	private Integer serviceId;
	
	/**
	 * 城市id
	 */
	private Integer cityId;
	
	/**
	 *  提现规则id
	 */
	private Integer ruleId;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	
	
	
}
