/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public class AliPayNotify {

	/**
	 * 通知时间
	 */
	private String notify_time;
	
	/**
	 * 通知类型
	 */
	private String notify_type;
	
	/**
	 * 通知校验ID
	 */
	private String notify_id;
	
	/**
	 * 签名方式
	 */
	private String sign_type;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 商户网站唯一订单号
	 */
	private String out_trade_no;
	
	/**
	 * 商品名称
	 */
	private String subject;
	
	/**
	 * 支付类型
	 */
	private String payment_type;
	
	/**
	 * 支付宝交易号
	 */
	private String trade_no;
	
	/**
	 * 交易状态
	 */
	private String trade_status;
	
	/**
	 * 卖家支付宝用户号
	 */
	private String seller_id;
	
	/**
	 * 卖家支付宝账号
	 */
	private String seller_email;
	
	/**
	 * 买家支付宝用户号
	 */
	private String buyer_id;
	
	/**
	 * 买家支付宝账号
	 */
	private String buyer_email;
	
	/**
	 * 交易金额
	 */
	private Double total_fee;
	
	/**
	 * 购买数量
	 */
	private Integer quantity;
	
	/**
	 * 商品单价
	 */
	private Double price;
	
	/**
	 * 该笔订单的备注、描述、明细等。对应请求时的body参数，原样通知回来
	 */
	private String body;
	
	/**
	 * 交易创建时间
	 */
	private String gmt_create; 

	/**
	 * 交易付款时间
	 */
	private String gmt_payment;
	
	/**
	 * 是否调整总价
	 */
	private String is_total_fee_adjust;
	
	/**
	 * 是否使用红包买家
	 */
	private String use_coupon;
	
	/**
	 * 折扣
	 */
	private Double discount;
	
	/**
	 * 退款状态
	 */
	private String refund_status;
	
	/**
	 * 卖家退款的时间，退款通知时会发送。格式为yyyy-MM-dd HH:mm:ss。
	 */
	private String gmt_refund;
	
	/**
	 * 交易关闭时间
	 */
	private String gmt_close;
	
	/**
	 * 商户自定义参数
	 */
	private String attach;

	/**
	 * @return the notify_time
	 */
	public String getNotify_time() {
		return notify_time;
	}

	/**
	 * @param notify_time the notify_time to set
	 */
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	/**
	 * @return the notify_type
	 */
	public String getNotify_type() {
		return notify_type;
	}

	/**
	 * @param notify_type the notify_type to set
	 */
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	/**
	 * @return the notify_id
	 */
	public String getNotify_id() {
		return notify_id;
	}

	/**
	 * @param notify_id the notify_id to set
	 */
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	/**
	 * @return the sign_type
	 */
	public String getSign_type() {
		return sign_type;
	}

	/**
	 * @param sign_type the sign_type to set
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the payment_type
	 */
	public String getPayment_type() {
		return payment_type;
	}

	/**
	 * @param payment_type the payment_type to set
	 */
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	/**
	 * @return the trade_no
	 */
	public String getTrade_no() {
		return trade_no;
	}

	/**
	 * @param trade_no the trade_no to set
	 */
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	/**
	 * @return the trade_status
	 */
	public String getTrade_status() {
		return trade_status;
	}

	/**
	 * @param trade_status the trade_status to set
	 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	/**
	 * @return the seller_id
	 */
	public String getSeller_id() {
		return seller_id;
	}

	/**
	 * @param seller_id the seller_id to set
	 */
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	/**
	 * @return the seller_email
	 */
	public String getSeller_email() {
		return seller_email;
	}

	/**
	 * @param seller_email the seller_email to set
	 */
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	/**
	 * @return the buyer_id
	 */
	public String getBuyer_id() {
		return buyer_id;
	}

	/**
	 * @param buyer_id the buyer_id to set
	 */
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	/**
	 * @return the buyer_email
	 */
	public String getBuyer_email() {
		return buyer_email;
	}

	/**
	 * @param buyer_email the buyer_email to set
	 */
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	/**
	 * @return the total_fee
	 */
	public Double getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the gmt_create
	 */
	public String getGmt_create() {
		return gmt_create;
	}

	/**
	 * @param gmt_create the gmt_create to set
	 */
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	/**
	 * @return the gmt_payment
	 */
	public String getGmt_payment() {
		return gmt_payment;
	}

	/**
	 * @param gmt_payment the gmt_payment to set
	 */
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	/**
	 * @return the is_total_fee_adjust
	 */
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}

	/**
	 * @param is_total_fee_adjust the is_total_fee_adjust to set
	 */
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}

	/**
	 * @return the use_coupon
	 */
	public String getUse_coupon() {
		return use_coupon;
	}

	/**
	 * @param use_coupon the use_coupon to set
	 */
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}

	/**
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * @return the refund_status
	 */
	public String getRefund_status() {
		return refund_status;
	}

	/**
	 * @param refund_status the refund_status to set
	 */
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	/**
	 * @return the gmt_refund
	 */
	public String getGmt_refund() {
		return gmt_refund;
	}

	/**
	 * @param gmt_refund the gmt_refund to set
	 */
	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}

	/**
	 * @return the gmt_close
	 */
	public String getGmt_close() {
		return gmt_close;
	}

	/**
	 * @param gmt_close the gmt_close to set
	 */
	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @param ali
	 */
	public void toCopy(AliPayNotify ali) {
		ali.setAttach(attach);
		ali.setBody(body);
		ali.setBuyer_email(buyer_email);
		ali.setBuyer_id(buyer_id);
		ali.setDiscount(discount);
		ali.setGmt_close(gmt_close);
		ali.setGmt_create(gmt_create);
		ali.setGmt_payment(gmt_payment);
		ali.setGmt_refund(gmt_refund);
		ali.setIs_total_fee_adjust(is_total_fee_adjust);
		ali.setNotify_id(notify_id);
		ali.setNotify_time(notify_time);
		ali.setNotify_type(notify_type);
		ali.setOut_trade_no(out_trade_no);
		ali.setPayment_type(payment_type);
		ali.setPrice(price);
		ali.setQuantity(quantity);
		ali.setRefund_status(refund_status);
		ali.setSeller_email(seller_email);
		ali.setSeller_id(seller_id);
		ali.setSign(sign);
		ali.setSign_type(sign_type);
		ali.setSubject(subject);
		ali.setTotal_fee(total_fee);
		ali.setTrade_no(trade_no);
		ali.setTrade_status(trade_status);
		ali.setUse_coupon(use_coupon);
	}
	
	

}