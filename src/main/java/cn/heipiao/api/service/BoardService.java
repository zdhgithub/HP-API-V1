package cn.heipiao.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.pojo.Page;
import cn.heipiao.api.pojo.Pole;

/**
 * 说明 : 看板业务 
 * 功能 : a.查看某钓厂的在线用户列表
 * 		b.判断用户是否离开钓场
 * @author chenwenye
 * @since 2016-7-4  heipiao1.0
 */
public interface BoardService {
	
	/** 存在 **/
	String EXIST = "1";
	
	/** 不存在  **/
	String NOT_EXIST = "0";
	
	/** 普通开杆 **/
	String NORMAL_BEGIN_TYPE = "0";
	
	/** 三十分钟后开杆 **/
	String THIRTY_MIN_AFTER_BEGIN_TYPE = "1";
	
	public Pole getById(Long id);
	
	/**
	 * 作用: 查看某一用户是否存在某钓场
	 * @param id 票券id
	 * @return 
	 */
	boolean isExist(String id);
	
	/**
	 * 作用: 开杆
	 * @param id 票券id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	boolean beginPole(String id, String beginType);
	
	/**
	 * 作用: 用户离开钓场
	 * @param id 票券id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	boolean leave(String id);
	
	/**
	 * 作用: 查询 某段时间内的销售客户量
	 * @param begin
	 * @param end
	 * @param fishSitsId 钓场id
	 * @param isLeave 状态
	 * @param page 分页对象
	 * @return
	 * @throws Exception 
	 */
	List<Pole> findPeople(Date begin,Date end,String fishSitsId, String isLeave, Page page) throws Exception;
	
	/**
	 * 作用: update
	 * @param pole bean
	 * @return
	 */
	@Transactional
	boolean update(Pole pole);
	
	/**
	 * 作用: 添加时间
	 * @param id
	 * @param time
	 * @return
	 */
	@Transactional
	boolean addTime(String id , Date time);
	
	/**
	 * 查询看板头信息
	 * @return
	 */
	Pole findHead(String finshSiteId);
	
}
