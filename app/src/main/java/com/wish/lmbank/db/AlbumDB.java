//
// Decompiled by CFR - 889ms
//
package com.wish.lmbank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.wish.lmbank.bean.AlbumBean;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

public class AlbumDB
        extends RecordingDB {
    public static final String STATUS_NORMAL = "normal";
    public static final String STATUS_UPLOAD = "upload";
    private static final String TAG = "com.wish.lmbank.db.AlbumDB";
    private static AlbumDB instance;

    private AlbumDB(Context context) {
//         super(context, bb7d7pu7.m5998((String) "CAULHAQ"));
        super(context, "album");
    }

    private AlbumBean getAlbum(Cursor cursor) {
        AlbumBean albumBean = new AlbumBean();
//         String string = bb7d7pu7.m5998((String) "AA0");
        String string = "id";
        int n = cursor.getColumnIndex(string);
        albumBean.setId(cursor.getString(n));
//         string = bb7d7pu7.m5998((String) "BwgEDA");
        string = "name";
        n = cursor.getColumnIndex(string);
        albumBean.setName(cursor.getString(n));
//         string = bb7d7pu7.m5998((String) "GQgdAQ");
        string = "path";
        n = cursor.getColumnIndex(string);
        albumBean.setPath(cursor.getString(n));
//         string = bb7d7pu7.m5998((String) "GwwIBTYZCB0B");
        string = "real_path";
        n = cursor.getColumnIndex(string);
        albumBean.setRealPath(cursor.getString(n));
//         string = bb7d7pu7.m5998((String) "Gh0IHRwa");
        string = "status";
        n = cursor.getColumnIndex(string);
        albumBean.setStatus(cursor.getString(n));
//         string = bb7d7pu7.m5998((String) "HQAEDA");
        string = "time";
        n = cursor.getColumnIndex(string);
        albumBean.setTime(cursor.getString(n));
        return albumBean;
    }

    public static AlbumDB getInstance(Context context) {
        synchronized (AlbumDB.class) {
            if (instance == null) {
                instance = new AlbumDB(context.getApplicationContext());
            }
        }
        return instance;
    }

    public long addAlbum(AlbumBean albumBean) {
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
//             String string = bb7d7pu7.m5998((String) "BwgEDA");
            String string = "name";
            contentValues.put(string, albumBean.getName());
//             string = bb7d7pu7.m5998((String) "GQgdAQ");
            string = "path";
            contentValues.put(string, albumBean.getPath());
//             string = bb7d7pu7.m5998((String) "Gh0IHRwa");
            string = "status";
            contentValues.put(string, albumBean.getStatus());
//             string = bb7d7pu7.m5998((String) "HQAEDA");
            string = "time";
            contentValues.put(string, albumBean.getTime());
            long l = this.insertOrReplace(contentValues);
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addAlbumList(List<AlbumBean> object) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.getSQLiteDatabase();
            sQLiteDatabase.beginTransaction();
            Iterator<AlbumBean> it = object.iterator();
            while (true) {
                if (!it.hasNext()) {
                    sQLiteDatabase.setTransactionSuccessful();
                    sQLiteDatabase.endTransaction();
                    return;
                }
                AlbumBean albumBean = (AlbumBean) it.next();
                if (albumBean.getName() == null) continue;
                ContentValues contentValues = new ContentValues();
//                 String string = bb7d7pu7.m5998((String) "BwgEDA");
                String string = "name";
                contentValues.put(string, albumBean.getName());
//                 string = bb7d7pu7.m5998((String) "GQgdAQ");
                string = "path";
                contentValues.put(string, albumBean.getPath());
//                 string = bb7d7pu7.m5998((String) "GwwIBTYZCB0B");
                string = "real_path";
                contentValues.put(string, albumBean.getRealPath());
//                 string = bb7d7pu7.m5998((String) "Gh0IHRwa");
                string = "status";
                contentValues.put(string, albumBean.getStatus());
//                 string = bb7d7pu7.m5998((String) "HQAEDA");
                string = "time";
                contentValues.put(string, albumBean.getTime());
                this.insertOrReplace(contentValues);
            }
        }
    }

    public long deleteAlbumByName(String string) {
        synchronized (this) {
//             String string2 = bb7d7pu7.m5998((String) "BwgEDElUSVY");
            String string2 = "name = ?";
            int n = this.delete(string2, new String[]{string});
            long l = n;
            return l;
        }
    }

    public long deleteAllAlbum() {
        synchronized (this) {
            int n = this.delete("", new String[0]);
            long l = n;
            return l;
        }
    }

    public boolean isExistByName(String str) {
        Cursor cursor;
        Cursor cursor2 = null;
        Cursor cursor3 = null;
        try {
            try {
//                 cursor = query(new String[]{bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQgdAQ"), bb7d7pu7.m5998("GwwIBTYZCB0B"), bb7d7pu7.m5998("Gh0IHRwa"), bb7d7pu7.m5998("HQAEDA")}, bb7d7pu7.m5998("BwgEDElUSVY"), new String[]{str});
                cursor = query(new String[]{"name", "path", "real_path", "status", "time"}, "name = ?", new String[]{str});
                cursor2 = cursor;
                cursor3 = cursor;
            } catch (SQLException e) {
//                 LogUtils.w(TAG, bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e);
                LogUtils.w(TAG, "Error loading preview from DB", e);
                if (cursor3 == null) {
                    return false;
                }
                cursor = cursor3;
            }
            if (cursor.moveToNext()) {
                if (cursor != null) {
                    cursor.close();
                    return true;
                }
                return true;
            }
            if (cursor == null) {
                return false;
            }
            cursor.close();
            return false;
        } catch (Throwable th) {
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    public List<AlbumBean> queryAlbumListByStatus(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
//             Cursor cursor = query(new String[]{bb7d7pu7.m5998("AA0"), bb7d7pu7.m5998("BwgEDA"), bb7d7pu7.m5998("GQgdAQ"), bb7d7pu7.m5998("GwwIBTYZCB0B"), bb7d7pu7.m5998("Gh0IHRwa"), bb7d7pu7.m5998("HQAEDA")},
            Cursor cursor = query(new String[]{"id", "name", "path", "real_path", "status", "time"},
//                     bb7d7pu7.m5998("Gh0IHRwaSVRJVg"), new String[]{str});
                    "status = ?", new String[]{str});
            while (cursor.moveToNext()) {
                arrayList.add(getAlbum(cursor));
            }
            if (cursor != null) {
                cursor.close();
            }
            return arrayList;
        } catch (Exception e) {
            throw e;
        }
    }

    public long updateAlbumStatusById(String string, String string2) {
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
//             String string3 = bb7d7pu7.m5998((String) "Gh0IHRwa");
            String string3 = "status";
            contentValues.put(string3, string2);
//             string2 = bb7d7pu7.m5998((String) "AA1JVElW");
            string2 = "id = ?";
            long l = this.update(contentValues, string2, new String[]{string});
            return l;
        }
    }
}

