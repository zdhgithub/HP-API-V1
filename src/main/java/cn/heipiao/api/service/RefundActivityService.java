package cn.heipiao.api.service;

import cn.heipiao.api.pojo.RefundActivity;

/**
 * @author wzw
 * @date 2017年3月11日
 */
public interface RefundActivityService {

	int activityRefund(RefundActivity ra) throws Exception;

	int activityRefunds(Integer cid) throws Exception;
	
	void refund(String refundNo) throws Exception;

}
