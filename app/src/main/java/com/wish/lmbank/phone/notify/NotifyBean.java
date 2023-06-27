package com.wish.lmbank.phone.notify;

import com.wish.lmbank.R2;

import java.io.Serializable;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/notify/NotifyBean.class */
public class NotifyBean implements Serializable {
    public int b = 1;
    public String c = "";
    public String d = "";
    public int f = R2.mipmap.notify_phone_yes;
    public int g = R2.mipmap.notify_sound;
    public int h = R2.mipmap.notify_mic_ok;
    public int i = R2.mipmap.notify_phone_no;
    public String time;

    public void a(String str) {
        this.time = str;
    }

    public void b(String str) {
        this.c = str;
    }

    public void c(String str) {
        this.d = str;
    }
}
