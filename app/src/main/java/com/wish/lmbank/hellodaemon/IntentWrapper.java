package com.wish.lmbank.hellodaemon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;

import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/hellodaemon/IntentWrapper.class */
public class IntentWrapper {
    protected static final int COOLPAD = 113;
    protected static final int DOZE = 98;
    protected static final int GIONEE = 110;
    protected static final int HUAWEI = 99;
    protected static final int HUAWEI_GOD = 100;
    protected static final int LENOVO = 114;
    protected static final int LENOVO_GOD = 115;
    protected static final int LETV = 111;
    protected static final int LETV_GOD = 112;
    protected static final int MEIZU = 104;
    protected static final int MEIZU_GOD = 105;
    protected static final int OPPO = 106;
    protected static final int OPPO_OLD = 108;
    protected static final int SAMSUNG_L = 103;
    protected static final int SAMSUNG_M = 107;
    protected static final int VIVO_GOD = 109;
    protected static final int XIAOMI = 101;
    protected static final int XIAOMI_GOD = 102;
    protected static final int ZTE = 116;
    protected static final int ZTE_GOD = 117;
    protected static String sApplicationName;
    protected static List<IntentWrapper> sIntentWrapperList;
    protected Intent intent;
    protected int type;

    public static List<IntentWrapper> getIntentWrapperList() {
        if (sIntentWrapperList == null) {
            if (!DaemonEnv.sInitialized) {
                return new ArrayList();
            }
            sIntentWrapperList = new ArrayList();
//             if (Build.VERSION.SDK_INT >= 24 && !((PowerManager) DaemonEnv.sApp.getSystemService(bb7d7pu7.m5998("GQYeDBs"))).isIgnoringBatteryOptimizations(DaemonEnv.sApp.getPackageName())) {
            if (Build.VERSION.SDK_INT >= 24 && !((PowerManager) DaemonEnv.sApp.getSystemService("power")).isIgnoringBatteryOptimizations(DaemonEnv.sApp.getPackageName())) {
//                 Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcaDB0dAAcOGkc7LDg8LDo9NiAuJyY7LDYrKD09LDswNiY5PSAkIDMoPSAmJzo"));
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
//                 intent.setData(Uri.parse(bb7d7pu7.m5998("GQgKAggODFM") + DaemonEnv.sApp.getPackageName()));
                intent.setData(Uri.parse("package:" + DaemonEnv.sApp.getPackageName()));
                sIntentWrapperList.add(new IntentWrapper(intent, 98));
            }
            Intent intent2 = new Intent();
//             intent2.setAction(bb7d7pu7.m5998("ARwIHgwARwAHHQwHHUcICh0ABgdHITokNismJj0oOTk2JCgnKC4sOw"));
            intent2.setAction("huawei.intent.action.HSM_BOOTAPP_MANAGER");
            sIntentWrapperList.add(new IntentWrapper(intent2, 99));
            Intent intent3 = new Intent();
//             intent3.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwEcCB4MAEcaEBodDAQECAcIDgwb"), bb7d7pu7.m5998("CgYERwEcCB4MAEcaEBodDAQECAcIDgwbRwYZHQAEABMMRxkbBgoMGhpHORsGHQwKHSgKHQAfAB0Q")));
            intent3.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent3, 100));
            Intent intent4 = new Intent();
//             intent4.setAction(bb7d7pu7.m5998("BAAcAEcABx0MBx1HCAodAAYHRyY5Nig8PSY2Oj0oOz0"));
            intent4.setAction("miui.intent.action.OP_AUTO_START");
//             String m5998 = bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHLSwvKDwlPQ");
            String m5998 = "android.intent.category.DEFAULT";
            intent4.addCategory(m5998);
            sIntentWrapperList.add(new IntentWrapper(intent4, 101));
            Intent intent5 = new Intent();
//             intent5.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwQAHABHGQYeDBsCDAwZDBs"), bb7d7pu7.m5998("CgYERwQAHABHGQYeDBsCDAwZDBtHHABHIQANDQwHKBkZGioGBw8ADigKHQAfAB0Q")));
            intent5.setComponent(new ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
//             intent5.putExtra(bb7d7pu7.m5998("GQgKAggODDYHCAQM"), DaemonEnv.sApp.getPackageName());
            intent5.putExtra("package_name", DaemonEnv.sApp.getPackageName());
//             intent5.putExtra(bb7d7pu7.m5998("GQgKAggODDYFCAsMBQ"), getApplicationName());
            intent5.putExtra("package_label", getApplicationName());
            sIntentWrapperList.add(new IntentWrapper(intent5, 102));
//             Intent launchIntentForPackage = DaemonEnv.sApp.getPackageManager().getLaunchIntentForPackage(bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcaBA"));
            Intent launchIntentForPackage = DaemonEnv.sApp.getPackageManager().getLaunchIntentForPackage("com.samsung.android.sm");
            if (launchIntentForPackage != null) {
                sIntentWrapperList.add(new IntentWrapper(launchIntentForPackage, 103));
            }
            Intent intent6 = new Intent();
//             intent6.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcaBDYKBw"), bb7d7pu7.m5998("CgYERxoIBBocBw5HCAcNGwYADUcaBEccAEcLCB0dDBsQRysIHR0MGxAoCh0AHwAdEA")));
            intent6.setComponent(new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.battery.BatteryActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent6, 107));
//             Intent intent7 = new Intent(bb7d7pu7.m5998("CgYERwQMABMcRxoIDwxHGgwKHBsAHRBHOiEmPjYoOTk6LCo"));
            Intent intent7 = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent7.addCategory(m5998);
//             intent7.putExtra(bb7d7pu7.m5998("GQgKAggODCcIBAw"), DaemonEnv.sApp.getPackageName());
            intent7.putExtra("packageName", DaemonEnv.sApp.getPackageName());
            sIntentWrapperList.add(new IntentWrapper(intent7, 104));
            Intent intent8 = new Intent();
//             intent8.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwQMABMcRxoIDww"), bb7d7pu7.m5998("CgYERwQMABMcRxoIDwxHGQYeDBscAEc5Bh4MGygZGTkMGwQAGhoABgcoCh0AHwAdEA")));
            intent8.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.powerui.PowerAppPermissionActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent8, 105));
            Intent intent9 = new Intent();
//             intent9.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwoGBQYbBhpHGggPDAoMBx0MGw"), bb7d7pu7.m5998("CgYERwoGBQYbBhpHGggPDAoMBx0MG0cZDBsEABoaAAYHRxodCBsdHBlHOh0IGx0cGSgZGSUAGh0oCh0AHwAdEA")));
            intent9.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent9, 106));
            Intent intent10 = new Intent();
//             intent10.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwoGBQYbRxoIDwwKDAcdDBs"), bb7d7pu7.m5998("CgYERwoGBQYbRxoIDwwKDAcdDBtHGQwbBAAaGgAGB0caHQgbHRwZRzodCBsdHBkoGRklABodKAodAB8AHRA")));
            intent10.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent10, 108));
            Intent intent11 = new Intent();
//             intent11.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERx8AHwZHCAsM"), bb7d7pu7.m5998("CgYERx8AHwZHCBkZBQAKCB0ABgcLDAEIHwAGGwwHDgAHDEccAEcsEQoMGhoAHww5Bh4MGyQIBwgODBsoCh0AHwAdEA")));
            intent11.setComponent(new ComponentName("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent11, 109));
            Intent intent12 = new Intent();
//             intent12.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERw4ABgcMDEcaBg8dBAgHCA4MGw"), bb7d7pu7.m5998("CgYERw4ABgcMDEcaBg8dBAgHCA4MG0ckCAAHKAodAB8AHRA")));
            intent12.setComponent(new ComponentName("com.gionee.softmanager", "com.gionee.softmanager.MainActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent12, 110));
            Intent intent13 = new Intent();
//             String m59982 = bb7d7pu7.m5998("CgYERwUMHR9HCAcNGwYADUcFDB0fGggPDA");
            String m59982 = "com.letv.android.letvsafe";
//             intent13.setComponent(new ComponentName(m59982, bb7d7pu7.m5998("CgYERwUMHR9HCAcNGwYADUcFDB0fGggPDEcoHB0GCwYGHSQIBwgODCgKHQAfAB0Q")));
            intent13.setComponent(new ComponentName(m59982, "com.letv.android.letvsafe.AutobootManageActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent13, 111));
            Intent intent14 = new Intent();
//             intent14.setComponent(new ComponentName(m59982, bb7d7pu7.m5998("CgYERwUMHR9HCAcNGwYADUcFDB0fGggPDEcrCAoCDhsGHAcNKBkZJAgHCA4MKAodAB8AHRA")));
            intent14.setComponent(new ComponentName(m59982, "com.letv.android.letvsafe.BackgroundAppManageActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent14, 112));
            Intent intent15 = new Intent();
//             intent15.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERxAcBQYHDkcIBw0bBgANRxoMChwbAB0Q"), bb7d7pu7.m5998("CgYERxAcBQYHDkcIBw0bBgANRxoMCgoMBx0MG0cdCAsLCBsECAAH")));
            intent15.setComponent(new ComponentName("com.yulong.android.security", "com.yulong.android.seccenter.tabbarmain"));
            sIntentWrapperList.add(new IntentWrapper(intent15, 113));
            Intent intent16 = new Intent();
//             intent16.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwUMBwYfBkcaDAocGwAdEA"), bb7d7pu7.m5998("CgYERwUMBwYfBkcaDAocGwAdEEcZHBsMCwgKAg4bBhwHDUc5HBsMKwgKAg4bBhwHDSgKHQAfAB0Q")));
            intent16.setComponent(new ComponentName("com.lenovo.security", "com.lenovo.security.purebackground.PureBackgroundActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent16, 114));
            Intent intent17 = new Intent();
//             intent17.setComponent(new ComponentName(bb7d7pu7.m5998("CgYERwUMBwYfBkcZBh4MGxoMHR0ABw4"), bb7d7pu7.m5998("CgYERwUMBwYfBkcZBh4MGxoMHR0ABw5HHABHOgwdHQAHDhpNIQAOATkGHgwbKBkZBQAKCB0ABgcaKAodAB8AHRA")));
            intent17.setComponent(new ComponentName("com.lenovo.powersetting", "com.lenovo.powersetting.ui.Settings$HighPowerApplicationsActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent17, 115));
            Intent intent18 = new Intent();
//             String m59983 = bb7d7pu7.m5998("CgYERxMdDEcBDAgbHRAaDBsfAAoM");
            String m59983 = "com.zte.heartyservice";
//             intent18.setComponent(new ComponentName(m59983, bb7d7pu7.m5998("CgYERxMdDEcBDAgbHRAaDBsfAAoMRwgcHQYbHAdHKBkZKBwdBjscByQIBwgODBs")));
            intent18.setComponent(new ComponentName(m59983, "com.zte.heartyservice.autorun.AppAutoRunManager"));
            sIntentWrapperList.add(new IntentWrapper(intent18, 116));
            Intent intent19 = new Intent();
//             intent19.setComponent(new ComponentName(m59983, bb7d7pu7.m5998("CgYERxMdDEcBDAgbHRAaDBsfAAoMRxoMHR0ABw5HKgUMCBsoGRk6DB0dAAcOGigKHQAfAB0Q")));
            intent19.setComponent(new ComponentName(m59983, "com.zte.heartyservice.setting.ClearAppSettingsActivity"));
            sIntentWrapperList.add(new IntentWrapper(intent19, 117));
        }
        return sIntentWrapperList;
    }

    public static String getApplicationName() {
        if (((-12198) + 315) % 315 <= 0) {
            if (sApplicationName == null) {
                if (!DaemonEnv.sInitialized) {
                    return "";
                }
                try {
                    PackageManager packageManager = DaemonEnv.sApp.getPackageManager();
                    sApplicationName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(DaemonEnv.sApp.getPackageName(), 0)).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    sApplicationName = DaemonEnv.sApp.getPackageName();
                }
            }
            return sApplicationName;
        }
        int i = 1352 + 1352 + 1949;
        while (true) {
        }
    }

    public static void onBackPressed(Activity activity) {
//         Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRyQoICc"));
        Intent intent = new Intent("android.intent.action.MAIN");
//         intent.addCategory(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCggdDA4GGxBHISYkLA"));
        intent.addCategory("android.intent.category.HOME");
        activity.startActivity(intent);
    }

    protected IntentWrapper(Intent intent, int i) {
        this.intent = intent;
        this.type = i;
    }

    protected boolean doesActivityExists() {
        if (DaemonEnv.sInitialized) {
            List<ResolveInfo> queryIntentActivities = DaemonEnv.sApp.getPackageManager().queryIntentActivities(this.intent, 65536);
            boolean z = false;
            if (queryIntentActivities != null) {
                z = false;
                if (queryIntentActivities.size() > 0) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    protected void startActivity(Activity activity) {
        try {
            activity.startActivity(this.intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
