package com.cn.qfzs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Ttz_xgcsDao;
import com.cn.qfzs.pojo.Ttz_xgcs;
import com.cn.qfzs.service.Ttz_xgcsService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("ttz_xgcsService")
public class Ttz_xgcsServiceImpl implements Ttz_xgcsService{
	@Resource
	private Ttz_xgcsDao ttz_xgcsDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Ttz_xgcs selectByName(String name) {
		return ttz_xgcsDao.selectByName(name);
	}
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Ttz_xgcs selectByName2(String name) {
		return ttz_xgcsDao.selectByName2(name);
	}
	
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public Integer updateTtz(Ttz_xgcs xgcs) {
		return ttz_xgcsDao.updateByPrimaryKeySelective(xgcs);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public Integer InsertTtz(Ttz_xgcs xgcs) {
		return ttz_xgcsDao.insertSelective(xgcs);
	}

}
