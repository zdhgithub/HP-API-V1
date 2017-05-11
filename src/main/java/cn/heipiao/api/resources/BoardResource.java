package cn.heipiao.api.resources;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Page;
import cn.heipiao.api.pojo.Pole;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.service.BoardService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * 说明 : 看板Resource 功能 :
 * 
 * @author chenwenye
 * @since 2016-7-5 heipiao1.0
 */
@RestController
@RequestMapping(value = "boards",produces="application/json")
public class BoardResource {
	
	private final static Logger logger = LoggerFactory.getLogger(BoardResource.class);

	/** 日期格式转换 **/
//	private static final FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");

	/** 当天 **/
//	private static final String TODAY = "today";
	
	@Resource
	private BoardService boardService;

	
//	@Path("head/{finshSiteId}")
//	@GET
	@RequestMapping(value = "head/{finshSiteId}",method = RequestMethod.GET)
	public String systemTime(@PathVariable("finshSiteId") String finshSiteId ){
		if( StringUtils.isBlank(finshSiteId) ){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))) ; 
		}
		Pole pole = this.boardService.findHead(finshSiteId);
		pole.setSystemTime(ExDateUtils.getDate());
		return JSONObject.toJSONString(new RespMsg<Pole>(pole));
	}
	
//	@Path("delete/{id}")
//	@DELETE
	@RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") String id) {

		try {
			logger.debug("json:{}", id);
			Pole pole = new Pole();
			pole.setId(id);
			pole.setIsLeave(BoardService.NOT_EXIST);
			if (this.boardService.update(pole)) {
				return JSONObject.toJSONString(new RespMsg<>());
			}
			return JSONObject.toJSONString(new RespMsg<>(Status.user_finish_fail, RespMessage.getRespMsg(Status.user_finish_fail)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
//	@Path("addTime/{id}/{time}")
//	@POST
	@RequestMapping(value = "addTime/{id}/{time}",method = RequestMethod.POST,consumes = "application/json")
	public String addTime(@PathVariable("id")String id , @PathVariable("time") String _time ){
		logger.debug("json:{}", id , _time);
		if( StringUtils.isBlank(id) || !NumberUtils.isNumber(_time) || Long.valueOf(_time) < 0 ){
			return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error))) ;
		}
		return this.boardService.addTime(id, new Date(Long.valueOf(_time))) ? JSONObject.toJSONString(new RespMsg<>()) : JSONObject.toJSONString(new RespMsg<>(Status.ADD_TIME_ERROR, RespMessage.getRespMsg(Status.ADD_TIME_ERROR)));
	}

	/**
	 * 作用: 查询某天当时的在线用户列表(分页)
	 * 
	 * @param time
	 *            时间 
	 * @param fishSiteId
	 *            钓场Id
	 * @param pageNo
	 *            第几页
	 * @return
	 * @since 1.0
	 */
//	@Path("userList/{time}/{fishSiteId}/{start}/{size}")
//	@GET
	@RequestMapping(value = "userList/{time}/{fishSiteId}/{start}/{size}",method = RequestMethod.GET)
	public String finList(@PathVariable("time") String time, @PathVariable("fishSiteId") String fishSiteId,@PathVariable("start") Integer start, @PathVariable("size") Integer size) {
		try {
			logger.debug("json:{}", time , fishSiteId , start ,  size);	
			Calendar c = ExDateUtils.getCalendar();
			Date begin = ExDateUtils.getCurrentMin(c);
			Date end = ExDateUtils.getCurrentMax(c);
			List<Pole> poles = this.boardService.findPeople(begin, end, fishSiteId, BoardService.EXIST,
					new Page(start, size));
			return JSONObject.toJSONString(new RespMsg<>(poles));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 作用: 查询钓场某天总人数
	 * 
	 * @param time
	 * @param fishSiteId
	 * @return
	 */
	@Deprecated
//	@Path("userCount/{time}/{fishSiteId}")
//	@GET
	@RequestMapping(value = "userCount/{time}/{fishSiteId}",method = RequestMethod.GET)
	public String findUserCount(@PathVariable("time") String time, @PathVariable("fishSiteId") String fishSiteId) {
		try {
			logger.debug("json:{}", time , fishSiteId );
			Calendar c = ExDateUtils.getCalendar();
			Date begin = ExDateUtils.getCurrentMin(c);
			Date end = ExDateUtils.getCurrentMax(c);
			List<Pole> poles = this.boardService.findPeople(begin, end, fishSiteId, null, new Page());
			RespMsg<Object> resp = new RespMsg<>();
			resp.setBody(poles.size());
			return JSONObject.toJSONString(resp);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 作用: 查询钓场某天的在线人数
	 * 
	 * @param time
	 * @param fishSiteId
	 * @return
	 */
//	@Path("onLineCount/{time}/{fishSiteId}")
//	@GET
	@RequestMapping(value = "onLineCount/{time}/{fishSiteId}",method = RequestMethod.GET)
	@Deprecated
	public String findOnLineUserCount(@PathVariable("time") String time, @PathVariable("fishSiteId") String fishSiteId) {
		try {
			logger.debug("json:{}", time , fishSiteId );
			Calendar c = ExDateUtils.getCalendar();
			Date begin = ExDateUtils.getCurrentMin(c);
			Date end = ExDateUtils.getCurrentMax(c);
			List<Pole> poles = this.boardService.findPeople(begin, end, fishSiteId, BoardService.EXIST, new Page());
			RespMsg<Object> resp = new RespMsg<>();
			resp.setBody(poles.size());
			return JSONObject.toJSONString(resp);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}

	/**
	 * 作用: 日期设置
	 * 
	 * @param time
	 * @param begin
	 * @param end
	 * @throws Exception
	 */
	/*@SuppressWarnings("unused")
	@Deprecated
	private void timeSet(String time, Date begin, Date end) throws Exception {
		Calendar calendar = ExDateUtils.getCalendar();
		if (!TODAY.equals(time)) { // 时间操作
			calendar.setTime(sdf.parse(time)); // 日期转换异常
		}
		calendar.set(Calendar.MINUTE, 0x00000000);
		calendar.set(Calendar.SECOND, 0x00000000);
		calendar.set(Calendar.HOUR_OF_DAY, 0x00000000);
		System.out.println(DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss"));
		begin = ExDateUtils.getCurrentMin(calendar); // 设置初始时间 当天0点
		System.out.println(DateFormatUtils.format(begin, "yyyy-MM-dd HH:mm:ss"));
		System.out.println(0x00000018 + ":" + calendar.get(Calendar.HOUR_OF_DAY));
		end = ExDateUtils.getCurrentMax(calendar); // 设置结束时间 当天24点(明天0点)
		System.out.println(DateFormatUtils.format(end, "yyyy-MM-dd HH:mm:ss"));
	}*/

}
