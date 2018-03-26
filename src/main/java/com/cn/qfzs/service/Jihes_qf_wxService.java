package com.cn.qfzs.service;

import java.util.List;

import com.cn.qfzs.pojo.Jihes_qf_wx;

public interface Jihes_qf_wxService {
	 /**
     * 批量插入wx号
     * @param wxs
     * @return
     */
    int insertWxs(List<Jihes_qf_wx> wxs);
    
    
    int insert(Jihes_qf_wx record);
}
