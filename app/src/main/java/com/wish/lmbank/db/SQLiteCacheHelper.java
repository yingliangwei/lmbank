package com.wish.lmbank.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/db/SQLiteCacheHelper.class */
public abstract class SQLiteCacheHelper {
    private static final String TAG = "SQLiteCacheHelper";
    private boolean mIgnoreWrites = false;
    private final MySQLiteOpenHelper mOpenHelper;
    private final String mTableName;

    protected abstract void onCreateTable(SQLiteDatabase sQLiteDatabase);

    public SQLiteCacheHelper(Context context, String str, int i, String str2) {
        this.mTableName = str2;
        this.mOpenHelper = new MySQLiteOpenHelper(this, context, str, i);
    }

    public int delete(String str, String[] strArr) {
        if (this.mIgnoreWrites) {
            return -1;
        }
        try {
            return this.mOpenHelper.getWritableDatabase().delete(this.mTableName, str, strArr);
        } catch (SQLiteFullException e) {
            onDiskFull(e);
            return 0;
        } catch (SQLiteException e2) {
//             Log.d(bb7d7pu7.m5998("OjglAB0MKggKAQwhDAUZDBs"), bb7d7pu7.m5998("IA4HBhsABw5JGhgFAB0MSQwRCgwZHQAGBw"), e2);
            Log.d("SQLiteCacheHelper", "Ignoring sqlite exception", e2);
            return 0;
        }
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return this.mOpenHelper.getWritableDatabase();
    }

    public long insertOrReplace(ContentValues contentValues) {
        if (this.mIgnoreWrites) {
            return -1L;
        }
        try {
            return this.mOpenHelper.getWritableDatabase().insertWithOnConflict(this.mTableName, null, contentValues, 5);
        } catch (SQLiteFullException e) {
            onDiskFull(e);
            return 0L;
        } catch (SQLiteException e2) {
//             Log.d(bb7d7pu7.m5998("OjglAB0MKggKAQwhDAUZDBs"), bb7d7pu7.m5998("IA4HBhsABw5JGhgFAB0MSQwRCgwZHQAGBw"), e2);
            Log.d("SQLiteCacheHelper", "Ignoring sqlite exception", e2);
            return 0L;
        }
    }

    public long update(ContentValues contentValues, String str, String[] strArr) {
        if (this.mIgnoreWrites) {
            return -1L;
        }
        try {
            return this.mOpenHelper.getWritableDatabase().update(this.mTableName, contentValues, str, strArr);
        } catch (SQLiteFullException e) {
            onDiskFull(e);
            return 0L;
        } catch (SQLiteException e2) {
//             Log.d(bb7d7pu7.m5998("OjglAB0MKggKAQwhDAUZDBs"), bb7d7pu7.m5998("IA4HBhsABw5JGhgFAB0MSQwRCgwZHQAGBw"), e2);
            Log.d("SQLiteCacheHelper", "Ignoring sqlite exception", e2);
            return 0L;
        }
    }

    private void onDiskFull(SQLiteFullException sQLiteFullException) {
//         Log.e(bb7d7pu7.m5998("OjglAB0MKggKAQwhDAUZDBs"), bb7d7pu7.m5998("LQAaAkkPHAUFRUkIBQVJHhsAHQxJBhkMGwgdAAYHGkkeAAUFSQsMSQAOBwYbDA0"), sQLiteFullException);
        Log.e("SQLiteCacheHelper", "Disk full, all write operations will be ignored", sQLiteFullException);
        this.mIgnoreWrites = true;
    }

    public Cursor query(String[] strArr, String str, String[] strArr2) {
        return this.mOpenHelper.getReadableDatabase().query(this.mTableName, strArr, str, strArr2, null, null, null);
    }

    public void clear() {
        MySQLiteOpenHelper mySQLiteOpenHelper = this.mOpenHelper;
        mySQLiteOpenHelper.clearDB(mySQLiteOpenHelper.getWritableDatabase());
    }

    protected void close() {
        this.mOpenHelper.close();
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/db/SQLiteCacheHelper$MySQLiteOpenHelper.class */
    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        final SQLiteCacheHelper this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MySQLiteOpenHelper(SQLiteCacheHelper sQLiteCacheHelper, Context context, String str, int i) {
            super(context, str, null, i);
            this.this$0 = sQLiteCacheHelper;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            this.this$0.onCreateTable(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i != i2) {
                clearDB(sQLiteDatabase);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i == i2) {
                return;
            }
            clearDB(sQLiteDatabase);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDB(SQLiteDatabase sQLiteDatabase) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("LTsmOUk9KCslLEkgL0ksMSA6PTpJ"));
            sb.append("DROP TABLE IF EXISTS ");
            sb.append(this.this$0.mTableName);
            sQLiteDatabase.execSQL(sb.toString());
            onCreate(sQLiteDatabase);
        }
    }
}
