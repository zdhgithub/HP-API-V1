package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.TabRelavance;

/**
 * @author zf
 * @version 1.0
 * @description user标签
 * @date 2016年6月6日
 */
public interface TabRelavanceMapper {
	/**
	 * 查询user某类型的标签
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<TabRelavance> getUserTab(@Param("userId") final Integer userId,
			@Param("type") final String type) throws Exception;

	/**
	 * 保存user标签
	 * 
	 * @param tab
	 * @return
	 * @throws Exception
	 */
	public int saveUserTab(TabRelavance tab) throws Exception;

	/**
	 * 删除user标签
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void deleteUserTab(@Param("userId") final int userId,
			@Param("type") final String type) throws Exception;

}
