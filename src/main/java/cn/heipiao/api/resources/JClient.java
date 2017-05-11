package cn.heipiao.api.resources;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
/**
 * 推送客户端封装
 * @ClassName: JClient
 * @Description: TODO
 * @author zf
 * @version 2.1
 */
public class JClient {
	//C端
	private static final String appKey = "96b16050b072fe127277b985";
	private static final String masterSecret = "81a754ae046ac03a0e39887c";
	//B端
	private static final String b_appKey = "4e931d34dd679f6985b7a1cd";
	private static final String b_masterSecret = "f79bdab6141fd1737c698e33";

	private static JPushClient jpushClient;
	private static JPushClient jBpushClient;

	static {
		ClientConfig config = ClientConfig.getInstance();
		// config.setMaxRetryTimes(5);
		config.setConnectionTimeout(10 * 1000); // 10 seconds
		jpushClient = new JPushClient(masterSecret, appKey, null, config);
		jBpushClient = new JPushClient(b_masterSecret, b_appKey, null, config);
	}

	public static JPushClient getJpushClient(String type) {
		if("B".equals(type)) {//B端
			return jBpushClient;
		}else {//C端
			return jpushClient;
		}
	}

}
