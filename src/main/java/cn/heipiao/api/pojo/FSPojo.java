package cn.heipiao.api.pojo;

import java.util.Date;

public class FSPojo {
	private Long id;
	private String img;
	private String name;
	private String addr;
	private Date time;
	private Integer status;

	public Long getId() {
		return id;
	}

	public String getImg() {
		return img;
	}

	public String getName() {
		return name;
	}

	public String getAddr() {
		return addr;
	}

	public Date getTime() {
		return time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
