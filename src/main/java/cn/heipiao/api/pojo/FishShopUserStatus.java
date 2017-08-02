package cn.heipiao.api.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月12日
 * @version 2.0
 */
public class FishShopUserStatus implements Serializable {

	private static final long serialVersionUID = 850496916813722913L;
	
	/**
	 * 主键id
	 */
	private Integer fusId;
	
	/**
	 * 处理类型 0-点赞 1-收藏
	 */
	private Integer fusType;
	
	/**
	 * 渔具店唯一标识
	 */
	private Long fsId;
	
	/**
	 * 操作用户唯一标识
	 */
	private Long uid;
	
	/**
	 * 创建时间
	 */
	private String fusIdCreateTime;
	
	
	
	public Integer getFusId() {
		return fusId;
	}



	public void setFusId(Integer fusId) {
		this.fusId = fusId;
	}



	public Integer getFusType() {
		return fusType;
	}



	public void setFusType(Integer fusType) {
		this.fusType = fusType;
	}



	public Long getFsId() {
		return fsId;
	}



	public void setFsId(Long fsId) {
		this.fsId = fsId;
	}



	public Long getUid() {
		return uid;
	}



	public void setUid(Long uid) {
		this.uid = uid;
	}



	public String getFusIdCreateTime() {
		return fusIdCreateTime;
	}



	public void setFusIdCreateTime(String fusIdCreateTime) {
		this.fusIdCreateTime = fusIdCreateTime;
	}



	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
