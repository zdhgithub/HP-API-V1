/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.mapper.AliPayNotifyMapper;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.RewardAmountMapper;
import cn.heipiao.api.mapper.RewardDetailMapper;
import cn.heipiao.api.mapper.ShopAccountMapper;
import cn.heipiao.api.mapper.ShopCouponsRecordMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.mapper.UserGoldCoinMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.WxPayNotifyMapper;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardDetail;
import cn.heipiao.api.pojo.RewardPlatform;
import cn.heipiao.api.pojo.ShopAccount;
import cn.heipiao.api.pojo.ShopCouponsRecord;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.PartnerShopEarningOrdersService;
import cn.heipiao.api.service.PayShopConfigService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.ShopEarnTradeOrdersService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExMapUtil;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年10月14日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ShopTradeOrdersImpl implements ShopTradeOrdersService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShopTradeOrdersImpl.class);

	/** 订单超时时间 - 单位秒 **/
	@Value("${order.outTime}")
	private Long outTime;
	
	@Resource(name="PayService")
	private PayService pay;
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	@Resource
	private NotifyService notifyService;
	
	@Resource
	private WxPayNotifyMapper wxPayNotifyMapper;
	
	@Resource
	private AliPayNotifyMapper aliPayNotifyMapper;
	
	@Resource
	private UserGoldCoinMapper userGoldCoinMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private PayShopConfigService payShopConfigService;
	
	@Resource
	private ShopEarnTradeOrdersService shopEarnTradeOrdersService;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private ShopAccountMapper shopAccountMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private PartnerService partnerService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private JPushService jPushService;
	
	@Resource
	private ShopCouponsRecordMapper shopCouponsRecordMapper;
	
	@Resource
	private UserRewardService userRewardService;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private RewardPlatformService rewardPlatformService;
	
	@Resource
	private RewardDetailMapper rewardDetailMapper;
	
	@Resource
	private RewardAmountMapper rewardAmountMapper;
	
	@Resource
	private PartnerShopEarningOrdersService partnerShopEarningOrdersService;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#selectByOrderId(java.lang.String)
	 */
	@Override
	public ShopTradeOrders selectByOrderId(String orderId) {
		return shopTradeOrdersMapper.selectByOrderId(orderId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#addShopTradeOrders(cn.heipiao.api.pojo.ShopTradeOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addShopTradeOrders(ShopTradeOrders pojo) throws Exception {
		
		double total  = 0D;
		UserGoldCoin ugc = null;
		ShopAccount sa = shopAccountMapper.selectUnique(pojo.getShopId());
		if(sa == null){
			return Status.account_not_exist;
		}
		FishShop fs = fishShopMapper.selectFishShopByShopId(pojo.getShopId());
		if(fs == null || fs.getUid() == null || fs.getUid().intValue() != sa.getUid().intValue()
				|| fs.getStatus().intValue() != 1){
			return Status.account_not_exist;
		}
		//检查漂币
		if(pojo.getGoldCoinMoney() > 0){
			ugc = userGoldCoinMapper.selectByUidAsLock(pojo.getUid());
			if(ugc == null || ugc.getEarningsGoldCoin() + ugc.getGoldCoin() < pojo.getGoldCoinMoney()){
				return Status.user_gold_coin_not_enough;
			}
			total = ArithUtil.add(total,ArithUtil.div(pojo.getGoldCoinMoney(), 100, 2));
			//将支付漂币拆分为充值漂币和收益漂币
			//先扣除收益漂币再扣充值漂币
			if(pojo.getGoldCoinMoney() <= ugc.getEarningsGoldCoin()){
				pojo.setEarningsGoldCoinMoney(pojo.getGoldCoinMoney());
				pojo.setGoldCoinMoney(0);
			}else{
				pojo.setEarningsGoldCoinMoney(ugc.getEarningsGoldCoin());
				pojo.setGoldCoinMoney(pojo.getGoldCoinMoney() - pojo.getEarningsGoldCoinMoney());
			}
		}
		
				
		Coupons cou = null;
		//验证优惠券
		if(pojo.getCouponId() != null){
			cou = couponsMapper.selectByCidAsLock(pojo.getCouponId());
			if(cou == null || cou.getStatus() != 0 || cou.getCategory() > 5 || cou.getCategory() < 3
					|| cou.getCouponFee().intValue() != pojo.getCouponsMoney()
					|| cou.getUid().longValue() != pojo.getUid().longValue()
					){
				return Status.user_coupons;
			}
			
			//如果优惠券过期将优惠券设置为已过期
//			if(cou.getDeadline().getTime() < ExDateUtils.getCalendar().getTimeInMillis()){
			if(CouponUserServiceImpl.isTimeoutByCoupons(cou.getDeadline())){
				cou.setStatus(2);
				couponsMapper.updatePojo(cou);
				return Status.user_coupons;
			}
			
			if(cou.getUseRule() > pojo.getOrdersMoney()){
				return Status.coupons_not_use_rule;
			}
			
			if(cou.getCategory() == 3){
				
				ShopCouponsRecord fscr = shopCouponsRecordMapper.selectByCid(cou.getCid());
				if(fscr == null || fscr.getShopId().longValue() != pojo.getShopId()){
					return Status.user_coupons;
				}
				
				if(cou.getStartTime() != null && cou.getStartTime().getTime() > ExDateUtils.getCalendar().getTimeInMillis()){
					return Status.user_coupons;
				}
				
				//设置金额为其他金额
				pojo.setOtherCouponsMoney(cou.getCouponFee());
				pojo.setCouponsMoney(0);
				//设置优惠券收费费用
				if(cou.getFee() > 0){
					pojo.setCouponsFee(cou.getFee() * 100);
				}
			}
			
			total = ArithUtil.add(total,pojo.getCouponsMoney().doubleValue(),pojo.getOtherCouponsMoney().doubleValue());
		}
		
		total = ArithUtil.add(total, pojo.getPayMoney());
		
		//判断订单总金额
		if(cou != null){
			if(ArithUtil.compareTo(pojo.getOrdersMoney(), total) > 0){
				return Status.user_orders_totalPrice_error;
			}
		}else{
			if(ArithUtil.compareTo(total, pojo.getOrdersMoney()) != 0){
				return Status.user_orders_totalPrice_error;
			}
		}
		
		//订单的生成方式  当前日期 + 固定长度uid + 业务号 + 24小时的长度(秒)
		pojo.setOrderId(ExDateUtils.getDateFormat(ExDateUtils.getDate(), "yyMMdd") + RandomNumberUtils.getFixedLengthNum(10, pojo.getUid().toString()) + "03"
					+ ((ExDateUtils.getCalendar().getTimeInMillis() - ExDateUtils.getCurrentMin(ExDateUtils.getCalendar()).getTime()) / 1000));
		
		
		//更新漂币
		if(ugc != null){
			ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() - pojo.getEarningsGoldCoinMoney());
			ugc.setGoldCoin(ugc.getGoldCoin() - pojo.getGoldCoinMoney());
			userGoldCoinMapper.updatePojo(ugc);
			//添加账单
			accountBillService.addPojo(pojo.getUid(), pojo.getOrderId(), 2, 3, 2, ArithUtil.div((double)(pojo.getEarningsGoldCoinMoney()+ pojo.getGoldCoinMoney()),100,2), "票支付-漂币");
		}
		
		
		
		//更新优惠券
		if(cou != null){
			if(cou.getCategory() == 3){
				userRewardService.addPojo(pojo.getUid(), cou.getCouponFee() * 100 / 3, cou.getCouponFee() * 100);
			}else{
				userRewardService.addPojo(pojo.getUid(), cou.getCouponFee() * 100, 0);
			}
			
			cou.setStatus(1);
			cou.setUseTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			couponsMapper.updatePojo(cou);
		}else{
			pojo.setCouponsMoney(0);
		}
		
		//计算距离
		if(pojo.getLng() != null && pojo.getLat() != null){
			pojo.setDistance((long)ExMapUtil.getDistanceByLL(pojo.getLng(), pojo.getLat(), fs.getLongitude(), fs.getLatitude()));
		}
		
		shopTradeOrdersMapper.insertPojo(pojo);
		
		//第三方支付和黑漂支付处理 
		if(pojo.getTradePlatform() == 0){
			
			shopDeal(pojo);
			
//			double balance = getBalance(pojo);
//			// 店铺添加金额
//			ShopAccount sam = accountService.addShopAccountBalance(pojo.getShopId(), balance);
//			//奖励
////			payShopConfigService.reward(pojo.getUid(),pojo.getOrderId(),pojo.getOrdersMoney());
//			
//			//合伙人收益
//			partnerService.shopEarn(pojo,(int)(balance * 100));
//			
//			//更新订单
//			pojo.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//			pojo.setStatus(1);
//			shopTradeOrdersMapper.updatePart(pojo);
//			
//			//创建订单
//			shopEarnTradeOrdersService.addPojo(pojo.getShopId(),sam.getUid(), balance);
			
			
//			//店铺添加金额
//			double balance = ShopTradeOrdersImpl.getBalance(sto);
//			accountService.addShopAccountBalance(sto.getShopId(), balance);
//			//奖励
////			payShopConfigService.reward(sto.getUid(),sto.getOrderId(),sto.getOrdersMoney());
//			
//			//合伙人收益
//			partnerService.shopEarn(sto,(int)(balance * 100));
//			
//			// 更新订单表
//			// 更新订单交易号
//			sto.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//			sto.setStatus(1);
//			shopTradeOrdersMapper.updatePart(sto);
			
		}
		
		return Status.success;
	}

	@Override
	public void shopDeal(ShopTradeOrders pojo) throws Exception{
		double balance = getBalance(pojo);
		
		FishShop fs = fishShopMapper.selectFishShopByShopId(pojo.getShopId());
		// 店铺添加金额
		ShopAccount sam = accountService.addShopAccountBalance(pojo.getShopId(), balance);
		//奖励
//		payShopConfigService.reward(pojo.getUid(),pojo.getOrderId(),pojo.getOrdersMoney());
		
		/**
		 * 2.3去掉奖励
		 * 合伙人收益 
		 */
//		partnerService.shopEarn(pojo,(int)(balance * 100));
		
		//更新订单
		pojo.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		pojo.setStatus(1);
		shopTradeOrdersMapper.updatePart(pojo);
		
		//创建订单
		shopEarnTradeOrdersService.addPojo(pojo.getShopId(),sam.getUid(), balance);
		//消息
		systemMsgService.saveMsg("支付成功，快带上我去钓鱼吧", null, fs.getName() + "票支付成功", pojo.getUid().intValue(), null);
		//推送消息
		jPushService.sendPushForPay(sam.getUid().intValue(), "B", "票支付获得", "用户付款:" + balance + "元", null);
	}
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#cancelShopTradeOrders(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int cancelShopTradeOrders(String orderId) throws Exception {
		ShopTradeOrders sto = shopTradeOrdersMapper.selectAsLockByOrderId(orderId);
		if(sto == null || sto.getStatus() != 0){
			return Status.orders_not_exists;
		}
		
		//取消订单
		if((sto.getCreateTime().getTime() + outTime * 1000) < ExDateUtils.getDate().getTime()){
			sto.setStatus(2);
		}else{
			sto.setStatus(3);
		}
		
		//退优惠券
		if(sto.getCouponId() != null){
			Coupons c = couponsMapper.selectByCidAsLock(sto.getCouponId());
			if(c.getDeadline().getTime() < ExDateUtils.getDate().getTime()){
				c.setStatus(2);
			}else{
				c.setStatus(0);
			}
			c.setUseTime(null);
			couponsMapper.updatePojo(c);
		}
		
		//退漂币
		if(sto.getGoldCoinMoney() > 0 || sto.getEarningsGoldCoinMoney() > 0){
			accountService.addGoldCoin(sto.getUid(),sto.getGoldCoinMoney(),sto.getEarningsGoldCoinMoney());
			//添加账单
			accountBillService.addPojo(sto.getUid(), sto.getOrderId(), 1, 3, 2, ArithUtil.div((double)(sto.getGoldCoinMoney() + sto.getEarningsGoldCoinMoney()),100,2), "取消支付-退漂币");
		}
		
		
		//更新订单
		shopTradeOrdersMapper.updatePart(sto);;
		
		if(sto.getTradePlatform() > 0){
			//关闭未支付订单（第三方）
			pay.closeTrade(sto.getOrderId(),sto.getTradePlatform(),sto.getWhereIsApp());
		}
		
		return Status.success;
	}

	/**
	 * 获取票支付店铺所得金额
	 * @return
	 */
	public static double getBalance(ShopTradeOrders sto){
		double balance = ArithUtil.add(sto.getCouponsMoney().doubleValue(),sto.getPayMoney(),
				ArithUtil.div(sto.getEarningsGoldCoinMoney() + sto.getGoldCoinMoney(), 100,2));
		if(sto.getCouponsFee() > 0){
			balance = ArithUtil.sub(balance, ArithUtil.div(sto.getCouponsFee(), 100, 2));
		}
		return balance;
	}
	

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#verifyOrders(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void verifyOrders(String orderId) throws Exception {
		ShopTradeOrders order = shopTradeOrdersMapper.selectAsLockByOrderId(orderId);
		if(order != null && order.getStatus() == 0){
			int i = notifyService.verifyOrders(order.getTradePlatform(), order.getOrderId(),cn.heipiao.api.service.PayService.payShop,order.getWhereIsApp());
			if(i == 0){
				cancelShopTradeOrders(orderId);
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#getRecentOrders(java.lang.Long)
	 */
	@Override
	public List<ShopTradeOrders> getRecentOrders(Long shopId) {
		return shopTradeOrdersMapper.selectRecentOrders(shopId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#countBoard(java.lang.Long)
	 */
	@Override
	public Map<String, Object> countBoard(Long shopId) {
		Map<String,Object> map = new HashMap<String, Object>();
		Calendar c = ExDateUtils.getCalendar();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		Timestamp min = new Timestamp(c.getTimeInMillis());
		map.put("countUid",shopTradeOrdersMapper.selectCountCurrentMonthPayUid(shopId, min));
		Double currentFees = shopTradeOrdersMapper.selectCountCurrentMoney(shopId,min);
		map.put("currentFees",currentFees == null ? 0 : currentFees );
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
		Double prevMonthFees = shopTradeOrdersMapper.selectCountMoneyByMonth(shopId,new Timestamp(c.getTimeInMillis()),min);
		map.put("prevMonthFees",prevMonthFees == null ? 0 : prevMonthFees);
		return map;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopTradeOrdersService#getOrdersByShopId(java.util.Map)
	 */
	@Override
	public List<ShopTradeOrders> getOrdersByShopId(Map<String, Object> map) {
		return shopTradeOrdersMapper.selectOrdersByShopId(map);
	}

	@Override
	public Map<String, Object> getTradeRecord(Map<String, Object> map) {
		String phone = (String) map.get("phone");
		List<ShopTradeOrders> list = new ArrayList<ShopTradeOrders>();
		Integer total = 0;
		if(phone != null){
			User u = userMapper.queryUserByPhone(phone);
			if(u != null){
				map.put("uid", u.getId());
				list = shopTradeOrdersMapper.getTradeRecord(map);
				total = shopTradeOrdersMapper.getTradeRecordCount(map);
			}
		}else{
			list = shopTradeOrdersMapper.getTradeRecord(map);
			total = shopTradeOrdersMapper.getTradeRecordCount(map);
		}
		Map<String,Object> mp = new HashMap<String, Object>();
		mp.put("data", list);
		mp.put("total", total);
		return mp;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void shopDetial(ShopTradeOrders pojo) throws Exception{
		// 平台票支付奖添加到明细    每单金额大于等于30.00
				FishShop fs = fishShopMapper.selectFishShopByShopId(pojo.getShopId());
				RewardPlatform rewardPlatform = rewardPlatformService
						.findRewardPlatform(fs.getId(), 0, 1);
				logger.debug("===========================================");
				logger.info("fs=====:" + fs != null ? JSONObject.toJSONString(fs) : null);
				logger.info("rewardPlatform=====:" + rewardPlatform != null ? JSONObject.toJSONString(rewardPlatform) : null);
				if(pojo.getOrdersMoney()>=30 && rewardPlatform != null && fs !=null){
							RewardDetail rewardDetail = new RewardDetail();
							rewardDetail
									.setTime(new Timestamp(ExDateUtils.getDate().getTime()));
							rewardDetail.setBid(fs.getId());
							rewardDetail.setAmount(rewardPlatform.getBonus());
							rewardDetail.setBtype(0);
							rewardDetail.setType(2);
							rewardDetail.setDescription("票支付平台奖励");
							rewardDetailMapper.insertRewardDetail(rewardDetail);
						
						// 更新平台奖励累计奖励金额
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("bid", fs.getId());
						params.put("type", 0);
						RewardAmount rewardAmount1 = rewardAmountMapper
								.selectByBidAsLock(params);
						if (rewardAmount1 == null) {
							RewardAmount rewardAmount = new RewardAmount();
							rewardAmount.setBalance(0.00);
							rewardAmount.setBid(fs.getId());
							rewardAmount.setRemain(0.00);
							rewardAmount.setTotal(rewardPlatform.getBonus());
							rewardAmount.setType(0);
							rewardAmount.setWithdrawTime(new Timestamp(ExDateUtils.getDate().getTime()));
							rewardAmountMapper.insertRewardPlatformAmount(rewardAmount);
						} else {
							params.put("total",
									(rewardAmount1.getTotal() + rewardPlatform.getBonus()));
							rewardAmountMapper.updateByBid(params);
					}
						//合伙人收益
						
						if(fs.getSignUid()!=null){
							User u = partnerMapper.selectByPartnerId(fs.getSignUid());
							if(u!=null){
								partnerShopEarningOrdersService.addPojo(u.getId(), pojo.getOrderId(), fs.getSignUid(), fs.getId(), rewardPlatform.getPartnerBonus());
								accountService.addGoldCoin(u.getId(), rewardPlatform.getPartnerBonus()); 
								//消息
								systemMsgService.saveMsg("票支付平台奖励", null, fs.getName() + "已进入账户", u.getId().intValue(), null);
							}
						}
						//消息
						systemMsgService.saveMsg("平台奖励", null, fs.getName() + "已进入账户", fs.getUid().intValue(), null);
						//推送消息
						jPushService.sendPushForPay(fs.getUid().intValue(), "B", "票支付平台奖励", "获得平台奖励:" + rewardPlatform.getBonus() + "元", null);
				}
	}

}
