package cn.heipiao.api.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ArticleNewMapper {
	/**
	 * 查询一条记录
	 * @param uid
	 * @param type
	 * @return
	 */
//	Integer selectOne(@Param("uid") Integer uid, @Param("type") Integer type);
	/**
	 * 插入一条记录
	 * @param uid
	 * @param type
	 * @return
	 */
//	Integer insert(@Param("uid") Integer uid, @Param("type") Integer type);
	/**
	 * 更新一条记录
	 * @param type
	 * @param flag
	 * @return
	 */
//	Integer update(@Param("type") Integer type, @Param("flag") Integer flag);
	/**
	 * 更新
	 * @param uid
	 * @param type
	 * @param flag
	 * @return
	 */
//	Integer updateOne(@Param("uid") Integer uid, @Param("type") Integer type,
//			@Param("flag") Integer flag);

	/******************************/
	// 初始化调用
	Integer insertNew(@Param("sid") Long sid, @Param("uid") Integer uid,
			@Param("type") Integer type);
	//用户批量
	Integer insertNewBatchUser(Map<String,Object> map);
	
	//钓场或渔具店批量
	Integer insertNewBatchSite(Map<String,Object> map);

	// 店主或是钓场调用
	Integer updateNewSite(@Param("sid") Long sid, @Param("type") Integer type,
			@Param("flag") Integer flag);

	// 用户调用
	Integer updateNewUser(@Param("sid") Long sid, @Param("type") Integer type,
			@Param("uid") Integer uid, @Param("flag") Integer flag);

	// 查flag通用
	Integer selectNew(@Param("sid") Long sid, @Param("type") Integer type,
			@Param("uid") Integer uid);
	// 查num通用
	Integer selectNewNum(@Param("sid") Long sid, @Param("type") Integer type,
			@Param("uid") Integer uid);
	//清0
	Integer updateNewClear(@Param("sid") Long sid, @Param("type") Integer type,
			@Param("uid") Integer uid);
	//+1
	Integer updateNewAdd(@Param("sid") Long sid, @Param("type") Integer type);
}
