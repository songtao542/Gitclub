package com.aperise.gitclub.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.aperise.gitclub.utils.SLog;

import java.util.ArrayList;

public class GitclubProvider extends ContentProvider {
    private static final String TAG = "GitclubProvider";

    public static final String DATABASE_NAME = "GitclubProvider.db";
    private static final Object sDatabaseLock = new Object();

    private SQLiteDatabase mDatabase;

    private static final int ACCESS_TOKEN_BASE = 0;
    private static final int ACCESS_TOKEN = ACCESS_TOKEN_BASE;
    private static final int ACCESS_TOKEN_ID = ACCESS_TOKEN_BASE + 1;

    private static final int USER_BASE = 0x1000;
    private static final int USER = USER_BASE;
    private static final int USER_ID = USER_BASE + 1;
    private static final int USER_NAME = USER_BASE + 2;
    private static final int USER_EMAIL = USER_BASE + 3;


    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    {
        // All access token
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "access_token", ACCESS_TOKEN);
        // A specific access token
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "access_token/#", ACCESS_TOKEN_ID);

        // All user
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "user", USER);
        // A specific user
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "user/#", USER_ID);
        // A specific user
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "user/name/*", USER_NAME);
        sURIMatcher.addURI(GitclubContent.AUTHORITY, "user/email/*", USER_EMAIL);

    }

    public GitclubProvider() {
    }

    @Override
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        switch (match) {
            case ACCESS_TOKEN:
                return "vnd.android.cursor.dir/gitclub-access-token";
            case ACCESS_TOKEN_ID:
                return "vnd.android.cursor.item/gitclub-access-token";
            case USER:
                return "vnd.android.cursor.dir/gitclub-user";
            case USER_ID:
                return "vnd.android.cursor.item/gitclub-user";
            case USER_NAME:
                return "vnd.android.cursor.item/gitclub-user";
            case USER_EMAIL:
                return "vnd.android.cursor.item/gitclub-user";
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SLog.d(TAG, "delete uri=" + uri);
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        switch (match) {
            case ACCESS_TOKEN:
                return db.delete(GitclubContent.AccessToken.TABLE_NAME, selection, selectionArgs);
            case ACCESS_TOKEN_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "delete ACCESS_TOKEN_ID parameter id=" + longId);
                return db.delete(GitclubContent.AccessToken.TABLE_NAME, GitclubContent.AccessTokenColumns._ID + "=" + longId + " AND (" + selection + ")", selectionArgs);
            case USER:
                return db.delete(GitclubContent.User.TABLE_NAME, selection, selectionArgs);
            case USER_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "delete USER_ID parameter id=" + longId);
                return db.delete(GitclubContent.User.TABLE_NAME, GitclubContent.UserColumns._ID + "=" + longId + " AND (" + selection + ")", selectionArgs);
            case USER_NAME:
                String name = uri.getLastPathSegment();
                SLog.d(TAG, "delete USER_NAME parameter name=" + name);
                return db.delete(GitclubContent.User.TABLE_NAME, GitclubContent.UserColumns.LOGIN + "=" + name + " AND (" + selection + ")", selectionArgs);
            case USER_EMAIL:
                String email = uri.getLastPathSegment();
                SLog.d(TAG, "delete USER_EMAIL parameter email=" + email);
                return db.delete(GitclubContent.User.TABLE_NAME, GitclubContent.UserColumns.EMAIL + "=" + email + " AND (" + selection + ")", selectionArgs);
            default:
                SLog.d(TAG, "delete uri match nothing, uri=" + uri);
                return 0;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SLog.d(TAG, "insert uri=" + uri);
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        Uri resultUri = null;
        switch (match) {
            case ACCESS_TOKEN:
                longId = db.insert(GitclubContent.AccessToken.TABLE_NAME, null, values);
                SLog.d(TAG, "insert ACCESS_TOKEN row id=" + longId);
                resultUri = ContentUris.withAppendedId(GitclubContent.AccessToken.CONTENT_URI, longId);
                break;
            case ACCESS_TOKEN_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "insert ACCESS_TOKEN_ID parameter id=" + longId);
                values.put(GitclubContent.AccessTokenColumns._ID, longId);
                insert(GitclubContent.AccessToken.CONTENT_URI, values);
                resultUri = ContentUris.withAppendedId(GitclubContent.AccessToken.CONTENT_URI, longId);
                break;
            case USER:
                longId = db.insert(GitclubContent.User.TABLE_NAME, null, values);
                SLog.d(TAG, "insert USER row id=" + longId);
                resultUri = ContentUris.withAppendedId(GitclubContent.User.CONTENT_URI, longId);
                break;
            case USER_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "insert USER_ID parameter id=" + longId);
                values.put(GitclubContent.UserColumns._ID, longId);
                resultUri = insert(GitclubContent.User.CONTENT_URI, values);
                break;
            case USER_NAME:
                String name = uri.getLastPathSegment();
                SLog.d(TAG, "insert USER_NAME parameter name=" + name);
                values.put(GitclubContent.UserColumns.LOGIN, name);
                resultUri = insert(GitclubContent.User.CONTENT_URI, values);
                break;
            case USER_EMAIL:
                String email = uri.getLastPathSegment();
                SLog.d(TAG, "insert USER_EMAIL parameter email=" + email);
                values.put(GitclubContent.UserColumns.EMAIL, email);
                resultUri = insert(GitclubContent.User.CONTENT_URI, values);
                break;
            default:
                SLog.d(TAG, "insert uri match nothing, uri=" + uri);
                break;
        }
        return resultUri;
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SLog.d(TAG, "query uri=" + uri);
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        String limit = uri.getQueryParameter(GitclubContent.PARAMETER_LIMIT);
        switch (match) {
            case ACCESS_TOKEN:
                return db.query(GitclubContent.AccessToken.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder, limit);
            case ACCESS_TOKEN_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "query ACCESS_TOKEN_ID parameter id=" + longId);
                return db.query(GitclubContent.AccessToken.TABLE_NAME, projection, GitclubContent.AccessTokenColumns._ID + "='" + longId + "'" + (selection != null ? (" AND (" + selection + ")") : ""),
                        selectionArgs, null, null, sortOrder, limit);
            case USER:
                return db.query(GitclubContent.User.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder, limit);
            case USER_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "query USER_ID parameter id=" + longId);
                return db.query(GitclubContent.User.TABLE_NAME, projection, GitclubContent.UserColumns._ID + "='" + longId + "'" + (selection != null ? (" AND (" + selection + ")") : ""),
                        selectionArgs, null, null, sortOrder, limit);
            case USER_NAME:
                String name = uri.getLastPathSegment();
                SLog.d(TAG, "query USER_NAME parameter name=" + name);
                return db.query(GitclubContent.User.TABLE_NAME, projection, GitclubContent.UserColumns.LOGIN + "='" + name + "'" + (selection != null ? (" AND (" + selection + ")") : ""),
                        selectionArgs, null, null, sortOrder, limit);
            case USER_EMAIL:
                String email = uri.getLastPathSegment();
                SLog.d(TAG, "query USER_EMAIL parameter email=" + email);
                return db.query(GitclubContent.User.TABLE_NAME, projection, GitclubContent.UserColumns.EMAIL + "='" + email + "'" + (selection != null ? (" AND (" + selection + ")") : ""),
                        selectionArgs, null, null, sortOrder, limit);
            default:
                SLog.d(TAG, "query uri match nothing, uri=" + uri);
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SLog.d(TAG, "update uri=" + uri);
        int match = sURIMatcher.match(uri);
        final Context context = getContext();
        final SQLiteDatabase db = getDatabase(context);
        long longId;
        switch (match) {
            case ACCESS_TOKEN:
                return db.update(GitclubContent.AccessToken.TABLE_NAME, values, selection, selectionArgs);
            case ACCESS_TOKEN_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "update ACCESS_TOKEN_ID parameter id=" + longId);
                values.put(GitclubContent.AccessTokenColumns._ID, longId);
                return db.update(GitclubContent.AccessToken.TABLE_NAME, values, selection, selectionArgs);
            case USER:
                return db.update(GitclubContent.User.TABLE_NAME, values, selection, selectionArgs);
            case USER_ID:
                longId = Long.parseLong(uri.getPathSegments().get(1));
                SLog.d(TAG, "update USER_ID parameter id=" + longId);
                values.put(GitclubContent.UserColumns._ID, longId);
                return db.update(GitclubContent.User.TABLE_NAME, values, selection, selectionArgs);
            case USER_NAME:
                String name = uri.getPathSegments().get(1);
                SLog.d(TAG, "update USER_NAME parameter name=" + name);
                values.put(GitclubContent.UserColumns.LOGIN, name);
                return db.update(GitclubContent.User.TABLE_NAME, values, selection, selectionArgs);
            case USER_EMAIL:
                String email = uri.getLastPathSegment();
                SLog.d(TAG, "update USER_EMAIL parameter email=" + email);
                values.put(GitclubContent.UserColumns.EMAIL, email);
                return db.update(GitclubContent.User.TABLE_NAME, values, selection, selectionArgs);
            default:
                SLog.d(TAG, "update uri match nothing, uri=" + uri);
                return 0;
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = getDatabase(getContext());
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    public SQLiteDatabase getDatabase(Context context) {
        synchronized (sDatabaseLock) {
            // Always return the cached database, if we've got one
            if (mDatabase != null) {
                return mDatabase;
            }
            DBHelper.DatabaseHelper helper = new DBHelper.DatabaseHelper(context, DATABASE_NAME);
            mDatabase = helper.getWritableDatabase();
            return mDatabase;
        }
    }
}
