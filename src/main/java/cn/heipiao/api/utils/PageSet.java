package cn.heipiao.api.utils;

import java.util.List;

/**
 * @author Chris
 *
 * TODO 考虑本类的实现方法是否有必要
 * @param <T>
 */
public class PageSet<T> {
	
	private int pagenum;
	private int pagesize;
	private int totalitem;
	private int totalpage;
	private Object obj;

	private List<T> items;
	
	public PageSet(int pagenum, int pagesize, int totalitem, List<T> items) {
		if (pagenum < 1) {
			throw new IllegalArgumentException("pagenum cannot less than 1");
		}
		if (pagesize < 1) {
			throw new IllegalArgumentException("pagesize cannot less than 1");
		}
		this.pagenum = pagenum;
		this.pagesize = pagesize;
		this.totalitem = totalitem;
		this.totalpage = totalitem == 0 ? 0 : (totalitem / pagesize) + (totalitem % pagesize > 0 ? 1 : 0);
		this.items = items;
		
	}

	public int getPageNum() {
		return pagenum;
	}

	public int getPageSize() {
		return pagesize;
	}

	public int getTotalPage() {
		return totalpage;
	}
	
	public int getTotalItem() {
		return totalitem;
	}

	public List<T> getItems() {
		return items;
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}