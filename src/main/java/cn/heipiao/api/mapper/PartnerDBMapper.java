package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;
/**
 * 
 * @ClassName: PartnerDBMapper
 * @Description: TODO
 * @author zf
 */
public interface PartnerDBMapper<T> {
	/**
	 * 查询合伙人资料列表
	 * @param params
	 * @return
	 */
	List<T> selectList(Map<String, Object> params);
	/**
	 * 插入
	 * @param pojo
	 * @return
	 */
	int insert(T pojo);
	/**
	 * 更新
	 * @param pojo
	 * @return
	 */
	int update(T pojo);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(Integer id);
}
