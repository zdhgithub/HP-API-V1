/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
public class CouponsCount {

	/**
	 * 累计发放次数
	 */
	private Long times;
	
	/**
	 * 累计发放用户人次
	 */
	private Long users;
	
	/**
	 * 累计使用张数
	 */
	private Long used;
	
	/**
	 * 累计优惠金额
	 */
	private Long fees;
	
	/**
	 * 累计大众券被领取张数
	 */
	private Long counts;

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	public Long getUsers() {
		return users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

	public Long getUsed() {
		return used;
	}

	public void setUsed(Long used) {
		this.used = used;
	}

	public Long getFees() {
		return fees;
	}

	public void setFees(Long fees) {
		this.fees = fees;
	}

	public Long getCounts() {
		return counts;
	}

	public void setCounts(Long counts) {
		this.counts = counts;
	}
	
}
