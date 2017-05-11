/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.WithdrawConfig;

/**
 * @author wzw
 * @date 2016年9月26日
 * @version 1.0
 */
public interface WithdrawConfigMapper {

	List<WithdrawConfig> selectAllByRuleId(@Param("withdrawRuleId")Integer withdrawRuleId,@Param("serviceId")Integer serviceId);
	
	void insertPojo(WithdrawConfig pojo);
	
	void updatePojo(WithdrawConfig pojo);

	/**
	 * 
	 * @param serviceId 业务号
	 * @param withdrawRuleId 规则id
	 * @param delayForDay 延迟天数
	 * @return
	 */
	WithdrawConfig selectByRuleIdAndDelayForDay(@Param("serviceId")Integer serviceId ,@Param("withdrawRuleId")Integer withdrawRuleId,@Param("delayForDay") Integer delayForDay);

	/**
	 * @return
	 */
	List<WithdrawConfig> selectAll();

}
