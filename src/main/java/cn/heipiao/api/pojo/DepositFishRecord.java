package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description user钓场存鱼记录
 * @date 2016年6月28日
 */
public class DepositFishRecord implements Serializable {
	private static final long serialVersionUID = -2626168937686943024L;
	/** userID */
	private Long userId;
	/** 用户昵称 */
	private String nickname;
	/** 钓场ID */
	private Integer siteId;
	/** 增减类型1表示增，0表示减 */
	private Integer direction;
	/** 交易金额 */
	private Double tradeMoney;
	/** 交易时间 */
	private Date createTime;
	/** 头像url **/
	private String portriat;

	public Long getUserId() {
		return userId;
	}

	public String getNickname() {
		return nickname;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public Integer getDirection() {
		return direction;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

}
