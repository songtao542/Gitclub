package org.gitclub.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.transition.Slide;

import org.gitclub.model.AccessToken;
import org.gitclub.provider.GitclubContent;
import org.gitclub.utils.SLog;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by wangsongtao on 2017/5/14.
 */

public class AccessTokenAccessor extends Accessor {


    @Inject
    public AccessTokenAccessor(Context context) {
        super(context);
    }

    public void save(AccessToken accessToken) {
        insert(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues());
    }

    public void insertOrUpdateByEmail(AccessToken accessToken) {
        ArrayList<AccessToken> tokens = AccessToken.fromCursor(query(GitclubContent.AccessToken.CONTENT_URI, null, GitclubContent.AccessTokenColumns.USER_EMAIL + "=?", new String[]{accessToken.email}, null));
        String selection = null;
        String[] selectionArgs = null;
        if (tokens != null && tokens.size() > 0) {
            selection = GitclubContent.AccessTokenColumns._ID + "=?";
            selectionArgs = new String[]{String.valueOf(tokens.get(0).id)};
        }
        insertOrUpdate(accessToken, selection, selectionArgs);
    }

    public void insertOrUpdate(AccessToken accessToken, String selection, String[] selectionArgs) {
        if (selection != null && selection.length() > 0) {
            update(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues(), selection, selectionArgs);
        } else {
            insert(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues());
        }
    }

    public void updateUserKeyByEmail(String email, long userKey) {
        if (email != null && userKey > 0) {
            AccessToken accessToken = queryByEmail(email);
            if (accessToken != null) {
                accessToken.userId = userKey;
                update(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues(), GitclubContent.AccessTokenColumns._ID + "=?", new String[]{String.valueOf(accessToken.id)});
            }
        }
    }

    public AccessToken queryByEmail(String email) {
        SLog.d("queryByEmail email=" + email);
        if (email == null) {
            return null;
        }
        ArrayList<AccessToken> tokens = AccessToken.fromCursor(query(GitclubContent.AccessToken.CONTENT_URI, null, GitclubContent.AccessTokenColumns.USER_EMAIL + "=?", new String[]{email}, null));
        if (tokens != null && tokens.size() > 0) {
            return tokens.get(0);
        }
        return null;
    }

}
