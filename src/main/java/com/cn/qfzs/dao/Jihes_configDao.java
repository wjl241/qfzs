package com.cn.qfzs.dao;

import java.util.Map;

import com.cn.qfzs.pojo.Jihes_config;

public interface Jihes_configDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_config record);

    int insertSelective(Jihes_config record);

    Jihes_config selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_config record);

    int updateByPrimaryKey(Jihes_config record);
    
    String selectConfig(Map<String,Object> map);
}