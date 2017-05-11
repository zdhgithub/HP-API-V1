package cn.heipiao.api.resources;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.Pole;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.service.BoardService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.PclockRecordService;
import cn.heipiao.api.service.UserTicketsService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ResultUtils;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.device.TagAliasResult;
/**
 * 推送管理
 * @ClassName: PushResource
 * @Description: TODO
 * @author zf
 * @version 2.1
 */
//@Component
//@Consumes({ MediaType.APPLICATION_JSON })
//@Produces({ MediaType.APPLICATION_JSON })
//@Path("push")
@RestController
@RequestMapping(value = "push",produces="application/json")
public class PushResource {

	private static final Logger log = LoggerFactory
			.getLogger(PushResource.class);
	@Resource
	private JPushService jPushService;
	@Resource
	private UserTicketsService userTicketsService;
	@Resource
	private PclockRecordService pclockRecordService;
	@Resource
	private BoardService boardService;
	/**
	 * 注册唯一标识
	 * @param json
	 * @return
	 * @since 2.1
	 */
//	@Path("pc")
//	@PUT
	@RequestMapping(value = "pc",method = RequestMethod.PUT,consumes = "application/json")
	public String registRid(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getInteger("uid") == null
					|| json.getString("rid") == null
					|| json.getString("type") == null
					|| json.getString("os") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			jPushService.bindMobile(json.getInteger("uid"),
					json.getString("rid"), json.getString("type"),
					json.getString("os"));
			return ResultUtils.out(Status.success);
		} catch (APIConnectionException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (APIRequestException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 催钟
	 * @param json
	 * @return
	 * @since 2.1
	 */
//	@Path("sp")
//	@POST
	@RequestMapping(value = "sp",method = RequestMethod.POST,consumes = "application/json")
	public String pushClock(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getLong("tid") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			Pole p = boardService.getById(json.getLong("tid"));
			if(p.getEnd().after(ExDateUtils.getDate())) {
				return ResultUtils.out(Status.pole_fail);
			}
			UserTickets userTicket = userTicketsService.selectTicketsByTid(json
					.getLong("tid"));
			Integer count = this.pclockRecordService.count(userTicket.getTid());
			if (count >= 6) {
				return ResultUtils.out(Status.pc_out_of_times);
			}
			List<Date> dates = this.pclockRecordService.getDates(userTicket
					.getTid());
			if (dates.size() > 0) {
				long interval = ExDateUtils.getDate().getTime()
						- dates.get(0).getTime();
				if (Math.abs(interval) < 600000) {
					return ResultUtils.out(Status.pc_time_short);
				}
			}

			boolean result = this.jPushService.sendPush(userTicket.getUid()
					.intValue(), "C", null, "您到钟了");
			if (!result) {
				return ResultUtils.out(Status.pc_fail);
			}
			this.pclockRecordService.add(userTicket.getTid());
			return ResultUtils.out(count + 1 + "");
		} catch (APIConnectionException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (APIRequestException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 给所有用户发推送
	 * @param json
	 * @return
	 * @since 2.1
	 */
//	@Path("spa")
//	@PUT
	@RequestMapping(value = "spa",method = RequestMethod.PUT,consumes = "application/json")
	public String sendPushToAll(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getString("alert") == null
					|| json.getString("type") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.jPushService.sendPushToAll(json.getString("alert"),
					json.getString("type"));
			return ResultUtils.out(Status.success);
		} catch (APIConnectionException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (APIRequestException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 推送测试
	 * @param json
	 * @return
	 * @since 2.1
	 */
//	@Path("sptest")
//	@PUT
	@RequestMapping(value = "sptest",method = RequestMethod.PUT,consumes = "application/json")
	public String sendPushTest(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json == null || json.getString("alert") == null
					|| json.getInteger("uid") == null
					|| json.getString("type") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			this.jPushService.sendPushMessage(json.getInteger("uid"),
					json.getString("alert"), json.getString("type"));
			return ResultUtils.out(Status.success);
		} catch (APIConnectionException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (APIRequestException e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 根据唯一标识查标签和别名
	 * @param rid
	 * @param type
	 * @return
	 * @since 2.1
	 */
//	@Path("{rid}/{type}")
//	@GET
	@RequestMapping(value = "{rid}/{type}",method = RequestMethod.GET)
	public String getDeviceTagAlias(@PathVariable("rid") final String rid,
			@PathVariable("type") final String type) {
		try {
			log.debug("rid:{},type:{}", rid, type);
			TagAliasResult result = JClient.getJpushClient(type)
					.getDeviceTagAlias(rid);
			return ResultUtils.out("tags:"+result.tags.toString()+" alias:"+result.alias);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}

	}
	/**
	 * 设置看板配置
	 * @param json
	 * @return
	 * @since 2.1
	 */
//	@Path("sc")
//	@PUT
	@RequestMapping(value = "sc",method = RequestMethod.PUT,consumes = "application/json")
	public String setConfig(@RequestBody JSONObject json) {
		try {
			log.debug("json:{}", json);
			if (json.getLong("id") == null || json.getInteger("type") == null
					|| json.getInteger("config") == null) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.jPushService.setConfig(
					json.getLong("id"), json.getInteger("type"),
					json.getInteger("config")));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}
	/**
	 * 查询看板配置
	 * @param id
	 * @param type
	 * @return
	 * @since 2.1
	 */
//	@Path("gc/{id}/{type}")
//	@GET
	@RequestMapping(value = "gc/{id}/{type}",method = RequestMethod.GET)
	public String getConfig(@PathVariable("id") Long id,
			@PathVariable("type") Integer type) {
		try {
			if (null == id || null == type) {
				return ResultUtils.out(Status.value_is_null_or_error);
			}
			return ResultUtils.out(this.jPushService.getConfig(id, type) + "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

}
