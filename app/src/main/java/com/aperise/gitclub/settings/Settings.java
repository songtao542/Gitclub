package com.aperise.gitclub.settings;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;

/**
 * Created by song on 17-11-17.
 */

public class Settings {
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String VALUE = "value";

    public static final String CALL_METHOD_GET = "method_get";
    public static final String CALL_METHOD_PUT = "method_put";
    public static final String AUTHORITY = "com.ape.settings";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/settings");
    private static final String TAG = "Settings";

    private static final String[] SELECT_VALUE = new String[]{VALUE};
    private static final String NAME_EQ_PLACEHOLDER = "name=?";

    public static boolean putFloat(Context context, String name, float value) {
        return putString(context, name, Float.toString(value));
    }

    public static boolean putInt(Context context, String name, int value) {
        return putString(context, name, Integer.toString(value));
    }

    public static boolean putLong(Context context, String name, long value) {
        return putString(context, name, Long.toString(value));
    }

    public static boolean putString(Context context, String name, String value) {
        try {
            Bundle arg = new Bundle();
            arg.putString(VALUE, value);
            context.getContentResolver().call(CONTENT_URI, CALL_METHOD_PUT, name, arg);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static float getFloat(Context context, String name) throws SettingNotFoundException {
        String v = getString(context, name);
        if (v == null) {
            throw new SettingNotFoundException(name);
        }
        try {
            return Float.parseFloat(v);
        } catch (NumberFormatException e) {
            throw new SettingNotFoundException(name);
        }
    }

    public static float getFloat(Context context, String name, float def) {
        String v = getString(context, name);
        try {
            return v != null ? Float.parseFloat(v) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }


    public static int getInt(Context context, String name) throws SettingNotFoundException {
        String v = getString(context, name);
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            throw new SettingNotFoundException(name);
        }
    }

    public static int getInt(Context context, String name, int def) {
        String v = getString(context, name);
        try {
            return v != null ? Integer.parseInt(v) : def;
        } catch (NumberFormatException e) {
            return def;
        }
    }


    public static long getLong(Context context, String name) throws SettingNotFoundException {
        String valString = getString(context, name);
        try {
            return Long.parseLong(valString);
        } catch (NumberFormatException e) {
            throw new SettingNotFoundException(name);
        }
    }


    public static long getLong(Context context, String name, long def) {
        String valString = getString(context, name);
        long value;
        try {
            value = valString != null ? Long.parseLong(valString) : def;
        } catch (NumberFormatException e) {
            value = def;
        }
        return value;
    }


    public static String getString(Context context, String name) {
        ContentResolver cr = context.getContentResolver();
        try {
            Bundle b = cr.call(CONTENT_URI, CALL_METHOD_GET, name, null);
            if (b != null) {
                String value = b.getString(VALUE);
                return value;
            }
        } catch (Exception e) {
            Log.w(TAG, "Can't get key " + name + " from " + CONTENT_URI, e);
        }

        Cursor c = null;
        try {
            c = cr.query(CONTENT_URI, SELECT_VALUE, NAME_EQ_PLACEHOLDER, new String[]{name}, null, null);
            if (c == null) {
                Log.w(TAG, "Can't get key " + name + " from " + CONTENT_URI);
                return null;
            }
            String value = c.moveToNext() ? c.getString(0) : null;
            return value;
        } catch (Exception e) {
            Log.w(TAG, "Can't get key " + name + " from " + CONTENT_URI, e);
            return null;
        } finally {
            if (c != null) c.close();
        }
    }

    public static Uri getUriFor(String name) {
        return getUriFor(CONTENT_URI, name);
    }


    public static Uri getUriFor(Uri uri, String name) {
        return Uri.withAppendedPath(uri, name);
    }

    public static class SettingNotFoundException extends AndroidException {
        public SettingNotFoundException(String msg) {
            super(msg);
        }
    }
}
