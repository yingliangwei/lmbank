package com.wish.lmbank.dialer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.adapter.DialerSearchAdapter;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.ContactBean;
import com.wish.lmbank.dialer.bean.DialerSearchBean;
import com.wish.lmbank.dialer.utils.CallLogHelper;
import com.wish.lmbank.dialer.utils.ContactHelper;

import java.util.ArrayList;
import java.util.Iterator;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/DialerSearchActivity.class */
public class DialerSearchActivity extends AppCompatActivity implements View.OnClickListener, DialerSearchAdapter.OnClickListener {
    private ArrayList<ContactBean> contactBeanList;
    private DialerSearchAdapter dialerSearchAdapter;
    private ExpandableListView elList;
    private EditText etDialerSearchInput;
    private ImageView ivDialerSearchClear;
    private ArrayList<ContactBean> contactBeanFilterList = new ArrayList<>();
    private ArrayList<DialerSearchBean> dialerSearchList = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.dialer.DialerSearchActivity.3

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            DialerSearchActivity dialerSearchActivity = DialerSearchActivity.this;
            DialerSearchActivity dialerSearchActivity2 = DialerSearchActivity.this;
            dialerSearchActivity.dialerSearchAdapter = new DialerSearchAdapter(dialerSearchActivity2, dialerSearchActivity2.dialerSearchList, DialerSearchActivity.this);
            DialerSearchActivity.this.elList.setAdapter(DialerSearchActivity.this.dialerSearchAdapter);
            for (int i = 0; i < DialerSearchActivity.this.dialerSearchList.size(); i++) {
                DialerSearchActivity.this.elList.expandGroup(i);
            }
        }
    };

    @Override // com.wish.lmbank.dialer.adapter.DialerSearchAdapter.OnClickListener
    public void onCheck(int i, int i2) {
    }

    @Override // com.wish.lmbank.dialer.adapter.DialerSearchAdapter.OnClickListener
    public void onLongClick(int i, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R2.layout.activity_dialer_search);
        initView();
        initListener();
    }

    private void initListener() {
        findViewById(R2.id.ivDialerSearchBack).setOnClickListener(this);
        this.ivDialerSearchClear.setOnClickListener(this);
        this.etDialerSearchInput.addTextChangedListener(new TextWatcher() { // from class: com.wish.lmbank.dialer.DialerSearchActivity.1

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String valueOf = String.valueOf(charSequence);
                if (TextUtils.isEmpty(valueOf)) {
                    DialerSearchActivity.this.ivDialerSearchClear.setVisibility(View.GONE);
                } else {
                    DialerSearchActivity.this.ivDialerSearchClear.setVisibility(View.VISIBLE);
                }
                DialerSearchActivity.this.doSearch(valueOf);
            }
        });
    }

    private void initView() {
        this.etDialerSearchInput = (EditText) findViewById(R2.id.etDialerSearchInput);
        this.ivDialerSearchClear = (ImageView) findViewById(R2.id.ivDialerSearchClear);
        this.elList = (ExpandableListView) findViewById(R2.id.elList);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R2.id.ivDialerSearchBack /* 2131296578 */:
                finish();
                return;
            case R2.id.ivDialerSearchClear /* 2131296579 */:
                this.etDialerSearchInput.setText("");
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.wish.lmbank.dialer.DialerSearchActivity$2] */
    public void doSearch(String str) {
        new Thread() { // from class: com.wish.lmbank.dialer.DialerSearchActivity.2


            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                ArrayList arrayList = new ArrayList();
                DialerSearchActivity dialerSearchActivity = DialerSearchActivity.this;
                dialerSearchActivity.contactBeanList = ContactHelper.getInstance(dialerSearchActivity).queryAll();
                DialerSearchActivity.this.contactBeanFilterList.clear();
                if (!TextUtils.isEmpty(str) && DialerSearchActivity.this.contactBeanList != null) {
                    Iterator it = DialerSearchActivity.this.contactBeanList.iterator();
                    while (it.hasNext()) {
                        ContactBean contactBean = (ContactBean) it.next();
                        if ((contactBean.data1 != null && contactBean.data1.contains(str)) || (contactBean.displayName != null && contactBean.displayName.contains(str))) {
                            DialerSearchActivity.this.contactBeanFilterList.add(contactBean);
                        }
                    }
                }
                DialerSearchBean dialerSearchBean = new DialerSearchBean();
                if (DialerSearchActivity.this.contactBeanFilterList.size() > 0) {
                    dialerSearchBean.type = 1;
//                     dialerSearchBean.title = bb7d7pu7.m5998("hf7ZgvTUhdvx");
                    dialerSearchBean.title = "연락처";
                    dialerSearchBean.contactList = DialerSearchActivity.this.contactBeanFilterList;
                    arrayList.add(dialerSearchBean);
                }
                ArrayList<CallLogBean> queryAllByLike = CallLogHelper.getInstance(DialerSearchActivity.this).queryAllByLike(str);
                if (queryAllByLike.size() > 0) {
                    DialerSearchBean dialerSearchBean2 = new DialerSearchBean();
                    dialerSearchBean2.type = 2;
//                     dialerSearchBean2.title = bb7d7pu7.m5998("hdz1g97Vg9HZgsj0");
                    dialerSearchBean2.title = "최근기록";
                    dialerSearchBean2.callLogList = queryAllByLike;
                    arrayList.add(dialerSearchBean2);
                }
                DialerSearchActivity.this.dialerSearchList = arrayList;
                DialerSearchActivity.this.mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    @Override // com.wish.lmbank.dialer.adapter.DialerSearchAdapter.OnClickListener
    public void onJumpActivity(Class cls, Bundle bundle, boolean z) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (z) {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacksAndMessages(null);
    }
}
