package cn.joojee.wxqh.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface WxqhConfigService {

    /**
     * 推送微信取号配置修改消息
     */
    public	void pushFriendMessage(JSONObject json);


    /**
     *根据电话号码查出该用户主管主管税务局
     * @param phone
     * @return
     */
    public List<String> getZgswjsByPhone(String phone);

}
