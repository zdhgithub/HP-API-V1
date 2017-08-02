package cn.heipiao.api.pojo;

import java.util.Date;

public class PushUser {
	private String mobile;
	private String registration_id;
	private String tags;
	private String alias;
	private String type;
	private String os;
	private Date createTime;

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getMobile() {
		return mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegistration_id() {
		return registration_id;
	}

	public String getTags() {
		return tags;
	}

	public String getAlias() {
		return alias;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setRegistration_id(String registration_id) {
		this.registration_id = registration_id;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
