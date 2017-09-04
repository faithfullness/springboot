package cn.joojee.wxqh.controller;

import cn.joojee.wxqh.Application;
import cn.joojee.wxqh.model.OpenidBean;
import cn.joojee.wxqh.model.User;
import cn.joojee.wxqh.service.LoginService;
import cn.joojee.wxqh.service.RedisService;
import cn.joojee.wxqh.service.UserService;
import cn.joojee.wxqh.service.WxqhService;
import cn.joojee.wxqh.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/3.
 */
@RestController
@RequestMapping("/wxqh")
public class LoginController {

    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);

    /** 获取验证码 */
    private static final String GET_AUTH_CODE = "https://sqt.joojee.cn/api/passport/get/authCode";

    /** 用户openid登录 */
    public static final String OPENID_LOGIN = "https://sqt.joojee.cn/api/passport/openidLogin";

    /** 用户authCode登录 */
    public static final String AUTHCODE_LOGIN = "https://sqt.joojee.cn/api/passport/authCodeLogin";

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private WxqhService wxqhService;

    @Autowired
    private RedisService redisService;

    /**
     * html登陆验证
     * @Title: htmlUserLogin
     * @Description: TODO
     * @param @param request
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/html/user/login",method =RequestMethod.POST)
    public Object  htmlUserLogin(HttpServletRequest request, HttpServletResponse response){
        JSONObject json=new JSONObject();
        String phone =  request.getParameter("phone");
        String password =  request.getParameter("password");
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
        }
        JSONObject resultJson = JSONObject.parseObject(UserOperRequest.userLogin(phone,password));
        if(null == resultJson){
            JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.SERVER_ERROR, response);
        }

        logger.info("html登陆,result:"+JSONObject.toJSONString(resultJson,true));

        if(!JoojeeCommonCode.SUCCESS.equals(resultJson.get("code"))){
            return resultJson.toString();

        }
        User model = new User();
        model.setPhone(phone);
        model.setAccessToken(resultJson.getJSONObject("resultData").getJSONObject("userCenter").getString("accessToken"));
        model.setOauth_consumer_key(Info.oauth_consumer_key);
        model.setAvatar(resultJson.getJSONObject("resultData").getJSONObject("user").getString("avatar"));
        model.setRealName(resultJson.getJSONObject("resultData").getJSONObject("user").getString("realName"));
//		model.setTaxofficeCode(resultJson.getJSONObject("resultData").getJSONObject("taxoffice").getString("taxofficeCode"));
//		model.setTaxofficeName(resultJson.getJSONObject("resultData").getJSONObject("taxoffice").getString("taxofficeName"));
//		model.setTaxofficeParentCode(resultJson.getJSONObject("resultData").getJSONObject("taxoffice").getString("taxofficeParentCode"));
//		model.setTaxofficeParentName(resultJson.getJSONObject("resultData").getJSONObject("taxoffice").getString("taxofficeParentName"));
        JSONObject acc=new JSONObject();
        acc.put("accessToken",model.getAccessToken());
        acc.put("phone",phone);
        List<String> lists=loginService.getZgswjDm(phone);
        acc.put("zgswjs",lists.toArray());
        JSONObject json2=new JSONObject();
        json2.put("code","99999");
        json2.put("info","success");
        json2.put("resultData",acc);
        return json2;
    }


    /**
     * 公众号用户登录
     *
     * @author cheng_chen
     * @date 2017-06-07 15:54:44
     * @param request
     * @param response
     * @param code
     */
    @RequestMapping(value = "/passport/openidLogin",method = RequestMethod.POST)
    public Object getUserPhone(HttpServletRequest request, HttpServletResponse response, String code) {
        if (org.springframework.util.StringUtils.isEmpty(code)) {
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", code);
            jsonResult.put("info", JoojeeCommonCode.getMessage(JoojeeCommonCode.MISSING_PARAMS));
            logger.info("--openidLogin返回值-->"+jsonResult);
            return jsonResult.toJSONString();
        }
        logger.info("/passport/openidLogin--code-->"+code);
        // 通过code获取微信用户的access_token信息
        String result = userService.getWxUserAccessToken(code);
        logger.info("/passport/openidLogin--code-->"+result);
        if (!org.springframework.util.StringUtils.isEmpty(JSON.parseObject(result).getString("errcode"))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", JSON.parseObject(result).getString("errcode"));
            jsonObject.put("info", JSON.parseObject(result).getString("errmsg"));
            //JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
            logger.info("--openidLogin返回值-->"+jsonObject);
            return jsonObject.toJSONString();
        }
        String accessToken = JSON.parseObject(result).getString("access_token");
        String openid = JSON.parseObject(result).getString("openid");
        logger.info("accessToken-->"+accessToken);
        logger.info("openid-->"+openid);
        // 通过access_token和openid和获取微信用户基本信息
        String result2 = userService.getWxUserUnionid(accessToken, openid);
        logger.info("通过access_token和openid和获取微信用户基本信息-->"+result2);
        String unionid = JSON.parseObject(result2).getString("unionid");

        // 税企通用户登录
        //JSONObject jsonObject = JSON.parseObject(UserOperRequest.userLogin(OPENID_LOGIN, "wechat", unionid));
        OpenidBean openidBean=loginService.getPhoneByOpenId(openid);
        JSONObject jsonObject=new JSONObject();
        Map<String,Object> map=new HashMap<String, Object>();
        if(openidBean!=null){
            List<String> lists=loginService.getZgswjDm(openidBean.getPhone());
            Boolean falg=loginService.getWxUserAuthStatus(openidBean.getPhone());
            logger.info("登录的返回值：" + jsonObject);
            jsonObject.put("code","99999");
            jsonObject.put("info","success");
            map.put("phone",openidBean.getPhone());
            map.put("zgswjs",lists.toArray());
            if(falg){
                map.put("smcj","Y");
            }else{
                map.put("smcj","N");
            }
            jsonObject.put("resultData",map);
            logger.info("--openidLogin返回值-->"+jsonObject);
            //JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
            return jsonObject.toJSONString();
        }else{
            jsonObject.put("code","40008");
            jsonObject.put("info","未绑定手机号");
            map.put("openid",openid);
            jsonObject.put("resultData",map);
            logger.info("--openidLogin返回值-->"+jsonObject);
            //JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
            return jsonObject.toJSONString();
        }
    }

    /**
     * 绑定身份证手机号
     *
     * @author cai_ming
     * @date 2017-06-07 15:54:44
     * @param request
     * @param response
     * @param
     */
    @RequestMapping("/passport/bingIdcardPhone")
    public void bingOpenidPhone(HttpServletRequest request, HttpServletResponse response, String phone,String idCard) {
        if (org.springframework.util.StringUtils.isEmpty(phone)|| StringUtils.isEmpty(idCard)||phone.equals("null")||phone.equals("undefined")) {
            JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
            return;
        }
        loginService.setWxUserAuthStatus(phone);
        JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.SUCCESS, response);
        return;
    }

    /**
     * 发送验证码
     * @author cheng_chen
     * @date 2017-06-07 15:54:08
     * @param request
     * @param response
     * @param phone
     */
    @RequestMapping("/get/authCode")
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response, String phone) {
        logger.info("/get/authCode--phone-->"+phone);
        if (org.springframework.util.StringUtils.isEmpty(phone)) {
            JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
            return;
        }

        String getAuthCodeUrl = GET_AUTH_CODE + "?phone=" + phone + "&authCodeType=loginAuthCode";
        String result = HttpsRequest.sendHttpsRequestByGet(getAuthCodeUrl);
        logger.info("发送验证码的返回值：" + result);
        JoojeeWebCommon.outputJSONResult(result, response);
    }

    /**
     * 验证码登录
     *
     * @author cheng_chen
     * @date 2017-06-07 15:54:16
     * @param request
     * @param response
     * @param phone
     * @param authCode
     */
    @RequestMapping(value = "/passport/authCodeLogin",method = RequestMethod.POST)
    public void verifyUserPhone(HttpServletRequest request, HttpServletResponse response, String phone, String authCode,
                                String openid) {
        logger.info("/passport/authCodeLogin--phone"+phone+"--authCode"+authCode+"--openid-->"+openid);
        if (org.springframework.util.StringUtils.isEmpty(phone) || org.springframework.util.StringUtils.isEmpty(authCode) || org.springframework.util.StringUtils.isEmpty(openid)) {
            JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.MISSING_PARAMS, response);
            return;
        }

        // 获取税企通用户信息
        JSONObject jsonObject =  JSON.parseObject(UserOperRequest.userLogin(AUTHCODE_LOGIN, phone, authCode));
        logger.info("-jsonObject->"+jsonObject.toJSONString());
        // 设置微信取号用户校验状态
        //JSONObject resultData = jsonObject.getJSONObject("resultData");
        //resultData.put("smcj", wxqhService.getWxUserAuthStatus(resultData.getJSONObject("user").getString("phone")));

        //关联用户体系
        if(JoojeeCommonCode.SUCCESS.equals(jsonObject.getString("code"))){
            //userService.unionUserPhone(phone, unionid);
            loginService.bingOpenidPhone(phone,openid);
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("phone",phone);
        map.put("openid",openid);
        jsonObject.put("resultData",map);
        JoojeeWebCommon.outputJSONResult(jsonObject.toJSONString(), response);
    }


    /**
     * 取号消息推送
     * @param json
     * @param request
     * @param response
     */
    @RequestMapping(value = "/qhxxPush",method = RequestMethod.POST)
    public void testPush(String json,HttpServletRequest request, HttpServletResponse response ){
        logger.info("qhxxPush-->"+json);
        JSONObject jsonObject=JSONObject.parseObject(json);
        OpenidBean openidBean=loginService.getOpneidByPhone(jsonObject.getString("phone"));
        logger.info("openidBean-->"+JSONObject.toJSONString(openidBean,true));
        String accessToken= userService.getWxAccessToken();
        String wxqhConfig=redisService.getRedisValueBykey(RedisKey.getWxqhCongifKey());

        JSONObject wxqhDtxxjson=JSONObject.parseObject(wxqhConfig);
        JSONArray jsonArray=wxqhDtxxjson.getJSONArray("resultData");
        JSONArray tsmbArray=new JSONArray();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject ob = (JSONObject) jsonArray.get(i);
            if(jsonObject.getString("bsfwtdm").equals(ob.getString("fwtDm"))){
                tsmbArray=ob.getJSONArray("tsgzs");
            }
        }
        logger.info("--tsmbArray-->"+tsmbArray);
        String mbstr="";
        for (int j = 0; j <tsmbArray.size() ; j++) {
            JSONObject mb = (JSONObject) tsmbArray.get(j);
            logger.info("--mb-->"+mb.toJSONString());
            if(mb.getString("tsgzDm").equals("01")){
                mbstr=mb.getString("tsmb");
            }
        }
        logger.info("--mbstr-->"+mbstr);
        //被替换关键字的的数据源
        Map<String,String> tokens = new HashMap<String,String>();
        tokens.put("#电子票号#", jsonObject.getString(""));
        tokens.put("#业务名称#", jsonObject.getString(""));
        tokens.put("#最新受理号#", jsonObject.getString(""));
        tokens.put("#排队等候时间#", jsonObject.getString(""));
        tokens.put("#排队等候人数#", jsonObject.getString(""));
        tokens.put("#可办理窗口#", jsonObject.getString(""));

        Iterator<Map.Entry<String, String>> it = tokens.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if(mbstr.contains(entry.getKey())){
                mbstr.replaceAll(entry.getKey(),entry.getValue());
            }
        }
        loginService.pushWxxx(accessToken,openidBean.getOpenid(),mbstr);
        JoojeeWebCommon.outPutErrorCodeJson(JoojeeCommonCode.SUCCESS, response);
        return ;
    }



    public static void main(String[] args) {
        try {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String bDate = "2011-12-31";
            Date bdate = format1.parse(bDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(bdate);
            if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
            {
                System.out.print("ok");
            }
            else {
                System.out.print("no");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
