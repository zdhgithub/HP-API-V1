package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 分享或是渔获
 * @date 2016年6月13日
 */
public class Share implements Serializable {
	private static final long serialVersionUID = 8323947596258748319L;
	/** 主键id **/
	private Long id;
	/** 主图 **/
	private String mainImg;
	/** 图片 **/
	private String img;
	/** 带html格式的图片或是视频 **/
	private String imgExt;
	/** 地点 **/
	private String address;
	/** 发布内容 **/
	private String content;
	/** 发布时间 **/
	private Date createTime;
	/** 有效标识，1表示有效，0表示无效 **/
	private String flag;
	/** 内容类型 1 表示渔获，2表示分享 **/
	private String type;
	/** 发布者 **/
	private Long userId;
	/** 发布人昵称 */
	private String nickName;
	/** 哪个钓场id的鱼获或是分享 */
	private Integer siteId;
	/** 点赞数 **/
	private Long likesNum;
	/** 评论数 **/
	private Long commentNum;
	/** user 头像url **/
	private String portriat;
	/** 分享或是渔获的 头像url **/
	private String sharePortriat;
	/** 是否点赞标示 Y表示点过赞，N表示未点赞 **/
	private String likeFlag;
	/** 浏览量 */
	private Integer clickNum;

	public Integer getClickNum() {
		return clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public String getMainImg() {
		return mainImg;
	}

	public String getImg() {
		return img;
	}

	public String getImgExt() {
		return imgExt;
	}

	public String getAddress() {
		return address;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getFlag() {
		return flag;
	}

	public String getType() {
		return type;
	}

	public Long getUserId() {
		return userId;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public Long getLikesNum() {
		return likesNum;
	}

	public Long getCommentNum() {
		return commentNum;
	}

	public String getPortriat() {
		return portriat;
	}

	public String getSharePortriat() {
		return sharePortriat;
	}

	public String getLikeFlag() {
		return likeFlag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setImgExt(String imgExt) {
		this.imgExt = imgExt;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public void setLikesNum(Long likesNum) {
		this.likesNum = likesNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public void setSharePortriat(String sharePortriat) {
		this.sharePortriat = sharePortriat;
	}

	public void setLikeFlag(String likeFlag) {
		this.likeFlag = likeFlag;
	}

}
