package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Likes;

/**
 * @author z
 * @version 1.0
 * @description
 * @date 2016年6月15日
 */
public interface LikeMapper extends BaseMapper<Likes> {
	/**
	 * 谁赞了我
	 * 
	 * @param userId
	 * @return
	 */
//	public List<Likes> selectWhoLikesMe(Integer userId);

	/**
	 * 统计某个评论，分享或是渔获的点赞数
	 * 
	 * @return
	 */
	public Long countLikes(@Param("id") Long id, @Param("type") String type);

	/**
	 * 删除
	 * 
	 * @param like
	 */
	public void deleteLikeById(Likes like);

	/**
	 * 查询某个赞
	 * 
	 * @param userId
	 * @param shareId
	 * @param type
	 * @return
	 */
	public Likes selectByUnionId(@Param("userId") Long userId,
			@Param("shareId") Long shareId, @Param("type") String type);
	/**
	 * 查询所有赞
	 * @param id
	 * @return
	 */
	public List<Likes> selectAll(Integer id);
}
