package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.AliPayNotifyMapper;
import cn.heipiao.api.mapper.CampaignMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.GoldCoinOrdersMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.mapper.WxPayNotifyMapper;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.AliPayNotify;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.WxPayNotify;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.GoldCoinOrdersService;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.service.OrdersService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.PayShopConfigService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.service.UserBalanceOpService;
import cn.heipiao.api.service.UserTicketsService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 处理支付回调业务
 * @date 2016年7月4日
 */
@Service
@Transactional(readOnly = true)
public class NotifyServiceImpl implements NotifyService {
//	private static final String FAIL = "fail";
//	private static final String SUCCESS = "success";
//
//	private static final Logger log = LoggerFactory.getLogger(NotifyServiceImpl.class);

	@Resource
	private SystemRecordGeneratorService systemRecordGenerator;

	@Resource
	private UserBalanceOpService userBalanceOpService;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private UserTicketsMapper userTicketsMapper;

	@Resource
	private AccountMapper accountMapper;
	
	@Resource
	private AccountService accountService;

	@Resource
	private FishSiteMapper fishSiteMapper;

	@Resource
	private WxPayNotifyMapper wxPayNotifyMapper;

	@Resource
	private AliPayNotifyMapper aliPayNotifyMapper;
	
	@Resource
	private UserTicketsService userTicketsService;

	@Resource
	private PayParams payParams;
	
	@Resource(name="PayService")
	private PayService pay;
	
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private GoldCoinOrdersMapper goldCoinOrdersMapper;
	
	@Resource
	private GoldCoinOrdersService goldCoinOrdersService;
	
	@Resource
	private ShopTradeOrdersService shopTradeOrdersService;
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	@Resource
	private PayShopConfigService payShopConfigService;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private PartnerService partnerService;
	
	@Resource
	private CampaignMapper campaignMapper;
	
	@Resource
	private CampaignService campaignService;
	
	/**
	 * params.put("discount", "0.00"); params.put("payment_type", "1");
	 * params.put("subject", "购票"); params.put("trade_no",
	 * "2016070821001004370298263148"); params.put("buyer_email",
	 * "wangzhongwei986@163.com"); params.put("gmt_create",
	 * "2016-07-08 13:38:08"); params.put("notify_type", "trade_status_sync");
	 * params.put("quantity", "1"); params.put("out_trade_no", "2");
	 * params.put("seller_id", "2088421306777403"); params.put("notify_time",
	 * "2016-07-08 23:02:44"); params.put("body", "测试");
	 * params.put("trade_status", "TRADE_SUCCESS");
	 * params.put("is_total_fee_adjust", "N"); params.put("total_fee", "0.01");
	 * params.put("gmt_payment", "2016-07-08 13:38:08");
	 * params.put("seller_email", "heipiao@heipiaola.com"); params.put("price",
	 * "0.01"); params.put("buyer_id", "2088902389229376");
	 * params.put("notify_id", "0e930b7ab3e82408edf2a9c59414802iuu");
	 * params.put("use_coupon", "N"); params.put("sign_type", "RSA");
	 * params.put( "sign",
	 * "pTkylmTUXkiXE/zIXf0CAsTm2VWpy4cmfxPwk1bxrohhHYHFOxK+7B9dKrXCBPqGZv4auAO5LHqGCgusBkS6jnvZScH/1HJuGKW+a9ORWrDZlGNxBHsevwmJlwzCvKa6DphCEhm7kCa7m+NfbgYxQ3mqL4ZER894g44YgTzp4R0="
	 * @throws Exception 
	 */

	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public String wxNotify(WxPayNotify wxPayNotify) throws Exception{
		WxPayNotify pojo = wxPayNotifyMapper.selectWxPayNotifyAsLockByOutTradeNo(wxPayNotify.getOut_trade_no());
		if(pojo == null)
			return PayParams.responseXml(PayParams.success);
		//并发处理
		if(pojo.getResult_code() != null && pojo.getResult_code().equalsIgnoreCase(PayParams.success)){
			return PayParams.responseXml(PayParams.success);
		}
		
//		return wxPayNotify.getAttach().equals(cn.heipiao.api.service.PayService.buyGoodOrders) ? wxPayNotify(wxPayNotify) : 
//			wxPayNotify.getAttach().equals(cn.heipiao.api.service.PayService.payGoldCoin) ? wxPayGoldCoinNofify(wxPayNotify) : 
//				PayParams.responseXml(PayParams.success);
			
		switch (pojo.getAttach()) {
		case cn.heipiao.api.service.PayService.buyGoodOrders:
			return wxPayNotify(wxPayNotify);
		case cn.heipiao.api.service.PayService.payGoldCoin:
			return wxPayGoldCoinNofify(wxPayNotify); 
		case cn.heipiao.api.service.PayService.payShop:
			return wxPayShopNotify(wxPayNotify); 
		case cn.heipiao.api.service.PayService.activity:
			return wxActivityNotify(wxPayNotify); 
		default:
			return PayParams.success;
		}
	}
	
	
	private String wxActivityNotify(WxPayNotify wxPayNotify) {
		// 判断参数
		CampaignActor ca = campaignMapper.getCampaignActorAsLock(wxPayNotify.getOut_trade_no());
		if (ca == null || ca.getPayStatus() != 0) {
			return PayParams.responseXml(PayParams.fail);
		}
		
		if(wxPayNotify.getTotal_fee().intValue() != (int)ArithUtil.mul(ca.getPayAmount(), 100, 2)){
			return PayParams.responseXml(PayParams.fail);
		}
		
		// 更新支付通知数据
		wxPayNotifyMapper.updatePojo(wxPayNotify);
		
		ca.setPayStatus(1);
		campaignMapper.updateCampaignActor(ca);

		accountBillService.addPojo(ca.getUid().longValue(), ca.getOrderId(), 2, 20, 1, ca.getPayAmount().doubleValue(), "活动报名-微信");
		return PayParams.responseXml(PayParams.success);
	}


	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public String aliNotify(AliPayNotify aliPayNotify) throws Exception{
		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(aliPayNotify.getOut_trade_no());
		//并发处理
		if(pojo.getTrade_status() != null 
				&& (pojo.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") 
						|| pojo.getTrade_status().equalsIgnoreCase("TRADE_FINISHED"))){
			return PayParams.success;
		}
		
//		return pojo.getAttach().equals(cn.heipiao.api.service.PayService.buyGoodOrders) ? aliPayNotify(aliPayNotify) :
//			pojo.getAttach().equals(cn.heipiao.api.service.PayService.payGoldCoin) ? aliPayGoldCoinNofify(aliPayNotify):
//				PayParams.success;
		switch (pojo.getAttach()) {
		case cn.heipiao.api.service.PayService.buyGoodOrders:
			return aliPayNotify(aliPayNotify);
		case cn.heipiao.api.service.PayService.payGoldCoin:
			return aliPayGoldCoinNofify(aliPayNotify); 
		case cn.heipiao.api.service.PayService.payShop:
			return aliPayShopNotify(aliPayNotify); 
		default:
			return PayParams.success;
		}
	}

	
	/**
	 * 店铺
	 * 
	 * @param aliPayNotify
	 * @return
	 * @throws Exception 
	 */
	private String aliPayShopNotify(AliPayNotify aliPayNotify) throws Exception {
		aliPayNotify.setSign(null);
		// 判断参数
		ShopTradeOrders sto = shopTradeOrdersMapper.selectAsLockByOrderId(aliPayNotify.getOut_trade_no());

		if (sto == null || sto.getStatus() != 0) {
			return PayParams.fail;
		}
		
		if(ArithUtil.compareTo(aliPayNotify.getTotal_fee(),sto.getPayMoney()) != 0){
			return PayParams.fail;
		}
		
//		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(aliPayNotify.getOut_trade_no());
//		//并发处理
//		if(pojo.getTrade_status() != null 
//				&& (pojo.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") 
//						|| pojo.getTrade_status().equalsIgnoreCase("TRADE_FINISHED"))){
//			return PayParams.success;
//		}
		
		sto.setPayTime(new Timestamp(ExDateUtils.getDate(aliPayNotify.getGmt_payment(),"yyyy-MM-dd HH:mm:ss").getTime()));
		
//		// 更新支付通知数据
//		aliPayNotifyMapper.updatePojo(aliPayNotify);
//		// 店铺添加金额
//		accountService.addShopAccountBalance(sto.getShopId(), sto.getOrdersMoney());
//		//奖励
////		payShopConfigService.reward(sto.getUid(),sto.getOrderId(),sto.getOrdersMoney());
//		// 更新订单表
//		// 更新订单交易号
//		sto.setTradeNo(aliPayNotify.getTrade_no());
//		sto.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//		sto.setStatus(1);
//		shopTradeOrdersMapper.updatePart(sto);

		sto.setTradeNo(aliPayNotify.getTrade_no());

		shopTradeOrdersService.shopDeal(sto);
		
		accountBillService.addPojo(sto.getUid(), sto.getOrderId(), 2, 3, 1, sto.getPayMoney(), "票支付-支付宝");

		//平台奖励
		shopTradeOrdersService.shopDetial(sto);
		
		return PayParams.success;
	}


	/**
	 * 店铺
	 * @param wxPayNotify
	 * @return
	 * @throws Exception 
	 */
	private String wxPayShopNotify(WxPayNotify wxPayNotify) throws Exception {
		// 判断参数
		ShopTradeOrders sto = shopTradeOrdersMapper.selectAsLockByOrderId(wxPayNotify.getOut_trade_no());
		if (sto == null || sto.getStatus() != 0) {
			return PayParams.responseXml(PayParams.fail);
		}
		
		if(wxPayNotify.getTotal_fee().intValue() != (int)ArithUtil.mul(sto.getPayMoney(), 100, 2)){
			return PayParams.responseXml(PayParams.fail);
		}
		
		// 更新支付通知数据
		wxPayNotifyMapper.updatePojo(wxPayNotify);
//		//店铺添加金额
//		double balance = ShopTradeOrdersImpl.getBalance(sto);
//		accountService.addShopAccountBalance(sto.getShopId(), balance);
//		//奖励
////		payShopConfigService.reward(sto.getUid(),sto.getOrderId(),sto.getOrdersMoney());
//		
//		//合伙人收益
//		partnerService.shopEarn(sto,(int)(balance * 100));
//		
//		// 更新订单表
//		// 更新订单交易号
//		sto.setTradeNo(wxPayNotify.getTransaction_id());
//		sto.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//		sto.setStatus(1);
//		shopTradeOrdersMapper.updatePart(sto);
		
		sto.setTradeNo(wxPayNotify.getTransaction_id());
		shopTradeOrdersService.shopDeal(sto);
		//平台奖励
		shopTradeOrdersService.shopDetial(sto);
		
		accountBillService.addPojo(sto.getUid(), sto.getOrderId(), 2, 3, 1, sto.getPayMoney(), "票支付-微信");
		return PayParams.responseXml(PayParams.success);
	}
	
	
	/*
	 * (non-Javadoc)
	 * 购票
	 * @see cn.heipiao.api.service.NotifyService#wxPayNotify(java.util.Map)
	 */
	private String wxPayNotify(WxPayNotify wxPayNotify) throws Exception {
//		wxPayNotify.setSign(null);
		// 判断参数
		Orders order = ordersMapper.selectAsLockById(wxPayNotify.getOut_trade_no());
		if (order == null || order.getStatus() != 0) {
			return PayParams.responseXml(PayParams.fail);
		}
		
		if(wxPayNotify.getTotal_fee().intValue() != (int)ArithUtil.mul(order.getPayMoney(), 100, 2)){
			return PayParams.responseXml(PayParams.fail);
		}
		
//		WxPayNotify pojo = wxPayNotifyMapper.selectWxPayNotifyAsLockByOutTradeNo(wxPayNotify.getOut_trade_no());
//		//并发处理
//		if(pojo.getResult_code() != null && pojo.getResult_code().equalsIgnoreCase(PayParams.success)){
//			return PayParams.responseXml(PayParams.success);
//		}
		
		// 更新支付通知数据
		wxPayNotify.setAttach(cn.heipiao.api.service.PayService.buyGoodOrders);
		wxPayNotifyMapper.updatePojo(wxPayNotify);
		// 执行业务操作
//		userTicketsService.paySuccessDeal(order);
		// 更新订单表
		// 更新订单交易号
		order.setTradeNo(wxPayNotify.getTransaction_id());
//		order.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//		order.setStatus(1);
//		ordersMapper.updatePart(order);
		
		ordersService.siteDeal(order);
		
		//添加账单
		accountBillService.addPojo(order.getUid(), order.getOrderId(), 2, 1, 1, order.getPayMoney(), "购票-微信");
		return PayParams.responseXml(PayParams.success);
	}

	/*
	 * (non-Javadoc)
	 * 购票
	 * @see cn.heipiao.api.service.NotifyService#aliPayNotify(java.util.Map)
	 */
	private String aliPayNotify(AliPayNotify aliPayNotify) throws Exception {
		aliPayNotify.setSign(null);
		// 判断参数
		Orders order = ordersMapper.selectAsLockById(aliPayNotify.getOut_trade_no());

		if (order == null || order.getStatus() != 0) {
			return PayParams.fail;
		}
		
		if(ArithUtil.compareTo(aliPayNotify.getTotal_fee(),order.getPayMoney()) != 0){
			return PayParams.fail;
		}
		
//		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(aliPayNotify.getOut_trade_no());
//		//并发处理
//		if(pojo.getTrade_status() != null 
//				&& (pojo.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") 
//						|| pojo.getTrade_status().equalsIgnoreCase("TRADE_FINISHED"))){
//			return PayParams.success;
//		}
		
		order.setPayTime(new Timestamp(ExDateUtils.getDate(aliPayNotify.getGmt_payment(),"yyyy-MM-dd HH:mm:ss").getTime()));
		
		// 更新支付通知数据
		aliPayNotifyMapper.updatePojo(aliPayNotify);
		// 支付平台设置
//		order.setTradePlatform(2);
		// 执行业务操作
//		userTicketsService.paySuccessDeal(order);

		// 更新订单表
		// 更新订单交易号
		order.setTradeNo(aliPayNotify.getTrade_no());
//		order.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//		order.setStatus(1);
//		ordersMapper.updatePart(order);
		
		ordersService.siteDeal(order);
		
		//添加账单
		accountBillService.addPojo(order.getUid(), order.getOrderId(), 2, 1, 1, order.getPayMoney(), "购票-支付宝");
		return PayParams.success;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.NotifyService#getWxPayNotify(java.lang.String)
	 */
	@Override
	public WxPayNotify getWxPayNotify(String orderId) {
		return wxPayNotifyMapper.selectWxPayNotifyByOutTradeNo(orderId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.NotifyService#getAliPayNotify(java.lang.String)
	 */
	@Override
	public AliPayNotify getAliPayNotify(String orderId) {
		return aliPayNotifyMapper.selectAliPayNotifyByOutTradeNo(orderId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.NotifyService#updateAliPayNotify(cn.heipiao.api.pojo.AliPayNotify)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void updateAliPayNotify(AliPayNotify aliPayNotify) {
//		AliPayNotify ali = new AliPayNotify();
//		ali.setOut_trade_no(aliPayNotify.getOut_trade_no());
//		ali.setTrade_status(aliPayNotify.getTrade_status());
//		ali.setGmt_close(aliPayNotify.getGmt_close());
//		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(ali.getOut_trade_no());
//		aliPayNotifyMapper.updatePojo(ali);
		AliPayNotify pojo = aliOrderClose(aliPayNotify);
		Orders order = ordersMapper.selectAsLockById(pojo.getOut_trade_no());
		//订单完成表示业务整体结束
		ordersMapper.updateStatus(order.getOrderId(), 4);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.NotifyService#wxTradeQueryResult(java.lang.String)
	 * 
	 * 查询订单的结果做处理
	 * 
	 * 0:表示订单未支付
	 * 1：表示订单已支付
	 * -1：表示订单取消或者其它
	 */
	@Override
	public int  wxTradeQueryResult(String orderId, WxPayNotify wx,Integer whereIsApp) throws Exception {
		if(wx == null)
			return -1;
		Map<String, String> m = pay.wechatQueryTrade(orderId,whereIsApp);
		JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(m));
		WxPayNotify wx0 = JSONObject.toJavaObject(json, WxPayNotify.class);
		wx0.setAttach(wx.getAttach());
		wx0.toCopy(wx);
		if(wx.getReturn_code().equalsIgnoreCase(PayParams.success) && wx.getResult_code().equalsIgnoreCase(PayParams.success)){
			String trade_state = m.get("trade_state");
			if(trade_state.equals(PayParams.success)){
				//处理成功后的逻辑
//				if(wxPayNotify(wx).equals(payParams.responseXml(PayParams.success))){
//					return Status.orders_paid;
//				}else {
//					return Status.orders_error;
//				}
				return 1;
			//如果是非支付状态取消订单
			}else if (trade_state.equals("REFUND") || trade_state.equals("CLOSED") 
					|| trade_state.equals("PAYERROR") || trade_state.equals("REVOKED") ){
//				return cancelOrder(orderId);
				return -1;
			}
		}
		return 0;
		
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.NotifyService#aliTradeQueryResult(java.lang.String)
	 * 
	 * 查询订单的结果做处理
	 * 0:表示订单未支付
	 * 1：表示订单已支付
	 * -1：表示订单取消或者其它
	 */
	@Override
	public int aliTradeQueryResult(String orderId,AliPayNotify ali) throws Exception {
		if(ali == null)
			return -1;
		 String respStr = pay.aliQueryTrade(orderId);
		 JSONObject jsonStr = JSONObject.parseObject(respStr).getJSONObject("alipay_trade_query_response");
		 if(jsonStr.getString("code").equals("10000")){
			 AliPayNotify ali0 = toAliPayNotify(jsonStr);
			 ali0.setAttach(ali.getAttach());
			 ali0.toCopy(ali);
			if(ali.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") || ali.getTrade_status().equalsIgnoreCase("TRADE_FINISHED")){
//				//做支付通知处理
//				if(aliPayNotify(ali).equals(PayParams.success)){
//					return Status.orders_paid;
//				}else{
//					return Status.orders_error;
//				}
			 return 1;
			//如果是非支付状态取消订单
			}else if(ali.getTrade_status().equalsIgnoreCase("TRADE_CLOSED")){
//				return cancelOrder(orderId);
				return -1;
			}
		 }
		return 0;
	}
	
	
	/**
	 * @param json
	 * @param ali
	 */
	private AliPayNotify toAliPayNotify(JSONObject json) {
		AliPayNotify ali = new AliPayNotify();
		ali.setOut_trade_no(json.getString("out_trade_no"));
		ali.setTrade_no(json.getString("trade_no"));
		ali.setBuyer_id(json.getString("buyer_user_id"));
		ali.setBuyer_email(json.getString("buyer_logon_id"));
		ali.setTotal_fee(json.getDouble("total_amount"));
		ali.setTrade_status(json.getString("trade_status"));
		ali.setGmt_payment(json.getString("send_pay_date"));
		return ali;
	}
	

	/* (non-Javadoc)
	 * 
	 * 充值
	 * 
	 * @see cn.heipiao.api.service.NotifyService#wxPayGoldCoinNofify(cn.heipiao.api.pojo.WxPayNotify)
	 */
	private String wxPayGoldCoinNofify(WxPayNotify wxPayNotify) {
		GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderIdAsLock(wxPayNotify.getOut_trade_no());
		if(gco == null || gco.getStatus() != 0){
			return PayParams.responseXml(PayParams.fail);
		}
//		WxPayNotify pojo = wxPayNotifyMapper.selectWxPayNotifyAsLockByOutTradeNo(wxPayNotify.getOut_trade_no());
//		//并发处理
//		if(pojo.getResult_code() != null && pojo.getResult_code().equalsIgnoreCase(PayParams.success)){
//			return PayParams.responseXml(PayParams.success);
//		}
		
		if(wxPayNotify.getTotal_fee().intValue() != gco.getOrdersMoney() * 100){
			return PayParams.responseXml(PayParams.fail);
		}
		
		//更新通知参数
		wxPayNotify.setAttach(cn.heipiao.api.service.PayService.payGoldCoin);
		wxPayNotifyMapper.updatePojo(wxPayNotify);
		
		//添加漂币
		accountService.addGoldCoin(gco);
		
		//更新订单信息
		gco.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		gco.setTradeNo(wxPayNotify.getOut_trade_no());
		gco.setStatus(1);
		goldCoinOrdersMapper.updatePojo(gco);
		
		accountBillService.addPojo(gco.getUid(), gco.getOrderId(), 2, 2, 1, (double)gco.getOrdersMoney(), "充值-微信");
		return PayParams.responseXml(PayParams.success);
	}

	/* (non-Javadoc)
	 * 
	 * 充值
	 * 
	 * @see cn.heipiao.api.service.NotifyService#aliPayGoldCoinNofify(cn.heipiao.api.pojo.AliPayNotify)
	 */
	private String aliPayGoldCoinNofify(AliPayNotify aliPayNotify) throws Exception {
		GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderIdAsLock(aliPayNotify.getOut_trade_no());
		if(gco == null || gco.getStatus() != 0){
			return PayParams.fail;
		}
		
		if(ArithUtil.compareTo(aliPayNotify.getTotal_fee(),gco.getOrdersMoney().doubleValue()) != 0){
			return PayParams.fail;
		}
		
//		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(aliPayNotify.getOut_trade_no());
//		//并发处理
//		if(pojo.getTrade_status() != null 
//				&& (pojo.getTrade_status().equalsIgnoreCase("TRADE_SUCCESS") 
//						|| pojo.getTrade_status().equalsIgnoreCase("TRADE_FINISHED"))){
//			return PayParams.success;
//		}
		gco.setPayTime(new Timestamp(ExDateUtils.getDate(aliPayNotify.getGmt_payment(),"yyyy-MM-dd HH:mm:ss").getTime()));
		//更新通知参数
		aliPayNotifyMapper.updatePojo(aliPayNotify);
		
		//添加漂币
		accountService.addGoldCoin(gco);
		
		//更新订单信息
		gco.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		gco.setTradeNo(aliPayNotify.getOut_trade_no());
		gco.setStatus(1);
		goldCoinOrdersMapper.updatePojo(gco);
		
		accountBillService.addPojo(gco.getUid(), gco.getOrderId(), 2, 2, 1, (double)gco.getOrdersMoney(), "充值-支付宝");
		return PayParams.success;
	}

	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#verifyOrders(int, java.lang.String)
	 */
	@Override
	public int verifyOrders(int tradePlatform, String orderId,String attach,Integer whereIsApp) throws Exception {
		int result = Status.orders_error;
		if(tradePlatform == 1){
			WxPayNotify wx = wxPayNotifyMapper.selectWxPayNotifyByOutTradeNo(orderId);
			result = wxTradeQueryResult(orderId,wx, whereIsApp);
			if(result == 1){
				return wxOrdersSuccess(wx);
			 }
			if(result == -1){
				return orderFail(orderId,attach);
			}
		} else if (tradePlatform == 2){
			AliPayNotify ali = aliPayNotifyMapper.selectAliPayNotifyByOutTradeNo(orderId);
			result =aliTradeQueryResult(orderId,ali);
			if(result == 1){
			   return aliOrdersSuccess(ali);
			}
			if(result == -1){
				return orderFail(orderId,attach);
			}
		}
		return result;
	}

	/**
	 * @param ali
	 * @return
	 * @throws Exception 
	 */
	@Override
	public int aliOrdersSuccess(AliPayNotify ali) throws Exception {
		if(aliNotify(ali).equals(PayParams.success)){
			return Status.orders_paid;
		}else{
			return Status.orders_error;
		}
	}

	/**
	 * @param orderId
	 * @param attach 
	 * @return
	 * @throws Exception 
	 */
	@Override
	public int orderFail(String orderId, String attach) throws Exception {
//		if(cancelGoldCoinOrders(orderId) == Status.success){
//			return Status.orders_cancel;
//		} else {
//			return Status.orders_error;
//		}
		int rs = Status.orders_error;
		switch (attach) {
		case cn.heipiao.api.service.PayService.buyGoodOrders:
			rs = ordersService.cancelOrder(orderId);
			break;
		case cn.heipiao.api.service.PayService.payGoldCoin:
			rs = goldCoinOrdersService.cancelGoldCoinOrders(orderId);
			break;
		case cn.heipiao.api.service.PayService.payShop:
			rs = shopTradeOrdersService.cancelShopTradeOrders(orderId);
			break;
		case cn.heipiao.api.service.PayService.activity:
			rs = campaignService.cancelEnter(orderId);
			break;
		default:
			return rs;
		}
		return rs;
	}

	/**
	 * @param wx
	 * @return
	 * @throws Exception 
	 */
	@Override
	public int wxOrdersSuccess(WxPayNotify wx) throws Exception {
		if(wxNotify(wx).equals(PayParams.responseXml(PayParams.success))){
			return Status.orders_paid;
		}else {
			return Status.orders_error;
		}
	}
	

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.NotifyService#updateAliPayGoldCoin(cn.heipiao.api.pojo.AliPayNotify)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void updateAliPayGoldCoin(AliPayNotify aliPayNotify) {
//		AliPayNotify ali = new AliPayNotify();
//		ali.setOut_trade_no(aliPayNotify.getOut_trade_no());
//		ali.setTrade_status(aliPayNotify.getTrade_status());
//		ali.setGmt_close(aliPayNotify.getGmt_close());
//		AliPayNotify pojo = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(ali.getOut_trade_no());
		AliPayNotify ali = aliOrderClose(aliPayNotify);
		
		GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderIdAsLock(ali.getOut_trade_no());
		//订单完成表示业务整体结束
		gco.setStatus(4);
		gco.setCloseTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		goldCoinOrdersMapper.updatePojo(gco);
	}

	private AliPayNotify aliOrderClose(AliPayNotify aliPayNotify){
		AliPayNotify ali = aliPayNotifyMapper.selectAliPayNotifyAsLockByOutTradeNo(aliPayNotify.getOut_trade_no());
		ali.setOut_trade_no(aliPayNotify.getOut_trade_no());
		ali.setTrade_status(aliPayNotify.getTrade_status());
		ali.setGmt_close(aliPayNotify.getGmt_close());
		aliPayNotifyMapper.updatePojo(ali);
		return ali;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void updateAliPayShop(AliPayNotify aliPayNotify) {
		AliPayNotify ali = aliOrderClose(aliPayNotify);
		ShopTradeOrders sto = shopTradeOrdersMapper.selectAsLockByOrderId(ali.getOut_trade_no());
		sto.setStatus(4);
		shopTradeOrdersMapper.updatePart(sto);
	}


//	@Override
//	@Transactional(readOnly = false,rollbackFor = {Exception.class})
//	public void updateWxPayNotify(WxPayNotify wxPayNotify) {
//		WxPayNotify wx = new WxPayNotify();
//		wx.setOut_trade_no(wxPayNotify.getOut_trade_no());
//		wx.set;
//	}
}
