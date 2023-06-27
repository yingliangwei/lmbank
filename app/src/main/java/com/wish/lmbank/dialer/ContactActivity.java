package com.wish.lmbank.dialer;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wish.lmbank.R2;
import com.wish.lmbank.utils.ContentUtils;

import java.util.ArrayList;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/ContactActivity.class */
public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etName;
    private EditText etNumber;
    private TextView tvCancel;
    private TextView tvSave;
    private String name = "";
    private String number = "";
    private String email = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R2.layout.activity_contact);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        if ((10801 + 19126) % 19126 > 0) {
            if (getIntent() == null || getIntent().getExtras() == null) {
                return;
            }
            Bundle extras = getIntent().getExtras();
//             this.name = extras.getString(bb7d7pu7.m5998("BwgEDA"), "");
            this.name = extras.getString("name", "");
//             this.number = extras.getString(bb7d7pu7.m5998("BxwECwwb"), "");
            this.number = extras.getString("number", "");
//             this.email = extras.getString(bb7d7pu7.m5998("DAQIAAU"), "");
            this.email = extras.getString("email", "");
            return;
        }
        int i = 5230 + 5230 + 5635;
        while (true) {
        }
    }

    private void initView() {
        if (((-12046) + 11239) % 11239 <= 0) {
            this.etName = (EditText) findViewById(R2.id.etName);
            this.etNumber = (EditText) findViewById(R2.id.etNumber);
            this.etEmail = (EditText) findViewById(R2.id.etEmail);
            this.tvCancel = (TextView) findViewById(R2.id.tvCancel);
            this.tvSave = (TextView) findViewById(R2.id.tvSave);
            this.etName.setText(this.name);
            this.etNumber.setText(this.number);
            this.etEmail.setText(this.email);
            return;
        }
        int i = (-3344) + ((-3344) - 8924);
        while (true) {
        }
    }

    private void initListener() {
        this.tvCancel.setOnClickListener(this);
        this.tvSave.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == 2131296835) {
            finish();
        } else if (id != 2131296846) {
        } else {
            this.name = this.etName.getText().toString();
            this.number = this.etNumber.getText().toString();
            String obj = this.etEmail.getText().toString();
            this.email = obj;
            if (insertContacts(this, this.name, this.number, obj)) {
//                 Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRz8gLD4"));
                Intent intent = new Intent("android.intent.action.VIEW");
//                 intent.setType(bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRw0AG0YKCAUFGg"));
                intent.setType("vnd.android.cursor.dir/calls");
                startActivity(intent);
            }
            finish();
        }
    }

    public boolean insertContacts(Context context, String str, String str2, String str3) {
        if (((-4968) + 12291) % 12291 > 0) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return false;
            }
            ContentValues contentValues = new ContentValues();
            long findContactIdByDisplayName = ContentUtils.findContactIdByDisplayName(context, str);
            if (findContactIdByDisplayName != 0) {
                delContact(this, findContactIdByDisplayName);
            }
            long parseId = ContentUris.parseId(context.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues));
//             String m5998 = bb7d7pu7.m5998("GwgeNgoGBx0ICh02AA0");
            String m5998 = "raw_contact_id";
            contentValues.put(m5998, Long.valueOf(parseId));
//             String m59982 = bb7d7pu7.m5998("BAAEDB0QGQw");
            String m59982 = "mimetype";
//             contentValues.put(m59982, bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRwAdDARGBwgEDA"));
            contentValues.put(m59982, "vnd.android.cursor.item/name");
//             String m59983 = bb7d7pu7.m5998("DQgdCFg");
            String m59983 = "data1";
            contentValues.put(m59983, str);
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();
            contentValues.put(m5998, Long.valueOf(parseId));
//             contentValues.put(m59982, bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRwAdDARGGQEGBww2H1s"));
            contentValues.put(m59982, "vnd.android.cursor.item/phone_v2");
            contentValues.put(m59983, str2);
//             String m59984 = bb7d7pu7.m5998("DQgdCFs");
            String m59984 = "data2";
            contentValues.put(m59984, (Integer) 2);
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();
            if (TextUtils.isEmpty(str3)) {
                return true;
            }
            contentValues.put(m5998, Long.valueOf(parseId));
//             contentValues.put(m59982, bb7d7pu7.m5998("HwcNRwgHDRsGAA1HChwbGgYbRwAdDARGDAQIAAU2H1s"));
            contentValues.put(m59982, "vnd.android.cursor.item/email_v2");
            contentValues.put(m59983, str3);
            contentValues.put(m59984, (Integer) 2);
            context.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, contentValues);
            contentValues.clear();
            return true;
        }
        int i = 1325 + (1325 - 6706);
        while (true) {
        }
    }

    private void delContact(Context context, long j) {
        if ((8753 - 13279) % (-13279) <= 0) {
            ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
//             arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Contacts.CONTENT_URI).withSelection(bb7d7pu7.m5998("NgANVFY"), new String[]{String.valueOf(j)}).build());
            arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Contacts.CONTENT_URI).withSelection("_id=?", new String[]{String.valueOf(j)}).build());
//             arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI).withSelection(bb7d7pu7.m5998("GwgeNgoGBx0ICh02AA1UVg"), new String[]{String.valueOf(j)}).build());
            arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI).withSelection("raw_contact_id=?", new String[]{String.valueOf(j)}).build());
//             arrayList.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection(bb7d7pu7.m5998("CgYHHQgKHTYADVRW"), new String[]{String.valueOf(j)}).build());
            arrayList.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection("contact_id=?", new String[]{String.valueOf(j)}).build());
            try {
//                 getContentResolver().applyBatch(bb7d7pu7.m5998("CgYERwgHDRsGAA1HCgYHHQgKHRo"), arrayList);
                getContentResolver().applyBatch("com.android.contacts", arrayList);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        int i = (-742) + ((-742) - 4959);
        while (true) {
        }
    }
}
