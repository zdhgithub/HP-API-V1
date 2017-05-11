package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.ActivityArticle;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public interface ActivityArticleMapper {

	List<ActivityArticle> selectByCid(Map<String,Object> map);

	ActivityArticle selectById(Long id);
	
	int insertPojo(ActivityArticle pojo);
	
	int updatePojo(ActivityArticle pojo);

	int deleteById(Long id);

	List<ActivityArticle> selectFilterByCid(Map<String, Object> map);
	
}
