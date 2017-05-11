/**
 * 
 */
package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;

/**
 * @author wzw
 * @date 2016年6月29日
 * @version 1.0
 */
public interface AccountRecordMapper {

	List<AccountRecord> selectByUid(Map<String, Object> map);

	void insertPojo(AccountRecord pojo);

	public int countRecords(Integer uid);

	public List<AccountExt> selectList(Map<String, Object> param);

	public int countAccountExts();

	public List<AccountExtSite> selectListExt(Map<String, Object> param);

	public int countAccountExtSites(@Param("regionId")Integer regionId);
	
	public List<AccountRecord> selectRecordBySite(Map<String, Object> map) ;

}
