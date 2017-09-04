package cn.joojee.wxqh.service;

import cn.joojee.wxqh.model.WxqhConfig;

public interface WxqhService {

	/**
	 * 判断用户当天是否还能取某类业务号，每天每类业务仅可使用微信取号一次，
	 * 
	 * @author cheng_chen
	 * @date 2017-06-20 14:32:54
	 * @return
	 */
	boolean verifyUserPermissionsOnDay(String fzdm, String phone);

	/**
	 * 判断用户当月是否还能使用微信取号，累计两次使用微信取号但是未办理业务，当月禁用微信取号功能，次月恢复
	 * 
	 * @author cheng_chen
	 * @date 2017-06-20 14:36:33
	 * @return
	 */
	boolean verifyUserPermissionsOnMonth(String phone);

	/**
	 * 获取取号相关信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-22 12:07:36
	 * @return
	 */
	WxqhConfig getTakeNumInfo(String fzdm);

	/**
	 * 获取业务办理状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-26 12:09:05
	 * @param phone
	 * @param fzdm
	 * @return
	 */
	WxqhConfig getHandleStatus(WxqhConfig wxqhConfig);

	/**
	 * 记录用户取号信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-26 10:44:00
	 * @param wxqhConfig
	 * @return
	 */
	boolean setTakeNumInfo(WxqhConfig wxqhConfig);

	/**
	 * 记录微信用户身份校验状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-23 09:42:32
	 * @param phone
	 */
	void setWxUserAuthStatus(String phone);

	/**
	 * 获取微信用户身份校验状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-23 09:48:36
	 * @param phone
	 * @return
	 */
	boolean getWxUserAuthStatus(String phone);

	/**
	 * 确认办理
	 * 
	 * @author cheng_chen
	 * @date 2017-06-26 10:07:09
	 * @param phone
	 */
	boolean confirmHandle(WxqhConfig wxqhConfig);
	
	/**
	 * 更新当天未办理的状态
	 * @author cheng_chen
	 * @date 2017-06-29 12:06:15
	 * @return
	 */
	void updateNotHandleStatus();
}
