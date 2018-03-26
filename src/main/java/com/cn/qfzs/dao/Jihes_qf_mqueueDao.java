package com.cn.qfzs.dao;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Jihes_qf_aqueue;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;

public interface Jihes_qf_mqueueDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_mqueue record);

    int insertSelective(Jihes_qf_mqueue record);

    Jihes_qf_mqueue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_mqueue record);

    int updateByPrimaryKey(Jihes_qf_mqueue record);
    
    /**
     * 查询当前未发送的自动发送队列
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueueAuto(Map<String,Object> map);
    
    /**
     * 查询当前小时手动添加的商品
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueueByExpireTime(Map<String,Object> map);
    
    
    /**
     * 查询当前小时手动添加的商品
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueueByExpireTimes(Map<String,Object> map);
    
    
    /**
     * 批量修改M队列
     * @param mQueues
     * @return
     */
    int updateMqueues(Map<String,Object> map);
    
    /**
     * 批量插入
     * @return
     */
    int insertMqueues(List<Jihes_qf_mqueue> mQueues);
    
    /**
     * 获取有效的手动添加队列
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getValueMqueue(Map<String,Object> map);
    
    /**
     * 获取当日及次日Mqueue实体，根据itemid
     * @param map
     * @return
     */
    Jihes_qf_mqueue  getMqueueInfo(Map<String,Object> map);
    
    /**
     * 获取当日及次日Aqueue实体，根据itemid
     * @param map
     * @return
     */
    List<String> getDeleteAqueue(Map<String,Object> map);
    
    
    /**
     * 	根据item_id批量获取MQ
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueues(Map<String,Object> map);
    
    /**
     * 获取itemid的有效数量
     * @param map
     * @return
     */
    Integer getValueMqueue2(Map<String,Object> map);
    
   
    
}