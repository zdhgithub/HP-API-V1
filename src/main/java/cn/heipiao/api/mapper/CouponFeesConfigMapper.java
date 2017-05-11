/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.CouponFeesConfig;

/**
 * @author wzw
 * @date 2016年11月4日
 * @version 1.0
 */
public interface CouponFeesConfigMapper {

//	CouponFeesConfig selectUnique(@Param("serviceId")Integer serviceId,@Param("fee")Integer fee);

	/**
	 * @param serviceId
	 * @return
	 */
	List<CouponFeesConfig> selectById(@Param("serviceId")Integer serviceId,@Param("categoryId")Integer categoryId);
	
}
