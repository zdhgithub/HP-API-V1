package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: Article
 * @Description: TODO
 * @author zf
 * @date 2016年10月12日
 */
public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6753843090628242315L;
	/** ID */
	private Long id;
	/** 标题 */
	private String title;
	/** 副标题 */
	private String subTitle;
	/** 主图 */
	private String mainPicture;
	/** 描述 */
	private String description;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 2发布，1草稿，0表示删除 */
	private Integer status;
	/** 内容外键 */
	private Long contentId;
	/** 内容 */
	private String content;
	/** 发布人 */
	private Integer uid;
	/** 店铺ID */
	private Long shopId;
	/** 浏览数 */
	private Integer viewCount;
	/** 头像 */
	private String portriat;
	/** 昵称 */
	private String nickname;
	/** 点赞的用户名称 */
	private String likeUserNames;
	/** 点赞数量 */
	private Integer likeNum;
	/** 是否点赞过表示 */
	private Integer isLike;
	/** 分享uri */
	private String url;
	/** 类型1大师文章，大师经验，3钓场动态，4放鱼信息，5鱼获 */
	private Integer type;

	
	/** 钓场头像 */
	private String mainImg;
	/** 钓场所在省名称 */
	private String fishSiteProvinceName;
	/** 钓场所在市名称 */
	private String fishSiteCityName;
	/** 钓场所在区名称 */
	private String fishSiteRegionName;
	/** 钓场所在具体地址 */
	private String fishSiteAddress;
	/** 钓场名称 */
	private String fishSiteName;
	/** 经度 */
	private Double lng;
	/** 纬度 */
	private Double lat;
	/** 是否关注 */
	private int focus;
	/** 距离 */
	private Integer duration;
	/**位置*/
	private String location;
	/**新增钓点*/
	private String site;
	/** 评论 */
	private List<Comment> coms;

	/** 是否已读？0未读，1已读 */
	private boolean isRead;
	/** 区域id*/
	private Integer regionId;
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public String getFishSiteProvinceName() {
		return fishSiteProvinceName;
	}

	public void setFishSiteProvinceName(String fishSiteProvinceName) {
		this.fishSiteProvinceName = fishSiteProvinceName;
	}

	public String getFishSiteCityName() {
		return fishSiteCityName;
	}

	public void setFishSiteCityName(String fishSiteCityName) {
		this.fishSiteCityName = fishSiteCityName;
	}

	public String getFishSiteRegionName() {
		return fishSiteRegionName;
	}

	public void setFishSiteRegionName(String fishSiteRegionName) {
		this.fishSiteRegionName = fishSiteRegionName;
	}

	public String getFishSiteAddress() {
		return fishSiteAddress;
	}

	public void setFishSiteAddress(String fishSiteAddress) {
		this.fishSiteAddress = fishSiteAddress;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Double getLng() {
		return lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public List<Comment> getComs() {
		return coms;
	}

	public void setComs(List<Comment> coms) {
		this.coms = coms;
	}

	public String getFishSiteName() {
		return fishSiteName;
	}

	public int getFocus() {
		return focus;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setFishSiteName(String fishSiteName) {
		this.fishSiteName = fishSiteName;
	}

	public void setFocus(int focus) {
		this.focus = focus;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getMainPicture() {
		return mainPicture;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public Long getContentId() {
		return contentId;
	}

	public String getContent() {
		return content;
	}

	public Integer getUid() {
		return uid;
	}

	public Long getShopId() {
		return shopId;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public String getPortriat() {
		return portriat;
	}

	public String getNickname() {
		return nickname;
	}

	public String getLikeUserNames() {
		return likeUserNames;
	}

	public Integer getIsLike() {
		return isLike;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public void setMainPicture(String mainPicture) {
		this.mainPicture = mainPicture;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public void setPortriat(String portriat) {
		this.portriat = portriat;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setLikeUserNames(String likeUserNames) {
		this.likeUserNames = likeUserNames;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
}
