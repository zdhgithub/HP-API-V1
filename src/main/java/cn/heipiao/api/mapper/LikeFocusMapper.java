package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.User;
/**
 * 
 * @ClassName: LikeFocusMapper
 * @Description: TODO
 * @author zf
 */
public interface LikeFocusMapper<T> {
	/**
	 * 查询文章的点赞人
	 * @param targetId
	 * @return
	 */
	public List<String> selectNamesforArticle(Integer targetId);
	/**被谁关注的数量*/
	public int countFocus(Integer uid);
	/**我关注了谁的数量*/
	public int countFocusOther(Integer uid);
	/**
	 * 统计点赞用户数
	 * @param uid
	 * @return
	 */
	public int countLikeUser(Integer uid);
	/**
	 * 统计点赞文章数
	 * @param id
	 * @return
	 */
	public int countLikeArticle(Long id);
	/**
	 * 插入点赞用户
	 * @param pojo
	 * @return
	 */
	public int insertLikeUser(T pojo);
	/**
	 * 插入关注或是点赞记录
	 * @param pojo
	 * @return
	 */
	public int insertFocusUser(T pojo);
	/**
	 * 插入点赞文章
	 * @param pojo
	 * @return
	 */
	public int insertLikeArticle(T pojo);
	/**
	 * 删除点赞或是关注
	 * @param uid
	 * @param targetId
	 * @return
	 */
	public int deleteLikeUser(@Param("uid") Integer uid,
			@Param("targetId") Integer targetId);
	/**
	 * 删除关注用户
	 * @param uid
	 * @param targetId
	 * @return
	 */
	public int deleteFocusUser(@Param("uid") Integer uid,
			@Param("targetId") Integer targetId);
	/**
	 * 删除文章点赞
	 * @param uid
	 * @param targetId
	 * @return
	 */
	public int deleteLikeArticle(@Param("uid") Integer uid,
			@Param("targetId") Integer targetId);
	/**
	 * 查询一条关注记录
	 * @param uid
	 * @param uid2
	 * @return
	 */
	public T selectOneFocus(@Param("uid")Integer uid,@Param("uid2")Integer uid2);
	/**
	 * 查询一条点赞记录
	 * @param uid
	 * @param uid2
	 * @return
	 */
	public T selectOneLike(@Param("uid")Integer uid,@Param("uid2")Integer uid2);
	/**
	 * 查询一条文章点赞记录
	 * @param uid
	 * @param targetId
	 * @return
	 */
	public T selectOneLikeArticle(@Param("uid") Integer uid,
			@Param("targetId") Long targetId );

	/**
	 * 根据文章ID查询关注用户ID
	 * @param uid
	 * @return
	 */
	public Set<Long> selectUidsByUid(Long uid);
	/**
	 * 关注了那些人
	 * @param uid
	 * @return
	 */
	public List<User> selectFocusUsers(Integer uid);
	/**
	 * 被哪些人关注
	 * @param uid
	 * @return
	 */
	public List<Integer> selectFocusByUser(Integer uid);

}
