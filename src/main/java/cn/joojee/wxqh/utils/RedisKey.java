package cn.joojee.wxqh.utils;

import org.apache.commons.lang3.StringUtils;


public class RedisKey {

	/**accessToken_key redis key 获取*/
	public static String getAccessToken_key(){
		return "joojee_user_jid_map_access_token_hs";
	}

	/**微信取号配置文件 redis key 获取*/
	public static String getWxqhCongifKey(){
		return "joojee_wxqh_bsfwt_config";
	}

	/**微信取号大厅业务配置文件 redis key 获取*/
	public static String getWxqhYwCongifKey(String bsfwtDm){
		return StringUtils.join(CodeInfo.WXQH,CodeInfo.UNDERLINE,bsfwtDm,CodeInfo.UNDERLINE,CodeInfo.BSFWTYW);
	}

   	/*public static void main(String[] args) {
		//System.out.println(getXcphDay_key("14201010000"));
		System.out.println(getWxqhYwCongifKey("9142050017916520XC" ));
	}*/
}
