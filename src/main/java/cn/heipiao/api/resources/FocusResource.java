/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
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
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Focus;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.FocusService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "sites/focus",produces="application/json")
public class FocusResource {

	private static final Logger logger = LoggerFactory.getLogger(FocusResource.class);
	
	@Resource
	private FocusService focusService;
	
	@Resource
	private FishSiteService fishSiteService;

	/**
	 * 
	 *  查询用户是否关注钓场
	 * {"uid":1,"siteId":12}
	 * @param json
	 * @return
	 */
//	@Path("{uid}/{siteId}")
//	@GET
	@RequestMapping(value = "{uid}/{siteId}",method = RequestMethod.GET)
	public String isFollow(@PathVariable("uid")Long uid,@PathVariable("siteId")Integer siteId){
		try {
			Focus pojo = focusService.selectByUidAndFid(uid,siteId);
			FishSite fs = fishSiteService.selectById(siteId);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("isFocus", pojo != null);
			map.put("count", fs == null ? 0 : fs.getFocusCount());
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 
	 *  关注钓场
	 * {"uid":1,"fid":12}
	 * @param json
	 * @return
	 */
//	@Path("follow")
//	@POST
	@RequestMapping(value = "follow",method = RequestMethod.POST,consumes = "application/json")
	public String follow(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}" ,json);
			Focus f = JSONObject.toJavaObject(json, Focus.class);
			if(f == null || f.getUid() == null || f.getFid() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishSite fs = fishSiteService.selectById(f.getFid());
			if(fs == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists,RespMessage.getRespMsg(Status.fish_site_not_exists)));
			}
			Focus pojo = focusService.selectByUidAndFid(f.getUid(),f.getFid());
			if(pojo == null){
				f.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
				focusService.addFollow(f);
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 取消关注钓场
	 * @param uid
	 * @param fid
	 * @return
	 */
//	@Path("unfollow/{uid}/{fid}")
//	@DELETE
	@RequestMapping(value = "unfollow/{uid}/{fid}",method = RequestMethod.DELETE)
	public String unfollow(@PathVariable("uid")Long uid,@PathVariable("fid")Integer fid){
		try {
			logger.debug("uid:{},fid:{}",uid, fid);
			Focus f = new Focus();
			f.setUid(uid);
			f.setFid(fid);
			focusService.deleteFollow(f);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取关注钓场的列表
	 * @param uid
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/list/{uid}/{start}/{size}")
	@RequestMapping(value = "/list/{uid}/{start}/{size}",method = RequestMethod.GET)
	public String queryByFocus(@PathVariable("uid") Integer uid,
			@PathVariable("start") Integer start,@PathVariable("size") Integer size){
		try {
			logger.debug("uid:{},start:{},size:{}",uid, start,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("start", start);
			map.put("size", size);
			List<FishSite> pojos = focusService.selectByFocus(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 获取关注钓场列表
	 * @param uid
	 * @param lng
	 * @param lat
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("{uid}/{lat}/{lng}/{start}/{size}")
	@RequestMapping(value = "{uid}/{lat}/{lng}/{start}/{size}",method = RequestMethod.GET)
	public String focusSiteList(@PathVariable("uid") Long uid,@PathVariable("lng") Double lng, @PathVariable("lat") Double lat,
			@PathVariable("start") Integer start, @PathVariable("size") Integer size){
		
		try {
			logger.info("uid:{},lng:{},lat:{},start:{},size:{}",uid,lng,lat,start,size);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", start);
			map.put("size", size);
			map.put("lng", lng);
			map.put("lat", lat);
			map.put("uid", uid);
			List<FishSite> pojos = fishSiteService.selectByFocusList(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
