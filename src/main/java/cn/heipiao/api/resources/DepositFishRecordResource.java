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

import cn.heipiao.api.pojo.DepositFishRecord;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.DepositFishRecordService;

/**
 * @author zf
 * @version 1.0
 * @description 存鱼记录
 * @date 2016年6月30日
 */
//@Path("deposits")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@Component
@RestController
@RequestMapping(value = "deposits",produces="application/json")
public class DepositFishRecordResource {
	private static final Logger logger = LoggerFactory
			.getLogger(DepositFishRecordResource.class);
	@Resource
	private DepositFishRecordService depositFishRecordService;

	/**
	 * 查询钓场所有用户的存鱼记录
	 * 
	 * @param siteId
	 * @return
	 * @since 1.0
	 */
//	@Path("records/{siteId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "records/{siteId}/{start}/{size}",method = RequestMethod.GET)
	public String queryDprecordsBySite(
			@PathVariable("siteId") final Integer siteId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		List<DepositFishRecord> list = null;
		// int counts = 0;
		try {
			list = depositFishRecordService.getRecordsBySite(siteId, start,
					size);
			// counts = depositFishRecordService.countRecords(siteId);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:", e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<DepositFishRecord>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<DepositFishRecord>>(
				list));
	}

	/**
	 * 查询用户在钓场的存鱼记录列表
	 * 
	 * @param siteId
	 * @param userId
	 * @return
	 * @since 1.0
	 */
//	@Path("records/{siteId}/{userId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "records/{siteId}/{userId}/{start}/{size}",method = RequestMethod.GET)
	public String queryDprecordsByUserSite(
			@PathVariable("siteId") final Integer siteId,
			@PathVariable("userId") final Long userId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		List<DepositFishRecord> list = null;
		try {
			list = depositFishRecordService.getRecordsBySiteOfUser(userId,
					siteId,start,size);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:", e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<DepositFishRecord>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<DepositFishRecord>>(
				list));
	}
}
