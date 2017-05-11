/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月14日
 * @version 1.0
 * 
 *          钓场动态发布
 * 
 */
public class FishPostNews implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4868754529888471139L;

	/**
	 * 主键id
	 */
	private Long nid;

	/**
	 * 图片路径
	 */
	private String picture;
	/**
	 * 主图片路径
	 */
	private String mainPicture;
	/**
	 * 图片路径扩展,存放被HTML格式化的图片
	 */
	private String pictureExt;

	/**
	 * 内容
	 */
	private String content;
	/**
	 * 内容扩展，存放被HTML格式化的内容
	 */
	private String contentExt;

	/**
	 * 时间
	 */
	private Timestamp createTime;

	/**
	 * 钓场id
	 */
	private Integer fishSiteId;

	/**
	 * 钓场名称
	 */
	private String fishSiteName;

	/**
	 * 钓场动态的类型
	 */
	private String type;

	/**
	 * 是否关注
	 */
	private int focus;

	/**
	 * 间距
	 */
	private Integer duration;

	public String getMainPicture() {
		return mainPicture;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public Long getNid() {
		return nid;
	}

	public String getPicture() {
		return picture;
	}

	public String getPictureExt() {
		return pictureExt;
	}

	public String getContent() {
		return content;
	}

	public String getContentExt() {
		return contentExt;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Integer getFishSiteId() {
		return fishSiteId;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getType() {
		return type;
	}

	public int getFocus() {
		return focus;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setPictureExt(String pictureExt) {
		this.pictureExt = pictureExt;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentExt(String contentExt) {
		this.contentExt = contentExt;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
