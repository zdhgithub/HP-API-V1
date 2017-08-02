package cn.heipiao.api.pojo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public class HpArticle {

	@ApiModelProperty("文章idLong")
	private Long articleId;

	@ApiModelProperty("用户id")
	private Long articleUid;
	
	@ApiModelProperty("商家类型：0：用户，1：钓场，2：渔具店，10:黑漂有态度 ,11:活动回顾")
	private Integer category;
	
	@ApiModelProperty("文章类型：1：内容型，2：链接型")
	private Integer articleCategory;

	@ApiModelProperty("文章标题")
	private String title;
	
	@ApiModelProperty("文章内容(内容型文章)")
	private String content;

	@ApiModelProperty("详细内容(链接型的文章才有)")
	private String contentDetail;

	@ApiModelProperty("封面图")
	private String banner;

	@ApiModelProperty("动态图片或视频url。如果是长文返回值中代表是长文的url")
	private String url;
	
	@ApiModelProperty("文章细分 0:纯文本，1:文本+图片，2：文本+视频")
	private Integer articleType;

	@ApiModelProperty("点赞数量")
	private Integer likeCount;

	@ApiModelProperty("评论数量")
	private Integer commentCount;

	@ApiModelProperty("类型 表t_article_dict")
	private Integer type;
	
	@ApiModelProperty("类型说明")
	private String typeDesc;

	@ApiModelProperty("位置名称")
	private String positionName;

	@ApiModelProperty("商家id")
	private Long businessId;
	
	@ApiModelProperty("商家名称")
	private String businessName;

	@ApiModelProperty("经度")
	private Double longitude;

	@ApiModelProperty("纬度")
	private Double latitude;

	@ApiModelProperty("城市id")
	private Integer cityId;

	@ApiModelProperty("发布时间")
	private Long articleTime;
	
	@ApiModelProperty("用户昵称")
	private String articleNickname;
	
	@ApiModelProperty("用户头像")
	private String articlePortrait; 
	
	@ApiModelProperty("商家主图")
	private String businessPortrait;
	
	@ApiModelProperty("外部关联的文章id")
	private Long otherArticleId;
	
	@ApiModelProperty("发布动态或长文时推荐输入的商家名称")
	private String remarkName;
	
	@ApiModelProperty("文章是否删除，true是，false：否")
	private Boolean isDelete;
	
	@ApiModelProperty("浏览量")
	private Long readCount;
	
	private List<HpArticleComments> comments;
	
	private List<HpArticleLikes> likes;

	
	public List<HpArticleComments> getComments() {
		return comments;
	}

	public void setComments(List<HpArticleComments> comments) {
		this.comments = comments;
	}

	public List<HpArticleLikes> getLikes() {
		return likes;
	}

	public void setLikes(List<HpArticleLikes> likes) {
		this.likes = likes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(Integer articleCategory) {
		this.articleCategory = articleCategory;
	}

	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public Long getArticleUid() {
		return articleUid;
	}

	public void setArticleUid(Long articleUid) {
		this.articleUid = articleUid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Long getArticleTime() {
		return articleTime;
	}

	public void setArticleTime(Long articleTime) {
		this.articleTime = articleTime;
	}

	public String getArticleNickname() {
		return articleNickname;
	}

	public void setArticleNickname(String articleNickname) {
		this.articleNickname = articleNickname;
	}

	public String getArticlePortrait() {
		return articlePortrait;
	}

	public void setArticlePortrait(String articlePortrait) {
		this.articlePortrait = articlePortrait;
	}

	public String getBusinessPortrait() {
		return businessPortrait;
	}

	public void setBusinessPortrait(String businessPortrait) {
		this.businessPortrait = businessPortrait;
	}

	public Long getOtherArticleId() {
		return otherArticleId;
	}

	public void setOtherArticleId(Long otherArticleId) {
		this.otherArticleId = otherArticleId;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount(Long readCount) {
		this.readCount = readCount;
	}

}
