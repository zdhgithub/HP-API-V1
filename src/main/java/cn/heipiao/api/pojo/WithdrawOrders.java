/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月6日
 * @version 1.0
 */
public class WithdrawOrders {

	/**
	 *交易号 
	 */
	private String tradeNo;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 平台交易号
	 */
	private String platformTradeNo;
	
	/**
	 * 1:wx,2:zfb 
	 */
	private Integer platform;
	
	/**
	 * 收款账号
	 */
	private String tradeAccount;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 提现总金额 , 单位：分
	 */
	private Integer tradeFee;
	
	/**
	 * 实际提现金额, 单位：分
	 */
	private Integer  actualFee;
	
	/**
	 * 手续费, 单位：分
	 */
	private Integer poundageFee;
	
	/**
	 * 0:提现中,1:提现中,2:提现完成,3:提现失败
	 */
	private Integer status;
	
	/**
	 * 支付时间
	 */
	private Timestamp payTime;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 提现如果则是失败的原因,否则为空
	 */
	private String desc;
	
	/**
	 * 平台错误
	 */
	private String platformDesc;
	
	/**
	 * 提现延迟天数
	 */
	private Integer delayForDay;
	
	/**
	 * 提现费率
	 */
	private Double withdrawRate;

	/**
	 * @return the tradeNo
	 */
	public String getTradeNo() {
		return tradeNo;
	}

	/**
	 * @param tradeNo the tradeNo to set
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the platformTradeNo
	 */
	public String getPlatformTradeNo() {
		return platformTradeNo;
	}

	/**
	 * @param platformTradeNo the platformTradeNo to set
	 */
	public void setPlatformTradeNo(String platformTradeNo) {
		this.platformTradeNo = platformTradeNo;
	}

	/**
	 * @return the platform
	 */
	public Integer getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	/**
	 * @return the tradeAccount
	 */
	public String getTradeAccount() {
		return tradeAccount;
	}

	/**
	 * @param tradeAccount the tradeAccount to set
	 */
	public void setTradeAccount(String tradeAccount) {
		this.tradeAccount = tradeAccount;
	}

	/**
	 * @return the realname
	 */
	public String getRealname() {
		return realname;
	}

	/**
	 * @param realname the realname to set
	 */
	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * @return the tradeFee
	 */
	public Integer getTradeFee() {
		return tradeFee;
	}

	/**
	 * @param tradeFee the tradeFee to set
	 */
	public void setTradeFee(Integer tradeFee) {
		this.tradeFee = tradeFee;
	}

	/**
	 * @return the actualFee
	 */
	public Integer getActualFee() {
		return actualFee;
	}

	/**
	 * @param actualFee the actualFee to set
	 */
	public void setActualFee(Integer actualFee) {
		this.actualFee = actualFee;
	}

	/**
	 * @return the poundageFee
	 */
	public Integer getPoundageFee() {
		return poundageFee;
	}

	/**
	 * @param poundageFee the poundageFee to set
	 */
	public void setPoundageFee(Integer poundageFee) {
		this.poundageFee = poundageFee;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the payTime
	 */
	public Timestamp getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPlatformDesc() {
		return platformDesc;
	}

	public void setPlatformDesc(String platformDesc) {
		this.platformDesc = platformDesc;
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
	
}
