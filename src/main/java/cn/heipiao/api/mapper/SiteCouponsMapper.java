/**
 * 
 */
package cn.heipiao.api.mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.SiteCoupons;

/**
 * @author wzw
 * @date 2016年11月2日
 * @version 1.0
 */
public interface SiteCouponsMapper {

	SiteCoupons selectById(Long id);

	SiteCoupons selectAslockById(Long id);
	
	/**
	 * 统计正常的大众券数量
	 * @param siteId
	 * @return
	 */
	int countNormal(Integer siteId);
	
	void updatePojo(SiteCoupons pojo);

	void insertPojo(SiteCoupons pojo);

	List<SiteCoupons> selectListBySiteId(Map<String,Object> map);

	/**
	 * @param siteId
	 * @return
	 */
	CouponsCount countBySiteId(Integer siteId);

	/**
	 * @param id
	 * @param status
	 */
	void updateSiteCouponStatus(@Param("id")Long id, @Param("status")Integer status);

	/**
	 * @param siteId
	 * @param uid
	 * @return
	 */
	List<SiteCoupons> selectNotGetList(@Param("siteId")Integer siteId, @Param("uid")Long uid,@Param("curDate")Timestamp curDate);

	/**
	 * @param map
	 * @return
	 */
	List<SiteCoupons> selectNewListBySiteId(Map<String, Object> map);

	/**
	 * 更新所有优惠券为暂停状态
	 * @param siteId
	 */
	void updateSiteAllCoupon(Integer siteId);

	/**
	 * 任务调用
	 * @param map
	 * @return
	 */
	List<Long> selectByTimeout(Map<String, Object> map);
}
