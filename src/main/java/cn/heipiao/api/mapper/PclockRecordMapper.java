package cn.heipiao.api.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 催钟记录
 * @ClassName: PclockRecordMapper
 * @Description: TODO
 * @author zf
 */
public interface PclockRecordMapper {
	/**
	 * 统计催钟记录数
	 * @param tid
	 * @return
	 */
	Integer count(Long tid);
	/**
	 * 查询催钟日期
	 * @param tid
	 * @return
	 */
	List<Date> selectDate(Long tid);
	/**
	 * 插入一条记录
	 * @param tid
	 * @param createTime
	 * @return
	 */
	int insert(@Param("tid")Long tid,@Param("createTime")Date createTime);
}
