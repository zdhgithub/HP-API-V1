package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.PartnerDBMapper;
import cn.heipiao.api.pojo.PartnerDB;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.PartnerDBService;
import cn.heipiao.api.utils.ExDateUtils;

import com.alibaba.fastjson.JSONObject;

@Service
public class PartnerDBServiceImpl implements PartnerDBService {
	@Resource
	private PartnerDBMapper<PartnerDB> partnerDBMapper;

	@Override
	public List<PartnerDB> getList(Integer start, Integer size)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("size", size);
		return this.partnerDBMapper.selectList(params);
	}

	@Override
	@Transactional
	public Integer adDB(JSONObject json) throws Exception {
		PartnerDB pojo = JSONObject.toJavaObject(json, PartnerDB.class);
		pojo.setCreateTime(ExDateUtils.getDate());
		pojo.setUpdateTime(ExDateUtils.getDate());
		this.partnerDBMapper.insert(pojo);
		return pojo.getId();
	}

	@Override
	@Transactional
	public Integer rmDB(Integer id) throws Exception {
		this.partnerDBMapper.delete(id);
		return Status.success;
	}

	@Override
	@Transactional
	public Integer upDB(JSONObject json) throws Exception {
		PartnerDB pojo = JSONObject.toJavaObject(json, PartnerDB.class);
		pojo.setUpdateTime(ExDateUtils.getDate());
		this.partnerDBMapper.update(pojo);
		return Status.success;
	}

}
