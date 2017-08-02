package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopUserStatus;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月12日
 * @version 2.0
 */
public interface FishShopMapper {
	public List<Integer> getFishShopUIDs();
	/**
	 * 通过渔具店唯一标识查询指定的渔具店
	 * @param id
	 * @param uid 可以为空，为空则不查询此人对此渔具店的点赞及收藏情况
	 * @return
	 */
	FishShop selectFishShopById(Map<String, Object> map);
	
	/**
	 * 通过渔具店所属用户唯一标识查询渔具店列表数据（多行）
	 * @param uid
	 * @return
	 */
	List<FishShop> selectFishShopByUid(Integer uid);
	
	/**
	 * 通过渔具店所属用户唯一标识查询渔具店全部数据（多行）
	 * @param uid
	 * @return
	 */
	List<FishShop> selectFishShopAllByUid(Integer uid);

	/**
	 * 新建一个渔具店
	 * @param fishShop
	 * @return
	 */
	Integer insertFishShop(FishShop fishShop);

	/**
	 * 通过渔具店唯一标识修改渔具店信息
	 * @param fishShop
	 * @return
	 */
	Integer updateFishShopById(FishShop fishShop);

	/**
	 * 统一某城市的渔具店数量
	 * @param cityId
	 * @return
	 */
	Integer countCityFishShop(Integer cityId);
	

	/**
	 * 指定渔具店修改所属用户及实名认证状态
	 * @param fishShop
	 * @return
	 */
	Integer updateFishShopUidAuthStatus(FishShop fishShop);

	/**
	 * 查询渔具店(分类检索)
	 * @param map
	 * @return
	 */
	List<FishShop> queryList(Map<String, Object> map);

	/**
	 * 查询渔具店(分类检索),总记录数
	 * @param map
	 * @return
	 */
	int queryListCount(Map<String, Object> map);

	/**
	 * 指定渔具店置顶
	 * @param id
	 */
	int updateFishShopTop(Map<String, Object> map);
	
	/**
	 * 获取当前置顶的最大值
	 * @return
	 */
	int selectMaxTop();

	/**
	 * 查询指定用户及渔具店的用户状态信息
	 * @return
	 */
	FishShopUserStatus selectFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 删除指定用户及渔具店的用户状态信息
	 * @return
	 */
	int deleteFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 添加指定用户及渔具店的用户状态信息
	 * @return
	 */
	int insertFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 指定渔具店设置规模分类
	 * @param pojo
	 * @return
	 */
	int updateFishShopScale(FishShop pojo);

	/**
	 * 当签约认证完成时，清除渔具店认领表的相应记录
	 * @param pojo
	 */
	void deleteFishShopSign(FishShop pojo);

	/**
	 * 获取店铺基本信息
	 * 只查询店铺表
	 * @param shopId
	 * @return
	 */
	FishShop selectFishShopByShopId(Long shopId);
	
	/**
	 * 加锁
	 * 获取店铺基本信息
	 * 只查询店铺表
	 * @param shopId
	 * @return
	 */
	FishShop selectFishShopAsLockByShopId(Long shopId);

	List<FishShop> selectAllFishShop();
	
	List<Long> selectAllWhereUIDNotNull();

	/**
	 * @param shopId
	 * @return
	 */
	Set<Long> selectUidsByShopId(Long shopId);

	/**
	 * @param shopId
	 */
	void updateOpenCoupon(Long shopId);

	int selectPartnerListCount(Map<String, Object> map);

	List<FishShop> selectPartnerList(Map<String, Object> map);

	/**
	 * @param fs
	 */
	void updateIsCouponAndCount(@Param("shopId")Long shopId,@Param("isCoupon")Boolean isCoupon,@Param("couponCount")Integer couponCount);

	/**
	 * @param uid
	 * @return
	 */
	boolean isExistsNormal(Long uid);

	/**
	 * 
	 * @param userStatus
	 * @return
	 */
	int deleteFishShopUserStatus4HD(FishShopUserStatus userStatus);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int updateFishShopFocusCount(Long id);

	/**
	 * 
	 * @return
	 */
	List<FishShopUserStatus> getAllFSUSShopId();

	void updateIsAuthCoupon(@Param("isAuthCoupon")Boolean isAuthCoupon,@Param("shopId")Long shopId, @Param("isCoupon")Boolean isCoupon);

	/**
	 * @param id
	 * @param partnerId
	 */
	void updatePartnerIdFishShopById(@Param("shopId")Long shopId);

	/**
	 * @param partnerId
	 */
	void updatePartnerIdIsNull(Integer partnerId);

	/**
	 * 查询指定用户所关注的店铺（数据）
	 */
	List<FishShop> selectCollectList(Map<String, Object> map);

	/**
	 * 查询指定用户所关注的店铺（数量）
	 */
	int selectCollectListCount(Map<String, Object> map);

	/**
	 * 指定店铺设置图标logo
	 * @param pojo
	 * @return
	 */
	int updateLogo(FishShop pojo);
	/**查询钓场附近的渔具店（10公里以内）*/
	List<FishShop> queryDistributionList(Map<String,Object> map);
	/**平台收回渔具店*/
	int updateFishShopAuthUid(Long shopId);
	/**查询合伙人签约的渔具店*/
	List<FishShop> queryFishShopStatusByPartner(Map<String,Object> map);
	/**查询合伙人已认领，或已售票认领的渔具店*/
	List<FishShop> queryFishSignShopStatusByPartner(Map<String,Object> map);
	
}
