/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
public interface DistributionService {
	/**
	 * 删除配送关系
	 */
	int deleteDistribution(Map<String, Object> map);
	/**
	 * 添加配送关系
	 */
	int insertDistribution(Map<String,Object> map);
	/**
	 * 查询已支持配送的钓场
	 */
	List<FishSite> selectByShopId(Map<String,Object> map);
}
