/**
 * 
 */
package cn.heipiao.api.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.ShopCoupons;

/**
 * @author wzw
 * @date 2016年11月2日
 * @version 1.0
 */
public interface ShopCouponsMapper {

	
	ShopCoupons selectById(Long id);

	ShopCoupons selectAslockById(Long id);
	
	/**
	 * 统计正常的大众券数量
	 * @param shopId
	 * @return
	 */
	int countNormal(Long shopId);
	
	void updatePojo(ShopCoupons pojo);

	void insertPojo(ShopCoupons pojo);

	/**
	 * @param map
	 * @return
	 */
	List<ShopCoupons> selectListByShopId(Map<String, Object> map);
	
	/**
	 * @param shopId
	 * @return
	 */
	CouponsCount countByShopId(Long shopId);

	/**
	 * @param id
	 * @param status
	 */
	void updateShopCouponStatus(@Param("id")Long id, @Param("status")Integer status);

	/**
	 * @param shopId
	 * @param uid
	 * @return
	 */
	List<ShopCoupons> selectNotGetList(@Param("shopId")Long shopId, @Param("uid")Long uid,@Param("curDate")Timestamp curDate);

	/**
	 * @param map
	 * @return
	 */
	List<ShopCoupons> selectNewListByShopId(Map<String, Object> map);

	/**
	 * 更新所有优惠券为暂停状态
	 * @param shopId
	 */
	void updateShopAllCoupon(Long shopId);

	/**
	 * 任务调用
	 * @param map
	 * @return
	 */
	List<Long> selectByTimeout(Map<String, Object> map);
	
}
