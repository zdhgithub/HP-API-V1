package cn.heipiao.api.pojo;

import java.util.Date;

public class CommentUser {
	private Long id;
	private String content;
	private Date createTime;
	private String flag;
	private Integer reply_uid;
	private String reply_name;
	private Integer replyed_uid;
	private String replyed_name;

	public String getReply_name() {
		return reply_name;
	}

	public String getReplyed_name() {
		return replyed_name;
	}

	public void setReply_name(String reply_name) {
		this.reply_name = reply_name;
	}

	public void setReplyed_name(String replyed_name) {
		this.replyed_name = replyed_name;
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

	public Integer getReply_uid() {
		return reply_uid;
	}

	public Integer getReplyed_uid() {
		return replyed_uid;
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

	public void setReply_uid(Integer reply_uid) {
		this.reply_uid = reply_uid;
	}

	public void setReplyed_uid(Integer replyed_uid) {
		this.replyed_uid = replyed_uid;
	}

}
