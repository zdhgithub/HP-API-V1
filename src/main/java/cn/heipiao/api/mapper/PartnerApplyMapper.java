package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.PartnerApply;

/**
 * 
 * @author zf
 *
 */
public interface PartnerApplyMapper {
	/**
	 * 通过id查
	 * 
	 * @param id
	 * @return
	 */
	public PartnerApply selectOne(Integer id);

	/**
	 * 查询列表
	 * 
	 * @param params
	 * @return
	 */
	public List<PartnerApply> selectList(Map<String, Object> params);

	/**
	 * 添加
	 * 
	 * @param pojo
	 * @return
	 */
	public int insert(PartnerApply pojo);

	/**
	 * 更新
	 * 
	 * @param params
	 * @return
	 */
	public int update(Map<String, Object> params);

}
