package cn.joojee.wxqh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5 {

	private MessageDigest messageDigest;
	  private String charsert = "UTF-8";

	  public String getMD5ofStr(String str)
	  {
	    try
	    {
	      this.messageDigest = MessageDigest.getInstance("MD5");

	      this.messageDigest.reset();

	      this.messageDigest.update(str.getBytes(this.charsert));

	      byte[] byteArray = this.messageDigest.digest();

	      StringBuilder md5StrBuff = new StringBuilder();

	      for (int i = 0; i < byteArray.length; i++) {
	        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
	          md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	        else {
	          md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	      }

	      return md5StrBuff.toString().toUpperCase();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  public String getMD5ofFile(File file)
	  {
	    try
	    {
	      this.messageDigest = MessageDigest.getInstance("MD5");

	      this.messageDigest.reset();

	      byte[] buffer = new byte[1024];
	      FileInputStream in = new FileInputStream(file);
	      int len;
	      while ((len = in.read(buffer, 0, 1024)) != -1)
	      {
	        this.messageDigest.update(buffer, 0, len);
	      }
	      in.close();

	      byte[] byteArray = this.messageDigest.digest();

	      StringBuilder md5StrBuff = new StringBuilder();

	      for (int i = 0; i < byteArray.length; i++) {
	        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
	          md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	        else {
	          md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	      }

	      return md5StrBuff.toString().toUpperCase();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  public String getMD5ofByte(byte[] bytes)
	  {
	    try
	    {
	      this.messageDigest = MessageDigest.getInstance("MD5");

	      this.messageDigest.reset();

	      this.messageDigest.update(bytes);

	      byte[] byteArray = this.messageDigest.digest();

	      StringBuilder md5StrBuff = new StringBuilder();

	      for (int i = 0; i < byteArray.length; i++) {
	        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
	          md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	        else {
	          md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
	        }
	      }

	      return md5StrBuff.toString().toUpperCase();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
}
