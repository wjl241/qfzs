package com.cn.qfzs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Ttz_bill_ordersDao;
import com.cn.qfzs.dao.Ttz_goodsDao;
import com.cn.qfzs.dao.Ttz_ordersDao;
import com.cn.qfzs.dao.Ttz_tuantuanDao;
import com.cn.qfzs.pojo.Jihes_goods;
import com.cn.qfzs.pojo.Ttz_goods;
import com.cn.qfzs.service.Ttz_goodsService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("Ttz_GoodsService")
public class Ttz_goodsServiceImpl implements Ttz_goodsService{
	@Resource
	private Ttz_goodsDao ttz_goodsDao;
	@Resource
	private Ttz_tuantuanDao ttz_tuantuanDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Jihes_goods> selectPage(Map<String, Integer> map) {
		return ttz_goodsDao.selectPage(map);
	}
	@Resource
	private Ttz_bill_ordersDao ttz_bill_ordersDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public int selectByGoodId(Map<String,Object> map) {
		return ttz_bill_ordersDao.selectByGoodId(map);
	}
	@Resource
	private Ttz_ordersDao ttz_ordersDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public int selectTuanNum(Map<String, Object> map) {
		return ttz_ordersDao.selectTuanNum(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_goods> selectTtzGoodsId(Map<String, Object> map) {
		return ttz_goodsDao.selectTtzGoodsId(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateExpireTuanStatus(Map<String, Object> map) {
		return ttz_tuantuanDao.updateExpireTuanStatus(map);
	}

}
