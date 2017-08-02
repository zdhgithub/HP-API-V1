package cn.heipiao.api.pojo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public class HpArticleComments {

	@ApiModelProperty("文章id")
	private Long articleId;

	@ApiModelProperty("评论用户uid")
	private Long commentUid;

	@ApiModelProperty("评论评论者的用户(评论者回复评论者的时候才有)")
	private Long commentRUid;

	@ApiModelProperty("评论内容")
	private String commentContent;

	@ApiModelProperty("评论时间")
	private Long commentTime;
	
	@ApiModelProperty("评论用户昵称")
	private String commentNickname;
	
	@ApiModelProperty("评论用户头像")
	private String commentPortrait;
	
	@ApiModelProperty("回复评论用户的用户昵称")
	private String commentRNickname;
	
	@ApiModelProperty("回复评论用户的用户头像")
	private String commentRPortrait;

	
	public String getCommentPortrait() {
		return commentPortrait;
	}

	public void setCommentPortrait(String commentPortrait) {
		this.commentPortrait = commentPortrait;
	}

	public String getCommentRPortrait() {
		return commentRPortrait;
	}

	public void setCommentRPortrait(String commentRPortrait) {
		this.commentRPortrait = commentRPortrait;
	}

	public String getCommentNickname() {
		return commentNickname;
	}

	public void setCommentNickname(String commentNickname) {
		this.commentNickname = commentNickname;
	}


	public String getCommentRNickname() {
		return commentRNickname;
	}

	public void setCommentRNickname(String commentRNickname) {
		this.commentRNickname = commentRNickname;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public Long getCommentUid() {
		return commentUid;
	}

	public void setCommentUid(Long commentUid) {
		this.commentUid = commentUid;
	}

	public Long getCommentRUid() {
		return commentRUid;
	}

	public void setCommentRUid(Long commentRUid) {
		this.commentRUid = commentRUid;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Long getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Long commentTime) {
		this.commentTime = commentTime;
	}

}
