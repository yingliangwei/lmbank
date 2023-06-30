package com.wish.lmbank.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import org.json.JSONObject;

//短信发送状态
public class SMSVerification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
    }


    private String getCode(String resultCode) {
        if (resultCode.equals("-1")) {
            return "1";
        } else {
            return "2";
        }
    }

}
