package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticleLikes;

/**
 * @author wzw
 * @date 2017年3月27日
 */
public interface HpArticleLikesMapper {

	List<HpArticleLikes> selectByArticleId(Map<String,Object> map);
	
	HpArticleLikes selectUnique(HpArticleLikes pojo);
	
	int insertPojo(HpArticleLikes pojo);
	
	int deletePojo(HpArticleLikes pojo);

	List<HpArticleLikes> selectLikeListByArticleId(Map<String, Object> map);
	
}
