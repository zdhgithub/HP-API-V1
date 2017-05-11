/**
 * 
 */
package cn.heipiao.api.resources;

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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.FishPostNews;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.FishPostNewsService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年6月14日
 * @version 1.0
 */
@RestController
@RequestMapping(value = "sites/fishPost",produces="application/json")
public class FishPostNewsResource {

	private static final Logger logger = LoggerFactory.getLogger(FishPostNewsResource.class);
	
	@Resource
	private FishPostNewsService fishPostNewsService;
	
	/**
	 * 钓场全部动态列表
	 * @param fishSiteId
	 * @param start
	 * @param size
	 * @return
	 */
//	@Path("list/{fishSiteId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{fishSiteId}/{start}/{size}",method = RequestMethod.GET)
	public String selectBySiteId(@PathVariable("fishSiteId") Integer fishSiteId,
			@PathVariable("start") Integer start,@PathVariable("size") Integer size){
		try {
			logger.debug("fishSiteId:{},start:{},size:{}", fishSiteId,start,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", fishSiteId);
			map.put("start", start);
			map.put("size", size);
			List<FishPostNews> pojos = fishPostNewsService.selectBySiteId(map);
			return JSONObject.toJSONString(new  RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 钓场某个类型动态列表
	 * @param fishSiteId
	 * @param start
	 * @param size
	 * @return
	 */
//	@Path("list/{fishSiteId}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{fishSiteId}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String selectBySiteIdAndType(@PathVariable("fishSiteId") Integer fishSiteId,
			@PathVariable("type") Integer type,
			@PathVariable("start") Integer start,@PathVariable("size") Integer size){
		try {
			logger.debug("fishSiteId:{},start:{},size:{}", fishSiteId,start,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", fishSiteId);
			map.put("start", start);
			map.put("size", size);
//			map.put("type", type);
			List<FishPostNews> pojos = fishPostNewsService.selectBySiteIdAnd(map);
			return JSONObject.toJSONString(new  RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 放鱼信息列表
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/putFish/{uid}/{lng}/{lat}/{regionId}/{start}/{size}")
	@RequestMapping(value = "list/putFish/{uid}/{lng}/{lat}/{regionId}/{start}/{size}",method = RequestMethod.GET)
	public String selectByPutFish(@PathVariable("uid") Long uid,@PathVariable("regionId") Integer regionId,
			@PathVariable("lng") Double lng,@PathVariable("lat") Double lat,
			@PathVariable("start") Integer start,@PathVariable("size") Integer size){
		try {
			logger.debug("start:{},size:{},regionId:{},lng:{},lat:{},uid:{}",start,size,regionId,lng,lat,uid);
			if(Math.abs(lng) > 180 || Math.abs(lat) > 90){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
//			double[] lls = ExMapUtil.getAround(lat.doubleValue(), lng.doubleValue(), radius * 1000);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lng", lng);
			map.put("lat", lat);
//			map.put("minLng", lls[0]);
//			map.put("minLat", lls[1]);
//			map.put("maxLng", lls[2]);
//			map.put("maxLat", lls[3]);
			//城市id
			map.put("regionId", regionId);
			map.put("uid", uid);
			map.put("start", start);
			map.put("size", size);
			List<FishPostNews> pojos = fishPostNewsService.selectByPutFish(map);
			return JSONObject.toJSONString(new  RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 
	 * {"fishSiteId":1,"picture":"","content":"","type":"1"}
	 * 
	 * @param json
	 * @return
	 */
//	@Path("create")
//	@POST
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addPojo(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			FishPostNews pojo = JSONObject.toJavaObject(json, FishPostNews.class);
			if(pojo == null || StringUtils.isBlank(pojo.getType()) 
					|| pojo.getFishSiteId() == null 
					|| StringUtils.isBlank(pojo.getPicture()) 
					&& StringUtils.isBlank(pojo.getContent())){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			if(pojo.getPicture()!=null && pojo.getMainPicture()==null){
				pojo.setMainPicture(pojo.getPicture().split(",")[0]);
			}
			fishPostNewsService.insertPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 
	 * @param nid
	 * @return
	 */
//	@Path("remove/{nid}")
//	@DELETE
	@RequestMapping(value = "remove/{nid}",method = RequestMethod.DELETE)
	@Deprecated
	public String removePojo(@PathVariable("nid") Long nid){
		try {
			logger.debug("nid:{}", nid);
			fishPostNewsService.deletePojo(nid);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
