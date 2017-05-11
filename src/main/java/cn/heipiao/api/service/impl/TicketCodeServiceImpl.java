package cn.heipiao.api.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.TicketCodeMapper;
import cn.heipiao.api.mapper.UserMapper;
import cn.heipiao.api.mapper.UserTicketsMapper;
import cn.heipiao.api.pojo.TicketCode;
import cn.heipiao.api.pojo.User;
import cn.heipiao.api.pojo.UserTickets;
import cn.heipiao.api.resources.Status;
import cn.heipiao.api.service.TicketCodeService;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年8月8日
 */
@Service
public class TicketCodeServiceImpl implements TicketCodeService {
	@Resource
	private TicketCodeMapper ticketCodeMapper;

	@Resource
	private UserTicketsMapper userTicketsMapper;

	@Resource
	private UserMapper userMapper;

	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	@Override
	public int generateTicketCode(Long ticketId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ticketId", ticketId);
		List<TicketCode> list = ticketCodeMapper.selectList(params);
		if (list != null && list.size() > 0) {
			return list.get(0).getCode();
		}
		Integer code = generateCode();
		TicketCode ticketCode = new TicketCode(code, ticketId);
		ticketCodeMapper.insert(ticketCode);
		return code;
	}

	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	@Override
	public void deleteTicketCode(Integer code) throws Exception {
		ticketCodeMapper.deleteById(code);
	}

	/**
	 * 生成票ID策略
	 * 
	 * @return
	 */
	private Integer generateCode() {
//		SimpleDateFormat sdf = new SimpleDateFormat("ddHH");
//		String date = sdf.format(ExDateUtils.getDate());
		Integer number = RandomUtils.nextInt(10000000, 100000000);
//		int id = Integer.parseInt(StringUtils.join(number.toString(), date));
		int id = number;
		TicketCode tc = ticketCodeMapper.selectById(id);
		if (tc == null) {
			return id;
		} else {
			id = generateCode();
		}
		return id;
	}

	@Override
	public Map<String, Object> confirmTicketCode(Integer code) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 票ID
		TicketCode ticketCode = ticketCodeMapper.selectById(code);
		if (ticketCode == null) {
			result.put("status", Status.INVALID_CODE);
			return result;
		}
		// 票信息
		UserTickets userTicket = userTicketsMapper
				.selectTicketsByTid(ticketCode.getTicketId());
		if (userTicket == null) {
			result.put("status", Status.INVALID_CODE);
			return result;
		}
		// 用户名，用户头像
		User user = userMapper.selectById(userTicket.getUid());
		if (user == null) {
			result.put("status", Status.INVALID_CODE);
			return result;
		}
		result = Bean2Map(userTicket);
		result.put("nickname", user.getNickname());
		result.put("portrait", user.getPortriat());
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> Bean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<K, V>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);
					ret.put((K) field, (V) (null == value ? null : value));
				}
			}
		} catch (Exception e) {
		}
		return ret;
	}

	@Override
	public TicketCode getTicketCodeByCode(Integer code) throws Exception {
		TicketCode ticketCode = ticketCodeMapper.selectById(code);
		return ticketCode;
	}
}
