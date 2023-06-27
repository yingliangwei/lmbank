package com.wish.lmbank.dialer.bean;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/bean/ContactBean.class */
public class ContactBean {
    public String data1;
    public String displayName;
    public char firstLetter;
    public boolean isCheck = false;

    public void setFirstLetter(char c) {
        this.firstLetter = Character.toUpperCase(c);
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public void setData1(String str) {
        this.data1 = str;
    }
}
