/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.PartnerEarningOrdersMapper;
import cn.heipiao.api.pojo.PartnerEarningOrders;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.PartnerEarningOrdersService;
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
public class PartnerEarningOrdersServiceImpl implements PartnerEarningOrdersService {

	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private PartnerEarningOrdersMapper partnerEarningOrdersMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerEarningOrdersService#addPojo()
	 */
	@Override
	public void addPojo(Long uid ,Long tid,Integer partnerId, Integer fishSiteid, Integer amount) {
		PartnerEarningOrders peo = new PartnerEarningOrders();
		peo.setAmount(amount);
		peo.setCreateTime(new Timestamp(ExDateUtils.getDate().getTime()));
		peo.setDesc("钓场核票收益");
		peo.setFishSiteId(fishSiteid);
		peo.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, uid.toString()));
		peo.setPartnerId(partnerId);
		peo.setTid(tid);
		peo.setUid(uid);
		partnerEarningOrdersMapper.insertPojo(peo);
		
		//添加账单
		accountBillService.addPojo(peo.getUid(), peo.getOrderId(), 1, 9	, 2, ArithUtil.div((double)peo.getAmount(),100,2), "核票收益");
			
	}

}
