/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.Arrays;
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
import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;
import cn.heipiao.api.pojo.LikeSite;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.DistributionService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.LikeSiteService;
import cn.heipiao.api.service.ModuleService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.RegionService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExMapUtil;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
@Api(tags = "钓场模块")
@RestController
@RequestMapping(value = "sites",produces="application/json")
public class FishSiteResource {

	private static final Logger logger = LoggerFactory
			.getLogger(FishSiteResource.class);

	private static final Integer FLAG = 1;

	private static final Integer STATUS = 2;

	@Resource
	private FishSiteService fishSiteService;

	@Resource
	private UserOpService userService;

	@Resource
	private PartnerService partnerService;

	@Resource
	private RegionService regionService;
	
	@Resource
	private LikeSiteService likeSiteService;
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private DistributionService distributionService;

	
	/**
	 * 2.2
	 * 
	 * 钓场点赞
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("like")
	@RequestMapping(value = "like",method = RequestMethod.POST,consumes = "application/json")
	public String likeSite(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			LikeSite ls = json == null ? null : JSONObject.toJavaObject(json, LikeSite.class);
			if(ls == null || ls.getSiteId() == null || ls.getUid() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			ls.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			int rs = likeSiteService.likeSite(ls);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 2.2
	 * 
	 * 钓场取消点赞
	 * @param siteId
	 * @param uid
	 * @return
	 */
//	@DELETE
//	@Path("like/{siteId}/{uid}")
	@RequestMapping(value = "like/{siteId}/{uid}",method = RequestMethod.DELETE)
	public String unLikeSite(@PathVariable("siteId")Integer siteId,@PathVariable("uid")Long uid){
		try {
			LikeSite ls = new LikeSite();
			ls.setSiteId(siteId);
			ls.setUid(uid);
			likeSiteService.unLikeSite(ls);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 2.2
	 * 
	 * 获取点赞数量
	 * @param siteId
	 * @return
	 */
//	@GET
//	@Path("like/{siteId}/{uid}")
	@RequestMapping(value = "like/{siteId}/{uid}",method = RequestMethod.GET)
	public String countLikeSite(@PathVariable("siteId")Integer siteId,@PathVariable("uid")Long uid){
		try {
			int count = likeSiteService.countSite(siteId);
			LikeSite ls = likeSiteService.getLikeUser(siteId,uid);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("count", count);
			map.put("isLike", ls == null ? 0 : 1);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 2.1
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
	 * 2.1
	 * 
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
	 * 2.1
	 * 
	 * {"siteId":123,"uid":1}
	 * 开通优惠券发放功能
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("open/coupon")
	@RequestMapping(value = "open/coupon",method = RequestMethod.PUT,consumes = "application/json")
	public String openCoupon(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Integer siteId = json == null ? null : json.getInteger("siteId");
			Long  uid = json == null ? null : json.getLong("uid");
			if(siteId == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = fishSiteService.modifyOpenCoupon(uid,siteId);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 2.0
	 * 
	 * api
	 * 获取附近的钓场
	 * @return
	 */
//	@GET
//	@Path("/list/near/{uid}/{lng}/{lat}")
	@RequestMapping(value = "list/near/{uid}/{lng}/{lat}",method = RequestMethod.GET)
	public String getNearList( @PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat, @PathVariable("uid") Integer uid) {
		try {
			logger.debug("lng:{},lat:{},uid:{}", 
					lng, lat, uid);
			if (Math.abs(lng) > 180 || Math.abs(lat) > 90) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			double[] lls = ExMapUtil.getAround(lat, lng, 30 * 1000);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", 0);
			map.put("size", 5);
			map.put("lng", lng);
			map.put("lat", lat);
			map.put("minLng", lls[0]);
			map.put("minLat", lls[1]);
			map.put("maxLng", lls[2]);
			map.put("maxLat", lls[3]);
			map.put("uid", uid);
			List<FishSite> pojos = fishSiteService.queryList(map);
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	
	/**
	 * api
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/list/{uid}/{lng}/{lat}/{radius}/{start}/{size}")
	@RequestMapping(value = "list/{uid}/{lng}/{lat}/{radius}/{start}/{size}",method = RequestMethod.GET)
	public String getList(@PathVariable("start") Integer start,
			@PathVariable("size") Integer size, @PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat, @PathVariable("uid") Integer uid,
			@PathVariable("radius") Integer radius) {
		try {
			logger.debug("start:{},size:{},lng:{},lat:{},uid:{}", start, size,
					lng, lat, uid);
			if (Math.abs(lng) > 180 || Math.abs(lat) > 90) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			double[] lls = ExMapUtil.getAround(lat, lng, radius * 1000);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("size", size);
			map.put("lng", lng);
			map.put("lat", lat);
			map.put("radius", radius * 1000);
			map.put("minLng", lls[0]);
			map.put("minLat", lls[1]);
			map.put("maxLng", lls[2]);
			map.put("maxLat", lls[3]);
			map.put("uid", uid);
			List<FishSite> pojos = fishSiteService.queryList(map);
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	/**
	 * 通过城市ID查询钓场列表
	 * 
	 * @param cityId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/list/{uid}/{cityId}/{lat}/{lng}/{status}/{start}/{size}")
	@RequestMapping(value = "list/{uid}/{cityId}/{lat}/{lng}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getListByCity(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size,
			@PathVariable("cityId") Integer cityId,
			@PathVariable("status") Integer status, @PathVariable("lat") Double lat,
			@PathVariable("lng") Double lng) {
		try {
			logger.debug("uid:{},cityId:{},start:{},size:{},lat:{},lng:{}",
					uid, cityId, start, size, lat, lng);
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"请下载最新APP"));
//			Partner partner = partnerService.queryPartnerById(uid);
//			if (partner == null) {
//				return JSONObject.toJSONString(new RespMsg<Integer>(
//						Status.partner_not_exists));
//			}
//			Long partnerId = partner.getpId();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("start", (start - 1) * size);
//			map.put("size", size);
//			map.put("cityId", cityId);
//			map.put("signStatus", status == 4 ? null : status);
//			map.put("lat", lat);
//			map.put("lng", lng);
//			map.put("partnerId", partnerId);
//			List<FishSite> pojos = fishSiteService.queryListByCity(map);
//			for (FishSite fishSite : pojos) {
//				if(fishSite.getSignStatus()!=3){
//					fishSite.setTransactionSum(null);
//				}
//			}
//			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}

	/**
	 * 通过城市ID查询合伙人可认领，或已认领，售票认领的钓场列表
	 * 对于已售票认领失败的合伙人，该钓场不显示
	 * @param cityId
	 * @param start
	 * @param size
	 * @since 2.3 新接口
	 */
//	@GET
//	@Path("/list/signSite/{uid}/{cityId}/{lat}/{lng}/{status}/{start}/{size}")
	@RequestMapping(value="/list/signSite/{uid}/{cityId}/{lat}/{lng}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getSiteListByCity(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size,
			@PathVariable("cityId") Integer cityId,
			@PathVariable("status") Integer status, @PathVariable("lat") Double lat,
			@PathVariable("lng") Double lng) {
		try {
			logger.debug("uid:{},cityId:{},start:{},size:{},lat:{},lng:{}",
					uid, cityId, start, size, lat, lng);
			Partner partner = partnerService.queryPartnerById(uid);
			if (partner == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.partner_not_exists));
			}
			Long partnerId = partner.getpId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (start - 1) * size);
			map.put("size", size);
			map.put("cityId", cityId);
			map.put("signStatus", status == 5 ? null : status);
			map.put("lat", lat);
			map.put("lng", lng);
			map.put("partnerId", partnerId);
			List<FishSite> pojos = fishSiteService.queryListByCity(map);
			for (FishSite fishSite : pojos) {
				if(fishSite.getIsApply()!=null && 
						(fishSite.getIsApply()==1 || fishSite.getIsApply() ==4 || fishSite.getIsApply()==3 || fishSite.getIsApply()==2) ){
					fishSite.setSignStatus(fishSite.getIsApply());
				}
			}
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	
	/**
	 * 查询合伙人的已签约钓场
	 * 
	 * @param cityId
	 * @param start
	 * @param size
	 * @return2.3
	 */
//	@GET
//	@Path("/list/{uid}/{start}/{size}")
	@RequestMapping(value = "list/{uid}/{start}/{size}",method = RequestMethod.GET)
	public String getListByPartner(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},start:{},size:{}", uid, start, size);
			Partner partner = partnerService.queryPartnerById(uid);
			List<FishSite> pojos =null;
			if (partner != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("start", (start - 1) * size);
				map.put("size", size);
				map.put("pid", partner.getpId());
				pojos = fishSiteService.queryListByPartner(map);
			}
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(
					pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	/**
	 * 查询合伙人的已签约或是已认领的钓场和渔具店
	 * 
	 * @param cityId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/list/sign/{uid}/{start}/{size}")
	@RequestMapping(value = "list/sign/{uid}/{start}/{size}",method = RequestMethod.GET)
	public String getListByPartnerSign(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},start:{},size:{}", uid, start, size);
			Partner partner = partnerService.queryPartnerById(uid);
			if (partner != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("start", (start - 1) * size);
				map.put("size", size);
				map.put("pid", partner.getpId());
				List<FSPojo> pojos = fishSiteService.selectListForPartnerSign(map);
				return JSONObject.toJSONString(new RespMsg<List<FSPojo>>(
						pojos));
			}
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		
	}

	/**
	 * 通过钓场id查询钓场
	 * 
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
//	@Path("/site/{siteId}")
//	@GET
	@RequestMapping(value = "site/{siteId}",method = RequestMethod.GET)
	public String getSite(@PathVariable("siteId") Integer siteId) {
		try {
			logger.debug("siteId:{}", siteId);
			FishSite fs = fishSiteService.selectById(siteId);
			if (fs == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.fish_site_not_exists, RespMessage
								.getRespMsg(Status.fish_site_not_exists)));
			}
			// return new RespMsg<FishSite>(fs);
			return JSONObject.toJSONString(new RespMsg<FishSite>(fs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// return new
			// RespMsg(Status.error,RespMessage.getRespMsg(Status.error));
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 用户uid查询钓场
	 * 
	 * @param uid
	 * @return
	 */
//	@GET
//	@Path("user/{uid}")
	@RequestMapping(value = "user/{uid}",method = RequestMethod.GET)
	public String getSiteByUid(@PathVariable("uid") Long uid) {
		try {
			logger.debug("uid:{}", uid);
			FishSite pojo = fishSiteService.selectByUid(uid);
			if (pojo == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.fish_site_not_exists, RespMessage
								.getRespMsg(Status.fish_site_not_exists)));
			}
			return JSONObject.toJSONString(new RespMsg<FishSite>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取钓场列表
	 * 
	 * @param uid
	 * @param category
	 * @param regionId
	 * @param filterNames
	 * @param lng
	 * @param lat
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("{uid}/{category}/{regionId}/{filterNames}/{lng}/{lat}/{start}/{size}")
	@RequestMapping(value = "{uid}/{category}/{regionId}/{filterNames}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	public String siteList(@PathVariable("uid") Long uid,
			@PathVariable("category") Integer category,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("filterNames") String filterNames,
			// @PathVariable("fishSiteName") String fishSiteName,
			@PathVariable("lng") Double lng, @PathVariable("lat") Double lat,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {

		logger.debug(
				"uid:{},category:{},regionId:{},filterNames:{},lng:{},lat:{},start:{},size:{}",
				uid, category, regionId, filterNames, lng, lat, start, size);

		String[] s = filterNames.split(ApiConstant.SysConstant.DIVIDE_STR_0, 4);
		logger.debug(Arrays.toString(s));
		if (s.length < 4)
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		logger.debug("filterNames:{}", Arrays.toString(s));
		Map<String, Object> filterName = new HashMap<String, Object>();
		filterName.put("fishSiteType", StringUtils.isBlank(s[0]) ? null : "%"
				+ s[0] + "%");
		filterName.put("isFree", StringUtils.isBlank(s[1]) ? null : s[1]);
		filterName.put("authType", StringUtils.isBlank(s[2]) ? null : s[2]);
		// other暂时没有使用
		filterName.put("other", StringUtils.isBlank(s[3]) ? null : s[3]);
		filterName.put("start", start);
		filterName.put("size", size);
		filterName.put("lng", lng);
		filterName.put("lat", lat);
		filterName.put("uid", uid);
		filterName.put("category", category);
		filterName.put("regionId", regionId);
		// filterName.put("fishSiteName", "%" + fishSiteName + "%");

		List<FishSite> pojos = fishSiteService.siteSelectList(filterName);
		return JSONObject.toJSONString(new RespMsg<>(pojos));
	}

	/**
	 * 获取钓场列表
	 * 
	 * @param uid
	 * @param category
	 * @param regionId
	 * @param filterNames
	 * @param lng
	 * @param lat
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{uid}/{category}/{regionId}/{filterNames}/{lng}/{lat}/{start}/{size}")
	@RequestMapping(value = "list/{uid}/{category}/{regionId}/{filterNames}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	public String siteList2(@PathVariable("uid") Long uid,
			@PathVariable("category") Integer category,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("filterNames") String filterNames,
			// @PathVariable("fishSiteName") String fishSiteName,
			@PathVariable("lng") Double lng, @PathVariable("lat") Double lat,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {

		logger.debug(
				"uid:{},category:{},regionId:{},filterNames:{},lng:{},lat:{},start:{},size:{}",
				uid, category, regionId, filterNames, lng, lat, start, size);

		String[] s = filterNames.split(ApiConstant.SysConstant.DIVIDE_STR_0, 4);
		logger.debug(Arrays.toString(s));
		if (s.length < 4)
			return JSONObject.toJSONString(new RespMsg<>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		logger.debug("filterNames:{}", Arrays.toString(s));
		Map<String, Object> filterName = new HashMap<String, Object>();
		filterName.put("fishSiteType", StringUtils.isBlank(s[0]) ? null : "%"
				+ s[0] + "%");
		filterName.put("isFree", StringUtils.isBlank(s[1]) ? null : s[1]);
		filterName.put("authType", StringUtils.isBlank(s[2]) ? null : s[2]);
		// other暂时没有使用
		filterName.put("other", StringUtils.isBlank(s[3]) ? null : s[3]);
		filterName.put("start", start > 0 ? (start - 1) * size : 0);
		filterName.put("size", size);
		filterName.put("lng", lng);
		filterName.put("lat", lat);
		filterName.put("uid", uid);
		filterName.put("category", category);
		filterName.put("regionId", regionId);
		// filterName.put("fishSiteName", "%" + fishSiteName + "%");

		List<FishSite> pojos = fishSiteService.siteSelectList2(filterName);
		return JSONObject.toJSONString(new RespMsg<>(pojos));
	}

	/**
	 * 钓场筛选 和分页 使用 "\31"进行分割 钓场类型(fishSiteType) 钓场是否收费(isFree) 认证类型(authType)
	 * 其他:(other) 以上以\31分隔符，按照顺序分割
	 * 
	 * @param region
	 * @param isFree
	 * @param payType
	 * @param authType
	 * @return
	 */
//	@Path("/filter/{uid}/{filterNames}/{lng}/{lat}/{start}/{size}")
//	@GET
	@RequestMapping(value = "filter/{uid}/{filterNames}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	@Deprecated
	public String filterSite(@PathVariable("uid") Long uid,
			@PathVariable("filterNames") String filterNames,
			@PathVariable("lng") Double lng, @PathVariable("lat") Double lat,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("filter:{}", filterNames);

			String[] s = filterNames.split(
					ApiConstant.SysConstant.DIVIDE_STR_0, 4);
			logger.debug(Arrays.toString(s));
			if (s.length < 4)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));

			Map<String, Object> filterName = new HashMap<String, Object>();
			filterName.put("fishSiteType", StringUtils.isBlank(s[0]) ? null
					: "%" + s[0] + "%");
			filterName.put("isFree", StringUtils.isBlank(s[1]) ? null : s[1]);
			filterName.put("authType", StringUtils.isBlank(s[2]) ? null : s[2]);
			// other暂时没有使用
			filterName.put("other", StringUtils.isBlank(s[3]) ? null : s[3]);
			filterName.put("start", start);
			filterName.put("size", size);
			filterName.put("lng", lng);
			filterName.put("lat", lat);
			filterName.put("uid", uid);

			List<FishSite> list = fishSiteService.selectList(filterName);
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * api 通过区域查询钓场
	 * 
	 * @param category
	 * @param lng
	 * @param lat
	 * @param uid
	 * @param regionId
	 * @param start
	 * @param size
	 * @return
	 */
//	@Path("/region/{category}/{regionId}/{uid}/{lng}/{lat}/{start}/{size}")
//	@GET
	@Deprecated
	@RequestMapping(value = "region/{category}/{regionId}/{uid}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	public String filterByRegion(@PathVariable("category") Integer category,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("lng") Double lng, @PathVariable("lat") Double lat,
			@PathVariable("uid") Long uid, @PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("regionId:{}", regionId);
			Map<String, Object> filterName = new HashMap<String, Object>();
			filterName.put("regionId", regionId);
			filterName.put("category", category);
			filterName.put("lng", lng);
			filterName.put("lat", lat);
			filterName.put("uid", uid);
			filterName.put("start", start);
			filterName.put("size", size);
			List<FishSite> list = fishSiteService.selectByRegion(filterName);
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}

	/**
	 * cp
	 * 
	 * 通过区域查询钓场
	 * 
	 * @param regionId
	 * @param category
	 *            1:省,2:市,3:区
	 * @param start
	 * @param size
	 * @return
	 */
//	@Path("/region/{category}/{regionId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "/region/{category}/{regionId}/{start}/{size}",method = RequestMethod.GET)
	public String filterByRegion(@PathVariable("category") Integer category,
			@PathVariable("regionId") Integer regionId,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("regionId:{}", regionId);
			Map<String, Object> filterName = new HashMap<String, Object>();
			filterName.put("regionId", regionId);
			filterName.put("category", category);
			filterName.put("start", (start - 1) * size);
			filterName.put("size", size);
			List<FishSite> list = fishSiteService.selectByRegionCp(filterName);

			Map<String, Object> rm = new HashMap<String, Object>();
			rm.put("data", list);
			rm.put("total",
					list.size() <= 0 ? 0 : list.size() < size ? list.size()
							: fishSiteService.selectByRegionCpTotal(filterName));
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(rm));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}


	/**
	 * 通过名称搜索钓场
	 * 
	 * @param fishSiteName
	 * @param uid
	 * @param lng
	 * @param lat
	 * @param start
	 * @param size
	 * @return
	 */
//	@Path("/like/{fishSiteName}/{uid}/{lng}/{lat}/{start}/{size}")
//	@GET
	@RequestMapping(value = "like/{fishSiteName}/{uid}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	public String likeSite(@PathVariable("fishSiteName") String fishSiteName,
			@PathVariable("uid") Long uid, @PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat, @PathVariable("start") Integer start,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("condition:{}", fishSiteName);
			if (StringUtils.isBlank(fishSiteName)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("likeName", "%" + fishSiteName + "%");
			map.put("lng", lng);
			map.put("lat", lat);
			map.put("uid", uid);
			map.put("start", start);
			map.put("size", size);
			List<FishSite> list = fishSiteService.likeByName(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * cp 通过名称和电话号码搜索钓场
	 * 
	 * @param condition
	 * @return
	 */
//	@Path("/like/{status}/{condition}/{start}/{size}")
//	@GET
	@RequestMapping(value = "like/{status}/{condition}/{start}/{size}",method = RequestMethod.GET)
	public String likeSite(@PathVariable("condition") String condition,
			@PathVariable("status") Integer status,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("condition:{}", condition);
			if (StringUtils.isBlank(condition)) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("likeName", "%" + condition + "%");
			map.put("status", status);
			map.put("start", (start - 1) * size);
			map.put("size", size);
			List<FishSite> list = fishSiteService.likeByNameAndPhone(map);
			Integer total = fishSiteService.selectCountByNameAndPhone(map);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", list);
			m.put("total", total);
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(m));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * cp
	 * 
	 * 查询星标钓场
	 * 
	 * @param flag
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Path("/flag/{flag}/{currentPage}/{size}")
//	@GET
	@RequestMapping(value = "flag/{flag}/{currentPage}/{size}",method = RequestMethod.GET)
	public String selectFlag(@PathVariable("flag") Integer flag,
			@PathVariable("currentPage") Integer currentPage,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("flag:{}", flag);
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.zero_one);
			if (flag == null || !zo.contains(flag) || currentPage <= 0)
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", flag);
			map.put("start", (currentPage - 1) * size);
			map.put("size", size);
			map.put("category", FLAG);
			List<FishSite> list = fishSiteService.selectByValue(map);
			Integer total = fishSiteService.selectCount(flag);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", list);
			m.put("total", total);
			return JSONObject.toJSONString(new RespMsg(m));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));

		}
	}
	
	/**
	 * cp 根据钓场状态查询列表
	 * 
	 * @param status
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Path("/status/{status}/{currentPage}/{size}")
//	@GET
	@RequestMapping(value = "status/{status}/{currentPage}/{size}",method = RequestMethod.GET)
	public String selectStatus(@PathVariable("status") Integer status,
			@PathVariable("currentPage") Integer currentPage,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("status:{}", status);
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.FISH_STATUS_STR);
			if (status == null || !zo.contains(status) || currentPage <= 0)
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", status);
			map.put("start", (currentPage - 1) * size);
			map.put("size", size);
			map.put("category", STATUS);
			List<FishSite> list = fishSiteService.selectByValue(map);
			Integer total = fishSiteService.selectCount(status);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", list);
			m.put("total", total);
			return JSONObject.toJSONString(new RespMsg(m));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * cp 根据钓场状态查询列表
	 * 
	 * @param status
	 * @return
	 */
//	@Path("/status/{status}/{currentPage}/{size}")
//	@GET
//	@RequestMapping(value = "status/{status}/{currentPage}/{size}",method = RequestMethod.GET)
	public String selectStatus(Integer status,Integer category,Integer regionId,String likeName,String order,Integer start,Integer size) {
		try {
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.FISH_STATUS_STR);
			if (status == null || !zo.contains(status) || start <= 0)
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("start", (start - 1) * size);
			map.put("size", size);
			map.put("category", category==0?null:category);
			map.put("regionId", regionId==0?null :regionId);
			map.put("likeName", likeName.equals("_")?null:"%"+likeName.substring(1)+"%");
			//处理显示排序分类
			if(!StringUtils.isBlank(order) && order.indexOf('_') == 0){
				order = order.trim().substring(1);
				if(order.length() > 0){
					String[] orderArray = order.split(",");
					for(int i=0; i<orderArray.length; i++){
						String[] oArray = orderArray[i].split(":");
						if(oArray.length == 2){
							map.put(oArray[0].trim(), oArray[1].trim().toUpperCase());
						}
					}
				}
			}

			List<FishSite> list = fishSiteService.selectBytheValue(map);
			Integer total = fishSiteService.selectSiteCount(map);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("data", list);
			m.put("total", total);
			return JSONObject.toJSONString(new RespMsg<>(m));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 编辑备注
	 * 
	 * { "siteId": 102323, "remark": "我是钓场" }
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
//	@Path("/remark")
//	@PUT
	@RequestMapping(value = "remark",method = RequestMethod.PUT,consumes = "application/json")
	public String editRemark(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Integer siteId = json.getInteger("siteId");
			String remark = json.getString("remark");
			if (siteId == null || StringUtils.isBlank(remark))
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			FishSite pojo = new FishSite();
			pojo.setFishSiteId(siteId);
			pojo.setSysRemarks(remark);
			pojo.setUpdateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			fishSiteService.updateSite(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	
	/**
	 * 合伙人添加钓场
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("/site/partner/create")
	@RequestMapping(value = "site/partner/create",method = RequestMethod.POST,consumes = "application/json")
	public String createFishSiteExt(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FishSiteExt pojo = json == null ? null : JSONObject.toJavaObject(json,
					FishSiteExt.class);
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.zero_one);
			if (pojo == null || StringUtils.isBlank(pojo.getFishSiteName())
					|| StringUtils.isBlank(pojo.getFishSiteType())
					|| StringUtils.isBlank(pojo.getAddr())
					|| pojo.getIsFree() == null
					|| !zo.contains(pojo.getIsFree())
					|| pojo.getLongitude() == null || pojo.getLongitude() < 0
					|| pojo.getLongitude() > 180 || pojo.getLatitude() == null
					|| pojo.getLatitude() < 0 || pojo.getLatitude() > 90) {
				return JSONObject.toJSONString(new RespMsg<String>(
						Status.value_is_null_or_error));
			}

			Timestamp t = new Timestamp(ExDateUtils.getDate().getTime());
			pojo.setIsBuyTicket(FishSiteConstant.ZERO);
			pojo.setAuthType(FishSiteConstant.UNAUTHERIZED);
			pojo.setStatus(FishSiteConstant.ONLINE_CHECK);
			pojo.setFlag(FishSiteConstant.ZERO);
			pojo.setFocusCount(FishSiteConstant.ZERO);
			pojo.setCreateTime(t);
			pojo.setUpdateTime(t);
			pojo.setCityId(110000);
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

			int fishSiteId = fishSiteService.createSiteByPartner(pojo);
			return JSONObject
					.toJSONString(new RespMsg<String>(fishSiteId + ""));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}

	}

	@SuppressWarnings("rawtypes")
//	@PUT
//	@Path("site/modification")
	@RequestMapping(value = "site/modification",method = RequestMethod.PUT,consumes = "application/json")
	public String editSite(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			FishSite pojo = json == null ? null : JSONObject.toJavaObject(json,
					FishSite.class);
			if (pojo == null || pojo.getFishSiteId() == null) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			List<Integer> zo = FishSiteConstant
					.getMaps(FishSiteConstant.zero_one);
			if (pojo.getIsFree() != null && !zo.contains(pojo.getIsFree())) {
				return JSONObject.toJSONString(new RespMsg(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			fishSiteService.updateSiteModify(pojo);
			return JSONObject.toJSONString(new RespMsg());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 查询不存在合伙人的钓场
	 *  cp使用，app不用
	 * @param likeName
	 * @param index
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/partnerIdIsNull/{likeName}/{index}/{size}")
	@RequestMapping(value = "list/partnerIdIsNull/{likeName}/{index}/{size}",method = RequestMethod.GET)
	public String getPartnerIdIsNullFishSite(
			@PathVariable("likeName") String likeName,
			@PathVariable("index") Integer index, @PathVariable("size") Integer size) {
		try {
			logger.info("like:{},index:{},size:{}", likeName, index, size);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("likeName",
					likeName.length() > 1 ? "%" + likeName.substring(1) + "%"
							: null);
			map.put("index", index);
			map.put("size", size);
			List<FishSite> list = fishSiteService
					.getPartnerIdIsNullFishSite(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 指定钓场设置图标logo
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
//	@Path("/logo")
//	@PUT
	@RequestMapping(value = "logo",method = RequestMethod.PUT,consumes = "application/json")
	public String logo(@RequestBody JSONObject json){
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
			
			fishSiteService.setLogo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 *B端  查询渔具店附近的钓场(10公里之内)
	 * @param regionId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("distribution/near/{regionId}/{lng}/{lat}/{start}/{size}")
	@RequestMapping(value = "distribution/near/{regionId}/{lng}/{lat}/{start}/{size}",method = RequestMethod.GET)
	public String addDistribution(@PathVariable("regionId") Integer regionId,
			@PathVariable("start")int start,
			@PathVariable("size")int size, 
			@PathVariable("lng") Double lng,
			@PathVariable("lat") Double lat){
		try {
			logger.debug("regionId:{},shopId:{},start:{},size:{},lat:{},lng:{}", 
					regionId,start, size,lat,lng);
			if (regionId == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("size", size);
			map.put("regionId", regionId);
			map.put("lat", lat);
			map.put("lng", lng);
			List<FishSite> pojos = fishSiteService.queryDistributionList(map);
			return JSONObject.toJSONString(new RespMsg<List<FishSite>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 *B端    删除渔具店与钓场配送关系
	 * @param shopId
	 * @param siteId
	 * @return
	 */
//	@DELETE
//	@Path("distribution/{shopId}/{siteId}")
	@RequestMapping(value = "distribution/{shopId}/{siteId}",method = RequestMethod.DELETE)
	public String deleteDistribution(@PathVariable("shopId")Long shopId,@PathVariable("siteId")Integer siteId){
		try {
			logger.debug("shopId:{},siteId:{}",shopId,siteId);
			if(shopId==null&&siteId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("siteId", siteId);
			distributionService.deleteDistribution(map);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 *B端     添加渔具店与钓场配送关系
	 * @param shopId
	 * @param siteId
	 * @return
	 */
//	@POST
//	@Path("distribution/{shopId}/{siteId}")
	@RequestMapping(value = "distribution/{shopId}/{siteId}",method = RequestMethod.POST,consumes = "application/json")
	public String insertDistribution(@PathVariable("shopId")Long shopId,@PathVariable("siteId")Integer siteId){
		try {
			logger.debug("shopId:{},siteId:{}",shopId,siteId);
			if(shopId==null&&siteId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("siteId", siteId);
			distributionService.insertDistribution(map);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 *B端     查询已支持配送的钓场
	 * @param shopId
	 * @return
	 */
//	@GET
//	@Path("distribution/{shopId}")
	@RequestMapping(value = "distribution/{shopId}",method = RequestMethod.GET)
	public String selectDistribution(@PathVariable("shopId")Long shopId){
		try {
			logger.debug("shopId:{},siteId:{}",shopId);
			if(shopId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			List<FishSite> list = distributionService.selectByShopId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
}