package com.cn.qfzs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.Jihes_userDao;
import com.cn.qfzs.dao.Ttz_goodsDao;
import com.cn.qfzs.dao.Ttz_ordersDao;
import com.cn.qfzs.dao.Ttz_teamDao;
import com.cn.qfzs.dao.Ttz_tuantuanDao;
import com.cn.qfzs.dao.Ttz_user_relationDao;
import com.cn.qfzs.pojo.Jihes_user;
import com.cn.qfzs.pojo.Ttz_goods;
import com.cn.qfzs.pojo.Ttz_orders;
import com.cn.qfzs.pojo.Ttz_team;
import com.cn.qfzs.pojo.Ttz_tuantuan;
import com.cn.qfzs.pojo.Ttz_user_relation;
import com.cn.qfzs.service.Ttz_OrderService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;

@Service("Ttz_OrderService")
public class Ttz_OrderServiceImpl implements Ttz_OrderService {
	@Resource
	private Ttz_ordersDao ttz_ordersDao;
	@Resource
	private Ttz_goodsDao ttz_goodsDao;
	@Resource
	private Ttz_tuantuanDao ttz_tuantuanDao;
	@Resource
	private Ttz_user_relationDao ttz_user_relationDao;
	@Resource
	private Jihes_userDao jihes_userDao;
	@Resource
	private Ttz_teamDao ttz_teamDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateOrders(Map<String,Object> map) {
		return ttz_ordersDao.updateOrders(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_orders> selectGoodsNum(Map<String, Object> map) {
		return ttz_ordersDao.selectGoodsNum(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_goods> selectGoodPerson(Map<String, Object> map) {
		return ttz_goodsDao.selectGoodPerson(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateTuanStatus(Map<String,Object> map) {
		return ttz_tuantuanDao.updateTuanStatus(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_tuantuan> selectByUser_id(Map<String, Object> map) {
		return ttz_tuantuanDao.selectByUser_id(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateByPrimaryKeySelective(Ttz_tuantuan record) {
		return ttz_tuantuanDao.updateByPrimaryKey(record);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public int updateByPrimaryKeySelective(Ttz_goods record) {
		return ttz_goodsDao.updateByPrimaryKeySelective(record);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_tuantuan> selectByGoodId(Map<String, Object> map) {
		return ttz_tuantuanDao.selectByGoodId(map);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_orders> getOrdersByGoodsId(Integer ttz_goods_id) {
		return ttz_ordersDao.getOrdersByGoodsId(ttz_goods_id);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_user_relation> selectRelations(Map<String, Object> map) {
		return ttz_user_relationDao.selectRelations(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_orders> getValuesOrders(Map<String, Object> map) {
		return ttz_ordersDao.getValuesOrders(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Jihes_user selectNPCInfo(Integer count) {
		return jihes_userDao.selectNPCInfo(count);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public List<Ttz_orders> getNPCOrderNumber(Map<String, Object> map) {
		return ttz_ordersDao.getNPCOrderNumber(map);
	}

	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_A)
	public Integer insertNPC(List<Ttz_orders> list) {
		return ttz_ordersDao.insertNPC(list);
	}
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public Ttz_team getFaildOrderTeam(Map<String, Object> map) {
		return ttz_teamDao.getFaildOrderTeam(map);
	}
}
