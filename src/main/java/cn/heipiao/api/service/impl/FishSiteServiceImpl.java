/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AccountMapper;
import cn.heipiao.api.mapper.FishPondMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.FishSiteSignMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.PartnerOverMapper;
import cn.heipiao.api.mapper.PartnerSiteRateConfigMapper;
import cn.heipiao.api.mapper.SequenceMapper;
import cn.heipiao.api.mapper.SiteCouponsMapper;
import cn.heipiao.api.pojo.Account;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FSPojo;
import cn.heipiao.api.pojo.FishPond;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.FishSiteExt;
import cn.heipiao.api.pojo.FishSiteSign;
import cn.heipiao.api.pojo.Partner;
import cn.heipiao.api.pojo.PartnerSiteRateConfig;
import cn.heipiao.api.pojo.Sequence;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年6月2日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class FishSiteServiceImpl implements FishSiteService {

	@Resource
	private FishSiteMapper fishSiteMapper;

	@Resource
	private FishPondMapper fishPondMapper;

	@Resource
	private SequenceMapper sequenceMapper;

	@Resource
	private AccountMapper accountMapper;

	@Resource
	private EmpService empService;
	
	@Resource
	private PartnerMapper partnerMapper;
	
	@Resource
	private SiteCouponsMapper siteCouponsMapper;
	@Resource
	private PartnerOverMapper partnerOverMapper;
	@Resource
	private PartnerSiteRateConfigMapper partnerSiteRateConfigMapper;
	@Resource
	private FishSiteSignMapper fishSiteSignMapper;

	private static final Object obj = new Object();

	private static final int max = 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#selectById(java.lang.Integer)
	 */
	@Override
	public FishSite selectById(Integer id) {
		return fishSiteMapper.selectById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#selectList(java.lang.Integer,
	 * java.lang.Integer, java.util.Map)
	 */
	@Override
	public List<FishSite> selectList(Map<String, Object> filterName) {
		return fishSiteMapper.selectList(filterName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#createSite(cn.heipiao.api.pojo.
	 * FishSite)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int createSite(FishSite pojo) throws Exception {
		synchronized (obj) {
			// pojo.setChargeDesc("");
			if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {
				setFishSite(pojo);
			}

			setPrimaryKey(pojo);
			
			
			if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {
				for (FishPond fishPond : pojo.getFishPonds()) {
					fishPond.setFishSiteId(pojo.getFishSiteId());
				}
				fishPondMapper.insertBatchPojo(pojo.getFishPonds());
			}

			// 生成序列
			Sequence s = new Sequence();
			s.setFishSiteId(pojo.getFishSiteId());
			s.setCount(0);
			sequenceMapper.insertPojo(s);

			return pojo.getFishSiteId();
		}
	}
	
	
	/**
	 * 设置主键
	 * @param pojo
	 * @throws Exception
	 */
	private void setPrimaryKey(FishSite pojo) throws Exception{
		Integer c = fishSiteMapper.countCityFishSite(pojo.getCityId());
		if (c >= max - 1) {
			throw new Exception(
					"fishsite count not greater than 1000 of city");
		}
		pojo.setFishSiteId(pojo.getCityId() * max + c + 1);
		try {
			fishSiteMapper.insertPojo(pojo);
		} catch (Exception e) {
			saveFishSite(pojo,e,c);
		}
	}

	
	/**
	 * 重复设置主键
	 * @param pojo
	 * @param e
	 * @param c 
	 * @throws Exception
	 */
	private void saveFishSite(FishSite pojo,Exception e, Integer c) throws Exception{
		if(e.getMessage() != null && e.getMessage().trim().contains(String.format("Duplicate entry '%s' for key 'PRIMARY'",pojo.getFishSiteId()))){
			try {
				//生成钓场id
				c++;
				if (c >= max - 1) {
					throw new Exception(
							"fishsite count not greater than 1000 of city");
				}
				pojo.setFishSiteId(pojo.getCityId() * max + c + 1);
				fishSiteMapper.insertPojo(pojo);
				return;
			} catch (Exception e1) {
				saveFishSite(pojo, e1,c);
			}
		}else{
			throw e;
		}
	}
	
	/**
	 * 设置changeDesc,label
	 * 
	 * @param pojo
	 */
	public static void setFishSite(FishSite pojo) {
		List<FishPond> list = pojo.getFishPonds();
		StringBuilder sb = new StringBuilder();
		// 添加钟塘,斤塘标签
		for (FishPond fishPond : list) {
			if(fishPond.getPayType() != null){
				if (sb.indexOf(fishPond.getPayType() + "") >= 0)
					continue;
				sb.append(fishPond.getPayType() + ",");
			}
		}

		if (sb.length() > 2 || list.size() == 1) {
			pojo.setLabel(sb.length() > 0 ? sb.substring(0, sb.length() - 1)
					: null);
		} else {
			pojo.setLabel(sb.length() > 0 ? sb.append(
					sb.substring(0, sb.length() - 1)).toString() : null);
		}

		// 设置收费描述
		ArrayList<Number> cd = new ArrayList<Number>(2);
//		int ats[] = null;
		List<Integer> ats = null;
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getAdmissionTicket() != null){
				if (i > 0) {
					if (ats == null)
//						ats = new int[list.size()];
						ats = new ArrayList<Integer>();
					ats.add(list.get(count++).getAdmissionTicket());
//					ats[count++] = list.get(i).getAdmissionTicket();
					// 钟塘
				} else if (list.get(0).getPayType() != null && list.get(0).getPayType().intValue() == 0) {
					cd.add(0, list.get(0).getAdmissionTicket());
					cd.add(1, list.get(0).getUnitPrice());
					// 斤塘
				} else if (list.get(0).getPayType() != null && list.get(0).getPayType().intValue() == 1) {
					cd.add(0, list.get(0).getUnitPrice());
					cd.add(1, 1);
				}
			}
		}

		if (ats != null) {
			if(ats.size() > 1){
				pojo.setChargeDesc(getChargeDescStr(ats.toArray(new Integer[ats.size()])));
			}else{
				pojo.setChargeDesc(ats.get(0).toString());
			}
		} else {
			pojo.setChargeDesc(cd.size() > 0
					&& (cd.get(0) != null && cd.get(1) != null) ? cd.get(0)
					+ "," + cd.get(1) : null);
		}
	}

	private static String getChargeDescStr(Integer[] is) {
		int[] rs = new int[2];
		for (int i = 0; i < is.length; i++) {
			if (i > 1) {
				if (is[i].intValue() < rs[0]) {
					rs[0] = is[i];
				} else if (is[i] > rs[1]) {
					rs[1] = is[i];
				}
			} else {
				rs[i] = is[i];
				if (i > 0 && rs[0] > rs[1]) {
					rs[0] = rs[0] + rs[1];
					rs[1] = rs[0] - rs[1];
					rs[0] = rs[0] - rs[1];
				}
			}
		}
		return rs[0] + "," + rs[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#updateSite(cn.heipiao.api.pojo.
	 * FishSite)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updateSite(FishSite pojo) throws Exception {
		fishSiteMapper.updatePojo(pojo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#updateSiteModify(cn.heipiao.api
	 * .pojo. FishSite)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updateSiteModify(FishSite pojo) {
		if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {
			// ArrayList<Integer> cd = new ArrayList<Integer>(2);
			// StringBuilder sb = new StringBuilder();
			// List<FishPond> fps = pojo.getFishPonds();
			// for (FishPond fishPond : fps) {
			// // 设置收费描述
			// if (cd.size() == 0) {
			// cd.add(fishPond.getAdmissionTicket() == null ? 0 :
			// fishPond.getAdmissionTicket());
			// } else if (cd.size() == 1) {
			// cd.set(0, Math.min(cd.get(0).intValue(),
			// fishPond.getAdmissionTicket() == null ? 0 :
			// fishPond.getAdmissionTicket().intValue()));
			// cd.add(1, Math.max(cd.get(0).intValue(),
			// fishPond.getAdmissionTicket() == null ? 0 :
			// fishPond.getAdmissionTicket().intValue()));
			// } else if (cd.size() == 2) {
			// cd.set(0, Math.min(cd.get(0).intValue(),
			// fishPond.getAdmissionTicket() == null ? 0 :
			// fishPond.getAdmissionTicket().intValue()));
			// cd.set(1, Math.max(cd.get(1).intValue(),
			// fishPond.getAdmissionTicket() == null ? 0 :
			// fishPond.getAdmissionTicket().intValue()));
			// }
			// // 添加钟塘,斤塘标签
			// if (sb.indexOf(fishPond.getPayType() + "") >= 0)
			// continue;
			// sb.append(fishPond.getPayType() + ",");
			// }
			// pojo.setLabel(sb.length() == 0 ? null :sb.substring(0,
			// sb.length() - 1));
			// pojo.setChargeDesc("");
			// if (cd.size() == 1) {
			// pojo.setChargeDesc(cd.get(0).toString());
			// } else if (cd.size() == 2) {
			// pojo.setChargeDesc(cd.get(0) + "-" + cd.get(1));
			// }
			// pojo.setChargeDesc(pojo.getChargeDesc() != null &&
			// pojo.getChargeDesc().length() > 0 ? pojo.getChargeDesc() + "元" :
			// null);
			// fishPondMapper.updateBatchPojo(pojo.getFishPonds());

			setFishSite(pojo);
		}
		if(StringUtils.isBlank(pojo.getMainImg())){
			if(pojo.getResources() != null){
				pojo.setMainImg(pojo.getResources().split(",")[0]);
			}
		}	
		fishSiteMapper.updatePojo(pojo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#likeByNameAndPhone(Map<String,
	 * Object> map)
	 */
	@Override
	public List<FishSite> likeByNameAndPhone(Map<String, Object> map) {
		return fishSiteMapper.selectByLikeNameAndPhone(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectByValue(java.lang.Integer)
	 */
	@Override
	public List<FishSite> selectByValue(Map<String, Object> map) {
		return fishSiteMapper.selectByValue(map);
	}
	@Override
	public Integer selectSiteCount(Map<String, Object> map) {
		return fishSiteMapper.selectSiteCount(map);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int updateByPartnerIdAndSiteId(Integer partnerId, Integer siteId,
			Integer signStatus) {
		
		Partner p = partnerMapper.selectByPartnerId(partnerId);
		if(p == null){
			return Status.partner_not_exists;
		}
		
		FishSite fs = fishSiteMapper.selectById(siteId);
		if(fs == null){
			return Status.fish_site_not_exists;
		}
		
		FishSiteSign fishSign = fishSiteSignMapper.selectOne(siteId);
		if(fishSign!=null){
			return Status.FISH_NOT_SIGN;
		}
		fishSign = new FishSiteSign();
		fishSign.setPartnerId(partnerId);
		fishSign.setSignStatus(signStatus);
		fishSign.setSiteId(siteId);
		fishSiteSignMapper.insert(fishSign);
		return Status.success;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectByUid(java.lang.Integer)
	 */
	@Override
	public FishSite selectByUid(Long uid) {
		return fishSiteMapper.selectByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#queryList(java.util.Map)
	 */
	@Override
	public List<FishSite> queryList(Map<String, Object> map) {
		return fishSiteMapper.queryList(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#selectCount(java.lang.String)
	 */
	@Override
	public Integer selectCount(Integer status) {
		return fishSiteMapper.selectCount(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#selectByRegion(java.util.Map)
	 */
	@Override
	public List<FishSite> selectByRegion(Map<String, Object> map) {
		return fishSiteMapper.selectByRegion(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectByRegionCp(java.util.Map)
	 */
	@Override
	public List<FishSite> selectByRegionCp(Map<String, Object> map) {
		return fishSiteMapper.selectByRegionCp(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectByRegionCpTotal(java.util.
	 * Map)
	 */
	@Override
	public int selectByRegionCpTotal(Map<String, Object> map) {
		return fishSiteMapper.selectByRegionCpTotal(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#likeByName(java.util.Map)
	 */
	@Override
	public List<FishSite> likeByName(Map<String, Object> map) {
		return fishSiteMapper.selectByLikeName(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectFishSiteByPartnerId(java.
	 * util.Map)
	 */
	@Override
	public List<FishSite> selectFishSiteByPartnerId(Map<String, Object> map) {
		return fishSiteMapper.selectFishSiteByPartnerId(map);
	}

	@Override
	public List<FishSite> getSitesByPartnerId(Map<String, Object> param)
			throws Exception {
		List<FishSite> list = fishSiteMapper.selectSitesSignByPartnerId(param);
		return list;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectCountByNameAndPhone(java.
	 * util.Map)
	 */
	@Override
	public Integer selectCountByNameAndPhone(Map<String, Object> map) {
		return fishSiteMapper.selectCountByNameAndPhone(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#updatePoundageRate(java.util.Map)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public void updateWithdrawRate(Map<String, Object> map) {
		fishSiteMapper.updateWithdrawRate(map);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int updateSiteUid(FishSite pojo) throws Exception {
		FishSite fs = fishSiteMapper.selectByIdAsLock(pojo.getFishSiteId());
		// 创建钓场财务账户
		if (fs.getUid() != null) {
			return Status.fish_site_exists_user;
		}
		FishSite p = fishSiteMapper.selectAllByUid(pojo.getUid());
		if (p != null)
			return Status.user_exists_fish_site;
		
		//创建员工权限
		setEmp(pojo);
		
		//设置账户信息
		setAccount(pojo.getUid(),null);
		
		pojo.setNamedTime(new Date());
		fishSiteMapper.updatePojo(pojo);
		return Status.success;
	}

	
	/**
	 * 初始化员工
	 * @param pojo
	 * @throws Exception
	 */
	private void setEmp(FishSite pojo) throws Exception{
		Emp emp = null;
		if ((emp = empService.selectById(pojo.getUid())) == null) {
			empService.insert(pojo.getUid().toString(), pojo.getFishSiteId()
					.toString());
		} else {
			emp.setEmpStatus(String.valueOf(EmpService.ROOT_USER));
			empService.delete(String.valueOf(pojo.getUid()));
			empService.insert(pojo.getUid().toString(), pojo.getFishSiteId()
					.toString());
		}
	}
	
	
	private void setAccount(Long uid,Long oldUid){
		//查询oldUid的账户
		Account oa = null;
		if(oldUid != null){
			oa = accountMapper.selectByUid(uid);
		}
		
		// 注册账户信息
		Account a = accountMapper.selectByUid(uid);
		if(a == null){
			a = new Account();
			a.setBalance(oa == null ? 0D : oa.getBalance());
			a.setUid(uid);
			a.setWithdrawRuleId(2);
			a.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar()
					.getTimeInMillis() - 24L * 60 * 60 * 1000));
			accountMapper.insertPojo(a);
		}else{
			a.setWithdrawTime(new Timestamp(ExDateUtils.getCalendar()
					.getTimeInMillis() - 24L * 60 * 60 * 1000));
			a.setBalance(oa == null ? 0D : oa.getBalance());
			a.setWithdrawRuleId(2);
			accountMapper.updatePojo(a);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectAllByUid(java.lang.Long)
	 */
	@Override
	public FishSite selectAllByUid(Long uid) {
		return fishSiteMapper.selectAllByUid(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.heipiao.api.service.FishSiteService#siteSelectList(java.util.Map)
	 */
	@Override
	public List<FishSite> siteSelectList(Map<String, Object> map) {
		return fishSiteMapper.siteSelectList(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#selectByFocusList(java.util.Map)
	 */
	@Override
	public List<FishSite> selectByFocusList(Map<String, Object> map) {
		return fishSiteMapper.selectByFocus(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#siteSelectList2(java.util.Map)
	 */
	@Override
	public List<FishSite> siteSelectList2(Map<String, Object> map) {
		return fishSiteMapper.siteSelectList2(map);
	}
	/**
	 * 对认领成功但售票认领失败的合伙人筛选
	 */
	@Override
	public List<FishSite> queryListByCity(Map<String, Object> map) {
		List<FishSite> list=fishSiteMapper.selectByCityId(map);
		return list;
	}

	@Override
	public Integer countClaimSiteByPartner(Integer partnerId) throws Exception {
		return fishSiteMapper.countClaimSite(partnerId);
	}

	@Override
	public List<FishSite> queryListByPartner(Map<String, Object> map) {
		return fishSiteMapper.selectByPartner(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.heipiao.api.service.FishSiteService#getPartnerIdIsNullFishSite(java
	 * .util.Map)
	 */
	@Override
	public List<FishSite> getPartnerIdIsNullFishSite(Map<String, Object> map) {
		return fishSiteMapper.selectPartnerIdIsNullFishSite(map);
	}

	@Override
	@Transactional
	public int createSiteByPartner(FishSiteExt pojo) throws Exception {
		synchronized (obj) {
			Integer c = fishSiteMapper.countCityFishSite2(pojo.getCityId());
			if (c >= max - 1) {
				throw new Exception(
						"fishsite count not greater than 1000 of city");
			}
			pojo.setFishSiteId(pojo.getCityId() * max + c + 1);
			if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {

				setFishSite(pojo);
			}
			fishSiteMapper.insertPojo2(pojo);
			if (pojo.getFishPonds() != null && pojo.getFishPonds().size() > 0) {
				for (FishPond fishPond : pojo.getFishPonds()) {
					fishPond.setFishSiteId(pojo.getFishSiteId());
				}
				fishPondMapper.insertBatchPojo2(pojo.getFishPonds());
			}

			return pojo.getFishSiteId();
		}
	}

	@Override
	public int getMaxTop() {
		return fishSiteMapper.selectMaxTop();
	}

	@Override
	@Transactional
	public int setFishShopTop(Map<String, Object> map) {
		return fishSiteMapper.updateSiteTop(map);
	}

	@Override
	public List<FSPojo> selectListForPartnerSign(Map<String,Object> map) {
		return this.fishSiteMapper.selectListForPartnerSign(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FishSiteService#modifyOpenCoupon(java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifyOpenCoupon(Long uid, Integer siteId) {
		FishSite fs = fishSiteMapper.selectById(siteId);
		if(fs == null || fs.getStatus() != 1){
			return Status.fish_site_not_exists;
		}
		if(!fs.getIsAuthCoupon()){
			return Status.coupon_not_auth;
		}
		fishSiteMapper.updateOpenCoupon(siteId);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FishSiteService#modifyIsAuthCoupon(java.lang.Long, java.lang.Integer, java.lang.Boolean)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifyIsAuthCoupon(Long uid, Integer siteId, Boolean isAuthCoupon) {
		if(!isAuthCoupon){
			siteCouponsMapper.updateSiteAllCoupon(siteId);
		}
		fishSiteMapper.updateIsAuthCoupon(isAuthCoupon, siteId);
		return Status.success;
	}

	/* (non-Javadoc)
	 * 更换钓场老板
	 * @see cn.heipiao.api.service.FishSiteService#changeFishSite(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int changeFishSite(Long oldUid, Long newUid) throws Exception {
		FishSite fs1 = fishSiteMapper.selectByUid(oldUid);
		if(fs1 == null){
			return Status.fish_site_not_exists;
		}
		FishSite fs2 = fishSiteMapper.selectByUid(newUid);
		if(fs2 != null){
			return Status.user_exists_fish_site;
		}
		
		//删除钓场员工
		empService.deleteAll(fs1.getFishSiteId());
		//删除新钓场主在其他钓场的权限
		empService.delete(newUid.toString());
		//下架旧的钓场
//		fishSiteMapper.updateOldSite(fs1.getFishSiteId());
		
		//设置uid
		fs1.setUid(newUid);
		//创建新钓场
//		List<FishPond> ponds = fishPondMapper.selectBySiteId(fs1.getFishSiteId());
//		fs1.setFishPonds(ponds);
//		createSite(fs1);
		
		fishSiteMapper.updatePojo(fs1);
		
		//设置员工
		setEmp(fs1);
		
		//设置财务
		setAccount(newUid,oldUid);
		
		return Status.success;
	}

	/**
	 * 指定钓场设置图标logo
	 */
	@Override
	@Transactional
	public int setLogo(FishSite pojo) {
		return fishSiteMapper.updateLogo(pojo);
	}

	@Override
	public List<FishSite> queryDistributionList(Map<String, Object> map) {
			return fishSiteMapper.queryDistributionList(map);
	}

	@Override
	public PartnerSiteRateConfig selectSiteRateConfig(Integer siteId ,Integer partnerId) {
		PartnerSiteRateConfig partnerSiteRateConfig =partnerSiteRateConfigMapper.selectById(siteId, partnerId);
		return partnerSiteRateConfig;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateSiteRateConfig(Integer siteId,Integer partnerId,Double siteRate) {
		PartnerSiteRateConfig partnerSiteRateConfig = partnerSiteRateConfigMapper.selectById(siteId,partnerId);
		if(partnerSiteRateConfig!=null){
			partnerSiteRateConfig.setSiteRate(siteRate);
			partnerSiteRateConfig.setPartnerId(partnerId);
			partnerSiteRateConfig.setSiteId(siteId);
			partnerSiteRateConfigMapper.updatePojo(partnerSiteRateConfig);
		}else{
			partnerSiteRateConfig = new PartnerSiteRateConfig();
			partnerSiteRateConfig.setSiteRate(siteRate);
			partnerSiteRateConfig.setPartnerId(partnerId);
			partnerSiteRateConfig.setSiteId(siteId);
			partnerSiteRateConfigMapper.insertPojo(partnerSiteRateConfig);	
		}
		return Status.success;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public int updateByPartnerOfTrading(Integer partnerId, Integer siteId, Integer signStatus) {

		Partner p = partnerMapper.selectByPartnerId(partnerId);
		if(p == null){
			return Status.partner_not_exists;
		}
		
		FishSite fs = fishSiteMapper.selectById(siteId);
		if(fs == null){
			return Status.fish_site_not_exists;
		}
		
		FishSiteSign fishSign = fishSiteSignMapper.selectOne(siteId);
		if(fishSign==null || fishSign.getSignStatus()==1 || fishSign.getSignStatus()==4){
			return Status.FISH_NOT_SIGN;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("partnerId",partnerId);
		map.put("signStatus",signStatus);
		map.put("siteId",siteId);
		map.put("partnerId", partnerId);
		fishSiteSignMapper.update(map);
		return Status.success;
	}

	@Override
	public List<FishSite> selectBytheValue(Map<String, Object> map) {
		// 
		return fishSiteMapper.selectSiteList(map);
	}
}
