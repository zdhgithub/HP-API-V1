/**
 * 
 */
package cn.heipiao.api.mapper;

import cn.heipiao.api.pojo.Sequence;

/**
 * @author wzw
 * @date 2016年8月11日
 * @version 1.0
 */
public interface SequenceMapper {

	/**
	 * 获取记录并加锁
	 * @param fishSiteId
	 * @return
	 */
	Sequence selectByIdAsLock(Integer fishSiteId);
	
	/**
	 * 新增
	 * @param s
	 */
	void insertPojo(Sequence s);
	
	/**
	 * 更新
	 * @param s
	 */
	void updatePojo(Sequence s);
	
			
}
