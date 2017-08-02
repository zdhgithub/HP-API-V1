package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticle;

/**
 * @author wzw
 * @date 2017年3月27日
 */
public interface HpArticleMapper {
	
	List<HpArticle> selectListByRegionId(Map<String,Object> map);
	
	HpArticle selectByIdAsLock(Long articleId);

	HpArticle selectById(Long articleId);
	
	HpArticle selectNormalById(Long articleId);

	int insertPojo(HpArticle pojo);

	int updatePojo(HpArticle pojo);
	
	int deletePojo(Long articleId);

	List<HpArticle> selectListByUid(Map<String, Object> map);

	HpArticle selectByOtherArticleId(HpArticle pojo);

	int updateByArticleId(HpArticle pojo);

	List<HpArticle> selectProfile(Map<String,Object> map);
	
	List<HpArticle> selectList(Map<String, Object> map);
	
	Long selectListCount(Map<String, Object> map);
	
	int update(HpArticle pojo);
}
