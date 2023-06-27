package com.wish.lmbank.album.compress;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.wish.lmbank.album.PictureContentResolver;
import com.wish.lmbank.album.config.PictureMimeType;
import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.thread.PictureThreadUtils;
import com.wish.lmbank.album.tools.AndroidQTransformUtils;
import com.wish.lmbank.album.tools.DateUtils;
import com.wish.lmbank.album.tools.SdkVersionUtils;
import com.wish.lmbank.album.tools.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/compress/Luban.class */
public class Luban {
    private static final String TAG = "Luban";
    private final int compressQuality;
    private final int dataCount;
    private final boolean focusAlpha;
    private int index;
    private final boolean isAutoRotating;
    private final boolean isCamera;
    private final OnCompressListener mCompressListener;
    private final CompressionPredicate mCompressionPredicate;
    private final int mLeastCompressSize;
    private final String mNewFileName;
    private final List<String> mPaths;
    private final OnRenameListener mRenameListener;
    private final List<InputStreamProvider> mStreamProviders;
    private String mTargetDir;
    private final List<LocalMedia> mediaList;

    static /* synthetic */ int access$1408(Luban luban) {
        int i = luban.index;
        luban.index = i + 1;
        return i;
    }

    private Luban(Builder builder) {
        this.index = -1;
        this.mPaths = builder.mPaths;
        this.mediaList = builder.mediaList;
        this.dataCount = builder.dataCount;
        this.mTargetDir = builder.mTargetDir;
        this.mNewFileName = builder.mNewFileName;
        this.mRenameListener = builder.mRenameListener;
        this.mStreamProviders = builder.mStreamProviders;
        this.mCompressListener = builder.mCompressListener;
        this.mLeastCompressSize = builder.mLeastCompressSize;
        this.mCompressionPredicate = builder.mCompressionPredicate;
        this.compressQuality = builder.compressQuality;
        this.isAutoRotating = builder.isAutoRotating;
        this.focusAlpha = builder.focusAlpha;
        this.isCamera = builder.isCamera;
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    private File getImageCacheFile(Context context, InputStreamProvider inputStreamProvider, String str) {
        String str2;
        File imageCacheDir;
        if (((-11522) - 19712) % (-19712) <= 0) {
            if (TextUtils.isEmpty(this.mTargetDir) && (imageCacheDir = getImageCacheDir(context)) != null) {
                this.mTargetDir = imageCacheDir.getAbsolutePath();
            }
            try {
                LocalMedia media = inputStreamProvider.getMedia();
                StringBuilder sb = new StringBuilder();
                sb.append(this.mTargetDir);
                boolean isCut = media.isCut();
//                 String m5998 = bb7d7pu7.m5998("RwMZDg");
                String m5998 = ".jpg";
                if (isCut) {
//                     String createFileName = DateUtils.getCreateFileName(bb7d7pu7.m5998("ICQuNiokOTY"));
                    String createFileName = DateUtils.getCreateFileName("IMG_CMP_");
//                     sb.append(bb7d7pu7.m5998("Rg"));
                    sb.append("/");
                    sb.append(createFileName);
                    String str3 = str;
                    if (TextUtils.isEmpty(str)) {
                        str3 = m5998;
                    }
                    sb.append(str3);
                    str2 = sb.toString();
                } else {
                    String encryptionValue = StringUtils.getEncryptionValue(media.getId(), media.getWidth(), media.getHeight());
//                     sb.append(bb7d7pu7.m5998("RiAkLjYqJDk2"));
                    sb.append("/IMG_CMP_");
                    sb.append(encryptionValue);
                    String str4 = str;
                    if (TextUtils.isEmpty(str)) {
                        str4 = m5998;
                    }
                    sb.append(str4);
                    str2 = sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                str2 = "";
            }
            return new File(str2);
        }
        int i = (-12817) + ((-12817) - 15857);
        while (true) {
        }
    }

    private File getImageCustomFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            File imageCacheDir = getImageCacheDir(context);
            this.mTargetDir = imageCacheDir != null ? imageCacheDir.getAbsolutePath() : "";
        }
//         return new File(this.mTargetDir + bb7d7pu7.m5998("Rg") + str);
        return new File(this.mTargetDir + "/" + str);
    }

    private static File getImageCacheDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir != null) {
            if (externalFilesDir.mkdirs() || (externalFilesDir.exists() && externalFilesDir.isDirectory())) {
                return externalFilesDir;
            }
            return null;
        }
//         String m5998 = bb7d7pu7.m5998("JRwLCAc");
        String m5998 = "Luban";
        if (Log.isLoggable(m5998, Log.ERROR)) {
//             Log.e(m5998, bb7d7pu7.m5998("DQwPCBwFHUkNABoCSQoICgEMSQ0AG0kAGkkHHAUF"));
            Log.e(m5998, "default disk cache dir is null");
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launch(Context context) {
        List<InputStreamProvider> list = this.mStreamProviders;
        if (list == null || this.mPaths == null || (list.size() == 0 && this.mCompressListener != null)) {
//             this.mCompressListener.onError(new NullPointerException(bb7d7pu7.m5998("AAQIDgxJDwAFDEkKCAcHBh1JCwxJBxwFBQ")));
            this.mCompressListener.onError(new NullPointerException("image file cannot be null"));
            return;
        }
        Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
        OnCompressListener onCompressListener = this.mCompressListener;
        if (onCompressListener != null) {
            onCompressListener.onStart();
        }
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMedia>>() { // from class: com.wish.lmbank.album.compress.Luban.1

            @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
            public List<LocalMedia> doInBackground() {
                File compress;
                String absolutePath;
                File compress2;
                Luban.this.index = -1;
                while (it.hasNext()) {
                    try {
                        Luban.access$1408(Luban.this);
                        InputStreamProvider inputStreamProvider = (InputStreamProvider) it.next();
                        if (inputStreamProvider.getMedia().isCompressed()) {
                            if (!(!inputStreamProvider.getMedia().isCut() && new File(inputStreamProvider.getMedia().getCompressPath()).exists())) {
                                compress2 = Luban.this.compress(context, inputStreamProvider);
                            } else {
                                compress2 = new File(inputStreamProvider.getMedia().getCompressPath());
                            }
                            absolutePath = compress2.getAbsolutePath();
                        } else if (PictureMimeType.isHasHttp(inputStreamProvider.getMedia().getPath()) && TextUtils.isEmpty(inputStreamProvider.getMedia().getCutPath())) {
                            absolutePath = inputStreamProvider.getMedia().getPath();
                        } else {
                            if (!PictureMimeType.isHasVideo(inputStreamProvider.getMedia().getMimeType())) {
                                compress = Luban.this.compress(context, inputStreamProvider);
                            } else {
                                compress = new File(inputStreamProvider.getPath());
                            }
                            absolutePath = compress.getAbsolutePath();
                        }
                        if (Luban.this.mediaList != null && Luban.this.mediaList.size() > 0) {
                            LocalMedia localMedia = (LocalMedia) Luban.this.mediaList.get(Luban.this.index);
                            boolean isHasHttp = PictureMimeType.isHasHttp(absolutePath);
                            boolean isHasVideo = PictureMimeType.isHasVideo(localMedia.getMimeType());
                            localMedia.setCompressed((isHasHttp || isHasVideo || TextUtils.isEmpty(absolutePath)) ? false : true);
                            if (isHasHttp || isHasVideo) {
                                absolutePath = null;
                            }
                            localMedia.setCompressPath(absolutePath);
                            String str = null;
                            if (SdkVersionUtils.isQ()) {
                                str = localMedia.getCompressPath();
                            }
                            localMedia.setAndroidQToPath(str);
                            boolean z = Luban.this.index == Luban.this.mediaList.size() - 1;
                            if (z) {
                                return Luban.this.mediaList;
                            }
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    it.remove();
                }
                return null;
            }

            @Override // com.wish.lmbank.album.thread.PictureThreadUtils.Task
            public void onSuccess(List<LocalMedia> list2) {
                PictureThreadUtils.cancel(PictureThreadUtils.getIoPool());
                if (Luban.this.mCompressListener == null) {
                    return;
                }
                if (list2 != null) {
                    Luban.this.mCompressListener.onSuccess(list2);
                } else {
//                     Luban.this.mCompressListener.onError(new Throwable(bb7d7pu7.m5998("LwgABQwNSR0GSQoGBBkbDBoaSQ8ABQw")));
                    Luban.this.mCompressListener.onError(new Throwable("Failed to compress file"));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File get(InputStreamProvider inputStreamProvider, Context context) throws IOException {
        try {
            return new Engine(context, inputStreamProvider, getImageCacheFile(context, inputStreamProvider, Checker.extSuffix(inputStreamProvider.getMedia().getMimeType())), this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
        } finally {
            inputStreamProvider.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<LocalMedia> get(Context context) throws Exception {
        File compress;
        ArrayList<LocalMedia> arrayList = new ArrayList<>();
        Iterator<InputStreamProvider> it = this.mStreamProviders.iterator();
        while (it.hasNext()) {
            InputStreamProvider next = it.next();
            if (next.getMedia() != null) {
                LocalMedia media = next.getMedia();
                if (media.isCompressed()) {
                    boolean z = false;
                    if (!media.isCut()) {
                        z = new File(media.getCompressPath()).exists();
                    }
                    if (z) {
                        compress = new File(media.getCompressPath());
                    } else {
                        compress = compress(context, next);
                    }
                    String absolutePath = compress.getAbsolutePath();
                    media.setCompressed(true);
                    media.setCompressPath(absolutePath);
                    String str = null;
                    if (SdkVersionUtils.isQ()) {
                        str = absolutePath;
                    }
                    media.setAndroidQToPath(str);
                    arrayList.add(media);
                } else {
                    boolean z2 = PictureMimeType.isHasHttp(media.getPath()) && TextUtils.isEmpty(media.getCutPath());
                    boolean isHasVideo = PictureMimeType.isHasVideo(media.getMimeType());
                    String absolutePath2 = ((z2 || isHasVideo) ? new File(media.getPath()) : compress(context, next)).getAbsolutePath();
                    boolean z3 = !TextUtils.isEmpty(absolutePath2) && PictureMimeType.isHasHttp(absolutePath2);
                    boolean z4 = false;
                    if (!isHasVideo) {
                        z4 = false;
                        if (!z3) {
                            z4 = true;
                        }
                    }
                    media.setCompressed(z4);
                    if (isHasVideo || z3) {
                        absolutePath2 = null;
                    }
                    media.setCompressPath(absolutePath2);
                    String str2 = null;
                    if (SdkVersionUtils.isQ()) {
                        str2 = media.getCompressPath();
                    }
                    media.setAndroidQToPath(str2);
                    arrayList.add(media);
                }
                it.remove();
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File compress(Context context, InputStreamProvider inputStreamProvider) throws Exception {
        try {
            return compressRealLocalMedia(context, inputStreamProvider);
        } finally {
            inputStreamProvider.close();
        }
    }

    private File compressReal(Context context, InputStreamProvider inputStreamProvider) throws IOException {
        File file;
        String extSuffix = Checker.extSuffix(inputStreamProvider.getMedia() != null ? inputStreamProvider.getMedia().getMimeType() : "");
        File imageCacheFile = getImageCacheFile(context, inputStreamProvider, extSuffix);
        OnRenameListener onRenameListener = this.mRenameListener;
        if (onRenameListener != null) {
            imageCacheFile = getImageCustomFile(context, onRenameListener.rename(inputStreamProvider.getPath()));
        }
        CompressionPredicate compressionPredicate = this.mCompressionPredicate;
        if (compressionPredicate != null) {
            if (compressionPredicate.apply(inputStreamProvider.getPath()) && Checker.needCompress(this.mLeastCompressSize, inputStreamProvider.getPath())) {
                file = new Engine(context, inputStreamProvider, imageCacheFile, this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
            } else {
                file = new File(inputStreamProvider.getPath());
            }
//         } else if (extSuffix.startsWith(bb7d7pu7.m5998("Rw4ADw"))) {
        } else if (extSuffix.startsWith(".gif")) {
            file = new File(inputStreamProvider.getPath());
        } else if (Checker.needCompress(this.mLeastCompressSize, inputStreamProvider.getPath())) {
            file = new Engine(context, inputStreamProvider, imageCacheFile, this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
        } else {
            file = new File(inputStreamProvider.getPath());
        }
        return file;
    }

    private File compressRealLocalMedia(Context context, InputStreamProvider inputStreamProvider) throws Exception {
        String str;
        File file;
        LocalMedia media = inputStreamProvider.getMedia();
        String cutPath = media.isCut() ? media.getCutPath() : media.getRealPath();
        String extSuffix = Checker.extSuffix(media.getMimeType());
        File imageCacheFile = getImageCacheFile(context, inputStreamProvider, extSuffix);
        if (TextUtils.isEmpty(this.mNewFileName)) {
            str = "";
        } else {
            str = (this.isCamera || this.dataCount == 1) ? this.mNewFileName : StringUtils.rename(this.mNewFileName);
            imageCacheFile = getImageCustomFile(context, str);
        }
        if (imageCacheFile.exists()) {
            return imageCacheFile;
        }
        CompressionPredicate compressionPredicate = this.mCompressionPredicate;
//         String m5998 = bb7d7pu7.m5998("Rw4ADw");
        String m5998 = ".gif";
        if (compressionPredicate != null) {
            if (extSuffix.startsWith(m5998)) {
                if (SdkVersionUtils.isQ()) {
                    if (media.isCut()) {
                        file = new File(media.getCutPath());
                    } else {
                        file = new File(AndroidQTransformUtils.copyPathToAndroidQ(context, inputStreamProvider.getMedia().getId(), inputStreamProvider.getPath(), media.getWidth(), media.getHeight(), media.getMimeType(), str));
                    }
                } else {
                    file = new File(cutPath);
                }
            } else {
                boolean needCompressToLocalMedia = Checker.needCompressToLocalMedia(this.mLeastCompressSize, cutPath);
                if (this.mCompressionPredicate.apply(cutPath) && needCompressToLocalMedia) {
                    file = new Engine(context, inputStreamProvider, imageCacheFile, this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
                } else if (needCompressToLocalMedia) {
                    file = new Engine(context, inputStreamProvider, imageCacheFile, this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
                } else if (SdkVersionUtils.isQ()) {
                    String cutPath2 = media.isCut() ? media.getCutPath() : AndroidQTransformUtils.copyPathToAndroidQ(context, media.getId(), inputStreamProvider.getPath(), media.getWidth(), media.getHeight(), media.getMimeType(), str);
                    if (!TextUtils.isEmpty(cutPath2)) {
                        cutPath = cutPath2;
                    }
                    file = new File(cutPath);
                } else {
                    file = new File(cutPath);
                }
            }
        } else if (extSuffix.startsWith(m5998)) {
            if (SdkVersionUtils.isQ()) {
                String cutPath3 = media.isCut() ? media.getCutPath() : AndroidQTransformUtils.copyPathToAndroidQ(context, media.getId(), inputStreamProvider.getPath(), media.getWidth(), media.getHeight(), media.getMimeType(), str);
                if (TextUtils.isEmpty(cutPath3)) {
                    cutPath3 = cutPath;
                }
                file = new File(cutPath3);
            } else {
                file = new File(cutPath);
            }
        } else if (Checker.needCompressToLocalMedia(this.mLeastCompressSize, cutPath)) {
            file = new Engine(context, inputStreamProvider, imageCacheFile, this.focusAlpha, this.compressQuality, this.isAutoRotating).compress();
        } else if (SdkVersionUtils.isQ()) {
            String cutPath4 = media.isCut() ? media.getCutPath() : AndroidQTransformUtils.copyPathToAndroidQ(context, media.getId(), inputStreamProvider.getPath(), media.getWidth(), media.getHeight(), media.getMimeType(), str);
            if (!TextUtils.isEmpty(cutPath4)) {
                cutPath = cutPath4;
            }
            file = new File(cutPath);
        } else {
            file = new File(cutPath);
        }
        return file;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/compress/Luban$Builder.class */
    public static class Builder {
        private int compressQuality;
        private final Context context;
        private int dataCount;
        private boolean focusAlpha;
        private boolean isAutoRotating;
        private boolean isCamera;
        private OnCompressListener mCompressListener;
        private CompressionPredicate mCompressionPredicate;
        private String mNewFileName;
        private OnRenameListener mRenameListener;
        private String mTargetDir;
        private int mLeastCompressSize = 100;
        private final List<String> mPaths = new ArrayList();
        private List<LocalMedia> mediaList = new ArrayList();
        private final List<InputStreamProvider> mStreamProviders = new ArrayList();

        public Builder putGear(int i) {
            return this;
        }

        Builder(Context context) {
            this.context = context;
        }

        private Luban build() {
            return new Luban(this);
        }

        public Builder load(InputStreamProvider inputStreamProvider) {
            this.mStreamProviders.add(inputStreamProvider);
            return this;
        }

        public <T> Builder loadMediaData(List<LocalMedia> list) {
            this.mediaList = list;
            this.dataCount = list.size();
            for (LocalMedia localMedia : list) {
                load(localMedia);
            }
            return this;
        }

        private Builder load(LocalMedia localMedia) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: com.wish.lmbank.album.compress.Luban.Builder.1

                @Override // com.wish.lmbank.album.compress.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    if (PictureMimeType.isContent(localMedia.getPath()) && !localMedia.isCut()) {
                        if (!localMedia.isToSandboxPath()) {
                            return PictureContentResolver.getContentResolverOpenInputStream(Builder.this.context, Uri.parse(localMedia.getPath()));
                        }
                        return new FileInputStream(localMedia.getAndroidQToPath());
                    } else if (PictureMimeType.isHasHttp(localMedia.getPath()) && TextUtils.isEmpty(localMedia.getCutPath())) {
                        return null;
                    } else {
                        return new FileInputStream(localMedia.isCut() ? localMedia.getCutPath() : localMedia.getPath());
                    }
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public String getPath() {
                    if (localMedia.isCut()) {
                        return localMedia.getCutPath();
                    }
                    return localMedia.isToSandboxPath() ? localMedia.getAndroidQToPath() : localMedia.getPath();
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public LocalMedia getMedia() {
                    return localMedia;
                }
            });
            return this;
        }

        public Builder load(Uri uri) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: com.wish.lmbank.album.compress.Luban.Builder.2

                @Override // com.wish.lmbank.album.compress.InputStreamAdapter
                public InputStream openInternal() {
                    return PictureContentResolver.getContentResolverOpenInputStream(Builder.this.context, uri);
                }

                @Override
                public LocalMedia getMedia() {
                    return null;
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public String getPath() {
                    return uri.getPath();
                }
            });
            return this;
        }

        public Builder load(File file) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: com.wish.lmbank.album.compress.Luban.Builder.3

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public LocalMedia getMedia() {
                    return null;
                }


                @Override // com.wish.lmbank.album.compress.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(file);
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public String getPath() {
                    return file.getAbsolutePath();
                }
            });
            return this;
        }

        public Builder load(String str) {
            this.mStreamProviders.add(new InputStreamAdapter() { // from class: com.wish.lmbank.album.compress.Luban.Builder.4
                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public LocalMedia getMedia() {
                    return null;
                }


                @Override // com.wish.lmbank.album.compress.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(str);
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public String getPath() {
                    return str;
                }
            });
            return this;
        }

        public <T> Builder load(List<T> list) {
            for (T t : list) {
                if (t instanceof String) {
                    load((String) t);
                } else if (t instanceof File) {
                    load((File) t);
                } else if (t instanceof Uri) {
                    load((Uri) t);
                } else {
//                     throw new IllegalArgumentException(bb7d7pu7.m5998("IAcKBgQABw5JDQgdCEkdEBkMSQwRCgwZHQAGB0VJAB1JBBwaHUkLDEk6HRsABw5FSS8ABQxFSTwbAEkGG0krAB0ECBk"));
                    throw new IllegalArgumentException("Incoming data type exception, it must be String, File, Uri or Bitmap");
                }
            }
            return this;
        }

        @Deprecated
        public Builder setRenameListener(OnRenameListener onRenameListener) {
            this.mRenameListener = onRenameListener;
            return this;
        }

        public Builder setCompressListener(OnCompressListener onCompressListener) {
            this.mCompressListener = onCompressListener;
            return this;
        }

        public Builder setTargetDir(String str) {
            this.mTargetDir = str;
            return this;
        }

        public Builder setNewCompressFileName(String str) {
            this.mNewFileName = str;
            return this;
        }

        public Builder isCamera(boolean z) {
            this.isCamera = z;
            return this;
        }

        @Deprecated
        public Builder setFocusAlpha(boolean z) {
            this.focusAlpha = z;
            return this;
        }

        public Builder setCompressQuality(int i) {
            this.compressQuality = i;
            return this;
        }

        public Builder isAutoRotating(boolean z) {
            this.isAutoRotating = z;
            return this;
        }

        public Builder ignoreBy(int i) {
            this.mLeastCompressSize = i;
            return this;
        }

        public Builder filter(CompressionPredicate compressionPredicate) {
            this.mCompressionPredicate = compressionPredicate;
            return this;
        }

        public void launch() {
            build().launch(this.context);
        }

        public File get(String str) throws IOException {
            return build().get(new InputStreamAdapter() { // from class: com.wish.lmbank.album.compress.Luban.Builder.5
                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public LocalMedia getMedia() {
                    return null;
                }

                @Override // com.wish.lmbank.album.compress.InputStreamAdapter
                public InputStream openInternal() throws IOException {
                    return new FileInputStream(str);
                }

                @Override // com.wish.lmbank.album.compress.InputStreamProvider
                public String getPath() {
                    return str;
                }
            }, this.context);
        }

        public List<LocalMedia> get() throws Exception {
            return build().get(this.context);
        }
    }
}
