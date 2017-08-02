package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.Comment;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: ArticleService
 * @Description: TODO
 * @author z
 * @date 2016年10月12日
 */
public interface ArticleService {
	/**
	 * 获取所有文章列表
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Article> getList(Integer uid,Integer start, Integer size,Integer regionId) throws Exception;
	/**
	 * 放鱼信息列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Article> getPutFishList(Map<String,Object> map) throws Exception;
	/**
	 * 经验标红
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getArticleRed(Integer uid) throws Exception;
	/**
	 * 放鱼信息标红
	 * @param uid
	 * @param lng
	 * @param lat
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getPutFishRed(Integer uid,Double lng,Double lat,Integer regionId) throws Exception;
	/**
	 * C端调用文章列表
	 * @param start
	 * @param size
	 * @param shopId
	 * @param visitor
	 * @return
	 * @throws Exception
	 */
	public List<Article> getListForC(Integer start, Integer size,Long shopId,Integer visitor) throws Exception;
	/**
	 * 通过类型查文章列表
	 * @param start
	 * @param size
	 * @param shopId
	 * @param type
	 * @param visitor
	 * @return
	 * @throws Exception
	 */
	public List<Article> getListForCByType(Integer start, Integer size,Long shopId, Integer type, Integer visitor) throws Exception;
	/**
	 * 查询渔具店贡献
	 * @param shopId
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Article> getListByShop(Long shopId, Integer start, Integer size)
			throws Exception;
	/**
	 * 查特定类型的文章
	 * @param shopId
	 * @param type
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Article> getList2ByType(Long shopId, Integer type, Integer start, Integer size)
			throws Exception;
	/**
	 * 查商铺文章
	 * @param shopId
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Article> getListByShop2(Long shopId, Integer start, Integer size)
			throws Exception;
	/**
	 * 查询所有文章
	 * @param shopId
	 * @param type
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Article> getListByType(Long shopId,Integer type, Integer start, Integer size)
			throws Exception;
	/**
	 * 编辑文章
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Integer editArticle(JSONObject json) throws Exception;
	/**
	 * 编辑鱼获，动态
	 * @param json
	 * @return
	 * @throws Exception
	 */
	
	public Integer edits(JSONObject json) throws Exception;
	/**
	 * 删除文章
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer rmArticle(Long id) throws Exception;
	/**
	 * 旧版，查询文章内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Article getOneContent(Long id) throws Exception;
	/**
	 * 新版，查询文章内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Article getOneContentForNew(Long id) throws Exception;
	/**
	 * 通过文章ID查询评论
	 * @param id
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Comment> getComments(Long id, Integer start, Integer size)
			throws Exception;
	/**
	 * 保存文章评论
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Integer adComment(JSONObject json) throws Exception;
	/**
	 * 删除文章评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer rmComment(Long id) throws Exception;
	/**
	 * 获取分享的url
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getShareUri(Integer id) throws Exception;
	/**
	 * 截取描述内容
	 * @param content
	 * @return
	 */
	public String subDes(String content);
}
