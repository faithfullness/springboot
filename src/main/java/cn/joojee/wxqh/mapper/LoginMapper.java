package cn.joojee.wxqh.mapper;

import cn.joojee.wxqh.model.OpenidBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
@Mapper
public interface LoginMapper {

    /**
     * 通过账号密码登录
     *
     * @author
     * @date 2017-06-07 11:30:31
     * @param
     * @return
     */
    @Select("SELECT count(1) FROM tbl_joojee_user WHERE phone=#{phone} AND PASSWORD=#{password} ")
    public Integer selectPhoneByUnionid(@Param("phone") String phone, @Param("password")String password);


    /**
     * 根据纳税人电话号码获取主管税务机关
     * @param phone
     * @return
     */
    @Select("SELECT distinct a.zgswj_dm FROM gt3_nsrxx a,gt3_nsrxx_lxr b WHERE a.djxh=b.djxh AND b.phone=#{phone} ")
    public List<String> getZgswjDmByPhone(String phone);


    /**
     * 根据微信OPENID获取用户手机号
     * @param
     * @return
     */
    @Select("SELECT * FROM sqt_user_openid WHERE openid=#{openid} and auth_channel='wechat' and app_type='wxqh' ")
    @Results({
            @Result(column = "phone",property = "phone"),
            @Result(column = "openid",property = "openid"),
            @Result(column = "auth_channel",property = "authChannel"),
            @Result(column = "app_type",property = "appType"),
    })
    public OpenidBean getPhoneByOpenId(String openid);

    /**
     * 根据PHONE获取微信openid
     * @param
     * @return
     */
    @Select("SELECT * FROM sqt_user_openid WHERE phone=#{phone} and auth_channel='wechat' and app_type='wxqh' ")
    @Results({
            @Result(column = "phone",property = "phone"),
            @Result(column = "openid",property = "openid"),
            @Result(column = "auth_channel",property = "authChannel"),
            @Result(column = "app_type",property = "appType"),
    })
    public OpenidBean getOpenidByPhone(String phone);

    /**
     * 更改openid对应的phone
     * @param
     * @return
     */
    @Update("update  sqt_user_openid set phone =#{phone}  WHERE phone=#{openid} ")
    public Integer updateOpenidPhone(OpenidBean openidBean);

    /**
     * 新增openid对应的phone
     * @param
     * @return
     */
    @Insert(" INSERT INTO sqt_user_openid(phone,openid,auth_channel,app_type,createtime) VALUES(#{phone},#{openid},#{authChannel},#{appType},NOW()) ")
    public Integer insertOpenidPhone(OpenidBean openidBean);

    /**
     * 插入微信叫号用户的身份校验状态
     *
     * @author cheng_chen
     * @date 2017-06-23 09:46:21
     * @param phone
     * @return
     */
    @Insert("INSERT INTO wxqh_auth(phone,createtime) VALUES(#{phone},NOW())")
    int insertUserAuthStatus(String phone);

    /**
     * 查询微信叫号用户的身份校验状态
     *
     * @author cheng_chen
     * @date 2017-06-23 09:52:44
     * @param phone
     * @return
     */
    @Select("SELECT COUNT(*) FROM wxqh_auth WHERE phone=#{phone}")
    Integer selectUserAuthStatus(String phone);
}
