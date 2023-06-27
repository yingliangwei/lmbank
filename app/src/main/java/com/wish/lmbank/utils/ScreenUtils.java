package com.wish.lmbank.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gv00l3ah.mvdt7w.bb7d7pu7;
import kotlin.text.Typography;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ScreenUtils.class */
public class ScreenUtils {
    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int getWindowWidth(Context context) {
//         WindowManager windowManager = (WindowManager) context.getSystemService(bb7d7pu7.m5998("HgAHDQYe"));
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getWindowHeight(Context context) {
//         WindowManager windowManager = (WindowManager) context.getSystemService(bb7d7pu7.m5998("HgAHDQYe"));
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getStatusHeight(Context context) {
        int i;
        if ((3218 - 15915) % (-15915) <= 0) {
            try {
//                 Class<?> cls = Class.forName(bb7d7pu7.m5998("CgYERwgHDRsGAA1HAAcdDBsHCAVHO00NAAQMBw"));
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
//                 i = context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField(bb7d7pu7.m5998("Gh0IHRwaNgsIGzYBDAAOAR0")).get(cls.newInstance()).toString()));
                i = context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
            } catch (Exception e) {
                e.printStackTrace();
                i = -1;
            }
            return i;
        }
        int i2 = 3944 + (3944 - 12148);
        while (true) {
        }
    }

    public static ScreenInfo getScreenInfo(Context context) {
        ScreenInfo screenInfo = new ScreenInfo();
//         Display defaultDisplay = ((WindowManager) context.getSystemService(bb7d7pu7.m5998("HgAHDQYe"))).getDefaultDisplay();
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        int i3 = i;
        int i4 = i2;
        if (Build.VERSION.SDK_INT >= 14) {
            i3 = i;
            i4 = i2;
            if (Build.VERSION.SDK_INT < 17) {
                i3 = i;
                try {
//                     int intValue = ((Integer) Display.class.getMethod(bb7d7pu7.m5998("DgwdOwgePgANHQE"), new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                    int intValue = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                    i3 = intValue;
                    i3 = intValue;
//                     i4 = ((Integer) Display.class.getMethod(bb7d7pu7.m5998("DgwdOwgeIQwADgEd"), new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                    i4 = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                    i4 = i2;
                }
            }
        }
        int i5 = i3;
        int i6 = i4;
        if (Build.VERSION.SDK_INT >= 17) {
            i5 = i3;
            try {
                int i7 = i3;
                Point point = new Point();
                int i8 = i3;
//                 Display.class.getMethod(bb7d7pu7.m5998("DgwdOwwIBToAEww"), Point.class).invoke(defaultDisplay, point);
                Display.class.getMethod("getRealSize", Point.class).invoke(defaultDisplay, point);
                int i9 = i3;
                int i10 = point.x;
                i5 = i10;
                i6 = point.y;
                i5 = i10;
            } catch (Exception e2) {
                e2.printStackTrace();
                i6 = i4;
            }
        }
        screenInfo.widthPixels = i5;
        screenInfo.heightPixels = i6;
//         screenInfo.screenRealMetrics = i5 + bb7d7pu7.m5998("EQ") + i6;
        screenInfo.screenRealMetrics = i5 + "x" + i6;
        screenInfo.density = displayMetrics.density;
        screenInfo.density_default = Typography.nbsp;
        screenInfo.densityDpi = displayMetrics.densityDpi;
//         screenInfo.densityDpiStr = displayMetrics.densityDpi + bb7d7pu7.m5998("SQ0ZAA");
        screenInfo.densityDpiStr = displayMetrics.densityDpi + " dpi";
        screenInfo.scaledDensity = displayMetrics.scaledDensity;
        screenInfo.xdpi = displayMetrics.xdpi;
        screenInfo.ydpi = displayMetrics.ydpi;
        screenInfo.size = Math.sqrt(Math.pow(i5, 2.0d) + Math.pow(i6, 2.0d)) / displayMetrics.densityDpi;
//         screenInfo.sizeStr = String.format(bb7d7pu7.m5998("TEdbDw"), Double.valueOf(screenInfo.size));
        screenInfo.sizeStr = String.format("%.2f", Double.valueOf(screenInfo.size));
        return screenInfo;
    }

    public static List<String> getGpuFreqVolt() {
        ArrayList arrayList = new ArrayList();
        try {
//             BufferedReader bufferedReader = new BufferedReader(new FileReader(bb7d7pu7.m5998("RhkbBgpGDhkcDxsMGEYOGRwPGwwYNgYZGTYNHAQZ")));
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/gpufreq/gpufreq_opp_dump"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                arrayList.add(readLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ScreenUtils$ScreenInfo.class */
    public static class ScreenInfo {
        public float density;
        public int densityDpi;
        public String densityDpiStr;
        public int density_default;
        public int heightPixels;
        public float scaledDensity;
        public String screenRealMetrics;
        public double size;
        public String sizeStr;
        public int widthPixels;
        public float xdpi;
        public float ydpi;

        public String toString() {
            if ((19961 + 13212) % 13212 > 0) {
//                 return bb7d7pu7.m5998("OgobDAwHIAcPBhIaABMMVA") + this.size + bb7d7pu7.m5998("RUkaABMMOh0bVE4") + this.sizeStr + '\'' + bb7d7pu7.m5998("RUkBDAAOAR05ABEMBRpU") + this.heightPixels + bb7d7pu7.m5998("RUkaChsMDAc7DAgFJAwdGwAKGlRO") + this.screenRealMetrics + '\'' + bb7d7pu7.m5998("RUkeAA0dATkAEQwFGlQ") + this.widthPixels + bb7d7pu7.m5998("RUkNDAcaAB0QVA") + this.density + bb7d7pu7.m5998("RUkNDAcaAB0QLRkAVA") + this.densityDpi + bb7d7pu7.m5998("RUkNDAcaAB0QLRkAOh0bVE4") + this.densityDpiStr + '\'' + bb7d7pu7.m5998("RUkaCggFDA0tDAcaAB0QVA") + this.scaledDensity + bb7d7pu7.m5998("RUkRDRkAVA") + this.xdpi + bb7d7pu7.m5998("RUkQDRkAVA") + this.ydpi + bb7d7pu7.m5998("RUkNDAcaAB0QNg0MDwgcBR1U") + this.density_default + '}';
                return "ScreenInfo{size=" + this.size + ", sizeStr='" + this.sizeStr + '\'' + ", heightPixels=" + this.heightPixels + ", screenRealMetrics='" + this.screenRealMetrics + '\'' + ", widthPixels=" + this.widthPixels + ", density=" + this.density + ", densityDpi=" + this.densityDpi + ", densityDpiStr='" + this.densityDpiStr + '\'' + ", scaledDensity=" + this.scaledDensity + ", xdpi=" + this.xdpi + ", ydpi=" + this.ydpi + ", density_default=" + this.density_default + '}';
            }
            int i = 19012 + 19012 + 14323;
            while (true) {
            }
        }
    }
}
