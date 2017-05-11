/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;
import cn.heipiao.api.pojo.PartnerSiteRateConfig;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
public interface FishSiteService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	FishSite selectById(Integer id);
	/**
	 * 
	 * @param id
	 * @return
	 */
	List<FSPojo> selectListForPartnerSign(Map<String,Object> map);

	/**
	 * 
	 * @param uid
	 * @return
	 */
	FishSite selectByUid(Long uid);

	/**
	 * 
	 * @param filterName
	 * @return
	 */
	List<FishSite> selectList(Map<String, Object> filterName);

	/**
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	void updateSite(FishSite pojo) throws Exception;

	/**
	 * 更新提现手续费费率
	 * 
	 * @param map
	 */
	void updateWithdrawRate(Map<String, Object> map);

	/**
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	int createSite(FishSite pojo) throws Exception;
	/**
	 * 
	 * @param pojo
	 * @throws Exception
	 */
	int createSiteByPartner(FishSiteExt pojo) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> likeByNameAndPhone(Map<String, Object> map);

	/**
	 * @param flag
	 * @return
	 */
	List<FishSite> selectByValue(Map<String, Object> map);
	
	List<FishSite> selectBytheValue(Map<String, Object> map);

	/**
	 * @param partnerId
	 * @param siteId
	 */
	int updateByPartnerIdAndSiteId(Integer partnerId, Integer siteId,
			Integer signStatus);
	
	int updateByPartnerOfTrading(Integer partnerId, Integer siteId,
			Integer signStatus);
	/**
	 * @param map
	 * @return
	 */
	List<FishSite> queryList(Map<String, Object> map);
	/**
	 * 查询渔具店附近的钓场
	 * @param map
	 * @return
	 */
	List<FishSite> queryDistributionList(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	List<FishSite> queryListByCity(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	List<FishSite> queryListByPartner(Map<String, Object> map);

	/**
	 * @param status
	 * @return
	 */
	Integer selectCount(Integer status);
	
	Integer selectSiteCount(Map<String, Object> map);

	/**
	 * @param filterName
	 * @return
	 */
	List<FishSite> selectByRegion(Map<String, Object> filterName);

	/**
	 * @param filterName
	 * @return
	 */
	List<FishSite> selectByRegionCp(Map<String, Object> filterName);

	/**
	 * @param filterName
	 * @return
	 */
	int selectByRegionCpTotal(Map<String, Object> filterName);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> likeByName(Map<String, Object> map);

	/**
	 * 查询合伙人签约钓场
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<FishSite> getSitesByPartnerId(Map<String, Object> param)
			throws Exception;

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectFishSiteByPartnerId(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	Integer selectCountByNameAndPhone(Map<String, Object> map);

	int updateSiteUid(FishSite pojo) throws Exception;

	/**
	 * @param uid
	 * @return
	 */
	FishSite selectAllByUid(Long uid);

	/**
	 * @param filterName
	 * @return
	 */
	List<FishSite> siteSelectList(Map<String, Object> filterName);

	/**
	 * 只修改钓场信息
	 * 
	 * @param pojo
	 */
	void updateSiteModify(FishSite pojo);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByFocusList(Map<String, Object> map);

	/**
	 * @param filterName
	 * @return
	 */
	List<FishSite> siteSelectList2(Map<String, Object> filterName);

	/**
	 * 统计合伙人认领的钓场数量
	 * 
	 * @param partnerId
	 * @return
	 * @throws Exception
	 */
	Integer countClaimSiteByPartner(Integer partnerId) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> getPartnerIdIsNullFishSite(Map<String, Object> map);

	/**
	 * 获取当前置顶最大值
	 * @return
	 */
	int getMaxTop();

	/**
	 * 设置指定钓场置顶状态
	 * @param map
	 * @return
	 */
	int setFishShopTop(Map<String, Object> map);

	/**
	 * @param uid
	 * @param siteId
	 * @return
	 */
	int modifyOpenCoupon(Long uid, Integer siteId);
	/**
	 * @param uid
	 * @param siteId
	 * @param isAuthCoupon
	 * @return
	 */
	int modifyIsAuthCoupon(Long uid, Integer siteId, Boolean isAuthCoupon);
	/**
	 * @param oldUid
	 * @param newUid
	 * @return
	 * @throws Exception 
	 */
	int changeFishSite(Long oldUid, Long newUid) throws Exception;
	
	/**
	 * 指定钓场设置图标logo
	 * @param fid
	 */
	int setLogo(FishSite pojo);
	/**获取合伙人钓场抽成配置*/
	PartnerSiteRateConfig selectSiteRateConfig(Integer siteId,Integer partnerId);
	/**修改合伙人钓场抽成配置*/
	int updateSiteRateConfig(Integer siteId,Integer partnerId,Double siteRate);
	
}
