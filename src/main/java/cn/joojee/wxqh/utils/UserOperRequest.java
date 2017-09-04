package cn.joojee.wxqh.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserOperRequest {
	
	private static Logger logger = LoggerFactory.getLogger(UserOperRequest.class);

	public static String oauth_consumer_key = "7ca30c10df6966603479e1f319a3ba5d";
	public static String app_secret = "ffc045a7899d493a";
	
	/**
	* @Title: userLogin 
	* @Description: 用户登录
	* @param @param phone
	* @param @param password
	* @param @return
	* @return String
	* @throws
	 */
	@SuppressWarnings("deprecation")
	public static String userLogin(String url,String phone, String password) {

		List<NameValuePair> parms = new ArrayList<NameValuePair>();
		// 这里key同时用于计算签名
		parms.add(new BasicNameValuePair("oauth_consumer_key",oauth_consumer_key));
		parms.add(new BasicNameValuePair("oauth_nonce", System.currentTimeMillis() + ""));
		parms.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
		parms.add(new BasicNameValuePair("oauth_timestamp", System.currentTimeMillis() / 1000 + ""));
		parms.add(new BasicNameValuePair("oauth_version", "1.0"));
		parms.add(new BasicNameValuePair("x_auth_model", "client_auth"));
		parms.add(new BasicNameValuePair("password",password));
		parms.add(new BasicNameValuePair("phone",phone));

		String urlEncode = null;
		try {
			urlEncode = URLEncoder.encode(
					"http://sqt.joojee.com/service/xauth", "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String baseString = "POST&" + urlEncode + "&" + sortAndEncodeParams(parms);
		String signature = getSignature(baseString,app_secret);

		parms.add(new BasicNameValuePair("oauth_signature", signature));

		HttpClient httpClient = null;
		HttpPost method = null;
		try {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(url);
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(parms,"UTF-8");
			method.setEntity(urlEntity);
			HttpResponse resultResponse = httpClient.execute(method);
			int resultCode = resultResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == resultCode) {
				HttpEntity entity = resultResponse.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity,"utf-8");
				}
			}else{
				System.out.println(resultCode);
			}
		} catch (Exception e) {
			logger.error("user login error:"+e);
		}
		return null;
	}

	/**
	 * Base64编码
	 * 
	 * @param bytes
	 * @return
	 */
	private static String getBase64String(byte[] bytes) {
		byte[] encoded = Base64.encodeBase64(bytes);
		return new String(encoded);
	}

	/**
	 * 用 HmacSHA1 算法加密
	 * 
	 * @param baseString
	 * @param key
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */
	private static String computeHmac(String baseString, String key)
			throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		// Mac类提供 消息验证码（Message Authentication Code，MAC）算法的功能。
		// getInstance返回实现指定 MAC 算法的 Mac对象。
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec secret = new SecretKeySpec(key.getBytes(),
				mac.getAlgorithm());
		mac.init(secret);
		byte[] digest = mac.doFinal(baseString.getBytes());
		return getBase64String(digest);
	}

	/**
	 * 生成签名
	 * 
	 * @param baseString
	 * @param oauth_consumer_secret
	 * @return Signature
	 */
	public static String getSignature(String baseString,
			String oauth_consumer_secret) {
		String key = oauth_consumer_secret + "&";
		String signature = null;
		try {
			signature = computeHmac(baseString, key);
		} catch (InvalidKeyException e) {

		} catch (NoSuchAlgorithmException e) {

		} catch (IllegalStateException e) {

		} catch (UnsupportedEncodingException e) {

		}
		return signature;
	}

	/**
	 * 对baseString排序并encode编码(encode两次)
	 * 
	 * @param params
	 * @return
	 */
	private static String sortAndEncodeParams(List<NameValuePair> params) {
		StringBuffer sb = new StringBuffer();

		for (NameValuePair pair : params) {
			String encodedName = null;
			try {
				encodedName = URLEncoder.encode(pair.getName(), "utf-8");
				encodedName = URLEncoder.encode(encodedName, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			final String value = pair.getValue();
			String encodedValue = null;
			try {
				encodedValue = value != null ? URLEncoder
						.encode(value, "utf-8") : "";
				encodedValue = URLEncoder.encode(encodedValue, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			if (sb.length() > 0) {
				sb.append("%26");
			}
			sb.append(encodedName);
			sb.append("%3D");
			sb.append(encodedValue);
		}
		return sb.toString();
	}

	/**
	 * @Title: userLogin
	 * @Description: 用户登录
	 * @param @param phone
	 * @param @param password
	 * @param @return
	 * @return String
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public static String userLogin(String phone, String password) {

		List<NameValuePair> parms = new ArrayList<NameValuePair>();
		// 这里key同时用于计算签名
		parms.add(new BasicNameValuePair("oauth_consumer_key",Info.oauth_consumer_key));
		parms.add(new BasicNameValuePair("oauth_nonce", System.currentTimeMillis() + ""));
		parms.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
		parms.add(new BasicNameValuePair("oauth_timestamp", System.currentTimeMillis() / 1000 + ""));
		parms.add(new BasicNameValuePair("oauth_version", "1.0"));
		parms.add(new BasicNameValuePair("x_auth_model", "client_auth"));
		parms.add(new BasicNameValuePair("password",password));
		parms.add(new BasicNameValuePair("phone",phone));

		String urlEncode = null;
		try {
			urlEncode = URLEncoder.encode(
					"http://sqt.joojee.com/service/xauth", "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String baseString = "POST&" + urlEncode + "&" + sortAndEncodeParams(parms);
		String signature = getSignature(baseString,Info.app_secret);

		parms.add(new BasicNameValuePair("oauth_signature", signature));

		HttpClient httpClient = null;
		HttpPost method = null;
		try {
			httpClient = new DefaultHttpClient();
			method = new HttpPost(Info.userLoginUrl);
			UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(parms,"UTF-8");
			method.setEntity(urlEntity);
			HttpResponse resultResponse = httpClient.execute(method);
			int resultCode = resultResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == resultCode) {
				HttpEntity entity = resultResponse.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity,"utf-8");
				}
			}
		} catch (Exception e) {
			logger.error("user login error:"+e);
		}
		return null;
	}
}
