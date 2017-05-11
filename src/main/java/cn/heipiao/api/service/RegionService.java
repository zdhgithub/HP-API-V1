/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.MenuRegion;
import cn.heipiao.api.pojo.Region;

/**
 * @author wzw
 * @date 2016年6月22日
 * @version 1.0
 */
public interface RegionService {

	/**
	 * 
	 * @return
	 */
	List<Region> selectAll();
	/**
	 * 获取招募城市
	 */
	List<Region> selectRecruitAll();
	/**
	 * 
	 * @param type 
	 * @return
	 */
	 List<MenuRegion> selectAllAndCount(Integer type);

	/**
	 * @return
	 */
	List<Region> selectCitys();
	
	public Integer appointOpenCity(Integer cityId) throws Exception;
	
	public List<Region> getRegionStepByStep();
	/**
	 * 关闭/开启正在招募合伙人的城市
	 */
	int upDateRecruit(Integer regionId,Integer status);
	/**
	 * 获取对合伙人开发的城市
	 */
	List<Region> selectOutside();
	/**删除对合伙人开放 的城市*/
	int updateOutsideRecruit(Integer regionId);
}
