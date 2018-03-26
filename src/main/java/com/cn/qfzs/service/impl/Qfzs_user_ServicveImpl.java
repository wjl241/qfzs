package com.cn.qfzs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Jihes_goodsDao;
import com.cn.qfzs.dao.Jihes_qf_aqueueDao;
import com.cn.qfzs.dao.Jihes_qf_heartbeatDao;
import com.cn.qfzs.dao.Jihes_qf_mqueueDao;
import com.cn.qfzs.dao.Jihes_qf_timeDao;
import com.cn.qfzs.dao.Jihes_qf_useDao;
import com.cn.qfzs.dao.Jihes_userDao;
import com.cn.qfzs.pojo.Jihes_goods;
import com.cn.qfzs.pojo.Jihes_qf_aqueue;
import com.cn.qfzs.pojo.Jihes_qf_heartbeat;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;
import com.cn.qfzs.pojo.Jihes_qf_time;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;
import com.cn.qfzs.service.Qfzs_user_Servicve;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("Qfzs_user_Servicve")
public class Qfzs_user_ServicveImpl implements Qfzs_user_Servicve{
	@Resource
	private Jihes_userDao jihes_userDao;
	@Resource
	private Jihes_goodsDao jihes_goodsDao;
	@Resource
	private Jihes_qf_timeDao jihes_qf_timeDao;
	@Resource
	private Jihes_qf_mqueueDao jihes_qf_mqueueDao;
	@Resource
	private Jihes_qf_useDao jihes_qf_useDao;
	@Resource
	private Jihes_qf_aqueueDao jihes_qf_aqueueDao;
	@Resource
	private Jihes_qf_heartbeatDao jihes_qf_heartbeatDao;
	
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_user selectUserByPhone(String phone) {
		return jihes_userDao.selectUserByPhone(phone);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_goods selectGoodById(String itemId) {
		return jihes_goodsDao.selectByPrimaryKey(itemId);
	}

	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getNoUseTime(int user_id) {
		return jihes_qf_timeDao.getNoUseTime(user_id);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateMqueues(Map<String,Object> map) {
		return jihes_qf_mqueueDao.updateMqueues(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<String> getNoUseTimes(int user_id) {
		return jihes_qf_timeDao.getNoUseTimes(user_id);
	}
	
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_use selectUseStatus(Map<String, Object> map) {
		return jihes_qf_useDao.selectUseStatus(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_aqueue> getValueAqueue(Map<String, Object> map) {
		return jihes_qf_aqueueDao.getValueAqueue(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_mqueue> getValueMqueue(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getValueMqueue(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getNoUseTime2(Map<String, Object> map) {
		return jihes_qf_timeDao.getNoUseTime2(map);
	}
	
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getNoUseTimes3(int user_id) {
		return jihes_qf_timeDao.getNoUseTimes3(user_id);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_time getDeleteTime(Map<String, Object> map) {
		return jihes_qf_timeDao.getDeleteTime(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateDeleteTime(Jihes_qf_time time,boolean insert) {
		if(insert) {
			return jihes_qf_timeDao.insert(time);
		}else{
			return jihes_qf_timeDao.updateByPrimaryKey(time);
		}
		
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateDeleteMqueue(Jihes_qf_mqueue jihes_qf_mqueue,boolean insert) {
		if(insert) {
			return jihes_qf_mqueueDao.insert(jihes_qf_mqueue);
		}else {
			return jihes_qf_mqueueDao.updateByPrimaryKey(jihes_qf_mqueue);
		}
		
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<String> getDeleteAqueue(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getDeleteAqueue(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_mqueue getMqueueInfo(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getMqueueInfo(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getDeleteTimes(Map<String, Object> map) {
		return jihes_qf_timeDao.getDeleteTimes(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateTimes(List<Jihes_qf_time> times) {
		return jihes_qf_timeDao.updateTimes(times);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertTimes(List<Jihes_qf_time> times) {
		return jihes_qf_timeDao.insertTimes(times);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getAllTimes(Map<String, Object> map) {
		return jihes_qf_timeDao.getAllTimes(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_mqueue> getMqueues(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getMqueues(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_aqueue> getAqueues(Map<String, Object> map) {
		return jihes_qf_aqueueDao.getAqueues(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertMqueues(List<Jihes_qf_mqueue> mQueues) {
		return jihes_qf_mqueueDao.insertMqueues(mQueues);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_use getUseInfo(Integer user_id) {
		return jihes_qf_useDao.getUseInfo(user_id);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertUse(Jihes_qf_use record) {
		return jihes_qf_useDao.insert(record);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateUse(Jihes_qf_use record) {
		return jihes_qf_useDao.updateByPrimaryKey(record);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_mqueue> getMqueueByExpireTime(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getMqueueByExpireTime(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_time> getNoUseTimes4(Integer user_id) {
		return jihes_qf_timeDao.getNoUseTimes4(user_id);
	}
	
	

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_mqueue> getMqueueByExpireTimes(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getMqueueByExpireTimes(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_aqueue getAqueueInfo(Map<String, Object> map) {
		return jihes_qf_aqueueDao.getAqueueInfo(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Integer getValueMqueue2(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getValueMqueue2(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Integer getValueAqueue2(Map<String, Object> map) {
		return jihes_qf_aqueueDao.getValueAqueue2(map);
	}

	@Override
	public Jihes_qf_heartbeat getHeartBeat(String phone) {
		return jihes_qf_heartbeatDao.getHeartBeat(phone);
	}

	@Override
	public int insertHerarBeat(Jihes_qf_heartbeat heart, boolean insert) {
		if(insert) {
			return jihes_qf_heartbeatDao.insert(heart);
		}else {
			return jihes_qf_heartbeatDao.updateByPrimaryKey(heart);
		}
		
	}


}
