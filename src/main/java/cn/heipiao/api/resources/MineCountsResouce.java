package cn.heipiao.api.resources;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.MineCountsService;
/**
 * 我的相关统计
 * @ClassName: MineCountsResouce
 * @Description: TODO
 * @author zf
 * @version 1.1
 */
@RestController
@RequestMapping(value = "mine",produces="application/json")
public class MineCountsResouce {
	@Resource
	private MineCountsService mineCountsService;
	private static final Logger logger = LoggerFactory
			.getLogger(MineCountsResouce.class);

	/**
	 * 我的 相关 统计
	 * 
	 * @param userId
	 * @return
	 * @since 1.1
	 */
//	@Path("counts/{userId}")
//	@GET
	@RequestMapping(value = "counts/{userId}",method = RequestMethod.GET)
	public String mycounts(@PathVariable("userId") final Integer userId) {
		try {
			Map<String, Object> result = mineCountsService.getResult(userId);
			return JSONObject.toJSONString(new RespMsg<Map<String, Object>>(
					result));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<Integer>(Status.error));
		}
	}
}
