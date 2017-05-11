/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.CouponUser;

/**
 * @author wzw
 * @date 2016年9月22日
 * @version 1.0
 */
public interface CouponUser10Mapper {

	/**
	 * @param param
	 * @return
	 */
	List<CouponUser> selectByUid(Map<String, Object> param);

	/**
	 * @param userId
	 * @param status
	 * @return
	 */
	int countUnusedCoupons(@Param("uid")Integer userId, @Param("status")Integer status);

	/**
	 * @param m
	 * @return
	 */
	List<CouponUser> getUsableCouponsByUser(Map<String, Object> m);
	
}
