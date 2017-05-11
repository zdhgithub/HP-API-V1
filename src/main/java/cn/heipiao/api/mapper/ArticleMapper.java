package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: ArticleMapper
 * @Description: TODO
 * @author z
 * @date 2016年10月12日
 */
public interface ArticleMapper<T> {
	/**
	 * 通过类型统计文章数（2.2）
	 * @param type
	 * @return
	 */
	Integer countArtNew(Integer type);
	/**
	 * 通过类型统计文章数
	 * @param type
	 * @return
	 */
	Integer countArts(Integer type);
	/**
	 * 查询文章列表
	 * @param params
	 * @return
	 */
	public List<T> selectList(Map<String, Object> params);
	/**
	 * 查询放鱼文章列表
	 * @param params
	 * @return
	 */
	public List<T> selectListExtForPutFish(Map<String, Object> params);
	/**
	 * 查询一条文章
	 * @param id
	 * @return
	 */
	public T selectOne(Long id);
	/**
	 * 通过标题查文章
	 * @param title
	 * @return
	 */
	public T selectOneByTitle(String title);
	/**
	 * 查询文章内容
	 * @param id
	 * @return
	 */
	public T selectOneContent(Long id);
	/**
	 * 查询最大ＩＤ
	 * @return
	 */
//	public long selectMaxId();
	/**
	 * 保存一条记录
	 * @param pojo
	 * @return
	 */
	public int insert(T pojo);
	/**
	 * 保存内容
	 * @param pojo
	 * @return
	 */
	public int insertContent(T pojo);
	/**
	 * 更新列表
	 * @param params
	 * @return
	 */
	public int update(T pojo);
	
	
	public int updateArticleContentId(T pojo);
	
	/**
	 * 更新内容
	 * @param params
	 * @return
	 */
	public int updateContent(Map<String, Object> params);
	/**
	 * 更新相关统计
	 * @param id
	 * @return
	 */
	public int updateCount(Long id);
	
	/**
	 * 通过文章id找记录
	 * @param id
	 * @return
	 */
	public T selectById(Long id);
	/**查询大师经验*/
	
	public List<T> selecShoptList(Map<String, Object> params);
}
