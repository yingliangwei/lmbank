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
import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.ContactActivity;
import com.wish.lmbank.dialer.ContactDetailActivity;
import com.wish.lmbank.dialer.CustomDialerActivity;
import com.wish.lmbank.dialer.bean.CallLogBean;
import com.wish.lmbank.dialer.bean.ContactBean;
import com.wish.lmbank.dialer.bean.DialerSearchBean;
import com.wish.lmbank.dialer.contact.RoundLetterView;
import com.wish.lmbank.dialer.utils.ColorUtils;

import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/DialerSearchAdapter.class */
public class DialerSearchAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<DialerSearchBean> mDatas;
    private LayoutInflater mInflater;
    private OnClickListener mOnClickListener;
    private View lastExpandView = null;
    private boolean isShowCheck = false;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/DialerSearchAdapter$ChildViewCallLogHolder.class */
    public static class ChildViewCallLogHolder {
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

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/DialerSearchAdapter$ChildViewContactHolder.class */
    public static class ChildViewContactHolder {
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

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/DialerSearchAdapter$OnClickListener.class */
    public interface OnClickListener {
        void onCheck(int i, int i2);

        void onJumpActivity(Class cls, Bundle bundle, boolean z);

        void onLongClick(int i, int i2);
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/adapter/DialerSearchAdapter$ParentViewHolder.class */
    public static class ParentViewHolder {
        public TextView tv_group;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        if ((10225 + 7459) % 7459 > 0) {
            return i;
        }
        int i2 = 5563 + 5563 + 6693;
        while (true) {
        }
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return false;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public DialerSearchAdapter(Context context, List<DialerSearchBean> list, OnClickListener onClickListener) {
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
        if (this.mDatas.get(i).type == 1) {
            return this.mDatas.get(i).getContactChildrenSize();
        }
        if (this.mDatas.get(i).type == 2) {
            return this.mDatas.get(i).getCallLogChildrenSize();
        }
        return 0;
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getGroup(int i) {
        return this.mDatas.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public Object getChild(int i, int i2) {
        if (((-19185) + 3434) % 3434 <= 0) {
            if (this.mDatas.get(i).type == 1) {
                return this.mDatas.get(i).getContactChildren().get(i2);
            }
            if (this.mDatas.get(i).type != 2) {
                return null;
            }
            return this.mDatas.get(i).getCallLogChildren().get(i2);
        }
        int i3 = 7149 + 7149 + 7653;
        while (true) {
        }
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
        parentViewHolder.tv_group.setText(this.mDatas.get(i).title);
        return view;
    }

    @Override // android.widget.ExpandableListAdapter
    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        ChildViewCallLogHolder childViewCallLogHolder;
        ChildViewContactHolder childViewContactHolder;
        DialerSearchBean dialerSearchBean = this.mDatas.get(i);
        if (dialerSearchBean.type == 2) {
            if (view != null && (view == null || (view.getTag() instanceof ChildViewCallLogHolder))) {
                childViewCallLogHolder = (ChildViewCallLogHolder) view.getTag();
            } else {
                view = this.mInflater.inflate(R2.layout.call_log_list, viewGroup, false);
                childViewCallLogHolder = new ChildViewCallLogHolder();
                childViewCallLogHolder.call_log_root = (RelativeLayout) view.findViewById(R2.id.call_log_root);
                childViewCallLogHolder.callTypeImg = (ImageView) view.findViewById(R2.id.callTypeImg);
                childViewCallLogHolder.tvName = (TextView) view.findViewById(R2.id.tvName);
                childViewCallLogHolder.tvCallDate = (TextView) view.findViewById(R2.id.tvCallDate);
                childViewCallLogHolder.tvDuration = (TextView) view.findViewById(R2.id.tvDuration);
                childViewCallLogHolder.tvNumber = (TextView) view.findViewById(R2.id.tvNumber);
                childViewCallLogHolder.phone = (ImageView) view.findViewById(R2.id.phone);
                childViewCallLogHolder.call_log_detail = (LinearLayout) view.findViewById(R2.id.call_log_detail);
                childViewCallLogHolder.message = (ImageView) view.findViewById(R2.id.message);
                childViewCallLogHolder.info = (ImageView) view.findViewById(R2.id.info);
                childViewCallLogHolder.call_log_item = (LinearLayout) view.findViewById(R2.id.call_log_item);
                childViewCallLogHolder.callCheckImg = (ImageView) view.findViewById(R2.id.callCheckImg);
                childViewCallLogHolder.addContact = (LinearLayout) view.findViewById(R2.id.addContact);
                childViewCallLogHolder.contactNumber = (LinearLayout) view.findViewById(R2.id.contactNumber);
                view.setTag(childViewCallLogHolder);
            }
            CallLogBean callLogBean = this.mDatas.get(i).getCallLogChildren().get(i2);
            childViewCallLogHolder.callTypeImg.setImageResource(callLogBean.type);
            StringBuilder sb = new StringBuilder();
            if (callLogBean.count > 1) {
//                 sb.append(bb7d7pu7.m5998("SUE"));
                sb.append(" (");
                sb.append(callLogBean.count);
//                 sb.append(bb7d7pu7.m5998("QA"));
                sb.append(")");
            }
            String str = callLogBean.name;
            if (TextUtils.isEmpty(str)) {
                str = callLogBean.number;
                childViewCallLogHolder.addContact.setVisibility(View.VISIBLE);
                childViewCallLogHolder.contactNumber.setVisibility(View.GONE);
            } else {
                childViewCallLogHolder.addContact.setVisibility(View.GONE);
                childViewCallLogHolder.contactNumber.setVisibility(View.VISIBLE);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append((CharSequence) sb);
            childViewCallLogHolder.tvName.setText(sb2);
            childViewCallLogHolder.tvCallDate.setText(callLogBean.date);
            childViewCallLogHolder.tvNumber.setText(callLogBean.number);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(callLogBean.c);
            sb3.append(callLogBean.duration);
            childViewCallLogHolder.tvDuration.setText(sb3);
            if (i2 == 0 && z) {
                childViewCallLogHolder.call_log_root.setBackgroundResource(R.drawable.at);
            } else if (i2 == 0) {
                childViewCallLogHolder.call_log_root.setBackgroundResource(R.drawable.av);
            } else if (z) {
                childViewCallLogHolder.call_log_root.setBackgroundResource(R.drawable.au);
            } else {
                childViewCallLogHolder.call_log_root.setBackgroundResource(R.color.white);
            }
            childViewCallLogHolder.addContact.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.1

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Bundle bundle = new Bundle();
//                     bundle.putString(bb7d7pu7.m5998("BxwECwwb"), callLogBean.number);
                    bundle.putString("number", callLogBean.number);
                    DialerSearchAdapter.this.mOnClickListener.onJumpActivity(ContactActivity.class, bundle, true);
                    childViewCallLogHolder.call_log_detail.setVisibility(View.GONE);
                }
            });
            if (this.isShowCheck) {
                childViewCallLogHolder.callCheckImg.setVisibility(View.VISIBLE);
                if (callLogBean.isCheck) {
                    childViewCallLogHolder.callCheckImg.setImageResource(R.drawable.cs);
                } else {
                    childViewCallLogHolder.callCheckImg.setImageResource(R.drawable.cr);
                }
                childViewCallLogHolder.callCheckImg.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.2

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        DialerSearchAdapter.this.mOnClickListener.onCheck(i, i2);
                    }
                });
            } else {
                childViewCallLogHolder.callCheckImg.setVisibility(View.GONE);
            }
            childViewCallLogHolder.phone.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.3

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CustomDialerActivity.call(callLogBean.number, AppStartV.getContext());
                }
            });
            childViewCallLogHolder.message.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.4

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CustomDialerActivity.sendSMS(callLogBean.number, AppStartV.getContext());
                }
            });
            childViewCallLogHolder.info.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.5

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Bundle bundle = new Bundle();
//                     bundle.putSerializable(bb7d7pu7.m5998("AAcPBg"), callLogBean);
                    bundle.putSerializable("info", callLogBean);
                    DialerSearchAdapter.this.mOnClickListener.onJumpActivity(ContactDetailActivity.class, bundle, false);
                    childViewCallLogHolder.call_log_detail.setVisibility(View.GONE);
                }
            });
            childViewCallLogHolder.call_log_item.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.6


                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DialerSearchAdapter.this.isShowCheck) {
                        DialerSearchAdapter.this.mOnClickListener.onCheck(i, i2);
                        return;
                    }
                    if (DialerSearchAdapter.this.lastExpandView != null && DialerSearchAdapter.this.lastExpandView != childViewCallLogHolder.call_log_detail) {
                        DialerSearchAdapter.this.lastExpandView.setVisibility(View.GONE);
                    }
                    if (childViewCallLogHolder.call_log_detail.getVisibility() == View.VISIBLE) {
                        childViewCallLogHolder.call_log_detail.setVisibility(View.GONE);
                        return;
                    }
                    childViewCallLogHolder.call_log_detail.setVisibility(View.VISIBLE);
                    DialerSearchAdapter.this.lastExpandView = childViewCallLogHolder.call_log_detail;
                }
            });
            childViewCallLogHolder.call_log_item.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.7

                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    childViewCallLogHolder.call_log_detail.setVisibility(View.GONE);
                    DialerSearchAdapter.this.mOnClickListener.onLongClick(i, i2);
                    return false;
                }
            });
        } else if (dialerSearchBean.type == 1) {
            if (view != null && view.getTag() instanceof ChildViewContactHolder) {
                childViewContactHolder = (ChildViewContactHolder) view.getTag();
            } else {
                view = this.mInflater.inflate(R2.layout.contact_list, viewGroup, false);
                childViewContactHolder = new ChildViewContactHolder();
                childViewContactHolder.contact_root = (RelativeLayout) view.findViewById(R2.id.contact_root);
                childViewContactHolder.contact_item = (LinearLayout) view.findViewById(R2.id.contact_item);
                childViewContactHolder.contact_detail = (LinearLayout) view.findViewById(R2.id.contact_detail);
                childViewContactHolder.vRoundLetterView = (RoundLetterView) view.findViewById(R2.id.vRoundLetterView);
                childViewContactHolder.tvContactName = (TextView) view.findViewById(R2.id.tvContactName);
                childViewContactHolder.tvNumber = (TextView) view.findViewById(R2.id.tvNumber);
                childViewContactHolder.phone = (ImageView) view.findViewById(R2.id.phone);
                childViewContactHolder.message = (ImageView) view.findViewById(R2.id.message);
                childViewContactHolder.edit = (ImageView) view.findViewById(R2.id.edit);
                childViewContactHolder.ivSelectedState = (ImageView) view.findViewById(R2.id.ivSelectedState);
                view.setTag(childViewContactHolder);
            }
            ContactBean contactBean = this.mDatas.get(i).getContactChildren().get(i2);
            childViewContactHolder.tvNumber.setText(String.valueOf(contactBean.data1));
            childViewContactHolder.vRoundLetterView.setBackgroundColor(ColorUtils.getColor());
            childViewContactHolder.vRoundLetterView.setTitleText(String.valueOf(contactBean.firstLetter));
            childViewContactHolder.tvContactName.setText(String.valueOf(contactBean.displayName));
            if (i2 == 0 && z) {
                childViewContactHolder.contact_root.setBackgroundResource(R.drawable.at);
            } else if (i2 == 0) {
                childViewContactHolder.contact_root.setBackgroundResource(R.drawable.av);
            } else if (z) {
                childViewContactHolder.contact_root.setBackgroundResource(R.drawable.au);
            } else {
                childViewContactHolder.contact_root.setBackgroundResource(R.color.white);
            }
            if (this.isShowCheck) {
                childViewContactHolder.vRoundLetterView.setVisibility(View.GONE);
                childViewContactHolder.ivSelectedState.setVisibility(View.VISIBLE);

                if (contactBean.isCheck) {
                    childViewContactHolder.ivSelectedState.setImageResource(R.drawable.cs);
                } else {
                    childViewContactHolder.ivSelectedState.setImageResource(R.drawable.cr);
                }
                childViewContactHolder.ivSelectedState.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.8


                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        DialerSearchAdapter.this.mOnClickListener.onCheck(i, i2);
                    }
                });
            } else {
                childViewContactHolder.vRoundLetterView.setVisibility(View.VISIBLE);
                childViewContactHolder.ivSelectedState.setVisibility(View.GONE);
            }
            childViewContactHolder.phone.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.9

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CustomDialerActivity.call(contactBean.data1, AppStartV.getContext());
                }
            });
            childViewContactHolder.message.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.10

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CustomDialerActivity.sendSMS(contactBean.data1, AppStartV.getContext());
                }
            });
            childViewContactHolder.edit.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.11

                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    Bundle bundle = new Bundle();
//                     bundle.putString(bb7d7pu7.m5998("BwgEDA"), contactBean.displayName);
                    bundle.putString("name", contactBean.displayName);
//                     bundle.putString(bb7d7pu7.m5998("BxwECwwb"), contactBean.data1);
                    bundle.putString("number", contactBean.data1);
                    DialerSearchAdapter.this.mOnClickListener.onJumpActivity(ContactActivity.class, bundle, true);
                }
            });
            childViewContactHolder.contact_item.setOnClickListener(new View.OnClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.12


                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DialerSearchAdapter.this.isShowCheck) {
                        DialerSearchAdapter.this.mOnClickListener.onCheck(i, i2);
                        return;
                    }
                    if (DialerSearchAdapter.this.lastExpandView != null && DialerSearchAdapter.this.lastExpandView !=childViewContactHolder.contact_detail) {
                        DialerSearchAdapter.this.lastExpandView.setVisibility(View.GONE);
                    }
                    if (childViewContactHolder.contact_detail.getVisibility() == View.VISIBLE) {
                        childViewContactHolder.contact_detail.setVisibility(View.GONE);
                        return;
                    }
                    childViewContactHolder.contact_detail.setVisibility(View.VISIBLE);
                    DialerSearchAdapter.this.lastExpandView = childViewContactHolder.contact_detail;
                }
            });
            childViewContactHolder.contact_item.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.wish.lmbank.dialer.adapter.DialerSearchAdapter.13

                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                   childViewContactHolder.contact_detail.setVisibility(View.GONE);
                    DialerSearchAdapter.this.mOnClickListener.onLongClick(i, i2);
                    return false;
                }
            });
        }
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
