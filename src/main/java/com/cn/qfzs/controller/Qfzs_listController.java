package com.cn.qfzs.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.qfzs.pojo.Agent_user;
import com.cn.qfzs.pojo.Jihes_goods;
import com.cn.qfzs.pojo.Jihes_qf_aqueue;
import com.cn.qfzs.pojo.Jihes_qf_heartbeat;
import com.cn.qfzs.pojo.Jihes_qf_mqueue;
import com.cn.qfzs.pojo.Jihes_qf_time;
import com.cn.qfzs.pojo.Jihes_qf_use;
import com.cn.qfzs.pojo.Jihes_user;
import com.cn.qfzs.service.Jihes_userService;
import com.cn.qfzs.service.Qfzs_user_Servicve;

import util.CookieUtil;
import util.HttpClientUtil;
import util.HttpClientUtils;
import util.TimerUtil;





/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
*/



/**
 * 群发工具列表页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/queue")
public class Qfzs_listController {
	Logger logger = LoggerFactory.getLogger(TtzBillOderController.class); 
	private static String valueTime = "5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23";
	private static boolean test = false;
	//private static int testUserId = 8587;
	private static int testUserId = 14;
	@Resource
	private Qfzs_user_Servicve qfzs_user_Servicve;
	@Resource
	private Jihes_userService jihes_userService;
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response){
		responseWriteInfo(200, "链接成功", null, response);
	}
	@RequestMapping("/addAuto")
	public void addAuto(HttpServletRequest request,HttpServletResponse response){
		int user_id ;
		
		String test_id = request.getParameter("test_id") == null ? "" : request.getParameter("test_id");
		if(!test_id.equals("")  ) {//测试预留入口
			user_id = Integer.valueOf(test_id);
		}else {
			if(!test) {
				//根绝cookie获取request方法
				user_id = CookieUtil.getUserId(request, response);
				if(user_id == -1 || user_id<0) {
					responseWriteInfo(401, "未登录", null, response);
					return;
				}
			}else {
				user_id = testUserId;
			}
		}

		
		JSONObject data= new JSONObject();
	
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
//			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		String item_id = request.getParameter("item_id") == null ? "" : request.getParameter("item_id");
//		String goods_id = request.getParameter("goods_id") == null ? "" : request.getParameter("goods_id");
		if(item_id ==null || item_id.equals("")  ) {
			responseWriteInfo(401, "参数有误", null, response);
			return;
		}
		

		
	
		String hour = request.getParameter("hour") == null ? "" : request.getParameter("hour");
//		String goods_id = request.getParameter("goods_id") == null ? "" : request.getParameter("goods_id");
		if(hour ==null || hour.equals("")  ) {
			responseWriteInfo(401, "参数有误", null, response);
			return;
		}
		
		
		
		Jihes_goods goods = qfzs_user_Servicve.selectGoodById(item_id);
		if(goods == null ) {
			responseWriteInfo(401, "未找到商品信息", null, response);
			return;
		}
		Calendar cal = Calendar.getInstance();
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int mode = 0;//今日
		if(h>Integer.valueOf(hour)) {
			//明日商品
			mode = 1;
		}
		
		
		Map<String,Object> sp = new HashMap<String,Object>();
		Map<String,Object> tp = TimerUtil.getStartandEndTime6();
		if(mode==0) {
			tp = TimerUtil.getStartandEndTime6();
		}else if(mode==1){
			tp = TimerUtil.getStartandEndTime7();
		}
		sp.putAll(tp);
		sp.put("user_id", user_id);
		sp.put("item_id", item_id);
		int count = qfzs_user_Servicve.getValueMqueue2(sp);
		if(count>=1) {
			responseWriteInfo(600, "请勿重复添加商品", null, response);
			return;
		}
		
		count = qfzs_user_Servicve.getValueAqueue2(sp);
		if(count>=1) {
			responseWriteInfo(600, "当前商品已存在自动发送队列", null, response);
			return;
		}
		//获取当前用户无效时间
//		List<Jihes_qf_time> times = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTime(user_id);
		
		
		
		
//		for(Jihes_qf_time time : times) {
//			if(time.getTime().equals(hour)) {
//				responseWriteInfo(600, "当前时间已禁用，请开启后再试", null, response);
//				return;
//			}
//		}
		
		
		
		//生成小程序二维码图片
		
		Map<String,String> createMap = new HashMap<String,String>();
		String os_type = request.getParameter("os_type") == null ? "" : request.getParameter("os_type");
		String os_version = request.getParameter("os_version") == null ? "" : request.getParameter("os_version");
		String app_version = request.getParameter("app_version") == null ? "" : request.getParameter("app_version");
		String api_version = request.getParameter("api_version") == null ? "" : request.getParameter("api_version");
		String device_name = request.getParameter("device_name") == null ? "" : request.getParameter("device_name");
		String client_id = request.getParameter("client_id") == null ? "" : request.getParameter("client_id");
		String sign = request.getParameter("sign") == null ? "" : request.getParameter("sign");
		String t = request.getParameter("t") == null ? "" : request.getParameter("t");
		
	//	String api_url = "http://test_api.jihes.com/weixin_small_program/wx_code?os_type="+os_type+"&os_version="+os_version+"&app_version="+app_version+"&api_version="+api_version+"&device_name="+device_name+"&client_id="+client_id+"&sign="+sign+"&t="+t;
		String api_url = "http://api.jihes.com/weixin_small_program/wx_code?os_type=1&os_version=10.2&app_version=1.0.0&api_version=2.0.0&device_name=myphone&client_id=83a14e030ad94096b4cdbfc2a94dc203&page=1";
		//api_url = api_url.replace(" ", "%20");
		createMap.put("page", "pages/index/index");
		//createMap.put("scene", "type=goodsdetail&id="+goods.getId());
		createMap.put("scene", "tp=g&i="+goods.getItemId()+"&pi="+user_id);
		createMap.put("user_id", user_id+"");
		createMap.put("platform", "java1");//java1 是服务端 java2是群发小程序客户端
		
		HttpClientUtils httpClientUtil2 = new HttpClientUtils();
		
		String httpOrgCreateTestRtn2 = httpClientUtil2.doPost2(api_url,createMap,"utf-8"); 
		int code =200;
		String message = "";
		String send_cover ="";
		if(httpOrgCreateTestRtn2 == null || httpOrgCreateTestRtn2.equals("")) {
			responseWriteInfo(600, "生成小程序二维码图片失败", null, response);
			return;
		}else {
			JSONObject jb = new JSONObject();
			
			jb = (JSONObject) JSONObject.parse(httpOrgCreateTestRtn2);
			if(jb== null ) {//回滚数据
				responseWriteInfo(600, "生成小程序二维码图片失败，空指针", null, response);
				return;
			}else {
				code = (Integer) jb.get("code");
				message =  jb.get("message") == null ? "" : (String) jb.get("message");
				if(code != 200) {//回滚数据
					responseWriteInfo(600, message, null, response);
					return;
				}else {
					JSONObject retData = (JSONObject) jb.get("data");
					if( retData == null) {
						responseWriteInfo(600, "返回data为空", null, response);
						return;
					}
					send_cover = retData.getString("qrcode") ==null ? "" : retData.getString("qrcode") ;
					if(send_cover.equals("")) {
						responseWriteInfo(600, "返回qrcode为空", null, response);
					return;
					}
				}
			}
		}
		
		api_url = "http://api.jihes.com/weixin_small_program/detail_share_img?os_type=1&os_version=10.2&app_version=1.0.0&api_version=2.0.0&device_name=myphone&client_id=83a14e030ad94096b4cdbfc2a94dc203&page=1";
		//api_url = api_url.replace(" ", "%20");
		createMap.put("goods_id", String.valueOf(goods.getItemId()));
		createMap.put("small_url", send_cover);
		createMap.put("platform", "1");
		createMap.put("type", "1");
		createMap.put("user_id", user_id+"");
		httpClientUtil2 = new HttpClientUtils();
		
		httpOrgCreateTestRtn2 = httpClientUtil2.doPost2(api_url,createMap,"utf-8"); 
		code =200;
		message = "";
		send_cover ="";
		if(httpOrgCreateTestRtn2 == null || httpOrgCreateTestRtn2.equals("")) {
			responseWriteInfo(600, "生成七牛推送图片失败", null, response);
			return;
		}else {
			JSONObject jb = new JSONObject();
			
			jb = (JSONObject) JSONObject.parse(httpOrgCreateTestRtn2);
			if(jb== null ) {//回滚数据
				responseWriteInfo(600, "生成七牛推送图片失败，空指针", null, response);
				return;
			}else {
				code = (Integer) jb.get("code");
				message =  jb.get("message") == null ? "" : (String) jb.get("message");
				if(code != 200) {//回滚数据
					responseWriteInfo(600, message, null, response);
					return;
				}else {
					JSONObject retData = (JSONObject) jb.get("data");
					if( retData == null) {
						responseWriteInfo(600, "返回data为空", null, response);
						return;
					}
					send_cover = retData.getString("url") ==null ? "" : retData.getString("url") ;
					if(send_cover.equals("")) {
						responseWriteInfo(600, "返回url为空", null, response);
					return;
					}
				}
			}
		}
		
		
		
		
		//生成mQueue
		Map<String,Object> sendMap = new HashMap<String,Object>();
		
		Map<String,Object> timerMap = new HashMap<String,Object>();
		if(mode ==1) {
			timerMap = TimerUtil.getStartandEndTime7();
		}else {
			timerMap = TimerUtil.getStartandEndTime6();
		}
		sendMap.putAll(timerMap);
		sendMap.put("user_id", user_id);
		sendMap.put("expire_time", Integer.valueOf(hour));
		// 查询当前未发送的自动发送队列（手动添加的)
		List<Jihes_qf_mqueue> mQueues = (List<Jihes_qf_mqueue>) qfzs_user_Servicve.getMqueueByExpireTime(sendMap);
		int number = 0;
		List<Integer> numbers = new ArrayList<>();
		for(int i=0;i<=5;i++) {
			numbers.add(i);
		}
		if(mQueues != null && mQueues.size()>=6) {
			data.put("success", "false");
			responseWriteInfo(600, "此时间段手动添加商品已饱和", data, response);
			return;
		}else if(mQueues == null || mQueues.size()==0) {
			number = 0;
		}else {
			
			for(Jihes_qf_mqueue m : mQueues) {
				numbers.remove(m.getNumber());
			}
		}
		
		//获取当前用户无效时间
		List<Jihes_qf_time> times = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTimes4(user_id);
		for(Jihes_qf_time time:times) {
			if(time.getTime().equals(hour)) {//当前时间
				numbers.remove(time.getCreateTime());
			}
		}
		
			Jihes_qf_mqueue mqueue = new Jihes_qf_mqueue();
			mqueue.setUserId(user_id);
			mqueue.setPhone(user.getPhone());
			mqueue.setItemId(item_id);
			mqueue.setName(goods.getName());
			mqueue.setCover(goods.getCover());
			
			mqueue.setPrice(goods.getMarketPrice());//市场价
			mqueue.setShopCouponPrice(goods.getShopCouponPrice());
			mqueue.setSalesAmount(goods.getSalesAmount());//销量
			
			//调用七牛图片接口//TODO
			mqueue.setSendCover(getImgUrl());
			
			//分享比例，要根据一系列计算 //TODO
			mqueue.setShareRate(calculate_rate(goods.getMaxRetio(), goods.getPrice(), agent_user.getCommissionPercent()));
			mqueue.setType((byte)1);//手动条件
			mqueue.setStatus((byte)1);//手动列表
			mqueue.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			mqueue.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			mqueue.setOrigin(goods.getOrigin());
			mqueue.setRemark("");
			mqueue.setSendCover(send_cover);
			mqueue.setExpireTime(Integer.valueOf(hour));
			cal = Calendar.getInstance();
			int hour2 = cal.get(Calendar.HOUR_OF_DAY);
			int number2 = 0;
			boolean trueNumber =false;
			if(hour2 == Integer.valueOf(hour)) {//若为当前时间
				int minute = cal.get(Calendar.MINUTE);
				if(minute<10) {
					for(int n :numbers) {
						if(n>0) {
							trueNumber = true;
							number2 = n;
							break;
						}
					}
				}else {
					int minute2 = Integer.valueOf(String.valueOf(minute).substring(0, 1));
					for(int n :numbers) {
						if(n>minute2) {
							trueNumber = true;
							number2 = n;
							break;
						}
					}
					
				}
			}else {
				trueNumber = true;
				number2 = numbers.get(0);
			}
			if(trueNumber == false) {
				data.put("success", "false");
				responseWriteInfo(600, "当前时间已满，请添加一下时段", data, response);
				return;
			}
			mqueue.setNumber(number2);
			if(mode==0) {//当日
				mqueue.setSendTime(TimerUtil.getStartandEndTime4(Integer.valueOf(hour), number2));
			}else {//明日
				mqueue.setSendTime(TimerUtil.getStartandEndTime4(Integer.valueOf(hour), number2)+86400);
			}
			
			//保存即可
			int success = jihes_userService.insertMqueue(mqueue);
			if(success == 0) {
				data.put("success", "false");
				responseWriteInfo(600, "添加失败", data, response);
				return;
			}else {
				data.put("success", "true");
				responseWriteInfo(200, "添加成功", data, response);
				return;
			}
		
		
	}
	
	
	@RequestMapping("/showInfo")
	public void showInfo(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		JSONObject data= new JSONObject();
		String phone = request.getParameter("phone") == null ?"":request.getParameter("phone");
		if(!phone.equals("")) {//phone不为空，则走PC端
			Jihes_user user = qfzs_user_Servicve.selectUserByPhone(phone);
			if(user == null) {
				responseWriteInfo(200, "未知的用户信息", data, response);
				return;
			}
			user_id = user.getId();
			
		}else {//否则，是正常接口流程
			if(!test) {
				//根绝cookie获取request方法
				user_id = CookieUtil.getUserId(request, response);
				if(user_id == -1 || user_id<0) {
					responseWriteInfo(401, "未登录", null, response);
					return;
				}
			}else {
				user_id = testUserId;
			}
		}
		
		
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常2", null, response);
			return;
		}
		Calendar now = Calendar.getInstance();
		String selectHour = request.getParameter("selectHour") == null ? 
				now.get(Calendar.HOUR_OF_DAY)+"" : request.getParameter("selectHour");
		
		if(Integer.valueOf(selectHour)<0 || Integer.valueOf(selectHour)>=24 || (Integer.valueOf(selectHour)>0 && Integer.valueOf(selectHour)<5)) {
			data.put("selectHour", selectHour);
			responseWriteInfo(600, "参数有误", data, response);
			return;
		}
		
	
		
//		String today = request.getParameter("today") == null ? "true":request.getParameter("today");
//		if(today == null || today.equals("") ) {
//			responseWriteInfo(200, "参数有误", null, response);
//			return;
//		}
//		boolean isToday = true;
//		if(today.equals("true")) {
//			isToday = true;
//		}else if(today.equals("false")){
//			isToday = false;
//		}else {
//			responseWriteInfo(200, "参数有误", null, response);
//			return;
//		}
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		boolean isToday = true;
		if(hour<=Integer.valueOf(selectHour)) {
			isToday = true;
		}else {
			isToday =false;
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("user_id", user_id);
		paramMap.put("status", (byte) 1);//1开启 0 关闭
		Jihes_qf_use use = qfzs_user_Servicve.selectUseStatus(paramMap);
		if(use == null) {//是否启用群发工具
			data.put("use", "false");
		}else {
			data.put("use", "true");
		}
		//获取当前用户无效时间（小段）
		paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("time", selectHour);
		
		
		//所有的无效时间，小时
		List<String> times = (List<String>) qfzs_user_Servicve.getNoUseTimes(user_id);
		
		List<String> valueTimes = caclValueTime(times,selectHour);
		//先获取有效的时间及顺序
		data.put("time", valueTimes);
		
		//当前小时段失效的时间，分
		List<Jihes_qf_time> expireTimes = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTime2(paramMap);
		
		paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.putAll(TimerUtil.getStartandEndTime5(isToday));
		List<Jihes_qf_mqueue> mQueues = qfzs_user_Servicve.getValueMqueue(paramMap);
		int end_time = (int)paramMap.get("end_time");
		paramMap.put("end_time",end_time+7200);//超过24点的自动业收入
		List<Jihes_qf_aqueue> aQueues = qfzs_user_Servicve.getValueAqueue(paramMap);
		
		//获取自动列表删除的队列
		List<String> deleteItemid_A = qfzs_user_Servicve.getDeleteAqueue(paramMap);
//		if(aQueues ==null || aQueues.size()==0) {
//			responseWriteInfo(200, "未获取到默认自动队列", data, response);
//			return;
//		}
		JSONArray sdfs = new JSONArray();//手动发送
		JSONArray zdlb = new JSONArray();//自动列表
		//选中小时删除位
		List<Integer> expires = new ArrayList<Integer>();
		JSONObject sd = new JSONObject();//手动
		List<Integer> sdList = new ArrayList<Integer>();
		for(int i=0;i<6;i++) {
			sdList.add(i);
		}
		
		for(Jihes_qf_time expireTime : expireTimes) {
			expires.add(expireTime.getNumber());
			sd = new JSONObject();//手动
			sd.put("expireTime", expireTime.getTime()+"");//小时
			sd.put("number", expireTime.getNumber()+"");//段位
			sd.put("type", "2");//0自动 1手动添加 2 删除位
			sdfs.add(sd);
			sdList.remove(expireTime.getNumber());
		}
		for(Jihes_qf_mqueue m : mQueues) {
			if(String.valueOf(m.getExpireTime()).equals(selectHour) //当前小时有手动添加
					&& !expires.contains(m.getNumber())) {//并且时段未失效
				sd = new JSONObject();//手动
				sd.put("expireTime", m.getExpireTime()+"");//小时
				sd.put("number", m.getNumber()+"");//段位
				sd.put("type", "1");//0自动 1手动添加 2 删除位
				sd.put("item_id", m.getItemId());
				sd.put("name", m.getName());
				sd.put("price", m.getPrice()+"");//市场价格
				sd.put("shop_coupon_price", m.getShopCouponPrice()+"");//券价格 
				sd.put("realPrice", calRealPrice(m.getPrice(), m.getShopCouponPrice())+"");//实际价格
				sd.put("shareRate", m.getShareRate().setScale(BigDecimal.ROUND_HALF_UP, 2)+"");//获取分享指数
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sd.put("sendTime", sdf.format(new Date(Long.valueOf(m.getSendTime())*1000)));//发送时间
				if(isToday) {
					sd.put("day", "今日");//今日/明日
				}else {
					sd.put("day", "明日");//今日/明日
				}
				//sd.put("day", getToday(Long.valueOf(m.getSendTime())*1000));//今日/明日
				sd.put("saleAmount", m.getSalesAmount()+"");//销量
				sd.put("cover", m.getCover());//图片
				sd.put("remark", m.getRemark());//推荐语
				sd.put("manual", "true");
				sd.put("origin", m.getOrigin()== null ? "0" :m.getOrigin()+"");
				if(isToday) {
					if(m.getSendTime()>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
						sd.put("sendStatus", "未发送");
					}else {
						sd.put("sendStatus", "已发送");
					}
				}else {
					sd.put("sendStatus", "未发送");
				}
				
				sd.put("send_Cover", m.getSendCover());
				sdfs.add(sd);
				sdList.remove(m.getNumber());
			}
		}
		
		for(int i=0;i<sdList.size();i++) {
			sd = new JSONObject();//手动
			sd.put("expireTime", selectHour+"");//小时
			sd.put("number", sdList.get(i)+"");//段位
			sd.put("type", "0");//0自动 1手动添加 2 删除位
			sdfs.add(sd);
		}
		data.put("sdfs", sdfs);//添加手动发送列表
		
		
		//所有的无效时间，小时
		List<Jihes_qf_time> allExpireTimes = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTimes3(user_id);
		
		//自动列表及已发送的 手动添加队列
		List<Jihes_qf_mqueue> mQueues2 =  new ArrayList<Jihes_qf_mqueue>();
		for(Jihes_qf_mqueue m : mQueues) {
			if(m.getStatus() ==0 || m.getStatus()==4) {
				mQueues2.add(m);
			}
		}
		
		
		int autoCount = 0;//记录aMqueue队列的序号，使用到了谁
		Jihes_qf_aqueue a = new Jihes_qf_aqueue();
		JSONObject zd = new JSONObject();
		//添加自动列表
		a:for(int i=5;i<24;i++) {//当日
			b:for(int j=0;j<=5;j++) {//6个时段
				c:for(Jihes_qf_time time:allExpireTimes) {
					if(time.getTime().equals(String.valueOf(i)) 
							&& time.getNumber() == j) {//若当前位删除，则跳出b层循环
						continue b;
					}
				}
				//当前没有删除,则判断是否有手动添加位
				d:for(Jihes_qf_mqueue m : mQueues) {
					if(m.getExpireTime() == i
							&& m.getNumber() == j) {//若当前位有 手动 添加的自动列表，则添加手动位,0自动列表 4已发送状态，若为1手动列表，则跳过
						if(m.getStatus()==1) {
							continue b;
						}
						zd = new JSONObject();//自动
						zd.put("expireTime", m.getExpireTime()+"");//小时
						zd.put("number", m.getNumber()+"");//段位
						zd.put("type", m.getType()+"");//0自动 1手动添加 2 删除位,3过期 4发送
						zd.put("item_id", m.getItemId());//商品id
						zd.put("name", m.getName());
						zd.put("price", m.getPrice()+"");//市场价格
						zd.put("shop_coupon_price", m.getShopCouponPrice()+"");//券价格 
						zd.put("realPrice", calRealPrice(m.getPrice(), m.getShopCouponPrice())+"");//实际价格
						zd.put("shareRate", m.getShareRate().setScale(BigDecimal.ROUND_HALF_UP, 2)+"");//获取分享指数
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						zd.put("sendTime", sdf.format(new Date(Long.valueOf(m.getSendTime())*1000)));//发送时间
						if(isToday) {
							zd.put("day", "今日");//今日/明日
						}else {
							zd.put("day", "明日");//今日/明日
						}
						//zd.put("day", getToday(Long.valueOf(m.getSendTime())*1000));//今日/明日
						zd.put("saleAmount", m.getSalesAmount()+"");//销量
						zd.put("cover", m.getCover());//图片
						zd.put("remark", m.getRemark());//推荐语
						zd.put("manual", "true");
						zd.put("origin", m.getOrigin()== null ? "0" :m.getOrigin()+"");
						if(isToday) {
							if(m.getSendTime()>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
								zd.put("sendStatus", "未发送");
							}else {
								zd.put("sendStatus", "已发送");
							}
						}else {
							zd.put("sendStatus", "未发送");
						}
					
						zd.put("send_Cover", m.getSendCover());
						//现在手动添加的直接去上面sdfs列表啦，这里直接无视，不会出现的，恩至少现在
						//zdlb.add(zd);
						continue b;
					}
				}
					
					if(aQueues.size()<=autoCount) {
						zd = new JSONObject();//自动
						zd.put("expireTime", i+"");//小时
						zd.put("number", j+"");//段位
						zd.put("type", "5");//0自动 1手动添加 2 删除位,3过期 4发送，5缺失
						zdlb.add(zd);
						continue b;
					}
					
					//手动位也没有，用自动位填充
					a = aQueues.get(autoCount);
					//判断是否有删除位，若有，则删除
					for(int u=0;u<300;u++) {//正常不会超过300吧
						if(deleteItemid_A.contains(a.getItemId())) {
							autoCount = autoCount + 1;
							if(aQueues.size()<=autoCount) {
								zd = new JSONObject();//自动
								zd.put("expireTime", i+"");//小时
								zd.put("number", j+"");//段位
								zd.put("type", "5");//0自动 1手动添加 2 删除位,3过期 4发送，5缺失
								zdlb.add(zd);
								continue b;
							}
							a = aQueues.get(autoCount);
						}else {
							autoCount = autoCount + 1;
							break;
						}
					}
					if(a.getItemId().equals("560753865953")) {
						System.err.println("");
					}
					zd = new JSONObject();//自动
					zd.put("expireTime", i+"");//小时
					zd.put("number", j+"");//段位
					zd.put("type", a.getType()+"");//0自动 1手动添加 2 删除位,3过期 4发送
					zd.put("item_id", a.getItemId());//商品id
					zd.put("name", a.getName());
					zd.put("price", a.getPrice()+"");//市场价格
					zd.put("shop_coupon_price", a.getShopCouponPrice()+"");//券价格 
					zd.put("realPrice", calRealPrice(a.getPrice(), a.getShopCouponPrice())+"");//实际价格
					zd.put("shareRate", calculate_rate(a.getMaxRetio(), calRealPrice(a.getPrice(), a.getShopCouponPrice()), agent_user.getCommissionPercent())+"");//获取分享指数
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					zd.put("sendTime", sdf.format(new Date(Long.valueOf(TimerUtil.getStartandEndTime4(i, j))*1000)));//发送时间 06:12
					if(isToday) {
						zd.put("day", "今日");//今日/明日
					}else {
						zd.put("day", "明日");//今日/明日
					}
					zd.put("saleAmount", a.getSalesAmount()+"");//销量
					zd.put("cover", a.getCover());//图片
					zd.put("remark", a.getRemark());//推荐语
					zd.put("manual", "false");
					zd.put("origin", a.getOrigin()== null ? "0" :a.getOrigin()+"");
					if(isToday) {
						if(TimerUtil.getStartandEndTime42(i, j)+60>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
							zd.put("sendStatus", "未发送");
						}else {
							zd.put("sendStatus", "已发送");
						}
					}else {
						zd.put("sendStatus", "未发送");
					}
					
					zd.put("send_Cover", a.getSendCover());
					zdlb.add(zd);
					continue b;
			}
		}
		boolean isContinue = true;
		if(aQueues ==null ||aQueues.size()==0) {
			isContinue = false;
		}
		
		while(isContinue) {
			if(aQueues.size() == autoCount) {
				break;
			}
			//手动位也没有，用自动位填充
			a = aQueues.get(autoCount);
			//判断是否有删除位，若有，则删除
			for(int u=0;u<300;u++) {//正常不会超过300吧
				
				
				if(deleteItemid_A.contains(a.getItemId())) {
					autoCount = autoCount + 1;
					if(aQueues.size()<=autoCount) {
						isContinue =false;
						break;
					}
					a = aQueues.get(autoCount);
				}else {
					autoCount = autoCount + 1;
					break;
				}
			}
			if(!isContinue) {
				break;
			}
			zd = new JSONObject();//自动
			zd.put("expireTime", "0");//小时
			zd.put("number", "0");//段位
			zd.put("type", a.getType().toString());//0自动 1手动添加 2 删除位,3过期 4发送
			zd.put("item_id", a.getItemId());//商品id
			zd.put("name", a.getName());
			zd.put("price", a.getPrice().toString());//市场价格
			zd.put("shop_coupon_price", a.getShopCouponPrice()+"");//券价格 
			zd.put("realPrice", calRealPrice(a.getPrice(), a.getShopCouponPrice()).toString());//实际价格
			zd.put("shareRate", calculate_rate(a.getMaxRetio(), calRealPrice(a.getPrice(), a.getShopCouponPrice()), agent_user.getCommissionPercent()).toString());//获取分享指数
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			zd.put("sendTime", "");//发送时间 06:12
			if(isToday) {
				zd.put("day", "今日");//今日/明日
			}else {
				zd.put("day", "明日");//今日/明日
			}
		
			zd.put("saleAmount", a.getSalesAmount());//销量
			zd.put("cover", a.getCover());//图片
			zd.put("remark", a.getRemark());//推荐语
			zd.put("manual", "false");
			zd.put("origin", a.getOrigin()== null ? "0" :a.getOrigin().toString());
			zd.put("sendStatus", "未发送");
			zd.put("send_Cover", a.getSendCover());
			zdlb.add(zd);
			
			if(aQueues.size()<=autoCount) {
				isContinue = false;
				break;
			}
		}
		
		data.put("zdlb", zdlb);
		responseWriteInfo(200, "", data,response);
		
	}
	
	
	@RequestMapping("/deleteTime")
	public void deleteTime(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		
		JSONObject data= new JSONObject();
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		String expire_time = request.getParameter("expire_time");
		if(expire_time ==null || expire_time.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		String selectHour = request.getParameter("selectHour");
		if(selectHour ==null || selectHour.equals("")) {
			selectHour = expire_time;
		}
		
		
		String number = request.getParameter("number");
		if(number ==null || number.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		if(Integer.valueOf(number)<0 || Integer.valueOf(expire_time)<0){
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		String status = request.getParameter("status");
		if(status ==null || status.equals("")){
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		Map<String,Object> parmMap = new HashMap<String,Object>();
		parmMap.put("user_id", user_id);
		parmMap.put("time", expire_time);
		parmMap.put("number", number);
		Jihes_qf_time time  = qfzs_user_Servicve.getDeleteTime(parmMap);
		int count =0;
		if(time == null) {//新建一个无效时间
			time = new Jihes_qf_time();
			time.setUserId(user_id);
			time.setPhone(user.getPhone());
			time.setStatus(Byte.valueOf(status));
			time.setTime(expire_time);
			time.setNumber(Integer.valueOf(number));
			time.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			time.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = qfzs_user_Servicve.updateDeleteTime(time,true);
		}else{
			time.setStatus(Byte.valueOf(status));
			time.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			count = qfzs_user_Servicve.updateDeleteTime(time,false);
		}
		if(count >= 1) {
			data.put("count", count);
			//调用下刷新界面
			data =showInfo2(request, response,data,"保存成功");
			if(data ==null) {
				responseWriteInfo(600, "刷新列表失败", null, response);
			}else {
				responseWriteInfo(200, "删除商品成功", data, response);
			}
		}else {
			responseWriteInfo(600, "保存失败", data, response);
		}
		
	
		
		
		
	}
	
	
	
	@RequestMapping("/deleteGoods")
	public void deleteGoods(HttpServletRequest request,HttpServletResponse response){
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		
		JSONObject data= new JSONObject();
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		String item_id = request.getParameter("item_id");
		if(item_id ==null || item_id.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		
		Map<String,Object> map = TimerUtil.getStartandEndTime3("24");
		map.put("item_id", item_id);
		map.put("user_id", user_id);
		Jihes_qf_mqueue mQueue = (Jihes_qf_mqueue) qfzs_user_Servicve.getMqueueInfo(map);
		if(mQueue!=null) {
			if(mQueue.getType()==3) {
				data.put("delete", "false");
				responseWriteInfo(600, "该商品已删除", data, response);
				return;
			}
			
			if(mQueue.getStatus()==4 ) {
				data.put("delete", "false");
				responseWriteInfo(600, "商品已发送，无法删除", data, response);
				return;
			}
			
			mQueue.setStatus((byte)2);
			mQueue.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			int count = qfzs_user_Servicve.updateDeleteMqueue(mQueue,false);
			if(count<1) {
				data.put("delete", "false");
				responseWriteInfo(600, "删除商品失败", data, response);
				return;
			}
		}else {//否则应为自动商品
			Jihes_qf_aqueue aQueue = (Jihes_qf_aqueue) qfzs_user_Servicve.getAqueueInfo(map);
			if(aQueue == null) {
				data.put("delete", "false");
				responseWriteInfo(600, "删除商品失败，未找到对应商品信息", data, response);
				return;
			}
			Jihes_qf_mqueue m = new Jihes_qf_mqueue();
			m.setUserId(user_id);
			m.setPhone(user.getPhone());
			m.setItemId(aQueue.getItemId());
			m.setName(aQueue.getName());
			m.setPrice(aQueue.getPrice());
			m.setShopCouponPrice(aQueue.getShopCouponPrice());
			m.setSalesAmount(aQueue.getSalesAmount());
			m.setCover(aQueue.getCover());
			m.setSendCover(aQueue.getSendCover());
			m.setType((byte)2);//默认列表转入的删除
			m.setStatus((byte)2);
			m.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			m.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			m.setOrigin(aQueue.getOrigin());
			m.setShareRate(new BigDecimal(0.00));
			m.setSendTime(aQueue.getSendTime());
			m.setExpireTime(aQueue.getExpireTime());
			m.setNumber(aQueue.getNumber());
			m.setRemark(aQueue.getRemark());
			int count =  qfzs_user_Servicve.updateDeleteMqueue(m, true);
			if(count<=0) {
				data.put("delete", "false");
				responseWriteInfo(600, "删除商品失败", data, response);
				return;
			}
		}
		data.put("delete", "success");
		//responseWriteInfo(200, "删除商品成功", data, response);
		//删除完成后，再调用一下刷新界面
		
		//调用下刷新界面
		data =showInfo2(request, response,data,"删除商品成功");
		if(data ==null) {
			responseWriteInfo(600, "刷新列表失败", null, response);
		}else {
			responseWriteInfo(200, "删除商品成功", data, response);
		}
	
	}
	
	
	
	@RequestMapping("/removeZdfs")
	public void removeZdfs(HttpServletRequest request,HttpServletResponse response,JSONObject data){
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		if(data ==null) {
			data= new JSONObject();
		}
		
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
	}
	
	
	
	@RequestMapping("/updateTime")
	public void updateTime(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		JSONObject data = new JSONObject();
		
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		String type = request.getParameter("type") == null ? "" : request.getParameter("type");
		if(type.equals("") ) {
			responseWriteInfo(600, "参数异常", null, response);
			return;
		}
		if(!type.equals("0") && !type.equalsIgnoreCase("1")){//0开启，1关闭
			responseWriteInfo(600, "参数异常", null, response);
			return;
		}
		
		String time = request.getParameter("time") == null ? "" : request.getParameter("time");
		if(time.equals("") ) {
			responseWriteInfo(600, "参数异常", null, response);
			return;
		}
		
		int updateTime = Integer.valueOf(time);
		if(updateTime<5 || updateTime>24) {
			responseWriteInfo(600, "参数异常", null, response);
			return;
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("user_id", user_id);
		paramMap.put("time", time);
		List<Jihes_qf_time> times = qfzs_user_Servicve.getDeleteTimes(paramMap);
		List<Jihes_qf_time> insertTimes = new ArrayList<>();
		List<Jihes_qf_time> updateTimes = new ArrayList<>();
		Jihes_qf_time insertTime = new Jihes_qf_time();
		for(int i=0;i<=5;i++) {
			boolean update =false;
			insertTime = new Jihes_qf_time();
			for(Jihes_qf_time t : times) {
				if(t.getNumber() == i) {
					update = true;
					t.setStatus(Byte.valueOf(type));
					t.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
					updateTimes.add(t);
				}
			}
			if(!update) {//若没有update，则插入
				insertTime.setUserId(user_id);
				insertTime.setPhone(user.getPhone());
				insertTime.setStatus(Byte.valueOf(type));
				insertTime.setTime(time);
				insertTime.setNumber(i);
				insertTime.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				insertTime.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
				insertTimes.add(insertTime);
			}
		}
		
		if(insertTimes.size()>=1) {//插入
			int count = qfzs_user_Servicve.insertTimes(insertTimes);
			if(count <=0) {
				data.put("updateTime", false);
				responseWriteInfo(600, "保存失败", data, response);
				return;
			}
		}
		if(updateTimes.size()>=1) {//修改
			int count = qfzs_user_Servicve.updateTimes(updateTimes);
			if(count <=0) {
				data.put("updateTime", false);
				responseWriteInfo(600, "保存失败", data, response);
				return;
			}
		}
		
		data.put("updateTime", true);
		responseWriteInfo(200, "保存成功", data, response);
		return;
		
		
	}
	
	
	@RequestMapping("/showTime")
	public void showTime(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		
	
		JSONObject data = new JSONObject();
		
		
		//所有的无效时间，小时
		List<String> times = (List<String>) qfzs_user_Servicve.getNoUseTimes(user_id);
		JSONArray jar = new JSONArray();
		JSONObject jb = new JSONObject();
		for(int i=5;i<=24;i++) {
			jb = new JSONObject();
			jb.put("time", i+"");
			jb.put("status", "0");//开启
			for(String t : times) {
				
				if(Integer.valueOf(t) == i) {
					jb.put("status", "1");//关闭
				}
			}
			jar.add(jb);
		}
		
		data.put("time", jar);
		responseWriteInfo(200, "", data, response);
	}
	
	
	@RequestMapping("/synQueue")
	public void synQueue(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		JSONObject data= new JSONObject();
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		Calendar now = Calendar.getInstance();
		String selectHour = request.getParameter("selectHour") == null ? String.valueOf(now.get(Calendar.HOUR_OF_DAY)) : request.getParameter("selectHour");
		
		if(Integer.valueOf(selectHour)<0 || Integer.valueOf(selectHour)>24 || (Integer.valueOf(selectHour)>0 && Integer.valueOf(selectHour)<6)) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		boolean isToday = true;
		if(hour<=Integer.valueOf(selectHour)) {
			isToday = true;
		}else {
			isToday =false;
		}
		
		String sdfs = request.getParameter("sdfs") == null ? "" :request.getParameter("sdfs");
		if(sdfs.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		String zdlb = request.getParameter("zdlb") == null ? "" :request.getParameter("zdlb");
		if(zdlb.equals("")) {
			responseWriteInfo(600, "参数有误", null, response);
			return;
		}
		
		
		
		//JSONObject sdfs_jb = (JSONObject) JSON.parse(sdfs);
		//手动发送
		JSONArray sdfs_ar = (JSONArray)JSON.parse(sdfs);
		JSONObject sd = new JSONObject();
		List<String> item_ids = new ArrayList<String>();
		Map<String,Object> values = new HashMap<String,Object>();
		String item_id = "";
		for(int i=0;i<sdfs_ar.size();i++) {
			sd = (JSONObject) sdfs_ar.get(i);
			item_id =  sd.get("item_id") == null ? "" : (String)sd.get("item_id");
			item_ids.add(item_id);
			values.put(item_id, sd);
		}
		JSONArray zdlb_ar = (JSONArray)JSON.parse(zdlb);
		for(int i=0;i<zdlb_ar.size();i++) {
			sd = (JSONObject) sdfs_ar.get(i);
			item_id =  sd.get("item_id") == null ? "" : (String)sd.get("item_id");
			item_ids.add(item_id);
			values.put(item_id, sd);
		}
		String items ="";
		for(String item : item_ids) {
			items = items+","+item;
		}
		items = items.substring(1);
		Map<String,Object> map = new HashMap<String,Object>();
		map = TimerUtil.getStartandEndTime5(isToday);
		map.put("list", item_ids);
		map.put("user_id", user_id);
		List<Jihes_qf_mqueue> mQueues = qfzs_user_Servicve.getMqueues(map);
		JSONObject jb = new JSONObject();
		List<Jihes_qf_mqueue> updateMqueues = new ArrayList<Jihes_qf_mqueue>();
		for(Jihes_qf_mqueue m : mQueues) {
			jb = (JSONObject) values.get(m.getItemId());
			m.setExpireTime((Integer)jb.get("expire_time"));
			m.setNumber((Integer)jb.get("number"));
			int a = (Integer)jb.get("status");
			Byte.valueOf(String.valueOf(a));
			m.setStatus(Byte.valueOf(String.valueOf(a)));//0自动 1手动
			m.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			m.setRemark((String)jb.get("remark"));
			updateMqueues.add(m);
		}
		//批量修改
		List<Jihes_qf_aqueue> aQueues = qfzs_user_Servicve.getAqueues(map);
		Jihes_qf_mqueue m2 = new Jihes_qf_mqueue();
		List<Jihes_qf_mqueue> insertMqueues = new ArrayList<Jihes_qf_mqueue>();
		for(Jihes_qf_aqueue a : aQueues) {
			m2 = new Jihes_qf_mqueue();
			jb = (JSONObject) values.get(a.getItemId());
			m2.setUserId(user_id);
			m2.setPhone(user.getPhone());
			m2.setItemId(a.getItemId());
			m2.setOrigin(a.getOrigin() == null ? (byte)0 :a.getOrigin());
			m2.setName(a.getName());
			m2.setPrice(a.getPrice());
			m2.setShopCouponPrice(a.getShopCouponPrice());
			m2.setSalesAmount(a.getSalesAmount());
			m2.setShareRate(calculate_rate(a.getMaxRetio(), calRealPrice(a.getPrice(), a.getShopCouponPrice()), agent_user.getCommissionPercent()));
			m2.setCover(a.getCover());
			m2.setSendCover(a.getSendCover());
			m2.setSendTime(TimerUtil.getStartandEndTime4(a.getExpireTime(), a.getNumber()));//发送时间十进制
			m2.setType((byte)1);//手动添加
			m2.setExpireTime((Integer)jb.get("expire_time"));
			m2.setNumber((Integer)jb.get("number"));
			int hehe = (Integer)jb.get("status");
			m2.setStatus(Byte.valueOf(String.valueOf(hehe)));//0自动 1手动
			//m2.setStatus(Byte.valueOf((String)jb.get("status")));//0自动 1手动
			m2.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			m2.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			m2.setRemark(a.getRemark());
			insertMqueues.add(m2);
		}
		
		
		
		
		
		
		//批量插入、
		if(insertMqueues.size()>=1) {//插入
			int count = qfzs_user_Servicve.insertMqueues(insertMqueues);
			if(count <=0) {
				data.put("updateQueue", false);
				responseWriteInfo(600, "保存失败", data, response);
				return;
			}
		}
		//保存
		if(updateMqueues.size()>=1) {//修改
			Map<String,Object> lists2 = new HashMap<String,Object>();
			lists2.put("list", updateMqueues);
			int count = qfzs_user_Servicve.updateMqueues(lists2);
			if(count <=0) {
				data.put("updateQueue", false);
				responseWriteInfo(600, "保存失败", data, response);
				return;
			}
		}
		
		if(insertMqueues.size() <=0 && updateMqueues.size() <=0) {
			data.put("updateQueue", false);
			responseWriteInfo(600, "未找到需要修改的队列", data, response);
			return;
		}
		data.put("updateQueue", true);
		data =showInfo2(request, response,data,"保存成功");
		if(data ==null) {
			responseWriteInfo(600, "刷新列表失败", null, response);
		}else {
			responseWriteInfo(200, "保存成功", data, response);
		}
	}
	
	
	@RequestMapping("/changQfStatus")
	public void changQfStatus(HttpServletRequest request,HttpServletResponse response){
		//根绝cookie获取request方法
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		JSONObject data= new JSONObject();
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		String status = request.getParameter("status") == null ? "" : request.getParameter("status");//0关闭 1开启
		if(status.equals("")) {
			responseWriteInfo(600, "请求参数异常", null, response);
			return;
		}
		
		if(status.equals("1")) {//若开启
			Jihes_qf_heartbeat heartbeat = qfzs_user_Servicve.getHeartBeat(user.getPhone());
			if(heartbeat ==null) {
				responseWriteInfo(600, "未检测到PC端开启群发工具，请检查", null, response);
				return;
			}
			int t = Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)) - heartbeat.getUpdateTime();
			if(t>120) {//若小于两分钟，则说明开启
				responseWriteInfo(600, "未检测到PC端开启群发工具，请检查", null, response);
				return;
			}
		}
		
		Jihes_qf_use use = qfzs_user_Servicve.getUseInfo(user_id);
		if(use ==null) {
			use = new Jihes_qf_use();
			use.setUserId(user_id);
			use.setPhone(user.getPhone());
			use.setStatus(Byte.valueOf(status));
			use.setCreateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			use.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			int count = qfzs_user_Servicve.insertUse(use);
			if(count <=0) {
				data.put("update", "false");
				if(status.equals("0")) {
					responseWriteInfo(600, "关闭失败", data, response);
				}else {
					responseWriteInfo(600, "开启失败", data, response);
				}
				return;
			}
		}else {
			use.setStatus(Byte.valueOf(status));
			use.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
			int count = qfzs_user_Servicve.updateUse(use);
			if(count <=0) {
				data.put("update", "false");
				if(status.equals("0")) {
					responseWriteInfo(600, "关闭失败", data, response);
				}else {
					responseWriteInfo(600, "开启失败", data, response);
				}
				return;
			}
		}
		data.put("update", "true");
		if(status.equals("0")) {
			responseWriteInfo(200, "关闭成功", data, response);
		}else {
			responseWriteInfo(200, "开启成功", data, response);
		}
		
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
	
	
	public static void main(String[] args) {
		getImgUrl();
	}
	
	public static String getImgUrl() {
		String api_url = "http://api.jihes.com/weixin_small_program/wx_code?os_type=1&os_version=10.2&app_version=1.0.0&api_version=2.0.0&device_name=myphone&client_id=83a14e030ad94096b4cdbfc2a94dc203&page=1";
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		Map<String,String> createMap = new HashMap<String,String>();  
		String ret = httpClientUtil.doPost2(api_url,createMap,"utf-8");  
		System.err.println(ret);
		return "";
	}
	
	/**
	 * 计算分享值
	 * @param max_retio
	 * @param pirce
	 * @param commission_percent
	 * @return
	 */
	public static BigDecimal calculate_rate(float max_retio,BigDecimal pirce,float commission_percent) {
		BigDecimal share_rate = new BigDecimal(max_retio)
				.multiply(new BigDecimal(commission_percent))
				.multiply(pirce).divide(new BigDecimal("10000"))
				.setScale(BigDecimal.ROUND_HALF_UP, 2);
		return share_rate;
	}
	
	/**
	 * 循环获得下一个没有被关闭的时间位
	 * @param times
	 * @param expire_time
	 * @param order
	 * @return
	 */
	public static Map<String,Object> circleTime(List<Jihes_qf_time> times,int expire_time,int order,boolean isNew) {
		Map<String,Object> ret = new HashMap<String,Object>(); 
		int returnTime =expire_time;
		int returnOrder = order;
		boolean bj = true;
		for(Jihes_qf_time time : times) {//检测time是否停止
			if(Integer.valueOf(time.getTime()) == expire_time) {//若小时相等
				if(time.getNumber() == order) {//若顺序也相等,则计算出新时间重新循环
					bj = false;
					if(order!=5) {
						returnTime = expire_time;
						returnOrder = order+1;
						ret = circleTime(times, returnTime, returnOrder,false);
					}else if(order == 5) {//刚好事一个小时的最后，则小时进位
						returnTime = expire_time + 1;
						returnOrder = 0;
						ret = circleTime(times, returnTime, returnOrder,false);
					}else {
						ret.put("success", false);
						return ret;
					}
				}
				
			}
		}
		if(bj && !isNew) {//新增就不用+1了，让原来的实体后退一位
			if(returnOrder!=5) {
				returnOrder = returnOrder+1;
			}else {
				returnOrder = 0;
				returnTime= returnTime +1;
			}
		}
		//循环完成后返回时间
		ret.put("success", true);
		ret.put("expire_time", returnTime);
		ret.put("number", returnOrder);
		return ret;
	}
	
	/**
	 * 根据当前选中的时间，返回时间列表
	 * @param times
	 * @param nowHour
	 * @return
	 */
	private static List<String> caclValueTime(List<String> times,String nowHour) {
		String[] valueTimes = valueTime.split(",");
		List<String> retTimes = new ArrayList<String>();
		for(String valueTime : valueTimes) {
			boolean add =true;
			for(String expireTime : times) {
				if(valueTime.equals(expireTime)) {//说明改时间已失效
					add=false;
					break;
				}
			}
			if(add) {
				retTimes.add(valueTime);
			}
		}
		
		
		int HH = Integer.valueOf(nowHour);
		if(HH<=5 && HH>=0) {//5点以内，直接返回
			return retTimes;
		}
		List<String> retTimes2 = new ArrayList<String>();
		List<String> copyTimes = new ArrayList<String>();
		List<String> copyTimes2 = new ArrayList<String>();
		
		for(String realTime : retTimes) {
			copyTimes.add(realTime);
		}
		for(String realTime : retTimes) {
			copyTimes2.add(realTime);
		}
		boolean ks = false;
		for(String realTime : retTimes) {
			if(realTime.equals(nowHour)) {//若想等，则从此开始
				ks =true;
			}else {
				if(!ks) {//未到当前时间，删除   比如10点开始，这里保留10，11，12.。。。24
					copyTimes.remove(realTime);
				}else {//开始后，就拼命删除，这里保留6，7，8，9
					copyTimes2.remove(realTime);
				}
				
			}
		}
		copyTimes2.remove(nowHour);
		
		retTimes2.addAll(copyTimes);
		retTimes2.addAll(copyTimes2);
		System.err.println(retTimes2);
		return retTimes2;
	}
	
	/**
	 * 根据券价格和市场价，计算实际价格
	 * @param price
	 * @param couponPrice
	 * @return
	 */
	private static BigDecimal calRealPrice(BigDecimal price ,float couponPrice) {
		BigDecimal cP = new BigDecimal(couponPrice);
		BigDecimal realPrice = new BigDecimal("0.00");
		realPrice = price.subtract(cP).setScale(BigDecimal.ROUND_HALF_UP, 2);
		return realPrice;
	}
	
	/**
	 * 计算今日还是明日
	 * @param sendTime
	 * @return
	 */
	private static String getToday(long sendTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String end_time = now + "23:59:59";
		try {
			long end = sdf2.parse(end_time).getTime();
			if(sendTime>end) {
				return "明日";
			}else{
				return "今日";
			}
		} catch (ParseException e) {
		}
		return "今日";
	}
	
	
	public JSONObject showInfo2(HttpServletRequest request,HttpServletResponse response,JSONObject data,String message){

		//根绝cookie获取request方法
		int user_id ;
		String phone = request.getParameter("phone") == null ?"":request.getParameter("phone");
		if(!phone.equals("")) {//phone不为空，则走PC端
			Jihes_user user = qfzs_user_Servicve.selectUserByPhone(phone);
			if(user == null) {
				responseWriteInfo(200, "未知的用户信息", data, response);
				return null;
			}
			user_id = user.getId();
			
		}else {//否则，是正常接口流程
			if(!test) {
				//根绝cookie获取request方法
				user_id = CookieUtil.getUserId(request, response);
				if(user_id == -1 || user_id<0) {
					responseWriteInfo(401, "未登录", null, response);
					return null;
				}
			}else {
				user_id = testUserId;
			}
		}
		
		
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return null;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return null;
		}
		Calendar now = Calendar.getInstance();
		String selectHour = request.getParameter("selectHour") == null ? 
				now.get(Calendar.HOUR_OF_DAY)+"" : request.getParameter("selectHour");
		
		if(Integer.valueOf(selectHour)<0 || Integer.valueOf(selectHour)>=24 || (Integer.valueOf(selectHour)>0 && Integer.valueOf(selectHour)<5)) {
			data.put("selectHour", selectHour);
			responseWriteInfo(600, "参数有误", data, response);
			return null;
		}
		
	
		
//		String today = request.getParameter("today") == null ? "true":request.getParameter("today");
//		if(today == null || today.equals("") ) {
//			responseWriteInfo(200, "参数有误", null, response);
//			return;
//		}
//		boolean isToday = true;
//		if(today.equals("true")) {
//			isToday = true;
//		}else if(today.equals("false")){
//			isToday = false;
//		}else {
//			responseWriteInfo(200, "参数有误", null, response);
//			return;
//		}
		
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		boolean isToday = true;
		if(hour<=Integer.valueOf(selectHour)) {
			isToday = true;
		}else {
			isToday =false;
		}
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("user_id", user_id);
		paramMap.put("status", (byte) 1);//1开启 0 关闭
		Jihes_qf_use use = qfzs_user_Servicve.selectUseStatus(paramMap);
		if(use == null) {//是否启用群发工具
			data.put("use", "false");
		}else {
			data.put("use", "true");
		}
		//获取当前用户无效时间（小段）
		paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.put("time", selectHour);
		
		
		//所有的无效时间，小时
		List<String> times = (List<String>) qfzs_user_Servicve.getNoUseTimes(user_id);
		
		List<String> valueTimes = caclValueTime(times,selectHour);
		//先获取有效的时间及顺序
		data.put("time", valueTimes);
		
		//当前小时段失效的时间，分
		List<Jihes_qf_time> expireTimes = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTime2(paramMap);
		
		paramMap = new HashMap<>();
		paramMap.put("user_id", user_id);
		paramMap.putAll(TimerUtil.getStartandEndTime5(isToday));
		List<Jihes_qf_mqueue> mQueues = qfzs_user_Servicve.getValueMqueue(paramMap);
		int end_time = (int)paramMap.get("end_time");
		paramMap.put("end_time",end_time+7200);//超过24点的自动业收入
		List<Jihes_qf_aqueue> aQueues = qfzs_user_Servicve.getValueAqueue(paramMap);
		
		//获取自动列表删除的队列
		List<String> deleteItemid_A = qfzs_user_Servicve.getDeleteAqueue(paramMap);
//		if(aQueues ==null || aQueues.size()==0) {
//			responseWriteInfo(200, "未获取到默认自动队列", data, response);
//			return;
//		}
		JSONArray sdfs = new JSONArray();//手动发送
		JSONArray zdlb = new JSONArray();//自动列表
		//选中小时删除位
		List<Integer> expires = new ArrayList<Integer>();
		JSONObject sd = new JSONObject();//手动
		List<Integer> sdList = new ArrayList<Integer>();
		for(int i=0;i<6;i++) {
			sdList.add(i);
		}
		
		for(Jihes_qf_time expireTime : expireTimes) {
			expires.add(expireTime.getNumber());
			sd = new JSONObject();//手动
			sd.put("expireTime", expireTime.getTime()+"");//小时
			sd.put("number", expireTime.getNumber()+"");//段位
			sd.put("type", "2");//0自动 1手动添加 2 删除位
			sdfs.add(sd);
			sdList.remove(expireTime.getNumber());
		}
		for(Jihes_qf_mqueue m : mQueues) {
			if(String.valueOf(m.getExpireTime()).equals(selectHour) //当前小时有手动添加
					&& !expires.contains(m.getNumber())) {//并且时段未失效
				sd = new JSONObject();//手动
				sd.put("expireTime", m.getExpireTime()+"");//小时
				sd.put("number", m.getNumber()+"");//段位
				sd.put("type", "1");//0自动 1手动添加 2 删除位
				sd.put("item_id", m.getItemId());
				sd.put("name", m.getName());
				sd.put("price", m.getPrice()+"");//市场价格
				sd.put("shop_coupon_price", m.getShopCouponPrice()+"");//券价格 
				sd.put("realPrice", calRealPrice(m.getPrice(), m.getShopCouponPrice())+"");//实际价格
				sd.put("shareRate", m.getShareRate().setScale(BigDecimal.ROUND_HALF_UP, 2)+"");//获取分享指数
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				sd.put("sendTime", sdf.format(new Date(Long.valueOf(m.getSendTime())*1000)));//发送时间
				//sd.put("day", getToday(Long.valueOf(m.getSendTime())*1000));//今日/明日
				if(isToday) {
					sd.put("day", "今日");//今日/明日
				}else {
					sd.put("day", "明日");//今日/明日
				}
				sd.put("saleAmount", m.getSalesAmount()+"");//销量
				sd.put("cover", m.getCover());//图片
				sd.put("remark", m.getRemark());//推荐语
				sd.put("manual", "true");
				sd.put("origin", m.getOrigin()== null ? "0" :m.getOrigin()+"");
				if(isToday) {
					if(m.getSendTime()>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
						sd.put("sendStatus", "未发送");
					}else {
						sd.put("sendStatus", "已发送");
					}
				}else {
					sd.put("sendStatus", "未发送");
				}
				
				sd.put("send_Cover", m.getSendCover());
				sdfs.add(sd);
				sdList.remove(m.getNumber());
			}
		}
		
		for(int i=0;i<sdList.size();i++) {
			sd = new JSONObject();//手动
			sd.put("expireTime", selectHour+"");//小时
			sd.put("number", sdList.get(i)+"");//段位
			sd.put("type", "0");//0自动 1手动添加 2 删除位
			sdfs.add(sd);
		}
		data.put("sdfs", sdfs);//添加手动发送列表
		
		
		//所有的无效时间，小时
		List<Jihes_qf_time> allExpireTimes = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTimes3(user_id);
		
		//自动列表及已发送的 手动添加队列
		List<Jihes_qf_mqueue> mQueues2 =  new ArrayList<Jihes_qf_mqueue>();
		for(Jihes_qf_mqueue m : mQueues) {
			if(m.getStatus() ==0 || m.getStatus()==4) {
				mQueues2.add(m);
			}
		}
		
		
		int autoCount = 0;//记录aMqueue队列的序号，使用到了谁
		Jihes_qf_aqueue a = new Jihes_qf_aqueue();
		JSONObject zd = new JSONObject();
		//添加自动列表
		a:for(int i=5;i<24;i++) {//当日
			b:for(int j=0;j<=5;j++) {//6个时段
				c:for(Jihes_qf_time time:allExpireTimes) {
					if(time.getTime().equals(String.valueOf(i)) 
							&& time.getNumber() == j) {//若当前位删除，则跳出b层循环
						continue b;
					}
				}
				//当前没有删除,则判断是否有手动添加位
				d:for(Jihes_qf_mqueue m : mQueues) {
					if(m.getExpireTime() == i
							&& m.getNumber() == j) {//若当前位有 手动 添加的自动列表，则添加手动位,0自动列表 4已发送状态，若为1手动列表，则跳过
						if(m.getStatus()==1) {
							continue b;
						}
						zd = new JSONObject();//自动
						zd.put("expireTime", m.getExpireTime()+"");//小时
						zd.put("number", m.getNumber()+"");//段位
						zd.put("type", m.getType()+"");//0自动 1手动添加 2 删除位,3过期 4发送
						zd.put("item_id", m.getItemId());//商品id
						zd.put("name", m.getName());
						zd.put("price", m.getPrice()+"");//市场价格
						zd.put("shop_coupon_price", m.getShopCouponPrice()+"");//券价格 
						zd.put("realPrice", calRealPrice(m.getPrice(), m.getShopCouponPrice())+"");//实际价格
						zd.put("shareRate", m.getShareRate().setScale(BigDecimal.ROUND_HALF_UP, 2)+"");//获取分享指数
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						zd.put("sendTime", sdf.format(new Date(Long.valueOf(m.getSendTime())*1000)));//发送时间
						//zd.put("day", getToday(Long.valueOf(m.getSendTime())*1000));//今日/明日
						if(isToday) {
							zd.put("day", "今日");//今日/明日
						}else {
							zd.put("day", "明日");//今日/明日
						}
						zd.put("saleAmount", m.getSalesAmount()+"");//销量
						zd.put("cover", m.getCover());//图片
						zd.put("remark", m.getRemark());//推荐语
						zd.put("manual", "true");
						zd.put("origin", m.getOrigin()== null ? "0" :m.getOrigin()+"");
						if(isToday) {
							if(m.getSendTime()>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
								zd.put("sendStatus", "未发送");
							}else {
								zd.put("sendStatus", "已发送");
							}
						}else {
							zd.put("sendStatus", "未发送");
						}
					
						zd.put("send_Cover", m.getSendCover());
						//现在手动添加的直接去上面sdfs列表啦，这里直接无视，不会出现的，恩至少现在
						//zdlb.add(zd);
						continue b;
					}
				}
					
					if(aQueues.size()<=autoCount) {
						zd = new JSONObject();//自动
						zd.put("expireTime", i+"");//小时
						zd.put("number", j+"");//段位
						zd.put("type", "5");//0自动 1手动添加 2 删除位,3过期 4发送，5缺失
						zdlb.add(zd);
						continue b;
					}
					
					//手动位也没有，用自动位填充
					a = aQueues.get(autoCount);
					//判断是否有删除位，若有，则删除
					for(int u=0;u<300;u++) {//正常不会超过300吧
						if(deleteItemid_A.contains(a.getItemId())) {
							autoCount = autoCount + 1;
							if(aQueues.size()<=autoCount) {
								zd = new JSONObject();//自动
								zd.put("expireTime", i+"");//小时
								zd.put("number", j+"");//段位
								zd.put("type", "5");//0自动 1手动添加 2 删除位,3过期 4发送，5缺失
								zdlb.add(zd);
								continue b;
							}
							a = aQueues.get(autoCount);
						}else {
							autoCount = autoCount + 1;
							break;
						}
					}
					if(a.getItemId().equals("560753865953")) {
						System.err.println("");
					}
					zd = new JSONObject();//自动
					zd.put("expireTime", i+"");//小时
					zd.put("number", j+"");//段位
					zd.put("type", a.getType()+"");//0自动 1手动添加 2 删除位,3过期 4发送
					zd.put("item_id", a.getItemId());//商品id
					zd.put("name", a.getName());
					zd.put("price", a.getPrice()+"");//市场价格
					zd.put("shop_coupon_price", a.getShopCouponPrice()+"");//券价格 
					zd.put("realPrice", calRealPrice(a.getPrice(), a.getShopCouponPrice())+"");//实际价格
					zd.put("shareRate", calculate_rate(a.getMaxRetio(), calRealPrice(a.getPrice(), a.getShopCouponPrice()), agent_user.getCommissionPercent())+"");//获取分享指数
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					zd.put("sendTime", sdf.format(new Date(Long.valueOf(TimerUtil.getStartandEndTime4(i, j))*1000)));//发送时间 06:12
					if(isToday) {
						zd.put("day", "今日");//今日/明日
					}else {
						zd.put("day", "明日");//今日/明日
					}
					zd.put("saleAmount", a.getSalesAmount()+"");//销量
					zd.put("cover", a.getCover());//图片
					zd.put("remark", a.getRemark());//推荐语
					zd.put("manual", "false");
					zd.put("origin", a.getOrigin()== null ? "0" :a.getOrigin()+"");
					if(isToday) {
						if(TimerUtil.getStartandEndTime42(i, j)+60>=Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000))){
							zd.put("sendStatus", "未发送");
						}else {
							zd.put("sendStatus", "已发送");
						}
					}else {
						zd.put("sendStatus", "未发送");
					}
					
					zd.put("send_Cover", a.getSendCover());
					zdlb.add(zd);
					continue b;
			}
		}
		boolean isContinue = true;
		if(aQueues ==null ||aQueues.size()==0) {
			isContinue = false;
		}
		
		while(isContinue) {
			if(aQueues.size() == autoCount) {
				break;
			}
			//手动位也没有，用自动位填充
			a = aQueues.get(autoCount);
			//判断是否有删除位，若有，则删除
			for(int u=0;u<300;u++) {//正常不会超过300吧
				
				
				if(deleteItemid_A.contains(a.getItemId())) {
					autoCount = autoCount + 1;
					if(aQueues.size()<=autoCount) {
						isContinue =false;
						break;
					}
					a = aQueues.get(autoCount);
				}else {
					autoCount = autoCount + 1;
					break;
				}
			}
			if(!isContinue) {
				break;
			}
			zd = new JSONObject();//自动
			zd.put("expireTime", "0");//小时
			zd.put("number", "0");//段位
			zd.put("type", a.getType().toString());//0自动 1手动添加 2 删除位,3过期 4发送
			zd.put("item_id", a.getItemId());//商品id
			zd.put("name", a.getName());
			zd.put("price", a.getPrice().toString());//市场价格
			zd.put("shop_coupon_price", a.getShopCouponPrice()+"");//券价格 
			zd.put("realPrice", calRealPrice(a.getPrice(), a.getShopCouponPrice()).toString());//实际价格
			zd.put("shareRate", calculate_rate(a.getMaxRetio(), calRealPrice(a.getPrice(), a.getShopCouponPrice()), agent_user.getCommissionPercent()).toString());//获取分享指数
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			zd.put("sendTime", "");//发送时间 06:12
			if(isToday) {
				zd.put("day", "今日");//今日/明日
			}else {
				zd.put("day", "明日");//今日/明日
			}
		
			zd.put("saleAmount", a.getSalesAmount());//销量
			zd.put("cover", a.getCover());//图片
			zd.put("remark", a.getRemark());//推荐语
			zd.put("manual", "false");
			zd.put("origin", a.getOrigin()== null ? "0" :a.getOrigin().toString());
			zd.put("sendStatus", "未发送");
			zd.put("send_Cover", a.getSendCover());
			zdlb.add(zd);
			
			if(aQueues.size()<=autoCount) {
				isContinue = false;
				break;
			}
		}
		
		data.put("zdlb", zdlb);
		responseWriteInfo(200, "", data,response);
		return data;
	
	}
	
	
	@RequestMapping("/showTimeInfo")
	public void showTimeInfo(HttpServletRequest request,HttpServletResponse response){
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		
		JSONObject data= new JSONObject();
	
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		
	
		
	
		
		//获取当前用户无效时间
		List<Jihes_qf_time> times = (List<Jihes_qf_time>) qfzs_user_Servicve.getNoUseTimes4(user_id);
		Map<String,Object> sendMap = new HashMap<String,Object>();
		Map<String,Object> timerMap = TimerUtil.getStartandEndTime6();
		sendMap.putAll(timerMap);
		sendMap.put("user_id", user_id);
		// 查询当前未发送的自动发送队列（手动添加的) 今日的
		List<Jihes_qf_mqueue> mQueues = (List<Jihes_qf_mqueue>) qfzs_user_Servicve.getMqueueByExpireTimes(sendMap);
		
		timerMap = TimerUtil.getStartandEndTime7();
		sendMap.putAll(timerMap);
		sendMap.put("user_id", user_id);
		// 查询当前未发送的自动发送队列（手动添加的) 次日的
		List<Jihes_qf_mqueue> mQueues2 = (List<Jihes_qf_mqueue>) qfzs_user_Servicve.getMqueueByExpireTimes(sendMap);
		
		
		List<Map<Integer,Integer>> lists = new ArrayList<Map<Integer,Integer>>();
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		JSONArray jar = new JSONArray();
		JSONObject jb = new JSONObject();
		int zdtj = 6;//最大添加
		int yjtj = 0;//已经添加
		Calendar cal =  Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		for(int i=cal.get(Calendar.HOUR_OF_DAY);i<=23;i++) {
			jb  = new JSONObject();
			zdtj = 6;//最大添加
			yjtj = 0;//已经添加
			if(i==18) {
				System.err.println("aa");
			}
			if(i==cal.get(Calendar.HOUR_OF_DAY)) {
				int muinute = cal.get(Calendar.MINUTE);
				if(muinute<10) {
					zdtj = 5;
				}else {
					zdtj = zdtj - Integer.valueOf(String.valueOf(muinute).substring(1)) - 1;
				}
			}
			
			
			for(Jihes_qf_time time : times) {
				if(time.getTime().equals(String.valueOf(i))) {
					zdtj = zdtj-time.getCreateTime();//这个createTime实际上是关闭的位置
					break;
				}
			
			}
			for(Jihes_qf_mqueue m : mQueues) {
				if(m.getExpireTime() == i) {
					yjtj = yjtj + m.getCreateTime();//这个createTime实际上是已添加数量
					break;
				}
				
			}
			
			if(zdtj == yjtj) {
				continue;
			}
			jb.put("expire_time", String.valueOf(i));
			jb.put("zdtj", String.valueOf(zdtj));
			jb.put("yjtj", String.valueOf(yjtj));
			jb.put("day", "今日");//今日
			jar.add(jb);
		}
		
		for(int i=5;i<=cal.get(Calendar.HOUR_OF_DAY)-1;i++) {
			jb  = new JSONObject();
			zdtj = 6;//最大添加
			yjtj = 0;//已经添加
			if(i==18) {
				System.err.println("aa");
			}
			for(Jihes_qf_time time : times) {
				if(time.getTime().equals(String.valueOf(i))) {
					zdtj = zdtj-time.getCreateTime();//这个createTime实际上是关闭的位置
					break;
				}
			
			}
			for(Jihes_qf_mqueue m : mQueues2) {
				if(m.getExpireTime() == i) {
					yjtj = yjtj + m.getCreateTime();//这个createTime实际上是已添加数量
					break;
				}
				
			}
			
			if(zdtj == yjtj) {
				continue;
			}
			jb.put("expire_time", String.valueOf(i));
			jb.put("zdtj", String.valueOf(zdtj));
			jb.put("yjtj", String.valueOf(yjtj));
			jb.put("day", "明日");//今日
			jar.add(jb);
		}
		
		data.put("info", jar);
		data.put("user_id", user_id);
		responseWriteInfo(200, "获取成功", data, response);
		
		
		
		
		
	}
	
	/**
	 * 修改推荐语
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateRemark")
	public void updateRemark(HttpServletRequest request,HttpServletResponse response){
		int user_id ;
		if(!test) {
			//根绝cookie获取request方法
			user_id = CookieUtil.getUserId(request, response);
			if(user_id == -1 || user_id<0) {
				responseWriteInfo(401, "未登录", null, response);
				return;
			}
		}else {
			user_id = testUserId;
		}
		
		JSONObject data= new JSONObject();
	
		Jihes_user user = jihes_userService.selectByPrimaryKey(user_id);
		if(user ==null ) {
//			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		Agent_user agent_user = jihes_userService.selectAgentUserById(user_id);
		if(agent_user ==null ) {
			responseWriteInfo(401, "用户信息异常", null, response);
			return;
		}
		
		String item_id = request.getParameter("item_id") == null ? "" : request.getParameter("item_id");
//		String goods_id = request.getParameter("goods_id") == null ? "" : request.getParameter("goods_id");
		if(item_id ==null || item_id.equals("")  ) {
			responseWriteInfo(401, "参数有误", null, response);
			return;
		}
		

		
	
		String expire_time = request.getParameter("expire_time") == null ? "" : request.getParameter("expire_time");
//		String goods_id = request.getParameter("goods_id") == null ? "" : request.getParameter("goods_id");
		if(expire_time ==null || expire_time.equals("")  ) {
			responseWriteInfo(401, "参数有误", null, response);
			return;
		}
		String remark = request.getParameter("remark") == null ? "" : request.getParameter("remark");
//		String goods_id = request.getParameter("goods_id") == null ? "" : request.getParameter("goods_id");
		if(remark ==null || remark.equals("")  ) {
			responseWriteInfo(401, "参数有误", null, response);
			return;
		}
		
		Jihes_goods goods = qfzs_user_Servicve.selectGoodById(item_id);
		if(goods == null ) {
			responseWriteInfo(401, "未找到商品信息", null, response);
			return;
		}
		
		Map<String,Object> map = TimerUtil.getStartandEndTime3("24");
		map.put("item_id", item_id);
		map.put("user_id", user_id);
		Jihes_qf_mqueue mQueue = (Jihes_qf_mqueue) qfzs_user_Servicve.getMqueueInfo(map);
		if(mQueue == null) {
			responseWriteInfo(600, "未找到手动添加商品信息", null, response);
			return;
		}
		mQueue.setRemark(remark);
		mQueue.setUpdateTime(Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000)));
		int count = qfzs_user_Servicve.updateDeleteMqueue(mQueue,false);
		if(count<1) {
			data.put("update", "false");
			responseWriteInfo(600, "保存推荐语失败", data, response);
			return;
		}
		
		data.put("update", "true");
		responseWriteInfo(200, "保存推荐语成功", data, response);
		
	}
	
}
