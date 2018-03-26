package com.cn.qfzs.dao;

import java.util.Map;

import com.cn.qfzs.pojo.Jihes_qf_use;

public interface Jihes_qf_useDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_use record);

    int insertSelective(Jihes_qf_use record);

    Jihes_qf_use selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_use record);

    int updateByPrimaryKey(Jihes_qf_use record);
    
    /**
     * 查询当前用户是否开启群发工具
     * user_id
     * status 0关闭， 1 开启
     *	//null 或 0 为关闭
     * @param map
     * @return
     */
    Jihes_qf_use selectUseStatus(Map<String,Object> map);
    
    /**
     * 获取用户群发工具的开启/关闭信息
     * @param user_id
     * @return
     */
    Jihes_qf_use getUseInfo(Integer user_id);
    
    
    Jihes_qf_use getUseInfoByPhone(String phone);

    
    
}