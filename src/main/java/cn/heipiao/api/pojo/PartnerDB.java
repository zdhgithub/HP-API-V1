package cn.heipiao.api.pojo;

import java.util.Date;

public class PartnerDB {
	private Integer id;
	private String title;
	private String des;
	private Date createTime;
	private Date updateTime;
	private String content;
	private String url;
	private Integer downloadNum;

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDes() {
		return des;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getContent() {
		return content;
	}

	public String getUrl() {
		return url;
	}

	public Integer getDownloadNum() {
		return downloadNum;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDownloadNum(Integer downloadNum) {
		this.downloadNum = downloadNum;
	}

}
