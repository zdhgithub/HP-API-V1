/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月9日
 * @version 1.0
 */
public class PlatformAccountConstraint {

	/**
	 * 1:wx,2:zfb
	 */
	private Integer platform;

	/**
	 * 每天的最大金额 单位：分
	 */
	private Integer daySumLimited;

	/**
	 * 单笔的最小金额 单位：分
	 */
	private Integer singleMinFee;

	/**
	 * 单笔的最大金额 单位：分
	 */
	private Integer singleMaxFee;

	/**
	 * 每天交易最大总笔数
	 */
	private Integer tradeMaxAmount;

	/**
	 * 创建日期
	 */
	private Timestamp createTime;

	/**
	 * 生效日期
	 */
	private Timestamp useTime;

	/**
	 * @return the platform
	 */
	public Integer getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	/**
	 * @return the daySumLimited
	 */
	public Integer getDaySumLimited() {
		return daySumLimited;
	}

	/**
	 * @param daySumLimited
	 *            the daySumLimited to set
	 */
	public void setDaySumLimited(Integer daySumLimited) {
		this.daySumLimited = daySumLimited;
	}

	/**
	 * @return the singleMinFee
	 */
	public Integer getSingleMinFee() {
		return singleMinFee;
	}

	/**
	 * @param singleMinFee
	 *            the singleMinFee to set
	 */
	public void setSingleMinFee(Integer singleMinFee) {
		this.singleMinFee = singleMinFee;
	}

	/**
	 * @return the singleMaxFee
	 */
	public Integer getSingleMaxFee() {
		return singleMaxFee;
	}

	/**
	 * @param singleMaxFee
	 *            the singleMaxFee to set
	 */
	public void setSingleMaxFee(Integer singleMaxFee) {
		this.singleMaxFee = singleMaxFee;
	}

	/**
	 * @return the tradeMaxAmount
	 */
	public Integer getTradeMaxAmount() {
		return tradeMaxAmount;
	}

	/**
	 * @param tradeMaxAmount
	 *            the tradeMaxAmount to set
	 */
	public void setTradeMaxAmount(Integer tradeMaxAmount) {
		this.tradeMaxAmount = tradeMaxAmount;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the useTime
	 */
	public Timestamp getUseTime() {
		return useTime;
	}

	/**
	 * @param useTime
	 *            the useTime to set
	 */
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

}
