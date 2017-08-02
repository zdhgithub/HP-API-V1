/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.ShopCoupons;
import cn.heipiao.api.pojo.ShopCouponsRecord;

/**
 * @author wzw
 * @date 2016年11月3日
 * @version 1.0
 */
public interface ShopCouponsService {

	
	List<ShopCoupons> getListByShopId(Map<String,Object> map );
	
	
	int addPojo(ShopCoupons pojo);


	/**
	 * @param shopId
	 * @return
	 */
	CouponsCount countByShopId(Long shopId);


	/**
	 * @param pojo
	 * @return
	 * @throws Exception 
	 */
	int addSpecifyPojo(ShopCoupons pojo) throws Exception;


	/**
	 * @param pojo
	 * @return
	 * @throws Exception 
	 */
	int addRoutinePojo(ShopCoupons pojo) throws Exception;


	/**
	 * @param uid
	 * @param id
	 * @param status
	 * @return
	 */
	int modifyShopCouponStatus(Long uid, Long id, Integer status);


	/**
	 * @param shopId
	 * @param flag
	 * @return
	 */
	List<ShopCouponsRecord> getShopUsers(Long shopId, Integer flag);


	/**
	 * @param id
	 * @return
	 */
	Boolean isOpenCoupon(Long shopId);


	/**
	 * @param uid
	 * @param shopId
	 * @param id 
	 * @return
	 * @throws Exception 
	 */
	int getShopCoupon(Long uid, Long shopId, Long id) throws Exception;


	/**
	 * @param shopId
	 * @param id 
	 * @return
	 */
	List<ShopCouponsRecord> getDetailList(Long shopId, Map<String,Object> map);


	/**
	 * @param shopId
	 * @param uid 
	 * @return
	 */
	List<ShopCoupons> getNotGetList(Long shopId, Long uid);


	/**
	 * @param sid
	 * @param fs
	 * @return
	 */
	int countUids(Long shopId, String[] fs);


	/**
	 * @param map
	 * @return
	 */
	List<ShopCoupons> getNewListByShopId(Map<String, Object> map);


	/**
	 *	线程调用
	 * @param id
	 */
	void updateCouponsTimeout(Long id);
	
}
