package cn.heipiao.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.ClearThreadConstant;
import cn.heipiao.api.mapper.DictMapper;
import cn.heipiao.api.mapper.FishPondMapper;
import cn.heipiao.api.mapper.GoodsMapper;
import cn.heipiao.api.pojo.DictConfig;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.Goods;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.GoodsService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author zf
 * @version 1.0
 * @description 商品servicImpl
 * @date 2016年6月28日
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	private static final Logger log = LoggerFactory
			.getLogger(GoodsServiceImpl.class);

	@Resource
	private GoodsMapper goodsMapper;

	@Resource
	private DictMapper dictMapper;
	
	@Resource
	private FishPondMapper fishPondMapper;

	@Override
	@Transactional(readOnly = false)
	public int save(Goods good) throws Exception {
		boolean ispass = isPass(good);
		if(good.getCategory().intValue() == 1){
			FishPond fp = fishPondMapper.selectByPondId(good.getpId().longValue());
			if(fp == null)
				return Status.fish_pond_not_exists;
		}
		if (!ispass) {
			return Status.INFO_NOT_COMPLETE;
		}
		if (good.getAmount() < 0 || good.getDiscountPrice() < 0 || good.getHourLong() < 0) {
			return Status.goods_param_error;
		}
		good.setCreateTime(ExDateUtils.getDate());
		good.setUpdateTime(ExDateUtils.getDate());
		goodsMapper.insert(good);
		return Status.success;
	}

	@Override
	public List<Goods> queryGoods(Map<String, Object> params) throws Exception {
		Integer siteId = (Integer) params.get("siteId");
		if (siteId != null && siteId == 0000) {
			params.put("siteId", null);
		}
		List<Goods> goods = goodsMapper.selectList(params);
		return goods;
	}

	@Override
	@Transactional(readOnly = false)
	public int modifyGoods(Goods good) throws Exception {
		if (good.getAmount() != null && good.getAmount() < 0) {
			return Status.goods_param_error;
		}
		good.getAmount();
		good.setUpdateTime(ExDateUtils.getDate());
		goodsMapper.updateById(good);
		return Status.success;
	}

	@Override
	public int modifyGoodsEx(Goods good) throws Exception {
		boolean ispass = isPass(good);
		if (!ispass) {
			return Status.INFO_NOT_COMPLETE;
		}
		
		if(good.getCategory().intValue() == 1){
			FishPond fp = fishPondMapper.selectByPondId(good.getpId().longValue());
			if(fp == null)
				return Status.fish_pond_not_exists;
		}
		
		if (good.getAmount() < 0
				|| good.getDiscountPrice() < 0 || good.getHourLong() < 0) {
			return Status.goods_param_error;
		}
		good.setUpdateTime(ExDateUtils.getDate());
		goodsMapper.updateById(good);
		return Status.success;
	}
	/**
	 * 参数校验
	 * @param good
	 * @return
	 */
	private boolean isPass(Goods good) {
		if (good == null) {
			return false;
		}
		if (good.getFishSiteId() == null || StringUtils.isEmpty(good.getName())
//				|| StringUtils.isEmpty(good.getUrl())
//				|| good.getPrice() == null 
				|| good.getDiscountPrice() == null
				|| good.getAmount() == null || good.getCategory() == null
				|| good.getStatus() == null || good.getpId() == null) {
			return false;
		}
		return true;
	}

	@Transactional
	@Override
	public void salesVolume() {
		Map<String, Object> params = new LinkedMap<String, Object>();
		String value = null;
		DictConfig dict = null;
		try {
			dict = dictMapper.selectDictByCode(VOLUME_TIME_KEY);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (dict != null) { // 没有记录统计时间的记录
			Date _begin = (value = dict.getValue()) != null ? new Date(Long.valueOf(value)) : new Date(0);
			params.put("begin", _begin);
			dict.setValue(String.valueOf(ExDateUtils.getDate().getTime())); // 替换为当前时间
			this.dictMapper.updateById(dict);
			if ( this.goodsMapper.selectNewCountOrder(params) > 0 )
			this.goodsMapper.salesVolume(params);
			return;
		}
		dict = new DictConfig();
		dict.setCode(VOLUME_TIME_KEY);
		dict.setRemarks("商品销量统计");
		dict.setValid(DictConfig.VALID);
		dict.setType(ClearThreadConstant.DictGroup);
		params.put("begin", new Date(0));
		if ( this.goodsMapper.selectNewCountOrder(params) > 0 )
		this.goodsMapper.salesVolume(params);
		dict.setValue(String.valueOf(ExDateUtils.getDate().getTime())); // 替换为当前时间
		this.dictMapper.insert(dict);
	}
}
