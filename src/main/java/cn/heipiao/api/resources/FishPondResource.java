/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.List;

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

import cn.heipiao.api.constant.FishSiteConstant;
import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.FishPondService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
@Api(tags = "鱼塘模块")
@RestController
@RequestMapping(value = "sites/pond",produces="application/json")
public class FishPondResource {
	
	private static final Logger logger = LoggerFactory.getLogger(FishPondResource.class);
	
	@Resource
	private FishPondService fishPondService;
	
	@Resource
	private FishSiteService fishSiteService;
	
	/**
	 * 添加池塘
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addPond(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			FishPond pojo = JSONObject.toJavaObject(json, FishPond.class);
			if(pojo == null || pojo.getFishSiteId() == null || StringUtils.isBlank(pojo.getFishPondName())){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			FishSite fs = fishSiteService.selectById(pojo.getFishSiteId());
			if(fs == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.fish_site_not_exists,RespMessage.getRespMsg(Status.fish_site_not_exists)));
			}
			pojo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
			pojo.setStatus(FishSiteConstant.ONE);
			fishPondService.insertPojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取池塘
	 * @param siteId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{siteId}")
	@RequestMapping(value = "list/{siteId}",method = RequestMethod.GET)
	public String queryBySiteId(@PathVariable("siteId") Integer siteId){
		try {
			logger.debug("siteId:{}",siteId);
			List<FishPond> pojos = fishPondService.selectBySiteId(siteId);
			return JSONObject.toJSONString(new RespMsg<List<FishPond>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 修改池塘
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("modification")
	@RequestMapping(value = "modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyPond(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			FishPond pojo = JSONObject.toJavaObject(json, FishPond.class);
			if(pojo == null || pojo.getFishSiteId() == null || StringUtils.isBlank(pojo.getFishPondName()) || pojo.getFishPondId() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			fishPondService.updatePojo(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 删除池塘
	 * @param siteId
	 * @param pondId
	 * @return
	 */
//	@DELETE
//	@Path("removel/{siteId}/{pondId}")
	@RequestMapping(value = "removel/{siteId}/{pondId}",method = RequestMethod.DELETE)
	public String removePojo(@PathVariable("siteId") Integer siteId ,@PathVariable("pondId") Integer pondId){
		try {
			logger.debug("siteId:{},pondId:{}",siteId,pondId);
			FishPond pojo = new FishPond();
			pojo.setFishSiteId(siteId);
			pojo.setFishPondId(pondId);
//			int rs = fishPondService.deletePojo(pojo);
//			return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs)));

			return JSONObject.toJSONString(new RespMsg<>(Status.function_not_open,RespMessage.getRespMsg(Status.function_not_open)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
}
