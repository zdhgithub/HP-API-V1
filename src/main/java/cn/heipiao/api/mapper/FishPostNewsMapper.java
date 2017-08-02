/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishPostNews;

/**
 * @author wzw
 * @date 2016年6月14日
 * @version 1.0
 */
public interface FishPostNewsMapper {

	List<FishPostNews> selectByMap(Map<String ,Object> map);
	
	List<FishPostNews> selectAll(Map<String,Object> params);
	
	void insertPojo(FishPostNews pojo);
	

	void deletePojo(Long nid);


	/**
	 * @param map
	 * @return
	 */
	List<FishPostNews> selectByPutFish(Map<String, Object> map);
}
