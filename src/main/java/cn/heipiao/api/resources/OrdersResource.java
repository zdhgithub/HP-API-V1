/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.OrdersDetails;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.OrdersService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
@Api(tags = "购票订单模块")
@RestController
@RequestMapping(value = "orders/",produces="application/json")
public class OrdersResource {

	private static final Logger logger = LoggerFactory
			.getLogger(OrdersResource.class);

	@Resource
	private OrdersService ordersService;

	/**
	 * 创建购票订单
	 * 
	 *
	 * {"uid": 1,"fishSiteId": 61030013312,"ordersMoney": "50.5","payMoney":
	 * "40.5","depositMoney": "20","goldCoinMoney":100, "couponsMoney":
	 * "50","couponId": "123","tradePlatform":1 "ordersDetails": [{"goodId":
	 * "","payPrice": "","amount": 3}],"whereIsApp":1}
	 * whereIsApp  1:平台的,2:小程序的
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addOrder(@RequestBody JSONObject json) {

		try {
			logger.debug("json:{}", json);
			Orders pojo = json == null ? null : JSONObject.toJavaObject(json,
					Orders.class);
			if (pojo == null || pojo.getUid() == null
					|| pojo.getFishSiteId() == null
					|| pojo.getOrdersMoney() == null
					|| pojo.getPayMoney() == null
					|| pojo.getDepositMoney() == null
					|| pojo.getOrdersDetails() == null
					|| pojo.getGoldCoinMoney() == null
					|| pojo.getTradePlatform() == null) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.value_is_null_or_error, RespMessage
								.getRespMsg(Status.value_is_null_or_error)));
			}
			// 判断详情字段
			for (OrdersDetails od : pojo.getOrdersDetails()) {
				if (od.getGoodId() == null || od.getPayPrice() == null
						|| od.getAmount() <= 0)
					return JSONObject
							.toJSONString(new RespMsg<>(
									Status.value_is_null_or_error,
									RespMessage
											.getRespMsg(Status.value_is_null_or_error)));
			}
			Timestamp t = new Timestamp(ExDateUtils.getDate().getTime());
			pojo.setCreateTime(t);
			// pojo.setPayTime(t);
			pojo.setStatus(0);
			pojo.setRefundFee(0d);
			pojo.setEarningsGoldCoinMoney(0);
			pojo.setOtherCouponsMoney(0);
			pojo.setCouponsFees(0);
			if(pojo.getWhereIsApp() == null){
				pojo.setWhereIsApp(1);
			}
			if(pojo.getWhereIsApp() > 2 || pojo.getWhereIsApp() < 1){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}

			int rs = ordersService.addOrder(pojo);
			logger.info("pojo-orderId:{}", JSONObject.toJSONString(pojo));
			return JSONObject.toJSONString(new RespMsg<>(rs, RespMessage
					.getRespMsg(rs), pojo.getOrderId()));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 取消购票订单
	 * @param orderId
	 * @param uid
	 * @return
	 */
//	@Path("cancel/{uid}/{orderId}")
//	@DELETE
	@RequestMapping(value = "cancel/{uid}/{orderId}",method = RequestMethod.DELETE)
	public String cancelOrder(@PathVariable("orderId") String orderId,
			@PathVariable("uid") Long uid) {

		try {
			logger.debug("orderId:{}", orderId);
			Orders or = ordersService.getOrdersById(orderId);
			if (or == null || or.getStatus().intValue() != 0) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.orders_not_exists, RespMessage
								.getRespMsg(Status.orders_not_exists)));
			}

			if (or.getUid().intValue() != uid.intValue()) {
				return JSONObject.toJSONString(new RespMsg<>(
						Status.orders_error, RespMessage
								.getRespMsg(Status.orders_error)));
			}
			int ri = ordersService.verifyCancelOrders(orderId);
			return JSONObject.toJSONString(new RespMsg<>(ri, RespMessage
					.getRespMsg(ri)));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 钓点获取订单
	 * 
	 * @param siteId
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/site/{siteId}/{status}/{start}/{size}")
	@RequestMapping(value = "list/site/{siteId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getSiteOrdersList(@PathVariable("siteId") Integer siteId,
			@PathVariable("status") Integer status,
			@PathVariable("start") Long start, @PathVariable("size") Integer size) {
		try {
			logger.debug("siteId:{},start:{},size:{}", siteId, start, size);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fishSiteId", siteId);
			map.put("status", status == 6 ? null : status);
			map.put("start", start > 0 ? new Timestamp(start) : null);
			map.put("size", size);
			List<Orders> pojos = ordersService.getOrdersListBySite(map);
			return JSONObject.toJSONString(new RespMsg<List<Orders>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 用户查找订单列表
	 * 
	 * @param uid
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/user/{uid}/{status}/{start}/{size}")
	@RequestMapping(value = "list/user/{uid}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getUidOrdersList(@PathVariable("uid") Long uid,
			@PathVariable("status") Long status, @PathVariable("start") Long start,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("uid:{},status:{},start:{},size:{}", uid, status,
					start, size);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("status", status == 4 ? null : status);
			map.put("start", start > 0 ? new Timestamp(start) : null);
			map.put("size", size);
			List<Orders> pojos = ordersService.getOrdersListByUid(map);
			Iterator<Orders> it = pojos.iterator();
			while(it.hasNext()){
				Orders or = it.next();
				if(or.getWhereIsApp() == 2 && or.getStatus() == 0){
					or.setStatus(2);
				}
			}
			return JSONObject.toJSONString(new RespMsg<List<Orders>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}

}
