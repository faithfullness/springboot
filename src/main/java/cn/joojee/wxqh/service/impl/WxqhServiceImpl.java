package cn.joojee.wxqh.service.impl;

import java.sql.Time;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.joojee.wxqh.mapper.WxqhMapper;
import cn.joojee.wxqh.model.WxqhConfig;
import cn.joojee.wxqh.service.WxqhService;

@Service
public class WxqhServiceImpl implements WxqhService {

	@Autowired
	private WxqhMapper wxqhMapper;

	@Override
	public boolean verifyUserPermissionsOnDay(String fzdm, String phone) {
		return wxqhMapper.selectUserTackNumOnDay(fzdm, phone) < 1;
	}

	@Override
	public boolean verifyUserPermissionsOnMonth(String phone) {
		return wxqhMapper.selectUserUndoneOnMonth(phone) < 2;
	}

	@Override
	public WxqhConfig getTakeNumInfo(String fzdm) {
		WxqhConfig wxqhConfig = wxqhMapper.selectTakeNumConfig(fzdm);
		if (wxqhConfig == null) {
			return null;
		}
		// 判断是否在工作时间
		Time kqhsj_q = wxqhConfig.getKqhsj_q();
		Time kqhsj_z = wxqhConfig.getKqhsj_z();

		String h = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		String m = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
		String s = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
		Time now = Time.valueOf(h + ":" + m + ":" + s);

		wxqhConfig.setKqhsj(now.getTime() < kqhsj_z.getTime() && now.getTime() > kqhsj_q.getTime());

		// 计算余号
		int num = wxqhMapper.selectTakeNumOnDay(fzdm);
		wxqhConfig.setYkqh(wxqhConfig.getTotal() - num > 0);

		return wxqhConfig;
	}

	@Override
	public void setWxUserAuthStatus(String phone) {
		wxqhMapper.insertUserAuthStatus(phone);
	}

	@Override
	public boolean getWxUserAuthStatus(String phone) {
		return wxqhMapper.selectUserAuthStatus(phone) > 0;
	}

	@Override
	public boolean confirmHandle(WxqhConfig wxqhConfig) {
		return wxqhMapper.updateConfirmHandleInfo( wxqhConfig) > 0;
	}

	@Override
	public boolean setTakeNumInfo(WxqhConfig wxqhConfig) {
		return wxqhMapper.insertUserTakeNumInfo(wxqhConfig)>0;
	}

	@Override
	public WxqhConfig getHandleStatus(WxqhConfig wxqhConfig) {
		return wxqhMapper.selectHandleStatus(wxqhConfig);
	}

	@Override
	public void updateNotHandleStatus() {
		 wxqhMapper.updateNotHandleUser();
	}
}
