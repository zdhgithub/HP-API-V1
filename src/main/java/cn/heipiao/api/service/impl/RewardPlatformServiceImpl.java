package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.ModuleMapper;
import cn.heipiao.api.mapper.RewardConfigPlatformMapper;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.FishShop;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.Module;
import cn.heipiao.api.pojo.RewardPlatform;
import cn.heipiao.api.pojo.ShopEmp;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.FishShopService;
import cn.heipiao.api.service.FishSiteService;
import cn.heipiao.api.service.RewardPlatformService;
/**
 * 
 * @ClassName: RewardPlatformServiceImpl
 * @Description: 平台奖励配置
 * @author duzh
 * @date 2017年1月16日
 */

@Service
@Transactional
public class RewardPlatformServiceImpl implements RewardPlatformService{
	@Resource
	private RewardConfigPlatformMapper rewardConfigPlatformMapper;
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private FishShopService fishShopService;
	@Resource
	private FishSiteService fishSiteService;
	@Resource
	private EmpService empService;
	@Resource
	private EmpMapper empMapper;
	/**
	 * cp通过商家id商家类型查询
	 */
	@Override
	public RewardPlatform findRewardPlatformByBid(Long bid, int type) {
		Map<String,Object> params = new HashMap<String, Object>();
		if(type == 0){
			params.put("bid", bid);
		}else{
			params.put("bid", bid.intValue());
		}
		params.put("type", type);
		RewardPlatform rewardPlatform=rewardConfigPlatformMapper.selectRewardPlatformByBid(params);
		return rewardPlatform;
	}
	/**
	 * cp添加/修改商家平台奖励配置
	 */
	@Override
	public int insertRewardPlatform(RewardPlatform rewardPlatform) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("bid", rewardPlatform.getBid());
		params.put("type", rewardPlatform.getType());
		RewardPlatform reward=rewardConfigPlatformMapper.selectRewardPlatformByBid(params);
		if(reward!=null){
			params.put("bonus", rewardPlatform.getBonus());
			params.put("percent", rewardPlatform.getPercent());
			params.put("partnerBonus", rewardPlatform.getPartnerBonus());
			rewardConfigPlatformMapper.updateRewardPlatform(params);
		}else{
			rewardConfigPlatformMapper.insertRewardPlatform(rewardPlatform);
		}
		return Status.success;
	}
	/**
	 * cp删除商家平台奖励配置（同时关闭B端平台奖励权限）
	 * @throws Exception 
	 */
	@Override
	public int update(Long bid,Integer status,Integer type) throws Exception {
		if(type==0){
			
			//渔具店员工权限设置
			List<ShopEmp> shopEmps = empService.getShopEmps(bid);
			for (ShopEmp shopEmp : shopEmps) {	
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("empId", shopEmp.getUid());
				param.put("moduleId",409);
				param.put("shopId", bid);
				if(status==0){
					moduleMapper.deleteShopPlatformModule(param);
				}else{
					Module module = moduleMapper.selectShopModuleofOneId(shopEmp.getUid(), bid, 409);
					if(module !=null){
						return 0;
					}
					moduleMapper.insertShopPlatform(param);
				}
			}
		}else if(type==1){
			Map<String, String> params = new LinkedMap<String, String>();
			params.put("fishSiteId",bid.toString());
			List<Emp> emps = empMapper.selectList(params);
			for (Emp emp : emps) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("empId", emp.getUserId());
				map.put("moduleId",50);
				if(status == 0){	
					moduleMapper.deleteSitePlatformModule(map);
				}else{
					Module module = moduleMapper.selectOneModuleId(emp.getUserId(),50);
					if(module !=null){
						return 0;
					}
					moduleMapper.insertSitePlatform(map);
				}
			}
		}
		rewardConfigPlatformMapper.update(bid,status,type);
		return Status.success;
	}
	@Override
	public RewardPlatform findRewardPlatform(Long bid, int type,int status) {
		Map<String,Object> params = new HashMap<String, Object>();
		if(type == 0){
			params.put("bid", bid);
		}else{
			params.put("bid", bid.intValue());
		}
		params.put("type", type);
		params.put("status", status);
		RewardPlatform rewardPlatform=rewardConfigPlatformMapper.selectRewardPlatformByBid(params);
		return rewardPlatform;
	}
}
