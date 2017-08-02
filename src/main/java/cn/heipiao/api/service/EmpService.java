package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.ShopEmp;

/**
 * 说明 : 员工管理service 功能 : 添加员工
 * 
 * @author chenwenye
 * @since 2016-6-27 heipiao1.0
 */
public interface EmpService {

	/** 钓场主业务状态 **/
	int ROOT_USER = 2;

	/** 有效状态 **/
	int VALID = 1;

	/** 冻结状态 **/
	int UN_VALID = 0;

	/** 一级菜单父id,根模板 **/
	String ROOT_MOUDLE = "0";

	Emp selectById(Long userId);

	/**
	 * 作用: 根据电话号码查看用户是否已经注册钓场管理端
	 * 
	 * @param phone
	 * @return
	 */
	Integer findFishIdByPhone(String phone);

	/**
	 * 作用: 新添加员工 - 通过手机号码添加
	 * 
	 * @param emp
	 * @throws Exception
	 */
	boolean insertByPhone(Emp emp) throws Exception;

	/**
	 * 作用: 新添加钓场主 - 并且初始化所有权限
	 * 
	 * @param userId
	 *            用户Id
	 * @param fishSiteId
	 *            钓场Id
	 * @return
	 */
	boolean insert(String userId, String fishSiteId);

	/**
	 * 作用: 根据钓场Id查询员工列表
	 * 
	 * @param fishSiteId
	 *            钓场Id
	 * @return
	 */
	List<Emp> findList(String fishSiteId);

	/**
	 * 作用: 更新员工属性操作
	 * 
	 * @param emp
	 * @return
	 */
	boolean update(Emp emp);

	/**
	 * 作用: 删除某钓场员工 隐含业务:删除该员工的权限
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(String id) throws Exception;

	/**
	 * 作用: 启用员工
	 * 
	 * @param empId
	 * @return
	 */
	boolean allow(String empId);

	/**
	 * 作用: 禁用员工 隐含业务:删除该员工所拥有的权限
	 * 
	 * @param empId
	 * @return
	 */
	boolean notAllow(String empId) throws Exception;

	/**
	 * 作用: 电话号码+邮箱+用户名查询员工
	 * 
	 * @param params
	 * @return
	 */
	Emp findEmpByPhoneOrEmaileOrUserName(String params);

	/**
	 * 作用: 密码登录 - 传统登陆
	 * 
	 * @param param
	 * @param password
	 * @return
	 */
	Emp passowrdlogin(String param, String password);
	/**
	 * 作用: 根据参数查找用户
	 * 
	 * @param param
	 * @param password
	 * @return
	 */
	Emp findByUseId(int param);
	/**
	 * 查询渔具店店主和员工列表
	 * 
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	public List<ShopEmp> getShopEmps(Long shopId) throws Exception;

	/**
	 * 添加渔具店员工
	 * 
	 * @param uid
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	public Integer addShopEmp(String phone, Long shopId) throws Exception;

	/**
	 * 渔具店禁用或启用员工
	 * 
	 * @param status
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public Integer startOrForbidShopEmp(Integer status, Integer uid, Long shopId)
			throws Exception;

	/**
	 * 移除渔具店的员工
	 * 
	 * @param uid
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	public Integer removeShopEmp(Integer uid, Long shopId) throws Exception;

	/**
	 * @param id
	 */
	List<ShopEmp> selectShopEmpByUid(Long uid);

	/**
	 * @param uid
	 * @return
	 */
	List<Emp> selectSiteEmpByUid(Long uid);

	/**
	 * 指定渔具店店主
	 * 
	 * @param uid
	 * @param shopId
	 * @return
	 */
	public Integer addShopMan(Integer uid, Long shopId) throws Exception;

	/**
	 * @param fishSiteId
	 * @param newUid 
	 */
	void deleteAll(Integer fishSiteId);

	/**
	 * @param id
	 */
	void removeShopEmpAll(Long shopId);

}
