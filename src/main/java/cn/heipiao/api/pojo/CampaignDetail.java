package cn.heipiao.api.pojo;

public class CampaignDetail {
	
	/**
	 * 参加活动总人数
	 */
	private int actorCount;
	
	/**
	 * 报名总费用
	 */
	private float payAmount;

	public CampaignDetail(int actorCount, float payAmount) {
		super();
		this.actorCount = actorCount;
		this.payAmount = payAmount;
	}

	public int getActorCount() {
		return actorCount;
	}

	public void setActorCount(int actorCount) {
		this.actorCount = actorCount;
	}

	public float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(float payAmount) {
		this.payAmount = payAmount;
	}

}
