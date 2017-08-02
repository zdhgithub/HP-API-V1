/**
 * 
 */
package cn.heipiao.api.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.heipiao.api.mapper.AccountBillMapper;
import cn.heipiao.api.pojo.AccountBill;
import cn.heipiao.api.pojo.AccountExt;
import cn.heipiao.api.pojo.AccountExtSite;
import cn.heipiao.api.pojo.AccountRecord;
import cn.heipiao.api.service.AccountBillService;
import cn.heipiao.api.utils.ExDateUtils;

/**
 * @author wzw
 * @date 2016年10月17日
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AccountBillServiceImpl implements AccountBillService {

	@Resource
	private AccountBillMapper accountBillMapper;
	
	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountBillService#getByUid(java.lang.Long)
	 */
	@Override
	public List<AccountBill> getByUid(Map<String,Object> map) {
		return accountBillMapper.selectByUid(map);
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountBillService#getByOrderId(java.lang.String)
	 */
	@Override
	public List<AccountBill> getByOrderId(String orderId) {
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.heipiao.api.service.AccountBillService#addPojo(cn.heipiao.api.pojo.AccountBill)
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = {Exception.class})
	public void addPojo(Long uid,String orderId,Integer inOut,Integer type,Integer subType,Double tradeFee,String desc) {
		AccountBill pojo = new AccountBill();
		pojo.setCreateTime(new Timestamp(ExDateUtils.getCalendar().getTimeInMillis()));
		pojo.setDesc(desc);
		pojo.setInOut(inOut);
		pojo.setOrderId(orderId);
		pojo.setType(type);
		pojo.setSubType(subType);
		pojo.setTradeFee(tradeFee);
		pojo.setUid(uid);
		accountBillMapper.insertPojo(pojo);
	}
	
	@Override
	public List<AccountBill> getRecordBySite(Map<String, Object> map) {
		return accountBillMapper.selectRecordBySite(map);
	}

	@Override
	public List<AccountExtSite> selectListExt(Map<String, Object> map) {
		List<AccountExtSite> list = accountBillMapper.selectListExt(map);
		return list;
	}
	/**cp 用户财务列表*/
	@Override
	public List<AccountExt> selectList(Map<String, Object> param) {
		List<AccountExt> list = accountBillMapper.selectList(param);
		return list;
	}
	/** 用户财务交易记录 */
	@Override
	public List<AccountBill> selectRecordByUid(Map<String, Object> map) {
		List<AccountBill> list = accountBillMapper.selectRecordByUid(map);
		return list;
	}
	
}
