/**
 * 
 */
package cn.heipiao.api.mapper;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.LikeSite;

/**
 * @author wzw
 * @date 2016年11月29日
 * @version 1.0
 */
public interface LikeSiteMapper {
	
	int countSite(Integer siteId);
	
	void insertPojo(LikeSite pojo);
	
	void deletePojo(@Param("siteId")Integer siteId,@Param("uid")Long uid);

	/**
	 * @param siteId
	 * @param uid
	 * @return
	 */
	LikeSite selectIsLike(@Param("siteId")Integer siteId,@Param("uid")Long uid);
}
