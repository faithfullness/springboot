package cn.joojee.wxqh.model;

import com.alibaba.fastjson.JSONObject;

import cn.joojee.wxqh.utils.JoojeeCommonCode;

public class ResultBody {
	/** 响应代码 */
	private String code;
	/** 响应消息 */
	private String info;
	/** 响应结果 */
	private Object resultData;

	public ResultBody(Object resultData) {
		this.code = JoojeeCommonCode.SUCCESS;
		this.info = JoojeeCommonCode.SUCCESS_VALUE;
		this.resultData = resultData;
	}

	public ResultBody(String code) {
		this.code = code;
		this.info = JoojeeCommonCode.getMessage(code);
	}

	public ResultBody(String code, String info) {
		this.code = code;
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

//	public String toJSONString() {
//		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("code", code);
//		jsonObject.put("info", info);
//		return jsonObject.toJSONString();
//	}

	@Override
	public String toString() {
		return "ResultBody [code=" + code + ", info=" + info + ", resultData=" + resultData + "]";
	}
}
