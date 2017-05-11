/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.WithdrawConfig;

/**
 * @author wzw
 * @date 2016年9月26日
 * @version 1.0
 */
public interface WithdrawConfigService {

	void addPojo(WithdrawConfig pojo);
	
	
	void updatePojo(WithdrawConfig pojo);
	
	List<WithdrawConfig> selectAllByRuleId(Integer withdrawRuleId,Integer serviceId);

	/**
	 * @return
	 */
	List<WithdrawConfig> selectAll();
	
	
	WithdrawConfig selectByRuleIdAndDelayForDay(Integer serviceId,Integer ruleId,Integer delay);
	
}
