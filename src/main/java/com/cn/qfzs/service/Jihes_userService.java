package com.cn.qfzs.service;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Agent_user;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;
import com.cn.qfzs.pojo.Jihes_qf_time;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;

public interface Jihes_userService {
	/**
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	Jihes_user selectByPrimaryKey(int id);
	
	/**
	 * 查询代理信息
	 * @param id
	 * @return
	 */
	Agent_user selectAgentUserById(int id);
	
	
	/**
     * 查询当前未发送的自动发送队列
     * @param map
     * @return
     */
	List<Jihes_qf_mqueue> getMqueueAuto(Map<String,Object> map);
    
    /**
     * 插入queue实体
     * @param queue
     * @return
     */
    int insertMqueue(Jihes_qf_mqueue queue);
    
    
    Jihes_qf_use getUseInfoByPhone(String phone);
    
   
    
    /**
     * 根据手机号查询当前用户是否购买过软件
     * @param map
     * @return
     */
    int getUserBuyInfo(Map<String,Object> map);
    
    /**
     * 根据手机号获取代理信息
     * @param phone
     * @return
     */
    Agent_user getAgentUserByPhone(String phone);
    
    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    Agent_user getAgentById(Integer id);
}
