package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerRewardConfig;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.pojo.ShopTradeOrders;
import cn.heipiao.api.pojo.UserTickets;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月3日
 */
public interface PartnerService {
	/**
	 * 通过user的id查询partner
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Partner queryPartnerById(Integer userId) throws Exception;

	/**
	 * 通过手机号或是username查询partner--支持模糊查询
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	/*
	 * public RespMsg<?> queryPartnerByPhoneOrName(Map<String,Object> map)
	 * throws Exception;
	 */
	/**
	 * 通过管理的区域id查询partner
	 * 
	 * @param region
	 * @return
	 * @throws Exception
	 */
	public List<Partner> queryPartnerByRegion(Map<String, Object> map)
			throws Exception;

	/**
	 * 分页查询partner--支持排序
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	/*
	 * public RespMsg<?> queryPartners(Map<String, Object> params) throws
	 * Exception;
	 *//**
	 * 分页查询合伙人签约钓场--支持排序
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	/*
	 * public RespMsg<?> queryPartnerSite(Map<String, Object> params) throws
	 * Exception;
	 */
	/**
	 * 添加合伙人
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public int savePartner(Partner partner) throws Exception;

	/**
	 * 取消合伙人
	 * 
	 * @param partnerId
	 * @return
	 * @throws Exception
	 */
	public int deletePartner(Integer partnerId) throws Exception;

	/**
	 * 合伙人分配钓场/取消
	 * 
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	public void distributePartnerSite(FishSite pojo) throws Exception;

	/**
	 * 修改合伙人信息
	 * 
	 * @param partner
	 * @return
	 * @throws Exception
	 */
	public void modifyPartner(Partner partner) throws Exception;

	/**
	 * 模糊查询合伙人列表
	 * 
	 * @param conditions
	 * @return
	 */
	public List<Partner> queryPartnerByPhoneOrNickname(Map<String, Object> map);

	/**
	 * @说明 获取合伙人所有的城市列表
	 * @return
	 */
	List<Region> getPaertnerRegion();

	/**
	 * @param uid
	 * @return
	 */
	public Partner selectByUid(Long uid);

	/**
	 * @param p
	 */
	public void updateCouponFeeAndSum(Partner p);

	/**
	 * @param map
	 * @return
	 */
	public List<FishSite> selectFishSiteByPartner(Map<String, Object> map);

	/**
	 * 查询对合伙人开放的城市列表
	 * 
	 * @return
	 */
	public List<Region> getOpenRegion();
	
	public List<Region> getOpenRegionSp();
	/**渔具店认领*/
	public Integer signShop(Long shopId, Long uid) throws Exception;
	/**渔具店售票认领*/
	public Integer ticketSignShop(Long shopId, Long uid) throws Exception;
	/**
	 * @param p
	 * @param fs
	 * @param ut
	 * @param fee fee 交易金额(漂币)
	 * @throws Exception 
	 */
	void siteEarn(Partner p, FishSite fs, UserTickets ut, Integer fee) throws Exception;

	/**
	 * @param sto
	 * @param fee 
	 * @throws Exception 
	 */
	void shopEarn(ShopTradeOrders sto, int fee) throws Exception;

	/**
	 * @param partnerId
	 * @param shopId
	 * @return
	 */
	public int removePartnerShop(Integer partnerId, Long shopId);

	/**
	 * @param partnerId
	 * @param siteId
	 * @return
	 */
	public int removePartnerSite(Integer partnerId, Integer siteId);

	/**
	 * @param uid
	 * @return
	 */
	public Partner selectPartnerByUid(Long uid);
	/**
	 * 
	 */
	public int insertConfig(PartnerRewardConfig partnerRewardConfig);
	public int deleteConfig(Integer id);
	public int updateConfig(PartnerRewardConfig partnerRewardConfig);
	public List<PartnerRewardConfig> selectConfig(Map<String,Object> map);
	public PartnerRewardConfig selectOne(Map<String,Object> map);
	/**查询所有的省份*/
	List<Region> getAllRegion();
}
