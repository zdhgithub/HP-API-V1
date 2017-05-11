package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zf
 * @version 1.0
 * @description 商品
 * @date 2016年6月28日
 */
public class Goods implements Serializable {

	private static final long serialVersionUID = 2727951136997845671L;
	/** 商品ID */
	private Integer goodId;
	/** 钓场ID */
	private Integer fishSiteId;
	/** 商品名 */
	private String name;
	/** 头像url */
	private String url;
	/** 商品原价格 */
	private Double price;
	/** 商品商品优惠价 */
	private Double discountPrice;
	/** 商品数量 */
	private Integer amount;
	/** 线下消费产品id（塘ID） */
	private Integer pId;
	/** 描述 */
	private String description;
	/** 商品分类--1:票 */
	private Integer category;
	/** 状态 0:删除(下架),1:正常(上架) */
	private Integer status;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 时长 */
	private Float hourLong;

	public Integer getGoodId() {
		return goodId;
	}

	public Integer getFishSiteId() {
		return fishSiteId;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Double getPrice() {
		return price;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public Integer getAmount() {
		return amount;
	}

	public Integer getpId() {
		return pId;
	}

	public String getDescription() {
		return description;
	}

	public Integer getCategory() {
		return category;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public Float getHourLong() {
		return hourLong;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public void setFishSiteId(Integer fishSiteId) {
		this.fishSiteId = fishSiteId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setHourLong(Float hourLong) {
		this.hourLong = hourLong;
	}

	@Override
	public String toString() {
		Class<? extends Goods> good = this.getClass();
		Field[] field = good.getDeclaredFields();
		String str = "";
		for (Field f : field) {
			try {
				f.setAccessible(true);
				if(f.getName().contains("serialVersionUID")) {
					continue;
				}
				Object obj = f.get(this);
				str = StringUtils.join(str, f.getName(), ":", obj, "; ");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}
