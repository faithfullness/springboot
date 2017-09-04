package cn.joojee.wxqh.service;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface RedisService {


    /**
     * 将新查到的微信取号办税服务厅配置信息写入到redis
     * @param config
     * @return
     */
    public Boolean updateWxqhConfig(String config);

    /**
     * 根据key查询key的值
     * @param
     * @return
     */
    public String getRedisValueBykey(String key);

    /**
     * 将新查到的微信取号办税服务厅业务配置信息写入到redis
     * @param config
     * @param bsfwtDm
     * @return
     */
    public Boolean updateWxqhYwConfig(String config,String bsfwtDm);

}
