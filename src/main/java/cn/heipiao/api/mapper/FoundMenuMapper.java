/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FoundMenu;

/**
 * @author wzw
 * @date 2016年6月15日
 * @version 1.0
 */
public interface FoundMenuMapper {
	
	List<FoundMenu> selectAllByStatus(Integer status);

	List<FoundMenu> selectAll();
	
	List<FoundMenu> selectSeconds(Integer pid);
	
	List<FoundMenu> selectArticles(Map<String,Object> params);
	
	void insertPojo(FoundMenu pojo);

	void updatePojo(FoundMenu pojo);

	void deletePojo(Integer fid);
	
	void updateList();
	
}

