/**
 * 
 */
package cn.heipiao.api.resources;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.GoldCoinOrdersService;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年8月19日
 * @version 1.0
 */
@Api(tags = "充值订单模块")
@RestController
@RequestMapping(value = "orders/goldCoin",produces="application/json")
public class GoldCoinOrdersResource {

	private static final Logger logger = LoggerFactory.getLogger(GoldCoinOrdersResource.class);
	
	@Resource
	private GoldCoinOrdersService goldCoinOrdersService;
	
	/**
	 * {"uid":1,"ordersMoney":121,"tradePlatform":1,"whereIsApp":1}
	 * 
	 * 1:微信,2:支付宝
	 * 1:平台的,2:小程序的
	 * 创建充值漂币订单
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addGoldCoinOrder(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			GoldCoinOrders gco = json == null ? null : JSONObject.toJavaObject(json, GoldCoinOrders.class);
			if(gco == null || gco.getUid() == null || gco.getOrdersMoney() == null || gco.getTradePlatform() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			if(gco.getWhereIsApp() == null){
				gco.setWhereIsApp(1);
			}
			if(gco.getWhereIsApp() > 2 || gco.getWhereIsApp() < 1){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			int rs = goldCoinOrdersService.addGoldCoinOrders(gco);
			return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs),gco.getOrderId()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 充值订单取消
	 */
//	@DELETE
//	@Path("cancel/{uid}/{orderId}")
//	public String cancelGoldCoinOrder(){
//		
//		return null;
//	}
	
}
