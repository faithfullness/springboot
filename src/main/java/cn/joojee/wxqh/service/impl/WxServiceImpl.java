package cn.joojee.wxqh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.joojee.wxqh.mapper.WxMapper;
import cn.joojee.wxqh.service.WxService;

@Service
public class WxServiceImpl implements WxService {

	@Autowired
	private WxMapper wxMapper;

	@Override
	public String getUserPhone(String unionid) {
		return wxMapper.selectPhoneByUnionid(unionid);
	}

	@Override
	public boolean verifyUserPhone(String phone) {
		return wxMapper.selectPhone(phone) > 0 ? true : false;
	}

	@Override
	public boolean unionUserPhone(String phone, String unionid) {
		try {
			return wxMapper.insertUserUnionid(phone, unionid) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

}
