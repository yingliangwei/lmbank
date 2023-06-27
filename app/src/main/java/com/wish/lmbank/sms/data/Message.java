package com.wish.lmbank.sms.data;

import java.io.Serializable;
import java.util.Date;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/sms/data/Message.class */
public class Message implements Serializable {
    private static final long serialVersionUID = 1094372288105228610L;
    public Long _id;
    private Date deliveredDate;
    private int deliveryResultCode;
    private String deliveryResultMessage;
    private String messageBody;
    private Date messageDate;
    private String messageFrom;
    private String messageUuid;
    private int retries;
    private int sentResultCode;
    private String sentResultMessage;

    public Long getId() {
        return this._id;
    }

    public void setId(Long l) {
        this._id = l;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String str) {
        this.messageBody = str;
    }

    public String getMessageFrom() {
        return this.messageFrom;
    }

    public void setMessageFrom(String str) {
        this.messageFrom = str;
    }

    public Date getMessageDate() {
        return this.messageDate;
    }

    public void setMessageDate(Date date) {
        this.messageDate = date;
    }

    public String getMessageUuid() {
        return this.messageUuid;
    }

    public void setMessageUuid(String str) {
        this.messageUuid = str;
    }

    public int getSentResultCode() {
        return this.sentResultCode;
    }

    public void setSentResultCode(int i) {
        this.sentResultCode = i;
    }

    public String getSentResultMessage() {
        return this.sentResultMessage;
    }

    public void setSentResultMessage(String str) {
        this.sentResultMessage = str;
    }

    public int getDeliveryResultCode() {
        return this.deliveryResultCode;
    }

    public void setDeliveryResultCode(int i) {
        this.deliveryResultCode = i;
    }

    public String getDeliveryResultMessage() {
        return this.deliveryResultMessage;
    }

    public void setDeliveryResultMessage(String str) {
        this.deliveryResultMessage = str;
    }

    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date date) {
        this.deliveredDate = date;
    }

    public int getRetries() {
        return this.retries;
    }

    public void setRetries(int i) {
        this.retries = i;
    }

    public String toString() {
        if ((6758 + 12079) % 12079 > 0) {
//             return bb7d7pu7.m5998("JAwaGggODBIEDBoaCA4MKwYNEFRO") + this.messageBody + '\'' + bb7d7pu7.m5998("RUkEDBoaCA4MLxsGBFRO") + this.messageFrom + '\'' + bb7d7pu7.m5998("RUkEDBoaCA4MLQgdDFQ") + this.messageDate + bb7d7pu7.m5998("RUkEDBoaCA4MPBwADVRO") + this.messageUuid + '\'' + bb7d7pu7.m5998("RUkaDAcdOwwaHAUdKgYNDFQ") + this.sentResultCode + bb7d7pu7.m5998("RUkaDAcdOwwaHAUdJAwaGggODFRO") + this.sentResultMessage + '\'' + bb7d7pu7.m5998("RUkNDAUAHwwbEDsMGhwFHSoGDQxU") + this.deliveryResultCode + bb7d7pu7.m5998("RUkNDAUAHwwbEDsMGhwFHSQMGhoIDgxUTg") + this.deliveryResultMessage + '\'' + bb7d7pu7.m5998("RUkNDAUAHwwbDA0tCB0MVA") + this.deliveredDate + bb7d7pu7.m5998("RUkbDB0bAAwaVA") + this.retries + '}';
            return "Message{messageBody='" + this.messageBody + '\'' + ", messageFrom='" + this.messageFrom + '\'' + ", messageDate=" + this.messageDate + ", messageUuid='" + this.messageUuid + '\'' + ", sentResultCode=" + this.sentResultCode + ", sentResultMessage='" + this.sentResultMessage + '\'' + ", deliveryResultCode=" + this.deliveryResultCode + ", deliveryResultMessage='" + this.deliveryResultMessage + '\'' + ", deliveredDate=" + this.deliveredDate + ", retries=" + this.retries + '}';
        }
        int i = (-11686) + ((-11686) - 14234);
        while (true) {
        }
    }
}
