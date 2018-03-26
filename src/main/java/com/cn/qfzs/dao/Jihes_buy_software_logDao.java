package com.cn.qfzs.dao;

import java.util.Map;

import com.cn.qfzs.pojo.Jihes_buy_software_log;

public interface Jihes_buy_software_logDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_buy_software_log record);

    int insertSelective(Jihes_buy_software_log record);

    Jihes_buy_software_log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_buy_software_log record);

    int updateByPrimaryKey(Jihes_buy_software_log record);
    
    /**
     * 根据手机号查询当前用户是否购买过软件
     * @param map
     * @return
     */
    int getUserBuyInfo(Map<String,Object> map);
}