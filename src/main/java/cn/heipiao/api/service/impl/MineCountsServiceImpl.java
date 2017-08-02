package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.heipiao.api.constant.ApiConstant;
import cn.heipiao.api.mapper.CouponsMapper;
import cn.heipiao.api.mapper.DepositFishMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.FocusMapper;
import cn.heipiao.api.mapper.LikeFocusMapper;
import cn.heipiao.api.mapper.OrdersMapper;
import cn.heipiao.api.mapper.SystemMsgMapper;
import cn.heipiao.api.mapper.UserGoldCoinMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.pojo.Coupons;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.pojo.UserGoldCoin;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.service.MineCountsService;
/**
 * 
 * @ClassName: MineCountsServiceImpl
 * @Description: TODO
 * @author zf
 */
@Service
public class MineCountsServiceImpl implements MineCountsService {
	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private CouponsMapper couponsMapper;
	@Resource
	private UserTicketsMapper userTicketsMapper;
	@Resource
	private SystemMsgMapper systemMsgMapper;
	@Resource
	private DepositFishMapper depositFishMapper;
	@Resource
	private OrdersMapper ordersMapper;
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private FocusMapper focusMapper;
	@Resource
	private LikeFocusMapper<?> likeFocusMapper;
	@Resource
	private UserGoldCoinMapper userGoldCoinMapper;

	@Override
	public Map<String, Object> getResult(Integer userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("partnerId", userId);
		// 合伙人签约钓场
		List<FishSite> sites = fishSiteMapper.selectSitesByPartnerId(params);
		// 我的优惠券(可用)
		// params.put("userId", userId);
		// params.put("status", ApiConstant.CouponUserStatus.UNUSED);
		List<Coupons> coupons = couponsMapper.selectListByUserAndStatus(userId,
				ApiConstant.CouponUserStatus.UNUSED);
		// 我的钓场券，门票
		params.put("uid", userId);
		params.put("status", ApiConstant.TicketStatus.UNUSED);
		 params.put("start", 0);
		 params.put("size", 1000);
		List<UserTickets> tickets = userTicketsMapper
				.selectTicketsByUid(params);
		
		//我的存鱼
		Double depositFishNum = this.depositFishMapper.countDepositFishTotalByUserID(userId);
		//我的订单
		Integer orderNum = this.ordersMapper.countOrders(userId);
		//我的优惠券（新增标识）
		boolean couponNew = this.couponsMapper.countCouponsNew(userId)>0?true:false;
		//我的关注
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("uid", userId);
		Integer shopNum = this.fishShopMapper.selectCollectListCount(map);
		Integer fishSiteNum = this.focusMapper.countFocus(userId); 
		Integer userNum = this.likeFocusMapper.countFocusOther(userId);
		//我的消息
		Integer MsgNum = this.systemMsgMapper.countMsg(userId);
		//我的消息（新增标志）
		boolean MsgNew = this.systemMsgMapper.countMsgNew(userId)>0?true:false;
		//我的收益提现
		UserGoldCoin userGold =userGoldCoinMapper.selectByUid(userId.longValue());
		if(userGold!=null){
			Integer coinNum = userGold.getEarningsGoldCoin();
		
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("siteNum", sites.size());
			result.put("couponNum", coupons.size());
			result.put("couponNew", couponNew);
			result.put("ticketNum", tickets.size());
			result.put("messageNum", MsgNum);
			result.put("MsgNew", MsgNew);
			result.put("depositFishNum", depositFishNum);
			result.put("orderNum", orderNum);
			result.put("focusNum", shopNum+fishSiteNum+userNum);
			result.put("coinNum", coinNum);
			return result;
		}else{
			return null;
		}
	}

}
