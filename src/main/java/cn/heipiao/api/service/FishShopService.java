package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopUserStatus;
import cn.heipiao.api.pojo.PartnerOver;
import cn.heipiao.api.pojo.PartnerShopRateConfig;
import cn.heipiao.api.pojo.PartnerSiteRateConfig;


/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
public interface FishShopService {
	public FishShop getFishShopById(Long id) throws Exception;
	/**
	 * 通过渔具店所属用户唯一标识查询渔具店列表数据（多行）
	 * @param uid
	 * @return
	 */
	List<FishShop> getFishShopByUid(Integer uid);

	/**
	 * 通过渔具店所属用户唯一标识查询渔具店全部数据（多行）
	 * @param uid
	 * @return
	 */
	List<FishShop> getFishShopAllByUid(Integer uid);

	/**
	 * 通过渔具店唯一标识修改渔具店信息
	 * @param fishShop
	 * @return
	 */
	void setFishShop(FishShop pojo);

	/**
	 * 通过渔具店唯一标识查询指定的渔具店
	 * @param id
	 * @param uid 
	 * @return
	 */
	FishShop getFishShopById(Long id, Integer uid);

	/**
	 * 新建渔具店
	 */
	long addFishShop(FishShop pojo) throws Exception;

	/**
	 * 指定渔具店店主
	 * @param fishShop
	 * @return
	 */
	int setFishShopUid(FishShop pojo) throws Exception;

	/**
	 * 指定渔具店签约认证
	 * @param pojo
	 * @return
	 */
	int setFishShopPactPass(FishShop pojo);

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
	int setFishShopTop(Map<String, Object> map);

	/**
	 * 获取当前置顶的最大值
	 * @return
	 */
	int getMaxTop();

	/**
	 * 查询指定用户及渔具店的用户状态信息
	 * @param map
	 * @return
	 */
	FishShopUserStatus getFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 删除指定用户及渔具店的用户状态信息（状态）
	 * @param map
	 * @return
	 */
	int delFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 添加指定用户及渔具店的用户状态信息
	 * @param map
	 * @return
	 */
	int addFishShopUserStatus(FishShopUserStatus userStatus);

	/**
	 * 指定渔具店设置规模分类
	 * @param pojo
	 * @return
	 */
	int setFishShopScale(FishShop pojo);

	List<FishShop> getAllFishShop();
	/**
	 * @param uid
	 * @param shopId
	 * @return
	 */
	public int modifyOpenCoupon(Long uid, Long shopId);
	
	public int queryPartnerListCount(Map<String, Object> map);
	
	public List<FishShop> queryPartnerList(Map<String, Object> map);
	List<PartnerOver> queryPartnerOverList(Integer partnerId);
	/**
	 * 删除指定用户及渔具店的用户状态信息（硬盘）
	 * @param userStatus
	 */
	public int delFishShopUserStatus4HD(FishShopUserStatus userStatus);
	
	/**
	 * 更新根据渔具店的用户收藏状态信息更新渔具店主表的关注数量
	 * @param id
	 */
	public int updateFishShopFocusCount(Long id);
	/**
	 * 查询“渔具店用户保存状态流水表”中的所有渔具店id
	 * @return
	 */
	public List<FishShopUserStatus> getAllFSUSShopId();
	/**
	 * @param uid
	 * @param shopId
	 * @param isAuthCoupon
	 * @return
	 */
	public int modifyIsAuthCoupon(Long uid, Long shopId, Boolean isAuthCoupon);
	
	/**
	 * 查询指定用户所关注的店铺（数据）
	 */
	public List<FishShop> queryCollectList(Map<String, Object> map);
	/**
	 * 查询指定用户所关注的店铺（数量）
	 */
	public int queryCollectListCount(Map<String, Object> map);
	
	/**
	 * 指定店铺设置图标logo
	 * @param pojo
	 */
	public int setLogo(FishShop pojo);
	/**
	 * 店铺换老板
	 * @param oldUid
	 * @param newUid
	 * @return
	 * @throws Exception 
	 */
	public int changeFishShop(Long shopId, Long newUid) throws Exception;
	/**
	 * 查询钓场附近的渔具店10公里以内
	 */
	List<FishShop> queryDistributionList(Map<String,Object> map);
	/**获取合伙人钓场抽成配置*/
	PartnerShopRateConfig selectShopRateConfig(Long shopId,Integer partnerId);
	/**修改合伙人钓场抽成配置*/
	int updateShopRateConfig(Long shopId,Integer partnerId,Double shopRate);
	/**修改渔具店签约合伙人*/
	int updateFishShopSignUser(Long shopId);
	/**查询 合伙人渔具店签约状态*/
	List<FishShop> selectFishShopByStatus(Integer partnerId,Integer status);
}
