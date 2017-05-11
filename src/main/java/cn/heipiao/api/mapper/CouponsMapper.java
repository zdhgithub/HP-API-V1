/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Coupons;

/**
 * @author wzw
 * @date 2016年9月22日
 * @version 1.0
 */
public interface CouponsMapper {

	Integer countCouponsNew(Integer uid);
	Coupons selectByCid(Long cid);
	/**
	 * @param couponId
	 * @return
	 */
	Coupons selectByCidAsLock(Long couponId);
	
	List<Coupons> selectListByUserAndStatus(@Param("uid")Integer uid,@Param("status")Integer status);
	
	void insertPojo(Coupons pojo);
	
	void insertBatch(List<Coupons> pojos);
	
	/**
	 * 更新status和useTime
	 * @param pojo
	 */
	void updatePojo(Coupons pojo);

	/**
	 * @param c
	 */
	void updateCouponByUid(Coupons c);
	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	List<Coupons> selectUnusedShopCoupons(@Param("uid")Long uid, @Param("shopId")Long shopId);

	/**
	 * @param map
	 */
	void updateCouponsTimeout(Map<String, Object> map);
	/**
	 * @param param
	 * @return
	 */
	List<Coupons> selectByUid(Map<String, Object> param);
	
	/**
	 * 更新已读
	 */
	void updateIsRead();
}
