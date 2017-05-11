/**
 * 
 */
package cn.heipiao.api.resources;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.MenuRegion;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.RegionService;

/**
 * @author wzw
 * @date 2016年6月22日
 * @version 1.0
 */
//@Path("region")
//@Component
//@Produces({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=UTF-8" })
@RestController
@RequestMapping(value = "region",produces="application/json")
public class RegionResource {

	private static final Logger logger = LoggerFactory
			.getLogger(RegionResource.class);

	@Resource
	private RegionService regionService;

//	@GET
//	@Path("list")
	@RequestMapping(value = "list",method = RequestMethod.GET)
	public String getRegionAll() {
		try {
			List<Region> list = regionService.selectAll();
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	

	
//	@GET
//	@Path("menulist/{type}")
	@RequestMapping(value = "menulist/{type}",method = RequestMethod.GET)
	public String getRegionFishSiteCount(
			@PathVariable("type") final Integer type) {
		try {
			List<MenuRegion> list = regionService.selectAllAndCount(type);
			return JSONObject.toJSONString(new RespMsg<List<MenuRegion>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}

//	@GET
//	@Path("citys")
	@RequestMapping(value = "citys",method = RequestMethod.GET)
	public String getCitys() {
		try {
			List<Region> list = regionService.selectCitys();
			return JSONObject.toJSONString(new RespMsg<List<Region>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
//	@GET
//	@Path("all")
	@RequestMapping(value = "all",method = RequestMethod.GET)
	public String getAll() {
		try {
			List<Region> list = regionService.getRegionStepByStep();
			return JSONObject.toJSONString(new RespMsg<List<Region>>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}

//	@PUT
//	@Path("opencity/{id}")
	@RequestMapping(value = "opencity/{id}",method = RequestMethod.PUT,consumes = "application/json")
	public String appointOpenCity(@PathVariable("id") final Integer id) {
		try {
			logger.debug("id:{}", id);
			regionService.appointOpenCity(id);
			return JSONObject
					.toJSONString(new RespMsg<Integer>(Status.success));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
	
	/**
	 * 获取招募中的城市
	 * @return
	 */
//	@GET
//	@Path("recruit")
	@RequestMapping(value = "recruit",method = RequestMethod.GET)
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
	
}
