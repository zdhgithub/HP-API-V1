package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ArticleDictMapper;
import cn.heipiao.api.mapper.DictMapper;
import cn.heipiao.api.pojo.ArticleDict;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.ConfigService;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月7日
 */
@Service
public class ConfigServiceImpl implements ConfigService {
	@Resource
	private DictMapper dictMapper;
	
	@Resource
	private ArticleDictMapper articleDictMapper;

	@Override
	public List<DictConfig> queryConfigByType(String type) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type);
		param.put("valid", "1");
		List<DictConfig> results = dictMapper.selectList(param);
		return results;
	}

	@Override
	@Transactional
	public Integer updateDict(DictConfig dict) throws Exception {
		dictMapper.updateById(dict);
		return Status.success;
	}

	@Override
	@Transactional
	public Integer addDict(DictConfig dict) throws Exception {
		dict.setValid("1");
		this.dictMapper.insert(dict);
		return dict.getId(); 
	}

	@Override
	@Transactional
	public Integer deleteDict(Integer id) throws Exception {
		this.dictMapper.deleteById(id);
		return Status.success;
	}

	/**
	 * 通过数据类别和数据名称/值获取字典对象
	 */
	@Override
	public DictConfig queryConfigByTypeValue(String type, String value) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("type", type);
		param.put("value", value);
		DictConfig results = dictMapper.selectConfigByTypeValue(param);
		return results;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ConfigService#selectByType(java.lang.String)
	 */
	@Override
	public DictConfig selectByType(String type) {
		return dictMapper.selectByType(type);
	}

	@Override
	public List<ArticleDict> getListArticleCategory(Integer category) {
		return articleDictMapper.selectListByCategory(category);
	}

}
