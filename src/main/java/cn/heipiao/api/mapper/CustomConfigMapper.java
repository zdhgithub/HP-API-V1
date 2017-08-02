package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;
/**
 * 
 * @ClassName: CustomConfigMapper
 * @Description: TODO
 * @author zf
 */
public interface CustomConfigMapper {
	/**
	 * 查一条配置
	 * @param id
	 * @param type
	 * @return
	 */
	public Integer selectOne(@Param("id")Long id, @Param("type")Integer type);
	/**
	 * 插入一条配置
	 * @param id
	 * @param type
	 * @param config
	 * @return
	 */
	public int insert(@Param("id") Long id, @Param("type") Integer type,
			@Param("config") Integer config);
	/**
	 * 更新一条配置
	 * @param id
	 * @param type
	 * @param config
	 * @return
	 */
	public int update(@Param("id") Long id, @Param("type") Integer type,
			@Param("config") Integer config);
}
