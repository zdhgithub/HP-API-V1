package cn.heipiao.api.pojo;
/**
 * @author wzw
 * @date 2017年3月10日
 */
public class ShopFinance {

	
	private Long shopId;
	
	private Long uid;
	
	private String shopRealname;
	
	private String shopPhoneNum;
	
	private String shopName;
	
	private String shopImg;
	
	private Double balance;
	
	private Double tradeFee;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getShopRealname() {
		return shopRealname;
	}

	public void setShopRealname(String shopRealname) {
		this.shopRealname = shopRealname;
	}

	public String getShopPhoneNum() {
		return shopPhoneNum;
	}

	public void setShopPhoneNum(String shopPhoneNum) {
		this.shopPhoneNum = shopPhoneNum;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopImg() {
		return shopImg;
	}

	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(Double tradeFee) {
		this.tradeFee = tradeFee;
	}
	
	
	
}
