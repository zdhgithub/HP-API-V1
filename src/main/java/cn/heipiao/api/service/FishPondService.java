/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishPond;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
public interface FishPondService {

	/**
	 * @param siteId
	 * @return
	 */
	List<FishPond> selectBySiteId(Integer siteId);

	/**
	 * 
	 * @param pojo
	 */
	void  insertPojo(FishPond pojo);
	
	/**
	 * 
	 * @param pojo
	 */
	void updatePojo(FishPond pojo);
	
	/**
	 * 
	 * @param pojo
	 */
	int deletePojo(FishPond pojo);

	/**
	 * @param map
	 * @return
	 */
	List<FishPond> selectBySiteIdLimit(Map<String, Object> map);
	
}
