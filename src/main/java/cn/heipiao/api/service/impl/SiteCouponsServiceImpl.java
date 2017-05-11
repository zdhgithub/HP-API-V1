/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.FocusMapper;
import cn.heipiao.api.mapper.SiteCouponsMapper;
import cn.heipiao.api.mapper.SiteCouponsRecordMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.SiteCoupons;
import cn.heipiao.api.pojo.SiteCouponsRecord;
import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.CouponFeesConfigService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.SiteCouponsService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年11月3日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SiteCouponsServiceImpl implements SiteCouponsService {
	
	@Resource
	private SiteCouponsMapper siteCouponsMapper;
	
	@Resource
	private SiteCouponsRecordMapper siteCouponsRecordMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private UserTicketsMapper userTicketsMapper;
	
	@Resource
	private FocusMapper focusMapper;
	
	@Resource
	private CouponFeesConfigService couponFeesConfigService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private JPushService jPushService;
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getListByUid(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<SiteCoupons> getListBySiteId(Map<String,Object> map) {
		return siteCouponsMapper.selectListBySiteId(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#addPojo(cn.heipiao.api.pojo.SiteCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addPojo(SiteCoupons pojo) {
		FishSite fs = fishSiteMapper.selectByIdAsLock(pojo.getSiteId());
		if(fs == null || fs.getStatus() != 1){
			return Status.fish_site_not_exists;
		}
		//cp平台是否授权
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		//更新优惠券总数
		fishSiteMapper.updateIsCouponAndCount(pojo.getSiteId(),true,fs.getCouponCount() + pojo.getAmount());
		//设置收费金额
		couponFeesConfigService.setFees(1, pojo);
		siteCouponsMapper.insertPojo(pojo);
		return Status.success;
	}

	
//	private int getFishSite(Integer siteId){
//		FishSite fs = fishSiteMapper.selectById(siteId);
//		if(fs == null){
//			return Status.fish_site_not_exists;
//		}
//		return Status.success;
//	}
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#addRoutinePojo(cn.heipiao.api.pojo.SiteCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addRoutinePojo(SiteCoupons pojo) throws Exception {
		FishSite fs = fishSiteMapper.selectById(pojo.getSiteId());
		if(fs == null || fs.getStatus() != 1){
			return Status.fish_site_not_exists;
		}
		
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		
		Set<Long> uids = null;
		if(pojo.getUids() == null){
			pojo.setUids(new HashSet<Long>());
		}
		for(int flag : pojo.getFlag()){
			switch (flag) {
			case 1:
				uids = userTicketsMapper.selectUidsBySiteId(pojo.getSiteId());
				break;
			case 2:
				uids = focusMapper.selectUidsBySiteId(pojo.getSiteId());
				break;
			case 3:
				break;
			}
			
			if(uids != null && uids.size() > 0){
				pojo.getUids().addAll(uids);
			}
		}
		
		if(pojo.getUids() == null || pojo.getUids().size() == 0){
			return Status.no_such_user;
		}
		pojo.setAmount(uids.size());
		pojo.setCount(uids.size());
		//发券
		addPojos(pojo,fs);
		
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#addSpecifyPojo(cn.heipiao.api.pojo.SiteCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addSpecifyPojo(SiteCoupons pojo) throws Exception {
		FishSite fs = fishSiteMapper.selectById(pojo.getSiteId());
		if(fs == null || fs.getStatus() != 1){
			return Status.fish_site_not_exists;
		}
		
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		
		addPojos(pojo,fs);
		
		return Status.success;
	}
	
	
	private void addPojos(SiteCoupons pojo, FishSite fs) throws Exception{
		//设置收费
		couponFeesConfigService.setFees(1, pojo);
		//保存
		siteCouponsMapper.insertPojo(pojo);
		//发券
		giveCoupon(pojo,pojo.getUids(),fs);
		
//		List<Coupons> cs = new ArrayList<Coupons>();
//		Coupons c = null;
//		for (Long uid : pojo.getUids()) {
//			c = new Coupons();
//			c.setCategory(2);
//			c.setCouponFee(pojo.getCouponFee());
//			c.setCouponName(pojo.getCouponName());
//			c.setDeadline(pojo.getEndTime());
//			c.setDesc("钓场优惠券");
//			c.setGetTime(new Timestamp(cc.getTimeInMillis()));
//			c.setStatus(0);
//			c.setUid(uid);
//			c.setUseRule(pojo.getUseRule());
//			c.setStartTime(pojo.getStartTime());
//			cs.add(c);
//		}
//		//发放优惠券
//		couponsMapper.insertBatch(cs);
//		
//		List<SiteCouponsRecord> scsr = new ArrayList<SiteCouponsRecord>();
//		SiteCouponsRecord scr = null;
//		for (Coupons coupons : cs) {
//			scr = new SiteCouponsRecord();
//			scr.setCid(coupons.getCid());
//			scr.setId(pojo.getId());
//			scr.setSietId(pojo.getSiteId());
//			scr.setUid(coupons.getUid());
//			scsr.add(scr);
//		}
//		//优惠券记录
//		siteCouponsRecordMapper.insertBatch(scsr);
	}

	
	private void giveCoupon(SiteCoupons pojo,Collection<Long> uids, FishSite fs) throws Exception{
		Calendar cc = ExDateUtils.getCalendar();
		List<Coupons> cs = new ArrayList<Coupons>();
		Coupons c = null;
		for (Long uid : uids) {
			c = new Coupons();
			c.setCategory(2);
			c.setCouponFee(pojo.getCouponFee());
			c.setCouponName(pojo.getCouponName());
			c.setDeadline(pojo.getEndTime());
			c.setDesc(fs.getFishSiteName() + "优惠券");
			c.setGetTime(new Timestamp(cc.getTimeInMillis()));
			c.setStatus(0);
			c.setUid(uid);
			c.setUseRule(pojo.getUseRule());
			c.setStartTime(pojo.getStartTime());
			c.setFee(pojo.getFee());
			cs.add(c);
		}
		//发放优惠券
		couponsMapper.insertBatch(cs);
		
		List<SiteCouponsRecord> scsr = new ArrayList<SiteCouponsRecord>();
		SiteCouponsRecord scr = null;
		for (Coupons coupons : cs) {
			scr = new SiteCouponsRecord();
			scr.setCid(coupons.getCid());
			scr.setId(pojo.getId());
			scr.setSiteId(pojo.getSiteId());
			scr.setUid(coupons.getUid());
			scsr.add(scr);
		}
		//优惠券记录
		siteCouponsRecordMapper.insertBatch(scsr);

		//消息
		SystemMsg sm = new SystemMsg();
		sm.setContent(fs.getFishSiteName() + pojo.getCouponName());
		sm.setCreateTime(ExDateUtils.getDate());
		sm.setFlag(0);
		sm.setTitle("恭喜您！您获得了一张优惠券");
		
		systemMsgService.saveMsgBatch(sm, uids.toArray());
		
		//推送消息
		for (Long uid : uids) {
			jPushService.sendPush(uid.intValue(), "C", "钓场券", "您获得了钓场优惠券了", null);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#countBySiteId(java.lang.Integer)
	 */
	@Override
	public CouponsCount countBySiteId(Integer siteId) {
		return siteCouponsMapper.countBySiteId(siteId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#modifySiteCouponStatus(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifySiteCouponStatus(Long uid, Long id, Integer status) {
		SiteCoupons sc = siteCouponsMapper.selectAslockById(id);
		if(sc == null || sc.getStatus() == 3){
			return Status.value_is_null_or_error;
		}
		FishSite fs = fishSiteMapper.selectByIdAsLock(sc.getSiteId());
		if(fs == null || fs.getStatus() != 1 || !fs.getOpenCoupon()){
			return Status.value_is_null_or_error;
		}
		//是否授权
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		//优惠券是否过期
//		if(sc.getEndTime().getTime() < ExDateUtils.getCalendar().getTimeInMillis()){
		if(CouponUserServiceImpl.isTimeoutByCoupons(sc.getEndTime())){
			siteCouponsMapper.updateSiteCouponStatus(sc.getId(), 2);
			fishSiteMapper.updateIsCouponAndCount(sc.getSiteId(), true,
					fs.getCouponCount() - (sc.getAmount() - sc.getCount()) >= 0 ? fs.getCouponCount() - (sc.getAmount() - sc.getCount()):0 );
			return Status.success;
		}
		if(status == 0){
			//如果为0则为发布优惠券中，发布中并更新数量
			fishSiteMapper.updateIsCouponAndCount(sc.getSiteId(), true,fs.getCouponCount() + (sc.getAmount() - sc.getCount()));
		}else{
			//如果是暂停某个优惠券则判断优惠券是否是<=1
			if(siteCouponsMapper.countNormal(sc.getSiteId()) <= 1){
				//如果<=1关闭优惠券发布中
				fishSiteMapper.updateIsCouponAndCount(sc.getSiteId(), false,0);
			}else{
				//如果是>1则之暂停某个优惠券
				fishSiteMapper.updateIsCouponAndCount(sc.getSiteId(), true,
						fs.getCouponCount() - (sc.getAmount() - sc.getCount()) >= 0 ? fs.getCouponCount() - (sc.getAmount() - sc.getCount()):0 );
			}
		}
		siteCouponsMapper.updateSiteCouponStatus(id,status);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getSiteUsers(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<SiteCouponsRecord> getSiteUsers(Integer siteId, Integer flag) {
		List<SiteCouponsRecord> scl = null;
		switch (flag) {
		case 1:
			scl = siteCouponsRecordMapper.selectConsumeUserBySiteId(siteId);
			break;
		case 2:
			scl = siteCouponsRecordMapper.selectFocusSiteBySiteId(siteId);
			break;
		case 3:
			//暂时没有
			break;
		}
		return scl;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#isOpenCoupon(java.lang.Integer)
	 */
	@Override
	public Boolean isOpenCoupon(Integer siteId) {
		FishSite fs = fishSiteMapper.selectById(siteId);
		if(fs != null && fs.getStatus() == 1){
			return fs.getOpenCoupon();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getSiteCoupon(java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int getSiteCoupon(Long uid, Integer siteId, Long id) throws Exception {
		boolean b = siteCouponsRecordMapper.isGet(id, uid);
		if(b){
			//返回已经领取过优惠券
			return Status.coupon_existed;
		}
		FishSite fs = fishSiteMapper.selectByIdAsLock(siteId);
		if(fs == null || fs.getStatus() != 1 || !fs.getOpenCoupon()
				|| fs.getFishSiteId().intValue() != siteId.intValue() || !fs.getIsCoupon() || !fs.getIsAuthCoupon()){
			//返回优惠券被抢完了
			return Status.coupon_is_zore;
		}
		SiteCoupons sc = siteCouponsMapper.selectAslockById(id);
		if(sc.getStatus() != 0){
			//返回优惠券被抢完了
			return Status.coupon_is_zore;
		}
		//判断数量
		if(sc.getCount() + sc.getLimit() > sc.getAmount()){
			siteCouponsMapper.updateSiteCouponStatus(sc.getId(), 3);
			if(siteCouponsMapper.countNormal(sc.getSiteId()) <= 0){
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),false,0);
			}else{
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
			return Status.coupon_is_zore;
		}
		
		//判断时间是否过期
//		if(sc.getEndTime().getTime() <= ExDateUtils.getCalendar().getTimeInMillis()){
		if(CouponUserServiceImpl.isTimeoutByCoupons(sc.getEndTime())){
			siteCouponsMapper.updateSiteCouponStatus(sc.getId(), 2);
			if(siteCouponsMapper.countNormal(sc.getSiteId()) <= 0){
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),false,0);
			}else{
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
			return Status.coupon_is_zore;
		}
		
		//发券
		List<Long> uids = new ArrayList<Long>();
		for(int i = 0 ; i < sc.getLimit(); i++){
			uids.add(uid);
		}
		//钓场券领取
		giveCoupon(sc,uids,fs);
		sc.setCount(sc.getCount() + sc.getLimit());
		
		if(sc.getCount() >= sc.getAmount()){
			sc.setStatus(3);
		}
		
		siteCouponsMapper.updatePojo(sc);
		//查询优惠券是否被领取完
		if(sc.getCount() >= sc.getAmount() && siteCouponsMapper.countNormal(sc.getSiteId()) <= 0){
			fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),false,0);
		}else{
			fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),true,
					fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
		}
		return Status.success;
	}

	
	/**
	 * 线程调用
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void updateCouponsTimeout(Long id){	
		SiteCoupons sc = siteCouponsMapper.selectAslockById(id);
		if(sc.getStatus().intValue() < 2){
			FishSite fs = fishSiteMapper.selectByIdAsLock(sc.getSiteId());
			siteCouponsMapper.updateSiteCouponStatus(sc.getId(), 2);
			if(siteCouponsMapper.countNormal(sc.getSiteId()) <= 0){
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),false,0);
			}else{
				fishSiteMapper.updateIsCouponAndCount(fs.getFishSiteId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getDetailList(java.lang.Integer)
	 */
	@Override
	public List<SiteCouponsRecord> getDetailList(Integer siteId,Map<String,Object> map) {
		return siteCouponsRecordMapper.selectList(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getNotGetList(java.lang.Integer)
	 */
	@Override
	public List<SiteCoupons> getNotGetList(Integer siteId, Long uid) {
		return siteCouponsMapper.selectNotGetList(siteId,uid,new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#countUids(java.lang.Integer, java.lang.String[])
	 */
	@Override
	public int countUids(Integer siteId, String[] fs) {
		Set<Long> uids = new HashSet<Long>();
		Set<Long> us = null;
		for(String flag : fs){
			switch (Integer.parseInt(flag)) {
			case 1:
				us = userTicketsMapper.selectUidsBySiteId(siteId);
				break;
			case 2:
				us = focusMapper.selectUidsBySiteId(siteId);
				break;
			case 3:
				break;
			}
			
			if(us != null && us.size() > 0){
				uids.addAll(us);
			}
		}
		return uids.size();
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.SiteCouponsService#getNewListBySiteId(java.util.Map)
	 */
	@Override
	public List<SiteCoupons> getNewListBySiteId(Map<String, Object> map) {
		return siteCouponsMapper.selectNewListBySiteId(map);
	}

}
