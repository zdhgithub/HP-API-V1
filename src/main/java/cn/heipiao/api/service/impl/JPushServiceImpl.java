package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.CustomConfigMapper;
import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.RegistMapper;
import cn.heipiao.api.pojo.PushUser;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.resources.JClient;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.PartnerService;
import cn.heipiao.api.service.UserOpService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.common.resp.DefaultResult;
import cn.jpush.api.device.OnlineStatus;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.google.gson.JsonObject;
@SuppressWarnings("unused")
@Service
public class JPushServiceImpl implements JPushService {
	private static final Logger log = LoggerFactory.getLogger(JPushServiceImpl.class);
	
    public static final String B = "B";
    public static final String C = "C";
    public static final boolean TRUE_OR_FALSE = true;
    private static final String CC_WAV = "c_clocktip.wav";
    private static final String BC_WAV = "b_clocktip.wav";
    private static final String BP_WAV = "b_paytip.wav";
    @Resource
    private RegistMapper registMapper;
    @Resource
    private UserOpService userOpService;
    @Resource
    private PartnerService partnerService;
    @Resource
	private FishSiteMapper fishSiteMapper;
    @Resource
    private FishShopMapper fishShopMapper;
    @Resource
    private CustomConfigMapper customConfigMapper;
    
	@Override
	public boolean sendPush(Integer uid,String type,String title,String alert) throws APIConnectionException, APIRequestException,Exception  {
		User user = userOpService.queryUserById(uid.longValue());
		PushUser pu = this.registMapper.selectPojo(user.getPhone(), type);
		if(pu == null) {
			log.debug("{} rid is null",uid);
			return false;
		}
		if(pu.getOs().equals("ios")) {//ios推送
			PushPayload payload_ios = buildPushObject_ios(alert, pu.getRegistration_id(), CC_WAV, null);
			PushResult result_ios = JClient.getJpushClient(type).sendPush(payload_ios);
			log.debug("sendPush ios result:{}", result_ios.isResultOK());
			return result_ios.isResultOK();
		}else {//android 推送
			Map<String,String> extras_android = new HashMap<String, String>();
			extras_android.put("sound", CC_WAV);
			PushPayload payload_android = buildPushObject_android_withMessage(title, alert, pu.getRegistration_id(), extras_android);
			PushResult result_android = JClient.getJpushClient(type).sendPush(payload_android);
			log.debug("sendPush android result:{}", result_android.isResultOK());
			return result_android.isResultOK();
		}
	}
	
	@Override
	public boolean sendPush(Integer uid,String type,String alert) throws APIConnectionException, APIRequestException,Exception  {
		User user = userOpService.queryUserById(uid.longValue());
		PushUser pu = this.registMapper.selectPojo(user.getPhone(), type);
		if(pu == null) {
			log.debug("{} rid is null",uid);
			return false;
		}
		if(pu.getOs().equals("ios")) {//ios推送
			PushPayload payload_ios = buildPushObject_ios(alert, pu.getRegistration_id(), BC_WAV, null);
			PushResult result_ios = JClient.getJpushClient(type).sendPush(payload_ios);
			log.debug("sendPush ios result:{}", result_ios.isResultOK());
			return result_ios.isResultOK();
		}else {//android 推送
			Map<String,String> extras_android = new HashMap<String, String>();
			extras_android.put("sound", BC_WAV);
			PushPayload payload_android = buildPushObject_android_withMessage(null, alert, pu.getRegistration_id(), extras_android);
			PushResult result_android = JClient.getJpushClient(type).sendPush(payload_android);
			log.debug("sendPush android result:{}", result_android.isResultOK());
			return result_android.isResultOK();
		}
	}
	@Override
	public boolean sendPush(Integer uid, String type, String title,
			String alert, Map<String, String> businessParams)
			throws APIConnectionException, APIRequestException, Exception {
		if(uid==null) {
			log.debug("uid is null");
			return false;
		}
		User user = userOpService.queryUserById(uid.longValue());
		if(user==null) {
			log.debug("user is null");
			return false;
		}
		String rid = this.registMapper.selectOne(user.getPhone(), type);
		if(StringUtils.isEmpty(rid)) {
			log.debug("{} rid is null",uid);
			return false;
		}
		PushPayload payload = this.buildPushObject_with_extra(rid, alert, title, businessParams);
		PushResult result = JClient.getJpushClient(type).sendPush(payload);
		log.debug("sendPush result:{}", result.isResultOK());
		return result.isResultOK();
	}
	
	@Override
	public boolean sendPushForPay(Integer uid, String type, String title,
			String alert, Map<String, String> businessParams)
			throws APIConnectionException, APIRequestException, Exception {
		User user = userOpService.queryUserById(uid.longValue());
		PushUser pu = this.registMapper.selectPojo(user.getPhone(), type);
		if(pu == null) {
			log.debug("{} rid is null",uid);
			return false;
		}
		if(pu.getOs().equals("ios")) {//ios 推送
			PushPayload payload_ios = buildPushObject_ios(alert, pu.getRegistration_id(), BP_WAV, businessParams);
			PushResult result_ios = JClient.getJpushClient(type).sendPush(payload_ios);
			log.debug("sendPush ios result:{}", result_ios.isResultOK());
			return result_ios.isResultOK();
		}else {//android 推送
			if(businessParams==null) {
				businessParams = new HashMap<String, String>();
			}
			businessParams.put("sound", BP_WAV);
			PushPayload payload_android = buildPushObject_android_withMessage(title, alert, pu.getRegistration_id(), businessParams);
			PushResult result_android = JClient.getJpushClient(type).sendPush(payload_android);
			log.debug("sendPush android result:{}", result_android.isResultOK());
			return result_android.isResultOK();
		}
	}
	
	/**
	 * all
	 */
	@Override
	public boolean sendPushToAll(String alert, String type)
			throws APIConnectionException, APIRequestException, Exception {
		PushPayload payLoad = buildPushObject_all_all_alert(alert);
		PushResult result = JClient.getJpushClient(type).sendPush(payLoad);
		return result.isResultOK();
	}
	/**
	 * test
	 */
	@Override
	public boolean sendPushMessage(Integer uid,String alert, String type)
			throws APIConnectionException, APIRequestException, Exception {
		User user = userOpService.queryUserById(uid.longValue());
		String rid = this.registMapper.selectOne(user.getPhone(), type);
		if(StringUtils.isEmpty(rid)) {
			log.debug("{} rid is null",uid);
			return false;
		}
		Map<String,String> extras = new HashMap<String, String>();
		extras.put("sound", CC_WAV);
		PushPayload payLoad = buildPushObject_android_withMessage(null, alert, rid, extras);
		PushResult result = JClient.getJpushClient(type).sendPush(payLoad);
		return result.isResultOK();
	}
	@Override
	public boolean UpdateDeviceTagAlias_add_remove_tags(String registration_id,String alias,Set<String> tagsToAdd,Set<String> tagsToRemove,String type)
			throws APIConnectionException, APIRequestException {
		// FIXME  2016-12-16报错：{"error":{"code":7002, "message":"The registration_id does not belong to this appkey!"}}
		DefaultResult result = JClient.getJpushClient(type).updateDeviceTagAlias(registration_id, alias, tagsToAdd, tagsToRemove);
		log.debug("UpdateDeviceTagAlias_add_remove_tags result:{}", result.isResultOK());
		return result.isResultOK();
	}
	
	@Override
	public Map<String,Object> getDeviceTagAlias(String registration_id,String type) throws APIConnectionException, APIRequestException {
		TagAliasResult tagAliasResult = JClient.getJpushClient(type).getDeviceTagAlias(registration_id);
		log.debug("getDeviceTagAlias responseCode:{}", tagAliasResult.getResponseCode());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("alias", tagAliasResult.alias);
		map.put("tags", tagAliasResult.tags);
		log.debug("getDeviceTagAlias map:{}", map);
		return map;
	}
	
	@Override
	public boolean addRemoveDevicesFromTag(String tag,Set<String> toAddUsers,Set<String> toRemoveUsers,String type) throws APIConnectionException, APIRequestException {
		DefaultResult result = JClient.getJpushClient(type).addRemoveDevicesFromTag(tag, toAddUsers, toRemoveUsers);
		log.debug("addRemoveDevicesFromTag result:{}", result.isResultOK());
		return result.isResultOK();
	}

	@Override
	@Deprecated
	public Map<String, OnlineStatus> getUserOnlineStatus(String type,String... registrationIds) throws APIConnectionException, APIRequestException {
		Map<String, OnlineStatus> result =  JClient.getJpushClient(type).getUserOnlineStatus(registrationIds);
		log.debug("getUserOnlineStatus isReusltNull", result==null?true:false);
		return result;
	}
	
	private static PushPayload buildPushObject_all_all_alert(String alert) {
	    return PushPayload.newBuilder()
	    			.setPlatform(Platform.all())
	    			.setAudience(Audience.all())
    				.setNotification(Notification.alert(alert))
    				.setOptions(Options.newBuilder()
    					.setApnsProduction(TRUE_OR_FALSE)
    					.build())
    				.build();
	}
	
	private static PushPayload buildPushObject_android_and_ios(String title,String alert,String rid,String sound) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.registrationId(rid))
                .setNotification(Notification.newBuilder()
                		.setAlert(alert)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(title).build())
                		.addPlatformNotification(IosNotification.newBuilder().setSound(sound).build())
                		.build())
        		 .setOptions(Options.newBuilder()
                 .setApnsProduction(TRUE_OR_FALSE)
                 .build())
                 .setMessage(Message.newBuilder().setTitle(title).setMsgContent(alert)
                		 .addExtra("sound", sound).build())
                .build();
    }
	/**
	 * 重用 ANDROID
	 * @param title
	 * @param alert
	 * @param rid
	 * @param extras
	 * @return
	 */
	private static PushPayload buildPushObject_android_withMessage(String title,String alert,String rid,Map<String,String> extras) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(rid))
                .setMessage(Message.newBuilder().setTitle(title).setMsgContent(alert)
                		 .addExtras(extras).build())
                .setOptions(Options.newBuilder()
                 .setApnsProduction(TRUE_OR_FALSE)
                 .build())
                .build();
    }
	/**
	 * 重用 IOS
	 * @param alert
	 * @param rid
	 * @param sound
	 * @param extras
	 * @return
	 */
	private static PushPayload buildPushObject_ios(String alert,String rid,String sound,Map<String,String> extras) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.registrationId(rid))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder()
										.incrBadge(+1)
										.setSound(sound)
										.setAlert(alert)
										.addExtras(extras).build())
										.build())
				.setOptions(Options.newBuilder()
                         .setApnsProduction(TRUE_OR_FALSE)
                         .build())
				.build();
	}
	/**
	 * 通过tag生成推送体，重用
	 * @param tag
	 * @param alert
	 * @param title
	 * @return
	 */
	private static PushPayload buildPushObject_with_tag(String tag,String alert,String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                		.setAlert(alert)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(title).build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.build())
                		.build())
                .build();
    }
	/**
	 * 通过业务参数生成推送体 
	 * @param registrationId
	 * @param alert
	 * @param title
	 * @param extras
	 * @return
	 */
	private PushPayload buildPushObject_with_extra(String registrationId,String alert,String title,Map<String,String> extras) {
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.registrationId(registrationId))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(AndroidNotification.newBuilder()
								.setTitle(title)
								.setAlert(alert)
								.addExtras(extras)
								.build())
								.addPlatformNotification(IosNotification.newBuilder()
										.incrBadge(+1)
										.setAlert(alert)
										.addExtras(extras).build())
										.build())
				.setOptions(Options.newBuilder()
                         .setApnsProduction(TRUE_OR_FALSE)
                         .build())
										.build();
		return payload;
	}
    
	@Override
	public Integer setConfig(Long id, Integer type, Integer config)
			throws Exception {
		if(this.customConfigMapper.selectOne(id, type)==null) {
			this.customConfigMapper.insert(id, type, config);
		}else {
			this.customConfigMapper.update(id, type, config);
		}
		return Status.success;
	}
	@Override
	public Integer getConfig(Long id, Integer type) throws Exception {
		return this.customConfigMapper.selectOne(id, type);
	}
	
	/**
	 * 注册手机rid
	 */
	@Override
	@Transactional
	public void bindMobile(Integer uid,String rid,String type,String os) throws APIConnectionException, APIRequestException,Exception {
		User user = userOpService.queryUserById(uid.longValue());
		Set<String> tagsToAdd = new HashSet<String>();
		if(user!=null) {
			tagsToAdd.add("user");//普通用户
		}
		if(partnerService.queryPartnerById(uid)!=null) {
			tagsToAdd.add("partner");//合伙人
		}
		if(fishSiteMapper.selectByUid(uid.longValue())!=null) {
			tagsToAdd.add("fishSite");//钓场主
		}
		if(fishShopMapper.selectFishShopByUid(uid).size()>0) {
			tagsToAdd.add("fishShop");//渔具店主
		}
		String tagsToAddStr = StringUtils.join(tagsToAdd.toArray(), ",");
		this.UpdateDeviceTagAlias_add_remove_tags(rid, uid.toString(), tagsToAdd, null,type);
		if(this.registMapper.selectPojo(user.getPhone(), type)==null) {
			PushUser pu = new PushUser();
			pu.setMobile(user.getPhone());
			pu.setRegistration_id(rid);
			pu.setTags(tagsToAddStr);
			pu.setAlias(uid.toString());
			pu.setType(type);
			pu.setOs(os);
			pu.setCreateTime(ExDateUtils.getDate());
			this.registMapper.insert(pu);
		}else {
			this.registMapper.update(user.getPhone(), uid.toString(), tagsToAddStr, rid, type,os);
		}
	}

	@Override
	public boolean sendPushWithTags(String tag, String alert, String title,String type) throws APIConnectionException, APIRequestException {
		PushPayload push = buildPushObject_with_tag(tag, alert, title);
		PushResult result_ios = JClient.getJpushClient(type).sendPush(push);
		log.debug("sendPush ios result:{}", result_ios.isResultOK());
		return result_ios.isResultOK();
	}

}
