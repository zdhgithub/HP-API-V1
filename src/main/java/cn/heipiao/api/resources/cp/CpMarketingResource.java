package cn.heipiao.api.resources.cp;


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

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.LikeUser;
import cn.heipiao.api.pojo.Marketing;
import cn.heipiao.api.pojo.MarketingPicture;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.MarketingService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @author 作者 :dzh
* @version 创建时间：2017年4月26日 上午9:51:42
* 类说明 ：营销活动cp
 */

@RestController
@Api(tags="营销活动模块")
@RequestMapping(value = "cp/marketing",produces="application/json")
public class CpMarketingResource {
	private static final Logger logger = LoggerFactory.getLogger(CpMarketingResource.class);
	
	@Resource
	private MarketingService marketingService;
	/**
	 * cp添加营销活动
	 * @param json
	 * @return
	 */
	@ApiOperation(value = "添加营销活动",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r发布营销活动：\n\rname(Y):活动名称 ,\n\rbanner(Y) :封面图片,\n\rdetail(Y):活动详情,\n\rendTime(Y):结束时间")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json" )
	public String addMarketing(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Marketing pojo = json == null ? null : JSONObject.toJavaObject(json, Marketing.class);
			if(pojo == null || pojo.getName()==null || pojo.getDetail() == null
					||pojo.getEndTime() == null 
					|| pojo.getBanner() == null ){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			if(pojo.getEndTime().getTime()<ExDateUtils.getDate().getTime()){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"重新设置活动时间"));
			}
			pojo.setStatus(0);
			pojo.setCreateTime(ExDateUtils.getDate());
			marketingService.addMarketing(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "发布/删除营销活动")
	@RequestMapping(value = "/{id}/{status}",method = RequestMethod.PUT)
	public String updateMarketing(@ApiParam(value="营销活动id",required=true)@PathVariable("id")Integer id,
			@ApiParam(value="营销活动状态：1 发布，3 删除",required=true)@PathVariable("status")Integer status){
		try {
			logger.debug("id:{},status:{}",id,status);
			if(id==null || status==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Marketing marketing = marketingService.getOneMarketing(id);
			marketing.setStatus(status);
			if(status == 1){
				marketing.setBeginTime(ExDateUtils.getDate());
			}
			marketingService.updateMarketing(marketing);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "营销活动列表",response = Marketing.class)
	@RequestMapping(value = "list/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getList(@ApiParam(value="营销活动状态：0 未发布，1 已发布，2 已结束，3 删除 ,全部10",required=true)@PathVariable("status")Integer status,
			@ApiParam(value="开始页,首页为1",required=true)@PathVariable("start")Integer start,
			@ApiParam(value="每页大小",required=true)@PathVariable("size")Integer size){
		try {
			logger.debug("status:{},start；{}，size；{}",status,start,size);
			if(status==10){
				status=null;
			}
			Map<String,Object> map = new HashMap<>();
			map.put("status", status);
			Integer total = marketingService.getMarketingCount(map);
			map.put("start", (start-1)*size);
			map.put("size", size);
			List<Marketing> data = marketingService.getList(map);
			Map<String,Object> result = new HashMap<>();
			result.put("total", total);
			result.put("data", data);
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "发布图片内容列表",response = LikeUser.class)
	@RequestMapping(value = "pictures/list/{marketingId}",method = RequestMethod.GET)
	public String getPictures(@ApiParam(value="营销活动id",required=true)@PathVariable("marketingId")Integer marketingId){
		try {
			logger.debug("marketingId:{}",marketingId);
			Map<String,Object> map = new HashMap<>();
			map.put("marketingId", marketingId);
			List<MarketingPicture> list = marketingService.getPictureList(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "审核发布内容图片",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r审核通过:\n\rstatus(Y):审核状态： 1  通过,\n\rmarketingId(Y):活动id,\n\ruid(Y):参与活动用户id"
			+"\n\r审核不通过:\n\rstatus(Y):审核状态： 2  未通过,\n\rrefundReason(Y):不通过原因,\n\rmarketingId(Y):活动id,\n\ruid(Y):参与活动用户id")
	@RequestMapping(value = "/picture",method = RequestMethod.PUT,consumes = "application/json")
	public String updatePicture(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			if(json == null || json.getInteger("status") == null 
					|| json.getInteger("marketingId") == null 
					|| json.getLong("uid") == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Long uid = json.getLong("uid");
			Integer marketingId = json.getInteger("marketingId");
			Integer status = json.getInteger("status");
			Map<String ,Object> map = new HashMap<>();
			map.put("uid", uid);
			map.put("marketingId", marketingId);
			map.put("status", status);
			if(status== 2){
				map.put("refundReason", json.getString("refundReason"));
				map.put("refundTime", ExDateUtils.getDate());
			}
			marketingService.updateMarketingPicture(map);
			return JSONObject.toJSONString(new RespMsg<>());					
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
