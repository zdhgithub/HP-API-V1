/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.RegionMapper;
import cn.heipiao.api.mapper.SysRegionMapper;
import cn.heipiao.api.pojo.MenuRegion;
import cn.heipiao.api.pojo.MenuRegion.City;
import cn.heipiao.api.pojo.Region;
import cn.heipiao.api.redis.RedisConfig;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.CacheService;
import cn.heipiao.api.service.RegionService;

/**
 * @author wzw
 * @date 2016年6月22日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class RegionServiceImpl implements RegionService {

	@Resource
	private RegionMapper regionMapper;
	
	@Resource
	private CacheService cacheService;

	@Resource
	private SysRegionMapper sysRegionMapper;
	
	public static final String regoinKey = "region";

	public static final String REGOIN_PARTNER_RECRUIT = "region.parnet";
	
	public static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.RegionService#selectAll()
	 */
	@Override
//	@Cacheable(value = { "dataCache" }, key = "#root.methodName")
	public List<Region> selectAll() {
		List<Region> list = null;
		String regionStr = cacheService.get(regoinKey, RedisConfig.regoinDB);
		if(regionStr == null){
			list = regionMapper.selectAll();
			regionStr = JSONObject.toJSONString(list);
			cacheService.put(regoinKey, regionStr, RedisConfig.regoinDB);
		}else{
			list = JSONObject.parseArray(regionStr,Region.class);
		}
		return list;
	}
	/**
	 *获取招募的城市
	 */
	@Override
	public List<Region> selectRecruitAll() {
		List<Region> list = null;
		int regionRecruit = 1;
 		String regionStr = cacheService.get(RedisConfig.PARTNER_CITY, RedisConfig.GLOBAL_CONFIG);
		
		if(regionStr == null){
			list = regionMapper.selectRecruitAll(regionRecruit);
			if(list!=null&&list.size()>0){
				regionStr = JSONObject.toJSONString(list);	
				cacheService.put(RedisConfig.PARTNER_CITY, regionStr, RedisConfig.GLOBAL_CONFIG);
			}		
		}else{
			list = JSONObject.parseArray(regionStr,Region.class);
		}
		return list;
	}

//	@Cacheable(value = { "dataCache" }, key = "#p0")
	public List<MenuRegion> selectAllAndCount(Integer type) {
		List<MenuRegion> list = null;
		String menuStr = cacheService.get(type + "", RedisConfig.regoinDB);
		if(menuStr == null){
			list = setMenuRegions(type);
		}else{
			list = JSONObject.parseArray(menuStr,MenuRegion.class);
		}
		return list; 

	}

	/**
	 * @return
	 */
	private List<MenuRegion> setMenuRegions(Integer type) {
		List<Region> provinces = null;
		List<Region> citys = null;
		if(type == ApiConstant.fishEntityType.TYPE_MENU_REGIONS_SITE){
			provinces = regionMapper.selectAllAndFishSiteCount(1);
			citys = regionMapper.selectAllAndFishSiteCount(2);
		}else if(type == ApiConstant.fishEntityType.TYPE_MENU_REGIONS_SHOP){
			provinces = regionMapper.selectAllAndFishShopCount(1);
			citys = regionMapper.selectAllAndFishShopCount(2);
		}else{
			return null;
		}
		List<MenuRegion> menuRegions = new ArrayList<MenuRegion>();

		Iterator<Region> itp = provinces.iterator();
		while (itp.hasNext()) {
			Region pro = itp.next();
			MenuRegion mr = new MenuRegion();
			mr.setCount(pro.getCount() == null ? 0 : pro.getCount());
			mr.setProvince(pro.getRegionName());
			mr.setRid(pro.getRegionNum());
			mr.setCitys(new ArrayList<City>());
			menuRegions.add(mr);
		}

		for (MenuRegion mr : menuRegions) {
			Iterator<Region> itc = citys.iterator();
			while (itc.hasNext()) {
				Region city = itc.next();
				// 省份id对应城市父id
				if (mr.getRid().equals(city.getPid())) {
					City c = new City();
					c.setCity(city.getRegionName());
					c.setCount(city.getCount() == null ? 0 : city.getCount());
					c.setRid(city.getRegionNum());
					mr.getCitys().add(c);
					itc.remove();
				}

			}
		}

		return menuRegions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.RegionService#selectCitys()
	 */
	@Override
	public List<Region> selectCitys() {
		return regionMapper.selectCitys();
	}

	@CachePut(value = { "dataCache" }, key = "#root.targetClass")
	public List<MenuRegion> modifyFishSiteCount() {
		return setMenuRegions(ApiConstant.fishEntityType.TYPE_MENU_REGIONS_SITE);
	}

	@Override
	@Transactional
	public Integer appointOpenCity(Integer cityId) throws Exception {
		sysRegionMapper.appointOpenCity(cityId);
		return null;
	}

	@Override
	public List<Region> getRegionStepByStep() {
		List<Region> province = sysRegionMapper.selectListByPid(0);
		if (province != null && province.size()>0) {
			for(Region p: province) {
				List<Region> city = sysRegionMapper.selectListByPid(p.getRegionNum());
				if(city !=null && city.size()>0) {
					p.setSubcollection(city);
					for(Region c : city) {
						List<Region> region = sysRegionMapper.selectListByPid(c.getRegionNum());
						c.setSubcollection(region);
					}
				}
			}
		}
		return province;
	}
	@Override
	@Transactional(readOnly = false)
	public int upDateRecruit(Integer regionId,Integer status) {
		regionMapper.updateRecruit(regionId,status);
		return Status.success;
	}
	@Override
	public List<Region> selectOutside() {
		List<Region> list=regionMapper.selectOutsideRegion();
		return list;
	}
	@Override
	@Transactional(readOnly = false)
	public int updateOutsideRecruit(Integer regionId) {
		regionMapper.updateOutsideRecruit(regionId);
		return Status.success;
	}

}
