/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerEarningRecord;

/**
 * @author wzw
 * @date 2016年9月21日
 * @version 1.0
 */
public interface PartnerEarningRecordMapper {

	List<PartnerEarningRecord> selectByUid(Map<String ,Object> map);
	
	
	List<PartnerEarningRecord> selectByUidAndFishSiteId(Map<String ,Object> map);
	
	
	void insertPojo(PartnerEarningRecord pojo);


	/**
	 * @param map
	 * @return
	 */
	List<PartnerEarningRecord> selectByFishSiteIdIsNull(Map<String, Object> map);
	
}
