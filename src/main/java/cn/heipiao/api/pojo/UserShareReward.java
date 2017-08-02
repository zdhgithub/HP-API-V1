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
public class UserShareReward {
	 
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 获得漂币
	 */
	private Integer amount;
	
	/**
	 * 当天获得的次数
	 */
	private Integer times;
	
	/**
	 * 每次获取时间
	 */
	private Timestamp updateTime;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
}
