package cn.heipiao.api.thread;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishSiteMapper;
import cn.heipiao.api.pojo.FishSite;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 
 * @author zf
 *
 */
//v2.2开始废弃
@Deprecated
@Service
public class SignFishSiteThread {
	private static Logger logger = LoggerFactory
			.getLogger(SignFishSiteThread.class);

	private static final int delay = 1 * 60 * 60 * 1000;
	/** 签约限制最长天数 */
	private static final int days = 3;

	@Resource
	private FishSiteMapper fishSiteMapper;

	public void run() {
		while (true) {
			try {
				Thread.sleep(delay);
				repealFishSiteClaim();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				;
			}
		}
	}

	/**
	 * 如果合伙人认领钓场时间超过期限，取消认领
	 */
	@Transactional
	public void repealFishSiteClaim() {
		List<FishSite> sites = fishSiteMapper.selectBySignStatus();
		if (sites != null && sites.size() > 0) {
			for (FishSite f : sites) {
				Date signTime = f.getSignTime();
				if (DateUtils.addDays(signTime, days).before(
						ExDateUtils.getDate())) {
					fishSiteMapper.updateFishSite(f.getFishSiteId());
				} else {
					continue;
				}
			}
		}
	}
}
