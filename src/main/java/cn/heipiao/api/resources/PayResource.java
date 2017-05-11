/**
 * 
 */
package cn.heipiao.api.resources;

import java.net.InetAddress;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.OrdersService;
import cn.heipiao.api.service.PayService;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
@Api(tags = "第三方支付参数获取模块")
@RestController
@RequestMapping(value = "pay",produces="application/json")
public class PayResource {

	private static final Logger logger  = LoggerFactory.getLogger(PayResource.class);
	
	@Value("${order.outTime}")
	private Integer outTime;
	
	@Resource
	private PayService payService;
	
	@Resource
	private OrdersService ordersService;
	
	/**
	 * {"uid":1,"platform":1,"orderId":"订单号","appIp":"终端ip","body":"商品名称","hpService":1,"openid":openid}
	 * 0:商戶平台(无效),1:微信,2:支付宝
	 * hpService :黑漂业务 1:购买商品(购票),2:充值,3:票支付
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("payParam")
	@RequestMapping(value = "payParam",method = RequestMethod.POST,consumes = "application/json")
	public String payParam(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Long uid = json == null ? null : json.getLong("uid");
			Integer platform = json == null ? null : json.getIntValue("platform");
			String orderId = json == null ? null : json.getString("orderId");
			String appIp = json == null ? null : json.getString("appIp");
			String body = json == null ? null : json.getString("body");
			Integer hpService = json == null ? null : json.getIntValue("hpService");
			String openid = json == null ? null : json.getString("openid");
			
			if(uid == null || platform == null || StringUtils.isBlank(orderId) || hpService == null
					|| StringUtils.isBlank(appIp) || StringUtils.isBlank(body)){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			appIp = appIp.matches("^([1-9]?[1-9]?[0-9]{1}\\.){3}[1-9]?[1-9]?[0-9]{1}$") ? appIp : InetAddress.getLocalHost().getHostAddress();
//			Orders order = ordersService.selectByOrderId(orderId);
			//判断订单
//			if(order == null || order.getStatus().intValue() != 0
//					|| ExDateUtils.getCalendar().getTimeInMillis() - order.getCreateTime().getTime() >= outTime * 1000){
//				return JSONObject.toJSONString(new RespMsg<>(Status.orders_error,RespMessage.getRespMsg(Status.orders_error)));
//			}
			String rs = payService.generatePayParam(uid,platform,orderId,appIp,body,hpService,openid);
			return rs;
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
//	/**
//	 * 微信小程序获取支付参数
//	 * 
//	 * {"uid":1,"orderId":"订单号","appIp":"终端ip","body":"商品名称","hpService":1}
//	 * hpService :黑漂业务 1:购买商品(购票),2:充值,3:票支付
//	 * @return
//	 */
//	@Path("wxMiniParam")
//	@POST
//	public String wxMiniParam(@RequestBody JSONObject json){
//		try {
//			logger.debug("json:{}",json);
//			Long uid = json == null ? null : json.getLong("uid");
//			String orderId = json == null ? null : json.getString("orderId");
//			String appIp = json == null ? null : json.getString("appIp");
//			String body = json == null ? null : json.getString("body");
//			Integer hpService = json == null ? null : json.getIntValue("hpService");
//			if(uid == null || StringUtils.isBlank(orderId) || hpService == null
//					|| StringUtils.isBlank(appIp) || StringUtils.isBlank(body)){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			appIp = appIp.matches("^([1-9]?[1-9]?[0-9]{1}\\.){3}[1-9]?[1-9]?[0-9]{1}$") ? appIp : InetAddress.getLocalHost().getHostAddress();
//			
//			return payService.generatePayParam(uid,1,orderId,appIp,body,hpService);
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
//		}
//	}
	
	
	/**
	 * {"uid":1,"platform":3,"orderId":"订单号"}
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("payByAccount")
//	public String payByAccountBalance(@RequestBody JSONObject json){
//		try {
//			logger.debug("json:{}",json);
//			Long uid = json == null ? null : json.getLong("uid");
//			Integer platform = json == null ? null : json.getIntValue("platform");
//			String orderId = json == null ? null : json.getString("orderId");
//			if(uid == null || platform == null || platform.intValue() != 3 || orderId == null){
//				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
//			}
//			int respInt = payService.payOrderConfirm(orderId);
//			return JSONObject.toJSONString(new RespMsg<>(respInt,RespMessage.getRespMsg(respInt)));
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
//		}
//		
//	}
	
}
