package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FodderContent;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
public interface FodderContentService {

	FodderContent getById(Integer id);

	int create(FodderContent pojo);

	int modification(FodderContent pojo);

	List<Map<String, Object>> selectArticles(Map<String, Object> params);

	int queryListCount(Map<String, Object> map);

	List<FodderContent> queryList(Map<String, Object> map);

	List<FodderContent> queryAllClassify();

	void plusReadCount(Integer id);

	void delectArticle(Integer id);

}
