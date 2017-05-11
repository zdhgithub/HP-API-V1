/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CityRewardConfigMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.RewardAmountMapper;
import cn.heipiao.api.mapper.RewardConfigMapper;
import cn.heipiao.api.mapper.RewardDetailMapper;
import cn.heipiao.api.mapper.RewardOrdersMapper;
import cn.heipiao.api.mapper.UserRewardMapper;
import cn.heipiao.api.pojo.CityRewardConfig;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.RewardAmount;
import cn.heipiao.api.pojo.RewardConfig;
import cn.heipiao.api.pojo.RewardDetail;
import cn.heipiao.api.pojo.RewardOrders;
import cn.heipiao.api.pojo.RewardPlatform;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.UserReward;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.RewardPlatformService;
import cn.heipiao.api.service.ShopTradeOrdersService;
import cn.heipiao.api.service.UserRewardService;
import cn.heipiao.api.utils.ArithUtil;
import cn.heipiao.api.utils.ExDateUtils;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserRewardServiceImpl implements UserRewardService {

	@Resource
	private UserRewardMapper userRewardMapper;
	
	@Resource
	private RewardConfigMapper rewardConfigMapper;
	
	@Resource
	private CityRewardConfigMapper cityRewardConfigMapper;
	
	@Resource
	private RewardOrdersMapper rewardOrdersMapper;
	
	@Resource
	private AccountBillService accountBillService;
	
	@Resource
	private AccountService accountService;
	@Resource
	private RewardPlatformService rewardPlatformService;
	
	@Resource
	private RewardDetailMapper rewardDetailMapper;
	
	@Resource
	private RewardAmountMapper rewardAmountMapper;
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private ShopTradeOrdersService shopTradeOrdersService;
	
	/*
	 * 2.3关闭
	 *  (non-Javadoc)
	 * @see cn.heipiao.api.service.UserRewardService#reward(java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int reward(Long uid, Integer serviceId, Integer amount, String orderId,Integer cityId) {
		Calendar c = ExDateUtils.getCalendar();
		UserReward ur = userRewardMapper.selectAsLockByUid(uid);
		boolean b = ur == null;
		if(ur == null){
			ur = new UserReward();
			ur.setBusinessGive(0);
			ur.setCreateTime(new Timestamp(c.getTimeInMillis()));
			ur.setMonthTimes(0);
			ur.setPlatformGive(0);
			ur.setTimes(0);
			ur.setUid(uid);
			ur.setUpdateTime(new Timestamp(c.getTimeInMillis()));
		}
		
		//判断该区域是否有奖励机制
		CityRewardConfig crc = cityRewardConfigMapper.selectById(cityId);
		if(crc == null){
			//通用设置
			crc = cityRewardConfigMapper.selectById(1);
		}
		
		if(crc == null || crc.getRewardAmount() <= ur.getPlatformGive())
			return 0;
		RewardConfig rc = getUnique(serviceId, amount);
		if(rc == null)
			return 0;
		
		//判断是否为当前月次数是否超过2次
		if(Integer.parseInt(ExDateUtils.getDateFormat(c.getTime(), "yyyyMM")) ==
				Integer.parseInt(ExDateUtils.getDateFormat(ur.getUpdateTime(), "yyyyMM"))){
			if(ur.getMonthTimes() >= 2){
				return 0;
			}
			ur.setMonthTimes(ur.getMonthTimes() + 1);
		}else{
			ur.setMonthTimes(1);
		}
		
		
		
		//获取奖励漂币通过次数
		int rAmount = 0;
		if(ur.getTimes() == 0){
			rAmount = rc.getOnce(); 
		} else if(ur.getTimes() == 1){
			rAmount = rc.getTwice(); 
		} else {
			rAmount = rc.getMore(); 
		}
		
		int rate = (int)(ur.getPlatformGive().doubleValue() / crc.getRewardAmount() * 100);
		int rg = 0;
		if(rate < 50){
			rg = rAmount;
		}else if(rate >= 50 && rate < 80){
			rg = (int) (rAmount * 0.4);
		}else if(rate >= 80 && rate < 100){
			rg = (int) (rAmount * 0.2);
		}else{
			return 0;
		}
		
		//更新数据并插入
		ur.setPlatformGive(ur.getPlatformGive() + rg);
		ur.setTimes(ur.getTimes() + 1);
		ur.setUpdateTime(new Timestamp(c.getTimeInMillis()));
		if(b){
			userRewardMapper.insertPojo(ur);
		}else{
			userRewardMapper.updatePojo(ur);
		}
		// 增加漂币
		accountService.addGoldCoin(uid, rg, 0);

		//创建订单
		RewardOrders ro = new RewardOrders();
		ro.setAmount(rg);
		ro.setCreateTime(new Timestamp(c.getTimeInMillis()));
		ro.setDesc(serviceId == 1 ? "核票奖励漂币":"票支付奖励漂币");
		ro.setOrderId(orderId);
		ro.setServiceId(serviceId);
		ro.setTradeId(ExDateUtils.getCurrentDayFormat("yyMMddHHmmss") + RandomNumberUtils.getFixedLengthNum(10, uid.toString()));
		ro.setUid(uid);
		rewardOrdersMapper.insertPojo(ro);
		//添加记录
		accountBillService.addPojo(uid, ro.getTradeId(), 1, serviceId == 1 ? 14:13, 2, ArithUtil.div(rg, 100, 2), serviceId == 1 ? "购票奖励漂币":"票支付奖励漂币");
		return rg;
	}

	/**
	 * @param serviceId
	 * @param amount
	 * @return
	 */
	private RewardConfig getUnique(Integer serviceId, Integer amount) {
		List<RewardConfig> list = rewardConfigMapper.selectByServiceId(serviceId);
		if(list != null){
			for (RewardConfig rc : list) {
				if(rc.getMin() <= amount && (rc.getMax() == null || rc.getMax() > amount)){
					return rc;
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserRewardService#addPojo(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public void addPojo(Long uid, Integer pAmount, Integer bAmount) {
		Calendar c = ExDateUtils.getCalendar();
		UserReward ur = userRewardMapper.selectAsLockByUid(uid);
		if(ur == null){
			ur = new UserReward();
			ur.setBusinessGive(bAmount);
			ur.setCreateTime(new Timestamp(c.getTimeInMillis()));
			ur.setMonthTimes(0);
			ur.setPlatformGive(pAmount);
			ur.setTimes(0);
			ur.setUid(uid);
			ur.setUpdateTime(new Timestamp(c.getTimeInMillis()));
			userRewardMapper.insertPojo(ur);
		}else{
			ur.setBusinessGive(ur.getBusinessGive() + bAmount);
			ur.setPlatformGive(ur.getPlatformGive() + pAmount);
			userRewardMapper.updatePojo(ur);
		}
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserRewardService#getUserReward(java.lang.Long)
	 */
	@Override
	public UserReward getUserReward(Long uid) {
		return userRewardMapper.selectByUid(uid);
	}

}
