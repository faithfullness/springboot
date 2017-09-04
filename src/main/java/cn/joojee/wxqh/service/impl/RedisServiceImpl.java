package cn.joojee.wxqh.service.impl;

import cn.joojee.wxqh.service.RedisService;
import cn.joojee.wxqh.utils.RedisKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
public class RedisServiceImpl implements RedisService {

    /** 获取redis连接池配置类 */
    @Autowired
    private JedisPool jedisPool;

    /**
     * 将传进来的配置信息更新至微信取号大厅的key中
     * @param config
     * @return
     */
    @Override
    public Boolean updateWxqhConfig(String config) {
        Jedis jedis = jedisPool.getResource();
        try{
            System.out.println("-->"+config);
            jedis.set(RedisKey.getWxqhCongifKey(),config);
            return true;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    @Override
    public String getRedisValueBykey(String key) {
        Jedis jedis = jedisPool.getResource();
        try{
            String value=jedis.get(key);
            return value;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    public boolean isKeyExist(String key) {
        Jedis jedis = jedisPool.getResource();
        try{
            if(StringUtils.isEmpty(key)){
                return Boolean.FALSE;
            }
            return jedis.exists(key);
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 将传进来的配置信息写入到对应的办税服务厅的key中
     * @param config
     * @param bsfwtDm
     * @return
     */
    @Override
    public Boolean updateWxqhYwConfig(String config, String bsfwtDm) {
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(RedisKey.getWxqhYwCongifKey(bsfwtDm),config);
            return true;
        }finally{
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
