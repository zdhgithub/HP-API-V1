package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ResultUtils;

/**
 * 
 * @author zf
 * @version 2.1
 *
 */
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Component
//@Path("/")
@RestController
@RequestMapping(value = "/",produces="application/json")
public class SystemMsgResource {
	private static final Logger logger = LoggerFactory
			.getLogger(SystemMsgResource.class);
	@Resource
	private SystemMsgService systemMsgService;
	
	/**
	 * 获取某人的C端的系统消息
	 * 
	 * @param userId
	 * @param start
	 * @param size
	 * @return
	 * @since 2.1
	 */
//	@Path("systemsg/{userId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "systemsg/{userId}/{start}/{size}",method = RequestMethod.GET)
	public String queryComments(@PathVariable("userId") final Long userId,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		if (start == null || size == null || userId == null) {
			return ResultUtils.out(Status.value_is_null_or_error);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("receiver", userId);
			params.put("start", start);
			params.put("size", size);
			params.put("type", "C");
			List<SystemMsg> result = systemMsgService.getSystemMsg(params);
			return ResultUtils.out(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

	/**
	 * 获取某人的B端的系统消息
	 * 
	 * @param userId
	 * @param start
	 * @param size
	 * @return
	 * @since 2.2
	 */
//	@Path("systemsg/{userId}/{type}/{start}/{size}")
//	@GET
	@RequestMapping(value = "systemsg/{userId}/{type}/{start}/{size}",method = RequestMethod.GET)
	public String getBMsg(@PathVariable("userId") final Long userId,
			@PathVariable("type") final String type,
			@PathVariable("start") final Integer start,
			@PathVariable("size") final Integer size) {
		if (start == null || size == null || userId == null
				|| StringUtils.isBlank(type)) {
			return ResultUtils.out(Status.value_is_null_or_error);
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("receiver", userId);
			params.put("start", start);
			params.put("size", size);
			params.put("type", type);
			List<SystemMsg> result = systemMsgService.getSystemMsg(params);
			return ResultUtils.out(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResultUtils.out(Status.error);
		}
	}

}
