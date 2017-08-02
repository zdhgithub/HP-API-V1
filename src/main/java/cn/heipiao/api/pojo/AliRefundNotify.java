/**
 * 
 */
package cn.heipiao.api.pojo;

import java.util.Date;

/**
 * @author wzw
 * @date 2016年7月21日
 * @version 1.0
 */
public class AliRefundNotify {

	/**
	 * 通知时间
	 */
	private Date notify_time;

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
	 * 退款id 主键
	 */
	private String batch_no;

	/**
	 * 退款交易总笔数
	 */
	private String success_num;

	/**
	 * 处理结果详情
	 */
	private String result_details;

	/**
	 * 解冻结果明细
	 */
	private String unfreezed_details;

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

	public String getBatch_no() {
		return batch_no;
	}

	public String getSuccess_num() {
		return success_num;
	}

	public String getResult_details() {
		return result_details;
	}

	public String getUnfreezed_details() {
		return unfreezed_details;
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

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}

	public void setResult_details(String result_details) {
		this.result_details = result_details;
	}

	public void setUnfreezed_details(String unfreezed_details) {
		this.unfreezed_details = unfreezed_details;
	}

}
