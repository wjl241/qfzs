package com.cn.qfzs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Jihes_qf_wxDao;
import com.cn.qfzs.pojo.Jihes_qf_wx;
import com.cn.qfzs.service.Jihes_qf_wxService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("Jihes_qf_wxService")
public class Jihes_qf_wxServiceImpl implements Jihes_qf_wxService{
	@Resource
	private Jihes_qf_wxDao jihes_qf_wxDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public int insertWxs(List<Jihes_qf_wx> wxs) {
		return jihes_qf_wxDao.insertWxs(wxs);
	}

	@Override
	public int insert(Jihes_qf_wx record) {
		return jihes_qf_wxDao.insert(record);
	}

}
