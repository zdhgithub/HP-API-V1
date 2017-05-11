package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 个人印象
 * @ClassName: PersonImpressionMapper
 * @Description: TODO
 * @author zf
 */
public interface PersonImpressionMapper<T> {
	/**
	 * 查询个人印象列表
	 * @param params
	 * @return
	 */
	public List<T> selectList(Map<String, Object> params);
	/**
	 * 查询某一条印象
	 * @param uid
	 * @param shopId
	 * @param label
	 * @return
	 */
	public T selectOne(@Param("uid") Integer uid,@Param("shopId") Long shopId, @Param("label") String label);
	/**
	 * 删除印象
	 * @param uid
	 * @param shopId
	 * @param label
	 * @return
	 */
	public int delete(@Param("uid") Integer uid,@Param("shopId") Long shopId, @Param("label") String label);
	/**
	 * 更新印象
	 * @param params
	 * @return
	 */
	public int update(Map<String, Object> params);
	/**
	 * 插入
	 * @param pojo
	 * @return
	 */
	public int insert(T pojo);
	/**
	 * 查询领域列表
	 * @param params
	 * @return
	 */
	public List<T> selectListField(Map<String, Object> params);
	/**
	 * 查询一条领域记录
	 * @param uid
	 * @param shopId
	 * @param label
	 * @return
	 */
	public T selectOneField(@Param("uid") Integer uid,@Param("shopId") Long shopId,
			@Param("label") String label);
	/**
	 * 插入一条领域记录
	 * @param pojo
	 * @return
	 */
	public int insertField(T pojo);
	/**
	 * 删除一条领域记录
	 * @param uid
	 * @param shopId
	 * @param label
	 * @return
	 */
	public int deleteField(@Param("uid") Integer uid,@Param("shopId") Long shopId,
			@Param("label") String label);

}
