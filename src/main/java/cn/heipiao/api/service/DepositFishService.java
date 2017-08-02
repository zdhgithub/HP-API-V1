package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.DepositFish;
import cn.heipiao.api.pojo.DepositFishExtend;
import cn.heipiao.api.pojo.DepositFishTicketRecord;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月29日
 */
public interface DepositFishService {
	/**
	 * 查询钓场的存鱼列表
	 * 
	 * @param siteId
	 * @return
	 */
	public List<DepositFish> getDepositFishsBySite(Map<String, Object> param)
			throws Exception;

	/**
	 * 查询用户在不同钓场的存鱼列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<DepositFishExtend> getDepositFishsByUser(Long userId,
			Integer start, Integer size) throws Exception;

	/**
	 * 查询钓场的存鱼总额
	 * 
	 * @param siteId
	 * @return
	 */
	public Double countDepositFishBySite(Integer siteId) throws Exception;

	/**
	 * 增加存鱼
	 * 
	 * @param depositFish
	 */
	public void saveDepositFish(DepositFish depositFish) throws Exception;

	/**
	 * 更新用户存鱼余额
	 * 
	 * @param depositFish
	 */
	public void modifyDepositFish(DepositFish depositFish) throws Exception;

	/**
	 * 查询某个用户存鱼余额
	 * 
	 * @param userId
	 * @throws Exception
	 */
	public Double getDepositesForUser(Integer userId, Integer siteId)
			throws Exception;

	/**
	 * 查询门票存鱼记录
	 * 
	 * @return
	 * @param uid
	 * @param tid
	 * @throws Exception
	 */
	public DepositFishTicketRecord getDFTR(Integer uid, Long tid)
			throws Exception;
}
