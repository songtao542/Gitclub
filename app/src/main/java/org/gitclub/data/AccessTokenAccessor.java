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
import javax.inject.Singleton;

/**
 * Created by wangsongtao on 2017/5/14.
 */
public class AccessTokenAccessor extends Accessor {

    @Inject
    public AccessTokenAccessor(Context context) {
        super(context);
    }

    /**
     * @param accessToken
     * @return true if insertOrUpdate success, otherwise false
     */
    public boolean insertOrUpdateByEmail(AccessToken accessToken) {
        ArrayList<AccessToken> tokens = AccessToken.fromCursor(query(GitclubContent.AccessToken.CONTENT_URI, null, GitclubContent.AccessTokenColumns.USER_EMAIL + "=?", new String[]{accessToken.email}, null));
        String selection = null;
        String[] selectionArgs = null;
        if (tokens != null && tokens.size() > 0) {
            selection = GitclubContent.AccessTokenColumns._ID + "=?";
            selectionArgs = new String[]{String.valueOf(tokens.get(0).id)};
        }
        return insertOrUpdate(accessToken, selection, selectionArgs);
    }

    /**
     * @param accessToken
     * @param selection
     * @param selectionArgs
     * @return true if insertOrUpdate success, otherwise false
     */
    public boolean insertOrUpdate(AccessToken accessToken, String selection, String[] selectionArgs) {
        if (selection != null && selection.length() > 0) {
            int rows = update(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues(), selection, selectionArgs);
            SLog.d(this, "insertOrUpdate->update rows=" + rows);
            if (rows > 0) {
                return true;
            }
        } else {
            Uri uri = insert(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues());
            SLog.d(this, "insertOrUpdate->insert uri=" + uri);
            if (uri != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param email
     * @param userKey
     * @return true if update success, otherwise false
     */
    public boolean updateUserKeyByEmail(String email, long userKey) {
        if (email != null && userKey >= 0) {
            AccessToken accessToken = queryByEmail(email);
            if (accessToken != null) {
                accessToken.userId = userKey;
                int rows = update(GitclubContent.AccessToken.CONTENT_URI, accessToken.toContentValues(), GitclubContent.AccessTokenColumns._ID + "=?", new String[]{String.valueOf(accessToken.id)});
                if (rows > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param email
     * @return A AccessToken or null.
     */
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
