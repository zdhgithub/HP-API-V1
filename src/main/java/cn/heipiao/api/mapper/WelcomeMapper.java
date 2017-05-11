package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.Welcome;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
public interface WelcomeMapper {

	/**
	 * 根据终端标识查询欢迎(启动)页面配置信息
	 * @param terminal
	 * @return
	 */
	Welcome selectByTerminal(Integer terminal);
	
	/**
	 * 根据终端标识更新欢迎(启动)页面配置信息
	 * @param welcome
	 * @return
	 */
	Integer updateByTerminal(Welcome welcome);

	/**
	 * 查询所有的配置信息
	 */
	List<Welcome> selectAll();
}
