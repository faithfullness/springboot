package cn.joojee.wxqh.model;

/**
 * 微信取号业务信息
 * Created by Administrator on 2017/8/2.
 */
public class WxqhYwBean {

    //业务代码
    private String ywdm;
    //业务名称
    private String ywmc;
    //当前受理号
    private String dqslh;
    //排队等候人数
    private String pddhrs;
    //等待时间
    private String ddsj;
    //业务状态
    private String ywzt;
    //不可取号原因
    private String bkqhyy;
    //当前业务剩余票号
    private String syph;
    //票号状态
    private String phzt;
    //取号时间
    private String qhsj;
    //当前用户的票号
    private String dqph;
    //用户当前票号的id
    private String id;

    public String getYwdm() {
        return ywdm;
    }

    public void setYwdm(String ywdm) {
        this.ywdm = ywdm;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getDqslh() {
        return dqslh;
    }

    public void setDqslh(String dqslh) {
        this.dqslh = dqslh;
    }

    public String getPddhrs() {
        return pddhrs;
    }

    public void setPddhrs(String pddhrs) {
        this.pddhrs = pddhrs;
    }

    public String getDdsj() {
        return ddsj;
    }

    public void setDdsj(String ddsj) {
        this.ddsj = ddsj;
    }

    public String getYwzt() {
        return ywzt;
    }

    public void setYwzt(String ywzt) {
        this.ywzt = ywzt;
    }

    public String getBkqhyy() {
        return bkqhyy;
    }

    public void setBkqhyy(String bkqhyy) {
        this.bkqhyy = bkqhyy;
    }

    public String getSyph() {
        return syph;
    }

    public void setSyph(String syph) {
        this.syph = syph;
    }

    public String getPhzt() {
        return phzt;
    }

    public void setPhzt(String phzt) {
        this.phzt = phzt;
    }

    public String getQhsj() {
        return qhsj;
    }

    public void setQhsj(String qhsj) {
        this.qhsj = qhsj;
    }

    public String getDqph() {
        return dqph;
    }

    public void setDqph(String dqph) {
        this.dqph = dqph;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
