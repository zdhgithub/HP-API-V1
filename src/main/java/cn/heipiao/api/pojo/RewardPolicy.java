package cn.heipiao.api.pojo;

import java.util.Date;



/**
 * 
 * @ClassName: RewardPolicy
 * @Description: TODO
 * @author duzh
 * @date 2017年1月14日
 */
public class RewardPolicy {
	/**
	 * 奖励政策id
	 */
	private Integer id;
	/**
	 * 商家id
	 */
	private Long bid;
	/**
	 * 平台奖励政策标题
	 */
	private String title;
	/**
	 * 平台奖励政策内容
	 */
	private String content;
	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 删除标记
	 */
	private int flag;
	/**
	 * 平台奖励政策类型  0表示渔具店  1表示钓场
	 * @return
	 */
	private int type;
	
	public Integer getId() {
		return id;
	}
	public Long getBid() {
		return bid;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public Date getTime() {
		return time;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setBid(Long bid) {
		this.bid = bid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
