package cn.joojee.wxqh.service.impl;

import cn.joojee.wxqh.mapper.LoginMapper;
import cn.joojee.wxqh.model.OpenidBean;
import cn.joojee.wxqh.service.LoginService;
import cn.joojee.wxqh.utils.HttpsRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/3.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    //private static final  String WXXX_PUSH="https://api.weixin.qq.com/cgi-bin/message/mass/send";

    private static final  String WXXX_PUSH="https://api.weixin.qq.com/cgi-bin/message/custom/send";


    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Override
    public boolean getUserByPassword(String phone, String password) {
        Integer i=loginMapper.selectPhoneByUnionid(phone,password);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public List<String> getZgswjDm(String phone) {
        return loginMapper.getZgswjDmByPhone(phone);
    }

    @Override
    public OpenidBean getPhoneByOpenId(String openid) {
        return loginMapper.getPhoneByOpenId(openid);
    }

    @Override
    public OpenidBean getOpneidByPhone(String phone) {
        return loginMapper.getOpenidByPhone(phone);
    }

    @Override
    public String pushWxxx(String accessToken, String openid,String tsmb) {
            String accessTokenUrl = WXXX_PUSH + "?access_token=" +accessToken;
            Map<String, Object> requestParamMapr=new HashMap<String, Object>();
            requestParamMapr.put("touser",openid);
            requestParamMapr.put("msgtype","news");
            JSONObject newsjson=new JSONObject();
            JSONArray  articlesArray=new JSONArray();
            JSONObject json=new JSONObject();
            json.put("title","微信取号");
            json.put("description",tsmb);
            json.put("url","https://whwxqh.joojee.cn");
            articlesArray.add(json);
            newsjson.put("articles",articlesArray);
            requestParamMapr.put("news",newsjson);
            String param=JSON.toJSONString(requestParamMapr);
            logger.info("--微信推送result->"+param);
            String result = HttpsRequest.post(accessTokenUrl,param,"UTF-8");
            logger.info("--微信推送result->"+result);
            return result;
    }

    @Override
    public Boolean bingOpenidPhone(String phone, String openid) {
        OpenidBean openidBean=new OpenidBean();
        openidBean.setOpenid(openid);
        openidBean.setPhone(phone);
        openidBean.setAppType("wxqh");
        openidBean.setAuthChannel("wechat");
        Integer i=0;
        if(loginMapper.getPhoneByOpenId(openid)!=null){
               i= loginMapper.updateOpenidPhone(openidBean);
        }else{
               i= loginMapper.insertOpenidPhone(openidBean);
        }
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public void setWxUserAuthStatus(String phone) {
        loginMapper.insertUserAuthStatus(phone);
    }

    @Override
    public boolean getWxUserAuthStatus(String phone) {
        Integer i=loginMapper.selectUserAuthStatus(phone);
        logger.info("--i-->"+i);
        return i > 0;
    }
}
