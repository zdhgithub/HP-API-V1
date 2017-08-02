package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.DepositFishRecord;

/**
 * @author z
 * @version 1.0
 * @description 存鱼记录mapper
 * @date 2016年6月29日
 */
public interface DepositFishRecordMapper extends BaseMapper<DepositFishRecord> {
	/**
	 * 统计钓场存鱼记录数量
	 * 
	 * @param siteId
	 * @return
	 */
	public Integer countRecords(Integer siteId);
}
