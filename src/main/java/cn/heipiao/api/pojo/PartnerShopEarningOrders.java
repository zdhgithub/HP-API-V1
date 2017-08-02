/**
 * 
 */
package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
public class PartnerShopEarningOrders extends PartnerEarningOrders{

	/**
	 * 订单号  yyMMddHHmmss + 支付人uid
	 */
	private String orderId;
	
	/**
	 * 钓场id
	 */
	private Long shopId;
	
	/**
	 * 票支付订单号
	 */
	private String shopTradeId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopTradeId() {
		return shopTradeId;
	}

	public void setShopTradeId(String shopTradeId) {
		this.shopTradeId = shopTradeId;
	}
	
}
