package cn.joojee.wxqh.controller;

import cn.joojee.wxqh.mapper.TestMapper;
import cn.joojee.wxqh.service.RedisService;
import cn.joojee.wxqh.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/8/24.
 */

@RestController
public class TestController {

    @Autowired
    TestMapper testMapper;

    @Autowired
    RedisService redisService;


    @RequestMapping("/test")
    public Object getAuthCode(HttpServletRequest request, HttpServletResponse response, String phone) {
        boolean a=WebStringUtils.isEmpty(phone);
        System.out.println("a的值："+a);
        System.out.println("request.getRemoteUser();"+request.getRemoteUser());
        return redisService.getRedisValueBykey(RedisKey.getWxqhCongifKey());
    }
}
