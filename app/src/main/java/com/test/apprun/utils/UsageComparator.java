package com.test.apprun.utils;

import android.app.usage.UsageStats;
import android.os.Build;

import java.util.Comparator;

/**
 * 运行时间排行
 * Created by Administrator on 2017/12/22.
 */
public class UsageComparator implements Comparator<UsageStats> {
    @Override
    public int compare(UsageStats usageStats1, UsageStats usageStats2) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            return 0;
        }
        long time1 = usageStats1.getLastTimeStamp();
        long time2 = usageStats2.getLastTimeStamp();
        int compare = 0;

        //降序排列
        if (time1 > time2){
            compare = -1;
        }else if (time1 < time2){
            compare = 1;
        }else{
            compare = 0;
        }
        return compare;
    }
}
