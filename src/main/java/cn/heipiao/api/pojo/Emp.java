package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * 说明 : 员工bean
 * @author chenwenye
 * @since 2016-6-27  heipiao1.0
 */
public final class Emp extends User{

	/**
	 * SUID
	 */
	private static final long serialVersionUID = -8433197222582623735L;

	/** 用户Id **/
	private Integer userId;

	/** 钓场Id **/
    private Integer fishSiteId;

    /** 钓场主Id **/
    private Long fishSiteUid;
    
    /** 钓场主真实姓名 **/
    private String fishSiteMasterRealname;
    
    /** 钓场主电话 **/
    private String masterPhone;
    
    /** 备注 - 钓场对员工的备注,不是用户备注 **/
    private String remark;

    /** 创建时间 **/
    private Date createTime;
    
    /** 员工状态 - @see EmpService **/
    private String empStatus;
    
    /** 用户名称 **/
    private String userName;
    
    /** 电话号码 **/
    private String phone;
    
    /** 头像 **/
    private String portriat;
    
    /** 登陆密码 **/
    private String password;
    
    /**
     * 钓场主图
     */
    private String mainImg;
    
    /**
     * 钓场名称
     */
    private String fishSiteName;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFishSiteId() {
        return fishSiteId;
    }

    public void setFishSiteId(Integer fishSiteId) {
        this.fishSiteId = fishSiteId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fishSiteUid
	 */
	public Long getFishSiteUid() {
		return fishSiteUid;
	}

	/**
	 * @param fishSiteUid the fishSiteUid to set
	 */
	public void setFishSiteUid(Long fishSiteUid) {
		this.fishSiteUid = fishSiteUid;
	}
	
	public String getFishSiteMasterRealname() {
		return fishSiteMasterRealname;
	}

	public void setFishSiteMasterRealname(String fishSiteMasterRealname) {
		this.fishSiteMasterRealname = fishSiteMasterRealname;
	}
	
	public String getMasterPhone() {
		return masterPhone;
	}

	public void setMasterPhone(String masterPhone) {
		this.masterPhone = masterPhone;
	}
	
	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}
	
	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

}