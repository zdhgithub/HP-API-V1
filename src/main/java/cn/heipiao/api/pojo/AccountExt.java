package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author zf
 * @version 1.0
 * @description 财务个人首页参数封装
 * @date 2016年7月15日
 */
public class AccountExt implements Serializable {
	private static final long serialVersionUID = -1728906557072186436L;
	/** 用户id */
	private Integer uid;
	/** 头像url **/
	private String portriat;
	/** 昵称 **/
	private String nickname;
	/** 真实姓名 */
	private String realName;
	/** 手机号 **/
	private String phone;
	/** 用户余额 */
	private Double balance;
	/** 累计充值 */
	private Double rechargeTotal;
	/** 累计提现 */
	private Double withdrawTotal;

	public Integer getUid() {
		return uid;
	}

	public String getPortriat() {
		return portriat;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPhone() {
		return phone;
	}

	public Double getBalance() {
		return balance;
	}

	public Double getRechargeTotal() {
		return rechargeTotal;
	}

	public Double getWithdrawTotal() {
		return withdrawTotal;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setRechargeTotal(Double rechargeTotal) {
		this.rechargeTotal = rechargeTotal;
	}

	public void setWithdrawTotal(Double withdrawTotal) {
		this.withdrawTotal = withdrawTotal;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	
}
