package cn.heipiao.api.service;

import java.util.Date;
import java.util.List;
/**
 * 催钟
 * @ClassName: PclockRecordService
 * @Description: TODO
 * @author zf
 */
public interface PclockRecordService {
	/**
	 * 查询催钟次数
	 * @param tid
	 * @return
	 * @throws Exception
	 */
	Integer count(Long tid) throws Exception;
	/**
	 * 查询催钟的时间
	 * @param tid
	 * @return
	 * @throws Exception
	 */
	List<Date> getDates(Long tid) throws Exception;
	/**
	 * 添加记录
	 * @param tid
	 * @return
	 */
	Integer add(Long tid);
}
