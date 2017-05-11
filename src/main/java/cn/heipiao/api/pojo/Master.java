package cn.heipiao.api.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.1
 */
public class Master implements Serializable {
	
	private static final long serialVersionUID = -8876710635814270672L;
	
	/**
	 * 主键id
	 */
	private Long uid;
	
	/**
	 * 昵称
	 */
	private String nickname;
	
	/**
	 * 真实姓名
	 */
	private String realname;
	
	/**
	 * 头像url
	 */
	private String portriat;
	
	/**
	 * 主图(顶部图片-t_sys_pictures.f_id)
	 */
	private String topImg;


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getPortriat() {
		return portriat;
	}


	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}


	public String getTopImg() {
		return topImg;
	}


	public void setTopImg(String topImg) {
		this.topImg = topImg;
	}


	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
