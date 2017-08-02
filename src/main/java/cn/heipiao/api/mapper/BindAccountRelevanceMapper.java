/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.BindAccountList;

/**
 * @author wzw
 * @date 2016年8月16日
 * @version 1.0
 */
public interface BindAccountRelevanceMapper {

	BindAccountList selectUniqueAccount(@Param("uid")Long uid,@Param("platform")Integer platform,@Param("bindAccountNum")String bindAccount);
	
	void insertPojo(BindAccountList pojo);
	
	void deleteBindAccount(BindAccountList bal);
	
}
