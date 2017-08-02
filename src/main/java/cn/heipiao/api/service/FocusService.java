/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Focus;

/**
 * @author wzw
 * @date 2016年6月13日
 * @version 1.0
 */
public interface FocusService {

	/**
	 * @param f
	 */
	void deleteFollow(Focus f);

	/**
	 * @param f
	 */
	void addFollow(Focus f);

	/**
	 * @param map
	 * @return
	 */
	List<FishSite> selectByFocus(Map<String, Object> map);

	/**
	 * @param uid
	 * @param fid
	 * @return
	 */
	Focus selectByUidAndFid(Long uid, Integer fid);

}
