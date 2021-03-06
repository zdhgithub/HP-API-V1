/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年11月2日
 * @version 1.0
 */
public class SiteCouponsRecord extends User  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 发券记录id 
	 */
	private Long id;
	
	/**
	 * 钓场id
	 */
	private Integer siteId;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 优惠券id
	 */
	private Long cid;
	
	/**
	 * 获取时间
	 */
	private Timestamp getTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Timestamp getGetTime() {
		return getTime;
	}

	public void setGetTime(Timestamp getTime) {
		this.getTime = getTime;
	}
	
}
