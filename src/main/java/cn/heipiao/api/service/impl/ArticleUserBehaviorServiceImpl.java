package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ArticleUserBehaviorMapper;
import cn.heipiao.api.pojo.ArticleUserBehavior;
import cn.heipiao.api.service.ArticleUserBehaviorService;

/**
 * @author asdf3070@163.com
 * @date 2016年11月7日
 * @version 2.1
 */
@Service
@Transactional(readOnly = true)
public class ArticleUserBehaviorServiceImpl implements ArticleUserBehaviorService {

	@Resource
	private ArticleUserBehaviorMapper articleUserBehaviorMapper;
	
	@Override
	public ArticleUserBehavior getBehavior(ArticleUserBehavior pojo) {
		return articleUserBehaviorMapper.selectBehavior(pojo);
	}

	@Override
	@Transactional
	public int add(ArticleUserBehavior pojo) {
		return articleUserBehaviorMapper.add(pojo);
	}

	@Override
	@Transactional
	public int updateCount(ArticleUserBehavior pojo) {
		return articleUserBehaviorMapper.updateCount(pojo);
	}

}
