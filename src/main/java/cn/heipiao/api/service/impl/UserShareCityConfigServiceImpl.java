/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.UserShareCityConfigMapper;
import cn.heipiao.api.mapper.UserShareGlobalConfigMapper;
import cn.heipiao.api.pojo.UserShareCityConfig;
import cn.heipiao.api.service.UserShareCityConfigService;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserShareCityConfigServiceImpl implements UserShareCityConfigService {

	@Resource
	private UserShareGlobalConfigMapper userShareGlobalConfigMapper;
	
	@Resource
	private UserShareCityConfigMapper userShareCityConfigMapper;
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareCityConfigService#setShareStatus(java.lang.Boolean)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void setShareStatus(Boolean status) {
		if(userShareGlobalConfigMapper.selectStatus() == null){
			userShareGlobalConfigMapper.insertPojo(status);
		}else{
			userShareGlobalConfigMapper.updatePojo(status);
		}
	}


	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareCityConfigService#getShareStatus()
	 */
	@Override
	public Boolean getShareStatus() {
		return userShareGlobalConfigMapper.selectStatus();
	}


	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareCityConfigService#modifyShareCityStatus(java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void modifyShareCity(UserShareCityConfig pojo) {
		UserShareCityConfig p = userShareCityConfigMapper.selectByCityId(pojo.getCityId());
		if(p != null){
			p.setStatus(pojo.getStatus());
			p.setAmount(pojo.getAmount());
			if(pojo.getDesc()  != null){
				p.setDesc(pojo.getDesc());
			}
			p.setLimit(pojo.getLimit());
			userShareCityConfigMapper.updatePojo(p);
		}
	}


	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareCityConfigService#addShareCityStatus(java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addShareCity(UserShareCityConfig pojo) {
		userShareCityConfigMapper.insertPojo(pojo);
	}


	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareCityConfigService#listShareCity()
	 */
	@Override
	public List<UserShareCityConfig> listShareCity(Map<String, Object> m) {
		return userShareCityConfigMapper.selectAll(m);
	}


	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void deleteShareCity(Integer cityId) {
		userShareCityConfigMapper.deleteShareCity(cityId);
	}


	@Override
	public int countShareCity() {
		return userShareCityConfigMapper.countShareCity();
	}

}
