package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 支付宝和数据库映射的pojo
 * @date 2016年7月11日
 */
public class AliPayDetail implements Serializable {

	private static final long serialVersionUID = -6339783719555271355L;
	/** 订单号 */
	private String order_id;
	/** 请求报文 */
	private String request_pay_detail;
	/** 卖家支付宝用户号 */
	private String seller_id;
	/** 交易金额 */
	private Double total_fee;
	/** 请求时间 */
	private Date request_time;
	/** 返回报文 */
	private String return_pay_detail;
	/** 返回时间 */
	private Date return_time;
	/** 交易状态 */
	private String return_trade_status;
	/** 商户给支付宝返回的状态 */
	private String return_result_str;

	public String getOrder_id() {
		return order_id;
	}

	public String getRequest_pay_detail() {
		return request_pay_detail;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public Date getRequest_time() {
		return request_time;
	}

	public String getReturn_pay_detail() {
		return return_pay_detail;
	}

	public Date getReturn_time() {
		return return_time;
	}

	public String getReturn_trade_status() {
		return return_trade_status;
	}

	public String getReturn_result_str() {
		return return_result_str;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public void setRequest_pay_detail(String request_pay_detail) {
		this.request_pay_detail = request_pay_detail;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}

	public void setReturn_pay_detail(String return_pay_detail) {
		this.return_pay_detail = return_pay_detail;
	}

	public void setReturn_time(Date return_time) {
		this.return_time = return_time;
	}

	public void setReturn_trade_status(String return_trade_status) {
		this.return_trade_status = return_trade_status;
	}

	public void setReturn_result_str(String return_result_str) {
		this.return_result_str = return_result_str;
	}

}
