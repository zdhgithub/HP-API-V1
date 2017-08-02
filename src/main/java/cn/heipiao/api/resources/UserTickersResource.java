/**
 * 
 */
package cn.heipiao.api.resources;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import cn.heipiao.api.constant.RespMessage;
import cn.heipiao.api.pojo.Emp;
import cn.heipiao.api.pojo.Refund;
import cn.heipiao.api.pojo.RespMsg;
import cn.heipiao.api.pojo.TicketCode;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.service.EmpService;
import cn.heipiao.api.service.TicketCodeService;
import cn.heipiao.api.service.UserTicketsService;
import io.swagger.annotations.Api;

/**
 * @author wzw
 * @date 2016年7月1日
 * @version 1.0
 */
//@Path("ticket/")
//@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Consumes({ MediaType.APPLICATION_JSON + ";charset=utf-8" })
//@Component
@Api(tags = "票模块")
@RestController
@RequestMapping(value = "ticket",produces="application/json")
public class UserTickersResource {

	private static final Logger logger = LoggerFactory.getLogger(UserTickersResource.class);
	
	@Resource
	private UserTicketsService userTickersService;
	
	@Resource
	private EmpService empService;
	
	@Resource
	private TicketCodeService ticketCodeService;

	/**
	 * 通过uid查询票
	 * @param uid
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/uid/{uid}/{status}/{start}/{size}")
	@RequestMapping(value = "list/uid/{uid}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String getTicketsByUid(@PathVariable("uid") Long uid,
			@PathVariable("status")Integer status,@PathVariable("start")Long start,@PathVariable("size")Integer size){
		try {
			logger.debug("uid:{},status:{},start:{},size:{}",uid,status,start,size);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("uid", uid);
			map.put("status", status);
			map.put("start", start > 0 ? (start - 1) * size : 0);
			map.put("size", size);
			List<UserTickets> pojos = userTickersService.selectTicketsByUid(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	
	/**
	 * 通过钓场查询票
	 * @param siteId
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/site/{siteId}/{status}/{start}/{size}")
	@RequestMapping(value = "list/site/{siteId}/{status}/{start}/{size}",method = RequestMethod.GET)
	public String  getTicketsBySite(@PathVariable("siteId") Long siteId,
			@PathVariable("status")Integer status,@PathVariable("start")Long start,@PathVariable("size")Integer size){
		try {
			logger.debug("siteId:{},status:{},start:{},size:{}",siteId,status,start,size);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("fishSiteId", siteId);
			map.put("status", status);
			map.put("start", start > 0 ? (start - 1) * size : 0);
			map.put("size", size);
			List<UserTickets> pojos = userTickersService.selectTicketsBySite(map);
			return JSONObject.toJSONString(new RespMsg<>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 获取唯一的票
	 * @return
	 */
//	@GET
////	@Path("{tid}/{uid}/{fishSiteId}/{euid}")
//	@Path("{tid}/{fishSiteId}/{euid}")
	@RequestMapping(value = "{tid}/{fishSiteId}/{euid}",method = RequestMethod.GET)
	public String getUniqueTickets(@PathVariable("tid") Long tid
			,@PathVariable("fishSiteId") Integer fishSiteId,@PathVariable("euid") Long euid){
		
		try {
//			logger.info("tid:{},uid:{},fishSiteId:{},euid:{}",tid,uid,fishSiteId,euid);
			logger.info("tid:{},fishSiteId:{},euid:{}",tid,fishSiteId,euid);
			
			//验证员工
			Emp emp = empService.selectById(euid);
			if(emp == null || emp.getFishSiteId().intValue() != fishSiteId.intValue() || emp.getEmpStatus().equals("0")){
				return JSONObject.toJSONString(new RespMsg<>(Status.EMP_NOT_EXISTS,RespMessage.getRespMsg(Status.EMP_NOT_EXISTS)));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("tid", tid);
//			map.put("uid", uid);
			map.put("fishSiteId", fishSiteId);
			UserTickets ut = userTickersService.selectUniqueTicket(map);
			//判断票
			if(ut == null || (ut.getStatus().intValue() != 0 && ut.getStatus().intValue() != 3)){
				return JSONObject.toJSONString(new RespMsg<>(Status.userTicket_invalid,RespMessage.getRespMsg(Status.userTicket_invalid)));
			}
			return JSONObject.toJSONString(new RespMsg<>(ut));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * {"uid":1,"tid":1,"reason":"退票原因"}
	 * 
	 * 退票
	 * @return
	 */
//	@PUT
//	@Path("refundTickets")
	@RequestMapping(value = "refundTickets",method = RequestMethod.PUT)
	public String cancelTickets(@RequestBody JSONObject json){
		try {
			logger.debug("json:{}",json);
			Refund r = json == null ? null : JSONObject.toJavaObject(json, Refund.class);
			if(r == null || r.getUid() == null || r.getTid() == null || r.getReason() == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			//生成退票订单
			int rs = userTickersService.refundTickerApply(r);
			
			//第三方退款走第三方退款流程 FIXME 改成由调度程序统一处理
			if(rs == 0 && r.getTradePlatform() != 0){
				//退票钱流程
				try {
					userTickersService.refundTicker(r.getRefundNo());
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
			return JSONObject.toJSONString(new RespMsg<>(rs,RespMessage.getRespMsg(rs)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 核票
	 * 
	 * {"uid":1,"tid":1,"fishSiteId":2,"euid":3}
	 * 
	 *  {"tid":1,"fishSiteId":2,"euid":3,"extraTime":1}
	 * 
	 * @param json
	 * @return
	 */
//	@PUT
//	@Path("verifyTickets")
	@RequestMapping(value = "verifyTickets",method = RequestMethod.PUT)
	public String verifyTickets(@RequestBody JSONObject json){
		
		try {
			logger.info("json:{}",json);
//			Long uid = json == null ? null : json.getLong("uid");
			Long tid = json == null ? null : json.getLong("tid");
			Integer fishSiteId = json == null ? null : json.getInteger("fishSiteId");
			Long euid = json == null ? null : json.getLong("euid");
			Integer extraTime = json == null ? null : json.getInteger("extraTime");
			if(tid == null || fishSiteId == null || euid == null || extraTime == null){
				return JSONObject.toJSONString(new RespMsg<>(Status.value_is_null_or_error,RespMessage.getRespMsg(Status.value_is_null_or_error)));
			}
			//验证员工
			Emp emp = empService.selectById(euid);
			if(emp == null || emp.getFishSiteId().intValue() != fishSiteId || emp.getEmpStatus().equals("0")){
				return JSONObject.toJSONString(new RespMsg<>(Status.EMP_NOT_EXISTS,RespMessage.getRespMsg(Status.EMP_NOT_EXISTS)));
			}
			
			//验证票
			UserTickets ticker = userTickersService.selectTicketsByTid(tid);
			if(ticker == null || (ticker.getStatus().intValue() != 0 && ticker.getStatus().intValue() != 3) || ticker.getFishSiteId().intValue() != fishSiteId.intValue()){
				return JSONObject.toJSONString(new RespMsg<>(Status.userTicket_invalid,RespMessage.getRespMsg(Status.userTicket_invalid)));
			}
			//开杆时间处理
			extraTime = extraTime.intValue() == 1 ? 30 * 60 * 1000 : 0 ;
			
			//业务处理
			int re = userTickersService.verifyTicket(tid,extraTime,euid);
			return JSONObject.toJSONString(new RespMsg<>(re,RespMessage.getRespMsg(re)));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,RespMessage.getRespMsg(Status.error)));
		}
	}
	/**
	 * 生成票的编码
	 * 
	 * @param tid
	 * @return
	 */
//	@Path("ticketCode/{tid}")
//	@GET
	@RequestMapping(value = "ticketCode/{tid}",method = RequestMethod.GET)
	public String generateTicketCode(@PathVariable("tid") final Long tid) {
		try {
			logger.debug("票ID:{}",tid);
			Integer code =  ticketCodeService.generateTicketCode(tid);
			Map<String,Object> result = new HashMap<String, Object>();
			result.put("code", code);
			return JSONObject.toJSONString(new RespMsg<Map<String,Object>>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
		
	}
	/**
	 * 票编码核票确认
	 * @param json
	 * @return
	 */
//	@Path("confirm/{code}/{euid}/{fishSiteId}")
//	@GET
	@RequestMapping(value = "confirm/{code}/{euid}/{fishSiteId}",method = RequestMethod.GET)
	public String confirmTicketCode(@PathVariable("code")final Integer code
			,@PathVariable("euid") final Long euid
			,@PathVariable("fishSiteId") final Integer fishSiteId) {
		logger.debug("核票编码:{},员工ID:{},钓场ID:{}",code,euid,fishSiteId);
		try {
			//验证员工
			Emp emp = empService.selectById(euid);
			if(emp == null || emp.getFishSiteId().intValue() != fishSiteId.intValue() || emp.getEmpStatus().equals("0")){
				return JSONObject.toJSONString(new RespMsg<>(Status.EMP_NOT_EXISTS));
			}
			logger.debug(emp.getFishSiteId() + ":" + emp.getEmpStatus());
			Map<String,Object> map = new HashMap<String, Object>();
			TicketCode ticketCode = ticketCodeService.getTicketCodeByCode(code);
			if(ticketCode==null) {
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.INVALID_CODE));
			}
			UserTickets ticket = userTickersService.selectTicketsByTid(ticketCode.getTicketId());
			map.put("tid", ticketCode.getTicketId());
			map.put("uid", ticket.getUid());
			map.put("fishSiteId", fishSiteId);
			UserTickets ut = userTickersService.selectUniqueTicket(map);
			//判断票
			if(ut == null || ut.getStatus().intValue() != 0){
				return JSONObject.toJSONString(new RespMsg<>(Status.userTicket_invalid));
			}
			Map<String,Object> result = ticketCodeService.confirmTicketCode(code);
			if((Integer)result.get("status") == Status.INVALID_CODE) {
				return JSONObject.toJSONString(new RespMsg<Integer>(Status.INVALID_CODE));
			}
			return JSONObject.toJSONString(new RespMsg<Map<String,Object>>(result));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error));
		}
	}
	
	
	/**
	 * 用户查找订单列表
	 * 
	 * @param uid
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
//	@GET
//	@Path("list/{siteId}/{index}/{size}")
	@RequestMapping(value = "list/{siteId}/{index}/{size}",method = RequestMethod.GET)
	public String getOrdersBySiteId(@PathVariable("siteId") Long siteId, @PathVariable("index") Long index,
			@PathVariable("size") Integer size) {
		try {
			logger.debug("siteId:{},index:{},size:{}", siteId, index, size);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("siteId", siteId);
			map.put("index", index > 0 ? new Timestamp(index) : null);
			map.put("size", size);
			List<UserTickets> pojos = userTickersService.getOrdersListBySiteId(map);
			return JSONObject.toJSONString(new RespMsg<List<UserTickets>>(pojos));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
	
	/**
	 * 通过tid查询票信息
	 * @return
	 */
//	@Path("tid/{tid}")
//	@GET
	@RequestMapping(value = "tid/{tid}",method = RequestMethod.GET)
	public String getTicketByTid(@PathVariable("tid")Long tid){
		try {
			UserTickets ut = userTickersService.selectTicketsByTid(tid);
			return JSONObject.toJSONString(new RespMsg<>(ut));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.toJSONString(new RespMsg<>(Status.error,
					RespMessage.getRespMsg(Status.error)));
		}
	}
}
