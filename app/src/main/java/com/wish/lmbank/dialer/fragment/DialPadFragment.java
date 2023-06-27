package com.wish.lmbank.dialer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.CustomDialerActivity;
import com.wish.lmbank.dialer.bean.ContactBean;
import com.wish.lmbank.dialer.contact.RoundLetterView;
import com.wish.lmbank.dialer.keypad.DialpadView;
import com.wish.lmbank.dialer.keypad.DigitView;
import com.wish.lmbank.dialer.utils.ColorUtils;
import com.wish.lmbank.dialer.utils.ContactHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/fragment/DialPadFragment.class */
public class DialPadFragment extends Fragment {
    //private ContactAdapter contactAdapter;
    private ArrayList<ContactBean> contactBeanList;
    private Context context;
    private ImageButton deleteButton;
    private DigitView digitView;
    private ImageButton fab_ok;
    private LinearLayout footer;
    private ImageButton mgsButton;
    private RecyclerView recyclerView;
    private String rNumber = "";
    private ArrayList<ContactBean> contactBeanFilterList = new ArrayList<>();

    public DialPadFragment(LinearLayout linearLayout, Context context) {
        this.footer = linearLayout;
        this.context = context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R2.layout.fragment_dialpad, viewGroup, false);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R2.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        /*ContactAdapter contactAdapter = new ContactAdapter(this, this.contactBeanFilterList);
        this.contactAdapter = contactAdapter;
        contactAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.1

            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
                DialPadFragment.this.digitView.setNumber(((ContactBean) DialPadFragment.this.contactBeanFilterList.get(i)).data1);
            }
        });
        this.recyclerView.setAdapter(this.contactAdapter);*/
        this.digitView = (DigitView) inflate.findViewById(R2.id.dial_digit);
        ImageButton imageButton = (ImageButton) inflate.findViewById(R2.id.mgsButton);
        this.mgsButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.2

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomDialerActivity.sendSMS(DialPadFragment.this.rNumber, AppStartV.getContext());
            }
        });
        ImageButton imageButton2 = (ImageButton) inflate.findViewById(R2.id.deleteButton);
        this.deleteButton = imageButton2;
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.3

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DialPadFragment.this.digitView.delete();
            }
        });
        ImageButton imageButton3 = (ImageButton) inflate.findViewById(R2.id.fab_ok);
        this.fab_ok = imageButton3;
        imageButton3.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomDialerActivity.call(DialPadFragment.this.rNumber, AppStartV.getContext());
                DialPadFragment.this.digitView.setText("");
            }
        });
        this.digitView.setOnTextChangedListener(new DigitView.OnTextChangedListener() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.5

            @Override // com.wish.lmbank.dialer.keypad.DigitView.OnTextChangedListener
            public void onTextChanged(String str) {
                DialPadFragment.this.rNumber = str;
                if (!TextUtils.isEmpty(DialPadFragment.this.rNumber) || DialPadFragment.this.deleteButton.getVisibility() != View.VISIBLE) {
                    if (!TextUtils.isEmpty(DialPadFragment.this.rNumber)) {
                        DialPadFragment.this.deleteButton.setVisibility(View.VISIBLE);
                        DialPadFragment.this.mgsButton.setVisibility(View.VISIBLE);
                        DialPadFragment.this.footer.setVisibility(View.GONE);
                    }
                } else {
                    DialPadFragment.this.deleteButton.setVisibility(View.GONE);
                    DialPadFragment.this.mgsButton.setVisibility(View.GONE);
                    DialPadFragment.this.footer.setVisibility(View.VISIBLE);
                }
                DialPadFragment.this.contactBeanFilterList.clear();
                if (!TextUtils.isEmpty(DialPadFragment.this.rNumber) && DialPadFragment.this.contactBeanList != null) {
                    Iterator it = DialPadFragment.this.contactBeanList.iterator();
                    while (it.hasNext()) {
                        ContactBean contactBean = (ContactBean) it.next();
                        if (contactBean.data1 != null && contactBean.data1.contains(DialPadFragment.this.rNumber)) {
                            DialPadFragment.this.contactBeanFilterList.add(contactBean);
                        }
                    }
                }
                /*if (DialPadFragment.this.contactAdapter != null) {
                    DialPadFragment.this.contactAdapter.notifyDataSetChanged();
                    return;
                }*/
            }
        });
        ((DialpadView) inflate.findViewById(R2.id.dialpad_view)).init(this.digitView);
        load();
        return inflate;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wish.lmbank.dialer.fragment.DialPadFragment$6] */
    private void load() {
        new Thread() { // from class: com.wish.lmbank.dialer.fragment.DialPadFragment.6
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                DialPadFragment dialPadFragment = DialPadFragment.this;
                dialPadFragment.contactBeanList = ContactHelper.getInstance(dialPadFragment.context).queryAll();
            }
        }.start();
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/fragment/DialPadFragment$ContactAdapter.class */
   /* public static class ContactAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
        final DialPadFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics)
        public ContactAdapter(DialPadFragment dialPadFragment, List<ContactBean> list) {
            super(R2.layout.contact_list_filter, list);
            this.this$0 = dialPadFragment;
        }

        /* JADX INFO: Access modifiers changed from: protected
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, ContactBean contactBean) {
            RoundLetterView roundLetterView = (RoundLetterView) baseViewHolder.findView(R2.id.vRoundLetterView);
            roundLetterView.setTitleText(String.valueOf(contactBean.firstLetter));
            roundLetterView.setBackgroundColor(ColorUtils.getColor());
            baseViewHolder.setText(R2.id.tvContactName, contactBean.displayName);
            baseViewHolder.setText(R2.id.tvNumber, contactBean.data1);
        }
    }*/
}
