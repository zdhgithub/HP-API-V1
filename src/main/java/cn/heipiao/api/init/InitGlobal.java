/**
 * 
 */
package cn.heipiao.api.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.heipiao.api.constant.RespMessage;

/**
 * spring 容器启动后需要初始化的数据类
 * 
 * @author wzw
 * @date 2016年9月18日
 * @version 1.0
 */
@Component
public class InitGlobal implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory
			.getLogger(InitGlobal.class);

//	@Resource
//	private DataThread dataThread;

//	@Resource
//	private SignFishSiteThread signFishSiteThread;
//	
//	@Resource 
//	private PushClockThread pushClockThread;
//	
//	@Resource
//	private SignShopThread signShopThread;

	private static volatile boolean isStart = false;

	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (!isStart) {
			logger.info("init Global start ");

			try {
				RespMessage.initMap();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

//			dataThread.setDaemon(true);
//			dataThread.start();

//			signFishSiteThread.start();
//			signShopThread.start();
//			pushClockThread.start();

			isStart = true;

			logger.info("init Global end ");
		}

	}

}
