package com.wish.lmbank.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ContactUtils.class */
public class ContactUtils {
    public static String queryDisplayName(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
//         Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{bb7d7pu7.m5998("DQAaGQUIEDYHCAQM")}, null, null, null);
        Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name"}, null, null, null);
        if (query != null) {
            if (query.moveToFirst()) {
                String string = query.getString(0);
                query.close();
                return string;
            }
            query.close();
            return null;
        }
        return null;
    }
}
