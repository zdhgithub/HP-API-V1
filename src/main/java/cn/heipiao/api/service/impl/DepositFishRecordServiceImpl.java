package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.MyBatisConstant;
import cn.heipiao.api.mapper.DepositFishRecordMapper;
import cn.heipiao.api.pojo.DepositFishRecord;
import cn.heipiao.api.service.DepositFishRecordService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 存鱼记录业务实现
 * @date 2016年6月29日
 */
@Service
public class DepositFishRecordServiceImpl implements DepositFishRecordService {
	@Resource
	private DepositFishRecordMapper depositFishRecordMapper;

	@Override
	public List<DepositFishRecord> getRecordsBySiteOfUser(Long userId,
			Integer siteId, Integer start, Integer size) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("siteId", siteId);
		param.put("start", start);
		param.put("size", size);
		param.put(MyBatisConstant.ORDER_DESC, MyBatisConstant.ORDER_DESC);
		param.put(MyBatisConstant.ORDER, "d.f_create_time");
		List<DepositFishRecord> list = depositFishRecordMapper
				.selectList(param);
		return list;
	}

	@Override
	public List<DepositFishRecord> getRecordsBySite(Integer siteId,
			Integer start, Integer size) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("siteId", siteId);
		param.put("start", start);
		param.put("size", size);
		param.put(MyBatisConstant.ORDER_DESC, MyBatisConstant.ORDER_DESC);
		param.put(MyBatisConstant.ORDER, "d.f_create_time");
		List<DepositFishRecord> list = depositFishRecordMapper
				.selectList(param);
		return list;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDepositFishRecord(DepositFishRecord record)
			throws Exception {
		record.setCreateTime(ExDateUtils.getDate());
		depositFishRecordMapper.insert(record);

	}

	@Override
	public int countRecords(Integer siteId) throws Exception {
		int num = depositFishRecordMapper.countRecords(siteId);
		return num;
	}

}
