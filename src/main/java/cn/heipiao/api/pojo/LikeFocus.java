package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * 
 * @ClassName: LikeFocus
 * @Description: TODO
 * @author zf
 * @date 2016年10月14日
 */
public class LikeFocus {
	private Integer uid;
	private Integer targetId;
	private Date createTime;
	private String nickname;

	public Integer getUid() {
		return uid;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
