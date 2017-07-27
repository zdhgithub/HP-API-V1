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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.ActivityArticle;
import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignDetail;
import cn.heipiao.api.pojo.CampaignType;
import cn.heipiao.api.pojo.RefundActivity;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.ActivityArticleService;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.RefundActivityService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wzw
 * @date 2017年3月11日
 */
//@Path("cp/campaign/")
//@Component
@RestController
@Api(tags="活动模块")
@RequestMapping(value = "cp/campaign",produces="application/json")
public class ActivityResource {
	
	private final static Logger logger = LoggerFactory.getLogger(ActivityResource.class);

	@Resource
	private CampaignService campaignService;
	
	@Resource
	private RefundActivityService refundActivityService;
	
	@Resource
	private ActivityArticleService activityArticleService;
	
	/**
	 * 新建活动
	 * FIXME 活动详情分隔
	 * @param obj
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json" )
	public String newCampaign(@RequestBody JSONObject obj) {
		logger.debug("json={}", obj);
		
		try {
			Campaign campaign = obj == null ? null : JSONObject.toJavaObject(obj, Campaign.class);
			if (campaign == null || campaign.getName() == null || campaign.getType() == null || campaign.getQuota() == null
					|| campaign.getBeginTime() == null || campaign.getEndTime() == null || campaign.getEntryTerminalTime() == null
					|| campaign.getBill() == null || campaign.getBill() == null
					|| campaign.getProvince() == null || campaign.getCity() == null || campaign.getAddr() == null || campaign.getCost() == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			//判断时间
			if(!verifyTime(campaign)){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "时间错误"));
			}
			
			if (StringUtils.isNotEmpty(obj.getString("background"))) {
				campaign.setBackground(obj.getString("background"));
			}
			
			if (StringUtils.isNotEmpty(obj.getString("note"))) {
				campaign.setNote(obj.getString("note"));
			}
			
			if (StringUtils.isNotEmpty(obj.getString("detail"))) {
				campaign.setDetail(obj.getString("detail"));
			}
			
			if (StringUtils.isNotEmpty(obj.getString("call"))) {
				campaign.setCall(obj.getString("call"));
			}
			
			if (StringUtils.isNotEmpty(obj.getString("costExplain"))) {
				campaign.setCostExplain(obj.getString("costExplain"));
			}
			
			campaign.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			
			campaignService.addCampaign(campaign);
			return JSONObject.toJSONString(new RespMsg<>(campaign.getId()));
		} catch (Exception e) {
			logger.error("新建活动失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	
	//判断时间 
	//活动结束时间> 活动开始时间 , 活动开始时间 > 报名开始时间   , 报名开始时间>当前时间 
	public static boolean verifyTime(Campaign campaign) {
		return  campaign.getEndTime().getTime() > campaign.getBeginTime().getTime()
				 && campaign.getBeginTime().getTime() > campaign.getEntryTerminalTime().getTime()
				 && campaign.getEntryTerminalTime().getTime() > ExDateUtils.getCalendar().getTimeInMillis();
	}




	/**
	 * 修改活动
	 * FIXME 活动详情分隔
	 * @param id 活动id
	 * @param obj
	 * @return
	 */
//	@PUT
//	@Path("{id}")
	@RequestMapping(value="{id}",method = RequestMethod.PUT,consumes = "application/json" )
	public String updateCampaign(@PathVariable("id") Integer id,@RequestBody JSONObject obj) {
		logger.debug("id={}, json={}", id, obj);
		
		try {
			Campaign campaign = obj == null ? null : JSONObject.toJavaObject(obj, Campaign.class);
			if (campaign == null || id == null ) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			campaign.setId(id);
			Campaign camp = campaignService.getCampaign(campaign.getId());
			if(camp.getStatus() > 2 && camp.getStatus() < 5 || camp.getStatus() == 6){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "不允许编辑的活动"));
			}
			//如果已发布，不能修改报名费
			if(campaign.getCost() != null && camp.getStatus() > 0){
				campaign.setCost(camp.getCost());
			}
			//如果发布后不允许修改人数
			if(camp.getStatus() > 0){
				campaign.setQuota(camp.getQuota());
			}
			
//			if (StringUtils.isNotEmpty(obj.getString("background"))) {
//				campaign.setBackground(obj.getString("background"));
//			}
//			
//			if (StringUtils.isNotEmpty(obj.getString("note"))) {
//				campaign.setNote(obj.getString("note"));
//			}
//			
//			if (StringUtils.isNotEmpty(obj.getString("detail"))) {
//				campaign.setDetail(obj.getString("detail"));
//			}
//			
//			if (StringUtils.isNotEmpty(obj.getString("call"))) {
//				campaign.setCall(obj.getString("call"));
//			}
//			
//			if (StringUtils.isNotEmpty(obj.getString("costExplain"))) {
//				campaign.setCostExplain(obj.getString("costExplain"));
//			}
			
			boolean result = campaignService.updateCampaign(campaign);
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error("修改活动失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 删除活动
	 * @param id 活动id
	 * @return
	 */
//	@DELETE
//	@Path("{id}")
	@RequestMapping(value="{id}",method = RequestMethod.DELETE)
	public String delCampaign(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			boolean result = campaignService.delCampaign(id);
			
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error("删除活动失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 抽签
	 * @param id
	 * @return
	 */
//	@PUT
//	@Path("draw/{id}")
	@RequestMapping(value="draw/{id}",method = RequestMethod.PUT,consumes = "application/json" )
	public String draw(@PathVariable("id") Integer id) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			return campaignService.draw(id);
			
//			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error("抽签失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, 
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 修改活动备注
	 * @param id
	 * @param obj
	 * @return
	 */
//	@PUT
//	@Path("remark/{id}")
	@RequestMapping(value="remark/{id}",method = RequestMethod.PUT,consumes = "application/json" )
	public String setRemark(@PathVariable("id") Integer id, JSONObject obj) {
		logger.debug("id={}", id);
		
		try {
			if (id == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			boolean result = campaignService.setCampaignRemark(id, obj.getString("remark"));
			
			return JSONObject.toJSONString(new RespMsg<>(result));
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
	@RequestMapping(value="remark/{id}",method = RequestMethod.GET)
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
	 * 获取活动详情
	 * @param id
	 * @return
	 */
//	@GET
//	@Path("detail/{id}")
	@RequestMapping(value="detail/{id}",method = RequestMethod.GET)
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
	 * 修改活动状态：发布/启/停/取消/结束
	 * @param id
	 * @param value
	 * @return
	 */
//	@PUT
//	@Path("status/{id}/{value}")
	@RequestMapping(value="status/{id}/{value}",method = RequestMethod.PUT,consumes = "application/json" )
	public String start(@PathVariable("id") Integer id, @PathVariable("value") Integer value) {
		logger.debug("id={}, value={}", id, value);
		
		try {
			if (id == null || value == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			boolean result;
			
			switch (value) {
				case 1 : // 发布/启动
					result = campaignService.publishCampaign(id);
					break;
				case 2 : // 待抽签，改为2的场景是：之前使用过暂停功能，现在恢复启动，因为系统未保存暂停之前的状态，所以可以给CP端选择恢复启动状态1还是2
					result = campaignService.start(id, value);
					break;
				case 4 : // 已结束
					result = campaignService.finish(id);
					break;
				case 5 : // 暂停
					result = campaignService.pause(id);
					break;
				case 6 : // 取消
					result = campaignService.cancel(id);
					break;
				default :
					// nothing to do
					result = false;
			}
			
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error("获取活动详情失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	
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
			List<Campaign> list = campaignService.getCampaignNewList(start, size);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("total", campaignService.getCampaignCount());
			
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error("获取活动列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 活动退款 - cp端
	 * {"cid":1,"orderId":"23213"}
	 * 
	 */
//	@POST
//	@Path("actor/refund")
	@RequestMapping(value="actor/refund",method = RequestMethod.POST,consumes = "application/json" )
	public String activityRefund(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Integer cid = json == null ? null : json.getInteger("cid");
			String orderId = json == null ? null : json.getString("orderId");
			if(cid == null || orderId == null ){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			RefundActivity ra = new RefundActivity();
			ra.setCid(cid);
			ra.setOrderId(orderId);
			int rs = refundActivityService.activityRefund(ra);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error("退款失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 活动退款 - cp端
	 * {"cid":1}
	 * 
	 */
//	@POST
//	@Path("actor/refunds")
	@RequestMapping(value="actor/refunds",method = RequestMethod.POST,consumes = "application/json" )
	public String activityRefunds(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Integer cid = json == null ? null : json.getInteger("cid");
			if(cid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = refundActivityService.activityRefunds(cid);
			return JSONObject.toJSONString(new RespMsg<>(rs));
		} catch (Exception e) {
			logger.error("退款失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 报名-cp端
	 * {"uid":1,"cid":12}
	 * @param obj
	 * @return
	 */
//	@POST
//	@Path("actor")
	@RequestMapping(value="actor",method = RequestMethod.POST,consumes = "application/json" )
	public String enter(@RequestBody JSONObject obj) {
		logger.debug("json={}", obj);
		
		try {
			Long uid = obj == null ? null : obj.getLong("uid");
			Integer cid = obj == null ? null : obj.getInteger("cid");
			if (uid == null || cid == null) {
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			String	result = campaignService.enter(uid, cid, null,0);
			
			return result;
		} catch (Exception e) {
			logger.error("报名失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
//	@GET
//	@Path("types")
	@RequestMapping(value="types",method = RequestMethod.GET)
	public String typeList(){
		try {
			List<CampaignType> list = campaignService.getCampaignTypesAll();
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error("获取列表失败", e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	//==============活动回顾===============
	/**
	 * 添加回顾
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("article/add")
	@ApiOperation(value = "添加回顾",notes="参数描述 ：\n\r title:string:标题  \n\r banner:string:封面 "
			+ "\n\r sort:int:排序 \n\r type:int:回顾类型1:人物,2:图片,3:技巧, \n\r cid:int:活动id "
			+ "\n\r content:string:内容")
	@RequestMapping(value = "article/add",method = RequestMethod.POST ,consumes = "application/json")
	public String addArticle(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			ActivityArticle pojo = json == null ? null : JSONObject.toJavaObject(json, ActivityArticle.class);
			if(pojo == null || pojo.getCid() == null || pojo.getContent() == null || pojo.getSort() == null
					|| pojo.getBanner() == null || pojo.getTitle() == null || pojo.getType() == null
					|| pojo.getType() < 1 || pojo.getType() > 3 || pojo.getIsVideo() == null
					){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}

			Campaign camp = campaignService.getCampaign(pojo.getCid());
			if(camp == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动不存在"));
			}
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			activityArticleService.add(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 编辑回顾
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("article/modification")
	@ApiOperation(value = "编辑回顾",notes="参数说明: id : long :记录id  \n\r title:string:标题  \n\r banner:string:封面 "
			+ "\n\r sort:int:排序 \n\r type:int:回顾类型1:人物,2:图片,3:技巧"
			+ "\n\r content:string:内容")
	@RequestMapping(value = "article/modification",method = RequestMethod.PUT ,consumes = "application/json")
	public String modifyArticle(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			ActivityArticle pojo = json == null ? null : JSONObject.toJavaObject(json, ActivityArticle.class);
			if(pojo == null || pojo.getId() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			if(pojo.getBanner() == null && pojo.getContent() == null && pojo.getSort() == null
					&& pojo.getTitle() == null && pojo.getType() == null && pojo.getIsVideo() == null){
				return JSONObject.toJSONString(new RespMsg<>());
			}
			ActivityArticle aa = activityArticleService.getById(pojo.getId());
			if(aa != null){
				activityArticleService.modify(pojo);
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取回顾列表
	 * @param cid 活动id
	 * @return
	 */
	@ApiOperation(value = "获取回顾列表")
	@RequestMapping(value = "article/list/{cid}",method = RequestMethod.GET)
	public String getListByCid(@ApiParam(value="活动id") @PathVariable("cid")Integer cid){
		try {
			logger.debug("cid:{}",cid);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("cid", cid);
			List<ActivityArticle> list = activityArticleService.getListByCid(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 添加回顾视频
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("article/video")
	@ApiOperation(value = "添加回顾视频",notes="参数说明：\n\r id : int : 活动id,\n\r video : string :视频")
	@RequestMapping(value = "article/video",method = RequestMethod.PUT ,consumes = "application/json")
	public String uploadVideo(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Campaign camp = json == null ? null : JSONObject.toJavaObject(json, Campaign.class);
			if(camp == null || camp.getId() == null || camp.getVideo() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			campaignService.putVideo(camp);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 删除回顾文章
	 * @param id:文章id
	 * @return
	 */
//	@DELETE
//	@Path("article/{id}")
	@ApiOperation(value="删除回顾文章")
	@RequestMapping(value = "article/{id}",method = RequestMethod.DELETE)
	public String deleteById(@ApiParam("文章id")@PathVariable("id")Long id){
		try {
			logger.debug("id:{}",id);
			activityArticleService.deleteById(id);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
}
