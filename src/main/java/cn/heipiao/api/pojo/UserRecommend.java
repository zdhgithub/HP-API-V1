/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public class UserRecommend {

	/**
	 * 推荐人uid
	 */
	private Long ruid;
	
	/**
	 * 领券人的uid
	 */
	private Long  uid;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	//手机号
	private String phone;
	
	//状态
	private Integer status;

	/**
	 * @return the ruid
	 */
	public Long getRuid() {
		return ruid;
	}

	/**
	 * @param ruid the ruid to set
	 */
	public void setRuid(Long ruid) {
		this.ruid = ruid;
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

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
