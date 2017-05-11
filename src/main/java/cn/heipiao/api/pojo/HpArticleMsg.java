package cn.heipiao.api.pojo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wzw
 * @date 2017年4月1日
 */
public class HpArticleMsg {

	@ApiModelProperty("文章id")
	private Long articleId;

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("评论或点赞用户id")
	private Long msgUid;
	
	@ApiModelProperty("Integer  1:点赞,2:评论")
	private Integer msgType;

	@ApiModelProperty("评论内容")
	private String msgContent;

	@ApiModelProperty("时间戳")
	private Long msgTime;

	@ApiModelProperty("消息用户头像")
	private String msgPortrait;

	@ApiModelProperty("消息用户昵称")
	private String msgNickname;
	
	@ApiModelProperty("文章内容   1：图片链接，2：文字内容")
	private Integer msgArticleType;
	
	@ApiModelProperty("文章部分内容或者链接，根据文章内容展示")
	private String msgArticle;
	
	@ApiModelProperty("消息是否删除")
	private Boolean msgIsDelete;
	
	

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getMsgUid() {
		return msgUid;
	}

	public void setMsgUid(Long msgUid) {
		this.msgUid = msgUid;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Long getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(Long msgTime) {
		this.msgTime = msgTime;
	}

	public String getMsgPortrait() {
		return msgPortrait;
	}

	public void setMsgPortrait(String msgPortrait) {
		this.msgPortrait = msgPortrait;
	}

	public String getMsgNickname() {
		return msgNickname;
	}

	public void setMsgNickname(String msgNickname) {
		this.msgNickname = msgNickname;
	}

	public String getMsgArticle() {
		return msgArticle;
	}

	public void setMsgArticle(String msgArticle) {
		this.msgArticle = msgArticle;
	}

	public Integer getMsgArticleType() {
		return msgArticleType;
	}

	public void setMsgArticleType(Integer msgArticleType) {
		this.msgArticleType = msgArticleType;
	}

	public Boolean getMsgIsDelete() {
		return msgIsDelete;
	}

	public void setMsgIsDelete(Boolean msgIsDelete) {
		this.msgIsDelete = msgIsDelete;
	}

}
