package cn.heipiao.api.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.mapper.PclockRecordMapper;
import cn.heipiao.api.service.PclockRecordService;
import cn.heipiao.api.utils.ExDateUtils;
@Service
public class PclockRecordServiceImpl implements PclockRecordService{
	@Resource
	private PclockRecordMapper pclockRecordMapper;
	@Override
	public Integer count(Long tid) throws Exception {
		return pclockRecordMapper.count(tid);
	}

	@Override
	public List<Date> getDates(Long tid) throws Exception {
		return pclockRecordMapper.selectDate(tid);
	}

	@Override
	public Integer add(Long tid) {
		this.pclockRecordMapper.insert(tid, ExDateUtils.getDate());
		return null;
	}

}
