/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
public class UserShareCityConfig {
	
	/**
	 * 城市id
	 */
	private Integer cityId;
	
	/**
	 * 每天可以获得的次数
	 */
	private Integer limit;
	
	/**
	 * 每天可获得的漂币数
	 */
	private Integer amount;
	
	/**
	 * 是否启用
	 */
	private Boolean status;
	
	/**
	 *  创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 描述
	 */
	private String desc;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
