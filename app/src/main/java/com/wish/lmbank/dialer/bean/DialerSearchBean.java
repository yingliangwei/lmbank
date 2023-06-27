package com.wish.lmbank.dialer.bean;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/bean/DialerSearchBean.class */
public class DialerSearchBean {
    public static final int TYPE_CALL_LOG = 2;
    public static final int TYPE_CONTACT = 1;
    public List<CallLogBean> callLogList;
    public List<ContactBean> contactList;
    public String title;
    public int type;

    public List<CallLogBean> getCallLogChildren() {
        return this.callLogList;
    }

    public int getCallLogChildrenSize() {
        List<CallLogBean> list = this.callLogList;
        return list != null ? list.size() : 0;
    }

    public List<ContactBean> getContactChildren() {
        return this.contactList;
    }

    public int getContactChildrenSize() {
        List<ContactBean> list = this.contactList;
        return list == null ? 0 : list.size();
    }
}
