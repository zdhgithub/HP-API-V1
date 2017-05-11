package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Pole;

public interface PoleMapper extends BaseMapper<Pole>{
	
	int selectCountByParams(Map<String, String> params);
	
	/**
	 * 作用: 根据核票时间查询列表
	 * @param params
	 * @return
	 */
	List<Pole> selectListByTime(Map<String, Object> params);

	/**
	 * 查询看板头信息
	 * @return
	 */
	Pole selectHead(Map<String , Object> param);
	
	List<Pole> selectOverTimeUsers(Map<String , Object> param);
	
}