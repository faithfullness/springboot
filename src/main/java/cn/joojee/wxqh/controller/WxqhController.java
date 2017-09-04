package cn.joojee.wxqh.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joojee.usercenter.client.JoojeeUserCenterResult;
import com.joojee.usercerter.oauth.service.ServiceProvider;

import cn.joojee.wxqh.model.ResultBody;
import cn.joojee.wxqh.model.WxqhConfig;
import cn.joojee.wxqh.service.UserService;
import cn.joojee.wxqh.service.WxqhService;
import cn.joojee.wxqh.utils.HttpsRequest;
import cn.joojee.wxqh.utils.JoojeeCommonCode;
import cn.joojee.wxqh.utils.JoojeeWebCommon;
import cn.joojee.wxqh.utils.UserOperRequest;

@RestController
@RequestMapping("/api")
public class WxqhController {
	private static final Logger logger = Logger.getLogger(WxqhController.class);

	@Autowired
	private ServiceProvider joojeeOauthService;

	@Autowired
	private UserService userService;

	@Autowired
	private WxqhService wxqhService;

	/** 大厅基本信息接口 */
	private static final String GET_DTJBXX_URL = "https://mobile.hb-n-tax.gov.cn:55583/api/l/taxpayer/wxqh/get/dtjbxx.json";

	/** 分组信息接口 */
	private static final String GET_FZXX_URL = "https://mobile.hb-n-tax.gov.cn:55583/api/l/taxpayer/wxqh/get/fzxxByDtdm.json";

	/** 判断是否实名 */
	private static final String VERIFY_SMCJ_URL = "https://mobile.hb-n-tax.gov.cn:55583/api/l/extend/swr/get/isSmcjBySfzjhmPhone.json";

	/** 获取大厅分组信息及当前叫号信息 */
	private static final String GET_JHXX_URL = "https://mobile.hb-n-tax.gov.cn:55583/api/l/taxpayer/wxqh/get/jhFzxxList.json";

	/** 大厅取号 */
	private static final String CALL_DTQH_URL = "https://mobile.hb-n-tax.gov.cn:55583/api/l/taxpayer/wxqh/call/dtqh.json";

	/** 发送微信模板消息 */
	private static final String SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send";

	/**
	 * 获取大厅基本信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-12 15:22:08
	 * @return
	 */
	@RequestMapping("/get/dtjbxx")
	public String getDtjbxx() {
		return HttpsRequest.sendHttpsRequestByGet(GET_DTJBXX_URL);
	}

	/**
	 * 获取大厅分组信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-12 15:22:22
	 * @param dtdm
	 * @return
	 */
	@RequestMapping("/get/fzxxByDtdm")
	public String getFzxxByDtdm(String dtdm) {
		if (StringUtils.isEmpty(dtdm)) {
			return new ResultBody(JoojeeCommonCode.MISSING_PARAMS).toString();
		}

		return HttpsRequest.sendHttpsRequestByGet(GET_FZXX_URL + "?dtdm=" + dtdm);
	}

	/**
	 * 校验实名采集状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-20 10:54:33
	 * @param name
	 * @param sfzjhm
	 * @param accessToken
	 * @return isSmcj 0代表已实名采集 1代表未实名采集 2代表手机号已实名采集但是身份证信息输入不正确 3代表已实名采集但不是洪山管辖户
	 * 
	 */
	@RequestMapping("/l/get/isSmcjBySfzjhmPhone")
	public ResultBody verifySmcj(String name, String sfzjhm, String accessToken) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(sfzjhm) || StringUtils.isEmpty(accessToken)) {
			return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
		}

		JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
		logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
		if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
			return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
		}
		String phone = (String) result.getObject();

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("sfzjhm", sfzjhm);
		map.put("accessToken", accessToken);
		map.put("oauth_consumer_key", UserOperRequest.oauth_consumer_key);

		JSONObject jsonObject = JSON.parseObject(HttpsRequest.sendHttpsRequestByPost(VERIFY_SMCJ_URL, map));

		// 记录微信取号用户身份验证已通过
		try {
			if ("0".equals(jsonObject.getJSONObject("resultData").getString("isSmcj"))) {
				wxqhService.setWxUserAuthStatus(phone);
			}
		} catch (Exception e) {
		}

		return new ResultBody(jsonObject.getJSONObject("resultData"));
	}

	/**
	 * 获取大厅分组信息及当前叫号信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-20 11:58:47
	 * @param dtdm
	 * @param accessToken
	 * @return
	 */
	@RequestMapping("/l/get/jhFzxxList")
	public ResultBody getJhxx(String dtdm, String accessToken) {
		System.out.println(dtdm + accessToken);
		if (StringUtils.isEmpty(dtdm) || StringUtils.isEmpty(accessToken)) {
			return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
		}

		JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
		logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
		if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
			return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
		}
		String phone = (String) result.getObject();

		Map<String, String> map = new HashMap<String, String>();
		map.put("dtdm", dtdm);
		map.put("accessToken", accessToken);
		map.put("oauth_consumer_key", UserOperRequest.oauth_consumer_key);

		JSONObject jsonObject = JSON.parseObject(HttpsRequest.sendHttpsRequestByPost(GET_JHXX_URL, map));
		JSONObject resultData = jsonObject.getJSONObject("resultData");

		// 判断本月权限
		if (!wxqhService.verifyUserPermissionsOnMonth(phone)) {
			resultData.put("byxz", true);
		}

		JSONArray list = resultData.getJSONArray("lists");
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject2 = list.getJSONObject(i);

			// 判断是否在可取号时间内、是否有余号、是否办理
			WxqhConfig takeNumInfo = wxqhService.getTakeNumInfo(jsonObject2.getString("fzDm"));
			if (takeNumInfo != null) {
				jsonObject2.put("kqhsj", takeNumInfo.isKqhsj());
				jsonObject2.put("ykqh", takeNumInfo.isYkqh());
			}

			// 查询云端订单状态
			WxqhConfig qh = new WxqhConfig();
			qh.setFzdm(jsonObject2.getString("fzDm"));
			qh.setPhone(phone);
			WxqhConfig wxqhInfo = wxqhService.getHandleStatus(qh);
			if (wxqhInfo != null) {
				jsonObject2.put("blbz", wxqhInfo.getBlbz() == 1 ? true : false);

				Date qh_time = wxqhInfo.getQh_time();
				if (qh_time != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					jsonObject2.put("qhsj", dateFormat.format(qh_time));
				}
			}
		}

		return new ResultBody(resultData);
	}

	/**
	 * 大厅取号
	 * 
	 * @author cheng_chen
	 * @date 2017-06-20 14:23:54
	 * @param dtdm
	 * @param accessToken
	 * @return
	 */
	@RequestMapping("/l/call/dtqh")
	public ResultBody callDtqh(String dtdm, String fzdm, String accessToken) {
		if (StringUtils.isEmpty(dtdm) || StringUtils.isEmpty(fzdm)) {
			return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
		}

		JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
		logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
		if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
			return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
		}
		String phone = (String) result.getObject();

		JSONObject resultData = new JSONObject();
		// 判断本月限制
		if (!wxqhService.verifyUserPermissionsOnMonth(phone)) {
			resultData.put("byxz", true);
			return new ResultBody(resultData);
		}

		// 判断是否在可取号时间内和余号
		WxqhConfig takeNumInfo = wxqhService.getTakeNumInfo(fzdm);
		if (takeNumInfo != null) {
			resultData.put("kqhsj", takeNumInfo.isKqhsj());
			resultData.put("ykqh", takeNumInfo.isYkqh());
		}
		System.out.println(resultData.toJSONString());

		if (resultData.getBoolean("kqhsj") && resultData.getBoolean("ykqh")) {
			// 从浪潮取号
			Map<String, String> map = new HashMap<String, String>();
			map.put("dtdm", dtdm);
			map.put("fzdm", fzdm);
			map.put("accessToken", accessToken);
			map.put("oauth_consumer_key", UserOperRequest.oauth_consumer_key);

			JSONObject jsonObject = JSON.parseObject(HttpsRequest.sendHttpsRequestByPost(CALL_DTQH_URL, map));
			JSONObject resultDataJson = jsonObject.getJSONObject("resultData");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			resultDataJson.put("qhsj", dateFormat.format(date));

			// 记录取号信息
			WxqhConfig qh = new WxqhConfig();
			qh.setDtdm(dtdm);
			qh.setFzdm(fzdm);
			qh.setPhone(phone);
			qh.setQh_time(date);
			wxqhService.setTakeNumInfo(qh);
			return new ResultBody(resultDataJson);
		} else {
			return new ResultBody(resultData);
		}
	}

	/**
	 * 确认办理业务
	 * 
	 * @author cheng_chen
	 * @date 2017-06-26 10:16:38
	 * @param
	 * @param
	 * @param accessToken
	 * @return
	 */
	@RequestMapping("/l/confirm/Handle")
	public ResultBody confirmHandle(String code, WxqhConfig wxqhConfig, String accessToken) {
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(wxqhConfig.getDtdm())
				|| StringUtils.isEmpty(wxqhConfig.getFzdm()) || StringUtils.isEmpty(accessToken)) {
			return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
		}

		if (!"6760".equals(code)) {
			return new ResultBody(JoojeeCommonCode.PassportCode.ERROR_CODE);
		}

		JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
		logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
		if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
			return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
		}
		String phone = (String) result.getObject();
		wxqhConfig.setPhone(phone);

		if (wxqhService.confirmHandle(wxqhConfig)) {
			return new ResultBody(JoojeeCommonCode.SUCCESS);
		}
		return new ResultBody(JoojeeCommonCode.FAIL);
	}

	/**
	 * 每天6点钟处理订单状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-29 12:07:30
	 */
	@Scheduled(cron = "0 0 18 * * ?") // 每天24时
	private void updateNotHandleStatus() {
		wxqhService.updateNotHandleStatus();
	}

	/**
	 * 推送微信模板消息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-29 18:01:17
	 * @param data
	 * @param openid
	 * @return
	 */
	@RequestMapping("/send/message")
	private ResultBody sendMessage(String data, String openid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", data);
		map.put("touser", openid);
		map.put("accessToken", userService.getWxAccessToken());
		map.put("template_id", "1");

		String result = HttpsRequest.sendHttpsRequestByPost(SEND_MESSAGE, map);
		System.out.println(result);
		return new ResultBody(JoojeeCommonCode.SUCCESS);
	}

	public static void main(String[] args) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		System.out.println(dateFormat.format(date));

	}
}
