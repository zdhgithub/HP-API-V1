package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.UserOpLog;
/**
 * 
 * @ClassName: UserOpLogMapper
 * @Description: TODO
 * @author zf
 */
public interface UserOpLogMapper {
	/**
	 * 查询user操作记录列表
	 * @param map
	 * @return
	 */
	public List<UserOpLog> selectList(Map<String,Object> map);
	/**
	 * 插入一条操作记录
	 * @param javabean
	 * @return
	 */
	public Integer insert(UserOpLog javabean);
	
}
