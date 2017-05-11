package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishPond;

/**
 * 
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
public interface FishPondMapper {

	/**
	 * 
	 * @param fishSiteId
	 * @return
	 */
	List<FishPond> selectBySiteId(Integer fishSiteId);

	/**
	 * 
	 * @param pojos
	 */
	void updateBatchPojo(List<FishPond> pojos);

	/**
	 * 
	 * @param pojos
	 */
	void insertBatchPojo(List<FishPond> pojos);
	/**
	 * 
	 * @param pojos
	 */
	void insertBatchPojo2(List<FishPond> pojos);

	/**
	 * @param pojo
	 */
	void deletePojo(FishPond pojo);

	/**
	 * @param pojo
	 */
	void insertPojo(FishPond pojo);

	/**
	 * @param pojo
	 */
	void updatePojo(FishPond pojo);

	/**
	 * @param map
	 * @return
	 */
	List<FishPond> selectBySiteIdLimit(Map<String, Object> map);

	/**
	 * @param pid
	 * @return
	 */
	FishPond selectByPondId(Long pid);

}