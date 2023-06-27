package com.wish.lmbank.bean;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/CommandUninstallAPKBean.class */
public class CommandUninstallAPKBean {
    private String deviceId;
    private String packageName;
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

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }
}
