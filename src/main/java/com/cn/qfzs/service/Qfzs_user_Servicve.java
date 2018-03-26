package com.cn.qfzs.service;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Jihes_goods;
import com.cn.qfzs.pojo.Jihes_qf_aqueue;
import com.cn.qfzs.pojo.Jihes_qf_heartbeat;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;
import com.cn.qfzs.pojo.Jihes_qf_time;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;

public interface Qfzs_user_Servicve {
	 /**
     * 根据手机号查询集合特卖是否有此用户
     * @param phone
     * @return
     */
    Jihes_user selectUserByPhone(String phone);
    
    /**
     * 根据good_id查询商品信息
     * @param id
     * @return
     */
    Jihes_goods selectGoodById(String id);
    
    /**
     * 获取当前用户关闭时间段
     * @param user_id
     * @return
     */
    List<Jihes_qf_time> getNoUseTime(int user_id);
    
    
    /**
     * 批量修改M队列
     * @param mQueues
     * @return
     */
    int updateMqueues(Map<String,Object> map );
    
    /**
     * 查询当前用户所有无效的发送时间，整段小时
     * @param user_id
     * @return
     */
    List<String> getNoUseTimes(int user_id);
    
    
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
     * 获取有效的自动添加队列
     * @param map
     * @return
     */
    List<Jihes_qf_aqueue> getValueAqueue(Map<String,Object> map);
    
    /**
     * 获取有效的手动添加队列
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getValueMqueue(Map<String,Object> map);
    
    
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
     * 获取某个用户某个时间段time实体
     * @param map
     * @return
     */
    Jihes_qf_time getDeleteTime(Map<String,Object> map);
    
    /**
     * 修改time实体
     * @param time
     * @return
     */
    int updateDeleteTime(Jihes_qf_time time,boolean insert);
    
    /**
     * 修改mqueue实体
     * @param jihes_qf_mqueue
     * @return
     */
    int updateDeleteMqueue(Jihes_qf_mqueue jihes_qf_mqueue,boolean insert);
    
    
    /**
     * 获取当日及次日Aqueue实体，根据itemid
     * @param map
     * @return
     */
    List<String> getDeleteAqueue(Map<String,Object> map);
    
    /**
     * 获取当日及次日Mqueue实体，根据itemid
     * @param map
     * @return
     */
    Jihes_qf_mqueue  getMqueueInfo(Map<String,Object> map);
    
    
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
     * 	根据item_id批量获取MQ
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueues(Map<String,Object> map);
    
    /**
     * 	根据item_id批量获取AQ
     * @param map
     * @return
     */
    List<Jihes_qf_aqueue> getAqueues(Map<String,Object> map);
    
    /**
     * 批量插入Mqueues
     * @return
     */
    int insertMqueues(List<Jihes_qf_mqueue> mQueues);
    
    /**
     * 获取用户群发工具的开启/关闭信息
     * @param user_id
     * @return
     */
    Jihes_qf_use getUseInfo(Integer user_id);
    
    /**
     * 插入Use信息
     * @param record
     * @return
     */
    int insertUse(Jihes_qf_use record);
    
    /**
     * 修改use信息
     */
    int updateUse(Jihes_qf_use record);
    
    /**
     * 查询当前小时手动添加的商品
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueueByExpireTime(Map<String,Object> map);
    
    /**
     * 获取每个时间段的无效时间
     * @param map
     * @return
     */
    List<Jihes_qf_time> getNoUseTimes4(Integer user_id);
    
    
    /**
     * 查询当日所有手动添加的商品
     * 数量  小时
     * @param map
     * @return
     */
    List<Jihes_qf_mqueue> getMqueueByExpireTimes(Map<String,Object> map);
    
    /**
     * 获取当日及次日Aqueue实体，根据itemid
     * @param map
     * @return
     */
    Jihes_qf_aqueue  getAqueueInfo(Map<String,Object> map);
    
    
    
    /**
     * 获取itemid的有效数量
     * @param map
     * @return
     */
    Integer getValueMqueue2(Map<String,Object> map);
    
    
    
    
    /**
     * 获取itemid的有效数量
     * @param map
     * @return
     */
    Integer getValueAqueue2(Map<String,Object> map);
    
    
    /**
     * 根据phone查询心跳状态 
     */
    Jihes_qf_heartbeat getHeartBeat(String phone);
    
    /**
     * 插入，修改心跳
     * @param heart
     * @param insert
     * @return
     */
    int insertHerarBeat(Jihes_qf_heartbeat heart,boolean insert);
}
