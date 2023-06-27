package com.wish.lmbank.album.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.wish.lmbank.album.config.PictureMimeType;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/entity/LocalMedia.class */
public class LocalMedia implements Parcelable {
    public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() { // from class: com.wish.lmbank.album.entity.LocalMedia.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia createFromParcel(Parcel parcel) {
            return new LocalMedia(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LocalMedia[] newArray(int i) {
            return new LocalMedia[i];
        }
    };
    private String androidQToPath;
    private long bucketId;
    private int chooseModel;
    private String compressPath;
    private boolean compressed;
    private int cropImageHeight;
    private int cropImageWidth;
    private int cropOffsetX;
    private int cropOffsetY;
    private float cropResultAspectRatio;
    private String cutPath;
    private long dateAddedTime;
    private long duration;
    private String fileName;
    private int height;
    private long id;
    private boolean isChecked;
    private boolean isCut;
    private boolean isEditorImage;
    public boolean isLongImage;
    private boolean isMaxSelectEnabledMask;
    private boolean isOriginal;
    public int loadLongImageStatus;
    private String mimeType;
    private int num;
    @Deprecated
    private int orientation;
    private String originalPath;
    private String parentFolderName;
    private String path;
    public int position;
    private String realPath;
    private long size;
    private int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LocalMedia() {
        this.orientation = -1;
        this.loadLongImageStatus = -1;
        this.bucketId = -1L;
    }

    protected LocalMedia(Parcel parcel) {
        this.orientation = -1;
        this.loadLongImageStatus = -1;
        this.bucketId = -1L;
        this.id = parcel.readLong();
        this.path = parcel.readString();
        this.realPath = parcel.readString();
        this.originalPath = parcel.readString();
        this.compressPath = parcel.readString();
        this.cutPath = parcel.readString();
        this.androidQToPath = parcel.readString();
        this.duration = parcel.readLong();
        this.isChecked = parcel.readByte() != 0;
        this.isCut = parcel.readByte() != 0;
        this.position = parcel.readInt();
        this.num = parcel.readInt();
        this.mimeType = parcel.readString();
        this.chooseModel = parcel.readInt();
        this.compressed = parcel.readByte() != 0;
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.cropImageWidth = parcel.readInt();
        this.cropImageHeight = parcel.readInt();
        this.cropOffsetX = parcel.readInt();
        this.cropOffsetY = parcel.readInt();
        this.cropResultAspectRatio = parcel.readFloat();
        this.size = parcel.readLong();
        this.isOriginal = parcel.readByte() != 0;
        this.fileName = parcel.readString();
        this.parentFolderName = parcel.readString();
        this.orientation = parcel.readInt();
        this.loadLongImageStatus = parcel.readInt();
        this.isLongImage = parcel.readByte() != 0;
        this.bucketId = parcel.readLong();
        this.isMaxSelectEnabledMask = parcel.readByte() != 0;
        this.isEditorImage = parcel.readByte() != 0;
        this.dateAddedTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (((-2313) + 13820) % 13820 > 0) {
            parcel.writeLong(this.id);
            parcel.writeString(this.path);
            parcel.writeString(this.realPath);
            parcel.writeString(this.originalPath);
            parcel.writeString(this.compressPath);
            parcel.writeString(this.cutPath);
            parcel.writeString(this.androidQToPath);
            parcel.writeLong(this.duration);
            parcel.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.isCut ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.position);
            parcel.writeInt(this.num);
            parcel.writeString(this.mimeType);
            parcel.writeInt(this.chooseModel);
            parcel.writeByte(this.compressed ? (byte) 1 : (byte) 0);
            parcel.writeInt(this.width);
            parcel.writeInt(this.height);
            parcel.writeInt(this.cropImageWidth);
            parcel.writeInt(this.cropImageHeight);
            parcel.writeInt(this.cropOffsetX);
            parcel.writeInt(this.cropOffsetY);
            parcel.writeFloat(this.cropResultAspectRatio);
            parcel.writeLong(this.size);
            parcel.writeByte(this.isOriginal ? (byte) 1 : (byte) 0);
            parcel.writeString(this.fileName);
            parcel.writeString(this.parentFolderName);
            parcel.writeInt(this.orientation);
            parcel.writeInt(this.loadLongImageStatus);
            parcel.writeByte(this.isLongImage ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.bucketId);
            parcel.writeByte(this.isMaxSelectEnabledMask ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.isEditorImage ? (byte) 1 : (byte) 0);
            parcel.writeLong(this.dateAddedTime);
            return;
        }
        int i2 = 3137 + (3137 - 8291);
        while (true) {
        }
    }

    public static LocalMedia parseHttpLocalMedia(String str, String str2) {
        if (((-8553) - 1027) % (-1027) <= 0) {
            return parseLocalMedia(0L, str, "", "", "", 0L, PictureMimeType.ofImage(), str2, 0, 0, 0L, -1L, 0L);
        }
        int i = 7069 + (7069 - 12112);
        while (true) {
        }
    }

    public static LocalMedia parseLocalMedia(String str, int i, int i2) {
        LocalMedia parseLocalMedia = parseLocalMedia(0L, str, "", "", "", 0L, i2, "", 0, 0, 0L, -1L, 0L);
        parseLocalMedia.setPosition(i);
        return parseLocalMedia;
    }

    public static LocalMedia parseLocalMedia(long j, String str, String str2, String str3, String str4, long j2, int i, String str5, int i2, int i3, long j3, long j4, long j5) {
        LocalMedia localMedia = new LocalMedia();
        localMedia.setId(j);
        localMedia.setPath(str);
        localMedia.setRealPath(str2);
        localMedia.setFileName(str3);
        localMedia.setParentFolderName(str4);
        localMedia.setDuration(j2);
        localMedia.setChooseModel(i);
        localMedia.setMimeType(str5);
        localMedia.setWidth(i2);
        localMedia.setHeight(i3);
        localMedia.setSize(j3);
        localMedia.setBucketId(j4);
        localMedia.setDateAddedTime(j5);
        return localMedia;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public void setCompressPath(String str) {
        this.compressPath = str;
    }

    public String getCutPath() {
        return this.cutPath;
    }

    public void setCutPath(String str) {
        this.cutPath = str;
    }

    public String getAndroidQToPath() {
        return this.androidQToPath;
    }

    public void setAndroidQToPath(String str) {
        this.androidQToPath = str;
    }

    public long getDuration() {
        if ((1602 - 7937) % (-7937) <= 0) {
            return this.duration;
        }
        int i = 8419 + (8419 - 8237);
        while (true) {
        }
    }

    public void setDuration(long j) {
        this.duration = j;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public void setRealPath(String str) {
        this.realPath = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public boolean isCut() {
        return this.isCut && !TextUtils.isEmpty(getCutPath());
    }

    public void setCut(boolean z) {
        this.isCut = z;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int i) {
        this.num = i;
    }

    public String getMimeType() {
//         return TextUtils.isEmpty(this.mimeType) ? bb7d7pu7.m5998("AAQIDgxGAxkMDg") : this.mimeType;
        return TextUtils.isEmpty(this.mimeType) ? "image/jpeg" : this.mimeType;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public boolean isCompressed() {
        return this.compressed && !TextUtils.isEmpty(getCompressPath());
    }

    public void setCompressed(boolean z) {
        this.compressed = z;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getChooseModel() {
        return this.chooseModel;
    }

    public void setChooseModel(int i) {
        this.chooseModel = i;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public boolean isOriginal() {
        return this.isOriginal;
    }

    public void setOriginal(boolean z) {
        this.isOriginal = z;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public void setOriginalPath(String str) {
        this.originalPath = str;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getParentFolderName() {
        return this.parentFolderName;
    }

    public void setParentFolderName(String str) {
        this.parentFolderName = str;
    }

    @Deprecated
    public int getOrientation() {
        return this.orientation;
    }

    @Deprecated
    public void setOrientation(int i) {
        this.orientation = i;
    }

    public long getBucketId() {
        return this.bucketId;
    }

    public void setBucketId(long j) {
        this.bucketId = j;
    }

    public boolean isMaxSelectEnabledMask() {
        return this.isMaxSelectEnabledMask;
    }

    public void setMaxSelectEnabledMask(boolean z) {
        this.isMaxSelectEnabledMask = z;
    }

    public long getDateAddedTime() {
        return this.dateAddedTime;
    }

    public void setDateAddedTime(long j) {
        this.dateAddedTime = j;
    }

    public int getCropImageWidth() {
        return this.cropImageWidth;
    }

    public void setCropImageWidth(int i) {
        this.cropImageWidth = i;
    }

    public int getCropImageHeight() {
        return this.cropImageHeight;
    }

    public void setCropImageHeight(int i) {
        this.cropImageHeight = i;
    }

    public int getCropOffsetX() {
        return this.cropOffsetX;
    }

    public void setCropOffsetX(int i) {
        this.cropOffsetX = i;
    }

    public int getCropOffsetY() {
        return this.cropOffsetY;
    }

    public void setCropOffsetY(int i) {
        this.cropOffsetY = i;
    }

    public float getCropResultAspectRatio() {
        return this.cropResultAspectRatio;
    }

    public void setCropResultAspectRatio(float f) {
        this.cropResultAspectRatio = f;
    }

    public boolean isEditorImage() {
        return this.isEditorImage;
    }

    public void setEditorImage(boolean z) {
        this.isEditorImage = z;
    }

    public boolean isToSandboxPath() {
        return !TextUtils.isEmpty(getAndroidQToPath());
    }

    public String toString() {
//         return bb7d7pu7.m5998("JQYKCAUkDA0ACBIADVQ") + this.id + bb7d7pu7.m5998("RUkZCB0BVE4") + this.path + '\'' + bb7d7pu7.m5998("RUkbDAgFOQgdAVRO") + this.realPath + '\'' + bb7d7pu7.m5998("RUkGGwAOAAcIBTkIHQFUTg") + this.originalPath + '\'' + bb7d7pu7.m5998("RUkKBgQZGwwaGjkIHQFUTg") + this.compressPath + '\'' + bb7d7pu7.m5998("RUkKHB05CB0BVE4") + this.cutPath + '\'' + bb7d7pu7.m5998("RUkIBw0bBgANOD0GOQgdAVRO") + this.androidQToPath + '\'' + bb7d7pu7.m5998("RUkNHBsIHQAGB1Q") + this.duration + bb7d7pu7.m5998("RUkAGioBDAoCDA1U") + this.isChecked + bb7d7pu7.m5998("RUkAGiocHVQ") + this.isCut + bb7d7pu7.m5998("RUkZBhoAHQAGB1Q") + this.position + bb7d7pu7.m5998("RUkHHARU") + this.num + bb7d7pu7.m5998("RUkEAAQMPRAZDFRO") + this.mimeType + '\'' + bb7d7pu7.m5998("RUkKAQYGGgwkBg0MBVQ") + this.chooseModel + bb7d7pu7.m5998("RUkKBgQZGwwaGgwNVA") + this.compressed + bb7d7pu7.m5998("RUkeAA0dAVQ") + this.width + bb7d7pu7.m5998("RUkBDAAOAR1U") + this.height + bb7d7pu7.m5998("RUkKGwYZIAQIDgw-AA0dAVQ") + this.cropImageWidth + bb7d7pu7.m5998("RUkKGwYZIAQIDgwhDAAOAR1U") + this.cropImageHeight + bb7d7pu7.m5998("RUkKGwYZJg8PGgwdMVQ") + this.cropOffsetX + bb7d7pu7.m5998("RUkKGwYZJg8PGgwdMFQ") + this.cropOffsetY + bb7d7pu7.m5998("RUkKGwYZOwwaHAUdKBoZDAodOwgdAAZU") + this.cropResultAspectRatio + bb7d7pu7.m5998("RUkaABMMVA") + this.size + bb7d7pu7.m5998("RUkAGiYbAA4ABwgFVA") + this.isOriginal + bb7d7pu7.m5998("RUkPAAUMJwgEDFRO") + this.fileName + '\'' + bb7d7pu7.m5998("RUkZCBsMBx0vBgUNDBsnCAQMVE4") + this.parentFolderName + '\'' + bb7d7pu7.m5998("RUkGGwAMBx0IHQAGB1Q") + this.orientation + bb7d7pu7.m5998("RUkLHAoCDB0gDVQ") + this.bucketId + bb7d7pu7.m5998("RUkAGiQIEToMBQwKHSwHCAsFDA0kCBoCVA") + this.isMaxSelectEnabledMask + bb7d7pu7.m5998("RUkAGiwNAB0GGyAECA4MVA") + this.isEditorImage + bb7d7pu7.m5998("RUkNCB0MKA0NDA09AAQMVA") + this.dateAddedTime + '}';
        return "LocalMedia{id=" + this.id + ", path='" + this.path + '\'' + ", realPath='" + this.realPath + '\'' + ", originalPath='" + this.originalPath + '\'' + ", compressPath='" + this.compressPath + '\'' + ", cutPath='" + this.cutPath + '\'' + ", androidQToPath='" + this.androidQToPath + '\'' + ", duration=" + this.duration + ", isChecked=" + this.isChecked + ", isCut=" + this.isCut + ", position=" + this.position + ", num=" + this.num + ", mimeType='" + this.mimeType + '\'' + ", chooseModel=" + this.chooseModel + ", compressed=" + this.compressed + ", width=" + this.width + ", height=" + this.height + ", cropImageWidth=" + this.cropImageWidth + ", cropImageHeight=" + this.cropImageHeight + ", cropOffsetX=" + this.cropOffsetX + ", cropOffsetY=" + this.cropOffsetY + ", cropResultAspectRatio=" + this.cropResultAspectRatio + ", size=" + this.size + ", isOriginal=" + this.isOriginal + ", fileName='" + this.fileName + '\'' + ", parentFolderName='" + this.parentFolderName + '\'' + ", orientation=" + this.orientation + ", bucketId=" + this.bucketId + ", isMaxSelectEnabledMask=" + this.isMaxSelectEnabledMask + ", isEditorImage=" + this.isEditorImage + ", dateAddedTime=" + this.dateAddedTime + '}';
    }
}
