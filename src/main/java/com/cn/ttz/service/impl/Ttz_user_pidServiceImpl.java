package com.cn.ttz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.ttz.dao.Ttz_user_pidDao;
import com.cn.ttz.pojo.Ttz_user_pid;
import com.cn.ttz.service.Ttz_user_pidService;
@Service("Ttz_user_pidService")
public class Ttz_user_pidServiceImpl implements Ttz_user_pidService{

	@Resource
	private Ttz_user_pidDao ttz_user_pidDao;
	
	public int insertSelective(Ttz_user_pid ttz_user_pid) {
		return ttz_user_pidDao.insertSelective(ttz_user_pid);
	}

	public int selectByUserid(Integer user_id) {
		return ttz_user_pidDao.selectByUserid(user_id);
	}

	public  List<Ttz_user_pid> selectUseridByPid(Map<String, Object> map) {
		
		return ttz_user_pidDao.selectUseridByPid(map);
	}

}
