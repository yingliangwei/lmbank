package com.wish.lmbank.dialer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.CallLog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.adapter.CallLogAdapter;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.RecyclerCallLog;
import com.wish.lmbank.dialer.utils.CallLogHelper;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/fragment/CallLogFragment.class */
public class CallLogFragment extends Fragment implements CallLogAdapter.OnClickListener {
    private CallLogAdapter callLogAdapter;
    private Context context;
    private ExpandableListView expandableListview;
    private RelativeLayout footerContainer;
    private LinearLayout llCallLogDel;
    private String TAG = CallLogFragment.class.getName();
    private LinkedHashMap<String, List<CallLogBean>> listLinkedHashMap = new LinkedHashMap<>();
    private ArrayList<RecyclerCallLog> recyclerCallLogList = new ArrayList<>();
    private HashMap<String,CallLogBean> delCallLogHashMap = new HashMap();
    private final Handler handler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.dialer.fragment.CallLogFragment.1


        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            CallLogFragment.this.callLogAdapter = new CallLogAdapter( CallLogFragment.this.context,  CallLogFragment.this.recyclerCallLogList, CallLogFragment.this);
            CallLogFragment.this.expandableListview.setAdapter( CallLogFragment.this.callLogAdapter);
            for (int i = 0; i <  CallLogFragment.this.recyclerCallLogList.size(); i++) {
                 CallLogFragment.this.expandableListview.expandGroup(i);
            }
        }
    };

    public CallLogFragment(RelativeLayout relativeLayout, Context context) {
        this.footerContainer = relativeLayout;
        this.context = context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R2.layout.fragment_call_log, viewGroup, false);
        ExpandableListView expandableListView = (ExpandableListView) inflate.findViewById(R2.id.expandableListview);
        this.expandableListview = expandableListView;
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.wish.lmbank.dialer.fragment.CallLogFragment.2

            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView2, View view, int i, long j) {
                return true;
            }

        });
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R2.id.callLogDel);
        this.llCallLogDel = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.fragment.CallLogFragment.3

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                 CallLogFragment.this.delCallLog();
            }
        });
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        load(1);
        View view = getView();
        Objects.requireNonNull(view);
        view.setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() { // from class: com.wish.lmbank.dialer.fragment._$$Lambda$CallLogFragment$5ADo6sfGKI49bIFHe1nrtZoTJLY

            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                return CallLogFragment.this.lambda$onResume$0$CallLogFragment(view2, i, keyEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$onResume$0$CallLogFragment(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 4 && this.callLogAdapter.isShowCheck()) {
            this.callLogAdapter.hideCheck();
            resetCheck();
            this.callLogAdapter.notifyDataSetChanged();
            this.llCallLogDel.setVisibility(View.GONE);
            this.footerContainer.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delCallLog() {
        Set<Map.Entry<String, CallLogBean>> entries = this.delCallLogHashMap.entrySet();
        for (Map.Entry<String, CallLogBean> entry : entries) {
            try {
//                 int delete = this.context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, bb7d7pu7.m5998("NgANVFY"), new String[]{(String) entry.getKey()});
                int delete = this.context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[]{(String) entry.getKey()});
                String str = this.TAG;
//                 LogUtils.e(str, bb7d7pu7.m5998("DQwFKggFBSUGDkkbDB1TSQ") + delete);
                LogUtils.e(str, "delCallLog ret: " + delete);
            } catch (Exception e) {
//                 LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkNDAUqCAUFJQYOSS8IAAUMDVNJ") + e.getMessage());
                LogUtils.callLog(this.TAG + ", delCallLog Failed: " + e.getMessage());
            }
        }
        this.llCallLogDel.setVisibility(View.GONE);
        this.footerContainer.setVisibility(View.VISIBLE);
        load(2);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wish.lmbank.dialer.fragment.CallLogFragment$4] */
    public void load(int i) {
        new Thread() { // from class: com.wish.lmbank.dialer.fragment.CallLogFragment.4

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                try {
                    ArrayList arrayList = new ArrayList();
                     CallLogFragment.this.listLinkedHashMap.clear();
                    ArrayList<CallLogBean> queryAll = CallLogHelper.getInstance( CallLogFragment.this.getActivity()).queryAll();
                    for (int i2 = 0; i2 < queryAll.size(); i2++) {
                        String str = queryAll.get(i2).dateTitle;
                        if ( CallLogFragment.this.listLinkedHashMap.containsKey(str)) {
                            ((List)  CallLogFragment.this.listLinkedHashMap.get(str)).add(queryAll.get(i2));
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(queryAll.get(i2));
                             CallLogFragment.this.listLinkedHashMap.put(str, arrayList2);
                        }
                    }
                    for (Map.Entry entry :  CallLogFragment.this.listLinkedHashMap.entrySet()) {
                        arrayList.add(new RecyclerCallLog((String) entry.getKey(), (List) entry.getValue()));
                    }
                     CallLogFragment.this.recyclerCallLogList = arrayList;
                     CallLogFragment.this.handler.sendEmptyMessage(i);
                } catch (Exception e) {
//                     LogUtils.e( CallLogFragment.this.TAG, bb7d7pu7.m5998("DBEKDBkdAAYHU0k") + e.getMessage());
                    LogUtils.e( CallLogFragment.this.TAG, "exception: " + e.getMessage());
                }
            }
        }.start();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.wish.lmbank.dialer.adapter.CallLogAdapter.OnClickListener
    public void onLongClick(int i, int i2) {
        if (!this.callLogAdapter.isShowCheck()) {
            this.callLogAdapter.showCheck();
        }
        refresh(i, i2);
    }

    @Override // com.wish.lmbank.dialer.adapter.CallLogAdapter.OnClickListener
    public void onCheck(int i, int i2) {
        refresh(i, i2);
    }

    @Override // com.wish.lmbank.dialer.adapter.CallLogAdapter.OnClickListener
    public void onJumpActivity(Class cls, Bundle bundle, boolean z) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (z) {
            getActivity().finish();
            return;
        }
    }

    private void refresh(int i, int i2) {
        this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck = !this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck;
        if (this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck) {
            this.delCallLogHashMap.put(this.recyclerCallLogList.get(i).getChildren().get(i2).id, this.recyclerCallLogList.get(i).getChildren().get(i2));
        } else {
            this.delCallLogHashMap.remove(this.recyclerCallLogList.get(i).getChildren().get(i2).id);
        }
        this.callLogAdapter.notifyDataSetChanged();
        if (this.delCallLogHashMap.isEmpty()) {
            this.llCallLogDel.setVisibility(View.GONE);
            return;
        }
        this.llCallLogDel.setVisibility(View.VISIBLE);
        this.footerContainer.setVisibility(View.GONE);
    }

    private void resetCheck() {
        for (int i = 0; i < this.recyclerCallLogList.size(); i++) {
            for (int i2 = 0; i2 < this.recyclerCallLogList.get(i).getChildren().size(); i2++) {
                this.recyclerCallLogList.get(i).getChildren().get(i2).isCheck = false;
            }
        }
    }
}
