package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志
 * 
 * @author zf
 *
 */
public class UserOpLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680270510654449170L;
	/** 用户id */
	private Integer uid;
	/** 用户手机号 可空 */
	private String phone;
	/** 操作类型 */
	private String type;
	/** 记录时间 */
	private Date opTime;
	/** 记录IP 可空 */
	private String ip;
	/** 经度 */
	private Double lng;
	/** 纬度 */
	private Double lat;
	/** 地理位置 可空 */
	private String location;

	public Integer getUid() {
		return uid;
	}

	public String getPhone() {
		return phone;
	}

	public String getType() {
		return type;
	}

	public Date getOpTime() {
		return opTime;
	}

	public String getIp() {
		return ip;
	}

	public Double getLng() {
		return lng;
	}

	public Double getLat() {
		return lat;
	}

	public String getLocation() {
		return location;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
