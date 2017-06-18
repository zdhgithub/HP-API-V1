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
import cn.heipiao.api.pojo.LikeUser;
import cn.heipiao.api.pojo.Marketing;
import cn.heipiao.api.pojo.MarketingPicture;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.cp.CpMarketingResource;
import cn.heipiao.api.service.MarketingService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
* @author 作者 :dzh
* @version 创建时间：2017年4月26日 下午1:40:55
* 类说明
 */

@RestController
@Api(tags="营销活动模块")
@RequestMapping(value = "marketing",produces="application/json")
public class MarketingResource {
	private static final Logger logger = LoggerFactory.getLogger(CpMarketingResource.class);
	
	@Resource
	private MarketingService marketingService;
	
	@Resource
	private UserOpService userOpService;
	
	@ApiOperation(value = "获取营销活动详情",response = Marketing.class)
	@RequestMapping(value = "marketing/{id}",method = RequestMethod.GET)
	public String getOneMarketing(@ApiParam(value="营销活动id",required=true)@PathVariable("id")Integer id){
		try {
			logger.debug("id:{}",id);
			if(id==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			return JSONObject.toJSONString(new RespMsg<Marketing>(
					marketingService.getOneMarketing(id)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value="获取发布图片的列表",response = MarketingPicture.class)
	@RequestMapping(value="list/{marketingId}/{uid}",method = RequestMethod.GET)
	public String getPicturesList(
			@ApiParam(value="营销活动marketingId",required=true)@PathVariable("marketingId")Integer marketingId,
			@ApiParam(value="用户id", required=true) @PathVariable("uid") Integer uid,
			@ApiParam(value="起始页", required=false) @RequestParam(value = "start", required = false) Integer start,
			@ApiParam(value="页大小", required=false) @RequestParam(value = "size", required = false) Integer size
			){
		try {
			logger.debug("marketingId:{}",marketingId);
			if(marketingId==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			if (start != null && size != null) {
				start = start - 1 <= 0 ? 0 : (start - 1) * size;
			}
			
			Map<String,Object> map = new HashMap<>();
			map.put("marketingId", marketingId);
			map.put("uid", uid);
			map.put("status", 1);
			map.put("start", start);
			map.put("size", size);
			List<MarketingPicture> list = marketingService.getPictureList(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value="发布图片内容",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r发布营销活动：\n\rmarketingId(Y):营销活动id ,\n\ruid(Y) :发布用户id,\n\rpicture(Y):图片,\n\rpictureDesc(Y):图片描述")
	@RequestMapping(value="pictures",method=RequestMethod.POST,consumes = "application/json")
	public String addPictures(@RequestBody JSONObject json){
		try {
			logger.debug("json；{}",json);
			MarketingPicture pojo = json == null ? null : JSONObject.toJavaObject(json, MarketingPicture.class);
			if(pojo==null || pojo.getMarketingId()==null || pojo.getUid()==null
					|| pojo.getPicture()==null || pojo.getPictureDesc() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			pojo.setLikeCount(0);
			pojo.setStatus(0);
			pojo.setUploadTime(ExDateUtils.getDate());
			marketingService.addPictures(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value="修改发布的图片内容",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r修改内容：\n\rmarketingId(Y):营销活动id ,\n\ruid(Y) :发布用户id,\n\rpicture(Y):图片,\n\rpictureDesc(Y):图片描述")
	@RequestMapping(value="pictures",method=RequestMethod.PUT,consumes = "application/json")
	public String updatePictures(@RequestBody JSONObject json) {
		logger.debug("json；{}",json);
		
		MarketingPicture pojo = json == null ? null : JSONObject.toJavaObject(json, MarketingPicture.class);
		if(pojo==null || pojo.getMarketingId()==null || pojo.getUid()==null
				|| pojo.getPicture()==null || pojo.getPictureDesc() == null){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
					RespMessage.getRespMsg(Status.value_is_null_or_error)));
		}
		
		try {
			Map<String ,Object> map = new HashMap<>();
			map.put("uid", pojo.getUid());
			map.put("marketingId", pojo.getMarketingId());
			map.put("picture", pojo.getPicture());
			map.put("pictureDesc", pojo.getPictureDesc());
			map.put("uploadTime", ExDateUtils.getDate());
			map.put("status", "0");
			
			marketingService.updatePictures(map);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	@ApiOperation(value="获取发布图片内容",response = MarketingPicture.class)
	@RequestMapping(value="picture/{marketingId}/{uid}",method = RequestMethod.GET)
	public String getPicturesList(@ApiParam(value="营销活动marketingId",required=true)@PathVariable("marketingId")Integer marketingId,
			@ApiParam(value="上传用户id",required=true)@PathVariable("uid")Long uid){
		try {
			logger.debug("marketingId:{},uid:{}",marketingId,uid);
			if(marketingId==null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Marketing marketing = marketingService.getOneMarketing(marketingId);
			if(marketing == null || marketing.getStatus()==2){
				return JSONObject.toJSONString(new RespMsg<>(Status.FORBIDDEN,"活动已结束"));
			}
			Map<String,Object> map = new HashMap<>();
			map.put("marketingId", marketingId);
			map.put("uid", uid);
			return JSONObject.toJSONString(new RespMsg<>(
					marketingService.getOneMaretingPicture(map)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation(value="用户点赞",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r点赞用户：\n\rlikeUid(Y):点赞用户id ,\n\rmarketUid(Y) :发布用户id,\n\rmarketingId(Y):营销活动id")
	@RequestMapping(value="likeUser",method=RequestMethod.POST,consumes = "application/json")
	public String addLikeUser(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			LikeUser pojo = json == null ? null :JSONObject.toJavaObject(json, LikeUser.class);
			if(pojo == null || pojo.getLikeUid()==null || pojo.getMarketingId()== null 
					||pojo.getMarketUid()==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Marketing marketing = marketingService.getOneMarketing(pojo.getMarketingId());
			if(marketing == null || marketing.getStatus()==2){
				return JSONObject.toJSONString(new RespMsg<>(Status.FORBIDDEN,"活动已结束"));
			}
			if(marketing.getEndTime().getTime()<ExDateUtils.getDate().getTime()){
				return JSONObject.toJSONString(new RespMsg<>(Status.FORBIDDEN,"活动点赞已结束"));
			}
			Long uid = pojo.getLikeUid();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("marketingId", pojo.getMarketingId());
			map.put("marketUid", pojo.getMarketUid());
			map.put("likeUid", uid);
			if(marketingService.getOneLikeUser(map)!=null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,"已点赞"));
			}
			User user = userOpService.queryUserById(uid);
			LikeUser likeUser = new LikeUser();
			if(user==null){
				return JSONObject.toJSONString(new RespMsg<>(Status.no_such_user,
						RespMessage.getRespMsg(Status.no_such_user)));
			}
			likeUser.setLikeUid(uid);
			likeUser.setMarketingId(pojo.getMarketingId());
			likeUser.setMarketUid(pojo.getMarketUid());
			likeUser.setLikeTime(ExDateUtils.getDate());
			likeUser.setNickName(user.getNickname()==null ? null:user.getNickname());
			likeUser.setPortrait(user.getPortriat()==null ? null :user.getPortriat());
			marketingService.addLkeUser(likeUser);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value="用户是否点赞",notes="1：表示已点赞，2：表示未点赞")
	@RequestMapping(value="status/{likeUid}/{marketUid}/{marketingId}",method=RequestMethod.GET)
	public String isLikeUser(@ApiParam("用户id")@PathVariable("likeUid")Long likeUid,
			@ApiParam("发布图片用户id")@PathVariable("marketUid")Long marketUid,
			@ApiParam("活动id")@PathVariable("marketingId")Integer marketingId){
		try {
			logger.debug("likeUid:{},marketUid:{},marketingId:{}",likeUid,marketUid,marketingId);
			if(likeUid == null || marketUid == null || marketingId == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
						RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("marketingId", marketingId);
			map.put("marketUid", marketUid);
			map.put("likeUid", likeUid);
			LikeUser likeUser = marketingService.getOneLikeUser(map);
			Integer status=0;
			if(likeUser!=null){
				status = 1;
			}
			return JSONObject.toJSONString(new RespMsg<>(status));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "用户是否参加活动", notes = "1：表示已参加， 0：表示未参加")
	@RequestMapping(value = "status/{uid}/{mid}", method = RequestMethod.GET)
	public String isJoin(@ApiParam("用户id") @PathVariable("uid") Long uid,
			@ApiParam("活动id") @PathVariable("mid") Integer mid) {
		logger.debug("uid:{}, mid:{}", uid, mid);
		
		if(uid == null || mid == null){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,
					RespMessage.getRespMsg(Status.value_is_null_or_error)));
		}
		
		try {
			Integer result = marketingService.isJoin(uid, mid);
			return JSONObject.toJSONString(new RespMsg<>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "营销活动列表",response = Marketing.class)
	@RequestMapping(value = "listpage/{start}/{size}",method = RequestMethod.GET)
	public String getList(@ApiParam(value="开始页,首页为1",required=true)@PathVariable("start")Integer start,
			@ApiParam(value="每页大小",required=true)@PathVariable("size")Integer size){
		logger.debug("start:{}，size:{}",start,size);
		
		try {
			List<Marketing> data = marketingService.getList(start - 1 <= 0 ? 0 : (start - 1) * size , size);
			return JSONObject.toJSONString(new RespMsg<>(data));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
