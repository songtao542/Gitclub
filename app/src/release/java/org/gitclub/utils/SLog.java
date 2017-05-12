package org.gitclub.utils;

import android.util.Log;

/**
 * Created by le on 5/9/17.
 */

public class SLog {

    private static final String TAG = "GITCLUB";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (tag == null) {
            tag = TAG;
        }
        if (Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, msg);
        }
    }


}
