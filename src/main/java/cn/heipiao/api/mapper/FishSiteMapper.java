package cn.heipiao.api.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;

/**
 * 
 * @author wzw
 * @date 2016年6月1日
 * @version 1.0
 */
public interface FishSiteMapper {
	public List<Integer> getAllSiteUIDs();
	
	public List<Integer> getFishSiteUIDs();
	/**
	 * 
	 * @param value
	 * @return
	 */
	Integer selectCount(Integer value);
	
	
	List<FSPojo> selectListForPartnerSign(Map<String , Object> map);

	/**
	 * 
	 * @param fishSiteId
	 * @return
	 */
	FishSite selectById(Integer fishSiteId);
	
	/**
	 * @说明 查询所有钓场
	 * @return
	 */
	List<Long> selectAllWhereUidNotNull();

	/**
	 * 
	 * @param uid
	 * @return
	 */
	FishSite selectByUid(Long uid);

	/**
	 * 
	 * @param map
	 * @return
	 */
	List<FishSite> selectList(Map<String, Object> map);

	/**
	 * 
	 * @param pojo
	 */
	void updatePojo(FishSite pojo);
	
	void updateFishSite(Integer id);

	/**
	 * 更新提现手续费费率
	 * 
	 * @param map
	 */
	void updateWithdrawRate(Map<String, Object> map);

	/**
	 * 
	 * @param pojo
	 */
	void insertPojo(FishSite pojo);
	/**
	 * 
	 * @param pojo
	 */
	void insertPojo2(FishSiteExt pojo);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByLikeName(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByLikeNameAndPhone(Map<String, Object> map);

	/**
	 * @param flag
	 * @param category
	 * @return
	 */
	List<FishSite> selectByValue(Map<String, Object> map);

	/**
	 * @param partnerId
	 * @param siteId
	 */
	void updateByPartnerIdAndSiteId(Map<String, Object> params);

	/**
	 * @param start
	 * @param size
	 * @return
	 */
	List<FishSite> selectByFocus(Map<String, Object> map);

	/**
	 * @param fishSiteId
	 * @param date
	 */
	void updatePutFishTime(@Param("siteId") Integer fishSiteId,
			@Param("updatePutFishTime") Date date);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> queryList(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	List<FishSite> queryDistributionList(Map<String, Object> map);
	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByRegion(Map<String, Object> map);

	/**
	 * 通过城市ID查询所有钓场
	 * 
	 * @param cityId
	 * @return
	 */
	List<FishSite> selectByCityId(Map<String, Object> map);

	/**
	 * 查询合伙人已经签约或是已经认领的钓场
	 * 
	 * @param uid
	 * @return
	 */
	List<FishSite> selectByPartner(Map<String, Object> map);
	/**
	 * 查询合伙人已经认领的钓场
	 * 
	 * @param uid
	 * @return
	 */
	List<FishSite> selectBySignStatus();

	/**
	 * @param cityId
	 * @return
	 */
	Integer countCityFishSite(@Param("cityId") Integer cityId);
	/**
	 * @param cityId
	 * @return
	 */
	Integer countCityFishSite2(@Param("cityId") Integer cityId);

	/**
	 * 加update锁
	 * 
	 * @param fid
	 * @return
	 */
	FishSite selectByIdAsLock(Integer fid);

	/**
	 * @param pojo
	 */
	void updateFocusCount(FishSite pojo);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByRegionCp(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	int selectByRegionCpTotal(Map<String, Object> map);

	/**
	 * 查询合伙人签约钓场
	 * 
	 * @param map
	 * @return
	 */
	List<FishSite> selectSitesByPartnerId(Map<String, Object> map);
	List<FishSite> selectSitesSignByPartnerId(Map<String, Object> map);
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

	/**
	 * @param uid
	 * @return
	 */
	FishSite selectAllByUid(Long uid);

	/**
	 * @说明 地图转换器
	 * @return
	 */
	int mapTransverter(FishSite fishSite);

	/**
	 * @说明 查询所有钓场
	 * @return
	 */
	List<FishSite> selectAll();

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> siteSelectList(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> siteSelectList2(Map<String, Object> map);

	/**
	 * 统计合伙人已认领的钓场数量
	 * 
	 * @param partnerId
	 * @return
	 */
	Integer countClaimSite(Integer partnerId);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectPartnerIdIsNullFishSite(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectFishSiteByPartner(Map<String, Object> map);

	/**
	 * 获取当前置顶最大值
	 * @return
	 */
	int selectMaxTop();

	/**
	 * 设置指定钓场置顶状态
	 * @param map
	 * @return
	 */
	int updateSiteTop(Map<String, Object> map);

	/**
	 * @param siteId
	 */
	void updateOpenCoupon(Integer siteId);

	/**
	 * @param fs
	 */
	void updateIsCouponAndCount(@Param("siteId")Integer siteId,@Param("isCoupon")Boolean isCoupon,@Param("couponCount")Integer couponCount);

	/**
	 * @param uid
	 * @return
	 */
	boolean isExistsNormal(Long uid);
	
	
	void updateIsAuthCoupon(@Param("isAuthCoupon")Boolean isAuthCoupon,@Param("siteId")Integer siteId);

	/**
	 * @param partnerId
	 */
	void updatePartnerIdIsNull(Integer partnerId);

	/**
	 * 指定钓场设置图标logo
	 */
	int updateLogo(FishSite pojo);

	/**
	 * @param siteId
	 * @param object
	 */
	void updatePartnerIdBySiteId(Integer siteId);
	/**
	 * 
	 * @param map
	 * @return
	 */
	int deleteDistribution(Map<String, Object> map);
	/**
	 * 添加配送关系
	 */
	int insertDistribution(Map<String, Object> map);
	/**
	 * 
	 */
	int updateBySiteId(Map<String, Object> map);
	/**
	 * 根据shopId查询已经支持配送的钓场
	 */
	List<FishSite> selectByShopId(Map<String,Object> map);
	
	List<FishSite> selectSiteList(Map<String,Object> map);
	Integer selectSiteCount(Map<String,Object> map);
}