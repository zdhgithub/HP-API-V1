package cn.heipiao.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.MasterMapper;
import cn.heipiao.api.pojo.Master;
import cn.heipiao.api.service.MasterService;

@Service
@Transactional(readOnly = true)
public class MasterServiceImpl implements MasterService {

	@Resource
	private MasterMapper masterMapper;
	
	@Override
	public Master get(Long uid) {
		return masterMapper.selectByUid(uid);
	}

}
