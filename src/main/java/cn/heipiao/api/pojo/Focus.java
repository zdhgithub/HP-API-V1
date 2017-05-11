/**
 * 
 */
package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
public class Focus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8783671421928154090L;

	/**
	 * 关注钓场的id
	 */
	private Integer fid;
	
	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 关注时间
	 */
	private  Timestamp createTime;

	/**
	 * @return the fid
	 */
	public Integer getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(Integer fid) {
		this.fid = fid;
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
