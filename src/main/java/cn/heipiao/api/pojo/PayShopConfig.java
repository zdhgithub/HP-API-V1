/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年10月15日
 * @version 1.0
 */
public class PayShopConfig {

	/**
	 * 业务号 1：票支付
	 */
	private Integer serviceId;
	
	/**
	 * 获取比例
	 */
	private Double rate;
	
	/**
	 * 使用规则 ：单位：元
	 */
	private Integer useRule;
	
	/**
	 * 开始时间
	 */
	private Timestamp startTime;
	
	/**
	 * 结束时间
	 */
	private Timestamp endTime;
	
	/**
	 * 状态：0：禁用，1：启用
	 */
	private Integer status;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getUseRule() {
		return useRule;
	}

	public void setUseRule(Integer useRule) {
		this.useRule = useRule;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
