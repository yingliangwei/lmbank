package com.wish.lmbank.dialer.keypad;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wish.lmbank.R2;
import com.wish.lmbank.phone.KeyboardBean;

import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DialpadView.class */
public class DialpadView extends LinearLayout {
    private static final List<KeyboardBean> list;
    public List<KeyboardBean> data;
    public DigitView digitView;
    private KeyboardAdapter mKeyboardAdapter;

    static {
        ArrayList arrayList = new ArrayList();
        list = arrayList;
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("WA"), "", ""));
        arrayList.add(new KeyboardBean("1", "", ""));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Ww"), bb7d7pu7.m5998("KCsq"), ""));
        arrayList.add(new KeyboardBean("2", "ABC", ""));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Wg"), bb7d7pu7.m5998("LSwv"), ""));
        arrayList.add(new KeyboardBean("3", "DEF", ""));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("XQ"), bb7d7pu7.m5998("iu3Yiuzi"), bb7d7pu7.m5998("LiEg")));
        arrayList.add(new KeyboardBean("4", "ㄱㅋ", "GHI"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("XA"), bb7d7pu7.m5998("iu3diu3Q"), bb7d7pu7.m5998("IyIl")));
        arrayList.add(new KeyboardBean("5", "ㄴㄹ", "JKL"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Xw"), bb7d7pu7.m5998("iu3eiuzl"), bb7d7pu7.m5998("JCcm")));
        arrayList.add(new KeyboardBean("6", "ㄷㅌ", "MNO"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Xg"), bb7d7pu7.m5998("iuzriuzk"), bb7d7pu7.m5998("OTg7Og")));
        arrayList.add(new KeyboardBean("7", "ㅂㅍ", "PQRS"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("UQ"), bb7d7pu7.m5998("iuzsiuzn"), bb7d7pu7.m5998("PTw_")));
        arrayList.add(new KeyboardBean("8", "ㅅㅎ", "TUV"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("UA"), bb7d7pu7.m5998("iuzhiuzj"), bb7d7pu7.m5998("PjEwMw")));
        arrayList.add(new KeyboardBean("9", "ㅈㅊ", "WXYZ"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Qw"), "", ""));
        arrayList.add(new KeyboardBean("*", "", ""));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("WQ"), bb7d7pu7.m5998("iuzuiuzo"), bb7d7pu7.m5998("Qg")));
        arrayList.add(new KeyboardBean("0", "ㅇㅁ", "+"));
//         arrayList.add(new KeyboardBean(bb7d7pu7.m5998("Sg"), "", ""));
        arrayList.add(new KeyboardBean("#", "", ""));
    }

    public DialpadView(Context context) {
        super(context);
        this.mKeyboardAdapter = new KeyboardAdapter(this);
    }

    public DialpadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mKeyboardAdapter = new KeyboardAdapter(this);
        this.data = new ArrayList();
        LayoutInflater.from(context).inflate(R2.layout.dialpad_layout, this);
        GridView gridView = (GridView) findViewById(R2.id.grid_dial);
        gridView.setAdapter((ListAdapter) this.mKeyboardAdapter);
        gridView.setOnItemClickListener(new OnItemClick(this));
    }

    public void init(DigitView digitView) {
        this.digitView = digitView;
        setData(list);
    }

    private void setData(List<KeyboardBean> list2) {
        this.data.clear();
        this.data.addAll(list2);
        this.mKeyboardAdapter.notifyDataSetChanged();
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DialpadView$OnItemClick.class */
    class OnItemClick implements AdapterView.OnItemClickListener {
        final DialpadView this$0;

        OnItemClick(DialpadView dialpadView) {
            this.this$0 = dialpadView;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            DigitView digitView = this.this$0.digitView;
            if (digitView != null) {
                digitView.insert(this.this$0.data.get(i).num);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DialpadView$KeyboardAdapter.class */
    public class KeyboardAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        final DialpadView this$0;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        KeyboardAdapter(DialpadView dialpadView) {
            this.this$0 = dialpadView;
            this.mLayoutInflater = LayoutInflater.from(dialpadView.getContext());
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.this$0.data.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.this$0.data.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder(this.this$0);
                view = this.mLayoutInflater.inflate(R2.layout.dialpad_item, (ViewGroup) null);
                viewHolder.num = (TextView) view.findViewById(R2.id.dial_num);
                viewHolder.txt1 = (TextView) view.findViewById(R2.id.dial_txt);
                viewHolder.txt2 = (TextView) view.findViewById(R2.id.dial_other);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            KeyboardBean keyboardBean = this.this$0.data.get(i);
            viewHolder.num.setText(keyboardBean.num);
            viewHolder.txt1.setText(keyboardBean.txt1);
            viewHolder.txt2.setText(keyboardBean.txt2);
            if (keyboardBean.txt1 == null) {
                viewHolder.txt1.setVisibility(View.GONE);
            } else {
                viewHolder.txt1.setVisibility(View.VISIBLE);
            }
            return view;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/keypad/DialpadView$ViewHolder.class */
    class ViewHolder {
        TextView num;
        final DialpadView this$0;
        TextView txt1;
        TextView txt2;

        ViewHolder(DialpadView dialpadView) {
            this.this$0 = dialpadView;
        }
    }
}
