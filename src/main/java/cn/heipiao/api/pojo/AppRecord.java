package cn.heipiao.api.pojo;

import java.util.Date;

public class AppRecord {
	private Long id;
	private String version;
	private Date createTime;
	private Double lng;
	private Double lat;
	private String city;
	private String district;
	private String addr;
	private Integer uid;
	private String ip;
	/** 运营商 **/
	private String telecom;
	/** 网络制式 **/
	private String netType;
	/** 手机厂商 **/
	private String vendor;
	/** 手机型号 **/
	private String model;
	private String os;

	public Long getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Double getLng() {
		return lng;
	}

	public Double getLat() {
		return lat;
	}

	public String getCity() {
		return city;
	}

	public String getDistrict() {
		return district;
	}

	public String getAddr() {
		return addr;
	}

	public Integer getUid() {
		return uid;
	}

	public String getIp() {
		return ip;
	}

	public String getTelecom() {
		return telecom;
	}

	public String getNetType() {
		return netType;
	}

	public String getVendor() {
		return vendor;
	}

	public String getModel() {
		return model;
	}

	public String getOs() {
		return os;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setOs(String os) {
		this.os = os;
	}

}
