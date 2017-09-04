package cn.joojee.wxqh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JoojeeWebCommon {

	/** 定义税企通项目的环信访问操作地址 */
	// public static final String SQT_EASEMOB_URL =
	// "https://a1.easemob.com/joojee2014/sqtonline/";

	/** 定义环信访问操作访问host */
	public static final String SQT_EASEMOB_HOST = "https://a1.easemob.com/";

	/** 定义税企通项目的环信管理员默认账号 */
	public static final String SQT_EASEMOB_ADMIN_USERNAME = "joojee";

	/** 定义税企通项目的环信访问管理员默认密码 */
	public static final String SQT_EASEMOB_ADMIN_PASSWORD = "jjy87338828";

	/** 定义税企通项目的环信所有用户的统一登录密码 */
	public static final String SQT_EASEMOB_USER_DEFAULT_PASSWORD = "jjy87338828";

	/** 定义税企通项目的手机验证码的长度为六位 */
	private static final Integer SQT_PHONE_AUTH_CODE_LENGTH = 6;

	/** 定义默认的每个手机号每天最大的接收短信次数 */
	public static final int SQT_MAX_SEND_AUTH_CODE_COUNT = 5;

	/** 定义默认的每个手机号对应验证码的有限时间 */
	public static final Integer SQT_AUTH_CODE_EXPIRE_TIME = 10;

	/** 短信验证码发送成功的标示 */
	public static final String SMS_SEND_SUCCESS_TEMP = "1";

	/** 定义默认的jjy用户的jid前缀 */
	public static final String DEFAULST_JOOJEE_USER_PREFIX = "jjy_";

	/** 定义税企通用户默认的头像地址 */
	public static final String DEFAULT_JOOJEE_USER_IMAGE = "http://group.store.qq.com/qun/V14d2Gog2K2A5A/V2tBCuLN8LL4VJo6oMk/800?w5=964&h5=640&rf=viewer_421";

	/** 定义默认的每页显示记录数量15条 */
	public static final Integer NUM = 15;

	/** 定义默认的当前页数为第1页 */
	public static final Integer PAGE = 1;

	/** 定义默认的服务厅上下班时间 */
	private static final String WORKTIME1 = "08:30:00";
	private static final String WORKTIME2 = "12:00:00";
	private static final String WORKTIME3 = "14:30:00";
	private static final String WORKTIME4 = "17:30:00";

	/** 定义每天默认生成订单号的起始值 */
	public static final String ORDER_NUM_START = "00001";
	
	private static Logger logger = LoggerFactory.getLogger(JoojeeWebCommon.class);

	/**
	 * 根据状态码获取对应的提示信息，并封装成json数据返回
	 * 
	 * @param code
	 *            状态码
	 * @param response
	 *            http请求的response对象
	 */
	public static void outPutErrorCodeJson(String code, HttpServletResponse response) {

		JSONObject jsonResult = new JSONObject();

		if (!StringUtils.isEmpty(code)) {
			jsonResult.put("code", code);
			jsonResult.put("info", JoojeeCommonCode.getMessage(code));
		}

		outputJSONResult(jsonResult.toJSONString(), response);
	}

	public static String outPutErrorCodeJson(String code) {

		JSONObject jsonResult = new JSONObject();

		if (!StringUtils.isEmpty(code)) {
			jsonResult.put("code", code);
			jsonResult.put("info", JoojeeCommonCode.getMessage(code));
		}

		return jsonResult.toJSONString();
	}

	/**
	 * 将消息字符串返回给调用方
	 * 
	 * @param code
	 *            消息字符串
	 * @param response
	 *            http请求的response对象
	 */
	public static void outputJSONResult(String result, HttpServletResponse response) {

		try {
//			response.setHeader("ContentType", "text/plain");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();

			if (!StringUtils.isEmpty(result)) {
				pw.write(result);
			} else {
				pw.write(returnJSONResult(null));
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 封装json数据返回给调用方
	 * 
	 * @param result
	 *            json对象
	 * @param response
	 *            response对象
	 */
	public static void outputJSONObjectSuccess(JSONObject result, HttpServletResponse response) {

		JSONObject resultJson = new JSONObject();

		resultJson.put("code", JoojeeCommonCode.SUCCESS);
		resultJson.put("info", JoojeeCommonCode.SUCCESS_VALUE);

		if (null != result) {
			resultJson.put("resultData", result);
		}

		// 调用传递数据到客户端的方法
		outputJSONResult(resultJson.toJSONString(), response);
	}

	/**
	 * 为ajax请求封装json数据
	 * 
	 * @param result
	 * @param response
	 */
	public static void outputJSONArraySuccess(JSONArray result, HttpServletResponse response) {

		JSONObject resultJson = new JSONObject();

		resultJson.put("code", JoojeeCommonCode.SUCCESS);
		resultJson.put("info", JoojeeCommonCode.SUCCESS_VALUE);

		if (null != result) {
			resultJson.put("resultData", result);
		}

		// 调用传递数据到客户端的方法
		outputJSONResult(resultJson.toJSONString(), response);
	}

	/**
	 * 根据传递的map集合封装json格式的数据并返回，如果map为空，则返回错误状态码405 所有的json数据外面包含一层result标签
	 * 
	 * @param resultParamMap
	 * @param response
	 */
	public static String returnJSONResult(HashMap<String, String> resultParamMap) {

		JSONObject jsonData = new JSONObject();
		if (!CollectionUtils.isEmpty(resultParamMap)) {
			for (Entry<String, String> entry : resultParamMap.entrySet()) {
				jsonData.put(entry.getKey(), entry.getValue());
			}
		} else {
			jsonData.put("code", JoojeeCommonCode.SERVER_ERROR);
			jsonData.put("info", JoojeeCommonCode.SERVER_ERROR_VALUE);
		}
		return jsonData.toJSONString();
	}

	/**
	 * @Description 定义服务厅返回的json数据格式
	 * @author zhan_xiaolong
	 * @date 2014-8-20 上午09:57:45
	 * @param result
	 * @param response
	 */
	public static void outputFWTJSONObjectSuccess(JSONObject result, HttpServletResponse response) {

		if (null == result) {
			result = new JSONObject();
			result.put("resultData", new JSONObject());
		}
		result.put("code", JoojeeCommonCode.SUCCESS);
		result.put("info", JoojeeCommonCode.SUCCESS_VALUE);

		// 调用传递数据到客户端的方法
		outputJSONResult(result.toJSONString(), response);
	}

	/**
	 * 格式化手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static String formatPhoneNumber(String phone) {
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		String numStr = getNumStr(phone);
		if (numStr.length() < 11) {
			return numStr;
		} else if (numStr.length() == 11) {
			if (numStr.charAt(0) == '1' && (numStr.charAt(1) == '3' || numStr.charAt(1) == '4'
					|| numStr.charAt(1) == '5' || numStr.charAt(1) == '7' || numStr.charAt(1) == '8')) {
				return "+86" + numStr;
			} else {
				return numStr;
			}
		} else if (numStr.length() == 13) {
			if (numStr.charAt(0) == '8' && numStr.charAt(1) == '6') {
				if (numStr.charAt(2) == '1' && (numStr.charAt(3) == '3' || numStr.charAt(3) == '4'
						|| numStr.charAt(3) == '5' || numStr.charAt(3) == '7' || numStr.charAt(1) == '8')) {
					return '+' + numStr;
				}
			}
			return numStr;
		} else if (numStr.length() == 16) {
			String pre = numStr.substring(0, 5);
			if (pre.equalsIgnoreCase("12593") || pre.equalsIgnoreCase("12520") || pre.equalsIgnoreCase("10193")
					|| pre.equalsIgnoreCase("17900") || pre.equalsIgnoreCase("17911")
					|| pre.equalsIgnoreCase("17951")) {
				return "+86" + numStr.substring(5);
			}
			return numStr;
		} else if (numStr.length() == 17) {
			String pre = numStr.substring(0, 6);
			if (pre.equalsIgnoreCase("125930") || pre.equalsIgnoreCase("125200") || pre.equalsIgnoreCase("101930")
					|| pre.equalsIgnoreCase("179000") || pre.equalsIgnoreCase("179111")
					|| pre.equalsIgnoreCase("179511")) {
				return "+86" + numStr.substring(6);
			}
			return numStr;
		}
		return phone;
	}

	/**
	 * 获取字符,只获取字符中所有的数字
	 * 
	 * @param input
	 * @return
	 */
	private static String getNumStr(String input) {
		String result = "";
		for (int pos = 0; pos < input.length(); ++pos) {
			char ch = input.charAt(pos);
			if (Character.isDigit(ch)) {
				result += ch;
			}
		}
		return result;
	}

	/**
	 * 根据传入的时间戳获取当前的日期字符串(如：2013-11－21)，如果传入时间为空，默认返回日期为当天
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateByTime(String time) {

		Date dt = new Date();
		if (!StringUtils.isEmpty(time) && time.length() == 13) {
			dt = new Date(Long.parseLong(time));
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		return df.format(dt);
	}

	/**
	 * 获取传递时间对应的时间戳,时间连接符必须是-(例如2011-12-27 ，返回时间戳),数据 必须规范，否则抛异常
	 * 
	 * @param time
	 * @return
	 */
	public static Long getTimestamp(String time) {

		try {
			// 构建calendar对象
			Calendar cal = Calendar.getInstance();
			// 参数非空判断
			if (!StringUtils.isEmpty(time) && time.indexOf("-") > -1) {
				// 去除前后空格并获取年月日的值
				// time = time.trim();
				String timeStr = time.trim();
				int year = Integer.parseInt(timeStr.substring(0, timeStr.indexOf("-")));
				timeStr = timeStr.substring(timeStr.indexOf("-") + 1);
				int month = Integer.parseInt(timeStr.substring(0, timeStr.indexOf("-")));
				timeStr = timeStr.substring(timeStr.indexOf("-") + 1);
				int date = 0;
				if (timeStr.length() > 2) {
					date = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(" ")));
					timeStr = timeStr.substring(timeStr.indexOf(" ") + 1);
				} else {
					date = Integer.parseInt(timeStr);
					timeStr = "";
				}
				cal.set(year, month - 1, date);
				// 判断是否有时分秒的值
				if (timeStr.length() >= 2) {
					int hour = Integer.parseInt(timeStr.substring(0, 2));
					cal.set(Calendar.HOUR_OF_DAY, hour);
				} else {
					cal.set(Calendar.HOUR_OF_DAY, 0);
				}
				if (timeStr.length() >= 5) {

					int minite = Integer.parseInt(timeStr.substring(3, 5));
					cal.set(Calendar.MINUTE, minite);
				} else {
					cal.set(Calendar.MINUTE, 0);
				}
				if (timeStr.length() >= 7) {
					int seconds = Integer.parseInt(timeStr.substring(6));
					cal.set(Calendar.SECOND, seconds);
				} else {
					cal.set(Calendar.SECOND, 0);
				}
				return cal.getTimeInMillis();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	/**
	 * 根据手机随机生成六位验证码的方法
	 * 
	 * @param phone
	 * @return
	 */
	public static String createAuthCodeForMobile(String phone) {

		// 参数非空判断
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		// 随机获取一个六位的验证码
		String authCode = "";
		for (int i = 0; i < SQT_PHONE_AUTH_CODE_LENGTH; i++) {
			authCode += (int) (Math.random() * 10);
		}
		logger.info("随机生成的六位验证码:" + authCode + "***********************");

		return authCode;
	}

	/**
	 * 随机生成6位数的企业关联口令
	 * 
	 * @return
	 */
	public static int createEnterpriseShibboleth() {

		// 随机获取一个六位的验证码
		String shibboleth = "";
		for (int i = 0; i < SQT_PHONE_AUTH_CODE_LENGTH; i++) {
			shibboleth += (int) (Math.random() * 10);
		}

		return Integer.parseInt(shibboleth);
	}

	/**
	 * 过滤掉所有的html标签
	 * 
	 * @param source
	 * @return
	 */
	public static String removeHtmlTag(String source) {

		if (StringUtils.isEmpty(source)) {
			return "";
		}
		Pattern pattern = Pattern.compile("<([^>]*)>");

		Matcher matcher = pattern.matcher(source);
		String string = matcher.replaceAll("");
		string = string.replaceAll("\r", "").trim();
		string = string.replaceAll("\n", "").trim();
		string = string.replaceAll("\t", "").trim();
		return string;
	}

	/**
	 * 判断当前时间是否在国税局上班时间
	 * 
	 * @author zhan_xiaolong
	 * @return
	 */
	public static boolean isWorkTime() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			// 格式化上下班时间
			Date date1 = sdf.parse(WORKTIME1);
			Date date2 = sdf.parse(WORKTIME2);
			Date date3 = sdf.parse(WORKTIME3);
			Date date4 = sdf.parse(WORKTIME4);
			long now = sdf.parse(sdf.format(new Date())).getTime();
			if ((date1.getTime() < now && date2.getTime() > now) // 在上午上班时间里面
					|| (date3.getTime() < now && date4.getTime() > now)) { // 在下午上班时间里面
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	/**
	 * 
	 * pdf返回到客户端
	 * 
	 * @param name
	 * @param response
	 * @param in
	 *            void
	 * @throws :
	 */
	public static void outputJSONResultForPdfDownload(String name, HttpServletResponse response, InputStream in,
			String type) {

		try {
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型

			if ("1".equals(type)) {
				response.setContentType("application/pdf");
			} else if ("2".equals(type)) {
				response.setContentType("image/png");
			}

			// response.setHeader("content-disposition", "attachment;filename="
			// + URLEncoder.encode(name,"UTF-8"));

			byte[] buffer = new byte[1024];
			int contentLength = 0;
			int len = 0;

			while ((len = in.read(buffer)) > 0) {
				contentLength += len;
				response.getOutputStream().write(buffer, 0, len);
			}
			response.setContentLength(contentLength);
		} catch (Exception e) {
			logger.info("文件流返回错误" + e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.info("流关闭出现异常：" + e.getMessage());
			}
		}

		// //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		// response.setContentType("application/pdf");
		// //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
		//// response.setHeader("Content-Disposition",
		// "attachment;fileName="+"a.pdf");
		// ServletOutputStream out;
		//
		// try {
		//
		// //3.通过response获取ServletOutputStream对象(out)
		// out = response.getOutputStream();
		//
		// int b = 0;
		// byte[] buffer = new byte[512];
		// while (b != -1){
		// b = in.read(buffer);
		// //4.写到输出流(out)中
		// out.write(buffer,0,b);
		// }
		// in.close();
		// out.close();
		// out.flush();
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
	
	public static long getDayMaxTime(){
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY)); 
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE)); 
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND)); 
		 
		return calendar.getTimeInMillis()/1000l;
	}
	
	public static String getDateTimeStrByExp(long timeInMillis,String dateformatExp){
		
		if(0 == timeInMillis){
			timeInMillis = System.currentTimeMillis();
		}
		
		if(StringUtils.isEmpty(dateformatExp)){
			dateformatExp = "yyyy-MM-dd";
		}
		
		Date dt = new Date(timeInMillis);
		DateFormat df = new SimpleDateFormat(dateformatExp);
		
		return df.format(dt);
	}

}
