package com.wish.lmbank.album.model;

import android.content.ContentResolver;
import android.content.Context;

import com.wish.lmbank.album.config.PictureSelectionConfig;
import com.wish.lmbank.album.entity.LocalMediaFolder;
import com.wish.lmbank.album.listener.OnQueryDataResultListener;
import com.wish.lmbank.album.thread.PictureThreadUtils;
import com.wish.lmbank.album.tools.SdkVersionUtils;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class LocalMediaLoader extends IBridgeMediaLoader {

    private final PictureSelectionConfig config;

    private final boolean isAndroidQ;

    private final Context mContext;

    public LocalMediaLoader(Context paramContext, PictureSelectionConfig paramPictureSelectionConfig) {
        this.mContext = paramContext.getApplicationContext();
        this.isAndroidQ = SdkVersionUtils.isQ();
        this.config = paramPictureSelectionConfig;
    }

    public void loadAllMedia(final OnQueryDataResultListener<LocalMediaFolder> listener) {
        PictureThreadUtils.SimpleTask<List<LocalMediaFolder>> simpleTask = new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() {

            public List<LocalMediaFolder> doInBackground() {
                    ArrayList arrayList = new ArrayList();
                    LocalMediaLoader localMediaLoader = LocalMediaLoader.this;
                    Context context = localMediaLoader.mContext;
                    ContentResolver contentResolver = context.getContentResolver();
                arrayList.add(localMediaLoader);
                    return arrayList;
            }

            public void onSuccess(List<LocalMediaFolder> param1List) {
                PictureThreadUtils.cancel(PictureThreadUtils.getIoPool());
                OnQueryDataResultListener onQueryDataResultListener = listener;
                if (onQueryDataResultListener != null)
                    onQueryDataResultListener.onComplete(param1List);
            }
        };
        PictureThreadUtils.executeByIo((PictureThreadUtils.Task)simpleTask);
    }



}
