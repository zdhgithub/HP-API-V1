package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description user钓场存鱼
 * @date 2016年6月28日
 */
public class DepositFish implements Serializable {

	private static final long serialVersionUID = 7630125243551480391L;
	/** userID */
	private Long userId;
	/** 钓场ID */
	private Integer siteId;
	/** 存鱼数额 */
	private Double amount;
	/** 存鱼余额 */
	private Double balance;
	/** 更新时间 */
	private Date updateTime;
	/** 创建时间 */
	private Date createTime;
	/** 昵称 **/
	private String nickname;
	/** 头像url **/
	private String portriat;
	/** 扩展字段，不需和数据库映射 */
	private Long ticketId;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getUserId() {
		return userId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getBalance() {
		return balance;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

}
