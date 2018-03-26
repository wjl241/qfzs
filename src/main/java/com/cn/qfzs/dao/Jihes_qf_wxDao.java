package com.cn.qfzs.dao;

import java.util.List;

import com.cn.qfzs.pojo.Jihes_qf_wx;

public interface Jihes_qf_wxDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_wx record);

    int insertSelective(Jihes_qf_wx record);

    Jihes_qf_wx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_wx record);

    int updateByPrimaryKey(Jihes_qf_wx record);
    
    /**
     * 批量插入wx号
     * @param wxs
     * @return
     */
    int insertWxs(List<Jihes_qf_wx> wxs);
}