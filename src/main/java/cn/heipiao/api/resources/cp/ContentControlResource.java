package cn.heipiao.api.resources.cp;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.HpArticle;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.HpArticleResource;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.HpArticleService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author dzh
 * @date 2017年3月31日
 */
@RestController
@Api(tags = "身边图文")
@RequestMapping(value="cp/content/",produces = "application/json")
public class ContentControlResource {
	
	private final static Logger logger = LoggerFactory.getLogger(ContentControlResource.class);
	
	@Resource
	private UserOpService userOpService;
	
	@Resource
	private HpArticleService hpArticleService;
	
	@Resource
	private HpArticleResource hpArticleResource;

	/**
	 * cp添加虚拟用户
	 * @param url 虚拟用户头像url
	 * @param nickname 虚拟用户昵称
	 * @param cityId 虚拟用户区域id
	 * @param remark    虚拟 用户备注
	 */ 
	@ApiOperation(value = "虚拟用户添加",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r虚拟用户：\n\rurl(N):虚拟 用户头像路径 ,\n\rnickname(Y):虚拟用户昵称,\n\rremark(N):备注"
			)
	@RequestMapping(value = "ideal/user",method = RequestMethod.POST,consumes = "application/json")
	public String addIdealUser(@RequestBody JSONObject json){
		try {
			
			logger.debug("json:{}",json);
			if(json.getString("nickname") == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			String url = json.getString("url");
			String nickname = json.getString("nickname");
//			Integer regionid = json.getInteger("cityid");
			String remark = json.getString("remark");
			User user = new User();
			user.setPortriat(url);
			user.setNickname(nickname);
			user.setRemark(remark);
			user.setUsername("_"+RandomStringUtils.randomAlphanumeric(6));
			user.setRegisTime(ExDateUtils.getDate());
			user.setStatus("0");
			userOpService.saveIdealUser(user);
			return JSONObject.toJSONString(new RespMsg<>(user));
		} catch (Exception e) {
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 查询所有的虚拟用户
	 */
	@ApiOperation(value = "查询所有的虚拟用户")
	@RequestMapping(value = "ideal/user",method = RequestMethod.GET)
	public String findIdealUser(){
		try {
			List<User> list = userOpService.queryIdealUser();	
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			return JSONObject.toJSONString(new RespMsg<>
				(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * cp修改虚拟用户信息
	 */
	@ApiOperation(value = "虚拟用户修改",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r虚拟用户：\n\rurl(N):虚拟 用户头像路径 ,\n\rnickname(Y):虚拟用户昵称,\n\rremark(N):备注,\n\ruid(Y):用户id"
			)
	@RequestMapping(value="ideal/user",method = RequestMethod.PUT,consumes = "application/json" )
	public String updateIdealUser(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			String url = json.getString("url");
			String nickname = json.getString("nickname");
			Long uid = json.getLong("uid");
			if(url == null || nickname == null || uid == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			userOpService.updateIdealUser(json);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			
			return 	JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "内容列表",response = HpArticle.class)
	@RequestMapping(value = "list/{cityId}/{articleUid}/{keyword}/{start}/{size}",method = RequestMethod.GET)
	public String getListArticle(@ApiParam(value = "所有为0,城市id",required=true)@PathVariable("cityId")Integer cityId,
			@ApiParam(value = "为空传0,用户id",required=true)@PathVariable("articleUid")Long articleUid,
			@ApiParam(value = "模糊搜索\\_关键词(用户名、标题、商家) ,为空时传\\_",required=true)@PathVariable("keyword")String keyword,
			@ApiParam(value = "首次传1，页数")@PathVariable("start") Integer start,@PathVariable("size")Integer size){
		try {
			
			if(cityId==0){
				cityId=null;
			}
			if(keyword.equals("_")){
				keyword=null;
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("cityId", cityId);
			map.put("articleUid", articleUid ==0 ? null:articleUid);
			map.put("keyword", keyword !=null && keyword.length()>1 ?"%" + keyword.substring(1) + "%":null);
			long total = hpArticleService.getListCount(map);
			map.put("start", size*(start-1));
			map.put("size", size);
			List<HpArticle> pojos = hpArticleService.getAllList(map);
			Map<String,Object> rm = new HashMap<String, Object>();
			rm.put("data", pojos);
			rm.put("total", total);
			return JSONObject.toJSONString(new RespMsg<>(rm));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	@ApiOperation(value = "隐藏或显示文章")
	@RequestMapping(value = "delete/{articleId}/{isDelete}",method = RequestMethod.PUT)
	public String hiddenArticle(@ApiParam(value = "文章id",required=true)@PathVariable("articleId")Long articleId,
			@ApiParam(value = "文章状态,true-隐藏，false-显示",required=true)@PathVariable("isDelete")Boolean isDelete){
		try {
			if(articleId == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			HpArticle pojo =hpArticleService.getByArticleId(articleId);
			pojo.setArticleId(articleId);
			pojo.setIsDelete(isDelete);
			hpArticleService.update(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	@ApiOperation(value = "虚拟用户身边动态发布",notes="参数说明 :(Y) 必须字段，(N) 可选字段"
			+ "\n\r动态发布：\n\rarticleCategory(Y):1：动态，2：长文 ,\n\rcityId(Y):城市id 400300,\n\rcontent(Y) : 内容,\n\rlatitude(N):纬度,"
			+ "\n\rlongitude(N):经度,\n\r articleType(Y):(内容型才有) 文章细分 0:纯文本，1:文本+图片，2：文本+视频,\n\rpositionName(N):位置名称,\n\rarticleUid(Y):虚拟用户uid 3345,\n\rurl(N):图片地址或者视频地址使用规定的分隔符(,)处理"
			+ "\n\r长文：\n\rarticleCategory(Y):1：动态，2：长文 ,\n\rcityId(Y):城市id 400300,\n\rcontentDetail(Y) : 内容,\n\rlatitude(N):纬度,\n\rlongitude(N):经度,"
			+ "\n\rpositionName(N):位置名称,\n\rarticleUid(Y):虚拟用户uid 3345 ,\n\rtitle(Y):文章标题, "
			+ "\n\rtype(Y):文章类型, \n\rtypeDesc(Y):文章类型名称, \n\rbanner(N) :封面图片")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addArticle(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
//			HpArticle pojo = json == null ? null : JSONObject.toJavaObject(json, HpArticle.class);
//			if(pojo == null || pojo.getArticleCategory() == null || pojo.getCityId() == null
//					|| pojo.getArticleUid() == null ){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))); 
//			}
//			
//			if(pojo.getArticleCategory() == 1 && pojo.getContent() != null && pojo.getArticleType() != null){
//				pojo.setType(0);
//				hpArticleService.addPojo(pojo);
//			}else if(pojo.getArticleCategory() == 2 && pojo.getTitle() != null && pojo.getType() != null
//					&& pojo.getTypeDesc() != null){
//				hpArticleService.addPojo(pojo);
//			}else{
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))); 
//			}
//			return JSONObject.toJSONString(new RespMsg<>());
			return hpArticleResource.addArticle(json);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
}
