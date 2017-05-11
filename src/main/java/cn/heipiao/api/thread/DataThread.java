/**
 * 
 */
package cn.heipiao.api.thread;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.GoldCoinOrdersMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.PartnerWithdrawOrdersMapper;
import cn.heipiao.api.mapper.RefundActivityMapper;
import cn.heipiao.api.mapper.RefundMapper;
import cn.heipiao.api.mapper.ShopCouponsMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.mapper.ShopWithdrawOrdersMapper;
import cn.heipiao.api.mapper.SiteCouponsMapper;
import cn.heipiao.api.mapper.WithdrawOrdersMapper;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.PartnerWithdrawOrders;
import cn.heipiao.api.pojo.ShopWithdrawOrders;
import cn.heipiao.api.pojo.WithdrawOrders;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.GoldCoinOrdersService;
import cn.heipiao.api.service.OrdersService;
import cn.heipiao.api.service.RefundActivityService;
import cn.heipiao.api.service.RefundService;
import cn.heipiao.api.service.ShopCouponsService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.SiteCouponsService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年9月18日
 * @version 1.0
 * 调度程序使用
 * 后续版本需要将本类中所有功能迁移至调度程序中实现
 */
@Component
public class DataThread {
	
	private static final Logger logger = LoggerFactory.getLogger(DataThread.class);
	
	/** 订单超时时间 - 单位秒 **/
	@Value("${order.outTime}")
	private Long outTime;
	
	@Resource
	private WithdrawOrdersMapper withdrawOrdersMapper;
	
	@Resource
	private ShopWithdrawOrdersMapper shopWithdrawOrdersMapper;
	
	@Resource
	private PartnerWithdrawOrdersMapper partnerWithdrawOrdersMapper;

	@Resource
	private AccountService accountService;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private GoldCoinOrdersMapper goldCoinOrdersMapper;
	
	@Resource
	private GoldCoinOrdersService goldCoinOrdersService;
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	@Resource
	private ShopTradeOrdersService shopTradeOrdersService;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private RefundService refundService;
	
	@Resource
	private RefundMapper refundMapper;
	
	@Resource
	private RefundActivityMapper refundActivityMapper;
	
	@Resource
	private RefundActivityService refundActivityService;
	
	@Resource
	private SiteCouponsService siteCouponsService;
	
	@Resource
	private SiteCouponsMapper siteCouponsMapper;
	
	@Resource
	private ShopCouponsService shopCouponsService;
	
	@Resource
	private ShopCouponsMapper shopCouponsMapper;
	
	@Resource(name="PayService")
	private PayService pay;
	
	
	//15m
//	private static  long delay = 15 * 60 * 1000; 
	
	//1s
//	private static  long delay = 60 * 1000; 
	private static final int size = 100;
	
//	private  volatile boolean isEnd = false;
	
//	public void run() {
//		while(true){
//			try {
//				Thread.sleep(delay);
//			} catch (InterruptedException e) {}
//			
//			//防止上次程序执行未完成
//			if(isEnd)
//				continue;
//
//			logger.info("thread start ===============:" + Thread.currentThread().getName());
//			
//			isEnd = true;
//			
//			//业务逻辑
//			
//			try {
//				start0();
//			} catch (Throwable e) {
//				logger.error(e.getMessage(),e);
//			}
//			//================
//			
//			isEnd = false;
//			logger.info("thread end ===============:" + Thread.currentThread().getName());
//		}
//	}
	
//	@Deprecated
//	private void start0() throws Exception{
//		Map<String,Object> map = new HashMap<String, Object>();
//		try {
//			//钓场主提现
//			map.put("size", size);
//			map.put("date", new Timestamp(ExDateUtils.getDate().getTime()));
//			withdraw(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		try {
//			//合伙人提现
//			partnerWithdraw(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		try {
//			//处理购票超时订单
//			map.put("date", new Timestamp(ExDateUtils.getDate().getTime() - (outTime * 1000)));
//			buyTicketOrders(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		try {
//			//处理充值漂币超时订单
//			map.put("date", new Timestamp(ExDateUtils.getDate().getTime() - outTime * 1000));
//			buyGoldCoinOrders(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		try {
//			//处理票支付订单
////			map.put("date", new Timestamp(ExDateUtils.getDate().getTime() - outTime * 1000));
//			payShopOrders(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		try {
//			//处理优惠券
//			map.put("date", new Timestamp(ExDateUtils.getDate().getTime()));
//			couponsTimeout(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//		try {
//			//店铺提现
//			map.put("date", new Timestamp(ExDateUtils.getDate().getTime()));
//			shopWithdraw(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//	
//		try{
//			//退票
//			refundTicket(map);
//			//活动退报名款
//			refundActivity(map);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		
//	}
	

	/**
	 * 退票
	 * @param map
	 * @throws Exception 
	 */
	private void refundTicket(Map<String, Object> map) throws Exception {
		List<String> refunds = refundMapper.selectByStatus(map);
		if(refunds.size() > 0){
			for(String refundNo : refunds){
				refundService.confirmRefund(refundNo);
			}
			if(refunds.size() < size){
				return;
			}
			refundTicket(map);
		}
	}
	
	/**
	 * 退票
	 * @param map
	 * @throws Exception 
	 */
	private void refundActivity(Map<String, Object> map) throws Exception {
		List<String> refunds = refundActivityMapper.selectByStatus(map);
		int count = refunds.size();
		if(count > 0){
			
			for(int i = 0 ; i < count ; i++){
				refundActivityService.refund(refunds.get(i));
				if(i == count - 1){
					map.put("refundNo", refunds.get(i));
				}
			}
			if(refunds.size() < size){
				return;
			}
			refundActivity(map);
		}
	}


	/**
	 * 钓场主提现
	 * @param map
	 * @throws Exception
	 */
	private void withdraw(Map<String, Object> map) throws Exception{
		List<WithdrawOrders> orders = withdrawOrdersMapper.selectByStatus(map);
		if(orders != null && orders.size() > 0){
			boolean []isEnough = new boolean[]{true};
			for (WithdrawOrders order : orders) {
				accountService.accountWithdraw(order,isEnough);
				if(!isEnough[0]){
					return;
				}
			}
			if(orders.size() < size){
				return;
			}
			withdraw(map);
		}
	}
	
	/**
	 *  合伙人提现
	 * @param map
	 * @throws Exception
	 */
	private void partnerWithdraw(Map<String, Object> map) throws Exception{
		List<PartnerWithdrawOrders> orders = partnerWithdrawOrdersMapper.selectByStatus(map);
		if(orders != null && orders.size() > 0){
			boolean []isEnough = new boolean[]{true};
			for (PartnerWithdrawOrders order : orders) {
				accountService.partnerAccountWithdraw(order,isEnough);
				if(!isEnough[0]){
					return;
				}
			}
			if(orders.size() < size){
				return;
			}
			partnerWithdraw(map);
		}
	}
	
	/**
	 * 购票订单超时处理
	 * @throws Exception 
	 */
	private void buyTicketOrders(Map<String, Object> map) throws Exception{
		List<String> orders = ordersMapper.selectTimeoutOrders(map);
		if(orders != null && orders.size() > 0){
			for (String orderId : orders) {
				ordersService.verifyOrders(orderId);
			}
			if(orders.size() < size){
				return ;
			}
			buyTicketOrders(map);
		}
	}
	
	/**
	 * 购票订单超时处理
	 * @throws Exception 
	 */
//	private void buyTicketOrders(Map<String, Object> map) throws Exception{
//		List<String> orders = ordersMapper.selectTimeoutOrders(map);
//		if(orders != null && orders.size() > 0){
//			for (int i = 0 ; i < orders.size() ; i++) {
//				ordersService.verifyOrders(orders.get(i));
//			}
//			if(orders.size() < size){
//				return ;
//			}
//			map.put("orderId", orders.get(size - 1));
//			buyTicketOrders(map);
//		}
//	}
	
	
	/**
	 * 充值订单超时处理
	 * @param timestamp 
	 * @throws Exception 
	 */
	private void buyGoldCoinOrders(Map<String, Object> map) throws Exception{
		List<String> orders = goldCoinOrdersMapper.selectTimeoutOrders(map);
		if(orders != null && orders.size() > 0){
			for (String orderId : orders) {
				goldCoinOrdersService.verifyOrders(orderId);
			}
			if(orders.size() < size){
				return ;
			}
			buyGoldCoinOrders(map);
		}
		
	}
	
	
	/**
	 * 票支付处理
	 * @param map
	 * @throws Exception 
	 */
	private void payShopOrders(Map<String, Object> map) throws Exception {
		List<String> orders = shopTradeOrdersMapper.selectTimeoutOrders(map);
		if(orders != null && orders.size() > 0){
			for (String orderId : orders) {
				shopTradeOrdersService.verifyOrders(orderId);
			}
			if(orders.size() < size){
				return ;
			}
			payShopOrders(map);
		}
	}

	
	/**
	 * 个人优惠券过期处理
	 * @param map
	 */
	private void couponsTimeout(Map<String, Object> map) {
		couponsMapper.updateCouponsTimeout(map);
	}
	
	/**
	 * 商家优惠券过期处理
	 * @param map
	 */
	private void sTimeout(Map<String, Object> map) {
		//商家优惠券
		List<Long> sitec = siteCouponsMapper.selectByTimeout(map);
		if(sitec.size() > 0){
			for (Long id : sitec) {
				siteCouponsService.updateCouponsTimeout(id);
			}
		}
		//店铺优惠券
		List<Long> shopc = shopCouponsMapper.selectByTimeout(map);
		if(shopc.size() > 0){
			for (Long id : shopc) {
				shopCouponsService.updateCouponsTimeout(id);
			}
		}
	}
	
	
	/**
	 * 店铺提现
	 * @param map
	 * @throws Exception
	 */
	private void shopWithdraw(Map<String, Object> map) throws Exception{
		List<ShopWithdrawOrders> orders = shopWithdrawOrdersMapper.selectByStatus(map);
		if(orders != null && orders.size() > 0){
			boolean []isEnough = new boolean[]{true};
			for (ShopWithdrawOrders order : orders) {
				accountService.shopAccountWithdraw(order,isEnough);
				if(!isEnough[0]){
					return;
				}
			}
			if(orders.size() < size){
				return;
			}
			shopWithdraw(map);
		}
	}

// ===========================================================================
	
	
	/**
	 * 退票处理
	 */
	public void refundTicket() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("size", size);
		try{
			//退票
			refundTicket(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		try{
			//活动退报名款
			refundActivity(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	/**
	 * 用户提现处理
	 */
	public void userWithdraw() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("size", size);
		map.put("date", new Timestamp(ExDateUtils.getDate().getTime()));
		try {
			//钓场主提现
			withdraw(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			//合伙人提现
			partnerWithdraw(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			//店铺提现
			shopWithdraw(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	/**
	 * 优惠券处理
	 */
	public void coupons() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("size", size);
//		map.put("date", new Timestamp(ExDateUtils.getDate().getTime()));
		map.put("date", Integer.parseInt(ExDateUtils.getCurrentDayFormat()));
		try {
			//处理个人优惠券
			couponsTimeout(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
		try {
			//处理商家优惠券
			sTimeout(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
	}


	/**
	 * 第三方支付超时订单处理
	 */
	public void pay() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("size", size);
		map.put("date", new Timestamp(ExDateUtils.getDate().getTime() - (outTime * 1000)));
		try {
			//处理购票超时订单
			buyTicketOrders(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		try {
			//处理充值漂币超时订单
			buyGoldCoinOrders(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		try {
			//处理票支付订单
			payShopOrders(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
