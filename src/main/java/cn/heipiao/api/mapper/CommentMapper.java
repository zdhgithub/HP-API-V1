package cn.heipiao.api.mapper;

import java.util.List;

import cn.heipiao.api.pojo.Comment;

/**
 *	@author z
 *	@version 1.0
 *	@description 
 *	@date 2016年6月14日
 */
public interface CommentMapper extends BaseMapper<Comment>{
	/**
	 * 统计分享或渔获的评论数
	 * @param sid
	 * @return
	 */
	public Long countComments(Long sid);
	/**
	 * 通过ID查询所有评论
	 * @param sid
	 * @return
	 */
	public List<Comment> selectAll(Integer sid);
	
}
