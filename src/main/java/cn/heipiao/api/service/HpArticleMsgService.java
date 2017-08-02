package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.HpArticleComments;
import cn.heipiao.api.pojo.HpArticleLikes;
import cn.heipiao.api.pojo.HpArticleMsg;

/**
 * @author wzw
 * @date 2017年4月1日
 */
public interface HpArticleMsgService {

	List<HpArticleMsg> getListByUid(Map<String,Object> map);
	
	void addPojo(HpArticleMsg pojo);
	
	void removeAllByUid(Long uid);
	
	void removePojo(HpArticleMsg pojo);
	
	void modifyMsgContentIsNull(HpArticleMsg pojo);

	void addCommentMsgPojo(HpArticleComments pojo,HpArticle a);

	void addLikeMsgPojo(HpArticleLikes pojo, HpArticle a);
	
}
