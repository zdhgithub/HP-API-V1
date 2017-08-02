/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Focus;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
public interface FocusMapper {

	Integer countFocus(Integer uid);
	/**
	 * @param f
	 */
	void insertPojo(Focus f);

	/**
	 * @param f
	 */
	void deletePojo(Focus f);

	/**
	 * @param uid
	 * @param fid
	 * @return
	 */
	Focus selectByUidAndFid(@Param("uid") Long uid, @Param("fid")Integer fid);

	/**
	 * 查询关注钓场的用户uid
	 * @param siteId
	 * @return
	 */
	Set<Long> selectUidsBySiteId(Integer siteId);

	
}
