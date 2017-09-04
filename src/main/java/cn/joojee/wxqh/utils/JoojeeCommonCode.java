package cn.joojee.wxqh.utils;

import java.util.HashMap;
import java.util.Map;

public class JoojeeCommonCode {

	/** 200 */
	public static String SUCCESS = "99999";
	/** 200 */
	public static String SUCCESS_VALUE = "success";

	/** 99998 */
	public static String FAIL = "99998";

	/** 99998 */
	public static String FAIL_VALUE = "操作失败";

	/** 99997 */
	public static String EMPTY_RESULT = "99997";

	/** 99997 */
	public static String EMPTY_RESULT_VALUE = "结果集为空";

	/** 405 */
	public static String CODE_405 = "405";
	/** 405 */
	public static String CODE_405_VALUE = "method not allowed";

	/** 10000 */
	public static String CODE_10000 = "10000";
	/** 10000 */
	public static String CODE_10000_VALUE = "平台来源标示ID不正确";

	/** 405 */
	public static String SERVER_ERROR = "505";
	/** 405 */
	public static String SERVER_ERROR_VALUE = "服务器异常";

	// added by yang_jie 2016-07-12 begin
	/** 605 */
	public static String PARAM_VALIDATE_ERROR = "605";
	/** 605状态码含义 */
	public static String PARAM_VALIDATE_ERROR_VALUE = "签名校验失败";

	/** 705 */
	public static String URL_VALID_OUT_DATED = "705";
	/** 705状态码含义 */
	public static String URL_VALID_OUT_DATED_VALUE = "链接url有效时间过时";
	// added by yang_jie 2016-07-12 end

	/** 10008 */
	public static String MISSING_PARAMS = "10008";

	/** 10008 */
	public static String MISSING_PARAMS_VALUE = "缺少参数";

	/** 10002 */
	public static String NEED_LOGIN = "10002";

	/** 10002 */
	public static String NEED_LOGIN_VALUE = "请先登录";

	/** 10008 */
	public static String UPLOAD_IMAGE_ERROR = "10004";

	/** 10008 */
	public static String UPLOAD_IMAGE_ERROR_VALUE = "图片上传失败";

	/** 10010 */
	public static String INFO_NOT_COMPLETE = "10010";

	/** 10010 */
	public static String INFO_NOT_COMPLETE_VALUE = "头像和真实姓名不能为空，请完善个人信息";

	public static String INVALID_URL = "10011";
	public static String INVALID_URL_VALUE = "请求路径无效";

	public static String INVALID_PDFDOWNLOAR_URL = "10012";
	public static String INVALID_PDFDOWNLOAR__URL_VALUE = "pdf下载路径错误";

	/** 10012 */
	public static String SYSTEM_UNDER_TEST = "10012";
	/** 10012 */
	public static String SYSTEM_UNDER_TEST_VALUE = "服务器维护中,请关注开机公告";

	/** 10013 */
	public static String ACCESS_TOKEN_TIME_OUT = "10013";
	/** 10013 */
	public static String ACCESS_TOKEN_TIME_OUT_VALUE = "access_token已过期";
	

	/** 1111 */
	public static String FCQWXBS_EXCEPTION = "1111";
	/** 1111 */
	public static String FCQWXBS_EXCEPTION_VALUE = "内部异常,请联系开发人员";

	/** 状态码存储列表 */
	public static Map<String, String> allCodeMap = new HashMap<String, String>();

	/** 登录注册授权状态码 */
	public static class PassportCode {

		/** 40001 */
		public static String EMPTY_PHONE = "40001";

		/** 40002 */
		public static String EMPTY_CODE = "40002";

		/** 40003 */
		public static String EMPTY_PASSWORD = "40003";

		/** 40004 */
		public static String ERROR_CODE = "40004";

		/** 40005 */
		public static String INVALID_PHONE = "40005";

		/** 40006 */
		public static String LOGIN_ERROR_PASSWORD = "40006";

		/** 40007 */
		public static String REGISTED_FAILD = "40007";

		/** 40009 */
		public static String PHONE_EXIST = "40009";

		/** 40010 */
		public static String RANGE_OF_MAX_SEND_COUNT = "40010";

		/** 40011 */
		public static String SMS_SEND_FAIL = "40011";

		/** 40012 */
		public static String RANGE_OF_ENTER_AUTH_CODE = "40012";

		/** 40013 */
		public static String AUTH_CODE_HAS_EXPIRED = "40013";

		/** 40014 */
		public static String RESET_PASSWORD_ERROR = "40014";

		/** 40005 */
		public static String EMPTY_AUTH_CODE_TYPE = "40015";

		/** 40016 */
		public static String ERROR_AUTH_CODE_TYPE = "40016";

		/** 40017 */
		public static String PHONE_IS_NOT_EXISTED = "40017";

		/** 40018 */
		public static String MIN_TIME_OF_GET_NEXT_AUTH_CODE = "40018";

		/** 40019 */
		public static String NOT_COMPLETE_OF_USER_BASIC_INFO = "40019";

		/** 40020 */
		public static String ACCOUNT_IS_ALREADY_FORBIDDEN = "40020";

		/** 40021 */
		public static String EMPTY_CLIENTID = "40021";

		/** 40022 */
		public static String EMPTY_CLIENTMODEL = "40022";

		/** 40000 */
		public static String SEND_AUTH_MSG = "40000";

		/** 40023 */
		public static String REGISTER_AND_RESETPASSWORD_ERROR_PASSWORD = "40023";

		/** 40024 */
		public static String NEED_MODIFY_PWD = "40024";

		/** 用户验证信息完整性的状态码 */
		public static String EMPTY_REALNAME = "40025";
		public static String EMPTY_AVATAR = "40026";
		public static String EMPTY_JID = "40027";
		public static String EMPTY_AREA = "40028";
		public static String EMPTY_GENDAR = "40029";

		public static String NO_PERMISSION_LOGIN = "40030";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EMPTY_PHONE, "手机号不能为空");
			codeMap.put(EMPTY_CODE, "验证码不能为空");
			codeMap.put(EMPTY_PASSWORD, "密码不能为空");
			codeMap.put(ERROR_CODE, "验证码不正确");
			codeMap.put(INVALID_PHONE, "手机号格式不合法");
			codeMap.put(LOGIN_ERROR_PASSWORD, "账号或密码错误");
			codeMap.put(REGISTED_FAILD, "注册失败");
			codeMap.put(PHONE_EXIST, "手机号码不可用或已被注册");
			codeMap.put(SMS_SEND_FAIL, "短信发送失败");
			codeMap.put(PHONE_IS_NOT_EXISTED, "手机号不存在");
			codeMap.put(RESET_PASSWORD_ERROR, "重置密码失败");
			codeMap.put(MIN_TIME_OF_GET_NEXT_AUTH_CODE, "获取验证码的间隔时间不足一分钟");
			codeMap.put(EMPTY_AUTH_CODE_TYPE, "检验校验码的操作类型参数不能为空");
			codeMap.put(ERROR_AUTH_CODE_TYPE, "检验校验码的操作类型参数不合法");
			codeMap.put(AUTH_CODE_HAS_EXPIRED, "验证码错误或已过期");
			codeMap.put(RANGE_OF_ENTER_AUTH_CODE, "验证码输入错误次数过多,请重新获取验证码");
			codeMap.put(RANGE_OF_MAX_SEND_COUNT, "今天发送验证码次数已超过限制，请明天再尝试");
			codeMap.put(SEND_AUTH_MSG, "您的验证码为{0}");
			codeMap.put(NOT_COMPLETE_OF_USER_BASIC_INFO, "信息不完整,请先填写基本信息");
			codeMap.put(ACCOUNT_IS_ALREADY_FORBIDDEN, "你的账号已经被封禁");
			codeMap.put(EMPTY_CLIENTID, "手机推送唯一标示不能为空");
			codeMap.put(EMPTY_CLIENTMODEL, "对应用户的唯一设备标示不能为空");
			codeMap.put(REGISTER_AND_RESETPASSWORD_ERROR_PASSWORD, "密码不合法");
			codeMap.put(NEED_MODIFY_PWD, "请修改初始密码");
			codeMap.put(EMPTY_REALNAME, "真实姓名不能为空");
			codeMap.put(EMPTY_AVATAR, "用户图像不能为空");
			codeMap.put(EMPTY_JID, "平台id不能为空");
			codeMap.put(EMPTY_AREA, "所属区域不能为空");
			codeMap.put(EMPTY_GENDAR, "性别不能为空");
			codeMap.put(NO_PERMISSION_LOGIN, "暂无权登陆该系统版本,请联系管理员");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	public static class EnterpriseCode {

		/** 40101 */
		public static String EMPTY_ENTERPRISE_NAME = "40101";

		/** 40102 */
		public static String NOT_EXIST_ENTERPRISE_TAX_NUMBER = "40102";

		/** 40103 */
		public static String EMPTY_ENTERPRISE_UROLE = "40103";

		/** 40104 */
		public static String EMPTY_ENTERPRISE_UNAME = "40104";

		/** 40105 */
		public static String EMPTY_ENTERPRISE_UPHONE = "40105";

		/** 40106 */
		public static String EMPTY_ENTERPRISE_TAXNUMBER = "40106";

		/** 40107 */
		public static String INVALID_ENTERPRISE_USER_ROLE = "40107";

		/** 40108 */
		public static String EMPTY_ENTERPRISE_SHIBBOLETH = "40108";

		/** 40109 */
		public static String INVALID_ENTERPRISE_SHIBBOLETH = "40109";

		/** 40110 */
		public static String NO_AFFILIATED_ENTERPRISE = "40110";

		/** 40111 */
		public static String CAN_NOT_CHOOSE_THIS_ENTERPRISE = "40111";

		/** 40112 */
		public static String CAN_NOT_CHOOSE_THIS_ENTERPRISE_ROLE = "40112";

		/** 40113 */
		public static String NOT_EXIST_ENTERPRISE_ROLE = "40113";

		public static String ALREADY_AFFILIATED = "40114";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EMPTY_ENTERPRISE_NAME, "企业名称不能为空");
			codeMap.put(NOT_EXIST_ENTERPRISE_TAX_NUMBER, "企业税号不存在");
			codeMap.put(EMPTY_ENTERPRISE_UROLE, "用户对应企业角色不能为空");
			codeMap.put(EMPTY_ENTERPRISE_UNAME, "用户对应企业姓名不能为空");
			codeMap.put(EMPTY_ENTERPRISE_UPHONE, "用户对应企业手机号不能为空");
			codeMap.put(EMPTY_ENTERPRISE_TAXNUMBER, "企业税号不能为空");
			codeMap.put(INVALID_ENTERPRISE_USER_ROLE, "当前企业的用户对应角色信息不存在");
			codeMap.put(EMPTY_ENTERPRISE_SHIBBOLETH, "企业的关联口令不能为空");
			codeMap.put(INVALID_ENTERPRISE_SHIBBOLETH, "关联口令不合法");
			codeMap.put(NO_AFFILIATED_ENTERPRISE, "还未关联企业");
			codeMap.put(CAN_NOT_CHOOSE_THIS_ENTERPRISE, "无权限操作这个企业信息");
			codeMap.put(CAN_NOT_CHOOSE_THIS_ENTERPRISE_ROLE, "无权限选择此企业的角色");
			codeMap.put(NOT_EXIST_ENTERPRISE_ROLE, "此角色信息不合法");
			codeMap.put(ALREADY_AFFILIATED, "已经关联过该企业的该角色");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	/**
	 * 联系人模块错误状态码信息
	 * 
	 * @author xu_min
	 * @version 2.0.0 2014-08-13
	 */
	public static class UserContactCode {

		/** 40201 */
		public static String EMPTY_USER_CONTACT_NAME = "40201";

		/** 40202 */
		public static String EMPTY_USER_CONTACT_PHONE = "40202";

		/** 40203 */
		public static String EMPTY_USER_CONTACT_TYPE = "40203";

		/** 40204 */
		public static String INVALID_USER_CONTACT_TYPE = "40204";

		/** 40205 */
		public static String NOT_EXIST_USER = "40205";

		/** 40206 */
		public static String NOT_EXIST_ENTERPRISE = "40206";

		/** 40207 */
		public static String WAITTING_FOR_ADD = "40207";

		/** 40208 */
		public static String ALREADY_YOUR_FRIEND = "40208";

		/** 40209 */
		public static String NOT_ALLOWED_ADD = "40209";

		/** 40210 */
		public static String EMPTY_USER_SWJG_MC = "40210";

		/** 40211 */
		public static String EMPTY_USER_SWJG_DM = "40211";

		/** 40212 */
		public static String EMPTY_USER_JB_DM = "40212";

		/** 40213 */
		public static String EMPTY_USER_JB_MC = "40213";

		/** 40214 */
		public static String EMPTY_USER_CZRY_DM = "40214";

		/** 40215 */
		public static String NOT_EXIST_PUBLIC = "40215";

		/** 40216 */
		public static String CAN_NOT_HANDLE_SELF = "40216";

		public static String ADDACCOUNT_IS_ALREADY_FORBIDDEN = "40217";

		public static String WATING_FOR_AGREE = "40218";
		public static String NOT_EXIST_PUBLIC_SERVER = "40219";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EMPTY_USER_CONTACT_NAME, "用户联系人名称不能为空");
			codeMap.put(EMPTY_USER_CONTACT_PHONE, "用户联系人手机号不能为空");
			codeMap.put(EMPTY_USER_CONTACT_TYPE, "用户联系人类型不能为空");
			codeMap.put(INVALID_USER_CONTACT_TYPE, "用户联系人类型不合法");
			codeMap.put(NOT_EXIST_USER, "用户联系人不存在");
			codeMap.put(NOT_EXIST_ENTERPRISE, "企业联系人不存在");
			codeMap.put(WAITTING_FOR_ADD, "请求发送成功，请等待对方验证");
			codeMap.put(ALREADY_YOUR_FRIEND, "对方已经是你的好友");
			codeMap.put(NOT_ALLOWED_ADD, "对方不允许被添加为好友");
			codeMap.put(EMPTY_USER_SWJG_MC, "用户联系人税务机关名称不能为空");
			codeMap.put(EMPTY_USER_SWJG_DM, "用户联系人税务机关代码不能为空");
			codeMap.put(EMPTY_USER_JB_DM, "用户联系人级别代码不能为空");
			codeMap.put(EMPTY_USER_JB_MC, "用户联系人级别名称不能为空");
			codeMap.put(EMPTY_USER_CZRY_DM, "用户联系人操作人代码不能为空");
			codeMap.put(NOT_EXIST_PUBLIC, "纳税服务号不存在");
			codeMap.put(CAN_NOT_HANDLE_SELF, "不能添加自己的手机号为好友");
			codeMap.put(ADDACCOUNT_IS_ALREADY_FORBIDDEN, "被添加用户账号已经被封禁");
			codeMap.put(WATING_FOR_AGREE, "联系人已添加过，请等待对方同意");
			codeMap.put(NOT_EXIST_PUBLIC_SERVER, "税务人不存在");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	/**
	 * @Description 客户管理模块状态码
	 * @author zhan_xiaolong
	 * @date 2014-8-26 上午11:16:13
	 * @version V1.0.0
	 */
	public static class CustomerCode {

		/** 40301 */
		public static String EXIST_CUSTOMER = "40301";

		/** 40302 */
		public static String EXIST_ACCOUNT = "40302";

		/** 40303 */
		public static String UNMODIFY_PWD = "40303";

		/** 40304 */
		public static String WRONG_NEWPWD = "40304";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EXIST_CUSTOMER, "客户名称或客户账号已经存在");
			codeMap.put(EXIST_ACCOUNT, "账号不能重复");
			codeMap.put(UNMODIFY_PWD, "未修改初始密码,请修改");
			codeMap.put(WRONG_NEWPWD, "两次输入的密码不一致");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	/**
	 * @Description 活动管理模块状态码
	 * @author Hu_wentao
	 * @date 2014-8-26
	 * @version V1.0.0
	 */
	public static class ActivityCode {

		/** 40401 */
		public static String EXIST_ACTIVITY = "40401";
		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EXIST_ACTIVITY, "此活动已经存在");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	/**
	 * @Description 用户报名模块状态码
	 * @author Hu_wentao
	 * @date 2014-8-28
	 * @version V1.0.0
	 */
	public static class SignUpUserCode {

		/** 40501 */
		public static String EXIST_USER = "40501";

		/** 40502 */
		public static String NOTEXIST_USER = "40502";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EXIST_USER, "此活动您已报名过了");
			codeMap.put(NOTEXIST_USER, "用户信息不存在，请您重新登录了");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	public static class UserCollectionCode {

		/** 40601 */
		public static String EXIST_COLLECTION = "40601";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EXIST_COLLECTION, "该条记录已经收藏过");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	public static class TaxOfficerCode {

		/** 40701 */
		public static String EMPTY_VPN_USERNAME = "40701";

		/** 40702 */
		public static String EMPTY_VPN_PASSWORD = "40702";

		/** 40711 */
		public static String NO_EXISTED_USER = "40711";

		/** 40712 */
		public static String EXISTED_SQT_USER = "40712";

		/** 40713 */
		public static String NO_EXISTED_TAXAUTHORITY = "40713";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EMPTY_VPN_USERNAME, "用户的vpn用户名不能为空");
			codeMap.put(EMPTY_VPN_PASSWORD, "用户的vpn密码不能为空");
			codeMap.put(NO_EXISTED_USER, "该用户不存在");
			codeMap.put(EXISTED_SQT_USER, "该手机号已在税企通应用中注册过");
			codeMap.put(NO_EXISTED_TAXAUTHORITY, "该手机号码对应的税务机关不存在,请确认手机号码");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	public static class MessageCode {

		/** 40801 */
		public static String EMPTY_USER_VERIFY_MESSAGE = "40801";

		/** 40802 */
		public static String TOOMANY_UNVIEWMESSAGE = "40802";

		/** 状态码存储列表 */
		public static Map<String, String> codeMap = new HashMap<String, String>();

		/** 初始化状态码 */
		static {
			codeMap.put(EMPTY_USER_VERIFY_MESSAGE, "用户的验证消息为空");
			codeMap.put(TOOMANY_UNVIEWMESSAGE, "对方未读的消息太多，请勿频繁发送消息");
		}

		/**
		 * 获取状态码对应的消息
		 * 
		 * @param code
		 *            状态码
		 * @return
		 */
		public static String getMessage(String code) {
			return codeMap.get(code);
		}
	}

	static {
		allCodeMap.putAll(PassportCode.codeMap);
		allCodeMap.putAll(UserContactCode.codeMap);
		allCodeMap.putAll(EnterpriseCode.codeMap);
		allCodeMap.putAll(CustomerCode.codeMap);
		allCodeMap.putAll(UserCollectionCode.codeMap);
		allCodeMap.putAll(TaxOfficerCode.codeMap);
		allCodeMap.putAll(MessageCode.codeMap);
		allCodeMap.put(SUCCESS, SUCCESS_VALUE);
		allCodeMap.put(CODE_405, CODE_405_VALUE);
		allCodeMap.put(SERVER_ERROR, SERVER_ERROR_VALUE);
		allCodeMap.put(MISSING_PARAMS, MISSING_PARAMS_VALUE);
		allCodeMap.put(UPLOAD_IMAGE_ERROR, UPLOAD_IMAGE_ERROR_VALUE);
		allCodeMap.put(NEED_LOGIN, NEED_LOGIN_VALUE);
		allCodeMap.put(FAIL, FAIL_VALUE);
		allCodeMap.put(INFO_NOT_COMPLETE, INFO_NOT_COMPLETE_VALUE);
		allCodeMap.put(EMPTY_RESULT, EMPTY_RESULT_VALUE);
		allCodeMap.put(INVALID_URL, INVALID_URL_VALUE);
		allCodeMap.put(INVALID_PDFDOWNLOAR_URL, INVALID_PDFDOWNLOAR__URL_VALUE);
		allCodeMap.put(SYSTEM_UNDER_TEST, SYSTEM_UNDER_TEST_VALUE);
		allCodeMap.put(CODE_10000, CODE_10000_VALUE);
		// added by yangjie 2016-07-13 begin
		allCodeMap.put(PARAM_VALIDATE_ERROR, PARAM_VALIDATE_ERROR_VALUE);
		allCodeMap.put(URL_VALID_OUT_DATED, URL_VALID_OUT_DATED_VALUE);
		// added by yangjie 2016-07-13 begin
		allCodeMap.put(ACCESS_TOKEN_TIME_OUT, ACCESS_TOKEN_TIME_OUT_VALUE);
	}

	/**
	 * 获取状态码对应的消息
	 * 
	 * @param code
	 *            状态码
	 * @return
	 */
	public static String getMessage(String code) {
		return allCodeMap.get(code);
	}
}
