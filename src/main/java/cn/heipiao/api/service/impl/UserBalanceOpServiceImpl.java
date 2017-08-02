package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.DepositFishMapper;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.service.UserBalanceOpService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 账户余额操作
 * @date 2016年7月11日
 */
@Service
public class UserBalanceOpServiceImpl implements UserBalanceOpService {
	@Resource
	private DepositFishMapper depositFishMapper;
	@Resource
	private AccountMapper accountMapper;

	@Override
	@Transactional(readOnly = false)
	public void inUserDepositFishBalance(Long userId, Integer siteId,
			Double amount) {
		DepositFish depositFish = depositFishMapper
				.selectByUidAndFishSiteIdAsLock(userId, siteId);
		if (depositFish != null) {
			double balance = ArithUtil.add(depositFish.getBalance(), amount);
			depositFish.setBalance(balance);
			depositFish.setUpdateTime(ExDateUtils.getDate());
			depositFishMapper.updateByUidAndFishSiteId(userId,
					siteId, balance);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deUserDepositFishBalance(Long userId, Integer siteId,
			Double amount) {
		DepositFish depositFish = depositFishMapper
				.selectByUidAndFishSiteIdAsLock(userId, siteId);
		if (depositFish != null) {
			double balance = ArithUtil.sub(depositFish.getBalance(), amount);
			depositFish.setBalance(balance);
			depositFish.setUpdateTime(ExDateUtils.getDate());
			depositFishMapper.updateByUidAndFishSiteId(userId,
					siteId, balance);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void inUserBalance(Long userId, Double amount) {
		Account account = accountMapper.selectByUidAsLock(userId);
		if (account != null) {
			double balance = ArithUtil.add(account.getBalance(), amount);
			accountMapper.updateBalance(userId, balance);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deUserBalance(Long userId, Double amount) {
		Account account = accountMapper.selectByUidAsLock(userId);
		if (account != null) {
			double balance = ArithUtil.sub(account.getBalance(), amount);
			accountMapper.updateBalance(userId, balance);
		}
	}

}
