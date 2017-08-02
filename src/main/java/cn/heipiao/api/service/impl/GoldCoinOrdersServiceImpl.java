/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AliPayNotifyMapper;
import cn.heipiao.api.mapper.GoldCoinOrdersMapper;
import cn.heipiao.api.mapper.WxPayNotifyMapper;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.GoldCoinOrdersService;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年8月19日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class GoldCoinOrdersServiceImpl implements GoldCoinOrdersService {

	/** 订单超时时间 - 单位秒 **/
	@Value("${order.outTime}")
	private Long outTime;
	
	@Resource
	private GoldCoinOrdersMapper goldCoinOrdersMapper;
	
	@Resource
	private NotifyService notifyService;
	
	@Resource(name="PayService")
	private PayService pay;
	
	@Resource
	private WxPayNotifyMapper wxPayNotifyMapper;
	
	@Resource
	private AliPayNotifyMapper aliPayNotifyMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#getGoldCoinOrdersByOrderId(java.lang.String)
	 */
	@Override
	public GoldCoinOrders getGoldCoinOrdersByOrderId(String orderId) {
		return goldCoinOrdersMapper.selectByOrderId(orderId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#addGoldCoinOrders(cn.heipiao.api.pojo.GoldCoinOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addGoldCoinOrders(GoldCoinOrders pojo) {
		Calendar c = ExDateUtils.getCalendar();
		pojo.setCreateTime(new Timestamp(c.getTimeInMillis()));
		//訂單狀態
		pojo.setStatus(0);
		//贈送漂幣
		pojo.setGivingGoldCoin(0);
		//订单的生成方式  当前日期 + 固定长度uid + 业务号 + 24小时的长度(秒)
		pojo.setOrderId(ExDateUtils.getCurrentDayFormat() + RandomNumberUtils.getFixedLengthNum(10, pojo.getUid().toString()) + "02"
			+ ((ExDateUtils.getCalendar().getTimeInMillis() - ExDateUtils.getCurrentMin(c).getTime()) / 1000));
		goldCoinOrdersMapper.insertPojo(pojo);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#cancelGoldCoinOrders(cn.heipiao.api.pojo.GoldCoinOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int cancelGoldCoinOrders(String orderId) throws Exception {
		GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderIdAsLock(orderId);
		//判断订单
		if(gco == null || gco.getStatus() != 0){
			return Status.orders_error;
		}
		
		//判断平台
//		int rs = verifyOrders(gco.getTradePlatform(), orderId);
//		if(rs != 0){
//			return rs;
//		}
		
		//取消订单
		if((gco.getCreateTime().getTime() + outTime * 1000) < ExDateUtils.getDate().getTime()){
			gco.setStatus(2);
		}else{
			gco.setStatus(3);
		}
		goldCoinOrdersMapper.updatePojo(gco);
		
		//关闭未支付订单
		pay.closeTrade(gco.getOrderId(),gco.getTradePlatform(),gco.getWhereIsApp());
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#verifyOrders(int, java.lang.String)
	 */
//	@Override
//	public int verifyOrders(int tradePlatform, String orderId) throws Exception {
//		int result = Status.orders_error;
//		if(tradePlatform == 1){
//			WxPayNotify wx = wxPayNotifyMapper.selectWxPayNotifyByOutTradeNo(orderId);
//			result = notifyService.wxTradeQueryResult(orderId,wx);
//			if(result == 1){
//				return notifyService.wxOrdersSuccess(wx);
//			 }
//			if(result == -1){
//				return notifyService.orderFail(orderId,wx.getAttach());
//			}
//		} else if (tradePlatform == 2){
//			AliPayNotify ali = aliPayNotifyMapper.selectAliPayNotifyByOutTradeNo(orderId);
//			result =notifyService.aliTradeQueryResult(orderId,ali);
//			if(result == 1){
//			   return notifyService.aliOrdersSuccess(ali);
//			}
//			if(result == -1){
//				return notifyService.orderFail(orderId,ali.getAttach());
//			}
//		}
//		return result;
//	}

//	/**
//	 * @param ali
//	 * @return
//	 * @throws Exception 
//	 */
//	private int aliOrdersSuccess(AliPayNotify ali) throws Exception {
//		if(notifyService.aliNotify(ali).equals(PayParams.success)){
//			return Status.orders_paid;
//		}else{
//			return Status.orders_error;
//		}
//	}
//
//	/**
//	 * @param orderId
//	 * @return
//	 * @throws Exception 
//	 */
//	private int orderFail(String orderId) throws Exception {
//		if(cancelGoldCoinOrders(orderId) == Status.success){
//			return Status.orders_cancel;
//		} else {
//			return Status.orders_error;
//		}
//	}
//
//	/**
//	 * @param wx
//	 * @return
//	 */
//	private int wxOrdersSuccess(WxPayNotify wx) {
//		if(notifyService.wxNotify(wx).equals(PayParams.responseXml(PayParams.success))){
//			return Status.orders_paid;
//		}else {
//			return Status.orders_error;
//		}
//	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.GoldCoinOrdersService#verifyOrders(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public void verifyOrders(String orderId) throws Exception {
		GoldCoinOrders order = goldCoinOrdersMapper.selectByOrderIdAsLock(orderId);
		if(order != null && order.getStatus() == 0){
			int i = notifyService.verifyOrders(order.getTradePlatform(), order.getOrderId(),cn.heipiao.api.service.PayService.payGoldCoin,order.getWhereIsApp());
			if(i == 0 && order.getStatus() ==0 && (order.getCreateTime().getTime() + outTime * 1000) < ExDateUtils.getDate().getTime()){
				cancelGoldCoinOrders(orderId);
			}
		}
	}

}
