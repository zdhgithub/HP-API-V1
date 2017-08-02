package cn.heipiao.api.pojo;

import java.util.Date;
import java.util.List;

public class MarketingPicture {
	
	/**营销活动id*/
	private Integer marketingId;
	
	/**上传图片用户id*/
	private Long uid;
	
	/**上传图片
	 * 必须是3张图片
	 * */
	private String picture;
	
	/**图片描述*/
	private String pictureDesc;
	
	/**点赞数*/
	private Integer likeCount;
	
	/**图片审核状态
	 * 0 待审核，1 通过，2 未通过
	 * */
	private Integer status;
	
	/**上传时间*/
	private Date uploadTime;
	
	/**审核不通过原因*/
	private String refundReason;
	
	/**审核不通过时间*/
	private Date refundTime;
	/**
	 * 上传用户昵称
	 */
	private String nickname;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户头像
	 */
	private String portriat;
	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 点赞人
	 */
	private List<LikeUser> likeUsuer;
	
	public Integer getMarketingId() {
		return marketingId;
	}
	public void setMarketingId(Integer marketingId) {
		this.marketingId = marketingId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPictureDesc() {
		return pictureDesc;
	}
	public void setPictureDesc(String pictureDesc) {
		this.pictureDesc = pictureDesc;
	}
	public Integer getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public List<LikeUser> getLikeUsuer() {
		return likeUsuer;
	}
	public void setLikeUsuer(List<LikeUser> likeUsuer) {
		this.likeUsuer = likeUsuer;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	
	
}
