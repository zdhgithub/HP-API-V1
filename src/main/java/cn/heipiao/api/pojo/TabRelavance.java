package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zf
 * @version 1.0
 * @description	用户标签
 * @date 2016年6月6日
 */
@XmlRootElement
public class TabRelavance implements Serializable{
	private static final long serialVersionUID = 5539992013458744698L;
	private Long userId;// 用户id
	private String tabCode;// 标签code
	private String tabName;// 标签
	private Date time;// 贴标时间

	public TabRelavance() {
	}

	@XmlElement
	public Long getUserId() {
		return userId;
	}

	@XmlElement
	public String getTabCode() {
		return tabCode;
	}

	@XmlElement
	public String getTabName() {
		return tabName;
	}

	@XmlElement
	public Date getTime() {
		return time;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
