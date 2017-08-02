package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.TicketCode;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年8月8日
 */
public interface TicketCodeMapper extends BaseMapper<TicketCode> {
	/**
	 * 根据ID删除code
	 * @param tid
	 */
	void deleteByTid(Long tid);

	/**
	 * 通过票ID查找票编码
	 * 
	 * @return
	 */
	public TicketCode selectByTicketId(Long tid);

}
