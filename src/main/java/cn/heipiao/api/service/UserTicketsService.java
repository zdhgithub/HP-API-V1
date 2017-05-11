/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Orders;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.pojo.UserTickets;

/**
 * @author wzw
 * @date 2016年7月1日
 * @version 1.0
 */
public interface UserTicketsService {

	/**
	 * @param map
	 * @return
	 */
	List<UserTickets> selectTicketsBySite(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<UserTickets> selectTicketsByUid(Map<String, Object> map);

	/**
	 * 
	 * @param tid
	 * @return
	 */
	UserTickets selectTicketsByTid(Long tid);
	
	/**
	 * 
	 * @param pojos
	 */
	void insertBatch(List<UserTickets> pojos);

	/**
	 * @param uid
	 * @param tid
	 * @return
	 * @throws Exception 
	 */
	int refundTickerApply(Refund r) throws Exception;
	
	/**
	 * 生成票
	 * @param order
	 */
	void paySuccessDeal(Orders order);

	/**
	 * @param tid
	 * @param extraTime 
	 * @param euid 
	 * @return 
	 * @throws Exception 
	 */
	int verifyTicket(Long tid, Integer extraTime, Long euid) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	UserTickets selectUniqueTicket(Map<String, Object> map);

	/**
	 * @param uid
	 * @param tid
	 * @throws Exception 
	 */
	void refundTicker(String refundNo) throws Exception;

	/**
	 * @param map
	 * @return
	 */
	List<UserTickets> getOrdersListBySiteId(Map<String, Object> map);
	
}
