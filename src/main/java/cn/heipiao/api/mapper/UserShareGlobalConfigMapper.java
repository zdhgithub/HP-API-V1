/**
 * 
 */
package cn.heipiao.api.mapper;

/**
 * @author wzw
 * @date 2016年11月28日
 * @version 1.0
 */
public interface UserShareGlobalConfigMapper {

	Boolean selectStatus();
	
	void updatePojo(Boolean status);

	void insertPojo(Boolean status);
	
}
