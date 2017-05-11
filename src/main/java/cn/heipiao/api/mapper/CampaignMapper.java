package cn.heipiao.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.Campaign;
import cn.heipiao.api.pojo.CampaignActor;
import cn.heipiao.api.pojo.CampaignType;

/**
 * 活动相关
 * @author Chris
 * @version 3.0
 * @date 2017.03.06
 *
 */
public interface CampaignMapper {
	
	/**
	 * 添加活动
	 * @param campaign
	 */
	public int addCampaign(Campaign campaign);
	
	/**
	 * 更新活动
	 * @param campaign
	 */
	public int updateCampaign(Campaign campaign);
	
	/**
	 * 获取活动
	 * @param id 活动id
	 * @return
	 */
	public Campaign getCampaign(@Param("id")Integer id);
	
	/**
	 * 获取活动
	 * 只查询当前id的单表记录
	 * @param id 活动id
	 * @return
	 */
	public Campaign getCampaignById(@Param("id")Integer id);
	
	/**
	 * 获取活动列表
	 * @return
	 */
	public List<Campaign> getCampaignList(@Param("start")Integer start, @Param("size")Integer size);
	
	/**
	 * 获取所有活动数量
	 * @return
	 */
	public Integer getCampaignCount();
	
	/**
	 * 获取活动列表（除了未发布的）
	 * @return
	 */
	public List<Campaign> getCampaignListByNormal(@Param("start")Integer start, @Param("size")Integer size);
	
	
	
	/**
	 * 获取指定活动参与人
	 * @param cid 活动id
	 * @param uid 参与人id
	 * @return
	 */
	public CampaignActor getCampaignActor(@Param("cid")Integer cid, @Param("uid")Integer uid);
	
	/**
	 * 获取所有活动参与人
	 * @param id 活动id
	 * @return
	 */
	public List<CampaignActor> getCampaignActorList(@Param("id")Integer id, @Param("top")Integer top);
	
	/**
	 * 获取所有活动参与人
	 * @param id 活动id
	 * @return
	 */
	public List<CampaignActor> getCampaignActorLists(Integer id);
	
	/**
	 * 设置活动状态
	 * @param id 活动id
	 * @param status 状态值
	 * @return
	 */
	public int setCampaignStatus(@Param("id")Integer id, @Param("status")Integer status);
	
	/**
	 * 删除活动（仅能删除未发布活动）
	 * @param id 活动id
	 * @return
	 */
	public int delCampaign(@Param("id")Integer id);
	
	/**
	 * 设置签号
	 * @param id 活动id
	 * @param luckyNumber 签号
	 */
	public int setLuckyNumber(@Param("orderId")String orderId, @Param("luckyNumber")Integer luckyNumber);
	
	/**
	 * 获取活动详情之总人数
	 * @param id 活动id
	 * @return
	 */
	public Integer getCampaignActorCount(@Param("id")Integer id);
	
	/**
	 * 获取活动详情之总费用
	 * @param id 活动id
	 * @return
	 */
	public Float getCampaignActorPayAmount(@Param("id")Integer id);
	
	/**
	 * 获取活动备注
	 * @param id 活动id
	 * @return
	 */
	public String getCampaignRemark(@Param("id")Integer id);
	
	/**
	 * 设置活动备注
	 * @param id 活动id
	 * @param remark 新备注信息
	 * @return
	 */
	public int setCampaignRemark(@Param("id")Integer id, @Param("remark")String remark);
	
	/**
	 * 报名
	 * @param ca 
	 * @return
	 */
	public int addActor(CampaignActor ca);

	/**
	 * 添加行级索
	 * @param cid
	 * @return
	 */
	public Campaign getCampaignAsLock(Integer cid);

	public CampaignActor getCampaignActorAsLock(String orderId);

	public void updateCampaignActor(CampaignActor ca);

	public List<CampaignActor> getCampaignActorAll(Integer cid);

	public int updateBatchCampaignActor(List<CampaignActor> actorList);

	public List<CampaignType> getCampaignTypesAll();

	public void updateCampaignActorByCidAndUid(CampaignActor caa);

}
