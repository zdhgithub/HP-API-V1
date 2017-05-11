package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FodderContent;

/**
 * @author GengJinpeng[asdf3070@163.com]
 * @version 2.3 2017-01-04
 */
public interface FodderContentMapper {

	FodderContent selectById(Integer id);

	int insertFodderContent(FodderContent pojo);

	int updateById(FodderContent pojo);

	List<Map<String, Object>> selectArticles(Map<String, Object> params);

	int selectListCount(Map<String, Object> map);

	List<FodderContent> selectList(Map<String, Object> map);

	List<FodderContent> selectAllClassify();

	void plusReadCount(Integer id);

	void delectArticle(Integer id);
	
}
