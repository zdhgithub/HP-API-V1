package cn.heipiao.api.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.Module;

/**
 * 说明 : 查询菜单 功能 :
 * 
 * @author chenwenye
 * @since 2016-6- heipiao1.0
 */
public interface ModuleService {

	/** 管理模板 id **/
	String MANAGER = "4";

	/** 员工管理模板 id **/
	String EMP_MANAGER = "45";

	/**
	 * 查询该员工拥有的模板id
	 * 
	 * @param empId
	 * @return
	 */
	List<String> findAllModileIds(String empId);

	/**
	 * 查询渔具店员工拥有的权限
	 * 
	 * @param empId
	 * @return
	 */
	List<String> getShopModileIds(Integer uid, Long shopId) throws Exception;

	/**
	 * 作用: 根据Pid查询菜单列表
	 * 
	 * @param pId
	 *            菜单资源的pid
	 * @param empId
	 *            员工Id
	 * @return
	 */
	List<Module> findModules(String pId, String empId);

	/**
	 * 作用: 删除员工某模板权限(包括子模板的权限)
	 * 
	 * @param id
	 *            模板id
	 * @param empId
	 *            员工id
	 * @return
	 */
	boolean deletePermissions(String moduleId, String empId);

	/**
	 * 作用: 添加某员工模板权限(不包括子权限)
	 * 
	 * @param moduleId
	 * @param empId
	 * @return
	 */
	boolean addPermission(String moduleId, String empId);

	/**
	 * 作用: 删除员工所有的权限
	 * 
	 * @param empId
	 * @return
	 */
	boolean deleteAllPermissions(String empId) throws Exception;

	/**
	 * 作用: 删除渔具店员工所有的权限
	 * 
	 * @param empId
	 * @return
	 */
	// Integer deleteAllPermissionsForShop(Integer empId) throws Exception;

	/**
	 * 
	 * 作用: 刷新用户所拥有的权限 ps:将之前的权限删除,再插入新的权限数据
	 * 
	 * @param empId
	 * @param moduleIds
	 * @return
	 */
	boolean refreshAllPermissions(String empId, List<String> moduleIds);

	/**
	 * 作用: 批量授权员工权限 ps:
	 * 
	 * @param empId
	 * @param moduleIds
	 * @return
	 */
	boolean addPermissions(String empId, List<String> moduleIds);

	/**
	 * 添加渔具店模块的员工关联的模板
	 * 
	 * @param empId
	 * @param moduleIds
	 * @return
	 */
	Integer addPermissionsForShop(JSONObject json);

	/**
	 * 初始化渔具店店主权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer initShopEmpLimits(Integer uid,Long shopId) throws Exception;
	/**
	 * 初始化渔具店店主权限2
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer initLimits() throws Exception;
	
	public Integer initData() throws Exception;
	
	public Integer initData(Integer uid) throws Exception;
	
	public Integer initData(Long shopId,Integer type) throws Exception;
	
}
