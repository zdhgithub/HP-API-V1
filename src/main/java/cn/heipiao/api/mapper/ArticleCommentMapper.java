package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

public interface ArticleCommentMapper<T> {
	/**
	 * 查询文章评论列表
	 * @param params
	 * @return
	 */
	public List<T> selectList(Map<String, Object> params);
	/**
	 * 保存评论
	 * @param pojo
	 * @return
	 */
	public int insert(T pojo);
	/**
	 * 更新评论
	 * @param id
	 * @return
	 */
	public int update(Long id);
	/**
	 * 统计评论数
	 * @param id
	 * @return
	 */
	public int countArticleComents(Long id);
}
