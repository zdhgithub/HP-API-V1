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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.ActivityArticle;
import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.CampaignDetail;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.ActivityArticleService;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.RefundActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 有关活动的功能：<br>
 * 创建活动、报名、抽签、活动列表、活动管理等
 * @author Chris
 * @version 3.0
 * @date 2017-03-03
 */
@Api(tags = "活动模块")
@RestController
@RequestMapping(value = "campaign",produces="application/json")
public class CampaignResource {
	
	public static final Logger logger = LoggerFactory.getLogger(CampaignResource.class);
	
	@Resource
	private CampaignService campaignService;
	
	@Resource
	private RefundActivityService refundActivityService;
	
	@Resource
	private ActivityArticleService activityArticleService; 
	

	/**
	 * 获取活动信息
	 * FIXME 活动详情分隔
	 * @param id 活动id
	 * @return
	 */
//	@GET
//	@Path("{id}")
	@RequestMapping(value="{id}",method = RequestMethod.GET )
	public String getCampaign(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			Campaign campaign = campaignService.getCampaign(id);
			return JSONObject.toJSONString(new RespMsg<>(campaign));
		} catch (Exception e) {
			logger.error("获取活动信息失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取活动参与人信息
	 * @param uid 用户id
	 * @param cid 钓场id
	 * @return
	 */
//	@GET
//	@Path("actor/{cid}/{uid}")
	@RequestMapping(value="actor/{cid}/{uid}",method = RequestMethod.GET )
	public String getCampaignActor(@PathVariable("cid") Integer cid, @PathVariable("uid") Integer uid) {
		logger.debug("cid={}, uid={}", cid, uid);
		
		try {
			if (cid == null || uid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			CampaignActor ca = campaignService.getCampaignActor(cid, uid);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("isJoin", ca != null && ca.getPayStatus() > 0 && ca.getPayStatus() < 3 ? true : false);
			map.put("isRefund", ca != null && ca.getRefundStatus() == 0 ? false : true);
			map.put("number",  ca != null && ca.getLuckyNumber() != null ? ca.getLuckyNumber() : 0);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error("获取活动参与人列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取活动参与人数
	 * @param id 活动id
	 * @return
	 */
//	@GET
//	@Path("actor/count/{id}")
	@RequestMapping(value="actor/count/{id}",method = RequestMethod.GET )
	public String getCampaignActorCount(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			Integer campaignActorCount = campaignService.getCampaignActorCount(id);
			
			return JSONObject.toJSONString(new RespMsg<>(campaignActorCount));
		} catch (Exception e) {
			logger.error("获取活动参与人列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取活动参与人（报名）列表
	 * @param id 活动id
	 * @param top 取前几位，用于小程序活动首页显示，传-1则不限制
	 * @return
	 */
//	@GET
//	@Path("actor/list/{id}/{top}")
	@RequestMapping(value="actor/list/{id}/{top}",method = RequestMethod.GET )
	public String getCampaignActorList(@PathVariable("id") Integer id, @PathVariable("top") Integer top) {
		logger.debug("id={}, top={}", id, top);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			List<CampaignActor> campaignActorList = campaignService.getCampaignActorList(id, top);
			
			return JSONObject.toJSONString(new RespMsg<>(campaignActorList));
		} catch (Exception e) {
			logger.error("获取活动参与人列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
//	/**
//	 * 发布活动
//	 * @param id
//	 * @return
//	 */
//	@PUT
//	@Path("status/{id}/1")
//	public String publishCampaign(@PathVariable("id") Integer id) {
//		logger.debug("id={}", id);
//		
//		try {
//			if (id == null) {
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			
//			boolean result = campaignService.publishCampaign(id);
//			
//			return JSONObject.toJSONString(new RespMsg<>(result));
//		} catch (Exception e) {
//			logger.error("发布活动失败", e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
//		}
//	}
	/**
	 * 获取活动列表
	 * @return
	 */
//	@GET
//	@Path("list")
	@RequestMapping(value="list",method = RequestMethod.GET )
	public String getCampaignList(@RequestParam("start") Integer start, @RequestParam("size") Integer size) {
		try {
			logger.debug("start={}, size={}", start, size);
			if(start == null || size == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			List<Campaign> campaignList = campaignService.getCampaignListByNormal(start - 1 <= 0 ? 0 : (start - 1) * size , size);
			return JSONObject.toJSONString(new RespMsg<>(campaignList));
		} catch (Exception e) {
			logger.error("获取活动列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	

	/**
	 * 获取活动详情
	 * @param id
	 * @return
	 */
//	@GET
//	@Path("detail/{id}")
	@RequestMapping(value="detail/{id}",method = RequestMethod.GET )
	public String getCampaignDetail(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			CampaignDetail detail = campaignService.getCampaignDetail(id);
			
			return JSONObject.toJSONString(new RespMsg<>(detail));
		} catch (Exception e) {
			logger.error("获取活动详情失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取活动备注
	 * @param id
	 * @return
	 */
//	@GET
//	@Path("remark/{id}")
	@RequestMapping(value="remark/{id}",method = RequestMethod.GET )
	public String getRemark(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			String remark = campaignService.getCampaignRemark(id);
			
			return JSONObject.toJSONString(new RespMsg<>(remark));
		} catch (Exception e) {
			logger.error("获取活动详情失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 报名--小程序端
	 * 
	 * {"uid":1,"cid":1,"openid":openid}
	 * 
	 * @param obj
	 * @return
	 */
//	@POST
//	@Path("actor/miniPay")
	@RequestMapping(value="actor/miniPay",method = RequestMethod.POST,consumes = "application/json" )
	public String miniEnter(@RequestBody JSONObject obj) {
		logger.debug("json={}", obj);
		
		try {
			Long uid = obj == null ? null : obj.getLong("uid");
			Integer cid = obj == null ? null : obj.getInteger("cid");
			String openid = obj == null ? null : obj.getString("openid");
			if (uid == null || cid == null || openid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			String	result = campaignService.enter(uid, cid, openid,1);
			return result;
		} catch (Exception e) {
			logger.error("报名失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 报名确认--小程序端
	 * 
	 * {"uid":1,"cid":1}
	 * 
	 * @param obj
	 * @return
	 */
//	@POST
//	@Path("actor/miniConfirm")
	@RequestMapping(value="actor/miniConfirm",method = RequestMethod.POST,consumes = "application/json" )
	public String miniConfirm(@RequestBody JSONObject obj) {
		logger.debug("json={}", obj);
		
		try {
			Long uid = obj == null ? null : obj.getLong("uid");
			Integer cid = obj == null ? null : obj.getInteger("cid");
			if (uid == null || cid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			@SuppressWarnings("unused")
			int	result = campaignService.payActivityConfirm(uid,cid);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error("报名确认失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 回顾详情列表
	 * @param id 活动id
	 * @return
	 */
//	@GET
//	@Path("article/list/{id}")
	@ApiOperation(value = "回顾详情列表")
	@RequestMapping(value = "article/list/{id}",method = RequestMethod.GET)
	public String getListById(@ApiParam(value = "活动id",required = true)@PathVariable("id")Integer id){ 
		try {
			logger.debug("id:{}",id);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("cid", id);
			List<ActivityArticle> list = activityArticleService.getListsByCid(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 获取单个回顾详情
	 * @param id 文章id
	 * @return
	 */
//	@GET
//	@Path("article/id/{id}")
	@ApiOperation(value="获取单个回顾详情",response = ActivityArticle.class)
	@RequestMapping(value="article/id/{id}",method = RequestMethod.GET)
	public String getListById(@ApiParam("文章id")@PathVariable("id")Long id){ 
		try {
			logger.debug("id:{}",id);
			ActivityArticle pojo = activityArticleService.getById(id);
			return JSONObject.toJSONString(new RespMsg<>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
}
