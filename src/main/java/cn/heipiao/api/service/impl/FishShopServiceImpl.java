package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.constant.FishShopConstant;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.PartnerOverMapper;
import cn.heipiao.api.mapper.PartnerShopRateConfigMapper;
import cn.heipiao.api.mapper.ShopCouponsMapper;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishShopUserStatus;
import cn.heipiao.api.pojo.PartnerOver;
import cn.heipiao.api.pojo.PartnerShopRateConfig;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.FishShopService;

/**
 * 
 * @author asdf3070@163.com
 * @date 2016年10月11日
 * @version 2.0
 */
@Service
@Transactional(readOnly = true)
public class FishShopServiceImpl implements FishShopService{

	@Resource
	private FishShopMapper fishShopMapper;
	
	@Resource
	private EmpService empService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private ShopCouponsMapper shopCouponsMapper;
	
	@Resource
	private PartnerOverMapper partnerOverMapper;
	
	@Resource
	private PartnerShopRateConfigMapper partnerShopRateConfigMapper;
	
	private static final Object obj = new Object();
	private static final int max = 100000;

	/**
	 * 通过渔具店所属用户唯一标识查询渔具店列表数据（多行）
	 * @param uid
	 * @return
	 */
	@Override
	public List<FishShop> getFishShopByUid(Integer uid) {
		return fishShopMapper.selectFishShopByUid(uid);
	}

	/**
	 * 通过渔具店所属用户唯一标识查询渔具店全部数据（多行）
	 * @param uid
	 * @return
	 */
	@Override
	public List<FishShop> getFishShopAllByUid(Integer uid) {
		return fishShopMapper.selectFishShopAllByUid(uid);
	}

	/**
	 * 通过渔具店唯一标识修改渔具店信息
	 * @param fishShop
	 * @return
	 */
	@Override
	@Transactional
	public void setFishShop(FishShop fishShop) {
		fishShopMapper.updateFishShopById(fishShop);
	}

	/**
	 * 通过渔具店唯一标识查询指定的渔具店
	 * @param id
	 * @return
	 */
	@Override
	public FishShop getFishShopById(Long id, Integer uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("uid", uid);
		return fishShopMapper.selectFishShopById(map);
	}

	/**
	 * 新建渔具店
	 */
	@Override
	@Transactional
	public long addFishShop(FishShop fishShop) throws Exception {
		synchronized (obj) {
			Integer c = fishShopMapper.countCityFishShop(fishShop.getCityId());
			if (c >= max - 1) {
				throw new Exception("fishsite count not greater than " + max + " of city");
			}
			long cityid = fishShop.getCityId();
			
			fishShop.setId(cityid * max + c + 1);
			fishShopMapper.insertFishShop(fishShop);
			return fishShop.getId();
		}
	}

	/**
	 * 指定渔具店店主
	 * @param fishShop
	 * @return
	 * @throws Exception 
	 */
	@Override
	@Transactional
	public int setFishShopUid(FishShop fishShop) throws Exception {
		int num = fishShopMapper.updateFishShopUidAuthStatus(fishShop);
		empService.addShopMan(fishShop.getUid().intValue(), fishShop.getId());
		//添加财务账户
		accountService.addShopAccount(fishShop.getUid().longValue(), fishShop.getId());
		return num;
	}

	/**
	 * 指定渔具店签约认证
	 */
	@Override
	@Transactional
	public int setFishShopPactPass(FishShop pojo) {
		pojo.setAuthStatus(FishShopConstant.AUTH_PACT_PASS);
		int count = fishShopMapper.updateFishShopUidAuthStatus(pojo);
		//fishShopMapper.deleteFishShopSign(pojo);
		return count;
	}

	/**
	 * 查询渔具店(分类检索)
	 */
	@Override
	public List<FishShop> queryList(Map<String, Object> map) {
		return fishShopMapper.queryList(map);
	}

	/**
	 * 查询渔具店(分类检索),总记录数
	 */
	@Override
	public int queryListCount(Map<String, Object> map) {
		return fishShopMapper.queryListCount(map);
	}

	/**
	 * 指定渔具店置顶
	 * @param id
	 */
	@Override
	@Transactional
	public int setFishShopTop(Map<String, Object> map) {
		return fishShopMapper.updateFishShopTop(map);
	}
	/**
	 * 获取当前置顶的最大值
	 * @return
	 */
	@Override
	public int getMaxTop() {
		return fishShopMapper.selectMaxTop();
	}


	/**
	 * 查询指定用户及渔具店的用户状态信息
	 * @param map
	 * @return
	 */
	@Override
	public FishShopUserStatus getFishShopUserStatus(FishShopUserStatus userStatus){
		return fishShopMapper.selectFishShopUserStatus(userStatus);
	}

	/**
	 * 删除指定用户及渔具店的用户状态信息
	 * @param map
	 * @return
	 */
	@Override
	@Transactional
	public int delFishShopUserStatus(FishShopUserStatus userStatus){
		return fishShopMapper.deleteFishShopUserStatus(userStatus);
		
	}

	/**
	 * 添加指定用户及渔具店的用户状态信息
	 * @param map
	 * @return
	 */
	@Override
	@Transactional
	public int addFishShopUserStatus(FishShopUserStatus userStatus){
		return fishShopMapper.insertFishShopUserStatus(userStatus);
		
	}

	/**
	 * 指定渔具店设置规模分类
	 */
	@Override
	@Transactional
	public int setFishShopScale(FishShop pojo) {
		return fishShopMapper.updateFishShopScale(pojo);
	}

	@Override
	public List<FishShop> getAllFishShop() {
		return fishShopMapper.selectAllFishShop();
	}

	@Override
	public FishShop getFishShopById(Long id) throws Exception {
		return this.fishShopMapper.selectFishShopByShopId(id);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FishShopService#modifyOpenCoupon(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifyOpenCoupon(Long uid, Long shopId) {
		FishShop fs = fishShopMapper.selectFishShopByShopId(shopId);
		if(fs == null || fs.getStatus() != 1){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		if(!fs.getIsAuthCoupon()){
			return Status.coupon_not_auth;
		}
		fishShopMapper.updateOpenCoupon(shopId);
		return Status.success;
	}

	@Override
	public int queryPartnerListCount(Map<String, Object> map) {
		return fishShopMapper.selectPartnerListCount(map);
	}

	@Override
	public List<FishShop> queryPartnerList(Map<String, Object> map) {
		return fishShopMapper.selectPartnerList(map);
	}
	@Override
	public List<PartnerOver> queryPartnerOverList(Integer partnerId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerId", partnerId);
		params.put("type", 1);
		List<PartnerOver> list = partnerOverMapper.selectByBid(params);
		return list;
	}
	/**
	 * 删除指定用户及渔具店的用户状态信息（硬盘）
	 */
	@Override
	@Transactional
	public int delFishShopUserStatus4HD(FishShopUserStatus userStatus) {
		return fishShopMapper.deleteFishShopUserStatus4HD(userStatus);
	}

	/**
	 * 更新根据渔具店的用户收藏状态信息更新渔具店主表的关注数量
	 */
	@Override
	@Transactional
	public int updateFishShopFocusCount(Long id) {
		return fishShopMapper.updateFishShopFocusCount(id);
	}

	@Override
	public List<FishShopUserStatus> getAllFSUSShopId() {
		return fishShopMapper.getAllFSUSShopId();
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FishShopService#modifyIsAuthCoupon(java.lang.Long, java.lang.Long, java.lang.Boolean)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int modifyIsAuthCoupon(Long uid, Long shopId, Boolean isAuthCoupon) {
		int count = 0;
		if(isAuthCoupon){
			count = shopCouponsMapper.countNormal(shopId);
		} 
		fishShopMapper.updateIsAuthCoupon(isAuthCoupon, shopId,count > 0);
		return Status.success;
	}

	/**
	 * 查询指定用户所关注的店铺（数据）
	 */
	@Override
	public List<FishShop> queryCollectList(Map<String, Object> map) {
		return fishShopMapper.selectCollectList(map);
	}

	/**
	 * 查询指定用户所关注的店铺（数量）
	 */
	@Override
	public int queryCollectListCount(Map<String, Object> map) {
		return fishShopMapper.selectCollectListCount(map);
	}

	/**
	 * 指定店铺设置图标logo
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int setLogo(FishShop pojo) {
		return fishShopMapper.updateLogo(pojo); 
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.FishShopService#changeFishShop(java.lang.Long, java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int changeFishShop(Long shopId, Long newUid) throws Exception {
		FishShop fs = fishShopMapper.selectFishShopByShopId(shopId);
		if(fs == null){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		
		//删除店铺所有的员工以及权限
		empService.removeShopEmpAll(fs.getId());
		
		//设置新老板
		fs.setUid(newUid.intValue());
		fishShopMapper.updateFishShopUidAuthStatus(fs);
		//创建员工
		empService.addShopMan(newUid.intValue(), shopId);
		//修改财务账户uid
		accountService.addShopAccount(newUid, shopId);
		return Status.success;
	}

	@Override
	public List<FishShop> queryDistributionList(Map<String, Object> map) {
		
		return fishShopMapper.queryDistributionList(map);
	}

	@Override
	public PartnerShopRateConfig selectShopRateConfig(Long shopId, Integer partnerId) {
		PartnerShopRateConfig partnerShopRateConfig =partnerShopRateConfigMapper.selectById(partnerId, shopId);
		
		return partnerShopRateConfig;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateShopRateConfig(Long shopId, Integer partnerId, Double shopRate) {
		PartnerShopRateConfig partnerShopRateConfig = partnerShopRateConfigMapper.selectById(partnerId, shopId);
		if(partnerShopRateConfig!=null){
			partnerShopRateConfig.setShopId(shopId);
			partnerShopRateConfig.setPartnerId(partnerId);
			partnerShopRateConfig.setShopRate(shopRate);
			partnerShopRateConfigMapper.updatePojo(partnerShopRateConfig);
		}else{
		partnerShopRateConfig = new PartnerShopRateConfig();
		partnerShopRateConfig.setShopId(shopId);
		partnerShopRateConfig.setPartnerId(partnerId);
		partnerShopRateConfig.setShopRate(shopRate);
		partnerShopRateConfigMapper.insertPojo(partnerShopRateConfig);
		}
		return Status.success;
	}

	@Override
	@Transactional(readOnly=false)
	public int updateFishShopSignUser(Long shopId) {
		FishShop fishShop=fishShopMapper.selectFishShopByShopId(shopId);
		if(fishShop==null){
			return Status.FISH_SHOP_NOT_EXISTS;
		}
		fishShopMapper.updateFishShopAuthUid(shopId);
		return Status.success;
	}
	@Override
	public List<FishShop> selectFishShopByStatus(Integer partnerId,Integer status) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("partnerId", partnerId);
		List<FishShop> list= null;
		switch(status){
			case 1: status=0;break;
			case 4: status=2;break;
			case 2: status=3;break;
		}
		map.put("status", status);
		list=fishShopMapper.queryFishSignShopStatusByPartner(map);
		return list;
	}
}
