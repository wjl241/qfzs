package com.cn.qfzs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cn.qfzs.pojo.Jihes_qf_wx;
import com.cn.qfzs.service.Jihes_qf_wxService;
@Controller
@RequestMapping("/cjjf")
public class Qfzs_cjjfController {
	Logger logger = LoggerFactory.getLogger(TtzBillOderController.class); 
	
	@Resource
	private Jihes_qf_wxService jihes_qf_wxService;
	
	@RequestMapping("/addWx")
	public void addAuto(HttpServletRequest request,HttpServletResponse response){
		int user_id ;
		String wxs = request.getParameter("wxs") == null ? "" : request.getParameter("wxs");
		JSONObject data= new JSONObject();
		if(wxs.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		List<Jihes_qf_wx> wxList = new ArrayList<Jihes_qf_wx>();
		Jihes_qf_wx wx = new Jihes_qf_wx();
		String[] wxss;
		int count = 0;
		if(wxs.contains(",")) {
			wxss = wxs.split(",");
			for(int i =0;i<wxss.length;i++) {
				wx = new Jihes_qf_wx();
				wx.setWx(wxss[i]);
				if(wxss[i].equals("")) {
					continue;
				}
				wx.setStatus((byte)0);
				wx.setCount(0);
				wx.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				wx.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				wxList.add(wx);
			}
			count = jihes_qf_wxService.insertWxs(wxList);
			if(count<=0) {
				data.put("insert", "false");
				responseWriteInfo(600, "插入记录数未0", data, response);
			}
			
		}else {
			wx = new Jihes_qf_wx();
			wx.setWx(wxs);
			wx.setStatus((byte)0);
			wx.setCount(0);
			wx.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			wx.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = jihes_qf_wxService.insert(wx);
			if(count<=0) {
				data.put("insert", "false");
				responseWriteInfo(600, "插入记录数未0", data, response);
			}
		}
		
		responseWriteInfo(200, "插入微信成功："+count, data, response);
		
		
	
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
