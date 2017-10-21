package com.aperise.gitclub.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

/**
 * Created by wangsongtao on 2017/5/29.
 */

public class Color {

    @TargetApi(Build.VERSION_CODES.M)
    public static int getColor(Context context, int colorRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorRes);
        } else {
            return context.getResources().getColor(colorRes);
        }
    }
}
