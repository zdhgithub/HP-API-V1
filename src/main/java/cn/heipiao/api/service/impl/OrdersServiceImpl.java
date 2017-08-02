/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.AliPayNotifyMapper;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.DepositFishMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.GoodsMapper;
import cn.heipiao.api.mapper.OrdersCouponsRelevanceMapper;
import cn.heipiao.api.mapper.OrdersDetailsMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.SiteCouponsRecordMapper;
import cn.heipiao.api.mapper.UserGoldCoinMapper;
import cn.heipiao.api.mapper.WxPayNotifyMapper;
import cn.heipiao.api.pay.PayService;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Goods;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.OrdersDetails;
import cn.heipiao.api.pojo.SiteCouponsRecord;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.NotifyService;
import cn.heipiao.api.service.OrdersService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.service.UserTicketsService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.ExMapUtil;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrdersServiceImpl implements OrdersService {
	
	Random ra =new Random();
	
	/** 订单超时时间 - 单位秒 **/
	@Value("${order.outTime}")
	private Long outTime;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private OrdersCouponsRelevanceMapper orderCouponsRelevanceMapper;
	
	@Resource
	private OrdersDetailsMapper ordersDetailsMapper;
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private SystemRecordGeneratorService srgs;
	
	@Resource
	private DepositFishMapper depositFishMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private UserGoldCoinMapper userGoldCoinMapper;
	
	@Resource(name="PayService")
	private PayService pay;
	
	@Resource
	private NotifyService notifyService;
	
	@Resource
	private WxPayNotifyMapper wxPayNotifyMapper;
	
	@Resource
	private AliPayNotifyMapper aliPayNotifyMapper;
	
	@Resource
	private UserTicketsService userTicketsService;
	
	@Resource
	private SiteCouponsRecordMapper siteCouponsRecordMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private UserRewardService userRewardService;
	
	/**
	 * 支付方式是一种以上的支付，需要平均分配
	 * 比如：订单价位:700元,	第三方支付693.00元,收益漂币:100分 ,充值漂币:600分
	 * 
	 * 	购买两种票150元,200元各两张，共4张票,一下两种票价的分配按照150:200比例分配
	 * 
	 * 	150元单价的票:分配第三方支付金额:297.00,分配充值漂币:258分,分配收益漂币:43
	 * 	150元的有2张票平均到单张票中每张票金额分配如下:
	 * 		第一张票:分配第三方支付金额:148.0元,分配充值漂币:129分,分配收益漂币:22
	 * 		第二张票:分配第三方支付金额:148.0元,分配充值漂币:129分,分配收益漂币:21
	 * 
	 * 
	 *  200元单价的票:分配第三方支付金额:396.00,分配充值漂币:342分,分配收益漂币:57
	 * 		第一张票:分配第三方支付金额:198.0元,分配充值漂币:171分,分配收益漂币:29
	 * 		第一张票:分配第三方支付金额:198.0元,分配充值漂币:171分,分配收益漂币:28
	 * 
	 * 注意：以上只是包含三种，有可能还会包含存鱼，平台优惠券
	 *  
	 *  使用这种方式主要是核票和退票金额出入正确
	 *  票的分配方式在生成票的方法中@see {@link UserTicketsServiceImpl}中的paySuccessDeal方法
	 *  
	 *  (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#addOrder(cn.heipiao.api.pojo.Orders)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int addOrder(Orders pojo) throws Exception {
		
		FishSite fs = fishSiteMapper.selectById(pojo.getFishSiteId());
		if(pojo.getLng()!=null && pojo.getLat()!=null) {
			Double distance = ExMapUtil.getDistanceByLL(pojo.getLng(), pojo.getLat(), fs.getLongitude(), fs.getLatitude());
			pojo.setDistance(distance);
		}
		//判断钓场是否支持购票
		if(fs.getUid() == null || fs.getStatus().intValue() != 1 || fs.getIsBuyTicket().intValue() == 0)
			return Status.fish_site_not_support_buy;
		
		//付款总价
		double totalPrice = 0.0;
		//校验存鱼
		DepositFish deposit = null;
		if(pojo.getDepositMoney() != null && pojo.getDepositMoney().doubleValue() > 0) {
			deposit = depositFishMapper.selectByUidAndFishSiteIdAsLock(pojo.getUid(),pojo.getFishSiteId());
			if(deposit == null || deposit.getBalance().doubleValue() <= 0
					|| ArithUtil.compareTo(deposit.getBalance(), pojo.getDepositMoney()) == -1
					){
				return Status.desposit_fish_insufficient;
			}
			totalPrice = ArithUtil.add(totalPrice, pojo.getDepositMoney());
		}

		//校验漂币
		UserGoldCoin ugc = null;
		if(pojo.getGoldCoinMoney() > 0){
			ugc = userGoldCoinMapper.selectByUidAsLock(pojo.getUid());
			if(ugc == null || ugc.getGoldCoin() + ugc.getEarningsGoldCoin() < pojo.getGoldCoinMoney()){
				return Status.user_gold_coin_not_enough;
			}
			totalPrice = ArithUtil.add(totalPrice, ArithUtil.div(pojo.getGoldCoinMoney().doubleValue(), 100,2));
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
		
		totalPrice = ArithUtil.add(totalPrice, pojo.getPayMoney());
		
		
		//优惠券处理
		Coupons cou = null;
		if(pojo.getCouponId() != null){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("userId", pojo.getUid());
//			map.put("couponId", pojo.getCouponId());
			cou = couponsMapper.selectByCidAsLock(pojo.getCouponId());
			if(cou == null ||cou.getUid().longValue() != pojo.getUid().longValue()){
				return Status.user_coupons;
			}
			
			//判断金额
			if(ArithUtil.compareTo(cou.getCouponFee(),pojo.getCouponsMoney()) != 0){
				
				return Status.coupons_money_error;
			}
			
//			暂时注释,平台优惠券和存鱼不能同时使用
//			if(cou.getCategory() < 2 && pojo.getDepositMoney() > 0){
//				return //添加一个错误码提示
//			}
			
			if(pojo.getOrdersMoney() < cou.getUseRule()){
				return Status.coupons_not_use_rule;
			}
			//判断优惠券信息
			if(cou.getStatus().intValue() != 0){
				return Status.user_coupons;
			}
			
//			if(cou.getDeadline().getTime() < ExDateUtils.getCalendar().getTimeInMillis()){
			if(CouponUserServiceImpl.isTimeoutByCoupons(cou.getDeadline())){
				cou.setStatus(2);
				couponsMapper.updatePojo(cou);
				return Status.user_coupons;
			}
			
			//0:平台钓场券, 1:合伙人钓场券, 2:钓场
			if(cou.getCategory().intValue() > 2){
				return Status.user_coupons;
			}
			
			if(cou.getCategory().intValue() == 2){
				SiteCouponsRecord fscr = siteCouponsRecordMapper.selectByCid(cou.getCid());
				if(fscr == null || fscr.getSiteId().intValue() != pojo.getFishSiteId().intValue() ){
					return Status.user_coupons;
				}
				
				if(cou.getStartTime() != null && cou.getStartTime().getTime() > ExDateUtils.getCalendar().getTimeInMillis()){
					return Status.user_coupons;
				}
				
				//设置金额为其他金额
				pojo.setOtherCouponsMoney(cou.getCouponFee());
				pojo.setCouponsMoney(0D);
				
				if(cou.getFee() > 0){
					pojo.setCouponsFees(cou.getFee() * 100);
				}
				
			}
			//添加到付款总金额中
			totalPrice = ArithUtil.add(totalPrice, pojo.getCouponsMoney(),pojo.getOtherCouponsMoney().doubleValue());
			cou.setStatus(1);
			cou.setUseTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
			
		}
		
		
		//商品检索
		List<Goods> gs = new ArrayList<Goods>();
		List<OrdersDetails> ods = pojo.getOrdersDetails();
		Double []pros = new Double[ods.size()]; 
		//购买商品总价
		double buyPrice = 0D;
		for (int i = 0; i < ods.size(); i++) {
			OrdersDetails od = ods.get(i);
			od.setOrderId(pojo.getOrderId());
			Goods g = goodsMapper.selectByIdAsLock(od.getGoodId());
			if(g == null 
					|| g.getStatus().intValue() != 1
//					|| 0 != ArithUtil.compareTo(g.getPrice().doubleValue(), od.getPrice().doubleValue())
					|| 0 != ArithUtil.compareTo(g.getDiscountPrice().doubleValue(), od.getPayPrice().doubleValue()) 
					|| od.getAmount() <= 0 || g.getAmount() < od.getAmount())
				return Status.goods_param_error;
			od.setPid(g.getpId());
			od.setGoodId(g.getGoodId());
			//如果有使用设置为票的单实付价
			od.setDepositFee(pojo.getDepositMoney() > 0 ? od.getPayPrice() : 0);
			//如果有使用设置为票的单实付价
			od.setGoldCoinFee(pojo.getGoldCoinMoney() > 0 ? (int)ArithUtil.mul(od.getPayPrice(),100,0) : 0);
			//如果有使用设置为票的单实付价
			od.setEarningsGoldCoinFee(pojo.getEarningsGoldCoinMoney() > 0 ? (int)ArithUtil.mul(od.getPayPrice(),100,0) : 0);
			/**
			 * 钓场优惠券没有收益
			 * 如果有使用设置为票的单价
			 */
			od.setCouponsFee(pojo.getCouponId() != null && cou.getCategory() < 2 ? pojo.getPayMoney() : 0);
			//每个商品的实付价
			od.setPayPrice(pojo.getPayMoney() > 0 ? od.getPayPrice() : 0);
			od.setPrice(g.getPrice());
			//实际单价
			od.setDiscountPrice(g.getDiscountPrice());
			od.setGoodsName(g.getName());
			od.setHourLong(g.getHourLong());
			od.setImg(g.getUrl());
			od.setCouponFee(0);
			buyPrice = ArithUtil.add(buyPrice, ArithUtil.mul(g.getDiscountPrice().doubleValue(), od.getAmount()));
			//减去库存
			g.setAmount(g.getAmount() - od.getAmount());
			gs.add(g);
			//每个产品的实付价
			pros[i] = od.getPrice();
		}
		
//		if(deposit != null){
//			Double [] depositPro =  ArithUtil.prorate(pojo.getDepositMoney(), pros);
//			for (int i = 0; i < ods.size(); i++) {
//				OrdersDetails od = ods.get(i);
//				od.setDepositFee(ArithUtil.round(ArithUtil.div(depositPro[i] , od.getAmount()), 2, RoundingMode.DOWN));
//				//如果有存鱼减去存鱼的实付价
////				od.setPayPrice(ArithUtil.round(ArithUtil.sub(od.getPayPrice(), od.getDepositFee()),2,RoundingMode.DOWN));
//			}
//		}
		
		
		
		//如果支付方式是一种以上的支付，需要平均分配
		if(isPros(deposit,pojo.getGoldCoinMoney() > 0 ? ugc : null,pojo.getEarningsGoldCoinMoney() > 0 ? ugc : null,
				cou,pojo.getPayMoney() > 0 ? pojo.getPayMoney() : null)){
			//漂币,存鱼,余额,平台优惠券
			Double [] depositPros = null;
			Integer [] coinPros = null;
			Integer [] earningsCoinPros = null;
			Double []balancePros = null;
			Double []couPros = null;
			Integer [] cfs = null;
			if(pojo.getDepositMoney() > 0){
				depositPros = ArithUtil.prorate(pojo.getDepositMoney(), pros);
			}
			if(pojo.getGoldCoinMoney() > 0){
				coinPros = ArithUtil.prorate(pojo.getGoldCoinMoney(), pros);
			}
			if(pojo.getEarningsGoldCoinMoney() > 0){
				earningsCoinPros = ArithUtil.prorate(pojo.getEarningsGoldCoinMoney(), pros);
			}
			if(pojo.getPayMoney() > 0){
				balancePros = ArithUtil.prorate(pojo.getPayMoney(), pros);
			}
			if(pojo.getCouponId() != null && cou != null && cou.getCategory() < 2){
				//注意：如果优惠券有多余的金额必须减掉多出的金额
				Double d = ArithUtil.sub(
						ArithUtil.sub(
						ArithUtil.sub(pojo.getOrdersMoney(), pojo.getDepositMoney()),pojo.getPayMoney())
						,ArithUtil.div(pojo.getGoldCoinMoney() + pojo.getEarningsGoldCoinMoney(), 100, 2));
				if(d > pojo.getCouponsMoney()){
					return Status.coupons_money_error;
				}
				couPros = ArithUtil.prorate(d, pros);
			}
			if(pojo.getCouponsFees() > 0){
				cfs = ArithUtil.prorate(pojo.getCouponsFees(), pros.length);
			}
			//遍历每个商品并且赋值
			for (int i = 0; i < ods.size(); i++) {
				OrdersDetails od = ods.get(i);
				if(depositPros != null){
					od.setDepositFee(depositPros[i]);
				}else{
					od.setDepositFee(0D);
				}
				if(coinPros != null){
					od.setGoldCoinFee(coinPros[i]);
				}else{
					od.setGoldCoinFee(0);
				}
				if(earningsCoinPros != null){
					od.setEarningsGoldCoinFee(earningsCoinPros[i]);
				}else{
					od.setEarningsGoldCoinFee(0);
				}
				if(balancePros != null){
					od.setPayPrice(balancePros[i]);
				}else{
					od.setPayPrice(0D);
				}
				if(couPros != null){
					od.setCouponsFee(couPros[i]);
				}else{
					od.setCouponsFee(0D);
				}
				if(cfs != null){
					od.setCouponFee(cfs[i]);
				}else{
					od.setCouponFee(0);
				}
				
			}
		}
		
		
		
		//如果使用了优惠券判断
		//验证订单总价是否相等   1.漂币+存鱼+优惠券+支付金额  ; 2.购买商品的总价
		if(cou != null){
			if(ArithUtil.compareTo(pojo.getOrdersMoney(), totalPrice) == 1
					|| ArithUtil.compareTo(pojo.getOrdersMoney(), buyPrice) != 0){
				return Status.user_orders_totalPrice_error;
			}
		}else{
			if(ArithUtil.compareTo(pojo.getOrdersMoney(), totalPrice) != 0
					|| ArithUtil.compareTo(pojo.getOrdersMoney(), buyPrice) != 0){
//			throw new Exception("订单总额不对");
				return Status.user_orders_totalPrice_error;
			}
		}
		
		//生成订单号
		pojo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMdd") + "01" + RandomNumberUtils.getFixedLengthNum(7, "" + ra.nextInt(9999999)));
		//创建订单
		try {
			ordersMapper.insertPojo(pojo);
		} catch (Exception e) {
			saveOrder(pojo,e);
		}
//		if(pojo.getCouponId() != null){
//			OrdersCouponsRelevance ocr = new OrdersCouponsRelevance();
//			ocr.setCouponId(pojo.getCouponId());
//			ocr.setCreateTime(pojo.getCreateTime());
//			ocr.setOrderId(pojo.getOrderId());
//			orderCouponsRelevanceMapper.insertPojo(ocr);
//		}
		
		for (OrdersDetails ordersDetails : ods) {
			ordersDetails.setOrderId(pojo.getOrderId());
		}
		
		ordersDetailsMapper.insertBatch(pojo.getOrdersDetails());
		
		//商品,存鱼减去库存,更新优惠券
		goodsMapper.updateAmountBatch(gs);
		if(deposit != null){
			//更新存鱼
			depositFishMapper.updateByUidAndFishSiteId(pojo.getUid(),pojo.getFishSiteId(),ArithUtil.sub(deposit.getBalance(), pojo.getDepositMoney()));
			srgs.generateDeDepositFishRecord(pojo.getUid(), pojo.getFishSiteId(), pojo.getDepositMoney());
			//添加账单
			accountBillService.addPojo(pojo.getUid(), pojo.getOrderId(), 2, 1, 3, pojo.getDepositMoney(), "购票-存鱼");
		}
		//更新漂币
		if(pojo.getGoldCoinMoney() > 0 || pojo.getEarningsGoldCoinMoney() > 0){
			ugc.setGoldCoin(ugc.getGoldCoin() - pojo.getGoldCoinMoney());
			ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() - pojo.getEarningsGoldCoinMoney());
			userGoldCoinMapper.updatePojo(ugc);
			srgs.generateAccountDeRecord(pojo.getUid().intValue(), pojo.getOrderId(), ApiConstant.TradeType.CONSUMPTION,
					pojo.getGoldCoinMoney() + pojo.getEarningsGoldCoinMoney(), "购票", "漂币", 1,pojo.getFishSiteId());
			//添加账单
			accountBillService.addPojo(pojo.getUid(), pojo.getOrderId(), 2, 1, 2, ArithUtil.div((double)(pojo.getGoldCoinMoney() + pojo.getEarningsGoldCoinMoney()),100,2), "购票-漂币");
		}
		if(cou != null){
			if(cou.getCategory() == 2){
				userRewardService.addPojo(pojo.getUid(), cou.getCouponFee() * 100 / 3, cou.getCouponFee() * 100);
			}else{
				userRewardService.addPojo(pojo.getUid(), cou.getCouponFee() * 100, 0);
			}
			couponsMapper.updatePojo(cou);
		}
		
		//商户平台支付操作
		if(pojo.getTradePlatform().intValue() == 0 && pojo.getPayMoney() <= 0){
//			// 执行票业务操作
//			userTicketsService.paySuccessDeal(pojo);
//			// 更新订单表
//			pojo.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
//			pojo.setStatus(1);
//			ordersMapper.updatePart(pojo);
			
			siteDeal(pojo);
			
		}
		return Status.success;
	}
	
	@Override
	public void siteDeal(Orders pojo) throws Exception{
		// 执行票业务操作
		userTicketsService.paySuccessDeal(pojo);
		// 更新订单表
		pojo.setPayTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		pojo.setStatus(1);
		ordersMapper.updatePart(pojo);
		FishSite fs = fishSiteMapper.selectById(pojo.getFishSiteId());
		systemMsgService.saveMsg("购票成功", null, "您在" + fs.getFishSiteName() + "购票成功", pojo.getUid().intValue(), null);
	}
	
	
	/**
	 * 重复生产订单号
	 * @param pojo
	 * @param e
	 * @throws Exception
	 */
	private void saveOrder(Orders pojo,Exception e) throws Exception{
		if(e.getMessage() != null && e.getMessage().trim().contains(String.format("Duplicate entry '%s' for key 'PRIMARY'",pojo.getOrderId()))){
			try {
				//生成订单号
				pojo.setOrderId(ExDateUtils.getCurrentDayFormat() + "01" + RandomNumberUtils.getFixedLengthNum(7, "" + ra.nextInt(9999999)));
				ordersMapper.insertPojo(pojo);
				return;
			} catch (Exception e1) {
				saveOrder(pojo, e1);
			}
		}else{
			throw e;
		}
	}
	
	/**
	 * @param deposit
	 * @param ugc
	 * @param cou
	 * @param payMoney
	 * @return
	 */
	public static boolean isPros(Object ...obj) {
		if(obj.length > 0){
			int c = 0;
			for (Object o : obj) {
				c = o == null ? c : ++c; 
			}
			return c > 1;
		}
		return false;
	}

	/**
	 * 取消订单
	 */
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int cancelOrder(String orderId) throws Exception{
		Orders order = ordersMapper.selectAsLockById(orderId);
		//订单超时时间,状态验证
		if(order == null || order.getStatus().intValue() != 0
//				|| ExDateUtils.getCalendar().getTimeInMillis() - order.getCreateTime().getTime() >= outTime * 1000
				){
			return Status.orders_error;
		}
		//判断平台
//		int result = verifyOrders(order.getTradePlatform(),orderId);
//		if(result != 0){
//			return result;
//		}
		//退存鱼到用户账户
		if(order.getDepositMoney() != null && order.getDepositMoney().doubleValue() > 0) {
			//获取记录锁
			DepositFish df = depositFishMapper.selectByUidAndFishSiteIdAsLock(order.getUid(), order.getFishSiteId());
			depositFishMapper.updateByUidAndFishSiteId(order.getUid(), df.getSiteId(), df.getBalance() + order.getDepositMoney());
			srgs.generateAccountInRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.CONSUMPTION,
					order.getDepositMoney(), "购票", "存鱼", 1,order.getFishSiteId());
			//添加账单
			accountBillService.addPojo(order.getUid(), order.getOrderId(), 1, 1, 3, order.getDepositMoney(), "取消购票-退存鱼");
			
		}
		
		//退漂币
		if(order.getGoldCoinMoney() > 0 || order.getEarningsGoldCoinMoney() > 0){
			UserGoldCoin ugc = userGoldCoinMapper.selectByUidAsLock(order.getUid());
			ugc.setGoldCoin(ugc.getGoldCoin() + order.getGoldCoinMoney());
			ugc.setEarningsGoldCoin(ugc.getEarningsGoldCoin() + order.getEarningsGoldCoinMoney());
			userGoldCoinMapper.updatePojo(ugc);
			srgs.generateAccountInRecord(order.getUid().intValue(), order.getOrderId(), ApiConstant.TradeType.CONSUMPTION,
					order.getGoldCoinMoney(), "购票", "漂币", 1,order.getFishSiteId());
			//添加账单
			accountBillService.addPojo(order.getUid(), order.getOrderId(), 1, 1, 2, ArithUtil.div((double)(order.getGoldCoinMoney() + order.getEarningsGoldCoinMoney()),100,2), "取消购票-退漂币");

		}
		
		//退优惠券到用户优惠券表,查看是否过期
		if(order.getCouponId() != null){
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("userId", order.getUid());
//			map.put("couponId", order.getCouponId());
			Coupons c = couponsMapper.selectByCidAsLock(order.getCouponId());
			if(c.getDeadline().getTime() < ExDateUtils.getDate().getTime()){
				c.setStatus(2);
			}else{
				c.setStatus(0);
			}
			c.setUseTime(null);
			couponsMapper.updatePojo(c);
		}
		
		
		//取消订单
		if(ExDateUtils.getCalendar().getTimeInMillis() - order.getCreateTime().getTime() > outTime * 1000){
			//超时
			ordersMapper.updateStatus(order.getOrderId(),2);
		}else{
			//取消
			ordersMapper.updateStatus(order.getOrderId(),3);
		}
		
		
		//增加商品库存,锁行操作
		List<Goods> gs = new ArrayList<Goods>();
		List<OrdersDetails> ods = ordersDetailsMapper.selectByOrderId(order.getOrderId());
		for ( OrdersDetails od : ods) {
			Goods g = goodsMapper.selectByIdAsLock(od.getGoodId());
			if(g == null)
				continue;
			g.setAmount(g.getAmount() + od.getAmount());
			gs.add(g);
		}
		if(gs.size() > 0)
			goodsMapper.updateAmountBatch(gs);
		
		//关闭未支付订单
		if(order.getTradePlatform().intValue() > 0){
			pay.closeTrade(order.getOrderId(),order.getTradePlatform(),order.getWhereIsApp());
		}
		return Status.success;
	}
	
	/**
	 * @param tradePlatform
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
//	public int verifyOrders(Integer tradePlatform, String orderId) throws Exception {
//		int result = Status.orders_error;
//		if(tradePlatform == 1){
//			WxPayNotify wx = wxPayNotifyMapper.selectWxPayNotifyByOutTradeNo(orderId);
//			result = notifyService.wxTradeQueryResult(orderId,wx);
//			if(result == 1){
//				return wxOrdersSuccess(wx);
//			 }
//			if(result == -1){
//				return orderFail(orderId);
//			}
//		} else if (tradePlatform == 2){
//			AliPayNotify ali = aliPayNotifyMapper.selectAliPayNotifyByOutTradeNo(orderId);
//			result =notifyService.aliTradeQueryResult(orderId,ali);
//			if(result == 1){
//			   return aliOrdersSuccess(ali);
//			}
//			if(result == -1){
//				return orderFail(orderId);
//			}
//		}
//		return result;
//	}
//
//	/**
//	 * @param ali2
//	 * @return
//	 * @throws Exception 
//	 */
//	private int aliOrdersSuccess(AliPayNotify ali) throws Exception {
//		//做支付通知处理
//		if(notifyService.aliNotify(ali).equals(PayParams.success)){
//			return Status.orders_paid;
//		}else{
//			return Status.orders_error;
//		}
//	}
//
//	/**
//	 * 
//	 * @param wx 
//	 * @return
//	 */
//	private int wxOrdersSuccess(WxPayNotify wx){
//		if(notifyService.wxNotify(wx).equals(PayParams.responseXml(PayParams.success))){
//			return Status.orders_paid;
//		}else {
//			return Status.orders_error;
//		}
//	}
//	
//	
//	/**
//	 * @param orderId
//	 * @return
//	 * @throws Exception 
//	 */
//	private int orderFail(String orderId) throws Exception {
//		if(cancelOrder(orderId) == Status.success){
//			return Status.orders_cancel;
//		} else {
//			return Status.orders_error;
//		}
//	}
	

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#getOrdersListBySite(java.util.Map)
	 */
	@Override
	public List<Orders> getOrdersListBySite(Map<String, Object> map) {
		return ordersMapper.selectOrdersBySite(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#getOrdersListByUid(java.util.Map)
	 */
	@Override
	public List<Orders> getOrdersListByUid(Map<String, Object> map) {
		return ordersMapper.selectOrdersByUid(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#getOrdersById(java.lang.Long)
	 */
	@Override
	public Orders getOrdersById(String orderId) {
		return ordersMapper.selectById(orderId);
	}

	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	@Override
	public void clearOutimeOrder() throws Exception {
		//暂时注释
//		Map<String , Object> params = new LinkedMap<String , Object>();
//		params.put("outTime", outTime);
//		params.put("now", ExDateUtils.getDate());
//		this.ordersMapper.clearOutTimeOrder(params);
		//超时需要和取消订单业务一致
		//查询超时订单
//		List<String> orders = ordersMapper.selectTimeoutOrders(new Timestamp(ExDateUtils.getDate().getTime() - outTime));
//		for (String orderId : orders) {
//			cancelOrder(orderId);
//		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#selectByOrderId(java.lang.String)
	 */
	@Override
	public Orders selectByOrderId(String orderId) {
		return ordersMapper.selectById(orderId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.OrdersService#verifyOrders(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public void verifyOrders(String orderId) throws Exception {
		Orders order = ordersMapper.selectAsLockById(orderId);
		if(order != null && order.getStatus() == 0){
			int i = notifyService.verifyOrders(order.getTradePlatform(), orderId,cn.heipiao.api.service.PayService.buyGoodOrders,order.getWhereIsApp());
			if(i == 0 && order.getStatus() == 0 && (order.getCreateTime().getTime() + outTime * 1000) < ExDateUtils.getCalendar().getTimeInMillis()){
				cancelOrder(orderId);
			}
		}
	}
	
	/* (non-Javadoc)
	 * 验证取消订单
	 * @see cn.heipiao.api.service.OrdersService#verifyOrders(java.lang.String)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public int verifyCancelOrders(String orderId) throws Exception {
		Orders order = ordersMapper.selectAsLockById(orderId);
		int rs = Status.success;
		if(order != null && order.getStatus() == 0){
			int i = notifyService.verifyOrders(order.getTradePlatform(), orderId,cn.heipiao.api.service.PayService.buyGoodOrders,order.getWhereIsApp());
			if(i == 0 && order.getStatus() == 0){
				rs = cancelOrder(orderId);
			}
		}
		return rs;
	}

}
