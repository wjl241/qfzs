package com.cn.qfzs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Agent_userDao;
import com.cn.qfzs.dao.Jihes_buy_software_logDao;
import com.cn.qfzs.dao.Jihes_qf_mqueueDao;
import com.cn.qfzs.dao.Jihes_qf_useDao;
import com.cn.qfzs.dao.Jihes_userDao;
import com.cn.qfzs.pojo.Agent_user;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;
import com.cn.qfzs.service.Jihes_userService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("Jihes_userService")
public class Jihes_userServiceImpl implements Jihes_userService{
	@Resource
	private Jihes_userDao jihes_userDao;
	@Resource
	private Jihes_qf_mqueueDao jihes_qf_mqueueDao;
	@Resource
	private Agent_userDao agent_userDao;
	@Resource
	private Jihes_buy_software_logDao jihes_buy_software_logDao;
	@Resource
	private Jihes_qf_useDao jihes_qf_useDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_user selectByPrimaryKey(int id) {
		return jihes_userDao.selectByPrimaryKey(id);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_qf_mqueue> getMqueueAuto(Map<String, Object> map) {
		return jihes_qf_mqueueDao.getMqueueAuto(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int insertMqueue(Jihes_qf_mqueue queue) {
		return jihes_qf_mqueueDao.insert(queue);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Agent_user selectAgentUserById(int id) {
		return agent_userDao.selectByPrimaryKey(id);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public int getUserBuyInfo(Map<String, Object> map) {
		return jihes_buy_software_logDao.getUserBuyInfo(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_qf_use getUseInfoByPhone(String phone) {
		return jihes_qf_useDao.getUseInfoByPhone(phone);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Agent_user getAgentUserByPhone(String phone) {
		return agent_userDao.getAgentUserByPhone(phone);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Agent_user getAgentById(Integer id) {
		return agent_userDao.getAgentById(id);
	}

	

	

}
