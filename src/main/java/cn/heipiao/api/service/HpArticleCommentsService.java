package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.HpArticleComments;

/**
 * @author wzw
 * @date 2017年3月29日
 */
public interface HpArticleCommentsService {

	public void addComment(HpArticleComments pojo);

	public List<HpArticleComments> getCommentListByArticleId(Map<String, Object> map);

	public void removeComment(HpArticleComments pojo);
	
}
