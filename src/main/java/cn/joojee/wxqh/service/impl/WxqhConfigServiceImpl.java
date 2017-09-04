package cn.joojee.wxqh.service.impl;

import cn.joojee.wxqh.service.WxqhConfigService;
import cn.joojee.wxqh.utils.HttpsRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
public class WxqhConfigServiceImpl implements WxqhConfigService{

    @Override
    public void pushFriendMessage(JSONObject json) {
        Map<String, String> keyValueParams = new HashMap<String,String>();
        keyValueParams.put("roomId", "WXQH");
        keyValueParams.put("message", json.toString());
        System.out.println("keyValueParams"+JSONObject.toJSONString(keyValueParams,true));
        String wxqh= HttpsRequest.sendHttpsRequestByPost("https://websocket.joojee.cn/api/pushChatMessage", keyValueParams);
        System.out.println("--pushChatMessage-->"+wxqh);
    }

    @Override
    public List<String> getZgswjsByPhone(String phone) {
        return null;
    }
}
