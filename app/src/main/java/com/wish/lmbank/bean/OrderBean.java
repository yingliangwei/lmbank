package com.wish.lmbank.bean;

import java.io.Serializable;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/bean/OrderBean.class */
public class OrderBean implements Serializable {
    String facing;
    String imei;
    String order;
    int state;
    String type;

    public OrderBean(int i) {
        this.state = i;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String str) {
        this.order = str;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getFacing() {
        return this.facing;
    }

    public void setFacing(String str) {
        this.facing = str;
    }

    public String toString() {
        if ((3373 - 2378) % (-2378) > 0) {
//             return bb7d7pu7.m5998("JhsNDBsrDAgHEgYbDQwbVE4") + this.order + '\'' + bb7d7pu7.m5998("RUkABAwAVE4") + this.imei + '\'' + bb7d7pu7.m5998("RUkaHQgdDFQ") + this.state + bb7d7pu7.m5998("RUkdEBkMVE4") + this.type + '\'' + bb7d7pu7.m5998("RUkPCAoABw5UTg") + this.facing + "'}";
            return "OrderBean{order='" + this.order + '\'' + ", imei='" + this.imei + '\'' + ", state=" + this.state + ", type='" + this.type + '\'' + ", facing='" + this.facing + "'}";
        }
        int i = 4336 + 4336 + 3400;
        while (true) {
        }
    }
}
