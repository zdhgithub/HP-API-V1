package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerOver;


public interface PartnerOverMapper {
	/**
	 * 通过商家id查询	
	 */
	List<PartnerOver> selectByBid(Map<String,Object> params);
	/**
	 * 添加
	 */
	int insert(PartnerOver partnerOver);
	/**
	 * 更新
	 */
	int updateByBid(PartnerOver partnerOver);
	/**
	 * 删除
	 */
	int deleteById(int id);
}
