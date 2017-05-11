package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticleComments;

/**
 * @author wzw
 * @date 2017年3月27日
 */
public interface HpArticleCommentsMapper {

	List<HpArticleComments> selectByArticleId(Map<String,Object> map);
	
	int insertPojo(HpArticleComments pojo);
	
	int deletePojo(HpArticleComments pojo);
	
	List<HpArticleComments> selectCommentListByArticleId(Map<String, Object> map);
	
}
