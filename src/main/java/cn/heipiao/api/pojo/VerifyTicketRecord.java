/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年11月14日
 * @version 1.0
 */
public class VerifyTicketRecord {

	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 钓场id
	 */
	private Integer siteId;
	
	/**
	 * 票id
	 */
	private Long tid;
	
	/**
	 * 核票时间
	 */
	private Timestamp createTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
