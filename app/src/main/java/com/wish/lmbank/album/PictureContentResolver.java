package com.wish.lmbank.album;

import android.content.Context;
import android.net.Uri;

import com.wish.lmbank.album.io.ArrayPoolProvide;

import java.io.InputStream;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/album/PictureContentResolver.class */
public final class PictureContentResolver {
    public static InputStream getContentResolverOpenInputStream(Context context, Uri uri) {
        return ArrayPoolProvide.getInstance().openInputStream(context.getContentResolver(), uri);
    }
}
