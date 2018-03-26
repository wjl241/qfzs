package com.cn.qfzs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.qfzs.dao.IUserDao;
import com.cn.qfzs.pojo.User;
import com.cn.qfzs.service.IUserService;

import util.datasources.DataSource;
import util.datasources.DataSourceContextHolder;
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Override
	@DataSource(DataSourceContextHolder.DATA_SOURCE_B)
	public User getUserById(int userId) {
		return userDao.selectByPrimaryKey(userId);
	}


}
