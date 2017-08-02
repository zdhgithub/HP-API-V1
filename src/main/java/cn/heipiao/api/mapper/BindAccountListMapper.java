/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.BindAccountList;

/**
 * @author wzw
 * @date 2016年8月5日
 * @version 1.0
 */
public interface BindAccountListMapper {

	BindAccountList selectUniqueAccountAsLock(@Param("uid")Long uid,@Param("platform")Integer platform,@Param("bindAccountNum")String bindAccount);
	
	BindAccountList selectUniqueAccount(@Param("uid")Long uid,@Param("platform")Integer platform,@Param("bindAccountNum")String bindAccount);
	
	BindAccountList selectByAccountNumAndPlatform(@Param("bindAccountNum")String bindAccountNum,@Param("platform")Integer platform);
	
	List<BindAccountList> selectByUid(Long uid);
	
	void insertPojo(BindAccountList pojo);
	
	void updatePojo(BindAccountList pojo);

	/*void deleteBindAccount(BindAccountList bal);*/
	
}
