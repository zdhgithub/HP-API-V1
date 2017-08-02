package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
public class Welcome implements Serializable {

	private static final long serialVersionUID = 850496916813722913L;
	
	/**
	 * 唯一标识
	 */
	private Integer welcomeId;
	
	/**
	 * 终端标识 1-商家版 2-用户版
	 */
	private Integer terminal;
	
	/**
	 * 是否显示跳过按钮 0-显示 1-不显示
	 */
	private Integer butShowFlag;
	
	/**
	 * 延迟时间（秒）
	 */
	private Integer delayTime;
	
	/**
	 * 页面内容，多个页面片英文逗号间隔
	 */
	private String content;
	
	/**
	 * 更新时间-图片存储
	 */
	private Date updatetime;
	
	/**
	 * 更新时间-延迟及按钮
	 */
	private Date updatetimeTools;
	
	public Integer getWelcomeId() {
		return welcomeId;
	}
	
	public void setWelcomeId(Integer welcomeId) {
		this.welcomeId = welcomeId;
	}
	
	public Integer getTerminal() {
		return terminal;
	}
	
	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}
	
	public Integer getButShowFlag() {
		return butShowFlag;
	}

	public void setButShowFlag(Integer butShowFlag) {
		this.butShowFlag = butShowFlag;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetimeTools() {
		return updatetimeTools;
	}

	public void setUpdatetimeTools(Date updatetimeTools) {
		this.updatetimeTools = updatetimeTools;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
