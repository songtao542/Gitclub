package org.gitclub.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.transition.Slide;

import org.gitclub.provider.GitclubContent;
import org.gitclub.utils.SLog;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by le on 5/9/17.
 */

public class AccessToken extends Model {
    //{"access_token":"b482e9061b6a6c32c3ac72dd0cd94ed6093bb887","token_type":"bearer","scope":"user:email"}
    public long id;
    public long userId;
    public String email;
    public String accessToken;
    public String tokenType;
    public String scope;

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(GitclubContent.AccessTokenColumns.ACCESS_TOKEN, accessToken);
        values.put(GitclubContent.AccessTokenColumns.USER_EMAIL, email);
        values.put(GitclubContent.AccessTokenColumns.TOKEN_TYPE, tokenType);
        values.put(GitclubContent.AccessTokenColumns.SCOPE, scope);
        return values;
    }

    public static ArrayList<AccessToken> fromCursor(Cursor cursor) {
        SLog.d("AccessToken fromCursor cursor=" + cursor);
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        ArrayList<AccessToken> tokens = new ArrayList<>(cursor.getCount());
        try {
            while (cursor.moveToNext()) {
                AccessToken token = new AccessToken();
                token.id = cursor.getLong(cursor.getColumnIndex(GitclubContent.AccessTokenColumns._ID));
                token.userId = cursor.getLong(cursor.getColumnIndex(GitclubContent.AccessTokenColumns.USER_KEY));
                token.email = cursor.getString(cursor.getColumnIndex(GitclubContent.AccessTokenColumns.USER_EMAIL));
                token.accessToken = cursor.getString(cursor.getColumnIndex(GitclubContent.AccessTokenColumns.ACCESS_TOKEN));
                token.tokenType = cursor.getString(cursor.getColumnIndex(GitclubContent.AccessTokenColumns.TOKEN_TYPE));
                token.scope = cursor.getString(cursor.getColumnIndex(GitclubContent.AccessTokenColumns.SCOPE));
                tokens.add(token);
                SLog.d("AccessToken fromCursor  token=" + token);
            }
        } finally {
            cursor.close();
        }
        return tokens;
    }
}
