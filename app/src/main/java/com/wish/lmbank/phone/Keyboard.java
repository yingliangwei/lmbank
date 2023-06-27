package com.wish.lmbank.phone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
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

import com.wish.lmbank.R;
import com.wish.lmbank.R2;

import java.util.ArrayList;
import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/Keyboard.class */
public class Keyboard extends LinearLayout {
    private static final List e = null;
    private static final List<KeyboardBean> f = new ArrayList<>();
    public static boolean g;
    public KeyboardListener keyboardListener;
    private KeyboardAdapter mKeyboardAdapter;
    public List<KeyboardBean> mList;
    public TextView mTextView;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/Keyboard$KeyboardListener.class */
    public interface KeyboardListener {
        void keyboardClickListener(String str);
    }

    public Keyboard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mKeyboardAdapter = new KeyboardAdapter(this);
        this.mList = new ArrayList<>();
        Keyboard(context);
    }

    @SuppressLint("NotConstructor")
    private void Keyboard(Context context) {
        LayoutInflater.from(context).inflate(R2.layout.keyboard_layout_v2, this);
        GridView gridView = (GridView) findViewById(R2.id.grid_view);
        gridView.setAdapter((ListAdapter) this.mKeyboardAdapter);
        gridView.setOnItemClickListener(new OnItemClick(this));
    }

    public void setKeyboardClickListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    private void setData(List<KeyboardBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        this.mKeyboardAdapter.notifyDataSetChanged();
    }

    public void b(TextView textView) {
        this.mTextView = textView;
        g = false;
        setData(f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/Keyboard$OnItemClick.class */
    public static class OnItemClick implements AdapterView.OnItemClickListener {
        final Keyboard this$0;

        OnItemClick(Keyboard keyboard) {
            this.this$0 = keyboard;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            TextView textView = this.this$0.mTextView;
            if (textView != null) {
                String charSequence = textView.getText().toString();
                textView.setText(charSequence + this.this$0.mList.get(i).num);
                if (Build.VERSION.SDK_INT >= 23) {
                    String str = this.this$0.mList.get(i).num;
                    if (this.this$0.keyboardListener != null) {
                        this.this$0.keyboardListener.keyboardClickListener(str);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/Keyboard$KeyboardAdapter.class */
    public class KeyboardAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        final Keyboard this$0;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        KeyboardAdapter(Keyboard keyboard) {
            this.this$0 = keyboard;
            this.mLayoutInflater = LayoutInflater.from(keyboard.getContext());
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.this$0.mList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.this$0.mList.get(i);
        }

        @SuppressLint("WrongConstant")
        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view != null) {
                viewHolder = (ViewHolder) view.getTag();
            } else {
                viewHolder = new ViewHolder(this.this$0);
                view = this.mLayoutInflater.inflate(!Keyboard.g ? R.layout.bj : R.layout.bk, (ViewGroup) null);
                viewHolder.num = (TextView) view.findViewById(R2.id.num);
                viewHolder.txt1 = (TextView) view.findViewById(R2.id.text_1);
                if (Keyboard.g) {
                    viewHolder.txt2 = (TextView) view.findViewById(R2.id.text_2);
                }
                view.setTag(viewHolder);
            }
            KeyboardBean keyboardBean = this.this$0.mList.get(i);
            viewHolder.num.setText(keyboardBean.num);
            if (keyboardBean.txt1 == null) {
                viewHolder.txt1.setText("");
            } else {
                viewHolder.txt1.setText(keyboardBean.txt1);
            }
            int i2 = keyboardBean.txt1 != null ? 0 : 8;
            viewHolder.txt1.setVisibility(i2);
            int i3 = !Keyboard.g ? R.color.ak : R.color.a;
            viewHolder.num.setTextColor(this.this$0.getResources().getColor(i3));
            viewHolder.txt1.setTextColor(this.this$0.getResources().getColor(i3));
            if (Keyboard.g) {
                if (keyboardBean.txt2 != null) {
                    viewHolder.txt2.setText(keyboardBean.txt2);
                } else {
                    viewHolder.txt2.setText("");
                }
                if (keyboardBean.txt2 == null) {
                    i2 = 8;
                }
                viewHolder.txt2.setVisibility(i2);
                viewHolder.txt2.setTextColor(this.this$0.getResources().getColor(i3));
            }
            return view;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/Keyboard$ViewHolder.class */
    class ViewHolder {
        TextView num;
        final Keyboard this$0;
        TextView txt1;
        TextView txt2;

        ViewHolder(Keyboard keyboard) {
            this.this$0 = keyboard;
        }
    }
}
