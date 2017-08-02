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
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.mapper.ShopCouponsMapper;
import cn.heipiao.api.mapper.ShopCouponsRecordMapper;
import cn.heipiao.api.mapper.ShopTradeOrdersMapper;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.CouponsCount;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.LikeFocus;
import cn.heipiao.api.pojo.ShopCoupons;
import cn.heipiao.api.pojo.ShopCouponsRecord;
import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.CouponFeesConfigService;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.ShopCouponsService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年11月3日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ShopCouponsServiceImpl implements ShopCouponsService {
	
	@Resource
	private ShopCouponsMapper shopCouponsMapper;
	
	@Resource
	private ShopCouponsRecordMapper shopCouponsRecordMapper;
	
	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private CouponsMapper couponsMapper;
	
	@Resource
	private ShopTradeOrdersMapper shopTradeOrdersMapper;
	
	@Resource
	private LikeFocusMapper<LikeFocus> likeFocusMapper;
	
	@Resource
	private CouponFeesConfigService couponFeesConfigService;
	
	@Resource
	private SystemMsgService systemMsgService;
	
	@Resource
	private JPushService jPushService;
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getListByUid(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<ShopCoupons> getListByShopId(Map<String,Object> map ) {
		return shopCouponsMapper.selectListByShopId(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#addPojo(cn.heipiao.api.pojo.ShopCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addPojo(ShopCoupons pojo) {
		FishShop fs = fishShopMapper.selectFishShopAsLockByShopId(pojo.getShopId());
		if(fs == null){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		couponFeesConfigService.setFees(2, pojo);
		fishShopMapper.updateIsCouponAndCount(pojo.getShopId(),true,fs.getCouponCount() + pojo.getAmount());
		shopCouponsMapper.insertPojo(pojo);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#countByShopId(java.lang.Integer)
	 */
	@Override
	public CouponsCount countByShopId(Long shopId) {
		return shopCouponsMapper.countByShopId(shopId);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#addSpecifyPojo(cn.heipiao.api.pojo.ShopCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addSpecifyPojo(ShopCoupons pojo) throws Exception {
		FishShop fs = fishShopMapper.selectFishShopByShopId(pojo.getShopId());
		if(fs == null){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		
		if(!fs.getIsAuthCoupon()){
			return Status.emp_not_permission;
		}
		
		addPojos(pojo,fs);
		
		return Status.success;
	}

	
	private void addPojos(ShopCoupons pojo, FishShop fs) throws Exception{
		
		//设置收费
		couponFeesConfigService.setFees(2, pojo);
		//保存
		shopCouponsMapper.insertPojo(pojo);
		
		//发券
		giveCoupon(pojo, pojo.getUids(),fs);
		
//		Calendar cc = ExDateUtils.getCalendar();
//		List<Coupons> cs = new ArrayList<Coupons>();
//		Coupons c = null;
//		for (Long uid : pojo.getUids()) {
//			c = new Coupons();
//			c.setCategory(3);
//			c.setCouponFee(pojo.getCouponFee());
//			c.setCouponName(pojo.getCouponName());
//			c.setDeadline(pojo.getEndTime());
//			c.setDesc("店铺优惠券");
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
//		List<ShopCouponsRecord> scsr = new ArrayList<ShopCouponsRecord>();
//		ShopCouponsRecord scr = null;
//		for (Coupons coupons : cs) {
//			scr = new ShopCouponsRecord();
//			scr.setCid(coupons.getCid());
//			scr.setId(pojo.getId());
//			scr.setShopId(pojo.getShopId());
//			scr.setUid(coupons.getUid());
//			scsr.add(scr);
//		}
//		//优惠券记录
//		shopCouponsRecordMapper.insertBatch(scsr);
	}
	
	
	private void giveCoupon(ShopCoupons pojo,Collection<Long> uids, FishShop fs) throws Exception{
		Calendar cc = ExDateUtils.getCalendar();
		List<Coupons> cs = new ArrayList<Coupons>();
		Coupons c = null;
		for (Long uid : uids) {
			c = new Coupons();
			c.setCategory(3);
			c.setCouponFee(pojo.getCouponFee());
			c.setCouponName(pojo.getCouponName());
			c.setDeadline(pojo.getEndTime());
			c.setDesc(fs.getName() + "优惠券");
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
		
		List<ShopCouponsRecord> scsr = new ArrayList<ShopCouponsRecord>();
		ShopCouponsRecord scr = null;
		for (Coupons coupons : cs) {
			scr = new ShopCouponsRecord();
			scr.setCid(coupons.getCid());
			scr.setId(pojo.getId());
			scr.setShopId(pojo.getShopId());
			scr.setUid(coupons.getUid());
			scsr.add(scr);
			
		}
		//优惠券记录
		shopCouponsRecordMapper.insertBatch(scsr);
		
		//消息
		SystemMsg sm = new SystemMsg();
		sm.setContent(fs.getName() + pojo.getCouponName());
		sm.setCreateTime(ExDateUtils.getDate());
		sm.setFlag(0);
		sm.setTitle("恭喜您！您获得了一张优惠券");
		
		systemMsgService.saveMsgBatch(sm, uids.toArray());
		
		//推送消息
		for (Long uid : uids) {
			jPushService.sendPush(uid.intValue(), "C", "店铺券", "您获得了店铺优惠券了", null);
		}
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#addRoutinePojo(cn.heipiao.api.pojo.ShopCoupons)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int addRoutinePojo(ShopCoupons pojo) throws Exception {
		FishShop fs = fishShopMapper.selectFishShopByShopId(pojo.getShopId());
		if(fs == null){
			return Status.FISH_SHOP_NOT_EXISTS;
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
				uids = shopTradeOrdersMapper.selectUidsByShopId(pojo.getShopId());
				break;
			case 2:
				uids = fishShopMapper.selectUidsByShopId(pojo.getShopId());
				break;
			case 3:
				uids = likeFocusMapper.selectUidsByUid(fs.getUid().longValue());
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
	 * @see cn.heipiao.api.service.ShopCouponsService#modifyShopCouponStatus(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifyShopCouponStatus(Long uid, Long id, Integer status) {
		ShopCoupons sc = shopCouponsMapper.selectAslockById(id);
		if(sc == null || sc.getStatus().intValue() == 3){
			return Status.value_is_null_or_error;
		}
		FishShop fs = fishShopMapper.selectFishShopAsLockByShopId(sc.getShopId());
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
			shopCouponsMapper.updateShopCouponStatus(sc.getId(), 2);
			fishShopMapper.updateIsCouponAndCount(sc.getShopId(), true,
					fs.getCouponCount() - (sc.getAmount() - sc.getCount()) >= 0 ? fs.getCouponCount() - (sc.getAmount() - sc.getCount()) : 0);
			return Status.success;
		}
		if(status == 0){
			//如果为0则为发布优惠券中，发布中并更新数量
			fishShopMapper.updateIsCouponAndCount(sc.getShopId(), true,fs.getCouponCount() + (sc.getAmount() - sc.getCount()));
		}else{
			//如果是暂停某个优惠券则判断优惠券是否是<=1
			if(shopCouponsMapper.countNormal(sc.getShopId()) <= 1){
				//如果<=1关闭优惠券发布中
				fishShopMapper.updateIsCouponAndCount(sc.getShopId(), false,0);
			}else{
				//如果是>1则之暂停某个优惠券
				fishShopMapper.updateIsCouponAndCount(sc.getShopId(), true,
						fs.getCouponCount() - (sc.getAmount() - sc.getCount()) >= 0 ? fs.getCouponCount() - (sc.getAmount() - sc.getCount()) : 0);
			}
		}
		shopCouponsMapper.updateShopCouponStatus(id,status);
		return Status.success;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getShopUsers(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public List<ShopCouponsRecord> getShopUsers(Long shopId, Integer flag) { 
		List<ShopCouponsRecord> scl = null;
		switch (flag) {
		case 1:
			scl = shopCouponsRecordMapper.selectConsumeUserByShopId(shopId);
			break;
		case 2:
			scl = shopCouponsRecordMapper.selectFocusShopByShopId(shopId);
			break;
		case 3:
			FishShop fs = fishShopMapper.selectFishShopByShopId(shopId);
			if(fs != null && fs.getUid() != null){
				scl = shopCouponsRecordMapper.selectFocusByUid(fs.getUid().longValue());
			}
			break;
		}
		return scl;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#isOpenCoupon(java.lang.Long)
	 */
	@Override
	public Boolean isOpenCoupon(Long shopId) {
		FishShop fs = fishShopMapper.selectFishShopByShopId(shopId);
		if(fs != null && fs.getStatus() == 1){
			return fs.getOpenCoupon();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getShopCoupon(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int getShopCoupon(Long uid, Long shopId, Long id) throws Exception {
		boolean b = shopCouponsRecordMapper.isGet(id, uid);
		if(b){
			//返回已经领取过优惠券 
			return Status.coupon_existed;
		}
		FishShop fs = fishShopMapper.selectFishShopAsLockByShopId(shopId);
		if(fs == null || fs.getStatus() != 1 || !fs.getOpenCoupon()
				|| fs.getId().longValue() != shopId.longValue() || !fs.getIsCoupon() || !fs.getIsAuthCoupon()){
			//返回优惠券被抢完了
			return Status.coupon_is_zore;
		}
		ShopCoupons sc = shopCouponsMapper.selectAslockById(id);
		if(sc.getStatus() != 0){
			//返回优惠券被抢完了
			return Status.coupon_is_zore;
		}
		//判断数量
		if(sc.getCount() + sc.getLimit() > sc.getAmount()){
			shopCouponsMapper.updateShopCouponStatus(sc.getId(), 3);
			if(shopCouponsMapper.countNormal(sc.getShopId()) <= 0){
				fishShopMapper.updateIsCouponAndCount(fs.getId(),false,0);
			}else{
				fishShopMapper.updateIsCouponAndCount(fs.getId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
			//返回优惠券被抢完了
			return Status.coupon_is_zore;
		}
		
		//判断时间是否过期
//		if(sc.getEndTime().getTime() <= ExDateUtils.getCalendar().getTimeInMillis()){
		if(CouponUserServiceImpl.isTimeoutByCoupons(sc.getEndTime())){
			shopCouponsMapper.updateShopCouponStatus(sc.getId(), 2);
			if(shopCouponsMapper.countNormal(sc.getShopId()) <= 0){
				fishShopMapper.updateIsCouponAndCount(fs.getId(),false,0);
			}else{
				fishShopMapper.updateIsCouponAndCount(fs.getId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
			return Status.coupon_is_zore;
		}
		//发券
		List<Long> uids = new ArrayList<Long>();
		for(int i = 0 ; i < sc.getLimit(); i++){
			uids.add(uid);
		}
		giveCoupon(sc,uids,fs);
		sc.setCount(sc.getCount() + sc.getLimit());
		if(sc.getCount() >= sc.getAmount()){
			sc.setStatus(3);
		}
		shopCouponsMapper.updatePojo(sc);
		if(sc.getCount() >= sc.getAmount() && shopCouponsMapper.countNormal(sc.getShopId()) <= 0){
			fishShopMapper.updateIsCouponAndCount(fs.getId(),false,0);
		}else{
			fishShopMapper.updateIsCouponAndCount(fs.getId(),true,
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
		ShopCoupons sc = shopCouponsMapper.selectAslockById(id);
		if(sc.getStatus().intValue() < 2){
			FishShop fs = fishShopMapper.selectFishShopAsLockByShopId(sc.getShopId());
			shopCouponsMapper.updateShopCouponStatus(sc.getId(), 2);
			if(shopCouponsMapper.countNormal(sc.getShopId()) <= 0){
				fishShopMapper.updateIsCouponAndCount(fs.getId(),false,0);
			}else{
				fishShopMapper.updateIsCouponAndCount(fs.getId(),true,
						fs.getCouponCount() - sc.getLimit() >= 0 ? fs.getCouponCount() - sc.getLimit() : 0);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getDetailList(java.lang.Long)
	 */
	@Override
	public List<ShopCouponsRecord> getDetailList(Long shopId,Map<String,Object> map) {
		return shopCouponsRecordMapper.selectList(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getNotGetList(java.lang.Long)
	 */
	@Override
	public List<ShopCoupons> getNotGetList(Long shopId, Long uid) {
		return shopCouponsMapper.selectNotGetList(shopId,uid,new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#countUids(java.lang.Long, java.lang.String[])
	 */
	@Override
	public int countUids(Long shopId, String[] fs) {
		Set<Long> uids = new HashSet<Long>();
		Set<Long> us = null;
		for(String flag : fs){
			switch (Integer.parseInt(flag)) {
			case 1:
				us = shopTradeOrdersMapper.selectUidsByShopId(shopId);
				break;
			case 2:
				us = fishShopMapper.selectUidsByShopId(shopId);
				break;
			case 3:
				us = likeFocusMapper.selectUidsByUid(shopId);
				break;
			}
			if(us != null && us.size() > 0){
				uids.addAll(us);
			}
		}
		return uids.size();
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.ShopCouponsService#getNewListByShopId(java.util.Map)
	 */
	@Override
	public List<ShopCoupons> getNewListByShopId(Map<String, Object> map) {
		return shopCouponsMapper.selectNewListByShopId(map);
	}

}
