/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public class CityRewardConfig {

	
	/**
	 * 城市id 
	 */
	private Integer cityId;
	
	/**
	 * 城市奖励数量(漂币)
	 */
	private Integer rewardAmount;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Integer rewardAmount) {
		this.rewardAmount = rewardAmount;
	}
	
	
	
}
