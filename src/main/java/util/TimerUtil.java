package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;


public class TimerUtil {
	public static void main(String[] args) {
		getStartandEndTime4(7,1);
	}

	public static  Map<String,Object> getStartandEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String start = now+":00:00";
		String end = now+":59:59";
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			Date endTime = sdf2.parse(end);
			int start_time = (int) (startTime.getTime()/1000);
			int end_time = (int) (endTime.getTime()/1000);
			ret.put("start_time", start_time);
			ret.put("end_time", end_time);
			System.err.println(start_time+","+end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	
	public static  Map<String,Object> getStartandEndTime2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:m");
		SimpleDateFormat sdf5 = new SimpleDateFormat("m");
		String now = sdf.format(new Date());
		String start = now+":00:00";
		String end = now+":59:59";
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			Date endTime = sdf2.parse(end);
			int start_time = (int) (startTime.getTime()/1000);
			int end_time = (int) (endTime.getTime()/1000);
			ret.put("start_time", start_time);
			ret.put("end_time", end_time);
			System.err.println(start_time+","+end_time);
			
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 10);
			int expire_time = Integer.valueOf(sdf3.format(cal.getTime()));
			ret.put("expire_time", expire_time);
			
			String Hm = sdf4.format(cal.getTime());//10:3
			Hm = Hm+String.valueOf((int)(Math.random()*10))+":00";
			int sendTime = (int) (sdf2.parse(Hm).getTime()/1000);
			ret.put("sendTime", sendTime);//实际发送时间
			int order = Integer.valueOf(sdf5.format(cal.getTime()).substring(0,1));
			ret.put("number", order);//发送的序号
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	/**
	 * 传入小时，段位 ，随机生成发送时间
	 * @return
	 */
	public static  int getStartandEndTime4(int expireTime,int number) {
		if(expireTime ==10) {
			System.err.println(11);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:m");
		SimpleDateFormat sdf5 = new SimpleDateFormat("m");
		String now = sdf.format(new Date());
		int sendTime = 0;
		String start;
		if(expireTime<=9) {
			start = now + " 0"+String.valueOf(expireTime) + ":"+number+"0:00";
		}else {
			start = now + " "+String.valueOf(expireTime) + ":"+number+"0:00";
		}
		
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			
			String Hm = sdf4.format(cal.getTime());//10:3
			if(number != 0) {
				Hm = Hm.substring(0, Hm.length()-1);
			}
			Hm = Hm+String.valueOf((int)(Math.random()*10))+":00";
			sendTime = (int) (sdf2.parse(Hm).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sendTime;
		
	}
	
	
	
	/**
	 * 传入小时，段位 ，随机生成发送时间
	 * @return
	 */
	public static  int getStartandEndTime42(int expireTime,int number) {
		if(expireTime ==10) {
			System.err.println(11);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:m");
		SimpleDateFormat sdf5 = new SimpleDateFormat("m");
		String now = sdf.format(new Date());
		int sendTime = 0;
		String start;
		if(expireTime<=9) {
			start = now + " 0"+String.valueOf(expireTime) + ":"+number+"0:00";
		}else {
			start = now + " "+String.valueOf(expireTime) + ":"+number+"0:00";
		}
		
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime);
			
			String Hm = sdf4.format(cal.getTime());//10:3
			if(number != 0) {
				Hm = Hm.substring(0, Hm.length()-1);
			}
			Hm = Hm+String.valueOf(9)+":00";
			sendTime = (int) (sdf2.parse(Hm).getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sendTime;
		
	}
	
	public static void test() {
		for(int i=0;i<100;i++) {
			int random=(int)(Math.random()*10);
			System.err.println(random);
		}
	}
	
	/**
	 * 获取当日6点-次日hour点前一小时
	 * @param hour
	 * @return
	 */
	public static  Map<String,Object> getStartandEndTime3(String hour) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String now = sdf.format(new Date());
		String start = now+" 06:00:00";
		
		
		if(hour.length()==1) {
			hour = "0"+hour;
		}
		
		String start2 = now + " "+hour+":00:00";
		
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			
			Date startTime2 = sdf2.parse(start2);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startTime2);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			
			int start_time = (int) (startTime.getTime()/1000);
			int end_time = (int) (cal.getTime().getTime()/1000);
			ret.put("start_time", start_time);
			ret.put("end_time", end_time);
			System.err.println(start_time+","+end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	
	/**
	 * 获取当日6点-次日hour点前一小时
	 * today true 当日， false 明日
	 * @param hour
	 * @return
	 */
	public static  Map<String,Object> getStartandEndTime5(boolean today) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String now = sdf.format(new Date());
		String start = now+" 05:00:00";
		
		
		String ends  = now + " 24:00:00";
		
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			
			Date endsTime = sdf2.parse(ends);
			int start_time;
			int end_time;
			if(!today) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startTime);
				cal.add(Calendar.DAY_OF_YEAR, 1);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endsTime);
				cal2.add(Calendar.DAY_OF_YEAR, 1);
				start_time = (int) (cal.getTime().getTime()/1000);
				end_time = (int) (cal2.getTime().getTime()/1000);
			}else {
				start_time = (int) (startTime.getTime()/1000);
				end_time = (int) (endsTime.getTime()/1000);
			}
			ret.put("start_time", start_time);
			ret.put("end_time", end_time);
			System.err.println(start_time+","+end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	
	public static  Map<String,Object> getStartandEndTime6() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String start = now+" 00:00:00";
		String end = now+" 23:59:59";
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			Date endTime = sdf2.parse(end);
			int start_time = (int) (startTime.getTime()/1000);
			int end_time = (int) (endTime.getTime()/1000);
			ret.put("start_time", start_time);
			ret.put("end_time", end_time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
	
	
	public static  Map<String,Object> getStartandEndTime7() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		String start = now+" 00:00:00";
		String end = now+" 23:59:59";
		Map<String,Object> ret = new HashedMap<String,Object>();
		try {
			Date startTime = sdf2.parse(start);
			Date endTime = sdf2.parse(end);
			int start_time = (int) (startTime.getTime()/1000);
			int end_time = (int) (endTime.getTime()/1000);
			ret.put("start_time", start_time+86400+14400);//次日4点
			ret.put("end_time", end_time+86400);//次日24点
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
		
	}
}
