package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.DepositFishMapper;
import cn.heipiao.api.mapper.DepositFishTicketRecordMapper;
import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.DepositFishExtend;
import cn.heipiao.api.pojo.DepositFishTicketRecord;
import cn.heipiao.api.service.DepositFishService;
import cn.heipiao.api.service.SystemRecordGeneratorService;
import cn.heipiao.api.service.UserBalanceOpService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 存鱼service
 * @date 2016年6月29日
 */
@Service
public class DepositFishServiceImpl implements DepositFishService {
	@Resource
	private DepositFishMapper depositFishMapper;
	@Resource
	private SystemRecordGeneratorService systemRecordGenerator;
	@Resource
	private UserBalanceOpService userBalanceOpService;
	@Resource
	private DepositFishTicketRecordMapper depositFishTicketRecordMapper;

	@Override
	public List<DepositFish> getDepositFishsBySite(Map<String, Object> param)
			throws Exception {
		List<DepositFish> depositFishs = depositFishMapper.selectListNew(param);
		return depositFishs;
	}

	@Override
	public List<DepositFishExtend> getDepositFishsByUser(Long userId,
			Integer start, Integer size) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("start", start);
		param.put("size", size);
		List<DepositFishExtend> depositFishs = depositFishMapper
				.selectExList(param);
		return depositFishs;
	}

	@Override
	public Double countDepositFishBySite(Integer siteId) throws Exception {
		Double counts = depositFishMapper.countDepositFishBySite(siteId);
		return counts;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDepositFish(DepositFish depositFish) throws Exception {
		if (depositFish == null) {
			return;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		Long userId = depositFish.getUserId();
		Integer siteId = depositFish.getSiteId();
		Double amount = depositFish.getAmount();
		if (amount == null || amount.doubleValue() < 0) {
			return;
		}
		param.put("userId", userId);
		param.put("siteId", siteId);
		List<DepositFish> depositFishs = depositFishMapper.selectList(param);
		// 不是第一次
		if (depositFishs != null && depositFishs.size() > 0) {
			userBalanceOpService.inUserDepositFishBalance(userId, siteId,
					amount);

		} else {
			depositFish.setCreateTime(ExDateUtils.getDate());
			depositFish.setUpdateTime(ExDateUtils.getDate());
			depositFish.setBalance(amount);
			depositFishMapper.insert(depositFish);
		}
		systemRecordGenerator.generateInDepositFishRecord(userId, siteId,
				amount);
		DepositFishTicketRecord dr = new DepositFishTicketRecord();
		dr.setUid(depositFish.getUserId().intValue());
		dr.setTid(depositFish.getTicketId());
		depositFishTicketRecordMapper.insert(dr);
	}

	@Override
	@Transactional(readOnly = false)
	public void modifyDepositFish(DepositFish depositFish) throws Exception {
		depositFish.setUpdateTime(ExDateUtils.getDate());
		depositFishMapper.updateById(depositFish);
	}

	@Override
	public Double getDepositesForUser(Integer userId, Integer siteId)
			throws Exception {
		DepositFish depositFish = depositFishMapper.selectByUidAndFishSiteId(
				userId, siteId);
		return depositFish == null ? 0 : depositFish.getBalance();
	}

	@Override
	public DepositFishTicketRecord getDFTR(Integer uid, Long tid)
			throws Exception {
		DepositFishTicketRecord dr = depositFishTicketRecordMapper.selectById(
				uid, tid);
		return dr;
	}

}
