package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
public class FodderContent implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2561289202790391982L;

	private Integer id;
	private String title;
	private String remark;
	private String content;
	private String coverImg;
	private String type;
	private Integer parentId;
	private Integer status;
	private Integer sort;
	private Integer shareCount;
	private Integer readCount;
	private Integer agreeCount;
	private Date createtime;
	private Date updatetime;
	


	/**
	 * 获取唯一标识
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * 设置唯一标识
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}



	/**
	 * 获取标题
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}



	/**
	 * 设置标题
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}



	/**
	 * 获取摘要
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}



	/**
	 * 设置摘要
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}



	/**
	 * 获取摘要
	 * @return the content
	 */
	public String getContent() {
		return content;
	}



	/**
	 * 设置摘要
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}



	/**
	 * 获取封面图片-分享到微信等的小图
	 * @return the coverImg
	 */
	public String getCoverImg() {
		return coverImg;
	}



	/**
	 * 设置封面图片-分享到微信等的小图
	 * @param coverImg the coverImg to set
	 */
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}



	/**
	 * 获取内容类型标识-t_sys_cfg_dict.fodderContent
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * 设置内容类型标识-t_syCcfg_dict.fodderContent
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}



	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}



	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}



	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}



	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}



	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}



	/**
	 * 获取分享总量
	 * @return the shareCount
	 */
	public Integer getShareCount() {
		return shareCount;
	}



	/**
	 * 设置分享总量
	 * @param shareCount the shareCount to set
	 */
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}



	/**
	 * 获取阅读总量
	 * @return the readCount
	 */
	public Integer getReadCount() {
		return readCount;
	}



	/**
	 * 设置阅读总量
	 * @param readCount the readCount to set
	 */
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}



	/**
	 * 获取点赞总量
	 * @return the agreeCount
	 */
	public Integer getAgreeCount() {
		return agreeCount;
	}



	/**
	 * 设置点赞总量
	 * @param agreeCount the agreeCount to set
	 */
	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}



	/**
	 * 获取创建时间
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}



	/**
	 * 设置创建时间
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}



	/**
	 * 获取更新时间
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}



	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
