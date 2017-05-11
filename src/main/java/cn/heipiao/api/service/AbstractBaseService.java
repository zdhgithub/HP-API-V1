package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.constant.MyBatisConstant;
import cn.heipiao.api.mapper.BaseMapper;
import cn.heipiao.api.mapper.Paginator;

/**
 * 
 * 说明 : 提供分页功能 功能 : a. 使用规范 : a.
 * 
 * @author chenwenye
 * @since 2016-6- heipiao1.0
 * @param <T>
 *            - 分页返回对象类型
 */
public abstract class AbstractBaseService<T> {

	/**
	 * 
	 * 功能 : 列表查询 - 不提供分页查询
	 * 
	 * @param mapper
	 * @param param
	 * @return
	 */
	protected <U extends BaseMapper<?>> List<T> SelectTable(U mapper, Map<String, Object> param) {
		return this.SelectTable(mapper, param, null);
	}

	/**
	 * 
	 * 功能 : 列表查询提供分页查询,后续版本可能提供排序
	 * 
	 * @param mapper
	 * @param param
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <U extends BaseMapper<?>> List<T> SelectTable(U mapper, Map<String, Object> param, Paginator page) {
		if (page != null) {
			param.put(MyBatisConstant.PARAM_START, page.getStart());
			param.put(MyBatisConstant.PARAM_SIZE, page.getSize());
		}
		return (List<T>) mapper.selectList(param);
	}

}
