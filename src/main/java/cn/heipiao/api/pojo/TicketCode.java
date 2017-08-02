package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年8月8日
 */
public class TicketCode implements Serializable {

	private static final long serialVersionUID = 6909511386708459936L;
	/** 数字码 */
	private Integer code;
	/** 票ID */
	private Long ticketId;

	public TicketCode() {

	}

	public TicketCode(Integer code, Long ticketId) {
		this.code = code;
		this.ticketId = ticketId;
	}

	public Integer getCode() {
		return code;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

}
