package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

public class SystemMsg implements Serializable {

	private static final long serialVersionUID = 5462459101093255417L;
	/** ID */
	private Long id;
	/** 标题 */
	private String title;
	/** 链接 */
	private String url;
	/** 消息内容 */
	private String content;
	/** 发送时间 */
	private Date createTime;
	/** 接收人 */
	private Integer receiver;
	/** 发送人 */
	private Integer sender;
	/** 0表示未读，1表示已读 */
	private Integer flag;
	/** B或 C */
	private String type ;

	public SystemMsg() {

	}
	public SystemMsg(String title,String url,String content,Integer receiver,Integer sender,String type) {
		this.title = title;
		this.url = url;
		this.content = content;
		this.receiver = receiver;
		this.sender = sender;
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Integer getReceiver() {
		return receiver;
	}

	public Integer getSender() {
		return sender;
	}

	public Integer getFlag() {
		return flag;
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

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}

	public void setSender(Integer sender) {
		this.sender = sender;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
