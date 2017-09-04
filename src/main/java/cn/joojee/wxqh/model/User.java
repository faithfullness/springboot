package cn.joojee.wxqh.model;

public class User {
	/**手机号*/
	private String phone;
	/**token*/
	private String accessToken;
	/**公钥*/
	private String oauth_consumer_key;
	/**税号*/
	private String taxpayerNumber;
	/**企业名称*/
	private String taxpayerName;
	/**登记序号*/
	private String djxh;
	/**税务机关代码*/
	private String taxofficeCode;
	private String taxofficeName;
	private String taxofficeParentCode;
	private String taxofficeParentName;
	/**用户头像*/
	private String avatar;
	/**用户名称*/
	private String realName;
	/**验证码*/
	private String authCodeNumber;
	/**密码*/
	private String password;
	/**进入系统标识*/
	private String intoFlag;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthCodeNumber() {
		return authCodeNumber;
	}
	public void setAuthCodeNumber(String authCodeNumber) {
		this.authCodeNumber = authCodeNumber;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public String getIntoFlag() {
		return intoFlag;
	}
	public void setIntoFlag(String intoFlag) {
		this.intoFlag = intoFlag;
	}
	public String getTaxofficeName() {
		return taxofficeName;
	}
	public void setTaxofficeName(String taxofficeName) {
		this.taxofficeName = taxofficeName;
	}
	public String getTaxofficeParentCode() {
		return taxofficeParentCode;
	}
	public void setTaxofficeParentCode(String taxofficeParentCode) {
		this.taxofficeParentCode = taxofficeParentCode;
	}
	public String getTaxofficeParentName() {
		return taxofficeParentName;
	}
	public void setTaxofficeParentName(String taxofficeParentName) {
		this.taxofficeParentName = taxofficeParentName;
	}
	public String getTaxofficeCode() {
		return taxofficeCode;
	}
	public void setTaxofficeCode(String taxofficeCode) {
		this.taxofficeCode = taxofficeCode;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOauth_consumer_key() {
		return oauth_consumer_key;
	}
	public void setOauth_consumer_key(String oauth_consumer_key) {
		this.oauth_consumer_key = oauth_consumer_key;
	}
	public String getTaxpayerNumber() {
		return taxpayerNumber;
	}
	public void setTaxpayerNumber(String taxpayerNumber) {
		this.taxpayerNumber = taxpayerNumber;
	}
	public String getTaxpayerName() {
		return taxpayerName;
	}
	public void setTaxpayerName(String taxpayerName) {
		this.taxpayerName = taxpayerName;
	}
	public String getDjxh() {
		return djxh;
	}
	public void setDjxh(String djxh) {
		this.djxh = djxh;
	}
	
}
