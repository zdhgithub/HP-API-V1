package cn.heipiao.api.service.impl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.mapper.CampaignMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.CampaignDetail;
import cn.heipiao.api.pojo.CampaignType;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.resources.cp.ActivityResource;
import cn.heipiao.api.service.CampaignService;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.service.PayService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 活动相关
 * @author Chris
 * @version 3.0
 * @date 2017.03.06
 *
 */
@Service
public class CampaignServiceImpl implements CampaignService {
	
	@Resource
	private UserMapper userMapper;

	@Resource
	private CampaignMapper campaignMapper;
	
	@Resource
	private PayService payService;
	
	@Resource
	private NotifyService notifyService;
	
	@Resource(name="PayService")
	private cn.heipiao.api.pay.PayService pay;

	@Override
	public void addCampaign(Campaign campaign) {
		campaignMapper.addCampaign(campaign);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public boolean updateCampaign(Campaign campaign) {
		
		Campaign camp = campaignMapper.getCampaignAsLock(campaign.getId());
		
		//时间发生修改
		if(campaign.getEntryTerminalTime() != null || campaign.getBeginTime() != null || campaign.getEndTime() != null){
			if(campaign.getEntryTerminalTime() == null){
				campaign.setEntryTerminalTime(camp.getEntryTerminalTime());
			}
			if(campaign.getBeginTime() == null){
				campaign.setBeginTime(camp.getBeginTime());
			}
			if(campaign.getEndTime() == null){
				campaign.setEndTime(camp.getEndTime());
			}
			//未发布，发布，暂停
			if(camp.getStatus().intValue() < 2 || camp.getStatus().intValue() == 5){
				if(!ActivityResource.verifyTime(campaign)){
					return false;
				}
			//报名完成
			}else if(camp.getStatus().intValue() == 2){
				if(camp.getCount().intValue() == camp.getQuota().intValue()){
					//不用修改报名时间
					campaign.setEntryTerminalTime(null);
					if(campaign.getBeginTime().getTime() <= ExDateUtils.getCalendar().getTimeInMillis()
							|| campaign.getEndTime().getTime() <= campaign.getBeginTime().getTime()){
						return false;
					}
				}else{
					if(!ActivityResource.verifyTime(campaign)){
						return false;
					}
//					campaignMapper.setCampaignStatus(campaign.getId(), 1);
					campaign.setStatus(1);
				}
			}else {
				return false;
			}
		}
		int count = campaignMapper.updateCampaign(campaign);
		return count == 1;
	}

	@Override
	public Campaign getCampaign(int id) {
		return campaignMapper.getCampaign(id);
	}
	
	@Override
	public Campaign getCampaignById(int id) {
		return campaignMapper.getCampaignById(id);
	}

	@Override
	public List<Campaign> getCampaignListByNormal(int start, int size) {
		return campaignMapper.getCampaignListByNormal(start, size);
	}

	
	@Override
	public Map<String,Object> getCampaignList(int start, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", campaignMapper.getCampaignList(start, size));
		map.put("total", campaignMapper.getCampaignCount());
		return map;
	}
	
	
	@Override
	public CampaignActor getCampaignActor(int cid, int uid) {
		return campaignMapper.getCampaignActor(cid, uid);
	}
	
	@Override
	public Integer getCampaignActorCount(int id) {
		return campaignMapper.getCampaignActorCount(id);
	}

	@Override
	public List<CampaignActor> getCampaignActorList(int id, int top) {
		return campaignMapper.getCampaignActorList(id, top);
	}

	@Override
	public boolean publishCampaign(int id) {
		int count = campaignMapper.setCampaignStatus(id, 1);
		return count == 1;
	}

	@Override
	public boolean delCampaign(int id) {
		int count = campaignMapper.delCampaign(id);
		return count == 1;
	}

	@Override
	@Transactional(readOnly=false, rollbackFor = {Exception.class})
	public String draw(int id) throws Exception {
		Campaign campaign = getCampaign(id);
		
		// 1、状态为2
		int status = campaign.getStatus();
		if (status != 2) {
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "当前状态不满足抽签要求"));
		}
		
		// 2、人数已满
//		int quota = campaign.getQuota();
//		List<CampaignActor> actorList = getCampaignActorList(id, -1);
//		int actorCount = actorList.size();
//		if (campaign.getQuota().intValue() != campaign.getCount().intValue()) {
//			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动报名人数不满足抽签要求"));
//		}
		
		// 抽签
		List<Integer> set = new ArrayList<Integer>();
		for (int i = 1; i <= campaign.getCount(); i++) {
			set.add(i);
		}
		
		Collections.shuffle(set);
		
		List<CampaignActor> actorList = campaignMapper.getCampaignActorList(campaign.getId(), 0);
		for (int i = 0; i < actorList.size(); i++) {
			CampaignActor ca = actorList.get(i);
			ca.setLuckyNumber(set.get(i));
		}
		//更新
		if(actorList != null && actorList.size() > 0){
			campaignMapper.updateBatchCampaignActor(actorList);
		}
		
		
//		Map<String, Integer> lnMap = new HashMap<String, Integer>();
//		for (int i=0; i<campaign.getCount(); i++) {
//			int idx1 = RandomUtils.nextInt(0, actorList.size());
//			int idx2 = RandomUtils.nextInt(0, numList.size());
//			CampaignActor actor = actorList.remove(idx1);
//			Integer num = numList.remove(idx2);
//			lnMap.put(actor.getOrderId(), num);
//		}
		
		// 填充
//		for (Map.Entry<String, Integer> entry : lnMap.entrySet()) {
//			int count = campaignMapper.setLuckyNumber(entry.getKey(), entry.getValue());
//			System.out.println(count);
//		}
		
		// 改状态
		campaignMapper.setCampaignStatus(id, 3);
		return JSONObject.toJSONString(new RespMsg<>());
	}

	@Override
	public CampaignDetail getCampaignDetail(int id) {
//		Integer actorCount = campaignMapper.getCampaignActorCount(id);
//		actorCount = actorCount == null ? 0 : actorCount;
//		
//		Float payAmount = campaignMapper.getCampaignActorPayAmount(id);
//		payAmount = payAmount == null ? 0.0f : payAmount;
//		Campaign camp = campaignMapper.getCampaign(id);
		List<CampaignActor> cas = campaignMapper.getCampaignActorLists(id);
		CampaignDetail detail = null;
		int cou = 0;
		double cost = 0;
		for (CampaignActor campaignActor : cas) {
			if(campaignActor.getPayType() == 1){
				if(cost <= 0)
					cost = campaignActor.getPayAmount();
				cou++;
			}
		}
		detail = new CampaignDetail(cas.size(), ArithUtil.convertsToFloat(ArithUtil.mul(cost, cou))); 
		return detail;
	}

	@Override
	public boolean start(int id, int value) {
		int count = campaignMapper.setCampaignStatus(id, value);
		return count == 1;
	}

	@Override
	public boolean finish(int id) {
		int count = campaignMapper.setCampaignStatus(id, 4);
		return count == 1;
	}

	@Override
	public boolean pause(int id) {
		int count = campaignMapper.setCampaignStatus(id, 5);
		return count == 1;
	}

	@Override
	public String getCampaignRemark(int id) {
		return campaignMapper.getCampaignRemark(id);
	}

	@Override
	public boolean setCampaignRemark(int id, String remark) {
		int count = campaignMapper.setCampaignRemark(id, remark);
		return count == 1;
	}

	@Override
	public boolean cancel(int id) {
		int count = campaignMapper.setCampaignStatus(id, 6);
		return count == 1;
	}

	@Override
	public String enter(Long uid, int cid, String openid, int payType) throws Exception {
		Timestamp entryTime = new Timestamp(ExDateUtils.getCalendar().getTimeInMillis());
		
		Campaign camp = campaignMapper.getCampaign(cid);
		if(camp == null || camp.getStatus() != 1){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动已结束"));
		}
		//报名结束时间的判断
		if(camp.getEntryTerminalTime().getTime() <= entryTime.getTime()){
			campaignMapper.setCampaignStatus(camp.getId(), 2);
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动报名已结束"));
		}
		
		User u = userMapper.selectById(uid);
		if(u == null){
			return JSONObject.toJSONString(new RespMsg<>(Status.no_such_user,RespMessage.getRespMsg(Status.no_such_user)));
		}
		if(payType == 1){
			return enter(uid, cid, openid, payType,camp.getCost(),entryTime);
		}else{
			return enter(uid, cid, null,payType,0.0f,entryTime);
		}
	}

//	@Override
//	public boolean enter(Integer uid, Integer cid, Timestamp entryTime, Float payAmount) {
//		return enter(uid, cid, entryTime, 1, payAmount);
//	}
	
	@Transactional(readOnly=false, isolation=Isolation.READ_COMMITTED, rollbackFor={Exception.class})
	private String enter(Long uid, Integer cid, String openid, int payType, Float payAmount,Timestamp entryTime) throws  Exception {
		
		CampaignActor ca = campaignMapper.getCampaignActor(cid, uid.intValue());
		if(ca != null && ca.getPayStatus() > 0 && ca.getPayStatus() < 3){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "您已报名"));
		}
		Campaign camp = campaignMapper.getCampaignAsLock(cid);
		if(camp.getStatus() != 1){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动已结束"));
		}
		if(camp.getQuota() <= camp.getCount()){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error, "活动人数已满"));
		}
		
		if(ca != null){
			ca = campaignMapper.getCampaignActorAsLock(ca.getOrderId());
			ca.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + "04" + uid);
			ca.setEntryTime(entryTime);
			ca.setPayAmount(payAmount);
			ca.setPayStatus(payType == 1 ? 0 : 2);
			ca.setPayType(payType);
			ca.setRefundStatus(0);
			campaignMapper.updateCampaignActorByCidAndUid(ca);
		}else{
			ca = new CampaignActor();
			ca.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + "04" + uid);
			ca.setEntryTime(entryTime);
			ca.setPayAmount(payAmount);
			ca.setPayStatus(payType == 1 ? 0 : 2);
			ca.setPayType(payType);
			ca.setRefundStatus(0);
			ca.setUid(uid.intValue());
			ca.setCid(cid);
			campaignMapper.addActor(ca);
		}
		
		camp.setCount(camp.getCount() + 1);
		
		if(camp.getCount().intValue() == camp.getQuota().intValue()){
			camp.setStatus(2);
//			campaignMapper.setCampaignStatus(camp.getId(), 2);
		}
		
		campaignMapper.updateCampaign(camp);
		
		if(ca.getPayType() == 1){
			
			return payService.generatePayParam(uid, 1, ca.getOrderId(), InetAddress.getLocalHost().getHostAddress(), camp.getName()
					, Integer.valueOf(PayService.activity), openid);
		}
		
		return JSONObject.toJSONString(new RespMsg<>());
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int payActivityConfirm(Long uid, Integer cid) throws Exception {
		CampaignActor ca = campaignMapper.getCampaignActor(cid, uid.intValue());
		if(ca == null){
			return Status.value_is_null_or_error;
		}
		CampaignActor caa = campaignMapper.getCampaignActorAsLock(ca.getOrderId());
		if(caa.getPayStatus() == 1){
			return Status.success;
		}
		if(caa.getPayType() != 1 ){
			return Status.value_is_null_or_error;
		}
		int i = notifyService.verifyOrders(1, caa.getOrderId(), PayService.activity, 2);
		if(i == 0){
			cancelEnter(caa.getOrderId());
		}
		return Status.success;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int cancelEnter(String orderId) throws Exception {
		CampaignActor ca = campaignMapper.getCampaignActorAsLock(orderId);
		if(ca == null || ca.getPayStatus() != 0){
			return Status.value_is_null_or_error;
		}
		ca.setPayStatus(3);
		Campaign camp = campaignMapper.getCampaignAsLock(ca.getCid());
		if(camp != null){
			camp.setCount(camp.getCount() - 1);
			if(camp.getEndTime().getTime() > ExDateUtils.getCalendar().getTimeInMillis()){
				camp.setStatus(1);
			}
		}
		campaignMapper.updateCampaign(camp);
		campaignMapper.updateCampaignActor(ca);
		return Status.success;
	}

	@Override
	public List<CampaignType> getCampaignTypesAll() {
		return campaignMapper.getCampaignTypesAll();
	}

	@Override
	public void putVideo(Campaign camp) {
		campaignMapper.updateCampaign(camp);
	}

}
