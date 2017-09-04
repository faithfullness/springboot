package cn.joojee.wxqh.utils;

public enum CodeEnum {
    EMPTY_PHONE("01", "手机号不能为空"),
    EMPTY_CODE("02", "验证码不能为空");

    private String code;
    private String info;

    CodeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
