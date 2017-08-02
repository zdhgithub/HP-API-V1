package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Share;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月13日
 */
public interface ShareService {
	/**
	 * 根据ID查询某个分享或是渔获
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Share queryShareById(Long id) throws Exception;

	/**
	 * 分页查询分享或是渔获列表，支持排序
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Share> queryShares(Map<String, Object> params) throws Exception;

	/**
	 * 审核分享或是渔获信息
	 * 
	 * @param share
	 * @throws Exception
	 */
	public void modifyShare(Share share) throws Exception;

	/**
	 * 发布分享或是渔获
	 * 
	 * @param share
	 * @throws Exception
	 */
	public void publishShare(Share share) throws Exception;

	/**
	 * 数量统计
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int countShares(String type,Integer siteId) throws Exception;
	/**
	 * +1
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Integer addClickNum(Integer sid) throws Exception;
}
