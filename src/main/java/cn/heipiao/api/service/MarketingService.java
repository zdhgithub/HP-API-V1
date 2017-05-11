package cn.heipiao.api.service;


import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.LikeUser;
import cn.heipiao.api.pojo.Marketing;
import cn.heipiao.api.pojo.MarketingPicture;

public interface MarketingService {
	/**
	 * 添加营销活动
	 * @param marketing
	 */
	void addMarketing(Marketing marketing);
	/**
	 * 修改营销活动状态
	 *status 0 未发布，1 已发布，2 已结束，3 删除
	 * @param map
	 */
	void updateMarketing(Marketing marketing);
	/**
	 * 获取营销活动列表
	 * @param status 0 未发布，1 已发布，2 已结束，3 删除
	 * @return
	 */
	List<Marketing> getList(Map<String,Object> map);
	/**
	 * 获取总数
	 * @param map
	 * @return
	 */
	Integer getMarketingCount(Map<String,Object> map);
	/**
	 * 获取单个营销活动详情
	 * @param id
	 * @return
	 */
	Marketing getOneMarketing(Integer id);
	/**
	 * 获取活动的所有发布的图片内容列表
	 * @param map
	 * @return
	 */
	List<MarketingPicture> getPictureList(Map<String,Object> map);
	/**
	 * 发布图片内容
	 * @param marketingPicture
	 */
	void addPictures(MarketingPicture marketingPicture);
	/**
	 * 获取用户发布的内容
	 * @param map
	 * @return MarketingPicture
	 */
	MarketingPicture getOneMaretingPicture(Map<String,Object> map);
	/**
	 * 添加点赞用户
	 * @param likeUser
	 */
	void addLkeUser(LikeUser likeUser);
	/**
	 * 查询点赞用户
	 * @param map
	 * @return
	 */
	LikeUser getOneLikeUser(Map<String,Object> map);
	/**
	 * 更新上传图片内容
	 * @param map
	 */
	void updateMarketingPicture(Map<String,Object> map);
}
