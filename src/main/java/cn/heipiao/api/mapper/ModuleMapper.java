package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Module;

/**
 * 说明 : 菜单模板CRUD 
 * @author chenwenye
 * @since 2016-6-28  heipiao1.0
 */
public interface ModuleMapper extends BaseMapper<Module>{
	
	/**
	 * 
	 * @return
	 */
	Boolean isShopPrivilege(@Param("shopId")Long shopId, @Param("uid")Long uid, @Param("moudle")Integer moudle);
	
	
	/**
	 * 作用: 根据员工id和模板Id查询权限总数
	 * @param id
	 * @return
	 */
	<U> Module selectCountByEmpAndModule(U id);
	
	/**
	 * 作用: 插入员工模板关联表数据(单条记录)
	 * 		对应业务就是添加关联员工和模板的权限
	 * @param u 可以是javaBean也可以是map
	 * 			key:empId,moduleId;(员工id和模板id)
	 * @return
	 */
	<U> int insertEmpModuleRelevance(U pojo);
	
	/**
	 * 作用: 删除员工模板关联数据(多条记录)
	 * 		对应业务就是添加关联员工和模板的权限(包括子权限)
	 * @param u 可以是javaBean也可以是map
	 * 			key:empId,moduleId;(员工id和模板id)
	 * @return
	 */
	<U> int deleteEmpModuleRelevance(U id);
	/**
	 * 作用: 删除渔具店员工模板关联数据(多条记录)
	 * 		对应业务就是添加关联员工和模板的权限(包括子权限)
	 * @param u 可以是javaBean也可以是map
	 * 			key:empId,moduleId;(员工id和模板id)
	 * @return
	 */
	 int deleteShopEmpModuleRelevance(@Param("id")Integer id,@Param("shopId")Long shopId);
	
	/**
	 * 作用: 根据用户id删除去所有的权限
	 * @param id
	 * @return
	 */
	<U> int deleteModuleRelevancesByEmpId(U id);
	
	/**
	 * 作用: 查询该员工拥有的模板id
	 * @param empId
	 * @return
	 */
	<U> List<String> selectAllModuleIds(U empId);
	/**
	 * 作用: 查询渔具店该员工拥有的权限
	 * @param empId
	 * @return
	 */
	List<String> selectShopModuleIds(@Param("empId")Integer empId,@Param("shopId")Long shopId);
	
	/**
	 * 作用: 插入员工模板关联表数据(多条记录)
	 * 		对应业务就是添加关联员工和模板的权限
	 * @param u 可以是javaBean也可以是map
	 * 			key:empId,moduleId;(员工id和模板id)
	 * @return
	 */
	<U> int insertEmpModuleRelevances(U pojos);
	/**
	 * 渔具店模块的员工关联的模板，插入多条数据
	 * @param pojos
	 * @return
	 */
	<U> int insertShopEmpModuleRelevances(U pojos);
	
	/**
	 * 作用: 初始化用户所有权限
	 * @param userId 用户Id
	 * @return
	 */
	<U> int insertAllEmpModuleRelevance(U userId);
	
	List<String> selectShopEmpModules();


	/**
	 * @param siteId
	 */
	void deleteAllPermissionsBySiteId(List<Long> uids);


	/**
	 * @param shopId
	 */
	void deleteShopEmpAllModuleRelevance(Long shopId);
	/**
	 * 渔具店添加平台奖励权限
	 */
	int insertShopPlatform(Map<String,Object> map);
	/**删除渔具店平台奖励*/
	int deleteShopPlatformModule(Map<String,Object> map);
	/**添加钓场平台奖励权限*/
	int insertSitePlatform(Map<String,Object> map);
	/**删除钓场平台奖励权限*/
	int deleteSitePlatformModule(Map<String,Object> map);
	/**查询一个用户的渔具店摸个模块*/
	
	Module selectShopModuleofOneId(@Param("empId")Integer empId,@Param("shopId")Long shopId,@Param("module")Integer module);
	Module selectOneModuleId(@Param("empId")Integer empId,@Param("module")Integer module);
	
}