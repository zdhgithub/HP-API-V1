package cn.heipiao.api.service;

import java.util.List;
import java.util.Map;

import cn.heipiao.api.pojo.SystemMsg;

public interface SystemMsgService {
	/**
	 * 用户的系统消息 默认取C端的消息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SystemMsg> getSystemMsg(Map<String, Object> params)
			throws Exception;

	/**
	 * 批量发送消息 默认发到C端
	 * 
	 * @param params
	 */
	public Integer saveMsgBatch(SystemMsg s, Object[] uids) throws Exception;

	/**
	 * 批量发送消息 分B端或C端
	 * 
	 * @param params
	 */
	public Integer saveMsgBatch(SystemMsg s, Integer object,boolean ispush) throws Exception;

	/**
	 * 系统提示消息 默认发到C端
	 * 
	 * @param title
	 *            标题(必选)
	 * @param url
	 *            链接
	 * @param content
	 *            内容
	 * @param receiver
	 *            接收者（必选）
	 * @param sender
	 *            发送者
	 * @return
	 * @throws Exception
	 */
	public Integer saveMsg(String title, String url, String content,
			Integer receiver, Integer sender) throws Exception;

	/**
	 * 系统提示消息 分B或C端
	 * 
	 * @param title
	 *            标题(必选)
	 * @param url
	 *            链接
	 * @param content
	 *            内容
	 * @param receiver
	 *            接收者（必选）
	 * @param sender
	 *            发送者
	 * @param type
	 *            发到B端传B，发到C端传C
	 * @return
	 * @throws Exception
	 */
	public Integer saveMsg(String title, String url, String content,
			Integer receiver, Integer sender, String type) throws Exception;

}
