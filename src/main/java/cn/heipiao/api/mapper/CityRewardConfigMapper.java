/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.CityRewardConfig;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public interface CityRewardConfigMapper {
	

	CityRewardConfig selectAsLockById(Integer cityId);
	
	
	CityRewardConfig selectById(Integer cityId);

	
	void insertPojo(CityRewardConfig pojo);
	
}
