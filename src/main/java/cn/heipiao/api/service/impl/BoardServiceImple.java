package cn.heipiao.api.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.MyBatisConstant;
import cn.heipiao.api.mapper.PoleMapper;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.Page;
import cn.heipiao.api.pojo.Pole;
import cn.heipiao.api.service.BoardService;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author chenwenye
 * @since 2016-7-4  heipiao1.0
 * @see BoardService
 */
@Service
public class BoardServiceImple implements BoardService{

	/**
	 * PoleMapper
	 */
	@Resource
	private PoleMapper poleMapper;
	@Resource
	private ConfigService configService;
	
	@Override
	public boolean isExist(String id) {
		Map<String, String> params = new LinkedMap<String, String>();
		params.put("id", id);
		params.put("isLeave", EXIST);
		return poleMapper.selectList(params).size() > 0;
	}

	@Override
	public boolean beginPole(String id,String beginType) {
		Map<String , Object> params = new LinkedMap<String , Object>();
		params.put("id", id);
		
		if( NORMAL_BEGIN_TYPE.equals(beginType) ){	//立刻开杆
			params.put("begin",  ExDateUtils.getDate());
			return this.poleMapper.updateById(params) > 0;
		}
		
		if( THIRTY_MIN_AFTER_BEGIN_TYPE.equals(beginType) ){	//半小时后开杆
			Calendar calendar = ExDateUtils.getCalendar();
			calendar.add(Calendar.MINUTE, 30);
			params.put("begin",  calendar.getTime());
			return this.poleMapper.updateById(params) > 0;
		}
		return false;
		
	}

	@Override
	public boolean leave(String id) {
		Map<String , String> params = new LinkedMap<String , String>();
		params.put("id", id);
		params.put("isLeave", NOT_EXIST);
		return this.poleMapper.updateById(params) > 0;
	}

	@Override
	@Transactional
	public List<Pole> findPeople(Date begin, Date end, String fishSitsId, String isLeave, Page page) throws Exception {
		Map<String, Object> params = new LinkedMap<String , Object>();
		params.put("fishSitsId", fishSitsId);
		params.put("begin", begin);
		params.put("end", end);
		params.put("isLeave", isLeave);
		params.put(MyBatisConstant.PARAM_START, page.getStart());
		params.put(MyBatisConstant.PARAM_SIZE, page.getSize());
		params.put("now", ExDateUtils.getDate());
		List<Pole> poles = this.poleMapper.selectListByTime(params); 
		List<DictConfig> overtime = this.configService.queryConfigByType("overtime");
		String value = "1";
		if(overtime.size()>0) {
			value = overtime.get(0).getValue();
		}
		Iterator<Pole> it = poles.iterator();
		while(it.hasNext()){
			Pole p = it.next();
			if(Math.abs(p.getEnd().getTime()-ExDateUtils.getDate().getTime()) > 1000 * 60 * 60 * Integer.valueOf(value)) {
				it.remove();
				p.setIsLeave(BoardService.NOT_EXIST);
				poleMapper.updateById(p);
			}
		}
		
//		for(Pole p: poles) {
//			if(Math.abs(p.getEnd().getTime()-ExDateUtils.getDate().getTime()) > 1000 * 60 * 60 * Integer.valueOf(value)) {
//				poles.remove(p);
//				p.setIsLeave(BoardService.NOT_EXIST);
//				poleMapper.updateById(p);
//			}
//		} 
		return poles;
	}

	@Override
	public boolean update(Pole pole) {
		return this.poleMapper.updateById(pole) > 0;
	}

	@Override
	public boolean addTime(String id, Date time) {
		Map<String , Object> params = new LinkedMap<String , Object>();
		params.put("id", id);
		params.put("end", time);
		return this.poleMapper.updateById(params) > 0;
	}

	@Override
	public Pole findHead(String finshSiteId) {
		Map<String , Object> param = new LinkedMap<String, Object>();
		
		Calendar c = ExDateUtils.getCalendar();
		param.put("begin", ExDateUtils.getCurrentMin(c));
		param.put("end", ExDateUtils.getCurrentMax(c));
		param.put("now", ExDateUtils.getDate());
		param.put("fishSitsId", finshSiteId);
		return poleMapper.selectHead(param);
	}

	@Override
	public Pole getById(Long id) {
		return this.poleMapper.selectById(id);
	}
	
}
