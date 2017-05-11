package cn.heipiao.api.pojo;

/**
 * @author wzw
 * @date 2017年3月31日
 */
public class UserProfile {

	private Long uid;

	private Integer msgSum;
	
	private String articleBanner;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getArticleBanner() {
		return articleBanner;
	}

	public void setArticleBanner(String articleBanner) {
		this.articleBanner = articleBanner;
	}

	public Integer getMsgSum() {
		return msgSum;
	}

	public void setMsgSum(Integer msgSum) {
		this.msgSum = msgSum;
	}

}
