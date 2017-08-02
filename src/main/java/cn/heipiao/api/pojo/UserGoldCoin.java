/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月16日
 * @version 1.0
 */
public class UserGoldCoin {

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 漂币总数
	 */
	private Integer goldCoin;
			
	/**
	 * 收益漂币,可提现
	 */
	private Integer earningsGoldCoin;
	
	/**
	 * 提现日期yyyyMM
	 */
	private Integer withdrawDate;
	
	/**
	 * 提现状态   0:提现，1：不能提现
	 * 根据 withdrawDate 判断能否提现
	 */
	private Integer withdrawStatus;

	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the goldCoin
	 */
	public Integer getGoldCoin() {
		return goldCoin;
	}

	/**
	 * @param goldCoin
	 *            the goldCoin to set
	 */
	public void setGoldCoin(Integer goldCoin) {
		this.goldCoin = goldCoin;
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
	 * @return the earningsGoldCoin
	 */
	public Integer getEarningsGoldCoin() {
		return earningsGoldCoin;
	}

	/**
	 * @param earningsGoldCoin the earningsGoldCoin to set
	 */
	public void setEarningsGoldCoin(Integer earningsGoldCoin) {
		this.earningsGoldCoin = earningsGoldCoin;
	}

	/**
	 * @return the withdrawDate
	 */
	public Integer getWithdrawDate() {
		return withdrawDate;
	}

	/**
	 * @param withdrawDate the withdrawDate to set
	 */
	public void setWithdrawDate(Integer withdrawDate) {
		this.withdrawDate = withdrawDate;
	}

	/**
	 * @return the withdrawStatus
	 */
	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	/**
	 * @param withdrawStatus the withdrawStatus to set
	 */
	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

}
