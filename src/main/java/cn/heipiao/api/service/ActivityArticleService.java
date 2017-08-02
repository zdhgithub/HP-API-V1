package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.ActivityArticle;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public interface ActivityArticleService {
	
	void add(ActivityArticle pojo);

	void modify(ActivityArticle pojo);
	
	List<ActivityArticle> getListByCid(Map<String,Object> map);

	ActivityArticle getById(Long id);

	void deleteById(Long id);

	List<ActivityArticle> getListsByCid(Map<String, Object> map);
	
}
