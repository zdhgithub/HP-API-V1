package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Comment;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月14日
 */
public interface CommentService {
	/**
	 * 根据分享或是渔获查询评论列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Comment> queryComments(Map<String, Object> params)
			throws Exception;

	/**
	 * 发表评论
	 * 
	 * @param com
	 * @throws Exception
	 */
	public void publishComment(JSONObject json) throws Exception;

	/**
	 * 审核评论
	 * 
	 * @param com
	 * @throws Exception
	 */
	public void checkComment(Comment com) throws Exception;
	/**
	 * 统计某个分享或是渔获的评论数
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public long countComments(Long sid) throws Exception;
}
