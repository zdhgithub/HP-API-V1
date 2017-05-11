package cn.heipiao.api.service;

import java.util.List;

import cn.heipiao.api.pojo.ArticleDict;
import cn.heipiao.api.pojo.DictConfig;

/**
 * @author zf
 * @version 1.0
 * @description 字典配置service
 * @date 2016年6月7日
 */
public interface ConfigService {
	
	/**
	 * 优惠券收费规则描述字段
	 */
	String coupon_rule_detail = "coupon_rule_detail";
	
	/**
	 * 查询某一类型的配置
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<DictConfig> queryConfigByType(String type) throws Exception;
	/**
	 * 更新字典表
	 * @param dict
	 * @return
	 * @throws Exception
	 */
	public Integer updateDict(DictConfig dict) throws Exception;
	/**
	 * 添加字典表数据
	 * @param dict
	 * @return
	 * @throws Exception
	 */
	public Integer addDict(DictConfig dict) throws Exception;
	/**
	 * 删除一条数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleteDict(Integer id) throws Exception;

	/**
	 * 通过数据类别和数据名称/值获取字典对象
	 * @param type
	 * @param scale
	 * @return
	 */
	public DictConfig queryConfigByTypeValue(String type, String scale);
	
	/**
	 * 通过类型查某个配置
	 * @param type
	 * @return
	 */
	DictConfig selectByType(String type);
	
	/**
	 * 通过类别查询列表
	 * @param category
	 * @return
	 */
	public List<ArticleDict> getListArticleCategory(Integer category);
	

}
