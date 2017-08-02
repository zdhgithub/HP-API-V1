package cn.heipiao.api.pojo;

/**
 * 
 * @ClassName: ImpressionRecord
 * @Description: TODO
 * @author zf
 * @date 2016年10月22日
 */
public class ImpressionRecord {
	private Integer targetId;
	private Long shopId;
	private Integer uid;
	private String label;

	public Integer getTargetId() {
		return targetId;
	}

	public Long getShopId() {
		return shopId;
	}

	public Integer getUid() {
		return uid;
	}

	public String getLabel() {
		return label;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
