/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.PartnerShopEarningOrdersMapper;
import cn.heipiao.api.pojo.PartnerShopEarningOrders;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.PartnerShopEarningOrdersService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年10月19日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PartnerShopEarningOrdersServiceImpl implements PartnerShopEarningOrdersService {

	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private PartnerShopEarningOrdersMapper partnerShopEarningOrdersMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerEarningOrdersService#addPojo()
	 */
	@Override
	public void addPojo(Long uid ,String shopTradeId,Integer partnerId, Long shopId, Integer amount) {
		PartnerShopEarningOrders peo = new PartnerShopEarningOrders();
		peo.setAmount(amount);
		peo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		peo.setDesc("店铺支付收益");
		peo.setShopId(shopId);
		peo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, uid.toString()));
		peo.setPartnerId(partnerId);
		peo.setShopTradeId(shopTradeId);
		peo.setUid(uid);
		partnerShopEarningOrdersMapper.insertPojo(peo);
		
		//添加账单
		accountBillService.addPojo(peo.getUid(), peo.getOrderId(), 1, 15 , 2, ArithUtil.div((double)peo.getAmount(),100,2), "店铺支付收益");
			
	}

}
