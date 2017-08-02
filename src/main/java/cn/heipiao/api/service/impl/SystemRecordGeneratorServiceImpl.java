package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AccountRecordMapper;
import cn.heipiao.api.mapper.DepositFishRecordMapper;
import cn.heipiao.api.pojo.AccountRecord;
import cn.heipiao.api.pojo.DepositFishRecord;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 交易记录生成
 * @date 2016年7月11日
 */
@Service
public class SystemRecordGeneratorServiceImpl implements
		SystemRecordGeneratorService {
	@Resource
	private AccountRecordMapper accountRecordMapper;
	@Resource
	private DepositFishRecordMapper depositFishRecordMapper;

	@Override
	@Transactional(readOnly = false)
	public void generateAccountDeRecord(Integer userId, String orderId,
			int tradeType, double tradeMoney, String description,
			String otherSide, Integer status,Integer fishSiteId) {
		AccountRecord pojo = new AccountRecord();
		pojo.setUid(userId);
		pojo.setDirection(0);
		pojo.setTradeMoney(tradeMoney);
		Timestamp ts = new Timestamp(ExDateUtils.getDate().getTime());
		pojo.setTradeTime(ts);
		pojo.setTradeType(tradeType);
		pojo.setDescription(description);
		pojo.setOrderId(orderId);
		pojo.setOtherSide(otherSide);
		pojo.setStatus(status);
		pojo.setSiteId(fishSiteId);
		accountRecordMapper.insertPojo(pojo);
	}

	@Override
	@Transactional(readOnly = false)
	public void generateAccountInRecord(Integer userId, String orderId,
			int tradeType, double tradeMoney, String description,
			String otherSide, Integer status,Integer fishSiteId) {
		AccountRecord pojo = new AccountRecord();
		pojo.setUid(userId);
		pojo.setDirection(1);
		pojo.setTradeMoney(tradeMoney);
		Timestamp ts = new Timestamp(ExDateUtils.getDate().getTime());
		pojo.setTradeTime(ts);
		pojo.setTradeType(tradeType);
		pojo.setDescription(description);
		pojo.setOrderId(orderId);
		pojo.setOtherSide(otherSide);
		pojo.setStatus(status);
		pojo.setSiteId(fishSiteId);
		accountRecordMapper.insertPojo(pojo);
	}

	@Override
	@Transactional(readOnly = false)
	public void generateDeDepositFishRecord(Long userId, Integer siteId,
			Double tradeMoney) {
		DepositFishRecord dp = new DepositFishRecord();
		dp.setUserId(userId);
		dp.setDirection(0);
		dp.setSiteId(siteId);
		dp.setTradeMoney(tradeMoney);
		dp.setCreateTime(ExDateUtils.getDate());
		depositFishRecordMapper.insert(dp);
	}

	@Override
	@Transactional(readOnly = false)
	public void generateInDepositFishRecord(Long userId, Integer siteId,
			Double tradeMoney) {
		DepositFishRecord dp = new DepositFishRecord();
		dp.setUserId(userId);
		dp.setDirection(1);
		dp.setSiteId(siteId);
		dp.setTradeMoney(tradeMoney);
		dp.setCreateTime(ExDateUtils.getDate());
		depositFishRecordMapper.insert(dp);
	}
}
