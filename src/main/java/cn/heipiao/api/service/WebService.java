/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerCouponRecord;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserRecommend;

/**
 * @author wzw
 * @date 2016年8月30日
 * @version 1.0
 */
public interface WebService {

	/**
	 * @param pcr
	 * @return
	 * @throws Exception 
	 */
	int giveOutCoupon(Long uid, String phone) throws Exception;

	
	int giveOutCouponNew(Long uid, String phone,int category) throws Exception;

	/**
	 * @param uid
	 * @param phone
	 * @return
	 * @throws Exception 
	 */
	int userRecommend(Long uid, String phone) throws Exception;

	/**
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	List<PartnerCouponRecord> selectByPartnerId(Long uid) throws Exception;

	/**
	 * @param uid
	 * @return
	 */
	List<UserRecommend> selectListByUid(Long uid);
	
	/**
	 * 推荐人获取奖励漂币
	 * @param uid
	 * @param amount
	 * @throws Exception 
	 */
	void addRewardToRuid(Long uid,Integer amount,String desc,Integer type) throws Exception;

	/**
	 * @param uid
	 * @return
	 */
	User selectUserByUid(Long uid);

	/**
	 * @param uid
	 * @return
	 */
	Integer selectUserCount(Long uid);

	/**
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> selectPartnerCount(Long uid) throws Exception;

	/**
	 * @param uid
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> selectPartnerPrerogativeCount(Long uid) throws Exception;


	/**
	 * 商家优惠券分享后，页面获取商家优惠券功能
	 * @param phone
	 * @param business
	 * @param coupons
	 * @param isSiteId
	 * @return
	 */
	int couponsShareGet(String phone, Long business, Long coupons, boolean isSiteId);

}
