package cn.heipiao.api.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zf
 * @version 1.0
 * @description 支付回调通知接收参数
 * @date 2016年7月1日
 */
@XmlRootElement
public class WXPayReturnNotify implements Serializable {
	private static final long serialVersionUID = -4316685735108501353L;
	/** 应用ID */
	private String appid;
	/** 商家数据包 */
	private String attach;
	/** 付款银行 */
	private String bank_type;
	/** 货币种类 */
	private String fee_type;
	/** 是否关注公众账号 */
	private String is_subscribe;
	/** 商户号 */
	private String mch_id;
	/** 随机字符串 */
	private String nonce_str;
	/** 用户标识 */
	private String openid;
	/** 商户订单号 */
	private String out_trade_no;
	/** 业务结果 */
	private String result_code;
	/** 返回信息 */
	private String return_code;
	/** 签名 */
	private String sign;
	/***/
	private String sub_mch_id;
	/** 支付完成时间 */
	private String time_end;
	/** 订单总金额，单位为分 */
	private Integer total_fee;
	/** 交易类型APP */
	private String trade_type;
	/** 微信支付订单号 */
	private String transaction_id;
	/** 设备号 */
	private String device_info;
	/** 错误代码 */
	private String err_code;
	/** 现金支付金额 */
	private Integer cash_fee;
	/** 现金支付货币类型 */
	private Integer cash_fee_type;
	/** 代金券或立减优惠金额 */
	private Integer coupon_fee;
	/** 代金券或立减优惠使用数量 */
	private Integer coupon_count;
	@XmlElement
	public String getAppid() {
		return appid;
	}
	@XmlElement
	public String getAttach() {
		return attach;
	}

	public String getBank_type() {
		return bank_type;
	}

	public String getFee_type() {
		return fee_type;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public String getMch_id() {
		return mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public String getOpenid() {
		return openid;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public String getResult_code() {
		return result_code;
	}

	public String getReturn_code() {
		return return_code;
	}

	public String getSign() {
		return sign;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public String getTime_end() {
		return time_end;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public String getErr_code() {
		return err_code;
	}

	public Integer getCash_fee() {
		return cash_fee;
	}

	public Integer getCash_fee_type() {
		return cash_fee_type;
	}

	public Integer getCoupon_fee() {
		return coupon_fee;
	}

	public Integer getCoupon_count() {
		return coupon_count;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	public void setCash_fee_type(Integer cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}

}
