package com.cn.qfzs.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cn.qfzs.pojo.Agent_user;
import com.cn.qfzs.pojo.Jihes_qf_heartbeat;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;
import com.cn.qfzs.service.Jihes_userService;
import com.cn.qfzs.service.Qfzs_user_Servicve;

@Controller
@RequestMapping("/user")
public class Qfzs_userController {
	Logger logger = LoggerFactory.getLogger(TtzBillOderController.class);  
	@Resource
	private Qfzs_user_Servicve qfzs_user_Servicve;
	@Resource
	private Jihes_userService jihes_userService;
	
	
	@RequestMapping("/showTp")
	public void showTp(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("phone") == null? "" : request.getParameter("phone");
		Jihes_user user = qfzs_user_Servicve.selectUserByPhone(phone);
		String meesage="";
		if(user == null ) {
			meesage = "";
		}else {
			meesage =user.getNickName();
		}
		responseWriteInfo(200, meesage, null, response);
	}
	
	@RequestMapping("/validUser")
	public void validUser(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("phone") == null? "" : request.getParameter("phone");
		
		JSONObject data = new JSONObject();
		String message="";
		Agent_user agent_user = jihes_userService.getAgentUserByPhone(phone);
		if(agent_user ==null ) {
			data.put("login", "false");
			message = "未查询到当前用户信息";
			responseWriteInfo(600, message, data, response);
			return;
		}
		if( agent_user.getTmUserId()<0 ) {
			data.put("login", "false");
			message = "当前用户user_id异常";
			responseWriteInfo(600, message, data, response);
			return;
		}
		int user_id  = agent_user.getTmUserId();
//		
//		if(agent_user.getLevel() == 2) {//二级代理,查询一级是否有购买
//			agent_user = jihes_userService.getAgentById(agent_user.getParentUserId());
//			if(agent_user ==null ) {
//				data.put("login", "false");
//				message = "未查询到上级用户的信息";
//				responseWriteInfo(200, message, data, response);
//				return;
//			}
//		}
//		
//		Map<String,Object> map = new HashMap<String,Object>(); 
//		map.put("phone", agent_user.getPhone());
//		map.put("end_time", Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
//		int count = jihes_userService.getUserBuyInfo(map);
//	
//
//		if(count ==0) {
//			data.put("login", "false");
//			message = "未查询到购买记录或已过期";
//		}else {
//			data.put("login", "success");
//			data.put("user_id", user_id+"");
//			message = "购买记录有效";
//		}
		data.put("login", "success");
		data.put("user_id", user_id+"");
		message = "有效的用户";
		responseWriteInfo(200, message, data, response);
	}
	
	
	@RequestMapping("/validUse")
	public void validUse(HttpServletRequest request,HttpServletResponse response){
		String phone = request.getParameter("phone") == null? "" : request.getParameter("phone");
		if(phone.equals("")) {
			responseWriteInfo(600, "参数异常", null, response);
			return;
		}
		Jihes_qf_use use = jihes_userService.getUseInfoByPhone(phone);
		JSONObject data = new JSONObject();
		String message="";
		if(use == null ||  use.getStatus() == (byte) 0) {
			data.put("use", "0");
			message = "未开启群发";
		}else {
			data.put("use", "1");
			message = "群发已开启";
		}
	
		responseWriteInfo(200, message, data, response);
	}
	
	/**
	 * 心跳检测
	 * @param request
	 * @param response
	 */
	@RequestMapping("/heartBeat")
	public void heartBeat(HttpServletRequest request,HttpServletResponse response){
		JSONObject data = new JSONObject();
		String phone = request.getParameter("phone") == null? "" : request.getParameter("phone");
		if(phone.equals("")) {
			responseWriteInfo(600, "参数异常", data, response);
			return;
		}
		Jihes_user user = qfzs_user_Servicve.selectUserByPhone(phone);
		if(user==null) {
			responseWriteInfo(600, "不存在的用户", data, response);
			return;
		}
		
		Jihes_qf_heartbeat heart = qfzs_user_Servicve.getHeartBeat(phone);
		int count = 0;
		if(heart == null) {
			heart = new Jihes_qf_heartbeat();
			heart.setUserId(0);
			heart.setUserId(user.getId());
			heart.setPhone(phone);
			heart.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			heart.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = qfzs_user_Servicve.insertHerarBeat(heart, true);
		}else {
			heart.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = qfzs_user_Servicve.insertHerarBeat(heart, false);
		}
		
		String message="";
		if(count>=0) {
			data.put("save", "success");
		}else {
			data.put("save", "false");
			message = "保存失败";
			responseWriteInfo(200, message, data, response);
			return;
		}
		
		Jihes_qf_use use = jihes_userService.getUseInfoByPhone(phone);
		if(use == null ||  use.getStatus() == (byte) 0) {
			data.put("use", "0");
			message = "未开启群发";
		}else {
			data.put("use", "1");
			data.put("status", use.getStatus());
			message = "群发已开启";
		}
		responseWriteInfo(200, message, data, response);
	}
	
	
	
	public HttpServletResponse responseWriteInfo(int code,String message,JSONObject data,HttpServletResponse response) {
		response.setContentType("application/json;charset=utf-8"); 
		try {
			JSONObject ret = new JSONObject();
    		ret.put("code", code);
    		ret.put("message", message);
    		if(data ==null) {
    			ret.put("data", "-1");
    		}else {
    			ret.put("data", data);
    		}
			response.getWriter().write(ret.toJSONString());
			return response;
		} catch (IOException e) {
			logger.error("response.getWriter().write错误：", e);
		}
		return response;
	}
}
