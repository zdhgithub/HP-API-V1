/**
 * 
 */
package cn.heipiao.api.pojo;

import java.sql.Timestamp;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 * 
 */
public class AccountBill {

	/**
	 * 用户id
	 */
	private Long uid;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 1:进账，2：出账
	 */
	private Integer inOut;
	
	/**
	 * 如果有新的类型继续添加新值
	 * 类型
	 * 
	 * 1:购票, 2:充值, 3:渔具店支付, 4:核票, 5:钓场主提现, 6:渔具店提现, 7:存鱼, 
	 * 8:推荐奖励, 9:合伙人钓场收益,  10:漂币,  11:退票,  12:合伙人提现, 13:票支付奖励,14:购票奖励,
	 * 15:合伙人店铺收益,16:分享渔获奖励,17:平台奖励提现,18：合伙人开发奖,19:微信首次支付奖励,20:活动报名费
	 * 21:退活动报名费
	 */
	private Integer type;
	
	/**
	 * 1:第三方，2:漂币，3:存鱼
	 */
	private Integer subType;
	
	/**
	 * 描述信息
	 */
	private String desc;
	
	/**
	 * 交易金额
	 */
	private Double tradeFee;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;


	public Long getUid() {
		return uid;
	}


	public void setUid(Long uid) {
		this.uid = uid;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Integer getInOut() {
		return inOut;
	}


	public void setInOut(Integer inOut) {
		this.inOut = inOut;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	public Integer getSubType() {
		return subType;
	}


	public void setSubType(Integer subType) {
		this.subType = subType;
	}


	public Double getTradeFee() {
		return tradeFee;
	}


	public void setTradeFee(Double tradeFee) {
		this.tradeFee = tradeFee;
	}
	
}
