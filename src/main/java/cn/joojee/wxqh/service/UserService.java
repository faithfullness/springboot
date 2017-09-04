package cn.joojee.wxqh.service;

/**
 * 微信关联服务
 * 
 * @author cheng_chen
 * @date 2017-06-07 11:20:56
 */
public interface UserService {

	/**
	 * 通过微信unionid获取关联的税企通用户手机号
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 11:21:41
	 * @return
	 */
	@Deprecated
	String getUserPhone(String unionid);

	/**
	 * 校验用户手机号是否注册过税企通
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 14:56:39
	 * @param phone
	 * @return
	 */
	@Deprecated
	boolean verifyUserPhone(String phone);

	/**
	 * 关联税企通和微信用户体系
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 16:33:35
	 * @param phone
	 * @param unionid
	 * @return
	 */
	boolean unionUserPhone(String phone, String unionid);
	
	/**
	 * 获取公众号的accessToken
	 * 
	 * @author cheng_chen
	 * @date 2017-06-15 10:03:07
	 * @param
	 * @return
	 */
	String getWxAccessToken();

	/**
	 * 通过code获取用户的accessToken
	 * 
	 * @author cheng_chen
	 * @date 2017-06-15 10:03:07
	 * @param code
	 * @return
	 */
	String getWxUserAccessToken(String code);

	/**
	 * 获取微信公众号用户的unionid
	 * 
	 * @author cheng_chen
	 * @date 2017-06-15 10:05:17
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	String getWxUserUnionid(String accessToken, String openid);

	/**
	 * 获取小程序用户的sessionKey
	 * 
	 * @author cheng_chen
	 * @date 2017-06-15 10:15:30
	 * @param code
	 * @return
	 */
	@Deprecated
	String getXcxSessionKey(String code);

	/**
	 * 获取小程序用户的uid
	 * @author cheng_chen
	 * @date 2017-06-15 10:22:24
	 * @param encryptedData
	 * @param session_key
	 * @param iv
	 * @return
	 */
	@Deprecated
	String getXcxUnionId(String encryptedData, String session_key, String iv);
}
