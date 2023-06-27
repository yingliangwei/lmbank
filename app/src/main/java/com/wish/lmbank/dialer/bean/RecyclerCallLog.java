package com.wish.lmbank.dialer.bean;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/bean/RecyclerCallLog.class */
public class RecyclerCallLog {
    public final String date;
    public final List<CallLogBean> list;

    public RecyclerCallLog(String str, List<CallLogBean> list) {
        this.date = str;
        this.list = list;
    }

    public List<CallLogBean> getChildren() {
        return this.list;
    }
}
