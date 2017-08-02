package cn.heipiao.api.service;

import java.util.Map;
import java.util.Set;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.device.OnlineStatus;

public interface JPushService {
	
	public void bindMobile(Integer uid,String rid,String type ,String os) throws APIConnectionException, APIRequestException,Exception;
	/**
	 * 催钟用
	 * @param uid 用户ID(必选)
	 * @param type B表示给B端发送，C表示给C端发送(必选)
	 * @param title （可为空）
	 * @param alert 内容(必选)
	 */
	public boolean sendPush(Integer uid,String type,String title,String alert) throws APIConnectionException, APIRequestException,Exception ;
	/**
	 * 提示用
	 * @param uid 用户ID(必选)
	 * @param type 给C端推送传C，给B端推送传B(必选)
	 * @param title 推送标题(可为空)
	 * @param alert 推送内容(必选)
	 * @param businessParams 业务参数,Map<String,String>类型(可选)
	 */
	public boolean sendPush(Integer uid,String type,String title,String alert,Map<String,String> businessParams) throws APIConnectionException, APIRequestException,Exception ;
	/**
	 * 支付提示用
	 * @param uid 用户ID(必选)
	 * @param type 给C端推送传C，给B端推送传B(必选)
	 * @param title 推送标题(可为空)
	 * @param alert 推送内容(必选)
	 * @param businessParams 业务参数,Map<String,String>类型(可选)
	 */
	public boolean sendPushForPay(Integer uid,String type,String title,String alert,Map<String,String> businessParams) throws APIConnectionException, APIRequestException,Exception ;
	/**
	 * 线程用
	 * @param uid
	 * @param type
	 * @param alert
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 * @throws Exception
	 */
	public boolean sendPush(Integer uid,String type,String alert) throws APIConnectionException, APIRequestException,Exception ;
	/**
	 * 通过tag发推送
	 * @param tag
	 * @param alert
	 * @param title
	 * @return
	 */
	public boolean sendPushWithTags(String tag,String alert,String title,String type) throws APIConnectionException, APIRequestException ;
	/**
	 * 给所有用户发推送
	 * @param alert
	 * @param type
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 * @throws Exception
	 */
	public boolean sendPushToAll(String alert,String type) throws APIConnectionException, APIRequestException,Exception;
	/**
	 * 通过类型发推送
	 * @param uid
	 * @param alert
	 * @param type
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 * @throws Exception
	 */
	public boolean sendPushMessage(Integer uid,String alert,String type) throws APIConnectionException, APIRequestException,Exception;
	/**
	 * 更新设备的别名和标签
	 * @param registration_id
	 * @param alias
	 * @param tagsToAdd
	 * @param tagsToRemove
	 * @param type
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public boolean UpdateDeviceTagAlias_add_remove_tags(String registration_id,String alias,Set<String> tagsToAdd,Set<String> tagsToRemove,String type) throws APIConnectionException, APIRequestException;
	/**
	 * 查询设备的标签和别名
	 * @param registration_id
	 * @param type
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public Map<String,Object> getDeviceTagAlias(String registration_id,String type) throws APIConnectionException, APIRequestException ;
	/**
	 * 增加或删除设备的标签
	 * @param tag
	 * @param toAddUsers
	 * @param toRemoveUsers
	 * @param type
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public boolean addRemoveDevicesFromTag(String tag,Set<String> toAddUsers,Set<String> toRemoveUsers,String type) throws APIConnectionException, APIRequestException ;
	/**
	 * 查询设备的在线状态
	 * @param type
	 * @param registrationIds
	 * @return
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public Map<String, OnlineStatus> getUserOnlineStatus(String type, String... registrationIds) throws APIConnectionException, APIRequestException ;
	/**
	 * 设置看板配置
	 * @param id
	 * @param type
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public Integer setConfig(Long id,Integer type ,Integer config) throws Exception;
	/**
	 * 查询看板配置
	 * @param id
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Integer getConfig(Long id,Integer type) throws Exception;
}
