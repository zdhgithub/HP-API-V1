package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.Welcome;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
public interface WelcomeService {

	/**
	 * 通过终端标识查询欢迎(启动)页面配置
	 * @param terminal
	 * @return
	 */
	Welcome getWelcomeByTerminal(Integer terminal);

	/**
	 * 修改欢迎启动页的配置信息(cp)
	 * @param welcome
	 */
	void setWelcomeByTerminal(Welcome welcome);

	/**
	 * 查询全部配置信息
	 * @return
	 */
	List<Welcome> getWelcomeAll();

	
}
