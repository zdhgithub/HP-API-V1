/**
 * 
 */
package cn.heipiao.api.resources.cp;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.constant.FishSiteConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.AccountBill;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.PartnerSiteRateConfig;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.FishSiteResource;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.RegionService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年11月30日
 * @version 2.2
 */
//@Path("cp/sites")
//@Produces({ MediaType.APPLICATION_JSON})
//@Consumes({ MediaType.APPLICATION_JSON})
//@Component
@Api(tags = "钓场管理模块")
@RestController
@RequestMapping(value = "cp/sites",produces="application/json")
public class CpSiteResource {

	private static final Logger logger = LoggerFactory
			.getLogger(CpSiteResource.class);
	
	@Resource
	private FishSiteService fishSiteService;
	
	@Resource
	private UserOpService userService;

	@Resource
	private RegionService regionService;
	
	@Resource
	private AccountBillService accountBillService;
	@Resource
	private PartnerService partnerService;
	
	@Resource
	private FishSiteResource fishSiteResource;
	/**
	 * {"uid":1,"siteId":12321}
	 * 
	 * 
	 * 指定塘主
	 * @param json
	 * @return
	 * 
	 * @version 2016/7/25
	 * @修改说明 指定塘主需要在员工表添加一条数据
	 */
//	@PUT
//	@Path("authentication")
	@RequestMapping(value = "authentication",method = RequestMethod.PUT,consumes = "application/json")
	public String authFishSite(@RequestBody JSONObject json) {
		try {
			Long uid = json == null ? null : json.getLong("uid");
			Integer siteId = json == null ? null : json.getInteger("siteId");
			if (uid == null || siteId == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			User u = userService.queryUserById(uid);
			if (u == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.no_such_user, RespMessage
								.getRespMsg(Status.no_such_user)));
			}
			FishSite pojo = fishSiteService.selectAllByUid(uid);
			if (pojo != null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.user_exists_fish_site, RespMessage
								.getRespMsg(Status.user_exists_fish_site)));
			// 用户指定到钓场
			pojo = new FishSite();
			pojo.setFishSiteId(siteId);
			pojo.setUid(uid);
			// 可购票
			pojo.setIsBuyTicket(1);
			// 钓场认证类型
			pojo.setAuthType(1);
			int rs = fishSiteService.updateSiteUid(pojo);
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * cp
	 * 钓场主变更 
	 * 
	 * {"oldUid":123,"newUid":456}
	 * 
	 * oldUid:原钓场主uid
	 * newUid:新钓场主uid
	 * 
	 * @return
	 */
//	@PUT
//	@Path("change")
	@RequestMapping(value = "change",method = RequestMethod.PUT,consumes = "application/json")
	public String changeFishSite(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Long oldUid = json == null ? null : json.getLong("oldUid");
			Long newUid = json == null ? null : json.getLong("newUid");
			if(oldUid == null || newUid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishSiteService.changeFishSite(oldUid,newUid);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 审核钓场
	 * 
	 * { "siteId": 102323, "status": 1}
	 * 
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("/site/check")
	@RequestMapping(value = "site/check",method = RequestMethod.PUT,consumes = "application/json")
	public String editCheck(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Integer siteId = json == null ? null : json.getInteger("siteId");
			Integer status = json == null ? null : json.getInteger("status");
			// Integer uid = json.getInteger("uid");
			if (siteId == null || status == null)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			FishSite pojo = new FishSite();
			pojo.setFishSiteId(siteId);
			pojo.setStatus(status);
			// pojo.setUid(uid);
			fishSiteService.updateSite(pojo);
			if (status == 3 || status == 1) {
				regionService.selectAllAndCount(ApiConstant.fishEntityType.TYPE_MENU_REGIONS_SITE);
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * cp
	 * 
	 * 修改星标
	 * 
	 * { "siteId": 102323, "flag": 1 }
	 * 
	 * flag: 0代表未标星,1代表标星
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
//	@PUT
//	@Path("/site/flag")
	@RequestMapping(value = "site/flag",method = RequestMethod.PUT,consumes = "application/json")
	public String editFlag(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Integer siteId = json.getInteger("siteId");
			Integer flag = json.getInteger("flag");
			if (siteId == null || flag == null || flag != 1 && flag != 0)
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			FishSite pojo = new FishSite();
			pojo.setFishSiteId(siteId);
			pojo.setFlag(flag);
			fishSiteService.updateSite(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 新建钓场
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
//	@POST
//	@Path("/site/create")
	@RequestMapping(value = "site/create",method = RequestMethod.POST,consumes = "application/json")
	public String createFishSite(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FishSite pojo = json == null ? null : JSONObject.toJavaObject(json,
					FishSite.class);
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.zero_one);
			if (pojo == null || StringUtils.isBlank(pojo.getFishSiteName())
					|| StringUtils.isBlank(pojo.getFishSiteType())
					|| pojo.getRegionId() == null || pojo.getCityId() == null
					|| pojo.getProvinceId() == null
					|| StringUtils.isBlank(pojo.getAddr())
					|| pojo.getIsFree() == null
					|| !zo.contains(pojo.getIsFree())
					|| pojo.getLongitude() == null || pojo.getLongitude() < 0
					|| pojo.getLongitude() > 180 || pojo.getLatitude() == null
					|| pojo.getLatitude() < 0 || pojo.getLatitude() > 90) {
				return JSONObject.toJSONString(new RespMsg<String>(
						Status.value_is_null_or_error));
			}

			// User u = userService.queryUserById(pojo.getUid().longValue());
			// if(u == null)
			// return new
			// RespMsg<>(Status.no_such_user,RespMessage.getRespMsg(Status.no_such_user));
			Timestamp t = new Timestamp(ExDateUtils.getDate().getTime());
			pojo.setIsBuyTicket(FishSiteConstant.ZERO);
			pojo.setAuthType(FishSiteConstant.UNAUTHERIZED);
			pojo.setStatus(FishSiteConstant.ONLINE_CHECK);
			pojo.setFlag(FishSiteConstant.ZERO);
			pojo.setFocusCount(FishSiteConstant.ZERO);
			pojo.setCreateTime(t);
			pojo.setUpdateTime(t);
			if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {
				for (FishPond pond : pojo.getFishPonds()) {
					// 判断鱼塘中字段不为空的
					if (pond == null
							|| StringUtils.isBlank(pond.getFishPondName()))
						return JSONObject.toJSONString(new RespMsg<String>(
								Status.value_is_null_or_error));
					pond.setCreateTime(t);
					pond.setStatus(FishSiteConstant.ONE);
				}
			}

			int fishSiteId = fishSiteService.createSite(pojo);
			return JSONObject
					.toJSONString(new RespMsg<String>(fishSiteId + ""));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error));
		}
	}
	
	/**
	 * 指定钓场置顶
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/top")
//	@PUT
	@RequestMapping(value = "top",method = RequestMethod.PUT,consumes = "application/json")
	public String top(@RequestBody JSONObject json){
		try{
			logger.debug("json:{}", json);
			Integer fid = json == null ? null : json.getInteger("fid");
			if (fid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, 
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishSite pojo = fishSiteService.selectById(fid);
			if (pojo == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists, 
						RespMessage.getRespMsg(Status.fish_site_not_exists)));
			}
			Integer top = pojo.getTop();
			if(top == 0){
				top = fishSiteService.getMaxTop() + 1;
			}else{
				top = 0;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fishSiteId", fid);
			map.put("top", top);
			int rs = fishSiteService.setFishShopTop(map);
			if(rs == 1){
				return JSONObject.toJSONString(new RespMsg<>());
			}else{
				return JSONObject.toJSONString(new RespMsg<>(Status.UPDATE_DATABASE_FAIL, 
						RespMessage.getRespMsg(Status.UPDATE_DATABASE_FAIL)));
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * {"siteId":123,"uid":1,"isAuthCoupon":true}
	 * cp
	 * 授权优惠券发放功能
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("auth/coupon")
	@RequestMapping(value = "auth/coupon",method = RequestMethod.PUT,consumes = "application/json")
	public String isAuthCoupon(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Integer siteId = json == null ? null : json.getInteger("siteId");
			Long  uid = json == null ? null : json.getLong("uid");
			Boolean  isAuthCoupon = json == null ? null : json.getBoolean("isAuthCoupon");
			if(siteId == null || uid == null || isAuthCoupon == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishSiteService.modifyIsAuthCoupon(uid,siteId,isAuthCoupon);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * CP-钓场财务交易记录
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/account/{uid}/{status}/{type}/{searchfor}/{start}/{size}")
	@RequestMapping(value = "account/{uid}/{status}/{type}/{searchfor}/{start}/{size}",method = RequestMethod.GET)
	public String getSiteList(
			@PathVariable("uid") Long uid,@PathVariable("type")Integer type,
			@PathVariable("status") Integer status,@PathVariable("searchfor")String searchfor,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},start:{},size:{},type:{},status{},searchfor:{}",
					uid, start, size,type,status,searchfor);
			if(searchfor.equals("_")){
				searchfor=null;
			}
			 if(type==0){
				 type =null;
			 }
			 if(status == 0){
				 status = null;
			 }
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("type", type);
			map.put("status", status);
			map.put("searchfor", searchfor);
			List<AccountBill> total = accountBillService.getRecordBySite(map);
			map.put("start", (start - 1) * size);
			map.put("size", size);
			List<AccountBill> data = accountBillService.getRecordBySite(map);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", data);
			result.put("total", total.size());
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * cp 钓场财务列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/site/{regionId}/{searchfor}/{status}/{start}/{size}")
	@RequestMapping(value = "list/site/{regionId}/{searchfor}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getSiteList(@PathVariable("regionId") Integer regionId,
			@PathVariable("start") Integer start,
			@PathVariable("searchfor") String searchfor,
			@PathVariable("status") Integer status,
			@PathVariable("size") Integer size ) {
		try {
			if(searchfor.equals("_")){
				searchfor=null;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("searchfor", searchfor);
			param.put("regionId", regionId == null ? 0: regionId);
			param.put("status", status == null ? 0: status);
			List<AccountExtSite> list = accountBillService
					.selectListExt(param);
			start = (start - 1) * size;
			param.put("start", start);
			param.put("size", size);
			List<AccountExtSite> results = accountBillService
					.selectListExt(param);
			int total = list.size();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", results);
			map.put("total", total);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}
	/**
	 * 用户漂币交易记录
	 * 
	 * @param uid
	 * @param start
	 * @param size
	 * @param source
	 * @return
	 */
//	@GET
//	@Path("/list/{uid}/{status}/{type}/{searchfor}/{start}/{size}")
	@RequestMapping(value = "list/{uid}/{status}/{type}/{searchfor}/{start}/{size}",method = RequestMethod.GET)
	public String getUserList(@PathVariable("uid") Integer uid,
			@PathVariable("status") Integer status, @PathVariable("type") Integer type,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size,
			@PathVariable("searchfor") String searchfor) {
		try {
			logger.debug("uid:{},status:{},searchfor:{},type:{},start:{},size:{}", uid,status, searchfor,
					type,start,size);
			if(searchfor.equals("_")){
				searchfor=null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid== 0 ? null :uid);
			map.put("status", status == 0 ? null: status);
			map.put("searchfor", searchfor);
			map.put("type", type == 0 ? null:type);
			List<AccountBill> list= accountBillService.selectRecordByUid(map);
			map.put("start", start = (start - 1) * size);
			map.put("size", size);
			List<AccountBill> results= accountBillService.selectRecordByUid(map);
			int total = list.size();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", results);
			result.put("total", total);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}
	
	/**
	 * 用户财务列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
	
//	@Path("/useraccount/{status}/{searchfor}/{start}/{size}")
//	@GET
	@RequestMapping(value = "useraccount/{status}/{searchfor}/{start}/{size}",method = RequestMethod.GET)
	public String getUserAccountList(@PathVariable("start") Integer start,
			@PathVariable("status")Integer status,
			@PathVariable("searchfor") String searchfor,
			@PathVariable("size") Integer size) {
		logger.info("status:{},searchfor:{},start:{},size:{}",status,searchfor,start,size);
		try {
			if(searchfor.equals("_")){
				searchfor=null;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("status", status);
			param.put("searchfor", searchfor);
			List<AccountExt> pojos = accountBillService.selectList(param);
			param.put("start", (start - 1) * size);
			param.put("size", size);
			List<AccountExt> results = accountBillService.selectList(param);
			int total = pojos.size();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", results);
			map.put("total", total);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject
					.toJSONString(new RespMsg<AccountExt>(Status.error));
		}
	}

	/**
	 * 获取合伙人抽成奖配置
	 * 
	 */
//	@Path("/partner/rate/config/{siteId}/{partnerId}")
//	@GET
	@RequestMapping(value="/partner/rate/config/{siteId}/{partnerId}",method = RequestMethod.GET )
	public String selectRateConfig(@PathVariable("siteId") Integer siteId,
			@PathVariable("partnerId") Integer partnerId){
		logger.debug("siteId:{},partnerId:{}",siteId,partnerId);
		if(siteId == null&&partnerId==null){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
		}
		PartnerSiteRateConfig partnerSiteRateConfig=fishSiteService.selectSiteRateConfig(siteId,partnerId);
		return JSONObject.toJSONString(new RespMsg<PartnerSiteRateConfig>(partnerSiteRateConfig));
	}
	/**
	 * 添加、修改合伙人抽成奖配置
	 */
//	@Path("/partner/rate/config")
//	@PUT
	@RequestMapping(value="/partner/rate/config",method = RequestMethod.PUT,consumes="application/json" )
	public String updateRateConfig(@RequestBody JSONObject json){
		logger.debug("json:{}",json);
		Integer siteId = json.getInteger("siteId");
		Integer partnerId = json.getInteger("partnerId");
		Double siteRate = json.getDouble("siteRate");
		
		if(siteId ==null || partnerId == null){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
		}
		fishSiteService.updateSiteRateConfig(siteId,partnerId,siteRate);
		return JSONObject.toJSONString(new RespMsg<>(Status.success));
	}
	/**
	 * 平台收回钓场
	 */
//	@Path("/partner/moving/{partnerId}/{siteId}")
//	@PUT
	@RequestMapping(value="/partner/moving/{partnerId}/{siteId}",method = RequestMethod.PUT,consumes="application/json" )
	public String removePartnerSite(@PathVariable("partnerId")Integer partnerId,@PathVariable("siteId")Integer siteId){
		try {
			int rs = partnerService.removePartnerSite(partnerId,siteId);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
	
	/**
	 * cp 根据钓场状态查询列表
	 * @param status  钓场状态
	 * @param regionId 为空传0
	 * @param category 为空传0
	 *            1:省,2:市,3:区
	 * @param likeName 模糊搜索   为空传_
	 * @param  order  排序 为空传_
	 * 			_create:desc/asc    创建时间排序 降序/升序
				_praise:asc/desc  点赞排序 升序/降序
				_collect:asc/desc 收藏排序 升序/降序
   
	 * @param start
	 * @param size
	 */
	@RequestMapping(value = "status/{status}/{category}/{regionId}/{likeName}/{order}/{start}/{size}",method = RequestMethod.GET)
	public String selectStatus(@PathVariable("status") Integer status,
			@PathVariable("category") Integer category,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("likeName") String likeName,
			@PathVariable("order") String order,
			@PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try{
			logger.debug("status:{},category:{},regionId:{},likeName:{},order:{},start:{},size:{}",
					status,category,regionId,likeName,order,start,size);
			
		return fishSiteResource.selectStatus(status,category,regionId,likeName, order,start, size);
		} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
}
