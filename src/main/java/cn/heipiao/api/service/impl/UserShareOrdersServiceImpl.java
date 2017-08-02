/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.UserShareOrdersMapper;
import cn.heipiao.api.pojo.UserShareOrders;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.UserShareOrdersService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年11月30日
 * @version 1.0
 */
@Transactional(readOnly = true)
@Service
public class UserShareOrdersServiceImpl implements UserShareOrdersService {

	@Resource
	private UserShareOrdersMapper userShareOrdersMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareOrdersService#addShareOrder(java.lang.Long, java.lang.Integer, java.lang.Long)
	 */
	@Override
	public void addShareOrder(Long uid, Integer amount, Long shareId) {
		UserShareOrders uso = new UserShareOrders();
		uso.setAmount(amount);
		uso.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		uso.setOrderId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + uid);
		uso.setShareId(shareId);
		uso.setUid(uid);
		
		userShareOrdersMapper.insertPojo(uso);
		
		//添加账单
		accountBillService.addPojo(uso.getUid(), uso.getOrderId(), 1, 16, 2, ArithUtil.div((double)uso.getAmount(),100,2), "分享渔获奖励");
				
	}

}
