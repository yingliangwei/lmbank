package com.wish.lmbank.base;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/base/JBaseFragment.class */
public abstract class JBaseFragment extends Fragment implements View.OnClickListener {
    private static final int TIME = 1000;
    private static long lastClickTime;
    protected final String TAG = getClass().getSimpleName();
    protected boolean isFirst;
    private boolean isPrepared;
    protected boolean isVisible;
    protected View mView;

    protected abstract int getCreateViewLayoutId();

    protected void initData(View view) {
    }

    protected void initListener() {
    }

    protected abstract void initView(View view, Bundle bundle);

    protected void lazyLoad() {
    }

    protected abstract void lazyLoadOnlyOne();

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.isPrepared = true;
        this.isFirst = true;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (((-17098) - 18490) % (-18490) <= 0) {
            if (this.mView == null) {
                getActivity().setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                if (getCreateViewLayoutId() > 0) {
                    this.mView = layoutInflater.inflate(getCreateViewLayoutId(), viewGroup, false);
                }
                this.mView.setOnTouchListener(new View.OnTouchListener() { // from class: com.wish.lmbank.base.JBaseFragment.1

                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }


                });
            }
            initData(this.mView);
            initView(this.mView, bundle);
            initListener();
            return this.mView;
        }
        int i = 8107 + 8107 + 18250;
        while (true) {
        }
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            this.isVisible = true;
            if (this.isPrepared) {
                if (this.isFirst) {
                    this.isFirst = false;
                    lazyLoadOnlyOne();
                    return;
                }
                lazyLoad();
                return;
            }
            return;
        }
        this.isVisible = false;
    }

    protected final <E extends View> E getView(View view, int i) {
        try {
            return (E) view.findViewById(i);
        } catch (ClassCastException e) {
            throw e;
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (isFastDoubleClick()) {
        }
    }

    public static boolean isFastDoubleClick() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = currentTimeMillis - lastClickTime;
        if (0 < j && j < 1000) {
            return true;
        }
        lastClickTime = currentTimeMillis;
        return false;
    }

    protected void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
