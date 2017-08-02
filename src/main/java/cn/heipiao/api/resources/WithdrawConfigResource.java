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

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.WithdrawConfig;
import cn.heipiao.api.service.WithdrawConfigService;

/**
 * @author wzw
 * @date 2016年9月26日
 * @version 1.0
 */
//@Component
//@Path("withdrawConfig/")
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
@RestController
@RequestMapping(value = "withdrawConfig",produces="application/json")
public class WithdrawConfigResource {

	private static final Logger logger = LoggerFactory.getLogger(WithdrawConfigResource.class);
	
	@Resource
	private WithdrawConfigService withdrawConfigService;
	
//	/**
//	 * cp
//	 * 
//	 * {"delayForDay":0,"withdrawRate":0.02,"withdrawRuleId":1,"serviceId":1}
//	 * 
//	 * 添加提现规则
//	 * @return
//	 */
//	@POST
//	@Path("create")
//	public String addConfig(JSONObject json){
//		try {
//			logger.info("json:{}", json);
//			WithdrawConfig wc = JSONObject.toJavaObject(json, WithdrawConfig.class);
//			if(wc == null || wc.getDelayForDay() == null || wc.getWithdrawRate() == null
//					||wc.getWithdrawRuleId() == null || wc.getServiceId() == null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			WithdrawConfig w = withdrawConfigService.selectByRuleIdAndDelayForDay(wc.getServiceId(),wc.getWithdrawRuleId(), wc.getDelayForDay());
//			if(w != null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			return JSONObject.toJSONString(new RespMsg<>());
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
//		}
//	}
//	
//	/**
//	 * 修改规则
//	 * {"delayForDay":0,"withdrawRate":0.02,"withdrawRuleId":1}
//	 * cp
//	 * @return
//	 */
//	@PUT
//	@Path("modification")
//	public String modifyConfig(JSONObject json){
//		try {
//			logger.info("json:{}", json);
//			WithdrawConfig wc = JSONObject.toJavaObject(json, WithdrawConfig.class);
//			if(wc == null || wc.getDelayForDay() == null || wc.getWithdrawRate() == null
//					||wc.getWithdrawRuleId() == null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			withdrawConfigService.updatePojo(wc);
//			return JSONObject.toJSONString(new RespMsg<>());
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
//		}
//	}
//	
//	/**
//	 * cp
//	 * @return
//	 */
//	@GET
//	@Path("listAll")
//	public String listAllConfig(){
//		try {
//			List<WithdrawConfig>  pojos = withdrawConfigService.selectAll();
//			return JSONObject.toJSONString(new RespMsg<>(pojos));
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
//		}
//	}
	
	/**
	 * 1.2
	 * api
	 * @param ruleId
	 * @return
	 */
//	@GET
//	@Path("list/{ruleId}")
	@RequestMapping(value = "list/{ruleId}",method = RequestMethod.GET)
	public String listConfig(@PathVariable("ruleId")Integer ruleId){
		try {
			List<WithdrawConfig>  pojos = withdrawConfigService.selectAllByRuleId(ruleId, 1);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 2.0
	 * api
	 * @param ruleId
	 * @return
	 */
//	@GET
//	@Path("list/{serviceId}/{ruleId}")
	@RequestMapping(value = "list/{serviceId}/{ruleId}",method = RequestMethod.GET)
	public String listConfig(@PathVariable("ruleId")Integer ruleId,@PathVariable("serviceId")Integer serviceId){
		try {
			List<WithdrawConfig>  pojos = withdrawConfigService.selectAllByRuleId(ruleId, serviceId);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
}
