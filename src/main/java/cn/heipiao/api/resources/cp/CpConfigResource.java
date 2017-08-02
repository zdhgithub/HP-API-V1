/**
 * 
 */
package cn.heipiao.api.resources.cp;

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
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.UserShareCityConfig;
import cn.heipiao.api.pojo.WithdrawConfig;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.UserShareCityConfigService;
import cn.heipiao.api.service.WithdrawConfigService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * cp所有的配置规则类
 * 
 * 
 * @author wzw
 * @date 2016年11月26日
 * @version 2.2
 */
@Api(tags = "系统配置规则模块")
@RestController
@RequestMapping(value = "cp/config",produces="application/json")
public class CpConfigResource {

	private final static Logger logger = LoggerFactory.getLogger(CpConfigResource.class);
	
	@Resource
	private UserShareCityConfigService userShareCityConfigService;
	
	@Resource
	private WithdrawConfigService withdrawConfigService;
	
	/**
	 * 修改分享获得漂币
	 * 总开关
	 * {"status":true}
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("share/status")
	@RequestMapping(value = "share/status",method = RequestMethod.PUT,consumes = "application/json")
	public String setShareStatus(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			Boolean status = json == null ? null:json.getBoolean("status");
			if(status == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			userShareCityConfigService.setShareStatus(status);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 获取分享获得漂币
	 * 总开关
	 * 
	 * @param json
	 * @return
	 */
//	@GET
//	@Path("share/status")
	@RequestMapping(value = "share/status",method = RequestMethod.GET)
	public String getShareStatus(){
		try {
			Boolean status = userShareCityConfigService.getShareStatus();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("status", status == null ? false : status);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 分享获得漂币
	 * 
	 * 城市 修改
	 * 
	 * {"cityId":400300,"status":true,"limit":1,"amount":100,"desc":"描述"}
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("share/city/modification")
	@RequestMapping(value = "share/city/modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyShareCityStatus(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			UserShareCityConfig pojo = json == null ? null : JSONObject.toJavaObject(json, UserShareCityConfig.class);
			if(pojo == null || pojo.getStatus() == null || pojo.getCityId() == null
					|| pojo.getAmount() == null || pojo.getLimit() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			userShareCityConfigService.modifyShareCity(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 分享获得漂币
	 * 
	 * 城市 删除
	 * 
	 * @return
	 */
//	@DELETE
//	@Path("share/city/remove/{cityId}")
	@RequestMapping(value = "share/city/remove/{cityId}",method = RequestMethod.DELETE)
	public String modifyShareCityStatus(@PathVariable("cityId")Integer cityId){
		try {
			userShareCityConfigService.deleteShareCity(cityId);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	/**
	 * 分享获得漂币
	 * 
	 * 城市 添加
	 * 
	 * {"cityId":400300,"status":true,"limit":1,"amount":100,"desc":"描述"}
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("share/city/add")
	@RequestMapping(value = "share/city/add",method = RequestMethod.POST,consumes = "application/json")
	public String addShareCity(@RequestBody JSONObject json){
		try {
			logger.info("json:{}",json);
			UserShareCityConfig pojo = json == null ? null : JSONObject.toJavaObject(json, UserShareCityConfig.class);
			if(pojo == null || pojo.getStatus() == null || pojo.getCityId() == null
					|| pojo.getAmount() == null || pojo.getLimit() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error));
			}
			//每天100漂币
//			pojo.setAmount(100);
			pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			//默认一次
//			pojo.setLimit(1);
			userShareCityConfigService.addShareCity(pojo);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	
	/**
	 * 获取分享渔获开放的城市列表
	 * 
	 * 
	 * @param json
	 * @return
	 */
//	@GET
//	@Path("share/city/list/{index}/{size}")
	@RequestMapping(value = "share/city/list/{index}/{size}",method = RequestMethod.GET)
	public String listShareCity(@PathVariable("index")Integer index,@PathVariable("size")Integer size){
		try {
			Map<String,Object> m = new HashMap<>();
			m.put("index", (index < 1 ? 0 : index - 1) * size);
			m.put("size", size);
			List<UserShareCityConfig> list = userShareCityConfigService.listShareCity(m);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("data", list);
			map.put("total", list.size() <= 0 ? 0 : list.size() < size ? list.size()
					: userShareCityConfigService.countShareCity());
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	
	
	/**
	 * cp
	 * 
	 * {"delayForDay":0,"withdrawRate":0.02,"withdrawRuleId":1,"serviceId":1}
	 * 
	 * 添加提现规则
	 * @return
	 */
//	@POST
//	@Path("withdrawConfig/create")
	@RequestMapping(value = "withdrawConfig/create",method = RequestMethod.POST,consumes = "application/json")
	public String addConfig(@RequestBody JSONObject json){
		try {
			logger.info("json:{}", json);
			WithdrawConfig wc = JSONObject.toJavaObject(json, WithdrawConfig.class);
			if(wc == null || wc.getDelayForDay() == null || wc.getWithdrawRate() == null
					||wc.getWithdrawRuleId() == null || wc.getServiceId() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			WithdrawConfig w = withdrawConfigService.selectByRuleIdAndDelayForDay(wc.getServiceId(),wc.getWithdrawRuleId(), wc.getDelayForDay());
			if(w != null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 修改提现规则
	 * {"delayForDay":0,"withdrawRate":0.02,"withdrawRuleId":1}
	 * cp
	 * @return
	 */
//	@PUT
//	@Path("withdrawConfig/modification")
	@RequestMapping(value = "withdrawConfig/modification",method = RequestMethod.PUT,consumes = "application/json")
	public String modifyConfig(@RequestBody JSONObject json){
		try {
			logger.info("json:{}", json);
			WithdrawConfig wc = JSONObject.toJavaObject(json, WithdrawConfig.class);
			if(wc == null || wc.getDelayForDay() == null || wc.getWithdrawRate() == null
					||wc.getWithdrawRuleId() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			withdrawConfigService.updatePojo(wc);
			return JSONObject.toJSONString(new RespMsg<>());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * cp
	 * 提现规则的列表
	 * @return
	 */
//	@GET
//	@Path("withdrawConfig/listAll")
	@RequestMapping(value = "withdrawConfig/listAll",method = RequestMethod.GET)
	public String listAllConfig(){
		try {
			List<WithdrawConfig>  pojos = withdrawConfigService.selectAll();
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
