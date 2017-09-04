package cn.joojee.wxqh.mapper;

import org.apache.ibatis.annotations.*;

import cn.joojee.wxqh.model.WxqhConfig;
@Mapper
public interface WxqhMapper {

	/**
	 * 查询用户当天是否取过业务号
	 *
	 * @author cheng_chen
	 * @date 2017-06-20 14:52:08
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM wxqh_log WHERE TO_DAYS(qh_time) = TO_DAYS(now()) AND fzdm=#{fzdm} AND phone=#{phone}")
	int selectUserTackNumOnDay(@Param("fzdm") String fzdm, @Param("phone") String phone);

	/**
	 * 查询用户当月未办理的次数
	 *
	 * @author cheng_chen
	 * @date 2017-06-20 15:11:28
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM wxqh_log WHERE DATE_FORMAT( qh_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) AND blbz=0 AND phone=#{phone}")
	int selectUserUndoneOnMonth(String phone);

	/**
	 * 查询取号配置信息
	 *
	 * @author cheng_chen
	 * @date 2017-06-22 12:06:30
	 * @param fzdm
	 * @return
	 */
	@Select("SELECT * FROM wxqh_config WHERE fzdm=#{fzdm}")
	WxqhConfig selectTakeNumConfig(String fzdm);

	/**
	 * 查询用户的业务办理状态
	 *
	 * @author cheng_chen
	 * @date 2017-06-26 12:08:09
	 * @return
	 */
	@Select("SELECT blbz,qh_time FROM wxqh_log WHERE phone=#{phone} AND fzdm=#{fzdm} AND TO_DAYS(qh_time) = TO_DAYS(now())")
	WxqhConfig selectHandleStatus(WxqhConfig wxqhConfig);

	/**
	 * 查询当天已取号总数
	 *
	 * @author cheng_chen
	 * @date 2017-06-22 14:37:38
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM wxqh_log WHERE TO_DAYS(qh_time) = TO_DAYS(NOW()) AND fzdm=#{fzdm}")
	int selectTakeNumOnDay(String fzdm);

	/**
	 * 插入用户的取号信息
	 *
	 * @author cheng_chen
	 * @date 2017-06-26 10:42:15
	 * @param wxqhConfig
	 * @return
	 */
	@Insert("INSERT INTO wxqh_log(phone,qh_time,dtdm,fzdm,createtime) VALUES(#{phone},#{qh_time},#{dtdm},#{fzdm},NOW())")
	int insertUserTakeNumInfo(WxqhConfig wxqhConfig);

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
	int selectUserAuthStatus(String phone);

	/**
	 * 更新确认办理信息
	 *
	 * @author cheng_chen
	 * @date 2017-06-26 09:59:15
	 * @return
	 */
	@Update("UPDATE wxqh_log SET blbz='1' WHERE phone=#{phone} AND fzdm=#{fzdm} ORDER BY id DESC LIMIT 1")
	int updateConfirmHandleInfo(WxqhConfig wxqhConfig);

	/**
	 * 每天6点钟更新所有未办理订单状态
	 * 
	 * @author cheng_chen
	 * @date 2017-06-29 12:04:18
	 * @return
	 */
	@Update("UPDATE wxqh_log SET blbz='0' WHERE blbz IS NULL")
	int updateNotHandleUser();
}
