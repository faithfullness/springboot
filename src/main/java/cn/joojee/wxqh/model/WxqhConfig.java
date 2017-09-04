package cn.joojee.wxqh.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author cheng_chen
 * @date 2017-06-22 14:26:53
 */
public class WxqhConfig {


	private String phone;
	/** 分组名称 */
	private String fzmc;
	/** 分组代码 */
	private String fzdm;
	/** 大厅代码 */
	private String dtdm;
	/** 可取号时间起 */
	private Time kqhsj_q;
	/** 可取号时间止 */
	private Time kqhsj_z;
	/** 总号数 */
	private int total;
	/** 是否可取号时间 */
	private boolean kqhsj;
	/** 是否有可取号 */
	private boolean ykqh;
	/** 取号时间 */
	private Date qh_time;
	/** 办理标志 1：已办理 0：未办理 NULL：初始化状态 */
	private int blbz;

	public int getBlbz() {
		return blbz;
	}

	public void setBlbz(int blbz) {
		this.blbz = blbz;
	}

	public Date getQh_time() {
		return qh_time;
	}

	public void setQh_time(Date qh_time) {
		this.qh_time = qh_time;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isKqhsj() {
		return kqhsj;
	}

	public void setKqhsj(boolean kqhsj) {
		this.kqhsj = kqhsj;
	}

	public boolean isYkqh() {
		return ykqh;
	}

	public void setYkqh(boolean ykqh) {
		this.ykqh = ykqh;
	}

	public String getFzmc() {
		return fzmc;
	}

	public void setFzmc(String fzmc) {
		this.fzmc = fzmc;
	}

	public String getFzdm() {
		return fzdm;
	}

	public void setFzdm(String fzdm) {
		this.fzdm = fzdm;
	}

	public String getDtdm() {
		return dtdm;
	}

	public void setDtdm(String dtdm) {
		this.dtdm = dtdm;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Time getKqhsj_q() {
		return kqhsj_q;
	}

	public void setKqhsj_q(Time kqhsj_q) {
		this.kqhsj_q = kqhsj_q;
	}

	public Time getKqhsj_z() {
		return kqhsj_z;
	}

	public void setKqhsj_z(Time kqhsj_z) {
		this.kqhsj_z = kqhsj_z;
	}
}
