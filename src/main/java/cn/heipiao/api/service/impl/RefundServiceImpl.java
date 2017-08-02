/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.RefundMapper;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.RefundService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.service.UserBalanceOpService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年7月20日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class RefundServiceImpl implements RefundService {

	@Resource
	private RefundMapper refundMapper;
	
	@Resource
	private SystemRecordGeneratorService srgs;
	
	@Resource
	private UserBalanceOpService ubo;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource(name="PayService")
	private PayService payService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private AccountBillService accountBillService;
	
//	@Resource
//	private JPushService jPushService;
	@Resource
	private SystemMsgService systemMsgService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.RefundService#confirmRefund(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public boolean confirmRefund(String refundNo) throws Exception {
		Refund r = refundMapper.selectByRefundNoAsLock(refundNo);
		boolean b = false;
		if(r.getStatus().intValue() < 2){
			Orders order = ordersMapper.selectAsLockById(r.getOrderId());
//			Orders or = ordersMapper.selectById(r.getOrderId());
			//验证退款金额和订单金额
//			if(ArithUtil.compareTo(order.getPayMoney(), ArithUtil.add(order.getRefundFee(), r.getRefundFee())) == -1)
//				return b;
			
			b = refundTicket(r,order);
		}
		return b;
	}

	/**
	 * 退票业务
	 * @param r
	 * @param order
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public boolean refundTicket(Refund r, Orders order) throws Exception {
		boolean b = false;
		switch (r.getTradePlatform().intValue()) {
//		case 0:
//			//平台退款
//			break;
		case 1:
			
			if(r.getStatus() == 0){
				//微信退款申请
				b = payService.wechatRefund(r.getOrderId(), r.getRefundNo(), ArithUtil.convertsToInt(r.getTotalFee() * 100) 
						,ArithUtil.convertsToInt(r.getRefundFee() * 100),order.getWhereIsApp());
				if(b){
					r.setStatus(1);
				}else{
					//验证订单是否全额退款
					b = payService.wechatQueryRefundByOutTradeNo(r.getOrderId(), r.getRefundNo(),order.getWhereIsApp());
					r.setStatus(b ? 2 : 0);
				}
				refundMapper.updatePart(r);
			}else if(r.getStatus() == 1){
				//查询退款
				Map<String, String> m = payService.wechatQueryRefundByOutRefundNo(r.getRefundNo(),order.getWhereIsApp());
				if(m.get("refund_status_0") != null && m.get("refund_status_0").equalsIgnoreCase(PayParams.success)){
					r.setStatus(2);
//					r.setConfirmRefundTime(new Timestamp(ExDateUtils.getDate().getTime()));
					r.setConfirmRefundTime(new Timestamp(ExDateUtils.getDate(m.get("refund_success_time_0"), "yyyy-MM-dd HH:mm:ss").getTime()));
					refundMapper.updatePart(r);
					//添加交易记录
					srgs.generateAccountInRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.TICKET_REFUND_WX, r.getRefundFee(),
							"微信退票", "", 1,order.getFishSiteId());
					//添加账单
					accountBillService.addPojo(order.getUid(), r.getOrderId(), 1, 11, 1, r.getRefundFee(), "退票-微信");
					b = true;
					
					systemMsgService.saveMsg("啊哦~您已经退票了", null, "微信退票成功",r.getUid().intValue() , null);
				}
			}
			
//			//查询退款
//			Map<String, String> m = payService.wechatQueryRefundByOutRefundNo(r.getRefundNo());
//			if(!(m.get("return_code").equalsIgnoreCase(PayParams.success) 
//					&& m.get("result_code").equalsIgnoreCase(PayParams.success))){
//				//微信退款申请
//				b = payService.wechatRefund(r.getOrderId(), r.getRefundNo(), ArithUtil.convertsToInt(r.getTotalFee() * 100) ,ArithUtil.convertsToInt(r.getRefundFee() * 100));
//			}else {
//				if(m.get("refund_status_0") != null && m.get("refund_status_0").equalsIgnoreCase(PayParams.success)){
//					r.setStatus(2);
//					r.setConfirmRefundTime(new Timestamp(ExDateUtils.getDate().getTime()));
//					refundMapper.updatePart(r);
//		//			ordersMapper.updateRefundFee(order.getOrderId(), ArithUtil.add(order.getRefundFee(), r.getRefundFee()));
//					//调度线程处理微信支付状态并添加交易记录
//					srgs.generateAccountInRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.TICKET_REFUND_WX, r.getRefundFee(),
//							"微信退票", "", 1,order.getFishSiteId());
//					//添加账单
//					accountBillService.addPojo(order.getUid(), r.getOrderId(), 1, 11, 1, r.getRefundFee(), "退票-微信");
//					b = true;
//					
//					systemMsgService.saveMsg("啊哦~您已经退票了", null, "微信退票成功",r.getUid().intValue() , null);
//					
//				}
//			}
			break;
		case 2:
			//查询退款
			if(!payService.aliRefundQuery(r.getOrderId(), r.getRefundNo())){
				
				//支付宝退款
				b = payService.aliRefund(r.getRefundNo(), r.getOrderId(), BigDecimal.valueOf(r.getRefundFee().doubleValue()).toString());
				if(b){
					r.setStatus(2);
					r.setConfirmRefundTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
					refundMapper.updatePart(r);
	//				ordersMapper.updateRefundFee(order.getOrderId(), ArithUtil.add(order.getRefundFee(), r.getRefundFee()));
					//退存鱼
	//				if(r.getDepositFee().doubleValue() > 0){
	//					ubo.inUserDepositFishBalance(order.getUid(), order.getFishSiteId(), r.getDepositFee());
	//					srgs.generateInDepositFishRecord(order.getUid(), order.getFishSiteId(), r.getDepositFee());
	//				}
					srgs.generateAccountInRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.TICKET_REFUND_ALI, r.getRefundFee(),
							"支付宝退票", "", 1,order.getFishSiteId());
					//添加账单
					accountBillService.addPojo(order.getUid(), r.getOrderId(), 1, 11, 1, r.getRefundFee(), "退票-支付宝");
					
					systemMsgService.saveMsg("啊哦~您已经退票了", null, "支付宝退票",r.getUid().intValue() , null);
				}
			}
			break;
//		case 3:
//			//存鱼
//			r.setStatus(2);
//			refundMapper.updateStatus(r);
//			ubo.inUserDepositFishBalance(order.getUid(), order.getFishSiteId(), r.getRefundFee());
//			srgs.generateInDepositFishRecord(order.getUid(), order.getFishSiteId(), r.getRefundFee());
//			b = true;
//			break;
		}
		return b;
	}
	
	@Override
	public void refundGoldAndDeposit(Refund r){
		if(r.getDepositFee() != null && r.getDepositFee().doubleValue() > 0){
			ubo.inUserDepositFishBalance(r.getUid(), r.getFishSiteId(),r.getDepositFee());
			srgs.generateInDepositFishRecord(r.getUid(), r.getFishSiteId(), r.getDepositFee());
			//添加账单
			accountBillService.addPojo(r.getUid(), r.getOrderId(), 1, 11, 3, r.getDepositFee(), "退票-存鱼");
		}
		if(r.getGoldCoinFee() != null && (r.getGoldCoinFee().intValue() > 0 || r.getEarningsGoldCoinFee() > 0)){
			accountService.addGoldCoin(r);
			//添加账单
			accountBillService.addPojo(r.getUid(), r.getOrderId(), 1, 11, 2, ArithUtil.div((double)(r.getEarningsGoldCoinFee() + r.getGoldCoinFee()),100,2), "退票-漂币");
		}
	}
	
	/**
	 * 微信更新退款状态
	 * @param out_refund_no
	 */
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void updateWxRefundStatus(String refundNo){
		Refund r = refundMapper.selectByRefundNoAsLock(refundNo);
		if(r != null && r.getStatus().intValue() == 1){
			r.setStatus(2);
			refundMapper.updateStatus(r);
			srgs.generateAccountInRecord(r.getUid().intValue(), r.getOrderId(), ApiConstant.TradeType.TICKET_REFUND_WX, r.getRefundFee(),
					"微信退票", "", 1,r.getFishSiteId());
		}
	}
	
	
}
