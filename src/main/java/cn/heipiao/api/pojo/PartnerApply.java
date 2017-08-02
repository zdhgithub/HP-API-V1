package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * 
 * @author zf
 *
 */
public class PartnerApply {
	/** 用户ID */
	private Integer id;
	/** 姓名 */
	private String realname;
	/** 手机 */
	private String phone;
	/** 年龄 */
	private Integer age;
	/** 钓龄 */
	private Integer fishAge;
	/** 是否有私家车0表示没有1表示有 */
	private Integer carFlag;
	/** 现在居住地 */
	private String addr;
	/** 常去的钓场 */
	private String site;
	/** 申请时间 */
	private Date applyTime;
	/** 申请状态 1表示审核中，0表示审核不通过，2表示审核通过 */
	private Integer status;
	/** 申请合伙城市 */
	private String region;

	public Integer getId() {
		return id;
	}

	public String getRealname() {
		return realname;
	}

	public String getPhone() {
		return phone;
	}

	public Integer getAge() {
		return age;
	}

	public Integer getFishAge() {
		return fishAge;
	}

	public Integer getCarFlag() {
		return carFlag;
	}

	public String getAddr() {
		return addr;
	}

	public String getSite() {
		return site;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setFishAge(Integer fishAge) {
		this.fishAge = fishAge;
	}

	public void setCarFlag(Integer carFlag) {
		this.carFlag = carFlag;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
	
}
