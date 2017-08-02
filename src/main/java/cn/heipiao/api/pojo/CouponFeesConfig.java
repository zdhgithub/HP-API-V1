/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
public class CouponFeesConfig {

	/**
	 *业务号 1:钓场,2:店铺 
	 */
	private Integer serviceId;
	
	/**
	 * 类别 1:大众券，2:定向常规券，3:定向特定券
	 */
	private Integer categoryId;
	
	/**
	 * 最大(元)  
	 */
	private Integer max;
	
	/**
	 * 最小(元)
	 */
	private Integer min;
	
	/**
	 * 费用(元)
	 */
	private Integer fee;

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

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
