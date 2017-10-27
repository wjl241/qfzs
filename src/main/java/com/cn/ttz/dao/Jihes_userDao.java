package com.cn.ttz.dao;

import com.cn.ttz.pojo.Jihes_user;

public interface Jihes_userDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_user record);

    int insertSelective(Jihes_user record);

    Jihes_user selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_user record);

    int updateByPrimaryKey(Jihes_user record);
    
    /**
     * 查询NPC信息，phone以110开头的user
     * @param count
     * @return
     */
    Jihes_user selectNPCInfo(Integer count);
}