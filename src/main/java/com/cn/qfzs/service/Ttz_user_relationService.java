package com.cn.qfzs.service;

import com.cn.qfzs.pojo.Ttz_user_relation;

public interface Ttz_user_relationService {
	/**
	 * 绑定上级用户
	 * @param ttz_user_relation
	 * @return
	 */
	public int insertSelective(Ttz_user_relation ttz_user_relation);
}
