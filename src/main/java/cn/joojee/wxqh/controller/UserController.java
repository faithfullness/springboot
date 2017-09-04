package cn.joojee.wxqh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.joojee.wxqh.model.OpenidBean;
import cn.joojee.wxqh.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.joojee.wxqh.mapper.WxqhMapper;
import cn.joojee.wxqh.service.UserService;
import cn.joojee.wxqh.service.WxqhService;
import cn.joojee.wxqh.utils.HttpsRequest;
import cn.joojee.wxqh.utils.JoojeeCommonCode;
import cn.joojee.wxqh.utils.JoojeeWebCommon;
import cn.joojee.wxqh.utils.UserOperRequest;

/**
 * 关联微信用户体系
 * 
 * @author cheng_chen
 * @date 2017-06-07 10:43:53
 */
@Controller
@RequestMapping("/api")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);

	/** 获取验证码 */
	private static final String GET_AUTH_CODE = "https://sqt.joojee.cn/api/passport/get/authCode";

	/** 用户openid登录 */
	public static final String OPENID_LOGIN = "https://sqt.joojee.cn/api/passport/openidLogin";

	/** 用户authCode登录 */
	public static final String AUTHCODE_LOGIN = "https://sqt.joojee.cn/api/passport/authCodeLogin";

	@Autowired
	private UserService userService;

	@Autowired
	private WxqhService wxqhService;

	@Autowired
	private LoginService loginService;

	/**
	 * 公众号用户登录
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 15:54:44
	 * @param request
	 * @param response
	 * @param code
	 */
	@RequestMapping("/passport/openidLogin")
	public void getUserPhone(HttpServletRequest request, HttpServletResponse response, String code) {
		if (StringUtils.isEmpty(code)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
			return;
		}

		// 通过code获取微信用户的access_token信息
		String result = userService.getWxUserAccessToken(code);
		if (!StringUtils.isEmpty(JSON.parseObject(result).getString("errcode"))) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", JSON.parseObject(result).getString("errcode"));
			jsonObject.put("info", JSON.parseObject(result).getString("errmsg"));
			JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
			return;
		}
		String accessToken = JSON.parseObject(result).getString("access_token");
		String openid = JSON.parseObject(result).getString("openid");

		// 通过access_token和openid和获取微信用户基本信息
		String result2 = userService.getWxUserUnionid(accessToken, openid);
		String unionid = JSON.parseObject(result2).getString("unionid");

		// 税企通用户登录
		JSONObject jsonObject = JSON.parseObject(UserOperRequest.userLogin(OPENID_LOGIN, "wechat", unionid));
		System.out.println(jsonObject);
		if (!jsonObject.containsKey("resultData")) {
			JSONObject resultData = new JSONObject();
			resultData.put("unionid", unionid);
			resultData.put("openid", openid);
			jsonObject.put("resultData", resultData);
		}
		if("99999".equals(jsonObject.getString("code"))){
			// 设置微信取号用户身份证校验状态
			JSONObject resultData = jsonObject.getJSONObject("resultData");
			resultData.put("smcj", wxqhService.getWxUserAuthStatus(resultData.getJSONObject("user").getString("phone")));
			resultData.put("unionid", unionid);
			resultData.put("openid", openid);
		}
		logger.info("登录的返回值：" + jsonObject);
		JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
	}

	/**
	 * 小程序用户获取sessionKey
	 * 
	 * @author cheng_chen
	 * @date 2017-06-15 10:14:47
	 * @param request
	 * @param response
	 * @param code
	 */
	@Deprecated
	@RequestMapping("/get/xcx/sessionKey")
	public void getXcxSessionKey(HttpServletRequest request, HttpServletResponse response, String code) {
		if (StringUtils.isEmpty(code)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
			return;
		}

		// 通过code获取小程序用户的session
		String result = userService.getXcxSessionKey(code);
		JoojeeWebCommon.outputJSONObjectSuccess(JSON.parseObject(result), response);
	}

	@Deprecated
	@RequestMapping("/passport/xcx/openidLogin")
	public void getXcxUserPhone(HttpServletRequest request, HttpServletResponse response, String encryptedData,
			String session_key, String iv) {
		if (StringUtils.isEmpty(encryptedData) || StringUtils.isEmpty(session_key) || StringUtils.isEmpty(iv)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
			return;
		}

		// 获取小程序用户的unionId
		String unionId = userService.getXcxUnionId(encryptedData, session_key, iv);
		if (StringUtils.isEmpty(unionId)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.FAIL, response);
			return;
		}

		// 登录税企通
		String result = UserOperRequest.userLogin(OPENID_LOGIN, "wechat", unionId);
		JoojeeWebCommon.outputJSONResult(result, response);
	}

	/**
	 * 发送验证码
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 15:54:08
	 * @param request
	 * @param response
	 * @param phone
	 */
	@RequestMapping("/get/authCode")
	public void getAuthCode(HttpServletRequest request, HttpServletResponse response, String phone) {
		if (StringUtils.isEmpty(phone)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
			return;
		}

		String getAuthCodeUrl = GET_AUTH_CODE + "?phone=" + phone + "&authCodeType=loginAuthCode";
		String result = HttpsRequest.sendHttpsRequestByGet(getAuthCodeUrl);
		logger.info("发送验证码的返回值：" + result);
		JoojeeWebCommon.outputJSONResult(result, response);
	}

	/**
	 * 验证码登录
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 15:54:16
	 * @param request
	 * @param response
	 * @param phone
	 * @param authCode
	 */
	@RequestMapping("/passport/authCodeLogin")
	public void verifyUserPhone(HttpServletRequest request, HttpServletResponse response, String phone, String authCode,
			String openid) {
		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(authCode) || StringUtils.isEmpty(openid)) {
			JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
			return;
		}

		// 获取税企通用户信息
		JSONObject jsonObject =  JSON.parseObject(UserOperRequest.userLogin(AUTHCODE_LOGIN, phone, authCode));

		// 设置微信取号用户校验状态
		JSONObject resultData = jsonObject.getJSONObject("resultData");
		resultData.put("smcj", wxqhService.getWxUserAuthStatus(resultData.getJSONObject("user").getString("phone")));
		
		//关联用户体系
		if(JoojeeCommonCode.SUCCESS.equals(jsonObject.getString("code"))){
			userService.unionUserPhone(phone, openid);
		}
		
		JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
	}
	
	@Autowired
	WxqhMapper wxqhMapper;
	
	@RequestMapping("/test")
	public void xxx(){
		wxqhService.getTakeNumInfo("2001");
	}

}