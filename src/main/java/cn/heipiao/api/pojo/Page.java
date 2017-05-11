package cn.heipiao.api.pojo;

/**
 * 说明 : 分页类 功能 :
 * 
 * @author chenwenye
 * @since 2016-7-5 heipiao1.0
 */
public class Page {

	private Integer start;
	private Integer size;

	public Page() {
	}

	/**
	 * 默认构造器
	 */
	public Page(Integer start, Integer size) {
		this.size = size;
		if( start != null )
		this.start = start;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getSize() {
		return size;
	}
}