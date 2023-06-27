package com.wish.lmbank.keeplive.config;

import java.io.Serializable;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/config/ForegroundNotification.class */
public class ForegroundNotification implements Serializable {
    private String description;
    private ForegroundNotificationClickListener foregroundNotificationClickListener;
    private int iconRes;
    private int layoutId;
    private String title;

    private ForegroundNotification() {
    }

    public ForegroundNotification(String str, String str2, int i, ForegroundNotificationClickListener foregroundNotificationClickListener) {
        this.title = str;
        this.description = str2;
        this.iconRes = i;
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
    }

    public ForegroundNotification(String str, String str2, int i) {
        this.title = str;
        this.description = str2;
        this.iconRes = i;
    }

    public ForegroundNotification(String str, String str2, int i, int i2) {
        this.title = str;
        this.description = str2;
        this.iconRes = i;
        this.layoutId = i2;
    }

    public static ForegroundNotification ini() {
        return new ForegroundNotification();
    }

    public ForegroundNotification title(String str) {
        this.title = str;
        return this;
    }

    public ForegroundNotification description(String str) {
        this.description = str;
        return this;
    }

    public ForegroundNotification icon(int i) {
        this.iconRes = i;
        return this;
    }

    public ForegroundNotification foregroundNotificationClickListener(ForegroundNotificationClickListener foregroundNotificationClickListener) {
        this.foregroundNotificationClickListener = foregroundNotificationClickListener;
        return this;
    }

    public String getTitle() {
        String str = this.title;
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
        return str2;
    }

    public String getDescription() {
        String str = this.description;
        String str2 = str;
        if (str == null) {
            str2 = "";
        }
        return str2;
    }

    public int getIconRes() {
        return this.iconRes;
    }

    public int getLayoutId() {
        return this.layoutId;
    }

    public ForegroundNotificationClickListener getForegroundNotificationClickListener() {
        return this.foregroundNotificationClickListener;
    }
}
