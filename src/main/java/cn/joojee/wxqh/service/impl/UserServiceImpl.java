package cn.joojee.wxqh.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.joojee.wxqh.mapper.UserMapper;
import cn.joojee.wxqh.service.UserService;
//import cn.joojee.wxqh.utils.AES;
import cn.joojee.wxqh.utils.HttpsRequest;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	private static final String APPID = "wx3b5e7b0722879169";

	private static final String SECRET = "98665411e6994d17402c66c47891686c";

	private static final String XCX_APPID = "wx24f8fffdec542eac";

	private static final String XCX_SECRET = "62273243802d1d6638098d664540662b";
	
	/** 获取微信公众号token信息 */
	private static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

	/** 获取微信用户token信息 */
	private static final String GET_USER_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/** 获取微信公众号用户基本信息 */
	private static final String GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo";

	/** 获取小程序用户session */
	private static final String GET_XCX_SESSION = "https://api.weixin.qq.com/sns/jscode2session";



	@Autowired
	private UserMapper wxMapper;

	@Override
	public String getUserPhone(String unionid) {
		return wxMapper.selectPhoneByUnionid(unionid);
	}

	@Override
	public boolean verifyUserPhone(String phone) {
		return wxMapper.selectPhone(phone) > 0;
	}

	@Override
	public boolean unionUserPhone(String phone, String unionid) {
		try {
			return wxMapper.insertUserUnionid(phone, unionid) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String getWxUserAccessToken(String code) {
		String accessTokenUrl = GET_USER_ACCESS_TOKEN + "?appid=" + APPID + "&secret=" + SECRET + "&code=" + code
				+ "&grant_type=authorization_code";
		String result = HttpsRequest.sendHttpsRequestByGet(accessTokenUrl);
		logger.info("通过code获取微信用户的access_token信息" + result);
		return result;
	}

	@Override
	public String getWxUserUnionid(String accessToken, String openid) {
		String userinfoUrl = GET_USERINFO + "?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
		String result = HttpsRequest.sendHttpsRequestByGet(userinfoUrl);
		logger.info("通过access_token和openid和获取微信用户基本信息" + result);
		return result;
	}

	@Override
	public String getXcxSessionKey(String code) {
		String xcxSessionUrl = GET_XCX_SESSION + "?appid=" + XCX_APPID + "&secret=" + XCX_SECRET + "&js_code=" + code
				+ "&grant_type=authorization_code";
		String result = HttpsRequest.sendHttpsRequestByGet(xcxSessionUrl);
		logger.info("通过code获取小程序用户session" + result);
		return result;
	}

	@Override
	public String getXcxUnionId(String encryptedData, String session_key, String iv) {
//		try {
//			AES aes = new AES();
//			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(session_key),
//					Base64.decodeBase64(iv));
//			if (null != resultByte && resultByte.length > 0) {
//				String userInfo = new String(resultByte, "UTF-8");
//				logger.info("小程序userinfo解密结果：" + userInfo);
//
//				String unionId = JSON.parseObject(userInfo).getString("unionId");
//				return unionId;
//			}
//		} catch (Exception e) {
//		}
		return null;
	}

	@Override
	public String getWxAccessToken() {
		String accessTokenUrl = GET_ACCESS_TOKEN + "?appid=" + APPID + "&secret=" + SECRET 
				+ "&grant_type=client_credential";
		String result = HttpsRequest.sendHttpsRequestByGet(accessTokenUrl);
		String access_token = JSON.parseObject(result).getString("access_token");
		logger.info("获取微信的access_token信息" + result);
		return access_token;
	}



}
