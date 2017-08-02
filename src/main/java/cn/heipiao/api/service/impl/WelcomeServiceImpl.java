package cn.heipiao.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.WelcomeMapper;
import cn.heipiao.api.pojo.Welcome;
import cn.heipiao.api.service.WelcomeService;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
@Service
@Transactional(readOnly = true)
public class WelcomeServiceImpl implements WelcomeService{

	@Resource
	private WelcomeMapper welcomeMapper;
	
	/**
	 * 通过终端标识查询欢迎(启动)页面配置
	 */
	@Override
	public Welcome getWelcomeByTerminal(Integer terminal) {
		return welcomeMapper.selectByTerminal(terminal);
	}

	/**
	 * 修改欢迎启动页的配置信息(cp)
	 */
	@Override
	@Transactional
	public void setWelcomeByTerminal(Welcome welcome) {
		welcomeMapper.updateByTerminal(welcome);
	}

	/**
	 * 查询全部配置信息
	 */
	@Override
	public List<Welcome> getWelcomeAll() {
		return welcomeMapper.selectAll();
	}

}
