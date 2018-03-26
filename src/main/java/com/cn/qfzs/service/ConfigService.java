package com.cn.qfzs.service;

import java.util.List;
import java.util.Map;

import com.cn.qfzs.pojo.Jihes_config;

public interface ConfigService {
	/**
	 * 获取配置参数， name, titile,key
	 * @param name
	 * @param title
	 * @param key
	 * @return
	 */
	String selectConfig(String name,String title,String key);
	
	
	boolean selectConfig2(String name,String title,String key);
}
