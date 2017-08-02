package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.ModuleMapper;
import cn.heipiao.api.mapper.ShopEmpMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.ModuleService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 说明 : 员工service 功能 : 员工添加,更改,权限操作等
 * 
 * @author chenwenye
 * @since 2016-6-27 heipiao1.0
 */
@Service
public class EmpServiceImpl implements EmpService {

	// private static final Logger log =
	// LoggerFactory.getLogger(EmpServiceImpl.class);

	@Resource
	private EmpMapper empMapper;

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserOpService userOpService;

	@Resource
	private ModuleMapper moduleMapper;

	@Resource
	private ModuleService moduleService;

	@Resource
	private FishSiteMapper fishSiteMapper;

	@Resource
	private ShopEmpMapper<ShopEmp> shopEmpMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean insertByPhone(Emp emp) throws Exception {
		User user = userMapper.findByLoginParam(emp.getPhone()); // 获取注册用户
		if (user == null) {
			user = new User();
			user.setPhone(emp.getPhone());
			user.setNickname(user.getPhone());
			user.setUsername(user.getNickname());
			userOpService.save(user);
		}
		emp.setUserId(user.getId().intValue());
		emp.setCreateTime(ExDateUtils.getDate());
		emp.setEmpStatus(String.valueOf(VALID));
		empMapper.insert(emp);
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<Emp> findList(String fishSiteId) {
		Map<String, String> params = new LinkedMap<String, String>();
		params.put("fishSiteId", fishSiteId);
		return empMapper.selectList(params);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(Emp emp) { // 如果返回int不是大于零则更新失败
		return this.empMapper.updateById(emp) > 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delete(String id) throws Exception {

		if (fishSiteMapper.selectByUid(Long.valueOf(id)) != null) { // 钓场主不可删除
			return false;
		}

		return this.fishSiteMapper.selectByUid(Long.valueOf(id)) == null
				&& this.empMapper.deleteById(id) > 0
				&& moduleService.deleteAllPermissions(id); // 删除员工所有权限
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean allow(String empId) {
		Map<String, Object> pojo = new LinkedMap<String, Object>();
		pojo.put("userId", empId);
		pojo.put("empStatus", EmpService.VALID);
		return this.empMapper.updateById(pojo) > 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean notAllow(String empId) throws Exception {
		Map<String, Object> pojo = new LinkedMap<String, Object>();
		pojo.put("userId", empId);
		pojo.put("empStatus", EmpService.UN_VALID);// 设置用户状态为无效
		// 删除用户的所有权限
		return this.fishSiteMapper.selectByUid(Long.valueOf(empId)) == null
				&& this.empMapper.updateById(pojo) > 0
				&& this.moduleService.deleteAllPermissions(empId);
	}

	@Override
	public Integer findFishIdByPhone(String phone) {
		return this.empMapper.getFishSiteIdByPhone(phone);
	}

	@Override
	public Emp findEmpByPhoneOrEmaileOrUserName(String params) {
		Emp emp = this.empMapper.findEmpByParams(params);
		if (emp == null) {
			return emp;
		}
		if (emp.getEmpStatus().equals("1") || emp.getEmpStatus().equals("2")) {
			FishSite fs = fishSiteMapper.selectById(emp.getFishSiteId());
			emp.setFishSiteUid(fs.getUid());
			User master = userMapper.selectById(fs.getUid());
			emp.setFishSiteMasterRealname(master.getRealname()); // 获取钓场主真实姓名
			emp.setMasterPhone(master.getPhone());
			emp.setFishSiteName(fs.getFishSiteName());
		}
		return emp;
	}
	
	@Override
	public Emp findByUseId(int uid){
		Emp emp = this.empMapper.selectEmpByuid(uid);
		FishSite fs = fishSiteMapper.selectById(emp.getFishSiteId());
		emp.setFishSiteUid(fs.getUid());
		User master = userMapper.selectById(fs.getUid());
		emp.setFishSiteMasterRealname(master.getRealname()); // 获取钓场主真实姓名
		emp.setMasterPhone(master.getPhone());
		emp.setFishSiteName(fs.getFishSiteName());
		return emp;
	}
	@Override
	public Emp passowrdlogin(String param, String password) {
		// 获取能员工信息; id+fishSiteId+password
		Emp emp = this.empMapper.selectEmpAndPasswordByParams(param);
		if (emp == null) {
			return emp;
		}
		if (emp.getEmpStatus().equals("1") || emp.getEmpStatus().equals("2")) {
			FishSite fs = fishSiteMapper.selectById(emp.getFishSiteId());
			emp.setFishSiteUid(fs.getUid());
			User master = userMapper.selectById(fs.getUid());
			emp.setFishSiteMasterRealname(master.getRealname()); // 获取钓场主真实姓名
			emp.setMasterPhone(master.getPhone());
			emp.setFishSiteName(fs.getFishSiteName());
		}
		return emp;
	}

	@Override
	public boolean insert(String userId, String fishSiteId) {
		Emp emp = new Emp();
		emp.setUserId(Integer.valueOf(userId));
		emp.setFishSiteId(Integer.valueOf(fishSiteId));
		emp.setEmpStatus(String.valueOf(ROOT_USER));
		return this.empMapper.insert(emp) > 0
				&& moduleMapper.insertAllEmpModuleRelevance(userId) > 0;
	}

	@Override
	public Emp selectById(Long userId) {
		return empMapper.selectById(userId);
	}

	@Override
	public List<ShopEmp> getShopEmps(Long shopId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopId", shopId);
		return shopEmpMapper.selectList(params);
	}

	@Override
	@Transactional
	public Integer addShopEmp(String phone, Long shopId) throws Exception {
		User user = userMapper.findByLoginParam(phone); // 获取注册用户
		if (user == null) {
			user = new User();
			user.setPhone(phone);
			userOpService.save(user);
		}
		ShopEmp pojo = new ShopEmp(user.getId().intValue(), shopId);
		pojo.setStatus(ApiConstant.ShopEmpStatus.STAFF);
		shopEmpMapper.insert(pojo);
		return user.getId().intValue();
	}

	@Override
	@Transactional
	public Integer startOrForbidShopEmp(Integer status, Integer uid, Long shopId)
			throws Exception {
		this.shopEmpMapper.update(uid, shopId, status);
		if (status == 0) {
			this.moduleMapper.deleteShopEmpModuleRelevance(uid, shopId);
		}
		return uid;
	}

	@Override
	public Integer removeShopEmp(Integer uid, Long shopId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("shopId", shopId);
		ShopEmp emp = this.shopEmpMapper.selectOne(params);
		if (emp != null
				&& emp.getStatus() != ApiConstant.ShopEmpStatus.SHOPKEEPER) {
			this.shopEmpMapper.delete(uid, shopId);
			this.moduleMapper.deleteShopEmpModuleRelevance(uid, shopId);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.EmpService#selectShopEmpByUid(java.lang.Long)
	 */
	@Override
	public List<ShopEmp> selectShopEmpByUid(Long uid) {
		return shopEmpMapper.selectByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.EmpService#selectSiteEmpByUid(java.lang.Long)
	 */
	@Override
	public List<Emp> selectSiteEmpByUid(Long uid) {
		return empMapper.selectByUid(uid);
	}

	@Override
	@Transactional
	public Integer addShopMan(Integer uid, Long shopId) throws Exception {
		ShopEmp pojo = new ShopEmp(uid, shopId);
		pojo.setStatus(ApiConstant.ShopEmpStatus.SHOPKEEPER);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("shopId", shopId);
		ShopEmp se = shopEmpMapper.selectOne(params);
		if (se == null) {
			shopEmpMapper.insert(pojo);
			moduleService.initShopEmpLimits(uid, shopId);
		}
		return null;
	}

	/* (non-Javadoc)
	 * 删除钓场所有的员工
	 * @see cn.heipiao.api.service.EmpService#deleteAll(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void deleteAll(Integer siteId) {
		List<Long> uids = empMapper.selectUidsBySiteId(siteId);
		if(uids.size() > 0){
			moduleMapper.deleteAllPermissionsBySiteId(uids); // 删除员工所有权限
		}
		empMapper.deleteBySiteId(siteId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.EmpService#removeShopEmpAll(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void removeShopEmpAll(Long shopId) {
		moduleMapper.deleteShopEmpAllModuleRelevance(shopId);
		shopEmpMapper.deleteEmpByShopId(shopId);
	}

}
