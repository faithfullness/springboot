package cn.joojee.wxqh.model;

/**
 * 微信取号办税服务厅信息
 * Created by Administrator on 2017/8/1.
 */
public class WxqhFwtBean {

    //主管税务局代码
    private String zgswjDm;
    //办税服务厅代码
    private String fwtDm;
    //办税服务厅名称
    private String fwtMc;
    //办税服务厅状态
    private String fwtZt;
    //不可取号原因
    private String bkqhyy;
    //办税服务厅地址
    private String fwtDz;
    //服务厅经度
    private String fwtJd;
    //服务厅维度
    private String fwtWd;
    //友情提示状态
    private String yqtsZt;
    //友情提示内容
    private String yqtsNr;
    //剩余票数
    private String syps;

    public String getZgswjDm() {
        return zgswjDm;
    }

    public void setZgswjDm(String zgswjDm) {
        this.zgswjDm = zgswjDm;
    }

    public String getFwtDm() {
        return fwtDm;
    }

    public void setFwtDm(String fwtDm) {
        this.fwtDm = fwtDm;
    }

    public String getFwtMc() {
        return fwtMc;
    }

    public void setFwtMc(String fwtMc) {
        this.fwtMc = fwtMc;
    }

    public String getFwtZt() {
        return fwtZt;
    }

    public void setFwtZt(String fwtZt) {
        this.fwtZt = fwtZt;
    }

    public String getFwtDz() {
        return fwtDz;
    }

    public void setFwtDz(String fwtDz) {
        this.fwtDz = fwtDz;
    }

    public String getFwtJd() {
        return fwtJd;
    }

    public void setFwtJd(String fwtJd) {
        this.fwtJd = fwtJd;
    }

    public String getFwtWd() {
        return fwtWd;
    }

    public void setFwtWd(String fwtWd) {
        this.fwtWd = fwtWd;
    }

    public String getYqtsZt() {
        return yqtsZt;
    }

    public void setYqtsZt(String yqtsZt) {
        this.yqtsZt = yqtsZt;
    }

    public String getYqtsNr() {
        return yqtsNr;
    }

    public void setYqtsNr(String yqtsNr) {
        this.yqtsNr = yqtsNr;
    }

    public String getBkqhyy() {
        return bkqhyy;
    }

    public void setBkqhyy(String bkqhyy) {
        this.bkqhyy = bkqhyy;
    }

    public String getSyps() {
        return syps;
    }

    public void setSyps(String syps) {
        this.syps = syps;
    }
}
