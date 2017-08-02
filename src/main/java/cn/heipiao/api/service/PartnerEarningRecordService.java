/**
 * 
 */
package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerEarningRecord;

/**
 * @author wzw
 * @date 2016年9月21日
 * @version 1.0
 */
public interface PartnerEarningRecordService {


	List<PartnerEarningRecord> selectByUid(Map<String ,Object> map);
	
	
	List<PartnerEarningRecord> selectByUidAndFishSiteId(Map<String ,Object> map);


	/**
	 * @param map
	 * @return
	 */
	List<PartnerEarningRecord> selectByFishSiteIdIsNull(Map<String, Object> map);
	
	
	void addPojo(PartnerEarningRecord pojo);
	
}
