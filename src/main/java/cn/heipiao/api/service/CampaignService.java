package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.CampaignDetail;
import cn.heipiao.api.pojo.CampaignType;

/**
 * 活动相关
 * @author Chris
 * @version 3.0
 * @date 2017.03.06
 *
 */
public interface CampaignService {
	
	/**
	 * 添加活动
	 * @param campaign
	 * @return
	 */
	public void addCampaign(Campaign campaign);
	
	/**
	 * 修改活动
	 * @param campaign
	 * @return
	 */
	public boolean updateCampaign(Campaign campaign);

	/**
	 * 获取活动
	 * @param id 活动id
	 * @return
	 */
	public Campaign getCampaign(int id);
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public Map<String, Object> getCampaignList(int start, int size);
	
	/**
	 * 获取所有活动参与人
	 * @param cid 活动id
	 * @param uid 参与人id
	 * @return
	 */
	public CampaignActor getCampaignActor(int cid, int uid);
	
	/**
	 * 获取活动参与人数
	 * @param id 活动id
	 * @return
	 */
	public Integer getCampaignActorCount(int id);
	
	/**
	 * 获取所有活动参与人
	 * @param id 活动id
	 * @param top 取前几位
	 * @return
	 */
	public List<CampaignActor> getCampaignActorList(int id, int top);

	/**
	 * 发布活动
	 * @param id 活动id
	 * @return
	 */
	public boolean publishCampaign(int id);
	
	/**
	 * 删除活动
	 * @param id 活动id
	 * @return
	 */
	public boolean delCampaign(int id);
	
	/**
	 * 抽签
	 * @param id 活动id
	 * @return
	 * @throws Exception 
	 */
	public String draw(int id) throws Exception;
	
	/**
	 * 获取活动详情
	 * @param id 活动id
	 * @return
	 */
	public CampaignDetail getCampaignDetail(int id);
	
	/**
	 * 启动活动
	 * @param id 活动id
	 * @param value 状态值，仅为为1已发布或2待抽签
	 * @return
	 */
	public boolean start(int id, int value);
	
	/**
	 * 结束活动
	 * @param id 活动id
	 * @return
	 */
	public boolean finish(int id);
	
	/**
	 * 暂停活动
	 * @param id 活动id
	 * @return
	 */
	public boolean pause(int id);
	
	/**
	 * 获取活动备注
	 * @param id 活动id
	 * @return
	 */
	public String getCampaignRemark(int id);
	
	/**
	 * 设置活动备注
	 * @param id 活动id
	 * @param remark 新备注信息
	 * @return
	 */
	public boolean setCampaignRemark(int id, String remark);
	
	/**
	 * 取消活动
	 * @param id 活动id
	 * @return
	 */
	public boolean cancel(int id);
	
	/**
	 * 报名
	 * @param uid 用户id
	 * @param cid 活动id
	 * @param openid 报名时间
	 * @param payType 支持类型
	 * @return
	 * @throws Exception 
	 */
	public String enter(Long uid, int cid, String openid, int payType) throws Exception;

	/**
	 * 报名确认
	 * @param uid
	 * @param cid
	 * @return
	 * @throws Exception 
	 */
	public int payActivityConfirm(Long uid, Integer cid) throws Exception;

	public int cancelEnter(String orderId) throws Exception;

	List<Campaign> getCampaignListByNormal(int start, int size);

	public List<CampaignType> getCampaignTypesAll();

	public void putVideo(Campaign camp);

	Campaign getCampaignById(int id);
	
	/**
	 * 报名
	 * @param uid 用户id
	 * @param cid 活动id
	 * @param entryTime 报名时间
	 * @param payAmount 支付金额
	 * @return
	 */
//	public boolean enter(Integer uid, Integer cid, Timestamp entryTime, Float payAmount);

}
