package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticleMsg;

/**
 * @author wzw
 * @date 2017年4月1日
 */
public interface HpArticleMsgMapper {
	
	List<HpArticleMsg> selectListByUid(Map<String, Object> map);

	HpArticleMsg selectUnique(HpArticleMsg pojo);
	
	int insertPojo(HpArticleMsg pojo);
	
	int updateMsgContentIsNull(HpArticleMsg pojo);
	
	int deletePojo(HpArticleMsg uid);
	
	int deleteAllByUid(Long uid);
	
}
