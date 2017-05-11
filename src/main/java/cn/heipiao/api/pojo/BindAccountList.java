/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月5日
 * @version 1.0
 */
public class BindAccountList {

	/**
	 * 用户id
	 */
	private Long uid;

	/**
	 * 1:wx,2:zfb
	 */
	private Integer platform;

	/**
	 * wx:微信appid对应的openId,zfb:支付宝账号
	 */
	private String bindAccountNum;

	/**
	 * 创建日期
	 */
	private Timestamp createTime;
	
	/**
	 * 防止同一个账号，出现多个账户账号问题。 1:b端,2:c端
	 */
	private Integer flag;
	

	/**
	 * 当天交易总金额 	单位：分
	 */
	private Integer currentSumFee;

	/**
	 * 当天交易总笔数
	 */
	private Integer tradeAmount;

	/**
	 * 上次提现时间
	 */
	private Timestamp tradeUpdateTime;
	
	/**
	 * 绑定时间
	 */
	private Timestamp bindTime;
	
	
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
	
	//钓场主姓名
	private String realname;
	
	//钓场主手机号
	private String phoneNum;
	
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
	 * @return the bindAccountNum
	 */
	public String getBindAccountNum() {
		return bindAccountNum;
	}

	/**
	 * @param bindAccountNum the bindAccountNum to set
	 */
	public void setBindAccountNum(String bindAccountNum) {
		this.bindAccountNum = bindAccountNum;
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
	 * @return the currentSumFee
	 */
	public Integer getCurrentSumFee() {
		return currentSumFee;
	}

	/**
	 * @param currentSumFee the currentSumFee to set
	 */
	public void setCurrentSumFee(Integer currentSumFee) {
		this.currentSumFee = currentSumFee;
	}

	/**
	 * @return the tradeAmount
	 */
	public Integer getTradeAmount() {
		return tradeAmount;
	}

	/**
	 * @param tradeAmount the tradeAmount to set
	 */
	public void setTradeAmount(Integer tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	/**
	 * @return the tradeUpdateTime
	 */
	public Timestamp getTradeUpdateTime() {
		return tradeUpdateTime;
	}

	/**
	 * @param tradeUpdateTime the tradeUpdateTime to set
	 */
	public void setTradeUpdateTime(Timestamp tradeUpdateTime) {
		this.tradeUpdateTime = tradeUpdateTime;
	}

	/**
	 * @return the bindTime
	 */
	public Timestamp getBindTime() {
		return bindTime;
	}

	/**
	 * @param bindTime the bindTime to set
	 */
	public void setBindTime(Timestamp bindTime) {
		this.bindTime = bindTime;
	}

	/**
	 * @return the daySumLimited
	 */
	public Integer getDaySumLimited() {
		return daySumLimited;
	}

	/**
	 * @param daySumLimited the daySumLimited to set
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
	 * @param singleMinFee the singleMinFee to set
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
	 * @param singleMaxFee the singleMaxFee to set
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
	 * @param tradeMaxAmount the tradeMaxAmount to set
	 */
	public void setTradeMaxAmount(Integer tradeMaxAmount) {
		this.tradeMaxAmount = tradeMaxAmount;
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
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


}
