package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FodderContentMapper;
import cn.heipiao.api.pojo.FodderContent;
import cn.heipiao.api.service.FodderContentService;
import cn.heipiao.api.service.HpArticleService;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
@Service
@Transactional(readOnly = true)
public class FodderContentServiceImpl implements FodderContentService{
	
	private static final Object obj = new Object();

	@Resource
	private FodderContentMapper fodderContentMapper;
	
	@Resource
	private HpArticleService hpArticleService;
	

	@Override
	public FodderContent getById(Integer id) {
		return fodderContentMapper.selectById(id);
	}
	
	@Override
	@Transactional
	public int create(FodderContent pojo) {
		int i = fodderContentMapper.insertFodderContent(pojo);
		hpArticleService.addPojo(pojo);
		return i;
	}

	@Override
	@Transactional
	public int modification(FodderContent pojo) {
		hpArticleService.addPojo(pojo);
		return fodderContentMapper.updateById(pojo);
	}

	@Override
	public List<Map<String, Object>> selectArticles(Map<String, Object> params) {
		return fodderContentMapper.selectArticles(params);
	}

	@Override
	public int queryListCount(Map<String, Object> map) {
		return fodderContentMapper.selectListCount(map);
	}

	@Override
	public List<FodderContent> queryList(Map<String, Object> map) {
		return fodderContentMapper.selectList(map);
	}

	@Override
	public List<FodderContent> queryAllClassify() {
		return fodderContentMapper.selectAllClassify();
	}

	@Override
	@Transactional
	public void plusReadCount(Integer id) {
		synchronized (obj) {
			fodderContentMapper.plusReadCount(id);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delectArticle(Integer id){
		fodderContentMapper.delectArticle(id);
	}

}
