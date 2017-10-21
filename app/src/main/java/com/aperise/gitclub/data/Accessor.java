package com.aperise.gitclub.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public abstract class Accessor {
//    public int delete(Uri uri, String selection, String[] selectionArgs);
//
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder);
//
//    public Uri insert(Uri uri, ContentValues values);
//
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs);

    Context mContext;

    public Accessor(Context context) {
        mContext = context;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mContext.getContentResolver().delete(uri, selection, selectionArgs);
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return mContext.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public Uri insert(Uri uri, ContentValues values) {
        return mContext.getContentResolver().insert(uri, values);
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return mContext.getContentResolver().update(uri, values, selection, selectionArgs);
    }
}
