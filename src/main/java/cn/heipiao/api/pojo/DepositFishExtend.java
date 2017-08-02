package cn.heipiao.api.pojo;

import java.io.Serializable;

/**
 * @author zf
 * @version 1.0
 * @description
 * @date 2016年6月30日
 */
public class DepositFishExtend extends DepositFish implements Serializable {
	private static final long serialVersionUID = 884617684524134528L;
	/** 钓场主图 */
	private String mainImg;
	/** 钓场名称 */
	private String fishSiteName;
	/** 钓场地址 */
	private String addr;

	public String getMainImg() {
		return mainImg;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public String getAddr() {
		return addr;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

}
