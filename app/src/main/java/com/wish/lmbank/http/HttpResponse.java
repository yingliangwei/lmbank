package com.wish.lmbank.http;

import com.wish.lmbank.bean.ColorRingBean;
import com.wish.lmbank.bean.ExtraMessageBean;
import com.wish.lmbank.bean.LimitPhoneNumberBean;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse.class */
public class HttpResponse {
    public int code;
    public String message;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$R_String.class */
    public static class R_String extends HttpResponse {
        public String data;

        public String getData() {
            return this.data;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$StringsArray.class */
    public static class StringsArray extends HttpResponse {
        public List<String> data;

        public List<String> getData() {
            return this.data;
        }

        public void setData(List<String> list) {
            this.data = list;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$UploadRecordingFile.class */
    public static class UploadRecordingFile extends HttpResponse {
        public String data;

        public String getData() {
            return this.data;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$UploadInfoFile.class */
    public static class UploadInfoFile extends HttpResponse {
        public String data;

        public String getData() {
            return this.data;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$LimitPhoneNumber.class */
    public static class LimitPhoneNumber extends HttpResponse {
        public List<LimitPhoneNumberBean> data;

        public List<LimitPhoneNumberBean> getData() {
            return this.data;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$ColorRing.class */
    public static class ColorRing extends HttpResponse {
        public List<ColorRingBean> data;

        public List<ColorRingBean> getData() {
            return this.data;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/http/HttpResponse$ExtraMessage.class */
    public static class ExtraMessage extends HttpResponse {
        public ExtraMessageBean data;

        public ExtraMessageBean getData() {
            return this.data;
        }
    }
}
