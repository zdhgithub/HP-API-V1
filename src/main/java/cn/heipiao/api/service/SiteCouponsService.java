/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.SiteCoupons;
import cn.heipiao.api.pojo.SiteCouponsRecord;

/**
 * @author wzw
 * @date 2016年11月3日
 * @version 1.0
 */
public interface SiteCouponsService {

	
	List<SiteCoupons> getListBySiteId(Map<String,Object> map);
	
	
	int addPojo(SiteCoupons pojo);


	/**
	 * @param pojo
	 * @return
	 * @throws Exception 
	 */
	int addRoutinePojo(SiteCoupons pojo) throws Exception;


	/**
	 * @param pojo
	 * @return
	 * @throws Exception 
	 */
	int addSpecifyPojo(SiteCoupons pojo) throws Exception;


	/**
	 * @param siteId
	 * @return
	 */
	CouponsCount countBySiteId(Integer siteId);


	/**
	 * @param uid
	 * @param id
	 * @param status
	 * @return
	 */
	int modifySiteCouponStatus(Long uid, Long id, Integer status);


	/**
	 * @param siteId
	 * @param flag
	 * @return
	 */
	List<SiteCouponsRecord> getSiteUsers(Integer siteId, Integer flag);


	/**
	 * @param intValue
	 * @return
	 */
	Boolean isOpenCoupon(Integer siteId);


	/**
	 * @param uid
	 * @param siteId
	 * @param id 
	 * @return
	 * @throws Exception 
	 */
	int getSiteCoupon(Long uid, Integer siteId, Long id) throws Exception;


	/**
	 * @param siteId
	 * @param id 
	 * @return
	 */
	List<SiteCouponsRecord> getDetailList(Integer siteId, Map<String,Object> map);


	/**
	 * @param siteId
	 * @param uid 
	 * @return
	 */
	List<SiteCoupons> getNotGetList(Integer siteId, Long uid);


	/**
	 * @param siteId
	 * @param fs
	 * @return
	 */
	int countUids(Integer siteId, String[] fs);


	/**
	 * @param map
	 * @return
	 */
	List<SiteCoupons> getNewListBySiteId(Map<String, Object> map);

	/**
	 * 线程调用
	 * @param id
	 */
	void updateCouponsTimeout(Long id);
	
}
