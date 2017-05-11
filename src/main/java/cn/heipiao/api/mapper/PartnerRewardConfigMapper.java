package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerRewardConfig;

public interface PartnerRewardConfigMapper {
	/**
	 * 查询列表
	 */
	List<PartnerRewardConfig> selectList(Map<String,Object> map);
	/**
	 * 通过type查询
	 */
	List<PartnerRewardConfig> selectListByType(int type);
	/**
	 * 删除
	 */
	int deleteById(int id);
	/**
	 * 添加
	 */
	int insert(PartnerRewardConfig partnerRewardConfig);
	/**
	 * 通过type（类型0-渔具店1-钓场） 、regionId（区域id）、scale(钓场或渔具店类型)查询
	 */
	PartnerRewardConfig selectByRegion(Map<String,Object> map);
	/**
	 * 通过id更新
	 */
	int updateById(PartnerRewardConfig partnerRewardConfig);
}
