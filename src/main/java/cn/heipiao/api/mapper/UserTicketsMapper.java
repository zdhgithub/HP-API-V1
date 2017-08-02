/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.pojo.VerifyTicketRecord;

/**
 * @author wzw
 * @date 2016年7月1日
 * @version 1.0
 */
public interface UserTicketsMapper {

	/**
	 * 用户id查询票
	 * @param map
	 * @return
	 */
	List<UserTickets> selectTicketsByUid(Map<String,Object> map);
	
	/**
	 * 钓场id查询票
	 * @param map
	 * @return
	 */
	List<UserTickets> selectTicketsBySite(Map<String,Object> map);
	
	/**
	 * 插入
	 * @param pojos
	 */
	void insertBatch(List<UserTickets> pojos);


	/**
	 * 通过票id查找票
	 * @param tid
	 * @return
	 */
	UserTickets selectTicketsByTid(Long tid);
	
	/**
	 * 通过票id查找票 加锁
	 * 
	 * @param tid
	 * @return
	 */
	UserTickets selectTicketsAsLockByTid(Long tid);


	/**
	 * @param ut
	 */
	void updateStatus(UserTickets ut);
	
	/**
	 * 
	 * @param ut
	 */
	void updateStatusAndUseTime(UserTickets ut);

	/**
	 * @param fishPondId
	 * @return
	 */
	Integer isExists(Integer f);

	/**
	 * @param map
	 * @return
	 */
	UserTickets selectUniqueTicket(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<UserTickets> selectTicketByFishSiteId(Map<String, Object> map);

	/**
	 * 获取已消费过的用户唯一uid
	 * @param siteId
	 * @return
	 */
	Set<Long> selectUidsBySiteId(Integer siteId);
	
	/**
	 * 记录核票人记录
	 * @param utt
	 */
	void insertVerifyTicketRecord(VerifyTicketRecord utt);
	
}
