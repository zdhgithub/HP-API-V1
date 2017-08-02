package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 赞
 * @date 2016年6月13日
 */
public class Likes implements Serializable {
	private static final long serialVersionUID = -2702060945835285624L;
	/** 分享或是渔获或是评论的id */
	private Long lid;
	/** 点赞时间 */
	private Date createTime;
	/** 点赞user */
	private Long userId;
	/** 类型1表示分享或渔获的赞，0表示评论的赞 */
	private String type;

	public Long getLid() {
		return lid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public String getType() {
		return type;
	}

	public void setLid(Long lid) {
		this.lid = lid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setType(String type) {
		this.type = type;
	}

}
