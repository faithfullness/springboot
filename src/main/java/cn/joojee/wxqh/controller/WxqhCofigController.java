package cn.joojee.wxqh.controller;

import cn.joojee.wxqh.model.ResultBody;
import cn.joojee.wxqh.model.WxqhFwtBean;
import cn.joojee.wxqh.model.WxqhYwBean;
import cn.joojee.wxqh.service.RedisService;
import cn.joojee.wxqh.service.WxqhConfigService;
import cn.joojee.wxqh.utils.HttpsRequest;
import cn.joojee.wxqh.utils.JoojeeCommonCode;
import cn.joojee.wxqh.utils.RedisKey;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joojee.usercenter.client.JoojeeUserCenterResult;
import com.joojee.usercerter.oauth.service.ServiceProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.joojee.wxqh.utils.holiday.request;

/**
 * 获取微信取号相关配置信息
 * Created by Administrator on 2017/7/24.
 */
@RestController
@RequestMapping("/api/wxqhConfig")
public class WxqhCofigController {

    private static final Logger logger= LoggerFactory.getLogger(WxqhCofigController.class);

    @Value("${wxqhConfigUrl}")
    private  String wxqhConfigUrl;

    @Value("${wxqhFwtQhxxUrl}")
    private  String wxqhFwtQhxxUrl;

    @Value("${wxqhFwtYwQhxxUrl}")
    private  String wxqhFwtYwQhxxUrl;

    @Value("${wxqhUrl}")
    private  String wxqhUrl;

    @Value("${qxWxqhUrl}")
    private  String qxWxqhUrl;

    @Value("${WxqhLsxxUrl}")
    private  String WxqhLsxxUrl;

    @Value("${pjWxqhUrl}")
    private  String pjWxqhUrl;

    @Autowired
    private RedisService redisService;

    @Autowired
    private WxqhConfigService wxqhConfigService;

    @Autowired
    private ServiceProvider joojeeOauthService;

    @RequestMapping(value = "/updateWxqhCofig",method = RequestMethod.POST)
    public Object getWxqhCofigByIntranet(String json){
        logger.info("--监听到被更改的参数--->"+json);
        if(StringUtils.isEmpty(json)){
                return "";
        }
        Map<String, String> requestParamMapr=new HashMap<String, String>();
        requestParamMapr.put("param",json);
        //到底层查询更改的微信取号基础信息
        String wxqhConfig=HttpsRequest.sendHttpsRequestByPost(wxqhConfigUrl,requestParamMapr);
        //logger.info("--在底层获取到微信取号基础信息--->"+wxqhConfig);
        JSONObject jsonObject=JSONObject.parseObject(json);
        JSONObject wxqhJson=JSONObject.parseObject(wxqhConfig);
        //如果在底层获取到了正确的信息则将其写入到redis
        if(wxqhJson.getString("code").equals("99999")){
            if(jsonObject.getString("type").equals("1")){
                //如果更改的是大厅的基础信息
                redisService.updateWxqhConfig(wxqhConfig);
            }else if(jsonObject.getString("type").equals("2")){
                //如果更改的是某个大厅业务的基础信息
                redisService.updateWxqhYwConfig(wxqhConfig,jsonObject.getString("bsfwtDm"));
            }
            //将微信基础信息的更改通过websocket推送到客户端
            if(!StringUtils.isEmpty(json)){
                wxqhConfigService.pushFriendMessage(jsonObject);
            }
        }
        return "";
    }

    /**
     * 获取微信取号办税服务厅信息
     * @return
     */
    @RequestMapping("/getWxqhDtxxCofig")
    public Object getWxqhCofigByRedis(String phone){
        try{
            logger.info("getWxqhDtxxCofig接受到的参数-->"+phone);
            if(StringUtils.isEmpty(phone)||phone.equals("null")||phone.equals("undefined")){
               return  new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
            }
            /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
            logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
            if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
                return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
            }
            String phone = (String) result.getObject();*/
            JSONObject json=new JSONObject();
            //从redis中获取办税服务厅基础配置信息
            String wxqhConfig=redisService.getRedisValueBykey(RedisKey.getWxqhCongifKey());
            JSONObject wxqhjson=JSONObject.parseObject(wxqhConfig);
            String resultData=wxqhjson.getString("resultData");

            //将其转化为OBJECT
            List<WxqhFwtBean> collection = JSONObject.parseArray(resultData, WxqhFwtBean.class);//把字符串转换成集合
            //判断是否是节假日
            SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
            String httpArg=f.format(new Date());
            logger.info("当前日期-->"+httpArg);

            //调用百度API判断是否是节假日
            //返回结果中工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
            String jsonResult = request(httpArg);
            logger.info("--jsonResult-->"+jsonResult);
            logger.info("--是否是工作日-->"+jsonResult);
            if(!StringUtils.isEmpty(jsonResult)){
                logger.info("--jsonResult-->"+jsonResult+"--");
                if (jsonResult!=null) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(jsonResult);
                    jsonResult = m.replaceAll("");
                }
                //如果不是工作日，将每个办税服务厅可取号标志改为N并且将办税服务厅数据返回
                if(!jsonResult.equals("0")){
                    for (WxqhFwtBean wxqhFwtBean: collection) {
                        wxqhFwtBean.setFwtZt("N");
                        wxqhFwtBean.setBkqhyy("0");
                    }
                    json.put("code","99999");
                    json.put("info","success");
                    json.put("resultData",collection);
                    return collection;
                }
            }

            //根据电话号码在该大厅到底层查今天微信取号是否达到上限及当前用户为办理数是否达到上限
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("phone",phone);
            JSONObject jsonObject = JSON.parseObject(HttpsRequest.sendHttpsRequestByPost(wxqhFwtQhxxUrl, paramMap));
            logger.info("--底层取号信息-->"+jsonObject);

            //如果通过底层查出来数据正常
            if(jsonObject.getString("code").equals("99999")){
                JSONArray jsonArray=jsonObject.getJSONArray("resultData");

                //循环比对底层查询返回上来的大厅是否还能取号
                Iterator<WxqhFwtBean> it = collection.iterator();
                while(it.hasNext()){
                    WxqhFwtBean x = it.next();
                    if(!x.getFwtDm().contains("1420109")){
                        it.remove();
                    }
                }
                for (WxqhFwtBean wxqhFwtBean: collection) {
                    for (int i=0;i<jsonArray.size();i++){
                        JSONObject ob = (JSONObject) jsonArray.get(i);
                        if(wxqhFwtBean.getFwtDm().equals(ob.getString("bsfwtDm"))){
                            wxqhFwtBean.setFwtZt(ob.getString("qhzt"));
                            wxqhFwtBean.setSyps(ob.getString("syps"));
                            if(ob.getString("qhzt").equals("N")){
                                wxqhFwtBean.setBkqhyy(ob.getString("bkqhYy"));
                            }
                        }
                    }
                }
            }
            json.put("code","99999");
            json.put("info","success");
            json.put("resultData",collection);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject json=new JSONObject();
            json.put("code","99998");
            json.put("info","服务器异常");
            return json;
        }
    }

    /**
     * 根据办税服务厅代码获取业务信息
     * @return
     */
    @RequestMapping("/getWxqhYwxxCofig")
    public Object getWxqhYwxxCofig(String bsfwtDm,String phone){
        try{
            if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(bsfwtDm)||phone.equals("null")||phone.equals("undefined")||bsfwtDm.equals("null")||bsfwtDm.equals("undefined")){
                return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
            }
            logger.info("getWxqhDtxxCofig接受到的参数-->"+phone);
            /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
            logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
            if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
                return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
            }
            String phone = (String) result.getObject();*/
            logger.info("getWxqhDtxxCofig接受到的参数-->"+phone+bsfwtDm);
            JSONObject json=new JSONObject();
            if(StringUtils.isEmpty(bsfwtDm)||StringUtils.isEmpty(phone)){
                json.put("code","99998");
                json.put("info","参数不全");
                return json;
            }

            //从redis中获取办税服务厅基础配置信息
            String wxqh1=redisService.getRedisValueBykey(RedisKey.getWxqhYwCongifKey(bsfwtDm));
            logger.info("redis的返回值-wxqh1->"+ wxqh1+"--<");
            if(StringUtils.isEmpty(wxqh1)){
                Map<String, String> requestParamMapr=new HashMap<String, String>();
                JSONObject paramjson=new JSONObject();
                paramjson.put("type","2");
                paramjson.put("bsfwtDm",bsfwtDm);
                requestParamMapr.put("param",paramjson.toJSONString());
                wxqh1=HttpsRequest.sendHttpsRequestByPost(wxqhConfigUrl,requestParamMapr);
                logger.info("wxqhConfig"+wxqh1);
                redisService.updateWxqhYwConfig(wxqh1,bsfwtDm);
            }
            JSONObject wxqhjson=JSONObject.parseObject(wxqh1);
            String resultData=wxqhjson.getString("resultData");


            //将其转化为OBJECT
            List<WxqhYwBean> collection = JSONObject.parseArray(resultData, WxqhYwBean.class);//把字符串转换成集合


            //根据电话号码在该大厅到底层查今天微信取号是否达到上限及当前用户为办理数是否达到上限
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("phone",phone);
            paramMap.put("fwtDm",bsfwtDm);
            JSONObject jsonObject = JSON.parseObject(HttpsRequest.sendHttpsRequestByPost(wxqhFwtYwQhxxUrl, paramMap));
            logger.info("--底层取号信息-->"+jsonObject);

            //如果通过底层查出来数据正常
            if(jsonObject.getString("code").equals("99999")){
                JSONArray jsonArray=jsonObject.getJSONArray("resultData");

                //循环比对底层查询返回上来的大厅是否还能取号
                for (WxqhYwBean wxqhYwBean: collection) {
                    for (int i=0;i<jsonArray.size();i++){
                        JSONObject ob = (JSONObject) jsonArray.get(i);
                        if(wxqhYwBean.getYwdm().equals(ob.getString("ywdm"))){
                            wxqhYwBean.setYwzt(ob.getString("ywzt"));
                            wxqhYwBean.setSyph(ob.getString("syps"));
                            wxqhYwBean.setDdsj(ob.getString("ddsj"));
                            wxqhYwBean.setPddhrs(ob.getString("ddrs"));
                            wxqhYwBean.setDqslh(ob.getString("zxslh"));
                            wxqhYwBean.setPhzt(ob.getString("phzt"));
                            wxqhYwBean.setDqph(ob.getString("dqph"));
                            wxqhYwBean.setQhsj(ob.getString("qhsj"));
                            wxqhYwBean.setId(ob.getString("id"));
                            if(ob.getString("ywzt").equals("N")){
                                wxqhYwBean.setBkqhyy(ob.getString("bkqhYy"));
                            }
                        }
                    }
                }

            }
            json.put("code","99999");
            json.put("info","success");
            json.put("resultData",collection);
            return json;
        }catch (Exception e){
            e.printStackTrace();
            JSONObject json=new JSONObject();
            json.put("code","99998");
            json.put("info","服务器异常");
            return json;
        }
    }


    /**
     * 微信取号
     * @param
     * @param bsfwtDm
     * @param ywdm
     * @return
     */
    @RequestMapping(value = "/wxqh",method = RequestMethod.POST)
    public Object getWxqh(String phone,String bsfwtDm,String ywdm){
        logger.info("getWxqh-->"+phone);
        logger.info("getWxqh-->"+bsfwtDm);
        logger.info("getWxqh-->"+ywdm);
        if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(bsfwtDm)||StringUtils.isEmpty(ywdm)){
            return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
        }
        /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
        logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
        if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
            return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
        }
        String phone = (String) result.getObject();*/
        logger.info("getWxqh-->"+phone+bsfwtDm);
        JSONObject json=new JSONObject();
        if(StringUtils.isEmpty(bsfwtDm)||StringUtils.isEmpty(phone)){
            json.put("code","99998");
            json.put("info","参数不全");
            return json;
        }
        //调用底层接口微信取号
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("phone",phone);
        paramMap.put("accessToken",phone);
        paramMap.put("bsfwtDm",bsfwtDm);
        paramMap.put("ywdm",ywdm);
        String wxqh1=HttpsRequest.sendHttpsRequestByPost(wxqhUrl, paramMap);
        return wxqh1;
    }

    /**
     * 取消微信取号
     * @param
     * @param id
     * @return
     */
    @RequestMapping(value = "/qxWxqh",method = RequestMethod.POST)
    public Object getWxqh(String phone,String id){
        logger.info("qxWxqh-->"+phone);
        logger.info("qxWxqh-->"+id);
        if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(id)){
            return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
        }
        /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
        logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
        if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
            return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
        }
        String phone = (String) result.getObject();*/
        //调用底层接口微信取号
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("accessToken",phone);
        paramMap.put("uuid",id);
        String wxqh1=HttpsRequest.sendHttpsRequestByPost(qxWxqhUrl, paramMap);
        return wxqh1;
    }

    /**
     * 获取用户历史票号
     * @param
     * @return
     */
    @RequestMapping(value = "/getWxqhLsxx",method = RequestMethod.GET)
    public Object getWxqhLsxx(String phone){
        logger.info("qxWxqh-->"+phone);
        if(StringUtils.isEmpty(phone)||phone.equals("null")||phone.equals("undefined")){
            return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
        }
        /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
        logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
        if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
            return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
        }
        String phone = (String) result.getObject();*/
        //调用底层接口微信取号
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("phone",phone);
        String wxqh1=HttpsRequest.sendHttpsRequestByPost(WxqhLsxxUrl, paramMap);
        return wxqh1;
    }

    /**
     * 评价微信取号
     * @param
     * @param id
     * @return
     */
    @RequestMapping(value = "/pjWxqh",method = RequestMethod.POST)
    public Object pjWxqh(String phone,String id,String pjzt){
        logger.info("accessToken-->"+phone);
        logger.info("id-->"+id);
        logger.info("pjzt-->"+pjzt);
        if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(id)||StringUtils.isEmpty(pjzt)){
            return new ResultBody(JoojeeCommonCode.MISSING_PARAMS);
        }
        /*JoojeeUserCenterResult result = joojeeOauthService.getPhoneByAccessToken(accessToken);
        logger.info("userCenter返回的根据accessToken获取的用户信息:" + result);
        if (null == result || result.getCode() != JoojeeUserCenterResult.OK.getCode()) {
            return new ResultBody(JoojeeCommonCode.NEED_LOGIN);
        }
        String phone = (String) result.getObject();*/
        JSONObject json=new JSONObject();
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(pjzt)){
            json.put("code","99998");
            json.put("info","参数不全");
            return json;
        }
        //调用底层接口微信取号
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("accessToken",phone);
        paramMap.put("uuid",id);
        paramMap.put("pjzt",pjzt);
        String wxqh1=HttpsRequest.sendHttpsRequestByPost(pjWxqhUrl, paramMap);
        return wxqh1;
    }

}
