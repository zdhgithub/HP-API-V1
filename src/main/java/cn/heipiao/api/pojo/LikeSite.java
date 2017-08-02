/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年11月29日
 * @version 1.0
 */
public class LikeSite {

	/**
	 * 钓场id
	 */
	private Integer siteId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
