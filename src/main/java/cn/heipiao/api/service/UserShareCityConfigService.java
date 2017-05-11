/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.UserShareCityConfig;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
public interface UserShareCityConfigService {

	/**
	 * @param status
	 */
	void setShareStatus(Boolean status);

	/**
	 * @return
	 */
	Boolean getShareStatus();

	/**
	 * @param cityId
	 * @param status
	 */
	void modifyShareCity(UserShareCityConfig pojo);

	/**
	 * @param cityId
	 * @param status
	 */
	void addShareCity(UserShareCityConfig pojo);

	/**
	 * @param m 
	 * @return
	 */
	List<UserShareCityConfig> listShareCity(Map<String, Object> m);

	void deleteShareCity(Integer cityId);

	int countShareCity();
	
	
}
