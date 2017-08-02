package cn.heipiao.api.pojo;

import java.sql.Timestamp;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wzw
 * @date 2017年3月21日
 */
public class ActivityArticle {

	/**
	 * 自增主键
	 */
	@ApiModelProperty("文章id")
	private Long id;

	/**
	 * 活动id
	 */
	@ApiModelProperty("活动id")
	private Integer cid;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 文章内容
	 */
	@ApiModelProperty("文章内容")
	private String content;

	/**
	 * 回顾类型1:人物,2:图片,3:技巧
	 */
	@ApiModelProperty(value = "回顾类型1:人物,2:图片,3:技巧")
	private Integer type;

	/**
	 * 封面
	 */
	@ApiModelProperty("封面")
	private String banner;

	/**
	 * 序号
	 */
	@ApiModelProperty("序号")
	private Integer sort;

	/**
	 * 发布时间
	 */
	@ApiModelProperty("发布时间")
	private Timestamp createTime;
	
	/**
	 * 是否有视频 0:没有,1:有
	 */
	private Integer isVideo;

	public Integer getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Integer isVideo) {
		this.isVideo = isVideo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
