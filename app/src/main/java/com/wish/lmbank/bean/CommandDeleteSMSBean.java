package com.wish.lmbank.bean;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/CommandDeleteSMSBean.class */
public class CommandDeleteSMSBean {
    private String data;
    private String data_multi;
    private String deviceId;
    private int userId;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getData_multi() {
        return this.data_multi;
    }

    public void setData_multi(String str) {
        this.data_multi = str;
    }
}
