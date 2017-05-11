package cn.heipiao.api.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.Welcome;
import cn.heipiao.api.service.WelcomeService;

/**
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
//@Path("/welcome")
//@Produces({ MediaType.APPLICATION_JSON})
//@Consumes({ MediaType.APPLICATION_JSON})
//@Component
@RestController
@RequestMapping(value = "welcome",produces="application/json")
public class WelcomeResource {

	private static final Logger logger = LoggerFactory.getLogger(WelcomeResource.class);

	@Resource
	private WelcomeService welcomeService;
	

	/**
	 * 通过终端标识查询欢迎(启动)页面配置
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("/get/{terminal}/{updatetime}")
	@RequestMapping(value = "get/{terminal}/{updatetime}",method = RequestMethod.GET)
	public String get(@PathVariable("terminal") final Integer terminal,
			@PathVariable("updatetime") final Long updatetime) {
		try {
			logger.debug("terminal:{} updatetime:{}", terminal, updatetime);
			Map<String, Object> ret = new HashMap<>();
			ret.put("terminal", terminal);
			ret.put("updatetime", updatetime);
			if (terminal == 0 || terminal == 1 || terminal == 2) {
				if(terminal == 0){
					List<Welcome> list = welcomeService.getWelcomeAll();
					return JSONObject.toJSONString(new RespMsg<List<Welcome>>(list));
				}else{
					Welcome pojo = welcomeService.getWelcomeByTerminal(terminal);
					if(pojo == null){
						return JSONObject.toJSONString(new RespMsg<>());
					}
					if(updatetime != pojo.getUpdatetime().getTime()){
						return JSONObject.toJSONString(new RespMsg<Welcome>(pojo));
					}else{
						ret.put("butShowFlag", pojo.getButShowFlag());
						ret.put("delayTime", pojo.getDelayTime());
						return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(ret));
					}
				}
			}else{
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
