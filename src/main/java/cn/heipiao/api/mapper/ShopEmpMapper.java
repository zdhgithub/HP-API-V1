package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.ShopEmp;

/**
 * 
 * @ClassName: ShopEmpMapper
 * @Description: 渔具店员工
 * @author z
 * @date 2016年10月9日
 */
public interface ShopEmpMapper<T> {

	public List<T> selectList(Map<String, Object> params);
	
	public List<T> selectListAll(Map<String, Object> params);

	public T selectOne(Map<String, Object> params);
	
	List<ShopEmp> selectByUid(Long uid);

	public int update(@Param("uid") Integer uid, @Param("shopId") Long shopId,
			@Param("status") Integer status);

	public int insert(T pojo);

	public int delete(@Param("uid") Integer uid, @Param("shopId") Long shopId);

	/**
	 * @param shopId
	 */
	public void deleteEmpByShopId(Long shopId);
}
