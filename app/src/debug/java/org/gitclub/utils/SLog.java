package org.gitclub.utils;


import android.util.Log;

/**
 * Created by le on 5/10/17.
 */

public class SLog {

    private static final String TAG = "GITCLUB_DEBUT";

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(Object invoker, String msg) {
        d(TAG, invoker.getClass().getSimpleName() + ": " + msg);
    }

    public static void d(String tag, String msg) {
        if (tag == null) {
            tag = TAG;
        }
        Log.d(tag, msg);
    }

}
