package com.cn.qfzs.dao;

import com.cn.qfzs.pojo.Agent_user;

public interface Agent_userDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Agent_user record);

    int insertSelective(Agent_user record);

    Agent_user selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agent_user record);

    int updateByPrimaryKey(Agent_user record);
    
    /**
     * 根据手机号获取代理信息
     * @param phone
     * @return
     */
    Agent_user getAgentUserByPhone(String phone);
    
    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    Agent_user getAgentById(Integer id);
}