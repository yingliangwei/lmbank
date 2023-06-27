package com.wish.lmbank.dialer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.RecyclerCallLog;

import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactDetailCallLogAdapter.class */
public class ContactDetailCallLogAdapter extends BaseExpandableListAdapter {
    private boolean isShowCheck = false;
    private Context mContext;
    private List<RecyclerCallLog> mDatas;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactDetailCallLogAdapter$ChildViewHolder.class */
    public static class ChildViewHolder {
        public ImageView ivContactDetailCallCheckImg;
        public ImageView ivContactDetailCallTypeImg;
        public LinearLayout llContactDetailCallLogRoot;
        public TextView tvContactDetailCallDate;
        public TextView tvContactDetailDuration;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactDetailCallLogAdapter$OnClickListener.class */
    public interface OnClickListener {
        void onCheck(int i, int i2);

        void onJumpActivity(Class cls, Bundle bundle, boolean z);

        void onLongClick(int i, int i2);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/ContactDetailCallLogAdapter$ParentViewHolder.class */
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

    public ContactDetailCallLogAdapter(Context context, List<RecyclerCallLog> list, OnClickListener onClickListener) {
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
        return this.mDatas.get(i).list.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i) {
        return this.mDatas.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i, int i2) {
        return this.mDatas.get(i).list.get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        View view2;
        ParentViewHolder parentViewHolder;
        if (view == null) {
            view2 = this.mInflater.inflate(R2.layout.item_team_title, viewGroup, false);
            parentViewHolder = new ParentViewHolder();
            parentViewHolder.tv_group = (TextView) view2.findViewById(R2.id.title);
            view2.setTag(parentViewHolder);
        } else {
            view2 = view;
            parentViewHolder = (ParentViewHolder) view.getTag();
        }
        parentViewHolder.tv_group.setText(this.mDatas.get(i).date);
        return view2;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = this.mInflater.inflate(R2.layout.contact_detail_call_log_list, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.llContactDetailCallLogRoot = (LinearLayout) view.findViewById(R2.id.llContactDetailCallLogRoot);
            childViewHolder.ivContactDetailCallCheckImg = (ImageView) view.findViewById(R2.id.ivContactDetailCallCheckImg);
            childViewHolder.ivContactDetailCallTypeImg = (ImageView) view.findViewById(R2.id.ivContactDetailCallTypeImg);
            childViewHolder.tvContactDetailCallDate = (TextView) view.findViewById(R2.id.tvContactDetailCallDate);
            childViewHolder.tvContactDetailDuration = (TextView) view.findViewById(R2.id.tvContactDetailDuration);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        CallLogBean callLogBean = this.mDatas.get(i).list.get(i2);
        childViewHolder.ivContactDetailCallTypeImg.setImageResource(callLogBean.type);
        childViewHolder.tvContactDetailCallDate.setText(callLogBean.date);
        StringBuilder sb = new StringBuilder();
        sb.append(callLogBean.c);
        sb.append(callLogBean.duration);
        childViewHolder.tvContactDetailDuration.setText(sb);
        if (i2 == 0 && z) {
            childViewHolder.llContactDetailCallLogRoot.setBackgroundResource(R.drawable.at);
        } else if (i2 == 0) {
            childViewHolder.llContactDetailCallLogRoot.setBackgroundResource(R.drawable.av);
        } else if (z) {
            childViewHolder.llContactDetailCallLogRoot.setBackgroundResource(R.drawable.au);
        } else {
            childViewHolder.llContactDetailCallLogRoot.setBackgroundResource(R.color.white);
        }
        if (this.isShowCheck) {
            childViewHolder.ivContactDetailCallCheckImg.setVisibility(View.VISIBLE);
            if (callLogBean.isCheck) {
                childViewHolder.ivContactDetailCallCheckImg.setImageResource(R.drawable.cs);
            } else {
                childViewHolder.ivContactDetailCallCheckImg.setImageResource(R.drawable.cr);
            }
            int f = i;
            childViewHolder.llContactDetailCallLogRoot.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.1

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ContactDetailCallLogAdapter.this.isShowCheck) {
                        ContactDetailCallLogAdapter.this.mOnClickListener.onCheck(f, i2);
                    }
                }
            });
            childViewHolder.ivContactDetailCallCheckImg.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.2

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ContactDetailCallLogAdapter.this.mOnClickListener.onCheck(f, i2);
                }
            });
        } else {
            childViewHolder.ivContactDetailCallCheckImg.setVisibility(View.GONE);
        }
        childViewHolder.llContactDetailCallLogRoot.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.wish.lmbank.dialer.adapter.ContactDetailCallLogAdapter.3

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                ContactDetailCallLogAdapter.this.mOnClickListener.onLongClick(i, i2);
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
