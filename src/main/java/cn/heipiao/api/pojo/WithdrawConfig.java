/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年9月26日
 * @version 1.0
 */
public class WithdrawConfig {

	/**
	 * 业务号 1：钓场，2：渔具店
	 */
	private Integer serviceId;
	/**
	 * 规则id
	 */
	private Integer withdrawRuleId;
	/**
	 * 提现延迟天数
	 */
	private Integer delayForDay;
	
	/**
	 * 提现费率
	 */
	private Double withdrawRate;
	

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getDelayForDay() {
		return delayForDay;
	}

	public void setDelayForDay(Integer delayForDay) {
		this.delayForDay = delayForDay;
	}

	public Double getWithdrawRate() {
		return withdrawRate;
	}

	public void setWithdrawRate(Double withdrawRate) {
		this.withdrawRate = withdrawRate;
	}

	public Integer getWithdrawRuleId() {
		return withdrawRuleId;
	}

	public void setWithdrawRuleId(Integer withdrawRuleId) {
		this.withdrawRuleId = withdrawRuleId;
	}
	
	
	
}
