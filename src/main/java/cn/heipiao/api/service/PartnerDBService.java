package cn.heipiao.api.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.PartnerDB;

public interface PartnerDBService {
	/**
	 * 查询合伙人资料库列表
	 * @param start
	 * @param size
	 * @return
	 * @throws Exception
	 */
	List<PartnerDB> getList(Integer start, Integer size) throws Exception;
	/**
	 * 新增资料
	 * @param json
	 * @return
	 * @throws Exception
	 */
	Integer adDB(JSONObject json) throws Exception;
	/**
	 * 删除资料
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Integer rmDB(Integer id) throws Exception;
	/**
	 * 更新资料
	 * @param json
	 * @return
	 * @throws Exception
	 */
	Integer upDB(JSONObject json) throws Exception;
}
