package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.ActivityArticle;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.FodderContent;
import cn.heipiao.api.pojo.HpArticle;

/**
 * @author wzw
 * @date 2017年3月29日
 */
public interface HpArticleService {

	public List<HpArticle> getList(Map<String,Object> map);
	
	public void addPojo(HpArticle pojo);
	
	public List<HpArticle> getListByUid(Map<String, Object> map);

	public HpArticle getByArticleId(Long articleId);

	public void addShopArticle(Article art);

	public void addSiteArticle(Article art);
	
	/**
	 * 黑漂有态度
	 * @param pojo
	 */
	public void addPojo(FodderContent pojo);
	
	public void addActivityArticle(ActivityArticle pojo);

	public List<HpArticle> getProfile(Map<String, Object> map);
	
	public List<HpArticle> getAllList(Map<String, Object> map);
	
	public Long getListCount(Map<String, Object> map);
	
	public int update(HpArticle pojo);

	HpArticle getNormalByArticleId(Long articleId);
	
}
