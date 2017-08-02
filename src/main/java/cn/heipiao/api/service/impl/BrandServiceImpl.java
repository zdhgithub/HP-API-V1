package cn.heipiao.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ImpressionRecordMapper;
import cn.heipiao.api.mapper.PersonImpressionMapper;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.ImpressionRecord;
import cn.heipiao.api.pojo.PersonImpression;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.BrandService;
import cn.heipiao.api.service.ConfigService;
import cn.heipiao.api.service.FishShopService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: BrandServiceImpl
 * @Description: TODO
 * @author z
 * @date 2016年10月11日
 */
@Service
public class BrandServiceImpl implements BrandService {
	@Resource
	private PersonImpressionMapper<PersonImpression> PersonImpressionMapper;

	@Resource
	private ConfigService configService;
	
	@Resource
	private FishShopService fishShopService;

	@Resource
	private ImpressionRecordMapper<ImpressionRecord> impressionRecordMapper;

	private static final String IMPRESSION = "impression";
	private static final String FIELD = "field";

	@Override
	public List<PersonImpression> getPersonImpression(Integer uid,Long shopId)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("shopId", shopId);
		List<PersonImpression> userList = this.PersonImpressionMapper
				.selectList(params);
		List<DictConfig> allList = this.configService
				.queryConfigByType(IMPRESSION);//查询字典表的印象
		if (allList == null || allList.size() <= 0) {
			return null;
		}
		List<String> userLabelList = new ArrayList<String>();
		List<PersonImpression> result = new ArrayList<PersonImpression>();
		for (PersonImpression p : userList) {
			userLabelList.add(p.getLabel());
		}
		for (DictConfig d : allList) {
			if (!userLabelList.contains(d.getValue())) {
				PersonImpression pi = new PersonImpression();
//				pi.setUid(uid);
				pi.setNum(0);
				pi.setLabel(d.getValue());
				pi.setStatus(0);
				result.add(pi);
			}else {
				PersonImpression pi = new PersonImpression();
				for(PersonImpression p : userList) {
					if(p.getLabel().equals(d.getValue())) {
						pi.setNum(p.getNum());
						pi.setLabel(p.getLabel());
						pi.setStatus(p.getStatus());
						result.add(pi);
						break;
					}
				}
				
			}
		}
		return result;
	}

	@Override
	@Transactional
	public Integer startOrForbid(JSONObject json) throws Exception {
		Long shopId = json.getLong("shopId");
		String label = json.getString("label");
		Integer type = json.getInteger("type");
		FishShop fs = this.fishShopService.getFishShopById(shopId);
		if (fs == null) {
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if (fs.getUid() == null) {
			return Status.FISH_SHOP_NOT_NAME_PASS;
		}
		if (type == 1) {// 添加印象
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("uid", fs.getUid());
//			param.put("shopId", shopId);
//			param.put("status", 1);
//			List<PersonImpression> list = this.PersonImpressionMapper
//					.selectList(param);
//			if (list.size() >= 9) {
//				return Status.IMPRESSION_BEYOND_LIMITS;
//			}
			PersonImpression imp = this.PersonImpressionMapper.selectOne(fs.getUid(),shopId,
					label);
			if (imp == null) {
				PersonImpression pojo = new PersonImpression(fs.getUid(),shopId, label, 1);
				this.PersonImpressionMapper.insert(pojo);
			}else {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("targetId", fs.getUid());
				params.put("shopId", shopId);
				params.put("label", label);
				params.put("status", 1);
				this.PersonImpressionMapper.update(params);
			}
		} else {// 取消印象
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("targetId", fs.getUid());
			params.put("shopId", shopId);
			params.put("label", label);
			params.put("status", 0);
			this.PersonImpressionMapper.update(params);
		}
		return Status.success;
	}

	@Override
	@Transactional
	public Integer plus(Integer uid, Long shopId, String label)
			throws Exception {
		FishShop fs = this.fishShopService.getFishShopById(shopId);
		if (fs == null) {
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if (fs.getUid() == null) {
			return Status.FISH_SHOP_NOT_NAME_PASS;
		}
		if (PersonImpressionMapper.selectOne(fs.getUid(),fs.getId(), label) == null) {
			return Status.IMPRESSION_IS_NOT_EXIST;
		}
		if (impressionRecordMapper.selectOne(uid, fs.getUid(),fs.getId(), label) != null) {
			return Status.COMMENT_EXIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("targetId", fs.getUid());
		params.put("shopId", fs.getId());
		params.put("label", label);
		params.put("num", "num");
		PersonImpressionMapper.update(params);
		ImpressionRecord ip = new ImpressionRecord();
		ip.setLabel(label);
		ip.setTargetId(fs.getUid());
		ip.setShopId(fs.getId());
		ip.setUid(uid);
		impressionRecordMapper.insert(ip);
		return Status.success;
	}

	@Override
	public List<PersonImpression> getFields(Long shopId) throws Exception {
		FishShop fs = this.fishShopService.getFishShopById(shopId);
		List<DictConfig> allList = this.configService.queryConfigByType(FIELD);
		if (allList == null || allList.size() <= 0) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", fs.getUid());
		params.put("shopId", fs.getId());
		List<PersonImpression> userList = this.PersonImpressionMapper
				.selectListField(params);
		List<String> userLabelList = new ArrayList<String>();
		List<PersonImpression> result = new ArrayList<PersonImpression>();
		for (PersonImpression p : userList) {
			userLabelList.add(p.getLabel());
		}
		for (DictConfig d : allList) {
			if (!userLabelList.contains(d.getValue())) {
				PersonImpression pi = new PersonImpression();
				pi.setLabel(d.getValue());
				pi.setStatus(0);
				result.add(pi);
			}else {
				PersonImpression pi = new PersonImpression();
				for(PersonImpression p : userList) {
					if(p.getLabel().equals(d.getValue())) {
						pi.setLabel(p.getLabel());
						pi.setStatus(1);
						result.add(pi);
						break;
					}
				}
				
			}
		}
		return result;
	}

	@Override
	@Transactional
	public Integer addField(Long shopId, String label) throws Exception {
		FishShop fs = this.fishShopService.getFishShopById(shopId);
		if (fs == null) {
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if (fs.getUid() == null) {
			return Status.FISH_SHOP_NOT_NAME_PASS;
		}
//		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("uid", fs.getUid());
//		param.put("shopId", shopId);
//		List<PersonImpression> list = this.PersonImpressionMapper
//				.selectListField(param);
//		if (list.size() >= 9) {
//			return Status.IMPRESSION_BEYOND_LIMITS;
//		}
		PersonImpression p = this.PersonImpressionMapper.selectOneField(fs.getUid(),shopId,
				label);
		if (p == null) {
			PersonImpression pojo = new PersonImpression();
			pojo.setUid(fs.getUid());
			pojo.setShopId(fs.getId());
			pojo.setLabel(label);
			this.PersonImpressionMapper.insertField(pojo);
		}
		return Status.success;
	}

	@Override
	@Transactional
	public Integer rmField(Long shopId, String label) throws Exception {
		FishShop fs = this.fishShopService.getFishShopById(shopId);
		if (fs == null) {
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if (fs.getUid() == null) {
			return Status.FISH_SHOP_NOT_NAME_PASS;
		}
		this.PersonImpressionMapper.deleteField(fs.getUid(), shopId, label);
		return Status.success;
	}

}
