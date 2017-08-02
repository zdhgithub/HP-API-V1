package cn.heipiao.api.resources;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.ModuleService;
import cn.heipiao.api.utils.ExPropertyUtils;
import cn.heipiao.api.utils.ResultUtils;

/**
 * 说明 : 员工管理 api 功能 : a.调场的员工管理 b.员工引入
 * 
 * @author chenwenye
 * @since 2016-6-27 heipiao1.0
 */
//@Path("emps")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@Component
@RestController
@RequestMapping(value = "emps",produces="application/json")
public final class EmpResource {

	/** 开启权限 or 启用用户 **/
	private final static String ON_FLAG = "on";

	/** 关闭权限 or 禁用用户 **/
	private final static String OFF_FLAG = "off";
	
	public static int num = 0;

	/** 日志 **/
	private static final Logger log = LoggerFactory
			.getLogger(EmpResource.class);

	@Resource
	private EmpService empService;

	@Resource
	private ModuleService moduleService;

	@Resource
	private FishSiteService fishSiteService;

	/**
	 * 作用: 引入员工函数
	 * 
	 * @param pbone
	 * @return
	 */
//	@Path("add/{fishSiteId}/{phone}")
//	@PUT
	@RequestMapping(value = "add/{fishSiteId}/{phone}",method = RequestMethod.PUT,consumes = "application/json")
	public String addEmp(@PathVariable("fishSiteId") String fishSiteId,
			@PathVariable("phone") String phone) {
		log.debug("fishSiteId:{}", fishSiteId);
		log.debug("phone:{}", phone);
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(fishSiteId)) { // 电话号码为空
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}

		FishSite fs = fishSiteService.selectById(Integer.valueOf(fishSiteId));
		if (fs.getUid() == null) {
			return JSONObject.toJSONString(new RespMsg<>(
					Status.fish_site_not_auth));
		}

		Integer _fishSiteId = this.empService.findFishIdByPhone(phone); // 该号码对定的用户已经在钓场管理端注册
		if (_fishSiteId != null) {
			RespMsg<?> resp = fishSiteId.equals(String.valueOf(_fishSiteId)) ? new RespMsg<>(
					Status.HAS_BEEN_REGISTER_THIS,
					RespMessage.getRespMsg(Status.HAS_BEEN_REGISTER_THIS))
					: new RespMsg<>(Status.HAS_BEEN_REGISTER_OTHER,
							RespMessage
									.getRespMsg(Status.HAS_BEEN_REGISTER_OTHER));
			return JSONObject.toJSONString(resp);
		}

		Emp emp = new Emp();
		emp.setPhone(phone);
		emp.setFishSiteId(Integer.valueOf(fishSiteId));
		try {
			if (!empService.insertByPhone(emp)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.USER_NO_REGISTER, RespMessage
								.getRespMsg(Status.USER_NO_REGISTER)));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

		// TODO 将员工添加缓存

		return JSONObject.toJSONString(new RespMsg<>());
	}

	/**
	 * 作用: 查询某钓场下的员工列表
	 * 
	 * @param fishSiteId
	 * @return
	 */
//	@Path("empList/{fishSiteId}")
//	@GET
	@RequestMapping(value = "empList/{fishSiteId}",method = RequestMethod.GET)
	public String findEmpList(@PathVariable("fishSiteId") String fishSiteId) {
		log.debug("fishSiteId:{}", fishSiteId);
		return JSONObject.toJSONString(new RespMsg<>(empService
				.findList(fishSiteId)));
	}

	/**
	 * 作用: 一级菜单列表
	 * 
	 * @param empId
	 * @return
	 */
//	@Path("moduleList/{empId}")
//	@GET
	@RequestMapping(value = "moduleList/{empId}",method = RequestMethod.GET)
	public String findmoduleList(@PathVariable("empId") String empId) {
		log.debug("empId:{}", empId);
		return JSONObject.toJSONString(new RespMsg<>(this.moduleService
				.findModules(EmpService.ROOT_MOUDLE, empId)));
	}

	/**
	 * 作用: 二级菜单查询
	 * 
	 * @param moduleId
	 * @param empId
	 * @return
	 */
//	@Path("secondaryModules/{empId}/{moduleId}")
//	@GET
	@RequestMapping(value = "secondaryModules/{empId}/{moduleId}",method = RequestMethod.GET)
	public String findSecondaryMenu(@PathVariable("moduleId") String moduleId,
			@PathVariable("empId") String empId) {
		log.debug("moduleId:{}", moduleId);
		log.debug("empId:{}", empId);
		return JSONObject.toJSONString(new RespMsg<>(this.moduleService
				.findModules(moduleId, empId)));
	}

//	@Path("allModulesId/{empId}")
//	@GET
	@RequestMapping(value = "allModulesId/{empId}",method = RequestMethod.GET)
	public String findAllmoduleId(@PathVariable("empId") String empId) {
		log.debug("empId:{}", empId);
		return JSONObject.toJSONString(new RespMsg<>(this.moduleService
				.findAllModileIds(empId)));
	}

	/**
	 * 作用: 用户更新数据
	 * 
	 * @param empId
	 *            用户id
	 * @param fieldName
	 *            更新字段名称
	 * @param fieldValue
	 *            更新字段值
	 * @return
	 */
//	@Path("update/{empId}/{fieldName}")
//	@POST
	@RequestMapping(value = "update/{empId}/{fieldName}",method = RequestMethod.POST,consumes = "application/json")
	public String update(@RequestBody JSONObject fieldValue,
			@PathVariable("empId") String empId,
			@PathVariable("fieldName") String fieldName) {
		log.debug("empId:{}", empId);
		log.debug("fieldName:{}", fieldName);
		Emp emp = new Emp();
		emp.setUserId(Integer.valueOf(empId));
		try {
			ExPropertyUtils.setProperty(emp, fieldName,
					fieldValue.getString("value"));
		} catch (Exception e) {
			log.error("请求参数异常", e);
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}

		if (!empService.update(emp)) {
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}

		return JSONObject.toJSONString(new RespMsg<>());
	}

	/**
	 * 作用: 权限分配
	 * 
	 * @param empId
	 * @param moduleId
	 * @param flag
	 * @return
	 */
//	@Path("permission/{empId}/{moduleId}")
//	@POST
	@RequestMapping(value = "permission/{empId}/{moduleId}",method = RequestMethod.POST,consumes = "application/json")
	@Deprecated
	public String updateModulePermission(@RequestBody JSONObject flag,
			@PathVariable("empId") String empId,
			@PathVariable("moduleId") String moduleId) {
		log.debug("flag:{}", flag);
		log.debug("empId:{}", empId);
		log.debug("moduleId:{}", moduleId);
		String f = flag.getString("falg");
		boolean b1 = ON_FLAG.equals(f)
				&& this.moduleService.addPermission(moduleId, empId); // 执行开启权限
																		// 业务并且执行成功

		boolean b2 = OFF_FLAG.equals(f)
				&& this.moduleService.deletePermissions(moduleId, empId); // 执行关闭权限业务并且执行成功

		if (b1 || b2) { // 两个业务有一个执行成功
			return JSONObject.toJSONString(new RespMsg<>());
		}

		return JSONObject.toJSONString(new RespMsg<>(
				Status.EMP_PERMISSION_ERROR, RespMessage
						.getRespMsg(Status.EMP_PERMISSION_ERROR)));
	}

	/**
	 * 作用: 删除员工
	 * 
	 * @param empId
	 * @return
	 */
//	@Path("delete/{empId}")
//	@DELETE
	@RequestMapping(value = "delete/{empId}",method = RequestMethod.DELETE)
	public String deleteEmp(@PathVariable("empId") String empId) {
		log.debug("empId:{}", empId);
		boolean b1 = false;
		try {
			b1 = this.empService.delete(empId);
		} catch (Exception e) {
			log.error("员工删除失败", e);
		}

		if (!b1) {
			return JSONObject.toJSONString(new RespMsg<>(
					Status.DELETE_EMP_ERROR, RespMessage
							.getRespMsg(Status.DELETE_EMP_ERROR)));
		}

		return JSONObject.toJSONString(new RespMsg<>());
	}

	/**
	 * 作用: 禁用/启用员工
	 * 
	 * @param flag
	 * @param empId
	 * @return
	 */
//	@Path("allow/{empId}")
//	@POST
	@RequestMapping(value = "allow/{empId}",method = RequestMethod.POST,consumes = "application/json")
	public String allowEmp(@RequestBody JSONObject flag, @PathVariable("empId") String empId) {
		log.debug("flag:{}", flag);
		log.debug("empId:{}", empId);
		String f = flag.getString("flag");

		if (this.fishSiteService.selectByUid(Long.valueOf(empId)) != null) { // 钓场主不可启动和禁用所有状态
			return JSONObject.toJSONString(new RespMsg<>(
					Status.ALLOW_EMP_ERROR, RespMessage
							.getRespMsg(Status.ALLOW_EMP_ERROR)));
		}

		boolean b1 = ON_FLAG.equals(f) && this.empService.allow(empId);
		boolean b2 = false;
		try {
			b2 = OFF_FLAG.equals(f) && this.empService.notAllow(empId);
		} catch (Exception e) {
			log.error("禁用员工失败", e);
		}
		if (b1 || b2) { // 两个业务有一个执行成功
			return JSONObject.toJSONString(new RespMsg<>());
		}
		return JSONObject.toJSONString(new RespMsg<>(Status.ALLOW_EMP_ERROR,
				RespMessage.getRespMsg(Status.ALLOW_EMP_ERROR)));
	}

//	@Path("refresh/permissions/{empId}")
//	@POST
	@RequestMapping(value = "refresh/permissions/{empId}",method = RequestMethod.POST,consumes = "application/json")
	public String refreshAllPermissions(@RequestBody JSONObject params,
			@PathVariable("empId") String empId) {
		log.debug("params:{}", params);
		log.debug("empId:{}", empId);
		return this.moduleService.refreshAllPermissions(empId,
				Arrays.asList(params.getString("moduleIds").split(","))) ? JSONObject
				.toJSONString(new RespMsg<>()) : JSONObject
				.toJSONString(new RespMsg<>(Status.EMP_PERMISSION_ERROR,
						RespMessage.getRespMsg(Status.EMP_PERMISSION_ERROR)));

	}

	/**
	 * 查询渔具店的管理员或员工的权限
	 * 
	 * @param uid
	 * @return
	 * @since 2.0
	 */
//	@Path("shop/limits/{uid}/{shopId}")
//	@GET
	@RequestMapping(value = "shop/limits/{uid}/{shopId}",method = RequestMethod.GET)
	public String getShopEmpModules(@PathVariable("uid") final Integer uid,
			@PathVariable("shopId") final Long shopId) {
		log.debug("uid:{},shopId:{}", uid, shopId);
		try {
			List<String> result = this.moduleService.getShopModileIds(uid,
					shopId);
			return ResultUtils.out(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 修改渔具店的管理员或员工的权限
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("shop/refresh/limits")
//	@POST
	@RequestMapping(value = "shop/refresh/limits",method = RequestMethod.POST,consumes = "application/json")
	public String addShopEmpModules(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("empId") == null
					|| json.getLong("shopId") == null
					|| StringUtils.isEmpty(json.getString("moduleIds"))) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.moduleService.addPermissionsForShop(json));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 渔具店添加员工
	 * 
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("shop/emp")
//	@POST
	@RequestMapping(value = "shop/emp",method = RequestMethod.POST,consumes = "application/json")
	public String addShopEmp(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || StringUtils.isEmpty(json.getString("phone"))
					|| json.getLong("shopId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.empService.addShopEmp(json.getString("phone"),
					json.getLong("shopId"));
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 查询渔具店的员工列表
	 * 
	 * @param shopId
	 * @return
	 * @since 2.0
	 */
//	@Path("shop/{shopId}")
//	@GET
	@RequestMapping(value = "shop/{shopId}",method = RequestMethod.GET)
	public String getShopEmps(@PathVariable("shopId") final Long shopId) {
		try {
			if (shopId == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.empService.getShopEmps(shopId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 启用或禁用渔具店员工
	 * 
	 * @param json
	 * @return
	 */
//	@Path("shop/authority")
//	@PUT
	@RequestMapping(value = "shop/authority",method = RequestMethod.PUT,consumes = "application/json")
	public String startOrForbidShopEmp(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("status") == null
					|| json.getInteger("uid") == null
					|| json.getLong("shopId") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.empService.startOrForbidShopEmp(json.getInteger("status"),
					json.getInteger("uid"), json.getLong("shopId"));
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 移除渔具店的员工
	 * 
	 * @param json
	 * @return
	 */
//	@Path("shop/rm")
//	@PUT
	@RequestMapping(value = "shop/rm",method = RequestMethod.PUT,consumes = "application/json")
	public String removeShopEmp(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json.getLong("shopId") == null
					|| json.getInteger("uid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.empService.removeShopEmp(json.getInteger("uid"),
					json.getLong("shopId"));
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * @since 2.2
	 * @return
	 */
//	@Path("init")
//	@GET
//	@RequestMapping(value = "init",method = RequestMethod.GET)
//	public String init() {
//		try {
//			if(num==1) {
//				return ResultUtils.out("你已经初始化了一次，不能初始化第二次了");
//			}
//			num++;
//			return ResultUtils.out(this.moduleService.initLimits());
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			return ResultUtils.out(Status.error);
//		}
//	}
	/**
	 * @since 2.2
	 * @return
	 */
//	@Path("init/data")
//	@GET
//	@RequestMapping(value = "init/data",method = RequestMethod.GET)
//	public String initData() {
//		try {
//			return ResultUtils.out(this.moduleService.initData());
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			return ResultUtils.out(Status.error);
//		}
//	}
}
