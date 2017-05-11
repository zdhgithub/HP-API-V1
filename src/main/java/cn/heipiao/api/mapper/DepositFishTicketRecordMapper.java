package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.DepositFishTicketRecord;

public interface DepositFishTicketRecordMapper {
	/**
	 * 通过ID查
	 * 
	 * @param uid
	 * @param tid
	 * @return
	 */
	public DepositFishTicketRecord selectById(@Param("uid") Integer uid,
			@Param("tid") Long tid);

	/**
	 * insert into
	 * 
	 * @param depositFishTicketRecord
	 * @return
	 */
	public int insert(DepositFishTicketRecord depositFishTicketRecord);
}
