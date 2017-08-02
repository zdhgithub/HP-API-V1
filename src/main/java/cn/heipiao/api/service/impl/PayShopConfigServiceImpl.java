/**
 * 
 */
package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.PayShopConfigMapper;
import cn.heipiao.api.pojo.PayShopConfig;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.PayShopConfigService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年10月15日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PayShopConfigServiceImpl implements PayShopConfigService {

	@Resource
	private PayShopConfigMapper payShopConfigMapper;
	
	@Resource
	private AccountService accountService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PayShopConfigService#getById(java.lang.Integer)
	 */
	@Override
	public PayShopConfig getById(Integer serviceId) {
		return payShopConfigMapper.selectById(serviceId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PayShopConfigService#addPojo(cn.heipiao.api.pojo.PayShopConfig)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addPojo(PayShopConfig pojo) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PayShopConfigService#modifyPojo(cn.heipiao.api.pojo.PayShopConfig)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void modifyPojo(PayShopConfig pojo) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * 
	 * 票支付奖励
	 * 
	 * @see cn.heipiao.api.service.PayShopConfigService#reward(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int reward(Long uid,String orderId, Double orderMoney) {
		long c = ExDateUtils.getCalendar().getTimeInMillis();
		int reward = 0;
		PayShopConfig psc = payShopConfigMapper.selectById(1);
		if(psc != null && psc.getStatus().intValue() == 1 && psc.getUseRule() <= orderMoney
				&& psc.getStartTime().getTime() >= c && psc.getEndTime().getTime() <= c  ){
			reward = (int)(orderMoney * 100 * psc.getRate());
			accountService.addGoldCoin(uid, reward , 0);
		}
		return reward;
	}

}
