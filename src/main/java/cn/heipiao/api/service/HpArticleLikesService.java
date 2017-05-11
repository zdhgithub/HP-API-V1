package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticleLikes;

/**
 * @author wzw
 * @date 2017年3月29日
 */
public interface HpArticleLikesService {

	
	void addLike(HpArticleLikes pojo);
	
	
	void removeLike(HpArticleLikes pojo);


	List<HpArticleLikes> getLikeListByArticleId(Map<String, Object> map);
	
}
