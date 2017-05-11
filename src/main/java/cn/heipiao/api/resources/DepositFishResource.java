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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.DepositFishExtend;
import cn.heipiao.api.pojo.DepositFishTicketRecord;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.DepositFishService;
import io.swagger.annotations.Api;

/**
 * @author zf
 * @version 1.0
 * @description 存鱼resource
 * @date 2016年6月29日
 */
@Api(tags = "存鱼模块")
@RestController
@RequestMapping(value = "deposits",produces="application/json")
public class DepositFishResource {
	private static final Logger logger = LoggerFactory
			.getLogger(DepositFishResource.class);
	@Resource
	private DepositFishService depositFishService;

	/**
	 * 统计排行榜
	 * 
	 * @param siteId
	 * @return 
	 * @since 1.0
	 */
//	@Path("{siteId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "{siteId}/{start}/{size}",method = RequestMethod.GET)
	public String getRankings(@PathVariable("siteId") final Integer siteId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		List<DepositFish> list = null;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("siteId", siteId);
			param.put("start", start);
			param.put("size", size);
			list = depositFishService.getDepositFishsBySite(param);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<DepositFish>>(list));
	}

	/**
	 * 统计当前存鱼总额
	 * 
	 * @param siteId
	 * @return
	 * @since 1.0
	 */
//	@Path("amount/{siteId}")
//	@GET
	@RequestMapping(value = "amount/{siteId}",method = RequestMethod.GET)
	public String getAmountBySite(@PathVariable("siteId") final Integer siteId) {
		Double total = 0D;
		try {
			total = depositFishService.countDepositFishBySite(siteId);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<Double>(total));
	}

	/**
	 * 查询user的存鱼列表
	 * 
	 * @param userId
	 * @return
	 * @since 1.0
	 */
//	@Path("list/{userId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "list/{userId}/{start}/{size}",method = RequestMethod.GET)
	public String getUserDeposits(@PathVariable("userId") final Long userId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		List<DepositFishExtend> list = null;
		try {
			list = depositFishService
					.getDepositFishsByUser(userId, start, size);
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFishExtend>(
					Status.error));
		}
		return JSONObject.toJSONString(new RespMsg<List<DepositFishExtend>>(
				list));
	}

	/**
	 * 查询某个用户在某个钓场的存鱼余额
	 * 
	 * @param userId
	 * @return
	 * @since 1.0
	 */
//	@Path("{siteId}/{userId}")
//	@GET
	@RequestMapping(value = "{siteId}/{userId}",method = RequestMethod.GET)
	public String getDepositesForUser(
			@PathVariable("userId") final Integer userId,
			@PathVariable("siteId") final Integer siteId) {
		try {
			logger.debug("用户ID:{},钓场ID:{}", userId, siteId);
			Double count = depositFishService.getDepositesForUser(userId,
					siteId);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("balance", count);
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFishExtend>(
					Status.error));
		}
	}

	/**
	 * 给user存鱼
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@POST
	@RequestMapping(method = RequestMethod.POST,consumes = "application/json")
	public String saveDeposit(@RequestBody JSONObject json) {
		try {
			logger.info("json:{}", json);
			if (json == null || json.getInteger("userId") == null
					|| json.getLong("ticketId") == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			DepositFishTicketRecord dr = depositFishService.getDFTR(
					json.getInteger("userId"), json.getLong("ticketId"));
			if (dr != null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.DepositFished));
			}
			DepositFish dp = JSONObject.toJavaObject(json, DepositFish.class);
			depositFishService.saveDepositFish(dp);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.success));
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.error));
		}
	}

	/**
	 * 查询在线用户是否已存过鱼
	 * 
	 * @param json
	 * @return
	 * @since 1.0
	 */
//	@PUT
//	@Path("yesornot")
	@RequestMapping(value = "yesornot",method = RequestMethod.PUT,consumes = "application/json")
	public String getYesOrNot(@RequestBody JSONObject json) {
		try {
			logger.info("json:{}", json);
			if (json == null || json.getInteger("userId") == null
					|| json.getLong("ticketId") == null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.value_is_null_or_error));
			}
			DepositFishTicketRecord dr = depositFishService.getDFTR(
					json.getInteger("userId"), json.getLong("ticketId"));
			if (dr != null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(
						Status.DepositFished));
			} else {
				return JSONObject.toJSONString(new RespMsg<DepositFish>(
						Status.success));
			}
		} catch (Exception e) {
			logger.error("ExceptionMessage:{},printStackTrace:",
					e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<DepositFish>(
					Status.error));
		}
	}

}
