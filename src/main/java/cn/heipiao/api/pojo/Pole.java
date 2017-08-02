package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * @author chenwenye
 * @since 2016-7-4  heipiao1.0
 */
public class Pole {
	/** 主键-票券id **/
    private String id;
    /** 用户id **/
    private String userId;
    /** 钓场id **/
    private String fishSiteId;
    /** 开杆时间 **/
    private Date begin;
    /** 核票时间 **/
    private Date checked;
    /** 收杆时间(斤塘为空,钟塘按时返回) **/
    private Date end;
    /** 是否已经离开钓场 ， 0 离开  1 在线  **/
    private String isLeave;
    /** 鱼塘名称 **/
    private String pondName;
    
    /** 用户名 - 其他业务字段 **/
    private String username;
    
    /** 用户头像 - 其他业务字段 **/
    private String portriat;
    
    private String allCount;
    
    private String onlienCount;
    
    private String outTimeCount;
    
    private Date now;
    
    private Date systemTime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFishSiteId() {
        return fishSiteId;
    }

    public void setFishSiteId(String fishSiteId) {
        this.fishSiteId = fishSiteId;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getChecked() {
        return checked;
    }

    public void setChecked(Date checked) {
        this.checked = checked;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

	public String getIsLeave() {
		return isLeave;
	}

	public void setIsLeave(String isLeave) {
		this.isLeave = isLeave;
	}

	public String getPondName() {
		return pondName;
	}

	public void setPondName(String pondName) {
		this.pondName = pondName;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPortriat() {
		return portriat;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}
	
	public String getAllCount() {
		return allCount;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}

	public String getOnlienCount() {
		return onlienCount;
	}

	public void setOnlienCount(String onlienCount) {
		this.onlienCount = onlienCount;
	}

	public String getOutTimeCount() {
		return outTimeCount;
	}

	public void setOutTimeCount(String outTimeCount) {
		this.outTimeCount = outTimeCount;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}

	@Override
	public String toString() {
		return "Pole [id=" + id + ", userId=" + userId + ", fishSiteId=" + fishSiteId + ", begin=" + begin
				+ ", checked=" + checked + ", end=" + end + ", isLeave=" + isLeave + ", pondName=" + pondName + "]";
	}

}