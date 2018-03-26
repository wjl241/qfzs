package com.cn.qfzs.dao;

import com.cn.qfzs.pojo.Jihes_qf_share;

public interface Jihes_qf_shareDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_share record);

    int insertSelective(Jihes_qf_share record);

    Jihes_qf_share selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_share record);

    int updateByPrimaryKey(Jihes_qf_share record);
}