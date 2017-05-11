/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public class WxPayNotify {

	/**
	 * SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	 */
	private String return_code;

	/**
	 * 返回信息，如非空，为错误原因 签名失败 参数格式校验错误
	 */
	private String return_msg;

	/**
	 * 应用ID
	 */
	private String appid;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 设备号
	 */
	private String device_info;

	/**
	 * 随机字符串
	 */
	private String nonce_str;

	/**
	 * 签名
	 */
	private String sign;

	/**
	 * 业务结果    SUCCESS/FAIL 
	 */
	private String result_code;
	
	/**
	 * 错误代码
	 */
	private String err_code;

	/**
	 * 错误代码描述
	 */
	private String err_code_des;

	/**
	 * 用户标识
	 */
	private String openid;

	/**
	 * 是否关注公众账号
	 */
	private String is_subscribe;

	/**
	 * 交易类型
	 */
	private String trade_type;

	/**
	 * 付款银行
	 */
	private String bank_type;

	/**
	 * 总金额 ,单位:分
	 */
	private Integer total_fee;

	/**
	 * 货币种类
	 */
	private String fee_type;

	/**
	 * 现金支付金额
	 */
	private Integer cash_fee;

	/**
	 * 现金支付货币类型
	 */
	private String cash_fee_type;

	/**
	 * 代金券或立减优惠金额
	 */
	private Integer coupon_fee;

	/**
	 * 代金券或立减优惠使用数量
	 */
	private Integer coupon_count;

	/**
	 * 代金券或立减优惠ID
	 */
	private String coupon_id_index;

	/**
	 * 单个代金券或立减优惠支付金额
	 */
	private Integer coupon_fee_index;

	/**
	 * 微信支付订单号
	 */
	private String transaction_id;

	/**
	 * 商户订单号
	 */
	private String out_trade_no;

	/**
	 * 商家数据包
	 */
	private String attach;

	/**
	 * 支付完成时间
	 */
	private String time_end;

	/**
	 * @return the return_code
	 */
	public String getReturn_code() {
		return return_code;
	}

	/**
	 * @param return_code
	 *            the return_code to set
	 */
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	/**
	 * @return the return_msg
	 */
	public String getReturn_msg() {
		return return_msg;
	}

	/**
	 * @param return_msg
	 *            the return_msg to set
	 */
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid
	 *            the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the mch_id
	 */
	public String getMch_id() {
		return mch_id;
	}

	/**
	 * @param mch_id
	 *            the mch_id to set
	 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	/**
	 * @return the device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info
	 *            the device_info to set
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @return the nonce_str
	 */
	public String getNonce_str() {
		return nonce_str;
	}

	/**
	 * @param nonce_str
	 *            the nonce_str to set
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign
	 *            the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the result_code
	 */
	public String getResult_code() {
		return result_code;
	}

	/**
	 * @param result_code the result_code to set
	 */
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	/**
	 * @return the err_code
	 */
	public String getErr_code() {
		return err_code;
	}

	/**
	 * @param err_code
	 *            the err_code to set
	 */
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	/**
	 * @return the err_code_des
	 */
	public String getErr_code_des() {
		return err_code_des;
	}

	/**
	 * @param err_code_des
	 *            the err_code_des to set
	 */
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the is_subscribe
	 */
	public String getIs_subscribe() {
		return is_subscribe;
	}

	/**
	 * @param is_subscribe
	 *            the is_subscribe to set
	 */
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	/**
	 * @return the trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * @param trade_type
	 *            the trade_type to set
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * @return the bank_type
	 */
	public String getBank_type() {
		return bank_type;
	}

	/**
	 * @param bank_type
	 *            the bank_type to set
	 */
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	/**
	 * @return the total_fee
	 */
	public Integer getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee
	 *            the total_fee to set
	 */
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the fee_type
	 */
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * @param fee_type
	 *            the fee_type to set
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * @return the cash_fee
	 */
	public Integer getCash_fee() {
		return cash_fee;
	}

	/**
	 * @param cash_fee
	 *            the cash_fee to set
	 */
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	/**
	 * @return the cash_fee_type
	 */
	public String getCash_fee_type() {
		return cash_fee_type;
	}

	/**
	 * @param cash_fee_type
	 *            the cash_fee_type to set
	 */
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	/**
	 * @return the coupon_fee
	 */
	public Integer getCoupon_fee() {
		return coupon_fee;
	}

	/**
	 * @param coupon_fee
	 *            the coupon_fee to set
	 */
	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	/**
	 * @return the coupon_count
	 */
	public Integer getCoupon_count() {
		return coupon_count;
	}

	/**
	 * @param coupon_count
	 *            the coupon_count to set
	 */
	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}

	/**
	 * @return the coupon_id_index
	 */
	public String getCoupon_id_index() {
		return coupon_id_index;
	}

	/**
	 * @param coupon_id_index
	 *            the coupon_id_index to set
	 */
	public void setCoupon_id_index(String coupon_id_index) {
		this.coupon_id_index = coupon_id_index;
	}

	/**
	 * @return the coupon_fee_index
	 */
	public Integer getCoupon_fee_index() {
		return coupon_fee_index;
	}

	/**
	 * @param coupon_fee_index
	 *            the coupon_fee_index to set
	 */
	public void setCoupon_fee_index(Integer coupon_fee_index) {
		this.coupon_fee_index = coupon_fee_index;
	}

	/**
	 * @return the transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id
	 *            the transaction_id to set
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no
	 *            the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach
	 *            the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the time_end
	 */
	public String getTime_end() {
		return time_end;
	}

	/**
	 * @param time_end
	 *            the time_end to set
	 */
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
	/**
	 * @param wx
	 */
	public void toCopy(WxPayNotify wx) {
		wx.setAppid(appid);
		wx.setAttach(attach);
		wx.setBank_type(bank_type);
		wx.setCash_fee(cash_fee);
		wx.setCash_fee_type(cash_fee_type);
		wx.setCoupon_count(coupon_count);;
		wx.setCoupon_fee(coupon_fee);
		wx.setCoupon_fee_index(coupon_fee_index);
		wx.setCoupon_id_index(coupon_id_index);
		wx.setDevice_info(device_info);
		wx.setErr_code(err_code);
		wx.setErr_code_des(err_code_des);
		wx.setFee_type(fee_type);
		wx.setIs_subscribe(is_subscribe);
		wx.setMch_id(mch_id);
		wx.setNonce_str(nonce_str);
		wx.setOpenid(openid);
		wx.setOut_trade_no(out_trade_no);
		wx.setResult_code(result_code);
		wx.setReturn_code(return_code);
		wx.setReturn_msg(return_msg);
		wx.setSign(sign);
		wx.setTime_end(time_end);
		wx.setTotal_fee(total_fee);
		wx.setTrade_type(trade_type);
		wx.setTransaction_id(transaction_id);
		
	}
	
}
