/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FoundMenu;

/**
 * @author wzw
 * @date 2016年6月15日
 * @version 1.0
 */
public interface FoundMenuService {

	List<FoundMenu> selectAllByStatus(Integer status);

	/**
	 * 查询一级
	 * 
	 * @return
	 */
	List<FoundMenu> selectAll();

	/**
	 * 查询下一级
	 * 
	 * @return
	 */
	List<FoundMenu> selectSeconds(Integer pid);

	List<FoundMenu> selectArticles(Map<String, Object> params) throws Exception;

	List<FoundMenu> insertPojo(FoundMenu pojo);

	List<FoundMenu> updatePojo(FoundMenu pojo);

	List<FoundMenu> deletePojo(Integer fid);

	/**
	 * 置顶
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Integer topArticle(Integer id) throws Exception;

}
