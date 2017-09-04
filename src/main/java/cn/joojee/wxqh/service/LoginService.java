package cn.joojee.wxqh.service;

import cn.joojee.wxqh.model.OpenidBean;

import java.util.List;

/**
 * 微信取号service
 * Created by Administrator on 2017/8/3.
 */
public interface  LoginService {

    /**
     * 根据手机号密码判断是否存在
     * @param phone
     * @param password
     * @return
     */
    public boolean getUserByPassword(String phone,String password);

    /**
     * 根据电话号码获取该用户的所属税务机关代码
     * @param phone
     * @return
     */
    public List<String> getZgswjDm(String phone);


    /**
     * 根据openid获取用户电话号码
     * @param openid
     * @return
     */
    public OpenidBean getPhoneByOpenId(String openid);

    /**
     * 根据phone获取openid
     * @param phone
     * @return
     */
    public OpenidBean getOpneidByPhone(String phone);


    /**
     *根据微信openid和accessToken推送微信取号
     * @param accessToken
     * @param openid
     * @return
     */
    public String pushWxxx(String accessToken,String openid,String tsmb);


    /**
     *将微信openid与手机号绑定起来
     * @param phone
     * @param openid
     * @return
     */
    public Boolean bingOpenidPhone(String phone,String openid);


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

}
