package cn.heipiao.api.service;

import java.util.Map;

public interface MineCountsService {
	/**
	 * 我的相关统计
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getResult(Integer userId) throws Exception;
}
