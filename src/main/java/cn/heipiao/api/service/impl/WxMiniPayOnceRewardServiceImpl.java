package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.GoldCoinOrdersMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.mapper.WxMiniPayOnceRewardMapper;
import cn.heipiao.api.pojo.GoldCoinOrders;
import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.WxMiniPayOnceReward;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.PayService;
import cn.heipiao.api.service.WxMiniPayOnceRewardService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2017年2月20日
 */
@Service
public class WxMiniPayOnceRewardServiceImpl implements WxMiniPayOnceRewardService {

	@Resource
	private WxMiniPayOnceRewardMapper wxMiniPayOnceRewardMapper;
	
	@Resource
	private OrdersMapper ordersMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private GoldCoinOrdersMapper goldCoinOrdersMapper;
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	
	@Override
	public int reward(Long uid, String orderId,Integer category) {
		int reward = 0;
		WxMiniPayOnceReward wxr = wxMiniPayOnceRewardMapper.selectByUid(uid);
		if (wxr != null) {
			return reward;
		}
		switch (category + "") {
		case PayService.buyGoodOrders:
			Orders order = ordersMapper.selectById(orderId);
			if (order == null || order.getWhereIsApp() != 2 || order.getStatus() != 1 || order.getStatus() != 4) {
				return reward;
			}
			break;
		case PayService.payGoldCoin:
			GoldCoinOrders gco = goldCoinOrdersMapper.selectByOrderId(orderId);
			if (gco == null || gco.getWhereIsApp() != 2 || gco.getStatus() != 1 || gco.getStatus() != 4) {
				return reward;
			}
			break;
		case PayService.payShop:
			ShopTradeOrders sto = shopTradeOrdersMapper.selectByOrderId(orderId);
			if (sto == null || sto.getWhereIsApp() != 2 || sto.getStatus() != 1 || sto.getStatus() != 4) {
				return reward;
			}
			break;
		default:
			return reward; 
		}
		reward = 200;
		wxr = new WxMiniPayOnceReward();
		wxr.setAmount(reward);
		wxr.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		wxr.setOrderId(orderId);
		wxr.setUid(uid);
		wxr.setTradeId(ExDateUtils.getCurrentDayFormat() + uid);
		wxMiniPayOnceRewardMapper.insertPojo(wxr);

		accountService.addGoldCoin(uid, 0, reward);

		accountBillService.addPojo(uid, wxr.getTradeId(), 1, 19, 2,
				ArithUtil.div(wxr.getAmount().doubleValue(), 100, 2), "微信小程序首次支付奖励");
		return reward;
	}

}
