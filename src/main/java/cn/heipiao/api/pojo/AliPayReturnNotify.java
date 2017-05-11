package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zf
 * @version 1.0
 * @description 支付宝回调参数封装
 * @date 2016年7月6日
 */
public class AliPayReturnNotify implements Serializable {

	private static final long serialVersionUID = 1779799903610441790L;
	/** 通知时间 not null */
	private Date notify_time;
	/** 通知类型 not null */
	private String notify_type;
	/** 通知校验ID not null */
	private String notify_id;
	/** 签名方式，固定取值为RSA */
	private String sign_type;
	/** 签名  not null*/
	private String sign;
	/** 订单号null 不建议为空，必须传值，支付宝会原样返回 */
	private String out_trade_no;
	/** 商品名称null，不建议空，必传，支付宝原样返回 */
	private String subject;
	/** 支付类型 null。默认值为：1（商品购买） */
	private String payment_type;
	/** 支付宝交易号 not null */
	private String trade_no;
	/** 交易状态 not null，例如：TRADE_SUCCESS */
	private String trade_status;
	/** 卖家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。not null */
	private String seller_id;
	/** 卖家支付宝账号，可以是email和手机号码。not null */
	private String seller_email;
	/** 买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。 not null */
	private String buyer_id;
	/** 买家支付宝账号，可以是Email或手机号码。 not null */
	private String buyer_email;
	/** 该笔订单的总金额。请求时对应的参数，原样通知回来。not null */
	private Double total_fee;
	/** 购买数量，固定取值为1 null */
	private Integer quantity;
	/** 商品单价 null */
	private Double price;
	/** 商品描述 null */
	private String body;
	/** 交易创建时间 null */
	private Date gmt_create;
	/** 交易付款时间 null */
	private Date gmt_payment;
	/** 该交易是否调整过价格。一般 N or Y ，null */
	private String is_total_fee_adjust;
	/** 是否使用红包买家,一般 N or Y */
	private String use_coupon;
	/** 折扣，单位元 null */
	private String discount;
	/** 退款状态 null，比如：REFUND_SUCCESS */
	private String refund_status;
	/** 退款时间 null */
	private Date gmt_refund;

	public Date getNotify_time() {
		return notify_time;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public String getSign_type() {
		return sign_type;
	}

	public String getSign() {
		return sign;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Double getPrice() {
		return price;
	}

	public String getBody() {
		return body;
	}

	public Date getGmt_create() {
		return gmt_create;
	}

	public Date getGmt_payment() {
		return gmt_payment;
	}

	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}

	public String getUse_coupon() {
		return use_coupon;
	}

	public String getDiscount() {
		return discount;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public Date getGmt_refund() {
		return gmt_refund;
	}

	public void setNotify_time(Date notify_time) {
		this.notify_time = notify_time;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}

	public void setGmt_payment(Date gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}

	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public void setGmt_refund(Date gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

}
