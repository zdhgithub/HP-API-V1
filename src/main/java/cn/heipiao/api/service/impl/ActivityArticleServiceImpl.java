package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ActivityArticleMapper;
import cn.heipiao.api.pojo.ActivityArticle;
import cn.heipiao.api.service.ActivityArticleService;
import cn.heipiao.api.service.HpArticleService;

/**
 * @author wzw
 * @date 2017年3月21日
 */
@Service
public class ActivityArticleServiceImpl implements ActivityArticleService {

	@Resource
	private ActivityArticleMapper activityArticleMapper;
	
	@Resource
	private HpArticleService hpArticleService;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void add(ActivityArticle pojo) {
		activityArticleMapper.insertPojo(pojo);
		hpArticleService.addActivityArticle(pojo);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void modify(ActivityArticle pojo) {
		activityArticleMapper.updatePojo(pojo);
		hpArticleService.addActivityArticle(pojo);
	}

	
	@Override
	public List<ActivityArticle> getListByCid(Map<String, Object> map) {
		return activityArticleMapper.selectByCid(map);
	}

	@Override
	public ActivityArticle getById(Long id) {
		return activityArticleMapper.selectById(id);
	}

	@Override
	public void deleteById(Long id) {
		activityArticleMapper.deleteById(id);
	}

	@Override
	public List<ActivityArticle> getListsByCid(Map<String, Object> map) {
		return activityArticleMapper.selectFilterByCid(map);
	}

}
