/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.RewardConfig;

/**
 * @author wzw
 * @date 2016年10月26日
 * @version 1.0
 */
public interface RewardConfigMapper {

	
	RewardConfig selectUnique(@Param("serviceId")Integer serviceId,@Param("amount")Integer amount);
	
	List<RewardConfig> selectByServiceId(Integer serviceId);
	
}
