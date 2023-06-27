package com.wish.lmbank.dialer.bean;

import com.wish.lmbank.R;
import com.wish.lmbank.R2;
import com.wish.lmbank.dialer.utils.DateFormatUtils;

import java.io.Serializable;
import java.util.Calendar;

import gv00l3ah.mvdt7w.bb7d7pu7;

import com.wish.lmbank.R2;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/dialer/bean/CallLogBean.class */
public class CallLogBean implements Serializable {
    public String c;
    public int count;
    public String date;
    public String dateTitle;
    public String duration;
    public String id;
    public boolean isCheck = false;
    public String name;
    public String number;
    public int type;

    public void setCount(int i) {
        this.count = i + 1;
    }

    public void setDate(long j) {
        Calendar.getInstance();
        Calendar.getInstance().setTimeInMillis(j);
//         this.date = DateFormatUtils.b(Long.valueOf(j), bb7d7pu7.m5998("CEkBAVMEBA"));
        this.date = DateFormatUtils.b(Long.valueOf(j), "a hh:mm");
//         this.dateTitle = DateFormatUtils.b(Long.valueOf(j), bb7d7pu7.m5998("MDAwMILs7UkkJIXy_UkNDYX01Q"));
        this.dateTitle = DateFormatUtils.b(Long.valueOf(j), "YYYY년 MM월 dd일");
    }

    public void setDuration(long j) {
        String str;
        if (j > 0) {
//             str = bb7d7pu7.m5998("RUk") + DateFormatUtils.a(j);
            str = ", " + DateFormatUtils.a(j);
        } else {
            str = "";
        }
        this.duration = str;
    }

    public void setType(String str) {
        String m5998;
        int parseInt = Integer.parseInt(str);
        if (parseInt == 1) {
            this.type = R.drawable.bw;
//             m5998 = bb7d7pu7.m5998("heHxheLJhcnthPD9");
            m5998 = "수신전화";
        } else if (parseInt == 2) {
            this.type = R.drawable.by;
//             m5998 = bb7d7pu7.m5998("gtn1heLJhcnthPD9");
            m5998 = "발신전화";
        } else if (parseInt == 3) {
            this.type = R.drawable.bx;
//             m5998 = bb7d7pu7.m5998("gt_phffFhc34SYXJ7YTw_Q");
            m5998 = "부재중 전화";
        } else if (parseInt != 5) {
            return;
        } else {
            this.type = R.drawable.bz;
//             m5998 = bb7d7pu7.m5998("g9jZhcnhhPz1SYXJ7YTw_Q");
            m5998 = "거절한 전화";
        }
        this.c = m5998;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setNumber(String str) {
        this.number = str;
    }
}
