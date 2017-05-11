/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.WithdrawConfigMapper;
import cn.heipiao.api.pojo.WithdrawConfig;
import cn.heipiao.api.service.WithdrawConfigService;

/**
 * @author wzw
 * @date 2016年9月26日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class WithdrawConfigServiceImpl implements WithdrawConfigService {

	@Resource
	private WithdrawConfigMapper withdrawConfigMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawConfigService#addPojo()
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void addPojo(WithdrawConfig pojo) {
		withdrawConfigMapper.insertPojo(pojo);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawConfigService#updatePojo()
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void updatePojo(WithdrawConfig pojo) {
		withdrawConfigMapper.updatePojo(pojo);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawConfigService#selectAll()
	 */
	@Override
	public List<WithdrawConfig> selectAllByRuleId(Integer withdrawRuleId,Integer serviceId) {
		return withdrawConfigMapper.selectAllByRuleId(withdrawRuleId, serviceId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawConfigService#selectAll()
	 */
	@Override
	public List<WithdrawConfig> selectAll() {
		return withdrawConfigMapper.selectAll();
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.WithdrawConfigService#selectByRuleIdAndDelayForDay(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public WithdrawConfig selectByRuleIdAndDelayForDay(Integer serviceId,Integer ruleId, Integer delay) {
		return withdrawConfigMapper.selectByRuleIdAndDelayForDay(serviceId,ruleId, delay);
	}

}
