package cn.heipiao.api.service;

import java.util.Map;

import cn.heipiao.api.pojo.TicketCode;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年8月8日
 */
public interface TicketCodeService {
	/**
	 * 生成票的数字码
	 * 
	 * @param code
	 * @param ticketId
	 * @return
	 * @throws Exception
	 */
	public int generateTicketCode(Long ticketId) throws Exception;

	/**
	 * 删除票数字码
	 * 
	 * @param code
	 * @throws Exception
	 */
	public void deleteTicketCode(Integer code) throws Exception;

	/**
	 * 根据code查找核票信息
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> confirmTicketCode(Integer code) throws Exception;

	/**
	 * 查找票ID通过code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public TicketCode getTicketCodeByCode(Integer code) throws Exception;

}
