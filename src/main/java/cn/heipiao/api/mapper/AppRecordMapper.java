package cn.heipiao.api.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.AppRecord;

public interface AppRecordMapper {
	/**
	 * 查记录列表
	 * @param params
	 * @return
	 */
	public List<AppRecord> selectList(Map<String, Object> params);
	/**
	 * 查询失效时间
	 * @param uid
	 * @param app
	 * @return
	 */
	public Date selectOneLoseTime(@Param("uid")Integer uid,@Param("app")String app);
	/**
	 * 保存记录
	 * @param pojo
	 * @return
	 */
	public Integer insert(AppRecord pojo);
	/**
	 * 保存失效记录
	 * @param uid
	 * @param time
	 * @param app
	 * @return
	 */
	public Integer insertOne(@Param("uid")Integer uid,@Param("time")Date time,@Param("app")String app);
	/**
	 * 更新失效时间
	 * @param uid
	 * @param time
	 * @param app
	 * @return
	 */
	public Integer updateOne(@Param("uid")Integer uid,@Param("time")Date time,@Param("app")String app);
	
}
