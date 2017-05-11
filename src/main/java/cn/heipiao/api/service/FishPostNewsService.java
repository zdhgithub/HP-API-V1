/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishPostNews;

/**
 * @author wzw
 * @date 2016年6月14日
 * @version 1.0
 */
public interface FishPostNewsService {

	
	List<FishPostNews> selectBySiteId(Map<String,Object> map);
	
	
	void insertPojo(FishPostNews pojo);
	
	
	void deletePojo(Long nid);


	/**
	 * @param map
	 * @return
	 */
	List<FishPostNews> selectBySiteIdAnd(Map<String, Object> map);


	/**
	 * @param map
	 * @return
	 */
	List<FishPostNews> selectByPutFish(Map<String, Object> map);
	
}
