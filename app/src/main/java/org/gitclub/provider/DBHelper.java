package org.gitclub.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import org.gitclub.provider.GitclubContent.User;
import org.gitclub.provider.GitclubContent.UserColumns;
import org.gitclub.provider.GitclubContent.AccessToken;
import org.gitclub.provider.GitclubContent.AccessTokenColumns;

/**
 * Created by le on 3/9/17.
 */

public class DBHelper {
    public static final int DATABASE_VERSION = 1;

    private static final String TRIGGER_USER_DELETE =
            "create trigger user_delete before delete on " + User.TABLE_NAME +
                    " begin delete from " + AccessToken.TABLE_NAME +
                    " where " + AccessTokenColumns.USER_KEY + "=old." + BaseColumns._ID +
                    "; end";

    private static final String TRIGGER_ACCESS_TOKEN_DELETE =
            "create trigger access_token_delete before delete on " + AccessToken.TABLE_NAME +
                    " begin delete from " + User.TABLE_NAME +
                    " where " + UserColumns.EMAIL + "=old." + AccessTokenColumns.USER_KEY +
                    "; end";

    static void createUserTable(SQLiteDatabase db) {
        String s = " (" + UserColumns._ID + " integer primary key autoincrement, "
                + UserColumns.LOGIN + " text, "
                + UserColumns.ID + " integer, "
                + UserColumns.AVATAR_URL + " text, "
                + UserColumns.GRAVATAR_ID + " text, "
                + UserColumns.URL + " text, "
                + UserColumns.HTML_URL + " text, "
                + UserColumns.FOLLOWERS_URL + " text, "
                + UserColumns.FOLLOWING_URL + " text, "
                + UserColumns.GISTS_URL + " text, "
                + UserColumns.STARRED_URL + " text, "
                + UserColumns.SUBSCRIPTIONS_URL + " text, "
                + UserColumns.ORGANIZATIONS_URL + " text, "
                + UserColumns.REPOS_URL + " text, "
                + UserColumns.EVENTS_URL + " text, "
                + UserColumns.RECEIVED_EVENTS_URL + " text, "
                + UserColumns.TYPE + " text, "
                + UserColumns.SITE_ADMIN + " integer, "
                + UserColumns.NAME + " text, "
                + UserColumns.COMPANY + " text, "
                + UserColumns.BLOG + " text, "
                + UserColumns.LOCATION + " text, "
                + UserColumns.EMAIL + " text, "
                + UserColumns.HIREABLE + " text, "
                + UserColumns.BIO + " text, "
                + UserColumns.PUBLIC_REPOS + " integer, "
                + UserColumns.PUBLIC_GISTS + " integer, "
                + UserColumns.FOLLOWERS + " integer, "
                + UserColumns.FOLLOWING + " integer, "
                + UserColumns.CREATED_AT + " text, "
                + UserColumns.UPDATED_AT + " text, "
                + UserColumns.TOTAL_PRIVATE_REPOS + " integer, "
                + UserColumns.OWNED_PRIVATE_REPOS + " integer, "
                + UserColumns.DISK_USAGE + " integer, "
                + UserColumns.COLLABORATORS + " integer, "
                + UserColumns.TWO_FACTOR_AUTHENTICATION + " integer "
                + ");";
        db.execSQL("create table " + User.TABLE_NAME + s);
        // Deleting an user deletes associated access_token
        db.execSQL(TRIGGER_USER_DELETE);
    }

    static void createAccessTokenTable(SQLiteDatabase db) {
        String s = " (" + AccessTokenColumns._ID + " integer primary key autoincrement, "
                + AccessTokenColumns.USER_KEY + " integer, "
                + AccessTokenColumns.USER_EMAIL + " text, "
                + AccessTokenColumns.ACCESS_TOKEN + " text, "
                + AccessTokenColumns.TOKEN_TYPE + " text, "
                + AccessTokenColumns.SCOPE + " text"
                + ");";
        db.execSQL("create table " + AccessToken.TABLE_NAME + s);
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
