/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.PartnerEarningRecordMapper;
import cn.heipiao.api.pojo.PartnerEarningRecord;
import cn.heipiao.api.service.PartnerEarningRecordService;

/**
 * @author wzw
 * @date 2016年9月21日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class PartnerEarningRecordServiceImpl implements PartnerEarningRecordService {

	@Resource
	private PartnerEarningRecordMapper partnerEarningRecordMapper; 
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerEarningRecordService#selectByUid(java.util.Map)
	 */
	@Override
	public List<PartnerEarningRecord> selectByUid(Map<String, Object> map) {
		return partnerEarningRecordMapper.selectByUid(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerEarningRecordService#selectByUidAndFishSiteId(java.util.Map)
	 */
	@Override
	public List<PartnerEarningRecord> selectByUidAndFishSiteId(Map<String, Object> map) {
		return partnerEarningRecordMapper.selectByUidAndFishSiteId(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.PartnerEarningRecordService#selectByFishSiteIdIsNull(java.util.Map)
	 */
	@Override
	public List<PartnerEarningRecord> selectByFishSiteIdIsNull(Map<String, Object> map) {
		return partnerEarningRecordMapper.selectByFishSiteIdIsNull(map);
	}
	

	@Transactional(readOnly=false,rollbackFor = {Exception.class})
	public  void addPojo(PartnerEarningRecord pojo){
		partnerEarningRecordMapper.insertPojo(pojo);
	}
	
}
