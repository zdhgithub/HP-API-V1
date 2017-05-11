/**
 * 
 */
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
import cn.heipiao.api.pojo.CouponFeesConfig;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.service.CouponFeesConfigService;

/**
 * @author wzw
 * @date 2016年11月10日
 * @version 1.0
 */
//@Component
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//@Path("rule/fees")
@RestController
@RequestMapping(value = "rule/fees",produces="application/json")
public class ConfigRuleResource {

	private static final Logger logger = LoggerFactory.getLogger(ConfigRuleResource.class);

//	@Resource
//	private ConfigRuleService configRuleService;
	@Resource
	private ConfigService configService;
	
	@Resource
	private CouponFeesConfigService couponFeesConfigService;
	
	/**
	 * 获取优惠券收费规则
	 * @param category
	 * @return
	 */
//	@GET
//	@Path("coupon/{serviceId}/{categoryId}")
	@RequestMapping(value = "coupon/{serviceId}/{categoryId}",method = RequestMethod.GET)
	public String couponFeesRule(@PathVariable("serviceId") Integer serviceId,@PathVariable("categoryId") Integer categoryId){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			List<CouponFeesConfig>	list = couponFeesConfigService.getCouponFeeRule(serviceId,categoryId);
			map.put("rules", list);
			map.put("ruleDetail", "");
			if(list.size() > 0){
				return JSONObject.toJSONString(new RespMsg<>(map));
			}
			DictConfig dict = configService.selectByType(ConfigService.coupon_rule_detail);
			map.put("ruleDetail", dict == null ? "" : dict.getValue());
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error, RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 获取推荐用户奖励规则
	 * @return
	 */
//	@GET
//	@Path("recommend")
	@RequestMapping(value = "recommend",method = RequestMethod.GET)
	public String recommendUserRule(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("login", 200);
		map.put("consume", 300);
		return JSONObject.toJSONString(new RespMsg<>(map));
	}
	
}
