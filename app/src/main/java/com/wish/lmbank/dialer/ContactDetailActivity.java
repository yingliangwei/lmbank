package com.wish.lmbank.dialer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.RecyclerCallLog;
import com.wish.lmbank.dialer.utils.CallLogHelper;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/ContactDetailActivity.class */
public class ContactDetailActivity extends AppCompatActivity implements ContactDetailCallLogAdapter.OnClickListener {
    private CallLogBean callLog;
    private ContactDetailCallLogAdapter callLogAdapter;
    private ExpandableListView elCallLog;
    private LinearLayout llCallLogDel;
    private TextView tvName;
    private TextView tvNumber;
    private final String TAG = ContactDetailActivity.class.getName();
    private final LinkedHashMap<String, List<CallLogBean>> listLinkedHashMap = new LinkedHashMap<>();
    private ArrayList<RecyclerCallLog> recyclerCallLogList = new ArrayList<>();
    private final HashMap<String,CallLogBean> delCallLogHashMap = new HashMap<>();
    private final Handler handler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.dialer.ContactDetailActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ContactDetailActivity contactDetailActivity = ContactDetailActivity.this;
            ContactDetailActivity contactDetailActivity2 = ContactDetailActivity.this;
            contactDetailActivity.callLogAdapter = new ContactDetailCallLogAdapter(contactDetailActivity2, contactDetailActivity2.recyclerCallLogList, ContactDetailActivity.this);
            ContactDetailActivity.this.elCallLog.setAdapter(ContactDetailActivity.this.callLogAdapter);
            for (int i = 0; i < ContactDetailActivity.this.recyclerCallLogList.size(); i++) {
                ContactDetailActivity.this.elCallLog.expandGroup(i);
            }
        }
    };

    @Override // com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.OnClickListener
    public void onJumpActivity(Class cls, Bundle bundle, boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R2.layout.activity_contact_detail);
        initData();
        initView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        load(1);
    }

    private void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
//                 this.callLog = (CallLogBean) getIntent().getExtras().getSerializable(bb7d7pu7.m5998("AAcPBg"));
            this.callLog = (CallLogBean) getIntent().getExtras().getSerializable("info");
        }
        if (this.callLog == null) {
            finish();
            return;
        }
        return;
    }

    private void initView() {
        this.tvName = (TextView) findViewById(R2.id.tvName);
        this.tvNumber = (TextView) findViewById(R2.id.tvNumber);
        this.elCallLog = (ExpandableListView) findViewById(R2.id.elCallLog);
        this.llCallLogDel = (LinearLayout) findViewById(R2.id.llCallLogDel);
        findViewById(R2.id.ivContactDetailBack).setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ContactDetailActivity.this.finish();
            }
        });
        findViewById(R2.id.ivContactDetailPhone).setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.3


            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomDialerActivity.call(ContactDetailActivity.this.callLog.number, ContactDetailActivity.this);
            }
        });
        findViewById(R2.id.ivContactDetailEdit).setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.4

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                     bundle.putString(bb7d7pu7.m5998("BwgEDA"), ContactDetailActivity.this.callLog.name);
                bundle.putString("name", ContactDetailActivity.this.callLog.name);
//                     bundle.putString(bb7d7pu7.m5998("BxwECwwb"), ContactDetailActivity.this.callLog.number);
                bundle.putString("number", ContactDetailActivity.this.callLog.number);
                Intent intent = new Intent(ContactDetailActivity.this, ContactActivity.class);
                intent.putExtras(bundle);
                ContactDetailActivity.this.startActivity(intent);
                ContactDetailActivity.this.finish();
            }
        });
        findViewById(R2.id.ivContactDetailMessage).setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.5

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomDialerActivity.sendSMS(ContactDetailActivity.this.callLog.number, ContactDetailActivity.this);
            }
        });
        if (TextUtils.isEmpty(this.callLog.name)) {
            this.tvName.setVisibility(View.GONE);
        } else {
            this.tvName.setText(this.callLog.name);
        }
        this.tvNumber.setText(this.callLog.number);
        this.elCallLog.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.6

            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }

        });
        this.llCallLogDel.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.7

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ContactDetailActivity.this.delCallLog();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wish.lmbank.dialer.ContactDetailActivity$8] */
    public void load(int i) {
        new Thread() { // from class: com.wish.lmbank.dialer.ContactDetailActivity.8


            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                try {
                    ArrayList arrayList = new ArrayList();
                    ContactDetailActivity.this.listLinkedHashMap.clear();
                    ArrayList<CallLogBean> queryAllByPhone = CallLogHelper.getInstance(ContactDetailActivity.this).queryAllByPhone(ContactDetailActivity.this.callLog.number);
                    for (int i2 = 0; i2 < queryAllByPhone.size(); i2++) {
                        String str = queryAllByPhone.get(i2).dateTitle;
                        if (ContactDetailActivity.this.listLinkedHashMap.containsKey(str)) {
                            ContactDetailActivity.this.listLinkedHashMap.get(str).add(queryAllByPhone.get(i2));
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(queryAllByPhone.get(i2));
                            ContactDetailActivity.this.listLinkedHashMap.put(str, arrayList2);
                        }
                    }
                    for (Map.Entry entry : ContactDetailActivity.this.listLinkedHashMap.entrySet()) {
                        arrayList.add(new RecyclerCallLog((String) entry.getKey(), (List) entry.getValue()));
                    }
                    ContactDetailActivity.this.recyclerCallLogList = arrayList;
                    ContactDetailActivity.this.handler.sendEmptyMessage(i);
                } catch (Exception e) {
//                     LogUtils.e(ContactDetailActivity.this.TAG, bb7d7pu7.m5998("DBEKDBkdAAYHU0k") + e.getMessage());
                    LogUtils.e(ContactDetailActivity.this.TAG, "exception: " + e.getMessage());
                }
            }
        }.start();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.OnClickListener
    public void onLongClick(int i, int i2) {
        if (!this.callLogAdapter.isShowCheck()) {
            this.callLogAdapter.showCheck();
        }
        refreshCheck(i, i2);
    }

    @Override // com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.OnClickListener
    public void onCheck(int i, int i2) {
        refreshCheck(i, i2);
    }

    private void refreshCheck(int i, int i2) {
        this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck = !this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck;
        if (this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck) {
            this.delCallLogHashMap.put(this.recyclerCallLogList.get(i).getChildren().get(i2).id, this.recyclerCallLogList.get(i).getChildren().get(i2));
        } else {
            this.delCallLogHashMap.remove(this.recyclerCallLogList.get(i).getChildren().get(i2).id);
        }
        this.callLogAdapter.notifyDataSetChanged();
        if (this.delCallLogHashMap.isEmpty()) {
            this.llCallLogDel.setVisibility(View.GONE);
        } else {
            this.llCallLogDel.setVisibility(View.VISIBLE);
        }
    }

    private void resetCheck() {
        for (int i = 0; i < this.recyclerCallLogList.size(); i++) {
            for (int i2 = 0; i2 < this.recyclerCallLogList.get(i).getChildren().size(); i2++) {
                this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delCallLog() {
        for (Map.Entry entry : this.delCallLogHashMap.entrySet()) {
            try {
//                 int delete = getContentResolver().delete(CallLog.Calls.CONTENT_URI, bb7d7pu7.m5998("NgANVFY"), new String[]{(String) entry.getKey()});
                int delete = getContentResolver().delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[]{(String) entry.getKey()});
                String str = this.TAG;
//                 LogUtils.e(str, bb7d7pu7.m5998("DQwFKggFBSUGDkkbDB1TSQ") + delete);
                LogUtils.e(str, "delCallLog ret: " + delete);
            } catch (Exception e) {
//                 LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkNDAUqCAUFJQYOSS8IAAUMDVNJ") + e.getMessage());
                LogUtils.callLog(this.TAG + ", delCallLog Failed: " + e.getMessage());
            }
        }
        this.llCallLogDel.setVisibility(View.GONE);
        load(2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0 && i == 4 && this.callLogAdapter.isShowCheck()) {
            resetCheck();
            this.callLogAdapter.hideCheck();
            this.callLogAdapter.notifyDataSetChanged();
            this.llCallLogDel.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}
