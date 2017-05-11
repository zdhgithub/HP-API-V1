/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.FishPondMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.GoodsMapper;
import cn.heipiao.api.mapper.OrdersDetailsMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PoleMapper;
import cn.heipiao.api.mapper.RefundMapper;
import cn.heipiao.api.mapper.RewardAmountMapper;
import cn.heipiao.api.mapper.RewardDetailMapper;
import cn.heipiao.api.mapper.SequenceMapper;
import cn.heipiao.api.mapper.TicketCodeMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.UserRecommendMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Goods;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.OrdersDetails;
import cn.heipiao.api.pojo.Pole;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardDetail;
import cn.heipiao.api.pojo.RewardPlatform;
import cn.heipiao.api.pojo.Sequence;
import cn.heipiao.api.pojo.TicketCode;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.pojo.VerifyTicketRecord;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.PartnerEarningOrdersService;
import cn.heipiao.api.service.PartnerEarningRecordService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.RefundService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.service.UserBalanceOpService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.service.UserTicketsService;
import cn.heipiao.api.service.VerifyTicketTradeOrdersService;
import cn.heipiao.api.service.WebService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年7月1日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserTicketsServiceImpl implements UserTicketsService {

	
	@Resource
	private UserTicketsMapper userTicketsMapper;	
	
	@Resource
	private OrdersDetailsMapper ordersDetailsMapper;
	
	@Resource
	private SystemRecordGeneratorService srgs;
	
	@Resource
	private UserBalanceOpService ubo;
	
	@Resource
	private RefundMapper refundMapper;
	
	@Resource
	private RefundService refundService;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private AccountMapper accountMapper;
	
	@Resource
	private PoleMapper poleMapper;
	
	@Resource
	private FishPondMapper fishPondMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private TicketCodeMapper ticketCodeMapper;
	
	@Resource
	private SequenceMapper sequenceMapper;
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private UserRecommendMapper userRecommendMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	@Resource
	private RewardDetailMapper rewardDetailMapper;
	
	@Resource
	private PartnerService partnerService;
	
	@Resource
	private PartnerEarningRecordService partnerEarningRecordService;
	
	@Resource
	private WebService webService;
	
	@Resource
	private PartnerEarningOrdersService partnerEarningOrdersService;
	
	@Resource
	private VerifyTicketTradeOrdersService verifyTicketTradeOrdersService;
	
	@Resource
	private JPushService jPushService;
	
	@Resource
	private UserRewardService userRewardService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private RewardPlatformService rewardPlatformService;
	
	@Resource
	private RewardAmountMapper rewardAmountMapper;
	
	@Resource
	private AccountBillService accountBillService;
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#selectTicketsBySite(java.util.Map)
	 */
	@Override
	public List<UserTickets> selectTicketsBySite(Map<String, Object> map) {
		return userTicketsMapper.selectTicketsBySite(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#selectTicketsByUid(java.util.Map)
	 */
	@Override
	public List<UserTickets> selectTicketsByUid(Map<String, Object> map) {
		return userTicketsMapper.selectTicketsByUid(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#insertBatch(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public void insertBatch(List<UserTickets> pojos) {
		userTicketsMapper.insertBatch(pojos);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#refundTicker(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public int refundTickerApply(Refund r) throws Exception {
		//查询票并判断
		UserTickets ut = userTicketsMapper.selectTicketsAsLockByTid(r.getTid());
		if(ut == null || ut.getStatus().intValue() != 0 
				|| r.getUid().longValue() != ut.getUid().longValue())
			return Status.userTicket_invalid;
		
		if(ut.getExpirationDate().getTime() < ExDateUtils.getCalendar().getTimeInMillis()){
			setExpiration(ut);
			return Status.userTicket_not_refund;
		}
		
		//查询原始订单
		Orders order = ordersMapper.selectAsLockById(ut.getOrderId());
		if(order == null || order.getStatus().intValue() != 1){
			return Status.orders_error;
		}
		
		//生成退款订单
		r.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		r.setOrderId(ut.getOrderId());
		r.setRefundFee(ut.getOutOfPocket());
		r.setDepositFee(ut.getDepositFee());
		r.setGoldCoinFee(ut.getGoldCoinFee());
		r.setEarningsGoldCoinFee(ut.getEarningsGoldCoinFee());
		r.setFishSiteId(ut.getFishSiteId());
		r.setTradePlatform(order.getTradePlatform());
		if(order.getTradePlatform().intValue() == 0){
			r.setStatus(2);
		}else{
			r.setStatus(0);
		}
		r.setTradeNo(order.getTradeNo());
		r.setTotalFee(order.getPayMoney());
		
		//生成退款单号
		r.setRefundNo(ExDateUtils.getDateFormatToSecond() + RandomNumberUtils.getFixedLengthNum(10, r.getUid().toString()));
//		try {
			refundMapper.insertPojo(r);
//		} catch (Exception e) {
//			if(e.getMessage().trim().contains(String.format("Duplicate entry '%s' for key 'PRIMARY'",r.getRefundNo()))){
//				try {
//					//二次生成退款单号
//					r.setRefundNo(ExDateUtils.getCurrentDayFormat() + RandomNumberUtils.getVerifyNum(8));
//				} catch (Exception e1) {
//					throw e1;
//				}
//			} else {
//				throw e;
//			}
//		}
			
		//更新库存
		if(ut.getGoodId() != null){
			Goods g = goodsMapper.selectByIdAsLock(ut.getGoodId());
			if(g != null){
				g.setAmount(g.getAmount() + 1);
				goodsMapper.updateById(g);
			}
		}
			
		//更新退款金额
		ordersMapper.updateRefundFee(order.getOrderId(), ArithUtil.add(order.getRefundFee(),ut.getOutOfPocket()));
		//更新票状态已退票
		ut.setStatus(2);
		userTicketsMapper.updateStatus(ut);
		
		//退漂币和存鱼
		refundService.refundGoldAndDeposit(r);
		
		return Status.success;
	}
	
	/**
	 * @param ut 
	 * 设置票过期
	 */
	private void setExpiration(UserTickets ut) {
		ut.setStatus(3);
		userTicketsMapper.updateStatus(ut);
	}

	/**
	 * 生成票业务
	 * 
	 * @see OrdersServiceImpl 中的addOrder
	 * 
	 */
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public  void paySuccessDeal(Orders order) {
		// 生成票
		List<OrdersDetails> ordersDetails = ordersDetailsMapper.selectByOrderId(order.getOrderId());
		List<UserTickets> uts = new ArrayList<UserTickets>();
		UserTickets ut = null;
		Timestamp t = new Timestamp(ExDateUtils.getDate().getTime());
		//退票的有效期
		Calendar c = ExDateUtils.getCalendar();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 30);
		Timestamp e = new Timestamp(c.getTimeInMillis());
		Sequence sequence = sequenceMapper.selectByIdAsLock(order.getFishSiteId());
		Integer count = sequence.getCount();
		//判断是否平均分配(如果支付方式是一种以上的支付)
//		boolean isPro = order.getCouponId() != null || order.getDepositMoney() > 0 || order.getGoldCoinMoney() > 0; 
		boolean isPro = OrdersServiceImpl.isPros(order.getCouponId()
										,order.getDepositMoney() > 0 ? order.getDepositMoney() : null
										,order.getGoldCoinMoney() > 0 ? order.getGoldCoinMoney() : null
										,order.getEarningsGoldCoinMoney() > 0 ? order.getEarningsGoldCoinMoney() : null
										,order.getPayMoney() > 0?order.getPayMoney() : null);
		Double [] depositPro = null;
		Integer [] coinPro = null;
		Integer [] earningsCoinPro = null;
		Double [] balancePro = null;
		Double [] couPro = null;
		Integer [] cfPro = null;
		int n = 0;
		for (OrdersDetails od : ordersDetails) {
			if(isPro){
				depositPro = null;
				coinPro = null;
				earningsCoinPro = null;
				balancePro = null;
				couPro = null;
				cfPro = null;
				//将存鱼,漂币,支付金额
				if(od.getDepositFee() > 0){
					depositPro = ArithUtil.prorate(od.getDepositFee(), od.getAmount());
				}
				if(od.getGoldCoinFee() > 0){
					coinPro = ArithUtil.prorate(od.getGoldCoinFee(), od.getAmount());
				}
				if(od.getEarningsGoldCoinFee() > 0){
					earningsCoinPro = ArithUtil.prorate(od.getEarningsGoldCoinFee(), od.getAmount());
				}
				if(od.getPayPrice() > 0){
					balancePro = ArithUtil.prorate(od.getPayPrice(), od.getAmount());
				}
				if(od.getCouponsFee() > 0){
					couPro = ArithUtil.prorate(od.getCouponsFee(), od.getAmount());
				}
				if(od.getCouponFee() > 0){
					cfPro = ArithUtil.prorate(od.getCouponFee(), od.getAmount());
				}
			}
			for (int i = 0; i < od.getAmount().intValue(); i++) {
				ut = new UserTickets();
				ut.setTicketName(od.getGoodsName());
				ut.setBuyTime(t);
				ut.setDuration(od.getHourLong());
				ut.setExpirationDate(e);
				ut.setFishSiteId(order.getFishSiteId());
				ut.setOrderId(od.getOrderId());
				//单张票实际支付金额
				ut.setOutOfPocket(isPro ? balancePro == null ? 0D : balancePro[i] : od.getPayPrice());
				//单张票使用的存鱼数量
				ut.setDepositFee(isPro ? depositPro == null ? 0D : depositPro[i] : od.getDepositFee());
				//单张票使用的漂币数量
				ut.setGoldCoinFee(isPro ? coinPro == null ? 0 : coinPro[i] : od.getGoldCoinFee());
				//单张票使用的收益漂币数量
				ut.setEarningsGoldCoinFee(isPro ? earningsCoinPro == null ? 0 : earningsCoinPro[i] : od.getEarningsGoldCoinFee());
				//平台优惠券金额
				ut.setCouponsFee(isPro ? couPro == null ? 0D : couPro[i] : od.getCouponsFee());
				//优惠券收费费用
				ut.setCouponFee(isPro ? cfPro == null ? 0 : cfPro[i] : od.getCouponFee());
				ut.setCouponId(order.getCouponId());
				ut.setPid(od.getPid().longValue());
				ut.setGoodId(od.getGoodId());
				ut.setPrimeCost(od.getDiscountPrice());
				ut.setStatus(0);
				ut.setUid(order.getUid());
				
				uts.add(ut);
				count++;
				//设置主键
				uts.get(n).setTid(Long.valueOf(ut.getFishSiteId().toString() + RandomNumberUtils.getFixedLengthNum(6, count.toString())));
				n++;
			}
		}
		
		//更新序列
		sequence.setCount(count);
		sequenceMapper.updatePojo(sequence);
		
		userTicketsMapper.insertBatch(uts);

		//如果支付金额为0,不记录流水
		if(order.getPayMoney() > 0){
			if(order.getTradePlatform().intValue() == 1){
				srgs.generateAccountDeRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.CONSUMPTION_WX, 
						order.getPayMoney(), "微信消费", "", 1,order.getFishSiteId());
			}else if(order.getTradePlatform().intValue() == 2){
				srgs.generateAccountDeRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.CONSUMPTION_ALI, 
						order.getPayMoney(), "支付宝消费", "", 1,order.getFishSiteId());
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#selectTicketsByTid(java.lang.Long)
	 */
	@Override
	public UserTickets selectTicketsByTid(Long tid) {
		return userTicketsMapper.selectTicketsByTid(tid);
	}

	/* (non-Javadoc)
	 * 核票
	 * @see cn.heipiao.api.service.UserTicketsService#verifyTicket(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public int verifyTicket(Long tid,Integer extraTime,Long euid) throws Exception {
		UserTickets ut = userTicketsMapper.selectTicketsAsLockByTid(tid);
		if(ut == null || ut.getStatus().intValue() != 0 ){
			return Status.userTicket_invalid;
		}
		FishSite fs = fishSiteMapper.selectById(ut.getFishSiteId());
		//获取钓场主账户
		Account account = accountMapper.selectByUidAsLock(fs.getUid()); 
		if(account == null){
			return Status.account_not_exist;
		}
		//查询鱼塘名称
		FishPond fp = fishPondMapper.selectByPondId(ut.getPid());
		if(fp == null){
			return Status.fish_pond_not_exists;
		}
		//修改票信息
		ut.setStatus(1);
		ut.setUseTime(new Timestamp(ExDateUtils.getDate().getTime()));
		userTicketsMapper.updateStatusAndUseTime(ut);
		//清除票的验证码
//		ticketCodeMapper.deleteByTid(ut.getTid());
		//平台核票奖添加到明细     每单金额超过50元   使用存鱼小于每单金额的50%，为有效规则
		RewardPlatform rewardPlatform = rewardPlatformService.findRewardPlatform(fs.getFishSiteId().longValue(), 1,1);
		if(ut.getPrimeCost()>=50 && ut.getDepositFee()<ArithUtil.div(ut.getPrimeCost(), 2, 2)){
			
			if(rewardPlatform != null){
				RewardDetail rewardDetail = new RewardDetail();
				rewardDetail.setTime(new Timestamp(ExDateUtils.getDate().getTime()));
				rewardDetail.setBid(fs.getFishSiteId().longValue());
				rewardDetail.setAmount(rewardPlatform.getBonus());
				rewardDetail.setBtype(1);
				rewardDetail.setType(1);
				rewardDetail.setDescription(ut.getNickname());
				rewardDetailMapper.insertRewardDetail(rewardDetail);
				
				//更新钓场主平台奖励累计奖励金额
				Map<String ,Object> params = new HashMap<String, Object>();
				params.put("bid", fs.getFishSiteId().longValue());
				params.put("type", 1);
				RewardAmount rewardAmount1 = rewardAmountMapper.selectByBidAsLock(params);
				if(rewardAmount1 == null){
					RewardAmount rewardAmount = new RewardAmount();
					rewardAmount.setBalance(0.00);
					rewardAmount.setBid(fs.getFishSiteId().longValue());
					rewardAmount.setRemain(0.00);
					rewardAmount.setTotal(rewardPlatform.getBonus());
					rewardAmount.setType(1);
					rewardAmount.setWithdrawTime(new Timestamp(ExDateUtils.getDate().getTime()));
					rewardAmountMapper.insertRewardPlatformAmount(rewardAmount);
				}else{
					params.put("total",(rewardAmount1.getTotal()+rewardPlatform.getBonus()));
					rewardAmountMapper.updateByBid(params);
				}
				
			
				//合伙人收益
				if(fs.getPartnerId()!=null){
					User u = partnerMapper.selectByPartnerId(fs.getPartnerId());
					if(u !=null){
						partnerEarningOrdersService.addPojo(u.getId(), tid, fs.getPartnerId(), fs.getFishSiteId(), rewardPlatform.getPartnerBonus());
						accountService.addGoldCoin(u.getId(), rewardPlatform.getPartnerBonus()); 
						systemMsgService.saveMsg("票支付平台奖励", null, fs.getFishSiteName() + "已进入账户", u.getId().intValue(), null);
					}
				}
				//消息
				systemMsgService.saveMsg("平台奖励", null, fs.getFishSiteName() + "已进入账户", fs.getUid().intValue(), null);
				//推送消息
				jPushService.sendPushForPay(fs.getUid().intValue(), "B", "核票平台奖励", "获得平台奖励:" + rewardPlatform.getBonus() + "元", null);
			}
		}
	
		/**
		 * 钓场主账户增加余额
		 * 支付金额 + 漂币(充值漂币+收益漂币) + 平台优惠券
		 */
		double addBalance = ArithUtil.add(ut.getOutOfPocket(),ArithUtil.div(ut.getGoldCoinFee(), 100, 2),
				ArithUtil.div(ut.getEarningsGoldCoinFee(), 100, 2),ut.getCouponsFee());
		
		//核票是查看是否使用了收费优惠券
		if(ut.getCouponFee() > 0){
			addBalance = ArithUtil.sub(addBalance, ArithUtil.div(ut.getCouponFee(), 100 ,2));
		}
		
		ubo.inUserBalance(account.getUid(), addBalance);
		srgs.generateAccountInRecord(account.getUid().intValue(), ut.getOrderId(), ApiConstant.TradeType.TICKET_INCOME, addBalance,"" ,"票消费" , 1,null);
		//核票订单 
		verifyTicketTradeOrdersService.addPojo(account.getUid(), ut.getFishSiteId(), ut.getTid(), addBalance);
		
		//如果验票订单有使用平台优惠券，这笔没有收益  .
		//其它情况（包含合伙人自己发的）是有收益，收益计算公式为，该单钓场主实收金额（除掉回鱼）*5%*40% 
		/**
		 * 2.3版本去掉合伙人收益
		 */
//		if(fs.getPartnerId() != null){
//			Coupons c = null;
//			if(ut.getCouponId() != null){
//				c = couponsMapper.selectByCid(ut.getCouponId());
//			}
//			Partner partner = partnerMapper.selectByPartnerId(fs.getPartnerId());
//			if(partner != null && (c == null || c.getCategory() > 0)){
//				//收益为漂币
//				
//				partnerService.siteEarn(partner, fs, ut, (int)(addBalance * 100));
//			}
//		}
		
		//增加看板内容
		Pole p = new Pole();
		Date d = ExDateUtils.getDate();
		p.setUserId(ut.getUid().toString());
		p.setBegin(new Timestamp(d.getTime()));
		p.setChecked(d);
		p.setEnd(new Timestamp(d.getTime() + (int)(ut.getDuration() * 60 * 60 * 1000) + extraTime));
		p.setFishSiteId(ut.getFishSiteId().toString());
		p.setId(ut.getTid().toString());
		p.setIsLeave("1");
		p.setPondName(fp.getFishPondName());
		poleMapper.insert(p);
		
		//查看是否是被推荐的人
		User u = userMapper.selectById(ut.getUid());
		if(u != null && u.getStat() != null && u.getStat().intValue() == 2){
			int amount = 300;
			//给推荐人奖励300漂币
			webService.addRewardToRuid(u.getId(), amount, "推荐好友首次消费奖励", 2);
			u.setStat(3);
			userMapper.updateById(u);
		}
		/**
		 * 2.3关闭
		 */
//		int reward = userRewardService.reward(ut.getUid(), 1, (int)(addBalance * 100), ut.getTid().toString(), fs.getCityId());
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("siteName", fs.getFishSiteName());
//		map.put("reward", reward + "");
//		map.put("type", 4 + "");
		
		//核票人
		VerifyTicketRecord utt = new VerifyTicketRecord();
		utt.setUid(euid);
		utt.setSiteId(ut.getFishSiteId());
		utt.setTid(ut.getTid());
		utt.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		userTicketsMapper.insertVerifyTicketRecord(utt);
		
		//用户消息
		systemMsgService.saveMsg("核票成功", null, fs.getFishSiteName() + "核票成功",ut.getUid().intValue() , null);
		
		//商家消息
		systemMsgService.saveMsg("核票成功", null, "恭喜，您成功核票，核票金额已经进入您的钱包，请在财务钱包-钱包明细中查看！",fs.getUid().intValue() , null);

		
		//消息推送(消费者推送)
//		jPushService.sendPush(ut.getUid().intValue(), "C", "核票成功", fs.getFishSiteName() + "核票成功", map);
		
		//消息
//		systemMsgService.saveMsg("平台奖励核票奖", null, fs.getFishSiteName() + "已进入账户", fs.getUid().intValue(), null);
		//推送消息
//		jPushService.sendPushForPay(fs.getUid().intValue(), "B", "平台奖励核票奖", "获得平台奖励:" + rewardPlatform.getBonus() + "元", null);
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#selectUniqueTicket(java.util.Map)
	 */
	@Override
	public UserTickets selectUniqueTicket(Map<String, Object> map) {
		UserTickets userTicket = userTicketsMapper.selectUniqueTicket(map);
		Map<String,Object> params = new HashMap<String, Object>();
		if(userTicket!=null) {
			params.put("ticketId", userTicket.getTid());
			List<TicketCode> list = ticketCodeMapper.selectList(params);
			if(list!=null && list.size()>0) {
				Integer code = list.get(0).getCode();
				userTicket.setTicketCode(code);
			}
		}
		return userTicket;
	}

	/* (non-Javadoc)
	 * 
	 * 退票钱
	 * @see cn.heipiao.api.service.UserTicketsService#refundTicker(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor={Exception.class})
	public void refundTicker(String refundNo) throws Exception {
		Refund r = refundMapper.selectByRefundNoAsLock(refundNo);
		if(r != null){
			
			Orders o = ordersMapper.selectAsLockById(r.getOrderId());
			//退款业务
			refundService.refundTicket(r,o);
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserTicketsService#getOrdersListBySiteId(java.util.Map)
	 */
	@Override
	public List<UserTickets> getOrdersListBySiteId(Map<String, Object> map) {
		return userTicketsMapper.selectTicketByFishSiteId(map);
	}

}
