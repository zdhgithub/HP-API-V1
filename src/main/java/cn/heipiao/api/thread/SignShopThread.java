package cn.heipiao.api.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.FishShopSignMapper;
import cn.heipiao.api.pojo.FishShopSign;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 
 * @author zf
 *
 */
//v2.2开始废弃
@Deprecated
@Service
public class SignShopThread {
	private static Logger logger = LoggerFactory
			.getLogger(SignShopThread.class);

	private static final int delay = 1 * 60 * 60 * 1000;
	/** 认领限制最长天数 */
	private static final int days = 3;

	@Resource
	private FishShopSignMapper<FishShopSign> fishShopSignMapper;

	public void run() {
		while (true) {
			try {
				Thread.sleep(delay);
				repealShopClaim();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 如果合伙人认领钓场时间超过期限，取消认领
	 */
	@Transactional
	public void repealShopClaim() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<FishShopSign> fss = fishShopSignMapper.selectList(params);
		if (fss != null && fss.size() > 0) {
			for (FishShopSign f : fss) {
				Date signTime = f.getSignTime();
				if (DateUtils.addDays(signTime, days).before(
						ExDateUtils.getDate())) {
					this.fishShopSignMapper.delete(f.getShopId());
				} else {
					continue;
				}
			}
		}
	}
}
