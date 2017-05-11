package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.HpArticleComments;
import cn.heipiao.api.pojo.HpArticleLikes;
import cn.heipiao.api.pojo.HpArticleMsg;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserProfile;
import cn.heipiao.api.service.HpArticleCommentsService;
import cn.heipiao.api.service.HpArticleLikesService;
import cn.heipiao.api.service.HpArticleMsgService;
import cn.heipiao.api.service.HpArticleService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.service.UserProfileService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wzw
 * @date 2017年3月29日
 */
@Api(tags = "身边模块")
@RestController
@RequestMapping(value = "circle" ,produces = "application/json")
public class HpArticleResource {
	
	private static final Logger logger = LoggerFactory.getLogger(HpArticleResource.class);

	@Resource
	private HpArticleService hpArticleService;
	
	@Resource
	private HpArticleCommentsService hpArticleCommentsService;
	
	@Resource
	private HpArticleLikesService hpArticleLikesService;
	
	@Resource
	private UserProfileService userProfileService;
	
	@Resource
	private HpArticleMsgService hpArticleMsgService;
	
	@Resource
	private UserOpService userOpService;
	
	@Value("${circle.url}")
	private String circleUrl ;
	
	
	@ApiOperation(value = "身边动态发布",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r动态发布：\n\rarticleCategory(Y):1：动态，2：长文 ,\n\rbanner(N) :封面图片,\n\rcityId(Y):城市id 400300,\n\rcontent(Y) : 内容,\n\rlatitude(N):纬度,"
			+ "\n\rlongitude(N):经度,\n\r articleType(Y): 文章细分 0:纯文本，1:文本+图片，2：文本+视频,\n\rpositionName(N):位置名称,\n\rarticleUid(Y):用户uid 3345,\n\rurl(N):图片地址或者视频地址使用规定的分隔符(,)处理"
			+ "\n\r长文：\n\rarticleCategory(Y):1：动态，2：长文 ,\n\rarticleType(Y): 文章细分 0:纯文本，1:文本+图片，2：文本+视频\n\rcityId(Y):城市id 400300,\n\rcontentDetail(Y) : 内容,\n\rlatitude(N):纬度,\n\rlongitude(N):经度,"
			+ "\n\rpositionName(N):位置名称,\n\rarticleUid(Y):用户uid 3345 ,\n\rtitle(Y):文章标题, "
			+ "\n\rtype(Y):文章类型, \n\rtypeDesc(Y):文章类型名称, \n\rbanner(N) :封面图片")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addArticle(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			HpArticle pojo = json == null ? null : JSONObject.toJavaObject(json, HpArticle.class);
			if(pojo == null || pojo.getArticleCategory() == null || pojo.getCityId() == null
//					||pojo.getLatitude() == null
//					||pojo.getLongitude() == null 
					|| pojo.getArticleUid() == null ){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))); 
			}
			
			pojo.setCategory(0);
			if(pojo.getArticleCategory() == 1 && pojo.getContent() != null && pojo.getArticleType() != null){
				pojo.setType(0);
				hpArticleService.addPojo(pojo);
			}else if(pojo.getArticleCategory() == 2 && pojo.getTitle() != null && pojo.getType() != null
					&& pojo.getTypeDesc() != null && pojo.getArticleType() != null){
				hpArticleService.addPojo(pojo);
			}else{
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))); 
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "身边列表",response = HpArticle.class)
	@RequestMapping(value = "list/{cityId}/{articleId}/{size}",method = RequestMethod.GET)
	public String getListArticle(@ApiParam(value = "城市id",required=true)@PathVariable("cityId")Integer cityId,
			@ApiParam(value = "首次传0,下次传最后一条数据的articleId",required=true)@PathVariable("articleId")Long articleId,
			@PathVariable("size")Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("cityId", cityId);
			map.put("articleId", articleId);
			map.put("size", size);
			List<HpArticle> list = hpArticleService.getList(map);
			for (HpArticle hpArticle : list) {
				setUrl(hpArticle);
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	private void setUrl(HpArticle hpArticle) {
		if(hpArticle.getArticleCategory().intValue() == 2){
			hpArticle.setUrl(circleUrl + hpArticle.getArticleId());
		}		
	}

	@ApiOperation(value = "单个动态-通过文章id获取",response = HpArticle.class)
	@RequestMapping(value = "id/{articleId}",method = RequestMethod.GET,produces = "application/json")
	public String getByArticleId(@ApiParam(value = "文章id",required=true)@PathVariable("articleId")Long articleId){
		try {
			HpArticle pojo = hpArticleService.getNormalByArticleId(articleId);
			return JSONObject.toJSONString(new RespMsg<>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	
	@ApiOperation(value = "身边列表-用户个人列表",response = HpArticle.class)
	@RequestMapping(value = "list/uid/{articleUid}/{articleId}/{size}",method = RequestMethod.GET)
	public String getListByUid(
			@ApiParam(value = "用户uid",required=true)@PathVariable("articleUid") Long articleUid,
			@ApiParam(value="首次传0,下次传最后一条数据的articleId",required=true)@PathVariable("articleId") Long articleId,
			@PathVariable("size") Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("articleUid", articleUid);
			map.put("articleId", articleId);
			map.put("size", size);
			List<HpArticle> list = hpArticleService.getListByUid(map);
			for (HpArticle hpArticle : list) {
				setUrl(hpArticle);
			}
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	
	@ApiOperation(value = "身边动态-评论",notes = "参数说明(Y) 必须字段，(N) 可选字段"
			+ "\n\r  articleId(Y) : 文章id,commentUid(Y) : 用户uid,commentRUid(N) : 回复评论人的用户uid,commentContent(Y) : 回复内容")
	@RequestMapping(value = "comment/add",method = RequestMethod.POST,consumes = "application/json")
	public String addComment(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			HpArticleComments pojo = JSONObject.toJavaObject(json, HpArticleComments.class);
			if(pojo == null || pojo.getArticleId() == null || pojo.getCommentContent() == null || pojo.getCommentUid() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			hpArticleCommentsService.addComment(pojo);
			return JSONObject.toJSONString(new RespMsg<>(pojo));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation("评论列表-通过指定的文章id")
	@RequestMapping(value = "comment/list/{articleId}/{commentTime}/{size}",method = RequestMethod.GET)
	public String getCommentListByArticleId(@ApiParam(value="文章id",required=true) @PathVariable("articleId")Long articleId
			,@ApiParam("首次传0，下次传评论列表最后一条记录的时间戳") @PathVariable("commentTime")Long commentTime,@PathVariable("size")Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("articleId", articleId);
			map.put("commentTime", commentTime);
			map.put("size", size);
			List<HpArticleComments> pojos = hpArticleCommentsService.getCommentListByArticleId(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "身边动态-删除评论")
	@RequestMapping(value = "comment/remove/{articleId}/{commentUid}/{commentTime}",method = RequestMethod.DELETE)
	public String removeComment(@ApiParam(value="文章id",required = true)@PathVariable("articleId")Long articleId, 
			@ApiParam(value="用户uid",required = true)@PathVariable("commentUid")Long commentUid,
			@ApiParam(value="当前评论的时间戳",required = true)@PathVariable("commentTime")Long commentTime){
		try {
			HpArticleComments pojo = new HpArticleComments();
			pojo.setArticleId(articleId);
			pojo.setCommentUid(commentUid);
			pojo.setCommentTime(commentTime);
			hpArticleCommentsService.removeComment(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation(value = "身边动态-点赞",notes = "参数说明(Y) 必须字段，(N) 可选字段"
			+ "\n\r articleId(Y) : 文章id,likeUid(Y) : 用户uid")
	@RequestMapping(value = "like",method = RequestMethod.POST,consumes = "application/json")
	public String like(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			HpArticleLikes pojo = json == null ? null : JSONObject.toJavaObject(json, HpArticleLikes.class);
			if(pojo == null || pojo.getArticleId() == null || pojo.getLikeUid() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			hpArticleLikesService.addLike(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "身边动态-取消点赞")
	@RequestMapping(value = "like/remove/{articleId}/{likeUid}",method = RequestMethod.DELETE)
	public String unLike(@ApiParam(value="文章id",required = true)@PathVariable("articleId")Long articleId, 
			@ApiParam(value="用户uid",required = true)@PathVariable("likeUid")Long likeUid){
		try {
			HpArticleLikes pojo = new HpArticleLikes();
			pojo.setArticleId(articleId);
			pojo.setLikeUid(likeUid);
			hpArticleLikesService.removeLike(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation("点赞列表-通过指定的文章id")
	@RequestMapping(value = "like/list/{articleId}/{likeTime}/{size}",method = RequestMethod.GET)
	public String getLikeListByArticleId(@ApiParam(value="文章id",required=true) @PathVariable("articleId")Long articleId
			,@ApiParam("首次传0，下次传点赞列表最后一条记录的时间戳") @PathVariable("likeTime")Long likeTime,@PathVariable("size")Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("articleId", articleId);
			map.put("likeTime", likeTime);
			map.put("size", size);
			List<HpArticleLikes> pojos = hpArticleLikesService.getLikeListByArticleId(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation(value = "身边封面和消息获取")
	@RequestMapping(value = "banner/{uid}",method = RequestMethod.GET)
	public String getBannerByUid(@ApiParam(value="用户uid",required = true)@PathVariable("uid")Long uid){
		try {
			UserProfile up = userProfileService.getByUid(uid);
			return JSONObject.toJSONString(new RespMsg<>(up));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "身边封面修改或添加",notes = "参数说明："
			+ "\n\r uid(Y):用户uid \n\r  articleBanner(Y) : 封面路径")
	@RequestMapping(value = "banner",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyBannerByUid(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			UserProfile up = JSONObject.toJavaObject(json, UserProfile.class);
			if(up == null || up.getArticleBanner() == null || up.getUid() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			userProfileService.addPojo(up);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "身边个人主页-获取个人图片+视频",notes = "返回参数说明："
			+ "\n\r url : 获取30天内的图片+视频前3张 "
			+ "\n\r nickname:昵称，\n\r sex:性别 ，\n\r portrait:头像，\n\r regionStr:区域")
	@RequestMapping(value = "user/{uid}" , method = RequestMethod.GET)
	public String getProfile(@PathVariable("uid") Long uid){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			Calendar c = ExDateUtils.getCalendar();
			//获取30天内的图片+视频前3张
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 30);
			map.put("time", new Timestamp(c.getTimeInMillis()));
			List<HpArticle> u = hpArticleService.getProfile(map);
			User user = userOpService.queryUserById(uid);
			Set<HpArticle> r = new HashSet<HpArticle>();
			for (HpArticle s : u) {
				if(StringUtils.isNotBlank(s.getUrl())){
					String []ss = s.getUrl().split(",");
					for (String s1 : ss) {
						if(r.size() < 3){
							HpArticle h = new HpArticle();
							h.setUrl(s1);
							h.setArticleType(s.getArticleType());
							r.add(h);
						}else{
							break;
						}
					}
				}
				if(r.size() >= 3){
					break;
				}
			}
			Map<String,Object> rm = new HashMap<String, Object>();
			rm.put("url", r);
			rm.put("nickname", user.getNickname());
			rm.put("sex", user.getSex());
			rm.put("portrait", user.getPortriat());
			rm.put("regionStr", user.getRegionStr());
			return JSONObject.toJSONString(new RespMsg<>(rm));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "消息获取-获取当前用户的所有消息",response = HpArticleMsg.class)
	@RequestMapping(value = "msg/{uid}/{msgTime}/{size}",method = RequestMethod.GET)
	public String getMsgListByUid(@PathVariable("uid") Long uid,
		@ApiParam(value = "首次传0，下次传消息列表最后一条记录的时间戳",required = true)@PathVariable("msgTime") Long msgTime
		,@PathVariable("size") Integer size){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("msgTime", msgTime > 0 ? msgTime : null);
			map.put("size", size);
			List<HpArticleMsg> pojos = hpArticleMsgService.getListByUid(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation("消息删除-通过uid删除当前用户的所有消息")
	@RequestMapping(value = "msg/{uid}",method = RequestMethod.DELETE)
	public String removeAllByUid(@PathVariable("uid") Long uid){
		try {
			hpArticleMsgService.removeAllByUid(uid);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation("消息删除-删除指定的消息")
	@RequestMapping(value = "msg/{articleId}/{msgUid}/{msgTime}",method = RequestMethod.DELETE)
	public String removeByPojo(@PathVariable("articleId") Long articleId,@PathVariable("msgUid") Long msgUid,
			@PathVariable("msgTime") Long msgTime){
		try {
			HpArticleMsg pojo = new HpArticleMsg();
			pojo.setArticleId(articleId);
			pojo.setMsgUid(msgUid);
			pojo.setMsgTime(msgTime);
			hpArticleMsgService.removePojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation("获取消息数量-通过用户id")
	@RequestMapping(value = "msg/msgSum/{uid}",method = RequestMethod.GET)
	public String getProfileMsgSum(@PathVariable("uid") Long uid){
		try {
			UserProfile up = userProfileService.getByUid(uid);
			if(up == null){
				up = new UserProfile();
				up.setMsgSum(0);
			}
			up.setArticleBanner(null);
			up.setUid(null);
			return JSONObject.toJSONString(new RespMsg<>(up));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation("设置消息已读-通过用户id")
	@RequestMapping(value = "msg/msgSum/{uid}",method = RequestMethod.DELETE)
	public String setMsgSum(@PathVariable("uid") Long uid){
		try {
			userProfileService.updateMsgSum(uid, 0);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}