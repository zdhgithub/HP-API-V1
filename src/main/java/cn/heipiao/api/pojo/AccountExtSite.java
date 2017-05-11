package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年7月15日
 */
public class AccountExtSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810939365472293262L;
	/** 用户id */
	private Integer uid;
	/** 钓场ID */
	private Integer fishSiteId;
	/** 钓场名称 */
	private String fishSiteName;
	/** 昵称 **/
	private String nickname;
	/** 手机号 **/
	private String phone;
	/** 钓场图标 */
	private String mainImg;
	/** 用户余额 */
	private Double balance;
	/** 存鱼数 */
	private Double depositFish;
	/** 累计提现 */
	private Double withdrawTotal;
	/** 累计存鱼 */
	private Double depositFish_Total;

	public Integer getUid() {
		return uid;
	}

	public Integer getFishSiteId() {
		return fishSiteId;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhone() {
		return phone;
	}

	public String getMainImg() {
		return mainImg;
	}

	public Double getBalance() {
		return balance;
	}

	public Double getDepositFish() {
		return depositFish;
	}

	public Double getWithdrawTotal() {
		return withdrawTotal;
	}

	public Double getDepositFish_Total() {
		return depositFish_Total;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setDepositFish(Double depositFish) {
		this.depositFish = depositFish;
	}

	public void setWithdrawTotal(Double withdrawTotal) {
		this.withdrawTotal = withdrawTotal;
	}

	public void setDepositFish_Total(Double depositFish_Total) {
		this.depositFish_Total = depositFish_Total;
	}

}
