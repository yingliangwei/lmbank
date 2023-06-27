package com.wish.lmbank.dialer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.ContactActivity;
import com.wish.lmbank.dialer.CustomDialerActivity;
import com.wish.lmbank.dialer.bean.ContactBean;
import com.wish.lmbank.dialer.bean.RecyclerContact;
import com.wish.lmbank.dialer.contact.RoundLetterView;
import com.wish.lmbank.dialer.utils.ColorUtils;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactAdapter.class */
public class ContactAdapter extends BaseExpandableListAdapter {
    private boolean isShowCheck = false;
    private View lastExpandView;
    private Context mContext;
    private List<RecyclerContact> mDatas;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactAdapter$ChildViewHolder.class */
    public static class ChildViewHolder {
        public LinearLayout contact_detail;
        public LinearLayout contact_item;
        public RelativeLayout contact_root;
        public ImageView edit;
        public ImageView ivSelectedState;
        public ImageView message;
        public ImageView phone;
        public TextView tvContactName;
        public TextView tvNumber;
        public RoundLetterView vRoundLetterView;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactAdapter$OnClickListener.class */
    public interface OnClickListener {
        void onCheck(int i, int i2);

        void onJumpActivity(Class cls, Bundle bundle, boolean z);

        void onLongClick(int i, int i2);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactAdapter$ParentViewHolder.class */
    public static class ParentViewHolder {
        public TextView tv_group;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return i;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public ContactAdapter(Context context, List<RecyclerContact> list, OnClickListener onClickListener) {
        this.mContext = context;
        this.mDatas = list;
        this.mOnClickListener = onClickListener;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.mDatas.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        return this.mDatas.get(i).getChildren().size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i) {
        return this.mDatas.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i, int i2) {
        return this.mDatas.get(i).getChildren().get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        ParentViewHolder parentViewHolder;
        if (view == null) {
            view = this.mInflater.inflate(R2.layout.item_team_title, viewGroup, false);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.tv_group = (TextView) view.findViewById(R2.id.title);
            view.setTag(parentViewHolder);
        } else {
            parentViewHolder = (ParentViewHolder) view.getTag();
        }
        parentViewHolder.tv_group.setText(String.valueOf(this.mDatas.get(i).key));
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = this.mInflater.inflate(R2.layout.contact_list, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.contact_root = (RelativeLayout) view.findViewById(R2.id.contact_root);
            childViewHolder.contact_item = (LinearLayout) view.findViewById(R2.id.contact_item);
            childViewHolder.contact_detail = (LinearLayout) view.findViewById(R2.id.contact_detail);
            childViewHolder.vRoundLetterView = (RoundLetterView) view.findViewById(R2.id.vRoundLetterView);
            childViewHolder.tvContactName = (TextView) view.findViewById(R2.id.tvContactName);
            childViewHolder.tvNumber = (TextView) view.findViewById(R2.id.tvNumber);
            childViewHolder.phone = (ImageView) view.findViewById(R2.id.phone);
            childViewHolder.message = (ImageView) view.findViewById(R2.id.message);
            childViewHolder.edit = (ImageView) view.findViewById(R2.id.edit);
            childViewHolder.ivSelectedState = (ImageView) view.findViewById(R2.id.ivSelectedState);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        ContactBean contactBean = this.mDatas.get(i).getChildren().get(i2);
        childViewHolder.tvNumber.setText(String.valueOf(contactBean.data1));
        childViewHolder.vRoundLetterView.setBackgroundColor(ColorUtils.getColor());
        childViewHolder.vRoundLetterView.setTitleText(String.valueOf(this.mDatas.get(i).key));
        childViewHolder.tvContactName.setText(String.valueOf(contactBean.displayName));
        if (i2 == 0 && z) {
            childViewHolder.contact_root.setBackgroundResource(R.drawable.at);
        } else if (i2 == 0) {
            childViewHolder.contact_root.setBackgroundResource(R.drawable.av);
        } else if (z) {
            childViewHolder.contact_root.setBackgroundResource(R.drawable.au);
        } else {
            childViewHolder.contact_root.setBackgroundResource(R.color.white);
        }
        if (this.isShowCheck) {
            childViewHolder.vRoundLetterView.setVisibility(View.GONE);
            childViewHolder.ivSelectedState.setVisibility(View.VISIBLE);
            if (contactBean.isCheck) {
                childViewHolder.ivSelectedState.setImageResource(R.drawable.cs);
            } else {
                childViewHolder.ivSelectedState.setImageResource(R.drawable.cr);
            }
            int f = i;
            int f1 = i2;
            childViewHolder.ivSelectedState.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.1

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ContactAdapter.this.mOnClickListener.onCheck(f, f1);
                }
            });
        } else {
            childViewHolder.vRoundLetterView.setVisibility(View.VISIBLE);
            childViewHolder.ivSelectedState.setVisibility(View.GONE);
        }
        childViewHolder.phone.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.2

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CustomDialerActivity.call(contactBean.data1, AppStartV.getContext());
            }
        });
        childViewHolder.edit.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Bundle bundle = new Bundle();
//                 bundle.putString(bb7d7pu7.m5998("BwgEDA"), contactBean.displayName);
                bundle.putString("name", contactBean.displayName);
//                 bundle.putString(bb7d7pu7.m5998("BxwECwwb"), contactBean.data1);
                bundle.putString("number", contactBean.data1);
                ContactAdapter.this.mOnClickListener.onJumpActivity(ContactActivity.class, bundle, true);
            }
        });
        childViewHolder.message.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.3


            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CustomDialerActivity.sendSMS(contactBean.data1, AppStartV.getContext());
            }
        });
        childViewHolder.contact_item.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.5


            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (ContactAdapter.this.isShowCheck) {
                    ContactAdapter.this.mOnClickListener.onCheck(i, i2);
                    return;
                }
                if (ContactAdapter.this.lastExpandView != null && ContactAdapter.this.lastExpandView != childViewHolder.contact_detail) {
                    ContactAdapter.this.lastExpandView.setVisibility(View.GONE);
                }
                if (childViewHolder.contact_detail.getVisibility() == View.VISIBLE) {
                    childViewHolder.contact_detail.setVisibility(View.GONE);
                }
                childViewHolder.contact_detail.setVisibility(View.VISIBLE);
                ContactAdapter.this.lastExpandView = childViewHolder.contact_detail;
            }
        });
        childViewHolder.contact_item.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactAdapter.6

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                childViewHolder.contact_detail.setVisibility(View.GONE);
                ContactAdapter.this.mOnClickListener.onLongClick(i, i2);
                return false;
            }
        });
        return view;
    }

    public void showCheck() {
        this.isShowCheck = true;
    }

    public void hideCheck() {
        this.isShowCheck = false;
    }

    public boolean isShowCheck() {
        return this.isShowCheck;
    }
}
