package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.Goods;

/**
 * @author zf
 * @version 1.0
 * @description 商品Mapper
 * @date 2016年6月28日
 */
public interface GoodsMapper extends BaseMapper<Goods> {

	/**
	 * 获取某个商品后加锁方法
	 * @param pid
	 * @return
	 */
	Goods selectByIdAsLock(Integer gid);

	/**
	 * @param gs
	 */
	void updateAmountBatch(List<Goods> gs);
	
	/**
	 * @说明 - 商品销量统计增量统计
	 * @return
	 */
	int salesVolume(Map<String , Object> params);
	
	/**
	 * @说明  - 查询新完成的订单的总条数
	 * @param params
	 * @return
	 */
	int selectNewCountOrder(Map<String , Object> params);

	/**
	 * @param fishPondId
	 * @return
	 */
	Integer isExists(Integer pid);

}
