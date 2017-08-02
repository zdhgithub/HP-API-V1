package cn.heipiao.api.pojo;

import java.util.Date;

public class Marketing {
	/**营销活动id*/
	private Integer id;
	/**名称*/
	private String name;
	/**封面图*/
	private String banner;
	/**活动详情*/
	private String detail;
	/**发布时间*/
	private Date beginTime;
	/**接收时间*/
	private Date endTime;
	/**创建时间*/
	private Date createTime;
	/**活动状态
	 * 0 未发布，1 已发布，2 已结束，3 删除
	 * */
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date date) {
		this.createTime = date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
