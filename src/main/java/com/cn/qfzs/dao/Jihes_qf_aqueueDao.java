package com.cn.qfzs.dao;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Jihes_qf_aqueue;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;

public interface Jihes_qf_aqueueDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Jihes_qf_aqueue record);

    int insertSelective(Jihes_qf_aqueue record);

    Jihes_qf_aqueue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jihes_qf_aqueue record);

    int updateByPrimaryKey(Jihes_qf_aqueue record);
    
    /**
     * 获取有效的自动添加队列
     * @param map
     * @return
     */
    List<Jihes_qf_aqueue> getValueAqueue(Map<String,Object> map);
    
    
    /**
     * 获取当日及次日Aqueue实体，根据itemid
     * @param map
     * @return
     */
    Jihes_qf_aqueue  getAqueueInfo(Map<String,Object> map);
    
    
    /**
     * 	根据item_id批量获取AQ
     * @param map
     * @return
     */
    List<Jihes_qf_aqueue> getAqueues(Map<String,Object> map);
    
    /**
     * 获取itemid的有效数量
     * @param map
     * @return
     */
    Integer getValueAqueue2(Map<String,Object> map);
}