/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishPondMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.GoodsMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.FishPondService;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FishPondServiceImpl implements FishPondService {

	@Resource
	private FishPondMapper fishPondMapper;

	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private GoodsMapper goodsMapper;
	
	@Resource
	private UserTicketsMapper userTicketsMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPondService#selectBySiteId(java.lang.Integer)
	 */
	@Override
	public List<FishPond> selectBySiteId(Integer siteId) {
		return fishPondMapper.selectBySiteId(siteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPondService#insertPojo(cn.heipiao.api.pojo.
	 * FishPond)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void insertPojo(FishPond pojo) {
		fishPondMapper.insertPojo(pojo);
		
		setFishSiteLabelAndChargeDesc(pojo);
		
		// 设置收费描述
//		ArrayList<Integer> cd = new ArrayList<Integer>();
//		Integer[] cd  = new Integer[2];
//		String[] cs = StringUtils.isBlank(fs.getChargeDesc()) ? null : fs.getChargeDesc().split("-");
//		if (cs != null) {
//			cd[0] = Math.min(Integer.valueOf(cs[0]), pojo.getAdmissionTicket() == null ? 0 : pojo.getAdmissionTicket().intValue());
//			if (cs.length == 1) {
//				cd[1] = Math.max(Integer.valueOf(cs[0]).intValue(), pojo.getAdmissionTicket() == null ? 0 : pojo.getAdmissionTicket().intValue());
//			} else if (cs.length == 2) {
//				cd[1] = Math.max(Integer.valueOf(cs[0]).intValue(), pojo.getAdmissionTicket() == null ? 0 : pojo.getAdmissionTicket().intValue());
//			}
//			fs.setChargeDesc(cd[0] + "-" + cd[1]);
//		} else {
//			fs.setChargeDesc(pojo.getAdmissionTicket() + "");
//		}
//		// 添加钟塘,斤塘标签
//		if (!StringUtils.isBlank(fs.getLabel())) {
//			int index = fs.getLabel().indexOf(pojo.getPayType() + "");
//			if (index < 0) {
//				fs.setLabel(fs.getLabel() + "," + pojo.getPayType());
//				fishSiteMapper.updatePojo(fs);
//			}
//		} else {
//			fs.setLabel(pojo.getPayType() + "");
//			fishSiteMapper.updatePojo(fs);
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPondService#deletePojo(cn.heipiao.api.pojo.
	 * FishPond)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int deletePojo(FishPond pojo) {
		//判断商品，票，
		Integer utb = userTicketsMapper.isExists(pojo.getFishPondId());
		Integer gb = goodsMapper.isExists(pojo.getFishPondId());
		if(utb != null || gb != null)
			return Status.fish_pond_relevance;
		fishPondMapper.deletePojo(pojo);
		setFishSiteLabelAndChargeDesc(pojo);
		return Status.success;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPondService#updatePojo(cn.heipiao.api.pojo.
	 * FishPond)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updatePojo(FishPond pojo) {
		fishPondMapper.updatePojo(pojo);

		setFishSiteLabelAndChargeDesc(pojo);
	}

	private void setFishSiteLabelAndChargeDesc(FishPond pojo){
		
		FishSite fs = fishSiteMapper.selectById(pojo.getFishSiteId());
		List<FishPond> list = fishPondMapper.selectBySiteId(pojo.getFishSiteId());
		if(list.size() > 0){
			fs.setFishPonds(list);
			FishSiteServiceImpl.setFishSite(fs);
			fishSiteMapper.updatePojo(fs);
		}
		
//		StringBuilder sb = new StringBuilder();
//		ArrayList<Integer> cd = new ArrayList<Integer>();
//		for (FishPond fishPond : list) {
//			// 设置收费描述
//			if (cd.size() == 0) {
//				cd.add(fishPond.getAdmissionTicket() == null ? 0 : fishPond.getAdmissionTicket());
//			} else if (cd.size() == 1) {
//				cd.set(0, Math.min(cd.get(0).intValue(), fishPond.getAdmissionTicket() == null ? 0 : fishPond.getAdmissionTicket().intValue()));
//				cd.add(1, Math.max(cd.get(0).intValue(), fishPond.getAdmissionTicket() == null ? 0 : fishPond.getAdmissionTicket().intValue()));
//			} else if (cd.size() == 2) {
//				cd.set(0, Math.min(cd.get(0).intValue(), fishPond.getAdmissionTicket() == null ? 0 : fishPond.getAdmissionTicket().intValue()));
//				cd.set(1, Math.max(cd.get(1).intValue(), fishPond.getAdmissionTicket() == null ? 0 : fishPond.getAdmissionTicket().intValue()));
//			}
//			// 添加钟塘,斤塘标签
//			if (sb.indexOf(fishPond.getPayType() + "") >= 0)
//				continue;
//			sb.append(fishPond.getPayType() + ",");
//		}
//		FishSite fs = new FishSite();
//		fs.setFishSiteId(pojo.getFishSiteId());
//		fs.setLabel(sb.length() == 0 ? null : sb.substring(0, sb.length() - 1));
//		fs.setChargeDesc(cd.size() == 0 ? null : cd.size() == 1 ? cd.get(0).toString() : cd.get(0) + "-" + cd.get(1));
//		fs.setChargeDesc(fs.getChargeDesc() != null && fs.getChargeDesc().length() > 0 ? fs.getChargeDesc() + "元" : null);
//		fishSiteMapper.updatePojo(fs);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishPondService#selectBySiteIdLimit(java.util.Map)
	 */
	@Override
	public List<FishPond> selectBySiteIdLimit(Map<String, Object> map) {
		return fishPondMapper.selectBySiteIdLimit(map);
	}

}
