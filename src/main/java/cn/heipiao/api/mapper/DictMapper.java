package cn.heipiao.api.mapper;

import java.util.Map;

import cn.heipiao.api.pojo.DictConfig;

/**
 * @author z
 * @version 1.0
 * @description 数据字典mapper
 * @date 2016年6月7日
 */
public interface DictMapper extends BaseMapper<DictConfig> {

	/**
	 * 通过code查询dict的value
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public DictConfig getDictByCode(String code) throws Exception;

	/**
	 * @说明 通过code查询
	 * @param code
	 * @return
	 */
	DictConfig selectDictByCode(String code);

	/**
	 * 通过数据类别和数据名称/值获取字典对象
	 * @param param
	 * @return
	 */
	public DictConfig selectConfigByTypeValue(Map<String, String> param);
	
	
	DictConfig selectByType(String type);
}
