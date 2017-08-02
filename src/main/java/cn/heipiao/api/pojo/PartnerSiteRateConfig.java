/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月9日
 * @version 1.0
 */
public class PartnerSiteRateConfig {

	/**
	 * 合伙人id 
	 */
	private Integer partnerId;
	
	/**
	 * 钓场id
	 */
	private Integer siteId;
	
	/**
	 * 钓场比例
	 */
	private Double siteRate;
	/**
	 * 合伙人昵称
	 */
	private String nickname;
	/**
	 * 合伙人名称
	 * @return
	 */
	private String username;
	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Double getSiteRate() {
		return siteRate;
	}

	public void setSiteRate(Double siteRate) {
		this.siteRate = siteRate;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
