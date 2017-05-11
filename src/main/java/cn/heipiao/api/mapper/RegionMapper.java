/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Region;

/**
 * @author wzw
 * @date 2016年6月22日
 * @version 1.0
 */
public interface RegionMapper {

	List<Region> selectAll();
	
	List<Region> selectByPid();

	List<Region> selectAllAndFishSiteCount(@Param("category")Integer category);

	List<Region> selectCitys();

	List<Region> selectAllAndFishShopCount(@Param("category")Integer category);
	/**
	 * 获取招募合伙人的城市
	 * @param regionRecruit 招募状态 0表示停止招募 1表示招募中
	 * @return
	 */
	List<Region> selectRecruitAll(int regionRecruit);
	/**
	 * 关闭/开启招募合伙人的城市
	 */
	int updateRecruit(@Param("regionId")Integer regionId,@Param("status")Integer status);
	/**
	 * 获取对合伙人开发的区域
	 */
	List<Region> selectOutsideRegion();
	/**删除对合伙人开发的区域*/
	int updateOutsideRecruit(Integer regionId);
	/**根据区域id查询*/
	Region selectByRegionId(Integer regionId);
	/**查询所有的省份*/
	List<Region> allRegion();
}
