package cn.heipiao.api.thread;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.heipiao.api.mapper.CustomConfigMapper;
import cn.heipiao.api.mapper.EmpMapper;
import cn.heipiao.api.mapper.PoleMapper;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.Pole;
import cn.heipiao.api.service.JPushService;
import cn.heipiao.api.utils.ExDateUtils;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;

//v2.2开始废弃
@Deprecated
@Component
public class PushClockThread{

	@Resource
	private PoleMapper poleMapper;
	@Resource
	private JPushService jPushService;
	@Resource
	private EmpMapper empMapper;
	@Resource
	private CustomConfigMapper customConfigMapper;

	private static Logger logger = LoggerFactory
			.getLogger(PushClockThread.class);

	private static final int delay = 3 * 60 * 1000;

	public void run() {
		while (true) {
			try {
				Thread.sleep(delay);
				pushAlert();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void pushAlert() {

		Map<String, Object> params = new LinkedMap<String, Object>();
		params.put("now", ExDateUtils.getDate());
		List<Pole> list = this.poleMapper.selectOverTimeUsers(params);
		for (Pole p : list) {
			Integer result = customConfigMapper.selectOne(Long.parseLong(p.getFishSiteId()), 2);
			if(result==null || result==0) {
				continue;
			}
			logger.debug("共有{}张票,结束时间到了",list.size());
//			if (ExDateUtils.getDate().getTime()>p.getEnd().getTime()) {
				Map<String, String> params2 = new LinkedMap<String, String>();
				params2.put("fishSiteId", p.getFishSiteId());
				List<Emp> emps = empMapper.selectList(params2);
				for (Emp m : emps) {
					try {
						this.jPushService.sendPush(m.getUserId(),"B","钓场主啊,用户ID："+m.getUserId()+"，票ID:"+p.getId()+"到钟，快收钱");
					} catch (APIConnectionException e) {
						logger.error(e.getMessage(), e);
					} catch (APIRequestException e) {
						logger.error(e.getMessage(), e);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				}
//			}
		}

	}
}
