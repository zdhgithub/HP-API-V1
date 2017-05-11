package cn.heipiao.api.utils;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.RespMsg;

/**
 * 
 * @ClassName: ResultUtils
 * @Description: 封装控制层的返回
 * @author zf
 * @date 2016年10月9日
 */
public class ResultUtils {
	public static <U> String out(U t) {
		if (t instanceof Integer) {
			return JSONObject.toJSONString(new RespMsg<Integer>((Integer) t, RespMessage
					.getRespMsg((Integer) t)));
		}
		return JSONObject.toJSONString(new RespMsg<U>(t));
	}
}
