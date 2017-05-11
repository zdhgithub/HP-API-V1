/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.UserShareCityConfig;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
public interface UserShareCityConfigMapper {

	UserShareCityConfig selectByCityId(Integer cityId);
	
	
	void updatePojo(UserShareCityConfig pojo);
	
	
	void insertPojo(UserShareCityConfig pojo);


	List<UserShareCityConfig> selectAll(Map<String, Object> m);


	void deleteShareCity(Integer cityId);


	int countShareCity();
}
