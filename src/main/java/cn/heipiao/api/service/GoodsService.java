package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Goods;

/**
 * @author zf
 * @version 1.0
 * @description 商品service
 * @date 2016年6月28日
 */
public interface GoodsService {
	
	/** 数据词典  - 商品统计时间key **/
	String VOLUME_TIME_KEY = "goodsSalesVolums";
	
	/**
	 * 保存商品
	 * 
	 * @param good
	 * @throws Exception
	 */
	public int save(Goods good) throws Exception;

	/**
	 * 查询商品-不支持排序
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Goods> queryGoods(Map<String, Object> params) throws Exception;

	/**
	 * 修改商品部分信息
	 * 
	 * @param good
	 * @throws Exception
	 */
	public int modifyGoods(Goods good) throws Exception;
	/**
	 * 编辑商品
	 * 
	 * @param good
	 * @throws Exception
	 */
	public int modifyGoodsEx(Goods good) throws Exception;
	
	/**
	 * @说明 销量统计 - 增量统计
	 *
	 */
	void salesVolume();
	
}
