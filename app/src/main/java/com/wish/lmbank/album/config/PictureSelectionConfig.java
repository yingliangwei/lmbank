package com.wish.lmbank.album.config;

import android.media.session.PlaybackState;
import android.os.Parcel;
import android.os.Parcelable;

import com.wish.lmbank.album.entity.LocalMedia;
import com.wish.lmbank.album.listener.OnResultCallbackListener;
import com.wish.lmbank.album.tools.SdkVersionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/config/PictureSelectionConfig.class */
public final class PictureSelectionConfig implements Parcelable {
    public static final Creator<PictureSelectionConfig> CREATOR = new Creator<PictureSelectionConfig>() { // from class: com.wish.lmbank.album.config.PictureSelectionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureSelectionConfig createFromParcel(Parcel parcel) {
            return new PictureSelectionConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PictureSelectionConfig[] newArray(int i) {
            return new PictureSelectionConfig[i];
        }
    };
    public static OnResultCallbackListener<LocalMedia> listener;
    public int animationMode;
    public int aspect_ratio_x;
    public int aspect_ratio_y;
    public boolean camera;
    public String cameraAudioFormat;
    public String cameraAudioFormatForQ;
    public String cameraFileName;
    public String cameraImageFormat;
    public String cameraImageFormatForQ;
    public int cameraMimeType;
    public String cameraPath;
    public String cameraVideoFormat;
    public String cameraVideoFormatForQ;
    public int captureLoadingColor;
    public boolean checkNumMode;
    public int chooseMode;
    public int circleDimmedBorderColor;
    public int circleDimmedColor;
    public boolean circleDimmedLayer;
    public int circleStrokeWidth;
    public int compressQuality;
    public String compressSavePath;
    public String cropCompressFormat;
    public int cropCompressQuality;
    public int cropHeight;
    @Deprecated
    public int cropStatusBarColorPrimaryDark;
    @Deprecated
    public int cropTitleBarBackgroundColor;
    @Deprecated
    public int cropTitleColor;
    public int cropWidth;
    @Deprecated
    public int downResId;
    public boolean enPreviewVideo;
    public boolean enableCrop;
    public boolean enablePreview;
    public boolean enablePreviewAudio;
    @Deprecated
    public float filterFileSize;
    public long filterMaxFileSize;
    public long filterMinFileSize;
    @Deprecated
    public boolean focusAlpha;
    public boolean freeStyleCropEnabled;
    public int freeStyleCropMode;
    public boolean hideBottomControls;
    public int imageSpanCount;
    public boolean isAndroidQTransform;
    public boolean isAutoRotating;
    public boolean isAutoScalePreviewImage;
    public boolean isAutomaticTitleRecyclerTop;
    public boolean isBmp;
    public boolean isCallbackMode;
    public boolean isCamera;
    public boolean isCameraAroundState;
    public boolean isCameraRotateImage;
    @Deprecated
    public boolean isChangeStatusBarFontColor;
    public boolean isCheckOriginalImage;
    public boolean isCompress;
    public boolean isDisplayOriginalSize;
    public boolean isDragCenter;
    public boolean isDragFrame;
    public boolean isEditorImage;
    public boolean isFallbackVersion;
    public boolean isFallbackVersion2;
    public boolean isFallbackVersion3;
    public boolean isFilterInvalidFile;
    public boolean isGif;
    public boolean isMaxSelectEnabledMask;
    public boolean isMultipleRecyclerAnimation;
    public boolean isMultipleSkipCrop;
    public boolean isNotPreviewDownload;
    public boolean isOnlySandboxDir;
    @Deprecated
    public boolean isOpenStyleCheckNumMode;
    @Deprecated
    public boolean isOpenStyleNumComplete;
    public boolean isOriginalControl;
    public boolean isPageStrategy;
    public boolean isQuickCapture;
    public boolean isSingleDirectReturn;
    public boolean isSyncCover;
    public boolean isUseCustomCamera;
    public boolean isWeChatStyle;
    public boolean isWebp;
    public boolean isWithVideoImage;
    public int language;
    public int maxSelectNum;
    public int maxVideoSelectNum;
    public int minSelectNum;
    public int minVideoSelectNum;
    public int minimumCompressSize;
    public int ofAllCameraType;
    public boolean openClickSound;
    public String originalPath;
    public String outPutCameraPath;
    public int pageSize;
    @Deprecated
    public int pictureStatusBarColor;
    public boolean previewEggs;
    public HashSet<String> queryMimeTypeHashSet;
    public int recordVideoMinSecond;
    public int recordVideoSecond;
    public String renameCompressFileName;
    public String renameCropFileName;
    public int requestedOrientation;
    public boolean returnEmpty;
    public boolean rotateEnabled;
    public String sandboxFolderPath;
    public boolean scaleEnabled;
    public List<LocalMedia> selectionMedias;
    public int selectionMode;
    public boolean showCropFrame;
    public boolean showCropGrid;
    @Deprecated
    public String specifiedFormat;
    @Deprecated
    public String suffixType;
    public boolean synOrAsy;
    @Deprecated
    public int titleBarBackgroundColor;
    @Deprecated
    public int upResId;
    public int videoMaxSecond;
    public int videoMinSecond;
    public int videoQuality;
    public boolean zoomAnim;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        throw new RuntimeException();
    }

    protected PictureSelectionConfig(Parcel parcel) {
        this.chooseMode = PictureMimeType.ofImage();
        this.camera = false;
        this.requestedOrientation = -1;
        this.selectionMode = 2;
        this.maxSelectNum = 9;
        this.minSelectNum = 0;
        this.maxVideoSelectNum = 1;
        this.minVideoSelectNum = 0;
        this.videoQuality = 1;
        this.cropCompressQuality = 90;
        this.recordVideoSecond = 60;
        this.minimumCompressSize = 100;
        this.imageSpanCount = 4;
        this.compressQuality = 80;
        this.filterMinFileSize = PlaybackState.ACTION_PLAY_FROM_MEDIA_ID;
        this.language = -2;
        this.isCamera = true;
        this.cameraMimeType = -1;
        this.pageSize = 60;
        this.isPageStrategy = true;
        this.animationMode = -1;
        this.isAutomaticTitleRecyclerTop = true;
        this.isQuickCapture = true;
        this.isCameraRotateImage = true;
        this.isAutoRotating = true;
        this.isSyncCover = false;
        this.isAutoScalePreviewImage = true;
        this.ofAllCameraType = PictureMimeType.ofAll();
        this.chooseMode = parcel.readInt();
        this.camera = parcel.readByte() != 0;
        this.isSingleDirectReturn = parcel.readByte() != 0;
        this.compressSavePath = parcel.readString();
        this.suffixType = parcel.readString();
        this.cameraImageFormat = parcel.readString();
        this.cameraVideoFormat = parcel.readString();
        this.cameraAudioFormat = parcel.readString();
        this.cameraImageFormatForQ = parcel.readString();
        this.cameraVideoFormatForQ = parcel.readString();
        this.cameraAudioFormatForQ = parcel.readString();
        this.focusAlpha = parcel.readByte() != 0;
        this.renameCompressFileName = parcel.readString();
        this.renameCropFileName = parcel.readString();
        this.specifiedFormat = parcel.readString();
        this.requestedOrientation = parcel.readInt();
        this.captureLoadingColor = parcel.readInt();
        this.isCameraAroundState = parcel.readByte() != 0;
        this.isAndroidQTransform = parcel.readByte() != 0;
        this.selectionMode = parcel.readInt();
        this.maxSelectNum = parcel.readInt();
        this.minSelectNum = parcel.readInt();
        this.maxVideoSelectNum = parcel.readInt();
        this.minVideoSelectNum = parcel.readInt();
        this.videoQuality = parcel.readInt();
        this.cropCompressQuality = parcel.readInt();
        this.videoMaxSecond = parcel.readInt();
        this.videoMinSecond = parcel.readInt();
        this.recordVideoSecond = parcel.readInt();
        this.recordVideoMinSecond = parcel.readInt();
        this.minimumCompressSize = parcel.readInt();
        this.imageSpanCount = parcel.readInt();
        this.aspect_ratio_x = parcel.readInt();
        this.aspect_ratio_y = parcel.readInt();
        this.cropWidth = parcel.readInt();
        this.cropHeight = parcel.readInt();
        this.compressQuality = parcel.readInt();
        this.filterFileSize = parcel.readFloat();
        this.filterMaxFileSize = parcel.readLong();
        this.filterMinFileSize = parcel.readLong();
        this.language = parcel.readInt();
        this.isMultipleRecyclerAnimation = parcel.readByte() != 0;
        this.isMultipleSkipCrop = parcel.readByte() != 0;
        this.isWeChatStyle = parcel.readByte() != 0;
        this.isUseCustomCamera = parcel.readByte() != 0;
        this.zoomAnim = parcel.readByte() != 0;
        this.isCompress = parcel.readByte() != 0;
        this.isOriginalControl = parcel.readByte() != 0;
        this.isDisplayOriginalSize = parcel.readByte() != 0;
        this.isEditorImage = parcel.readByte() != 0;
        this.isCamera = parcel.readByte() != 0;
        this.isGif = parcel.readByte() != 0;
        this.isWebp = parcel.readByte() != 0;
        this.isBmp = parcel.readByte() != 0;
        this.enablePreview = parcel.readByte() != 0;
        this.enPreviewVideo = parcel.readByte() != 0;
        this.enablePreviewAudio = parcel.readByte() != 0;
        this.checkNumMode = parcel.readByte() != 0;
        this.openClickSound = parcel.readByte() != 0;
        this.enableCrop = parcel.readByte() != 0;
        this.freeStyleCropEnabled = parcel.readByte() != 0;
        this.isDragCenter = parcel.readByte() != 0;
        this.circleDimmedLayer = parcel.readByte() != 0;
        this.circleDimmedColor = parcel.readInt();
        this.circleDimmedBorderColor = parcel.readInt();
        this.circleStrokeWidth = parcel.readInt();
        this.freeStyleCropMode = parcel.readInt();
        this.showCropFrame = parcel.readByte() != 0;
        this.showCropGrid = parcel.readByte() != 0;
        this.hideBottomControls = parcel.readByte() != 0;
        this.rotateEnabled = parcel.readByte() != 0;
        this.scaleEnabled = parcel.readByte() != 0;
        this.previewEggs = parcel.readByte() != 0;
        this.synOrAsy = parcel.readByte() != 0;
        this.returnEmpty = parcel.readByte() != 0;
        this.isDragFrame = parcel.readByte() != 0;
        this.isNotPreviewDownload = parcel.readByte() != 0;
        this.isWithVideoImage = parcel.readByte() != 0;
        this.selectionMedias = parcel.createTypedArrayList(LocalMedia.CREATOR);
        this.cameraFileName = parcel.readString();
        this.isCheckOriginalImage = parcel.readByte() != 0;
        this.isChangeStatusBarFontColor = parcel.readByte() != 0;
        this.isOpenStyleNumComplete = parcel.readByte() != 0;
        this.isOpenStyleCheckNumMode = parcel.readByte() != 0;
        this.titleBarBackgroundColor = parcel.readInt();
        this.pictureStatusBarColor = parcel.readInt();
        this.cropTitleBarBackgroundColor = parcel.readInt();
        this.cropStatusBarColorPrimaryDark = parcel.readInt();
        this.cropTitleColor = parcel.readInt();
        this.upResId = parcel.readInt();
        this.downResId = parcel.readInt();
        this.outPutCameraPath = parcel.readString();
        this.sandboxFolderPath = parcel.readString();
        this.originalPath = parcel.readString();
        this.cameraPath = parcel.readString();
        this.cameraMimeType = parcel.readInt();
        this.pageSize = parcel.readInt();
        this.isPageStrategy = parcel.readByte() != 0;
        this.isFilterInvalidFile = parcel.readByte() != 0;
        this.isMaxSelectEnabledMask = parcel.readByte() != 0;
        this.animationMode = parcel.readInt();
        this.isAutomaticTitleRecyclerTop = parcel.readByte() != 0;
        this.isCallbackMode = parcel.readByte() != 0;
        this.isQuickCapture = parcel.readByte() != 0;
        this.isCameraRotateImage = parcel.readByte() != 0;
        this.isAutoRotating = parcel.readByte() != 0;
        this.isSyncCover = parcel.readByte() != 0;
        this.cropCompressFormat = parcel.readString();
        this.isAutoScalePreviewImage = parcel.readByte() != 0;
        this.ofAllCameraType = parcel.readInt();
        this.isOnlySandboxDir = parcel.readByte() != 0;
        this.isFallbackVersion = parcel.readByte() != 0;
        this.isFallbackVersion2 = parcel.readByte() != 0;
        this.isFallbackVersion3 = parcel.readByte() != 0;
    }

    protected void initDefaultValue() {
        this.chooseMode = PictureMimeType.ofImage();
        this.camera = false;
        this.selectionMode = 2;
        this.maxSelectNum = 9;
        this.minSelectNum = 0;
        this.maxVideoSelectNum = 1;
        this.minVideoSelectNum = 0;
        this.videoQuality = 1;
        this.language = -2;
        this.cropCompressQuality = 90;
        this.videoMaxSecond = 0;
        this.videoMinSecond = 0;
        this.filterFileSize = 0.0f;
        this.filterMaxFileSize = 0L;
        this.filterMinFileSize = PlaybackState.ACTION_PLAY_FROM_MEDIA_ID;;
        this.recordVideoSecond = 60;
        this.recordVideoMinSecond = 0;
        this.compressQuality = 80;
        this.imageSpanCount = 4;
        this.isCompress = false;
        this.isOriginalControl = false;
        this.aspect_ratio_x = 0;
        this.aspect_ratio_y = 0;
        this.cropWidth = 0;
        this.cropHeight = 0;
        this.isCameraAroundState = false;
        this.isWithVideoImage = false;
        this.isAndroidQTransform = false;
        this.isCamera = true;
        this.isGif = false;
        this.isWebp = true;
        this.isBmp = true;
        this.focusAlpha = false;
        this.isCheckOriginalImage = false;
        this.isSingleDirectReturn = false;
        this.enablePreview = true;
        this.enPreviewVideo = true;
        this.enablePreviewAudio = true;
        this.checkNumMode = false;
        this.isNotPreviewDownload = false;
        this.openClickSound = false;
        this.isFallbackVersion = false;
        this.isFallbackVersion2 = true;
        this.isFallbackVersion3 = true;
        this.enableCrop = false;
        this.isWeChatStyle = false;
        this.isUseCustomCamera = false;
        this.isMultipleSkipCrop = true;
        this.isMultipleRecyclerAnimation = true;
        this.freeStyleCropEnabled = false;
        this.isDragCenter = false;
        this.circleDimmedLayer = false;
        this.showCropFrame = true;
        this.showCropGrid = true;
        this.hideBottomControls = true;
        this.rotateEnabled = true;
        this.scaleEnabled = true;
        this.previewEggs = false;
        this.returnEmpty = false;
        this.synOrAsy = true;
        this.zoomAnim = true;
        this.circleDimmedColor = 0;
        this.circleDimmedBorderColor = 0;
        this.circleStrokeWidth = 1;
        this.isDragFrame = true;
        this.compressSavePath = "";
        this.suffixType = "";
        this.cameraImageFormat = "";
        this.cameraVideoFormat = "";
        this.cameraAudioFormat = "";
        this.cameraImageFormatForQ = "";
        this.cameraVideoFormatForQ = "";
        this.cameraAudioFormatForQ = "";
        this.cameraFileName = "";
        this.specifiedFormat = "";
        this.renameCompressFileName = "";
        this.renameCropFileName = "";
        this.queryMimeTypeHashSet = null;
        this.selectionMedias = new ArrayList();
        this.titleBarBackgroundColor = 0;
        this.pictureStatusBarColor = 0;
        this.cropTitleBarBackgroundColor = 0;
        this.cropStatusBarColorPrimaryDark = 0;
        this.cropTitleColor = 0;
        this.upResId = 0;
        this.downResId = 0;
        this.isChangeStatusBarFontColor = false;
        this.isOpenStyleNumComplete = false;
        this.isOpenStyleCheckNumMode = false;
        this.outPutCameraPath = "";
        this.sandboxFolderPath = "";
        this.originalPath = "";
        this.cameraPath = "";
        this.cameraMimeType = -1;
        this.pageSize = 60;
        this.isPageStrategy = true;
        this.isFilterInvalidFile = false;
        this.isMaxSelectEnabledMask = false;
        this.animationMode = -1;
        this.isAutomaticTitleRecyclerTop = true;
        this.isCallbackMode = false;
        this.isQuickCapture = true;
        this.isCameraRotateImage = true;
        this.isAutoRotating = true;
        this.isSyncCover = !SdkVersionUtils.isQ();
        this.cropCompressFormat = "";
        this.isAutoScalePreviewImage = true;
        this.freeStyleCropMode = -1;
        this.isEditorImage = false;
        this.isDisplayOriginalSize = true;
        this.ofAllCameraType = PictureMimeType.ofAll();
        this.isOnlySandboxDir = false;
    }

    public static PictureSelectionConfig getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static PictureSelectionConfig getCleanInstance() {
        PictureSelectionConfig pictureSelectionConfig = getInstance();
        pictureSelectionConfig.initDefaultValue();
        return pictureSelectionConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/album/config/PictureSelectionConfig$InstanceHolder.class */
    public static final class InstanceHolder {
        private static final PictureSelectionConfig INSTANCE = new PictureSelectionConfig();

        private InstanceHolder() {
        }
    }

    public PictureSelectionConfig() {
        this.chooseMode = PictureMimeType.ofImage();
        this.camera = false;
        this.requestedOrientation = -1;
        this.selectionMode = 2;
        this.maxSelectNum = 9;
        this.minSelectNum = 0;
        this.maxVideoSelectNum = 1;
        this.minVideoSelectNum = 0;
        this.videoQuality = 1;
        this.cropCompressQuality = 90;
        this.recordVideoSecond = 60;
        this.minimumCompressSize = 100;
        this.imageSpanCount = 4;
        this.compressQuality = 80;
        this.filterMinFileSize = PlaybackState.ACTION_PLAY_FROM_MEDIA_ID;;
        this.language = -2;
        this.isCamera = true;
        this.cameraMimeType = -1;
        this.pageSize = 60;
        this.isPageStrategy = true;
        this.animationMode = -1;
        this.isAutomaticTitleRecyclerTop = true;
        this.isQuickCapture = true;
        this.isCameraRotateImage = true;
        this.isAutoRotating = true;
        this.isSyncCover = false;
        this.isAutoScalePreviewImage = true;
        this.ofAllCameraType = PictureMimeType.ofAll();
    }

    public static void destroy() {
        listener = null;
    }
}
