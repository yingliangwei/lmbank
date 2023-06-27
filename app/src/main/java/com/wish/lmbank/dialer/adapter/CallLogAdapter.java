package com.wish.lmbank.dialer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.ContactActivity;
import com.wish.lmbank.dialer.ContactDetailActivity;
import com.wish.lmbank.dialer.CustomDialerActivity;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.RecyclerCallLog;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/CallLogAdapter.class */
public class CallLogAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<RecyclerCallLog> mDatas;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;
    private View lastExpandView = null;
    private boolean isShowCheck = false;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/CallLogAdapter$ChildViewHolder.class */
    public static class ChildViewHolder {
        public LinearLayout addContact;
        public ImageView callCheckImg;
        public ImageView callTypeImg;
        public LinearLayout call_log_detail;
        public LinearLayout call_log_item;
        public RelativeLayout call_log_root;
        public LinearLayout contactNumber;
        public ImageView info;
        public ImageView message;
        public ImageView phone;
        public TextView tvCallDate;
        public TextView tvDuration;
        public TextView tvName;
        public TextView tvNumber;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/CallLogAdapter$OnClickListener.class */
    public interface OnClickListener {
        void onCheck(int i, int i2);

        void onJumpActivity(Class cls, Bundle bundle, boolean z);

        void onLongClick(int i, int i2);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/CallLogAdapter$ParentViewHolder.class */
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

    public CallLogAdapter(Context context, List<RecyclerCallLog> list, OnClickListener onClickListener) {
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
            view = this.mInflater.inflate(R2.layout.call_log_list, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.call_log_root = (RelativeLayout) view.findViewById(R2.id.call_log_root);
            childViewHolder.callTypeImg = (ImageView) view.findViewById(R2.id.callTypeImg);
            childViewHolder.tvName = (TextView) view.findViewById(R2.id.tvName);
            childViewHolder.tvCallDate = (TextView) view.findViewById(R2.id.tvCallDate);
            childViewHolder.tvDuration = (TextView) view.findViewById(R2.id.tvDuration);
            childViewHolder.tvNumber = (TextView) view.findViewById(R2.id.tvNumber);
            childViewHolder.phone = (ImageView) view.findViewById(R2.id.phone);
            childViewHolder.call_log_detail = (LinearLayout) view.findViewById(R2.id.call_log_detail);
            childViewHolder.message = (ImageView) view.findViewById(R2.id.message);
            childViewHolder.info = (ImageView) view.findViewById(R2.id.info);
            childViewHolder.call_log_item = (LinearLayout) view.findViewById(R2.id.call_log_item);
            childViewHolder.callCheckImg = (ImageView) view.findViewById(R2.id.callCheckImg);
            childViewHolder.addContact = (LinearLayout) view.findViewById(R2.id.addContact);
            childViewHolder.contactNumber = (LinearLayout) view.findViewById(R2.id.contactNumber);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        CallLogBean callLogBean = this.mDatas.get(i).list.get(i2);
        childViewHolder.callTypeImg.setImageResource(callLogBean.type);
        StringBuilder sb = new StringBuilder();
        if (callLogBean.count > 1) {
//             sb.append(bb7d7pu7.m5998("SUE"));
            sb.append(" (");
            sb.append(callLogBean.count);
//             sb.append(bb7d7pu7.m5998("QA"));
            sb.append(")");
        }
        String str = callLogBean.name;
        if (TextUtils.isEmpty(str)) {
            str = callLogBean.number;
            childViewHolder.addContact.setVisibility(View.VISIBLE);
            childViewHolder.contactNumber.setVisibility(View.GONE);
        } else {
            childViewHolder.addContact.setVisibility(View.GONE);
            childViewHolder.contactNumber.setVisibility(View.VISIBLE);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append((CharSequence) sb);
        childViewHolder.tvName.setText(sb2);
        childViewHolder.tvCallDate.setText(callLogBean.date);
        childViewHolder.tvNumber.setText(callLogBean.number);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(callLogBean.c);
        sb3.append(callLogBean.duration);
        childViewHolder.tvDuration.setText(sb3);
        if (i2 == 0 && z) {
            childViewHolder.call_log_root.setBackgroundResource(R2.drawable.bg_rect_round);
        } else if (i2 == 0) {
            childViewHolder.call_log_root.setBackgroundResource(R2.drawable.bg_rect_round_up);
        } else if (z) {
            childViewHolder.call_log_root.setBackgroundResource(R2.drawable.bg_rect_round_down);
        } else {
            childViewHolder.call_log_root.setBackgroundResource(R2.color.white);
        }
        childViewHolder.addContact.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Bundle bundle = new Bundle();
//                 bundle.putString(bb7d7pu7.m5998("BxwECwwb"), callLogBean.number);
                bundle.putString("number", callLogBean.number);
                CallLogAdapter.this.mOnClickListener.onJumpActivity(ContactActivity.class, bundle, true);
                childViewHolder.call_log_detail.setVisibility(View.GONE);
            }
        });
        if (this.isShowCheck) {
            childViewHolder.callCheckImg.setVisibility(View.VISIBLE);
            if (callLogBean.isCheck) {
                childViewHolder.callCheckImg.setImageResource(R2.drawable.ic_radio_on);
            } else {
                childViewHolder.callCheckImg.setImageResource(R2.drawable.ic_radio_off);
            }
            int f = i;
            int f1 = i2;
            childViewHolder.callCheckImg.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.2

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CallLogAdapter.this.mOnClickListener.onCheck(f, f1);

                }
            });
        } else {
            childViewHolder.callCheckImg.setVisibility(View.GONE);
        }
        childViewHolder.phone.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.3

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CustomDialerActivity.call(callLogBean.number, AppStartV.getContext());
            }
        });
        childViewHolder.message.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.4


            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CustomDialerActivity.sendSMS(callLogBean.number, AppStartV.getContext());
            }
        });
        childViewHolder.info.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.5

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Bundle bundle = new Bundle();
//                 bundle.putSerializable(bb7d7pu7.m5998("AAcPBg"), callLogBean);
                bundle.putSerializable("info", callLogBean);
                CallLogAdapter.this.mOnClickListener.onJumpActivity(ContactDetailActivity.class, bundle, false);
                childViewHolder.call_log_detail.setVisibility(View.GONE);
            }
        });
        int f = i;
        int f1 = i2;
        childViewHolder.call_log_item.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.6

            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (CallLogAdapter.this.isShowCheck) {
                    CallLogAdapter.this.mOnClickListener.onCheck(f, f1);
                    return;
                }
                if (CallLogAdapter.this.lastExpandView != null && CallLogAdapter.this.lastExpandView != childViewHolder.call_log_detail) {
                    CallLogAdapter.this.lastExpandView.setVisibility(View.GONE);
                }
                if (childViewHolder.call_log_detail.getVisibility() == View.VISIBLE) {
                    childViewHolder.call_log_detail.setVisibility(View.GONE);
                    return;
                }
                childViewHolder.call_log_detail.setVisibility(View.VISIBLE);
                CallLogAdapter.this.lastExpandView = childViewHolder.call_log_detail;
                return;
            }
        });
        childViewHolder.call_log_item.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.wish.lmbank.dialer.adapter.CallLogAdapter.7

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view2) {
                childViewHolder.call_log_detail.setVisibility(View.GONE);
                CallLogAdapter.this.mOnClickListener.onLongClick(i, i2);
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
