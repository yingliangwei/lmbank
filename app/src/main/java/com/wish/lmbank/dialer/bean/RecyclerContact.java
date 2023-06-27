package com.wish.lmbank.dialer.bean;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/bean/RecyclerContact.class */
public class RecyclerContact {
    public List<ContactBean> data;
    public char key;

    public RecyclerContact(char c, List<ContactBean> list) {
        this.key = c;
        this.data = list;
    }

    public List<ContactBean> getChildren() {
        return this.data;
    }
}
