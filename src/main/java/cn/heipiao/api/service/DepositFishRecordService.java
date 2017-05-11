package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.DepositFishRecord;

/**
 * @author zf
 * @version 1.0
 * @description 存鱼记录service
 * @date 2016年6月29日
 */
public interface DepositFishRecordService {
	/**
	 * 查询用户在某个钓场的存鱼记录
	 * 
	 * @param userId
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public List<DepositFishRecord> getRecordsBySiteOfUser(Long userId,
			Integer siteId, Integer start, Integer size) throws Exception;

	/**
	 * 查询钓场所有用户的存鱼记录
	 * 
	 * @param siteId
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<DepositFishRecord> getRecordsBySite(Integer siteId,
			Integer start, Integer size) throws Exception;

	/**
	 * 新增存鱼记录
	 * 
	 * @param record
	 * @throws Exception
	 */
	public void saveDepositFishRecord(DepositFishRecord record)
			throws Exception;

	/**
	 * 统计存鱼数量
	 * 
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public int countRecords(Integer siteId) throws Exception;
}
