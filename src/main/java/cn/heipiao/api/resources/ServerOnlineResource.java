//package cn.heipiao.api.resources;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//
//import cn.heipiao.api.pojo.RespMsg;
//
///**
// * 测试服务器是否可以正常访问
// * 
// * @author zf
// * @version 2.0
// *
// */
//// FIXME 待优化，如设置Session超时时间为1分钟
////@Controller
////@Consumes(MediaType.APPLICATION_JSON)
////@Produces(MediaType.APPLICATION_JSON)
////@Path("server")
//@RestController
//@RequestMapping(value = "server",produces="application/json")
//public class ServerOnlineResource {
//	private static final Logger log = LoggerFactory
//			.getLogger(ServerOnlineResource.class);
//
//	@Autowired
//	HttpServletRequest request;
//	/**
//	 * 服务器查询是否正常在线
//	 * @return
//	 * @since 2.0
//	 */
////	@Path("")
////	@GET
//	@RequestMapping(value = "online",method = RequestMethod.GET)
//	public String testOnline() {
//		log.debug("server is running");
//		Map<String, Object> result = new HashMap<String, Object>();
//		Integer sessionNum = (Integer) request.getSession().getServletContext()
//				.getAttribute("count");
//		//session数量
//		result.put("sessionNum", sessionNum);
//		return JSONObject
//				.toJSONString(new RespMsg<Map<String, Object>>(result));
//	}
//
//}
