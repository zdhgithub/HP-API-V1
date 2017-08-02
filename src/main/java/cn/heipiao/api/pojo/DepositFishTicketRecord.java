package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * 门票存鱼使用记录
 * 
 * @author zf
 *
 */
public class DepositFishTicketRecord implements Serializable {
	private static final long serialVersionUID = -1161438944323175090L;
	/** 用户id */
	private Integer uid;
	/** 票id */
	private Long tid;

	public Integer getUid() {
		return uid;
	}

	public Long getTid() {
		return tid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

}
