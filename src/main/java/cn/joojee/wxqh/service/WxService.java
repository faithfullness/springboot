package cn.joojee.wxqh.service;

/**
 * 微信关联服务
 * 
 * @author cheng_chen
 * @date 2017-06-07 11:20:56
 */
public interface WxService {

	/**
	 * 通过微信unionid获取关联的税企通用户手机号
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 11:21:41
	 * @return
	 */
	String getUserPhone(String unionid);

	/**
	 * 校验用户手机号是否注册过税企通
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 14:56:39
	 * @param phone
	 * @return
	 */
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
}
