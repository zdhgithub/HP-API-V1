/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月9日
 * @version 1.0
 */
public class PartnerShopRateConfig {

	/**
	 * 合伙人id 
	 */
	private Integer partnerId;
	
	/**
	 * 店铺id
	 */
	private Long shopId;
	
	/**
	 * 店铺比例
	 */
	private Double shopRate;
	/**合伙人用户*/
	private String username;
	/**合伙人昵称*/
	private String nickname;
	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Double getShopRate() {
		return shopRate;
	}

	public void setShopRate(Double shopRate) {
		this.shopRate = shopRate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
