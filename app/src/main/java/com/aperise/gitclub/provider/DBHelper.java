package com.aperise.gitclub.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by le on 3/9/17.
 */

public class DBHelper {
    public static final int DATABASE_VERSION = 1;

    private static final String TRIGGER_USER_DELETE =
            "create trigger user_delete before delete on " + GitclubContent.User.TABLE_NAME +
                    " begin delete from " + GitclubContent.AccessToken.TABLE_NAME +
                    " where " + GitclubContent.AccessTokenColumns.USER_KEY + "=old." + BaseColumns._ID +
                    "; end";

    private static final String TRIGGER_ACCESS_TOKEN_DELETE =
            "create trigger access_token_delete before delete on " + GitclubContent.AccessToken.TABLE_NAME +
                    " begin delete from " + GitclubContent.User.TABLE_NAME +
                    " where " + GitclubContent.UserColumns.EMAIL + "=old." + GitclubContent.AccessTokenColumns.USER_KEY +
                    "; end";

    static void createUserTable(SQLiteDatabase db) {
        String s = " (" + GitclubContent.UserColumns._ID + " integer primary key autoincrement, "
                + GitclubContent.UserColumns.LOGIN + " text, "
                + GitclubContent.UserColumns.ID + " integer, "
                + GitclubContent.UserColumns.AVATAR_URL + " text, "
                + GitclubContent.UserColumns.GRAVATAR_ID + " text, "
                + GitclubContent.UserColumns.URL + " text, "
                + GitclubContent.UserColumns.HTML_URL + " text, "
                + GitclubContent.UserColumns.FOLLOWERS_URL + " text, "
                + GitclubContent.UserColumns.FOLLOWING_URL + " text, "
                + GitclubContent.UserColumns.GISTS_URL + " text, "
                + GitclubContent.UserColumns.STARRED_URL + " text, "
                + GitclubContent.UserColumns.SUBSCRIPTIONS_URL + " text, "
                + GitclubContent.UserColumns.ORGANIZATIONS_URL + " text, "
                + GitclubContent.UserColumns.REPOS_URL + " text, "
                + GitclubContent.UserColumns.EVENTS_URL + " text, "
                + GitclubContent.UserColumns.RECEIVED_EVENTS_URL + " text, "
                + GitclubContent.UserColumns.TYPE + " text, "
                + GitclubContent.UserColumns.SITE_ADMIN + " integer, "
                + GitclubContent.UserColumns.NAME + " text, "
                + GitclubContent.UserColumns.COMPANY + " text, "
                + GitclubContent.UserColumns.BLOG + " text, "
                + GitclubContent.UserColumns.LOCATION + " text, "
                + GitclubContent.UserColumns.EMAIL + " text, "
                + GitclubContent.UserColumns.HIREABLE + " text, "
                + GitclubContent.UserColumns.BIO + " text, "
                + GitclubContent.UserColumns.PUBLIC_REPOS + " integer, "
                + GitclubContent.UserColumns.PUBLIC_GISTS + " integer, "
                + GitclubContent.UserColumns.FOLLOWERS + " integer, "
                + GitclubContent.UserColumns.FOLLOWING + " integer, "
                + GitclubContent.UserColumns.CREATED_AT + " text, "
                + GitclubContent.UserColumns.UPDATED_AT + " text, "
                + GitclubContent.UserColumns.TOTAL_PRIVATE_REPOS + " integer, "
                + GitclubContent.UserColumns.OWNED_PRIVATE_REPOS + " integer, "
                + GitclubContent.UserColumns.DISK_USAGE + " integer, "
                + GitclubContent.UserColumns.COLLABORATORS + " integer, "
                + GitclubContent.UserColumns.TWO_FACTOR_AUTHENTICATION + " integer "
                + ");";
        db.execSQL("create table " + GitclubContent.User.TABLE_NAME + s);
        // Deleting an user deletes associated access_token
        db.execSQL(TRIGGER_USER_DELETE);
    }

    static void createAccessTokenTable(SQLiteDatabase db) {
        String s = " (" + GitclubContent.AccessTokenColumns._ID + " integer primary key autoincrement, "
                + GitclubContent.AccessTokenColumns.USER_KEY + " integer, "
                + GitclubContent.AccessTokenColumns.USER_EMAIL + " text, "
                + GitclubContent.AccessTokenColumns.ACCESS_TOKEN + " text, "
                + GitclubContent.AccessTokenColumns.TOKEN_TYPE + " text, "
                + GitclubContent.AccessTokenColumns.SCOPE + " text"
                + ");";
        db.execSQL("create table " + GitclubContent.AccessToken.TABLE_NAME + s);
        // Deleting an access token deletes associated user
        db.execSQL(TRIGGER_ACCESS_TOKEN_DELETE);
    }


    protected static class DatabaseHelper extends SQLiteOpenHelper {
        final Context mContext;

        DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
            mContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            createUserTable(db);
            createAccessTokenTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
