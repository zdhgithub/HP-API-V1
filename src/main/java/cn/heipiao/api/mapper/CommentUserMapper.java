package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.CommentUser;
/**
 * 用户回复
 * @ClassName: CommentUserMapper
 * @Description: TODO
 * @author zf
 */
public interface CommentUserMapper {
	/**
	 * 查询用户回复内容
	 * @param params
	 * @return
	 */
	List<CommentUser> selectList(Map<String, Object> params);
	/**
	 * 查询鱼获的回复
	 * @param params
	 * @return
	 */
	List<CommentUser> selectListForShare(Map<String, Object> params);
	/**
	 * 插入一条回复
	 * @param cu
	 * @return
	 */
	int insert(CommentUser cu);
}
