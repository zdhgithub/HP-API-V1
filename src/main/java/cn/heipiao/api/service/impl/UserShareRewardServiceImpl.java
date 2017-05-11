/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.ArticleMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.UserShareCityConfigMapper;
import cn.heipiao.api.mapper.UserShareGlobalConfigMapper;
import cn.heipiao.api.mapper.UserShareRewardMapper;
import cn.heipiao.api.pojo.Article;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.UserShareCityConfig;
import cn.heipiao.api.pojo.UserShareReward;
import cn.heipiao.api.service.AccountService;
import cn.heipiao.api.service.UserShareOrdersService;
import cn.heipiao.api.service.UserShareRewardService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年11月26日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserShareRewardServiceImpl implements UserShareRewardService {

	@Resource
	private UserShareGlobalConfigMapper userShareGlobalConfigMapper;
	
	@Resource
	private UserShareCityConfigMapper userShareCityConfigMapper;
	
	@Resource
	private UserShareRewardMapper userShareRewardMapper;
	
	@Resource
	private UserShareOrdersService userShareOrdersService;
	
	@Resource
	private ArticleMapper<Article> articleMapper;
	
	@Resource
	private FishSiteMapper fishSiteMapper;
	
	@Resource
	private AccountService accountService;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.UserShareRewardService#shareReward(java.lang.Long, java.lang.Long, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public int shareReward(Long uid, Long shareId) {
		long t = ExDateUtils.getCalendar().getTimeInMillis();
		int ramount = 0; 
		
		Boolean status = userShareGlobalConfigMapper.selectStatus();
		if(status == null || !status){
			return ramount;
		}
		
		//验证shareId是否存在
		Article art = articleMapper.selectById(shareId);
		if(art == null){
			return ramount;
		}
		//此shopId为钓场id
		FishSite fs = fishSiteMapper.selectById(art.getShopId().intValue());
		if(fs == null || fs.getStatus().intValue() != 1){
			return ramount;
		}
		
		UserShareCityConfig usc = userShareCityConfigMapper.selectByCityId(fs.getCityId());
		if(usc == null || !usc.getStatus()){
			return ramount;
		}
		
		UserShareReward usr = userShareRewardMapper.selectAsLockByUid(uid);
		if(usr == null){
			usr = new UserShareReward();
			usr.setAmount(0);
			usr.setTimes(0);
			usr.setCreateTime(new Timestamp(t));
			usr.setUid(uid);
			usr.setUpdateTime(new Timestamp(t));
			userShareRewardMapper.insertPojo(usr);
		}

		//判断是否为当天
		if(ExDateUtils.isCurrentDay(usr.getUpdateTime())){
			if(usr.getTimes() < usc.getLimit()) {
				int c = usc.getLimit() - usr.getTimes();
				int a = usc.getAmount() - usr.getAmount();
				if(c == 1){
					ramount = a;
				}else{
					ramount = a / c;
				}
				usr.setAmount(usr.getAmount() + ramount);
				usr.setTimes(usr.getTimes() + 1);
			}
		} else {
			if(usc.getLimit() > 0){
				ramount = usc.getAmount() / usc.getLimit();
				usr.setAmount(ramount);
				usr.setTimes(1);
				usr.setUpdateTime(new Timestamp(t));
			}
		}
		
		if(ramount > 0){
			accountService.addGoldCoin(uid, 0, ramount);
			//添加订单
			userShareOrdersService.addShareOrder(uid, ramount, shareId);
			//更新
			userShareRewardMapper.updatePojo(usr);
		}
		
		return ramount;
	}

}
