package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.SiteCouponsRecord;

public interface SiteCouponsRecordMapper {

	boolean isGet(@Param("id")Long id,@Param("uid")Long uid);
	
	
	void insertBatch(List<SiteCouponsRecord> pojos);
	
	
	void insertPojo(SiteCouponsRecord pojo);


	/**
	 * 查询在钓场消费过的用户记录
	 * @param siteId
	 * @return
	 */
	List<SiteCouponsRecord> selectConsumeUserBySiteId(Integer siteId);


	/**
	 * 关注钓场的钓场券用户记录
	 * @param siteId
	 * @return
	 */
	List<SiteCouponsRecord> selectFocusSiteBySiteId(Integer siteId);


	/**
	 * @param map
	 * @return
	 */
	List<SiteCouponsRecord> selectList(Map<String, Object> map);


	/**
	 * @param cid
	 * @return
	 */
	SiteCouponsRecord selectByCid(Long cid);
	
}
