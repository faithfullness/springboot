package cn.joojee.wxqh.utils;

public class Info {
	
	/*线上环境*/
	public static String oauth_consumer_key = "7ca30c10df6966603479e1f319a3ba5d";
	public static String app_secret = "ffc045a7899d493a";
	/**用户登录*/
	public static String userLoginUrl = "https://sqt.joojee.cn/api/passport/login.json";
	/**获取企业列表*/
	public static String enterpriseList = "https://sqt.joojee.cn/api/l/taxpayer/get/taxpayerInfoList.json";
	/**发送验证码*/
	public static String authCode = "https://sqt.joojee.cn/api/passport/get/authCode.json";
	/**校验验证码*/
	public static String verCode = "https://sqt.joojee.cn/api/passport/auth/code.json";
	/**重置密码*/
	public static String resetPassword = "https://sqt.joojee.cn/api/passport/resetPwd.json";
	
	/*测试环境*/
//	public static String oauth_consumer_key = "c1e6dc723d9495d45e258185e9a73e4c";
//	public static String app_secret = "72f47de054bd38bb";
//	/**用户登录*/
//	public static String userLoginUrl = "http://192.168.1.90:82/api/passport/login.json";
//	/**获取企业列表*/
//	public static String enterpriseList = "http://192.168.1.90:82/api/l/enterprise/get/enterpriseInfoList.json";
//	/**发送验证码*/
//	public static String authCode = "http://192.168.1.90:82/api/passport/get/authCode.json";
//	/**校验验证码*/
//	public static String verCode = "http://192.168.1.90:82/api/passport/auth/code.json";
//	/**重置密码*/
//	public static String resetPassword = "http://192.168.1.90:82/api/passport/resetPwd.json";
}
