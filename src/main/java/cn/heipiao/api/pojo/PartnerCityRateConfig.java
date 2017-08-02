/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月9日
 * @version 1.0
 */
public class PartnerCityRateConfig {

	/**
	 * 城市id 
	 */
	private Integer cityId;
	
	/**
	 * 店铺比例
	 */
	private Double shopRate;
	
	/**
	 * 钓场比例
	 */
	private Double siteRate;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Double getShopRate() {
		return shopRate;
	}

	public void setShopRate(Double shopRate) {
		this.shopRate = shopRate;
	}

	public Double getSiteRate() {
		return siteRate;
	}

	public void setSiteRate(Double siteRate) {
		this.siteRate = siteRate;
	}
	
}
