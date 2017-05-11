package cn.heipiao.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.heipiao.api.pojo.SystemMsg;

/**
 * 系统消息
 * 
 * @author zf
 *
 */
public interface SystemMsgMapper extends BaseMapper<SystemMsg> {
	/**
	 * 统计用户系统消息数量
	 * @param uid
	 * @return
	 */
	Integer countMsg(Integer uid);
	/**
	 * 统计用户新消息的数量
	 * @param uid
	 * @return
	 */
	Integer countMsgNew(Integer uid);
	/**
	 * 更新消息状态
	 * @param uid
	 * @param type
	 * @return
	 */
	Integer updateByUid(@Param("uid")Integer uid,@Param("type")String type);
	/**
	 * 批量发送系统消息
	 * 
	 * @param userIdList
	 */
	public void batchInsert(Map<String, Object> map);
	/**
	 * 发送一条消息
	 * @param systemMsg
	 */
	public void insert(SystemMsg systemMsg);
	/**
	 * 更新消息信息
	 * @param list
	 */
	public void update(List<Integer> list);
}
