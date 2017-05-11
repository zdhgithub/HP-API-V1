/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
public class FoundMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5908987558376998914L;

	/**
	 * 发现id
	 */
	private Integer fid;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 副标题
	 */
	private String subtitle;

	/**
	 * 图标
	 */
	private String img;

	/**
	 * 资源链接
	 */
	private String url;

	/**
	 * 排序
	 */
	private Integer sortNo;

	/**
	 * 分组
	 */
	private String group;

	/**
	 * 状态,0:未启用,1:启用
	 */
	private Integer status;

	/**
	 * 分类，目前文章叫article
	 */
	private String type;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 父id
	 */
	private Integer pid;

	public Integer getFid() {
		return fid;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getImg() {
		return img;
	}

	public String getUrl() {
		return url;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public String getGroup() {
		return group;
	}

	public Integer getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Integer getPid() {
		return pid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
