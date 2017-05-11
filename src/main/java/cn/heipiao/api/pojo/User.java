package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zf
 * @version 1.0
 * @description 用户
 * @date 2016年6月1日
 */
@XmlRootElement
public class User implements Serializable {
	private static final long serialVersionUID = -1632393744255837723L;
	/** USERID **/
	private Long id;
	/** 用户名 **/
	private String username;
	/** 密码 **/
	private String password;
	/** 昵称 **/
	private String nickname;
	/** 真实姓名 **/
	private String realname;
	/** 身份证号码 **/
	private String idcard;
	/** 生日 **/
	private Date birthday;//
	/** 性别,1代表男，2代表女, 0未知,保密 **/
	private String sex;
	/** 手机号 **/
	private String phone;
	/** 电子邮件 **/
	private String email;
	/** 第三方id **/
	private String openId;
	/** 第三方登录来源 **/
	private String source;
	/** 注册时间 **/
	private Date regisTime;
	/** 区域id **/
	private Integer regionId;
	/** 区域 **/
	private String regionStr;
	/** 头像url **/
	private String portriat;
	/** 个性签名 **/
	private String remark;
	/** 平台备注 **/
	private String platformRemarks;
	/** 最后登录时间 **/
	private Date lastLoginTime;
	/** 状态,1代表可登录，0代表禁用 ,3代表隐式注册(其他人推荐)**/
	private String status;
	/**
	 * 用户标注状态 1:用户推荐注册,2:推荐用户登录,3:推荐用户已经消费 ,4:合伙人发券用户
	 * **/
	private Integer stat;
	
	/**
	 * 是否可以提现
	 */
	private Boolean isWithdraw;
	
	private Long shopId;
	
	private String unionId;
	

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public User() {

	}

	public String getPlatformRemarks() {
		return platformRemarks;
	}

	public void setPlatformRemarks(String platformRemarks) {
		this.platformRemarks = platformRemarks;
	}

	public User(String username) {
		this.username = username;
	}

	public String getRegionStr() {
		return regionStr;
	}

	public void setRegionStr(String regionStr) {
		this.regionStr = regionStr;
	}

	@XmlElement
	public Long getId() {
		return id;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	@XmlElement
	public String getNickname() {
		return nickname;
	}

	@XmlElement
	public String getRealname() {
		return realname;
	}

	/**
	 * @return the idcard
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * @param idcard
	 *            the idcard to set
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@XmlElement
	public Date getBirthday() {
		return birthday;
	}

	@XmlElement
	public String getSex() {
		return sex;
	}

	@XmlElement
	public String getPhone() {
		return phone;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	@XmlElement
	public String getOpenId() {
		return openId;
	}

	@XmlElement
	public String getSource() {
		return source;
	}

	@XmlElement
	public Date getRegisTime() {
		return regisTime;
	}

	@XmlElement
	public Integer getRegionId() {
		return regionId;
	}

	@XmlElement
	public String getPortriat() {
		return portriat;
	}

	@XmlElement
	public String getRemark() {
		return remark;
	}

	@XmlElement
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	@XmlElement
	public String getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setBirthday(Date birthday) {
		if (birthday != null) {
			this.birthday = (Date) birthday.clone();
		} else {
			this.birthday = null;
		}
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setRegisTime(Date regisTime) {
		if (regisTime != null) {
			this.regisTime = (Date) regisTime.clone();
		} else {
			this.regisTime = null;
		}
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		if (lastLoginTime != null) {
			this.lastLoginTime = (Date) lastLoginTime.clone();
		} else {
			this.lastLoginTime = null;
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the stat
	 */
	public Integer getStat() {
		return stat;
	}

	/**
	 * @param stat
	 *            the stat to set
	 */
	public void setStat(Integer stat) {
		this.stat = stat;
	}

	public Boolean getIsWithdraw() {
		return isWithdraw;
	}

	public void setIsWithdraw(Boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", username=" + username + ", password="
//				+ password + ", nickname=" + nickname + ", realname="
//				+ realname + ", birthday=" + birthday + ", sex=" + sex
//				+ ", phone=" + phone + ", email=" + email + ", openId="
//				+ openId + ", source=" + source + ", regisTime=" + regisTime
//				+ ", portriat=" + portriat + ", remark=" + remark
//				+ ", lastLoginTime=" + lastLoginTime + ", status=" + status
//				+ "]";
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

}
