package com.cn.qfzs.dao;

import com.cn.qfzs.pojo.Jihes_qf_heartbeat;

public interface Jihes_qf_heartbeatDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_heartbeat record);

    int insertSelective(Jihes_qf_heartbeat record);

    Jihes_qf_heartbeat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_heartbeat record);

    int updateByPrimaryKey(Jihes_qf_heartbeat record);
    
    
    
    /**
     * 根据phone查询心跳状态 
     */
    Jihes_qf_heartbeat getHeartBeat(String phone);
}