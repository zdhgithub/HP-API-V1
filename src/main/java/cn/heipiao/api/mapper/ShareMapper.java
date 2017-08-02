package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Share;

/**
 * @author z
 * @version 1.0
 * @description
 * @date 2016年6月13日
 */
public interface ShareMapper extends BaseMapper<Share> {
	
	/**
	 * 统计渔获或是分享的总数
	 * 
	 * @param type
	 * @return
	 */
	public Integer countShares(@Param("type") String type,
			@Param("siteId") Integer siteId);

	/**
	 * 分享或是鱼获浏览数+1
	 * 
	 * @param sid
	 */
	public Integer addClickNum(Integer sid);
	
	public List<Share> selectAll(Map<String,Object> params);
}
