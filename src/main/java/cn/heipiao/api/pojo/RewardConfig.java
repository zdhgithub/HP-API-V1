/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public class RewardConfig {

	/**
	 * 业务号 1:购票,2:票支付
	 */
	private Integer serviceId;
	
	/**
	 * 最大(漂币)
	 */
	private Integer max;
	
	/**
	 * 最小(漂币)
	 */
	private Integer min;
	
	
	/**
	 * 第一次送(漂币)
	 */
	private Integer once;
	
	/**
	 * 第二次送(漂币)
	 */
	private Integer twice;
	
	/**
	 * 第N次送(漂币)
	 */
	private Integer more;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getOnce() {
		return once;
	}

	public void setOnce(Integer once) {
		this.once = once;
	}

	public Integer getTwice() {
		return twice;
	}

	public void setTwice(Integer twice) {
		this.twice = twice;
	}

	public Integer getMore() {
		return more;
	}

	public void setMore(Integer more) {
		this.more = more;
	}
	
	
}
