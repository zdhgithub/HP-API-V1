package cn.heipiao.api.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ArticleUserBehavior implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 文章用户行为唯一标识
	 */
	private Long id;
	
	/**
	 * 文章唯一标识
	 */
	private Long articleId;
	
	/**
	 * 行为类型 1-立即打开点击
	 */
	private int type;
	
	/**
	 * 对应行为类型的点击次数
	 */
	private Long count;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
