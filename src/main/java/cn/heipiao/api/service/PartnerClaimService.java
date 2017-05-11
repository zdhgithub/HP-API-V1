package cn.heipiao.api.service;

import cn.heipiao.api.pojo.PartnerShopRewardReview;
import cn.heipiao.api.pojo.PartnerSiteRewardReview;

public interface PartnerClaimService {
	/**
	 * 合伙人认领钓场奖金发放
	 */
	int updateSiteId(Integer siteId,Integer shelvesAmount,Integer status)throws Exception;
	/**
	 * 合伙人售票认领钓场奖金发放
	 */
	int updateTradingSiteId(Integer siteId,Integer transactionSum,Integer status)throws Exception;
	/**
	 * 合伙人认领渔具店奖金发放
	 */
	int updateShopId(Long shopId,Integer shelvesAmount,Integer status)throws Exception;
	/**
	 * 合伙人售票认领渔具店奖金发放
	 */
	int updateTradingShopId(Long shopId,Integer transactionSum,Integer status)throws Exception;
	/**
	 * 拉去渔具店认领奖励配置
	 */
	PartnerShopRewardReview findShopRewardConfig(Long shopId);
	/**
	 * 拉去钓场认领奖励配置
	 */
	PartnerSiteRewardReview findSiteRewardConfig(Integer siteId);
	/**
	 * cp添加合伙人渔具店奖励配置
	 */
	int insertPartnerShopReward(Long bid,Integer amount,Integer style);
	/**
	 * cp添加合伙人钓场奖励配置
	 */
	int insertPartnerSiteReward(Integer bid,Integer amount,Integer style);
	/**cp审核合伙人认领钓场*/
	int updateSiteIdSign(Integer siteId,Integer status);
	/**cp审核合伙人售票认领钓场*/
	int updateSiteIdSignTrad(Integer siteId,Integer status);
	/**cp审核合伙人认领渔具店*/
	int updateShopIdSign(Long shopId,Integer status);
	/**cp审核合伙人售票认领渔具店*/
	int updateShopIdSignTrand(Long shopId,Integer status);
}
