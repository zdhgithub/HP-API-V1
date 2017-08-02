package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerApply;

/**
 * 
 * @author zf
 *
 */
public interface PartnerApplyService {
	/**
	 * 申请合伙人
	 * 
	 * @param javabean
	 * @return
	 * @throws Exception
	 */
	public Integer applyPartner(PartnerApply javabean) throws Exception;

	/**
	 * 查询列表
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<PartnerApply> getApplys(Map<String, Object> params)
			throws Exception;

	/**
	 * 审核合伙人申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer checkApply(Map<String, Object> params) throws Exception;

	/**
	 * 通过uid查
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public PartnerApply getApply(Integer uid) throws Exception;

}
