package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月13日
 */
public class Comment implements Serializable {
	private static final long serialVersionUID = 3057749754941726950L;
	/** 主键id */
	private Long id;
	/** 评论内容 */
	private String content;
	/** 评论时间 */
	private Date createTime;
	/** 有效标识，1表示有效，0表示无效 */
	private String flag;
	/** 评论人 */
	private Long userId;
	/** 头像url **/
	private String portriat;
	/** 昵称 **/
	private String nickname;
	/** 渔获或分享的id */
	private Long sid;
	/** 点赞数 */
	private Integer likesNum;
	/** 是否点赞标示 Y表示点过赞，N表示未点赞 **/
	private String likeFlag;
	
	private List<CommentUser> cus;
	

	public List<CommentUser> getCus() {
		return cus;
	}

	public void setCus(List<CommentUser> cus) {
		this.cus = cus;
	}

	public Long getId() {
		return id;
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

	public Long getUserId() {
		return userId;
	}

	public String getPortriat() {
		return portriat;
	}

	public String getNickname() {
		return nickname;
	}

	public Long getSid() {
		return sid;
	}

	public Integer getLikesNum() {
		return likesNum;
	}

	public String getLikeFlag() {
		return likeFlag;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public void setLikesNum(Integer likesNum) {
		this.likesNum = likesNum;
	}

	public void setLikeFlag(String likeFlag) {
		this.likeFlag = likeFlag;
	}

}
