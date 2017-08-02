package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CampaignMapper;
import cn.heipiao.api.mapper.RefundActivityMapper;
import cn.heipiao.api.pay.PayParams;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.RefundActivity;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.RefundActivityService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2017年3月11日
 */
@Service
public class RefundActivityServiceImpl implements RefundActivityService {

	@Resource
	private RefundActivityMapper refundActivityMapper;
	
	@Resource
	private CampaignMapper campaignMapper;
	
	@Resource
	private PayService payService;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int createRefundApply(RefundActivity ra) {
		CampaignActor ca = campaignMapper.getCampaignActorAsLock(ra.getOrderId());
		if(ca == null || ca.getCid().intValue() != ra.getCid().intValue() ||  ca.getRefundStatus() == 1
				|| ca.getPayStatus() < 1 ||ca.getPayStatus() > 2){
			return Status.value_is_null_or_error;
		}
		
		if(ca.getPayType().intValue() == 1){
			ra.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			ra.setRefundFee(ca.getPayAmount().doubleValue());
			ra.setTotalFee(ca.getPayAmount().doubleValue());
			ra.setStatus(0);
			ra.setTradePlatform(1);
			ra.setUid(ca.getUid().longValue());
			ra.setRefundNo(ExDateUtils.getCurrentDayFormat("yyyyMMddHHmmss") + ca.getUid());
			refundActivityMapper.insertPojo(ra);
		}
		ca.setPayStatus(3);
		ca.setRefundStatus(1);
		campaignMapper.updateCampaignActor(ca);
		//减人数
		Campaign camp = campaignMapper.getCampaignAsLock(ra.getCid());
		//如果退报名，判断是否已经爆满，如果爆满，并且时间为正常报名期限内，将status设置为1：报名中
		if(camp.getCount().intValue() == camp.getQuota().intValue()
				&& camp.getEntryTerminalTime().getTime() > ExDateUtils.getCalendar().getTimeInMillis()){
//			campaignMapper.setCampaignStatus(ra.getCid(), 1);
			camp.setStatus(1);
		}
		camp.setCount(camp.getCount() - 1 );
		campaignMapper.updateCampaign(camp);
		return Status.success;
	}

	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public void refund(String refundNo) throws Exception {
		RefundActivity ra = refundActivityMapper.selectByRefundNoAsLock(refundNo);
		if(ra.getStatus() == 0){
			//微信退款申请
			boolean b  = payService.wechatRefund(ra.getOrderId(), ra.getRefundNo(), ArithUtil.convertsToInt(ra.getTotalFee() * 100) 
					,ArithUtil.convertsToInt(ra.getRefundFee() * 100),2);
			if(b){
				ra.setStatus(1);
			}else{
				//验证订单是否全额退款
				b = payService.wechatQueryRefundByOutTradeNo(ra.getOrderId(), ra.getRefundNo(),2);
				ra.setStatus(b ? 2 : 0);
			}
			refundActivityMapper.updateStatus(ra);
		}else if(ra.getStatus() == 1){
			//查询退款
			Map<String, String> m = payService.wechatQueryRefundByOutRefundNo(ra.getRefundNo(),2);
			if(m.get("refund_status_0") != null && m.get("refund_status_0").equalsIgnoreCase(PayParams.success)){
				ra.setStatus(2);
				ra.setConfirmRefundTime(new Timestamp(ExDateUtils.getDate(m.get("refund_success_time_0"), "yyyy-MM-dd HH:mm:ss").getTime()));
				refundActivityMapper.updatePart(ra);
				
				//添加账单
				accountBillService.addPojo(ra.getUid(), ra.getOrderId(), 1, 21, 1, ra.getRefundFee(), "退活动报名费-微信");
				systemMsgService.saveMsg("啊哦~您已经报名费了", null, "微信退活动报名费成功",ra.getUid().intValue() , null);
			}
		}
	}

	@Override
	public int activityRefund(RefundActivity ra) throws Exception {
		int rs = verifyCamp(ra.getCid());
		if( rs != Status.success){
			return rs;
		}
		return dealRefund(ra);
	}

	@Override
	public int activityRefunds(Integer cid) throws Exception {
		int rs = verifyCamp(cid);
		if( rs != Status.success){
			return rs;
		}
		List<CampaignActor> list = campaignMapper.getCampaignActorAll(cid);
		if(list.size() > 0){
			for (CampaignActor ca : list) {
				RefundActivity ra = new RefundActivity();
				ra.setCid(cid);
				ra.setOrderId(ca.getOrderId());
				activityRefund(ra);
			}
			
		}
		return Status.success;
	}

	
	private int verifyCamp(Integer cid){
		Campaign camp = campaignMapper.getCampaign(cid);
//		if(camp == null || camp.getStatus() != 6){
		if(camp == null){
			return Status.value_is_null_or_error;
		}
		return Status.success;
	}
	
	private int dealRefund(RefundActivity ra) throws Exception{
		//退款申请创建
		int rs = createRefundApply(ra);
		if(rs == Status.success && ra.getRefundNo() != null){
			//退款
			refund(ra.getRefundNo());
		}
		return rs;
	}
	
}
