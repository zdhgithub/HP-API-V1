package cn.heipiao.api.mapper;

import cn.heipiao.api.constant.MyBatisConstant;

/**
 * 
 * 说明 :
 * 		分页对象类
 * 功能 : 
 *      a.	
 * 使用规范 :
 * 		a.
 * @author chenwenye
 * @since 2016-6-3 heipiao1.0
 */
public class Paginator {
	
	private int start;
	
	private int size;
	
	public Paginator(){
		this.start = MyBatisConstant.DEFAULT_START;
		this.size = MyBatisConstant.DEFALUT_SIZE;
	}
	
	public Paginator(int start, int size){
		this.start = start;
		this.size = size;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
