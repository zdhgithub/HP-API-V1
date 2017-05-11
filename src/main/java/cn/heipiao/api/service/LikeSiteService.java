/**
 * 
 */
package cn.heipiao.api.service;

import cn.heipiao.api.pojo.LikeSite;

/**
 * @author wzw
 * @date 2016年11月29日
 * @version 1.0
 */
public interface LikeSiteService {

	
	int likeSite(LikeSite pojo);
	
	void unLikeSite(LikeSite pojo);
	
	int countSite(Integer siteId);

	/**
	 * @param siteId
	 * @param uid
	 * @return
	 */
	LikeSite getLikeUser(Integer siteId, Long uid);
}
