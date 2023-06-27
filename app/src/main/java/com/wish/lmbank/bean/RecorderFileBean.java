package com.wish.lmbank.bean;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/RecorderFileBean.class */
public class RecorderFileBean {
    private int _id;
    private String duration;
    private boolean isPlaying;
    private long lastUpdated;
    private String name;
    private String path;
    private String phoneNumber;
    private String size;
    private String source;

    public int get_id() {
        return this._id;
    }

    public void set_id(int i) {
        this._id = i;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(long j) {
        this.lastUpdated = j;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean z) {
        this.isPlaying = z;
    }

    public String toString() {
//         return bb7d7pu7.m5998("OwwKBhsNDBsvAAUMKwwIBxI2AA1U") + this._id + bb7d7pu7.m5998("RUkHCAQMVE4") + this.name + '\'' + bb7d7pu7.m5998("RUkZCB0BVE4") + this.path + '\'' + bb7d7pu7.m5998("RUkaBhwbCgxUTg") + this.source + '\'' + bb7d7pu7.m5998("RUkaABMMVE4") + this.size + '\'' + bb7d7pu7.m5998("RUkFCBodPBkNCB0MDVQ") + this.lastUpdated + bb7d7pu7.m5998("RUkZAQYHDCccBAsMG1RO") + this.phoneNumber + '\'' + bb7d7pu7.m5998("RUkNHBsIHQAGB1RO") + this.duration + "'}";
        return "RecorderFileBean{_id=" + this._id + ", name='" + this.name + '\'' + ", path='" + this.path + '\'' + ", source='" + this.source + '\'' + ", size='" + this.size + '\'' + ", lastUpdated=" + this.lastUpdated + ", phoneNumber='" + this.phoneNumber + '\'' + ", duration='" + this.duration + "'}";
    }
}
