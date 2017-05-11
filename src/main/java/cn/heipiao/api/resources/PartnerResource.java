package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerApply;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.redis.RedisConfig;
import cn.heipiao.api.service.CacheService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.PartnerApplyService;
import cn.heipiao.api.service.PartnerClaimService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;

/**
 * @author zf
 * @version 1.0
 * @description 合伙人资源类
 * @date 2016年6月2日
 */
@Api(tags = "合伙人模块")
@RestController
@RequestMapping(value = "users/partner",produces="application/json")
public class PartnerResource {

	private static final Logger logger = LoggerFactory
			.getLogger(PartnerResource.class);

	@Resource
	private PartnerService partnerService;
	@Resource
	private PartnerApplyService partnerApplyService;

	@Resource
	private FishSiteService fishsiteService;
	
	@Resource
	private FishShopService fishShopService;

	@Resource
	private CacheService cacheService;
	
	@Resource
	private PartnerClaimService partnerClaimService;
	
	
	
	/**
	 * 通过user的ID查合伙人
	 * 
	 * @param partnerId
	 * @return
	 * @since 1.0
	 */

//	@Path("{userId}")
//	@GET
	@RequestMapping(value = "{userId}",method = RequestMethod.GET)
	public String queryPartner(@PathVariable("userId") final Integer userId) {
		try {
			Partner partner = partnerService.queryPartnerById(userId);
			return JSONObject.toJSONString(new RespMsg<Partner>(partner));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}

	/**
	 * cp根据昵称或手机号匹配查询合伙人
	 * 
	 * @param conditions
	 * @param start
	 * @param size
	 * @return
	 * @since 1.0
	 * 2.3修改过
	 */
//	@Path("like/{conditions}/{start}/{size}")
//	@GET
	@RequestMapping(value = "like/{conditions}/{start}/{size}",method = RequestMethod.GET)
	public String queryPartnerByPhoneOrName(
			@PathVariable("conditions")String conditions,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			logger.debug("conditions:{}", conditions);
			if(conditions.equals("-")){
				conditions=null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("likeName", conditions);
			List<Partner> results = partnerService
					.queryPartnerByPhoneOrNickname(map);
			map.put("start", (start-1)*size);
			map.put("size", size);
			List<Partner> data = partnerService
					.queryPartnerByPhoneOrNickname(map);
			int total = results.size();
			Map<String,Object> param = new HashMap<String, Object>(); 
			param.put("total", total);
			param.put("data", data);
			return JSONObject.toJSONString(new RespMsg<>(param));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}

	/**
	 * 根据合伙人管理区域查询合伙人
	 * 
	 * @param region
	 * @return
	 * @since 1.0
	 */
//	@Path("area/{region}/{start}/{size}")
//	@GET
	@RequestMapping(value = "area/{region}/{start}/{size}",method = RequestMethod.GET)
	public String queryPartnerByRegion(
			@PathVariable("region") final String region,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		try {
			logger.debug("region:{}", region);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("region", region);
			List<Partner> results = partnerService.queryPartnerByRegion(map);
			map.put("start", (start - 1) * size);
			map.put("size", size);
			List<Partner> data = partnerService.queryPartnerByRegion(map);
			int total = results.size();
			Map<String,Object> param = new HashMap<String, Object>(); 
			param.put("total", total);
			param.put("data", data);
			return JSONObject.toJSONString(new RespMsg<>(param));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 查询合伙人签约，已认领，已售票认领的钓场
	 * 
	 * @param start
	 * @param size
	 * @return
	 * @since1.0
	 * @since2.3 status  1表示已认领，2表示已签约,4表示已售票认领
	 */
//	@Path("fishsite/{partnerId}/{status}/{start}/{size}")
//	@GET
	@RequestMapping(value = "fishsite/{partnerId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String queryPartnerSite(@PathVariable("start") final Integer start,
			@PathVariable("status") final Integer status,
			@PathVariable("size") final Integer size,
			@PathVariable("partnerId") final Integer partnerId) {
		try {
			logger.debug("partnerId:{},status:{}", partnerId,status);
			Map<String, Object> filterName = new HashMap<String, Object>();
			filterName.put("partnerId", partnerId);
			filterName.put("status", status);
			List<FishSite> total = fishsiteService
					.getSitesByPartnerId(filterName);
			filterName.put("start", (start - 1) * size);
			filterName.put("size", size);
			List<FishSite> list = fishsiteService
					.getSitesByPartnerId(filterName);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", list);
			result.put("total", total.size());
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}

	
	/**
	 * 申请合伙人(APP)
	 * 
	 * @param
	 * @return
	 * @since 2.0
	 */
//	@POST
//	@Path("apply")
	@RequestMapping(value = "apply",method = RequestMethod.POST,consumes = "application/json")
	public String applyPartner(@RequestBody final JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			if ( jsonObject == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			PartnerApply javabean = JSONObject.parseObject(
					jsonObject.toJSONString(), PartnerApply.class);
			PartnerApply apply = partnerApplyService.getApply(javabean.getId());
			if (apply != null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.partner_apply_exists));
			}
			partnerApplyService.applyPartner(javabean);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
	
	/**
	 * 获取合伙人招募说明
	 */
//	@GET
//	@Path("recruit")
	@RequestMapping(value = "recruit",method = RequestMethod.GET)
	public String getPartnerRecruit(){
		String recruit=cacheService.get(RedisConfig.PARTNER_RECRUIT_INFO, RedisConfig.GLOBAL_CONFIG);
		return JSONObject.toJSONString(new RespMsg<String>(recruit));
	}
	
	/**
	 * 修改或添加合伙人招募说明
	 * @param jsonObj
	 * @return
	 */
//	@Path("recruit")
//	@POST
	@RequestMapping(value = "recruit",method = RequestMethod.POST,consumes = "application/json")
	public String updatePartnerRecruit(@RequestBody final JSONObject jsonObj){
		if (jsonObj == null) {
			return JSONObject.toJSONString(new RespMsg<Integer>(
					Status.value_is_null_or_error));
		}
		
		String recruit = jsonObj.getString("recruit");
		cacheService.put(RedisConfig.PARTNER_RECRUIT_INFO, recruit, 2);
		return JSONObject
				.toJSONString(new RespMsg<Integer>(Status.success)); 
	}

	/**
	 * 查询申请的合伙人列表(PC)
	 * 
	 * @param
	 * @return
	 * @since 2.0
	 */
//	@GET
//	@Path("applys/{status}/{start}/{size}")
	@RequestMapping(value = "applys/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getApplyPartners(@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size,
			@PathVariable("status") final Integer status) {
		try {
			logger.debug("start:{},size:{}", start, size);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", status);
			List<PartnerApply> total = partnerApplyService.getApplys(params);
			params.put("start", start);
			params.put("size", size);
			List<PartnerApply> data = partnerApplyService.getApplys(params);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", data);
			result.put("total", total.size());
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}

	/**
	 * 查询用户的合伙人身份(APP)
	 * 
	 * @param
	 * @return
	 * @since 2.0
	 */
//	@GET
//	@Path("identity/{uid}")
	@RequestMapping(value = "identity/{uid}",method = RequestMethod.GET)
	public String getPartnerIdentity(@PathVariable("uid") final Long uid) {
		try {
			logger.debug("uid:{}", uid);
			Partner partner = partnerService.selectByUid(uid);
			Map<String, Object> result = new HashMap<String, Object>();
			if (partner != null) {
				result.put("code", Status.partner_true);
				result.put("partnerId", partner.getpId());
				return JSONObject
						.toJSONString(new RespMsg<Map<String, Object>>(result));
			}
			PartnerApply apply = partnerApplyService.getApply(uid.intValue());
			if (apply == null) {
				result.put("code", Status.partner_not_exists);
				return JSONObject
						.toJSONString(new RespMsg<Map<String, Object>>(result));
			}
			if (ApiConstant.PartnerCheckStatus.CHECKING == apply.getStatus()) {
				result.put("code", Status.partner_checking);
				return JSONObject
						.toJSONString(new RespMsg<Map<String, Object>>(result));
			} else if (ApiConstant.PartnerCheckStatus.NO_PASS == apply
					.getStatus()) {
				result.put("code", Status.partner_false);
				return JSONObject
						.toJSONString(new RespMsg<Map<String, Object>>(result));
			}
			
			Partner p = partnerService.selectPartnerByUid(uid);
			if(!p.getIsExists()){
				return JSONObject.toJSONString(new RespMsg<>(Status.partner_dismissal));
			}
			return JSONObject.toJSONString(new RespMsg<String>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}

	/**
	 * 合伙人分配钓场
	 * 
	 * {"partnerId":12,"siteId":43}
	 * 
	 * 
	 * @param username
	 * @param siteId
	 * @return
	 * @since 1.0
	 */
//	@Path("fishsite")
//	@POST
	@RequestMapping(value = "fishsite",method = RequestMethod.POST,consumes = "application/json")
	public String distributeSite(@RequestBody final JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			Integer siteId = jsonObject.getInteger("siteId");
			Integer partnerId = jsonObject.getInteger("partnerId");
			if (siteId == null || partnerId == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			FishSite pojo = new FishSite();
			pojo.setFishSiteId(siteId);
			pojo.setPartnerId(partnerId);
			partnerService.distributePartnerSite(pojo);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}

	/**
	 * 合伙人认领钓场
	 * 
	 * @param partnerId
	 * @param siteId
	 * @return
	 * @since 2.0
	 */
//	@Path("fishsite")
//	@PUT
	@RequestMapping(value = "fishsite",method = RequestMethod.PUT,consumes = "application/json")
	public String claimSite(@RequestBody JSONObject json) {
		try {
			logger.debug("Json:{}", json);
			Integer partnerId = json.getInteger("partnerId");
			Integer siteId = json.getInteger("siteId");
			FishSite site = fishsiteService.selectById(siteId);
			if (site != null && site.getPartnerId() != null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.cannot_sign));
			}
//			int num = fishsiteService.countClaimSiteByPartner(partnerId);
//			if (num >= 3) {
//				return JSONObject.toJSONString(new RespMsg<Integer>(
//						Status.partner_out_of_claims));
//			}
			int rs = fishsiteService.updateByPartnerIdAndSiteId(partnerId, siteId,
					ApiConstant.SignStatus.CLAIMED);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * 合伙人钓场售票认领（对于售票认领失败的钓场，对该合伙人不显示)
	 * 
	 * @param partnerId
	 * @return
	 * @since 2.3
	 */
//	@Path("fishsite/ticketclaim")
//	@PUT
	@RequestMapping(value = "fishsite/ticketclaim",method = RequestMethod.PUT,consumes = "application/json")
	public String claimSiteTicket(@RequestBody JSONObject json) {
		try {
			logger.debug("Json:{}", json);
			Integer partnerId = json.getInteger("partnerId");
			Integer siteId = json.getInteger("siteId");
			
//			int num = fishsiteService.countClaimSiteByPartner(partnerId);
//			if (num >= 3) {
//				return JSONObject.toJSONString(new RespMsg<Integer>(
//						Status.partner_out_of_claims));
//			}
			int rs = fishsiteService.updateByPartnerOfTrading(partnerId, siteId,
					ApiConstant.SignStatus.TICK_CLAIMED);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * 查询合伙人的城市列表
	 * @return
	 * @since 2.0
	 * 2.3cp弃用
	 */
//	@Path("/region")
//	@GET
	@RequestMapping(value = "region",method = RequestMethod.GET)
	public String getPartnerResion() {
		try {
			return JSONObject.toJSONString(new RespMsg<List<Region>>(
					this.partnerService.getPaertnerRegion()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * cp使用 合伙人优惠券发放数量和面额的配置
	 * 
	 * {"couponSum":1,"couponFee":1,"partnerId":100}
	 * 
	 * @return
	 */
//	@PUT
//	@Path("config/coupon")
	@RequestMapping(value = "config/coupon",method = RequestMethod.PUT,consumes = "application/json")
	public String configCoupon(@RequestBody JSONObject json) {
		try {
			logger.info("json:{}", json);
			Integer couponSum = json == null ? null : json
					.getInteger("couponSum");
			Integer couponFee = json == null ? null : json
					.getInteger("couponFee");
			Integer partnerId = json == null ? null : json
					.getInteger("partnerId");
			if (couponFee == null || couponSum == null || partnerId == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			Partner p = new Partner();
			p.setpId(partnerId.longValue());
			p.setGivingOutCouponFee(couponFee);
			p.setGivingOutCouponSum(couponSum);
			partnerService.updateCouponFeeAndSum(p);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取合伙人的所有钓场
	 * 
	 * @param partnerId
	 * @param index
	 * @param size
	 * @return
	 * @since 1.0
	 */
//	@GET
//	@Path("list/fishSiteByPartner/{partnerId}/{index}/{size}")
	@RequestMapping(value = "list/fishSiteByPartner/{partnerId}/{index}/{size}",method = RequestMethod.GET)
	public String getFishSiteByPartner(
			@PathVariable("partnerId") Integer partnerId,
			@PathVariable("index") Integer index, @PathVariable("size") Integer size) {
		try {
			logger.info("partnerId:{},index:{},size:{}", partnerId, index, size);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("partnerId", partnerId);
			map.put("index", index);
			map.put("size", size);
			List<FishSite> list = partnerService.selectFishSiteByPartner(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 获取已经对合伙人开放钓场的城市
	 * 
	 * @param partnerId
	 * @param index
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@GET
//	@Path("region/open")
	@RequestMapping(value = "region/open",method = RequestMethod.GET)
	public String getOpenRegion() {
		try {
			List<Region> result = partnerService.getOpenRegion();
			return JSONObject.toJSONString(new RespMsg<List<Region>>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 获取已经对合伙人开放渔具店的城市
	 * 
	 * @param partnerId
	 * @param index
	 * @param size
	 * @return
	 * @since 2.0
	 */
//	@GET
//	@Path("region/open/sp")
	@RequestMapping(value = "region/open/sp",method = RequestMethod.GET)
	public String getOpenRegionSp() {
		try {
			List<Region> result = partnerService.getOpenRegionSp();
			return JSONObject.toJSONString(new RespMsg<List<Region>>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 认领渔具店
	 *   合伙人认领钓场，钓场变为已认领，等待cp审核
	 * @param json
	 * @return
	 * @since 2.0
	 */
//	@Path("shop/sign")
//	@POST
	@RequestMapping(value = "shop/sign",method = RequestMethod.POST,consumes = "application/json")
	public String signFishShop(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null || json.getLong("shopId") == null
					|| json.getLong("uid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			FishShop fs = this.fishShopService.getFishShopById(json.getLong("shopId"));
			if (fs == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_EXISTS);
			}
			return ResultUtils.out(this.partnerService.signShop(
					json.getLong("shopId"), json.getLong("uid")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 售票认领渔具店
	 *   合伙人售票认领之后，钓场变为已售票认领，等待cp审核
	 * @param json
	 * @return
	 * @since 2.3
	 */
//	@Path("shop/sign/ticket")
//	@POST
	@RequestMapping(value = "shop/sign/ticket",method = RequestMethod.POST,consumes = "application/json")
	public String ticketSignFishShop(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			if (json == null || json.getLong("shopId") == null
					|| json.getLong("uid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			FishShop fs = this.fishShopService.getFishShopById(json.getLong("shopId"));
			if (fs == null) {
				return ResultUtils.out(Status.FISH_SHOP_NOT_EXISTS);
			}
			return ResultUtils.out(this.partnerService.ticketSignShop(
					json.getLong("shopId"), json.getLong("uid")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 查询合伙人已认领，已签约，已售票认领的渔具店
	 * 
	 * @param start
	 * @param size
	 * @return  partnerId
	 * @since1.0
	 * @since2.3 status  1表示已认领，2表示已签约,4表示已售票认领
	 */
//	@Path("fishshop/{partnerId}/{status}")
//	@GET
	@RequestMapping(value = "fishshop/{partnerId}/{status}",method = RequestMethod.GET)
	public String queryPartnerShop(
			@PathVariable("status") final Integer status,
			@PathVariable("partnerId") final Integer partnerId) {
		try {
			logger.debug("partnerId:{},status:{}", partnerId,status);
			Map<String, Object> filterName = new HashMap<String, Object>();
			filterName.put("partnerId", partnerId);
			filterName.put("status", status);
			List<FishShop> list = fishShopService.selectFishShopByStatus(partnerId,status);
					
			return JSONObject.toJSONString(new RespMsg<>(
					list));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
}
