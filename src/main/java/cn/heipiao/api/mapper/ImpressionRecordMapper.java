package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;
/**
 * 
 * @ClassName: ImpressionRecordMapper
 * @Description: ImpressionRecord
 * @author z
 * @date 2016年10月22日
 */
public interface ImpressionRecordMapper<T> {
	/**
	 * 查询一条印象
	 * @param uid
	 * @param targetId
	 * @param shopId
	 * @param label
	 * @return
	 */
	public T selectOne(@Param("uid") Integer uid,
			@Param("targetId") Integer targetId,@Param("shopId") Long shopId, @Param("label") String label);
	/**
	 * 插入一条印象记录
	 * @param pojo
	 * @return
	 */
	public int insert(T pojo);
}
