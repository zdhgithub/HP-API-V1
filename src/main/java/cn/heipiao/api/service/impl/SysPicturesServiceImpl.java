package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ApiConstant.SysPicturesType;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.SysPicturesMapper;
import cn.heipiao.api.mapper.MasterMapper;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Master;
import cn.heipiao.api.pojo.SysPictures;
import cn.heipiao.api.resources.SysPicturesResource;
import cn.heipiao.api.service.SysPicturesService;
import cn.heipiao.api.utils.RandomNumberUtils;

/**
 * @author asdf3070@163.com
 * @date 2016年11月22日
 * @version 2.2
 */
@Service
@Transactional(readOnly = true)
public class SysPicturesServiceImpl implements SysPicturesService {

	@Resource
	private SysPicturesMapper sysPicturesMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private MasterMapper masterMapper;
	
	@Override
	public List<SysPictures> queryList(Map<String, Object> map) {
		return sysPicturesMapper.queryList(map);
	}

	@Override
	public int queryListCount(Map<String, Object> map) {
		return sysPicturesMapper.queryListCount(map);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int add(SysPictures pojo) {
		return sysPicturesMapper.insertNew(pojo);
	}

	@Override
	public SysPictures getById(String id) {
		return sysPicturesMapper.selectById(id);
	}

	@Override
	@Transactional
	public int updateById(SysPictures pojo) {
		return sysPicturesMapper.updateById(pojo);
	}

	@Override
	@Transactional
	public int deleteById(SysPictures pojo) {
		return sysPicturesMapper.deleteById(pojo);
	}
	
	private String getRandomImg(int type){
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("startItem", 0);
		map.put("pagesize", 1000);
		List<SysPictures> imgs = sysPicturesMapper.queryList(map);
		if(imgs.size() == 1){
			return SysPicturesResource.getPicturePrefix + imgs.get(0).getId();
		}else if(imgs.size() > 1){
			int index = RandomNumberUtils.getRangeRandom(0, imgs.size()-1);
			return SysPicturesResource.getPicturePrefix + imgs.get(index).getId();
		}else{
			return null;
		}
	}

	@Override
	@Transactional
	public String updateShopListImg(Long id) {
		String imgId = getRandomImg(SysPicturesType.SHOP_LIST);
		if(imgId != null){
			Map<String, Object> map = new HashMap<>();
			map.put("id", id);
			FishShop fishShop = fishShopMapper.selectFishShopById(map);
			if(fishShop != null){
				fishShop = new FishShop();
				fishShop.setId(id);
				fishShop.setMainImgNone(imgId);
				fishShopMapper.updateFishShopById(fishShop);
			}
		}
		return imgId;
	}

	@Override
	@Transactional
	public String updateSiteListImg(Long id) {
		String imgId = getRandomImg(SysPicturesType.SITE_LIST);
		if(imgId != null){
			FishSite fishSite = fishSiteMapper.selectById(id.intValue());
			if(fishSite != null){
				fishSite = new FishSite();
				fishSite.setFishSiteId(id.intValue());
				fishSite.setMainImgNone(imgId);
				fishSiteMapper.updatePojo(fishSite);
			}
		}
		return imgId;
	}

	@Override
	@Transactional
	public void updateTopImg(Integer type, Long id, String imgId) {
		if(type == SysPicturesType.SHOP_TOP){
			FishShop fishShop = new FishShop();
			fishShop.setId(id);
			fishShop.setMainTopImg(imgId);
			fishShopMapper.updateFishShopById(fishShop);
		}else if(type == SysPicturesType.SITE_TOP){
			FishSite fishSite = new FishSite();
			fishSite.setFishSiteId(id.intValue());
			fishSite.setMainTopImg(imgId);
			fishSiteMapper.updatePojo(fishSite);
		}else if(type == SysPicturesType.MASTER_TOP){
			Master master = masterMapper.selectByUid(id);
			if(master.getTopImg() == null){
				master = new Master();
				master.setUid(id);
				master.setTopImg(imgId);
				masterMapper.insertMaster(master);
			}else{
				master.setUid(id);
				master.setTopImg(imgId);
				masterMapper.updateById(master);
			}
		}
	}

	@Override
	public List<SysPictures> updateListImgAll(Integer type) {
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		List<SysPictures> list = sysPicturesMapper.selectMainIsNoneAll(map);
		return list;
	}

	@Override
	@Transactional
	public void setListEmpty() {
		sysPicturesMapper.setShopListEmpty();
		sysPicturesMapper.setSiteListEmpty();
	}

}
