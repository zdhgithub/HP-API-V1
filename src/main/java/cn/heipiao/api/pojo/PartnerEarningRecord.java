/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年9月21日
 * @version 1.0
 */
public class PartnerEarningRecord {

	/**
	 * 自增id
	 */
	private Long id;
	
	/**
	 *用户id 
	 */
	private Long uid;
	
	/**
	 * 交易漂币
	 */
	private Integer goldFee;
	
	/**
	 * 钓场id
	 */
	private Integer fishSiteId;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the goldFee
	 */
	public Integer getGoldFee() {
		return goldFee;
	}

	/**
	 * @param goldFee the goldFee to set
	 */
	public void setGoldFee(Integer goldFee) {
		this.goldFee = goldFee;
	}

	/**
	 * @return the fishSiteId
	 */
	public Integer getFishSiteId() {
		return fishSiteId;
	}

	/**
	 * @param fishSiteId the fishSiteId to set
	 */
	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
