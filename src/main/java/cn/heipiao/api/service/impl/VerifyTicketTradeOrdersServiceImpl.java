/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.VerifyTicketTradeOrdersMapper;
import cn.heipiao.api.pojo.VerifyTicketTradeOrders;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.VerifyTicketTradeOrdersService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年10月19日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class VerifyTicketTradeOrdersServiceImpl implements VerifyTicketTradeOrdersService {
	
	@Resource
	private VerifyTicketTradeOrdersMapper verifyTicketTradeOrdersMapper;
	
	@Resource
	private AccountBillService accountBillService;
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.VerifyTicketTradeOrdersService#addPojo()
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addPojo(Long uid,Integer fishSiteId,Long tid,Double balance) {
		VerifyTicketTradeOrders vtto = new VerifyTicketTradeOrders();
		vtto.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		vtto.setDesc("核票收益");
		vtto.setFishSiteId(fishSiteId);
		vtto.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, uid.toString()));
		vtto.setTid(tid);
		vtto.setTradeFee(balance);
		vtto.setUid(uid);
		verifyTicketTradeOrdersMapper.insertPojo(vtto);
		
		//添加账单
		accountBillService.addPojo(uid, vtto.getOrderId(), 1, 4, 1, balance, "核票收入");
	}

}
