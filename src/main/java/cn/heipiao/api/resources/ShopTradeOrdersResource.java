/**
 * 
 */
package cn.heipiao.api.resources;

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
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.utils.ExDateUtils;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年10月14日
 * @version 1.0
 */
//@Path("orders/shop")
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Component
@Api(tags = "票支付模块")
@RestController
@RequestMapping(value = "orders/shop",produces="application/json")
public class ShopTradeOrdersResource {

	private static final Logger logger = LoggerFactory.getLogger(ShopTradeOrdersResource.class);
	
	@Resource
	private ShopTradeOrdersService shopTradeOrdersService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private UserRewardService userRewardService;
	
	@Resource
	private FishShopService fishShopService;
	
	/**
	 * {"uid": 1,"shopId": 61030013312,"ordersMoney": "50.5","payMoney":"40.5","goldCoinMoney":100, 
	 * "couponsMoney":"50","couponId":123,"tradePlatform":1,"whereIsApp":1}
	 * 
	 * 1:微信,2:支付宝
	 * 1:平台的,2:小程序的
	 * 创建票支付订单
	 * @param json
	 * @return
	 */
//	@POST
//	@Path("create")
	@RequestMapping(value = "create",method = RequestMethod.POST,consumes = "application/json")
	public String addOrder(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}", json);
			ShopTradeOrders sto = json == null ? null : JSONObject.toJavaObject(json, ShopTradeOrders.class);
			if(sto == null || sto.getUid() == null || sto.getOrdersMoney() == null 
					|| sto.getTradePlatform() == null
					|| sto.getShopId() == null || sto.getPayMoney() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			if(sto.getGoldCoinMoney() == null){
				sto.setGoldCoinMoney(0);
			}
			sto.setEarningsGoldCoinMoney(0);
			sto.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			sto.setStatus(0);
			sto.setOtherCouponsMoney(0);
			sto.setCouponsFee(0);
			if(sto.getWhereIsApp() == null){
				sto.setWhereIsApp(1);
			}
			if(sto.getWhereIsApp() > 2 || sto.getWhereIsApp() < 1){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			
			int rs = shopTradeOrdersService.addShopTradeOrders(sto);
			return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs),sto.getOrderId()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * app调用订单确认获得奖励
	 * @param orderId
	 * @return
	 */
//	@GET
//	@Path("confirm/{orderId}")
	@RequestMapping(value = "confirm/{orderId}",method = RequestMethod.GET)
	public String getShopOrder(@PathVariable("orderId") String orderId){
		logger.info("orderId:{}",orderId);
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			int rw = 0;
			//验证订单
			shopTradeOrdersService.verifyOrders(orderId);
			ShopTradeOrders sto = shopTradeOrdersService.selectByOrderId(orderId);
			if(sto != null && sto.getStatus() == 1){
				FishShop fs = fishShopService.getFishShopById(sto.getShopId());
				//奖励
				try {
					/**
					 * 2.3关闭
					 */
//					rw = userRewardService.reward(sto.getUid(), 2, (int)ArithUtil.mul(ShopTradeOrdersImpl.getBalance(sto), 100, 0), sto.getOrderId(), fs.getCityId());
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
			map.put("shopReward", rw);
			return JSONObject.toJSONString(new RespMsg<>(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取最近收款记录
	 * 默认10行
	 * @param shopId
	 * @return
	 */
//	@GET
//	@Path("list/{shopId}")
	@RequestMapping(value = "list/{shopId}",method = RequestMethod.GET)
	public String getRecentOrders(@PathVariable("shopId") Long shopId){
		try {
			logger.info("shopId:{}",shopId);
			List<ShopTradeOrders> list = shopTradeOrdersService.getRecentOrders(shopId);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取票支付订单记录
	 * @param shopId
	 * @return
	 */
//	@GET
//	@Path("list/{shopId}/{index}/{size}")
	@RequestMapping(value = "list/{shopId}/{index}/{size}",method = RequestMethod.GET)
	public String getOrdersByShopId(@PathVariable("shopId") Long shopId,@PathVariable("index")Long index,@PathVariable("size") Integer size){
		try {
			logger.info("shopId:{},index:{},size:{}",shopId,index,size);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("shopId", shopId);
			map.put("index", index <= 0 ? null : new Timestamp(index));
			map.put("size", size);
			List<ShopTradeOrders> list = shopTradeOrdersService.getOrdersByShopId(map);
			return JSONObject.toJSONString(new RespMsg<>(list));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 店铺看板统计
	 * @param shopId
	 * @return
	 */
//	@GET
//	@Path("count/{shopId}")
	@RequestMapping(value = "count/{shopId}",method = RequestMethod.GET)
	public String getCountOrders(@PathVariable("shopId") Long shopId){
		try {
			logger.info("shopId:{}",shopId);
			Map<String,Object> countMap = shopTradeOrdersService.countBoard(shopId);
			return JSONObject.toJSONString(new RespMsg<>(countMap));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 根据付款码查找店铺
	 * @return
	 */
//	@GET
//	@Path("payCode/{PayCode}")
	@RequestMapping(value = "payCode/{PayCode}",method = RequestMethod.GET)
	public String getShopByPayCode(@PathVariable("PayCode")String PayCode){
		try {
			logger.info("PayCode:{}",PayCode);
			ShopAccount sa = accountService.getShopByPayCode(PayCode);
			if(sa == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.FISH_SHOP_NOT_EXISTS,RespMessage.getRespMsg(Status.FISH_SHOP_NOT_EXISTS)));
			}
			sa.setBalance(null);
			sa.setWithdrawTime(null);
			return JSONObject.toJSONString(new RespMsg<>(sa));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
}
