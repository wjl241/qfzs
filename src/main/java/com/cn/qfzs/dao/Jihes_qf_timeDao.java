package com.cn.qfzs.dao;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Jihes_qf_time;

public interface Jihes_qf_timeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_time record);

    int insertSelective(Jihes_qf_time record);

    Jihes_qf_time selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_time record);

    int updateByPrimaryKey(Jihes_qf_time record);
    
    /**
     * 查询当前用户所有无效的发送时间段
     * @param user_id
     * @return
     */
    List<Jihes_qf_time> getNoUseTime(int user_id);
    
    /**
     * 查询当前用户所有无效的发送时间，整段小时
     * @param user_id
     * @return
     */
    List<String> getNoUseTimes(int user_id);
    
    
    /**
     * 查询当前用户所有无效的发送时间段
     * @param user_id
     * @param time 6
     * @return
     */
    List<Jihes_qf_time> getNoUseTime2(Map<String,Object> map);
    
    /**
     * 查询当前用户所有无效的发送时间，整段小时
     * @param user_id
     * @return
     */
    List<Jihes_qf_time> getNoUseTimes3(int user_id);
    
    /**
     * 获取某个用户某个时间段tiem实体
     * @param map
     * @return
     */
    Jihes_qf_time getDeleteTime(Map<String,Object> map);
    
    /**
     * 获取某个用户某个时间段tiem实体
     * @param map
     * @return
     */
    List<Jihes_qf_time> getDeleteTimes(Map<String,Object> map);
    
    /**
     * 批量修改
     * @param times
     * @return
     */
    int updateTimes(List<Jihes_qf_time> times);
    /**
     * 批量插入
     * @param times
     * @return
     */
    int insertTimes(List<Jihes_qf_time> times);
    
    
    /**
     * 获取某个用户所有的时间
     * @param map
     * @return
     */
    List<Jihes_qf_time> getAllTimes(Map<String,Object> map);
    
    /**
     * 获取每个时间段的无效时间
     * @param map
     * @return
     */
    List<Jihes_qf_time> getNoUseTimes4(Integer user_id);
}