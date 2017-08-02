package cn.heipiao.api.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.PersonImpression;

public interface BrandService {
	/**
	 * 获取用户的印象列表
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public List<PersonImpression> getPersonImpression(Integer uid,Long shopId)
			throws Exception;

	/**
	 * 添加或取消印象
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Integer startOrForbid(JSONObject json) throws Exception;

	/**
	 * 印象评价
	 * 
	 * @param uid
	 * @param dictId
	 * @return
	 * @throws Exception
	 */
	public Integer plus(Integer uid,Long shopId, String label) throws Exception;

	/**
	 * 领域列表
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public List<PersonImpression> getFields(Long shopId) throws Exception;

	/**
	 * 添加领域
	 * 
	 * @param uid
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public Integer addField(Long shopId, String label) throws Exception;

	/**
	 * 删除领域
	 * 
	 * @param uid
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public Integer rmField(Long shopId, String label) throws Exception;
}
