package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.2
 */
public class SysPictures implements Serializable {
	
	private static final long serialVersionUID = 5650129903968994832L;
	
	/**
	 * 记录id
	 */
	private String id;
	
	/**
	 * 存储类型 1-钓场列表图 2-店铺顶部图 3-钓场列表图 4-店铺顶部图 5-大师顶部图
	 */
	private Integer type;
	
	/**
	 * 图片存储-base64格式
	 */
	private String picture;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
