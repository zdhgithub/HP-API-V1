package cn.heipiao.api.pojo;

import java.util.Date;

public class LikeUser {
	/**营销活动id*/
	private Integer marketingId;
	
	/**点赞用户id*/
	private Long likeUid;
	
	/**上传图片用户id*/
	private Long marketUid;
	
	/**点赞用户头像*/
	private String portrait;
	
	/**点赞用户昵称*/
	private String nickName;
	
	/**点赞时间*/
	private Date likeTime;

	public Integer getMarketingId() {
		return marketingId;
	}

	public void setMarketingId(Integer marketingId) {
		this.marketingId = marketingId;
	}

	public Long getLikeUid() {
		return likeUid;
	}

	public void setLikeUid(Long likeUid) {
		this.likeUid = likeUid;
	}

	public Long getMarketUid() {
		return marketUid;
	}

	public void setMarketUid(Long marketUid) {
		this.marketUid = marketUid;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}
	
	
}
