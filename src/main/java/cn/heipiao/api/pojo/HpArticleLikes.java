package cn.heipiao.api.pojo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public class HpArticleLikes {
	
	@ApiModelProperty("文章id")
	private Long articleId;

	@ApiModelProperty("点赞用户id")
	private Long likeUid;

	@ApiModelProperty("点赞时间(毫秒)")
	private Long likeTime;
	
	@ApiModelProperty("点赞用户昵称")
	private String likeNickname;
	
	@ApiModelProperty("点赞用户头像")
	private String likePortrait;

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getLikeUid() {
		return likeUid;
	}

	public void setLikeUid(Long likeUid) {
		this.likeUid = likeUid;
	}

	public Long getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Long likeTime) {
		this.likeTime = likeTime;
	}

	public String getLikeNickname() {
		return likeNickname;
	}

	public void setLikeNickname(String likeNickname) {
		this.likeNickname = likeNickname;
	}

	public String getLikePortrait() {
		return likePortrait;
	}

	public void setLikePortrait(String likePortrait) {
		this.likePortrait = likePortrait;
	}

}
