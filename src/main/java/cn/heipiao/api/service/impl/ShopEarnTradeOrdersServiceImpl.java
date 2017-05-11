/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ShopEarnTradeOrdersMapper;
import cn.heipiao.api.pojo.ShopEarnTradeOrders;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.ShopEarnTradeOrdersService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年10月19日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ShopEarnTradeOrdersServiceImpl implements ShopEarnTradeOrdersService {

	@Resource
	private ShopEarnTradeOrdersMapper shopEarnTradeOrdersMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopEarnTradeOrdersService#addPojo()
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addPojo(Long shopId,Long uid,Double balance) {
		ShopEarnTradeOrders seto = new ShopEarnTradeOrders();
		seto.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		seto.setDesc("店铺收益");
		seto.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, uid.toString()));
		seto.setShopId(shopId);
		seto.setTradeFee(balance);
		seto.setUid(uid);
		shopEarnTradeOrdersMapper.insertPojo(seto);
		
		//添加账单
		accountBillService.addPojo(uid, seto.getOrderId(), 1, 3, 1, balance, "店铺收益");
	}

}
