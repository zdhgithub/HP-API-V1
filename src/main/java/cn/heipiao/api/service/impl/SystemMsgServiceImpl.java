package cn.heipiao.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishShopMapper;
import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.mapper.PartnerMapper;
import cn.heipiao.api.mapper.SystemMsgMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.pojo.SystemMsg;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.service.SystemMsgService;
import cn.heipiao.api.utils.ExDateUtils;

@Service
public class SystemMsgServiceImpl implements SystemMsgService {
	@Resource
	private SystemMsgMapper systemMsgMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private PartnerMapper partnerMapper;
	@Resource
	private FishSiteMapper fishSiteMapper;
	@Resource
	private FishShopMapper fishShopMapper;
	@Resource
	private JPushService jPushService;

	@Override
	@Transactional
	public List<SystemMsg> getSystemMsg(Map<String, Object> params)
			throws Exception {
		List<SystemMsg> list = systemMsgMapper.selectList(params);
		Long userId = (Long) params.get("receiver");
		this.systemMsgMapper.updateByUid(userId.intValue(), (String)params.get("type"));
		return list;
	}

	@Override
	@Transactional
	public Integer saveMsgBatch(SystemMsg s, Object[] uids) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (uids == null || uids.length == 0) {
			List<Integer> all = this.userMapper.getUserIDs();
			map.put("uids", all);
		} else {
			map.put("uids", uids);
		}
		map.put("title", s.getTitle());
		map.put("url", s.getUrl());
		map.put("content", s.getContent());
		map.put("createTime", ExDateUtils.getDate());
		map.put("sender", s.getSender() == null ? 0 : s.getSender());
		map.put("type", "C");
		this.systemMsgMapper.batchInsert(map);
		return Status.success;
	}
	//partner,fishShop,fishSite,user
	@Override
	public Integer saveMsgBatch(SystemMsg s, Integer object,boolean ispush) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> uids = null;
		String type = null;
		switch (object) {
		// 全部用户，C端
		case 1:
			uids = this.userMapper.getUserIDs();
			type = "C";
			if(ispush) {
				jPushService.sendPushWithTags("user", s.getContent(), s.getTitle(), "C");
			}
			break;
		// 合伙人，C端
		case 2:
			uids = this.partnerMapper.getPartnerUIDs();
			type = "C";
			if(ispush) {
				jPushService.sendPushWithTags("partner", s.getContent(), s.getTitle(), "C");
			}
			break;
		// 全部商家，B端
		case 3:
			uids = this.fishSiteMapper.getAllSiteUIDs();
			type = "B";
			if(ispush) {
				jPushService.sendPushWithTags("fishShop", s.getContent(), s.getTitle(), "B");
				jPushService.sendPushWithTags("fishSite", s.getContent(), s.getTitle(), "B");
			}
			break;
		// 渔具店老板，B端
		case 4:
			uids = this.fishShopMapper.getFishShopUIDs();
			type = "B";
			if(ispush) {
				jPushService.sendPushWithTags("fishShop", s.getContent(), s.getTitle(), "B");
			}
			break;
		// 钓场老板，B端
		case 5:
			uids = this.fishSiteMapper.getFishSiteUIDs();
			type = "B";
			if(ispush) {
				jPushService.sendPushWithTags("fishSite", s.getContent(), s.getTitle(), "B");
			}
			break;
		}
		map.put("uids", uids);
		map.put("type", type);
		map.put("title", s.getTitle());
		map.put("url", s.getUrl());
		map.put("content", s.getContent());
		map.put("createTime", ExDateUtils.getDate());
		map.put("sender", s.getSender() == null ? 0 : s.getSender());
		this.systemMsgMapper.batchInsert(map);
		return Status.success;
	}

	@Override
	@Transactional
	public Integer saveMsg(String title, String url, String content,
			Integer receiver, Integer sender) throws Exception {
		SystemMsg s = new SystemMsg(title, url, content, receiver,
				sender == null ? 0 : sender, "C");
		s.setCreateTime(ExDateUtils.getDate());
		this.systemMsgMapper.insert(s);
		return s.getId().intValue();
	}

	@Override
	@Transactional
	public Integer saveMsg(String title, String url, String content,
			Integer receiver, Integer sender, String type) throws Exception {
		SystemMsg s = new SystemMsg(title, url, content, receiver,
				sender == null ? 0 : sender, type);
		s.setCreateTime(ExDateUtils.getDate());
		this.systemMsgMapper.insert(s);
		return s.getId().intValue();
	}

}