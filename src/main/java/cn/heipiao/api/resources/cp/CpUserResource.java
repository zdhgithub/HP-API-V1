/**
 * 
 */
package cn.heipiao.api.resources.cp;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerRewardConfig;
import cn.heipiao.api.pojo.PartnerShopRewardReview;
import cn.heipiao.api.pojo.PartnerSiteRewardReview;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.RewardPlatform;
import cn.heipiao.api.pojo.RewardPolicy;
import cn.heipiao.api.pojo.TabRelavance;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.PartnerApplyService;
import cn.heipiao.api.service.PartnerClaimService;
import cn.heipiao.api.service.PartnerDBService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.RegionService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.RewardService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * @author wzw
 * @date 2016年11月30日
 * @version 2.2
 */
@Api(tags = "用户管理模块")
@RestController
@RequestMapping(value = "cp/users",produces="application/json")
public class CpUserResource {

	private static final Logger logger = LoggerFactory
			.getLogger(CpUserResource.class);
	
	
	@Resource
	private UserOpService userOpService;
	
	@Resource
	private PartnerApplyService partnerApplyService;

	@Resource
	private PartnerService partnerService;
	
	@Resource
	private PartnerDBService partnerDBService;
	
	@Resource
	private PartnerClaimService partnerClaimService;
	
	@Resource
	private RewardService rewardService;
	@Resource
	private RewardPlatformService rewardPlatformService;
	@Resource
	private RegionService regionService;
	
	/**
	 * cp 用户认证 {"uid":1,"realname":"张三","idcard":"610342xxxxxxxxxxxx"}
	 * 
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("user/authUser")
	@RequestMapping(value="user/authUser",method = RequestMethod.PUT,consumes="application/json" )
	public String authUser(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}", json);
			Long uid = json == null ? null : json.getLong("uid");
			String realname = json == null ? null : json.getString("realname");
			String idcard = json == null ? null : json.getString("idcard");
			if (uid == null || realname == null || idcard == null) {
				JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			User u = new User();
			u.setId(uid);
			u.setRealname(realname);
			u.setIdcard(idcard);
			userOpService.authUser(u);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 贴（去）标签
	 * 
	 * @param username
	 * @param labels
	 * @return
	 * @since 1.0
	 */
//	@Path("modify/label")
//	@PUT
	@RequestMapping(value="modify/label",method = RequestMethod.PUT,consumes="application/json" )
	public String modifyLabel(@RequestBody JSONObject jsonObject) {
		if ( jsonObject == null) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		logger.debug("json:{}", jsonObject.toString());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", (String) jsonObject.get("userId"));
		params.put("labels", (String) jsonObject.get("labels"));
		params.put("op", "2");
		try {
			userOpService.modifyUser(params);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<User>(null));
	}
	
	
	@RequestMapping(value="modify/{type}/{uid}",method=RequestMethod.GET)
	public String getUserModify(@ApiParam(value = "类型 label:贴标签,black:黑名单",required=true)@PathVariable("type")String type,
			@ApiParam(value = "用户id",required=true)@PathVariable("uid")Integer uid){
		try {
			logger.debug("type:{},uid:{}",type,uid);
			if(type==null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			List<TabRelavance> list = userOpService.queryUserLabel(uid.toString(), type);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 拉黑/取消拉黑
	 * 
	 * @param username
	 * @param blackLabels
	 * @return
	 * @since 1.0
	 */
//	@Path("modify/black")
//	@PUT
	@RequestMapping(value="modify/black",method = RequestMethod.PUT,consumes="application/json" )
	public String modifyBlack(@RequestBody JSONObject jsonObject) {
		if ( jsonObject == null) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		logger.debug("json:{}", jsonObject.toJSONString());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", (String) jsonObject.get("userId"));
		params.put("blackLabels", (String) jsonObject.get("blackLabels"));
		params.put("op", "3");
		try {
			userOpService.modifyUser(params);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<User>(null));
	}

	/**
	 * 修改user备注
	 * 
	 * @param username
	 * @param remark
	 * @return
	 * @since 1.0
	 */
//	@Path("modify/remark")
//	@PUT
	@RequestMapping(value="modify/remark",method = RequestMethod.PUT,consumes="application/json" )
	public String modifyUserRemark(@RequestBody JSONObject jsonObject) {
		if ( jsonObject == null) {
			return JSONObject.toJSONString(new RespMsg<User>(
					Status.value_is_null_or_error, RespMessage
							.getRespMsg(Status.value_is_null_or_error)));
		}
		Long userId = jsonObject.getLong("userId");
		String platformRemarks = jsonObject.getString("remark");
		logger.debug("userId:{},platFormRemarks:{}", userId,platformRemarks);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("platformRemarks", platformRemarks);
		params.put("op", "1");
		try {
			userOpService.modifyUser(params);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
		return JSONObject.toJSONString(new RespMsg<User>(null));
	}
	
	/**
	 * 审核申请(PC)
	 * 
	 * @param
	 * @return
	 */
//	@PUT
//	@Path("partner/check")
	@RequestMapping(value="partner/check",method = RequestMethod.PUT,consumes="application/json" )
	public String checkPartner(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			if ( jsonObject == null || jsonObject.getInteger("regionId") == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			Map<String, Object> params = new HashMap<String, Object>();
			// 需要添加合伙人区域ID
			params.put("regionId", jsonObject.getInteger("regionId"));
			params.put("id", jsonObject.getLong("uid"));
			params.put("status", jsonObject.getInteger("status"));
			int result = partnerApplyService.checkApply(params);
			return JSONObject.toJSONString(new RespMsg<Integer>(result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
	
	/**
	 * 添加合伙人
	 * 
	 * @param username
	 * @return
	 * @since 1.0
	 */
//	@POST
//	@Path("partner/add")
	@RequestMapping(value="partner/add",method = RequestMethod.PUT,consumes="application/json" )
	public String grantPartner(@RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);

			Integer uid = jsonObject.getInteger("uid");
			Integer region = jsonObject.getInteger("region");
			if (uid == null || region == null) {
				return JSONObject.toJSONString(new RespMsg<String>(
						Status.value_is_null_or_error));
			}
			Partner partner = new Partner();
			partner.setId(uid.longValue());
			partner.setpRegionId(region);
			partner.setpCreateTime(ExDateUtils.getDate());
			int result = partnerService.savePartner(partner);
			if (result != Status.exist_partner) {
				return JSONObject
						.toJSONString(new RespMsg<String>(result + ""));
			}
			return JSONObject.toJSONString(new RespMsg<Integer>(result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
	
	
	/**
	 * 修改合伙人信息
	 * 
	 * @param partner
	 * @return
	 * @since 1.0
	 */
//	@Path("partner/modification")
//	@PUT
	@RequestMapping(value="partner/modification",method = RequestMethod.PUT,consumes="application/json" )
	public String modifyPartnerInfo( @RequestBody JSONObject jsonObject) {
		try {
			logger.debug("json:{}", jsonObject);
			Partner pojo = jsonObject == null ? null : JSONObject.toJavaObject(
					jsonObject, Partner.class);
			if (pojo == null || pojo.getId() == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			partnerService.modifyPartner(pojo);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	
	/**
	 * cp
	 * 解除合伙人关系 
	 * partnerId:1
	 * @return
	 */
//	@DELETE
//	@Path("partner/remove/{partnerId}")
	@RequestMapping(value="partner/remove/{partnerId}",method = RequestMethod.DELETE)
	public String removePartner(@PathVariable("partnerId")Integer partnerId){
		try {
			int rs = partnerService.deletePartner(partnerId);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	
	/**
	 * 解除合伙人名下的某个店铺
	 * partnerId:1,
	 * shopId:322131
	 * @return
	 */
//	@DELETE
//	@Path("partner/shop/remove/{partnerId}/{shopId}")
	@RequestMapping(value="partner/shop/remove/{partnerId}/{shopId}",method = RequestMethod.DELETE)
	public String removePartnerShop(@PathVariable("partnerId")Integer partnerId,@PathVariable("shopId")Long shopId){
		try {
			int rs = partnerService.removePartnerShop(partnerId,shopId);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<String>(Status.error));
		}
	}
	
	
	/**
	 * 解除合伙人名下的某个钓场
	 * partnerId:1,
	 * siteId:322131
	 * @return
	 */
//	@DELETE
//	@Path("partner/site/remove/{partnerId}/{siteId}")
	@RequestMapping(value="partner/site/remove/{partnerId}/{siteId}",method = RequestMethod.DELETE)
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
	 * CP审核合伙人认领钓场
	 * @param siteId 钓场id
	 * @param status 审核状态   0表示不用过 1 表示通过
	 * 合伙人认领钓场之后，cp审核，通过，钓场签约状态变为已售票认领 （合伙人认领完通过之后，会默认获得售票认领资格），不通过变为可认领状态
	 */
//	@Path("partner/site/shelves/{siteId}/{status}")
//	@PUT
	@RequestMapping(value="partner/site/shelves/{siteId}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public 	String shelvesReward(@PathVariable("siteId")Integer siteId,
			@PathVariable("status")Integer status){
		try {
			logger.debug("siteId:{},status:{}",siteId,status);
			if(siteId==null){
				return JSONObject.toJSONString(
						new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			int rs = partnerClaimService.updateSiteIdSign(siteId,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * cp审核合伙人认领钓场发放奖金
	 */
//	@Path("partner/site/shelves")
//	@POST
	@RequestMapping(value="partner/site/shelves",method = RequestMethod.POST,consumes="application/json" )
	public 	String shelvesReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			if(json.getInteger("siteId")==null 
					||json.getInteger("shelvesAmount")==null
					||json.getInteger("status")==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			Integer siteId = json.getInteger("siteId");
			Integer shelvesAmount = json.getInteger("shelvesAmount");
			Integer status = json.getInteger("status");
			int rs = partnerClaimService.updateSiteId(siteId,shelvesAmount,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * CP审核合伙人售票认领钓场
	 * @param siteId 钓场id
	 * @param status 审核状态   0表示不用过 1 表示通过
	 * 合伙人认领钓场之后，cp审核，通过，钓场签约状态变为签约状态 不用过，则变为售票认领状态，同时已售票认领失败的合伙人不可以再次认领该钓场 
	 */
//	@Path("partner/site/trading/{siteId}/{status}")
//	@PUT
	@RequestMapping(value="partner/site/trading/{siteId}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public 	String tradingReward(@PathVariable("siteId")Integer siteId,
			@PathVariable("status")Integer status){
		try {
			logger.debug("siteId:{},status:{}",siteId,status);
			if(siteId==null){
				return JSONObject.toJSONString(
						new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			int rs = partnerClaimService.updateSiteIdSignTrad(siteId,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * cp审核合伙人钓场售票认领发放奖金
	 */
//	@Path("partner/site/trading")
//	@POST
	@RequestMapping(value="partner/site/trading",method = RequestMethod.POST,consumes="application/json" )
	public 	String tradingReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			if(json.getInteger("siteId")==null 
					||json.getInteger("tradingAmount")==null
					||json.getInteger("status")==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			Integer siteId = json.getInteger("siteId");
			Integer tradingAmount = json.getInteger("tradingAmount");
			Integer status = json.getInteger("status");
			int rs = partnerClaimService.updateTradingSiteId(siteId,tradingAmount,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * CP审核合伙人认领渔具店
	 * @param shopId 渔具店id
	 * @param status 审核状态   0表示不用过 1 表示通过
	 * 合伙人认领渔具店之后，cp审核，通过，钓场签约状态变为已售票认领（合伙人认领通过之后，默认获得售票认领资格） ，不通过变为可认领状态
	 */
//	@Path("partner/shop/shelves/{shopId}/{status}")
//	@PUT
	@RequestMapping(value="partner/shop/shelves/{shopId}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public 	String shelvesRewardShopTrand(@PathVariable("shopId")Long shopId,
			@PathVariable("status")Integer status){
		try {
			logger.debug("shopId:{},status:{}",shopId,status);
			if(shopId==null){
				return JSONObject.toJSONString(
						new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			int rs = partnerClaimService.updateShopIdSign(shopId,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	/**
	 * cp审核合伙人认领渔具店发放奖金
	 */
//	@Path("partner/shop/shelves")
//	@POST
	@RequestMapping(value="partner/shop/shelves",method = RequestMethod.POST,consumes="application/json" )
	public 	String shopShelvesReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			if(json.getLong("shopId")==null 
					||json.getInteger("shelvesAmount")==null
					||json.getInteger("status")==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			Long shopId = json.getLong("shopId");
			Integer shelvesAmount = json.getInteger("shelvesAmount");
			Integer status = json.getInteger("status");
			int rs = partnerClaimService.updateShopId(shopId,shelvesAmount,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	/**
	 * CP审核合伙人售票认领渔具店
	 * @param shopId 渔具店id
	 * @param status 审核状态   0表示不用过 1 表示通过
	 * 合伙人认领渔具店之后，cp审核，通过，钓场签约状态变为已签约，不通过变为售票认领状态，
	 * 对于售票认领失败的合伙人，不可以再售票认领给渔具店
	 */
//	@Path("partner/shop/trading/{shopId}/{status}")
//	@PUT
	@RequestMapping(value="partner/shop/trading/{shopId}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public 	String trandingRewardShop(@PathVariable("shopId")Long shopId,
			@PathVariable("status")Integer status){
		try {
			logger.debug("shopId:{},status:{}",shopId,status);
			if(shopId==null){
				return JSONObject.toJSONString(
						new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			int rs = partnerClaimService.updateShopIdSignTrand(shopId,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	/**
	 * cp审核合伙人渔具店售票认领发放奖金
	 */
//	@Path("partner/shop/trading")
//	@POST
	@RequestMapping(value="partner/shop/trading",method = RequestMethod.POST,consumes="application/json" )
	public 	String shopTradingReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			if(json.getLong("shopId")==null 
					||json.getInteger("tradingAmount")==null
					||json.getInteger("status")==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			Long shopId = json.getLong("shopId");
			Integer tradingAmount = json.getInteger("tradingAmount");
			Integer status = json.getInteger("status");
			int rs = partnerClaimService.updateTradingShopId(shopId,tradingAmount,status);
			return JSONObject.toJSONString(new RespMsg<Integer>(rs));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * cp获取钓场的合伙人返利奖励配置
	 */
//	@Path("reward/site/config/{siteId}")
//	@GET
	@RequestMapping(value="reward/site/config/{siteId}",method = RequestMethod.GET)
	public String findConfig(@PathVariable("siteId")Integer siteId){
		try {
			logger.debug("siteId:{}",siteId);
			if(siteId==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			PartnerSiteRewardReview siteReward = partnerClaimService.findSiteRewardConfig(siteId);
			return JSONObject.toJSONString(new RespMsg<>(siteReward));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	/**
	 * cp获取渔具店的合伙人返利奖励配置
	 */
//	@Path("reward/shop/config/{shopId}")
//	@GET
	@RequestMapping(value="reward/shop/config/{shopId}",method = RequestMethod.GET)
	public String findShopConfig(@PathVariable("shopId")Long shopId){
		try {
			logger.debug("shopId:{}",shopId);
			if(shopId==null){
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.value_is_null_or_error));
			}
			PartnerShopRewardReview shopReward = partnerClaimService.findShopRewardConfig(shopId);
			return JSONObject.toJSONString(new RespMsg<PartnerShopRewardReview>(shopReward));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	/**
	 * cp添加合伙人返利奖励配置（统配）
	 * 2.3暂时不用
	 */
//	@Path("reward/config")
//	@POST
	@RequestMapping(value="reward/config",method = RequestMethod.POST,consumes="application/json" )
	public String addConfigOfReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			if (json == null 
					|| json.getInteger("type") == null
					|| json.getInteger("regionId") == null
					|| json.getInteger("scale") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Integer type = json.getInteger("type");
			Integer regionId = json.getInteger("regionId");
			Integer scale = json.getInteger("scale");
			Integer groundingSum = json.getInteger("groundingSum");
			Integer transactionSum = json.getInteger("transactionSum");
			String interpellation = json.getString("interpellation");
			
			PartnerRewardConfig partnerRewardConfig = new PartnerRewardConfig();
			partnerRewardConfig.setGroundingSum(groundingSum);
			partnerRewardConfig.setInterpellation(interpellation);
			partnerRewardConfig.setRegionId(regionId);
			partnerRewardConfig.setTransactionSum(transactionSum);
			partnerRewardConfig.setScale(scale);
			partnerRewardConfig.setType(type);
			return ResultUtils.out(partnerService.insertConfig(partnerRewardConfig));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * CP修改合伙人返利配置（统配）
	 * 2.3暂时不用
	 * @param json
	 * @return
	 */
//	@Path("reward/config")
//	@PUT
	@RequestMapping(value="reward/config",method = RequestMethod.PUT,consumes="application/json" )
	public String updateConfigOfReward(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			if (json == null 
					|| json.getInteger("id") ==null
					|| json.getInteger("type") == null
					|| json.getInteger("regionId") == null
					|| json.getInteger("scale") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			PartnerRewardConfig partnerRewardConfig = new PartnerRewardConfig();
			partnerRewardConfig.setId(json.getInteger("id"));
			partnerRewardConfig.setGroundingSum(json.getInteger("groundingSum"));
			partnerRewardConfig.setInterpellation(json.getString("interpellation"));
			partnerRewardConfig.setRegionId(json.getInteger("regionId"));
			partnerRewardConfig.setTransactionSum(json.getInteger("transactionSum"));
			partnerRewardConfig.setScale(json.getInteger("scale"));
			partnerRewardConfig.setType(json.getInteger("type"));
			return ResultUtils.out(partnerService.updateConfig(partnerRewardConfig));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
		
	}
	/**
	 * cp删除合伙人返利配置（统配）
	 * 2.3暂时不用
	 */
//	@Path("reward/config/{id}")
//	@DELETE
	@RequestMapping(value="reward/config/{id}",method = RequestMethod.DELETE)
	public String deleteConfig(@PathVariable("id")Integer id){
		try {
			logger.debug("id:{}",id);
			if(id==null){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(partnerService.deleteConfig(id));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * cp查询合伙人返利配置（统配）
	 * 2.3暂时不用
	 */
//	@Path("reward/config/{type}")
//	@GET
	@RequestMapping(value="reward/config/{type}",method = RequestMethod.GET)
	public String selectConfig(@PathVariable("type")Integer type){
		try{
			logger.debug("type:{}",type);
			if(type==null){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			List<PartnerRewardConfig> list = partnerService.selectConfig(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * cp端查询平台奖励政策信息
	 * @param bid 
	 * @param title
	 * @param time
	 * 
	 */
//	@Path("policy/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value="policy/{type}/{start}/{size}",method = RequestMethod.GET)
	public String findRewardPolicyAll(@PathVariable("type")int type,@PathVariable("start")int start,@PathVariable("size")int size,@RequestParam("bid")long bid,@RequestParam("title")String title){
		try {
			logger.debug("type:{},size:{},start:{},bid:{},title",type,size,start,bid,title);
			List<RewardPolicy> list=rewardService.selectRewardPolicyList(type,start,size,bid,title);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * CP端添加平台奖励政策信息
	 */
//	@POST
//	@Path("policy")
	@RequestMapping(value="policy",method = RequestMethod.POST,consumes="application/json" )
	public String addRewardPolicy(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			if(json==null || json.getLong("bid") == null 
					|| json.getInteger("type") ==null
					|| StringUtils.isEmpty(json.getString("title")) 
					|| StringUtils.isEmpty(json.getString("content"))){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Integer result = rewardService.saveRewardPolicy(json);
			if (result == 0) {
				return ResultUtils.out(Status.success);
			} else {
				return ResultUtils.out(result + "");
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * cp端删除平台奖励政策信息
	 * @param
	 */
//	@Path("policy/{id}")
//	@DELETE
	@RequestMapping(value="policy/{id}",method = RequestMethod.DELETE,consumes="application/json" )
	public String deleteRewardPolicy(@PathVariable("id")Long id){
		if(id == null){
			return ResultUtils.out(Status.value_is_null_or_error);
		}
		Integer result = rewardService.delectRewardPolicy(id);
		if (result == 0) {
			return ResultUtils.out(Status.success);
		} else {
			return ResultUtils.out(result + "");
		}
	} 
	/**
	 * cp端更新平台奖励信息
	 */
//	@Path("repolicy")
//	@PUT
	@RequestMapping(value="repolicy",method = RequestMethod.PUT,consumes="application/json" )
	public String updateRewardPolicy(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			if(json==null || json.getLong("id") == null 
					|| StringUtils.isEmpty(json.getString("title")) 
					|| StringUtils.isEmpty(json.getString("content"))){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Integer result = rewardService.updateRewardPolicy(json);
			if (result == 0) {
				return ResultUtils.out(Status.success);
			} else {
				return ResultUtils.out(result + "");
			}
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				return ResultUtils.out(Status.error);
			}
	}
	/**
	 * cp端配置商家平台奖励配置
	 * 
	 */
//	@Path("configuration")
//	@POST
	@RequestMapping(value="configuration",method = RequestMethod.POST,consumes="application/json" )
	public String updateRewardConfig(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			if(json==null || json.getLong("bid") == null 
					|| json.getInteger("type")==null){
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			RewardPlatform rewardPlatform = new RewardPlatform();
			rewardPlatform.setBid(json.getLongValue("bid"));
			rewardPlatform.setBonus(json.getDoubleValue("bonus"));
			rewardPlatform.setPercent(json.getFloatValue("percent"));
			rewardPlatform.setPartnerBonus(json.getInteger("partnerBonus"));
			rewardPlatform.setType(json.getIntValue("type"));
			rewardPlatformService.insertRewardPlatform(rewardPlatform);
			return JSONObject.toJSONString(
					new RespMsg<>(Status.success,RespMessage.getRespMsg(Status.success)));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(
					new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * cp端获取商家平台奖励配置
	 */
//	@Path("configuration/{bid}/{type}")
//	@GET
	@RequestMapping(value="configuration/{bid}/{type}",method = RequestMethod.GET)
	public String insetRewardConfig(@PathVariable("bid")Long bid,@PathVariable("type")int type){
		try{
			logger.debug("bid:{},type:{}",bid,type);
				RewardPlatform rewardPlatform=rewardPlatformService.findRewardPlatformByBid(bid, type);
				return JSONObject.toJSONString(new RespMsg<RewardPlatform>(rewardPlatform));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * cp端开启(或者关闭)商家平台奖励
	 */
//	@Path("platform/management/{bid}/{type}/{status}")
//	@PUT
	@RequestMapping(value="platform/management/{bid}/{type}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public String updateRewardConfig(@PathVariable("bid")Long bid,
			@PathVariable("status")Integer status,
			@PathVariable("type")Integer type){
		try {
			rewardPlatformService.update(bid,status,type);
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
		
	}
	/**cp端指定钓场合伙人*/
	
	
//	@Path("/site/partner/{siteId}/{partnerId}")
//	@PUT
	@RequestMapping(value="/site/partner/{siteId}/{partnerId}",method = RequestMethod.PUT,consumes="application/json" )
	public String updateSitePartner(@PathVariable("siteId")Integer siteId,
			@PathVariable("partnerId")Integer partnerId){
		try {
			logger.debug("siteId:{},partnerId:{}",siteId,partnerId);
			if(siteId==null || partnerId == null ){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			//fishSiteService.
			return ResultUtils.out(Status.success);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 获取合伙人开放的区域
	 */
//	@GET
//	@Path("outside")
	@RequestMapping(value="outside",method = RequestMethod.GET )
	public String getRegionOutside() {
		try {
			List<Region> list = regionService.selectOutside();
			if(list==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.CITY_RECUIT_NOT_EXIST));
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	/**
	 * 获取正在招募的合伙人区域
	 * @return
	 */
//	@GET
//	@Path("/outside/recruit")
	@RequestMapping(value="/outside/recruit",method = RequestMethod.GET)
	public String getRegionRecruitAll() {
		try {
			List<Region> list = regionService.selectRecruitAll();
			if(list==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.CITY_RECUIT_NOT_EXIST));
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	/**关闭或开启对合伙人招募的城市
	 * @param status   0表示关闭  1表示开启
	 */
//	@PUT
//	@Path("recruit/{regionId}/{status}")
	@RequestMapping(value="recruit/{regionId}/{status}",method = RequestMethod.PUT,consumes="application/json" )
	public String getRegionRecruitAll(@PathVariable("regionId")Integer regionId,
			@PathVariable("status")Integer status) {
		try {
			logger.debug("regionId:{},status:{}",regionId,status);
			if(regionId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			regionService.upDateRecruit(regionId,status);
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	/**删除对合伙人开放的城市
	 * 暂时不用2.3
	 */
//	@PUT
//	@Path("outside/{regionId}")
	@RequestMapping(value="outside/{regionId}",method = RequestMethod.PUT,consumes="application/json" )
	public String getRegionOutside(@PathVariable("regionId")Integer regionId) {
		try {
			logger.debug("regionId:{}",regionId);
			if(regionId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			regionService.updateOutsideRecruit(regionId);
			return JSONObject.toJSONString(new RespMsg<>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	/**
	 * 查询所有的省份
	 * @return
	 * @since 2.3
	 * 
	 */
//	@Path("region")
//	@GET
	@RequestMapping(value="region",method = RequestMethod.GET)
	public String getPartnerResion() {
		try {
			return JSONObject.toJSONString(new RespMsg<List<Region>>(
					this.partnerService.getAllRegion()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 查询不是合伙人的用户列表
	 * 
	 * @param start
	 * @param size
	 * @return
	 * @since 2.3
	 */
//	@Path("/nopartner/{searchfor}/{start}/{size}/{orderRule:[a-zA-Z]*}")
//	@GET
	@RequestMapping(value="/nopartner/{searchfor}/{start}/{size}/{orderRule:[a-zA-Z]*}",method = RequestMethod.GET)
	public String queryUsers(@PathVariable("searchfor")String searchfor,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size,
			@PathVariable("orderRule") final String orderRule) {
		try {
			if(searchfor.equals("_")){
				searchfor=null;
			}
		List<User> userList = null;
		int userNum = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("searchfor", searchfor);
		userNum = userOpService.selectNotPartnerList(params).size();
		params.put("start", (start - 1) * size);
		params.put("size", size);
		params.put("orderRule", orderRule);
		userList = userOpService.selectNotPartnerList(params);
		Map<String, Object> result = new HashMap<String, Object>();
			result.put("data", userList);
			result.put("total", userNum);
			return JSONObject
					.toJSONString(new RespMsg<Map<String, Object>>(result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
	/**
	 * 添加合/修改伙人返利奖励配置
	 * @param  bid  商家id 钓场和渔具店
	 * @param type  类型   0表示渔具店 1表示钓场
	 * @param style  表示奖励类型   0上架奖  1正常交易奖
	 * @param amount   奖励金额（分）
	 */
//	@Path("/partnerReward")
//	@PUT
	@RequestMapping(value="/partnerReward",method = RequestMethod.PUT,consumes="application/json" )
	public String insertReward(@RequestBody JSONObject json) {
		try {
			logger.debug("json:{}",json);
			Long bid = json.getLong("bid");
			Integer amount = json.getInteger("amount");
			Integer type = json.getInteger("type");
			Integer style=  json.getInteger("style");
			if(bid==null ||amount==null||type==null||style==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			if(type==0){
				partnerClaimService.insertPartnerShopReward(bid,amount,style);
					return JSONObject
							.toJSONString(new RespMsg<Map<String, Object>>());
			}else{	
				partnerClaimService.insertPartnerSiteReward(bid.intValue(),amount,style);
				return JSONObject
						.toJSONString(new RespMsg<Map<String, Object>>());
			}
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<User>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}

	}
}