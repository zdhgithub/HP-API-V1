/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public class UserReward {
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	
	/**
	 * 平台送(漂币)
	 */
	private Integer platformGive;
	
	/**
	 * 商家送(漂币)
	 */
	private Integer businessGive;
	
	/**
	 * 每月次数
	 */
	private Integer monthTimes;
	
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
	
	/**
	 * 总次数
	 */
	private Integer times;

	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public Integer getPlatformGive() {
		return platformGive;
	}


	public void setPlatformGive(Integer platformGive) {
		this.platformGive = platformGive;
	}


	public Integer getBusinessGive() {
		return businessGive;
	}


	public void setBusinessGive(Integer businessGive) {
		this.businessGive = businessGive;
	}


	public Integer getMonthTimes() {
		return monthTimes;
	}


	public void setMonthTimes(Integer monthTimes) {
		this.monthTimes = monthTimes;
	}


	public Timestamp getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}


	public Integer getTimes() {
		return times;
	}


	public void setTimes(Integer times) {
		this.times = times;
	}
	
	
}
