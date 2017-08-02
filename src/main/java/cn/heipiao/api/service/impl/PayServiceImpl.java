/**
 * 
 */
package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.mapper.AliPayNotifyMapper;
import cn.heipiao.api.mapper.CampaignMapper;
import cn.heipiao.api.mapper.GoldCoinOrdersMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.mapper.WxPayNotifyMapper;
import cn.heipiao.api.pay.PayConfig;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pojo.AliPayNotify;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.WxPayNotify;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.service.PayService;
import cn.heipiao.api.service.UserTicketsService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PayServiceImpl implements PayService{
	
	
	@Value("${order.outTime}")
	private Integer outTime;
	
	@Resource
	private PayConfig payConfig;
	
	@Resource
	private PayParams payParams;

	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private AliPayNotifyMapper aliPayNotifyMapper;
	
	@Resource
	private WxPayNotifyMapper wxPayNotifyMapper;
	
	@Resource(name="PayService")
	private cn.heipiao.api.pay.PayService pay;
	
	@Resource
	private NotifyService notifyService;
	
	@Resource
	private UserTicketsService userTicketsService;
	
	@Resource
	private GoldCoinOrdersMapper goldCoinOrdersMapper; 
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	@Resource
	private CampaignMapper campaignMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PayService#generatePayParam(java.lang.Long, int, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public String generatePayParam(Long uid, int platform, String orderId,String app_ip,String body,
			Integer hpService,String openid) throws Exception {
		String payStr = JSONObject.toJSONString(new RespMsg<>(Status.pay_param_generate_fail,RespMessage.getRespMsg(Status.pay_param_generate_fail)));
		String rs = null;
		if(hpService.intValue() == 1){
			rs = payParams(uid,platform,orderId,app_ip,body,openid);
		}else if(hpService.intValue() == 2){
			//充值获取参数
			rs = generatePayGoldCoinParam(uid,platform,orderId,app_ip,body,openid);
		}else if(hpService.intValue() == 3){
			rs = generatePayShop(uid,platform,orderId,app_ip,body,openid);
		}else if(hpService == 4) {
			rs = generateActivity(uid,platform,orderId,app_ip,body,openid);
		}
		return  rs == null ? payStr : rs;
	}

	/**
	 * 
	 * @param uid
	 * @param platform
	 * @param orderId
	 * @param app_ip
	 * @param body
	 * @param openid
	 * @return
	 * @throws Exception 
	 */
	private String generateActivity(Long uid, int platform, String orderId, String app_ip, String body, String openid) throws Exception {
		CampaignActor ca = campaignMapper.getCampaignActorAsLock(orderId);
		String ps = JSONObject.toJSONString(new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)));
		if(ca == null || ca.getPayStatus() != 0){
			return ps;
		}
		switch (platform) {
		case 1:
			ps = wx(ca.getOrderId(), activity, ca.getPayAmount().doubleValue(), body, app_ip,openid, 2);
			 break;
//		case 2:
//			ps = ali(sto.getOrderId(), payShop, sto.getPayMoney(), body, app_ip);
//			 break;
		}
		return ps;
	}

	/**
	 * @param uid
	 * @param platform
	 * @param orderId
	 * @param app_ip
	 * @param body
	 * @param openid 
	 * @return
	 * @throws Exception 
	 */
	private String generatePayShop(Long uid, int platform, String orderId, String app_ip, String body, String openid) throws Exception {
		ShopTradeOrders sto = shopTradeOrdersMapper.selectAsLockByOrderId(orderId);
		String ps = JSONObject.toJSONString(new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)));
		if(sto == null || sto.getStatus() != 0){
			return ps;
		}
		//平台判断
		if(sto.getTradePlatform().intValue() != platform){
			return JSONObject.toJSONString(new RespMsg<>(Status.pay_platform,RespMessage.getRespMsg(Status.pay_platform)));
		}
		switch (platform) {
		case 1:
			ps = wx(sto.getOrderId(), payShop, sto.getPayMoney(), body, app_ip,openid, sto.getWhereIsApp());
			 break;
		case 2:
			ps = ali(sto.getOrderId(), payShop, sto.getPayMoney(), body, app_ip);
			 break;
		}
		return ps;
	}

	/**
	 * 
	 * @param orderId
	 * @param hpService
	 * @param ordersMoney : 单位：元
	 * @param body
	 * @param app_ip
	 * @return
	 * @throws Exception
	 */
	private String wx(String orderId,String hpService,Double ordersMoney,String body,String app_ip,String openid,Integer whereIsApp) throws Exception{
		 WxPayNotify wx = wxPayNotifyMapper.selectWxPayNotifyByOutTradeNo(orderId);
		 if(wx == null){
			 wx = new WxPayNotify();
			 wx.setOut_trade_no(orderId);
			 wx.setAttach(hpService);
			 wxPayNotifyMapper.insertPojo(wx);
		 }else{
			 //处理查询支付
			 int result = notifyService.verifyOrders(1, orderId,hpService,whereIsApp);
			 if(result != 0)
				 return JSONObject.toJSONString(new RespMsg<>(result,RespMessage.getRespMsg(result)));
		 }
		 String payStr = pay.wechatUnifiedorder(whereIsApp != null && whereIsApp == 2 ? payConfig.wx_mini_appid : payConfig.pay_wx_appid_c, body, orderId, app_ip
				 , new Double(ArithUtil.mul(ordersMoney, 100, 2)).intValue(),wx.getAttach(),openid,whereIsApp);
		 return JSONObject.toJSONString(payStr == null ? new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)) : new RespMsg<>(payStr));
	}
	
	private String ali(String orderId,String hpService,Double ordersMoney,String body,String app_ip) throws Exception{
		 AliPayNotify ali = aliPayNotifyMapper.selectAliPayNotifyByOutTradeNo(orderId);
		 if(ali == null){
			 ali = new AliPayNotify();
			 ali.setOut_trade_no(orderId);
			 ali.setAttach(hpService);
			 aliPayNotifyMapper.insertPojo(ali);
		 }else{
			 //处理成功后的逻辑
			 int result = notifyService.verifyOrders(2, orderId,hpService,null);
			 if(result != 0)
				 return JSONObject.toJSONString(new RespMsg<>(result,RespMessage.getRespMsg(result)));
		 }
		 String payStr = pay.aliPayParam(orderId, body, ordersMoney.toString(),ali.getAttach());	
		 return JSONObject.toJSONString(payStr == null ? new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)) : new RespMsg<>(payStr));
	}

	/**
	 * @param uid
	 * @param platform
	 * @param orderId
	 * @param app_ip
	 * @param body
	 * @param openid 
	 * @return 
	 * @throws Exception 
	 */
	private String generatePayGoldCoinParam(Long uid, int platform, String orderId, String app_ip, String body, 
			String openid) throws Exception {
		GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderIdAsLock(orderId);
		String ps = JSONObject.toJSONString(new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)));
		if(gco == null || gco.getStatus() != 0){
			return ps;
		}
		//平台判断
		if(gco.getTradePlatform().intValue() != platform){
			return JSONObject.toJSONString(new RespMsg<>(Status.pay_platform,RespMessage.getRespMsg(Status.pay_platform)));
		}
		switch (platform) {
		case 1:
			ps = wx(gco.getOrderId(), payGoldCoin, gco.getOrdersMoney().doubleValue(), body, app_ip, openid, gco.getWhereIsApp() );
			 break;
		case 2:
			ps = ali(gco.getOrderId(), payGoldCoin, gco.getOrdersMoney().doubleValue(), body, app_ip);
			 break;
		}
		return ps;
	}


	/**
	 * @param uid
	 * @param platform
	 * @param orderId
	 * @param app_ip
	 * @param body
	 * @param openid 
	 * @return 
	 * @throws Exception 
	 */
	private String payParams(Long uid, int platform, String orderId, String app_ip, String body, 
			String openid) throws Exception {
		Orders order = ordersMapper.selectAsLockById(orderId);
		
		String ps = JSONObject.toJSONString(new RespMsg<>(Status.pay_error,RespMessage.getRespMsg(Status.pay_error)));
		//判断订单号是否为待支付状态或者超时
		if(order == null || order.getStatus().intValue() != 0
				|| ExDateUtils.getCalendar().getTimeInMillis() - order.getCreateTime().getTime() > outTime * 1000){
			return ps; 
		}
		//平台判断
		if(order.getTradePlatform().intValue() != platform){
			return JSONObject.toJSONString(new RespMsg<>(Status.pay_platform,RespMessage.getRespMsg(Status.pay_platform)));
		}
		
		switch (platform) {
		case 1:
			ps = wx(order.getOrderId(), buyGoodOrders, order.getPayMoney(), body, app_ip,openid ,order.getWhereIsApp());
			break;
		case 2:
			ps = ali(order.getOrderId(), buyGoodOrders, order.getPayMoney(), body, app_ip);
			break;
		}
		return ps;
	}


	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PayService#payOrderConfirm(java.lang.String)
	 */
//	@Override
//	@Transactional(readOnly = false,rollbackFor = {Exception.class})
//	public int payOrderConfirm(String orderId) {
//		Orders order = ordersMapper.selectAsLockById(orderId);
//		if(order.getStatus().intValue() != 0 || order.getPayMoney() > 0){
//			return Status.orders_error;
//		}
//		order.setTradePlatform(3);
//		userTicketsService.paySuccessDeal(order);
//		return Status.success;
//	}

}
