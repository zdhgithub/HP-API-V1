package cn.heipiao.api.service;

import cn.heipiao.api.pojo.Likes;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月15日
 */
public interface LikeService {
	/**
	 * 点赞
	 * 
	 * @param userId
	 * @param shareId
	 * @throws Exception
	 */
	public long iLikeIt(Long userId, Long shareId, String type)
			throws Exception;

	/**
	 * 取消赞
	 * 
	 * @param userId
	 * @param shareId
	 * @throws Exception
	 */
	public long unLikeIt(Long userId, Long shareId, String type)
			throws Exception;

	/**
	 * 统计评论或是分享或是渔获的赞的数量
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long countLikes(Long id, String type) throws Exception;

	/**
	 * 查询某个赞
	 * 
	 * @param userId
	 * @param shareId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Likes queryLike(Long userId, Long shareId, String type)
			throws Exception;
}
