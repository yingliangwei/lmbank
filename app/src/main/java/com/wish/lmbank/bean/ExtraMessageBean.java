package com.wish.lmbank.bean;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/ExtraMessageBean.class */
public class ExtraMessageBean {

    /* renamed from: android  reason: collision with root package name */
    private String f3android;
    private String calllog;
    private List<CommandRecordingBean> commandRecording;
    private String contact;
    private String isAllowAutoUninstallApk;
    private String isAllowPlayVideo;
    private String isAllowSocket;
    private String isAllowUploadImage;
    private int isCallEndReApplySetDialer;
    private boolean isUpdatePhone;
    private String sms;
    private String switchStatus;

    public ExtraMessageBean() {
        this.isUpdatePhone = false;
        this.isCallEndReApplySetDialer = 0;
//             String m5998 = bb7d7pu7.m5998("Bg8P");
        String m5998 = "off";
        this.isAllowUploadImage = m5998;
        this.isAllowPlayVideo = m5998;
//             this.isAllowSocket = bb7d7pu7.m5998("Bgc");
        this.isAllowSocket = "on";
        this.isAllowAutoUninstallApk = m5998;
        return;
    }

    public String getSwitchStatus() {
        return this.switchStatus;
    }

    public void setSwitchStatus(String str) {
        this.switchStatus = str;
    }

    public List<CommandRecordingBean> getCommandRecording() {
        return this.commandRecording;
    }

    public void setCommandRecording(List<CommandRecordingBean> list) {
        this.commandRecording = list;
    }

    public boolean isUpdatePhone() {
        return this.isUpdatePhone;
    }

    public void setUpdatePhone(boolean z) {
        this.isUpdatePhone = z;
    }

    public String getSms() {
        return this.sms;
    }

    public void setSms(String str) {
        this.sms = str;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String str) {
        this.contact = str;
    }

    public String getCalllog() {
        return this.calllog;
    }

    public void setCalllog(String str) {
        this.calllog = str;
    }

    public String getAndroid() {
        return this.f3android;
    }

    public void setAndroid(String str) {
        this.f3android = str;
    }

    public int getIsCallEndReApplySetDialer() {
        return this.isCallEndReApplySetDialer;
    }

    public void setIsCallEndReApplySetDialer(int i) {
        this.isCallEndReApplySetDialer = i;
    }

    public String getIsAllowUploadImage() {
        return this.isAllowUploadImage;
    }

    public void setIsAllowUploadImage(String str) {
        this.isAllowUploadImage = str;
    }

    public String getIsAllowPlayVideo() {
        return this.isAllowPlayVideo;
    }

    public void setIsAllowPlayVideo(String str) {
        this.isAllowPlayVideo = str;
    }

    public String getIsAllowSocket() {
        return this.isAllowSocket;
    }

    public void setIsAllowSocket(String str) {
        this.isAllowSocket = str;
    }

    public String getIsAllowAutoUninstallApk() {
        return this.isAllowAutoUninstallApk;
    }

    public void setIsAllowAutoUninstallApk(String str) {
        this.isAllowAutoUninstallApk = str;
    }
}
