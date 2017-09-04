package cn.joojee.wxqh.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface WxMapper {

	/**
	 * 通过微信的unionid查询税企通账号
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 11:30:31
	 * @param unionid
	 * @return
	 */
	@Select("SELECT phone FROM sqt_user_wx WHERE unionid=#{unionid}")
	String selectPhoneByUnionid(String unionid);

	/**
	 * 查询用户手机号是否注册税企通
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 14:53:28
	 * @param phone
	 * @return
	 */
	@Select("SELECT count(*) FROM tbl_sqt_user WHERE phone=#{phone}")
	int selectPhone(String phone);

	/**
	 * 插入税企通和微信的绑定信息
	 * 
	 * @author cheng_chen
	 * @date 2017-06-07 16:19:50
	 * @param phone
	 * @param unionid
	 * @return
	 */
	@Insert("INSERT INTO sqt_user_wx(phone,unionid,createtime) VALUES(#{phone},#{unionid},now()) ON DUPLICATE KEY UPDATE unionid=#{unionid}")
	int insertUserUnionid(@Param("phone") String phone, @Param("unionid") String unionid);
}
