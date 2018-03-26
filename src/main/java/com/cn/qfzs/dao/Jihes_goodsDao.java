package com.cn.qfzs.dao;

import com.cn.qfzs.pojo.Jihes_goods;

public interface Jihes_goodsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_goods record);

    int insertSelective(Jihes_goods record);

    Jihes_goods selectByPrimaryKey(String itemId);

    int updateByPrimaryKeySelective(Jihes_goods record);

    int updateByPrimaryKey(Jihes_goods record);
}