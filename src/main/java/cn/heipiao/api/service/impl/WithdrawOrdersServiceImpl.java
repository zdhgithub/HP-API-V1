/**
 * 
 */
package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.WithdrawOrdersMapper;
import cn.heipiao.api.pojo.WithdrawOrders;
import cn.heipiao.api.service.WithdrawOrdersService;

/**
 * @author wzw
 * @date 2016年8月6日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class WithdrawOrdersServiceImpl implements WithdrawOrdersService {

	@Resource
	private WithdrawOrdersMapper withdrawOrdersMapper;
	

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawOrdersService#selectByTradeNo(java.lang.String)
	 */
	@Override
	public WithdrawOrders selectByTradeNo(String tradeNo) {
		return withdrawOrdersMapper.selectByTradeNo(tradeNo);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawOrdersService#addPojo(cn.heipiao.api.pojo.WithdrawOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addPojo(WithdrawOrders pojo) {
		withdrawOrdersMapper.insertPojo(pojo);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawOrdersService#modifyPojo(cn.heipiao.api.pojo.WithdrawOrders)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void modifyPojo(WithdrawOrders pojo) {
		withdrawOrdersMapper.updatePojo(pojo);
	}

}
